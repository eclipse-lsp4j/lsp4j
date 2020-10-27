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
import org.eclipse.lsp4j.debug.ColumnDescriptor;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The ModulesViewDescriptor is the container for all declarative configuration options of a ModuleView.
 * <p>
 * For now it only specifies the columns to be shown in the modules view.
 */
@SuppressWarnings("all")
public class ModulesViewDescriptor {
  @NonNull
  private ColumnDescriptor[] columns;
  
  @Pure
  @NonNull
  public ColumnDescriptor[] getColumns() {
    return this.columns;
  }
  
  public void setColumns(@NonNull final ColumnDescriptor[] columns) {
    this.columns = Preconditions.checkNotNull(columns, "columns");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("columns", this.columns);
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
    ModulesViewDescriptor other = (ModulesViewDescriptor) obj;
    if (this.columns == null) {
      if (other.columns != null)
        return false;
    } else if (!Arrays.deepEquals(this.columns, other.columns))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.columns== null) ? 0 : Arrays.deepHashCode(this.columns));
  }
}
