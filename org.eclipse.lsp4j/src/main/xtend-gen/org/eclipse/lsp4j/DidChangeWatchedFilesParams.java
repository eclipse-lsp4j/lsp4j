package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import java.util.List;
import org.eclipse.lsp4j.FileEvent;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The watched files notification is sent from the client to the server when the client detects changes
 * to file watched by the language client.
 */
@SuppressWarnings("all")
public class DidChangeWatchedFilesParams extends WrappedJsonObject {
  private static WrappedJsonProperty<List<FileEvent>> changesProperty = new WrappedJsonProperty<>("changes", WrappedJsonConverter.listConverter(WrappedJsonConverter.objectConverter(FileEvent.class)));
  
  /**
   * The actual file events.
   */
  @Pure
  @NonNull
  public List<FileEvent> getChanges() {
    return changesProperty.get(jsonObject);
  }
  
  /**
   * The actual file events.
   */
  public void setChanges(@NonNull final List<FileEvent> changes) {
    changesProperty.set(jsonObject, changes);
  }
  
  /**
   * Removes the property changes from the underlying JSON object.
   */
  public List<FileEvent> removeChanges() {
    return changesProperty.remove(jsonObject);
  }
  
  public DidChangeWatchedFilesParams() {
    super();
  }
  
  public DidChangeWatchedFilesParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public DidChangeWatchedFilesParams(final List<FileEvent> changes) {
    this.setChanges(changes);
  }
}
