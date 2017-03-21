package ped.eped.tads;

public class TreeStatic<T> implements TreeIF<T> {
    private T element;
    private QueueIF<TreeIF<T>> children;
    private int capacity;

    /**
     * Constructor para TreeStatic.
     * @param capacity La capacidad (número de hijos) del árbol.
     */
    public TreeStatic(int capacity) {
        this.element = null;
        this.children = new QueueStatic<TreeIF<T>>(capacity);
        this.capacity = capacity;
    }

    /**
     * Constructor alternativo para TreeStatic.
     * @param element El tipo de elementos que contendrá.
     * @param capacity La capacidad (número de hijos) del árbol.
     */
    public TreeStatic(T element, int capacity) {
        this.element = element;
        this.children = new QueueStatic<TreeIF<T>>(capacity);
        this.capacity = capacity;
    }

    /**
     * Constructor alternativo para TreeStatic.
     * @param tree Otro árbol a partir del que se construye éste.
     * @param capacity La capacidad (número de hijos) del árbol.
     */
    @SuppressWarnings("unchecked")
    public TreeStatic(TreeIF tree, int capacity) {
        this.element = (T) tree.getRoot();
        this.children = new QueueStatic<TreeIF<T>>(capacity);
        this.capacity = capacity;

        ListIF<TreeIF<T>> tChildren = tree.getChildren();
        while(!tChildren.isEmpty()) {
            TreeIF<T> aChild = tChildren.getFirst();
            TreeIF<T> cChild = new TreeStatic<T>(aChild, capacity); // ¿?
            children.add(cChild);
            tChildren = tChildren.getTail();
        }
    }

    /**
     * Devuelve el elemento raíz del árbol
     * @return El elemento raíz.
     */
    @Override
    public T getRoot() {
        return this.element;
    }

    /**
     * Devuelve una lista con todos los hijos de la raíz del árbol.
     * @return La lista con los hijos del árbol.
     */
    @Override
    public ListIF<TreeIF<T>> getChildren() {
        ListIF<TreeIF<T>> lChildren = new ListStatic<TreeIF<T>>(capacity);
        StackIF<TreeIF<T>> sChildren = new StackStatic<TreeIF<T>>(capacity);
        IteratorIF<TreeIF<T>> childrenIt = children.getIterator();
        while(childrenIt.hasNext()) {
            sChildren.push(childrenIt.getNext());
        }
        while(!sChildren.isEmpty()) {
            lChildren.insert(sChildren.getTop());
            sChildren.pop();
        }
        return lChildren;
    }

    /**
     * Establece el elemento raíz del árbol.
     * @param element El elemento a establecer.
     */
    @Override
    public void setRoot(T element) {
        this.element = element;
    }

    /**
     * Inserta un nuevo hijo en el árbol.
     * @param child El hijo a insertar.
     */
    @Override
    public void addChild(TreeIF<T> child) {
        children.add(child);
    }

    /**
     * Borra el hijo indicado por el índice partiendo de 0, que sería la raíz o primer
     * elemento de la lista.
     * @param index El índice del subarbol con base en 0.
     */
    @Override
    public void removeChild(int index) {
        QueueIF<TreeIF<T>> aux = new QueueStatic<TreeIF<T>>(capacity);
        for(int i = 0; i < children.getLength(); i++) {
            TreeIF<T> aChild = children.getFirst();
            if(i != index)
                aux.add(aChild);
            children.remove();
        }
        children = aux;
    }

    /**
     * Devuelve cierto si el nodo no tiene hijos.
     * @return true si el nodo no tiene hijos.
     */
    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    /**
     * Devuelve cierto si el árbol no contiene elementos.
     * @return True si el árbol no contiene elementos.
     */
    @Override
    public boolean isEmpty() {
        return element == null && children.isEmpty();
    }

    /**
     * Devuelve cierto si el árbol contiene el elemento indicado.
     * @param element El elemento que buscar.
     * @return True si contiene el elemento.
     */
    @Override
    public boolean contains(T element) {
        if(this.element.equals(element))
            return true;
        else {
            IteratorIF<TreeIF<T>> childrenIt = children.getIterator();
            boolean found = false;
            while(!found && childrenIt.hasNext()) {
                found = childrenIt.getNext().contains(element);
            }
            return found;
        }
    }

    /**
     * Devuelve un iterador con el que recorrer el árbol y todos su hijos.
     * @param type El tipo de iteración a ejecutar (0, 1, 2 o 3)
     * @return El iterador del árbol.
     */
    @Override
    public IteratorIF<T> getIterator(int type) {
        TreeIF<T> handler = new TreeStatic<T>(this, capacity);
        return new TreeIterator<T>(handler, type);
    }

    /**
     * Devuelve el hashcode del árbol.
     * @return El hascode del árbol.
     */
    @Override
    public int hashCode() {
        return 31 * 31 * ((element == null) ? 0 : element.hashCode()) + 31 * ((children
        == null) ? 0 : children.hashCode()) + 31 * capacity;
    }

    /**
     * Devuelve cierto si se compara con un árbol idéntico.
     * @param o El objeto con el que compararse.
     * @return True si son iguales.
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(o == this)
            return true;
        if(o.getClass() != this.getClass())
            return false;
        if(this.isEmpty() && ((TreeStatic<T>) o).isEmpty() && (this.capacity == (
                (TreeStatic<T>) o).capacity))
            return true;
        if(this.isEmpty() ^ ((TreeStatic<T>) o).isEmpty())
            return false;
        else {
            TreeStatic<T> t = (TreeStatic<T>) o;
            return (this.element == t.element) && (this.capacity == t.capacity) &&
                    (this.children == t.children);
        }
    }

    /**
     * Devuelve un String que representa la construcción del árbol.
     * @return String que representa la construcción del árbol.
     */
    @Override
    public String toString() {
        return "[Árbol estático - Raíz: " + element.toString() + " Hijo/s: \r\n\t\t" +
                children.toString() + " ]";
    }
}
