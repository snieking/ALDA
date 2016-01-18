package alda.linear;

import java.util.Collection;
import java.util.Iterator;

public class MyALDAQueue<E> implements ALDAQueue<E> {
	
	private static class Node<T> {
		private final T data;
		private Node<T> next;
		
		public Node (T data) {
			this.data=data;
		}
		
		public void displayNode() {
			System.out.println(data + " ");
		}
	}
	
	private Node<E> first;
	private Node<E> last;
	private int capacity;
	private int currentSize = 0;
	
	public MyALDAQueue(int cap) {
		if(cap <= 0)
			throw new IllegalArgumentException();
		this.capacity = cap;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(E element) { 						// Inspiration från: http://codereview.stackexchange.com/questions/62746/queue-implementation-using-a-linked-list
		Node<E> n = new Node<E>(element);
		if (isEmpty()) {
			n.next = first;
			first = n;
			last = n;
		} else {
			last.next = n;
			last = n;
			last.next = null;
		}
		
	}

	@Override
	public void addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public E remove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E peek() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int size() {
		return currentSize;
	}

	@Override
	public boolean isEmpty() {
		if(currentSize == 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean isFull() {
		if(currentSize == capacity)
			return true;
		else
			return false;
	}

	@Override
	public int totalCapacity() {
		return capacity;
	}

	@Override
	public int currentCapacity() {
		return capacity - currentSize;
	}

	@Override
	public int discriminate(E e) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	@Override
	public String toString() {
		// TODO Iterate through list
		return "";
	}



}
