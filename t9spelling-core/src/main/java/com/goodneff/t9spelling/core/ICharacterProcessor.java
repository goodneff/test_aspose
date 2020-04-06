/*
 * Copyright (c) 2020.  Goodneff.
 * All rights reserved.
 */
package com.goodneff.t9spelling.core;

import java.util.Collection;

/**
 * A character processor interface.
 *
 * <p>Processes the given character into a string in accordance with its logic.
 *
 * <p>The processor instance can be used multiple times over different characters supported by this
 * one.
 *
 * @author Alexey Gudnev
 */
public interface ICharacterProcessor {

  /**
   * Returns characters that is supported by the processor instance.
   *
   * @return a collection of supported characters, never {@code null}.
   */
  Collection<Character> getSupportedCharacters();

  /**
   * Processes the specified {@code character}.
   *
   * @param character the character to process.
   * @return a string representing a result of processing the character, never {@code null}.
   * @throws UnsupportedCharacterException if the specified character is not supported by this
   *     processor.
   * @see #getSupportedCharacters()
   */
  String process(char character);
}
