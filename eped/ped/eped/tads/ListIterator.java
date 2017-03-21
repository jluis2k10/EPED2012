package ped.eped.tads;

public class ListIterator<T> implements IteratorIF<T> {
	private ListIF<T> handler;
	private ListIF<T> restart;
	
	/**
	 * Constructor para ListIterator.
	 * @param handler el manejador de listas.
	 */
	public ListIterator(ListIF<T> handler) {
		this.handler = handler;
		this.restart = handler;
	}
	
	/**
	 * Devuelve el siguiente elemento de la iteraci�n.
	 * @return el siguiente elemento de la iteraci�n.
	 */
	@Override
	public T getNext() {
		T next = handler.getFirst();
		handler = handler.getTail();
		return next;
	}
	
	/**
	 * Devuelve cierto si existen más elementos a iterar.
	 * @return true si existen más elementos a iterar.
	 */
	@Override
	public boolean hasNext() {
		return !handler.isEmpty();
	}
	
	/**
	 * Restablece el iterador para volver al inicio.
	 */
	@Override
	public void reset() {
		handler = restart;
	}

    /**
     * Devuelve el hashcode del iterador.
     * @return El hashcode del iterador.
     */
	@Override
	public int hashCode() {
		return 31 * ((handler == null) ? 0 : handler.hashCode()) + 31 * ((restart == null) ? 0 : handler.hashCode()) ;
	}

    /**
     * Devuelve cierto si dos Iteradores son iguales.
     * @param o El iterador con el que realizar la comparación.
     * @return True si son iguales.
     */
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
			ListIterator<T> l = (ListIterator<T>) o;
			return l.handler.equals(this.handler) && l.restart.equals(this.restart);
		}
	}

    /**
     * Devuelve el iterador convertido en String.
     * @return El iterador convertido en String.
     */
	@Override
	public String toString() {
		return "ListIterator - [Handler: " + handler.toString() + ", " +
                "Restart: " + restart.toString() + "]";
	}
}