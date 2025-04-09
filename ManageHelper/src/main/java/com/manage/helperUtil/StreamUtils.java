package com.manage.helperUtil;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamUtils {

	// フィルタリング: リストから条件に合致する要素のみを抽出
	public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
		return list.stream().filter(predicate).collect(Collectors.toList());
	}

	// マッピング: 各要素を関数を使って別の型に変換
	public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
		return list.stream().map(mapper).collect(Collectors.toList());
	}

	// ソート: Comparatorを使ってリストを並び替え
	public static <T> List<T> sort(List<T> list, Comparator<T> comparator) {
		return list.stream().sorted(comparator).collect(Collectors.toList());
	}

	// リミット: リストの先頭から指定された数の要素を取得
	public static <T> List<T> limit(List<T> list, int limit) {
		return list.stream().limit(limit).collect(Collectors.toList());
	}

	// 結合: リストの要素を指定された区切り文字で結合
	public static <T> String join(List<T> list, String delimiter) {
		return list.stream().map(Object::toString).collect(Collectors.joining(delimiter));
	}

	// 集計: リストの要素数を取得
	public static <T> long count(List<T> list) {
		return list.stream().count();
	}

	// 要素のチェック (全て一致): すべての要素が条件に合致するか確認
	public static <T> boolean allMatch(List<T> list, Predicate<T> predicate) {
		return list.stream().allMatch(predicate);
	}

	// 要素のチェック (少なくとも1つ一致): 条件に合致する要素が1つでもあるか確認
	public static <T> boolean anyMatch(List<T> list, Predicate<T> predicate) {
		return list.stream().anyMatch(predicate);
	}

	// 要素のチェック (一致しない): すべての要素が条件に一致しないか確認
	public static <T> boolean noneMatch(List<T> list, Predicate<T> predicate) {
		return list.stream().noneMatch(predicate);
	}

	// 最大値を取得: Comparatorを使ってリスト内の最大値を取得
	public static <T> Optional<T> findMax(List<T> list, Comparator<T> comparator) {
		return list.stream().max(comparator);
	}

	// 最小値を取得: Comparatorを使ってリスト内の最小値を取得
	public static <T> Optional<T> findMin(List<T> list, Comparator<T> comparator) {
		return list.stream().min(comparator);
	}

	// 重複を削除: リストから重複する要素を削除
	public static <T> List<T> distinct(List<T> list) {
		return list.stream().distinct().collect(Collectors.toList());
	}

	// 平均値を計算 (整数値のリスト用): 整数のリストから平均値を計算
	public static double average(List<Integer> list) {
		return list.stream().mapToInt(Integer::intValue).average().orElse(0.0);
	}

	// 合計を計算 (整数値のリスト用): 整数のリストの合計を計算
	public static int sum(List<Integer> list) {
		return list.stream().mapToInt(Integer::intValue).sum();
	}

	// 任意の要素を取得: リストから任意の要素を取得
	public static <T> Optional<T> findAny(List<T> list) {
		return list.stream().findAny();
	}

	// 最初の要素を取得: リストの最初の要素を取得
	public static <T> Optional<T> findFirst(List<T> list) {
		return list.stream().findFirst();
	}

	// グループ化: 要素を指定された分類関数でグループ化
	public static <T, K> Map<K, List<T>> groupBy(List<T> list, Function<T, K> classifier) {
		return list.stream().collect(Collectors.groupingBy(classifier));
	}

	// 順序付きグループ化: 入力の順序を維持したまま要素をグループ化
	public static <T, K> LinkedHashMap<K, List<T>> groupByOrdered(List<T> list, Function<T, K> classifier) {
		return list.stream().collect(Collectors.groupingBy(classifier, LinkedHashMap::new, // 順序を保持するマップ
				Collectors.toList()));
	}

	// 順序付きMAPへ変換: キーが重複した場合は最初の値を保持
	public static <T, K, V> LinkedHashMap<K, V> toLinkedHashMap(List<T> list, Function<T, K> keyMapper,
			Function<T, V> valueMapper) {
		return list.stream().collect(
				Collectors.toMap(keyMapper, valueMapper, (existing, replacement) -> existing, LinkedHashMap::new));
	}

	// パーティション分割: 条件に応じてリストを2つに分割
	public static <T> Map<Boolean, List<T>> partitionBy(List<T> list, Predicate<T> predicate) {
		return list.stream().collect(Collectors.partitioningBy(predicate));
	}

	// カウントマップ作成: 要素ごとの出現回数をマップで作成
	public static <T> Map<T, Long> countBy(List<T> list) {
		return list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}

	// 平均値 (Doubleリスト用): Doubleのリストから平均値を計算
	public static double averageDouble(List<Double> list) {
		return list.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
	}

	// 合計 (Doubleリスト用): Doubleのリストの合計を計算
	public static double sumDouble(List<Double> list) {
		return list.stream().mapToDouble(Double::doubleValue).sum();
	}

	// 指定インデックスの要素をスキップ: リストの先頭から指定した数の要素をスキップ
	public static <T> List<T> skip(List<T> list, int n) {
		return list.stream().skip(n).collect(Collectors.toList());
	}

	// 平坦化: 入れ子のリストを1次元リストに変換
	public static <T> List<T> flatten(List<List<T>> nestedList) {
		return nestedList.stream().flatMap(Collection::stream).collect(Collectors.toList());
	}

	// 条件付きマージ: 条件に一致する要素だけを2つのリストから結合
	public static <T> List<T> mergeIf(List<T> list1, List<T> list2, Predicate<T> condition) {
		return Stream.concat(list1.stream().filter(condition), list2.stream().filter(condition))
				.collect(Collectors.toList());
	}

	// 要素の出現回数をカウント: 各要素の出現回数をマップで返す
	public static <T> Map<T, Long> elementCounts(List<T> list) {
		return list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}

	// 条件付き最大値: 条件に一致する最大値を取得
	public static <T> Optional<T> findConditionalMax(List<T> list, Predicate<T> condition, Comparator<T> comparator) {
		return list.stream().filter(condition).max(comparator);
	}

	// 最頻出要素: 出現頻度が最も高い要素を取得
	public static <T> Optional<T> mostFrequentElement(List<T> list) {
		return list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet()
				.stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey);
	}

	// カスタム集計: ユーザー定義の集計ロジックを適用
	public static <T, R> R customAggregate(List<T> list, Supplier<R> supplier, BiConsumer<R, T> accumulator) {
		R result = supplier.get();
		list.stream().forEach(item -> accumulator.accept(result, item));
		return result;
	}

	// ペアリスト作成: 2つのリストを結合してペアリストを作成
	public static <T, U> List<Map.Entry<T, U>> zip(List<T> list1, List<U> list2) {
		int size = Math.min(list1.size(), list2.size());
		return IntStream.range(0, size).mapToObj(i -> new AbstractMap.SimpleEntry<>(list1.get(i), list2.get(i)))
				.collect(Collectors.toList());
	}

	// カスタム条件で分割: 指定された条件でリストを分類
	public static <T, K> Map<K, List<T>> splitByCustomCondition(List<T> list, Function<T, K> classifier) {
		return list.stream().collect(Collectors.groupingBy(classifier));
	}
}