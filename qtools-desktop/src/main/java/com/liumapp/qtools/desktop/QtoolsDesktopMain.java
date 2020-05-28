package com.liumapp.qtools.desktop;

import mdlaf.*;
import mdlaf.animation.*;
import mdlaf.utils.MaterialColors;

import javax.swing.*;
import java.awt.*;

/**
 * file QtoolsDesktopMain.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/5/28
 */
public class QtoolsDesktopMain {

    public static void main (String[] args) {
        try {
            UIManager.setLookAndFeel (new MaterialLookAndFeel ());
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace ();
        }

        JFrame frame = new JFrame ("Material Design UI for Swing by atharva washimkar");
        frame.setMinimumSize (new Dimension (600, 400));

        JButton button = new JButton ("PRESS ME");
        button.setMaximumSize (new Dimension (200, 200));

        JPanel content = new JPanel ();
        content.add (button);
        frame.add (content, BorderLayout.CENTER);

        // on hover, button will change to a light gray
        MaterialUIMovement.add (button, MaterialColors.GRAY_100);

        frame.pack ();
        frame.setVisible (true);
    }

}
