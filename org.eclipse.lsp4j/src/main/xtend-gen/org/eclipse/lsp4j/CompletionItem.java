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
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.InsertTextFormat;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.jsonrpc.json.adapters.JsonElementTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The Completion request is sent from the client to the server to compute completion items at a given cursor position.
 * Completion items are presented in the IntelliSense user class. If computing complete completion items is expensive
 * servers can additional provide a handler for the resolve completion item request. This request is send when a
 * completion item is selected in the user class.
 */
@SuppressWarnings("all")
public class CompletionItem {
  /**
   * The label of this completion item. By default also the text that is inserted when selecting this completion.
   */
  @NonNull
  private String label;
  
  /**
   * The kind of this completion item. Based of the kind an icon is chosen by the editor.
   */
  private CompletionItemKind kind;
  
  /**
   * A human-readable string with additional information about this item, like type or symbol information.
   */
  private String detail;
  
  /**
   * A human-readable string that represents a doc-comment.
   */
  private Either<String, MarkupContent> documentation;
  
  /**
   * Indicates if this item is deprecated.
   */
  private Boolean deprecated;
  
  /**
   * Select this item when showing.
   * 
   * *Note* that only one completion item can be selected and that the
   * tool / client decides which item that is. The rule is that the *first
   * item of those that match best is selected.
   */
  private Boolean preselect;
  
  /**
   * A string that shoud be used when comparing this item with other items. When `falsy` the label is used.
   */
  private String sortText;
  
  /**
   * A string that should be used when filtering a set of completion items. When `falsy` the label is used.
   */
  private String filterText;
  
  /**
   * A string that should be inserted a document when selecting this completion. When `falsy` the label is used.
   */
  private String insertText;
  
  /**
   * The format of the insert text. The format applies to both the `insertText` property
   * and the `newText` property of a provided `textEdit`.
   */
  private InsertTextFormat insertTextFormat;
  
  /**
   * An edit which is applied to a document when selecting this completion. When an edit is provided the value of
   * `insertText` is ignored.
   * 
   * *Note:* The range of the edit must be a single line range and it must contain the position at which completion
   * has been requested.
   */
  private TextEdit textEdit;
  
  /**
   * An optional array of additional text edits that are applied when
   * selecting this completion. Edits must not overlap (including the same insert position)
   * with the main edit nor with themselves.
   * 
   * Additional text edits should be used to change text unrelated to the current cursor position
   * (for example adding an import statement at the top of the file if the completion item will
   * insert an unqualified type).
   */
  private List<TextEdit> additionalTextEdits;
  
  /**
   * An optional set of characters that when pressed while this completion is active will accept it first and
   * then type that character. *Note* that all commit characters should have `length=1` and that superfluous
   * characters will be ignored.
   */
  private List<String> commitCharacters;
  
  /**
   * An optional command that is executed *after* inserting this completion. *Note* that
   * additional modifications to the current document should be described with the
   * additionalTextEdits-property.
   */
  private Command command;
  
  /**
   * An data entry field that is preserved on a completion item between a completion and a completion resolve request.
   */
  @JsonAdapter(JsonElementTypeAdapter.Factory.class)
  private Object data;
  
  public CompletionItem() {
  }
  
  public CompletionItem(@NonNull final String label) {
    this.label = Preconditions.<String>checkNotNull(label, "label");
  }
  
  /**
   * The label of this completion item. By default also the text that is inserted when selecting this completion.
   */
  @Pure
  @NonNull
  public String getLabel() {
    return this.label;
  }
  
  /**
   * The label of this completion item. By default also the text that is inserted when selecting this completion.
   */
  public void setLabel(@NonNull final String label) {
    if (label == null) {
      throw new IllegalArgumentException("Property must not be null: label");
    }
    this.label = label;
  }
  
  /**
   * The kind of this completion item. Based of the kind an icon is chosen by the editor.
   */
  @Pure
  public CompletionItemKind getKind() {
    return this.kind;
  }
  
  /**
   * The kind of this completion item. Based of the kind an icon is chosen by the editor.
   */
  public void setKind(final CompletionItemKind kind) {
    this.kind = kind;
  }
  
  /**
   * A human-readable string with additional information about this item, like type or symbol information.
   */
  @Pure
  public String getDetail() {
    return this.detail;
  }
  
  /**
   * A human-readable string with additional information about this item, like type or symbol information.
   */
  public void setDetail(final String detail) {
    this.detail = detail;
  }
  
  /**
   * A human-readable string that represents a doc-comment.
   */
  @Pure
  public Either<String, MarkupContent> getDocumentation() {
    return this.documentation;
  }
  
  /**
   * A human-readable string that represents a doc-comment.
   */
  public void setDocumentation(final Either<String, MarkupContent> documentation) {
    this.documentation = documentation;
  }
  
