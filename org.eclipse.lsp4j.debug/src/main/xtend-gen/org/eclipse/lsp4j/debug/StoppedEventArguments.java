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

import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The event indicates that the execution of the debuggee has stopped due to some condition.
 * <p>
 * This can be caused by a break point previously set, a stepping request has completed, by executing a debugger
 * statement etc.
 */
@SuppressWarnings("all")
public class StoppedEventArguments {
  /**
   * The reason for the event.
   * <p>
   * For backward compatibility this string is shown in the UI if the 'description' attribute is missing (but it
   * must not be translated).
   * <p>
   * Possible values include - but not limited to those defined in {@link StoppedEventArgumentsReason}
   */
  @NonNull
  private String reason;
  
  /**
   * The full reason for the event, e.g. 'Paused on exception'. This string is shown in the UI as is and must be
   * translated.
   * <p>
   * This is an optional property.
   */
  private String description;
  
  /**
   * The thread which was stopped.
   * <p>
   * This is an optional property.
   */
  private Integer threadId;
  
  /**
   * A value of true hints to the frontend that this event should not change the focus.
   * <p>
   * This is an optional property.
   */
  private Boolean preserveFocusHint;
  
  /**
   * Additional information. E.g. if reason is 'exception', text contains the exception name. This string is shown
   * in the UI.
   * <p>
   * This is an optional property.
   */
  private String text;
  
  /**
   * If 'allThreadsStopped' is true, a debug adapter can announce that all threads have stopped.
   * <ul>
   * <li>The client should use this information to enable that all threads can be expanded to access their
   * stacktraces.</li>
   * <li>If the attribute is missing or false, only the thread with the given threadId can be expanded.</li>
   * </ul>
   * <p>
   * This is an optional property.
   */
  private Boolean allThreadsStopped;
  
  /**
   * The reason for the event.
   * <p>
   * For backward compatibility this string is shown in the UI if the 'description' attribute is missing (but it
   * must not be translated).
   * <p>
   * Possible values include - but not limited to those defined in {@link StoppedEventArgumentsReason}
   */
  @Pure
  @NonNull
  public String getReason() {
    return this.reason;
  }
  
  /**
   * The reason for the event.
   * <p>
   * For backward compatibility this string is shown in the UI if the 'description' attribute is missing (but it
   * must not be translated).
   * <p>
   * Possible values include - but not limited to those defined in {@link StoppedEventArgumentsReason}
   */
  public void setReason(@NonNull final String reason) {
    this.reason = Preconditions.checkNotNull(reason, "reason");
  }
  
  /**
   * The full reason for the event, e.g. 'Paused on exception'. This string is shown in the UI as is and must be
   * translated.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getDescription() {
    return this.description;
  }
  
  /**
   * The full reason for the event, e.g. 'Paused on exception'. This string is shown in the UI as is and must be
   * translated.
   * <p>
   * This is an optional property.
   */
  public void setDescription(final String description) {
    this.description = description;
  }
  
  /**
   * The thread which was stopped.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getThreadId() {
    return this.threadId;
  }
  
  /**
   * The thread which was stopped.
   * <p>
   * This is an optional property.
   */
  public void setThreadId(final Integer threadId) {
    this.threadId = threadId;
  }
  
  /**
   * A value of true hints to the frontend that this event should not change the focus.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getPreserveFocusHint() {
    return this.preserveFocusHint;
  }
  
  /**
   * A value of true hints to the frontend that this event should not change the focus.
   * <p>
   * This is an optional property.
   */
  public void setPreserveFocusHint(final Boolean preserveFocusHint) {
    this.preserveFocusHint = preserveFocusHint;
  }
  
  /**
   * Additional information. E.g. if reason is 'exception', text contains the exception name. This string is shown
   * in the UI.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getText() {
    return this.text;
  }
  
  /**
   * Additional information. E.g. if reason is 'exception', text contains the exception name. This string is shown
   * in the UI.
   * <p>
   * This is an optional property.
   */
  public void setText(final String text) {
    this.text = text;
  }
  
  /**
   * If 'allThreadsStopped' is true, a debug adapter can announce that all threads have stopped.
   * <ul>
   * <li>The client should use this information to enable that all threads can be expanded to access their
   * stacktraces.</li>
   * <li>If the attribute is missing or false, only the thread with the given threadId can be expanded.</li>
   * </ul>
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getAllThreadsStopped() {
    return this.allThreadsStopped;
  }
  
  /**
   * If 'allThreadsStopped' is true, a debug adapter can announce that all threads have stopped.
   * <ul>
   * <li>The client should use this information to enable that all threads can be expanded to access their
   * stacktraces.</li>
   * <li>If the attribute is missing or false, only the thread with the given threadId can be expanded.</li>
   * </ul>
   * <p>
   * This is an optional property.
   */
  public void setAllThreadsStopped(final Boolean allThreadsStopped) {
    this.allThreadsStopped = allThreadsStopped;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("reason", this.reason);
    b.add("description", this.description);
    b.add("threadId", this.threadId);
    b.add("preserveFocusHint", this.preserveFocusHint);
    b.add("text", this.text);
    b.add("allThreadsStopped", this.allThreadsStopped);
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
    StoppedEventArguments other = (StoppedEventArguments) obj;
    if (this.reason == null) {
      if (other.reason != null)
        return false;
    } else if (!this.reason.equals(other.reason))
      return false;
    if (this.description == null) {
      if (other.description != null)
        return false;
    } else if (!this.description.equals(other.description))
      return false;
    if (this.threadId == null) {
      if (other.threadId != null)
        return false;
    } else if (!this.threadId.equals(other.threadId))
      return false;
    if (this.preserveFocusHint == null) {
      if (other.preserveFocusHint != null)
        return false;
    } else if (!this.preserveFocusHint.equals(other.preserveFocusHint))
      return false;
    if (this.text == null) {
      if (other.text != null)
        return false;
    } else if (!this.text.equals(other.text))
      return false;
    if (this.allThreadsStopped == null) {
      if (other.allThreadsStopped != null)
        return false;
    } else if (!this.allThreadsStopped.equals(other.allThreadsStopped))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.reason== null) ? 0 : this.reason.hashCode());
    result = prime * result + ((this.description== null) ? 0 : this.description.hashCode());
    result = prime * result + ((this.threadId== null) ? 0 : this.threadId.hashCode());
    result = prime * result + ((this.preserveFocusHint== null) ? 0 : this.preserveFocusHint.hashCode());
    result = prime * result + ((this.text== null) ? 0 : this.text.hashCode());
    return prime * result + ((this.allThreadsStopped== null) ? 0 : this.allThreadsStopped.hashCode());
  }
}
