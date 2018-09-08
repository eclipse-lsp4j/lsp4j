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

import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Represents information about programming constructs like variables, classes, interfaces etc.
 */
@SuppressWarnings("all")
public class SymbolInformation {
  /**
   * The name of this symbol.
   */
  @NonNull
  private String name;
  
  /**
   * The kind of this symbol.
   */
  @NonNull
  private SymbolKind kind;
  
  /**
   * Indicates if this symbol is deprecated.
   */
  private Boolean deprecated;
  
  /**
   * The location of this symbol. The location's range is used by a tool
   * to reveal the location in the editor. If the symbol is selected in the
   * tool the range's start information is used to position the cursor. So
   * the range usually spans more then the actual symbol's name and does
   * normally include things like visibility modifiers.
   * 
   * The range doesn't have to denote a node range in the sense of a abstract
   * syntax tree. It can therefore not be used to re-construct a hierarchy of
   * the symbols.
   */
  @NonNull
  private Location location;
  
  /**
   * The name of the symbol containing this symbol. This information is for
   * user interface purposes (e.g. to render a qualifier in the user interface
   * if necessary). It can't be used to re-infer a hierarchy for the document
   * symbols.
   */
  private String containerName;
  
  public SymbolInformation() {
  }
  
  public SymbolInformation(@NonNull final String name, @NonNull final SymbolKind kind, @NonNull final Location location) {
    this.name = name;
    this.kind = kind;
    this.location = location;
  }
  
  public SymbolInformation(@NonNull final String name, @NonNull final SymbolKind kind, @NonNull final Location location, final String containerName) {
    this.name = name;
    this.kind = kind;
    this.location = location;
    this.containerName = containerName;
  }
  
  /**
   * The name of this symbol.
   */
  @Pure
  @NonNull
  public String getName() {
    return this.name;
  }
  
  /**
   * The name of this symbol.
   */
  public void setName(@NonNull final String name) {
    this.name = name;
  }
  
  /**
   * The kind of this symbol.
   */
  @Pure
  @NonNull
  public SymbolKind getKind() {
    return this.kind;
  }
  
  /**
   * The kind of this symbol.
   */
  public void setKind(@NonNull final SymbolKind kind) {
    this.kind = kind;
  }
  
  /**
   * Indicates if this symbol is deprecated.
   */
  @Pure
  public Boolean getDeprecated() {
    return this.deprecated;
  }
  
  /**
   * Indicates if this symbol is deprecated.
   */
  public void setDeprecated(final Boolean deprecated) {
    this.deprecated = deprecated;
  }
  
  /**
   * The location of this symbol. The location's range is used by a tool
   * to reveal the location in the editor. If the symbol is selected in the
   * tool the range's start information is used to position the cursor. So
   * the range usually spans more then the actual symbol's name and does
   * normally include things like visibility modifiers.
   * 
   * The range doesn't have to denote a node range in the sense of a abstract
   * syntax tree. It can therefore not be used to re-construct a hierarchy of
   * the symbols.
   */
  @Pure
  @NonNull
  public Location getLocation() {
    return this.location;
  }
  
  /**
   * The location of this symbol. The location's range is used by a tool
   * to reveal the location in the editor. If the symbol is selected in the
   * tool the range's start information is used to position the cursor. So
   * the range usually spans more then the actual symbol's name and does
   * normally include things like visibility modifiers.
   * 
   * The range doesn't have to denote a node range in the sense of a abstract
   * syntax tree. It can therefore not be used to re-construct a hierarchy of
   * the symbols.
   */
  public void setLocation(@NonNull final Location location) {
    this.location = location;
  }
  
  /**
   * The name of the symbol containing this symbol. This information is for
   * user interface purposes (e.g. to render a qualifier in the user interface
   * if necessary). It can't be used to re-infer a hierarchy for the document
   * symbols.
   */
  @Pure
  public String getContainerName() {
    return this.containerName;
  }
  
  /**
   * The name of the symbol containing this symbol. This information is for
   * user interface purposes (e.g. to render a qualifier in the user interface
   * if necessary). It can't be used to re-infer a hierarchy for the document
   * symbols.
   */
  public void setContainerName(final String containerName) {
    this.containerName = containerName;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("name", this.name);
    b.add("kind", this.kind);
    b.add("deprecated", this.deprecated);
    b.add("location", this.location);
    b.add("containerName", this.containerName);
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
    SymbolInformation other = (SymbolInformation) obj;
    if (this.name == null) {
      if (other.name != null)
        return false;
    } else if (!this.name.equals(other.name))
      return false;
    if (this.kind == null) {
      if (other.kind != null)
        return false;
    } else if (!this.kind.equals(other.kind))
      return false;
    if (this.deprecated == null) {
      if (other.deprecated != null)
        return false;
    } else if (!this.deprecated.equals(other.deprecated))
      return false;
    if (this.location == null) {
      if (other.location != null)
        return false;
    } else if (!this.location.equals(other.location))
      return false;
    if (this.containerName == null) {
      if (other.containerName != null)
        return false;
    } else if (!this.containerName.equals(other.containerName))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.name== null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.kind== null) ? 0 : this.kind.hashCode());
    result = prime * result + ((this.deprecated== null) ? 0 : this.deprecated.hashCode());
    result = prime * result + ((this.location== null) ? 0 : this.location.hashCode());
    return prime * result + ((this.containerName== null) ? 0 : this.containerName.hashCode());
  }
}
