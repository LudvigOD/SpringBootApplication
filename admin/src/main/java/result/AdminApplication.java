package result;

import javax.swing.SwingUtilities;

public class AdminApplication implements Runnable {

    public static void main(String[] args) {
        AdminApplication example = new AdminApplication();
        SwingUtilities.invokeLater(example);
    }

    public void run() {
        AdminGUI.createAndShowGUI();
    }
}
