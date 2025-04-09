package com.manage.helperUtil;

import java.text.Collator;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;

public class ComparatorUtils {

	public static <U> int comparingByOrderdList(List<U> orderdList, U n1, U n2) {
		return Integer.compare(orderdList.indexOf(n1), orderdList.indexOf(n2));
	}

	/**
	 * 昇順ソート用Comparatorを作成
	 * 
	 * @param <T>          比較する要素の型
	 * @param keyExtractor キーを抽出する関数
	 * @return 昇順ソート用のComparator
	 */
	public static <T, U extends Comparable<? super U>> Comparator<T> ascending(Function<T, U> keyExtractor) {
		return Comparator.comparing(keyExtractor);
	}

	/**
	 * 降順ソート用Comparatorを作成
	 * 
	 * @param <T>          比較する要素の型
	 * @param keyExtractor キーを抽出する関数
	 * @return 降順ソート用のComparator
	 */
	public static <T, U extends Comparable<? super U>> Comparator<T> descending(Function<T, U> keyExtractor) {
		return Comparator.comparing(keyExtractor).reversed();
	}

	/**
	 * 複数キーでのComparatorを作成（昇順）
	 * 
	 * @param <T>         比較する要素の型
	 * @param comparators 複数のComparator
	 * @return 複数キー対応のComparator
	 */
	@SafeVarargs
	public static <T> Comparator<T> multiLevel(Comparator<T>... comparators) {
		Comparator<T> combined = (a, b) -> 0;
		for (Comparator<T> comparator : comparators) {
			combined = combined.thenComparing(comparator);
		}
		return combined;
	}

	/**
	 * null優先のComparatorを作成
	 * 
	 * @param <T>        比較する要素の型
	 * @param comparator 基本となるComparator
	 * @return nullを優先するComparator
	 */
	public static <T> Comparator<T> nullsFirst(Comparator<T> comparator) {
		return Comparator.nullsFirst(comparator);
	}

	/**
	 * nullを後にするComparatorを作成
	 * 
	 * @param <T>        比較する要素の型
	 * @param comparator 基本となるComparator
	 * @return nullを後にするComparator
	 */
	public static <T> Comparator<T> nullsLast(Comparator<T> comparator) {
		return Comparator.nullsLast(comparator);
	}

	/**
	 * 文字列の長さで比較 (昇順)
	 */
	public static Comparator<String> byStringLengthAsc() {
		return Comparator.comparingInt(String::length);
	}

	/**
	 * 文字列の長さで比較 (降順)
	 */
	public static Comparator<String> byStringLengthDesc() {
		return Comparator.comparingInt(String::length).reversed();
	}

	/**
	 * 大文字小文字を無視して文字列比較 (昇順)
	 */
	public static Comparator<String> caseInsensitiveAsc() {
		return String.CASE_INSENSITIVE_ORDER;
	}

	/**
	 * 大文字小文字を無視して文字列比較 (降順)
	 */
	public static Comparator<String> caseInsensitiveDesc() {
		return String.CASE_INSENSITIVE_ORDER.reversed();
	}

	/**
	 * 数値の絶対値で比較 (昇順)
	 */
	public static Comparator<Integer> byAbsoluteValueAsc() {
		return Comparator.nullsLast(Comparator.comparingInt(value -> Math.abs(value)));
	}

	/**
	 * 数値の絶対値で比較 (降順)
	 */
	public static Comparator<Integer> byAbsoluteValueDesc() {
		return Comparator
				.nullsLast(Comparator.comparingInt(value -> Math.abs(value != null ? (int) value : 0)).reversed());
	}

	/**
	 * 順序を逆転するComparatorを作成
	 */
	public static <T> Comparator<T> reverseOrder(Comparator<T> comparator) {
		return comparator.reversed();
	}

	/**
	 * nullを許容した昇順ソート (nullは最後)
	 */
	public static <T, U extends Comparable<? super U>> Comparator<T> ascendingWithNullsLast(
			Function<T, U> keyExtractor) {
		return Comparator.comparing(keyExtractor, Comparator.nullsLast(Comparator.naturalOrder()));
	}

	/**
	 * nullを許容した降順ソート (nullは最後)
	 */
	public static <T, U extends Comparable<? super U>> Comparator<T> descendingWithNullsLast(
			Function<T, U> keyExtractor) {
		return Comparator.comparing(keyExtractor, Comparator.nullsLast(Comparator.reverseOrder()));
	}

	/**
	 * nullを優先した昇順ソート (nullは最初)
	 */
	public static <T, U extends Comparable<? super U>> Comparator<T> ascendingWithNullsFirst(
			Function<T, U> keyExtractor) {
		return Comparator.comparing(keyExtractor, Comparator.nullsFirst(Comparator.naturalOrder()));
	}

	/**
	 * カスタム条件で動的なComparatorを作成
	 */
	public static <T, U extends Comparable<? super U>> Comparator<T> dynamicComparator(Function<T, U> keyExtractor) {
		return Comparator.comparing(keyExtractor);
	}

	/**
	 * 複数プロパティのネスト比較
	 */
	public static <T> Comparator<T> nestedComparator(List<Comparator<T>> comparators) {
		return comparators.stream().reduce(Comparator::thenComparing).orElse((a, b) -> 0);
	}

	/**
	 * ランダム順に並び替えるComparator
	 */
	public static <T> Comparator<T> randomOrder() {
		Random random = new Random();
		return (a, b) -> random.nextInt(3) - 1; // -1, 0, 1 のいずれか
	}

	/**
	 * 空白またはnullを優先するComparator
	 */
	public static Comparator<String> nullAndEmptyFirst() {
		return Comparator.comparing((String s) -> s == null || s.isEmpty()).thenComparing(Comparator.naturalOrder());
	}

	/**
	 * 数値の特定範囲を優先的にソート
	 */
	public static Comparator<Integer> rangePriority(int lower, int upper) {
		return Comparator.<Integer>comparingInt(value -> {
			if (value >= lower && value <= upper) {
				return 0; // 範囲内
			} else if (value < lower) {
				return -1; // 範囲の下
			} else {
				return 1; // 範囲の上
			}
		});
	}

	/**
	 * 日本語の辞書順で比較
	 */
	public static Comparator<String> dictionaryOrderJapanese() {
		Collator collator = Collator.getInstance(Locale.JAPANESE);
		return collator::compare;
	}

	/**
	 * 日付の古い順ソート
	 */
	public static Comparator<LocalDate> byDateAsc() {
		return Comparator.naturalOrder();
	}

	/**
	 * 日付の新しい順ソート
	 */
	public static Comparator<LocalDate> byDateDesc() {
		return Comparator.reverseOrder();
	}

	/**
	 * リスト内のインデックス順でソート
	 */
	public static <T> Comparator<T> indexOrder(List<T> referenceList) {
		return Comparator.comparingInt(referenceList::indexOf);
	}

	/**
	 * 条件付きカスタム優先度
	 */
	public static <T> Comparator<T> conditionalPriority(Predicate<T> condition) {
		return (a, b) -> {
			if (condition.test(a) && !condition.test(b)) {
				return -1; // a を優先
			} else if (!condition.test(a) && condition.test(b)) {
				return 1; // b を優先
			} else {
				return 0; // 同等
			}
		};
	}
}