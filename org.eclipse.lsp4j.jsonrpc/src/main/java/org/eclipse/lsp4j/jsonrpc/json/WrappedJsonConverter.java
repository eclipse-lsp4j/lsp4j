package org.eclipse.lsp4j.jsonrpc.json;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public interface WrappedJsonConverter<T> {
	T fromJson(JsonElement e);
	JsonElement toJson(T e);
	boolean isCompatible(JsonElement e);
	
	@SuppressWarnings("unchecked")
	public static <T> WrappedJsonConverter<T> getConverter(Type t) {
		if (t == String.class) {
			return (WrappedJsonConverter<T>) WrappedJsonConverter.stringConverter;
		} else if (t == Integer.class || t == int.class) {
			return (WrappedJsonConverter<T>) WrappedJsonConverter.integerConverter;
		} else if (t == Boolean.class || t == boolean.class) {
			return (WrappedJsonConverter<T>) WrappedJsonConverter.booleanConverter;
		} else if (t == JsonElement.class) {
			return (WrappedJsonConverter<T>) WrappedJsonConverter.noConverter;
		} else if (t == Object.class) {
			return (WrappedJsonConverter<T>) WrappedJsonConverter.anyConverter;
		} else if (t instanceof Class<?> && WrappedJsonObject.class.isAssignableFrom((Class<?>)t)) {
			return (WrappedJsonConverter<T>) WrappedJsonConverter.objectConverter((Class<WrappedJsonObject>) t);
		} else if (t instanceof Class<?> && WrappedJsonEnum.class.isAssignableFrom((Class<?>)t)) {
			return (WrappedJsonConverter<T>) WrappedJsonConverter.enumConverter((Class<WrappedJsonEnum>) t);
		} else if (t instanceof ParameterizedType) {
			ParameterizedType pType = (ParameterizedType) t;
			Class<?> rawType = (Class<?>)pType.getRawType();
			if (Either.class.isAssignableFrom(rawType)) {
				WrappedJsonConverter<?> left = getConverter(pType.getActualTypeArguments()[0]);
				WrappedJsonConverter<?> right = getConverter(pType.getActualTypeArguments()[1]);
				return (WrappedJsonConverter<T>) WrappedJsonConverter.eitherConverter(left, right);
			} else if (List.class.isAssignableFrom(rawType)) {
				WrappedJsonConverter<?> component = getConverter(pType.getActualTypeArguments()[0]);
				return (WrappedJsonConverter<T>) WrappedJsonConverter.listConverter(component);
			} else if (Map.class.isAssignableFrom(rawType)) {
				WrappedJsonConverter<?> component = getConverter(pType.getActualTypeArguments()[1]);
				return (WrappedJsonConverter<T>) WrappedJsonConverter.mapConverter(component);
			} else {
				throw new IllegalArgumentException("Unknown type "+t);
			}
		} else {
			throw new IllegalArgumentException("Unknown type "+t);
		}
	}
	
	public static WrappedJsonConverter<Object> anyConverter = new WrappedJsonConverter<Object>() {
		@Override
		public Object fromJson(JsonElement e) {
			return e;
		}
		@Override
		public JsonElement toJson(Object e) {
			if (e instanceof WrappedJson) {
				return ((WrappedJson) e).jsonElement();
			}
			return (JsonElement) e;
		}
		@Override
		public boolean isCompatible(JsonElement e) {
			return true;
		}
	};
	
	public static WrappedJsonConverter<JsonElement> noConverter = new WrappedJsonConverter<JsonElement>() {
		@Override
		public JsonElement fromJson(JsonElement e) {
			return e;
		}
		@Override
		public JsonElement toJson(JsonElement e) {
			return (JsonElement) e;
		}
		@Override
		public boolean isCompatible(JsonElement e) {
			return true;
		}
	};
	
	@SuppressWarnings("unchecked")
	public static <T extends WrappedJsonEnum>WrappedJsonConverter<T> enumConverter(Class<T> enumType) {
		return new WrappedJsonConverter<T>() {
			@Override
			public T fromJson(JsonElement e) {
				int asInt = e.getAsInt();
				Method method;
				try {
					method = enumType.getDeclaredMethod("valueOf", Integer.TYPE);
					return (T) method.invoke(null, asInt);
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
					throw new IllegalStateException(e1);
				}
			}
			@Override
			public JsonElement toJson(T e) {
				return new JsonPrimitive(e.getValue());
			}
			@Override
			public boolean isCompatible(JsonElement e) {
				try {
					return fromJson(e) != null;
				} catch (IllegalStateException ex) {
					return false;
				}
			}
		};
	}
	
	public static WrappedJsonConverter<String> stringConverter = new WrappedJsonConverter<String>() {
		@Override
		public String fromJson(JsonElement e) {
			if (e == null)
				return null;
			if (e instanceof JsonNull)
				return null;
			return e.getAsString();
		}
		@Override
		public JsonElement toJson(String e) {
			if (e == null)
				return JsonNull.INSTANCE;
			return new JsonPrimitive(e);
		}
		@Override
		public boolean isCompatible(JsonElement e) {
			if (e.isJsonNull())
				return true;
			if (!e.isJsonPrimitive())
				return false;
			try {
				e.getAsString();
				return true;
			} catch (ClassCastException ex) {
				return false;
			}
		}
	};
	
	public static WrappedJsonConverter<Integer> integerConverter = new WrappedJsonConverter<Integer>() {
		@Override
		public Integer fromJson(JsonElement e) {
			if (e == null)
				return 0;
			if (e instanceof JsonNull)
				return null;
			return e.getAsInt();
		}
		@Override
		public JsonElement toJson(Integer e) {
			if (e == null)
				return JsonNull.INSTANCE;
			return new JsonPrimitive(e);
		}
		
		@Override
		public boolean isCompatible(JsonElement e) {
			if (e.isJsonNull())
				return true;
			if (!e.isJsonPrimitive())
				return false;
			try {
				e.getAsInt();
				return true;
			} catch (ClassCastException ex) {
				return false;
			}
		}
	};
	
	public static WrappedJsonConverter<Boolean> booleanConverter = new WrappedJsonConverter<Boolean>() {
		@Override
		public Boolean fromJson(JsonElement e) {
			if (e == null)
				return false;
			if (e instanceof JsonNull)
				return null;
			return e.getAsBoolean();
		}
		@Override
		public JsonElement toJson(Boolean e) {
			if (e == null)
				return JsonNull.INSTANCE;
			return new JsonPrimitive(e);
		}
		
		public boolean isCompatible(JsonElement e) {
			if (e.isJsonNull())
				return true;
			if (!e.isJsonPrimitive())
				return false;
			try {
				e.getAsBoolean();
				return true;
			} catch (ClassCastException ex) {
				return false;
			}
		}
	};
	
	public static <T extends WrappedJsonObject> WrappedJsonConverter<T> objectConverter(Class<T> clazz) {
		try {
			Constructor<T> constructor = clazz.getConstructor(JsonObject.class);
			return new WrappedJsonConverter<T>() {
				@Override
				public T fromJson(JsonElement e) {
					try {
						if (e == null || e instanceof JsonNull)
							return null;
						return constructor.newInstance(e);
					} catch (InstantiationException | IllegalAccessException  | IllegalArgumentException  | InvocationTargetException ex) {
						throw new IllegalArgumentException(ex);
					}
				}
				@Override
				public JsonElement toJson(T e) {
					if (e == null)
						return JsonNull.INSTANCE;
					return e.jsonElement();
				}
				
				@Override
				public boolean isCompatible(JsonElement e) {
					if (e.isJsonNull())
						return true;
					return e.isJsonObject();
				}
			};
		} catch (NoSuchMethodException | SecurityException e) {
			throw new IllegalArgumentException("The given clazz "+clazz.getName()+" doesn't have a constructor that accepts a single JsonObject");
		}
	}
	
	public static <T> WrappedJsonConverter<List<T>> listConverter(WrappedJsonConverter<T> componentConverter) {
		return new WrappedJsonConverter<List<T>>() {
			@Override
			public List<T> fromJson(JsonElement e) {
				if (e instanceof JsonNull)
					return null;
				return new WrappedJsonList<>((JsonArray)e, componentConverter); 
			}
			@Override
			public JsonElement toJson(List<T> e) {
				if (e == null)
					return JsonNull.INSTANCE;
				if (e instanceof WrappedJsonList) {
			        return ((WrappedJsonList<?>) e).jsonElement();
			    } else {
			        JsonArray jsonArray = new JsonArray();
			        WrappedJsonList<T> newElements = new WrappedJsonList<>(jsonArray, componentConverter);
			        newElements.addAll(e);
			        return jsonArray;
			    }
			}
			@Override
			public boolean isCompatible(JsonElement e) {
				return e instanceof JsonArray || e instanceof JsonNull;
			}
		};
	}
	
	public static <T> WrappedJsonConverter<Map<String, T>> mapConverter(WrappedJsonConverter<T> componentConverter) {
		return new WrappedJsonConverter<Map<String, T>>() {
			@Override
			public Map<String, T> fromJson(JsonElement e) {
				if (e instanceof JsonNull)
					return null;
				return new WrappedJsonStringMap<>((JsonObject)e, componentConverter); 
			}
			@Override
			public JsonElement toJson(Map<String, T> e) {
				if (e == null)
					return JsonNull.INSTANCE;
				if (e instanceof WrappedJsonStringMap) {
					return ((WrappedJsonStringMap<?>) e).jsonElement();
				} else {
					JsonObject jsonObject = new JsonObject();
					WrappedJsonStringMap<T> newElements = new WrappedJsonStringMap<>(jsonObject, componentConverter);
					newElements.putAll(e);
					return jsonObject;
				}
			}
			
			public boolean isCompatible(JsonElement e) {
				return e instanceof JsonObject || e instanceof JsonNull;
			}
		};
	}
	public static <L, R> WrappedJsonConverter<Either<L, R>> eitherConverter(WrappedJsonConverter<L> leftConverter, WrappedJsonConverter<R> rightConverter) {
		return new WrappedJsonConverter<Either<L, R>>() {
			
			@Override
			public Either<L, R> fromJson(JsonElement e) {
				Either<L,R> result = new Either<L,R>(leftConverter, rightConverter);
				result.setWrapped(e);
				return result;
			}
			
			@Override
			public JsonElement toJson(Either<L, R> e) {
				if (e == null)
					return JsonNull.INSTANCE;
				if (e.isLeft()) {
					return leftConverter.toJson(e.getLeft());
				} else {
					return rightConverter.toJson(e.getRight());
				}
			}
			
			@Override
			public boolean isCompatible(JsonElement e) {
				return leftConverter.isCompatible(e) || rightConverter.isCompatible(e);
			}
		};
	}
}
