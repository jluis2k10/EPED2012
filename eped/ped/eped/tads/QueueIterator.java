package ped.eped.tads;

public class QueueIterator<T> implements IteratorIF<T> {
	private QueueIF<T> handler;
	private QueueIF<T> restart;
	
	/**
	 * Contructor para QueueIterator.
	 * @param handler el manejador de colas.
	 */
	public QueueIterator(QueueIF<T> handler) {
		this.handler = handler;
		this.restart = new QueueDynamic<T>(handler);
	}
	
	/**
	 * Devuelve el siguiente elemento de la iteración.
	 * @return el siguiente elemento de la iteración.
	 */
	@Override
	public T getNext() {
		T element = handler.getFirst();
		handler.remove();
		return element;
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
	 * Restablece el iterador para volver a iterar.
	 */
	@Override
	public void reset() {
		handler = new QueueDynamic<T>(restart);
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
            QueueIterator<T> l = (QueueIterator<T>) o;
            return l.handler.equals(this.handler) && l.restart.equals(this.restart);
        }
    }

    /**
     * Devuelve el iterador convertido en String.
     * @return El iterador convertido en String.
     */
    @Override
    public String toString() {
        return "QueueIterator - [Handler: " + handler.toString() + ", " +
                "Restart: " + restart.toString() + "]";
    }
}