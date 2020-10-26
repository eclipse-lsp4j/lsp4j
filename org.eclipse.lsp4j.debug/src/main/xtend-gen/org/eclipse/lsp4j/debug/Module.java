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

import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A Module object represents a row in the modules view.
 * <p>
 * Two attributes are mandatory: an id identifies a module in the modules view and is used in a ModuleEvent for
 * identifying a module for adding, updating or deleting.
 * <p>
 * The name is used to minimally render the module in the UI.
 * <p>
 * 
 * <p>
 * Additional attributes can be added to the module. They will show up in the module View if they have a
 * corresponding ColumnDescriptor.
 * <p>
 * 
 * <p>
 * To avoid an unnecessary proliferation of additional attributes with similar semantics but different names
 * <p>
 * we recommend to re-use attributes from the 'recommended' list below first, and only introduce new attributes if
 * nothing appropriate could be found.
 */
@SuppressWarnings("all")
public class Module {
  /**
   * Unique identifier for the module.
   */
  @NonNull
  private Either<Integer, String> id;
  
  /**
   * A name of the module.
   */
  @NonNull
  private String name;
  
  /**
   * optional but recommended attributes.
   * <p>
   * always try to use these first before introducing additional attributes.
   * <p>
   * 
   * <p>
   * Logical full path to the module. The exact definition is implementation defined, but usually this would be a
   * full path to the on-disk file for the module.
   * <p>
   * This is an optional property.
   */
  private String path;
  
  /**
   * True if the module is optimized.
   * <p>
   * This is an optional property.
   */
  private Boolean isOptimized;
  
  /**
   * True if the module is considered 'user code' by a debugger that supports 'Just My Code'.
   * <p>
   * This is an optional property.
   */
  private Boolean isUserCode;
  
  /**
   * Version of Module.
   * <p>
   * This is an optional property.
   */
  private String version;
  
  /**
   * User understandable description of if symbols were found for the module (ex: 'Symbols Loaded', 'Symbols not
   * found', etc.
   * <p>
   * This is an optional property.
   */
  private String symbolStatus;
  
  /**
   * Logical full path to the symbol file. The exact definition is implementation defined.
   * <p>
   * This is an optional property.
   */
  private String symbolFilePath;
  
  /**
   * Module created or modified.
   * <p>
   * This is an optional property.
   */
  private String dateTimeStamp;
  
  /**
   * Address range covered by this module.
   * <p>
   * This is an optional property.
   */
  private String addressRange;
  
  /**
   * Unique identifier for the module.
   */
  @Pure
  @NonNull
  public Either<Integer, String> getId() {
    return this.id;
  }
  
  /**
   * Unique identifier for the module.
   */
  public void setId(@NonNull final Either<Integer, String> id) {
    this.id = Preconditions.checkNotNull(id, "id");
  }
  
  public void setId(final Integer id) {
    if (id == null) {
      Preconditions.checkNotNull(id, "id");
      this.id = null;
      return;
    }
    this.id = Either.forLeft(id);
  }
  
  public void setId(final String id) {
    if (id == null) {
      Preconditions.checkNotNull(id, "id");
      this.id = null;
      return;
    }
    this.id = Either.forRight(id);
  }
  
  /**
   * A name of the module.
   */
  @Pure
  @NonNull
  public String getName() {
    return this.name;
  }
  
  /**
   * A name of the module.
   */
  public void setName(@NonNull final String name) {
    this.name = Preconditions.checkNotNull(name, "name");
  }
  
  /**
   * optional but recommended attributes.
   * <p>
   * always try to use these first before introducing additional attributes.
   * <p>
   * 
   * <p>
   * Logical full path to the module. The exact definition is implementation defined, but usually this would be a
   * full path to the on-disk file for the module.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getPath() {
    return this.path;
  }
  
  /**
   * optional but recommended attributes.
   * <p>
   * always try to use these first before introducing additional attributes.
   * <p>
   * 
   * <p>
   * Logical full path to the module. The exact definition is implementation defined, but usually this would be a
   * full path to the on-disk file for the module.
   * <p>
   * This is an optional property.
   */
  public void setPath(final String path) {
    this.path = path;
  }
  
  /**
   * True if the module is optimized.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getIsOptimized() {
    return this.isOptimized;
  }
  
  /**
   * True if the module is optimized.
   * <p>
   * This is an optional property.
   */
  public void setIsOptimized(final Boolean isOptimized) {
    this.isOptimized = isOptimized;
  }
  
  /**
   * True if the module is considered 'user code' by a debugger that supports 'Just My Code'.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getIsUserCode() {
    return this.isUserCode;
  }
  
  /**
   * True if the module is considered 'user code' by a debugger that supports 'Just My Code'.
   * <p>
   * This is an optional property.
   */
  public void setIsUserCode(final Boolean isUserCode) {
    this.isUserCode = isUserCode;
  }
  
