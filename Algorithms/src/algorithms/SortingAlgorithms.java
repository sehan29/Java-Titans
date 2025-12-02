/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algorithms;

import algorithms.SortAlgorithm;

/**
 *
 * @author HP
 */

// --- 1. Insertion Sort ---
class InsertionSort implements SortAlgorithm{
    
    @Override
    public String getName() { return "Insertion Sort"; }

    @Override
    public void sort(double[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            double key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }
}

// --- 1. End Insertion Sort ---
