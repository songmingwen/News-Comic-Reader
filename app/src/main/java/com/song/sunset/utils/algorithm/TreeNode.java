package com.song.sunset.utils.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author songmingwen
 * @description
 * @since 2020/1/13
 */
public class TreeNode {

    public int value;

    public TreeNode leftChild;

    public TreeNode rightChild;

    /**
     * 前序遍历：前序遍历首先访问根结点然后遍历左子树，最后遍历右子树
     */
    public static void preOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.value + ",");
        preOrder(node.leftChild);
        preOrder(node.rightChild);
    }

    public static void preOrderWithOutRecursive(TreeNode node) {
        Stack<TreeNode> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                System.out.print(node.value + ",");
                stack.push(node);
                node = node.leftChild;
            } else {
                node = stack.pop();
                node = node.rightChild;
            }
        }
    }

    /**
     * 中序遍历：中序遍历首先遍历左子树，然后访问根结点，最后遍历右子树
     */
    public static void inOrderTraversal(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.leftChild);
        System.out.print(node.value + ",");
        inOrderTraversal(node.rightChild);
    }

    public static void inOrderTraversalWithOutRecursive(TreeNode node) {
        Stack<TreeNode> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.leftChild;
            } else {
                node = stack.pop();
                System.out.print(node.value + ",");
                node = node.rightChild;
            }
        }
    }

    /**
     * 后序遍历：首先遍历左子树，然后遍历右子树，最后访问根结点
     */
    public static void postOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        postOrder(node.leftChild);
        postOrder(node.rightChild);
        System.out.print(node.value + ",");
    }

    public static void postOrderWithOutRecursive(TreeNode node) {
        Stack<TreeNode> stack = new Stack<>();
        Map<Integer, Integer> map = new HashMap<>();
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                map.put(node.value, 1); //node.data 标记这个值节点出现的次数
                node = node.leftChild;
            } else {
                node = stack.peek();
                if (map.get(node.value) == 2) {//第二次访问，抛出
                    stack.pop();
                    System.out.print(node.value + ",");
                    node = null;//需要往上走
                } else {
                    map.put(node.value, 2);
                    node = node.rightChild;
                }

            }
        }
    }

}
