package com.example.token.config;

import com.example.token.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

//配置文件导入
@Configuration
//导入token拦截器，设置拦截器拦截路径
public class WebMvcConfiguration implements WebMvcConfigurer {

    //@Autowired
    @Resource//将拦截器注入框架,
    TokenInterceptor tokenInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(tokenInterceptor);

        //对单独路径生效
        interceptorRegistration.addPathPatterns("/api/**")
                //排除特殊路径
                .excludePathPatterns("/error","/upload/image", "/api/sms","/api/user/create", "/api/token/create");
    }
}
