/*
 * Copyright (c) 2020.  Goodneff.
 * All rights reserved.
 */
package com.goodneff.t9spelling.internal.core;

import com.goodneff.t9spelling.core.ICharacterProcessor;
import com.goodneff.t9spelling.core.ICharacterProcessorFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The {@link ICharacterProcessorFactory} implementation that provides T9 processors for latin
 * lowercase alphabet, i.e. for characters in range [a-z] and the space character. For another
 * characters it produces {@code null}.
 *
 * <p><strong>NOTE:</strong> The factory is not thread-safe.
 *
 * @author Alexey Gudnev
 * @see T9CharacterProcessor
 */
public final class T9CharacterProcessorFactory implements ICharacterProcessorFactory {
  private final Map<Character, ICharacterProcessor> processorMap = new HashMap<>();

  /** Instantiates a factory instance. */
  public T9CharacterProcessorFactory() {
    List<ICharacterProcessor> processors = new ArrayList<>(10);
    processors.add(new T9CharacterProcessor((byte) 2, 'a', 'b', 'c'));
    processors.add(new T9CharacterProcessor((byte) 3, 'd', 'e', 'f'));
    processors.add(new T9CharacterProcessor((byte) 4, 'g', 'h', 'i'));
    processors.add(new T9CharacterProcessor((byte) 5, 'j', 'k', 'l'));
    processors.add(new T9CharacterProcessor((byte) 6, 'm', 'n', 'o'));
    processors.add(new T9CharacterProcessor((byte) 7, 'p', 'q', 'r', 's'));
    processors.add(new T9CharacterProcessor((byte) 8, 't', 'u', 'v'));
    processors.add(new T9CharacterProcessor((byte) 9, 'w', 'x', 'y', 'z'));
    processors.add(new T9CharacterProcessor((byte) 0, ' '));

    processors.forEach(this::registerProcessor);
  }

  @Override
  public ICharacterProcessor getOrCreateProcessor(char character) {
    return processorMap.get(character);
  }

  private void registerProcessor(ICharacterProcessor processor) {
    processorMap.putAll(
        processor.getSupportedCharacters().stream()
            .collect(Collectors.toMap(Function.identity(), k -> processor)));
  }
}
