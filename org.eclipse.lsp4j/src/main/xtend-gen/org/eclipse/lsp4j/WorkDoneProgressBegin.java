/**
 * Copyright (c) 2016-2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.WorkDoneProgressKind;
import org.eclipse.lsp4j.WorkDoneProgressNotification;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The $/progress notification payload to start progress reporting.
 * 
 * Since 3.15.0
 */
@SuppressWarnings("all")
public class WorkDoneProgressBegin implements WorkDoneProgressNotification {
  /**
   * Always return begin
   */
  @Override
  public WorkDoneProgressKind getKind() {
    return WorkDoneProgressKind.begin;
  }
  
  /**
   * Mandatory title of the progress operation. Used to briefly inform about
   * the kind of operation being performed.
   * 
   * Examples: "Indexing" or "Linking dependencies".
   */
  @NonNull
  private String title;
  
  /**
   * Controls if a cancel button should show to allow the user to cancel the
   * long running operation. Clients that don't support cancellation are allowed
   * to ignore the setting.
   */
  private Boolean cancellable;
  
  /**
   * Optional, more detailed associated progress message. Contains
   * complementary information to the `title`.
   * 
   * Examples: "3/25 files", "project/src/module2", "node_modules/some_dep".
   * If unset, the previous progress message (if any) is still valid.
   */
  private String message;
  
  /**
   * Optional progress percentage to display (value 100 is considered 100%).
   * If not provided infinite progress is assumed and clients are allowed
   * to ignore the `percentage` value in subsequent in report notifications.
   * 
   * The value should be steadily rising. Clients are free to ignore values
   * that are not following this rule.
   */
  private Integer percentage;
  
  /**
   * Mandatory title of the progress operation. Used to briefly inform about
   * the kind of operation being performed.
   * 
   * Examples: "Indexing" or "Linking dependencies".
   */
  @Pure
  @NonNull
  public String getTitle() {
    return this.title;
  }
  
  /**
   * Mandatory title of the progress operation. Used to briefly inform about
   * the kind of operation being performed.
   * 
   * Examples: "Indexing" or "Linking dependencies".
   */
  public void setTitle(@NonNull final String title) {
    this.title = Preconditions.checkNotNull(title, "title");
  }
  
  /**
   * Controls if a cancel button should show to allow the user to cancel the
   * long running operation. Clients that don't support cancellation are allowed
   * to ignore the setting.
   */
  @Pure
  public Boolean getCancellable() {
    return this.cancellable;
  }
  
  /**
   * Controls if a cancel button should show to allow the user to cancel the
   * long running operation. Clients that don't support cancellation are allowed
   * to ignore the setting.
   */
  public void setCancellable(final Boolean cancellable) {
    this.cancellable = cancellable;
  }
  
  /**
   * Optional, more detailed associated progress message. Contains
   * complementary information to the `title`.
   * 
   * Examples: "3/25 files", "project/src/module2", "node_modules/some_dep".
   * If unset, the previous progress message (if any) is still valid.
   */
  @Pure
  public String getMessage() {
    return this.message;
  }
  
  /**
   * Optional, more detailed associated progress message. Contains
   * complementary information to the `title`.
   * 
   * Examples: "3/25 files", "project/src/module2", "node_modules/some_dep".
   * If unset, the previous progress message (if any) is still valid.
   */
  public void setMessage(final String message) {
    this.message = message;
  }
  
  /**
   * Optional progress percentage to display (value 100 is considered 100%).
   * If not provided infinite progress is assumed and clients are allowed
   * to ignore the `percentage` value in subsequent in report notifications.
   * 
   * The value should be steadily rising. Clients are free to ignore values
   * that are not following this rule.
   */
  @Pure
  public Integer getPercentage() {
    return this.percentage;
  }
  
  /**
   * Optional progress percentage to display (value 100 is considered 100%).
   * If not provided infinite progress is assumed and clients are allowed
   * to ignore the `percentage` value in subsequent in report notifications.
   * 
   * The value should be steadily rising. Clients are free to ignore values
   * that are not following this rule.
   */
  public void setPercentage(final Integer percentage) {
    this.percentage = percentage;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("title", this.title);
    b.add("cancellable", this.cancellable);
    b.add("message", this.message);
    b.add("percentage", this.percentage);
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
    WorkDoneProgressBegin other = (WorkDoneProgressBegin) obj;
    if (this.title == null) {
      if (other.title != null)
        return false;
    } else if (!this.title.equals(other.title))
      return false;
    if (this.cancellable == null) {
      if (other.cancellable != null)
        return false;
    } else if (!this.cancellable.equals(other.cancellable))
      return false;
    if (this.message == null) {
      if (other.message != null)
        return false;
    } else if (!this.message.equals(other.message))
      return false;
    if (this.percentage == null) {
      if (other.percentage != null)
        return false;
    } else if (!this.percentage.equals(other.percentage))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.title== null) ? 0 : this.title.hashCode());
    result = prime * result + ((this.cancellable== null) ? 0 : this.cancellable.hashCode());
    result = prime * result + ((this.message== null) ? 0 : this.message.hashCode());
    return prime * result + ((this.percentage== null) ? 0 : this.percentage.hashCode());
  }
}
