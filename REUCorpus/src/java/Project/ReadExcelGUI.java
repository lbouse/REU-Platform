/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.awt.Cursor;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableModel;
import jxl.*;

/**
 *
 * @author josealvarado
 */
public class ReadExcelGUI extends javax.swing.JFrame {

    /**
     * Creates new form ReadExcelGUI
     */
    public ReadExcelGUI() {
        initComponents();
       
       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jMenu1.setText("File");

        jMenuItem1.setText("Open");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        File file = chooseExcelFile();
        // do nothing if open dialog was cancelled
        if (file == null) {
            return;
        }

        // loading long excel tables may be time consuming, so use wait cursor
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        Sheet sheet = readExcelSheet(file);
        if (sheet != null) {
            displaySheet(sheet, jTable1);
        }

        setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        dispose();
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReadExcelGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReadExcelGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReadExcelGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReadExcelGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ReadExcelGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

      private Sheet readExcelSheet (File file) {
        // open excel file (workbook) for reading
        Workbook wbk;
        try {
            wbk = Workbook.getWorkbook(file);
        } catch (Exception ex) {
            JOptionPane message = new JOptionPane(
                    "Can't read excel file " + file.getPath(),
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (wbk.getNumberOfSheets() <= 0) {
            JOptionPane message = new JOptionPane(
                    "Excel file doesn't have any sheets.",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        // return first sheet
        return wbk.getSheet(0);
    }
      
      /** Takes given sheet data and displays them in very simple way in
     * given table.
     *
     * @param sheet Excel sheet
     * @param table JTable to display sheet data in
     */
    private void displaySheet(Sheet sheet, JTable table) {
        table.setModel(new SheetTableModel(sheet));
        
    }

    /** Read-only TableModel implementation, which adapts Sheet object from
     * JExcelApi library for use as JTable model.
     */
    private static class SheetTableModel implements TableModel {

        private Sheet sheet;

        public SheetTableModel (Sheet sheet) {
            this.sheet = sheet;
        }

        public int getRowCount() {
            return sheet.getRows();
        }

        public int getColumnCount() {
            return sheet.getColumns();
        }

        /** Copied from javax.swing.table.AbstractTableModel,
         * to name columns using spreadsheet conventions:
         *  A, B, C, . Z, AA, AB, etc.
         */
        public String getColumnName(int column) {
            String result = "";
            for (; column >= 0; column = column / 26 - 1) {
                result = (char)((char)(column%26)+'A') + result;
            }
            return result;
        }

        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            // table is read only for demo purposes
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Cell cell = sheet.getCell(columnIndex, rowIndex);
            return cell.getContents();
        }

        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            // no operation, table is read only for demo purposes
        }

        public void addTableModelListener(TableModelListener l) {
            // no operation, table is read only for demo purposes
        }

        public void removeTableModelListener(TableModelListener l) {
            // no operation, table is read only for demo purposes
        }

    }  // end of SheetTableModel
    
      /** Opens dialog for user to choose an excel file to open and read.
     *
     * @return Excel file or null if user cancelled the dialog
     */
    private File chooseExcelFile () {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new XLSFilter());

        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        // cancel was clicked
        return null;
    }

    /** Filter which accepts only xls files */
    private static class XLSFilter extends FileFilter {
        public boolean accept(File f) {
            return f.isDirectory() || f.getName().endsWith(".xls");
        }

        public String getDescription() {
            return "Excel spreadsheet XLS files";
        }
    }
}
