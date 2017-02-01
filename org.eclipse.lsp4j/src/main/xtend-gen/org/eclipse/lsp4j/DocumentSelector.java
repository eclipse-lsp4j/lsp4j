package org.eclipse.lsp4j;

import java.util.List;
import org.eclipse.lsp4j.DocumentFilter;

/**
 * A document selector is the combination of one or many document filters.
 */
@SuppressWarnings("all")
public interface DocumentSelector extends List<DocumentFilter> {
}
