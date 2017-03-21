package ped.eped;

/**
 * <p>
 *     Clase que modela el objeto tarta.
 *     Una tarta es símplemente un Objeto con un identificador asociado (el tipo de tarta
 *     de la que se trata).
 * </p>
 * <p>
 *     Se sobrescriben los métodos {@link #hashCode()} y {@link #equals(Object)} porque
 *     en la clase Maquina ({@link Maquina}), en cada una de las fases se comprueba si
 *     la tarta que quiere el cliente es con la que está tratando la máquina mediante el
 *     método {@link #equals(Object)}.
 * </p>
 * <p>
 *     Podría haberse hecho con un simple Tarta1.getTipo() == Tarta2.getTipo(),
 *     pero como en toda la práctica se implementan de este modo las diferentes TADS he
 *     decidido hacerlo así.
 * </p>
 */
public class Tarta {
	/** Tipo de tarta */
	private int tipo;

    /**
     * Constructor de la clase.
     * @param tipo El tipo de tarta.
     */
	public Tarta(int tipo) {
		this.setTipo(tipo);
	}

    /**
     * Devuelve el tipo de tarta.
     * @return El tipo de tarta.
     */
	public int getTipo() {
		return tipo;
	}

    /**
     * Actualiza el tipo de tarta.
     * @param tipo El tipo de tarta (int).
     */
	private void setTipo(int tipo) {
		this.tipo = tipo;
	}

    /**
     * {@inheritDoc}
     * Devuelve el hasCode del objeto Tarta.
     * @return El hashcode del objeto Tarta.
     */
	@Override
	public int hashCode() {
		return 31 * 31 * tipo;
	}

    /**
     * {@inheritDoc}
     * Devuelve cierto si se le pasa como parámetro otro objeto Tarta del mismo tipo.
     * @param o El objeto con el que comprobar la igualdad.
     * @return True si el Objeto o es de la clase Tarta y además tiene el mismo
     * identificador (atributo 'tipo') asociado.
     */
	@Override
	public boolean equals(Object o) {
		if(o == this)
			return true;
		if(o == null)
			return false;
		if(o.getClass() != this.getClass())
			return false;
		else {
			Tarta t = (Tarta) o;
			return (t.tipo == tipo);
		}
	}

    /**
     * {@inheritDoc}
     * @return El Objeto Tarta convertido en String, donde se indica su atributo tipo.
     */
	@Override
	public String toString() {
		return "Tarta: " + Integer.toString(tipo);
	}
}
