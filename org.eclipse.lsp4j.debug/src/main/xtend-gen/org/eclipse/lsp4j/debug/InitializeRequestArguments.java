/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Arguments for 'initialize' request.
 */
@SuppressWarnings("all")
public class InitializeRequestArguments {
  /**
   * The ID of the (frontend) client using this adapter.
   * <p>
   * This is an optional property.
   */
  private String clientID;
  
  /**
   * The ID of the debug adapter.
   */
  @NonNull
  private String adapterID;
  
  /**
   * The ISO-639 locale of the (frontend) client using this adapter, e.g. en-US or de-CH.
   * <p>
   * This is an optional property.
   */
  private String locale;
  
  /**
   * If true all line numbers are 1-based (default).
   * <p>
   * This is an optional property.
   */
  private Boolean linesStartAt1;
  
  /**
   * If true all column numbers are 1-based (default).
   * <p>
   * This is an optional property.
   */
  private Boolean columnsStartAt1;
  
  /**
   * Determines in what format paths are specified. The default is 'path', which is the native format.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link InitializeRequestArgumentsPathFormat}
   */
  private String pathFormat;
  
  /**
   * Client supports the optional type attribute for variables.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsVariableType;
  
  /**
   * Client supports the paging of variables.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsVariablePaging;
  
  /**
   * Client supports the runInTerminal request.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsRunInTerminalRequest;
  
  /**
   * The ID of the (frontend) client using this adapter.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getClientID() {
    return this.clientID;
  }
  
  /**
   * The ID of the (frontend) client using this adapter.
   * <p>
   * This is an optional property.
   */
  public void setClientID(final String clientID) {
    this.clientID = clientID;
  }
  
  /**
   * The ID of the debug adapter.
   */
  @Pure
  @NonNull
  public String getAdapterID() {
    return this.adapterID;
  }
  
  /**
   * The ID of the debug adapter.
   */
  public void setAdapterID(@NonNull final String adapterID) {
    this.adapterID = adapterID;
  }
  
  /**
   * The ISO-639 locale of the (frontend) client using this adapter, e.g. en-US or de-CH.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getLocale() {
    return this.locale;
  }
  
  /**
   * The ISO-639 locale of the (frontend) client using this adapter, e.g. en-US or de-CH.
   * <p>
   * This is an optional property.
   */
  public void setLocale(final String locale) {
    this.locale = locale;
  }
  
  /**
   * If true all line numbers are 1-based (default).
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getLinesStartAt1() {
    return this.linesStartAt1;
  }
  
  /**
   * If true all line numbers are 1-based (default).
   * <p>
   * This is an optional property.
   */
  public void setLinesStartAt1(final Boolean linesStartAt1) {
    this.linesStartAt1 = linesStartAt1;
  }
  
  /**
   * If true all column numbers are 1-based (default).
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getColumnsStartAt1() {
    return this.columnsStartAt1;
  }
  
  /**
   * If true all column numbers are 1-based (default).
   * <p>
   * This is an optional property.
   */
  public void setColumnsStartAt1(final Boolean columnsStartAt1) {
    this.columnsStartAt1 = columnsStartAt1;
  }
  
  /**
   * Determines in what format paths are specified. The default is 'path', which is the native format.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link InitializeRequestArgumentsPathFormat}
   */
  @Pure
  public String getPathFormat() {
    return this.pathFormat;
  }
  
  /**
   * Determines in what format paths are specified. The default is 'path', which is the native format.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link InitializeRequestArgumentsPathFormat}
   */
  public void setPathFormat(final String pathFormat) {
    this.pathFormat = pathFormat;
  }
  
  /**
   * Client supports the optional type attribute for variables.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsVariableType() {
    return this.supportsVariableType;
  }
  
  /**
   * Client supports the optional type attribute for variables.
   * <p>
   * This is an optional property.
   */
  public void setSupportsVariableType(final Boolean supportsVariableType) {
    this.supportsVariableType = supportsVariableType;
  }
  
