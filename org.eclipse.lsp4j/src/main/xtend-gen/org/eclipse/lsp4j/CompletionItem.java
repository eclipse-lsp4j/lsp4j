package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import java.util.List;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The Completion request is sent from the client to the server to compute completion items at a given cursor position.
 * Completion items are presented in the IntelliSense user class. If computing complete completion items is expensive
 * servers can additional provide a handler for the resolve completion item request. This request is send when a
 * completion item is selected in the user class.
 */
@SuppressWarnings("all")
public class CompletionItem extends WrappedJsonObject {
  private static WrappedJsonProperty<String> labelProperty = new WrappedJsonProperty<>("label", WrappedJsonConverter.stringConverter);
  
  /**
   * The label of this completion item. By default also the text that is inserted when selecting this completion.
   */
  @Pure
  @NonNull
  public String getLabel() {
    return labelProperty.get(jsonObject);
  }
  
  /**
   * The label of this completion item. By default also the text that is inserted when selecting this completion.
   */
  public void setLabel(@NonNull final String label) {
    labelProperty.set(jsonObject, label);
  }
  
  /**
   * Removes the property label from the underlying JSON object.
   */
  public String removeLabel() {
    return labelProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<CompletionItemKind> kindProperty = new WrappedJsonProperty<>("kind", WrappedJsonConverter.enumConverter(CompletionItemKind.class));
  
  /**
   * The kind of this completion item. Based of the kind an icon is chosen by the editor.
   */
  @Pure
  public CompletionItemKind getKind() {
    return kindProperty.get(jsonObject);
  }
  
  /**
   * The kind of this completion item. Based of the kind an icon is chosen by the editor.
   */
  public void setKind(final CompletionItemKind kind) {
    kindProperty.set(jsonObject, kind);
  }
  
  /**
   * Removes the property kind from the underlying JSON object.
   */
  public CompletionItemKind removeKind() {
    return kindProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> detailProperty = new WrappedJsonProperty<>("detail", WrappedJsonConverter.stringConverter);
  
  /**
   * A human-readable string with additional information about this item, like type or symbol information.
   */
  @Pure
  public String getDetail() {
    return detailProperty.get(jsonObject);
  }
  
  /**
   * A human-readable string with additional information about this item, like type or symbol information.
   */
  public void setDetail(final String detail) {
    detailProperty.set(jsonObject, detail);
  }
  
  /**
   * Removes the property detail from the underlying JSON object.
   */
  public String removeDetail() {
    return detailProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> documentationProperty = new WrappedJsonProperty<>("documentation", WrappedJsonConverter.stringConverter);
  
  /**
   * A human-readable string that represents a doc-comment.
   */
  @Pure
  public String getDocumentation() {
    return documentationProperty.get(jsonObject);
  }
  
  /**
   * A human-readable string that represents a doc-comment.
   */
  public void setDocumentation(final String documentation) {
    documentationProperty.set(jsonObject, documentation);
  }
  
  /**
   * Removes the property documentation from the underlying JSON object.
   */
  public String removeDocumentation() {
    return documentationProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> sortTextProperty = new WrappedJsonProperty<>("sortText", WrappedJsonConverter.stringConverter);
  
  /**
   * A string that shoud be used when comparing this item with other items. When `falsy` the label is used.
   */
  @Pure
  public String getSortText() {
    return sortTextProperty.get(jsonObject);
  }
  
  /**
   * A string that shoud be used when comparing this item with other items. When `falsy` the label is used.
   */
  public void setSortText(final String sortText) {
    sortTextProperty.set(jsonObject, sortText);
  }
  
  /**
   * Removes the property sortText from the underlying JSON object.
   */
  public String removeSortText() {
    return sortTextProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> filterTextProperty = new WrappedJsonProperty<>("filterText", WrappedJsonConverter.stringConverter);
  
  /**
   * A string that should be used when filtering a set of completion items. When `falsy` the label is used.
   */
  @Pure
  public String getFilterText() {
    return filterTextProperty.get(jsonObject);
  }
  
  /**
   * A string that should be used when filtering a set of completion items. When `falsy` the label is used.
   */
  public void setFilterText(final String filterText) {
    filterTextProperty.set(jsonObject, filterText);
  }
  
  /**
   * Removes the property filterText from the underlying JSON object.
   */
  public String removeFilterText() {
    return filterTextProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> insertTextProperty = new WrappedJsonProperty<>("insertText", WrappedJsonConverter.stringConverter);
  
  /**
   * A string that should be inserted a document when selecting this completion. When `falsy` the label is used.
   */
  @Pure
  public String getInsertText() {
    return insertTextProperty.get(jsonObject);
  }
  
  /**
   * A string that should be inserted a document when selecting this completion. When `falsy` the label is used.
   */
  public void setInsertText(final String insertText) {
    insertTextProperty.set(jsonObject, insertText);
  }
  
  /**
   * Removes the property insertText from the underlying JSON object.
   */
  public String removeInsertText() {
    return insertTextProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<TextEdit> textEditProperty = new WrappedJsonProperty<>("textEdit", WrappedJsonConverter.objectConverter(TextEdit.class));
  
  /**
   * An edit which is applied to a document when selecting this completion. When an edit is provided the value of
   * insertText is ignored.
   */
  @Pure
  public TextEdit getTextEdit() {
    return textEditProperty.get(jsonObject);
  }
  
  /**
   * An edit which is applied to a document when selecting this completion. When an edit is provided the value of
   * insertText is ignored.
   */
  public void setTextEdit(final TextEdit textEdit) {
    textEditProperty.set(jsonObject, textEdit);
  }
  
  /**
   * Removes the property textEdit from the underlying JSON object.
   */
  public TextEdit removeTextEdit() {
    return textEditProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<List<TextEdit>> additionalTextEditsProperty = new WrappedJsonProperty<>("additionalTextEdits", WrappedJsonConverter.listConverter(WrappedJsonConverter.objectConverter(TextEdit.class)));
  
  /**
   * An optional array of additional text edits that are applied when
   * selecting this completion. Edits must not overlap with the main edit
   * nor with themselves.
   */
  @Pure
  public List<TextEdit> getAdditionalTextEdits() {
    return additionalTextEditsProperty.get(jsonObject);
  }
  
  /**
   * An optional array of additional text edits that are applied when
   * selecting this completion. Edits must not overlap with the main edit
   * nor with themselves.
   */
  public void setAdditionalTextEdits(final List<TextEdit> additionalTextEdits) {
    additionalTextEditsProperty.set(jsonObject, additionalTextEdits);
  }
  
  /**
   * Removes the property additionalTextEdits from the underlying JSON object.
   */
  public List<TextEdit> removeAdditionalTextEdits() {
    return additionalTextEditsProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Command> commandProperty = new WrappedJsonProperty<>("command", WrappedJsonConverter.objectConverter(Command.class));
  
  /**
   * An optional command that is executed *after* inserting this completion. *Note* that
   * additional modifications to the current document should be described with the
   * additionalTextEdits-property.
   */
  @Pure
  public Command getCommand() {
    return commandProperty.get(jsonObject);
  }
  
  /**
   * An optional command that is executed *after* inserting this completion. *Note* that
   * additional modifications to the current document should be described with the
   * additionalTextEdits-property.
   */
  public void setCommand(final Command command) {
    commandProperty.set(jsonObject, command);
  }
  
  /**
   * Removes the property command from the underlying JSON object.
   */
  public Command removeCommand() {
    return commandProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Object> dataProperty = new WrappedJsonProperty<>("data", WrappedJsonConverter.noConverter);
  
  /**
   * An data entry field that is preserved on a completion item between a completion and a completion resolve request.
   */
  @Pure
  public Object getData() {
    return dataProperty.get(jsonObject);
  }
  
  /**
   * An data entry field that is preserved on a completion item between a completion and a completion resolve request.
   */
  public void setData(final Object data) {
    dataProperty.set(jsonObject, data);
  }
  
  /**
   * Removes the property data from the underlying JSON object.
   */
  public Object removeData() {
    return dataProperty.remove(jsonObject);
  }
  
  public CompletionItem() {
    super();
  }
  
  public CompletionItem(final JsonObject jsonObject) {
    super(jsonObject);
  }
}
