/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tareaarbolesdesegmentos;

/**
 *
 * @author Juanjo
 */
class NodeData {
    long sum;
    long sumOfSquares;
    int count;

    public NodeData(long sum, long sumOfSquares, int count) {
        this.sum = sum;
        this.sumOfSquares = sumOfSquares;
        this.count = count;
    }

    // Constructor para un nodo vacío (usado al iniciar)
    public NodeData() {
        this(0, 0, 0);
    }

    /**
     * Combina la información de dos nodos (hijos) en un nodo padre.
     * La clave de la eficiencia es que las sumas son fácilmente combinables.
     */
    public static NodeData combine(NodeData left, NodeData right) {
        long newSum = left.sum + right.sum;
        long newSumOfSquares = left.sumOfSquares + right.sumOfSquares;
        int newCount = left.count + right.count;
        return new NodeData(newSum, newSumOfSquares, newCount);
    }
}