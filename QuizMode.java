
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.util.LinkedList;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class QuizMode {

    private static JFrame frame;
    private JLabel questionLabel;
    private JRadioButton[] questionOptions;
    private JButton nextQuestionButton, flagQuestionButton;
    private ButtonGroup questionOptionGroup;
    private JProgressBar questionProgressbar;
    private static LinkedList<Question> flaggedQuestions = new LinkedList<>();
    private int currentQuestionIndex = 0;
    private QuestionPool questionsPool;
    private int score = 0;

    public QuizMode() {

        try {

            // Load file
            questionsPool = LoadFile
                    .loadQuestion("C:\\Users\\erast\\Documents\\CS 401\\RevisionApplication\\src\\Questions.txt");

            System.out.println("loaded question " + questionsPool.size());
            if (questionsPool.size() == 0) {
                JOptionPane.showMessageDialog(null, "No question in the file ");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured" + e.getMessage());

            return;

        }

        // Frame
        frame = new JFrame("Quiz Mode");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Question Label and options

        questionLabel = new JLabel();
        questionOptions = new JRadioButton[4];
        questionOptionGroup = new ButtonGroup();
        JPanel questionOptionPanel = new JPanel(new GridLayout(4, 1));

        // Initialize options button and add to the option group
        for (int i = 0; i < 4; i++) {
            questionOptions[i] = new JRadioButton();
            questionOptionGroup.add(questionOptions[i]);
            questionOptionPanel.add(questionOptions[i]);
        }

        // progress bar

        questionProgressbar = new JProgressBar(0, questionsPool.size());
        questionProgressbar.setValue(0);

        // buttons of the QuizMode
        nextQuestionButton = new JButton("Next Question");
        flagQuestionButton = new JButton("Flagged Question");
        nextQuestionButton.addActionListener(e -> nextQuestion());
        flagQuestionButton.addActionListener(e -> flagQuestion());

        // Pannel
        JPanel questionButtonPanel = new JPanel();
        questionButtonPanel.add(nextQuestionButton);
        questionButtonPanel.add(flagQuestionButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(questionProgressbar, BorderLayout.NORTH);
        bottomPanel.add(questionButtonPanel, BorderLayout.SOUTH);

        // Layout

        frame.setLayout(new BorderLayout());
        frame.add(questionLabel, BorderLayout.NORTH);
        frame.add(questionOptionPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Load question
        loadQuestions();
        frame.setVisible(true);

    }

    // Load the current question and add completion message
    private void loadQuestions() {
        if (currentQuestionIndex >= questionsPool.size()) {
            JOptionPane.showMessageDialog(frame, "Quiz Complete! You scored: " + score);
            frame.dispose();
            return;

        }

        Question question = questionsPool.getQuestion(currentQuestionIndex);
        questionLabel.setText((currentQuestionIndex + 1) + "." + question.getQuestionText());
        String[] questionOptionsText = question.getQuestionOptions();
        for (int i = 0; i < questionOptionsText.length; i++) {
            questionOptions[i].setText(questionOptionsText[i]);
        }
        questionProgressbar.setValue(currentQuestionIndex + 1);

    }

    private void nextQuestion() {
        Question question = questionsPool.getQuestion(currentQuestionIndex);
        boolean isCorrect = false;

        // Check the selected option of the user if it is correct

        for (int i = 0; i < questionOptions.length; i++) {

            if (questionOptions[i].isSelected()) {

                if (i + 1 == question.getCorrectQuestionOption()) {
                    isCorrect = true;
                    score++;
                } else {
                    // Warning message
                    JOptionPane.showMessageDialog(frame,
                            "Incorrect answer, the correct one is: "
                                    + question.getQuestionOptions()[question.getCorrectQuestionOption() - 1],
                            "Wrong Answer", JOptionPane.WARNING_MESSAGE);
                }

                break;
            }
        }

        // If the question is incorrect and has not be flagged before, add it to the
        // list of flagged question
        if (!isCorrect && !flaggedQuestions.contains(question)) {
            flaggedQuestions.add(question);
        }

        currentQuestionIndex++;
        questionOptionGroup.clearSelection();
        loadQuestions();
    }

    // Flag the current question for review later
    private void flagQuestion() {
        Question currentQuestion = questionsPool.getQuestion(currentQuestionIndex);

        if (!flaggedQuestions.contains(currentQuestion)) {
            flaggedQuestions.add(currentQuestion);
            JOptionPane.showMessageDialog(frame, "Question Flagged to be Reviewed");
        } else {
            JOptionPane.showMessageDialog(frame, "This question is already flagged");
        }
    }

    // get the list of flagged questions
    public static LinkedList<Question> getFlaggedQuestions() {
        return flaggedQuestions;
    }

    // Display the flagged question

    public static void displayFlaggedQuestions() {
        if (flaggedQuestions.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No Question Flagged to be Reviewed");
            return;
        }

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        StringBuilder message = new StringBuilder("Flagged Questions: \n\n");
        for (Question question : flaggedQuestions) {
            message.append("Question: ").append(question.getQuestionText()).append("\n");
            message.append("Correct Answer: ")
                    .append(question.getQuestionOptions()[question.getCorrectQuestionOption() - 1]).append("\n\n");
        }

        textArea.setText(message.toString());

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 400));

        JOptionPane.showMessageDialog(frame, scrollPane, "Flagged Questions", JOptionPane.INFORMATION_MESSAGE);

    }

}