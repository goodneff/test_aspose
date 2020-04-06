/*
 * Copyright (c) 2020.  Goodneff.
 * All rights reserved.
 */
package com.goodneff.t9spelling.core;

/**
 * The message processor interface.
 *
 * <p>Processes the incoming string message into a string that represents a result of the processor
 * logic applying./p>
 *
 * @author Alexey Gudnev
 */
public interface IMessageProcessor {

  /**
   * Processes the specified {@code message} and returns a string that represents a result of
   * processing.
   *
   * @param message the incoming {@code message}, cannot be {@code null}.
   * @return a result of processing, never {@code null}.
   * @throws IllegalArgumentException if the message is {@code null}.
   * @throws UnsupportedCharacterException if the message has unsupported characters.
   */
  String process(String message);
}
