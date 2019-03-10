package com.kngines.hase;

import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * 
 * @ClassName: NavigableMapTest
 * @Description:TODO NavigableMap是SortedMap的扩展，它提供方便的导航方法，如lowerKey，floorKey，ceilingKey和higherKey。
 * @author: kngines
 * @date: 2019年3月10日 下午5:44:06
 *
 */

// NavigableMap的方法
// lowerKey（Object key）：返回严格小于给定键的最大键，或者如果没有这样的键。
// floorKey（Object key）：返回小于或等于给定键的最大键，或者如果没有这样的键。
// ceilingKey（Object key）：返回大于或等于给定键的最小键，或者如果没有这样的键。
// higherKey（Object key）：返回严格大于给定键的最小键，或者如果没有这样的键。
// descendingMap（）：返回此映射中包含的映射的逆序视图。
// headMap（object toKey，boolean inclusive）
// ：返回此映射部分的视图，其键小于（或等于，如果inclusive为true）toKey。
// subMap（object fromKey，boolean fromInclusive，object toKey，boolean toInclusive）
// ：返回此映射部分的视图，其键的范围从fromKey到toKey。
// tailMap（object fromKey，boolean inclusive）
// ：返回此映射部分的视图，其键大于（或等于，如果inclusive为true）fromKey

public class NavigableMapTest {

	public static void main(String[] args) {
		NavigableMap<String, Integer> nm = new TreeMap<String, Integer>();
		nm.put("C", 888);
		nm.put("Y", 999);
		nm.put("A", 444);
		nm.put("T", 555);
		nm.put("B", 666);
		nm.put("A", 555);

		System.out.printf("Descending Set  : %s%n", nm.descendingKeySet());
		System.out.printf("Floor Entry  : %s%n", nm.floorEntry("L"));
		System.out.printf("First Entry  : %s%n", nm.firstEntry());
		System.out.printf("Last Key : %s%n", nm.lastKey());
		System.out.printf("First Key : %s%n", nm.firstKey());
		System.out.printf("Original Map : %s%n", nm);
		System.out.printf("Reverse Map : %s%n", nm.descendingMap());
	}

}
