/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.awt.GridLayout;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Bouse
 */
public class sourceItem {
    private String sourceName;//Gathered from source
    private String sourceURL;//Entered by user
    private LinkedList<JCheckBox> localSchemaCheckBoxes = new LinkedList<JCheckBox>();
    public javax.swing.JScrollPane checkBoxScrollPane = new javax.swing.JScrollPane();
    public JLabel localSchemaLabel;
    public JPanel checkBoxPanel = new JPanel(new GridLayout(0,1));
    public JButton addCheckButton;
    public javax.swing.JSeparator theSeperator = new javax.swing.JSeparator();
    
    public void setSourceName(String x){ sourceName = new String(x); }
    public String getSourceName(){ return sourceName; }
    public void setSourceURL(String x){ sourceURL = new String(x); }
    public String getSourceURL(){ return sourceURL; }
    
    //Check Box tools
    public void addCheckBox(String x)
    {
        localSchemaCheckBoxes.add(new JCheckBox(x));
        checkBoxPanel.add( localSchemaCheckBoxes.getLast() );
    }
    
    public sourceItem(String name, String url, LinkedList<javax.swing.JLabel> srcList)
    {
        setSourceName(name);
        setSourceURL(url);
        checkBoxPanel.setBackground(new java.awt.Color(255, 255, 255));
        checkBoxScrollPane.setViewportView(checkBoxPanel);
        
        for(int i = 0; i <  10; i++)
        { addCheckBox( "CHECK BOX #" + (i+1) ); }
        
        addCheckButton = new JButton("Add Selected");
        addCheckButton.setBackground(new java.awt.Color(255, 255, 255));
        addCheckButton.setFont(new java.awt.Font("Calibri", 0, 12));
        addCheckButton.setForeground(new java.awt.Color(51, 51, 51));
        addCheckButton.setText("Add Selected");
        
        localSchemaLabel = new JLabel("Local Schema");
        localSchemaLabel.setFont(new java.awt.Font("Century Gothic", 0, 18));
        localSchemaLabel.setForeground(new java.awt.Color(102, 102, 102));
        localSchemaLabel.setText("Local schema");
    }

    private void addCheckButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
        
    } 
}
