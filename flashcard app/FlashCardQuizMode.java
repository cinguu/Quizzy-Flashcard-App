import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class FlashCardQuizMode{

    private JTextArea display;
    private ArrayList<FlashCard> cardList;
    private Iterator<FlashCard> cardIterator;
    private FlashCard currentCard;
    private int currentIndex;
    private JFrame frame;
    private boolean isShowAnswer;
    private JButton nextButton;
    private JButton backButton;
    private JLabel title;
    private JLabel counterLabel;

    public FlashCardQuizMode(){

        //frame setup
        frame = new JFrame ("QUIZZY");
        JPanel mainPanel = new JPanel (new BorderLayout());
        mainPanel.setBackground(Color.decode("#ede2d9"));
        Font mainFont = new Font("Comic Sans MS", Font.PLAIN, 20);
        frame.setSize(800, 600);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        display = new JTextArea(10,20);
        display.setFont(mainFont);
        
        JScrollPane qJScrollPane = new JScrollPane(display);
        qJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        mainPanel.add(qJScrollPane, BorderLayout.CENTER);
        
        JScrollPane aJScrollPane = new JScrollPane(display);
        aJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
        aJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        //next and back button 
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomButtonPanel.setBackground(Color.decode("#ede2d9"));
        backButton = new JButton("Back");
        backButton.addActionListener(new BackCardListener());
        bottomButtonPanel.add(backButton);
        backButton.setEnabled(false);
        mainPanel.add(bottomButtonPanel, BorderLayout.SOUTH);
        
        nextButton = new JButton("Next");
        mainPanel.add(qJScrollPane);
        mainPanel.add(aJScrollPane);
        bottomButtonPanel.add(nextButton);
        nextButton.addActionListener(new NextCardListener());
        mainPanel.add(bottomButtonPanel, BorderLayout.SOUTH);
        bottomButtonPanel.setPreferredSize(new Dimension(0, 50));  

        //start over and shuffle button
        JPanel sideButtonPanel = new JPanel();
        sideButtonPanel.setLayout(new BoxLayout(sideButtonPanel, BoxLayout.Y_AXIS));        
        sideButtonPanel.setBackground(Color.decode("#ede2d9"));
        JButton startButton = new JButton("Start Over");
        startButton.addActionListener(new StartOverListener());
        
        JButton shuffleButton = new JButton ("Shuffle");
        shuffleButton.addActionListener(new ShuffleListener());
        
        sideButtonPanel.add(startButton);
        sideButtonPanel.add(Box.createVerticalStrut(10));  
        sideButtonPanel.add(shuffleButton);
        mainPanel.add(sideButtonPanel, BorderLayout.EAST);
        
        // Top and west side panels to make jtextarea centered
        JPanel leftPanel = new JPanel(); 
        JPanel topPanel = new JPanel();  
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); 
        leftPanel.setPreferredSize(new Dimension(100, 0));  
        topPanel.setPreferredSize(new Dimension(0, 200));  
        topPanel.setBackground(Color.decode("#ede2d9"));
        leftPanel.setBackground(Color.decode("#ede2d9"));

        mainPanel.add(leftPanel, BorderLayout.WEST);  
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Quizzy logo
        ImageIcon logoIcon = new ImageIcon("quizmodelogo.png"); 
        Image logoImage = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); 
        logoIcon = new ImageIcon(logoImage); 
                
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); 
        topPanel.add(logoLabel); 

        // Title of flashcard set
        title = new JLabel("Flashcard Set: ");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT); 

        // Set the foreground color to brown
        Color brownColor = Color.decode("#715848"); // Using hex code for brown
        title.setForeground(brownColor); // Set question label color
        topPanel.add(title);       
        
        //flip card with the mouse click
        display.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                flipCard(); 
            }
        });
        
        //flashcard counter
        counterLabel = new JLabel("", JLabel.LEFT);
        counterLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        bottomButtonPanel.add(counterLabel);
        
        
        //menu bar components
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load a set");
        loadMenuItem.addActionListener(new openMenuListener());
        menuBar.add(fileMenu);
        
        JMenuItem homeMenuItem = new JMenuItem("Home");
        homeMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                frame.dispose();
                new HomePage();
            }
        });
        fileMenu.add(homeMenuItem);
        fileMenu.add(loadMenuItem);
        
        
        
        //frame components
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
        frame.setSize(800,600);
        frame.setVisible(true); 
    }
    
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run(){
                new FlashCardQuizMode();
            }
        });
    }
    
    
    
    //listeners
    private void loadFile(File selectedFile) {
        cardList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
            String line;
            
            while ((line = reader.readLine()) != null) {
                makeCard(line);
            }
            
            reader.close();
        } catch (Exception e) {
            System.out.println("Unable to read the card file");
            e.printStackTrace();
        }
        
        currentIndex = 0;
        showCard();
        // Update the title with the name of the selected file
        title.setText("Flashcard Set: " + selectedFile.getName());
    }
    
    private void showCard() {
        if (currentIndex >= 0 && currentIndex < cardList.size()) {
            FlashCard currentCard = cardList.get(currentIndex);
            display.setText(isShowAnswer ? currentCard.getAnswer() : currentCard.getQuestion());
            backButton.setEnabled(currentIndex > 0); 
            nextButton.setEnabled(currentIndex < cardList.size() - 1); 
            
            counterLabel.setText("Card " + (currentIndex + 1) + " of " + cardList.size());
        }
    }    
    
    
    public class BackCardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentIndex > 0) {
                currentIndex--;
                isShowAnswer = false; 
                showCard();
            }
        }
    }
    
    public class NextCardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isShowAnswer) {
                // If currently showing the question, flip the card to show the answer
                flipCard();
            } else {
                // Move to the next card if available
                if (currentIndex < cardList.size() - 1) {
                    currentIndex++;
                    isShowAnswer = false;
                    showCard();
                } else {
                    display.setText("You have reached the end of the deck.");
                    nextButton.setEnabled(false); 
                    backButton.setEnabled(true); 
                }
            }
        }
    }

    class openMenuListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.showOpenDialog(frame);
            loadFile(fileOpen.getSelectedFile());
        }
    }
    
    public class StartOverListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentIndex = 0;
            isShowAnswer = false;
            showCard();
            nextButton.setEnabled(true); 
            backButton.setEnabled(false); 
        }
    }
    
    public class ShuffleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Collections.shuffle(cardList);
            currentIndex = 0; 
            isShowAnswer = false; 
            showCard(); 
            nextButton.setEnabled(true); 
            backButton.setEnabled(false);
        }
    }
    
    private void showNextCard() {
        if (cardIterator.hasNext()) {
            currentCard = cardIterator.next(); 
            display.setText(currentCard.getQuestion());
            isShowAnswer = false;
            currentIndex++; 
            backButton.setEnabled(currentIndex > 0); 
        } else {
            display.setText("You have reached the end of the deck.");
            nextButton.setEnabled(false); 
        }
    }
    
    private void flipCard() {
        isShowAnswer = !isShowAnswer; // Toggle between showing the question and answer
        showCard();
    }
    
    private void makeCard(String lineToParse) {
        String[] phrase = lineToParse.split("//", 2); // Split into question and answer
        String question = phrase[0].trim(); // Get the question
        String answer = (phrase.length > 1) ? phrase[1].trim() : ""; // Get the answer, or set to empty if not present
        FlashCard card = new FlashCard(question, answer);
        cardList.add(card);
        System.out.println("Card created: " + question + " - " + answer);
    }
    



}