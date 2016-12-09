package org.eclipse.lsp4j;

import java.util.List;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
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
  private String documentation;
  
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
   * An edit which is applied to a document when selecting this completion. When an edit is provided the value of
   * insertText is ignored.
   */
  private TextEdit textEdit;
  
  /**
   * An optional array of additional text edits that are applied when
   * selecting this completion. Edits must not overlap with the main edit
   * nor with themselves.
   */
  private List<TextEdit> additionalTextEdits;
  
  /**
   * An optional command that is executed *after* inserting this completion. *Note* that
   * additional modifications to the current document should be described with the
   * additionalTextEdits-property.
   */
  private Command command;
  
  /**
   * An data entry field that is preserved on a completion item between a completion and a completion resolve request.
   */
  private Object data;
  
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
  public String getDocumentation() {
    return this.documentation;
  }
  
  /**
   * A human-readable string that represents a doc-comment.
   */
  public void setDocumentation(final String documentation) {
    this.documentation = documentation;
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
   * An edit which is applied to a document when selecting this completion. When an edit is provided the value of
   * insertText is ignored.
   */
  @Pure
  public TextEdit getTextEdit() {
    return this.textEdit;
  }
  
  /**
   * An edit which is applied to a document when selecting this completion. When an edit is provided the value of
   * insertText is ignored.
   */
  public void setTextEdit(final TextEdit textEdit) {
    this.textEdit = textEdit;
  }
  
  /**
   * An optional array of additional text edits that are applied when
   * selecting this completion. Edits must not overlap with the main edit
   * nor with themselves.
   */
  @Pure
  public List<TextEdit> getAdditionalTextEdits() {
    return this.additionalTextEdits;
  }
  
  /**
   * An optional array of additional text edits that are applied when
   * selecting this completion. Edits must not overlap with the main edit
   * nor with themselves.
   */
  public void setAdditionalTextEdits(final List<TextEdit> additionalTextEdits) {
    this.additionalTextEdits = additionalTextEdits;
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
  
  public CompletionItem() {
    
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("label", this.label);
    b.add("kind", this.kind);
    b.add("detail", this.detail);
    b.add("documentation", this.documentation);
    b.add("sortText", this.sortText);
    b.add("filterText", this.filterText);
    b.add("insertText", this.insertText);
    b.add("textEdit", this.textEdit);
    b.add("additionalTextEdits", this.additionalTextEdits);
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
    if (!super.equals(obj))
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
    int result = super.hashCode();
    result = prime * result + ((this.label== null) ? 0 : this.label.hashCode());
    result = prime * result + ((this.kind== null) ? 0 : this.kind.hashCode());
    result = prime * result + ((this.detail== null) ? 0 : this.detail.hashCode());
    result = prime * result + ((this.documentation== null) ? 0 : this.documentation.hashCode());
    result = prime * result + ((this.sortText== null) ? 0 : this.sortText.hashCode());
    result = prime * result + ((this.filterText== null) ? 0 : this.filterText.hashCode());
    result = prime * result + ((this.insertText== null) ? 0 : this.insertText.hashCode());
    result = prime * result + ((this.textEdit== null) ? 0 : this.textEdit.hashCode());
    result = prime * result + ((this.additionalTextEdits== null) ? 0 : this.additionalTextEdits.hashCode());
    result = prime * result + ((this.command== null) ? 0 : this.command.hashCode());
    result = prime * result + ((this.data== null) ? 0 : this.data.hashCode());
    return result;
  }
}
