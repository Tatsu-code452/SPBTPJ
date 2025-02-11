package com.manage.helperUtil;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * 
 * @author Owner
 *
 * @param <T> the type of element to be compared
 */
public class ComparatorUtils<T> implements Comparator<T> {

	// 指定キーを昇順、かつ、nullを最後にしてソート
	public static <T, U extends Comparable<U>> Comparator<T> comparingNaturalOrderNullsLast(
			Function<T, U> keyExtractor) {
		return Comparator.comparing(keyExtractor, Comparator.nullsLast(Comparator.naturalOrder()));
	}

	public static <U> int comparingByOrderdList(List<U> orderdList, U n1, U n2) {
		return Integer.compare(orderdList.indexOf(n1), orderdList.indexOf(n2));
	}

	@Override
	public int compare(T o1, T o2) {
		return 0;
	}

}