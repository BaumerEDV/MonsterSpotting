package com.google.baumeredv.monsterspotting;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan("com.google.baumeredv.monsterspotting")
@Component
public class App {

  public static void main(String[] args){
    ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
    context.getBean(App.class);
  }
}
