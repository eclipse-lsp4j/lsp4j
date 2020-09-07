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
import org.eclipse.lsp4j.DocumentFilter;
import org.eclipse.lsp4j.TextDocumentRegistrationOptions;
import org.eclipse.lsp4j.WorkDoneProgressOptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Options to signal work done progress support in server capabilities and TextDocumentRegistrationOptions.
 * It is not present in protocol specification, so it's just "dry" class.
 * 
 * Since 3.15.0
 */
@SuppressWarnings("all")
public abstract class AbstractTextDocumentRegistrationAndWorkDoneProgressOptions extends TextDocumentRegistrationOptions implements WorkDoneProgressOptions {
  private Boolean workDoneProgress;
  
  public AbstractTextDocumentRegistrationAndWorkDoneProgressOptions() {
  }
  
  public AbstractTextDocumentRegistrationAndWorkDoneProgressOptions(final List<DocumentFilter> documentSelector) {
    super(documentSelector);
  }
  
  @Pure
  public Boolean getWorkDoneProgress() {
    return this.workDoneProgress;
  }
  
  public void setWorkDoneProgress(final Boolean workDoneProgress) {
    this.workDoneProgress = workDoneProgress;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("workDoneProgress", this.workDoneProgress);
    b.add("documentSelector", getDocumentSelector());
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
    if (!super.equals(obj))
      return false;
    AbstractTextDocumentRegistrationAndWorkDoneProgressOptions other = (AbstractTextDocumentRegistrationAndWorkDoneProgressOptions) obj;
    if (this.workDoneProgress == null) {
      if (other.workDoneProgress != null)
        return false;
    } else if (!this.workDoneProgress.equals(other.workDoneProgress))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * super.hashCode() + ((this.workDoneProgress== null) ? 0 : this.workDoneProgress.hashCode());
  }
}
