package tasks;

import common.Area;
import common.Person;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 {

  public static Set<String> getPersonDescriptions(Collection<Person> persons,
                                                  Map<Integer, Set<Integer>> personAreaIds,
                                                  Collection<Area> areas) {
    Map<Integer, String> areaIdsNames = areas.stream().collect(Collectors.toMap(Area::getId, Area::getName));
    // Map<Имя, Set<регион>>
    Map<String, Set<String>> personAreaNames = persons.stream()
            .collect(Collectors.toMap(
                    Person::firstName,
                    person -> personAreaIds.get(person.id()).stream()
                            .map(areaIdsNames::get)
                            .collect(Collectors.toSet())
            ));
    return personAreaNames.entrySet().stream()
            .flatMap(entry -> entry.getValue().stream()
                    .map(value -> entry.getKey() + " - " + value))
            .collect(Collectors.toSet());
  }
}
