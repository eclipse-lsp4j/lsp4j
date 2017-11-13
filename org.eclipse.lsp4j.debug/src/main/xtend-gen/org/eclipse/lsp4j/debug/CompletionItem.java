/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.lsp4j.debug.CompletionItemType;
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
  private String label;
  
  /**
   * If text is not falsy then it is inserted instead of the label.
   * <p>
   * This is an optional property.
   */
  private String text;
  
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
   * The label of this completion item. By default this is also the text that is inserted when selecting this
   * completion.
   */
  @Pure
  public String getLabel() {
    return this.label;
  }
  
  /**
   * The label of this completion item. By default this is also the text that is inserted when selecting this
   * completion.
   */
  public void setLabel(final String label) {
    this.label = label;
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
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("label", this.label);
    b.add("text", this.text);
    b.add("type", this.type);
    b.add("start", this.start);
    b.add("length", this.length);
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
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.label== null) ? 0 : this.label.hashCode());
    result = prime * result + ((this.text== null) ? 0 : this.text.hashCode());
    result = prime * result + ((this.type== null) ? 0 : this.type.hashCode());
    result = prime * result + ((this.start== null) ? 0 : this.start.hashCode());
    result = prime * result + ((this.length== null) ? 0 : this.length.hashCode());
    return result;
  }
}
