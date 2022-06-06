package org.example.model;
import org.example.model.node.BinaryNode;

public class BinaryTree {
    private BinaryNode root;

    public BinaryTree() {
        this.root = null;
    }

    public BinaryNode getRoot() {
        return root;
    }

    // проверка на пустоту
    public boolean isEmpty(BinaryNode node) {
        return node == null;
    }

    // добавление узла
    public void addNode(int value) {
        BinaryNode node = new BinaryNode(value, null, null); // добавляемый узел
        if (isEmpty(this.root)) {
            this.root = node;
        } else {
            BinaryNode currentNode = this.root; // текущий узел
            while (currentNode != null) {
                if (value > currentNode.key) {
                    if (currentNode.right == null) {
                        currentNode.right = node;
                        return;
                    }
                    currentNode = currentNode.right;
                } else {
                    if (currentNode.left == null) {
                        currentNode.left = node;
                        return;
                    }
                    currentNode = currentNode.left;
                }
            }
        }
    }

    // построение дерева
    public void createTree(int[] keys) {
        this.addNode(keys[keys.length / 2]);
        for (int i = 0; i < keys.length / 2; i++) {
            this.addNode(keys[i]);
        }
        for (int i = keys.length / 2 + 1; i < keys.length; i++) {
            this.addNode(keys[i]);
        }
    }

    // симметричный обход
    public void inOrder(BinaryNode node) {
        if (node != null) {
            this.inOrder(node.left);
            System.out.print(node.key + " ");
            this.inOrder(node.right);
        }
    }

    // поиск по близости сверху
    public int searchBigger(int num) {
        return this.root.searchBigger(num).key;
    }

    private void printTree(String prefix, BinaryNode node) {
        if (node != null) {
            String spaces = "   ";
            System.out.println(prefix + node.key);
            printTree(prefix + spaces, node.left);
            printTree(prefix + spaces, node.right);
        }
    }

    // вывод дерева
    public void print() {
        printTree("", this.root);
    }
}
