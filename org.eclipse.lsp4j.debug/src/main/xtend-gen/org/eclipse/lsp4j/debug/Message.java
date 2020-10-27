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

import java.util.Map;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A structured message object. Used to return errors from requests.
 */
@SuppressWarnings("all")
public class Message {
  /**
   * Unique identifier for the message.
   */
  private int id;
  
  /**
   * A format string for the message. Embedded variables have the form '{name}'.
   * <p>
   * If variable name starts with an underscore character, the variable does not contain user data (PII) and can be
   * safely used for telemetry purposes.
   */
  @NonNull
  private String format;
  
  /**
   * An object used as a dictionary for looking up the variables in the format string.
   * <p>
   * This is an optional property.
   */
  private Map<String, String> variables;
  
  /**
   * If true send to telemetry.
   * <p>
   * This is an optional property.
   */
  private Boolean sendTelemetry;
  
  /**
   * If true show user.
   * <p>
   * This is an optional property.
   */
  private Boolean showUser;
  
  /**
   * An optional url where additional information about this message can be found.
   * <p>
   * This is an optional property.
   */
  private String url;
  
  /**
   * An optional label that is presented to the user as the UI for opening the url.
   * <p>
   * This is an optional property.
   */
  private String urlLabel;
  
  /**
   * Unique identifier for the message.
   */
  @Pure
  public int getId() {
    return this.id;
  }
  
  /**
   * Unique identifier for the message.
   */
  public void setId(final int id) {
    this.id = id;
  }
  
  /**
   * A format string for the message. Embedded variables have the form '{name}'.
   * <p>
   * If variable name starts with an underscore character, the variable does not contain user data (PII) and can be
   * safely used for telemetry purposes.
   */
  @Pure
  @NonNull
  public String getFormat() {
    return this.format;
  }
  
  /**
   * A format string for the message. Embedded variables have the form '{name}'.
   * <p>
   * If variable name starts with an underscore character, the variable does not contain user data (PII) and can be
   * safely used for telemetry purposes.
   */
  public void setFormat(@NonNull final String format) {
    this.format = Preconditions.checkNotNull(format, "format");
  }
  
  /**
   * An object used as a dictionary for looking up the variables in the format string.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Map<String, String> getVariables() {
    return this.variables;
  }
  
  /**
   * An object used as a dictionary for looking up the variables in the format string.
   * <p>
   * This is an optional property.
   */
  public void setVariables(final Map<String, String> variables) {
    this.variables = variables;
  }
  
  /**
   * If true send to telemetry.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSendTelemetry() {
    return this.sendTelemetry;
  }
  
  /**
   * If true send to telemetry.
   * <p>
   * This is an optional property.
   */
  public void setSendTelemetry(final Boolean sendTelemetry) {
    this.sendTelemetry = sendTelemetry;
  }
  
  /**
   * If true show user.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getShowUser() {
    return this.showUser;
  }
  
  /**
   * If true show user.
   * <p>
   * This is an optional property.
   */
  public void setShowUser(final Boolean showUser) {
    this.showUser = showUser;
  }
  
  /**
   * An optional url where additional information about this message can be found.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getUrl() {
    return this.url;
  }
  
  /**
   * An optional url where additional information about this message can be found.
   * <p>
   * This is an optional property.
   */
  public void setUrl(final String url) {
    this.url = url;
  }
  
  /**
   * An optional label that is presented to the user as the UI for opening the url.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getUrlLabel() {
    return this.urlLabel;
  }
  
  /**
   * An optional label that is presented to the user as the UI for opening the url.
   * <p>
   * This is an optional property.
   */
  public void setUrlLabel(final String urlLabel) {
    this.urlLabel = urlLabel;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("id", this.id);
    b.add("format", this.format);
    b.add("variables", this.variables);
    b.add("sendTelemetry", this.sendTelemetry);
    b.add("showUser", this.showUser);
    b.add("url", this.url);
    b.add("urlLabel", this.urlLabel);
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
    Message other = (Message) obj;
    if (other.id != this.id)
      return false;
    if (this.format == null) {
      if (other.format != null)
        return false;
    } else if (!this.format.equals(other.format))
      return false;
    if (this.variables == null) {
      if (other.variables != null)
        return false;
    } else if (!this.variables.equals(other.variables))
      return false;
    if (this.sendTelemetry == null) {
      if (other.sendTelemetry != null)
        return false;
    } else if (!this.sendTelemetry.equals(other.sendTelemetry))
      return false;
    if (this.showUser == null) {
      if (other.showUser != null)
        return false;
    } else if (!this.showUser.equals(other.showUser))
      return false;
    if (this.url == null) {
      if (other.url != null)
        return false;
    } else if (!this.url.equals(other.url))
      return false;
    if (this.urlLabel == null) {
      if (other.urlLabel != null)
        return false;
    } else if (!this.urlLabel.equals(other.urlLabel))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.id;
    result = prime * result + ((this.format== null) ? 0 : this.format.hashCode());
    result = prime * result + ((this.variables== null) ? 0 : this.variables.hashCode());
    result = prime * result + ((this.sendTelemetry== null) ? 0 : this.sendTelemetry.hashCode());
    result = prime * result + ((this.showUser== null) ? 0 : this.showUser.hashCode());
    result = prime * result + ((this.url== null) ? 0 : this.url.hashCode());
    return prime * result + ((this.urlLabel== null) ? 0 : this.urlLabel.hashCode());
  }
}
