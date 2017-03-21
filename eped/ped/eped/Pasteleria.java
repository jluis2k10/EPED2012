package ped.eped;

import ped.eped.tads.*;

import java.io.*;

/**
 * Clase principal de la aplicación. Modela la pastelería según las especificaciones
 * del enunciado de la práctica.
 * Al ejecutar la aplicación se le pueden pasar, mediante la línea de comandos,
 * tanto el archivo de entrada con la jornada laboral que se debe seguir,
 * como la ubicación del archivo de salida donde se deberá escribir el informe de
 * ventas.
 * Estos asrchivos se buscarán en el mismo directorio desde donde se esté ejecutando la
 * aplicación.
 * Por ejemplo:
 * <ul>
 *     <li>java -jar pasteleria.jar jornada.tsv</li>
 *     <li>java -jar pasteleria.jar jornada.tsv informe.tsv</li>
 * </ul>
 * Si no se le pasan parámetros tomará como fichero de entrada jornada_laboral.tsv y
 * como fichero de salida informe_ventas.tsv, estando ambos ubicados en el mismo
 * directorio que el programa.
 */
public class Pasteleria {
    /** Clientes que no están dentro de la pastelería todavía */
	private final QueueDynamic<Cliente> clientesFuera = new QueueDynamic<Cliente>();
	/** Clientes dentro de la pastelería */
	private TreeClientes<QueueIF<Cliente>> clientesDentro;
	/** El cliente con el que estamos tratando. También lo usamos como comparador. */
	private Cliente cliente = new Cliente();
    /** Paciencia máxima que pueden llegar a tener los clienter */
    private int pacienciaMax = 0;
	/** Máquina que hace las tartas y sirve los pedidos */
	private Maquina maquina;
	/** El nº total de moldes para tartas, mostradores y clientes se expresan en
     * función de N */
	private static int N;
    /** Ruta hasta el fichero con la entrada de datos */
    private String inputFile;
    /** Ruta hasta el fichero donde se guardará la salida del programa */
    private String outputFile;
    /** Ruta hasta el directorio donde se está ejecutando el programa */
    private final String currentDir = System.getProperty("user.dir");
    /** ¿Mostrar información sobre la ejecución de la aplicación? */
    private static final boolean DEBUG = false;

    /**
	 * Constructor de pasteleria.
	 */
	public Pasteleria() {
		this("", "");
	}

    /**
     * Constructor si se tiene el primer parámetro, que corresponde al fichero con la
     * jornada laboral a seguir.
     * @param input El fichero con la jornada laboral.
     */
    public Pasteleria(String input) {
        this(input, "");
    }

    /**
     * Constructor si se tienen ambos parámetros, el primero corresponde al fichero con
     * la jornada laboral, y el segundo al fichero donde se guardará el informe de
     * ventas.
     * @param input El fichero con la jornada laboral.
     * @param output El fichero donde se guardará el informe de ventas.
     */
    public Pasteleria(String input, String output) {
        if(input.equals(""))
            input = "jornada_laboral.tsv";
        File f = new File(input);

        if(f.isAbsolute()) {
            // Se ha dado una ruta completa hasta el archivo.
            if(f.exists())
                this.inputFile = input;
            else {
                System.err.println("No se encuentra el archivo de entrada indicado: " + inputFile);
                System.exit(0);
            }
        } else {
            // No se ha dado una ruta completa, comprobar si el archivo se encuentra en
            // el mismo directorio que la aplicación.
            if(f.exists())
                this.inputFile = currentDir + File.separator + input;
            else {
                System.err.println("No se encuentra el archivo de entrada indicado: " + inputFile);
                System.exit(0);
            }
        }

        if(output.equals(""))
            output = "informe_ventas.tsv";
        f = new File(output);

        if(f.isAbsolute()) {
            // Se ha dado la ruta completa para el archivo de salida.
            this.outputFile = output;
        } else {
            // No se ha dado la ruta completa, asumimos que el informe debe guardarse
            // en el directorio de la aplicación.
            this.outputFile = currentDir + File.separator + output;
        }

        load(); // Recuperar la información desde el fichero de entrada.
        init(); // Inicializar el árbol con los clientes de dentro de la pastelería.
        run(); // Atender clientes, actualizar cola y árbol, generar informe.
    }
	
