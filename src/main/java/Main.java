import view.GamePanel;
import view.MenuPanel;

import javax.swing.*;
import java.awt.*;
// Hlavní třída aplikace, která spouští hru

public class Main {

  public static void main(String[] args) {

    JFrame window = new JFrame("Mytická zvířátka");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);

    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);

    GamePanel gamePanel = new GamePanel();
    MenuPanel menuPanel = new MenuPanel(cardLayout, mainPanel, gamePanel);

    mainPanel.add(menuPanel, "menu");
    mainPanel.add(gamePanel, "game");

    window.add(mainPanel);
    window.pack();
    window.setLocationRelativeTo(null);
    window.setVisible(true);


  }
}
