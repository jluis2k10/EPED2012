package ped.eped.tads;

/**
 * Created with IntelliJ IDEA.
 * User: Jose Luis
 * Date: 8/05/13
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public interface TreeIF<T> {
    /**
     * Constantes que sirven para identificar en el método {@link #getIterator}
     * distintas estrategias de recorrido de los elementos del árbol
     */
    public int PREORDER = 0;
    public int POSTORDER = 1;
    public int LRBREADTH = 2;
    public int RLBREADTH = 3;

    /**
     * Devuelve el elemento raíz del árbol.
     * @return El elemento raíz del árbol.
     */
    public T getRoot();

    /**
     * Establece el elemento raíz.
     * @param element El elemento a establecer.
     */
    public void setRoot(T element);

    /**
     * Devuelve una lista con todos los subárboles hijos del nodo en curso.
     * @return Los hijos del nodo.
     */
    public ListIF<TreeIF <T>> getChildren();

    /**
     * Inserta un subarbol como último hijo.
     * @param child El hijo a insertar.
     */
    public void addChild(TreeIF<T> child);

    /**
     * Extrae un subarbol.
     * @param index El índice del subarbol con base en 0.
     */
    public void removeChild(int index);

    /**
     * Devuelve cierto si el árbol es un nodo hoja.
     * @return True si el árbol es un nodo hoja.
     */
    public boolean isLeaf();

    /**
     * Devuelve cierto si el árbol está vacío.
     * @return True si el árbol está vacío.
     */
    public boolean isEmpty();

    /**
     * Devuelve cierto si el árbol contiene el elemento.
     * @param element El elemento buscado.
     * @return True si el árbol contiene el elemento buscado.
     */
    public boolean contains(T element);

    /**
     * Devuelve un iterador para recorrer el árbol.
     * @param traversalType El tipo de recorrido.
     * @return Un iterador para la lista.
     */
    public IteratorIF<T> getIterator(int traversalType);
}
