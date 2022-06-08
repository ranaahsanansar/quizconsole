package quizappone3;

public abstract class Person {

    static boolean mode = true;
    String name;
    int age;

    Person() {
        name = "Null";
        age = 1;
    }

    Person(String name, int age) {
            this.age = age;
            
        if (name.length() > 3) {
            this.name = name;
        } else {
            this.name = "Unknown";
        }

    }

    protected abstract void result();


    protected abstract void display();

}
