package quizappone3;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author Rana Ahsan Ansar
 */
public class QuizAppOne3 {

    static Scanner input = new Scanner(System.in).useDelimiter("\n");
    static String userNameAuthentication = "root";
    static final int passwordAuthentication = 123;

    static String lastTeacher ;


    static <T extends Comparable<T>> void maxArray(T[] arr) {

        System.out.println(arr[0]);

    }

    static boolean checkNumbers(String string){
        
        CharSequence[] checks= {"0","1" , "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "*", "/" , "`", "!",
                                "@", "#", "$", "%", "^", "&", "(", ")", "_", "-", "=", "{", "}", "[", "]"};
        for(int i= 0; i < checks.length; i++ ){
            if(string.contains(checks[i])){
                return true;
            }
        }
        return false;
    }



    //    Main Function ---------------------
//    --------------------------------
    public static void main(String[] args)  {

        boolean flag = true;
        String name;
        int age;
        List<Person> array = new ArrayList<>();

        System.out.println("Welcome To Quiz Application");
        System.out.println("Rana Ahsan Ansar 70077960");
        System.out.println("Java Project 6th B");
        System.out.println("---------------------------------------------");
        System.out.println("---------------------------------------------");

        while (flag) {
            System.out.println("\n\nSelect Mode");
            System.out.println("1. Teacher");
            System.out.println("2. Student");
            System.out.println("3. Show Student in Sorted Form");
            System.out.println("4. Exit");
            int caseNo;
            try {
                caseNo = input.nextInt();
            } catch (InputMismatchException e) {
                caseNo =  99;
                input.nextLine();
                System.out.println("Please Enter Only a Integer Number");
            }
            switch (caseNo) {
                case 1 -> {
                    int password = 0;
                    String userName = "Null";
                    boolean flag2 ;
                    try{
                        System.out.println("Teacher Mode");
                        System.out.println("Enter Zero to Go Back");
                        System.out.print("Enter Authenticate user name: ");
                        userName = input.next();
                        if (userName.equals("0")){
                            throw new AhsanExceptions("Going Back");
                        }
                        System.out.print("Enter Authenticate Password: ");
                        password = input.nextInt();
                        if (password == 0){
                            throw new AhsanExceptions("Going Back");
                        }
                    }catch (InputMismatchException e){
                        input.nextLine();
                        System.out.println("Input Mismatch Enter only integer value as Password!");
                        break;
                    }catch (AhsanExceptions e){
                        System.out.println(e.getMessage());
                    }


                    if(password == passwordAuthentication && userName.equals(userNameAuthentication)){
//                        if(passwordAuthentication == 123 && userNameAuthentication.equals("root")){

                            flag2 = true;
                        try {
                            System.out.print("Enter your Name: ");
                            name = input.next();
                            if(name.equals("0")){
                                throw new AhsanExceptions("Going Back");
                            } else if (checkNumbers(name)) {
                                throw new AhsanExceptions("Your Name contain Special Characters which is not Valid!");
                            }

                            System.out.print("Enter your age: ");
                            age = input.nextInt();
                            if (age == 0){
                                throw new AhsanExceptions("Going Back");
                            }
                            try {
                                lastTeacher = QuestionDBImp.getLastTeacher();
                                System.out.println("Last Quiz modified by : "+lastTeacher);

                                if (age > 100){
                                    throw new AhsanExceptions("Invalid age");
                                }else if (age < 10){
                                    throw new AhsanExceptions("Invalid age");
                                }
                                else if (name.length() < 3){
                                    throw new AhsanExceptions("Invalid Name! Name length must be grater then 2");
                                } else if (name.length() > 100) {
                                    throw new AhsanExceptions("Your Name is Too Long");
                                }

                                Teacher teacher = new Teacher(name, age);


                                while (flag2) {
                                    try {
                                        System.out.println("\n1. Create New Quiz");
                                        System.out.println("2. Show List of Students");
                                        System.out.println("3. Update Question");
                                        System.out.println("4. Delete Question");
                                        System.out.println("5. Show List of Question");
                                        System.out.println("6. Add a new quesion in existing Quiz");
                                        System.out.println("7. Exit");
                                        caseNo = input.nextInt();
                                        switch (caseNo) {
                                            case 1 -> {
                                                String questionInsert, opt1, opt2, opt3, opt4;
                                                int correctAnswer , mcqsNum = 0;
                                                boolean next;
                                                teacher.emptyQuiz();
                                                do {
                                                    mcqsNum++;
                                                    System.out.print("Enter Question: ");
                                                    questionInsert = input.next();
                                                    System.out.print("Enter option 1: ");
                                                    opt1 = input.next();
                                                    System.out.print("Enter option 2: ");
                                                    opt2 = input.next();
                                                    System.out.print("Enter option 3: ");
                                                    opt3 = input.next();
                                                    System.out.print("Enter option 4: ");
                                                    opt4 = input.next();
                                                    System.out.print("Enter Correct Option Number(Integer):");

                                                    correctAnswer = input.nextInt();
                                                    try {
                                                        if (correctAnswer > 4){
                                                            throw new AhsanExceptions("Invalid Option");
                                                        }else if (correctAnswer <=0 ){
                                                            throw new AhsanExceptions("Invalid Option");
                                                        }

                                                        teacher.insert(mcqsNum, questionInsert, opt1 , opt2 , opt3, opt4 , correctAnswer , true);
                                                    }catch (AhsanExceptions e){
                                                        System.out.println(e.getMessage());
                                                    }
                                                    System.out.println("Enter True to add Next Question");
                                                    System.out.println("Enter False to GOBack");
                                                    next = input.nextBoolean();
                                                } while(next);

//                                                break;
                                            }
                                            case 2 -> //                                                break;
                                                    teacher.result();
                                            case 3 -> {
                                                int id;
                                                String question_s, opt1, opt2, opt3, opt4;
                                                int correct_ans;

                                                teacher.showAllQuestion();
                                                if(QuestionDBImp.effectedrows("SELECT * FROM `questions`") == 0){
                                                    System.out.println("There is No Question in DataBase");
                                                }else{
                                                    System.out.println("Enter 0 to go Back");
                                                    System.out.print("Enter Question No: ");
                                                    id = input.nextInt();
                                                    try {
                                                    if (id == 0 ){
                                                        throw new AhsanExceptions("Going Back");
                                                    }
                                                    if(QuestionDBImp.effectedrows("SELECT * FROM `questions` WHERE `id` = " + id) != 0){
                                                        Mcqs question =  teacher.selectById(id);
                                                        System.out.println("You Select :" + question.getQuestion() + "\n" + question.getOpt1() + "   " + question.getOpt2()+ "   " + question.getOpt3()+ "   " + question.getOpt4() );
                                                        System.out.println("Correct Option: " +  question.getCorrect());
                                                        System.out.println("---------------------------------------------");
                                                        System.out.println("---------------------------------------------");
                                                        System.out.println("Enter 0 to go Back");
                                                        System.out.print("Enter new Question:");
                                                        question_s = input.next();
                                                        if (question_s.equals("0")){
                                                            throw new AhsanExceptions("Going Back");
                                                        }else
                                                        System.out.print("Enter Options\n1: ");
                                                        opt1 = input.next();
                                                        System.out.print("2: ");
                                                        opt2 = input.next();
                                                        System.out.print("3: ");
                                                        opt3 = input.next();
                                                        System.out.print("4: ");
                                                        opt4 = input.next();
                                                        System.out.print("Enter new Correct Option Number(Integer):");
                                                        correct_ans = input.nextInt();
                                                        try{
                                                            if(correct_ans > 4 ){
                                                                throw new AhsanExceptions("Invalid Option");
                                                            }
                                                            if (correct_ans <= 0 ){
                                                                throw new AhsanExceptions("Invalid Option");
                                                            }
                                                            teacher.update(id , question_s , opt1 , opt2 , opt3, opt4 , correct_ans);
                                                        }catch (AhsanExceptions e){
                                                            System.out.println(e.getMessage());
                                                        }

                                                    }else{
                                                        System.out.println("There is no Question in DataBase with ID " + id );
                                                    }

                                                    }catch (AhsanExceptions e){
                                                        System.out.println(e.getMessage());
                                                    }

                                                }


//                                                break;
                                            }
                                            case 4 -> {
                                                int id;
                                                teacher.showAllQuestion();
                                                if(QuestionDBImp.effectedrows("SELECT * FROM `questions`") == 0){
                                                    System.out.println("Sorry There is No Question in DataBase");
                                                }else{
                                                    System.out.println("Enter 0 to go Back");
                                                    System.out.println("Enter Question No");
                                                    id = input.nextInt();
                                                    try {

                                                    if (id == 0 ){
                                                        throw new AhsanExceptions("Going Back");
                                                    }else {
                                                        teacher.delete(id);
                                                    }
                                                    }catch (AhsanExceptions e){
                                                        System.out.println(e.getMessage());

                                                    }
                                                }

//                                                break;
                                            }
                                            case 5 -> {
                                                System.out.println("----------------------------------");
                                                teacher.showAllQuestion();
//                                                break;
                                            }
                                            case 6 -> {
                                                try {
                                                    if (!lastTeacher.equals(teacher.name)){
                                                        throw new AhsanExceptions("Unauthorized Teacher");
                                                    }

                                                int mcqNum ;
                                                mcqNum = QuestionDBImp.effectedrows("SELECT * FROM `questions`");
                                                System.out.println("There are " + mcqNum +  " mcqs available in DataBase");

                                                try {
                                                    if(mcqNum <= 0){
                                                        throw new AhsanExceptions("There is no question In DataBase You Should need create quiz from Option 1");
                                                    }else {
                                                        mcqNum++;
                                                        String questionInsert, opt1, opt2, opt3, opt4;
                                                        int correctAnswer ;
                                                        boolean next;
                                                        do {

                                                            System.out.print("Enter Question: ");
                                                            questionInsert = input.next();
                                                            System.out.print("Enter option 1: ");
                                                            opt1 = input.next();
                                                            System.out.print("Enter option 2: ");
                                                            opt2 = input.next();
                                                            System.out.print("Enter option 3: ");
                                                            opt3 = input.next();
                                                            System.out.print("Enter option 4: ");
                                                            opt4 = input.next();
                                                            System.out.print("Enter Correct Option Number(Integer):");

                                                            correctAnswer = input.nextInt();
                                                            try {
                                                                if (correctAnswer > 4){
                                                                    throw new AhsanExceptions("Invalid Option");
                                                                }else if (correctAnswer <=0 ){
                                                                    throw new AhsanExceptions("Invalid Option");
                                                                }

                                                                teacher.insert(mcqNum, questionInsert, opt1 , opt2 , opt3, opt4 , correctAnswer , false);

                                                                System.out.println("Enter True to add Next Question");
                                                                System.out.println("Enter False to GOBack");
                                                                next = input.nextBoolean();

                                                            }catch (InputMismatchException e){
                                                                System.out.println("Input Mismatch");
                                                                input.nextLine();
                                                                next = false;
                                                            }catch (AhsanExceptions e){
                                                                next = false;
                                                                System.out.println(e.getMessage());
                                                            }


                                                        } while(next);
                                                    }
                                                }catch (AhsanExceptions e){
                                                    System.out.println(e.getMessage());
                                                }

                                                }catch (AhsanExceptions e){
                                                    System.out.println(e.getMessage());
                                                }
//                                                break;
                                            }
                                            case 7 -> flag2 = false;
                                            default -> //                                                break;
                                                    System.out.print("Invalid!");

                                        }
                                    }catch (SQLException e){
                                        System.out.println("DataBase Error: " + e.getMessage());
                                    }catch (InputMismatchException  e){
                                        input.nextLine();
                                        System.out.println("Please Enter a Valid Key Input Mismatch");
                                    }catch (AhsanExceptions e){
                                        System.out.println(e.getMessage());
                                    }


                                }
                            }catch (SQLException e){
                                System.out.println("Error : " + e.getMessage());
                            }catch (InputMismatchException e){
                                System.out.println("Input Mismatch Error");
                            }catch (AhsanExceptions e){
                                System.out.println("Exception : " + e.getMessage());
                            }catch(Exception e){
                                System.out.println(e.getMessage());
                            }

                        }catch (InputMismatchException e){
                            input.nextLine();
                            System.out.println("Input Mismatch Error " );
                        }catch (AhsanExceptions e ){
                            System.out.println("Error : " + e.getMessage());
//                            break;
                        }
                    }else{
                        System.out.println("Password or UserName Incorrect!");
                    }

//                    break;

                }
                case 2 -> {

                    boolean flag2 = true;
                    int roll_number;
                    System.out.println("Student Mode\n\n");
                    try {
                    System.out.print("Enter Name: ");
                    name = input.next();
                        if(name.length() < 3){
                            throw new AhsanExceptions("Invalid Name");
                        } else if (name.length() > 30) {
                            throw new AhsanExceptions("Name is too long and Invalid");
                        }

                        if(name.equals("0")){
                            throw new AhsanExceptions("You Enter Zero Going Back");
                        } else if (checkNumbers(name)) {
                            throw new AhsanExceptions("Your Name contain Special Characters which is not Valid!");
                        }
                    System.out.print("Enter Age: ");
                    age = input.nextInt();
                    if(age < 10){
                        throw new AhsanExceptions("Invalid age");
                    } else if (age > 25) {
                        throw new AhsanExceptions("Age is above then 25");
                    }



                        System.out.print("Enter Roll Number: ");
                    roll_number = input.nextInt();

                    if (roll_number <= 0){
                        throw new AhsanExceptions("Roll Number Invalid");
                    }

                    Student student = new Student(name, age, roll_number);

                    while (flag2) {
                        System.out.println("1. AttemptQuiz");
                        System.out.println("2. Show Result");
                        System.out.println("3. Exit");
                        caseNo = input.nextInt();

                        switch (caseNo) {
                            case 1 -> //                                break;
                                    student.attempt_Q();
                            case 2 -> student.result();
                            case 3 -> //                                break;
                                    flag2 = false;
                            default -> //                                break;
                                    System.out.println("Invalid");
                        }

                    }
                    }catch (InputMismatchException e){
                        input.nextLine();
                        System.out.println("Input Mismatch Error");
                    }catch (AhsanExceptions e){
                        System.out.println("Exception: " + e.getMessage());
                    }
//                    break;
                }

                case 3 -> {

                    int i = 0, j = 0;
                    try {
                        Student student = new Student();
                        array = student.getList();
                        if (array.isEmpty()){
                            throw new AhsanExceptions("Row is Empty!");
                        }
                        Collections.sort(array, new Comparator<Person>() {
                            @Override
                            public int compare(Person s1, Person s2) {
                                return String.valueOf(s1.name).compareTo(s2.name);
                            }
                        });
                        for (Person a : array) {
                            a.display();
                            System.out.println("-------------------------");
                            System.out.println("-------------------------");
                            i++;
                        }
                        Integer[] highest_age = new Integer[i];
                        for (Person a : array) {
                            highest_age[j] = a.age;
                        }
                        System.out.println("-------------------------");
                        System.out.print("Highest age in all Students: ");
                        maxArray(highest_age);
                    }
                    catch (IndexOutOfBoundsException e){
                        System.out.println(e.getMessage());
                    }catch (AhsanExceptions e){
                        System.out.println(e.getMessage());
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }


//                    break;
                }
                case 4 -> //                    break;
                        flag = false;
                default -> //                    break;
                        System.out.println("Not Valid Key");

            }
        }

    }

}
