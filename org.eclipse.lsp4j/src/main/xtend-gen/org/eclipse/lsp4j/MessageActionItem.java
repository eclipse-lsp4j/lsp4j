package org.eclipse.lsp4j;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The show message request is sent from a server to a client to ask the client to display a particular message in the
 * user class. In addition to the show message notification the request allows to pass actions and to wait for an
 * answer from the client.
 */
@SuppressWarnings("all")
public class MessageActionItem {
  /**
   * A short title like 'Retry', 'Open Log' etc.
   */
  @NonNull
  private String title;
  
  /**
   * A short title like 'Retry', 'Open Log' etc.
   */
  @Pure
  @NonNull
  public String getTitle() {
    return this.title;
  }
  
  /**
   * A short title like 'Retry', 'Open Log' etc.
   */
  public void setTitle(@NonNull final String title) {
    this.title = title;
  }
  
  public MessageActionItem() {
    
  }
  
  public MessageActionItem(final String title) {
    this.title = title;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("title", this.title);
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
    MessageActionItem other = (MessageActionItem) obj;
    if (this.title == null) {
      if (other.title != null)
        return false;
    } else if (!this.title.equals(other.title))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.title== null) ? 0 : this.title.hashCode());
    return result;
  }
}
