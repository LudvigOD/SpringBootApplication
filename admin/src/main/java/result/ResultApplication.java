package result;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


public class ResultApplication implements Runnable {

    public static void main(String[] args) {
        ResultApplication example = new ResultApplication();
        // schedule this for the event dispatch thread (edt)
        SwingUtilities.invokeLater(example);
    }

    public void run()
    {
        AdminView.createAndShowGUI();
    }
}