package com.first.family.review;

import com.first.family.algorithm.common.MyUtil;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2024/1/23 11:30
 */
public class 排序 {
    public static void main(String[] args) {
        排序 demo = new 排序();
        MyUtil.intArrPrint("HeapSort", demo.heapSort(new int[]{16, 52, 9, 12, 30}));
        MyUtil.intArrPrint("QuickSort", demo.quickSort(new int[]{16, 52, 9, 12, 30}));
    }

    public int[] quickSort(int[] arr) {
        doQuickSort(0, arr.length - 1, arr);
        return arr;
    }

    public void doQuickSort(int start, int stop, int[] arr) {
        int left = start;
        int right = stop;
        if (left < right) {
            int ptr = arr[left];
            while (left < right) {
                while (left < right && ptr < arr[right]) {
                    right--;
                }
                arr[left] = arr[right];
                while (left < right && ptr > arr[left]) {
                    left++;
                }
                arr[right] = arr[left];
            }
            arr[left] = ptr;
            doQuickSort(start, left - 1, arr);
            doQuickSort(left + 1, stop, arr);
        }
    }

    public int[] heapSort(int[] arr) {
        // 大顶堆-顺序排序
        int len = arr.length;
        // 从 最后一个非叶子节点 开始构造堆
        for (int i = len / 2 - 1; i >= 0; i--) {
            buildHeap(arr, i, len);
        }
        for (int i = len - 1; i > 0; i--) {
            swap(arr, 0, i);
            buildHeap(arr, 0, i);
        }
        return arr;
    }

    public void buildHeap(int[] arr, int i, int len) {
        while (true) {
            int ptr = i;
            if (2 * i + 1 < len && arr[ptr] < arr[2 * i + 1]) {
                ptr = 2 * i + 1;
            }
            if (2 * i + 2 < len && arr[ptr] < arr[2 * i + 2]) {
                ptr = 2 * i + 2;
            }
            if (ptr == i) {
                break;
            }
            swap(arr, i, ptr);
            i = ptr;
        }
    }

    public void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] + arr[j];
        arr[j] = arr[i] - arr[j];
        arr[i] = arr[i] - arr[j];
    }


}
