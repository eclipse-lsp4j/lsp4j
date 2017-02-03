package org.eclipse.lsp4j.jsonrpc.test.json;

import java.util.ArrayList;
import java.util.List;

public class MyClassList {

	private List<MyClass> items = new ArrayList<>();

	public List<MyClass> getItems() {
		return items;
	}

	public void setItems(List<MyClass> items) {
		this.items = items;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyClassList other = (MyClassList) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}

	public String toString() {
		return "MyClassList [items=" + items + "]";
	}

}
