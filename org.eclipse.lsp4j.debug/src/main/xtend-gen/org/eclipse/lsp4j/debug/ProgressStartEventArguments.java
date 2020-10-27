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
 * The event signals that a long running operation is about to start and
 * <p>
 * provides additional information for the client to set up a corresponding progress and cancellation UI.
 * <p>
 * The client is free to delay the showing of the UI in order to reduce flicker.
 * <p>
 * This event should only be sent if the client has passed the value true for the 'supportsProgressReporting'
 * capability of the 'initialize' request.
 */
@SuppressWarnings("all")
public class ProgressStartEventArguments {
  /**
   * An ID that must be used in subsequent 'progressUpdate' and 'progressEnd' events to make them refer to the same
   * progress reporting.
   * <p>
   * IDs must be unique within a debug session.
   */
  @NonNull
  private String progressId;
  
  /**
   * Mandatory (short) title of the progress reporting. Shown in the UI to describe the long running operation.
   */
  @NonNull
  private String title;
  
  /**
   * The request ID that this progress report is related to. If specified a debug adapter is expected to emit
   * <p>
   * progress events for the long running request until the request has been either completed or cancelled.
   * <p>
   * If the request ID is omitted, the progress report is assumed to be related to some general activity of the
   * debug adapter.
   * <p>
   * This is an optional property.
   */
  private Integer requestId;
  
  /**
   * If true, the request that reports progress may be canceled with a 'cancel' request.
   * <p>
   * So this property basically controls whether the client should use UX that supports cancellation.
   * <p>
   * Clients that don't support cancellation are allowed to ignore the setting.
   * <p>
   * This is an optional property.
   */
  private Boolean cancellable;
  
  /**
   * Optional, more detailed progress message.
   * <p>
   * This is an optional property.
   */
  private String message;
  
  /**
   * Optional progress percentage to display (value range: 0 to 100). If omitted no percentage will be shown.
   * <p>
   * This is an optional property.
   */
  private Double percentage;
  
  /**
   * An ID that must be used in subsequent 'progressUpdate' and 'progressEnd' events to make them refer to the same
   * progress reporting.
   * <p>
   * IDs must be unique within a debug session.
   */
  @Pure
  @NonNull
  public String getProgressId() {
    return this.progressId;
  }
  
  /**
   * An ID that must be used in subsequent 'progressUpdate' and 'progressEnd' events to make them refer to the same
   * progress reporting.
   * <p>
   * IDs must be unique within a debug session.
   */
  public void setProgressId(@NonNull final String progressId) {
    this.progressId = Preconditions.checkNotNull(progressId, "progressId");
  }
  
  /**
   * Mandatory (short) title of the progress reporting. Shown in the UI to describe the long running operation.
   */
  @Pure
  @NonNull
  public String getTitle() {
    return this.title;
  }
  
  /**
   * Mandatory (short) title of the progress reporting. Shown in the UI to describe the long running operation.
   */
  public void setTitle(@NonNull final String title) {
    this.title = Preconditions.checkNotNull(title, "title");
  }
  
  /**
   * The request ID that this progress report is related to. If specified a debug adapter is expected to emit
   * <p>
   * progress events for the long running request until the request has been either completed or cancelled.
   * <p>
   * If the request ID is omitted, the progress report is assumed to be related to some general activity of the
   * debug adapter.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getRequestId() {
    return this.requestId;
  }
  
  /**
   * The request ID that this progress report is related to. If specified a debug adapter is expected to emit
   * <p>
   * progress events for the long running request until the request has been either completed or cancelled.
   * <p>
   * If the request ID is omitted, the progress report is assumed to be related to some general activity of the
   * debug adapter.
   * <p>
   * This is an optional property.
   */
  public void setRequestId(final Integer requestId) {
    this.requestId = requestId;
  }
  
  /**
   * If true, the request that reports progress may be canceled with a 'cancel' request.
   * <p>
   * So this property basically controls whether the client should use UX that supports cancellation.
   * <p>
   * Clients that don't support cancellation are allowed to ignore the setting.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getCancellable() {
    return this.cancellable;
  }
  
  /**
   * If true, the request that reports progress may be canceled with a 'cancel' request.
   * <p>
   * So this property basically controls whether the client should use UX that supports cancellation.
   * <p>
   * Clients that don't support cancellation are allowed to ignore the setting.
   * <p>
   * This is an optional property.
   */
  public void setCancellable(final Boolean cancellable) {
    this.cancellable = cancellable;
  }
  
  /**
   * Optional, more detailed progress message.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getMessage() {
    return this.message;
  }
  
  /**
   * Optional, more detailed progress message.
   * <p>
   * This is an optional property.
   */
  public void setMessage(final String message) {
    this.message = message;
  }
  
  /**
   * Optional progress percentage to display (value range: 0 to 100). If omitted no percentage will be shown.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Double getPercentage() {
    return this.percentage;
  }
  
  /**
   * Optional progress percentage to display (value range: 0 to 100). If omitted no percentage will be shown.
   * <p>
   * This is an optional property.
   */
  public void setPercentage(final Double percentage) {
    this.percentage = percentage;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("progressId", this.progressId);
    b.add("title", this.title);
    b.add("requestId", this.requestId);
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
    ProgressStartEventArguments other = (ProgressStartEventArguments) obj;
    if (this.progressId == null) {
      if (other.progressId != null)
        return false;
    } else if (!this.progressId.equals(other.progressId))
      return false;
    if (this.title == null) {
      if (other.title != null)
        return false;
    } else if (!this.title.equals(other.title))
      return false;
    if (this.requestId == null) {
      if (other.requestId != null)
        return false;
    } else if (!this.requestId.equals(other.requestId))
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
    result = prime * result + ((this.progressId== null) ? 0 : this.progressId.hashCode());
    result = prime * result + ((this.title== null) ? 0 : this.title.hashCode());
    result = prime * result + ((this.requestId== null) ? 0 : this.requestId.hashCode());
    result = prime * result + ((this.cancellable== null) ? 0 : this.cancellable.hashCode());
    result = prime * result + ((this.message== null) ? 0 : this.message.hashCode());
    return prime * result + ((this.percentage== null) ? 0 : this.percentage.hashCode());
  }
}
