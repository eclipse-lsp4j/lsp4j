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

import com.google.common.annotations.Beta;
import java.util.List;
import org.eclipse.lsp4j.DynamicRegistrationCapabilities;
import org.eclipse.lsp4j.SemanticTokensClientCapabilitiesRequests;
import org.eclipse.lsp4j.TokenFormat;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * @since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class SemanticTokensCapabilities extends DynamicRegistrationCapabilities {
  /**
   * Which requests the client supports and might send to the server.
   */
  @NonNull
  private SemanticTokensClientCapabilitiesRequests requests;
  
  /**
   * The token types that the client supports.
   */
  @NonNull
  private List<String> tokenTypes;
  
  /**
   * The token modifiers that the client supports.
   */
  @NonNull
  private List<String> tokenModifiers;
  
  /**
   * The formats the clients supports.
   */
  @NonNull
  private List<TokenFormat> formats;
  
  public SemanticTokensCapabilities(final Boolean dynamicRegistration) {
    super(dynamicRegistration);
  }
  
  public SemanticTokensCapabilities(@NonNull final SemanticTokensClientCapabilitiesRequests requests, @NonNull final List<String> tokenTypes, @NonNull final List<String> tokenModifiers, @NonNull final List<TokenFormat> formats) {
    this.requests = Preconditions.<SemanticTokensClientCapabilitiesRequests>checkNotNull(requests, "requests");
    this.tokenTypes = Preconditions.<List<String>>checkNotNull(tokenTypes, "tokenTypes");
    this.tokenModifiers = Preconditions.<List<String>>checkNotNull(tokenModifiers, "tokenModifiers");
    this.formats = Preconditions.<List<TokenFormat>>checkNotNull(formats, "formats");
  }
  
  public SemanticTokensCapabilities(final Boolean dynamicRegistration, @NonNull final SemanticTokensClientCapabilitiesRequests requests, @NonNull final List<String> tokenTypes, @NonNull final List<String> tokenModifiers, @NonNull final List<TokenFormat> formats) {
    super(dynamicRegistration);
    this.requests = Preconditions.<SemanticTokensClientCapabilitiesRequests>checkNotNull(requests, "requests");
    this.tokenTypes = Preconditions.<List<String>>checkNotNull(tokenTypes, "tokenTypes");
    this.tokenModifiers = Preconditions.<List<String>>checkNotNull(tokenModifiers, "tokenModifiers");
    this.formats = Preconditions.<List<TokenFormat>>checkNotNull(formats, "formats");
  }
  
  /**
   * Which requests the client supports and might send to the server.
   */
  @Pure
  @NonNull
  public SemanticTokensClientCapabilitiesRequests getRequests() {
    return this.requests;
  }
  
  /**
   * Which requests the client supports and might send to the server.
   */
  public void setRequests(@NonNull final SemanticTokensClientCapabilitiesRequests requests) {
    this.requests = Preconditions.checkNotNull(requests, "requests");
  }
  
  /**
   * The token types that the client supports.
   */
  @Pure
  @NonNull
  public List<String> getTokenTypes() {
    return this.tokenTypes;
  }
  
  /**
   * The token types that the client supports.
   */
  public void setTokenTypes(@NonNull final List<String> tokenTypes) {
    this.tokenTypes = Preconditions.checkNotNull(tokenTypes, "tokenTypes");
  }
  
  /**
   * The token modifiers that the client supports.
   */
  @Pure
  @NonNull
  public List<String> getTokenModifiers() {
    return this.tokenModifiers;
  }
  
  /**
   * The token modifiers that the client supports.
   */
  public void setTokenModifiers(@NonNull final List<String> tokenModifiers) {
    this.tokenModifiers = Preconditions.checkNotNull(tokenModifiers, "tokenModifiers");
  }
  
  /**
   * The formats the clients supports.
   */
  @Pure
  @NonNull
  public List<TokenFormat> getFormats() {
    return this.formats;
  }
  
  /**
   * The formats the clients supports.
   */
  public void setFormats(@NonNull final List<TokenFormat> formats) {
    this.formats = Preconditions.checkNotNull(formats, "formats");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("requests", this.requests);
    b.add("tokenTypes", this.tokenTypes);
    b.add("tokenModifiers", this.tokenModifiers);
    b.add("formats", this.formats);
    b.add("dynamicRegistration", getDynamicRegistration());
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
    if (!super.equals(obj))
      return false;
    SemanticTokensCapabilities other = (SemanticTokensCapabilities) obj;
    if (this.requests == null) {
      if (other.requests != null)
        return false;
    } else if (!this.requests.equals(other.requests))
      return false;
    if (this.tokenTypes == null) {
      if (other.tokenTypes != null)
        return false;
    } else if (!this.tokenTypes.equals(other.tokenTypes))
      return false;
    if (this.tokenModifiers == null) {
      if (other.tokenModifiers != null)
        return false;
    } else if (!this.tokenModifiers.equals(other.tokenModifiers))
      return false;
    if (this.formats == null) {
      if (other.formats != null)
        return false;
    } else if (!this.formats.equals(other.formats))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.requests== null) ? 0 : this.requests.hashCode());
    result = prime * result + ((this.tokenTypes== null) ? 0 : this.tokenTypes.hashCode());
    result = prime * result + ((this.tokenModifiers== null) ? 0 : this.tokenModifiers.hashCode());
    return prime * result + ((this.formats== null) ? 0 : this.formats.hashCode());
  }
}
