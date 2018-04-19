/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Represents a parameter of a callable-signature. A parameter can have a label and a doc-comment.
 */
@SuppressWarnings("all")
public class ParameterInformation {
  /**
   * The label of this signature. Will be shown in the UI.
   */
  @NonNull
  private String label;
  
  /**
   * The human-readable doc-comment of this signature. Will be shown in the UI but can be omitted.
   */
  private Either<String, MarkupContent> documentation;
  
  public ParameterInformation() {
  }
  
  public ParameterInformation(@NonNull final String label) {
    this.label = label;
  }
  
  public ParameterInformation(@NonNull final String label, final String documentation) {
    this.label = label;
    this.setDocumentation(documentation);
  }
  
  public ParameterInformation(@NonNull final String label, final MarkupContent documentation) {
    this.label = label;
    this.setDocumentation(documentation);
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
    this.documentation = Either.forLeft(documentation);
  }
  
  public void setDocumentation(final MarkupContent documentation) {
    this.documentation = Either.forRight(documentation);
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("label", this.label);
    b.add("documentation", this.documentation);
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
    ParameterInformation other = (ParameterInformation) obj;
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
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.label== null) ? 0 : this.label.hashCode());
    result = prime * result + ((this.documentation== null) ? 0 : this.documentation.hashCode());
    return result;
  }
}
