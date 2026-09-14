package astroapp.view;

import astroapp.controller.AppController;
import astroapp.model.BirthDateModel;
import astroapp.model.ModelChangeListener;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

/**
 * Главная форма приложения.
 * Реализует ModelChangeListener, поэтому автоматически обновляется,
 * как только активная модель извещает об изменении данных — без каких-либо
 * циклов опроса модели со стороны View.
 */
public class MainFrame extends JFrame implements ModelChangeListener {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final BirthDateModel model;
    private AppController controller; // назначается извне после создания

    private final JButton enterDateButton = new JButton("Ввести дату рождения");
    private final JLabel birthDateLabel = new JLabel(" ");
    private final JTextArea fateArea = new JTextArea();

    public MainFrame(BirthDateModel model) {
        super("Судьба по дню недели рождения");
        this.model = model;
        model.addListener(this);

        buildUi();

        // если данные уже сохранены с прошлого запуска — сразу покажем судьбу
        if (model.hasBirthDate()) {
            onModelChanged(model);
        }
    }

    public void setController(AppController controller) {
        this.controller = controller;
        enterDateButton.addActionListener(e -> controller.onEnterDateRequested());
    }

    private void buildUi() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(enterDateButton);
        topPanel.add(birthDateLabel);
        add(topPanel, BorderLayout.NORTH);

        fateArea.setEditable(false);
        fateArea.setLineWrap(true);
        fateArea.setWrapStyleWord(true);
        fateArea.setFont(fateArea.getFont().deriveFont(15f));
        fateArea.setBorder(BorderFactory.createTitledBorder("Ваша судьба"));
        add(new JScrollPane(fateArea), BorderLayout.CENTER);

        setSize(480, 300);
        setLocationRelativeTo(null);
    }

    /** Вызывается активной моделью автоматически при любом её изменении. */
    @Override
    public void onModelChanged(BirthDateModel model) {
        birthDateLabel.setText("Дата рождения: " + model.getBirthDate().format(DATE_FORMAT));
        fateArea.setText(model.getFateText());
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
    }
}
