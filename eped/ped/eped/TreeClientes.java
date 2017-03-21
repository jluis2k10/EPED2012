package ped.eped;

import ped.eped.tads.*;

/**
 * Esta clase se utiliza para crear un árbol genérico estático que contendrá los
 * clientes que se encuentran ya dentro de la pastelería esperando ser atendidos.
 * <p>
 *     Se necesita una clase/tipo de objeto nuevo porque el árbol estático por defecto
 *     no contiene los métodos necesarios para trabajar sobre la estructura de árbol
 *     que se necesita.
 * </p>
 * <p>
 *     Esta estructura consiste en represntar con un nodo del árbol una Paciencia
 *     determinada, y dentro de ese nodo un único elemento en forma de Cola Dinámica
 *     llena de objetos Cliente (QueueDynamic<Cliente>),
 *     los cuales tendrán la misma Paciencia que el nodo en el que se agrupan. <br />
 *     Además cada nodo tendrá a su vez un único nodo hijo que será el nodo
 *     correspondiente a la Paciencia inmediatamente superior. De este modo,
 *     tendremos tantos nodos (o niveles) como posibles valores de Paciencia existentes.
 *     <br/>
 *     Gráficamente, podría representarse así:
 *
 *     +--------------+
 *     |  QD  | QS(1) |    (Nodo paciencia 0)
 *     +--------------+
 *                |
 *            +--------------+
 *            |  QD  | QS(1) |    (Nodo paciencia 1)
 *            +--------------+
 *                       |
 *                   +--------------+
 *                   |  QD  | QS(1) |    (Nodo paciencia 2)
 *                   +--------------+
 *                           ·
 *                           ·
 *                           ·
 *     Donde QD representa la Cola Dinámica con los clientes que tengan la misma
 *     paciencia que la que representa el nodo, y QS(1) una Cola Estática que contiene
 *     el único hijo de cada nodo (que será a su vez otro árbol pero cuya raíz será de
 *     paciencia superior a la raíx del árbol del cual parte).
 * </p>
 * @Author José Luis Pérez.
 */
public class TreeClientes<T> extends TreeStatic<T> {

    /**
     * Constructor para TreeCliente.
     * Si no se indica el número de niveles/nodos (árboles de Paciencia diferente) que
     * tendrá el árbol, se crea un Árbol Estático de capacidad 1.
     * @param element El tipo de elementos que contendrá el árbol.
     */
    public TreeClientes(T element) {
        super(element, 1);
    }

    /**
     * Constructor alternativo para TreeCliente.
     * Si se indica el número de niveles que debe tener el árbol, primero se crea un
     * árbol de capacidad 1 y luego se añaden tantos niveles/nodos (árboles de
     * Paciencia superior) como sean necesarios.
     * @param element El tipo de elementos que contendrá el árbol.
     * @param niveles El número de nodos/niveles necesarios.
     */
    public TreeClientes(T element, int niveles) {
        this(element);
        if(niveles > 0) {
            // Crear nodos necesarios.
            TreeClientes<T> childrens = (makeNiveles(this, element, niveles));
            // Añadir el árbol generado como hijo a la raíz.
            this.addChild(childrens);
        }
    }

