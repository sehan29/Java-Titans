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

// --- 2. Shell Sort ---
class ShellSort implements SortAlgorithm {
    @Override
    public String getName() { return "Shell Sort"; }

    @Override
    public void sort(double[] arr) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i += 1) {
                double temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap)
                    arr[j] = arr[j - gap];
                arr[j] = temp;
            }
        }
    }
}
// --- 2. End Shell Sort ---


// --- 5. Heap Sort ---
class HeapSort implements SortAlgorithm {
    @Override
    public String getName() { return "Heap Sort"; }

    @Override
    public void sort(double[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);
        for (int i = n - 1; i > 0; i--) {
            double temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }

    void heapify(double[] arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && arr[l] > arr[largest]) largest = l;
        if (r < n && arr[r] > arr[largest]) largest = r;
        if (largest != i) {
            double swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest);
        }
    }
}
// --- 5. End Heap Sort ---

// --- 6. Merge Sort ---
class MergeSort implements SortAlgorithm {
    @Override
    public String getName() { return "Merge Sort"; }

    @Override
    public void sort(double[] arr) {
        if (arr.length < 2) return;
        int mid = arr.length / 2;
        double[] l = new double[mid];
        double[] r = new double[arr.length - mid];

        System.arraycopy(arr, 0, l, 0, mid);
        System.arraycopy(arr, mid, r, 0, arr.length - mid);

        sort(l);
        sort(r);
        merge(arr, l, r);
    }

    private void merge(double[] arr, double[] l, double[] r) {
        int i = 0, j = 0, k = 0;
        while (i < l.length && j < r.length) {
            if (l[i] <= r[j]) arr[k++] = l[i++];
            else arr[k++] = r[j++];
        }
        while (i < l.length) arr[k++] = l[i++];
        while (j < r.length) arr[k++] = r[j++];
    }
}
// --- 6. End Merge Sort ---

// --- 7. Quick Sort ---
class QuickSort implements SortAlgorithm {
    @Override
    public String getName() { return "Quick Sort"; }

    @Override
    public void sort(double[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(double[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);
            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private int partition(double[] arr, int begin, int end) {
        double pivot = arr[end];
        int i = (begin - 1);
        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
                double swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }
        double swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;
        return i + 1;
    }
}
// --- 7. End Quick Sort ---