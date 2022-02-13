package com.example.demo.controller;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("secKill")
public class SecKillController {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    OrderMapper orderMapper;
    @Resource
    ProductMapper productMapper;
    @Resource
    ObjectMapper objectMapper;

    //将需要秒杀的商品id和库存数量，在秒杀开始前存入Redis中
    @PostConstruct
    private void init() {
        //获取key值，先清空redis缓存
        Set<String> keys = stringRedisTemplate.keys("*");
        if (keys != null) {
            stringRedisTemplate.delete(keys);
        }

        //获取库中的数据，把数据存入Redis中缓存
        List<Product> products = productMapper.selectList();
        for (Product product : products) {
            stringRedisTemplate.opsForValue().set("product_stock_" + product.getId(), product.getStock() + "");
        }
        //秒杀过程中，使用 incrby 相关原子自增（自减）指令，扣减库存
        //新线程把数据持久化放到数据库
        new Thread(() -> {
            while (true) {
                //有订单  leftPop移除集合中的左边第一个元素
                if (stringRedisTemplate.opsForList().size("orders") > 0) {
                    String orders = stringRedisTemplate.opsForList().leftPop("orders");
                    try {
                        orderMapper.insert(objectMapper.readValue(orders, Order.class));
                        productMapper.update(objectMapper.readValue(orders, Order.class).getProductId());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    //使用productSellOutMap当基准进行判断商品是否卖完的状态
    private static final ConcurrentHashMap<Long, Boolean> productSellOutMap = new ConcurrentHashMap<>();

    @PostMapping("/{productId}")
    public String secKill(@PathVariable("productId") Long productId, User user) throws JsonProcessingException {
        //判断商品是否卖完
        //使用一个标志当基准进行判断
        if (productSellOutMap.get(productId) != null) {
            return "商品已经售完";
        }

        //将已参加秒杀的用户id，存入 set 中，可以高效的防止同一用户多次抢购

        if (stringRedisTemplate.opsForSet().add(productId + "", user.getName()) == 0) {
            return "用户已经参加过秒杀";
        }
        if (stringRedisTemplate.opsForValue().decrement("product_stock_" + productId) < 0) {
            /**
             * 当订单表里有订单时说明订单卖完
             * 使用一个标志当基准进行判断,在上面声明
             */
            productSellOutMap.put(productId, true);
            //Redis商品数量恢复成0   increment以增量的方式将long值存储在变量中。
            stringRedisTemplate.opsForValue().increment("product_stock_"+ productId);
            return "商品已经售完";
        }
        //生成订单
        Order order = Order.builder()
                .productId(productId)
                .name(user.getName())
                .build();
        //Redis保存订单数据    rightPush在变量右边添加元素值。
        stringRedisTemplate.opsForList().rightPush("orders", objectMapper.writeValueAsString(order));

        return "商品-1";
    }
}
