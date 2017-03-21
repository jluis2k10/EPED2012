package ped.eped;

import ped.eped.tads.IteratorIF;
import ped.eped.tads.ListStatic;
import ped.eped.tads.StackStatic;

/**
 * Clase que modela el objeto Máquina.
 * Contiene los métodos necesarios para atender al Cliente y para fabricar y almacenar
 * Tartas según las especificaciones del enunciado de la práctica.
 */
public class Maquina {
	/** Recuento de ventas que hemos realizado hasta el momento */
	private String ventas = "";
	/** Lo que llevamos recaudado hasta el momento */
	private double Z = 0;
	/** Los mostradores de los que disponemos para almacenar tartas */
	private final ListStatic<Mostrador> mostradores;
	/** El comparador de mostradores (para ordenarlos) */
	private final MostradoresComparator compMostradores = new MostradoresComparator();
	/** La capacidad de los mostradores */
	private final int capacidad;
	/** El precio de cada tarta */
	private final int precio;
	/** La pila de tartas almacenadas */
	private final StackStatic<Tarta> tartas;
	/** índice que nos indica qué tipo de tarta es el siguiente en fabricarse */
	private int indexTarta = 1;
	
	/**
	 * El constructor para la Máquina.
	 * @param capacidad el número de tartas que puede almacenar.
	 * @param precio el precio de cada tarta.
	 */
	public Maquina(int capacidad, int precio) {
		this.capacidad = capacidad;
		this.precio = precio;
		this.tartas = new StackStatic<Tarta>(capacidad);

        // Inicializar cada uno de los Mostradores necesarios así como la Lista que
        // hará de contenedor de Mostradores.
		mostradores = new ListStatic<Mostrador>(capacidad);
		for (int i = 0; i<capacidad; i++) {
			mostradores.insert(new Mostrador(capacidad, i));
		}
	}
	
	/**
	 * Atender el pedido del cliente mientras éste tenga paciencia. Si la tarta
     * solicitada no está disponible, fabricamos una y actualizamos la paciencia del
     * cliente.
	 * @param cliente El cliente que estamos atendiendo.
	 */
	public void atenderCliente(Cliente cliente) {
		// ¿Qué tarta quiere el cliente?
        Tarta T = obtenerPedido(cliente);
		
		// FASE 1: comprabar si la tarta que quiere el cliente está en la cima
		// de la máquina o de alguno de los mostradores disponibles.
		if(T.equals(tartas.getTop()) && cliente.getPaciencia() >= 0) {
			entregarPedido(cliente);
			// Sacar la tarta de la pila de la máquina
			tartas.pop();
			return;
		} else {
            // Buscar la tarta en las cimas de los mostradores disponibles.
			IteratorIF<Mostrador> mostradoresIt = mostradores.getIterator();
			while(mostradoresIt.hasNext()) {
				Mostrador m = mostradoresIt.getNext();
				if(T.equals(m.getTop())) {
					entregarPedido(cliente);
					// Sacar la tarta de la pila del mostrador y reordenar los mostradores
					m.pop();
					mostradores.sort(compMostradores);
					return;
				}
			}
		}
		
		// FASE 2: La tarta no estaba en la cima de la máquina o de alguno de los
		// mostradores. Movemos las tartas de la máquina a los mostradores buscando la
		// que quiere el cliente.
		while(moverTartas()) {
			cliente.setPaciencia(cliente.getPaciencia() - 1);
			if(T.equals(tartas.getTop()) &&  cliente.getPaciencia() >= 0) {
				entregarPedido(cliente);
				tartas.pop();
				return;
			}
		}
		
		// FASE 3: No se pueden pasar más tartas desde la máquina hacia alguno de los
		// mostradores. Comenzar a fabricar tartas siempre que sea posible hasta dar
		// con la quiera el cliente.
		boolean sale_cliente = false;
		while (cliente.getPaciencia() > 0 && !sale_cliente) {
			if(fabricarTarta()) {
				cliente.setPaciencia(cliente.getPaciencia() - 2);
				if(T.equals(tartas.getTop()) && cliente.getPaciencia() >= 0) {
					entregarPedido(cliente);
					tartas.pop();
					return;
				}
			} else {
				sale_cliente = true;
			}
		}
		// No hemos sido capaces de atender al cliente
    }
	
	/**
	 * Averiguar qué tipo de tarta quiere el cliente.
	 * @param cliente El cliente que estamos atendiendo.
	 * @return el tipo de tarta que quiere el cliente.
	 */
	private Tarta obtenerPedido(Cliente cliente) {
		return new Tarta(cliente.getTipoTarta());
	}
	
	/**
	 * Entregamos al cliente la tarta que quería y actualizamos
	 * el log con las ventas realizadas.
	 * @param cliente El cliente con el que estamos tratando.
	 */
	private void entregarPedido(Cliente cliente) {
		int idCliente = cliente.getId();
		int tipoTarta = cliente.getTipoTarta();
		double clientePaga = precio + ((double)cliente.getPaciencia() / (double)
                (capacidad + 1));
		
		String eol = System.getProperty("line.separator");
		ventas += Integer.toString(idCliente) + "\t" + Integer.toString(tipoTarta) +
                "\t" + Double.toString(clientePaga) + eol;
		Z = Z + clientePaga;
	}
	
	/**
	 * Movemos las tartas de la máquina al mostrador cercano
	 * menos lleno. Puede suceder que no haya tartas que mover
	 * en la máquina o que los mostradores están llenos, con lo
	 * no se moverá ninguna y devolveremos falso.
	 * @return true si se ha conseguido mover una tarta.
	 */
	private boolean moverTartas() {
		Mostrador m = mostradores.getFirst();
		if(m.isFull() || tartas.isEmpty())
			return false;
		else {
			m.push(tartas.getTop());
			tartas.pop();
			mostradores.sort(compMostradores);
			return true;
		}
	}
	
	/**
	 * Fabricar un tarta del tipo adecuado y apilarla en la
	 * propia máquina.
	 * @return true si se ha conseguido fabricar y apilar una tarta
	 */
	private boolean fabricarTarta() {
		if(!tartas.isFull()) {
			tartas.push(new Tarta(indexTarta));
            // La capacidad es N - 1, por lo tanto se pueden fabricar capacidad + 1
            // tipos de tartas diferentes.
			indexTarta = 1 + (indexTarta % (capacidad + 1)); 
			return true;
		}
		return false;
	}
	
	/**
	 * Generamos e imprimimos el informe de ventas de acuerdo
	 * al proceso que ha seguido la máquina.
	 * @return el informe de ventas.
	 */
	public String getInforme() {
        // Incluimos como última línea el total de las ganancias obtenidas.
		ventas += Double.toString(Z);
		return ventas;
	}
}
