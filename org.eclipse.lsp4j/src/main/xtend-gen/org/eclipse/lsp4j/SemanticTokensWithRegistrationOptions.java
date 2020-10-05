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

import com.google.common.annotations.Beta;
import java.util.List;
import org.eclipse.lsp4j.AbstractWorkDoneProgressOptions;
import org.eclipse.lsp4j.DocumentFilter;
import org.eclipse.lsp4j.SemanticTokensLegend;
import org.eclipse.lsp4j.SemanticTokensServerFull;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@Beta
@SuppressWarnings("all")
public class SemanticTokensWithRegistrationOptions extends AbstractWorkDoneProgressOptions {
  /**
   * The legend used by the server
   */
  @NonNull
  private SemanticTokensLegend legend;
  
  /**
   * Server supports providing semantic tokens for a specific range
   * of a document.
   */
  private Either<Boolean, Object> range;
  
  /**
   * Server supports providing semantic tokens for a full document.
   */
  private Either<Boolean, SemanticTokensServerFull> full;
  
  /**
   * A document selector to identify the scope of the registration. If set to null
   * the document selector provided on the client side will be used.
   */
  private List<DocumentFilter> documentSelector;
  
  public SemanticTokensWithRegistrationOptions(@NonNull final SemanticTokensLegend legend) {
    this.legend = Preconditions.<SemanticTokensLegend>checkNotNull(legend, "legend");
  }
  
  public SemanticTokensWithRegistrationOptions(@NonNull final SemanticTokensLegend legend, final Boolean full) {
    this(legend);
    this.setFull(full);
  }
  
  public SemanticTokensWithRegistrationOptions(@NonNull final SemanticTokensLegend legend, final SemanticTokensServerFull full) {
    this(legend);
    this.setFull(full);
  }
  
  public SemanticTokensWithRegistrationOptions(@NonNull final SemanticTokensLegend legend, final Boolean full, final Boolean range) {
    this(legend);
    this.setFull(full);
    this.setRange(range);
  }
  
  public SemanticTokensWithRegistrationOptions(@NonNull final SemanticTokensLegend legend, final SemanticTokensServerFull full, final Boolean range) {
    this(legend);
    this.setFull(full);
    this.setRange(range);
  }
  
  public SemanticTokensWithRegistrationOptions(@NonNull final SemanticTokensLegend legend, final SemanticTokensServerFull full, final Boolean range, final List<DocumentFilter> documentSelector) {
    this(legend);
    this.setFull(full);
    this.setRange(range);
    this.documentSelector = documentSelector;
  }
  
  /**
   * The legend used by the server
   */
  @Pure
  @NonNull
  public SemanticTokensLegend getLegend() {
    return this.legend;
  }
  
  /**
   * The legend used by the server
   */
  public void setLegend(@NonNull final SemanticTokensLegend legend) {
    this.legend = Preconditions.checkNotNull(legend, "legend");
  }
  
  /**
   * Server supports providing semantic tokens for a specific range
   * of a document.
   */
  @Pure
  public Either<Boolean, Object> getRange() {
    return this.range;
  }
  
  /**
   * Server supports providing semantic tokens for a specific range
   * of a document.
   */
  public void setRange(final Either<Boolean, Object> range) {
    this.range = range;
  }
  
  public void setRange(final Boolean range) {
    if (range == null) {
      this.range = null;
      return;
    }
    this.range = Either.forLeft(range);
  }
  
  public void setRange(final Object range) {
    if (range == null) {
      this.range = null;
      return;
    }
    this.range = Either.forRight(range);
  }
  
  /**
   * Server supports providing semantic tokens for a full document.
   */
  @Pure
  public Either<Boolean, SemanticTokensServerFull> getFull() {
    return this.full;
  }
  
  /**
   * Server supports providing semantic tokens for a full document.
   */
  public void setFull(final Either<Boolean, SemanticTokensServerFull> full) {
    this.full = full;
  }
  
  public void setFull(final Boolean full) {
    if (full == null) {
      this.full = null;
      return;
    }
    this.full = Either.forLeft(full);
  }
  
  public void setFull(final SemanticTokensServerFull full) {
    if (full == null) {
      this.full = null;
      return;
    }
    this.full = Either.forRight(full);
  }
  
  /**
   * A document selector to identify the scope of the registration. If set to null
   * the document selector provided on the client side will be used.
   */
  @Pure
  public List<DocumentFilter> getDocumentSelector() {
    return this.documentSelector;
  }
  
  /**
   * A document selector to identify the scope of the registration. If set to null
   * the document selector provided on the client side will be used.
   */
  public void setDocumentSelector(final List<DocumentFilter> documentSelector) {
    this.documentSelector = documentSelector;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("legend", this.legend);
    b.add("range", this.range);
    b.add("full", this.full);
    b.add("documentSelector", this.documentSelector);
    b.add("workDoneProgress", getWorkDoneProgress());
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
    SemanticTokensWithRegistrationOptions other = (SemanticTokensWithRegistrationOptions) obj;
    if (this.legend == null) {
      if (other.legend != null)
        return false;
    } else if (!this.legend.equals(other.legend))
      return false;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    if (this.full == null) {
      if (other.full != null)
        return false;
    } else if (!this.full.equals(other.full))
      return false;
    if (this.documentSelector == null) {
      if (other.documentSelector != null)
        return false;
    } else if (!this.documentSelector.equals(other.documentSelector))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.legend== null) ? 0 : this.legend.hashCode());
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    result = prime * result + ((this.full== null) ? 0 : this.full.hashCode());
    return prime * result + ((this.documentSelector== null) ? 0 : this.documentSelector.hashCode());
  }
}
