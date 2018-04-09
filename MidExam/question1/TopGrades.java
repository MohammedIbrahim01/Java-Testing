package question1;

/**
 *
 * @author khalil2535
 */
public class TopGrades {

    private final SortedDoubleLinkedList<Student> TopStudents;

    public TopGrades(int size) {
        TopStudents = new SortedDoubleLinkedList<>(3);
    }

    public SortedDoubleLinkedList<Student> getTopStudents() {
        return TopStudents;
    }

    public boolean addNewStudent(Student student) {
        TopStudents.add(student);
        return true;
    }

    public boolean removeStudent(Student student) {
        return TopStudents.remove(student);
    }

    @Override
    public String toString() {
        return TopStudents.toString();
    }

}
