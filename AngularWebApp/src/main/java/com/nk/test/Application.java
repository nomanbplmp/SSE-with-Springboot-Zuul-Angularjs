package com.nk.test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Description;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@SpringBootApplication
@ComponentScan("com.nk.test")
public class Application extends WebMvcConfigurerAdapter{

public static void main(String[] args) {
	SpringApplication.run(Application.class, args);
}


@Bean
@Description("Thymeleaf template resolver serving HTML 5")
public ClassLoaderTemplateResolver templateResolver() {
    
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    
    
    templateResolver.setCacheable(false);
    templateResolver.setSuffix(".html");        
    
    templateResolver.setCharacterEncoding("UTF-8");
    
    return templateResolver;
}

@Bean
@Description("Thymeleaf template engine with Spring integration")
public SpringTemplateEngine templateEngine() {
    
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver());

    return templateEngine;
}

@Bean
@Description("Thymeleaf view resolver")
public ViewResolver viewResolver() {

    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    
    viewResolver.setTemplateEngine(templateEngine());
    viewResolver.setCharacterEncoding("UTF-8");

    return viewResolver;
}    

@Override
public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("index");
}

}
