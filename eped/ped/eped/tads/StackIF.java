package ped.eped.tads;

public interface StackIF<T> {

	/**
	 * Devuelve la cima de la pila.
	 * @return la cima de la pila.
	 */
	public T getTop();
	
	/**
	 * Inserta un elemento a la pila.
	 * @param element El elemento a ser a�adido.
	 * @return la pila incluyendo el elemento.
	 */
	public StackIF<T> push(T element);
	
	/**
	 * Extrae de la pila el elemento en la cima.
	 * @return la pila excluyendo la cabeza.
	 */
	public StackIF<T> pop();
	
	/**
	 * Devuelve cierto si la pila está vacía.
	 * @return true si la pila está vacía.
	 */
	public boolean isEmpty();
	
	/**
	 * Devuelve cierto si la pila está llena.
	 * @return true si la pila está llena.
	 */
	public boolean isFull();
	
	/**
	 * Devuelve el número de elementos de la pila.
	 * @return el número de elementos de la pila.
	 */
	public int getLength();
	
	/**
	 * Devuelve cierto si la pila contiene el elemento.
	 * @param element El elemento buscado.
	 * @return true si la pila contiene el elemento.
	 */
	public boolean contains(T element);
	
	/**
	 * Devuelve un iterador para la pila.
	 * @return un iterador para la pila.
	 */
	public IteratorIF<T> getIterator();
}
