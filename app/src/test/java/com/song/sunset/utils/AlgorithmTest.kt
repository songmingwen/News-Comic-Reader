package com.song.sunset.utils

import com.song.sunset.utils.algorithm.Algorithm
import com.song.sunset.utils.algorithm.AlgorithmKt
import com.song.sunset.utils.algorithm.TreeNode
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Before

/**
 * @author songmingwen
 * @description
 * @since 2019/12/11
 */
class AlgorithmTest {

    @Test
    fun selectionSort() {
        println("selectionSort")
        val array = AlgorithmKt.generateRandomArray(30000, 0, 10000)
        val start = System.currentTimeMillis()
        var end: Long
        Algorithm.selectionSort(array).apply {
            assertTrue(AlgorithmKt.isSorted(this))
            end = System.currentTimeMillis()
        }.forEach {
            print("$it,")
        }
        println("\ntime = " + (end - start))
    }

    @Test
    fun insertSort() {
        println("insertSort")
        val array = AlgorithmKt.generateRandomArray(30000, 0, 10000)
        val start = System.currentTimeMillis()
        var end: Long
        Algorithm.insertSort(array).apply {
            assertTrue(AlgorithmKt.isSorted(this))
            end = System.currentTimeMillis()
        }.forEach {
            print("$it,")
        }
        println("\ntime = " + (end - start))
    }

    @Test
    fun bubbleSort() {
        println("bubbleSort")
        val array = AlgorithmKt.generateRandomArray(30000, 0, 10000)
        val start = System.currentTimeMillis()
        var end: Long
        Algorithm.bubbleSort(array).apply {
            assertTrue(AlgorithmKt.isSorted(this))
            end = System.currentTimeMillis()
        }.forEach {
            print("$it,")
        }
        println("\ntime = " + (end - start))
    }

    @Test
    fun mergeSort() {
        println("mergeSort")
        val array = AlgorithmKt.generateRandomArray(1000000, 0, 100000)
        val start = System.currentTimeMillis()
        Algorithm.mergeSort(array, 0, array.size - 1)
        assertTrue(AlgorithmKt.isSorted(array))
        val end: Long = System.currentTimeMillis()
        println("time = " + (end - start))

    }

    @Test
    fun mergeSortFromBottomToUp() {
        println("mergeSort")
        val array = AlgorithmKt.generateRandomArray(30000, 0, 10000)
        val start = System.currentTimeMillis()
        Algorithm.mergeSortFromBottomToUp(array, array.size)
        assertTrue(AlgorithmKt.isSorted(array))
        val end: Long = System.currentTimeMillis()
        array.forEach { print("$it,") }
        println("\ntime = " + (end - start))
    }

    @Test
    fun quickSort() {
        println("quickSort")
        val array = AlgorithmKt.generateRandomArray(1000000, 0, 100000)
        val start = System.currentTimeMillis()
        Algorithm.quickSort(array)
        assertTrue(AlgorithmKt.isSorted(array))
        val end: Long = System.currentTimeMillis()
        println("time = " + (end - start))
    }

    @Test
    fun lengthOfLongestSubString() {
        println(Algorithm.lengthOfLongestSubstring("abcabcdabfhuacvbn"))
    }

    @Test
    fun isValidParentheses() {
        println(Algorithm.isValidParentheses("{{}[][()]}"))
    }

    private lateinit var rootNode: TreeNode

    @Before
    fun createNodeTree() {
        val node1 = TreeNode()
        node1.value = 1
        val node2 = TreeNode()
        node2.value = 2
        val node3 = TreeNode()
        node3.value = 3
        val node4 = TreeNode()
        node4.value = 4
        val node5 = TreeNode()
        node5.value = 5
        val node6 = TreeNode()
        node6.value = 6
        val node7 = TreeNode()
        node7.value = 7

        node2.leftChild = node4
        node2.rightChild = node5

        node3.leftChild = node6
        node3.rightChild = node7

        node1.leftChild = node2
        node1.rightChild = node3

        rootNode = node1
    }

    @Test
    fun treeOrder() {
        println("前序")
        TreeNode.preOrder(rootNode)
        println()
        TreeNode.preOrderWithOutRecursive(rootNode)

        println("\n中序")
        TreeNode.inOrderTraversal(rootNode)
        println()
        TreeNode.inOrderTraversalWithOutRecursive(rootNode)

        println("\n后序")
        TreeNode.postOrder(rootNode)
        println()
        TreeNode.postOrderWithOutRecursive(rootNode)
    }

}