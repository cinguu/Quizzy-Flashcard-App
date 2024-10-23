
// import java.awt.*;
// import java.awt.event.*;
// import java.io.*;
// import java.util.ArrayList;
// import javax.swing.*;

// public class FlashCardMaker {

//     private JTextArea display;
//     private JTextArea question;
//     private JTextArea answer;
//     private ArrayList<FlashCard> cardList;
//     private JFrame frame;
//     private FlashCard currentCard;
//     private boolean isShowAnswer;
//     private JButton nextButton;
//     private JButton backButton;
//     private int currentIndex = 0; // Tracks the current card index
//     private JLabel counterLabel;

//     public FlashCardMaker() {
//         // Initial setup
//         cardList = new ArrayList<>();
//         // Add an empty card as a placeholder
//         cardList.add(new FlashCard("", ""));
//         currentIndex = 0;
//         isShowAnswer = false;
//         counterLabel = new JLabel();
//         updateCounter();

//         // User interface
//         frame = new JFrame("QUIZZY");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         // Create a JPanel to hold everything
//         JPanel mainPanel = new JPanel(new GridBagLayout());
//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.insets = new Insets(10, 10, 10, 10);

//         // Create font
//         Font mainFont = new Font("Helvetica Neue", Font.PLAIN, 18);
//         mainPanel.setBackground(Color.decode("#ede2d9"));
//         Font labelFont = new Font("Comic Sans MS", Font.BOLD, 20);
        
//         answer = new JTextArea(6, 20);
//         answer.setLineWrap(true);
//         answer.setWrapStyleWord(true);
//         answer.setFont(mainFont);

//         question = new JTextArea(6, 20);
//         question.setLineWrap(true);
//         question.setWrapStyleWord(true);
//         question.setFont(mainFont);

//         // JScrollPane
//         JScrollPane aScrollPane = new JScrollPane(answer);
//         aScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//         aScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

//         JScrollPane qScrollPane = new JScrollPane(question);
//         qScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//         qScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

//         // Buttons
//         nextButton = new JButton("Next");
//         backButton = new JButton("Back");
//         JButton deleteButton = new JButton("Delete");

//         // Menu bar components
//         JMenuBar menuBar = new JMenuBar();
//         JMenu fileMenu = new JMenu("File");
//         JMenuItem newMenuItem = new JMenuItem("New");
//         JMenuItem saveMenuItem = new JMenuItem("Save");
//         JMenuItem homeMenuItem = new JMenuItem("Home");
//         fileMenu.add(homeMenuItem);
//         fileMenu.add(newMenuItem);
//         fileMenu.add(saveMenuItem);
//         menuBar.add(fileMenu);

//         // Event listeners for the buttons
//         nextButton.addActionListener(new NextCardListener());
//         newMenuItem.addActionListener(new NewMenuListener());
//         saveMenuItem.addActionListener(new SaveMenuListener());
//         homeMenuItem.addActionListener(new HomeMenuListener());
//         backButton.addActionListener(new BackCardListener());

//         frame.setJMenuBar(menuBar);

//         JLabel qJLabel = new JLabel("Question:");
//         JLabel aJLabel = new JLabel("Answer:");
//         qJLabel.setFont(labelFont);
//         aJLabel.setFont(labelFont);

//         // Listeners
//         question.addFocusListener(new FocusAdapter() {
//             @Override
//             public void focusLost(FocusEvent e) {
//                 saveCurrentCard();
//             }
//         });

//         answer.addFocusListener(new FocusAdapter() {
//             @Override
//             public void focusLost(FocusEvent e) {
//                 saveCurrentCard();
//             }
//         });

//         deleteButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 deleteCurrentCard();
//             }
//         });

//         // Grid components
//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         gbc.gridwidth = 2;
//         gbc.anchor = GridBagConstraints.CENTER;
//         mainPanel.add(qJLabel, gbc);

//         gbc.gridy = 1;
//         gbc.weightx = 1;
//         gbc.weighty = 1;
//         gbc.fill = GridBagConstraints.BOTH;
//         mainPanel.add(qScrollPane, gbc);

//         gbc.gridy = 2;
//         gbc.weightx = 0;
//         gbc.weighty = 0;
//         gbc.fill = GridBagConstraints.NONE;
//         mainPanel.add(aJLabel, gbc);

//         gbc.gridy = 3;
//         gbc.weightx = 1;
//         gbc.weighty = 1;
//         gbc.fill = GridBagConstraints.BOTH;
//         mainPanel.add(aScrollPane, gbc);

