package ped.eped.tads;

public class StackIterator<T> implements IteratorIF<T> {
	private StackIF<T> handler;
	private StackIF<T> restart;
	
	/**
	 * Constructor para StackIterator.
	 * @param handler el manejador de pilas.
	 */
	public StackIterator(StackIF<T> handler) {
		this.handler = handler;
		this.restart = new StackDynamic<T>(handler);
	}
	
	/**
	 * Devuelve el siguiente elemento de la iteración.
	 * @return el siguiente elemento de la iteración.
	 */
	@Override
	public T getNext() {
		T top = handler.getTop();
		handler.pop();
		return top;
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
	public void reset() {
		handler = new StackDynamic<T>(restart);
	}

    /**
     * Devuelve el hashcode del iterador.
     * @return El hashcode del iterador.
     */
	@Override
	public int hashCode() {
		return 31 * ((handler == null) ? 0 : handler.hashCode()) + 31 * ((restart ==
                null) ? 0 : restart.hashCode());
	}

    /**
     * Devuelve cierto si dos Iteradores son iguales.
     * @param o El iterador con el que realizar la comparación.
     * @return True si son iguales.
     */
    @SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if(o == null)
            return false;
        if(o == this)
            return true;
        if(o.getClass() != this.getClass())
            return false;
        else {
            StackIterator<T> s = (StackIterator<T>) o;
            return this.handler.equals(s.handler) && this.restart.equals(s.handler);
        }
	}

    /**
     * Devuelve el iterador convertido en String.
     * @return El iterador convertido en String.
     */
	@Override
	public String toString() {
        return "StackIterator - [Handler: " + handler.toString() + ", " +
                "Restart: " + restart.toString() + "]";
	}
}
