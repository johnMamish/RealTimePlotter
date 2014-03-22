/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package realtimeplotter;

import java.awt.Color;
import java.awt.Graphics;
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
    int xmin;
    int xmax;
    int xscale;
    int ymin;
    int ymax;
    int yscale;
    
    //basic constructor
    public RealTimePlotter()
    {
        //not much information to go off of for constructors, so set up default
        //values.
        this.thePlots = new ArrayList<>();
        
        this.xmin = 0;
        this.xmax = 10;
        this.xscale = 1;
        
        this.ymin = 0;
        this.ymax = 10;
        this.yscale = 1;
    }
    
    //creates a new, empty plot in the rtp
    public void addPlot(String plotName)
    {
        //add plot to list
        this.thePlots.add(new DataSet(plotName, new Color(0x00ff0000)));
        
        //probably need to redraw now.
        this.repaint();
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
        
        //draw stuff.
        
    }
}