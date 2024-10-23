// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class HomePage {

//     private JFrame frame;

//     public HomePage() {
//         frame = new JFrame("QUIZZY");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setSize(700,500);

//         JPanel mainPanel = new JPanel();
//         mainPanel.setLayout(new GridBagLayout());
//         mainPanel.setBackground(Color.decode("#ffeff9"));
//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.insets = new Insets(10, 10, 10, 10);
//         gbc.fill = GridBagConstraints.HORIZONTAL;

//         // Add your logo to the home page (replace "logo.png" with the path to your image)
//         ImageIcon originalLogoIcon = new ImageIcon("quizzylogo.png"); // Ensure the image path is correct
//         Image originalImage = originalLogoIcon.getImage();
//         // Scale image to fit the frame width
//         Image scaledImage = originalImage.getScaledInstance(400, 200, Image.SCALE_SMOOTH); // Adjust size as needed
//         ImageIcon scaledLogoIcon = new ImageIcon(scaledImage);

//         JLabel logoLabel = new JLabel(scaledLogoIcon);
//         logoLabel.setHorizontalAlignment(JLabel.CENTER); // Center the image

//         // Add logo to the panel
//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         gbc.gridwidth = 2; // Span the logo across two columns
//         mainPanel.add(logoLabel, gbc);

//         Font mainFont = new Font("Helvetica Neue", Font.BOLD, 18);

//         // Create buttons for "Create New Set" and "Load Set"
//         JButton createSetButton = new JButton("Create New Set");
//         createSetButton.setFont(mainFont);
//         createSetButton.setBackground(Color.decode("#f6c6e4"));
//         createSetButton.setOpaque(true);
//         createSetButton.setBorderPainted(false);

//         JButton loadSetButton = new JButton("Load Set");
//         loadSetButton.setFont(mainFont);
//         loadSetButton.setBackground(Color.decode("#f6c6e4"));
//         loadSetButton.setOpaque(true);
//         loadSetButton.setBorderPainted(false);


//         // Add buttons to the panel
//         gbc.gridwidth = 1;
//         gbc.gridx = 0;
//         gbc.gridy = 1;
//         mainPanel.add(createSetButton, gbc);

//         gbc.gridy = 1;
//         gbc.gridx = 1;
//         mainPanel.add(loadSetButton, gbc);

//         // Add ActionListeners to buttons
//         createSetButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 // Redirect to FlashCardMaker
//                 frame.dispose(); // Close home page
//                 new FlashCardMaker(); // Open the FlashCardMaker window
//             }
//         });

//         loadSetButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 // Redirect to FlashCardQuizMode (to load and review a set)
//                 frame.dispose(); // Close home page
//                 new FlashCardQuizMode(); // Open the FlashCardQuizMode window
//             }
//         });

//         // Frame setup
//         frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
//         frame.setSize(700, 500);
//         frame.setVisible(true);
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(new Runnable() {
//             @Override
//             public void run() {
//                 new HomePage();
//             }
//         });
//     }
// }

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage {

    private JFrame frame;

    public HomePage() {
        frame = new JFrame("QUIZZY");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.decode("#ede2d9"));  // Light pink background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Add padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Ensures buttons fill horizontally

        // Add your logo to the home page (ensure the image path is correct)
        ImageIcon originalLogoIcon = new ImageIcon("quizzylogo3.png");
        Image originalImage = originalLogoIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(300, 350, Image.SCALE_SMOOTH); // Adjust size as needed
        ImageIcon scaledLogoIcon = new ImageIcon(scaledImage);

        JLabel logoLabel = new JLabel(scaledLogoIcon);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);  // Center the image

        // Add logo to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Span the logo across two columns
        mainPanel.add(logoLabel, gbc);

        // Create buttons for "Create New Set" and "Load Set"
        JButton createSetButton = new JButton("Create New Set");
        JButton loadSetButton = new JButton("Load Set");

        // Apply styles using the StyleSheet class
        StyleSheet.styleButton(createSetButton);
        StyleSheet.styleButton(loadSetButton);

        // Set bigger size for buttons
        createSetButton.setPreferredSize(new Dimension(200, 50));  // Increase width and height
        loadSetButton.setPreferredSize(new Dimension(200, 50));    // Adjust as needed

        // Add buttons to the panel
        gbc.gridwidth = 1;  // Reset grid width
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(createSetButton, gbc);

        gbc.gridx = 1;
        mainPanel.add(loadSetButton, gbc);

        // ActionListeners for buttons
        createSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redirect to FlashCardMaker
                frame.dispose();  // Close home page
                new FlashCardMaker();  // Open FlashCardMaker window
            }
        });

        loadSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redirect to FlashCardQuizMode (to load and review a set)
                frame.dispose();  // Close home page
                new FlashCardQuizMode();  // Open FlashCardQuizMode window
            }
        });

        // Frame setup
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomePage();
            }
        });
    }
}
