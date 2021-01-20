package com.mobile.technologies;

import com.mobile.technologies.config.ApplicationConfig;
import com.mobile.technologies.service.CustomerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

  public static void main(String[] args) {

    try {
      ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
      CustomerService customerService = ctx.getBean(CustomerService.class);
      customerService.register("customers.csv");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
