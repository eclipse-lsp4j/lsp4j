package org.eclipse.lsp4j.adapters;

import java.util.List;

import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter.ListChecker;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter.PropertyChecker;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class LocationLinkListAdapter implements TypeAdapterFactory {

	private static final TypeToken<Either<List<? extends Location>, List<? extends LocationLink>>> EITHER_TYPE
			= new TypeToken<>() {};

	@SuppressWarnings("unchecked")
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		final var leftChecker = new ListChecker(new PropertyChecker("uri"), true);
		final var rightChecker = new ListChecker(new PropertyChecker("targetUri"), false);
		return (TypeAdapter<T>) new EitherTypeAdapter<>(gson, EITHER_TYPE, leftChecker, rightChecker);
	}

}
