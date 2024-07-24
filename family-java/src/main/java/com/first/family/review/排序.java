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
        MyUtil.intArrPrint("heapSort2", demo.heapSort2(new int[]{16, 52, 9, 12, 30}));
        MyUtil.intArrPrint("quickSort2", demo.quickSort2(new int[]{16, 52, 9, 12, 30}));
    }

    public int[] quickSort2(int[] arr) {
        doQuickSort2(arr, 0, arr.length - 1);
        return arr;
    }

    private void doQuickSort2(int[] arr, int start, int stop) {
        int left = start;
        int right = stop;
        if (left >= right) {
            return;
        }
        // 从最左 挖坑
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
        doQuickSort2(arr, start, left - 1);
        doQuickSort2(arr, left + 1, stop);
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

    public int[] heapSort2(int[] arr) {
        int len = arr.length;
        // 从 第一个 非叶子节点 构建 大顶推: 递增
        for (int i = len / 2 - 1; i >= 0; i--) {
            buildHeap2(arr, i, len);
        }
        for (int i = len - 1; i > 0; i--) {
            // 交换定点位置
            arr[0] = arr[0] ^ arr[i];
            arr[i] = arr[0] ^ arr[i];
            arr[0] = arr[0] ^ arr[i];
            buildHeap2(arr, 0, i);
        }
        return arr;
    }

    private void buildHeap2(int[] arr, int i, int len) {
        while (true) {
            int ptr = i;
            // 父节点 i 存在 左子节点，且 父节点 i 小于 左子节点
            if (2 * i + 1 < len && arr[ptr] < arr[2 * i + 1]) {
                // 左子节点 赋值给 父节点
                ptr = 2 * i + 1;
            }
            // 父节点 i 存在 右子节点 且 父节点 i 小于 右子结点
            if (2 * i + 2 < len && arr[ptr] < arr[2 * i + 2]) {
                ptr = 2 * i + 2;
            }
            // 父节点 不存在 左右子节点 或者 父节点 比 左右子节点 大
            if (ptr == i) {
                break;
            }
            arr[i] = arr[i] ^ arr[ptr];
            arr[ptr] = arr[i] ^ arr[ptr];
            arr[i] = arr[i] ^ arr[ptr];
            i = ptr;
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
