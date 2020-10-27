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

import java.util.Arrays;
import org.eclipse.lsp4j.debug.Checksum;
import org.eclipse.lsp4j.debug.SourcePresentationHint;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A Source is a descriptor for source code.
 * <p>
 * It is returned from the debug adapter as part of a StackFrame and it is used by clients when specifying
 * breakpoints.
 */
@SuppressWarnings("all")
public class Source {
  /**
   * The short name of the source. Every source returned from the debug adapter has a name.
   * <p>
   * When sending a source to the debug adapter this name is optional.
   * <p>
   * This is an optional property.
   */
  private String name;
  
  /**
   * The path of the source to be shown in the UI.
   * <p>
   * It is only used to locate and load the content of the source if no sourceReference is specified (or its value
   * is 0).
   * <p>
   * This is an optional property.
   */
  private String path;
  
  /**
   * If sourceReference > 0 the contents of the source must be retrieved through the SourceRequest (even if a path
   * is specified).
   * <p>
   * A sourceReference is only valid for a session, so it must not be used to persist a source.
   * <p>
   * The value should be less than or equal to 2147483647 (2^31 - 1).
   * <p>
   * This is an optional property.
   */
  private Integer sourceReference;
  
  /**
   * An optional hint for how to present the source in the UI.
   * <p>
   * A value of 'deemphasize' can be used to indicate that the source is not available or that it is skipped on
   * stepping.
   * <p>
   * This is an optional property.
   */
  private SourcePresentationHint presentationHint;
  
  /**
   * The (optional) origin of this source: possible values 'internal module', 'inlined content from source map',
   * etc.
   * <p>
   * This is an optional property.
   */
  private String origin;
  
  /**
   * An optional list of sources that are related to this source. These may be the source that generated this
   * source.
   * <p>
   * This is an optional property.
   */
  private Source[] sources;
  
  /**
   * Optional data that a debug adapter might want to loop through the client.
   * <p>
   * The client should leave the data intact and persist it across sessions. The client should not interpret the
   * data.
   * <p>
   * This is an optional property.
   */
  private Object adapterData;
  
  /**
   * The checksums associated with this file.
   * <p>
   * This is an optional property.
   */
  private Checksum[] checksums;
  
  /**
   * The short name of the source. Every source returned from the debug adapter has a name.
   * <p>
   * When sending a source to the debug adapter this name is optional.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getName() {
    return this.name;
  }
  
  /**
   * The short name of the source. Every source returned from the debug adapter has a name.
   * <p>
   * When sending a source to the debug adapter this name is optional.
   * <p>
   * This is an optional property.
   */
  public void setName(final String name) {
    this.name = name;
  }
  
  /**
   * The path of the source to be shown in the UI.
   * <p>
   * It is only used to locate and load the content of the source if no sourceReference is specified (or its value
   * is 0).
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getPath() {
    return this.path;
  }
  
  /**
   * The path of the source to be shown in the UI.
   * <p>
   * It is only used to locate and load the content of the source if no sourceReference is specified (or its value
   * is 0).
   * <p>
   * This is an optional property.
   */
  public void setPath(final String path) {
    this.path = path;
  }
  
  /**
   * If sourceReference > 0 the contents of the source must be retrieved through the SourceRequest (even if a path
   * is specified).
   * <p>
   * A sourceReference is only valid for a session, so it must not be used to persist a source.
   * <p>
   * The value should be less than or equal to 2147483647 (2^31 - 1).
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getSourceReference() {
    return this.sourceReference;
  }
  
  /**
   * If sourceReference > 0 the contents of the source must be retrieved through the SourceRequest (even if a path
   * is specified).
   * <p>
   * A sourceReference is only valid for a session, so it must not be used to persist a source.
   * <p>
   * The value should be less than or equal to 2147483647 (2^31 - 1).
   * <p>
   * This is an optional property.
   */
  public void setSourceReference(final Integer sourceReference) {
    this.sourceReference = sourceReference;
  }
  
