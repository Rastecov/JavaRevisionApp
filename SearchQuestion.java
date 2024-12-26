import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SearchQuestion {
    // List of questions
    private ArrayList<Question> questions;

    // Constructor to get questions from the question pool
    public SearchQuestion(QuestionPool questionPool) {
        questions = questionPool.getQuestions();
        JFrame frame = new JFrame("Search Questions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        // Text field
        JTextField searchField = new JTextField(20);
        // button to search
        JButton searchQuestionButton = new JButton("Search Question");
        // Text area to display
        JTextArea resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        // Button action listener
        searchQuestionButton.addActionListener(e -> {
            String userSearch = searchField.getText();
            List<Question> searchQuestionResults = SearchQuestionMatching(userSearch);
            // Display search result
            if (!searchQuestionResults.isEmpty()) {
                StringBuilder results = new StringBuilder();
                for (Question question : searchQuestionResults) {
                    results.append("Topic: ").append(question.getQuestionTopic()).append("\n");
                    results.append("Question: ").append(question.getQuestionText()).append("\n\n");

                }
                resultArea.setText(results.toString());
            } else {
                resultArea.setText("No Result found ");
            }
        });

        // Pannels
        JPanel searchResultPanel = new JPanel();
        searchResultPanel.add(searchField);
        searchResultPanel.add(searchQuestionButton);
        JPanel resultPanel = new JPanel();
        resultPanel.add(new JScrollPane(resultArea));

        // set layout
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        // Add pannels to the frame
        frame.add(searchResultPanel);
        frame.add(resultPanel);
        frame.setVisible(true);

    }

    // Search for a question method
    private List<Question> SearchQuestionMatching(String userSearch) {
        List<Question> matchingQuestionsList = new ArrayList<>();

        for (Question question : questions) {
            if (question.getQuestionText().toLowerCase().contains(userSearch.toLowerCase())) {
                matchingQuestionsList.add(question);
            }
        }

        return matchingQuestionsList;
    }

}
