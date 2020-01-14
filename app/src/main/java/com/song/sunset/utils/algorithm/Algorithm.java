package com.song.sunset.utils.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

/**
 * @author songmingwen
 * @description 罗列各种算法代码
 * 选择排序 {@link #selectionSort(int[])}
 * 插入排序 {@link #insertSort(int[])}
 * 冒泡排序 {@link #bubbleSort(int[])}
 * 递归归并排序 {@link #mergeSort(int[], int l, int r)}
 * 自底向上的归并排序 {@link #mergeSortFromBottomToUp(int[], int n)}
 * 快速排序 {@link #quickSort(int[])}
 * 无重复字符的最长子串 {@link #lengthOfLongestSubstring(String)}
 * @since 2019/12/11
 */
public class Algorithm {
    /**
     * 选择排序
     */
    public static int[] selectionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            AlgorithmKt.Companion.swap(array, i, minIndex);
        }
        return array;
    }

    /**
     * 插入排序
     * 数组越有序越快
     */
    public static int[] insertSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0 && array[j] < array[j - 1]; j--) {
                AlgorithmKt.Companion.swap(array, j, j - 1);
            }
        }
        return array;
    }

    /**
     * 冒泡排序
     */
    public static int[] bubbleSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < array.length - i; j++) {
                if (array[j] > array[j + 1]) {
                    AlgorithmKt.Companion.swap(array, j, j + 1);
                }
            }
        }
        return array;
    }

    /**
     * 递归归并排序
     *
     * @param l 左角标
     * @param r 右角标
     */
    public static void mergeSort(int[] array, int l, int r) {
        if (l >= r) {
            return;
        }
        int mid = (l + r) / 2;
        mergeSort(array, l, mid);
        mergeSort(array, mid + 1, r);
        merge(array, l, mid, r);
    }

    /**
     * 自底向上的归并排序
     *
     * @param n 数组长度
     */
    public static void mergeSortFromBottomToUp(int[] array, int n) {
        for (int sz = 1; sz <= n; sz += sz) {
            for (int i = 0; i + sz < n; i += sz + sz) {
                //对 array[i....i+sz-1] 和 array[i+sz...i+2sz-1] 进行归并
                merge(array, i, i + sz - 1, Math.min(i + sz + sz - 1, n - 1));
            }
        }
    }

    /**
     * 快速排序
     * 当数组接近有序时，快速排序最差情况，时间复杂度退化为 O(n^2)
     * 数组越无序越快
     */
    public static void quickSort(int[] array) {
        __quickSort(array, 0, array.length - 1);
    }

    private static void __quickSort(int[] array, int l, int r) {
        if (l >= r) {
            return;
        }

        int p = partition(array, l, r);
        __quickSort(array, l, p - 1);
        __quickSort(array, p + 1, r);
    }

    /**
     * @param l 左角标
     * @param r 右角标
     * @return p：返回 p ，使得 array[l....p-1] < array[p]; array[p+1...r] > array[p]
     */
    private static int partition(int[] array, int l, int r) {
        //对近乎有序数组优化时间复杂度
        AlgorithmKt.Companion.swap(array, l, new Random().nextInt(r - l + 1) + l);
        //选中第一个元素作为 v 的值，v 对应的角标即为 p
        int v = array[l];
        int p = l;
        for (int i = l + 1; i <= r; i++) {
            //数组 i 位置处的值如果比 v 小，那么将
            if (array[i] < v) {
                AlgorithmKt.Companion.swap(array, p + 1, i);
                p++;
            }
        }
        AlgorithmKt.Companion.swap(array, l, p);
        return p;
    }

    private static void merge(int[] array, int l, int mid, int r) {
        int[] array_new = new int[r - l + 1];

        if (r + 1 - l >= 0) System.arraycopy(array, l, array_new, 0, r + 1 - l);

//        System.out.println(l + "," + mid + "," + r);
//        System.out.println(Arrays.toString(array_new));

        int i = l, j = mid + 1;
        for (int k = l; k <= r; k++) {
            if (i > mid) {
                array[k] = array_new[j - l];
                j++;
            } else if (j > r) {
                array[k] = array_new[i - l];
                i++;
            } else if (array_new[i - l] < array_new[j - l]) {
                array[k] = array_new[i - l];
                i++;
            } else {
                array[k] = array_new[j - l];
                j++;
            }

        }
    }

    /**
     * 无重复字符的最长子串
     * 滑动窗口方法
     */
    public static int lengthOfLongestSubstring(String s) {
        int length = s.length();
        int ans = 0;
        int start = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int end = 0; end < length; end++) {
            char alpha = s.charAt(end);
            if (map.containsKey(alpha)) {
                start = Math.max(map.get(alpha), start);//此处必须这么写，保证start不会左移
            }
            ans = Math.max(ans, end - start + 1);
            map.put(s.charAt(end), end + 1);
        }
        return ans;
    }

    /**
     * 括号是否有效：
     * 有效 {}[]()
     * 无效 {}[(])
     *
     * @return
     */
    public static boolean isValidParentheses(String parentheses) {
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < parentheses.length(); i++) {
            char param = parentheses.charAt(i);

            if (map.containsKey(param)) {
                Character topElement = stack.empty() ? '#' : stack.pop();
                if (!topElement.equals(map.get(param))) {
                    return false;
                }
            } else {
                stack.push(param);
            }

        }
        return stack.empty();
    }
}
