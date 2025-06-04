package components;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.*;
import java.io.Serializable;

/**
 * CustomTable - Reusable table component for NetBeans GUI Builder
 * @author Syamil
 */
public class CustomTable extends JPanel implements Serializable {
    
    // JavaBean properties
    private String[] columnNames = {"Column 1", "Column 2", "Column 3"};
    private Object[][] tableData = {{"Data 1", "Data 2", "Data 3"}};
    private boolean showActionButtons = true;
    private Color headerBackgroundColor = new Color(248, 249, 250);
    private Color headerTextColor = new Color(73, 80, 87);
    private Color alternateRowColor = new Color(249, 249, 249);
    private int tableRowHeight = 50;
    private Font tableFont = new Font("Segoe UI Emoji", Font.PLAIN, 14);
    private Font headerFont = new Font("Segoe UI Emoji", Font.BOLD, 14);
    
    // Components
    private JTable table;
    private DefaultTableModel model;
    private ActionButtonListener actionListener;
    private PropertyChangeSupport propertySupport;
    
    public interface ActionButtonListener {
        void onEdit(int row, Object[] rowData);
        void onDelete(int row, Object[] rowData);
    }
    
    // Default constructor for GUI Builder
    public CustomTable() {
        // Initialize PropertyChangeSupport FIRST before calling super constructor
        propertySupport = new PropertyChangeSupport(this);
        
        try {
            initComponents();
        } catch (Exception e) {
            // Fallback initialization for GUI Builder
            System.err.println("Error in CustomTable constructor: " + e.getMessage());
            e.printStackTrace();
            initFallback();
        }
    }
    
    // Constructor with parameters
    public CustomTable(String[] columnNames, Object[][] data) {
        // Initialize PropertyChangeSupport FIRST
        propertySupport = new PropertyChangeSupport(this);
        
        setColumns(columnNames);
        setData(data);
        
        try {
            initComponents();
        } catch (Exception e) {
            System.err.println("Error in CustomTable parameterized constructor: " + e.getMessage());
            initFallback();
        }
    }
    
    public CustomTable(String[] columnNames, Object[][] data, ActionButtonListener listener) {
        // Initialize PropertyChangeSupport FIRST
        propertySupport = new PropertyChangeSupport(this);
        
        setColumns(columnNames);
        setData(data);
        this.actionListener = listener;
        
        try {
            initComponents();
        } catch (Exception e) {
            System.err.println("Error in CustomTable full constructor: " + e.getMessage());
            initFallback();
        }
    }
    
