/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import com.google.gson.annotations.JsonAdapter;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.json.adapters.JsonElementTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A document link is a range in a text document that links to an internal or external resource, like another
 * text document or a web site.
 */
@SuppressWarnings("all")
public class DocumentLink {
  /**
   * The range this link applies to.
   */
  @NonNull
  private Range range;
  
  /**
   * The uri this link points to. If missing a resolve request is sent later.
   */
  private String target;
  
  /**
   * A data entry field that is preserved on a document link between a
   * DocumentLinkRequest and a DocumentLinkResolveRequest.
   */
  @JsonAdapter(JsonElementTypeAdapter.Factory.class)
  private Object data;
  
  public DocumentLink() {
  }
  
  public DocumentLink(@NonNull final Range range) {
    this.range = range;
  }
  
  public DocumentLink(@NonNull final Range range, final String target) {
    this(range);
    this.target = target;
  }
  
  public DocumentLink(@NonNull final Range range, final String target, final Object data) {
    this(range, target);
    this.data = data;
  }
  
  /**
   * The range this link applies to.
   */
  @Pure
  @NonNull
  public Range getRange() {
    return this.range;
  }
  
  /**
   * The range this link applies to.
   */
  public void setRange(@NonNull final Range range) {
    this.range = range;
  }
  
  /**
   * The uri this link points to. If missing a resolve request is sent later.
   */
  @Pure
  public String getTarget() {
    return this.target;
  }
  
  /**
   * The uri this link points to. If missing a resolve request is sent later.
   */
  public void setTarget(final String target) {
    this.target = target;
  }
  
  /**
   * A data entry field that is preserved on a document link between a
   * DocumentLinkRequest and a DocumentLinkResolveRequest.
   */
  @Pure
  public Object getData() {
    return this.data;
  }
  
  /**
   * A data entry field that is preserved on a document link between a
   * DocumentLinkRequest and a DocumentLinkResolveRequest.
   */
  public void setData(final Object data) {
    this.data = data;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("range", this.range);
    b.add("target", this.target);
    b.add("data", this.data);
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
    DocumentLink other = (DocumentLink) obj;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    if (this.target == null) {
      if (other.target != null)
        return false;
    } else if (!this.target.equals(other.target))
      return false;
    if (this.data == null) {
      if (other.data != null)
        return false;
    } else if (!this.data.equals(other.data))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    result = prime * result + ((this.target== null) ? 0 : this.target.hashCode());
    return prime * result + ((this.data== null) ? 0 : this.data.hashCode());
  }
}
