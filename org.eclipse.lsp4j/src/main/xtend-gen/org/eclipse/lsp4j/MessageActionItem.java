package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The show message request is sent from a server to a client to ask the client to display a particular message in the
 * user class. In addition to the show message notification the request allows to pass actions and to wait for an
 * answer from the client.
 */
@SuppressWarnings("all")
public class MessageActionItem extends WrappedJsonObject {
  private static WrappedJsonProperty<String> titleProperty = new WrappedJsonProperty<>("title", WrappedJsonConverter.stringConverter);
  
  /**
   * A short title like 'Retry', 'Open Log' etc.
   */
  @Pure
  @NonNull
  public String getTitle() {
    return titleProperty.get(jsonObject);
  }
  
  /**
   * A short title like 'Retry', 'Open Log' etc.
   */
  public void setTitle(@NonNull final String title) {
    titleProperty.set(jsonObject, title);
  }
  
  /**
   * Removes the property title from the underlying JSON object.
   */
  public String removeTitle() {
    return titleProperty.remove(jsonObject);
  }
  
  public MessageActionItem() {
    super();
  }
  
  public MessageActionItem(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public MessageActionItem(final String title) {
    this.setTitle(title);
  }
}