	/**
	 * Leer el fichero de entrada, establecer los valores de N y de PRECIO según
     * indique, y llenar la cola de clientes que están 'esperando fuera de la
     * pastelería'.
	 */
	private void load() {
        // Leer archivo de entrada.
        File input;
        FileReader fr = null;
        BufferedReader br = null;
		try {
            input = new File(inputFile);
            fr = new FileReader(input);
            br = new BufferedReader(fr);

			// Obtener N y PRECIO del fichero de entrada.
			String linea = br.readLine();
			N = Integer.parseInt(linea.trim());
			linea = br.readLine();
            int PRECIO = Integer.parseInt(linea.trim());

            // Inicializar máquina
            maquina = new Maquina(N-1, PRECIO);

            // Leer línea a línea del fichero y crear un Cliente nuevo a partir de las
            // especificaciones de cada línea. Después insertar el Cliente en la cola
            // de clientes que están fuera de la pastelería.
            while((linea=br.readLine()) != null) {
                Cliente cliente = new Cliente(linea);
                pacienciaMax = Math.max(pacienciaMax, cliente.getPaciencia());
                clientesFuera.add(cliente);
            }
		} catch(Exception e) {
            System.err.println("No se ha podido leer el fichero: " + inputFile);
            System.err.println(e);
		} finally {
			try {                   
	            if(br != null) br.close();
                if(fr != null) br.close();
	         } catch(Exception e2) {
                System.err.println("Ocurrió un error cerrando el fichero: " + inputFile);
                System.err.println(e2);
	         }
		}
	}

    private void init() {
        // Inicializar el árbol que contiene a los clientes que entren en la tienda.
        clientesDentro = new TreeClientes<QueueIF<Cliente>>(new QueueDynamic<Cliente>()
                , pacienciaMax);

        // Rellenamos el árbol clientesDentro desde la cola clientesFuere,
        // teniendo en cuenta el número máximo de clientes que pueden entrar en la
        // pastelería (2*N+1).
        for(int i = 0; i < (2*N+1); i++) {
            Cliente cliente = clientesFuera.getFirst();
            clientesFuera.remove();
            clientesDentro.insertCliente(cliente, cliente.getPaciencia());
        }
    }
	
	/**
	 * Ejecutar parte de la lógica de la aplicación. Van entrando clientes a la
     * pastelería.
     * Una vez la máquina atiende a un cliente, éste abandona la pastelería y se deja
     * paso a otro si aún tuvieran que entrar más.
     * Cuando no quedan más clientes que atender, se guarda en un fichero el informe
     * que ha ido generando la máquina.
	 */
	private void run() {
        // Bucle principal para la ejecución de la lógica del programa. Correrá hasta
        // que no queden clientes que atender.
        while (!clientesDentro.isEmpty()) {
            cliente = (Cliente) clientesDentro.getFirst();
            maquina.atenderCliente(cliente);

            // Una vez atendido, el cliente sale de la pastelería.
            clientesDentro.removeFirst();

            // Si quedan clientes fuera, pasar el primero a dentro.
            if(!clientesFuera.isEmpty()) {
                Cliente cliente = clientesFuera.getFirst();
                clientesFuera.remove();
                clientesDentro.insertCliente(cliente, cliente.getPaciencia());
            }
        }

        // No quedan clientes, recuperar el informe de ventas.
		String ventas = maquina.getInforme();

        // Guardar el informe de ventas en un fichero.
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(ventas);

            // Mostrar en pantalla los resultados y dónde se ha generado el fichero.
            System.out.println("Fichero de salida creado en: " + outputFile);
            String eol = System.getProperty("line.separator");
            if(DEBUG) System.out.println("Informe de ventas: " + eol + ventas);
        } catch (IOException e) {
            System.err.println("Ocurrió un error guardando el fichero con el informe de" +
                    " ventas: " + outputFile);
            System.err.println(e);
        } finally {
            try {
                if(writer != null)
                    writer.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Clase principal.
     * @param args Los parámetros de entrada, indicando la ubicación del fichero con la
     *             jornada laboral y la del informe de ventas.
     */
	public static void main (String[] args) {
        // Se pasa la jornada laboral por línea de comandos.
        if(args.length == 1)
            new Pasteleria(args[0]);
        // Se pasa tanto la jornada laboral como el informe de ventas por línea de
        // comandos.
        else if(args.length == 2)
            new Pasteleria(args[0], args[1]);
        // No se pasa ningún parámetro, se tomarán los ficheros por defecto.
        else
		    new Pasteleria();
	}

}