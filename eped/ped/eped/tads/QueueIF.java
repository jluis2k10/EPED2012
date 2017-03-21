package ped.eped.tads;

public interface QueueIF<T> {

	/**
	 * Devuelve la cabeza de la cola.
	 * @return la cabeza de la cola.
	 */
	public T getFirst();
	
	/**
	 * Inserta un nuevo elemento en la cola.
	 * @param element El elemento a añadir.
	 * @return la cola incluyendo el elemento.
	 */
	public QueueIF<T> add(T element);
	
	/**
	 * Borra la cabeza de la cola.
	 * @return la cola excluyendo la cabeza.
	 */
	public QueueIF<T> remove();
	
	/**
	 * Devuelve cierto si la cola está vacía.
	 * @return true si la cola está vacía.
	 */
	public boolean isEmpty();
	
	/**
	 * Devuelve cierto si la cola está llena.
	 * @return trie si la cola está llena.
	 */
	public boolean isFull();
	
	/**
	 * Devuelve cierto si la cola contiene el elemento.
	 * @param element El elemento buscado.
	 * @return true si la cola contiene el elemento.
	 */
	public boolean contains(T element);
	
	/**
	 * Devuelve un iterador para la cola.
	 * @return un iterador para la cola.
	 */
	public IteratorIF<T> getIterator();
	
	/**
	 * Devuelve la longitud de la cola.
	 * @return la longitud de la cola.
	 */
	public int getLength();
}
