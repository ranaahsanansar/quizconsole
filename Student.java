package quizappone3;
import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import static quizappone3.QuizAppOne3.input;
import static quizappone3.QuizAppOne3.lastTeacher;

/**
 *
 * @author Rana Ahsan Ansar
 */
public class Student extends Person {

    int roll_number;
    int total;
    double percentage;
    int percentageInt;
    List<Mcqs> mcqArray = new ArrayList<>();
    private int score;

    Student() {

    }

    Student(String name, int age, int roll_num) {
        super(name, age);
        score = 0;
        total = 0;
        percentage = 0.0;
        percentageInt = 0;
        mode = false;
        this.roll_number = roll_num;

    }
//    -----------------------------------------------------------------------

    private void getMcqs() {

//        getting Array of Mcqs from DataBase
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = CreatConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `questions`");

            while (resultSet.next()) {
                Mcqs data = new Mcqs();
                data.setId(resultSet.getInt("id"));
                data.setQuestion(resultSet.getString("question"));

                data.setOpt1(resultSet.getString("opt1"));
                data.setOpt2(resultSet.getString("opt2"));
                data.setOpt3(resultSet.getString("opt3"));
                data.setOpt4(resultSet.getString("opt4"));

                data.setCorrect(resultSet.getInt("correct"));

                mcqArray.add(data);

            }
        }catch (SQLTimeoutException e){
            System.out.println(e.getMessage());
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getMessage());
//            e.printStackTrace();
        }
    }

    //    --------------------------------------------------------------
    @Override
    public void result() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = CreatConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM `student` WHERE roll_number = ? ");
            preparedStatement.setInt(1, this.roll_number);
            resultSet = preparedStatement.executeQuery();
//            System.out.println(resultSet + " and " + preparedStatement);

            while (resultSet.next()) {
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Roll Number: " + resultSet.getInt("roll_number"));
                System.out.println("Total: " + resultSet.getInt("total"));
                System.out.println("Obtained Marks: " + resultSet.getInt("obt_marks"));
                System.out.println("Persentage: " + resultSet.getInt("persentage") + "%");
                System.out.println("------------------------------------------");
                System.out.println("------------------------------------------");
                System.out.println("------------------------------------------");

            }

        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //    ------------------------------------------------
    private void insertStudent() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = CreatConnection.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO `student` (`id`, `roll_number`, `name`, `total`, `obt_marks`, `persentage`, `age`) VALUES (NULL, ?, ?, ?, ?, ? , ? )");

            preparedStatement.setInt(1, this.roll_number);
            preparedStatement.setString(2, this.name);
            preparedStatement.setInt(3, this.total);
            preparedStatement.setInt(4, this.score);
            preparedStatement.setInt(5, this.percentageInt);
            preparedStatement.setInt(6, this.age);
            if (preparedStatement.execute()) {
                System.out.println("Added Done");
            }

        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }
//-----------------------------------------------------------------

    public void attempt_Q() {
        
        getMcqs();
        try {
            if( mcqArray.isEmpty() ){
                throw new AhsanExceptions("No Question Found\n");
            }

            System.out.println("Welcome");
            System.out.println("This quiz is Created By: " + lastTeacher);
//        Put Clear Screen Syntax Here Later

            int answer;
            score = 0;
            total = 0;

            System.out.println("Quiz Starts Now");
            System.out.println("Enter 0 or any number to Skip\n\n");
            if (!mcqArray.isEmpty()) {
                for (Mcqs Q : mcqArray) {
                    total++;
                    System.out.println("Question No." + Q.getId() + ". " + Q.getQuestion() + "\n");

                    System.out.println("1. " + Q.getOpt1());
                    System.out.println("2. " + Q.getOpt2());
                    System.out.println("3. " + Q.getOpt3());
                    System.out.println("4. " + Q.getOpt4());
                    System.out.print("Enter your answer by 1, 2, 3, 4 according to Options given: ");

                    try {
                        answer = input.nextInt();
                        if (answer == Q.getCorrect()) {
                            score++;
                            System.out.println("Your answer is Saved");
                        } else if (answer < 5 && answer > 0) {
                            System.out.println("Your answer is Saved");
                        } else if (answer > 4 || answer == 0) {
                            System.out.println("Invalid option going to next question...");
                        }else if (answer < 0){
                            System.out.println("Invalid option you get Zero of Previous Question");
                        }

                    } catch (InputMismatchException e) {
                        input.nextLine();
                        System.out.println("Error: Input Mismatch ");
                    }catch (Exception e){
                        System.out.println("RunTimeError: " + e.getMessage());
                    }

//            -------------------------------------------------------------------
                }

                System.out.println("Total " + total);
                System.out.println("Your current Score: " + score);

                try {
                    percentageInt =  Integer.divideUnsigned(score*100, total);
                    System.out.println("**Percentage : " + percentageInt );
                }catch (ArithmeticException e){
                    System.out.println(e.getMessage());
                }
                insertStudent();
                mcqArray.removeAll(mcqArray);
            } else {
                System.out.println("There is No Question in Quiz");
            }



        }catch (AhsanExceptions e){
            System.out.println(e.getMessage());
        }



    }
//    ---------------------------------------------------------------------------------

    @Override
    public void display() {
        System.out.println("Name: " + name);
        System.out.println("Roll Number: " + roll_number);
        System.out.println("Persentage: " + percentageInt);
    }

    //    ---------------------------------------------------------------------------------

    public List<Person> getList() {
        List<Person> students = new ArrayList<>();
        String name_list;
        int roll_number_list, age_list, persentage_list;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = CreatConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `student`");

            while (resultSet.next()) {
                name_list = resultSet.getString("name");
                roll_number_list = resultSet.getInt("roll_number");
                persentage_list = resultSet.getInt("persentage");
                age_list = resultSet.getInt("age");
                Student getStudent = new Student(name_list, age_list, roll_number_list);
                getStudent.percentageInt = persentage_list;
                students.add(getStudent);

            }

        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("DataBase Connection Error!");
        }finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return students;
    }

}
