/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.awt.Point;
import java.util.LinkedList;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author Bouse
 */
public class inputNode extends javax.swing.JLabel {
    //-------------------------------------------------------------------
    //                          DECLARATIONS
    //-------------------------------------------------------------------    
    /*Private Variables*/
    private String inputName;
    private String inputForm; //name of the form input originated from
    private String formSource; //URL of the source website Form is from
    private int inputID;
    private String inputType;
    private boolean selected = false;
    
    /*Public get&set Methods*/
    public void setID(int x){ inputID = x; }
    public void setName(String x){ inputName = new String(x); }
    public void setType(String x){ inputType = new String(x); }
    public void setSource(String x){ formSource = new String(x); }
    public void setForm(String x){ inputForm = new String(x); }
    public void setLabel(){ setText(inputName); }
    public void select(){ selected = true; }
    public void setSelectStatus(boolean x){ selected = x; }
    
    public int getID(){ return inputID; }
    public String getName(){ return inputName; }
    public String getType(){ return inputType; }
    public String getSource(){ return formSource; }
    public String getForm(){ return inputForm; }
    public boolean getSelectedStatus(){ return selected; }
    
    //-------------------------------------------------------------------
    //                          CONSTRUCTORS
    //-------------------------------------------------------------------       
    public inputNode(String name, String type, String source, String form, int thisID)
    {
        Border border = LineBorder.createGrayLineBorder();
        setID(thisID);
        setName(name);
        setForm(form);
        setSource(source);
        setType(type);
        setLabel();
        setAlignmentY(CENTER_ALIGNMENT);
        setBorder(border);
        
        //Make method to get point for drawing line
        //setPoint(coord);
    }
    
    public inputNode()
    {
        setName("Empty");
        setID(0);
        setSource("N/A");
        setForm("N/A");
        setLabel();
    }
}
