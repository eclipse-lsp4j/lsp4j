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

import java.util.List;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.ParameterInformation;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Represents the signature of something callable. A signature can have a label, like a function-name, a doc-comment, and
 * a set of parameters.
 */
@SuppressWarnings("all")
public class SignatureInformation {
  /**
   * The label of this signature. Will be shown in the UI.
   */
  @NonNull
  private String label;
  
  /**
   * The human-readable doc-comment of this signature. Will be shown in the UI but can be omitted.
   */
  private Either<String, MarkupContent> documentation;
  
  /**
   * The parameters of this signature.
   */
  private List<ParameterInformation> parameters;
  
  public SignatureInformation() {
  }
  
  public SignatureInformation(@NonNull final String label) {
    this.label = Preconditions.<String>checkNotNull(label, "label");
  }
  
  public SignatureInformation(@NonNull final String label, final String documentation, final List<ParameterInformation> parameters) {
    this(label);
    this.setDocumentation(documentation);
    this.parameters = parameters;
  }
  
  public SignatureInformation(@NonNull final String label, final MarkupContent documentation, final List<ParameterInformation> parameters) {
    this(label);
    this.setDocumentation(documentation);
    this.parameters = parameters;
  }
  
  /**
   * The label of this signature. Will be shown in the UI.
   */
  @Pure
  @NonNull
  public String getLabel() {
    return this.label;
  }
  
  /**
   * The label of this signature. Will be shown in the UI.
   */
  public void setLabel(@NonNull final String label) {
    if (label == null) {
      throw new IllegalArgumentException("Property must not be null: label");
    }
    this.label = label;
  }
  
  /**
   * The human-readable doc-comment of this signature. Will be shown in the UI but can be omitted.
   */
  @Pure
  public Either<String, MarkupContent> getDocumentation() {
    return this.documentation;
  }
  
  /**
   * The human-readable doc-comment of this signature. Will be shown in the UI but can be omitted.
   */
  public void setDocumentation(final Either<String, MarkupContent> documentation) {
    this.documentation = documentation;
  }
  
  public void setDocumentation(final String documentation) {
    if (documentation == null) {
      this.documentation = null;
      return;
    }
    this.documentation = Either.forLeft(documentation);
  }
  
  public void setDocumentation(final MarkupContent documentation) {
    if (documentation == null) {
      this.documentation = null;
      return;
    }
    this.documentation = Either.forRight(documentation);
  }
  
  /**
   * The parameters of this signature.
   */
  @Pure
  public List<ParameterInformation> getParameters() {
    return this.parameters;
  }
  
  /**
   * The parameters of this signature.
   */
  public void setParameters(final List<ParameterInformation> parameters) {
    this.parameters = parameters;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("label", this.label);
    b.add("documentation", this.documentation);
    b.add("parameters", this.parameters);
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
    SignatureInformation other = (SignatureInformation) obj;
    if (this.label == null) {
      if (other.label != null)
        return false;
    } else if (!this.label.equals(other.label))
      return false;
    if (this.documentation == null) {
      if (other.documentation != null)
        return false;
    } else if (!this.documentation.equals(other.documentation))
      return false;
    if (this.parameters == null) {
      if (other.parameters != null)
        return false;
    } else if (!this.parameters.equals(other.parameters))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.label== null) ? 0 : this.label.hashCode());
    result = prime * result + ((this.documentation== null) ? 0 : this.documentation.hashCode());
    return prime * result + ((this.parameters== null) ? 0 : this.parameters.hashCode());
  }
}
