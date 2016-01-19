package alda.linear;

import java.util.Collection;
import java.util.Iterator;

public class MyALDAQueue<E> implements Iterable<E>, ALDAQueue<E> {
	
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
	
	private class QueueIterator implements java.util.Iterator<E> {
		private Node<E> current = head.next;
		private int expectedCap = capacity;
		private boolean canRemove = false;
		
		public Node<E> getCurrent() {
			return current;
		}

		@Override
		public boolean hasNext() {
			return current != tail;
		}

		@Override
		public E next() {
			if(capacity != expectedCap)
				throw new java.util.ConcurrentModificationException();
			if(!hasNext())
				throw new java.util.NoSuchElementException();
			
			E nextElement = current.data;
			current = current.next;
			canRemove = true;
			return nextElement;
		}
		
		public void remove() {
			if(capacity != expectedCap)
				throw new java.util.ConcurrentModificationException();
			if(!canRemove)
				throw new IllegalStateException();
			
			// TODO: Implement remove
		}
		
	}
	
	private Node<E> head;
	private Node<E> tail;
	private int capacity;
	private int currentSize = 0;
	
	public MyALDAQueue(int cap) {
		if(cap <= 0)
			throw new IllegalArgumentException();
		this.capacity = cap;
	}

	@Override
	public Iterator<E> iterator() {
		return new QueueIterator();
	}

	@Override
	public void add(E element) { 						// Inspiration från: http://codereview.stackexchange.com/questions/62746/queue-implementation-using-a-linked-list
		Node<E> n = new Node<E>(element);
		if (isEmpty()) {
			n.next = head;
			head = n;
			tail = n;
		} else {
			tail.next = n;
			tail = n;
			tail.next = null;
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
		String listString = "";
		Iterator<E> iter = iterator();
		while(iter.hasNext()) {
			listString += ((QueueIterator) iter).getCurrent();
			iter.next();
		}
		return listString;
	}



}
