package org.example.students;

import org.example.students.exceptions.RecordNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
class IExaminationTest {

    private  Examination examination;

    @BeforeEach
    void setUp() {
        examination = new IExamination();
            }

    @Test
    void addRecord() throws RecordNotFoundException {
        Record record = new Record("Иванов Иван", "Математика", 4);
        examination.addRecord(record);
        Record actual = examination.getRecord(record.name());

        Assertions.assertEquals(actual, record);
    }

    @Test
    void getRecord() {
        Assertions.assertThrows(RecordNotFoundException.class, () -> examination.getRecord("Сидоров Вадим"));
    }

    @Test
    void getAverageForSubject() {
        examination.addRecord(new Record("Петров Петр", "Физика", 3));
        examination.addRecord(new Record("Смирнов Сергей", "Химия", 4));
        double average = examination.getAverageForSubject("Физика");
        Assertions.assertTrue(Double.compare(average, 3.0) == 0);
    }

    @Test
    void multipleSubmissionsStudentNames() {
        examination.addRecord(new Record("Иванов Иван", "Математика", 4));
        examination.addRecord(new Record("Петров Петр", "Физика", 3));
        examination.addRecord(new Record("Иванов Иван", "Русский язык", 3));
        examination.addRecord(new Record("Иванов Иван", "Русский язык", 4));
        examination.addRecord(new Record("Смирнов Сергей", "Химия", 4));

        Set<String> expected = new HashSet<>(Collections.singletonList("Иванов Иван"));
        Set<String> actual = examination.multipleSubmissionsStudentNames();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void lastFiveStudentsWithExcellentMarkOnAnySubject() {
        examination.addRecord(new Record("Иванов Иван", "Математика", 5));
        examination.addRecord(new Record("Петров Петр", "Математика", 4));
        examination.addRecord(new Record("Сидоров Сидор", "Физика", 5));
        examination.addRecord(new Record("Смирнов Сергей", "Химия", 5));
        examination.addRecord(new Record("Федоров Федор", "Литература", 3));
        examination.addRecord(new Record("Козлов Козла", "История", 5));
        examination.addRecord(new Record("Лебедева Лариса", "Химия", 5));

        Set<String> result = examination.lastFiveStudentsWithExcellentMarkOnAnySubject();

        // Ожидаемое множество студентов
        Set<String> expected = new HashSet<>(Arrays.asList("Иванов Иван", "Сидоров Сидор", "Смирнов Сергей",
                "Козлов Козла", "Лебедева Лариса"));

        Assertions.assertEquals(expected, result); // Проверяем, что возвращаемое множество студентов совпадает с ожидаемым
    }

    @Test
    void getAllScores() {
        // Добавляем несколько записей
        examination.addRecord(new Record("Иванов Иван", "Математика", 4));
        examination.addRecord(new Record("Петров Петр", "Физика", 3));
        examination.addRecord(new Record("Сидоров Сидор", "Химия", 5));

        // Получаем все записи
        Collection<Record> scores = examination.getAllScores();

        // Ожидаемое множество записей
        Set<Record> expectedRecords = new HashSet<>(Arrays.asList(
                new Record("Иванов Иван", "Математика", 4),
                new Record("Петров Петр", "Физика", 3),
                new Record("Сидоров Сидор", "Химия", 5)
        ));

        // Проверяем, что возвращаемое множество совпадает с ожидаемым
        Assertions.assertEquals(expectedRecords.size(), scores.size());
        for (Record record : expectedRecords) {
            Assertions.assertTrue(scores.contains(record));
        }
}
}