import java.util.LinkedList;
import java.util.Queue;
public class ImplementacaoAVLTree {
    private Node root;

    private static class Node {
        int element;
        Node left;
        Node right;
        int height;

        Node(int element) {
            this.element = element;
            this.height = 1;
        }
    }

    /**
     * Adiciona um elemnto à árvore
     * @param element o elemento que é adicionado
     */
    public void add(int element) {
        root = add(root,element);
    }

    /**
     * Método auxiliar para adicionar recursivamente um elemento e balancear a árvore
     *
     * @param node nó atual
     * @param element o elemento que é adicionado
     * @return o nodo já balanceado
     */
    private Node add(Node node, int element) {
        if (node == null) {
            return new Node(element);
        }

        if (element < node.element) {
            node.left = add(node.left, element);
        } else if (element > node.element) {
            node.right = add(node.right, element);
        } else {
            return node;
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));

        return balance(node);
    }

    /**
     * Retorna o pai de um elemento na árvore
     * @param element elemento que o pai deve ser retornado
     * @return o valor do pai do elemento
     */
    public Integer getParent(int element) {
        return getParent(root, element, null);
    }

    /**
     * Método auxiliar que encontra o pai recursivamente
     * @param node nó atual
     * @param element elemento que o pai deve ser retornado
     * @param parent o nó pai atual
     * @return o valordo pai do elemento
     */
    private Integer getParent(Node node, int element, Node parent) {
        if (node == null) {
            return null;
        }

        if (node.element == element) {
            return parent == null ? null : parent.element;
        }

        if (element < node.element) {
            return getParent(node.left, element, node);
        } else {
            return getParent(node.right, element, node);
        }
    }

    /**
     * Limpa a árvore
     */
    public void clear() {
    root = null;
    }

    /**
     * Verifica se o elemento está na árvore
     * @param element o elemento a ser verificado
     * @return true se o elemento estiver presente
     */
    public boolean contains(int element) {
        return contains(root, element);
    }

    /**
     * Método auxiliar pra verificar presença recursivamente
     * @param node nó atual
     * @param element o elemento que é verificado
     * @return true se o elemento estiver presente
     */
    private boolean contains(Node node, int element) {
        if (node == null) {
            return false;
        }

        if (element < node.element) {
            return contains(node.left, element);
        } else if (element > node.element) {
            return contains(node.right, element);
        } else {
            return true;
        }
    }

    /**
     * Altura da árvore
     * @return a altura da árvore
     */
    public int height() {
        return height(root);
    }

    /**
     * Método auxiliar que cálcula a altura de um nó
     * @param node o nó atual
     * @return altura do nó
     */
    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    /**
     *
     * @return número de elementos na árvore
     */
    public int size() {
        return size(root);
    }

    /**
     * Método auxiliar pra contar número de elementos recursivamente
     * @param node nó atual
     * @return número de elemento no nó
     */
    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.left) + size(node.right);
    }

    /**
     * Verifica se a árvore está vazia
     * @return true se a árvore estiver vazia
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Faz o caminhamento em ordem e imprime os elementos da árvore.
     */
    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    /**
     * Método auxiliar pra fazer o caminhamento em ordem recursivamente
     * @param node nó atual
     */
    private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println(node.element + " ");
            inOrder(node.right);
        }
    }

    /**
     * Balanceia a árvore depois de inserir um nó
     * @param node nó atual
     * @return nó balanceado
     */
    private Node balance(Node node) {
        int fatorBalanco = getFatorBalanco(node);

        if (fatorBalanco > 1) {
            if (getFatorBalanco(node.left) < 0) {
                node.left = leftRotate(node.left);
            }
            node = rightRotate(node);
        } else if (fatorBalanco < -1) {
            if (getFatorBalanco(node.right) > 0) {
                node.right = rightRotate(node.right);
            }
            node = leftRotate(node);
        }

        return node;
    }

    /**
     * Calcula o fator de balanceamento de um nó
     * @param node nó atual
     * @return fator de balanceamento
     */
    private int getFatorBalanco(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    /**
     * Faz a rotação à direita de um nó
     * @param y nó desbalanceado
     * @return novo nó raiz pós rotação
     */
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    /**
     * Faz a rotação à esquerda de um nó
     * @param x nó desbalanceado
     * @return novo nó raiz pós rotação
     */
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public void printTree() {
        if (root == null) {
            System.out.println("Árvore vazia!");
            return;
        }
        printTree(root, "", true);
    }

    private void printTree(Node node, String prefix, boolean isTail) {
        if (node.left != null) {
            printTree(node.left, prefix + (isTail ? "│   " : "    "), false);
        }
        System.out.println(prefix + (isTail ? "└── " : "┌── ") + node.element);
        if (node.right != null) {
            printTree(node.right, prefix + (isTail ? "    " : "│   "), true);
        }
    }



}
