package org.eclipse.lsp4j

import java.util.List
import org.eclipse.lsp4j.generator.LanguageServerAPI
import org.eclipse.lsp4j.jsonrpc.validation.NonNull

/**
 * Representation of a computed mapping from ranges to the appropriate
 * highlighting style.
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
	 * A list of highlighting styles, that should be applied on
	 * the range. Several styles could be merged on the client-side by 
	 * applying all styles on the range. 
	 */
	List<Integer> styles;

}

class ColoringStyle {
	
	public val identifier     = 1
	public val entity         = 2
	public val constructor    = 3
	public val operators      = 4
	public val tag            = 5
	public val namespace      = 6
	public val keyword        = 7
	public val info_token     = 8
	public val type           = 9
	public val string         = 10
	public val warn_token     = 11
	public val predefined     = 12
	public val string_escape  = 13
	public val error_token    = 14
	public val invalid        = 15
	public val comment        = 16
	public val debug_token    = 17
	public val comment_doc    = 18
	public val regexp         = 19
	public val constant       = 20
	public val attribute      = 21
	
	// modifiers
	public val modifier_public = 22
	public val modifier_private = 23
	public val modifier_protected = 24
	public val modifier_static = 25
	public val modifier_final= 26
}
