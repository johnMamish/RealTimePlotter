/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeplotter;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @brief   Pretty lame.  Just a data structure to hold some data points and a
 *          few corresponding names.
 * 
 * @author  John Mamish
 */
public class DataSet
{
    private ArrayList<Point2D> data;
    private final String name;
    private Color c;
    
    public DataSet(String name, Color c)
    {
        this.name = name;
        this.data = new ArrayList<>();
        this.c = c;
    }
    
    public DataSet(String name, Color c, ArrayList<Point2D> data)
    {
        this.name = name;
        this.data = new ArrayList<>(data);
        this.c = c;
    }
    
    //accessor methods
    public String getName()
    {
        return name;
    }
    
    public void addData(Point2D p)
    {
        this.data.add(p);
    }
    
    //gets the ith data point
    public Point2D.Double get(int i)
    {
        //is this considered good oop style?!  This makes my eyes hurt.
        return new Point2D.Double(this.data.get(i).getX(), this.data.get(i).getY());
    }
    
    public int length()
    {
        return this.data.size();
    }
    
    public void setColor(Color c)
    {
        this.c = new Color(c.getRGB());
    }
    
    public Color getColor()
    {
        return new Color(this.c.getRGB());
    }
}
