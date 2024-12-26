import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class Menu {
    private JFrame frame;
    QuestionPool questionsPool = null;

    public Menu() {
        try {
            // load from file
            questionsPool = LoadFile
                    .loadQuestion("C:\\Users\\erast\\Documents\\CS 401\\RevisionApplication\\src\\Questions.txt");

            if (questionsPool.size() == 0) {
                System.out.println("No question in the file");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "The file does not exist", null, JOptionPane.ERROR_MESSAGE);
            return;

        }

        // Application window dimension
        frame = new JFrame("Revision Application Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // button of the menu
        JButton startQuizButton = new JButton("Start Quiz");
        JButton viewFlaggedQuestion = new JButton("View Flagged Questions");
        JButton searchQuestionButton = new JButton("Search Questions");
        JButton sortQuestionButton = new JButton("Filter Questions");
        JButton exitAppButton = new JButton("Exit Application");

        // Action listenner to each button
        startQuizButton.addActionListener(e -> new QuizMode());
        viewFlaggedQuestion.addActionListener(e -> new ReviewMode());
        searchQuestionButton.addActionListener(e -> new SearchQuestion(questionsPool));

        sortQuestionButton.addActionListener(e -> new FilterQuestion(questionsPool));
        exitAppButton.addActionListener(e -> System.exit(0));

        // defining the button layout in the panel

        JPanel menuPanel = new JPanel(new GridLayout(5, 1));
        menuPanel.add(startQuizButton);
        menuPanel.add(viewFlaggedQuestion);
        menuPanel.add(searchQuestionButton);
        menuPanel.add(sortQuestionButton);
        menuPanel.add(exitAppButton);

        // add panel to the frame and display it
        frame.add(menuPanel);
        frame.setVisible(true);

    }

    public static void main(String[] args) throws Exception {

        // Display the Menu
        new Menu();
    }
}
