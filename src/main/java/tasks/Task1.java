package tasks;

import common.Person;
import common.PersonService;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);
    // Асимптотика: O(n^2), т.к. для каждого id мы за линейное время находим Person
    return personIds.stream()
            .map(id -> persons.stream()
                    .filter(p -> Objects.equals(p.id(), id))
                    .findFirst().get()
            ).toList();
  }
}
