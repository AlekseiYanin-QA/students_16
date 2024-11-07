package org.example.students;

import org.example.students.exceptions.RecordNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class IExamination implements Examination{

    private final Map<String, List<Record>> records = new HashMap<>();

    @Override
    public void addRecord(Record score) {
        records.computeIfAbsent(score.name(), k -> new ArrayList<>()).add(score); // Добавляем запись в список
    }

    @Override
    public Record getRecord(String name) throws RecordNotFoundException {
        List<Record> studentRecords = records.get(name);
        if (studentRecords == null || studentRecords.isEmpty()) {
            throw new RecordNotFoundException(name);
        }
        return studentRecords.get(studentRecords.size() - 1); // Возвращаем последнюю запись
    }

    @Override
    public double getAverageForSubject(String subject) {
        // Используем flatMap для "разворачивания" списков в одну стрим последовательность
        List<Record> filteredRecords = records.values().stream()
                .flatMap(List::stream) // Разворачиваем списки записей в одну последовательность
                .filter(record -> record.subject().equals(subject)) // Фильтруем по предмету
                .collect(Collectors.toList());

        if (filteredRecords.isEmpty()) {
            return 0.0; // Если нет записей по данному предмету, возвращаем 0.0
        }

        double sum = filteredRecords.stream()
                .mapToInt(Record::grade) // Получаем оценки из записей
                .sum();

        return sum / filteredRecords.size(); // Возвращаем среднее значение
    }

    @Override
    public Set<String> multipleSubmissionsStudentNames() {
        Set<String> set = new HashSet<>();
        for (Map.Entry<String, List<Record>> entry : records.entrySet()) { // Изменяем тип на List<Record>
            // Проверяем, есть ли у студента более одной записи
            if (entry.getValue().size() > 1) {
                set.add(entry.getKey());
            }
        }
        return set;
    }

    @Override
    public Set<String> lastFiveStudentsWithExcellentMarkOnAnySubject() {
        return records.values().stream()
                .flatMap(List::stream) // "Разворачиваем" списки записей
                .filter(record -> record.grade() == 5) // Фильтруем записи по оценке 5
                .map(Record::name) // Получаем имена студентов
                .distinct() // Убираем дубликаты имен
                .limit(5) // Ограничиваем результат первыми пятью студентами
                .collect(Collectors.toSet()); // Собираем в множество
    }

    @Override
    public Collection<Record> getAllScores() {
        List<Record> allRecords = new ArrayList<>();
        records.values().forEach(allRecords::addAll);
        return allRecords;
    }
}
