package com.song.sunset.utils.algorithm;

/**
 * @author songmingwen
 * @description
 * @since 2020/7/30
 */
public class ListNode{

    public int value;

    public ListNode next;

    public ListNode(int value) {
        this.value = value;
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        ListNode end = dummy;

        while (end.next != null) {
            for (int i = 0; i < k && end != null; i++) end = end.next;
            if (end == null) break;
            ListNode start = pre.next;
            ListNode next = end.next;
            end.next = null;
            pre.next = reverse(start);
            start.next = next;
            pre = start;

            end = pre;
        }
        return dummy.next;
    }

    //链表反转
    public static ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }

    public static void printListNode(ListNode listNode){
        while (listNode != null) {
            System.out.print(" " + listNode.value);
            listNode = listNode.next;
        }
    }

}
