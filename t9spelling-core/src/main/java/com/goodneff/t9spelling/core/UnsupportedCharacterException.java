/*
 * Copyright (c) 2020.  Goodneff.
 * All rights reserved.
 */
package com.goodneff.t9spelling.core;

/**
 * Thrown to indicate that a method encountered an unsupported character.
 *
 * @author Alexey Gudnev
 */
public final class UnsupportedCharacterException extends RuntimeException {

  /**
   * Instantiates a new exception.
   *
   * @param message the detailed message. The detailed message is saved for later retrieval by the
   *     {@link #getMessage()} method.
   */
  public UnsupportedCharacterException(String message) {
    super(message);
  }
}
