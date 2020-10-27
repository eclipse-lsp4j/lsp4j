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

import org.eclipse.lsp4j.debug.CompletionItemType;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * CompletionItems are the suggestions returned from the CompletionsRequest.
 */
@SuppressWarnings("all")
public class CompletionItem {
  /**
   * The label of this completion item. By default this is also the text that is inserted when selecting this
   * completion.
   */
  @NonNull
  private String label;
  
  /**
   * If text is not falsy then it is inserted instead of the label.
   * <p>
   * This is an optional property.
   */
  private String text;
  
  /**
   * A string that should be used when comparing this item with other items. When `falsy` the label is used.
   * <p>
   * This is an optional property.
   */
  private String sortText;
  
  /**
   * The item's type. Typically the client uses this information to render the item in the UI with an icon.
   * <p>
   * This is an optional property.
   */
  private CompletionItemType type;
  
  /**
   * This value determines the location (in the CompletionsRequest's 'text' attribute) where the completion text is
   * added.
   * <p>
   * If missing the text is added at the location specified by the CompletionsRequest's 'column' attribute.
   * <p>
   * This is an optional property.
   */
  private Integer start;
  
  /**
   * This value determines how many characters are overwritten by the completion text.
   * <p>
   * If missing the value 0 is assumed which results in the completion text being inserted.
   * <p>
   * This is an optional property.
   */
  private Integer length;
  
  /**
   * Determines the start of the new selection after the text has been inserted (or replaced).
   * <p>
   * The start position must in the range 0 and length of the completion text.
   * <p>
   * If omitted the selection starts at the end of the completion text.
   * <p>
   * This is an optional property.
   */
  private Integer selectionStart;
  
  /**
   * Determines the length of the new selection after the text has been inserted (or replaced).
   * <p>
   * The selection can not extend beyond the bounds of the completion text.
   * <p>
   * If omitted the length is assumed to be 0.
   * <p>
   * This is an optional property.
   */
  private Integer selectionLength;
  
  /**
   * The label of this completion item. By default this is also the text that is inserted when selecting this
   * completion.
   */
  @Pure
  @NonNull
  public String getLabel() {
    return this.label;
  }
  
  /**
   * The label of this completion item. By default this is also the text that is inserted when selecting this
   * completion.
   */
  public void setLabel(@NonNull final String label) {
    this.label = Preconditions.checkNotNull(label, "label");
  }
  
  /**
   * If text is not falsy then it is inserted instead of the label.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getText() {
    return this.text;
  }
  
  /**
   * If text is not falsy then it is inserted instead of the label.
   * <p>
   * This is an optional property.
   */
  public void setText(final String text) {
    this.text = text;
  }
  
  /**
   * A string that should be used when comparing this item with other items. When `falsy` the label is used.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getSortText() {
    return this.sortText;
  }
  
  /**
   * A string that should be used when comparing this item with other items. When `falsy` the label is used.
   * <p>
   * This is an optional property.
   */
  public void setSortText(final String sortText) {
    this.sortText = sortText;
  }
  
  /**
   * The item's type. Typically the client uses this information to render the item in the UI with an icon.
   * <p>
   * This is an optional property.
   */
  @Pure
  public CompletionItemType getType() {
    return this.type;
  }
  
  /**
   * The item's type. Typically the client uses this information to render the item in the UI with an icon.
   * <p>
   * This is an optional property.
   */
  public void setType(final CompletionItemType type) {
    this.type = type;
  }
  
  /**
   * This value determines the location (in the CompletionsRequest's 'text' attribute) where the completion text is
   * added.
   * <p>
   * If missing the text is added at the location specified by the CompletionsRequest's 'column' attribute.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getStart() {
    return this.start;
  }
  
  /**
   * This value determines the location (in the CompletionsRequest's 'text' attribute) where the completion text is
   * added.
   * <p>
   * If missing the text is added at the location specified by the CompletionsRequest's 'column' attribute.
   * <p>
   * This is an optional property.
   */
  public void setStart(final Integer start) {
    this.start = start;
  }
  
