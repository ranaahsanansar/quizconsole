package quizappone3;
import java.sql.*;
import java.sql.SQLException;
import static quizappone3.QuizAppOne3.lastTeacher;

/**
 *
 * @author Rana Ahsan Ansar
 */
public class Teacher extends QuestionDBImp {


    public Teacher(String name, int age) {
        super(name, age);
        mode = true;

    }

    private void insertTecherDB() {

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            Statement statement  = null;

            try {
                connection = CreatConnection.getConnection();
                statement = connection.createStatement();
                statement.execute("DELETE FROM teachers");
                preparedStatement = connection.prepareStatement("INSERT INTO `teachers` (`id`, `name`, `age`) VALUES (NULL, ?, ?)");
                preparedStatement.setString(1, this.name);
                preparedStatement.setInt(2, this.age);
                if (preparedStatement.execute()) {
                    System.out.println("Teacher Details Added Into DataBase");
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

            }


    }

    public void insert(int mcqNum, String questionInsert, String opt1, String opt2, String opt3, String opt4, int correctAnswer , boolean teacherInsertion) throws SQLException {

        if (teacherInsertion){
            insertTecherDB();
        }

            lastTeacher = this.name;
            Mcqs question = new Mcqs(mcqNum, questionInsert, opt1, opt2, opt3, opt4, correctAnswer);
            insertQuestion(question);


    }

    public void delete(int id) {
        System.out.println(lastTeacher);
        if (lastTeacher.equals(this.name) ) {

            System.out.println("Deleting Question of Id = " + id);
            String checkQur = "SELECT * FROM `questions` WHERE `id` = " + id ;
            if (effectedrows(checkQur) != 0) {
                deleteDB(id);
            } else {
                System.out.println("There is no Question with ID: " + id + " in DataBase");
            }

        }else {
            System.out.println("Authentication Error you are Not Allowed");
        }
    }

    public void update(int id , String question_s , String opt1 , String opt2 , String opt3 , String opt4, int correct_ans ) {
        System.out.println("Last modified by Teacher"+ lastTeacher);
        System.out.println("Your name : " + this.name);
        if (lastTeacher.equals(this.name) ) {

            Mcqs question = new Mcqs(id, question_s, opt1, opt2, opt3, opt4, correct_ans);

            update(question, id);

        } else {
            System.out.println("Authentication Error You are not allowed");
        }
    }

    @Override
    public void result() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = CreatConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `student`");

            while (resultSet.next()) {
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Roll Number: " + resultSet.getInt("roll_number"));
                System.out.println("Total: " + resultSet.getInt("total"));
                System.out.println("Obtained Marks: " + resultSet.getInt("obt_marks"));
                System.out.println("Percentage: " + resultSet.getInt("persentage") + "%");
                System.out.println("------------------------------------------");
                System.out.println("------------------------------------------");
                System.out.println("------------------------------------------");

            }

        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
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
    }

    public void emptyQuiz() throws AhsanExceptions {
        boolean check = deleteAll();
        if(!check){
            throw new AhsanExceptions("DB Problem");
        }

    }

    public void showAllQuestion() throws AhsanExceptions {
        if(!showAll())
        {
            throw new AhsanExceptions("No Question is DataBase");
        }
    }

    @Override
    public void display() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }


}
