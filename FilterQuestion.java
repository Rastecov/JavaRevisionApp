import java.awt.BorderLayout;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FilterQuestion {
    private QuestionPool questionPool;
    private JTextArea resultArea;

    // Constructor

    public FilterQuestion(QuestionPool questionPool) {
        this.questionPool = questionPool;

        // Frame
        JFrame frame = new JFrame("Filter Questions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        // Dropdown filter for Topic and Difficulty
        JComboBox<String> difficultyDropdown = new JComboBox<>(new String[] { "All", "EASY", "MEDIUM", "HARD" });
        JComboBox<String> topicDropdown = new JComboBox<>(new String[] { "All", "Recursion", "ArrayList", "LinkedList",
                "Sorting_Algorithms", "Binary_Search", "Exception_Handling" });

        // Display the filter questions
        resultArea = new JTextArea(15, 50);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Button

        JButton filterButton = new JButton("Apply Filter");
        filterButton.addActionListener(e -> {
            String selecteDifficulty = (String) difficultyDropdown.getSelectedItem();
            String selectedTopic = (String) topicDropdown.getSelectedItem();
            filterQuestions(selecteDifficulty, selectedTopic);
        });

        // Pannel
        JPanel controlDropdownPanel = new JPanel();
        controlDropdownPanel.add(new JLabel("Difficulty"));
        controlDropdownPanel.add(difficultyDropdown);
        controlDropdownPanel.add(new JLabel("Topic"));
        controlDropdownPanel.add(topicDropdown);
        controlDropdownPanel.add(filterButton);

        // display the frame
        frame.setLayout(new BorderLayout());
        frame.add(controlDropdownPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);

    }

    private void filterQuestions(String difficulty, String topic) {
        List<Question> filteresQuestions = new ArrayList<>();

        for (Question question : questionPool.getQuestions()) {
            // check if the question match difficulty or topic
            boolean matchToDifficulty = "All".equalsIgnoreCase(difficulty)
                    || difficulty.trim().equalsIgnoreCase(question.getQuestionDifficulty().trim());
            boolean matchToTopic = "All".equalsIgnoreCase(topic)
                    || topic.trim().equalsIgnoreCase(question.getQuestionTopic().trim());

            if (matchToDifficulty && matchToTopic) {
                // Add the question that matched to the list
                filteresQuestions.add(question);
            }
        }

        // Display the filter question
        if (!filteresQuestions.isEmpty()) {
            StringBuilder results = new StringBuilder("Filtered Questions: \n");
            for (Question question : filteresQuestions) {
                results.append("Topic: ").append(question.getQuestionTopic()).append("\n");
                results.append("Difficulty: ").append(question.getQuestionDifficulty()).append("\n");
                results.append("Question: ").append(question.getQuestionText()).append("\n\n");

            }
            resultArea.setText(results.toString());

        } else {
            // message if there is no match
            resultArea.setText("No question matched the filter selected");

        }

    }

}
