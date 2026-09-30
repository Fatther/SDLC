package astroapp.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;

/**
 * Вычисляет "судьбу" человека по дню недели, на который приходится
 * его дата рождения.
 */
public final class FateCalculator {

    private static final Map<DayOfWeek, String> FATES = new EnumMap<>(DayOfWeek.class);

    static {
        FATES.put(DayOfWeek.MONDAY,
                "Рождённые в понедельник — люди дела. Вас ждёт непростой, но " +
                        "плодотворный путь: успех придётся добывать трудом, зато он будет прочным.");
        FATES.put(DayOfWeek.TUESDAY,
                "Рождённые во вторник обладают бойцовским характером. Судьба " +
                        "приготовила вам немало испытаний, но и упорства вам не занимать — вы победите.");
        FATES.put(DayOfWeek.WEDNESDAY,
                "Рождённые в среду — прирождённые мыслители и посредники. Вам " +
                        "суждено находить выход там, где другие видят тупик.");
        FATES.put(DayOfWeek.THURSDAY,
                "Рождённые в четверг щедры и удачливы. Жизнь одарит вас " +
                        "путешествиями, новыми знакомствами и неожиданной удачей.");
        FATES.put(DayOfWeek.FRIDAY,
                "Рождённые в пятницу романтичны и обаятельны. Ваша судьба " +
                        "тесно связана с любовью и творчеством — именно в них вы найдёте своё счастье.");
        FATES.put(DayOfWeek.SATURDAY,
                "Рождённые в субботу серьёзны и ответственны не по годам. Вас " +
                        "ждёт стабильность, добытая упорным трудом, и уважение окружающих.");
        FATES.put(DayOfWeek.SUNDAY,
                "Рождённые в воскресенье отмечены особым везением. Судьба " +
                        "будет благосклонна к вам во всех начинаниях, а жизнь — светлой и радостной.");
    }

    public static String calculateFate(LocalDate birthDate) {
        DayOfWeek dow = birthDate.getDayOfWeek();
        return FATES.get(dow);
    }
}
