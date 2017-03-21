package ped.eped.tads;

/**
 *
 */
public class TreeIterator<T> implements IteratorIF<T> {
    /** Iterador de una Cola en el que se delega la iteración de los elementos del
     * árbol */
    private IteratorIF<T> iterator;

    /**
     * Constructor para TreeIterator.
     * @param handler el manejador de árboles.
     * @param type el tipo de recorrido.
     */
    public TreeIterator(TreeIF<T> handler, int type) {
        QueueIF<T> traverse = null;
        switch(type) {
            case TreeIF.PREORDER:
                traverse = preorder(handler);
                break;
            case TreeIF.POSTORDER:
                traverse = postorder(handler);
                break;
            case TreeIF.LRBREADTH:
                traverse = lrBreadth(handler);
                break;
            case TreeIF.RLBREADTH:
                traverse = rlBreadth(handler);
                break;
        }
        this.iterator = new QueueIterator<T>(traverse);
    }

    /**
     * Devuelve el siguiente elemento de la iteración.
     * @return El siguiente elemento de la iteración.
     */
    @Override
    public T getNext() {
        return iterator.getNext();
    }

    /**
     * Devuelve cierto si existen más elementos en el iterador.
     * @return True si existen más elementos en el iterador.
     */
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * Restablece el iterador para volver a recorrer la estructura.
     */
    @Override
    public void reset() {
        iterator.reset();
    }

    /**
     * Recorrido recursivo en profundidad empezando por la raíz del árbol.
     * @param tree El árbol a recorrer.
     * @return Una cola con los nodos ordenados.
     */
    private QueueIF<T> preorder(TreeIF<T> tree) {
        QueueIF<T> traverse = new QueueDynamic<T>();
        T element = tree.getRoot();
        traverse.add(element);
        IteratorIF<TreeIF<T>> childrenIt = tree.getChildren().getIterator();
        while(childrenIt.hasNext()) {
            TreeIF<T> aChild = childrenIt.getNext();
            QueueIF<T> aTraverse = preorder(aChild);
            addAll(traverse, aTraverse);
        }
        return traverse;
    }

    /**
     * Recorrido recursivo en profundidad empezando por las hojas más alejadas de la
     * raíz del árbol.
     * @param tree El árbol a recorrer.
     * @return Una cola con los nodos ordenados.
     */
    private QueueIF<T> postorder(TreeIF<T> tree) {
        QueueIF<T> traverse = new QueueDynamic<T>();
        T element = tree.getRoot();
        IteratorIF<TreeIF<T>> childrenIt = tree.getChildren().getIterator();
        while(childrenIt.hasNext()) {
            TreeIF<T> aChild = childrenIt.getNext();
            QueueIF<T> aTraverse = postorder(aChild);
            addAll(traverse, aTraverse);
        }
        traverse.add(element);
        return traverse;
    }

    /**
     * Recorrido en anchura empezando por la raíz.
     * @param tree El árbol a recorrer.
     * @return Una cola con los nodos ordenados.
     */
    public QueueIF<T> lrBreadth(TreeIF<T> tree) {
        QueueIF<T> traverse = new QueueDynamic<T>();
        QueueIF<TreeIF<T>> aux = new QueueDynamic<TreeIF<T>>();

        aux.add(tree);
        while(!aux.isEmpty()) {
            TreeIF<T> aTree = aux.getFirst();
            T element = aTree.getRoot();
            IteratorIF<TreeIF<T>> childrenIt = aTree.getChildren().getIterator();
            while(childrenIt.hasNext()) {
                TreeIF<T> aChild = childrenIt.getNext();
                aux.add(aChild);
            }
            traverse.add(element);
            aux.remove();
        }
        return traverse;
    }

    /**
     * Recorrido en anchura empezando por las hojas más alejadas de la raíz.
     * @param tree El árbol a recorrer.
     * @return Una cola con los nodos ordenados.
     */
    private QueueIF<T> rlBreadth(TreeIF<T> tree) {
        QueueIF<T> traverse = lrBreadth(tree);
        StackIF<T> aux = new StackDynamic<T>();
        while(!traverse.isEmpty()) {
            T element = traverse.getFirst();
            aux.push(element);
            traverse.remove();
        }
        while(!aux.isEmpty()) {
            T element = aux.getTop();
            traverse.add(element);
            aux.pop();
        }
        return traverse;
    }

    /**
     * Une dos colas, añadiendo todos los elementos de una a otra.
     * @param q La cola a la que añadir elementos.
     * @param p La cola desde la que añadir elementos.
     */
    private void addAll(QueueIF<T> q, QueueIF<T> p) {
        while(!p.isEmpty()) {
            T element = p.getFirst();
            q.add(element);
            p.remove();
        }
    }
}
