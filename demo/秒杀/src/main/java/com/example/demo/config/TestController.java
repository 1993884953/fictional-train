package com.example.demo.config;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

//    @Autowired
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/test")
    public String test() {
        // string
        //添加string类型变量
        redisTemplate.opsForValue().set("foo", "hello");
        redisTemplate.opsForValue().set("bar", 100);

        String foo = (String) redisTemplate.opsForValue().get("foo");
        System.out.println(foo);  // hello

        String noData = (String) redisTemplate.opsForValue().get("noData");
        System.out.println(noData); // null

        redisTemplate.opsForValue().increment("bar");
        int bar2 = (int) redisTemplate.opsForValue().get("bar"); // 101
        System.out.println(bar2);

         //hash
        //存储一个user对象的键值对
        User user = new User(3, "Jack");
        redisTemplate.opsForValue().set("user1", user);
        System.out.println(redisTemplate.opsForValue().get("user1"));

        //obj    未赋值为null
        User obj = (User) redisTemplate.opsForValue().get("obj");
        System.out.println(obj);


        //存储一个键值对
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Mary");
        map.put("age", 18);
        redisTemplate.opsForHash().putAll("user2", map);


        Map<Object, Object> user3 = redisTemplate.opsForHash().entries("user2");
        System.out.println(user3);


        // list
        redisTemplate.opsForList().leftPush("names", "jack");
        redisTemplate.opsForList().leftPush("names", "mary");
        redisTemplate.opsForList().leftPush("names", "lily");

        System.out.println(redisTemplate.opsForList().rightPop("names"));  // jack
        System.out.println(redisTemplate.opsForList().rightPop("names"));  // mary
        System.out.println(redisTemplate.opsForList().rightPop("names"));  // lily
        System.out.println(redisTemplate.opsForList().rightPop("names"));  // null
        return "ok";
    }

    public static class User {
        private int id;
        private String name;

        public User() {
        }

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}