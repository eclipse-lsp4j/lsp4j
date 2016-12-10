package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The show message notification is sent from a server to a client to ask the client to display a particular message
 * in the user class.
 * 
 * The log message notification is send from the server to the client to ask the client to log a particular message.
 */
@SuppressWarnings("all")
public class MessageParams extends WrappedJsonObject {
  private static WrappedJsonProperty<MessageType> typeProperty = new WrappedJsonProperty<>("type", WrappedJsonConverter.enumConverter(MessageType.class));
  
  /**
   * The message type.
   */
  @Pure
  @NonNull
  public MessageType getType() {
    return typeProperty.get(jsonObject);
  }
  
  /**
   * The message type.
   */
  public void setType(@NonNull final MessageType type) {
    typeProperty.set(jsonObject, type);
  }
  
  /**
   * Removes the property type from the underlying JSON object.
   */
  public MessageType removeType() {
    return typeProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> messageProperty = new WrappedJsonProperty<>("message", WrappedJsonConverter.stringConverter);
  
  /**
   * The actual message.
   */
  @Pure
  @NonNull
  public String getMessage() {
    return messageProperty.get(jsonObject);
  }
  
  /**
   * The actual message.
   */
  public void setMessage(@NonNull final String message) {
    messageProperty.set(jsonObject, message);
  }
  
  /**
   * Removes the property message from the underlying JSON object.
   */
  public String removeMessage() {
    return messageProperty.remove(jsonObject);
  }
  
  public MessageParams() {
    super();
  }
  
  public MessageParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public MessageParams(final MessageType type, final String message) {
    this.setType(type);
    this.setMessage(message);
  }
}
