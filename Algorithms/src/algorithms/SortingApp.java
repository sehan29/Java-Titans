/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algorithms;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class SortingApp extends JFrame {

    // Modules
    private CsvHandler csvHandler;
    private List<SortAlgorithm> algorithms;
    private PerformanceGraph graphPanel;


    // UI Components
    private JComboBox<String> columnSelector;
    private DefaultTableModel tableModel;
    private JTextArea statusArea;
    private JButton loadBtn, sortBtn;
    private JTable dataTable;
    
    public SortingApp() {
        super("Algorithm Performance Analyzer");
        csvHandler = new CsvHandler();
        initializeAlgorithms();
        initUI();
    }

    private void initializeAlgorithms() {
        algorithms = new ArrayList<>();
        algorithms.add(new InsertionSort());
        algorithms.add(new ShellSort());
        algorithms.add(new MergeSort());
        algorithms.add(new QuickSort()); 
        algorithms.add(new HeapSort());
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());

        // --- 1. Left Sidebar (Navigation & Controls) ---
        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sideBar.setBackground(new Color(30, 32, 38));
        sideBar.setPreferredSize(new Dimension(220, getHeight()));
        

        JLabel title = new JLabel("Algorithm Analysis");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(230, 230, 230)); // Light color for dark mode
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        loadBtn = new JButton("1. Upload CSV");
        loadBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        loadBtn.addActionListener(e -> loadCsvFile());

        JLabel colLabel = new JLabel("2. Select Numeric Column:");
        colLabel.setForeground(new Color(200, 200, 200));
        colLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        Dimension labelDim = new Dimension(250, 25);
        colLabel.setPreferredSize(labelDim);
        columnSelector = new JComboBox<>();
        columnSelector.setAlignmentX(Component.LEFT_ALIGNMENT);
        columnSelector.setEnabled(false);
        columnSelector.setBackground(new Color(50, 50, 58));
        columnSelector.setForeground(Color.WHITE);
        columnSelector.setFocusable(false);

        Dimension comboDim = new Dimension(250, 30);
        columnSelector.setPreferredSize(comboDim);
        columnSelector.setMinimumSize(comboDim);
        columnSelector.setMaximumSize(comboDim);

        sortBtn = new JButton("3. Run Analysis");
        sortBtn.setEnabled(false);
        sortBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        sortBtn.setBackground(new Color(32, 136, 203));
        sortBtn.setForeground(Color.WHITE);
        sortBtn.setFocusPainted(false);

        sortBtn.addActionListener(e -> runSortingAnalysis());

        // LOG SECTION — Console Style
        statusArea = new JTextArea(10, 15);
        statusArea.setEditable(false);
        statusArea.setLineWrap(true);
        statusArea.setWrapStyleWord(true);
        statusArea.setBackground(new Color(20, 22, 28));  // Dark console
        statusArea.setForeground(new Color(0, 255, 120)); // Green terminal text
        statusArea.setFont(new Font("Consolas", Font.PLAIN, 13));

        JScrollPane statusScroll = new JScrollPane(statusArea);
        statusScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusScroll.setBorder(
            BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80)),
            "Log",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 12),
            new Color(200, 200, 200)
            )
        );

        // Add components to sidebar with spacing
        sideBar.add(title);
        sideBar.add(Box.createRigidArea(new Dimension(0, 20)));
        sideBar.add(loadBtn);
        sideBar.add(Box.createRigidArea(new Dimension(0, 15)));
        sideBar.add(colLabel);
        sideBar.add(columnSelector);
        sideBar.add(Box.createRigidArea(new Dimension(0, 15)));
        sideBar.add(sortBtn);
        sideBar.add(Box.createRigidArea(new Dimension(0, 20)));
        sideBar.add(statusScroll);

        // --- 2. Right Side (Table & Graph) ---
        // Top: Table
        
        // Implement View Data Logic Here
        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(dataTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Data View"));

        // Bottom: Graph
        graphPanel = new PerformanceGraph();

        // Display Bar Chart Here
        // Split Pane (Vertical split between Table and Graph)
        JSplitPane rightSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScroll, graphPanel);
        rightSplit.setDividerLocation(350);
        rightSplit.setResizeWeight(0.5);

        // Main Split Pane (Horizontal split between Sidebar and Right Content)
        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sideBar, rightSplit);
        mainSplit.setDividerLocation(230); // Fixed sidebar width
        mainSplit.setOneTouchExpandable(true);

        add(mainSplit, BorderLayout.CENTER);
    }

    private void loadCsvFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (csvHandler.loadFile(file)) {
                updateColumnSelector();
                populateTableRaw();
                
                statusArea.append("================================\n");
                statusArea.append("Welcome To Java Titans Console\n");
                statusArea.append("================================\n");

                statusArea.append("Loaded: " + file.getName() + "\n");
                sortBtn.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error loading file.");
            }
        }
    }

    private void updateColumnSelector() {
        columnSelector.removeAllItems();
        for (String header : csvHandler.getHeaders()) {
            columnSelector.addItem(header);
        }
        columnSelector.setEnabled(true);
    }

    private void populateTableRaw() {
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(csvHandler.getHeaders());
        for (String[] row : csvHandler.getRawData()) {
            tableModel.addRow(row);
        }
    }

    
 
  // Implement Sortest Algorithm Logic Here 
  // Function Name runSortingAnalysis()
    private void runSortingAnalysis() {
        int colIndex = columnSelector.getSelectedIndex();
        if (colIndex < 0) return;
        String selectedColName = (String) columnSelector.getSelectedItem();

        statusArea.append("\nStarting Analysis...\n");
        statusArea.append("--------------------------------\n");
        statusArea.append("Target Column: " + selectedColName + "\n");
        try {
            // 1. Get Data
            double[] originalData = csvHandler.getColumnData(colIndex);
            
            Map<String, Long> results = new HashMap<>();
            String bestAlgo = "";
            long minTime = Long.MAX_VALUE;
            double[] sortedResult = null;

            // 2. Run Algorithms
            // Print table header
            statusArea.append("--------------------------------\n");
            statusArea.append(String.format("%-15s | %-15s\n", "Algorithm", "Time (ns)"));
            statusArea.append("--------------------------------\n");

for (SortAlgorithm algo : algorithms) {

    double[] dataCopy = Arrays.copyOf(originalData, originalData.length);

    long startTime = System.nanoTime();
    algo.sort(dataCopy);
    long endTime = System.nanoTime();

    long duration = endTime - startTime;
    results.put(algo.getName(), duration);

    // Add row to table
    statusArea.append(
        String.format("%-20s | %-15d\n", algo.getName(), duration)
    );

    if (duration < minTime) {
        minTime = duration;
        bestAlgo = algo.getName();
        sortedResult = dataCopy;
    }
}

            // End line
            statusArea.append("--------------------------------\n");

            
            statusArea.append("=================================\n");
            statusArea.append("| Best: " + bestAlgo + "     |"+"\n");
            statusArea.append("=================================\n");

            // 3. Update UI
            graphPanel.setResults(results, bestAlgo);
            JOptionPane.showMessageDialog(this, "Sorting Complete!\nFastest Algorithm: " + bestAlgo);

        } catch (Exception e) {
            statusArea.append("Error: " + e.getMessage() + "\n");
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error parsing column. Ensure it is numeric.");
        }
    }


    public static void main(String[] args) {
        // Run on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            SortingApp app = new SortingApp();
            app.setVisible(true);
        });
    }
}