    /**
     * Método recursivo utilizado para crear tantos nodos/niveles como sean necesarios.
     * Cada nodo es a su vez un árbol de capacidad 1 cuyo único hijo es otra vez un
     * árbol de capacidad 1 que vuelve a tener un sólo hijo en forma de árbol,
     * y así sucesivamente hasta crear tantos árboles como niveles necesarios.
     * Ojo! Se devuelve un árbol con niveles-1 nodos, luego en el constructor se le
     * añade el árbol aquí generado como hijo al árbol inicial para conseguir el número
     * total de niveles indicados.
     * @param tree El árbol que será añadido como nodo hijo.
     * @param element El tipo de elementos que contendrá el árbol
     * @param niveles El número de niveles/nodos a añadir.
     * @return El árbol creado con tantos niveles/nodos como sea necesario.
     */
    @SuppressWarnings("unchecked")
    private TreeClientes<T> makeNiveles(TreeClientes<T> tree, T element, int niveles) {
        if(niveles == 0) {
            return tree;
        } else {
            // Crear un nuevo árbol al cual se le añadirá como hijo el árbol generado
            // en una iteración anterior.
            TreeClientes<T> aux = null;

            // Necesitamos saber el tipo de elemento que contendrá el árbol para poder
            // construirlo.
            try {
                aux = new TreeClientes<T>((T) element.getClass().newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // Si añadimos como hijo el árbol del que se parte, crearemos un árbol
            // de nodos 'infinitos' puesto que se se autorreferenciará.
            if(this != tree)
                aux.addChild(tree);
            // Iterar con un nivel menos puesto que ya hemos añadido un nodo.
            aux = makeNiveles(aux, element, niveles - 1);
            return aux;
        }
    }

    /**
     * Insertar un Cliente en la cola del nodo correspondiente a su Paciencia. Lo que
     * se hace es ir recuperando hijos recursivamente hasta que index se hace igual a
     * 0, momento en el cual se habrá recuperado el árbol del nodo que almacena los
     * clientes con la Paciencia indicada, y de éste último recuperar su raíz con la
     * cola de clientes para añadirle el Cliente indicado.
     * @param cliente El cliente a añadir.
     * @param index La paciencia del cliente.
     */
    @SuppressWarnings("unchecked")
    public void insertCliente(Object cliente, int index) {
        if(index == 0) {
            QueueIF<T> q = (QueueIF<T>) getRoot();
            q.add((T) cliente);
            return;
        } else {
            ListIF<T> children = (ListIF<T>) getChildren();
            TreeClientes<T> t = (TreeClientes<T>) children.getFirst();
            t.insertCliente(cliente, index - 1);
        }
    }

    /**
     * Recuperar el cliente con menos paciencia que haya entrado antes.
     * Funciona de forma similar al método anterior. Primero se obtiene la cola de
     * clientes desde la raíz del nodo sobre el que se está trabajando. Si no está
     * vacía, se recupera el primer elemento de esa cola y se devuelve. Si está vacía,
     * se recupera el árbol correspondiente al nodo inmediatamente posterior y sobre él
     * se vuelve a aplicar el método de forma recursiva, hasta que se encuentre un
     * Cliente.
     * @return El cliente de menor paciencia que haya entrado antes.
     */
    @SuppressWarnings("unchecked")
    public T getFirst() {
        QueueIF<T> q = (QueueIF<T>) getRoot();
        if(!q.isEmpty()) {
            return q.getFirst();
        } else {
            ListIF<T> children = (ListIF<T>) getChildren();
            TreeClientes<T> t = (TreeClientes<T>) children.getFirst();
            return t.getFirst();
        }
    }

    /**
     * Eliminar del árbol al cliente con menos paciencia que haya entrado antes.
     * Funciona exáctamente igual que el método anterior {@link #getFirst()} pero ahora
     * una vez alcanzado el nodo necesario se elimina al cliente de la cola en vez de
     * devolverlo.
     */
    @SuppressWarnings("unchecked")
    public void removeFirst() {
        QueueIF<T> q = (QueueIF<T>) getRoot();
        if(!q.isEmpty()) {
            q.remove();
            return;
        } else {
            ListIF<T> children = (ListIF<T>) getChildren();
            TreeClientes<T> t = (TreeClientes<T>) children.getFirst();
            t.removeFirst();
        }
    }

    /**
     * Devuelve cierto si el árbol no contiene ningún cliente.
     * El método de la superclase no es suficiente puesto que lo que hace para
     * determinar si el árbol está vacío es comprobar si la raíz del mismo es igual a
     * 'null'.
     * En este árbol la raíz de cada uno de los nodos contiene siempre una Cola
     * Dinámica, que puede estar vacía o no, pero nunca devolverá cierto al compararla
     * con 'null'. Se necesita entonces comprobar si cada una de las Colas Dinámicas de
     * los diferentes nodos están efectivamente vacías o no.
     * Se calcula de forma recursiva sobre cada uno de los nodos hasta que se encuentra
     * el último nodo, el cual no tiene hijos y es por lo tanto la única hoja del árbol.
     * @return True si el árbol está vacío.
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean isEmpty() {
        if(isLeaf()){
            QueueIF<T> q = (QueueIF<T>) getRoot();
            return q.isEmpty();
        } else {
            QueueIF<T> q = (QueueIF<T>) getRoot();
            ListIF<T> children = (ListIF<T>) getChildren();
            TreeClientes<T> t = (TreeClientes<T>) children.getFirst();
            return q.isEmpty() && t.isEmpty();
        }

    }
}