  /**
   * This value determines how many characters are overwritten by the completion text.
   * <p>
   * If missing the value 0 is assumed which results in the completion text being inserted.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getLength() {
    return this.length;
  }
  
  /**
   * This value determines how many characters are overwritten by the completion text.
   * <p>
   * If missing the value 0 is assumed which results in the completion text being inserted.
   * <p>
   * This is an optional property.
   */
  public void setLength(final Integer length) {
    this.length = length;
  }
  
  /**
   * Determines the start of the new selection after the text has been inserted (or replaced).
   * <p>
   * The start position must in the range 0 and length of the completion text.
   * <p>
   * If omitted the selection starts at the end of the completion text.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getSelectionStart() {
    return this.selectionStart;
  }
  
  /**
   * Determines the start of the new selection after the text has been inserted (or replaced).
   * <p>
   * The start position must in the range 0 and length of the completion text.
   * <p>
   * If omitted the selection starts at the end of the completion text.
   * <p>
   * This is an optional property.
   */
  public void setSelectionStart(final Integer selectionStart) {
    this.selectionStart = selectionStart;
  }
  
  /**
   * Determines the length of the new selection after the text has been inserted (or replaced).
   * <p>
   * The selection can not extend beyond the bounds of the completion text.
   * <p>
   * If omitted the length is assumed to be 0.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getSelectionLength() {
    return this.selectionLength;
  }
  
  /**
   * Determines the length of the new selection after the text has been inserted (or replaced).
   * <p>
   * The selection can not extend beyond the bounds of the completion text.
   * <p>
   * If omitted the length is assumed to be 0.
   * <p>
   * This is an optional property.
   */
  public void setSelectionLength(final Integer selectionLength) {
    this.selectionLength = selectionLength;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("label", this.label);
    b.add("text", this.text);
    b.add("sortText", this.sortText);
    b.add("type", this.type);
    b.add("start", this.start);
    b.add("length", this.length);
    b.add("selectionStart", this.selectionStart);
    b.add("selectionLength", this.selectionLength);
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
    CompletionItem other = (CompletionItem) obj;
    if (this.label == null) {
      if (other.label != null)
        return false;
    } else if (!this.label.equals(other.label))
      return false;
    if (this.text == null) {
      if (other.text != null)
        return false;
    } else if (!this.text.equals(other.text))
      return false;
    if (this.sortText == null) {
      if (other.sortText != null)
        return false;
    } else if (!this.sortText.equals(other.sortText))
      return false;
    if (this.type == null) {
      if (other.type != null)
        return false;
    } else if (!this.type.equals(other.type))
      return false;
    if (this.start == null) {
      if (other.start != null)
        return false;
    } else if (!this.start.equals(other.start))
      return false;
    if (this.length == null) {
      if (other.length != null)
        return false;
    } else if (!this.length.equals(other.length))
      return false;
    if (this.selectionStart == null) {
      if (other.selectionStart != null)
        return false;
    } else if (!this.selectionStart.equals(other.selectionStart))
      return false;
    if (this.selectionLength == null) {
      if (other.selectionLength != null)
        return false;
    } else if (!this.selectionLength.equals(other.selectionLength))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.label== null) ? 0 : this.label.hashCode());
    result = prime * result + ((this.text== null) ? 0 : this.text.hashCode());
    result = prime * result + ((this.sortText== null) ? 0 : this.sortText.hashCode());
    result = prime * result + ((this.type== null) ? 0 : this.type.hashCode());
    result = prime * result + ((this.start== null) ? 0 : this.start.hashCode());
    result = prime * result + ((this.length== null) ? 0 : this.length.hashCode());
    result = prime * result + ((this.selectionStart== null) ? 0 : this.selectionStart.hashCode());
    return prime * result + ((this.selectionLength== null) ? 0 : this.selectionLength.hashCode());
  }
}
