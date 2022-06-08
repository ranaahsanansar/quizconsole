package quizappone3;
/**
 *
 * @author Rana Ahsan Ansar
 */
public class Mcqs {

    private int id;
    private String Question_s;
    private String opt1;
    private String opt2;
    private String opt3;
    private String opt4;
    private int correct;

    public Mcqs() {

    }

    public Mcqs(int id, String Question, String opt1, String opt2, String opt3, String opt4, int correct) {
        this.id = id;
        this.Question_s = Question;
        this.opt1 = opt1;
        this.opt2 = opt2;
        this.opt3 = opt3;
        this.opt4 = opt4;
        this.correct = correct;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
//    -------------------------------

    public String getQuestion() {
        return this.Question_s;
    }

    public void setQuestion(String Question) {

        this.Question_s = Question;
    }
//    ----------------------------------

    public String getOpt1() {
        return this.opt1;
    }

    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    //    ----------------------------------------
    public String getOpt2() {
        return this.opt2;
    }

    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }
//    -----------------------------------------------

    public String getOpt3() {
        return this.opt3;
    }

    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }
//    ----------------------------------------------

    public String getOpt4() {
        return this.opt4;
    }

    public void setOpt4(String opt4) {
        this.opt4 = opt4;
    }
//    --------------------------------------------

    public int getCorrect() {
        return this.correct;
    }

    public void setCorrect(int corr) {
        this.correct = corr;
    }

}
