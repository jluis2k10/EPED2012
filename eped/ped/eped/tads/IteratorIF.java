package ped.eped.tads;

public interface IteratorIF<T> {
	
	/**
	 * Devuelve el siguente elemento de la iteración.
	 * @return el siguiente elemento de la iteración.
	 */
	public T getNext();
	
	/**
	 * Devuelve cierto si tiene un elemento siguiente.
	 * @return true si tiene un elemento siguiente.
	 */
	public boolean hasNext();
	
	/**
	 * Restablece el iterador para volver al inicio.
	 */
	public void reset();
}
