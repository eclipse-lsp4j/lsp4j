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
import org.eclipse.lsp4j.ChangeAnnotation;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A special text edit with an additional change annotation.
 * 
 * Since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class AnnotatedTextEdit extends TextEdit {
  /**
   * The actual annotation
   */
  @NonNull
  private ChangeAnnotation annotation;
  
  public AnnotatedTextEdit() {
  }
  
  public AnnotatedTextEdit(@NonNull final Range range, @NonNull final String newText, @NonNull final ChangeAnnotation annotation) {
    super(range, newText);
    this.annotation = Preconditions.<ChangeAnnotation>checkNotNull(annotation, "annotation");
  }
  
  /**
   * The actual annotation
   */
  @Pure
  @NonNull
  public ChangeAnnotation getAnnotation() {
    return this.annotation;
  }
  
  /**
   * The actual annotation
   */
  public void setAnnotation(@NonNull final ChangeAnnotation annotation) {
    this.annotation = Preconditions.checkNotNull(annotation, "annotation");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("annotation", this.annotation);
    b.add("range", getRange());
    b.add("newText", getNewText());
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
    AnnotatedTextEdit other = (AnnotatedTextEdit) obj;
    if (this.annotation == null) {
      if (other.annotation != null)
        return false;
    } else if (!this.annotation.equals(other.annotation))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * super.hashCode() + ((this.annotation== null) ? 0 : this.annotation.hashCode());
  }
}
