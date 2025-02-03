package result;

import javax.swing.SwingUtilities;

import result.model.AdminModelImpl;
import result.view.AdminGUI;

public class AdminApplication implements Runnable {

    public static void main(String[] args) {
        AdminApplication example = new AdminApplication();
        SwingUtilities.invokeLater(example);
    }

    public void run() {
        AdminModelImpl model = new AdminModelImpl();
        AdminGUI gui = new AdminGUI(model);
        gui.setVisible(true);
        model.test();
    }
}
