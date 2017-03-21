package ped.eped.tads;

import java.util.Arrays;

public class QueueStatic<T> implements QueueIF<T> {
	private Object[] elements;
	private int capacity;
	private int first;
	private int last;
	
	/**
	 * Constructor para QueueStatic.
	 * @param capacity el tamaño de la cola.
	 */
	public QueueStatic(int capacity) {
		this.elements = new Object[capacity + 1];
		this.capacity = capacity + 1;
		this.first = 0;
		this.last = 0;
	}
	
	/**
	 * Constructor alternativo para QueueStatic.
	 * @param queue la cola a partir de la cual se crea.
	 * @param capacity el tamaño de la cola.
	 */
	public QueueStatic(QueueIF<T> queue, int capacity) {
		this(capacity);
		if(queue != null)
			for(int i = 0; i < queue.getLength(); i++) {
				T element = queue.getFirst();
				add(element);
				queue.remove();
				queue.add(element);
			}
	}
	
	/**
	 * Constructor alternativo para QueueStatic.
	 * @param list la lista a partir de la cual se crea.
	 * @param capacity el tamaño de la cola.
	 */
	public QueueStatic(ListIF<T> list, int capacity) {
		this(capacity);
		if(list != null) {
			ListIF<T> l = list;
			while (!l.isEmpty()) {
				add(l.getFirst());
				l = l.getTail();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getFirst() {
		if(isEmpty())
			return null;
		return (T)elements[first];
	}
	
	@Override
	public QueueIF<T> add(T element) {
		if(element != null) {
			if(!isFull()) {
				elements[last] = element;
				last = next(last);
			}
		}
		return this;
	}
	
	@Override
	public QueueIF<T> remove() {
		if(!isEmpty())
			first = next(first);
		return this;
	}
	
	@Override
	public boolean isEmpty() {
		return first == last;
	}
	
	@Override
	public boolean isFull() {
		return next(last) == first;
	}
	
	@Override
	public boolean contains(T element) {
		boolean found = false;
		int index = first;
		while(!found && Math.abs(last - index) > 0) {
			found = elements[index].equals(element);
			index = next(index);
		}
		return found;
	}
	
	@Override
	public IteratorIF<T> getIterator() {
		QueueIF<T> handler = new QueueStatic<T>(this, capacity);
		return new QueueIterator<T>(handler);
	}
	
	@Override
	public int getLength() {
		if(first <= last)
			return last - first;
		else
			return capacity - (first - last);
	}
	
	private int next(int index) {
		return (index + 1) % capacity;
	}
	
	@Override
	public int hashCode() {
		return 31 * 31 * ((elements == null) ? 0 : Arrays.hashCode(elements)) + 31 * capacity + 31 * first + 31 * last;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if(o == this)
			return true;
		if(o == null)
			return false;
		if(o.getClass() != this.getClass())
			return false;
		if(isEmpty() && ((QueueStatic<T>) o).isEmpty() && ((QueueStatic<T>) o).capacity == capacity)
			// Ambas colas están vacías y tienen la misma capacidad
			return true;
		if(isEmpty() ^ ((QueueStatic<T>) o).isEmpty())
			// Una de las dos listas está vacía, la otra no
			return false;
		else {
			QueueStatic<T> q = (QueueStatic<T>) o;
			boolean sonIguales = true;
			for(int i = 1; i<capacity; i++) {
				sonIguales = sonIguales && elements[i].equals(q.elements[i]);
			}
			return sonIguales;
		}
	}
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("QueueStatic - [");
		IteratorIF<T> queueIt = getIterator();
		while(queueIt.hasNext()) {
			buff.append(queueIt.getNext());
			if(queueIt.hasNext())
				buff.append(", ");
		}
		buff.append("]");
		return buff.toString();
	}
}
