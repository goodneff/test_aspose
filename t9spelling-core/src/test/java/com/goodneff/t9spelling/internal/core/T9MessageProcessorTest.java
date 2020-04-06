/*
 * Copyright (c) 2020.  Goodneff.
 * All rights reserved.
 */
package com.goodneff.t9spelling.internal.core;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.goodneff.t9spelling.core.ICharacterProcessor;
import com.goodneff.t9spelling.core.ICharacterProcessorFactory;
import com.goodneff.t9spelling.core.UnsupportedCharacterException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * A set of test cases for {@link T9MessageProcessor}
 *
 * @author Alexey Gudnev
 */
public class T9MessageProcessorTest {
  private T9MessageProcessor messageProcessor;

  @Before
  public void setUp() {
    messageProcessor = new T9MessageProcessor();
  }

  @Test
  public void testProcessRegularWorkflow() {
    ICharacterProcessor processorA = mock(ICharacterProcessor.class);
    when(processorA.process('a')).thenReturn("A");
    ICharacterProcessor processorB = mock(ICharacterProcessor.class);
    when(processorB.process('b')).thenReturn("B");

    ICharacterProcessorFactory factory = mock(ICharacterProcessorFactory.class);
    when(factory.getOrCreateProcessor('a')).thenReturn(processorA);
    when(factory.getOrCreateProcessor('b')).thenReturn(processorB);

    messageProcessor.setCharacterProcessorFactory(factory);

    String result = messageProcessor.process("a");
    assertEquals("A", result);

    result = messageProcessor.process("b");
    assertEquals("B", result);

    result = messageProcessor.process("ab");
    assertEquals("AB", result);

    result = messageProcessor.process("ba");
    assertEquals("BA", result);

    result = messageProcessor.process("abababa");
    assertEquals("ABABABA", result);
  }

  @Test
  public void testProcessPause() {
    ICharacterProcessor processorA = mock(ICharacterProcessor.class);
    when(processorA.process('a')).thenReturn("A");

    ICharacterProcessorFactory factory = mock(ICharacterProcessorFactory.class);
    when(factory.getOrCreateProcessor('a')).thenReturn(processorA);

    messageProcessor.setCharacterProcessorFactory(factory);

    String result = messageProcessor.process("a");
    assertEquals("A", result);

    result = messageProcessor.process("aaaa");
    assertEquals("A A A A", result);
  }

  @Test(expected = UnsupportedCharacterException.class)
  public void testProcessUnsupported() {
    ICharacterProcessor processorA = mock(ICharacterProcessor.class);
    when(processorA.process('a')).thenReturn("A");

    ICharacterProcessorFactory factory = mock(ICharacterProcessorFactory.class);
    when(factory.getOrCreateProcessor('a')).thenReturn(processorA);
    messageProcessor.setCharacterProcessorFactory(factory);

    String result = messageProcessor.process("a a");
  }

  @Test
  public void testProcessEmpty() {
    ICharacterProcessorFactory factory = mock(ICharacterProcessorFactory.class);
    when(factory.getOrCreateProcessor('a')).thenReturn(null);
    messageProcessor.setCharacterProcessorFactory(factory);

    String result = messageProcessor.process("");
    assertEquals("", result);

    verifyNoInteractions(factory);
  }

  @Test
  public void testComplexScenario1() {
    ICharacterProcessor processor = mock(ICharacterProcessor.class);
    when(processor.process(anyChar()))
        .thenAnswer(
            new Answer<String>() {
              @Override
              public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                char c = invocationOnMock.getArgument(0);
                return String.valueOf(Character.toUpperCase(c));
              }
            });

    ICharacterProcessorFactory factory = mock(ICharacterProcessorFactory.class);
    when(factory.getOrCreateProcessor(anyChar())).thenReturn(processor);
    messageProcessor.setCharacterProcessorFactory(factory);

    String result = messageProcessor.process("as   o23mv jf");

    // remember about pauses
    assertEquals("A S       O 2 3 M V   J F", result);
  }

  @Test
  public void testComplexScenario2() {
    // in fact it is an integration test, but for simplicity lets keep it here
    messageProcessor.setCharacterProcessorFactory(ICharacterProcessorFactory.INSTANCE);
    String result = messageProcessor.process("hello world");
    assertEquals("4433555 555666096667775553", result);
  }

  // and so on ...
}
