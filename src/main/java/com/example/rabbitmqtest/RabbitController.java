package com.example.rabbitmqtest;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rabbitmq")
public class RabbitController {

  private final RabbitProducerService rabbitProducerService;

  public RabbitController(RabbitProducerService rabbitProducerService) {
    this.rabbitProducerService = rabbitProducerService;
  }

  @PostMapping("/")
  public void sendMessage(@RequestBody SampleMessage message) {
    rabbitProducerService.sendMessage(message);
  }

  @PostMapping("/convert")
  public void convertAndSendMessage(@RequestBody SampleMessage message) {
    rabbitProducerService.sendConvertMessage(message);
  }

}
