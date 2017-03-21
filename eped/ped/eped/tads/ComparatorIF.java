package ped.eped.tads;

public interface ComparatorIF<T> {

	public static int LESS = -1;
	public static int EQUAL = 0;
	public static int GREATER = 1;
	
	/**
	 * Compara dos elementos para indicar si el primero es
	 * menor, igual o mayor que el segundo elemento.
	 * @param e1 el primer elemento.
	 * @param e2 el segundo elemento.
	 * @return el orden de los elementos.
	 */
	public int compare(T e1, T e2);
	
	/**
	 * Indica si un elemento es menor que otro.
	 * @param e1 el primer elemento.
	 * @param e2 el segundo elemento.
	 * @return true si un elemento es menor que otro.
	 */
	public boolean isLess(T e1, T e2);
	
	/**
	 * Indica si un elemento es igual que otro.
	 * @param e1 el primer elemento.
	 * @param e2 el segundo elemento.
	 * @return true si un elemento es igual que otro.
	 */
	public boolean isEqual(T e1, T e2);
	
	/**
	 * Indica si un elemento es mayor que otro.
	 * @param e1 el primer elemento.
	 * @param e2 el segundo elemento.
	 * @return true si un elemento es mayor que otro.
	 */
	public boolean isGreater(T e1, T e2);
}
