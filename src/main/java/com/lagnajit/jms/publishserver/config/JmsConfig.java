package com.lagnajit.jms.publishserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lagnajit.jms.publishserver.dto.Order;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.jms.Queue;

import java.util.HashMap;
import java.util.Map;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class JmsConfig {
  @Bean
  public Queue queue() {
    return new ActiveMQQueue("test-queue");
  }
  public static final String ORDER_TOPIC = "order-topic";

  @Bean
  public MessageConverter messageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    Map<String, Class<?>> typeIdMappings = new HashMap<String, Class<?>>();
    typeIdMappings.put("JMS_TYPE", Order.class);
    converter.setTypeIdMappings(typeIdMappings);
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    converter.setObjectMapper(objectMapper());
    return converter;
  }

  @Bean
  public ObjectMapper objectMapper(){
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return mapper;
  }
  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2)
      .select()                 .apis(RequestHandlerSelectors.basePackage("com.lagnajit.jms.publishserver.rest"))
      .paths(regex("/api.*"))
      .build();
  }
}
