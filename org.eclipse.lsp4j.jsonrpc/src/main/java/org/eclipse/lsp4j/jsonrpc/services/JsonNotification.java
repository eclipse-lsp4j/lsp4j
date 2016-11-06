package org.eclipse.lsp4j.jsonrpc.services;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a notification method on an interface or class.
 * A notification method must be of type <code>void</code> and have zero or one argument.
 * According to jsonrpc an argument must be an 'object' (a java bean, not e,g. String).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JsonNotification {
	String value() default "";
}
