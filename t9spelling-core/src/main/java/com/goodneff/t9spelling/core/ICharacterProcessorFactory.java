/*
 * Copyright (c) 2020.  Goodneff.
 * All rights reserved.
 */
package com.goodneff.t9spelling.core;

import com.goodneff.t9spelling.internal.core.T9CharacterProcessorFactory;

/**
 * The character processor factory.
 *
 * <p>Provides processor instances by the specified characters.
 *
 * @author Alexey Gudnev
 * @see ICharacterProcessor
 */
public interface ICharacterProcessorFactory {

  /** The shared instance of the factory. */
  ICharacterProcessorFactory INSTANCE = new T9CharacterProcessorFactory();

  /**
   * Returns an existing or newly created character processor for the specified {@code character}.
   *
   * @param character the character to obtain {@link ICharacterProcessor} instance that supports it.
   * @return a character processor instance corresponding to the specified character or {@code null}
   *     if there are no processor that supports the character.
   */
  ICharacterProcessor getOrCreateProcessor(char character);
}
