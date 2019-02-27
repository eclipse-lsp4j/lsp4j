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

import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Tuple;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Represents a parameter of a callable-signature. A parameter can have a label and a doc-comment.
 */
@SuppressWarnings("all")
public class ParameterInformation {
  /**
   * The label of this parameter information.
   * 
   * Either a string or an inclusive start and exclusive end offsets within its containing
   * signature label (see {@link SignatureInformation#label}). The offsets are based on a UTF-16
   * string representation as {@code Position} and {@code Range} does.
   * 
   * <em>Note</em>: a label of type string should be a substring of its containing signature label.
   * Its intended use case is to highlight the parameter label part in the {@link SignatureInformation#label}.
   */
  @NonNull
  private Either<String, Tuple.Two<Integer, Integer>> label;
  
  /**
   * The human-readable doc-comment of this signature. Will be shown in the UI but can be omitted.
   */
  private Either<String, MarkupContent> documentation;
  
  public ParameterInformation() {
  }
  
  public ParameterInformation(@NonNull final String label) {
    this.setLabel(label);
  }
  
  public ParameterInformation(@NonNull final String label, final String documentation) {
    this.setLabel(label);
    this.setDocumentation(documentation);
  }
  
  public ParameterInformation(@NonNull final String label, final MarkupContent documentation) {
    this.setLabel(label);
    this.setDocumentation(documentation);
  }
  
  /**
   * The label of this parameter information.
   * 
   * Either a string or an inclusive start and exclusive end offsets within its containing
   * signature label (see {@link SignatureInformation#label}). The offsets are based on a UTF-16
   * string representation as {@code Position} and {@code Range} does.
   * 
   * <em>Note</em>: a label of type string should be a substring of its containing signature label.
   * Its intended use case is to highlight the parameter label part in the {@link SignatureInformation#label}.
   */
  @Pure
  @NonNull
  public Either<String, Tuple.Two<Integer, Integer>> getLabel() {
    return this.label;
  }
  
  /**
   * The label of this parameter information.
   * 
   * Either a string or an inclusive start and exclusive end offsets within its containing
   * signature label (see {@link SignatureInformation#label}). The offsets are based on a UTF-16
   * string representation as {@code Position} and {@code Range} does.
   * 
   * <em>Note</em>: a label of type string should be a substring of its containing signature label.
   * Its intended use case is to highlight the parameter label part in the {@link SignatureInformation#label}.
   */
  public void setLabel(@NonNull final Either<String, Tuple.Two<Integer, Integer>> label) {
    this.label = label;
  }
  
  public void setLabel(final String label) {
    if (label == null) {
      this.label = null;
      return;
    }
    this.label = Either.forLeft(label);
  }
  
  public void setLabel(final Tuple.Two<Integer, Integer> label) {
    if (label == null) {
      this.label = null;
      return;
    }
    this.label = Either.forRight(label);
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
    return prime * result + ((this.documentation== null) ? 0 : this.documentation.hashCode());
  }
}
