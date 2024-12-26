import java.io.IOException;

import javax.swing.JOptionPane;

import java.io.BufferedReader;
import java.io.FileReader;

public class LoadFile {
    // question pool with the loaded question
    public static QuestionPool loadQuestion(String fileName) {
        QuestionPool questionsPool = new QuestionPool();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Check the line that start with question
                if (!line.startsWith("Question:")) {
                    throw new IOException("Invalid question format: " + line);

                }

                // assign each line of the question
                String questionTopic = line.split(":")[1].trim();
                String questionText = br.readLine().trim();
                String[] questionOptions = new String[4];
                for (int i = 0; i < 4; i++) {
                    questionOptions[i] = br.readLine().trim();
                }

                // get the correct option
                int correctQuestionOption = Integer.parseInt(br.readLine().split(":")[1].trim());
                String questionDifficulty = br.readLine().trim();
                if (!questionDifficulty.equals("EASY") && !questionDifficulty.equals("MEDIUM")
                        && !questionDifficulty.equals("HARD")) {
                    throw new IOException("Invalid Difficulty level: " + questionDifficulty);
                }
                // Add the question to the pool
                questionsPool.addQuestion(new Question(questionTopic, questionText, questionOptions,
                        correctQuestionOption, questionDifficulty));

                // Check if the next line is blank
                line = br.readLine();

                if (line != null && !line.trim().isEmpty()) {
                    throw new IOException("Blank line not found: " + line);

                }

            }

        } catch (IOException e) {
            // Error message
            JOptionPane.showMessageDialog(null, "The file does not exist", null, JOptionPane.ERROR_MESSAGE);

        }

        return questionsPool;
    }
}