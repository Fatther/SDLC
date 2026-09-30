package astroapp.controller;

import astroapp.model.BirthDateModel;
import astroapp.view.DateInputDialog;
import astroapp.view.MainFrame;

import java.time.DateTimeException;

/**
 * Контроллер: реагирует на действия пользователя в View,
 * проверяет ввод и передаёт данные в модель.
 * Сама модель, будучи "активной", уже сама оповестит View об изменении —
 * контроллеру не нужно вручную обновлять форму после успешного ввода.
 */
public class AppController {

    private final BirthDateModel model;
    private final MainFrame view;

    public AppController(BirthDateModel model, MainFrame view) {
        this.model = model;
        this.view = view;
    }

    /** Вызывается из MainFrame по нажатию кнопки "Ввести дату рождения". */
    public void onEnterDateRequested() {
        DateInputDialog dialog = new DateInputDialog(
                view,
                model.getLastDay(),
                model.getLastMonth(),
                model.getLastYear()
        );
        dialog.setVisible(true);

        if (!dialog.isConfirmed()) {
            return; // пользователь нажал "Отмена" — ничего не меняем
        }

        int day = dialog.getSelectedDay();
        int month = dialog.getSelectedMonth();
        int year = dialog.getSelectedYear();

        try {
            model.setBirthDate(day, month, year);
        } catch (DateTimeException ex) {
            view.showError("Указанная дата не существует: " + day + "." + month + "." + year +
                    ". Проверьте корректность введённых данных.");
        }
    }
}