  /**
   * Version of Module.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getVersion() {
    return this.version;
  }
  
  /**
   * Version of Module.
   * <p>
   * This is an optional property.
   */
  public void setVersion(final String version) {
    this.version = version;
  }
  
  /**
   * User understandable description of if symbols were found for the module (ex: 'Symbols Loaded', 'Symbols not
   * found', etc.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getSymbolStatus() {
    return this.symbolStatus;
  }
  
  /**
   * User understandable description of if symbols were found for the module (ex: 'Symbols Loaded', 'Symbols not
   * found', etc.
   * <p>
   * This is an optional property.
   */
  public void setSymbolStatus(final String symbolStatus) {
    this.symbolStatus = symbolStatus;
  }
  
  /**
   * Logical full path to the symbol file. The exact definition is implementation defined.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getSymbolFilePath() {
    return this.symbolFilePath;
  }
  
  /**
   * Logical full path to the symbol file. The exact definition is implementation defined.
   * <p>
   * This is an optional property.
   */
  public void setSymbolFilePath(final String symbolFilePath) {
    this.symbolFilePath = symbolFilePath;
  }
  
  /**
   * Module created or modified.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getDateTimeStamp() {
    return this.dateTimeStamp;
  }
  
  /**
   * Module created or modified.
   * <p>
   * This is an optional property.
   */
  public void setDateTimeStamp(final String dateTimeStamp) {
    this.dateTimeStamp = dateTimeStamp;
  }
  
  /**
   * Address range covered by this module.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getAddressRange() {
    return this.addressRange;
  }
  
  /**
   * Address range covered by this module.
   * <p>
   * This is an optional property.
   */
  public void setAddressRange(final String addressRange) {
    this.addressRange = addressRange;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("id", this.id);
    b.add("name", this.name);
    b.add("path", this.path);
    b.add("isOptimized", this.isOptimized);
    b.add("isUserCode", this.isUserCode);
    b.add("version", this.version);
    b.add("symbolStatus", this.symbolStatus);
    b.add("symbolFilePath", this.symbolFilePath);
    b.add("dateTimeStamp", this.dateTimeStamp);
    b.add("addressRange", this.addressRange);
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
    Module other = (Module) obj;
    if (this.id == null) {
      if (other.id != null)
        return false;
    } else if (!this.id.equals(other.id))
      return false;
    if (this.name == null) {
      if (other.name != null)
        return false;
    } else if (!this.name.equals(other.name))
      return false;
    if (this.path == null) {
      if (other.path != null)
        return false;
    } else if (!this.path.equals(other.path))
      return false;
    if (this.isOptimized == null) {
      if (other.isOptimized != null)
        return false;
    } else if (!this.isOptimized.equals(other.isOptimized))
      return false;
    if (this.isUserCode == null) {
      if (other.isUserCode != null)
        return false;
    } else if (!this.isUserCode.equals(other.isUserCode))
      return false;
    if (this.version == null) {
      if (other.version != null)
        return false;
    } else if (!this.version.equals(other.version))
      return false;
    if (this.symbolStatus == null) {
      if (other.symbolStatus != null)
        return false;
    } else if (!this.symbolStatus.equals(other.symbolStatus))
      return false;
    if (this.symbolFilePath == null) {
      if (other.symbolFilePath != null)
        return false;
    } else if (!this.symbolFilePath.equals(other.symbolFilePath))
      return false;
    if (this.dateTimeStamp == null) {
      if (other.dateTimeStamp != null)
        return false;
    } else if (!this.dateTimeStamp.equals(other.dateTimeStamp))
      return false;
    if (this.addressRange == null) {
      if (other.addressRange != null)
        return false;
    } else if (!this.addressRange.equals(other.addressRange))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id== null) ? 0 : this.id.hashCode());
    result = prime * result + ((this.name== null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.path== null) ? 0 : this.path.hashCode());
    result = prime * result + ((this.isOptimized== null) ? 0 : this.isOptimized.hashCode());
    result = prime * result + ((this.isUserCode== null) ? 0 : this.isUserCode.hashCode());
    result = prime * result + ((this.version== null) ? 0 : this.version.hashCode());
    result = prime * result + ((this.symbolStatus== null) ? 0 : this.symbolStatus.hashCode());
    result = prime * result + ((this.symbolFilePath== null) ? 0 : this.symbolFilePath.hashCode());
    result = prime * result + ((this.dateTimeStamp== null) ? 0 : this.dateTimeStamp.hashCode());
    return prime * result + ((this.addressRange== null) ? 0 : this.addressRange.hashCode());
  }
}
