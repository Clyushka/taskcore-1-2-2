package demidova;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
        //for (int i = 0; i < 100; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
//        persons.add(new Person("G", "N", 18, Sex.MAN, Education.HIGHER));
//        persons.add(new Person("W", "N", 65, Sex.MAN, Education.HIGHER));
//        persons.add(new Person("Q", "N", 60, Sex.WOMAN, Education.HIGHER));
//        persons.add(new Person("T", "N", 65, Sex.WOMAN, Education.HIGHER));

        long childrenCount = persons.parallelStream().filter(p -> p.getAge() < 18).count();
        System.out.println("Несовершеннолетних всего: " + childrenCount);

        List<String> recruits = persons.parallelStream()
                .filter(p -> p.getSex() == Sex.MAN && p.getAge() >= 18 && p.getAge() <= 27)
                .map(p -> p.getFamily())
                .collect(Collectors.toList());
//        for (String r: recruits) {
//            System.out.println(r);
//        }
        System.out.println("Призывников всего: " + recruits.size());

        List<Person> workers = persons.parallelStream()
                .filter(p -> p.getEducation() == Education.HIGHER &&
                        (p.getSex() == Sex.MAN ? p.getAge() >= 18 && p.getAge() <= 65 : p.getAge() >= 18 && p.getAge() <= 60))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
//        for (Person w: workers) {
//            System.out.println(w);
//        }
        System.out.println("Работающих из них всего: " + workers.size());
    }
}
