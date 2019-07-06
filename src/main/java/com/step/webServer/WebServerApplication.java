package com.step.webServer;

import com.step.webServer.intercepror.LoginInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class WebServerApplication extends SpringBootServletInitializer {

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(100);
		executor.setThreadNamePrefix("AsyncMethod-");
		executor.initialize();
		return executor;
	}
//
//	@Bean
//	public HttpSessionIdResolver httpSessionIdResolver() {
//		return HeaderHttpSessionIdResolver.xAuthToken();
//	}
@Bean
public WebMvcConfigurer corsConfigurer() {
	return new WebMvcConfigurerAdapter() {
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/signup", "/login", "/hospital");
		}

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
					.allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
					.allowedOrigins("http://localhost:8080")
					.allowCredentials(true);
		}
	};
}

	public static void main(String[] args) {
		SpringApplication.run(WebServerApplication.class, args);
	}
}
