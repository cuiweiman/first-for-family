package com.first.family.algorithm.common;

import java.util.ArrayList;
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

    public static Node levelNode() {
        Node node7 = new Node(7);
        List<Node> node5Children = new ArrayList<>();
        node5Children.add(node7);
        Node node5 = new Node(5, node5Children);
        Node node4 = new Node(4);

        List<Node> node2Children = new ArrayList<>();
        node2Children.add(node4);
        node2Children.add(node5);
        Node node2 = new Node(2, node2Children);

        Node node6 = new Node(6);
        List<Node> node3Children = new ArrayList<>();
        node3Children.add(node6);
        Node node3 = new Node(3, node3Children);

        List<Node> nodeRootChildren = new ArrayList<>();
        nodeRootChildren.add(node2);
        nodeRootChildren.add(node3);
        return new Node(1, nodeRootChildren);
    }
}
