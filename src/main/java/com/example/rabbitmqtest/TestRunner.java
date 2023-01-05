package com.example.rabbitmqtest;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestRunner implements CommandLineRunner {

  @Autowired
  private RabbitProducerService producer;

  @Override
  public void run(String... args) throws Exception {
    SampleMessage testSampleMessage = new SampleMessage("beomsicA", "contentA");
    producer.sendConvertSampleMessage(testSampleMessage);

    OtherMessage testOtherMessage = new OtherMessage("beomsicB", "contentB", "범석");
    producer.sendConvertOtherMessage(testOtherMessage);

    AMessage testAMessage = new AMessage(1L, "contentAA");
    producer.sendConvertMessageA(testAMessage);

    BMessage testBMessage = new BMessage(2L, "contentBB", "범석");
    producer.sendConvertMessageB(testBMessage);
  }

}
