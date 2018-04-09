package question1;

/**
 *
 * @author khalil2535
 */
public class Student implements Comparable<Student> {

    private String name;
    private double grade;

    public Student(String name, double grade) {
        this.name = name;
        if (grade < 0) {
            return;
        }
        this.grade = grade;
    }

    @Override
    public int compareTo(Student o) {
        return (int) ((grade - o.getGrade()) * 1000);
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        if (grade < 0) {
            return;
        }
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " : " + grade;
    }

}
