/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeplotter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JComponent;

/**
 * @brief   RealTimePlotter is a JComponent which displays plots of data as they
 *          are constructed in real time.
 * 
 * @author  John Mamish
 */
public class RealTimePlotter extends JComponent
{
    //variable for holding list of plots that we have.
    private final ArrayList<DataSet> thePlots;
    
    //variables for holding range information.
    double xmin;
    double xwidth;
    //double xscale = 0.1;      //units of scale is pixels/unit
    double ymin;
    double ywidth;
    //double yscale = 500;
    
    //flags
    //do we want to scroll the x range forward as we add new points beyond the
    //end range?
    boolean lockx = true;
    
    //basic constructor
    public RealTimePlotter()
    {
        //not much information to go off of for constructors, so set up default
        //values.
        this.thePlots = new ArrayList<>();
        
        this.xmin = 0;
        this.xwidth = 20000;
        //this.xscale = 0.1;
        
        this.ymin = 0;
        this.ywidth = 1;
        //this.yscale = 500;
    }
    
    //creates a new, empty plot in the rtp
    public void addPlot(String plotName)
    {
        //add plot to list
        this.thePlots.add(new DataSet(plotName, new Color(0x00000000)));
    }
    
    //various accessor routines for the data sets
    public void addPlot(DataSet toAdd)
    {
        this.thePlots.add(toAdd);
    }
    
    //adds a point to the data set named "name".
    public int addPoint(String name, Point2D toAdd)
    {
        //search over all plots to find the one we want to add to.
        DataSet d;
        for(Iterator<DataSet> it = this.thePlots.iterator(); it.hasNext();)
        {
            d = it.next();
            if(d.getName().equals(name))
            {
                //add data to the plot
                d.addData(toAdd);
                
                //adjust graph if automatic scrolling is desired.
                if(this.lockx)
                {
                    this.xmin = toAdd.getX() - this.xwidth;
                    if(this.xmin < 0)
                    {
                        this.xmin = 0;
                    }
                }
                
                return 0;
            }
        }
        
        return 1;
    }
    
    /**
     * @brief Causes the graph to automatically scroll 
     */
    public void setAutoScroll()
    {
        //set flag
        this.lockx = true;
        
        //update to edge
        //search over all plots to find the one we want to add to.
        DataSet d;
        Double max = Double.MIN_VALUE;
        for(Iterator<DataSet> it = this.thePlots.iterator(); it.hasNext();)
        {
            d = it.next();
            
            if(d.length() == 0)
            {
                continue;
            }
            
            if(d.get(d.length() - 1).getX() > max)
            {
                max = d.get(d.length() - 1).getX();
            }
        }
        
        this.xmin = max - this.xwidth;
        if(this.xmin < 0)
        {
            this.xmin = 0;
        }
    }
    
    public int setColor(String name, Color c)
    {
        //search over all plots to find the one we want to add to.
        DataSet d;
        for(Iterator<DataSet> it = this.thePlots.iterator(); it.hasNext();)
        {
            d = it.next();
            if(d.getName().equals(name))
            {
                d.setColor(c);
                return 0;
            }
        }
        
        return 1;
    }
    
    /**
     * @brief converts a given point to the absolute pixel position on the
     *        screen.
     * 
     * @param p Point to convert.
     * 
     * @return returns the pixel corresponding to Point2D p.
     */
    public Point2D.Double pointToPixel(Point2D.Double p)
    {
        //instantiate new point for result.
        Point2D.Double res = new Point2D.Double();
        
        //calcualate conversion
        double xval = (p.getX() - this.xmin)*this.getWidth()/this.xwidth + 30;
        double yval = (p.getY() - this.ymin)*this.getHeight()/this.ywidth + 30;
        res.setLocation(xval, this.getHeight() - yval);
        
        //return
        return res;
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        
        //cast g
        Graphics2D g2 = (Graphics2D)g;
        System.out.println("hello");
        
        //draw stuff.
        g2.setColor(new Color(0x00000000));
        
        //Plot data points.
        DataSet d;
        int textx = 10;
        int texty = 50;
        for(Iterator<DataSet> it = this.thePlots.iterator(); it.hasNext();)
        {
            d = it.next();
            g2.setColor(d.getColor());
            
            if(d.length() > 0)
            {
                Point2D p = this.pointToPixel(d.get(0));
                Point2D pnext;
                for(int i = 1; i < d.length(); i++)
                {
                    //convert point
                    pnext = this.pointToPixel(d.get(i));
                    
                    //draw
                    g2.drawLine((int)p.getX(), (int)p.getY(),
                                (int)pnext.getX(), (int)pnext.getY());
                    
                    //debug text
                    //g2.drawString(p.toString(), textx, texty);
                    //texty += 20;
                    
                    //exchange points to avoid extra recalcs
                    p = pnext;
                }
            }
        }
    }
}
