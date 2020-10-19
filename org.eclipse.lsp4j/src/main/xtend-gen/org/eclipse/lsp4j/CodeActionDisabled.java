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
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Marks that the code action cannot currently be applied.
 * 
 * Clients should follow the following guidelines regarding disabled code actions:
 * 
 *   - Disabled code actions are not shown in automatic [lightbulb](https://code.visualstudio.com/docs/editor/editingevolved#_code-action)
 *     code action menu.
 * 
 *   - Disabled actions are shown as faded out in the code action menu when the user request a more specific type
 *     of code action, such as refactorings.
 * 
 *   - If the user has a [keybinding](https://code.visualstudio.com/docs/editor/refactoring#_keybindings-for-code-actions)
 *     that auto applies a code action and only a disabled code actions are returned, the client should show the user an
 *     error message with `reason` in the editor.
 * 
 * Since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class CodeActionDisabled {
  /**
   * Human readable description of why the code action is currently disabled.
   * 
   * This is displayed in the code actions UI.
   */
  @NonNull
  private String reason;
  
  public CodeActionDisabled() {
  }
  
  public CodeActionDisabled(@NonNull final String reason) {
    this.reason = Preconditions.<String>checkNotNull(reason, "reason");
  }
  
  /**
   * Human readable description of why the code action is currently disabled.
   * 
   * This is displayed in the code actions UI.
   */
  @Pure
  @NonNull
  public String getReason() {
    return this.reason;
  }
  
  /**
   * Human readable description of why the code action is currently disabled.
   * 
   * This is displayed in the code actions UI.
   */
  public void setReason(@NonNull final String reason) {
    this.reason = Preconditions.checkNotNull(reason, "reason");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("reason", this.reason);
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
    CodeActionDisabled other = (CodeActionDisabled) obj;
    if (this.reason == null) {
      if (other.reason != null)
        return false;
    } else if (!this.reason.equals(other.reason))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.reason== null) ? 0 : this.reason.hashCode());
  }
}
