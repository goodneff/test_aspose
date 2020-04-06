/*
 * Copyright (c) 2020.  Goodneff.
 * All rights reserved.
 */
package com.goodneff.t9spelling.internal.core;

import static java.text.MessageFormat.format;

import com.goodneff.t9spelling.core.ICharacterProcessor;
import com.goodneff.t9spelling.core.UnsupportedCharacterException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@link ICharacterProcessor} implementation that process supported character into T9 command
 * sequence.
 *
 * <p>In fact, the single processor instance provides the functionality that is opposite to the
 * button on a telephone keypad. Other words, it tells how many times the digit should be pressed to
 * get the desired character.
 *
 * <p>Each processor has a digit [0-9] and an ordered list of characters. During the processing it
 * takes the character position in the ordered list and repeats the digit P times, where P is the
 * position. If the character to process is not in the supported characters list, it throws an
 * {@link UnsupportedCharacterException}.
 *
 * <p><strong>NOTE:</strong> The processor implementation is not thread-safe.
 *
 * @author Alexey Gudnev
 */
public class T9CharacterProcessor implements ICharacterProcessor {
  private final List<Character> supportedCharacters;
  private final byte digit;

  /**
   * Instantiates a character processor with the {@code digit}.
   *
   * @param digit the digit in range [0-9].
   * @param orderedSupportedCharacters an ordered array of supported characters, cannot be {@code
   *     null} or empty.
   * @throws IllegalArgumentException if the digit is out of the range [0, 9] or if the characters
   *     are not provided or it has duplicated items.
   */
  public T9CharacterProcessor(byte digit, char... orderedSupportedCharacters) {
    if (digit < 0 || digit > 9) {
      String msg = format("The specified digit ''{0}'' is out of the T9 range [0 - 9]", digit);
      throw new IllegalArgumentException(msg);
    }

    if (orderedSupportedCharacters == null || orderedSupportedCharacters.length == 0) {
      throw new IllegalArgumentException("Characters are not specified");
    }
    List<Character> characters =
        String.valueOf(orderedSupportedCharacters)
            .chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.toList());

    if (characters.stream().distinct().count() != characters.size()) {
      String charactersPresentation =
          characters.stream().map(String::valueOf).collect(Collectors.joining(""));
      String msg =
          format("The specified characters ''{0}'' has duplicated items", charactersPresentation);
      throw new IllegalArgumentException(msg);
    }

    this.supportedCharacters = characters;
    this.digit = digit;
  }

  @Override
  public Collection<Character> getSupportedCharacters() {
    return supportedCharacters;
  }

  @Override
  public String process(char character) {
    int index = supportedCharacters.indexOf(character);
    if (index == -1) {
      String msg =
          format(
              "The ''{0}'' character is not supported by the {1}", character, getClass().getName());
      throw new UnsupportedCharacterException(msg);
    }

    return String.join("", Collections.nCopies(index + 1, String.valueOf(digit)));
  }
}
