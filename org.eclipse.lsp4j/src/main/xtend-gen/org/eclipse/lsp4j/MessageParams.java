package org.eclipse.lsp4j;

import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The show message notification is sent from a server to a client to ask the client to display a particular message
 * in the user class.
 * 
 * The log message notification is send from the server to the client to ask the client to log a particular message.
 */
@SuppressWarnings("all")
public class MessageParams {
  /**
   * The message type.
   */
  @NonNull
  private MessageType type;
  
  /**
   * The actual message.
   */
  @NonNull
  private String message;
  
  /**
   * The message type.
   */
  @Pure
  @NonNull
  public MessageType getType() {
    return this.type;
  }
  
  /**
   * The message type.
   */
  public void setType(@NonNull final MessageType type) {
    this.type = type;
  }
  
  /**
   * The actual message.
   */
  @Pure
  @NonNull
  public String getMessage() {
    return this.message;
  }
  
  /**
   * The actual message.
   */
  public void setMessage(@NonNull final String message) {
    this.message = message;
  }
  
  public MessageParams() {
    
  }
  
  public MessageParams(final MessageType type, final String message) {
    this.type = type;
    this.message = message;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("type", this.type);
    b.add("message", this.message);
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
    MessageParams other = (MessageParams) obj;
    if (this.type == null) {
      if (other.type != null)
        return false;
    } else if (!this.type.equals(other.type))
      return false;
    if (this.message == null) {
      if (other.message != null)
        return false;
    } else if (!this.message.equals(other.message))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.type== null) ? 0 : this.type.hashCode());
    result = prime * result + ((this.message== null) ? 0 : this.message.hashCode());
    return result;
  }
}