  /**
   * Client supports the paging of variables.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsVariablePaging() {
    return this.supportsVariablePaging;
  }
  
  /**
   * Client supports the paging of variables.
   * <p>
   * This is an optional property.
   */
  public void setSupportsVariablePaging(final Boolean supportsVariablePaging) {
    this.supportsVariablePaging = supportsVariablePaging;
  }
  
  /**
   * Client supports the runInTerminal request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsRunInTerminalRequest() {
    return this.supportsRunInTerminalRequest;
  }
  
  /**
   * Client supports the runInTerminal request.
   * <p>
   * This is an optional property.
   */
  public void setSupportsRunInTerminalRequest(final Boolean supportsRunInTerminalRequest) {
    this.supportsRunInTerminalRequest = supportsRunInTerminalRequest;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("clientID", this.clientID);
    b.add("adapterID", this.adapterID);
    b.add("locale", this.locale);
    b.add("linesStartAt1", this.linesStartAt1);
    b.add("columnsStartAt1", this.columnsStartAt1);
    b.add("pathFormat", this.pathFormat);
    b.add("supportsVariableType", this.supportsVariableType);
    b.add("supportsVariablePaging", this.supportsVariablePaging);
    b.add("supportsRunInTerminalRequest", this.supportsRunInTerminalRequest);
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
    InitializeRequestArguments other = (InitializeRequestArguments) obj;
    if (this.clientID == null) {
      if (other.clientID != null)
        return false;
    } else if (!this.clientID.equals(other.clientID))
      return false;
    if (this.adapterID == null) {
      if (other.adapterID != null)
        return false;
    } else if (!this.adapterID.equals(other.adapterID))
      return false;
    if (this.locale == null) {
      if (other.locale != null)
        return false;
    } else if (!this.locale.equals(other.locale))
      return false;
    if (this.linesStartAt1 == null) {
      if (other.linesStartAt1 != null)
        return false;
    } else if (!this.linesStartAt1.equals(other.linesStartAt1))
      return false;
    if (this.columnsStartAt1 == null) {
      if (other.columnsStartAt1 != null)
        return false;
    } else if (!this.columnsStartAt1.equals(other.columnsStartAt1))
      return false;
    if (this.pathFormat == null) {
      if (other.pathFormat != null)
        return false;
    } else if (!this.pathFormat.equals(other.pathFormat))
      return false;
    if (this.supportsVariableType == null) {
      if (other.supportsVariableType != null)
        return false;
    } else if (!this.supportsVariableType.equals(other.supportsVariableType))
      return false;
    if (this.supportsVariablePaging == null) {
      if (other.supportsVariablePaging != null)
        return false;
    } else if (!this.supportsVariablePaging.equals(other.supportsVariablePaging))
      return false;
    if (this.supportsRunInTerminalRequest == null) {
      if (other.supportsRunInTerminalRequest != null)
        return false;
    } else if (!this.supportsRunInTerminalRequest.equals(other.supportsRunInTerminalRequest))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.clientID== null) ? 0 : this.clientID.hashCode());
    result = prime * result + ((this.adapterID== null) ? 0 : this.adapterID.hashCode());
    result = prime * result + ((this.locale== null) ? 0 : this.locale.hashCode());
    result = prime * result + ((this.linesStartAt1== null) ? 0 : this.linesStartAt1.hashCode());
    result = prime * result + ((this.columnsStartAt1== null) ? 0 : this.columnsStartAt1.hashCode());
    result = prime * result + ((this.pathFormat== null) ? 0 : this.pathFormat.hashCode());
    result = prime * result + ((this.supportsVariableType== null) ? 0 : this.supportsVariableType.hashCode());
    result = prime * result + ((this.supportsVariablePaging== null) ? 0 : this.supportsVariablePaging.hashCode());
    return prime * result + ((this.supportsRunInTerminalRequest== null) ? 0 : this.supportsRunInTerminalRequest.hashCode());
  }
}
