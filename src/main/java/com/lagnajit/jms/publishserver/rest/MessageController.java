package com.lagnajit.jms.publishserver.rest;

import com.lagnajit.jms.publishserver.dto.Order;
import com.lagnajit.jms.publishserver.topic.OrderSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;

@RestController
@RequestMapping("/api")
public class MessageController {

  @Autowired
  private Queue queue;

  @Autowired
  private JmsTemplate jmsTemplate;

  @Autowired
  private OrderSender orderSender;

  @GetMapping("message/{message}")
  public ResponseEntity<String> publishQueue(@PathVariable("message") final String message){
    jmsTemplate.convertAndSend(queue, message);
    return new ResponseEntity(message, HttpStatus.OK);
  }

  @PostMapping(path="topic/",consumes = "application/json", produces = "application/json")
  public ResponseEntity<String> publishTopic(@RequestBody Order order){
    orderSender.sendTopic(order);
    return new ResponseEntity("Sent", HttpStatus.OK);
  }

}
