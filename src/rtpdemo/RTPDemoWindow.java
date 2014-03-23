/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rtpdemo;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.geom.Point2D;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import realtimeplotter.*;

/**
 *
 * @author John Mamish
 */
public class RTPDemoWindow extends JFrame
{
    private RealTimePlotter rtp;
    private final int rate;
    
    public RTPDemoWindow(int rate)
    {
        //initialize private vars
        this.rate = rate;
        this.rtp = new RealTimePlotter();
        
        //set up realtime plotter
        this.rtp.addPlot("rand");
        this.rtp.setColor("rand", new Color(0x00ff0000));
        this.rtp.setAutoScroll();
        
        //layout code
        this.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        this.add(rtp, c);
        
        Timer dataTimer = new Timer();
        dataTimer.scheduleAtFixedRate(new PointAdder(), 200, 150);
    }
    
    class PointAdder extends TimerTask
    {
        private int time = 0;
        
        @Override
        public void run()
        {
            rtp.addPoint("rand", new Point2D.Double(time, Math.random()));
            rtp.repaint();
            time += 500;
        }
    }
}