    private void initComponents() {
        try {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(600, 300));
            
            createTable();
        } catch (Exception e) {
            System.err.println("Error in initComponents: " + e.getMessage());
            initFallback();
        }
    }
    
    /**
     * Fallback initialization for GUI Builder compatibility
     */
    private void initFallback() {
        try {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(600, 300));
            
            // Create simple label as placeholder
            JLabel placeholder = new JLabel("CustomTable - Loading...", JLabel.CENTER);
            placeholder.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            placeholder.setForeground(Color.GRAY);
            add(placeholder, BorderLayout.CENTER);
            
            // Initialize with minimal data
            if (propertySupport == null) {
                propertySupport = new PropertyChangeSupport(this);
            }
            
            // Schedule proper initialization for later
            SwingUtilities.invokeLater(() -> {
                try {
                    remove(placeholder);
                    createTable();
                    revalidate();
                    repaint();
                } catch (Exception ex) {
                    System.err.println("Error in delayed initialization: " + ex.getMessage());
                }
            });
            
        } catch (Exception e) {
            System.err.println("Critical error in fallback initialization: " + e.getMessage());
            // Last resort - just set basic layout
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(600, 300));
        }
    }
    
    private void createTable() {
        try {
            // Ensure we have valid data
            if (columnNames == null || columnNames.length == 0) {
                columnNames = new String[]{"Column 1", "Column 2", "Column 3"};
            }
            if (tableData == null || tableData.length == 0) {
                tableData = new Object[][]{{"Data 1", "Data 2", "Data 3"}};
            }
            
            // Create table model with error handling
            model = new DefaultTableModel(tableData, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    try {
                        return column == getColumnCount() - 1 && showActionButtons && hasActionColumn();
                    } catch (Exception e) {
                        return false;
                    }
                }
            };
            
            // Remove existing scrollpane if present
            if (table != null) {
                Component parent = table.getParent();
                if (parent != null && parent.getParent() instanceof JScrollPane) {
                    remove(parent.getParent());
                }
            }
            
            table = new JTable(model);
            customizeTable();
            
            // Add custom renderer and editor for action column if exists
            if (showActionButtons && hasActionColumn()) {
                try {
                    int actionColumnIndex = table.getColumnCount() - 1;
                    table.getColumnModel().getColumn(actionColumnIndex).setCellRenderer(new ActionButtonRenderer());
                    table.getColumnModel().getColumn(actionColumnIndex).setCellEditor(new ActionButtonEditor());
                } catch (Exception e) {
                    System.err.println("Error setting action column renderer/editor: " + e.getMessage());
                }
            }
            
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240), 1));
            scrollPane.getViewport().setBackground(Color.WHITE);
            
            // Custom clean scrollbar with error handling
            try {
                scrollPane.setVerticalScrollBar(new CustomScrollBar(JScrollBar.VERTICAL));
                scrollPane.setHorizontalScrollBar(new CustomScrollBar(JScrollBar.HORIZONTAL));
                scrollPane.getVerticalScrollBar().setUnitIncrement(16);
                scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
            } catch (Exception e) {
                System.err.println("Error setting custom scrollbars: " + e.getMessage());
                // Use default scrollbars
            }
            
            add(scrollPane, BorderLayout.CENTER);
            revalidate();
            repaint();
            
        } catch (Exception e) {
            System.err.println("Error in createTable: " + e.getMessage());
            e.printStackTrace();
            
            // Fallback: create simple table
            try {
                JTable simpleTable = new JTable(new String[][]{{"Loading..."}}, new String[]{"Data"});
                JScrollPane simpleScrollPane = new JScrollPane(simpleTable);
                add(simpleScrollPane, BorderLayout.CENTER);
            } catch (Exception ex) {
                // Last resort
                add(new JLabel("Error loading table", JLabel.CENTER), BorderLayout.CENTER);
            }
        }
    }
    
    private boolean hasActionColumn() {
        try {
            return model != null && model.getColumnCount() > 0 && 
                   model.getColumnName(model.getColumnCount() - 1).toLowerCase().contains("aksi");
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
    * Forces solid header background by overriding LAF renderer
    */
    private void forceSolidHeaderBackground() {
       if (table == null || table.getTableHeader() == null) return;

       JTableHeader header = table.getTableHeader();

       // Method 1: Custom HeaderRenderer to override LAF
       header.setDefaultRenderer(new DefaultTableCellRenderer() {
           @Override
           public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Force solid background - override any LAF gradients
                setBackground(headerBackgroundColor != null ? headerBackgroundColor : new Color(248, 249, 250));
                setForeground(headerTextColor != null ? headerTextColor : new Color(73, 80, 87));
                setFont(headerFont != null ? headerFont : new Font("Segoe UI", Font.BOLD, 14));

                // Center the text
                setHorizontalAlignment(SwingConstants.CENTER);

                // Remove any borders that might cause gradient effects
                setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

                // Make sure it's opaque to override LAF transparency
                setOpaque(true);

                return c;
            }

            @Override
            protected void paintComponent(Graphics g) {
                // Force solid background painting
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(getBackground());
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();

                // Paint text
                super.paintComponent(g);
            }
       });

       // Method 2: Override header UI painting
       header.setUI(new javax.swing.plaf.basic.BasicTableHeaderUI() {
           @Override
           public void paint(Graphics g, JComponent c) {
               Graphics2D g2d = (Graphics2D) g.create();

               // Paint solid background
               g2d.setColor(headerBackgroundColor != null ? headerBackgroundColor : new Color(248, 249, 250));
               g2d.fillRect(0, 0, c.getWidth(), c.getHeight());

               // Paint border
               g2d.setColor(new Color(220, 220, 220));
               g2d.drawLine(0, c.getHeight() - 1, c.getWidth(), c.getHeight() - 1);

               g2d.dispose();

               // Let the default renderer paint the text
               super.paint(g, c);
           }
       });

       // Method 3: Force header properties
       header.setBackground(headerBackgroundColor != null ? headerBackgroundColor : new Color(248, 249, 750));
       header.setOpaque(true);
       header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
   }

    
    private void customizeTable() {
        try {
            if (table == null) return;
            
            // Table styling
            table.setFont(tableFont != null ? tableFont : new Font("Segoe UI", Font.PLAIN, 14));
            table.setRowHeight(tableRowHeight > 0 ? tableRowHeight : 50);
            table.setShowGrid(false);
            table.setIntercellSpacing(new Dimension(0, 0));
            table.setSelectionBackground(new Color(245, 245, 245));
            table.setBackground(Color.WHITE);
            
            // Header styling
            JTableHeader header = table.getTableHeader();
            if (header != null) {
                header.setFont(headerFont != null ? headerFont : new Font("Segoe UI", Font.BOLD, 14));
                header.setBackground(headerBackgroundColor != null ? headerBackgroundColor : new Color(248, 249, 250));
                header.setForeground(headerTextColor != null ? headerTextColor : new Color(73, 80, 87));
                header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
                header.setReorderingAllowed(false);
                
                // FORCE SOLID BACKGROUND - Add this line
                forceSolidHeaderBackground();
            }
            
            // Auto-resize columns based on content
            autoResizeColumns();
            
            // Custom row renderer for alternating colors
            table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    
                    try {
                        // ALWAYS set text color to black, regardless of selection
                        c.setForeground(Color.BLACK);
            
                        if (!isSelected) {
                            if (row % 2 == 0) {
                                c.setBackground(Color.WHITE);
                            } else {
                                c.setBackground(alternateRowColor != null ? alternateRowColor : new Color(249, 249, 249));
                            }
                        }
                        
                        setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
                        setFont(tableFont != null ? tableFont : new Font("Segoe UI", Font.PLAIN, 14));
                    } catch (Exception e) {
                        // Use defaults if anything fails
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK); // Force black text even when selected
                    }
                    
                    return c;
                }
            });
            
        } catch (Exception e) {
            System.err.println("Error in customizeTable: " + e.getMessage());
        }
    }
    
    private void autoResizeColumns() {
        try {
            if (table == null || table.getColumnModel() == null) return;
            
            for (int i = 0; i < table.getColumnCount(); i++) {
                TableColumn column = table.getColumnModel().getColumn(i);
                String columnName = model.getColumnName(i);
                
                if (columnName == null) continue;
                
                // Set preferred widths based on column name
                String lowerColumnName = columnName.toLowerCase();
                if (lowerColumnName.contains("id")) {
                    column.setPreferredWidth(80);
                } else if (lowerColumnName.contains("username") || 
                          lowerColumnName.contains("role")) {
                    column.setPreferredWidth(120);
                } else if (lowerColumnName.contains("name") || 
                          lowerColumnName.contains("email")) {
                    column.setPreferredWidth(200);
                } else if (lowerColumnName.contains("aksi") || 
                          lowerColumnName.contains("action")) {
                    column.setPreferredWidth(100);
                } else {
                    column.setPreferredWidth(150);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in autoResizeColumns: " + e.getMessage());
        }
    }
    
    // JavaBean Properties with PropertyChangeSupport
    
    /**
     * Gets the column names for the table
     * @return array of column names
     */
    public String[] getColumns() {
        return columnNames.clone();
    }
    
    /**
     * Sets the column names for the table
     * @param columns array of column names
     */
    public void setColumns(String[] columns) {
        String[] oldColumns = this.columnNames;
        this.columnNames = columns.clone();
        
        // Safe property change firing
        if (propertySupport != null) {
            propertySupport.firePropertyChange("columns", oldColumns, this.columnNames);
        }
        
        if (model != null) {
            refreshTable();
        }
    }
    
    /**
     * Gets the table data as 2D array
     * @return 2D array of table data
     */
    public Object[][] getData() {
        if (tableData == null) return new Object[0][0];
        Object[][] copy = new Object[tableData.length][];
        for (int i = 0; i < tableData.length; i++) {
            copy[i] = tableData[i].clone();
        }
        return copy;
    }
    
    /**
     * Sets the table data as 2D array
     * @param data 2D array of table data
     */
    public void setData(Object[][] data) {
        Object[][] oldData = this.tableData;
        if (data == null) {
            this.tableData = new Object[0][0];
        } else {
            this.tableData = new Object[data.length][];
            for (int i = 0; i < data.length; i++) {
                this.tableData[i] = data[i].clone();
            }
        }
        
        // Safe property change firing
        if (propertySupport != null) {
            propertySupport.firePropertyChange("data", oldData, this.tableData);
        }
        
        if (model != null) {
            refreshTable();
        }
    }
    
    /**
     * Gets whether action buttons are shown
     * @return true if action buttons are visible
     */
    public boolean isShowActionButtons() {
        return showActionButtons;
    }
    
    /**
     * Sets whether to show action buttons (Edit/Delete)
     * @param showActionButtons true to show action buttons
     */
    public void setShowActionButtons(boolean showActionButtons) {
        boolean oldValue = this.showActionButtons;
        this.showActionButtons = showActionButtons;
        
        // Safe property change firing
        if (propertySupport != null) {
            propertySupport.firePropertyChange("showActionButtons", oldValue, showActionButtons);
        }
        
        if (model != null) {
            refreshTable();
        }
    }
    
    /**
     * Gets the header background color
     * @return header background color
     */
    public Color getHeaderBackgroundColor() {
        return headerBackgroundColor;
    }
    
    /**
     * Sets the header background color
     * @param headerBackgroundColor the color for header background
     */
    public void setHeaderBackgroundColor(Color headerBackgroundColor) {
        Color oldColor = this.headerBackgroundColor;
        this.headerBackgroundColor = headerBackgroundColor;
        
        // Safe property change firing
        if (propertySupport != null) {
            propertySupport.firePropertyChange("headerBackgroundColor", oldColor, headerBackgroundColor);
        }
        
        if (table != null) {
            table.getTableHeader().setBackground(headerBackgroundColor);
            repaint();
        }
    }
    
    /**
     * Gets the header text color
     * @return header text color
     */
    public Color getHeaderTextColor() {
        return headerTextColor;
    }
    
    /**
     * Sets the header text color
     * @param headerTextColor the color for header text
     */
    public void setHeaderTextColor(Color headerTextColor) {
        Color oldColor = this.headerTextColor;
        this.headerTextColor = headerTextColor;
        
        // Safe property change firing
        if (propertySupport != null) {
            propertySupport.firePropertyChange("headerTextColor", oldColor, headerTextColor);
        }
        
        if (table != null) {
            table.getTableHeader().setForeground(headerTextColor);
            repaint();
        }
    }
    
    /**
     * Gets the alternate row background color
     * @return alternate row color
     */
    public Color getAlternateRowColor() {
        return alternateRowColor;
    }
    
    /**
     * Sets the alternate row background color
     * @param alternateRowColor the color for alternate rows
     */
    public void setAlternateRowColor(Color alternateRowColor) {
        Color oldColor = this.alternateRowColor;
        this.alternateRowColor = alternateRowColor;
        
        // Safe property change firing
        if (propertySupport != null) {
            propertySupport.firePropertyChange("alternateRowColor", oldColor, alternateRowColor);
        }
        
        if (table != null) {
            repaint();
        }
    }
    
    /**
     * Gets the table row height
     * @return row height in pixels
     */
    public int getTableRowHeight() {
        return tableRowHeight;
    }
    
    /**
     * Sets the table row height
     * @param tableRowHeight row height in pixels
     */
    public void setTableRowHeight(int tableRowHeight) {
        int oldHeight = this.tableRowHeight;
        this.tableRowHeight = tableRowHeight;
        
        // Safe property change firing
        if (propertySupport != null) {
            propertySupport.firePropertyChange("tableRowHeight", oldHeight, tableRowHeight);
        }
        
        if (table != null) {
            table.setRowHeight(tableRowHeight);
        }
    }
    
    /**
     * Gets the font for table content
     * @return table font
     */
    public Font getTableFont() {
        return tableFont;
    }
    
    /**
     * Sets the font for table content
     * @param tableFont font for table content
     */
    public void setTableFont(Font tableFont) {
        Font oldFont = this.tableFont;
        this.tableFont = tableFont;
        
        // Safe property change firing
        if (propertySupport != null) {
            propertySupport.firePropertyChange("tableFont", oldFont, tableFont);
        }
        
        if (table != null) {
            table.setFont(tableFont);
            repaint();
        }
    }
    
    /**
     * Gets the font for table header
     * @return header font
     */
    public Font getHeaderFont() {
        return headerFont;
    }
    
    /**
     * Sets the font for table header
     * @param headerFont font for table header
     */
    public void setHeaderFont(Font headerFont) {
        Font oldFont = this.headerFont;
        this.headerFont = headerFont;
        
        // Safe property change firing
        if (propertySupport != null) {
            propertySupport.firePropertyChange("headerFont", oldFont, headerFont);
        }
        
        if (table != null) {
            table.getTableHeader().setFont(headerFont);
            repaint();
        }
    }
    
    // PropertyChangeSupport methods with null checks
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (propertySupport == null) {
            propertySupport = new PropertyChangeSupport(this);
        }
        propertySupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        if (propertySupport == null) {
            propertySupport = new PropertyChangeSupport(this);
        }
        propertySupport.removePropertyChangeListener(listener);
    }
    
    // Public methods for table manipulation
    public void addRow(Object[] rowData) {
        model.addRow(rowData);
    }
    
    public void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < model.getRowCount()) {
            model.removeRow(rowIndex);
        }
    }
    
    public void updateRow(int rowIndex, Object[] newData) {
        if (rowIndex >= 0 && rowIndex < model.getRowCount()) {
            for (int i = 0; i < newData.length && i < model.getColumnCount(); i++) {
                model.setValueAt(newData[i], rowIndex, i);
            }
        }
    }
    
    public Object[] getRowData(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < model.getRowCount()) {
            Object[] rowData = new Object[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                rowData[i] = model.getValueAt(rowIndex, i);
            }
            return rowData;
        }
        return null;
    }
    
    public void clearTable() {
        model.setRowCount(0);
    }
    
    public int getRowCount() {
        return model.getRowCount();
    }
    
    public JTable getTable() {
        return table;
    }
    
    public void setActionButtonListener(ActionButtonListener listener) {
        this.actionListener = listener;
    }
    
    /**
     * Gets the current table model
     * @return DefaultTableModel currently used by the table
     */
    public DefaultTableModel getModel() {
        return model;
    }
    
    /**
     * Sets a new DefaultTableModel for the table
     * This allows direct use of DefaultTableModel with all its features
     * @param tableModel the DefaultTableModel to use
     */
    public void setModel(DefaultTableModel tableModel) {
        DefaultTableModel oldModel = this.model;
        
        if (tableModel == null) {
            throw new IllegalArgumentException("Table model cannot be null");
        }
        
        // Create a custom model that extends the provided model to handle action column editing
        this.model = new DefaultTableModel(tableModel.getDataVector(), 
                                         convertToVector(tableModel.getColumnCount(), tableModel)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Check if it's action column and action buttons are enabled
                if (column == getColumnCount() - 1 && showActionButtons && hasActionColumn()) {
                    return true;
                }
                // Otherwise, delegate to original model's editability rules
                return tableModel.isCellEditable(row, column);
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return tableModel.getColumnClass(columnIndex);
            }
        };
        
        // Update internal properties from the new model
        updatePropertiesFromModel();
        
        // Safe property change firing
        if (propertySupport != null) {
            propertySupport.firePropertyChange("model", oldModel, this.model);
        }
        
        // Refresh the table with new model
        refreshTable();
    }
    
    /**
     * Helper method to convert column identifiers from DefaultTableModel
     * @param columnCount number of columns
     * @param sourceModel source DefaultTableModel
     * @return Vector of column identifiers
     */
    private java.util.Vector<String> convertToVector(int columnCount, DefaultTableModel sourceModel) {
        java.util.Vector<String> columnVector = new java.util.Vector<>();
        for (int i = 0; i < columnCount; i++) {
            columnVector.add(sourceModel.getColumnName(i));
        }
        return columnVector;
    }
    
    /**
     * Updates internal properties based on current model
     */
    private void updatePropertiesFromModel() {
        if (model != null) {
            // Update column names
            String[] newColumnNames = new String[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                newColumnNames[i] = model.getColumnName(i);
            }
            this.columnNames = newColumnNames;
            
            // Update table data
            Object[][] newData = new Object[model.getRowCount()][model.getColumnCount()];
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    newData[i][j] = model.getValueAt(i, j);
                }
            }
            this.tableData = newData;
        }
    }
    
    /**
     * Creates a DefaultTableModel with specified columns and data
     * @param columnNames array of column names
     * @param data 2D array of data
     * @return configured DefaultTableModel
     */
    public static DefaultTableModel createTableModel(String[] columnNames, Object[][] data) {
        return new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // By default, make action column editable if it's the last column and named "aksi"
                if (column == getColumnCount() - 1 && 
                    getColumnName(column).toLowerCase().contains("aksi")) {
                    return true;
                }
                return false; // Other columns are not editable by default
            }
        };
    }
    
    /**
     * Creates a DefaultTableModel with editable columns specification
     * @param columnNames array of column names
     * @param data 2D array of data
     * @param editableColumns array of boolean indicating which columns are editable
     * @return configured DefaultTableModel
     */
    public static DefaultTableModel createTableModel(String[] columnNames, Object[][] data, 
                                                   boolean[] editableColumns) {
        return new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (editableColumns != null && column < editableColumns.length) {
                    return editableColumns[column];
                }
                return false;
            }
        };
    }
    
    public void refreshTable() {
        SwingUtilities.invokeLater(() -> createTable());
    }
    
    // Method untuk mengatur data dari String (untuk GUI Builder)
    /**
     * Gets column names as comma-separated string
     * @return column names separated by comma
     */
    public String getColumnNamesString() {
        return String.join(",", columnNames);
    }
    
    /**
     * Sets column names from comma-separated string
     * @param columnNamesString column names separated by comma
     */
    public void setColumnNamesString(String columnNamesString) {
        if (columnNamesString != null && !columnNamesString.trim().isEmpty()) {
            setColumns(columnNamesString.split(","));
        }
    }
    
    private JButton createActionButton(String text, Color backgroundColor, Dimension size) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Set symmetric size
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);

        return button;
    }
    
    // Custom renderer for action buttons
    class ActionButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton editButton;
        private JButton deleteButton;
        
        public ActionButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            setOpaque(true);
            
            Dimension buttonSize = new Dimension(30, 30);
            editButton = createActionButton("✏", new Color(255, 193, 7), buttonSize);
            deleteButton = createActionButton("🗑", new Color(220, 53, 69), buttonSize);
            
            add(editButton);
            add(deleteButton);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                if (row % 2 == 0) {
                    setBackground(Color.WHITE);
                } else {
                    setBackground(alternateRowColor);
                }
            }
            
            return this;
        }
    }
    
    // Custom editor for action buttons
    class ActionButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton editButton;
        private JButton deleteButton;
        private int currentRow;
        
        public ActionButtonEditor() {
            super(new JCheckBox());
            
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            
            Dimension buttonSize = new Dimension(30, 30);
            editButton = createActionButton("✏", new Color(255, 193, 7), buttonSize);
            deleteButton = createActionButton("🗑", new Color(220, 53, 69), buttonSize);
            
            editButton.addActionListener(e -> {
                if (actionListener != null) {
                    actionListener.onEdit(currentRow, getRowData(currentRow));
                } else {
                    JOptionPane.showMessageDialog(panel, "Edit button clicked for row " + (currentRow + 1));
                }
                fireEditingStopped();
            });
            
            deleteButton.addActionListener(e -> {
                if (actionListener != null) {
                    actionListener.onDelete(currentRow, getRowData(currentRow));
                } else {
                    int result = JOptionPane.showConfirmDialog(panel, 
                        "Apakah Anda yakin ingin menghapus data ini?", 
                        "Konfirmasi Hapus", 
                        JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        removeRow(currentRow);
                        JOptionPane.showMessageDialog(panel, "Data berhasil dihapus!");
                    }
                }
                fireEditingStopped();
            });
            
            panel.add(editButton);
            panel.add(deleteButton);
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            currentRow = row;
            return panel;
        }
        
        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }
    
    // Custom Clean ScrollBar
    class CustomScrollBar extends JScrollBar {
        
        public CustomScrollBar(int orientation) {
            super(orientation);
            setUI(new CustomScrollBarUI());
        }
    }
    
    // Custom ScrollBar UI for clean appearance
    class CustomScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(200, 200, 200);
            this.trackColor = new Color(245, 245, 245);
        }
        
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }
        
        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }
        
        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
        }
        
        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(trackColor);
            if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
                g2.fillRoundRect(trackBounds.x + 2, trackBounds.y, 
                               trackBounds.width - 4, trackBounds.height, 8, 8);
            } else {
                g2.fillRoundRect(trackBounds.x, trackBounds.y + 2, 
                               trackBounds.width, trackBounds.height - 4, 8, 8);
            }
            g2.dispose();
        }
        
        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Hover effect
            if (isThumbRollover()) {
                g2.setColor(new Color(150, 150, 150));
            } else {
                g2.setColor(thumbColor);
            }
            
            if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
                g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, 
                               thumbBounds.width - 4, thumbBounds.height - 4, 6, 6);
            } else {
                g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, 
                               thumbBounds.width - 4, thumbBounds.height - 4, 6, 6);
            }
            g2.dispose();
        }
        
        @Override
        public Dimension getPreferredSize(JComponent c) {
            if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
                return new Dimension(12, super.getPreferredSize(c).height);
            } else {
                return new Dimension(super.getPreferredSize(c).width, 12);
            }
        }
    }
}

