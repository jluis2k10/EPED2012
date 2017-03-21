package ped.eped.tads;

public class ListDynamic<T> implements ListIF<T> {
	protected T first;
	protected ListIF<T> tail;
	
	public ListDynamic() {
		first = null;
		tail = null;
	}
	
	public ListDynamic(ListIF<T> list) {
		this();
		if(list != null) {
			if(!list.isEmpty()) {
				first = list.getFirst();
				tail = new ListDynamic<T>(list.getTail());
			}
		}
	}
	
	@Override
	public T getFirst() {
		return first;
	}
	
	@Override
	public ListIF<T> getTail() {
		if (isEmpty())
			return this;
		return tail;
	}
	
	@Override
	public ListIF<T> insert(T element) {
		if(element != null) {
			ListDynamic<T> next = new ListDynamic<T>();
			next.first = first;
			next.tail = tail;
			first = element;
			tail = next;
		}
		return this;
	}
	
	/**
	 * Añade un elemento al final de la lista.
	 * 
	 * @param element El elemento a añadir.
	 * @return La lista.
	 */
	private ListIF<T> append(T element) {
		if(element != null) {
			if(isEmpty()) {
				insert(element);
				return this;
			}
			else {
				return (((ListDynamic<T>) tail).append(element));
			}
		}
		return this;
	}
	
	@Override
	public boolean isEmpty() {
		return first == null && tail == null;
	}
	
	@Override
	public boolean isFull() {
		return false;
	}
	
	@Override
	public int getLength() {
		if(isEmpty())
			return 0;
		else
			return 1 + tail.getLength();
	}
	
	@Override
	public IteratorIF<T> getIterator() {
		ListIF<T> handler = new ListDynamic<T>(this);
		return new ListIterator<T>(handler);
	}
	
	/**
	 * Devuelve cierto si la lista contiene el elemento.
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
			int middle = getLength() / 2;
			int index = 0;
			ListIF<T> lLeft = new ListDynamic<T>();
			ListIF<T> lRight = new ListDynamic<T>();
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
	 * Une dos listas.
	 * 
	 * @param lLeft La lista por la izquierda.
	 * @param lRight La lista por la derecha.
	 * @param comparator El comparador de elementos.
	 * @return La lista unida.
	 */
	private ListIF<T> sortMerge(ListIF<T> lLeft, ListIF<T> lRight, ComparatorIF<T> comparator) {
		//ListStatic<T> result = new ListStatic<T>(capacity);
		// TODO: rehacerlo con result (poner result.append() a los append() que hay, y preguntar en
		// el foro porqué no funciona sin asignación.
		// lista.sort(); no funciona
		// lista = (ListaStatic<Objeto>) lista.sort(); si funciona
		ListDynamic<T> aList = new ListDynamic<T>();
		while(lLeft.getLength() > 0 || lRight.getLength() > 0) {
			if (lLeft.getLength() > 0 && lRight.getLength() > 0) {
				T eLeft = lLeft.getFirst();
				T eRight = lRight.getFirst();
				if(comparator.isLess(eLeft, eRight)) {
					aList.append(eLeft);
					lLeft = lLeft.getTail();
				} else {
					aList.append(eRight);
					lRight = lRight.getTail();
				}
			} else if(lLeft.getLength() > 0) {
				T eLeft = lLeft.getFirst();
				aList.append(eLeft);
				lLeft = lLeft.getTail();
			} else if(lRight.getLength() > 0) {
				T eRight = lRight.getFirst();
				aList.append(eRight);
				lRight = lRight.getTail();
			}
		}
		first = aList.getFirst();
		tail = aList.getTail();
		return this;
	}
	
	@Override
	public int hashCode() {
		return 31 * 31 * ((first == null) ? 0 : first.hashCode()) + 31 * 31 * ((tail == null) ? 0 : tail.hashCode());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals (Object o) {
		if(o == this)
			return true;
		if(o == null)
			return true;		
		if(!(o instanceof ListDynamic))
			return false;
		if(isEmpty() && ((ListDynamic<T>) o).isEmpty())
			return true;
		if(isEmpty() ^ ((ListDynamic<T>) o).isEmpty()) // ^ es un XOR logico, devuelve 1 si ambos son diferentes
			return false;
		else {
			ListDynamic<T> l = (ListDynamic<T>) o;
			return l.first.equals(first) && l.tail.equals(tail);
		}
	}
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("ListDynamic - [");
		
		IteratorIF<T> listIt = getIterator();
		while(listIt.hasNext()) {
			T element = listIt.getNext();
			buff.append(element);
			if(listIt.hasNext())
				buff.append(", ");
		}
		
		buff.append("]");
		return buff.toString();
	}
}
