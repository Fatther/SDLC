package astroapp;

import astroapp.controller.AppController;
import astroapp.model.BirthDateModel;
import astroapp.view.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            BirthDateModel model = new BirthDateModel();     // Model
            MainFrame view = new MainFrame(model);            // View
            AppController controller = new AppController(model, view); // Controller
            view.setController(controller);

            view.setVisible(true);
        });
    }
}
