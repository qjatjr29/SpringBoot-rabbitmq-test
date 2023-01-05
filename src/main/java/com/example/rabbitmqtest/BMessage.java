package com.example.rabbitmqtest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BMessage {

  private Long id;
  private String content;
  private String writer;

}
