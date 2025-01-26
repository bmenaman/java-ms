package com.bmenaman.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AppTest {
  private final Calculator calculator = new Calculator();

  @Test
  void shouldAddNumbers() {
    assertEquals(4, calculator.add(2, 2), "Calculator should add two numbers correctly");
  }
}
