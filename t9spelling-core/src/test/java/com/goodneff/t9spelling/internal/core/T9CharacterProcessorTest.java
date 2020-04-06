/*
 * Copyright (c) 2020.  Goodneff.
 * All rights reserved.
 */
package com.goodneff.t9spelling.internal.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.goodneff.t9spelling.core.UnsupportedCharacterException;
import java.util.Arrays;
import org.junit.Test;

/**
 * A set of test cases for {@link T9CharacterProcessor}
 *
 * @author Alexey Gudnev
 */
public class T9CharacterProcessorTest {

  @Test
  public void testProcessRegularWorkflow() {
    {
      T9CharacterProcessor processor = new T9CharacterProcessor((byte) 1, 'a', 'b', 'c');

      String result = processor.process('a');
      assertEquals("1", result);

      result = processor.process('b');
      assertEquals("11", result);

      result = processor.process('c');
      assertEquals("111", result);
    }

    // check it respects the origin order
    {
      T9CharacterProcessor processor = new T9CharacterProcessor((byte) 8, 'x', 'z', 'w', 'a');

      String result = processor.process('a');
      assertEquals("8888", result);

      result = processor.process('w');
      assertEquals("888", result);

      result = processor.process('x');
      assertEquals("8", result);

      result = processor.process('z');
      assertEquals("88", result);
    }
  }

  @Test(expected = UnsupportedCharacterException.class)
  public void testProcessUnsupported() {
    T9CharacterProcessor processor = new T9CharacterProcessor((byte) 1, 'a');
    processor.process('b');
  }

  @Test
  public void testGetSupportedCharacters() {
    {
      T9CharacterProcessor processor = new T9CharacterProcessor((byte) 1, 'a');
      assertEquals(Arrays.asList('a'), processor.getSupportedCharacters());
    }

    // check it respects the origin order
    {
      T9CharacterProcessor processor = new T9CharacterProcessor((byte) 1, 'z', 'a', 'w');
      assertEquals(Arrays.asList('z', 'a', 'w'), processor.getSupportedCharacters());
    }
  }

  @Test
  public void testProcessorWrongArgs() {
    try {
      new T9CharacterProcessor((byte) -1, 'a');
      fail();
    } catch (IllegalArgumentException ex) {
      // ok
    }

    try {
      new T9CharacterProcessor((byte) 10, 'a');
      fail();
    } catch (IllegalArgumentException ex) {
      // ok
    }

    try {
      new T9CharacterProcessor((byte) 1);
      fail();
    } catch (IllegalArgumentException ex) {
      // ok
    }

    try {
      new T9CharacterProcessor((byte) 1, 'a', 'a');
      fail();
    } catch (IllegalArgumentException ex) {
      // ok
    }

    try {
      new T9CharacterProcessor((byte) 1, 'a', 'b', 'a');
      fail();
    } catch (IllegalArgumentException ex) {
      // ok
    }
  }

  // and so on ...
}
