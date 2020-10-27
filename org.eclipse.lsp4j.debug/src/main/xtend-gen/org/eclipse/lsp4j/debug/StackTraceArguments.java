/**
 * Copyright (c) 2017, 2020 Kichwa Coders Ltd. and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.lsp4j.debug.StackFrameFormat;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Arguments for 'stackTrace' request.
 */
@SuppressWarnings("all")
public class StackTraceArguments {
  /**
   * Retrieve the stacktrace for this thread.
   */
  private int threadId;
  
  /**
   * The index of the first frame to return; if omitted frames start at 0.
   * <p>
   * This is an optional property.
   */
  private Integer startFrame;
  
  /**
   * The maximum number of frames to return. If levels is not specified or 0, all frames are returned.
   * <p>
   * This is an optional property.
   */
  private Integer levels;
  
  /**
   * Specifies details on how to format the stack frames.
   * <p>
   * The attribute is only honored by a debug adapter if the capability 'supportsValueFormattingOptions' is true.
   * <p>
   * This is an optional property.
   */
  private StackFrameFormat format;
  
  /**
   * Retrieve the stacktrace for this thread.
   */
  @Pure
  public int getThreadId() {
    return this.threadId;
  }
  
  /**
   * Retrieve the stacktrace for this thread.
   */
  public void setThreadId(final int threadId) {
    this.threadId = threadId;
  }
  
  /**
   * The index of the first frame to return; if omitted frames start at 0.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getStartFrame() {
    return this.startFrame;
  }
  
  /**
   * The index of the first frame to return; if omitted frames start at 0.
   * <p>
   * This is an optional property.
   */
  public void setStartFrame(final Integer startFrame) {
    this.startFrame = startFrame;
  }
  
  /**
   * The maximum number of frames to return. If levels is not specified or 0, all frames are returned.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getLevels() {
    return this.levels;
  }
  
  /**
   * The maximum number of frames to return. If levels is not specified or 0, all frames are returned.
   * <p>
   * This is an optional property.
   */
  public void setLevels(final Integer levels) {
    this.levels = levels;
  }
  
  /**
   * Specifies details on how to format the stack frames.
   * <p>
   * The attribute is only honored by a debug adapter if the capability 'supportsValueFormattingOptions' is true.
   * <p>
   * This is an optional property.
   */
  @Pure
  public StackFrameFormat getFormat() {
    return this.format;
  }
  
  /**
   * Specifies details on how to format the stack frames.
   * <p>
   * The attribute is only honored by a debug adapter if the capability 'supportsValueFormattingOptions' is true.
   * <p>
   * This is an optional property.
   */
  public void setFormat(final StackFrameFormat format) {
    this.format = format;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("threadId", this.threadId);
    b.add("startFrame", this.startFrame);
    b.add("levels", this.levels);
    b.add("format", this.format);
    return b.toString();
  }
  
  @Override
  @Pure
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    StackTraceArguments other = (StackTraceArguments) obj;
    if (other.threadId != this.threadId)
      return false;
    if (this.startFrame == null) {
      if (other.startFrame != null)
        return false;
    } else if (!this.startFrame.equals(other.startFrame))
      return false;
    if (this.levels == null) {
      if (other.levels != null)
        return false;
    } else if (!this.levels.equals(other.levels))
      return false;
    if (this.format == null) {
      if (other.format != null)
        return false;
    } else if (!this.format.equals(other.format))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.threadId;
    result = prime * result + ((this.startFrame== null) ? 0 : this.startFrame.hashCode());
    result = prime * result + ((this.levels== null) ? 0 : this.levels.hashCode());
    return prime * result + ((this.format== null) ? 0 : this.format.hashCode());
  }
}
