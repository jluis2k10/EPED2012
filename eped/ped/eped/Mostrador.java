package ped.eped;

import ped.eped.tads.StackStatic;

/**
 * Clase que modela un mostrador de la pasteleria.
 * No es más que una pila estática, donde se irán almacenando objetos del tipo Tarta,
 * con un identificador único asociado para porder diferenciarlos y ordenarlos en caso
 * de que dos o más mostradores tengan el mismo número de tartas alamcenadas.
 */
public class Mostrador extends StackStatic<Tarta> {
	/** Identificador único del mostrador que representa su cercanía respecto a la
     * máquina. Cuanto menor sea, más cercano estará de ella. */
	private final int identificador;
	
	/**
	 * Constructor para el mostrador.
	 * @param capacidad El número de tartas que puede almacenar.
	 * @param identificador El identificador único de cada mostrador.
	 */
	public Mostrador(int capacidad, int identificador) {
		super(capacidad);
		this.identificador = identificador;
	}

    /**
     * Devuelve el identificador del mostrador.
     * @return El identificador del mostrador.
     */
	public int getId() {
		return identificador;
	}

    @Override
    public int hashCode() {
        return 31 * identificador + super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        Mostrador m = (Mostrador) o;
        return super.equals(o) && this.identificador == m.identificador;
    }

    @Override
    public String toString() {
        return "Mostrador " + this.identificador + ": " + super.toString();
    }
}
