package org.example.students;

import org.example.students.exceptions.RecordNotFoundException;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;

public interface Examination {
    void addRecord(Record score);

    Record getRecord(String name) throws RecordNotFoundException;


    double getAverageForSubject(String subject);

    Set<String> multipleSubmissionsStudentNames();

    Set<String> lastFiveStudentsWithExcellentMarkOnAnySubject();

    Collection<Record> getAllScores();
}
