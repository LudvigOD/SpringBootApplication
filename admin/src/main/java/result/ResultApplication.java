package result;

import javax.swing.SwingUtilities;

public class ResultApplication implements Runnable {

    public static void main(String[] args) {
        ResultApplication example = new ResultApplication();
        SwingUtilities.invokeLater(example);
    }

    public void run()
    {
        AdminView.createAndShowGUI();
    }
}