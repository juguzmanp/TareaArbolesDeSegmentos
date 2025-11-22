/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tareaarbolesdesegmentos;

/**
 *
 * @author Juanjo
 */
class SegmentTree {
    private NodeData[] tree;
    private int[] data;
    private int N;

    public SegmentTree(int[] data) {
        this.data = data;
        this.N = data.length;
        // El tamaño del array del árbol es típicamente 4*N para evitar desbordamientos.
        this.tree = new NodeData[4 * N];
        build(0, 0, N - 1);
    }

    // --- 1. Construcción del Árbol ---

    /**
     * Método recursivo para construir el árbol.
     * @param node Índice del nodo actual en el array 'tree'.
     * @param start Inicio del rango representado por este nodo.
     * @param end Fin del rango representado por este nodo.
     */
    private void build(int node, int start, int end) {
        if (start == end) {
            // Caso base: Nodo Hoja
            long val = data[start];
            tree[node] = new NodeData(val, val * val, 1);
        } else {
            // Nodo Interno
            int mid = (start + end) / 2;
            int leftChild = 2 * node + 1;
            int rightChild = 2 * node + 2;

            // Construir hijos recursivamente
            build(leftChild, start, mid);
            build(rightChild, mid + 1, end);

            // Combinar la información de los hijos
            tree[node] = NodeData.combine(tree[leftChild], tree[rightChild]);
        }
    }

    // --- 2. Actualización de Punto (Point Update) ---

    /**
     * Actualiza el valor en el índice 'idx' a 'newValue'.
     * @param idx El índice en el array original a actualizar.
     * @param newValue El nuevo valor a asignar.
     */
    public void update(int idx, int newValue) {
        update(0, 0, N - 1, idx, newValue);
    }

    private void update(int node, int start, int end, int idx, int newValue) {
        if (start == end) {
            // Caso base: Nodo Hoja. Actualizar el valor y la info.
            data[idx] = newValue; // Opcional: actualizar el array original
            long val = newValue;
            tree[node] = new NodeData(val, val * val, 1);
            return;
        }

        int mid = (start + end) / 2;
        int leftChild = 2 * node + 1;
        int rightChild = 2 * node + 2;

        if (idx >= start && idx <= mid) {
            // El índice a actualizar está en el hijo izquierdo
            update(leftChild, start, mid, idx, newValue);
        } else {
            // El índice a actualizar está en el hijo derecho
            update(rightChild, mid + 1, end, idx, newValue);
        }

        // Después de actualizar el hijo, se recalcula la información de este nodo.
        tree[node] = NodeData.combine(tree[leftChild], tree[rightChild]);
    }

    // --- 3. Consulta de Rango (Range Query) ---

    /**
     * Realiza una consulta para obtener la varianza del rango [l, r].
     * @param l Inicio del rango de consulta.
     * @param r Fin del rango de consulta.
     * @return La varianza del subarreglo A[l...r].
     */
    public double queryVariance(int l, int r) {
        // Primero, obtenemos los datos combinados para el rango [l, r]
        NodeData result = query(0, 0, N - 1, l, r);

        long totalSum = result.sum;
        long totalSumOfSquares = result.sumOfSquares;
        int count = result.count;

        if (count == 0 || count == 1) {
            // La varianza es 0 si el rango está vacío o tiene un solo elemento.
            return 0.0;
        }

        // Aplicar la fórmula de la varianza: (E[x^2] - (E[x])^2)
        // E[x] = Media = Suma / N
        // E[x^2] = Suma de Cuadrados / N

        double mean = (double) totalSum / count;
        double meanOfSquares = (double) totalSumOfSquares / count;

        double variance = meanOfSquares - (mean * mean);
        return variance;
    }

    /**
     * Método recursivo para obtener la información de rango [l, r].
     * @param node Índice del nodo actual.
     * @param start Inicio del rango del nodo.
     * @param end Fin del rango del nodo.
     * @param l Inicio del rango de consulta.
     * @param r Fin del rango de consulta.
     * @return Los datos combinados (suma, suma de cuadrados, conteo) del rango.
     */
    private NodeData query(int node, int start, int end, int l, int r) {
        // 1. Condición de No Superposición: El rango del nodo está completamente fuera del rango de consulta.
        if (r < start || end < l) {
            return new NodeData(); // Devuelve un dato neutro (0, 0, 0)
        }

        // 2. Condición de Superposición Total: El rango del nodo está completamente dentro del rango de consulta.
        if (l <= start && end <= r) {
            return tree[node]; // Devuelve directamente la información almacenada en el nodo
        }

        // 3. Condición de Superposición Parcial: Hay que buscar en los hijos.
        int mid = (start + end) / 2;
        int leftChild = 2 * node + 1;
        int rightChild = 2 * node + 2;

        NodeData p1 = query(leftChild, start, mid, l, r);
        NodeData p2 = query(rightChild, mid + 1, end, l, r);

        // Combina los resultados parciales de los hijos
        return NodeData.combine(p1, p2);
    }
}
