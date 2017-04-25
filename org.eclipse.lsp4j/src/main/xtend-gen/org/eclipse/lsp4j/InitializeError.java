package org.eclipse.lsp4j;

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class InitializeError {
  /**
   * Indicates whether the client execute the following retry logic:
   * (1) show the message provided by the ResponseError to the user
   * (2) user selects retry or cancel
   * (3) if user selected retry the initialize method is sent again.
   */
  private boolean retry;
  
  public InitializeError() {
  }
  
  public InitializeError(final boolean retry) {
    this.retry = retry;
  }
  
  /**
   * Indicates whether the client execute the following retry logic:
   * (1) show the message provided by the ResponseError to the user
   * (2) user selects retry or cancel
   * (3) if user selected retry the initialize method is sent again.
   */
  @Pure
  public boolean isRetry() {
    return this.retry;
  }
  
  /**
   * Indicates whether the client execute the following retry logic:
   * (1) show the message provided by the ResponseError to the user
   * (2) user selects retry or cancel
   * (3) if user selected retry the initialize method is sent again.
   */
  public void setRetry(final boolean retry) {
    this.retry = retry;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("retry", this.retry);
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
    InitializeError other = (InitializeError) obj;
    if (other.retry != this.retry)
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (this.retry ? 1231 : 1237);
    return result;
  }
}
