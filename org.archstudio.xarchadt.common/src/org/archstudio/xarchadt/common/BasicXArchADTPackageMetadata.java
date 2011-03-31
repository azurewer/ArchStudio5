package org.archstudio.xarchadt.common;

import java.util.Collections;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class BasicXArchADTPackageMetadata implements IXArchADTPackageMetadata {

	private final String nsURI;
	private final Map<String, IXArchADTTypeMetadata> typeMetadata;

	public BasicXArchADTPackageMetadata(String nsURI, Iterable<IXArchADTTypeMetadata> factoryElementMetadata) {
		this.nsURI = nsURI;
		this.typeMetadata = Collections.unmodifiableMap(Maps.uniqueIndex(
				factoryElementMetadata,
				new Function<IXArchADTTypeMetadata, String>() {
					@Override
					public String apply(IXArchADTTypeMetadata input) {
						return input.getTypeName();
					}
				}));
	}

	public String getNsURI() {
		return nsURI;
	}

	public Map<String, IXArchADTTypeMetadata> getTypeMetadata() {
		return typeMetadata;
	}
}
