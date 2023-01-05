package com.example.rabbitmqtest;



import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitProducerService {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private Queue queue;

  public void sendMessage(SampleMessage sampleMessage) {
    log.info("RabbitProducerService send {}", sampleMessage.getContent());
    MessageConverter converter = rabbitTemplate.getMessageConverter();
    MessageProperties props = new MessageProperties();
    Message message = converter.toMessage(sampleMessage, props);
    rabbitTemplate.send(message);
  }

  public void sendConvertMessage(SampleMessage sampleMessage) {
    rabbitTemplate.convertAndSend(queue.getName(), sampleMessage);
    log.info("RabbitProducerService convert and send {}", sampleMessage.getContent());
  }

  public void sendConvertSampleMessage(SampleMessage message) {
    rabbitTemplate.convertAndSend(queue.getName(), message);
    log.info("RabbitProducerService send SampleMessage {}", message.getContent());
  }

  public void sendConvertOtherMessage(OtherMessage message) {
    rabbitTemplate.convertAndSend(queue.getName(), message);
    log.info("RabbitProducerService send OtherMessage {}", message.getContent());
  }

  public void sendConvertMessageA(AMessage message) {
    rabbitTemplate.convertAndSend(queue.getName(), message);
    log.info("RabbitProducerService send SampleMessage {}", message.getContent());
  }

  public void sendConvertMessageB(BMessage message) {
    rabbitTemplate.convertAndSend(queue.getName(), message);
    log.info("RabbitProducerService send OtherMessage {}", message.getContent());
  }

  public void sendSetMessageProperty(SampleMessage sampleMessage) {
    rabbitTemplate.convertAndSend(queue.getName(), sampleMessage,
        new MessagePostProcessor() {
          @Override
          public Message postProcessMessage(Message message) throws AmqpException {
            MessageProperties props = message.getMessageProperties();
            props.setHeader("X_ORDER_SOURCE", "WEB");
            return message;
          }
        });
    log.info("RabbitProducerService convert and send message, set message property {}", sampleMessage.getContent());
  }

}
