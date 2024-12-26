import java.util.ArrayList;

public class QuestionPool {
    // list of questions
    private ArrayList<Question> questionsList;

    // constructor to initialize the list of questions
    public QuestionPool() {
        questionsList = new ArrayList<>();

    }

    // add a question to the list
    public void addQuestion(Question question) {
        questionsList.add(question);
    }

    // getters
    public Question getQuestion(int index) {
        return questionsList.get(index);
    }

    public ArrayList<Question> getQuestions() {
        return questionsList;
    }

    // get the size of the list
    public int size() {
        return questionsList.size();
    }

}
