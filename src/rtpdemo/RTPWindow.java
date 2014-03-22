/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rtpdemo;

import java.awt.geom.Point2D;
import java.util.TimerTask;
import javax.swing.JFrame;
import realtimeplotter.*;

/**
 *
 * @author John Mamish
 */
public class RTPWindow extends JFrame
{
    private RealTimePlotter rtp;
    
    
    
    class PointAdder extends TimerTask
    {
        @Override
        public void run()
        {
            rtp.addPoint(new Point2D.Double(Math.random(), this.scheduledExecutionTime()), "rand");
        }
    }
}
