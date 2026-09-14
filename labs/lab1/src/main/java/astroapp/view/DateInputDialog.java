package astroapp.view;

import javax.swing.*;
import java.awt.*;
import java.time.Year;

/**
 * Кастомная форма ввода даты рождения.
 * Согласно условию задания, СТАНДАРТНЫЙ компонент для ввода даты
 * (JSpinner с SpinnerDateModel, JCalendar/JDateChooser и т.п.) не
 * используется. Вместо этого день, месяц и год вводятся тремя
 * независимыми выпадающими списками (JComboBox) с обычными числами/строками —
 * с точки зрения Swing это ничем не отличается от выбора, скажем, товара
 * в интернет-магазине.
 */
public class DateInputDialog extends JDialog {

    private static final String[] MONTHS = {
            "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
    };

    private final JComboBox<Integer> dayCombo;
    private final JComboBox<String> monthCombo;
    private final JComboBox<Integer> yearCombo;

    private boolean confirmed = false;

    public DateInputDialog(Frame owner, int initialDay, int initialMonth, int initialYear) {
        super(owner, "Ввод даты рождения", true);

        int currentYear = Year.now().getValue();

        Integer[] days = new Integer[31];
        for (int i = 0; i < 31; i++) days[i] = i + 1;
        dayCombo = new JComboBox<>(days);

        monthCombo = new JComboBox<>(MONTHS);

        Integer[] years = new Integer[currentYear - 1900 + 1];
        for (int i = 0; i < years.length; i++) years[i] = currentYear - i; // от текущего к 1900
        yearCombo = new JComboBox<>(years);

        // восстанавливаем последние введённые данные, если они есть
        if (initialDay >= 1 && initialDay <= 31) {
            dayCombo.setSelectedItem(initialDay);
        }
        if (initialMonth >= 1 && initialMonth <= 12) {
            monthCombo.setSelectedIndex(initialMonth - 1);
        }
        if (initialYear >= 1900 && initialYear <= currentYear) {
            yearCombo.setSelectedItem(initialYear);
        }

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        formPanel.add(new JLabel("День:"));
        formPanel.add(dayCombo);
        formPanel.add(new JLabel("Месяц:"));
        formPanel.add(monthCombo);
        formPanel.add(new JLabel("Год:"));
        formPanel.add(yearCombo);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Отмена");
        okButton.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });
        cancelButton.addActionListener(e -> {
            confirmed = false;
            setVisible(false);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(formPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(okButton);
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public int getSelectedDay() {
        return (Integer) dayCombo.getSelectedItem();
    }

    public int getSelectedMonth() {
        return monthCombo.getSelectedIndex() + 1;
    }

    public int getSelectedYear() {
        return (Integer) yearCombo.getSelectedItem();
    }
}
