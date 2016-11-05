package org.eclipse.lsp4j.jsonrpc.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JsonRequest {
	String value() default "";
}
