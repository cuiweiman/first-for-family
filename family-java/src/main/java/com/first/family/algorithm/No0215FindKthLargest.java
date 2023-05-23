package com.first.family.algorithm;

/**
 * 215. 数组中的第K个最大元素
 *
 * @description: https://leetcode.cn/problems/kth-largest-element-in-an-array/
 * @author: cuiweiman
 * @date: 2022/11/18 16:24
 */
public class No0215FindKthLargest {

    public static void main(String[] args) {
        No0215FindKthLargest demo = new No0215FindKthLargest();
        int[] nums = {3, 2, 1, 5, 6, 4};
        int k = 2;
        int kthLargest = demo.findKthLargest(nums, k);
        System.out.println(kthLargest);
    }

    public int findKthLargest(int[] nums, int k) {
        // Arrays.sort(nums);
        // nums = quickSort(nums, 0, nums.length - 1);
        heapSort(nums);
        return nums[nums.length - k];
    }

    /**
     * 时间复杂度 O(nlogn)
     * 空间复杂度 O(logn)
     */
    public int[] quickSort(int[] nums, int left, int right) {
        if (left < right) {
            int partition = partition(nums, left, right);
            quickSort(nums, left, partition - 1);
            quickSort(nums, partition + 1, right);
        }
        return nums;
    }

    public int partition(int[] nums, int left, int right) {
        int pivot = nums[left];
        while (left < right) {
            while (left < right && nums[right] > pivot) {
                right--;
            }
            nums[left] = nums[right];
            while (left < right && pivot >= nums[left]) {
                left++;
            }
            nums[right] = nums[left];
        }
        nums[left] = pivot;
        return left;
    }



    /*
     * 堆排序
     */

    public void heapSort(int[] arr) {
        int len = arr.length;
        for (int i = len / 2 - 1; i >= 0; i--) {
            // 首先从 最后一个 非叶子节点 开始，进行 大根堆 的构建，由下至上。
            buildHeap(arr, i, len);
        }
        // 先交换 根值 和 尾元素(因为在这之前已经是 大根堆)，再从 arr[len - 1] 开始，重新构建 大根堆
        for (int i = len - 1; i >= 0; i--) {
            swap(arr, 0, i);
            // 注意，这里 的 i 值为 数组长度，一直在递减，表示每次构建大根堆时都筛掉旧根值
            buildHeap(arr, 0, i);
        }
    }

    /**
     * 调节 一个索引树 的 父节点 和 左右子节点
     * ....8          9
     * ../  \  -->  /  \
     * .6   9      6    8
     */
    public void buildHeap(int[] arr, int i, int len) {
        while (true) {
            int maxPos = i;
            // 先从 左子节点 开始比较，注意判断阈值
            if (2 * i + 1 < len && arr[maxPos] < arr[2 * i + 1]) {
                // 父节点小于左子节点，暂时记录需要交换的 下标值
                maxPos = 2 * i + 1;
            }
            if (2 * i + 2 < len && arr[maxPos] < arr[2 * i + 2]) {
                // 父节点小于右子节点，暂时记录需要交换的 下标值
                maxPos = 2 * i + 2;
            }
            if (maxPos == i) {
                // 表示 maxPos 父节点 > 左右子节点
                break;
            }
            // 交换
            swap(arr, i, maxPos);
            // 交换结束后，i索引 表示的是 旧的根值(最大数), maxPos索引 是 最小数，
            // 因此需要将 i 赋值成 maxPos，继续重头开始
            i = maxPos;
        }
    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
