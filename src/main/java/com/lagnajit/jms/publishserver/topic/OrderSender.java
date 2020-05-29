package com.lagnajit.jms.publishserver.topic;

import com.lagnajit.jms.publishserver.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import static com.lagnajit.jms.publishserver.config.JmsConfig.ORDER_TOPIC;

@Service
public class OrderSender {

  private static Logger log = LoggerFactory.getLogger(OrderSender.class);

  @Autowired
  private JmsTemplate jmsTemplate;

  public void sendTopic(Order order) {
    log.info("sending with convertAndSend() to topic <" + order + ">");
    jmsTemplate.convertAndSend(ORDER_TOPIC, order);
  }

}
