package ped.eped.tads;

public class QueueDynamic<T> implements QueueIF<T> {
	private Node<T> first;
	private Node<T> last;
	
	/**
	 * Constructor para QueueDynamic.
	 */
	public QueueDynamic() {
		first = null;
		last = null;
	}
	
	/**
	 * Constructor alternativo para QueueDynamic a partir de otra cola.
	 * @param queue la cola a partir de la cual se crea.
	 */
	public QueueDynamic(QueueIF<T> queue) {
		this();
		if(queue != null)
			for(int i = 0; i < queue.getLength(); i++) {
				T element = queue.getFirst();
				add(element);
				queue.remove();
				queue.add(element);
			}
	}
	
	/**
	 * Constructor alterantivo para QueueDynamic a partir de una lista.
	 * @param list la lista a partir de la cual se crea.
	 */
	public QueueDynamic(ListIF<T> list) {
		this();
		if(list != null) {
			ListIF<T> l = list;
			while(!l.isEmpty()) {
				add(l.getFirst());
				l = l.getTail();
			}
		}
	}
	
	@Override
	public T getFirst() {
		return first.getElement();
	}

	@Override
	public QueueIF<T> add(T element) {
		if(element != null) {
			if(isEmpty()) {
				Node<T> aNode = new Node<T>(element);
				first = aNode;
				last = aNode;
			} else {
				Node<T> aNode = new Node<T>(element);
				last.setNext(aNode);
				last = aNode;
			}
		}
		return this;
	}

	@Override
	public QueueIF<T> remove() {
		if(getLength() == 1) {
			first = null;
			last = null;
		}
		if(getLength() > 1)
			first = first.getNext();
		return this;
	}

	@Override
	public boolean isEmpty() {
		return first == null && last == null;
	}

	@Override
	public boolean isFull() {
		return false;
	}
	
	@Override
	public int getLength() {
		int length = 0;
		Node<T> node = first;
		while(node != null) {
			length++;
			node = node.getNext();
		}
		return length;
	}

	@Override
	public boolean contains(T element) {
		boolean found = false;
		Node<T> node = first;
		while (!found && node != null) {
			found = node.getElement().equals(element);
			node = node.getNext();
		}
		return found;
	}

	@Override
	public IteratorIF<T> getIterator() {
		QueueIF<T> handler = new QueueDynamic<T>(this);
		return new QueueIterator<T>(handler);
	}
	
	@Override 
	public int hashCode() {
		return 31 * ((first == null) ? 0 : first.hashCode()) + ((last == null) ? 0 : last.hashCode());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals (Object o) {
		if(o == this)
			return true;
		if(o == null)
			return false;
		if(o.getClass() != this.getClass())
			return false;
		else {
			QueueDynamic<T> q = (QueueDynamic<T>) o;
			return q.first.equals(first) && q.last.equals(last);
		}
	}
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("QueueDynamic - [");
		
		IteratorIF<T> queueIt = getIterator();
		while(queueIt.hasNext()) {
			T element = queueIt.getNext();
			buff.append(element);
			if(queueIt.hasNext())
				buff.append(", ");
		}
		buff.append("]");
		return buff.toString();
	}
}

class Node<T> {
	private T element;
	private Node<T> next;
	
	/**
	 * Constructor para Node.
	 */
	public Node() {
		this.element = null;
		this.next = null;
	}
	
	/**
	 * Constructor aleternativo para Node.
	 * @param element el elemento a insertar en el nodo.
	 */
	public Node(T element) {
		this();
		this.element = element;
	}
	
	/**
	 * Constructor alternativo para Node.
	 * @param element el elemento a insertar en el nodo.
	 * @param next el nodo siguiente.
	 */
	public Node(T element, Node<T> next) {
		this();
		this.element = element;
		this.next = next;
	}
	
	public T getElement() {
		return element;
	}
	
	public void setElement(T element) {
		this.element = element;
	}
	
	public Node<T> getNext() {
		return next;
	}
	
	public void setNext(Node<T> next) {
		this.next = next;
	}
}
