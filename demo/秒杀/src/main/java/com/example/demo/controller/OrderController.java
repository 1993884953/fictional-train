package com.example.demo.controller;

import com.example.demo.dto.OrderCreatedDto;
import com.example.demo.eneity.Commodity;
import com.example.demo.eneity.Order;
import com.example.demo.service.OrderService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class OrderController {

    @Resource
    OrderService orderService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/commodity/create")//需要usrId（因为无token，所以模拟userId），id（物品id）
    public String createCommodity() {
        //删除所有key
        redisTemplate.delete(Objects.requireNonNull(redisTemplate.keys("*")));
        //设置商品信息
        List<Commodity> commodities = new ArrayList<>();
        commodities.add(new Commodity(1, "西瓜", 10));
        commodities.add(new Commodity(2, "香蕉", 15));
        commodities.add(new Commodity(3, "可乐", 20));
        //商品转移到redis储存
        commodities.forEach(item -> {
            redisTemplate.opsForHash().put("commodity", String.valueOf(item.getId()), item);
            redisTemplate.opsForHash().put("quantity", String.valueOf(item.getId()), 999);
        });
        return commodities.toString();
    }

    @GetMapping("/order/create")//需要usrId（因为无token，所以模拟userId），id（物品id）
    public String createOrder(OrderCreatedDto orderCreatedDto) {
        return orderService.createOrder(orderCreatedDto);
    }
    @GetMapping("/order/find")//需要usrId（因为无token，所以模拟userId），id（物品id）
    public String findOrder(Order order) {
        if (order.getOrder()==null|| Objects.equals(order.getOrder(), ""))return "order为空";
        return orderService.findOrder(order).toString();
    }
}
