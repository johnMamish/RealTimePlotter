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
    double xmax;
    double xscale = 0.1;      //units of scale is pixels/unit
    double ymin;
    double ymax;
    double yscale = 500;
    
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
        this.xmax = 10;
        this.xscale = 0.1;
        
        this.ymin = 0;
        this.ymax = 10;
        this.yscale = 500;
    }
    
    //creates a new, empty plot in the rtp
    public void addPlot(String plotName)
    {
        //add plot to list
        this.thePlots.add(new DataSet(plotName, new Color(0x00ff0000)));
    }
    
    //various accessor routines for the data sets
    public void addPlot(DataSet toAdd)
    {
        this.thePlots.add(toAdd);
    }
    
    //adds a point to the data set named "name".
    public int addPoint(Point2D toAdd, String name)
    {
        //search over all plots to find the one we want to add to.
        DataSet d;
        for(Iterator<DataSet> it = this.thePlots.iterator(); it.hasNext();)
        {
            d = it.next();
            if(d.getName().equals(name))
            {
                d.addData(toAdd);
                return 0;
            }
        }
        
        return 1;
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
        double xval = p.getX()*this.xscale + 30;
        double yval = p.getY()*this.yscale + 30;
        res.setLocation(xval, this.getHeight() - yval);
        
        //return
        return res;
    }
}
