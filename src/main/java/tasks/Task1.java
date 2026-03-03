package tasks;

import common.Person;
import common.PersonService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    // Асимптотика: O(n), т.к. HashMap сделан за линейное время и прошлись по personIds тоже за линейное время

    Map<Integer, Person> personsMap = persons.stream()
            .collect(Collectors.toMap(
                    Person::id,
                    Function.identity(),
                    (a, b) -> a,
                    HashMap::new
            ));
    return personIds.stream()
            .map(personsMap::get)
            .toList();
  }
}
