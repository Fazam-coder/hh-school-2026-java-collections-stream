package tasks;

import common.Person;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3 {

  public static List<Person> sort(Collection<Person> persons) {
    if (persons == null) {
      return List.of();
    }
    return persons.stream()
            .sorted(Comparator.nullsLast(
                    Comparator.comparing(Person::firstName, Comparator.nullsLast(Comparator.naturalOrder()))
                            .thenComparing(Person::secondName, Comparator.nullsLast(Comparator.naturalOrder()))
                            .thenComparing(Person::createdAt, Comparator.nullsLast(Comparator.naturalOrder()))
            ))
            .toList();
  }
}
