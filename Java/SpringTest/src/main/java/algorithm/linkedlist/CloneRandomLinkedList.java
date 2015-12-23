package algorithm.linkedlist;

import java.util.HashMap;

public class CloneRandomLinkedList {

	/*
	 * A linked list is given such that each node contains an additional random
	 * pointer which could point to any node in the list or null.
	 * 
	 * Return a deep copy of the list.
	 */

	public Node clone(Node head) {
		if (head == null)
			return null;
		
		HashMap<Node, Node> map = new HashMap<Node, Node>();
		Node clone_h = new Node(head.val);

		Node origin_p = head;
		Node clone_p = clone_h;
		
		map.put(head, clone_h);
		origin_p = origin_p.next;
		
		while (origin_p != null) {
			Node newNode = new Node(origin_p.val);
			map.put(origin_p, newNode);
			clone_p.next = newNode;
			clone_p = newNode;
			origin_p = origin_p.next;
		}

		origin_p = head;
		clone_p = clone_h;
		while (origin_p != null) {
			if (origin_p.random != null)
				clone_p.random = map.get(origin_p.random);
			else
				clone_p.random = null;

			origin_p = origin_p.next;
			clone_p = clone_p.next;
		}

		return clone_h;
	}

	public static void main(String args[]) {
		CloneRandomLinkedList obj = new CloneRandomLinkedList();
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Node node5 = new Node(5);

		node1.next = node2;
		node1.random = node3;
		node2.next = node3;
		node2.random = node4;
		node3.next = node4;
		node3.random = node1;
		node4.next = node5;
		node4.random = node4;
		node5.next = null;
		node5.random = null;

		Node originHead = node1;
		Node cloneHead = obj.clone(originHead);
		System.out.println("------------------------------");
		obj.check(originHead, cloneHead);

	}

	public void check(Node origin, Node clone) {

		while (origin != null) {

			if (origin.next != null && clone.next != null
					&& origin.next == clone.next)
				throw new RuntimeException("Pointing the same object");
			if (origin.random != null && clone.random != null
					&& origin.random == clone.random)
				throw new RuntimeException("Pointing the same object");

			System.out.println(clone.val);
			origin = origin.next;
			clone = clone.next;
		}

	}

}
