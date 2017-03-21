package ped.eped;

import ped.eped.tads.ComparatorBase;

/**
 * Clase para modelar al objeto Cliente.
 * Los clientes constan de tres atributos: identificador, tipo de tarta que quieren,
 * y la paciencia que les queda.
 * Extiende la clase ComparatorBase porque se deberán comparar los clientes entre ellos
 * cuando toque ordenarlos según su paciencia.
 */
public class Cliente extends ComparatorBase<Cliente> {
	/** El identificador único de cliente */
	private final int identificador;
	/** El tipo de tarta que quiere el cliente */
	private final int tarta;
	/** Paciencia del cliente */
	private int paciencia;
	
	/**
	 * Constructor de la clase.
     * Si no se le pasan parámetros al constructor, se crea un cliente con todos sus
     * atributos a -1.
     * Necesario tener un objeto Cliente 'nulo' para utilizar su método {@link #compare
     * (Cliente, Cliente)}, así se evita el crear una clase específica para el
     * comparador de clientes.
	 */
	public Cliente() {
		identificador = tarta = paciencia = -1;
	}
	
	/**
	 * Constructor alternativo en el que se indican sus atributos.
	 * 
	 * @param linea Una línea de texto que contiene el identificador,
     *              el tipo de tarta y la paciencia.
	 */
	public Cliente(String linea) {
		String regexp = "[\\t]";
		String[] atributos = linea.split(regexp);
		this.identificador = Integer.parseInt(atributos[0].trim());
		this.tarta = Integer.parseInt(atributos[1].trim());
		this.paciencia = Integer.parseInt(atributos[2].trim());
	}
	
	/**
	 * Devuelve el identificador único del cliente.
	 * 
	 * @return El identificador único del cliente.
	 */
	public int getId() {
		return identificador;
	}
	
	/**
	 * Devuelve el tipo de tarta que quiere el cliente.
	 * 
	 * @return El tipo de tarta que quiere el cliente.
	 */
	public int getTipoTarta() {
		return tarta;
	}
	
	/**
	 * Devuelve la paciencia restante del cliente.
	 * 
	 * @return La paciencia restante del cliente.
	 */
	public int getPaciencia() {
		return paciencia;
	}
	
	/**
	 * Modifica la paciencia del cliente.
	 * 
	 * @param paciencia La paciencia del cliente.
	 */
	public void setPaciencia(int paciencia) {
		this.paciencia = paciencia;
	}
	
	/**
     * {@inheritDoc}
     * <p>
	 * Comparador de clientes según su paciencia y su orden de entrada (su
     * identificador).
	 * </p>
	 * @param cliente1 Cliente a comparar.
	 * @param cliente2 Cliente a comparar.
	 * @return El resultado de la comparación.
	 */
	@Override
	public int compare(Cliente cliente1, Cliente cliente2) {
		if(cliente1.getPaciencia() < cliente2.getPaciencia())
			return -1;
		if(cliente1.getPaciencia() > cliente2.getPaciencia())
			return 1;
		else {
			if(cliente1.getId() < cliente2.getId())
				return -1;
			if(cliente1.getId() > cliente2.getId())
				return 1;
            // No se debería dar este caso (misma paciencia e identificador,
            // pero por si acaso.
			else
				return 0;
		}
	}

    /**
     * {@inheritDoc}
     * @return El hashcode del Cliente.
     */
    @Override
    public int hashCode() {
        return 31 * identificador + 31 * tarta + 31 * paciencia;
    }

    /**
     * {@inheritDoc}
     * @param o Objeto con el que compararse.
     * @return True si son iguales.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (o.getClass() != this.getClass())
            return false;
        else {
            Cliente c = (Cliente) o;
            return c.identificador == identificador && c.tarta == tarta && c.paciencia == paciencia;
        }
    }

	/**
	 * {@inheritDoc}
	 * @return Un String describiendo los atributos del cliente.
	 */
	@Override
	public String toString() {
		return "ID: " + Integer.toString(identificador) + " Paciencia: " + Integer
                .toString(paciencia) + " Tarta: " + Integer.toString(tarta);
	}
}
