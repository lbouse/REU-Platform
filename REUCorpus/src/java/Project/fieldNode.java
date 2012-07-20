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
public class fieldNode extends javax.swing.JLabel {
    //-------------------------------------------------------------------
    //                          DECLARATIONS
    //-------------------------------------------------------------------    
    /*Private Variables*/
    private String fieldTitle;
    private int fieldSource; //the sourceID of its source
    private int fieldParent; //the ID of the parent table for the source
    private int fieldID;
    private Point lineStartPoint;
    private String fieldType;
    private boolean selected = false;
    
    /*Public Variables*/
//    public javax.swing.JLabel fieldLabel;
    public LinkedList<fieldNode> fieldLinks = new LinkedList<fieldNode>();
//    public boolean selected = false;
    
    /*Public get&set Methods*/
    public void setID(int x){ fieldID = x; }
    public void setTitle(String x){ fieldTitle = new String(x); }
    public void setType(String x){ fieldType = new String(x); }
    public void setSource(int x){ fieldSource = x; }
    public void setParent(int x){ fieldParent = x; }
    public void setPoint(){ lineStartPoint = new Point( this.getX(), this.getY() ); }
    public void setPoint(Point xy){ lineStartPoint = xy; }
    public void setSelectedStatus(boolean value){ selected = value; }
    public void setLabel(){ setText(fieldTitle); }
    
    public int getID(){ return fieldID; }
    public Point getPoint(){ return lineStartPoint; }
    public String getTitle(){ return fieldTitle; }
    public boolean getSelectedStatus(){ return selected; }
    public int getFieldParent(){ return fieldParent; }
    public int getFieldSource(){ return fieldSource; }
    public String getType(){ return fieldType; }
    
    //-------------------------------------------------------------------
    //                          CONSTRUCTORS
    //-------------------------------------------------------------------       
    public fieldNode(String title, int parentID, int thisID)
    {
        Border border = LineBorder.createGrayLineBorder();
        setID(thisID);
        setTitle(title);
        setSource(parentID);
        setLabel();
        setAlignmentY(CENTER_ALIGNMENT);
        setBorder(border);
        
        //Make method to get point for drawing line
        //setPoint(coord);
    }
    
    public fieldNode()
    {
        setTitle("Empty");
        setSource(0);
        setLabel();
    }

}
