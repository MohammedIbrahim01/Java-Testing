package question1;

/**
 *
 * @author khalil2535
 */
public class Question1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TopGrades topGrades = new TopGrades(3);

        Student s1 = new Student("student 1", 80.5);
        Student s2 = new Student("student 2", 70.9);
        Student s3 = new Student("student 3", 99.5);
        Student s4 = new Student("student 4", 50);
        Student s5 = new Student("student 5", 99.6);

        topGrades.addNewStudent(s1);
        topGrades.addNewStudent(s2);
        topGrades.addNewStudent(s3);
        System.out.println(topGrades);

        topGrades.addNewStudent(s4);
        System.out.println(topGrades);

        topGrades.addNewStudent(s5);
        System.out.println(topGrades);

        topGrades.removeStudent(s5);
        System.out.println(topGrades);
    }

}
