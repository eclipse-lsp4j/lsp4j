package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import java.util.List;
import org.eclipse.lsp4j.MessageActionItem;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The show message request is sent from a server to a client to ask the client to display a particular message in the
 * user class. In addition to the show message notification the request allows to pass actions and to wait for an
 * answer from the client.
 */
@SuppressWarnings("all")
public class ShowMessageRequestParams extends MessageParams {
  private static WrappedJsonProperty<List<MessageActionItem>> actionsProperty = new WrappedJsonProperty<>("actions", WrappedJsonConverter.listConverter(WrappedJsonConverter.objectConverter(MessageActionItem.class)));
  
  /**
   * The message action items to present.
   */
  @Pure
  public List<MessageActionItem> getActions() {
    return actionsProperty.get(jsonObject);
  }
  
  /**
   * The message action items to present.
   */
  public void setActions(final List<MessageActionItem> actions) {
    actionsProperty.set(jsonObject, actions);
  }
  
  /**
   * Removes the property actions from the underlying JSON object.
   */
  public List<MessageActionItem> removeActions() {
    return actionsProperty.remove(jsonObject);
  }
  
  public ShowMessageRequestParams() {
    super();
  }
  
  public ShowMessageRequestParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public ShowMessageRequestParams(final List<MessageActionItem> actions) {
    this.setActions(actions);
  }
}
