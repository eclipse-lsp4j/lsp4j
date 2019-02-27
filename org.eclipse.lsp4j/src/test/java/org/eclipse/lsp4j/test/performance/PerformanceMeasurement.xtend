package org.eclipse.lsp4j.test.performance

import com.google.gson.Gson
import java.util.ArrayList
import java.util.List
import java.util.Random
import org.eclipse.lsp4j.Location
import org.eclipse.lsp4j.Position
import org.eclipse.lsp4j.Range
import org.eclipse.lsp4j.SymbolInformation
import org.eclipse.lsp4j.SymbolKind
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler

class PerformanceMeasurement implements Runnable {
	
	def static void main(String[] args) {
		new PerformanceMeasurement().run()
	}
	
	val random = new Random(0)
	
	override run() {
		measureSymbolInformation()
	}
	
	def void measureSymbolInformation() {
		measure(new Measurement {
			static val SIZE = 100_000
			Gson gson
			List<SymbolInformation> data
			
			override prepare() {
				data = new ArrayList(SIZE)
				for (var i = 0; i < SIZE; i++) {
					data += new SymbolInformation => [
						name = randomString
						kind = SymbolKind.forValue(random.nextInt(SymbolKind.values.length) + 1)
						location = new Location => [
							uri = randomString
							range = new Range => [
								start = new Position(random.nextInt(100), random.nextInt(100))
								end = new Position(random.nextInt(100), random.nextInt(100))
							]
						]
					]
				}
				gson = new MessageJsonHandler(emptyMap).gson
			}
			
			override run() {
				gson.toJson(data)
			}
		}, 'measureSymbolInformation')
	}
	
	private def measure(Measurement measurement, String name) {
		// Warmup
		measurement.prepare()
		measurement.run()
		
		// Measure
		val startTime = System.nanoTime
		for (var i = 0; i < 10; i++) {
			measurement.run()
		}
		val endTime = System.nanoTime
		
		// Display
		val duration = (endTime - startTime) * 1e-7
		println(name + ': ' + duration + ' ms')
	}
	
	private def randomString() {
		val length = random.nextInt(30) + 2
		val builder = new StringBuilder(length)
		for (var i = 0; i < length; i++) {
			val c = (random.nextInt(75) + 48) as char
			builder.append(c)
		}
		return builder.toString
	}
	
	private interface Measurement extends Runnable {
		def void prepare()
	}
	
}