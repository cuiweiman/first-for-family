package com.first.family.algorithm.common;

import java.util.List;

/**
 * @description: 层序遍历时的树节点
 * @author: cuiweiman
 * @date: 2023/10/14 22:31
 */
public class Node {

    public int val;
    public List<Node> children;

    public Node() {
    }

    public Node(int val) {
        this.val = val;
    }

    public Node(int val, List<Node> children) {
        this.val = val;
        this.children = children;
    }
}
