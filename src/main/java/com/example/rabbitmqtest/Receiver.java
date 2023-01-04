package com.example.rabbitmqtest;

import java.util.concurrent.CountDownLatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Receiver {

  /**
   * 메시지 수신 시 처리할 Handler 함수
   * @param message RabbitMQ의 test-queue 으로부터 수신한 메시지
   */
  @RabbitListener(containerFactory = "SampleListenerContainerFactory", queues="test-queue")
  public void onReceiveMessage(SampleMessage message){
    log.info("== Receiver received message: {}", message);
  }

}
