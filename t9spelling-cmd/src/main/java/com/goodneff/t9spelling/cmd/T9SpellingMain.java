/*
 * Copyright (c) 2020.  Goodneff.
 * All rights reserved.
 */
package com.goodneff.t9spelling.cmd;

import com.goodneff.t9spelling.core.IMessageProcessor;
import com.goodneff.t9spelling.internal.core.T9MessageProcessor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * An application entry point.
 *
 * <p>It provides a regular workflow for the T9 Spelling problem. It takes care about input/output
 * operations.
 *
 * @author Alexey Gudnev
 */
public class T9SpellingMain {

  /**
   * An entry point.
   *
   * @param args the application arguments.
   */
  public static void main(String[] args) {
    List<String> strings = read();
    if (strings.isEmpty()) {
      System.err.println("The input has unsupported format");
      return;
    }

    Collection<String> processed = process(strings);
    print(processed);
  }

  private static Collection<String> process(List<String> strings) {
    IMessageProcessor processor = new T9MessageProcessor();
    List<String> result = new ArrayList<>(strings.size());

    for (int index = 0; index < strings.size(); index++) {
      int caseNo = index + 1;
      IMessageProcessor decoratingMessageProcessor =
          new DecoratingMessageProcessor(processor, caseNo);
      result.add(decoratingMessageProcessor.process(strings.get(index)));
    }

    return result;
  }

  private static List<String> read() {
    try (Scanner scanner = new Scanner(System.in)) {
      if (!scanner.hasNextLine()) {
        throw new IllegalArgumentException("Incorrect input format. Input is empty");
      }
      int lines = 0;
      try {
        lines = Integer.parseInt(scanner.nextLine());
      } catch (NumberFormatException ex) {
        throw new IllegalArgumentException("Incorrect input format", ex);
      }

      List<String> strings = new ArrayList<>(lines);
      while (--lines >= 0) {
        strings.add(scanner.nextLine());
      }
      return Collections.unmodifiableList(strings);
    }
  }

  private static void print(Collection<String> messages) {
    messages.forEach(System.out::println);
  }
}
