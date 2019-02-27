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
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class ColorPresentation {
  /**
   * The label of this color presentation. It will be shown on the color
   * picker header. By default this is also the text that is inserted when selecting
   * this color presentation.
   */
  @NonNull
  private String label;
  
  /**
   * An edit which is applied to a document when selecting
   * this presentation for the color.  When `null` the label is used.
   */
  private TextEdit textEdit;
  
  /**
   * An optional array of additional text edits that are applied when
   * selecting this color presentation. Edits must not overlap with the main edit nor with themselves.
   */
  private List<TextEdit> additionalTextEdits;
  
  public ColorPresentation() {
  }
  
  public ColorPresentation(@NonNull final String label) {
    this.label = Preconditions.<String>checkNotNull(label, "label");
  }
  
  public ColorPresentation(@NonNull final String label, final TextEdit textEdit) {
    this(label);
    this.textEdit = textEdit;
  }
  
  public ColorPresentation(@NonNull final String label, final TextEdit textEdit, final List<TextEdit> additionalTextEdits) {
    this(label);
    this.textEdit = textEdit;
    this.additionalTextEdits = additionalTextEdits;
  }
  
  /**
   * The label of this color presentation. It will be shown on the color
   * picker header. By default this is also the text that is inserted when selecting
   * this color presentation.
   */
  @Pure
  @NonNull
  public String getLabel() {
    return this.label;
  }
  
  /**
   * The label of this color presentation. It will be shown on the color
   * picker header. By default this is also the text that is inserted when selecting
   * this color presentation.
   */
  public void setLabel(@NonNull final String label) {
    if (label == null) {
      throw new IllegalArgumentException("Property must not be null: label");
    }
    this.label = label;
  }
  
  /**
   * An edit which is applied to a document when selecting
   * this presentation for the color.  When `null` the label is used.
   */
  @Pure
  public TextEdit getTextEdit() {
    return this.textEdit;
  }
  
  /**
   * An edit which is applied to a document when selecting
   * this presentation for the color.  When `null` the label is used.
   */
  public void setTextEdit(final TextEdit textEdit) {
    this.textEdit = textEdit;
  }
  
  /**
   * An optional array of additional text edits that are applied when
   * selecting this color presentation. Edits must not overlap with the main edit nor with themselves.
   */
  @Pure
  public List<TextEdit> getAdditionalTextEdits() {
    return this.additionalTextEdits;
  }
  
  /**
   * An optional array of additional text edits that are applied when
   * selecting this color presentation. Edits must not overlap with the main edit nor with themselves.
   */
  public void setAdditionalTextEdits(final List<TextEdit> additionalTextEdits) {
    this.additionalTextEdits = additionalTextEdits;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("label", this.label);
    b.add("textEdit", this.textEdit);
    b.add("additionalTextEdits", this.additionalTextEdits);
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
    ColorPresentation other = (ColorPresentation) obj;
    if (this.label == null) {
      if (other.label != null)
        return false;
    } else if (!this.label.equals(other.label))
      return false;
    if (this.textEdit == null) {
      if (other.textEdit != null)
        return false;
    } else if (!this.textEdit.equals(other.textEdit))
      return false;
    if (this.additionalTextEdits == null) {
      if (other.additionalTextEdits != null)
        return false;
    } else if (!this.additionalTextEdits.equals(other.additionalTextEdits))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.label== null) ? 0 : this.label.hashCode());
    result = prime * result + ((this.textEdit== null) ? 0 : this.textEdit.hashCode());
    return prime * result + ((this.additionalTextEdits== null) ? 0 : this.additionalTextEdits.hashCode());
  }
}
