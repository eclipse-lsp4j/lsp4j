package org.eclipse.lsp4j;

import org.eclipse.lsp4j.DynamicRegistrationCapabilities;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class SynchronizationCapabilities extends DynamicRegistrationCapabilities {
  /**
   * The client supports sending will save notifications.
   */
  private Boolean willSave;
  
  /**
   * The client supports sending a will save request and
   * waits for a response providing text edits which will
   * be applied to the document before it is saved.
   */
  private Boolean willSaveWaitUntil;
  
  /**
   * The client supports did save notifications.
   */
  private Boolean didSave;
  
  public SynchronizationCapabilities() {
  }
  
  public SynchronizationCapabilities(final Boolean willSave, final Boolean willSaveWaitUntil, final Boolean didSave) {
    this.willSave = willSave;
    this.willSaveWaitUntil = willSaveWaitUntil;
    this.didSave = didSave;
  }
  
  /**
   * The client supports sending will save notifications.
   */
  @Pure
  public Boolean getWillSave() {
    return this.willSave;
  }
  
  /**
   * The client supports sending will save notifications.
   */
  public void setWillSave(final Boolean willSave) {
    this.willSave = willSave;
  }
  
  /**
   * The client supports sending a will save request and
   * waits for a response providing text edits which will
   * be applied to the document before it is saved.
   */
  @Pure
  public Boolean getWillSaveWaitUntil() {
    return this.willSaveWaitUntil;
  }
  
  /**
   * The client supports sending a will save request and
   * waits for a response providing text edits which will
   * be applied to the document before it is saved.
   */
  public void setWillSaveWaitUntil(final Boolean willSaveWaitUntil) {
    this.willSaveWaitUntil = willSaveWaitUntil;
  }
  
  /**
   * The client supports did save notifications.
   */
  @Pure
  public Boolean getDidSave() {
    return this.didSave;
  }
  
  /**
   * The client supports did save notifications.
   */
  public void setDidSave(final Boolean didSave) {
    this.didSave = didSave;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("willSave", this.willSave);
    b.add("willSaveWaitUntil", this.willSaveWaitUntil);
    b.add("didSave", this.didSave);
    b.add("dynamicRegistration", getDynamicRegistration());
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
    SynchronizationCapabilities other = (SynchronizationCapabilities) obj;
    if (this.willSave == null) {
      if (other.willSave != null)
        return false;
    } else if (!this.willSave.equals(other.willSave))
      return false;
    if (this.willSaveWaitUntil == null) {
      if (other.willSaveWaitUntil != null)
        return false;
    } else if (!this.willSaveWaitUntil.equals(other.willSaveWaitUntil))
      return false;
    if (this.didSave == null) {
      if (other.didSave != null)
        return false;
    } else if (!this.didSave.equals(other.didSave))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.willSave== null) ? 0 : this.willSave.hashCode());
    result = prime * result + ((this.willSaveWaitUntil== null) ? 0 : this.willSaveWaitUntil.hashCode());
    result = prime * result + ((this.didSave== null) ? 0 : this.didSave.hashCode());
    return result;
  }
}
