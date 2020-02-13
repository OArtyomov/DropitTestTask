package com.dropit.conversion;

import org.springframework.core.convert.converter.Converter;

import java.util.Collection;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

public interface IConverter<SOURCE, TARGET> extends Converter<SOURCE, TARGET> {

	default List<TARGET> convertAll(Collection<? extends SOURCE> sources) {
		return isEmpty(sources) ? emptyList() : sources.stream()
				.map(this::convert)
				.collect(toList());
	}
}
