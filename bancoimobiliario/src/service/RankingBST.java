package service;

import java.util.ArrayList;
import java.util.List;

/**
 * BST que organiza jogadores pelo patrimônio total (saldo + propriedades),
 * com percurso inverso para ranking do maior ao menor.
 */
public class RankingBST {
    private class Node {
        Jogador j;
        Node left, right;
        Node(Jogador j) { this.j = j; }
    }

    private Node root;

    public void add(Jogador j) {
        root = insert(root, j);
    }

    private Node insert(Node node, Jogador j) {
        if (node == null) return new Node(j);
        double pj = j.calcularPatrimonio();
        double pn = node.j.calcularPatrimonio();
        if (pj < pn) node.left = insert(node.left, j);
        else node.right = insert(node.right, j);
        return node;
    }

    public List<Jogador> toListDesc() {
        List<Jogador> list = new ArrayList<>();
        traverseDesc(root, list);
        return list;
    }

    private void traverseDesc(Node n, List<Jogador> list) {
        if (n == null) return;
        traverseDesc(n.right, list);
        list.add(n.j);
        traverseDesc(n.left, list);
    }
}
