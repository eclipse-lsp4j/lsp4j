package org.eclipse.lsp4j;

import java.util.List;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Represents a collection of completion items to be presented in the editor.
 */
@SuppressWarnings("all")
public class CompletionList {
  /**
   * This list it not complete. Further typing should result in recomputing this list.
   */
  private boolean isIncomplete;
  
  /**
   * The completion items.
   */
  @NonNull
  private List<CompletionItem> items = CollectionLiterals.<CompletionItem>newArrayList();
  
  /**
   * This list it not complete. Further typing should result in recomputing this list.
   */
  @Pure
  public boolean isIncomplete() {
    return this.isIncomplete;
  }
  
  /**
   * This list it not complete. Further typing should result in recomputing this list.
   */
  public void setIsIncomplete(final boolean isIncomplete) {
    this.isIncomplete = isIncomplete;
  }
  
  /**
   * The completion items.
   */
  @Pure
  @NonNull
  public List<CompletionItem> getItems() {
    return this.items;
  }
  
  /**
   * The completion items.
   */
  public void setItems(@NonNull final List<CompletionItem> items) {
    this.items = items;
  }
  
  public CompletionList() {
    
  }
  
  public CompletionList(final boolean isIncomplete, final List<CompletionItem> items) {
    this.isIncomplete = isIncomplete;
    this.items = items;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("isIncomplete", this.isIncomplete);
    b.add("items", this.items);
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
    CompletionList other = (CompletionList) obj;
    if (other.isIncomplete != this.isIncomplete)
      return false;
    if (this.items == null) {
      if (other.items != null)
        return false;
    } else if (!this.items.equals(other.items))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (this.isIncomplete ? 1231 : 1237);
    result = prime * result + ((this.items== null) ? 0 : this.items.hashCode());
    return result;
  }
}
