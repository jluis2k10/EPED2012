package ped.eped.tads;

import java.util.Arrays;

public class ListStatic<T> implements ListIF<T> {
	private Object[] elements;
	private int capacity;
	private int first;
	
	/**
	 * Constructor para ListStatic.
	 * 
	 * @param capacity el tamaño (fijo) de la lista.
	 */
	public ListStatic(int capacity) {
		this.capacity = capacity;
		this.first = capacity;
		this.elements = new Object[capacity];
	}
	
	/**
	 * Constructor alternativo para ListStatic en el cual
	 * la creamos ya con elementos de otra lista.
	 * 
	 * @param capacity el tamaño (fijo) de la lista.
	 * @param list los elementos de la lista.
	 */
	public ListStatic(int capacity, ListIF<T> list) {
		this(capacity);
		if(list != null) {
			ListIF<T> aList = list;
			for(int i = capacity - list.getLength(); i < capacity; i++) {
				this.elements[i] = aList.getFirst();
				this.first = this.first - 1;
				aList = aList.getTail();
			}
		}
	}
	
	/**
	 * Crea una copia de la lista.
	 * 
	 * @param list la lista a copiar.
	 * @return la lista copiada.
	 */
	private ListIF<T> copy(ListStatic<T> list) {
		ListStatic<T> l = new ListStatic<T>(capacity);
        l.first = list.first;

        // Esto podría hacerse con un arrayCopy() de java en su lugar.
        for(int i = list.first; i < list.capacity; i++)
             l.elements[i] = list.elements[i];
        return l;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getFirst() {
		if(isEmpty())
			return null;
		return (T)elements[first];
	}

	@Override
	public ListIF<T> getTail() {
		if(!isEmpty()) {
			ListStatic<T> tail = (ListStatic<T>) copy(this);
			tail.first = first + 1;
			return tail;
		}
		return this;
	}

	@Override
	public ListIF<T> insert(T element) {
		if(!isFull()) {
			first = first - 1;
			elements[first] = element;
		}
		return this;
	}

	@Override
	public boolean isEmpty() {
		return first == capacity;
	}

	@Override
	public boolean isFull() {
		return first == 0;
	}

	@Override
	public int getLength() {
		return capacity - first;
	}

	@Override
	public IteratorIF<T> getIterator() {
		ListIF<T> handler = new ListStatic<T>(capacity, this);
		return new ListIterator<T>(handler);
	}
	
	/**
	 * Devuelve cierto si la lista contiene el elemento.
	 * 
	 * @param element El elemento buscado.
	 * @return true si la lista contiene el elemento.
	 */
	@Override
	public boolean contains(T element) {
		IteratorIF<T> listIt = this.getIterator();
		boolean found = false;
		while(!found && listIt.hasNext()) {
			T anElement = listIt.getNext();
			found = anElement.equals(element);
		}
		return found;
	}

	/**
	 * Ordena los elementos de una lista (por mezcla).
	 * 
	 * @param comparator El comparador de elementos.
	 * @return la lista ordenada.
	 */
	@Override
	public ListIF<T> sort(ComparatorIF<T> comparator) {
		if(getLength() <= 1)
			return this;
		else {
			int middle = (getLength() / 2);
			int index = 0;
			ListIF<T> lLeft = new ListStatic<T>(middle);
			ListIF<T> lRight = new ListStatic<T>(getLength() - middle);
			IteratorIF<T> listIt = getIterator();
			while(listIt.hasNext()) {
				T element = listIt.getNext();
				if(index < middle)
					lLeft.insert(element);
				if(index >= middle)
					lRight.insert(element);
				index = index + 1;
			}
			lLeft = lLeft.sort(comparator);
			lRight = lRight.sort(comparator);
			return sortMerge(lLeft, lRight, comparator);
		}
	}
	
	/**
	 * Comprueba los elementos uno a uno de dos listas y devuelve otra lista ordenada
     * con los elementos de las dos listas.
	 * 
	 * @param lLeft La lista por la izquierda.
	 * @param lRight La lista por la derecha.
	 * @param comparator El comparador de elementos.
	 * @return La lista unida.
	 */
	private ListIF<T> sortMerge(ListIF<T> lLeft, ListIF<T> lRight, ComparatorIF<T> comparator) {
		while(lLeft.getLength() > 0 || lRight.getLength() > 0) {
			if (lLeft.getLength() > 0 && lRight.getLength() > 0) {
				T eLeft = lLeft.getFirst();
				T eRight = lRight.getFirst();
				if(comparator.isLess(eLeft, eRight)) {
					append(eLeft);
					lLeft = lLeft.getTail();
				} else {
					append(eRight);
					lRight = lRight.getTail();
				}
			} else if(lLeft.getLength() > 0) {
				T eLeft = lLeft.getFirst();
				append(eLeft);
				lLeft = lLeft.getTail();
			} else if(lRight.getLength() > 0) {
				T eRight = lRight.getFirst();
				append(eRight);
				lRight = lRight.getTail();
			}
		}
		return this;
	}

    @Override
    public int hashCode() {
        return 31 * 31 * ((elements == null) ? 0 : Arrays.hashCode(elements)) + 31 * capacity + first;
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
        if(this.capacity != ((ListStatic<T>)o).capacity)
            return false;
        if(isEmpty() && ((ListStatic<T>) o).isEmpty() && ((ListStatic<T>) o).capacity == capacity)
            // Ambas están vacías y tienen la misma capacidad
            return true;
        if(isEmpty() ^ ((ListStatic<T>) o).isEmpty())
            // Una de las dos está vacía
            return false;
        else {
            ListStatic<T> l = (ListStatic<T>) o;
            boolean sonIguales = true;
            for(int i = 0; i<capacity; i++) {
                sonIguales = sonIguales && elements[i].equals(l.elements[i]);
            }
            return sonIguales && l.first == first;
        }
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append("ListStatic - [");
        IteratorIF<T> listIt = getIterator();
        while(listIt.hasNext()) {
            T element = listIt.getNext();
            buff.append (element);
            if(listIt.hasNext())
                buff.append(", ");
        }
        buff.append("]");
        return buff.toString();
    }

    /**
     * Inserta un elemento en la lista por el final.
     *
     * @param element El elemento a insertar.
     * @return La lista con el elemento insertado.
     */
    private ListIF<T> append(T element) {
        if((element != null)) {
            if(isEmpty()) {
                insert(element);
                return this;
            }
            else {
                T head = getFirst();
                return ((ListStatic<T>) letTail()).append(element).insert(head);
            }
        }
        return this;
    }

    private ListIF<T> letTail() {
        if(!isEmpty()) {
            elements[first] = null;
            first = first + 1;
        }
        return this;
    }
}
