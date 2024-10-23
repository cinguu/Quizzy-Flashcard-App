import javax.swing.*;
import java.awt.*;

public class StyleSheet {

    // Define a method to style buttons
    public static JButton styleButton(JButton button) {
        Font mainFont = new Font("Comic Sans MS", Font.BOLD, 18); // Define the font
        button.setFont(mainFont);
        button.setBackground(Color.decode("#b28e76"));  // Light pink background
        button.setForeground(Color.decode("#FFFFFF"));  // white text
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.decode("#9d7e69"), 2)); // Pink border
        button.setFocusPainted(false); // Remove focus outline

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#9d7e69"));  // Darker pink on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#b28e76"));  // Revert to original color
            }
        });

        return button;
    }
}
