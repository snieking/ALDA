package alda.linear;

import java.util.Collection;
import java.util.Iterator;

public class MyALDAQueue<E> implements Iterable<E>, ALDAQueue<E> {
	
	private static class Node<T> {
		private final T data;
		private Node<T> next = null;
		
		public Node (T data) {
			this.data=data;
		}
		
		public void displayNode() {
			System.out.println(data + " ");
		}
		
		public String toString() {
			return "" + data;
		}
	}
	
	private class QueueIterator implements java.util.Iterator<E> {
		private Node<E> current = head;
		private int expectedCap = capacity;
		private boolean canRemove = false;
		
		/*
		private Node<E> setNext() {
			System.out.println("Ska sätta next...");
			Node<E> iterNext = null;
			if(head.next != null && currentSize > 0)
				iterNext = head.next;
			
			System.out.println("Satte next...");
			return iterNext;	
		} */
		
		public QueueIterator() {
			
		}
		
		public Node<E> getCurrent() {
			return current;
		}

		@Override
		public boolean hasNext() {
			return current != tail;
		}

		@Override
		public E next() {
			System.out.println("Will try next");
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
		QueueIterator iter = new QueueIterator();
		return iter;
	}

	@Override
	public void add(E element) { 						// Inspiration från: http://codereview.stackexchange.com/questions/62746/queue-implementation-using-a-linked-list
		if(element == null)
			throw new java.lang.NullPointerException();
		
		Node<E> n = new Node<E>(element);
		if (isEmpty()) {
			n.next = head;
			head = n;
			tail = n;
			currentSize++;
		} else if(currentSize < 8){
			tail.next = n;
			tail = n;
			tail.next = null;
			currentSize++;
		}
		
	}

	@Override
	public void addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public E remove() {
		if(isEmpty())
			throw new java.util.NoSuchElementException();
		
		Node<E> toRemove = head;
		head = head.next;
		return (E) toRemove;
	}

	@Override
	public E peek() {
		return (E) head;
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
		String listString = "[";
		
		QueueIterator iter = (MyALDAQueue<E>.QueueIterator) iterator();
		while(iter.getCurrent() != null) {
			listString += ((QueueIterator) iter).getCurrent();
			iter.next();
		}
		listString += "]";
		return listString; 
	} 



}
