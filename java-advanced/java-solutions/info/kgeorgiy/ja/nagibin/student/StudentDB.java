package info.kgeorgiy.ja.nagibin.student;

import info.kgeorgiy.java.advanced.student.*;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDB implements AdvancedQuery {

    private final Comparator<Student> nameComparator = Comparator
            .comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .reversed()
            .thenComparing(Student::getId);

    private final Comparator<Student> idComparator = Comparator.comparingInt(Student::getId);

    private <E, T> Stream<Map.Entry<E, List<T>>> collectByFunctionToSetStream(final Collection<T> students,
                                                                              final Function<T, E> grouping) {
        return collectStreamByFunctionToSetStream(students.stream(), grouping);
    }

    private <E, T> Stream<Map.Entry<E, List<T>>> collectStreamByFunctionToSetStream(final Stream<T> stream,
                                                                                    final Function<T, E> grouping) {
        return stream
                .collect(Collectors.groupingBy(grouping))
                .entrySet().stream();
    }


    private Stream<Group> mapGroupsByUnaryOperator(final Collection<Student> students,
                                                   final UnaryOperator<List<Student>> unaryOperator) {
        return collectByFunctionToSetStream(students, Student::getGroup)
                .map(it -> new Group(
                        it.getKey(),
                        unaryOperator.apply(it.getValue())));
    }

    private List<Group> getGroupsByUnaryOperator(final Collection<Student> students,
                                                 final UnaryOperator<List<Student>> unaryOperator) {
        return mapGroupsByUnaryOperator(students, unaryOperator)
                .sorted(Comparator.comparing(Group::getName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Group> getGroupsByName(final Collection<Student> students) {
        return getGroupsByUnaryOperator(students, this::sortStudentsByName);
    }

    @Override
    public List<Group> getGroupsById(final Collection<Student> students) {
        return getGroupsByUnaryOperator(students, this::sortStudentsById);
    }

    private <E, T> E getLargestValueByFunction(final Collection<T> students,
                                               final Function<List<T>, Integer> function,
                                               final Comparator<Map.Entry<E, List<T>>> comparator,
                                               final Function<T, E> groping,
                                               final E emptyValue) {
        return collectByFunctionToSetStream(students, groping)
                .max(Comparator.comparingInt((Map.Entry<E, List<T>> it) -> function.apply(it.getValue()))
                        .thenComparing(comparator))
                .map(Map.Entry::getKey).orElse(emptyValue);
    }

    private final Comparator<Map.Entry<GroupName, List<Student>>> mapGroupNameComparator = Map.Entry.comparingByKey();

    @Override
    public GroupName getLargestGroup(final Collection<Student> students) {
        return getLargestValueByFunction(students, List::size, mapGroupNameComparator, Student::getGroup, null);
    }

    @Override
    public GroupName getLargestGroupFirstName(final Collection<Student> students) {
        return getLargestValueByFunction(students,
                it -> getDistinctFirstNames(it).size(),
                mapGroupNameComparator.reversed(),
                Student::getGroup, null);
    }

    private <E> Stream<E> mapToStreamByFunction(final List<Student> students, final Function<Student, E> function) {
        return students.stream().map(function);
    }

    private <E> List<E> mapToListByFunction(final List<Student> students, final Function<Student, E> function) {
        return mapToStreamByFunction(students, function).collect(Collectors.toList());
    }

    @Override
    public List<String> getFirstNames(final List<Student> students) {
        return mapToListByFunction(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(final List<Student> students) {
        return mapToListByFunction(students, Student::getLastName);
    }

    @Override
    public List<GroupName> getGroups(final List<Student> students) {
        return mapToListByFunction(students, Student::getGroup);
    }

    private final Function<Student, String> getFullStudent = it -> String.format("%s %s", it.getFirstName(), it.getLastName());

    @Override
    public List<String> getFullNames(final List<Student> students) {
        return mapToListByFunction(students, getFullStudent);
    }

    @Override
    public Set<String> getDistinctFirstNames(final List<Student> students) {
        return mapToStreamByFunction(students, Student::getFirstName).collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public String getMaxStudentFirstName(final List<Student> students) {
        return students.stream().max(idComparator)
                .map(Student::getFirstName).orElse("");
    }

    private List<Student> sortStudentsByComparator(final Collection<Student> students, final Comparator<Student> comparator) {
        return students.stream().sorted(comparator).toList();
    }

    @Override
    public List<Student> sortStudentsById(final Collection<Student> students) {
        return sortStudentsByComparator(students, idComparator);
    }

    @Override
    public List<Student> sortStudentsByName(final Collection<Student> students) {
        return sortStudentsByComparator(students, nameComparator);
    }

    private <E> List<Student> findStudentsByAttr(final Collection<Student> students,
                                                 final Function<Student, E> attr,
                                                 final E value) {
        return students.stream().filter(it -> attr.apply(it).equals(value)).sorted(nameComparator).toList();
    }

    @Override
    public List<Student> findStudentsByFirstName(final Collection<Student> students, final String name) {
        return findStudentsByAttr(students, Student::getFirstName, name);
    }

    @Override
    public List<Student> findStudentsByLastName(final Collection<Student> students, final String name) {
        return findStudentsByAttr(students, Student::getLastName, name);
    }

    @Override
    public List<Student> findStudentsByGroup(final Collection<Student> students, final GroupName group) {
        return findStudentsByAttr(students, Student::getGroup, group);
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(final Collection<Student> students, final GroupName group) {
        return findStudentsByGroup(students, group).stream()
                .collect(Collectors.toMap(
                        Student::getLastName,
                        Student::getFirstName,
                        BinaryOperator.minBy(Comparator.naturalOrder())
                ));
    }

    @Override
    public String getMostPopularName(final Collection<Student> students) {
        return getLargestValueByFunction(
                collectByFunctionToSetStream(students, Student::getGroup)
                        .map((Map.Entry<GroupName, List<Student>> it) -> getDistinctFirstNames(it.getValue()))
                        .flatMap(Collection::stream).collect(Collectors.toList()),
                List::size,
                Map.Entry.comparingByKey(),
                Function.identity(),
                "");

    }

    public <E> List<E> getListOfFunction(final Collection<Student> students, final int[] indices,
                                         final Function<Student, E> attr) {
        List<Student> list = students.stream().toList();
        return Arrays.stream(indices)
                .mapToObj(it -> attr.apply(list.get(it)))
                .toList();
    }

    @Override
    public List<String> getFirstNames(final Collection<Student> students, final int[] indices) {
        return Arrays.stream(indices)
                .mapToObj(it -> students.stream().toList().get(it).getFirstName())
                .toList();
    }

    @Override
    public List<String> getLastNames(final Collection<Student> students, final int[] indices) {
        return getListOfFunction(students, indices, Student::getLastName);
    }

    @Override
    public List<GroupName> getGroups(final Collection<Student> students, final int[] indices) {
        return getListOfFunction(students, indices, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(final Collection<Student> students, final int[] indices) {
        return getListOfFunction(students, indices, getFullStudent);
    }
}
