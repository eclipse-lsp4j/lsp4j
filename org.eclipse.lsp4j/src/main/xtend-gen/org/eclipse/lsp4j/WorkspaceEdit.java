package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * A workspace edit represents changes to many resources managed in the workspace.
 */
@SuppressWarnings("all")
public class WorkspaceEdit extends WrappedJsonObject {
  private static WrappedJsonProperty<Map<String, List<TextEdit>>> changesProperty = new WrappedJsonProperty<>("changes", WrappedJsonConverter.mapConverter(WrappedJsonConverter.listConverter(WrappedJsonConverter.objectConverter(TextEdit.class))));
  
  /**
   * Holds changes to existing resources.
   */
  @Pure
  @NonNull
  public Map<String, List<TextEdit>> getChanges() {
    return changesProperty.get(jsonObject);
  }
  
  /**
   * Holds changes to existing resources.
   */
  public void setChanges(@NonNull final Map<String, List<TextEdit>> changes) {
    changesProperty.set(jsonObject, changes);
  }
  
  /**
   * Removes the property changes from the underlying JSON object.
   */
  public Map<String, List<TextEdit>> removeChanges() {
    return changesProperty.remove(jsonObject);
  }
  
  public WorkspaceEdit() {
    super();
  }
  
  public WorkspaceEdit(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public WorkspaceEdit(final Map<String, List<TextEdit>> changes) {
    this.setChanges(changes);
  }
}