//         gbc.gridx = 1;
//         gbc.gridy = 5;
//         gbc.weightx = .5;
//         gbc.weighty = 1;
//         gbc.fill = GridBagConstraints.NONE;
//         gbc.anchor = GridBagConstraints.LINE_END;
//         mainPanel.add(nextButton, gbc);

//         gbc.gridx = 0;
//         gbc.anchor = GridBagConstraints.LINE_START;
//         mainPanel.add(backButton, gbc);

//         gbc.gridx = 1;
//         gbc.gridy = 4;  // Adjust this depending on your layout
//         mainPanel.add(counterLabel, gbc);

//         gbc.gridx = 2;
//         gbc.gridy = 5;
//         gbc.weightx = .5;
//         gbc.weighty = 1;
//         gbc.fill = GridBagConstraints.NONE;
//         gbc.anchor = GridBagConstraints.LINE_END;
//         mainPanel.add(deleteButton, gbc);

//         // Frame components
//         frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
//         frame.setSize(500, 600);
//         frame.setVisible(true);

//         // Show the first card on startup
//         showCard();
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(new Runnable() {
//             @Override
//             public void run() {
//                 new FlashCardMaker();
//             }
//         });
//     }
    
//     class ImagePanel extends JPanel {
//         private Image backgroundImage;
    
//         public ImagePanel(String imagePath) {
//             // Load the image
//             backgroundImage = new ImageIcon(imagePath).getImage();
//         }
    
//         @Override
//         protected void paintComponent(Graphics g) {
//             super.paintComponent(g);
//             // Draw the background image
//             g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
//         }
//     }
    

//     // ActionListener for the Next/Add button
//     public class NextCardListener implements ActionListener {
//         @Override
//         public void actionPerformed(ActionEvent e) {
//             // Save the current card data if it exists
//             if (currentIndex < cardList.size()) {
//                 cardList.get(currentIndex).setQuestion(question.getText());
//                 cardList.get(currentIndex).setAnswer(answer.getText());
//             }
    
//             // Check if we are at the last card or if cardList is empty
//             if (currentIndex == cardList.size() - 1 || cardList.isEmpty()) {
//                 // Create a new card with empty fields
//                 FlashCard newCard = new FlashCard("", ""); // Initialize with empty strings
//                 cardList.add(newCard); // Add the new card
//                 currentIndex = cardList.size() - 1; // Update index to point to the new card
//             } else {
//                 // Move to the next card
//                 currentIndex++;
//             }
    
//             // Show the new or updated card's question and answer
//             showCard();
//             backButton.setEnabled(currentIndex > 0); // Enable back button if not at the first card
//             updateCounter(); // Update the counter label
//         }
//     }
    
//     // ActionListener for the Back button
//     public class BackCardListener implements ActionListener {
//         @Override
//         public void actionPerformed(ActionEvent e) {
//             // Save the current card data before navigating
//             if (currentIndex < cardList.size()) {
//                 cardList.get(currentIndex).setQuestion(question.getText());
//                 cardList.get(currentIndex).setAnswer(answer.getText());
//             }
    
//             // Move to the previous card only if the currentIndex is greater than 0
//             if (currentIndex > 0) {
//                 currentIndex--;
//             }
    
//             // Ensure we show the card at the new currentIndex
//             showCard();
//             updateCounter();
    
//             // Enable/disable buttons accordingly
//             backButton.setEnabled(currentIndex > 0); // Disable only if you're at the first card
//             nextButton.setEnabled(currentIndex < cardList.size()); // Enable next if there are more cards
//         }
//     }

//     private void showCard() {
//         // Check if the current index is valid
//         if (currentIndex < cardList.size() && currentIndex >= 0) {
//             FlashCard currentCard = cardList.get(currentIndex);
//             question.setText(currentCard.getQuestion());
//             answer.setText(currentCard.getAnswer());
//         } else {
//             // If no valid card exists, clear fields
//             question.setText("");
//             answer.setText("");
//         }
//         updateCounter(); // Update the counter label
//     }
    
    
//     public class NewMenuListener implements ActionListener {
//         @Override
//         public void actionPerformed(ActionEvent e) {
//             cardList.clear(); // Clear the current list
//             cardList.add(new FlashCard("", "")); // Reset with an empty card
//             currentIndex = 0; // Reset index
//             showCard(); // Show the new card
//             updateCounter();
//         }
//     }

//     public class SaveMenuListener implements ActionListener {
//         @Override
//         public void actionPerformed(ActionEvent e) {
//             saveCurrentCard(); // Save the current card before saving to file
//             JFileChooser fileSave = new JFileChooser();
//             fileSave.showSaveDialog(frame);
//             saveFile(fileSave.getSelectedFile());
//         }
//     }

//     public class HomeMenuListener implements ActionListener {
//         @Override
//         public void actionPerformed(ActionEvent e) {
//             frame.dispose();
//             new HomePage(); // Open a new instance
//         }
//     }

//     // Method to update the counter label
//     private void updateCounter() {
//         if (cardList.size() > 0) {
//             counterLabel.setText("Card " + (currentIndex + 1) + " of " + cardList.size());
//         } else {
//             counterLabel.setText("No cards available");
//         }
//     }

//     // Save the current card details to the cardList
//     private void saveCurrentCard() {
//         if (currentIndex >= 0 && currentIndex < cardList.size()) {
//             FlashCard card = cardList.get(currentIndex);
//             card.setQuestion(question.getText());
//             card.setAnswer(answer.getText());
//         }
//     }

//     // Clear question and answer fields for new card creation
//     private void clearCard() {
//         question.setText("");
//         answer.setText("");
//     }

//     // Method to delete the current card
//     private void deleteCurrentCard() {
//         if (currentIndex >= 0 && currentIndex < cardList.size()) {
//             cardList.remove(currentIndex);
//             // Reset index if the list becomes empty
//             if (cardList.isEmpty()) {
//                 cardList.add(new FlashCard("", ""));
//                 currentIndex = 0;
//             } else {
//                 currentIndex = Math.max(0, currentIndex - 1); // Move back if possible
//             }
//             showCard();
//             updateCounter();
//         }
//     }

//     // Method to save the card list to a file
//     private void saveFile(File file) {
//         try {
//             BufferedWriter writer = new BufferedWriter(new FileWriter(file));
//             for (FlashCard card : cardList) {
//                 writer.write(card.getQuestion() + "//");
//                 writer.write(card.getAnswer() + "\n");
//             }
//             writer.close();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

// }

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class FlashCardMaker {

    private JTextArea display;
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<FlashCard> cardList;
    private JFrame frame;
    private FlashCard currentCard;
    private boolean isShowAnswer;
    private JButton nextButton;
    private JButton backButton;
    private int currentIndex = 0; // Tracks the current card index
    private JLabel counterLabel;

    // Path to your background image
    private static final String BACKGROUND_IMAGE_PATH = "bearbg.png";

    public FlashCardMaker() {
        // Initial setup
        cardList = new ArrayList<>();
        // Add an empty card as a placeholder
        cardList.add(new FlashCard("", ""));
        currentIndex = 0;
        isShowAnswer = false;
        counterLabel = new JLabel();
        updateCounter();

        // User interface
        frame = new JFrame("QUIZZY");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an ImagePanel for the background
        ImagePanel mainPanel = new ImagePanel(BACKGROUND_IMAGE_PATH);
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create font
        Font mainFont = new Font("Helvetica Neue", Font.PLAIN, 18);
        mainPanel.setBackground(Color.decode("#ede2d9")); // This won't be visible due to the image
        Font labelFont = new Font("Comic Sans MS", Font.BOLD, 20);
        
        answer = new JTextArea(6, 20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(mainFont);

        question = new JTextArea(6, 20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(mainFont);

        // JScrollPane
        JScrollPane aScrollPane = new JScrollPane(answer);
        aScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        JScrollPane qScrollPane = new JScrollPane(question);
        qScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Buttons
        nextButton = new JButton("Next");
        backButton = new JButton("Back");
        JButton deleteButton = new JButton("Delete");
        

        // Menu bar components
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem homeMenuItem = new JMenuItem("Home");
        fileMenu.add(homeMenuItem);
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);

        // Event listeners for the buttons
        nextButton.addActionListener(new NextCardListener());
        newMenuItem.addActionListener(new NewMenuListener());
        saveMenuItem.addActionListener(new SaveMenuListener());
        homeMenuItem.addActionListener(new HomeMenuListener());
        backButton.addActionListener(new BackCardListener());

        frame.setJMenuBar(menuBar);

        JLabel qJLabel = new JLabel("Question:");
        JLabel aJLabel = new JLabel("Answer:");
        qJLabel.setFont(labelFont);
        aJLabel.setFont(labelFont);
  
        // Set the foreground color to brown
        Color brownColor = Color.decode("#715848"); // Using hex code for brown
        qJLabel.setForeground(brownColor); // Set question label color
        aJLabel.setForeground(brownColor); // Set answer label color

        // Listeners
        question.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                saveCurrentCard();
            }
        });

        answer.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                saveCurrentCard();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCurrentCard();
            }
        });

        // Grid components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(qJLabel, gbc);

        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(qScrollPane, gbc);

        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(aJLabel, gbc);

        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(aScrollPane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = .5;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(nextButton, gbc);

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(backButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;  // Adjust this depending on your layout
        mainPanel.add(counterLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.weightx = .5;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(deleteButton, gbc);

        // Frame components
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(900, 600);
        frame.setResizable(true); // Allow the window to be resizable
        frame.setVisible(true);

        // Show the first card on startup
        showCard();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlashCardMaker();
            }
        });
    }

    // Custom JPanel to display background image
    class ImagePanel extends JPanel {
        private Image backgroundImage;

        public ImagePanel(String imagePath) {
            // Load the image
            backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw the background image
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // ActionListener for the Next/Add button
    public class NextCardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Save the current card data if it exists
            if (currentIndex < cardList.size()) {
                cardList.get(currentIndex).setQuestion(question.getText());
                cardList.get(currentIndex).setAnswer(answer.getText());
            }

            // Check if we are at the last card or if cardList is empty
            if (currentIndex == cardList.size() - 1 || cardList.isEmpty()) {
                // Create a new card with empty fields
                FlashCard newCard = new FlashCard("", ""); // Initialize with empty strings
                cardList.add(newCard); // Add the new card
                currentIndex = cardList.size() - 1; // Update index to point to the new card
            } else {
                // Move to the next card
                currentIndex++;
            }

            // Show the new or updated card's question and answer
            showCard();
            backButton.setEnabled(currentIndex > 0); // Enable back button if not at the first card
            updateCounter(); // Update the counter label
        }
    }

    // ActionListener for the Back button
    public class BackCardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Save the current card data before navigating
            if (currentIndex < cardList.size()) {
                cardList.get(currentIndex).setQuestion(question.getText());
                cardList.get(currentIndex).setAnswer(answer.getText());
            }

            // Move to the previous card only if the currentIndex is greater than 0
            if (currentIndex > 0) {
                currentIndex--;
            }

            // Ensure we show the card at the new currentIndex
            showCard();
            updateCounter();

            // Enable/disable buttons accordingly
            backButton.setEnabled(currentIndex > 0); // Disable only if you're at the first card
            nextButton.setEnabled(currentIndex < cardList.size()); // Enable next if there are more cards
        }
    }

    private void showCard() {
        // Check if the current index is valid
        if (currentIndex < cardList.size() && currentIndex >= 0) {
            FlashCard currentCard = cardList.get(currentIndex);
            question.setText(currentCard.getQuestion());
            answer.setText(currentCard.getAnswer());
        } else {
            question.setText("");
            answer.setText("");
        }
    }

    private void saveCurrentCard() {
        // Save the current card data
        if (currentIndex < cardList.size()) {
            cardList.get(currentIndex).setQuestion(question.getText());
            cardList.get(currentIndex).setAnswer(answer.getText());
        }
    }

    private void deleteCurrentCard() {
        // Remove the current card from the list and update the index
        if (!cardList.isEmpty() && currentIndex >= 0) {
            cardList.remove(currentIndex);
            if (currentIndex >= cardList.size()) {
                currentIndex = cardList.size() - 1; // Move back to the last card if we delete the last one
            }
            showCard(); // Show the updated card
            updateCounter();
        }
    }

    private void updateCounter() {
        counterLabel.setText("Card: " + (currentIndex + 1) + "/" + cardList.size());
    }

    // Inner class for FlashCard
    class FlashCard {
        private String question;
        private String answer;

        public FlashCard(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }

    // Listeners for the menu actions
        public class NewMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardList.clear(); // Clear the current list
            cardList.add(new FlashCard("", "")); // Reset with an empty card
            currentIndex = 0; // Reset index
            showCard(); // Show the new card
            updateCounter();
        }
    }

    private class SaveMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveToFile();
        }
    }

    public class HomeMenuListener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    new HomePage(); // Open a new instance
                }
            }

    private void saveToFile() {
        // Save the current flashcards to a file
        try {
            FileOutputStream fos = new FileOutputStream("flashcards.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(cardList);
            oos.close();
            JOptionPane.showMessageDialog(frame, "Flashcards saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving flashcards: " + e.getMessage());
        }
    }
}
