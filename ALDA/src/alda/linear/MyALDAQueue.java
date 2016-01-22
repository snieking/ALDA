package alda.linear;

import java.util.Collection;
import java.util.Iterator;

public class MyALDAQueue<E> implements Iterable<E>, ALDAQueue<E> {
	
	private static class Node<T> {
		private final T data;
		private Node<T> next = null;
		private boolean hasMoved = false;
		
		public Node (T data) {
			this.data=data;
		}
		
		public T printData() {
			return data;
		}
		
		@Override
		public String toString() {
			return data.toString();
		}
	}
	
	private class QueueIterator implements java.util.Iterator<E> {
		private Node<E> current = head;
		private int expectedCap = capacity;
		private boolean canRemove = false;
		
		
		public QueueIterator() {
			
		}
		
		public Node<E> getCurrent() {
			return current;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public E next() {
			if(capacity != expectedCap)
				throw new java.util.ConcurrentModificationException();
			if(!hasNext())
				throw new java.util.NoSuchElementException();
			
			Node<E> toReturn = new Node<E>(current.data);

			current = current.next;
			canRemove = true;
			return toReturn.data;
		}
		
		public void remove() { }
		
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
	public void add(E element) { 															// Inspiration från: http://codereview.stackexchange.com/questions/62746/queue-implementation-using-a-linked-list
		if(element == null)
			throw new java.lang.NullPointerException();
		
		if(currentSize == capacity)
			throw new java.lang.IllegalStateException();
		
		Node<E> n = new Node<E>(element);
		if (isEmpty()) {
			head = n;
			tail = n;
			tail.next = null;
			currentSize++;
		} else {
			tail.next = n;
			tail = n;
			tail.next = null;
			currentSize++;
		}
		
	}

	@Override
	public void addAll(Collection<? extends E> c) {
		if(c == null)
			throw new java.lang.NullPointerException();
		
		for(E element : c) {
			add(element);
		}
		
	}

	@Override
	public E remove() {
		if(isEmpty())
			throw new java.util.NoSuchElementException();
		
		Node<E> toRemove = head;
		head = head.next;
		currentSize--;
		return toRemove.printData();
	}

	@Override
	public E peek() {
		if(head != null)
			return (E) head.printData();
		else
			return null;
	}

	@Override
	public void clear() {
		head = null;
		tail = null;
		currentSize = 0;
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
		if(e == null)
			throw new java.lang.NullPointerException();
		
		int timesMoved = 0;
		
		Node<E> compareNode = new Node<E>(e);
		

		Node<E> backupHead = head;
		Node<E> backupTail = tail;
		clear();
		
		Node<E> inspecting = backupHead;
		
		while(inspecting != null) {
			if(inspecting.hasMoved == false && inspecting.printData().equals(compareNode.printData())) {
				add(inspecting.data);
				inspecting.hasMoved = true;
				timesMoved++;
			} else {
				inspecting = inspecting.next;
			}
		}

		Node<E> tempHead = head;
		head = backupHead;
		tail = backupTail;
		
		currentSize = 0;
		
		Node<E> notSearchingFor = head;
		while(notSearchingFor != null) {
			if(notSearchingFor.hasMoved == false && !(notSearchingFor.printData().equals(compareNode.printData()))) {
				add(notSearchingFor.data);
				notSearchingFor.hasMoved = true;
			} else
				notSearchingFor = notSearchingFor.next;
		}
		
		Node<E> temp = tempHead;
		while(temp != null) {
			add(temp.data);
			temp = temp.next;
		}
		
		return timesMoved;
	}
	
	
	@Override
	public String toString() {
		String listString = "[";
		
		QueueIterator iter = (MyALDAQueue<E>.QueueIterator) iterator();
		while(iter.getCurrent() != null) {
			listString += ((QueueIterator) iter).getCurrent().printData();
			iter.next();
			if(iter.getCurrent() != null)
				listString += ", ";
		}
		listString += "]";
		return listString; 
	} 



}
