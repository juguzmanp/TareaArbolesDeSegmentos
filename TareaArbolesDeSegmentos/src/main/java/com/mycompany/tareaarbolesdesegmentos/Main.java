/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tareaarbolesdesegmentos;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Leer tamaño del arreglo
        System.out.print("Ingresa el tamaño del arreglo: ");
        int n = sc.nextInt();

        int[] initialData = new int[n];

        // Leer elementos del arreglo
        System.out.println("Ingresa " + n + " números enteros:");
        for (int i = 0; i < n; i++) {
            initialData[i] = sc.nextInt();
        }

        // Construir Segment Tree
        SegmentTree st = new SegmentTree(initialData);

        System.out.println("\n--- CONSULTA DE VARIANZA ---");
        System.out.print("Ingresa índice inicial del rango: ");
        int l = sc.nextInt();
        System.out.print("Ingresa índice final del rango: ");
        int r = sc.nextInt();

        double variance = st.queryVariance(l, r);
        System.out.printf("Varianza de [%d, %d]: %.4f%n", l, r, variance);

        // Actualización
        System.out.println("\n--- ACTUALIZACIÓN ---");
        System.out.print("Ingresa índice a actualizar: ");
        int idx = sc.nextInt();
        System.out.print("Ingresa nuevo valor: ");
        int newValue = sc.nextInt();

        st.update(idx, newValue);
        System.out.println("Actualización realizada.");

        // Consultar nuevamente
        System.out.println("\n--- CONSULTA DESPUÉS DE ACTUALIZAR ---");
        System.out.print("Ingresa índice inicial del rango: ");
        l = sc.nextInt();
        System.out.print("Ingresa índice final del rango: ");
        r = sc.nextInt();

        variance = st.queryVariance(l, r);
        System.out.printf("Nueva varianza de [%d, %d]: %.4f%n", l, r, variance);

        sc.close();
    }
}