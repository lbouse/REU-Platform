package reu_platform;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bouse
 */
public class linkNode {
    /* PRIVATE VARIABLES */
    private int linkID;
    private boolean selected = false;
    private int endPointA; //fieldNode ID for point A
    private int endPointB;//fieldNode ID for point B
    private double slope;
    private double yIntercept;
    
    /* PUBLIC VARIABLES */ 
    //Each node needs to have a linked list of all the nodes linked to it.
    // Use a whiteboard to help plan out all the inter-linked nodes.
    // I've made this very confusing, haven't I? I'll clean it up later.
    public fieldNode nodeA;
    public fieldNode nodeB;
    
    
    /* GET & SET METHODS */
    public void setID(int in){ linkID = in; }
    public void setSelected(boolean in)
    {
        selected = in; 
        if(selected)
        { 
            nodeA.setBackground( new Color(238, 221, 130) ); 
            nodeA.setOpaque(true);
            nodeB.setBackground( new Color(238, 221, 130) ); 
            nodeB.setOpaque(true);
        }
        else
        {
            nodeA.setBackground( Color.WHITE );
            nodeA.setOpaque(true);
            nodeB.setBackground( Color.WHITE );
            nodeB.setOpaque(true);
        }
    }
    public void setEndPoints(int a, int b){ endPointA = a; endPointB = b; }
    public void setEndPointA(int in){ endPointA = in; }
    public void setEndPointB(int in){ endPointB = in; }
    public void setEndNodes( fieldNode a, fieldNode b )
    {
        nodeA = a; 
        nodeB = b; 
        setLineEq();
    }
    public void setEndNodeA(fieldNode a){ nodeA = a; setLineEq(); }
    public void setEndNodeB( fieldNode b){ nodeB = b; setLineEq(); }
    
    public int getID(){ return linkID; }
    public int getEndPointA(){ return endPointA; }//Get ID for point A
    public int getEndPointB(){ return endPointB; }//Get ID for point B
    public fieldNode getEndNodeA(){ return nodeA; }
    public fieldNode getEndNodeB(){ return nodeB; }
    
    /* CONSTRUCTORS */
    public linkNode() //Empty constructor
    {
        setID(0);
        setEndPoints(0,0);
    }
    
    public linkNode(int id, int pointA, int pointB)
    {
        linkID = id;
        setEndPoints(pointA, pointB);
        selected = true;
        
    }
    
    /* GENERAL METHODS */
    public boolean isSelected()
    { return selected; }
    
    public boolean hasEndPoints(int a, int b)
    {
        if( nodeA.getID() == a && nodeB.getID() == b ) return true;
        else return false;
    }
    
    public boolean containsPoint(int a)
    {
        if( nodeA.getID() == a || nodeA.getID() == a ) return true;
        else return false;
    }
    
    public void setLineEq()
    {   //y = mx+b 
        slope = ( (double)nodeA.getPoint().y - (double)nodeB.getPoint().y ) /
                ( (double)nodeA.getPoint().x - (double)nodeB.getPoint().x );
        java.text.DecimalFormat temp = new java.text.DecimalFormat("#.###");
        slope = Double.valueOf(temp.format(slope));
        
        yIntercept = (double)nodeA.getPoint().y - (slope * (double)nodeA.getPoint().x);
        yIntercept = Double.valueOf(temp.format(yIntercept));
    }
    
    public void selectLine()
    { selected = true; }
    
    //Determines if the coordinates given is along the line.
    public boolean isOnLine(Point coord)
    { 
        return (coord.y <= (slope * coord.x) + yIntercept + 5) && 
            (coord.y >= (slope * coord.x) + yIntercept - 5); 
    }
    
    public String getEquation()
    {
        return "y = " + slope + "x" + " + " + yIntercept;
    }
}
