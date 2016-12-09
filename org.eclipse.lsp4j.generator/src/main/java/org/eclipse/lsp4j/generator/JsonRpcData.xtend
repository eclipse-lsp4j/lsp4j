package org.eclipse.lsp4j.generator

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.Target
import org.eclipse.xtend.lib.macro.Active

@Target(ElementType.TYPE)
@Active(JsonRpcDataProcessor)
@Retention(SOURCE)
annotation JsonRpcData {
    
}