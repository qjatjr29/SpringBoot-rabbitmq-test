package com.example.rabbitmqtest.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.yml")
@Configuration
public class ReceiverConfiguration {

  @Value("${spring.rabbitmq.host}")
  private String RABBITMQ_HOST;

  @Value("${spring.rabbitmq.port}")
  private int RABBITMQ_PORT;

  @Value("${spring.rabbitmq.username}")
  private String RABBITMQ_USERNAME;

  @Value("${spring.rabbitmq.password}")
  private String RABBITMQ_PASSWORD;

  @Value("${spring.rabbitmq.template.routing-key}")
  private String ROUTING_KEY;

  @Value("${spring.rabbitmq.template.exchange}")
  private String EXCHANGE_NAME;

  @Value("${spring.rabbitmq.template.default-receive-queue}")
  private String QUEUE_NAME;


  /**
   * RabbitMQ Server와 connection을 생성하는 factory 객체 반환
   *
   * @return 초기화된 RabbitMQ ConnectionFactory 객체
   */
  @Bean
  public ConnectionFactory getConnectionFactory() {
    ConnectionFactory connectionFactory = new CachingConnectionFactory(RABBITMQ_HOST, RABBITMQ_PORT);
    ((CachingConnectionFactory) connectionFactory).setUsername(RABBITMQ_USERNAME);
    ((CachingConnectionFactory) connectionFactory).setPassword(RABBITMQ_PASSWORD);
    return connectionFactory;
  }


  /**
   * RabbitMQ에서 사용할 Exchange를 선언, 반환한다.
   * Exchange 종류로 Topic 사용
   *
   * @return 초기화된 TopicExchange 객체
   */
  @Bean
  public TopicExchange getExchange() {
    return new TopicExchange(EXCHANGE_NAME);
  }

  /**
   * RabbitMQ에서 사용할 Queue를 선언하고 반환한다.
   *
   * @return 초기화된 Queue 객체
   */
  @Bean
  public Queue queue() {

    // 큐의 이름, durable 여부 지정
    // durable 이 true => queue는 서버 재시작후에도 유지가 된다.
    return new Queue(QUEUE_NAME, true);
  }

  /**
   * Exchange와 Queue를 연결하는 Binding 객체를 선언하고 반환한다.
   *
   * @param queue 초기화된 Queue
   * @param exchange 초기화된 TopicExchange
   * @return Binding 객체
   */
  @Bean
  public Binding getBinding(Queue queue, TopicExchange exchange){
    // exchange 타입이 Topic -> routing key를 사용해 binding
    return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
  }

  /**
   * RabbitMQ에서 메시지를 수신할 Listener의 Factory를 선언하고 반환한다.
   *
   * @param connectionFactory ConnectionFactory 객체
   * @param converter Jackson Converter 객체. byte[] <-> 메시지 간 변환을 담당
   * @return 초기화된 Listener Factory 객체
   */
  @Bean("SampleListenerContainerFactory")
  public SimpleRabbitListenerContainerFactory getSampleContainerFactory(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(converter);
    return factory;
  }

  @Bean
  public Jackson2JsonMessageConverter getMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate() {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(getConnectionFactory());
    rabbitTemplate.setMessageConverter(getMessageConverter());
    return rabbitTemplate;
  }

}
