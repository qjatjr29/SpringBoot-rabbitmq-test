package com.example.rabbitmqtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class SampleMessage {

  private String name;
  private String content;

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    try {
      return new ObjectMapper().writeValueAsString(this);
    }
    catch(Exception e){
      log.error("error!!");
      return "";
    }
  }
}
