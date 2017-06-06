package korenski;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import korenski.intercepting.AuthorizationInterceptor;
import korenski.intercepting.SpecialInterceptor;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
@Configuration
public class MtbApplication extends WebMvcConfigurerAdapter {

	@Bean
	public AuthorizationInterceptor authorizationInterceptor() {
	    return new AuthorizationInterceptor();
	}
	
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor()).excludePathPatterns("/special/*");
        registry.addInterceptor(new SpecialInterceptor()).addPathPatterns("/special/*");
    }
    
	public static void main(String[] args) {
		SpringApplication.run(MtbApplication.class, args);
	}
}
