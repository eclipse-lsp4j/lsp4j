package org.eclipse.lsp4j.jsonrpc.services;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method annotated with {@link JsonDelegate} is treated a s a delegate method. 
 * As a result jsonrpc methods of the delegate will be considered, too.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JsonDelegate {
}
