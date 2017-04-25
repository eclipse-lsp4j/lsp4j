package org.eclipse.lsp4j;

import java.util.List;
import org.eclipse.lsp4j.TextDocumentRegistrationOptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class DocumentOnTypeFormattingRegistrationOptions extends TextDocumentRegistrationOptions {
  /**
   * A character on which formatting should be triggered, like `}`.
   */
  private String firstTriggerCharacter;
  
  /**
   * More trigger characters.
   */
  private List<String> moreTriggerCharacter;
  
  public DocumentOnTypeFormattingRegistrationOptions() {
  }
  
  public DocumentOnTypeFormattingRegistrationOptions(final String firstTriggerCharacter, final List<String> moreTriggerCharacter) {
    this.firstTriggerCharacter = firstTriggerCharacter;
    this.moreTriggerCharacter = moreTriggerCharacter;
  }
  
  /**
   * A character on which formatting should be triggered, like `}`.
   */
  @Pure
  public String getFirstTriggerCharacter() {
    return this.firstTriggerCharacter;
  }
  
  /**
   * A character on which formatting should be triggered, like `}`.
   */
  public void setFirstTriggerCharacter(final String firstTriggerCharacter) {
    this.firstTriggerCharacter = firstTriggerCharacter;
  }
  
  /**
   * More trigger characters.
   */
  @Pure
  public List<String> getMoreTriggerCharacter() {
    return this.moreTriggerCharacter;
  }
  
  /**
   * More trigger characters.
   */
  public void setMoreTriggerCharacter(final List<String> moreTriggerCharacter) {
    this.moreTriggerCharacter = moreTriggerCharacter;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("firstTriggerCharacter", this.firstTriggerCharacter);
    b.add("moreTriggerCharacter", this.moreTriggerCharacter);
    b.add("documentSelector", getDocumentSelector());
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
    DocumentOnTypeFormattingRegistrationOptions other = (DocumentOnTypeFormattingRegistrationOptions) obj;
    if (this.firstTriggerCharacter == null) {
      if (other.firstTriggerCharacter != null)
        return false;
    } else if (!this.firstTriggerCharacter.equals(other.firstTriggerCharacter))
      return false;
    if (this.moreTriggerCharacter == null) {
      if (other.moreTriggerCharacter != null)
        return false;
    } else if (!this.moreTriggerCharacter.equals(other.moreTriggerCharacter))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.firstTriggerCharacter== null) ? 0 : this.firstTriggerCharacter.hashCode());
    result = prime * result + ((this.moreTriggerCharacter== null) ? 0 : this.moreTriggerCharacter.hashCode());
    return result;
  }
}
