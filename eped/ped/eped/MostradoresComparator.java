package ped.eped;

import ped.eped.tads.ComparatorBase;

/**
 * Necesitamos una clase para comparar los mostradores entre sí. Debe extender
 * ComparatorBase pero no puede implementarse directamente en la clase Mostrador (como
 * sucede en la clase Cliente), porque ésta ya extiende la clase StackStatic.
 */
public class MostradoresComparator extends ComparatorBase<Mostrador> {
	
	/**
	 * Método para comparar dos mostradores según la cantidad
	 * de elementos que contengan sus respectivas pilas.
	 * @param m1 Mostrador 1 a comparar.
	 * @param m2 Mostrador 2 a comparar.
	 * @return el resultado de la comparación.
	 */
	@Override
	public int compare(Mostrador m1, Mostrador m2) {
		if(m1.getLength() < m2.getLength())
			return -1;
		if(m1.getLength() > m2.getLength())
			return 1;
		else {
			if(m1.getId() < m2.getId())
				return -1;
			if(m1.getId() > m2.getId())
				return 1;
			else
				return 0; // este caso no se dará nunca en la práctica porque el identificador siempre es difetente
		}
	}
}
