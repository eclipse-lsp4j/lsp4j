package org.eclipse.lsp4j.jsonrpc.test.json;

public enum MyEnum {
	
	A(1),
	B(2),
	C(3);
	
	private final int value;
	
	MyEnum(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static MyEnum forValue(int value) {
		MyEnum[] allValues = MyEnum.values();
		if (value < 1 || value > allValues.length)
			throw new IllegalArgumentException("Illegal enum value: " + value);
		return allValues[value - 1];
	}

}