  public void setDocumentation(final String documentation) {
    if (documentation == null) {
      this.documentation = null;
      return;
    }
    this.documentation = Either.forLeft(documentation);
  }
  
  public void setDocumentation(final MarkupContent documentation) {
    if (documentation == null) {
      this.documentation = null;
      return;
    }
    this.documentation = Either.forRight(documentation);
  }
  
  /**
   * Indicates if this item is deprecated.
   */
  @Pure
  public Boolean getDeprecated() {
    return this.deprecated;
  }
  
  /**
   * Indicates if this item is deprecated.
   */
  public void setDeprecated(final Boolean deprecated) {
    this.deprecated = deprecated;
  }
  
  /**
   * Select this item when showing.
   * 
   * *Note* that only one completion item can be selected and that the
   * tool / client decides which item that is. The rule is that the *first
   * item of those that match best is selected.
   */
  @Pure
  public Boolean getPreselect() {
    return this.preselect;
  }
  
  /**
   * Select this item when showing.
   * 
   * *Note* that only one completion item can be selected and that the
   * tool / client decides which item that is. The rule is that the *first
   * item of those that match best is selected.
   */
  public void setPreselect(final Boolean preselect) {
    this.preselect = preselect;
  }
  
  /**
   * A string that shoud be used when comparing this item with other items. When `falsy` the label is used.
   */
  @Pure
  public String getSortText() {
    return this.sortText;
  }
  
  /**
   * A string that shoud be used when comparing this item with other items. When `falsy` the label is used.
   */
  public void setSortText(final String sortText) {
    this.sortText = sortText;
  }
  
  /**
   * A string that should be used when filtering a set of completion items. When `falsy` the label is used.
   */
  @Pure
  public String getFilterText() {
    return this.filterText;
  }
  
  /**
   * A string that should be used when filtering a set of completion items. When `falsy` the label is used.
   */
  public void setFilterText(final String filterText) {
    this.filterText = filterText;
  }
  
  /**
   * A string that should be inserted a document when selecting this completion. When `falsy` the label is used.
   */
  @Pure
  public String getInsertText() {
    return this.insertText;
  }
  
  /**
   * A string that should be inserted a document when selecting this completion. When `falsy` the label is used.
   */
  public void setInsertText(final String insertText) {
    this.insertText = insertText;
  }
  
  /**
   * The format of the insert text. The format applies to both the `insertText` property
   * and the `newText` property of a provided `textEdit`.
   */
  @Pure
  public InsertTextFormat getInsertTextFormat() {
    return this.insertTextFormat;
  }
  
  /**
   * The format of the insert text. The format applies to both the `insertText` property
   * and the `newText` property of a provided `textEdit`.
   */
  public void setInsertTextFormat(final InsertTextFormat insertTextFormat) {
    this.insertTextFormat = insertTextFormat;
  }
  
  /**
   * An edit which is applied to a document when selecting this completion. When an edit is provided the value of
   * `insertText` is ignored.
   * 
   * *Note:* The range of the edit must be a single line range and it must contain the position at which completion
   * has been requested.
   */
  @Pure
  public TextEdit getTextEdit() {
    return this.textEdit;
  }
  
  /**
   * An edit which is applied to a document when selecting this completion. When an edit is provided the value of
   * `insertText` is ignored.
   * 
   * *Note:* The range of the edit must be a single line range and it must contain the position at which completion
   * has been requested.
   */
  public void setTextEdit(final TextEdit textEdit) {
    this.textEdit = textEdit;
  }
  
  /**
   * An optional array of additional text edits that are applied when
   * selecting this completion. Edits must not overlap (including the same insert position)
   * with the main edit nor with themselves.
   * 
   * Additional text edits should be used to change text unrelated to the current cursor position
   * (for example adding an import statement at the top of the file if the completion item will
   * insert an unqualified type).
   */
  @Pure
  public List<TextEdit> getAdditionalTextEdits() {
    return this.additionalTextEdits;
  }
  
  /**
   * An optional array of additional text edits that are applied when
   * selecting this completion. Edits must not overlap (including the same insert position)
   * with the main edit nor with themselves.
   * 
   * Additional text edits should be used to change text unrelated to the current cursor position
   * (for example adding an import statement at the top of the file if the completion item will
   * insert an unqualified type).
   */
  public void setAdditionalTextEdits(final List<TextEdit> additionalTextEdits) {
    this.additionalTextEdits = additionalTextEdits;
  }
  
  /**
   * An optional set of characters that when pressed while this completion is active will accept it first and
   * then type that character. *Note* that all commit characters should have `length=1` and that superfluous
   * characters will be ignored.
   */
  @Pure
  public List<String> getCommitCharacters() {
    return this.commitCharacters;
  }
  
  /**
   * An optional set of characters that when pressed while this completion is active will accept it first and
   * then type that character. *Note* that all commit characters should have `length=1` and that superfluous
   * characters will be ignored.
   */
  public void setCommitCharacters(final List<String> commitCharacters) {
    this.commitCharacters = commitCharacters;
  }
  
