package com.manage.helperUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 
 * @author Owner
 *
 */
public class CollectorUtils {

	public static <T, K, U> Collector<T, ?, Map<K, U>> collectLinkedHashMap(
			Function<? super T, ? extends K> keyExtractor, Function<? super T, ? extends U> valueMapper) {
		return Collectors.toMap(keyExtractor, valueMapper, (e1, e2) -> e1, LinkedHashMap::new);
	}

}