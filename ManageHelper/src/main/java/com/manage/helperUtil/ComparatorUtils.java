package com.manage.helperUtil;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * 
 * @author Owner
 *
 * @param  <T> the type of element to be compared
 * @param  <U> the type of the sort key
 */
public interface ComparatorUtils<D, T, U> extends Comparator<T> {

    public static <T, U extends Comparable<U>> Comparator<T> comparingNaturalOrderNullsLast(
            Function<T, U> keyExtractor) {
        return Comparator.comparing(keyExtractor, Comparator.nullsLast(Comparator.naturalOrder()));
    }

    public static <U> int comparingByOrderdList(List<U> orderdList, U n1, U n2) {
        return Integer.compare(orderdList.indexOf(n1),
                orderdList.indexOf(n2));
    }

}