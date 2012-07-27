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
    
    //-------------------------------------------------------------------
    //                          INNER CLASSES
    //-------------------------------------------------------------------
    public static class mySQLStruct {
        //Main Structure
        private String column;
        private String type;
        private String defaultValue;
        private String collation;
        private String attributes;
        private boolean nullValue;
        private boolean AUTO_INCREMENT;
        private String comments;
        private String MIMEtype;
        private String browserTransformation;
        private String transformationOptions;
        
        //Available values for some of the above variables.
        private String typeOptions[] = { "CHAR", "VARCHAR", "TINYTEXT", "TEXT", "BLOB", 
            "MEDIUMTEXT", "MEDIUMBLOB", "LONGTEXT", "LONGBLOB", "TINYINT", "SMALLINT", 
            "MEDIUMINT", "INT", "BIGINT", "FLOAT", "DOUBLE", "DECIMAL", "DATE", "DATETIME", 
            "TIMESTAMP", "TIME", "EMUN", "SET" };
        private int typeLengthValues;
        private String defaultValueOptions[] = { "None", "As defined:", "NULL", "CURRENT_TIMESTAMP" };
        private String defaultAsDefined;
        private String collationOptions[] = { 
            "armscii8_general_ci", "ascii_general_ci", "big5_chinese_ci", "cp1250_croatian_ci", 
            "cp1250_czech_cs", "cp1250_general_ci", "cp1250_polish_ci", "cp1251_bulgarian_ci", 
            "cp1251_general_ci", "cp1251_general_cs", "cp1251_ukrainian_ci", "cp1256_general_ci", 
            "cp1257_general_ci", "cp1257_lithuanian_ci", "cp850_general_ci", "cp852_general_ci",
            "cp866_general_ci", "cp932_japanese_ci", "dec8_swedish_ci", "eucjpms_japanese_ci",
            "euckr_korean_ci", "gb2312_chinese_ci", "gbk_chinese_ci", "geostd8_general_ci",
            "greek_general_ci", "hebrew_general_ci", "hp8_english_ci", "keybcs2_general_ci",
            "koi8r_general_ci", "koi8u_general_ci", "latin1_danish_ci", "latin1_general_ci",
            "latin1_general_cs", "latin1_german1_ci", "latin1_german2_ci", "latin1_spanish_ci",
            "latin1_swedish_ci", "latin2_croatian_ci", "latin2_czech_cs", "latin2_general_ci",
            "latin2_hungarian_ci", "latin5_turkish_ci", "latin7_estonian_cs", "latin7_general_ci",
            "latin7_general_cs", "macce_general_ci", "macroman_general_ci", "sjis_japanese_ci",
            "swe7_swedish_ci", "tis620_thai_ci", "ujis_japanese_ci", "utf8_czech_ci", "utf8_danish_ci",
            "utf8_esperanto_ci", "utf8_estonian_ci", "utf8_general_ci.african", "utf8_general_ci.american", 
            "utf8_general_ci.central_asian", "utf8_general_ci.european", "utf8_general_ci.indic", 
            "utf8_general_ci.ipa", "utf8_general_ci.korean", "utf8_general_ci.middle_eastern", 
            "utf8_general_ci.philippine", "utf8_general_ci.phonetic", "utf8_general_ci.south_east_asian", 
            "utf8_hungarian_ci", "utf8_icelandic_ci", "utf8_latvian_ci", "utf8_lithuanian_ci", 
            "utf8_persian_ci", "utf8_polish_ci", "utf8_roman_ci", "utf8_romanian_ci", 
            "utf8_sinhala_ci", "utf8_slovak_ci", "utf8_slovenian_ci", "utf8_spanish2_ci", 
            "utf8_spanish_ci", "utf8_swedish_ci", "utf8_turkish_ci", "utf8_unicode_ci.african", 
            "utf8_unicode_ci.american", "utf8_unicode_ci.central_asian", "utf8_unicode_ci.european", 
            "utf8_unicode_ci.indic", "utf8_unicode_ci.ipa", "utf8_unicode_ci.korean", "utf8_unicode_ci.middle_eastern",
            "utf8_unicode_ci.philippine", "utf8_unicode_ci.phonetic", "utf8_unicode_ci.south_east_asian" };
        private String attributesOptions[] = {
               "BINARY", "UNSIGNED", "UNSIGNED ZEROFILL", "on update CURRENT_TIMESTAMP" };
        private String MIMEtypeOptions[] = { "application/ocetetstream", "image/jpeg", "image/png", "text/plain" };
        private String browserTransformationOptions[] = { 
            "application/ocetetstream: download", 
            "application/ocetetstream: hex", "image/jpeg: inline", "image/jpeg: link", 
            "image/png: inline", "text/plain: dateformat", "text/plain: external", 
            "text/plain: formatted", "text/plain: imagelink", "text/plain: link", 
            "text/plain: longTolpv4", "text/plain: sql", "text/plain: substr" };
    }
}
