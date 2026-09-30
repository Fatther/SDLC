package astroapp.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * Активная модель приложения.
 * "Активная" означает, что модель не просто хранит данные и отдаёт их по
 * запросу, а сама уведомляет все зарегистрированные представления о любом своём изменении.
 * Дополнительно модель запоминает последний УСПЕШНО введённый день/месяц/год
 * в постоянном хранилище, чтобы значения
 * восстанавливались в форме ввода даже после перезапуска приложения.
 */
public class BirthDateModel {

    private static final String PREF_DAY = "lastDay";
    private static final String PREF_MONTH = "lastMonth";
    private static final String PREF_YEAR = "lastYear";

    private final Preferences prefs = Preferences.userNodeForPackage(BirthDateModel.class);
    private final List<ModelChangeListener> listeners = new ArrayList<>();

    private LocalDate birthDate;   // текущая корректная дата рождения (может быть null, если ещё не введена)
    private String fateText = "";  // текст судьбы для текущей даты

    public void addListener(ModelChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ModelChangeListener listener) {
        listeners.remove(listener);
    }

    private void fireChanged() {
        for (ModelChangeListener l : new ArrayList<>(listeners)) {
            l.onModelChanged(this);
        }
    }

    /**
     * Пытается установить новую дату рождения.
     * выбрасывает DateTimeException если день/месяц/год не образуют существующую дату
     */
    public void setBirthDate(int day, int month, int year) {
        LocalDate date = LocalDate.of(year, month, day); // бросит DateTimeException, если дата некорректна (напр. 30 февраля)

        this.birthDate = date;
        this.fateText = FateCalculator.calculateFate(date);

        // запоминаем последние успешно введённые данные (в т.ч. между запусками программы)
        prefs.putInt(PREF_DAY, day);
        prefs.putInt(PREF_MONTH, month);
        prefs.putInt(PREF_YEAR, year);

        fireChanged();
    }

    public boolean hasBirthDate() {
        return birthDate != null;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getFateText() {
        return fateText;
    }

    /** Последний введённый (или сохранённый ранее) день. -1, если данных ещё не было. */
    public int getLastDay() {
        return prefs.getInt(PREF_DAY, -1);
    }

    /** Последний введённый (или сохранённый ранее) месяц. -1, если данных ещё не было. */
    public int getLastMonth() {
        return prefs.getInt(PREF_MONTH, -1);
    }

    /** Последний введённый (или сохранённый ранее) год. -1, если данных ещё не было. */
    public int getLastYear() {
        return prefs.getInt(PREF_YEAR, -1);
    }
}
