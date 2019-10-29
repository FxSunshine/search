package com.sjar.search.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET")
                .maxAge(3600)
                .allowCredentials(true);
        registry.addMapping("/wechat/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET")
                .maxAge(3600)
                .allowCredentials(true);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/mock/wxlogin").setViewName("forward:/mock/wxlogin");
    }
}
/**
 * -------------------_ooOoo_
 * ------------------o8888888o
 * ------------------88" . "88
 * ------------------(| -_- |)
 * ------------------O\  =  /O
 * ---------------____/`---'\____
 * -------------.'  \\|     |//  `.
 * ------------/  \\|||  :  |||//  \
 * -----------/  _||||| -:- |||||-  \
 * -----------|   | \\\  -  /// |   |
 * -----------| \_|  ''\---/''  |   |
 * -----------\  .-\__  `-`  ___/-. /
 * ---------___`. .'  /--.--\  `. . __
 * ------."" '<  `.___\_<|>_/___.'  >'"".
 * -----| | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * -----\  \ `-.   \_ __\ /__ _/   .-` /  /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * -------------------`=---='
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * ------------佛祖保佑-------永无BUG-------------
 */