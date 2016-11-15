package org.eclipse.lsp4j

import java.util.List
import org.eclipse.lsp4j.generator.LanguageServerAPI
import org.eclipse.lsp4j.jsonrpc.validation.NonNull

/**
 * Representation of a computed mapping from ranges to the appropriate
 * highlighting style.
 * 
 * @author akos.kitta - Initial contribution and API
 */
@LanguageServerAPI
class ColoringParams {

	/**
	 * The URI for which coloring information is reported.
	 */
	@NonNull
	String uri;

	/**
	 * A list of coloring information instances.
	 */
	List<? extends ColoringInformation> infos;

}


/**
 * Representation of a range and highlighting style identifiers that should be
 * highlighted based on the underlying model.
 */
@LanguageServerAPI
class ColoringInformation {

	/**
	 * The range that should be highlighted on the client-side.
	 */
	Range range;

	/**
	 * A list of highlighting style identifiers, that should be applied on
	 * the range. Several styles could be merged on the client-side by 
	 * applying all styles on the range. 
	 */
	List<String> ids;

}