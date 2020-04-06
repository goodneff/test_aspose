/*
 * Copyright (c) 2020.  Goodneff.
 * All rights reserved.
 */
package com.goodneff.t9spelling.internal.core;

import com.goodneff.t9spelling.core.ICharacterProcessor;
import com.goodneff.t9spelling.core.ICharacterProcessorFactory;
import com.goodneff.t9spelling.core.IMessageProcessor;
import com.goodneff.t9spelling.core.UnsupportedCharacterException;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * The {@link IMessageProcessor} implementation.
 *
 * <p>It processes the incoming message with the following logic:
 *
 * <ul>
 *   <li>Iterate over the string characters
 *   <li>Obtain the {@link ICharacterProcessor} instance for each next character from the factory
 *   <li>Make a pause, i.e. add a space character to the output if the processor for the current
 *       character equals to the processor of the previous one
 *   <li>Add a result of character processing via {@link ICharacterProcessor} instance to the output
 * </ul>
 *
 * <strong>NOTE:</strong> if the processor of the next incoming character is {@code null} it throws
 * an {@link UnsupportedCharacterException} <strong>NOTE:</strong> The processor implementation is
 * not thread-safe.
 *
 * @author Alexey Gudnev
 * @see ICharacterProcessor
 * @see ICharacterProcessorFactory
 */
public final class T9MessageProcessor implements IMessageProcessor {
  private static final char PAUSE = ' ';

  // NOTE: It is better to use DI (guava/spring/pico), but let's limit our requirements with the
  // factory
  private ICharacterProcessorFactory characterProcessorFactory =
      ICharacterProcessorFactory.INSTANCE;

  @Override
  public String process(String message) {
    if (message == null) {
      throw new IllegalArgumentException("The 'message' argument cannot be null");
    }

    StringBuilder sb = new StringBuilder();

    ICharacterProcessor lastUsedProcessor = null;
    for (int i = 0; i < message.length(); i++) {
      char character = message.charAt(i);
      ICharacterProcessor processor = characterProcessorFactory.getOrCreateProcessor(character);
      if (processor == null) {
        String msg = MessageFormat.format("The character ''{0}'' is not supported", character);
        throw new UnsupportedCharacterException(msg);
      }
      String t9str = processor.process(character);
      if (Objects.equals(processor, lastUsedProcessor)) {
        sb.append(PAUSE);
      }
      sb.append(t9str);
      lastUsedProcessor = processor;
    }
    return sb.toString();
  }

  // For testing purposes
  /* package-private */ void setCharacterProcessorFactory(
      ICharacterProcessorFactory characterProcessorFactory) {
    if (characterProcessorFactory == null) {
      throw new IllegalArgumentException("The character processor factory cannot be 'null'");
    }
    this.characterProcessorFactory = characterProcessorFactory;
  }
}
