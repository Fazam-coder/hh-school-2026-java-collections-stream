package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  private long count;

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  public List<String> getNames(List<Person> persons) {
    // Используем skip(1) - пропустить 1 элемент в stream, валидация на пустой список тогда не нужна
    return persons.stream().skip(1).map(Person::firstName).collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    // Не нужен distinct, т.к. возвращаем Set, и stream() тоже не нужен, т.к. нет промежуточных операций
    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    // Переписал с помощью Stream API, с помощью  filter убрал null и использовал Collectors.joining(" ")
    // Странно выводить 2 раза secondName, если у нас также хранится middleName
    return Stream.of(person.secondName(), person.firstName(), person.middleName())
            .filter(p -> p != null)
            .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    // Используем Stream API и получаем решение в 1 строку
    // Изначально в коде в качестве значения было convertPersonToString, поэтому я его оставил
    return persons.stream().collect(Collectors.toMap(Person::id, this::convertPersonToString, (a, b) -> a));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    // Из 1 коллекции построили HashSet (поиск за O(1)) и прошлись по 2 с помощью stream
    Set<Person> personsHash1 = new HashSet<>(persons1);
    return persons2.stream()
            .filter(personsHash1::contains)
            .findAny()
            .isPresent();
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    // Вместо forEach используем count - количество элементов в Stream
    // Можно было бы написать в 1 строку, но допустим, что нам нужно поле count
    count = numbers.filter(num -> num % 2 == 0).count();
    return count;
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  /* hashcode() для Integer - значение самого Integer, когда мы создаем HashSet, изначально он создается с 16 buckets
  // и увеличивается до тех пор, пока будет заполнено не более 75%, получается, каждое число будет в своем отдельном bucket.
  // toString будет проходиться по ним, то есть всегда в отсортированном порядке
   */
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
