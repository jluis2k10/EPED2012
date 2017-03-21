package ped.eped.tads;

import java.util.Arrays;

public class StackStatic<T> implements StackIF<T> {
	private Object[] elements;
	private int capacity;
	private int top;
	
	/**
	 * Constructor para StackStatic.
	 * @param capacity el número de elementos que puede albergar
	 * la pila.
	 */
	public StackStatic(int capacity) {
		this.elements = new Object[capacity + 1];
		this.capacity = capacity + 1;
		this.top = 0;
	}
	
	/**
	 * Constructor adicional para StackStatic.
	 * @param stack otra pila.
	 * @param capacity el número de elementos de la pila.
	 */
	@SuppressWarnings("unchecked")
	public StackStatic(StackIF<T> stack, int capacity) {
		this(capacity);
		if(stack != null) {
			for(int i = stack.getLength(); i > 0; i--) {
				T element = stack.getTop();
				elements[i] = element;
				stack.pop();
				top = top + 1;
			}
			for(int i = 1; i <= getLength(); i++) {
				T element = (T)elements[i];
				stack.push(element);
			}
		}
	}
	
	/**
	 * Constructor adicional para StackStatic, donde creamos
	 * una pila a partir de una lista.
	 * @param list la lista de origen.
	 * @param capacity el número de elementos de la pila.
	 */
	public StackStatic(ListIF<T> list, int capacity) {
		this(capacity);
		if(list != null) {
			ListIF<T> aList = list;
			for(int i = 0; i < list.getLength(); i++) {
				this.elements[i] = aList.getFirst();
				aList = list.getTail();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getTop() {
		if(isEmpty())
			return null;
		return (T)elements[top];
	}

	@Override
	public StackIF<T> push(T element) {
		if(element != null)
			if(!isFull()) {
				top = top + 1;
				elements[top] = element;
			}
		return this;
	}

	@Override
	public StackIF<T> pop() {
		if(!isEmpty())
			top = top - 1;
		return this;
	}

	@Override
	public boolean isEmpty() {
		return top == 0;
	}

	@Override
	public boolean isFull() {
		return top == (capacity - 1);
	}

	@Override
	public int getLength() {
		return top;
	}

	@Override
	public boolean contains(T element) {
		IteratorIF<T> stackIt = this.getIterator();
		boolean found = false;
		while(!found && stackIt.hasNext()) {
			T anElement = stackIt.getNext();
			found = anElement.equals(element);
		}
		return found;
	}

	@Override
	public IteratorIF<T> getIterator() {
		StackIF<T> handler = new StackStatic<T>(this, capacity);
		return new StackIterator<T>(handler);
	}
	
	@Override
	public int hashCode() {
		return 31 * 31 * ((elements == null) ? 0 : Arrays.hashCode(elements)) + 31 * capacity + top;
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
        if(this.capacity != ((StackStatic<T>)o).capacity)
            return false;
        if(this.isEmpty() && ((StackStatic<T>)o).isEmpty() && this.capacity == (
                (StackStatic<T>)o).capacity)
            return true;
        if(this.isEmpty() ^ ((StackStatic<T>)o).isEmpty())
            return false;
		else {
			StackStatic<T> s = (StackStatic<T>) o;
            boolean sonIguales = true;
            for(int i = 0; i<capacity; i++) {
                sonIguales = sonIguales && elements[i].equals(s.elements[i]);
            }
            return sonIguales && this.top == s.top;
		}
	}
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("StackStatic - [");
		IteratorIF<T> stackIt = getIterator();
		while(stackIt.hasNext()) {
			T element = stackIt.getNext();
			buff.append(element);
			if(stackIt.hasNext())
				buff.append(", ");
		}
		buff.append("]");
		return buff.toString();
	}
}