// Example usage for testing
class CustomTableExample extends JFrame {
    
    public CustomTableExample() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("CustomTable - NetBeans Compatible");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 400);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        
        // Create CustomTable instance
        CustomTable customTable = new CustomTable();
        
        // Method 1: Set properties directly (original way)
        customTable.setColumns(new String[]{"User ID", "Username", "Full Name", "Role", "Aksi"});
        customTable.setData(new Object[][]{
            {"USR001", "admin", "Administrator", "Admin", ""},
            {"USR002", "john_doe", "John Doe", "User", ""},
            {"USR003", "jane_smith", "Jane Smith", "Manager", ""}
        });
        
        // Method 2: Using DefaultTableModel (new way)
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Product ID", "Product Name", "Price", "Stock", "Aksi"});
        tableModel.addRow(new Object[]{"PRD001", "Laptop", "$999", "15", ""});
        tableModel.addRow(new Object[]{"PRD002", "Mouse", "$25", "50", ""});
        tableModel.addRow(new Object[]{"PRD003", "Keyboard", "$75", "30", ""});
        
        // Create second table with DefaultTableModel
        CustomTable customTable2 = new CustomTable();
        customTable2.setModel(tableModel);  // This is the new feature!
        customTable2.setShowActionButtons(true);
        customTable2.setTableRowHeight(45);
        
        // Method 3: Using static helper method
        DefaultTableModel helperModel = CustomTable.createTableModel(
            new String[]{"Order ID", "Customer", "Total", "Status", "Aksi"},
            new Object[][]{
                {"ORD001", "John Doe", "$150", "Pending", ""},
                {"ORD002", "Jane Smith", "$280", "Completed", ""}
            }
        );
        
        CustomTable customTable3 = new CustomTable();
        customTable3.setModel(helperModel);
        customTable3.setShowActionButtons(true);
        
        // Create tabbed pane to show different examples
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Tab 1: Direct properties
        customTable.setActionButtonListener(new CustomTable.ActionButtonListener() {
            @Override
            public void onEdit(int row, Object[] rowData) {
                JOptionPane.showMessageDialog(null, "Edit User: " + rowData[2]);
            }
            
            @Override
            public void onDelete(int row, Object[] rowData) {
                int result = JOptionPane.showConfirmDialog(null, 
                    "Delete User " + rowData[2] + "?", 
                    "Confirm", 
                    JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    customTable.removeRow(row);
                }
            }
        });
        
        // Tab 2: DefaultTableModel
        customTable2.setActionButtonListener(new CustomTable.ActionButtonListener() {
            @Override
            public void onEdit(int row, Object[] rowData) {
                JOptionPane.showMessageDialog(null, "Edit Product: " + rowData[1]);
            }
            
            @Override
            public void onDelete(int row, Object[] rowData) {
                customTable2.getModel().removeRow(row); // Using model directly
            }
        });
        
        // Tab 3: Helper method
        customTable3.setActionButtonListener(new CustomTable.ActionButtonListener() {
            @Override
            public void onEdit(int row, Object[] rowData) {
                JOptionPane.showMessageDialog(null, "Edit Order: " + rowData[0]);
            }
            
            @Override
            public void onDelete(int row, Object[] rowData) {
                customTable3.getModel().removeRow(row);
            }
        });
        
        // Add tabs
        tabbedPane.addTab("Direct Properties", customTable);
        tabbedPane.addTab("DefaultTableModel", customTable2);
        tabbedPane.addTab("Helper Method", customTable3);
        
        // Add button panel for testing model operations
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        
        JButton addRowBtn = new JButton("Add Row to Tab 2");
        addRowBtn.addActionListener(e -> {
            DefaultTableModel model = customTable2.getModel();
            model.addRow(new Object[]{"PRD00" + (model.getRowCount() + 1), 
                                    "New Product", "$0", "0", ""});
        });
        
        JButton changeModelBtn = new JButton("Change Model Tab 2");
        changeModelBtn.addActionListener(e -> {
            DefaultTableModel newModel = new DefaultTableModel();
            newModel.setColumnIdentifiers(new String[]{"ID", "Name", "Category", "Aksi"});
            newModel.addRow(new Object[]{"1", "Test Product", "Electronics", ""});
            customTable2.setModel(newModel);
        });
        
        buttonPanel.add(addRowBtn);
        buttonPanel.add(changeModelBtn);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
    
    public static void main(String[] args) {
        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new CustomTableExample().setVisible(true);
        });
    }
}