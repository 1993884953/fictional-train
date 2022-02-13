package com.example.demo.service;

import com.example.demo.dto.OrderCreatedDto;
import com.example.demo.eneity.Commodity;
import com.example.demo.eneity.Order;
import com.example.demo.mapper.OrderMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    OrderMapper orderMapper;
    private Order orderCreated;
    public String createOrder(OrderCreatedDto orderCreatedDto) {
        //判断商品的存在信息
        Commodity commodity= (Commodity) redisTemplate.opsForHash().get("commodity", String.valueOf(orderCreatedDto.getId()));
        if(commodity==null){
            return "商品不存在";
        }
        //创建用户抢购记录
        Map<Integer,Integer>state=new HashMap<>();
        state.put((int) orderCreatedDto.getId(), (int) orderCreatedDto.getUserId());
        if (redisTemplate.opsForSet().add("state",state)!=1){
            return "禁止多次抢购";
        }

        //判断商品库存
        if((Integer) Objects.requireNonNull(redisTemplate.opsForHash().get("quantity", String.valueOf(orderCreatedDto.getId())))-orderCreatedDto.getQuantity()<1){
            return "库存不足";
        }
        //减去库存数量
        redisTemplate.opsForHash().increment("quantity",String.valueOf(orderCreatedDto.getId()), -orderCreatedDto.getQuantity());
        //存入redis
        Order order=Order.builder()
                .commodityId(orderCreatedDto.getId())
                .userId(orderCreatedDto.getUserId())
                .quantity( orderCreatedDto.getQuantity())
                .price(orderCreatedDto.getQuantity()*commodity.getPrice())
                .order( new SimpleDateFormat("yyyyMM").format(new Date())+UUID.randomUUID().toString().replaceAll("-", ""))
                .build();
        redisTemplate.opsForList().leftPush("ordered",order);
        //生成订单并且储存数组
        new Thread(()->{
            orderMapper.create(order);
        }).start();
        return order.toString();
    }
    public Order findOrder(Order order){
        return orderMapper.find(order);
    }

}
