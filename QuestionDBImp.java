package quizappone3;
import java.sql.*;

/**
 *
 * @author Rana Ahsan Ansar
 */
public abstract class QuestionDBImp extends Person implements Questions {
    QuestionDBImp(String name , int age)
    {
        super(name , age);
    }
    //    Insert New Question in Database ----------------------------------
    @Override
    public void insertQuestion(Mcqs question) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
//        ------------------------
//        INSERT INTO `questions` (`id`, `question`, `opt1`, `opt2`, `opt3`, `opt4`, `correct`) VALUES ('question.getId()', 'question.getQuestion()', 'question.getOpt1()', 'question.getOpt2()', 'question.getOpt3()', 'question.getOpt4()', 'question.getCorrect()');

//        System.out.println(querry);
        try {
            connection = CreatConnection.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO `questions` (`id`, `question`, `opt1`, `opt2`, `opt3`, `opt4`, `correct`) VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, question.getId());
            preparedStatement.setString(2, question.getQuestion());
            preparedStatement.setString(3, question.getOpt1());
            preparedStatement.setString(4, question.getOpt2());
            preparedStatement.setString(5, question.getOpt3());
            preparedStatement.setString(6, question.getOpt4());
            preparedStatement.setInt(7, question.getCorrect());
            preparedStatement.executeUpdate();
            System.out.println("Inserted");

        } catch (SQLException e) {
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
        }

    }

    @Override
    public void deleteDB(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String qur = "DELETE FROM `questions` WHERE `questions`.`id` = ?;";

        try {

            connection = CreatConnection.getConnection();
            preparedStatement = connection.prepareStatement(qur);
            preparedStatement.setInt(1, id);

            if (preparedStatement.executeUpdate() > 0 ) {
                System.out.println("Question Deleted!");
            }else{
                System.out.println("Invalid Question Number");
            }

        } catch (SQLException e) {
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
        }
    }

    @Override
    public void update(Mcqs question, int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = CreatConnection.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `questions` SET `question` = ? , `opt1` = ?, `opt2` = ?, `opt3` = ?, `opt4` = ?, `correct` = ? WHERE `questions`.`id` = ?");

            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setString(2, question.getOpt1());
            preparedStatement.setString(3, question.getOpt2());
            preparedStatement.setString(4, question.getOpt3());
            preparedStatement.setString(5, question.getOpt4());
            preparedStatement.setInt(6, question.getCorrect());
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
            System.out.println("Question Updated SuccesFully");

        } catch (SQLException e) {
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
        }
    }

    @Override
    public Mcqs selectById(int id) {
        Mcqs question = new Mcqs();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = CreatConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM `questions` WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                question.setId(resultSet.getInt("id"));
                question.setQuestion(resultSet.getString("question"));

                question.setOpt1(resultSet.getString("opt1"));
                question.setOpt2(resultSet.getString("opt2"));
                question.setOpt3(resultSet.getString("opt3"));
                question.setOpt4(resultSet.getString("opt4"));

                question.setCorrect(resultSet.getInt("correct"));

            }

        } catch (SQLException e) {
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

        return question;
    }

    protected boolean deleteAll() {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = CreatConnection.getConnection();
            statement = connection.createStatement();
            statement.execute("DELETE FROM questions");
            System.out.println("Old Quiz Cleared");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (statement != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected boolean showAll() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = CreatConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `questions`");

            while (resultSet.next()) {
                System.out.println("Question No#:: " + resultSet.getInt("id"));
                System.out.println("Question: " + resultSet.getString("question"));
                System.out.println("------------------------------------------");
                System.out.println("------------------------------------------");
                System.out.println("------------------------------------------");

            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }finally {
            if (statement != null) {
                try {
                    statement.close();
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

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int effectedrows(String qur){
        int count = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = CreatConnection.getConnection();
            statement = connection.createStatement();
//            resultSet = statement.executeQuery("SELECT * FROM `questions`");
            resultSet = statement.executeQuery(qur);

            while (resultSet.next()){
                count++;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            count = 0 ;
        }finally {
            if (statement != null) {
                try {
                    statement.close();
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

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }

    public static String getLastTeacher() throws SQLException{
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String teacherName = "Unknown";
//            try {
                connection = CreatConnection.getConnection();
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM `teachers`");
                while (resultSet.next()){
                    teacherName = resultSet.getString("name");
                }
//            }catch (SQLException e){
//                System.out.println("SQL Exception : "+e.getMessage());
//            }finally {
//                if (statement != null) {
//                    try {
//                        statement.close();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                if (resultSet != null) {
//                    try {
//                        resultSet.close();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                if (connection != null) {
//                    try {
//                        connection.close();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }



        return teacherName;
    }

}
