/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rtpdemo;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import realtimeplotter.*;

/**
 *
 * @author John Mamish
 */
public class main
{
    public static void main(String[] args)
    {
        System.out.println("");
        RTPDemoWindow d = new RTPDemoWindow(500);
        d.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        d.setVisible(true);
    }
}
