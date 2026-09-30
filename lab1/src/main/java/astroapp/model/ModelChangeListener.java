package astroapp.model;

/**
 * Слушатель изменений модели.
 * Модель является "активной": она сама оповещает все зарегистрированные
 * представления о своём изменении, а не ждёт, пока View её опросит.
 */
public interface ModelChangeListener {
    void onModelChanged(BirthDateModel model);
}
