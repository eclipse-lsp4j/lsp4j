package org.eclipse.lsp4j.adapters

import com.google.gson.stream.JsonWriter
import java.io.IOException
import org.eclipse.lsp4j.Location
import org.eclipse.lsp4j.Position
import org.eclipse.lsp4j.Range
import org.eclipse.lsp4j.SymbolInformation
import org.eclipse.lsp4j.generator.TypeAdapterImpl

/**
 * A type adapter for the SymbolInformation protocol type.
 */
@TypeAdapterImpl(SymbolInformation)
class SymbolInformationTypeAdapter {
	
	protected def writeLocation(JsonWriter out, Location value) throws IOException {
		if (value === null) {
	    	out.nullValue()
	    	return
	    }
	    
	    out.beginObject()
	    out.name('uri')
	    out.value(value.uri)
	    out.name('range')
	    writeRange(out, value.range)
	    out.endObject()
	}
	
	protected def writeRange(JsonWriter out, Range value) throws IOException {
		if (value === null) {
	    	out.nullValue()
	    	return
	    }
	    
	    out.beginObject()
	    out.name('start')
	    writePosition(out, value.start)
	    out.name('end')
	    writePosition(out, value.end)
	    out.endObject()
	}
	
	protected def writePosition(JsonWriter out, Position value) throws IOException {
		if (value === null) {
	    	out.nullValue()
	    	return
	    }
	    
	    out.beginObject()
	    out.name('line')
	    out.value(value.line)
	    out.name('character')
	    out.value(value.character)
	    out.endObject()
	}
	
}