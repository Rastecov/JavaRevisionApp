public class Question {
    private String questionTopic;
    private String questionText;
    private String[] questionOptions;
    private int correctQuestionOption;
    private String questionDifficulty;

    // Constructor to initialize a question
    public Question(String questionTopic, String questionText, String[] questionOptions, int correctQuestionOption,
            String questionDifficulty) {
        this.questionTopic = questionTopic;
        this.questionText = questionText;
        this.questionOptions = questionOptions;
        this.correctQuestionOption = correctQuestionOption;
        this.questionDifficulty = questionDifficulty;
    }

    // getters
    public String getQuestionTopic() {
        return questionTopic;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getQuestionOptions() {
        return questionOptions;
    }

    public int getCorrectQuestionOption() {
        return correctQuestionOption;
    }

    public String getQuestionDifficulty() {
        return questionDifficulty;
    }

}