  /**
   * An optional hint for how to present the source in the UI.
   * <p>
   * A value of 'deemphasize' can be used to indicate that the source is not available or that it is skipped on
   * stepping.
   * <p>
   * This is an optional property.
   */
  @Pure
  public SourcePresentationHint getPresentationHint() {
    return this.presentationHint;
  }
  
  /**
   * An optional hint for how to present the source in the UI.
   * <p>
   * A value of 'deemphasize' can be used to indicate that the source is not available or that it is skipped on
   * stepping.
   * <p>
   * This is an optional property.
   */
  public void setPresentationHint(final SourcePresentationHint presentationHint) {
    this.presentationHint = presentationHint;
  }
  
  /**
   * The (optional) origin of this source: possible values 'internal module', 'inlined content from source map',
   * etc.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getOrigin() {
    return this.origin;
  }
  
  /**
   * The (optional) origin of this source: possible values 'internal module', 'inlined content from source map',
   * etc.
   * <p>
   * This is an optional property.
   */
  public void setOrigin(final String origin) {
    this.origin = origin;
  }
  
  /**
   * An optional list of sources that are related to this source. These may be the source that generated this
   * source.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Source[] getSources() {
    return this.sources;
  }
  
  /**
   * An optional list of sources that are related to this source. These may be the source that generated this
   * source.
   * <p>
   * This is an optional property.
   */
  public void setSources(final Source[] sources) {
    this.sources = sources;
  }
  
  /**
   * Optional data that a debug adapter might want to loop through the client.
   * <p>
   * The client should leave the data intact and persist it across sessions. The client should not interpret the
   * data.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Object getAdapterData() {
    return this.adapterData;
  }
  
  /**
   * Optional data that a debug adapter might want to loop through the client.
   * <p>
   * The client should leave the data intact and persist it across sessions. The client should not interpret the
   * data.
   * <p>
   * This is an optional property.
   */
  public void setAdapterData(final Object adapterData) {
    this.adapterData = adapterData;
  }
  
  /**
   * The checksums associated with this file.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Checksum[] getChecksums() {
    return this.checksums;
  }
  
  /**
   * The checksums associated with this file.
   * <p>
   * This is an optional property.
   */
  public void setChecksums(final Checksum[] checksums) {
    this.checksums = checksums;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("name", this.name);
    b.add("path", this.path);
    b.add("sourceReference", this.sourceReference);
    b.add("presentationHint", this.presentationHint);
    b.add("origin", this.origin);
    b.add("sources", this.sources);
    b.add("adapterData", this.adapterData);
    b.add("checksums", this.checksums);
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
    Source other = (Source) obj;
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
    if (this.sourceReference == null) {
      if (other.sourceReference != null)
        return false;
    } else if (!this.sourceReference.equals(other.sourceReference))
      return false;
    if (this.presentationHint == null) {
      if (other.presentationHint != null)
        return false;
    } else if (!this.presentationHint.equals(other.presentationHint))
      return false;
    if (this.origin == null) {
      if (other.origin != null)
        return false;
    } else if (!this.origin.equals(other.origin))
      return false;
    if (this.sources == null) {
      if (other.sources != null)
        return false;
    } else if (!Arrays.deepEquals(this.sources, other.sources))
      return false;
    if (this.adapterData == null) {
      if (other.adapterData != null)
        return false;
    } else if (!this.adapterData.equals(other.adapterData))
      return false;
    if (this.checksums == null) {
      if (other.checksums != null)
        return false;
    } else if (!Arrays.deepEquals(this.checksums, other.checksums))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.name== null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.path== null) ? 0 : this.path.hashCode());
    result = prime * result + ((this.sourceReference== null) ? 0 : this.sourceReference.hashCode());
    result = prime * result + ((this.presentationHint== null) ? 0 : this.presentationHint.hashCode());
    result = prime * result + ((this.origin== null) ? 0 : this.origin.hashCode());
    result = prime * result + ((this.sources== null) ? 0 : Arrays.deepHashCode(this.sources));
    result = prime * result + ((this.adapterData== null) ? 0 : this.adapterData.hashCode());
    return prime * result + ((this.checksums== null) ? 0 : Arrays.deepHashCode(this.checksums));
  }
}
