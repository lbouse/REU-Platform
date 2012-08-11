/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//createNewProjectWeb.java
// Called by Platform_newProject.java to format web sources.
// Incomplete as of now.

package Project;

import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.*;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.*;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 *
 * @author Bouse
 */
public class createNewProjectWeb {
    
    private int stepNumber = 0;
    private LinkedList<inputNode> inputList = new LinkedList<inputNode>();
    private LinkedList<Parser> parserList = new LinkedList<Parser>();
    private LinkedList<String> urlList = new LinkedList<String>();
    public JPanel dbPanel = new JPanel();
    
    //First let's get the search parameters available.
    // Print out everything in "form" node
    public createNewProjectWeb() throws ParserException 
    {
        String input = getURL();
        
        while( !input.equals("exit") )
        {
            urlList.add(input);
            //Get the input tags from the form in the website
            getFormInputs(input);
            //Prompt user for sample query of first input
            sampleQuery(input);
            
            
            //Move on to the next web source
            input = getURL();
        }
        
        
    }
    
    public String sampleQuery(String url)
    {
        int i = 0;
        while( inputList.size() < i && !inputList.get(i).getSource().equals(url) )
        { i++; }
        
        return JOptionPane.showInputDialog(null, "Enter a sample search: \n" +
                inputList.get(i).getName() );
    }
    
    public String getURL()
    {
        String input = JOptionPane.showInputDialog(null, "Enter Website URL: \n"
                + "(enter \"exit\" when done)\n");
        
        if( !input.contains("http://") && !input.contains("exit") )
        { input = "http://" + input; }
        
        return input;
    }
    
    public void getFormInputs(String URL) throws ParserException
    {
        Parser temp = new Parser(URL);
        parserList.add(temp);
        NodeFilter filter = new HasAttributeFilter("form");
        NodeList inputs = temp.parse(filter);
        inputs = inputs.extractAllNodesThatMatch(filter);
        FormTag formTag = (FormTag) inputs.elementAt(0); //Assume first form is form needed
        // Later, allow user to view all forms from a webpage and choose which one to use.
        inputs = formTag.getFormInputs();
        
        InputTag tempInput;
        for(int i = 0; i < inputs.size(); i++)
        {
            tempInput = (InputTag) inputs.elementAt(i);
            inputList.add(new inputNode(tempInput.getAttribute("name"), 
                    tempInput.getAttribute("type"), URL, formTag.getAttribute("name"), inputList.size() ));
        }
        
    }

//    public static void main(String args[]) throws ParserException
//    {
//        Parser parser = new Parser("http://www.directhomes.com");
//        NodeFilter filter = new HasAttributeFilter("form");
//        NodeList list = parser.parse(filter);
//        list = list.extractAllNodesThatMatch(filter);
////        System.out.println( list.elementAt(0).getText() );
////        System.out.println( list.elementAt(1).getText() );
////        System.out.println(list.toHtml());
//        System.out.println( "------------------------------------------------------------------------");
//        FormTag test = (FormTag) list.elementAt(0);
//        list = test.getFormInputs();
//        NodeList txtAreas = test.getFormTextareas();
////        filter = new TagNameFilter("input");
////        list.keepAllNodesThatMatch(filter, true);
//        InputTag inputs[] = new InputTag[list.size()];
//        System.out.println( "Location: " + test.extractFormLocn() + "\n"
//                + "Inputs:" );
//        for(int i = 0 ; i < list.size(); i++)
//        { 
//            System.out.println( '\t' + list.elementAt(i).toHtml() );
//            inputs[i] = (InputTag) list.elementAt(i);
//            System.out.println("\tName: " + inputs[i].getAttribute("name") + '\n' );
//        }
//        
//        System.out.println( "TextAreas: " );
//        for(int i = 0 ; i < txtAreas.size(); i++)
//        { System.out.println( '\t' + txtAreas.elementAt(i).toHtml() ); }
//        
//        System.out.println( "------------------------------------------------------------------------");
//        
//        Parser bparser = new Parser("http://www.zillow.com/homes/97318_rb/#/homes/for_sale/Salem-OR-97317/399688_rid/1-_beds/1-_baths/500-200000_price/");
//        NodeFilter bfilter = new HasAttributeFilter("body");
//        
//        NodeFilter filterAry[] = { new TagNameFilter("div"),
//            new TagNameFilter("span") };
//        NodeFilter andFilter = new OrFilter(filterAry);
//        NodeList clist = bparser.parse(andFilter);
//        
////        NodeList blist = bparser.parse(bfilter);
////        blist = blist.extractAllNodesThatMatch(bfilter);
////        
////        BodyTag binput = (BodyTag) blist.elementAt(0);
////        blist = binput.getChildren();
//        NodeList temp;
//        resultNode resultTemp = new resultNode();
//        
//        temp = clist.elementAt(2).getChildren();
//        
//        for(int i = 0; i < clist.size(); i++)
//        {
//            temp = clist.elementAt(i).getChildren();
//            if( temp.elementAt(3) != null){ resultTemp.treeNodes = temp.elementAt(3).getChildren(); }
//
////            if( temp.elementAt(2) != null){System.out.println(temp.elementAt(2).toHtml());}
////            if( resultTemp.treeNodes != null ){
////                for(int j = 0; j < resultTemp.treeNodes.size(); j++)
////                { System.out.println(resultTemp.treeNodes.elementAt(j).toPlainTextString()); }
////                System.out.println( resultTemp.treeNodes.toHtml() ); 
////            }
//            
////            if( temp != null ){ 
////                for(int j = 0; j < temp.size(); j++)
////                { 
////                    System.out.println( "> " + temp.elementAt(j).toPlainTextString() ); 
////                    if(j == 2){ resultTemp.treeNodes = temp.elementAt(j).getChildren(); }
////                }
////            }
////            System.out.println( clist.elementAt(i).toPlainTextString() + "\n*****\n" );
////            System.out.println( clist.elementAt(i).toHtml() + "\n*****\n" );
////            System.out.println( "\n*******\n" );
//        }//end for
//        
//        System.out.println( "------------------------------------------------------------------------");
//        
////        System.out.println( "resultTemp nodes:" + resultTemp.treeNodes.toHtml() );
//    }
//    
}//end public class createNewProjectWeb
