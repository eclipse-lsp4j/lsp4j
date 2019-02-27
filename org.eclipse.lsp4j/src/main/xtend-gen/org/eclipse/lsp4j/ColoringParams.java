/**
 * Copyright (c) 2016 TypeFox and others.
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
import java.util.ArrayList;
import java.util.List;
import org.eclipse.lsp4j.ColoringInformation;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Representation of a computed mapping from ranges to the appropriate
 * highlighting style.
 * 
 * @deprecated Use {@link SemanticHighlightingParams} instead.
 */
@Beta
@Deprecated
@SuppressWarnings("all")
public class ColoringParams {
  /**
   * The URI for which coloring information is reported.
   */
  @NonNull
  private String uri;
  
  /**
   * A list of coloring information instances.
   */
  @NonNull
  private List<? extends ColoringInformation> infos;
  
  public ColoringParams() {
    ArrayList<ColoringInformation> _arrayList = new ArrayList<ColoringInformation>();
    this.infos = _arrayList;
  }
  
  public ColoringParams(final String uri, final List<? extends ColoringInformation> infos) {
    this.uri = uri;
    this.infos = infos;
  }
  
  /**
   * The URI for which coloring information is reported.
   */
  @Pure
  @NonNull
  public String getUri() {
    return this.uri;
  }
  
  /**
   * The URI for which coloring information is reported.
   */
  public void setUri(@NonNull final String uri) {
    if (uri == null) {
      throw new IllegalArgumentException("Property must not be null: uri");
    }
    this.uri = uri;
  }
  
  /**
   * A list of coloring information instances.
   */
  @Pure
  @NonNull
  public List<? extends ColoringInformation> getInfos() {
    return this.infos;
  }
  
  /**
   * A list of coloring information instances.
   */
  public void setInfos(@NonNull final List<? extends ColoringInformation> infos) {
    if (infos == null) {
      throw new IllegalArgumentException("Property must not be null: infos");
    }
    this.infos = infos;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("uri", this.uri);
    b.add("infos", this.infos);
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
    ColoringParams other = (ColoringParams) obj;
    if (this.uri == null) {
      if (other.uri != null)
        return false;
    } else if (!this.uri.equals(other.uri))
      return false;
    if (this.infos == null) {
      if (other.infos != null)
        return false;
    } else if (!this.infos.equals(other.infos))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.uri== null) ? 0 : this.uri.hashCode());
    return prime * result + ((this.infos== null) ? 0 : this.infos.hashCode());
  }
}
