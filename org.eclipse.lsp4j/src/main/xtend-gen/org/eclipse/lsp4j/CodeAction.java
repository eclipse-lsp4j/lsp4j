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

import com.google.gson.annotations.JsonAdapter;
import java.util.List;
import org.eclipse.lsp4j.CodeActionDisabled;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.json.adapters.JsonElementTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A code action represents a change that can be performed in code, e.g. to fix a problem or
 * to refactor code.
 * 
 * A CodeAction must set either `edit` and/or a `command`. If both are supplied, the `edit` is applied first, then the `command` is executed.
 */
@SuppressWarnings("all")
public class CodeAction {
  /**
   * A short, human-readable, title for this code action.
   */
  @NonNull
  private String title;
  
  /**
   * The kind of the code action.
   * 
   * Used to filter code actions.
   */
  private String kind;
  
  /**
   * The diagnostics that this code action resolves.
   */
  private List<Diagnostic> diagnostics;
  
  /**
   * Marks this as a preferred action. Preferred actions are used by the `auto fix` command and can be targeted
   * by keybindings.
   * 
   * A quick fix should be marked preferred if it properly addresses the underlying error.
   * A refactoring should be marked preferred if it is the most reasonable choice of actions to take.
   * 
   * Since 3.15.0
   */
  private Boolean isPreferred;
  
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
  private CodeActionDisabled disabled;
  
  /**
   * The workspace edit this code action performs.
   */
  private WorkspaceEdit edit;
  
  /**
   * A command this code action executes. If a code action
   * provides a edit and a command, first the edit is
   * executed and then the command.
   */
  private Command command;
  
  /**
   * A data entry field that is preserved on a code action between
   * a `textDocument/codeAction` and a `codeAction/resolve` request.
   * 
   * Since 3.16.0
   */
  @JsonAdapter(JsonElementTypeAdapter.Factory.class)
  private Object data;
  
  public CodeAction() {
  }
  
  public CodeAction(@NonNull final String title) {
    this.title = Preconditions.<String>checkNotNull(title, "title");
  }
  
  /**
   * A short, human-readable, title for this code action.
   */
  @Pure
  @NonNull
  public String getTitle() {
    return this.title;
  }
  
  /**
   * A short, human-readable, title for this code action.
   */
  public void setTitle(@NonNull final String title) {
    this.title = Preconditions.checkNotNull(title, "title");
  }
  
  /**
   * The kind of the code action.
   * 
   * Used to filter code actions.
   */
  @Pure
  public String getKind() {
    return this.kind;
  }
  
  /**
   * The kind of the code action.
   * 
   * Used to filter code actions.
   */
  public void setKind(final String kind) {
    this.kind = kind;
  }
  
  /**
   * The diagnostics that this code action resolves.
   */
  @Pure
  public List<Diagnostic> getDiagnostics() {
    return this.diagnostics;
  }
  
  /**
   * The diagnostics that this code action resolves.
   */
  public void setDiagnostics(final List<Diagnostic> diagnostics) {
    this.diagnostics = diagnostics;
  }
  
  /**
   * Marks this as a preferred action. Preferred actions are used by the `auto fix` command and can be targeted
   * by keybindings.
   * 
   * A quick fix should be marked preferred if it properly addresses the underlying error.
   * A refactoring should be marked preferred if it is the most reasonable choice of actions to take.
   * 
   * Since 3.15.0
   */
  @Pure
  public Boolean getIsPreferred() {
    return this.isPreferred;
  }
  
  /**
   * Marks this as a preferred action. Preferred actions are used by the `auto fix` command and can be targeted
   * by keybindings.
   * 
   * A quick fix should be marked preferred if it properly addresses the underlying error.
   * A refactoring should be marked preferred if it is the most reasonable choice of actions to take.
   * 
   * Since 3.15.0
   */
  public void setIsPreferred(final Boolean isPreferred) {
    this.isPreferred = isPreferred;
  }
  
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
  @Pure
  public CodeActionDisabled getDisabled() {
    return this.disabled;
  }
  
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
  public void setDisabled(final CodeActionDisabled disabled) {
    this.disabled = disabled;
  }
  
  /**
   * The workspace edit this code action performs.
   */
  @Pure
  public WorkspaceEdit getEdit() {
    return this.edit;
  }
  
  /**
   * The workspace edit this code action performs.
   */
  public void setEdit(final WorkspaceEdit edit) {
    this.edit = edit;
  }
  
  /**
   * A command this code action executes. If a code action
   * provides a edit and a command, first the edit is
   * executed and then the command.
   */
  @Pure
  public Command getCommand() {
    return this.command;
  }
  
  /**
   * A command this code action executes. If a code action
   * provides a edit and a command, first the edit is
   * executed and then the command.
   */
  public void setCommand(final Command command) {
    this.command = command;
  }
  
  /**
   * A data entry field that is preserved on a code action between
   * a `textDocument/codeAction` and a `codeAction/resolve` request.
   * 
   * Since 3.16.0
   */
  @Pure
  public Object getData() {
    return this.data;
  }
  
  /**
   * A data entry field that is preserved on a code action between
   * a `textDocument/codeAction` and a `codeAction/resolve` request.
   * 
   * Since 3.16.0
   */
  public void setData(final Object data) {
    this.data = data;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("title", this.title);
    b.add("kind", this.kind);
    b.add("diagnostics", this.diagnostics);
    b.add("isPreferred", this.isPreferred);
    b.add("disabled", this.disabled);
    b.add("edit", this.edit);
    b.add("command", this.command);
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
    CodeAction other = (CodeAction) obj;
    if (this.title == null) {
      if (other.title != null)
        return false;
    } else if (!this.title.equals(other.title))
      return false;
    if (this.kind == null) {
      if (other.kind != null)
        return false;
    } else if (!this.kind.equals(other.kind))
      return false;
    if (this.diagnostics == null) {
      if (other.diagnostics != null)
        return false;
    } else if (!this.diagnostics.equals(other.diagnostics))
      return false;
    if (this.isPreferred == null) {
      if (other.isPreferred != null)
        return false;
    } else if (!this.isPreferred.equals(other.isPreferred))
      return false;
    if (this.disabled == null) {
      if (other.disabled != null)
        return false;
    } else if (!this.disabled.equals(other.disabled))
      return false;
    if (this.edit == null) {
      if (other.edit != null)
        return false;
    } else if (!this.edit.equals(other.edit))
      return false;
    if (this.command == null) {
      if (other.command != null)
        return false;
    } else if (!this.command.equals(other.command))
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
    result = prime * result + ((this.title== null) ? 0 : this.title.hashCode());
    result = prime * result + ((this.kind== null) ? 0 : this.kind.hashCode());
    result = prime * result + ((this.diagnostics== null) ? 0 : this.diagnostics.hashCode());
    result = prime * result + ((this.isPreferred== null) ? 0 : this.isPreferred.hashCode());
    result = prime * result + ((this.disabled== null) ? 0 : this.disabled.hashCode());
    result = prime * result + ((this.edit== null) ? 0 : this.edit.hashCode());
    result = prime * result + ((this.command== null) ? 0 : this.command.hashCode());
    return prime * result + ((this.data== null) ? 0 : this.data.hashCode());
  }
}
