/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algorithms;
import java.io.*;
import java.util.*;

public class CsvHandler {
    private List<String[]> rawData;
    private String[] headers;

    public CsvHandler() {
        rawData = new ArrayList<>();
    }

    // Load CSV file
    public boolean loadFile(File file) {
        rawData.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                // Basic CSV splitting (handling commas)
                String[] values = line.split(","); 
                if (isHeader) {
                    headers = values;
                    isHeader = false;
                } else {
                    rawData.add(values);
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String[] getHeaders() {
        return headers;
    }

    public List<String[]> getRawData() {
        return rawData;
    }

    // Extract a specific column as doubles for sorting
    public double[] getColumnData(int columnIndex) throws NumberFormatException {
        double[] data = new double[rawData.size()];
        for (int i = 0; i < rawData.size(); i++) {
            String val = rawData.get(i)[columnIndex].trim();
            // Try parse, default to 0.0 if empty or invalid
            try {
                data[i] = Double.parseDouble(val);
            } catch (NumberFormatException e) {
                data[i] = 0.0;
            }
        }
        return data;
    }
}
