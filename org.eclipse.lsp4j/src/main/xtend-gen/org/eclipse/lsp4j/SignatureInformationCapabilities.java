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
import org.eclipse.lsp4j.ParameterInformationCapabilities;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The client supports the following `SignatureInformation` specific properties.
 */
@SuppressWarnings("all")
public class SignatureInformationCapabilities {
  /**
   * Client supports the following content formats for the documentation
   * property. The order describes the preferred format of the client.
   * 
   * See {@link MarkupKind} for allowed values.
   */
  private List<String> documentationFormat;
  
  /**
   * Client capabilities specific to parameter information.
   */
  private ParameterInformationCapabilities parameterInformation;
  
  /**
   * The client supports the `activeParameter` property on `SignatureInformation`
   * literal.
   * 
   * Since 3.16.0
   */
  @Beta
  private Boolean activeParameterSupport;
  
  public SignatureInformationCapabilities() {
  }
  
  public SignatureInformationCapabilities(final List<String> documentationFormat) {
    this.documentationFormat = documentationFormat;
  }
  
  /**
   * Client supports the following content formats for the documentation
   * property. The order describes the preferred format of the client.
   * 
   * See {@link MarkupKind} for allowed values.
   */
  @Pure
  public List<String> getDocumentationFormat() {
    return this.documentationFormat;
  }
  
  /**
   * Client supports the following content formats for the documentation
   * property. The order describes the preferred format of the client.
   * 
   * See {@link MarkupKind} for allowed values.
   */
  public void setDocumentationFormat(final List<String> documentationFormat) {
    this.documentationFormat = documentationFormat;
  }
  
  /**
   * Client capabilities specific to parameter information.
   */
  @Pure
  public ParameterInformationCapabilities getParameterInformation() {
    return this.parameterInformation;
  }
  
  /**
   * Client capabilities specific to parameter information.
   */
  public void setParameterInformation(final ParameterInformationCapabilities parameterInformation) {
    this.parameterInformation = parameterInformation;
  }
  
  /**
   * The client supports the `activeParameter` property on `SignatureInformation`
   * literal.
   * 
   * Since 3.16.0
   */
  @Pure
  public Boolean getActiveParameterSupport() {
    return this.activeParameterSupport;
  }
  
  /**
   * The client supports the `activeParameter` property on `SignatureInformation`
   * literal.
   * 
   * Since 3.16.0
   */
  public void setActiveParameterSupport(final Boolean activeParameterSupport) {
    this.activeParameterSupport = activeParameterSupport;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("documentationFormat", this.documentationFormat);
    b.add("parameterInformation", this.parameterInformation);
    b.add("activeParameterSupport", this.activeParameterSupport);
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
    SignatureInformationCapabilities other = (SignatureInformationCapabilities) obj;
    if (this.documentationFormat == null) {
      if (other.documentationFormat != null)
        return false;
    } else if (!this.documentationFormat.equals(other.documentationFormat))
      return false;
    if (this.parameterInformation == null) {
      if (other.parameterInformation != null)
        return false;
    } else if (!this.parameterInformation.equals(other.parameterInformation))
      return false;
    if (this.activeParameterSupport == null) {
      if (other.activeParameterSupport != null)
        return false;
    } else if (!this.activeParameterSupport.equals(other.activeParameterSupport))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.documentationFormat== null) ? 0 : this.documentationFormat.hashCode());
    result = prime * result + ((this.parameterInformation== null) ? 0 : this.parameterInformation.hashCode());
    return prime * result + ((this.activeParameterSupport== null) ? 0 : this.activeParameterSupport.hashCode());
  }
}
