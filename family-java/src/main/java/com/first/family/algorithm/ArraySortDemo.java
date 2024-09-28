package com.first.family.algorithm;

import com.first.family.algorithm.common.MyUtil;

/**
 * @description: 排序算法
 * @author: cuiweiman
 * @date: 2023/10/24 10:23
 */
public class ArraySortDemo {

    public static void main(String[] args) {
        ArraySortDemo demo = new ArraySortDemo();
        // 冒泡排序
        int[] bubbleSort = demo.bubbleSort(new int[]{6, 5, 7, 1, 4, 2, 3});
        MyUtil.intArrPrint("bubbleSort", bubbleSort);
        // 选择排序
        int[] selectSort = demo.selectSort(new int[]{6, 5, 7, 1, 4, 2, 3});
        MyUtil.intArrPrint("selectSort", selectSort);
        // 快速排序
        int[] quickSort = demo.quickSort(new int[]{6, 5, 7, 1, 4, 2, 3});
        MyUtil.intArrPrint("quickSort", quickSort);
        int[] quick = demo.quick(new int[]{6, 5, 7, 1, 4, 2, 3});
        MyUtil.intArrPrint("quick", quick);
        // 堆排序
        int[] heapSort = demo.heapSort(new int[]{6, 5, 7, 1, 4, 2, 3});
        MyUtil.intArrPrint("heapSort", heapSort);
    }

    public int[] quick(int[] arr) {
        doQuick(arr, 0, arr.length - 1);
        return arr;
    }

    public void doQuick(int[] arr, int start, int stop) {
        if (start > stop) {
            return;
        }
        int left = start;
        int right = stop;
        int ptr = arr[left];
        while (left < right) {
            while (left < right && arr[right] > ptr) {
                right--;
            }
            arr[left] = arr[right];
            while (left < right && arr[left] < ptr) {
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = ptr;
        doQuick(arr, start, left - 1);
        doQuick(arr, right + 1, stop);
    }

    /**
     * 冒泡排序 O(n^2)
     * 思路：i = 0
     * j=0 6,5,7,1,4,2,3
     * j=1 5,6,7,1,4,2,3
     * j=2 5,6,1,7,4,2,3
     * j=3 5,6,1,4,7,2,3
     * j=4 5,6,1,4,2,7,3
     * j=5 5,6,1,4,2,3,7
     * j=6 == arr.length - 1, break, i =1
     * <p>
     * i = 0	[5, 6, 1, 4, 2, 3, 7]
     * i = 1	[5, 1, 4, 2, 3, 6, 7]
     * i = 2	[1, 4, 2, 3, 5, 6, 7]
     * i = 3	[1, 2, 3, 4, 5, 6, 7]
     * i = 4	[1, 2, 3, 4, 5, 6, 7]
     * i = 5	[1, 2, 3, 4, 5, 6, 7]
     */
    public int[] bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }

    /**
     * 简单选择排序 O(n^2)
     * 1. 在arr[0]到arr[n-1]中选出最小（大）的数与arr[0]交换位置;
     * 2. 在arr[1]到arr[n-1]中选出最小（大）的数与arr[1]交换位置;
     * ...
     * 3. 在arr[n-2]到arr[n-1]中选出最小（大）的的数与arr[n-2]交换位置;
     * 4. 遍历次数：n + n-1 + n-2 + … + 2 + 1 = n(n+1) / 2 = 0.5n^2 + 0.5n 。时间复杂度为 O(n^2)。
     */
    public int[] selectSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }


    /**
     * 快速排序 O(nlogn)
     * 数据规模较小时，因为递归不断的出栈入栈，耗费效率，性能可能不如 插入排序。
     * 因此JDK中数据规模小于 37 时，使用的是插入排序
     */
    public int[] quickSort(int[] arr) {
        doQuickSort(0, arr.length - 1, arr);
        return arr;
    }

    public void doQuickSort(int start, int stop, int[] arr) {
        // 记录初始的左右节点，递归时确定数组的左右边界
        int left = start;
        int right = stop;
        if (left < right) {
            // 确定左侧为基准
            int ptr = arr[left];
            while (left < right) {
                while (left < right && ptr < arr[right]) {
                    // 左基准和右侧比较
                    right--;
                }
                // left==right 或者 ptr > arr[right]，需要赋值了
                arr[left] = arr[right];
                // 此时基准数的位置还没有确定，还需要和左侧比较。
                while (left < right && ptr > arr[left]) {
                    // 和左侧比较时，顺序排列情况下，基准大则 left++
                    left++;
                }
                arr[right] = arr[left];
            }
            // 一轮比较结束，基准放入 left 或 right 位置(此时 left==right)。
            arr[left] = ptr;
            // MyUtil.intArrPrint(arr);
            doQuickSort(start, left - 1, arr);
            doQuickSort(left + 1, stop, arr);
        }
    }

    /**
     * 堆排序 O(nlogn)
     */
    public int[] heapSort(int[] arr) {
        int len = arr.length;
        for (int i = len / 2 - 1; i >= 0; i--) {
            // 从最后一个 非叶子节点 开始，构建大顶堆，由下至上。
            // 因为需要比较左右子节点，因此只能从最后一个非叶子节点开始
            buildHeap(arr, i, len);
        }
        // 交换大顶堆的根值和堆的未元素，排除顶元素（根元素成未元素了）重新开始构建堆。
        for (int i = len - 1; i > 0; i--) {
            // 随着 i--，不再处理数组未的有序元素。
            this.swap3(arr, 0, i);
            // 类似冒泡不断找到数组最大值，并移到最后。
            // 交换位置后，只需要判断交换位置的部分大小，其余都是按照大小顺序没变，
            // 因此大顶堆在首次构建好之后，不会再比较到最后一个元素了
            buildHeap(arr, 0, i);
        }
        return arr;
    }

    /**
     * 调节 一个索引树 的 父节点 和 左右子节点
     * 数组中，当前 节点i 的 左子节点为 2*i+1, 右子结点为 2*i+2
     * ....8          9
     * ../  \  -->  /  \
     * .6   9      6    8
     */
    private void buildHeap(int[] arr, int i, int len) {
        while (true) {
            int ptr = i;
            // 先比较左子节点，再比较右子节点，判断当前节点需要和哪个交换位置
            if (2 * i + 1 < len && arr[ptr] < arr[2 * i + 1]) {
                // 非叶子节点 值 小于 左子节点
                ptr = 2 * i + 1;
            }
            if (2 * i + 2 < len && arr[ptr] < arr[2 * i + 2]) {
                // 非叶子节点 值 小于 右子节点
                ptr = 2 * i + 2;
            }
            if (ptr == i) {
                // 当前节点不用交换位置
                break;
            }
            // i表示根索引，ptr表示需要交换位置的索引，左子节点或右子节点，需要进行交换位置
            swap3(arr, i, ptr);
            // ptr位置发生了元素改变，因此需要从ptr位置开始，继续进行堆构建
            i = ptr;
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void swap2(int[] arr, int i, int j) {
        arr[i] = arr[i] + arr[j];
        arr[j] = arr[i] - arr[j];
        arr[i] = arr[i] - arr[j];
    }

    private void swap3(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }


}
