/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.awt.Point;
import java.util.LinkedList;

/**
 *
 * @author Bouse
 */
public class fieldNode1 {
    //-------------------------------------------------------------------
    //                          DECLARATIONS
    //-------------------------------------------------------------------    
    /*Private Variables*/
    private String fieldTitle;
    private int fieldSource; //the sourceID of its source
    private int fieldParent; //the ID of the parent table for the source
    private Point lineStartPoint;
    private String fieldType;
    
    /*Public Variables*/
    public javax.swing.JLabel fieldLabel;
    public LinkedList<fieldNode1> fieldLinks = new LinkedList<fieldNode1>();
    public boolean selected = false;
    
    /*Public get&set Methods*/
    public void setTitle(String x){ fieldTitle = new String(x); }
    public void setType(String x){ fieldType = new String(x); }
    public void setSource(int x){ fieldSource = x; }
    public void setParent(int x){ fieldParent = x; }
    public void setPoint(){ lineStartPoint = new Point( fieldLabel.getX(), fieldLabel.getY() ); }
    public void setLabel(){ fieldLabel = new javax.swing.JLabel(fieldTitle); }
    
    public Point getPoint(){ return lineStartPoint; }
    public String getTitle(){ return fieldTitle; }
    
    //-------------------------------------------------------------------
    //                          CONSTRUCTORS
    //-------------------------------------------------------------------       
    public fieldNode1(String title, int parentID)
    {
        setTitle(title);
        setSource(parentID);
        setLabel();
        
        
        //Make method to get point for drawing line
        //setPoint(coord);
    }
    
    public fieldNode1()
    {
        setTitle("Empty");
        setSource(0);
        //setLabel();
    }

}
