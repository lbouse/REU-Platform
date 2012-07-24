/*
 * DO NOT USE THIS FILE!!
 * This is an old version, kept for reference purpsoes ONLY!
 * Delete this when program is complete.
 */
package Project;

    //Importing LinkedLists for keeping track of the sources user adds.
    import java.util.LinkedList;
    import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Bouse
 */
public class createProject extends javax.swing.JFrame {
    //Declaring some public variables.
    private static String optionA;
    private static String optionB;
    private LinkedList<javax.swing.JLabel> userSources = new LinkedList<javax.swing.JLabel>();
    private LinkedList<javax.swing.JButton> sourceButtons = new LinkedList<javax.swing.JButton>();
    private LinkedList<sourceNode> sourceList = new LinkedList<sourceNode>();
    private javax.swing.JList tempList = new javax.swing.JList();
    public static javax.swing.DefaultListModel listModelA;
    public static javax.swing.DefaultListModel listModelB;
    private int cnt = 0;

    /**
     * Creates new form createProject
     */
    public createProject() {
     initComponents();
     
     sourceALabel.setText(optionA);
     sourceBLabel.setText(optionB);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        projectNameTxtField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tableNameTxtField = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBList = new javax.swing.JList();
        jLabel5 = new javax.swing.JLabel();
        sourceALabel = new javax.swing.JLabel();
        sourceBLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableAList = new javax.swing.JList();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Project Name:");

        jLabel2.setText("Database Table Name:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Tables");

        jButton1.setText("Help");

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Finish");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tableBList.setModel(listModelB);
        jScrollPane1.setViewportView(tableBList);

        jLabel5.setText("alt+click to select more than one");

        sourceALabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sourceALabel.setForeground(new java.awt.Color(51, 51, 51));
        sourceALabel.setText("Tables");

        sourceBLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sourceBLabel.setForeground(new java.awt.Color(51, 51, 51));
        sourceBLabel.setText("Tables");

        tableAList.setModel(listModelA);
        jScrollPane3.setViewportView(tableAList);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Create New Project");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sourceBLabel)
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                            .addComponent(jButton3)
                            .addGap(10, 10, 10)
                            .addComponent(jButton2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton1)
                            .addGap(6, 6, 6))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap()))))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tableNameTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(projectNameTxtField))))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sourceALabel)
                            .addComponent(jLabel4)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(141, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(projectNameTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tableNameTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sourceBLabel)
                    .addComponent(sourceALabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        //Close the current frame.
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        //Information to pass: userSources, number of sources, name, table
        //Mapping frame = new Mapping( projectNameTxtField.getText(), tableNameTxtField.getText(),
        //        userSources, userSources.size());
        
        //SchemaMapping frame = new SchemaMapping( userSources );
        
        // Get the index of all the selected items
        int[] selectedIx = tableAList.getSelectedIndices();
        List<String> selectedTables = new ArrayList();
        
        int[] selectedIxB = tableBList.getSelectedIndices();
        List<String> selectedTablesB = new ArrayList();

        // Get all the selected items using the indices
        for (int i=0; i<selectedIx.length; i++) {
            Object sel = tableAList.getModel().getElementAt(selectedIx[i]);
            selectedTables.add( sel.toString() );
        }
        
        for (int i=0; i<selectedIxB.length; i++) {
            Object sel = tableBList.getModel().getElementAt(selectedIxB[i]);
            selectedTablesB.add( sel.toString() );
        }
        
//        SchemaMapping frame = new SchemaMapping();
//        try {
//            frame = new SchemaMapping( optionA, selectedTables, optionB, selectedTablesB);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(createProject.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(createProject.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
//        frame.setVisible(true);
        
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        
        optionA = JOptionPane.showInputDialog("Source A: ");
        optionB = JOptionPane.showInputDialog("Source B: ");
                
        SQLController s = new SQLController();
        s.LoadSQLPlugin();
        s.ConnectToSever(optionA);
        s.allThree("SELECT * FROM INFORMATION_SCHEMA.TABLES");
        
        listModelA = new javax.swing.DefaultListModel();
        ArrayList tblNamesA = s.getTableNamesFromServer();
        for(int i = 0; i < tblNamesA.size(); i++ )
        { listModelA.addElement( tblNamesA.get(i) ); }
        s.DisconnectFromServer(optionA);
        
        SQLController j = new SQLController();
        j.LoadSQLPlugin();
        j.ConnectToSever(optionB);
        j.allThree("SELECT * FROM INFORMATION_SCHEMA.TABLES");
        
        listModelB = new javax.swing.DefaultListModel();
        ArrayList tblNamesB = j.getTableNamesFromServer();
        for(int i = 0; i < tblNamesB.size(); i++ )
        { listModelB.addElement( tblNamesB.get(i) ); }
        j.DisconnectFromServer(optionB);
        
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new createProject().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField projectNameTxtField;
    private javax.swing.JLabel sourceALabel;
    private javax.swing.JLabel sourceBLabel;
    private javax.swing.JList tableAList;
    private javax.swing.JList tableBList;
    private javax.swing.JTextField tableNameTxtField;
    // End of variables declaration//GEN-END:variables
}