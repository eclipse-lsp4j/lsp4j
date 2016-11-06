package org.eclipse.lsp4j.jsonrpc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use on a class or interface to prefix all declared {@link JsonRequest} and {@link JsonNotification} methods.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JsonSegment {
	String value() default "";
}