  /**
   * An optional command that is executed *after* inserting this completion. *Note* that
   * additional modifications to the current document should be described with the
   * additionalTextEdits-property.
   */
  @Pure
  public Command getCommand() {
    return this.command;
  }
  
  /**
   * An optional command that is executed *after* inserting this completion. *Note* that
   * additional modifications to the current document should be described with the
   * additionalTextEdits-property.
   */
  public void setCommand(final Command command) {
    this.command = command;
  }
  
  /**
   * An data entry field that is preserved on a completion item between a completion and a completion resolve request.
   */
  @Pure
  public Object getData() {
    return this.data;
  }
  
  /**
   * An data entry field that is preserved on a completion item between a completion and a completion resolve request.
   */
  public void setData(final Object data) {
    this.data = data;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("label", this.label);
    b.add("kind", this.kind);
    b.add("detail", this.detail);
    b.add("documentation", this.documentation);
    b.add("deprecated", this.deprecated);
    b.add("preselect", this.preselect);
    b.add("sortText", this.sortText);
    b.add("filterText", this.filterText);
    b.add("insertText", this.insertText);
    b.add("insertTextFormat", this.insertTextFormat);
    b.add("textEdit", this.textEdit);
    b.add("additionalTextEdits", this.additionalTextEdits);
    b.add("commitCharacters", this.commitCharacters);
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
    CompletionItem other = (CompletionItem) obj;
    if (this.label == null) {
      if (other.label != null)
        return false;
    } else if (!this.label.equals(other.label))
      return false;
    if (this.kind == null) {
      if (other.kind != null)
        return false;
    } else if (!this.kind.equals(other.kind))
      return false;
    if (this.detail == null) {
      if (other.detail != null)
        return false;
    } else if (!this.detail.equals(other.detail))
      return false;
    if (this.documentation == null) {
      if (other.documentation != null)
        return false;
    } else if (!this.documentation.equals(other.documentation))
      return false;
    if (this.deprecated == null) {
      if (other.deprecated != null)
        return false;
    } else if (!this.deprecated.equals(other.deprecated))
      return false;
    if (this.preselect == null) {
      if (other.preselect != null)
        return false;
    } else if (!this.preselect.equals(other.preselect))
      return false;
    if (this.sortText == null) {
      if (other.sortText != null)
        return false;
    } else if (!this.sortText.equals(other.sortText))
      return false;
    if (this.filterText == null) {
      if (other.filterText != null)
        return false;
    } else if (!this.filterText.equals(other.filterText))
      return false;
    if (this.insertText == null) {
      if (other.insertText != null)
        return false;
    } else if (!this.insertText.equals(other.insertText))
      return false;
    if (this.insertTextFormat == null) {
      if (other.insertTextFormat != null)
        return false;
    } else if (!this.insertTextFormat.equals(other.insertTextFormat))
      return false;
    if (this.textEdit == null) {
      if (other.textEdit != null)
        return false;
    } else if (!this.textEdit.equals(other.textEdit))
      return false;
    if (this.additionalTextEdits == null) {
      if (other.additionalTextEdits != null)
        return false;
    } else if (!this.additionalTextEdits.equals(other.additionalTextEdits))
      return false;
    if (this.commitCharacters == null) {
      if (other.commitCharacters != null)
        return false;
    } else if (!this.commitCharacters.equals(other.commitCharacters))
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
    result = prime * result + ((this.label== null) ? 0 : this.label.hashCode());
    result = prime * result + ((this.kind== null) ? 0 : this.kind.hashCode());
    result = prime * result + ((this.detail== null) ? 0 : this.detail.hashCode());
    result = prime * result + ((this.documentation== null) ? 0 : this.documentation.hashCode());
    result = prime * result + ((this.deprecated== null) ? 0 : this.deprecated.hashCode());
    result = prime * result + ((this.preselect== null) ? 0 : this.preselect.hashCode());
    result = prime * result + ((this.sortText== null) ? 0 : this.sortText.hashCode());
    result = prime * result + ((this.filterText== null) ? 0 : this.filterText.hashCode());
    result = prime * result + ((this.insertText== null) ? 0 : this.insertText.hashCode());
    result = prime * result + ((this.insertTextFormat== null) ? 0 : this.insertTextFormat.hashCode());
    result = prime * result + ((this.textEdit== null) ? 0 : this.textEdit.hashCode());
    result = prime * result + ((this.additionalTextEdits== null) ? 0 : this.additionalTextEdits.hashCode());
    result = prime * result + ((this.commitCharacters== null) ? 0 : this.commitCharacters.hashCode());
    result = prime * result + ((this.command== null) ? 0 : this.command.hashCode());
    return prime * result + ((this.data== null) ? 0 : this.data.hashCode());
  }
}
