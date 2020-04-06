/*
 * Copyright (c) 2020.  Goodneff.
 * All rights reserved.
 */
package com.goodneff.t9spelling.cmd;

import com.goodneff.t9spelling.core.IMessageProcessor;

/**
 * A decorating message processor.
 *
 * <p>It adds the leading "Case #N:" to the {@code origin} message processor, where N is an integer
 * representing the case sequence number.
 *
 * @author Alexey Gudnev
 */
public final class DecoratingMessageProcessor implements IMessageProcessor {
  private final IMessageProcessor origin;
  private final int caseNo;

  /**
   * Instantiates a new message processor.
   *
   * @param origin the origin processor, cannot be {@code null}.
   * @param caseNo the case number
   */
  public DecoratingMessageProcessor(IMessageProcessor origin, int caseNo) {
    if (origin == null) {
      throw new IllegalArgumentException("The origin message processor cannot be 'null'");
    }
    this.origin = origin;
    this.caseNo = caseNo;
  }

  @Override
  public String process(String message) {
    StringBuilder sb = new StringBuilder("Case #");
    sb.append(caseNo).append(": ").append(origin.process(message));
    return sb.toString();
  }
}
