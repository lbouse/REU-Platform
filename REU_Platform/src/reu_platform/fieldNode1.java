/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reu_platform;

import java.awt.Point;
import java.util.LinkedList;

/**
 *
 * @author Bouse
 */
public class fieldNode {
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
    public LinkedList<fieldNode> fieldLinks = new LinkedList<fieldNode>();
    
    /*Public get&set Methods*/
    public void setTitle(String x){ fieldTitle = new String(x); }
    public void setType(String x){ fieldType = new String(x); }
    public void setSource(int x){ fieldSource = x; }
    public void setParent(int x){ fieldParent = x; }
    public void setPoint(Point xy){ lineStartPoint = new Point(xy); }
    public void setLabel(){ fieldLabel = new javax.swing.JLabel(fieldTitle); }
    
    public Point getLinePoint(){ return lineStartPoint; }
    
    //-------------------------------------------------------------------
    //                          CONSTRUCTORS
    //-------------------------------------------------------------------       
    public fieldNode(String title, int parentID)
    {
        setTitle(title);
        setSource(parentID);
        setLabel();
        
        //Make method to get point for drawing line
        //setPoint(coord);
    }

}
