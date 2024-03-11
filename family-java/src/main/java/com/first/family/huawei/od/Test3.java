package com.first.family.huawei.od;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2024/3/9 20:53
 */
public class Test3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        String[] split = line.split(" ");
        String[] split0 = split[0].split("");
        String[] split1 = split[1].split("");
        if (split[0].length() < split[1].length()) {
            split0 = split[1].split("");
            split1 = split[0].split("");
        }

        int[][] array = new int[split0.length][split1.length];
        boolean[][] visited = new boolean[split0.length][split1.length];
        // ABCABBA CBABAC 3
        int distance = distance(array, visited, split0, split1, split0.length, split1.length);
        System.out.println(distance);

    }


    public static int distance(int[][] array, boolean[][] visited, String[] split0, String[] split1, int M, int N) {
        int result = 1;
        int x = 0;
        int y = 0;
        while (x < M && y < N) {
            if (Objects.equals(split0[x], split1[y])) {
                x++;
                y++;
                result++;
            }
        }

        return new Random().nextInt(10);
    }


}
