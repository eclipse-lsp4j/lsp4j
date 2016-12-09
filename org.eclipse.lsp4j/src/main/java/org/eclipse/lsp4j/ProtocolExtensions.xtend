package org.eclipse.lsp4j

import java.util.List
import org.eclipse.lsp4j.generator.JsonRpcData
import org.eclipse.lsp4j.jsonrpc.validation.NonNull
import java.util.ArrayList

/**
 * Representation of a computed mapping from ranges to the appropriate
 * highlighting style.
 */
@JsonRpcData
class ColoringParams {

	/**
	 * The URI for which coloring information is reported.
	 */
	@NonNull
	String uri;

	/**
	 * A list of coloring information instances.
	 */
	@NonNull
	List<? extends ColoringInformation> infos = new ArrayList;

}


/**
 * Representation of a range and highlighting style identifiers that should be
 * highlighted based on the underlying model.
 */
@JsonRpcData
class ColoringInformation {

	/**
	 * The range that should be highlighted on the client-side.
	 */
	@NonNull
	Range range;

	/**
	 * A list of highlighting styles, that should be applied on
	 * the range. Several styles could be merged on the client-side by 
	 * applying all styles on the range. 
	 */
	@NonNull
	List<Integer> styles = new ArrayList;

}

class ColoringStyle {
	
	public static val Identifier     = 1
	public static val Entity         = 2
	public static val Constructor    = 3
	public static val Operators      = 4
	public static val Tag            = 5
	public static val Namespace      = 6
	public static val Keyword        = 7
	public static val Info_token     = 8
	public static val Type           = 9
	public static val String         = 10
	public static val Warn_token     = 11
	public static val Predefined     = 12
	public static val String_escape  = 13
	public static val Error_token    = 14
	public static val Invalid        = 15
	public static val Comment        = 16
	public static val Debug_token    = 17
	public static val Comment_doc    = 18
	public static val Regexp         = 19
	public static val Constant       = 20
	public static val Attribute      = 21
	
	public static val Modifier_public  = 22
	public static val Modifier_private = 23
	public static val Modifier_protected = 24
	public static val Modifier_static  = 25
	public static val Modifier_final   = 26
}
