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

import org.eclipse.lsp4j.TypeHierarchyItem;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Request to resolve an unresolved {@link TypeHierarchyItem type hierarchy item} which is indicated if the
 * {@link TypeHierarchyItem#getParents parents} or the {@link TypeHierarchyItem#getChildren children} is not
 * defined. If resolved and no {@code parents} or {@code children} are available then an empty list is returned.
 */
@SuppressWarnings("all")
public class ResolveTypeHierarchyItemParams {
  /**
   * The hierarchy item to resolve.
   */
  @NonNull
  private TypeHierarchyItem item;
  
  /**
   * The number of hierarchy levels to resolve. {@code 0} indicates no hierarchy level.
   */
  private int resolve;
  
  /**
   * The direction of the type hierarchy resolution.
   * 
   * <p>
   * The followings are the legal values:
   * <ul>
   * <li>{@code children},</li>
   * <li>{@code parents}, and</li>
   * <li>{@code both}.</li>
   * </ul>
   * </p>
   */
  @NonNull
  private String direction;
  
  /**
   * The hierarchy item to resolve.
   */
  @Pure
  @NonNull
  public TypeHierarchyItem getItem() {
    return this.item;
  }
  
  /**
   * The hierarchy item to resolve.
   */
  public void setItem(@NonNull final TypeHierarchyItem item) {
    this.item = item;
  }
  
  /**
   * The number of hierarchy levels to resolve. {@code 0} indicates no hierarchy level.
   */
  @Pure
  public int getResolve() {
    return this.resolve;
  }
  
  /**
   * The number of hierarchy levels to resolve. {@code 0} indicates no hierarchy level.
   */
  public void setResolve(final int resolve) {
    this.resolve = resolve;
  }
  
  /**
   * The direction of the type hierarchy resolution.
   * 
   * <p>
   * The followings are the legal values:
   * <ul>
   * <li>{@code children},</li>
   * <li>{@code parents}, and</li>
   * <li>{@code both}.</li>
   * </ul>
   * </p>
   */
  @Pure
  @NonNull
  public String getDirection() {
    return this.direction;
  }
  
  /**
   * The direction of the type hierarchy resolution.
   * 
   * <p>
   * The followings are the legal values:
   * <ul>
   * <li>{@code children},</li>
   * <li>{@code parents}, and</li>
   * <li>{@code both}.</li>
   * </ul>
   * </p>
   */
  public void setDirection(@NonNull final String direction) {
    this.direction = direction;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("item", this.item);
    b.add("resolve", this.resolve);
    b.add("direction", this.direction);
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
    ResolveTypeHierarchyItemParams other = (ResolveTypeHierarchyItemParams) obj;
    if (this.item == null) {
      if (other.item != null)
        return false;
    } else if (!this.item.equals(other.item))
      return false;
    if (other.resolve != this.resolve)
      return false;
    if (this.direction == null) {
      if (other.direction != null)
        return false;
    } else if (!this.direction.equals(other.direction))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.item== null) ? 0 : this.item.hashCode());
    result = prime * result + this.resolve;
    return prime * result + ((this.direction== null) ? 0 : this.direction.hashCode());
  }
}
