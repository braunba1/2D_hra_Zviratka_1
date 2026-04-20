package view;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import data.DataStorage;
import view.GamePanel;

public class MenuPanel extends JPanel {

  CardLayout cardLayout;
  JPanel mainPanel;
  GamePanel gamePanel;

  Rectangle newGameButton = new Rectangle(284, 260, 200, 60);
  Rectangle loadGameButton = new Rectangle(284, 340, 200, 60);

  boolean newHover = false;
  boolean loadHover = false;

  public MenuPanel(CardLayout cardLayout, JPanel mainPanel, GamePanel gamePanel) {

    this.cardLayout = cardLayout;
    this.mainPanel = mainPanel;
    this.gamePanel = gamePanel;

    setPreferredSize(new Dimension(768, 576));

    addMouseMotionListener(new MouseAdapter() {
      public void mouseMoved(MouseEvent e) {

        newHover = newGameButton.contains(e.getPoint());
        loadHover = loadGameButton.contains(e.getPoint());

        repaint();
      }
    });

    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {

        if (newGameButton.contains(e.getPoint())) {
          startGame();
        }

        if (loadGameButton.contains(e.getPoint())) {
          loadGame();
        }

      }
    });
  }

  private void startGame() {

    cardLayout.show(mainPanel, "game");
    gamePanel.setUpObjects();
    gamePanel.setUpNPC();
    gamePanel.showFirstDialog();
    gamePanel.startGameThread();
    gamePanel.requestFocusInWindow();
  }

  private void loadGame() {


    try {
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
      DataStorage ds = (DataStorage) ois.readObject();
      gamePanel.player.worldX = ds.x;
      gamePanel.player.worldY = ds.y;
      gamePanel.questManager.rememberingQuest.completed = ds.isFireWomanCompleted;
      gamePanel.questManager.guessingQuest.completed = ds.isZoroCompleted;
      gamePanel.questManager.imageQuest.completed = ds.isBeaCompleted;
      gamePanel.player.movementHandler.questKeyCompleted = ds.isKeyQuestCompleted;
      gamePanel.player.animalNumber = ds.animalCount;
      gamePanel.player.movementHandler.hasKey = ds.hasKey;
      cardLayout.show(mainPanel, "game");
      gamePanel.setUpObjects();

      if (gamePanel.questManager.imageQuest.completed) {
        gamePanel.tileController.changeMap("maps/mapa_pozadi2.txt");}

      if (ds.chestOpened) {
        gamePanel.objects[1] = null;
      }
      if (ds.keyFound) {
        gamePanel.objects[2] = null;
      }

      gamePanel.setUpNPC();
      gamePanel.showWelcomeBackDialog();
      gamePanel.startGameThread();
      gamePanel.requestFocusInWindow();
      System.out.println("Key quest: " + ds.hasKey);

    } catch (Exception e) {


    }


    /*
    System.out.println("Loading save...");
    cardLayout.show(mainPanel,"game");
    gamePanel.setUpObjects();
    gamePanel.setUpNPC();
    gamePanel.startGameThread();
    gamePanel.requestFocusInWindow();*/

  }

  public void paintComponent(Graphics g) {

    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    // ANTIALIAS
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

    // 🌸 Gradient background
    GradientPaint gradient = new GradientPaint(
        0, 0, new Color(255, 182, 193),
        0, getHeight(), new Color(255, 105, 180));

    g2.setPaint(gradient);
    g2.fillRect(0, 0, getWidth(), getHeight());

    // 💗 TITLE
    g2.setFont(new Font("Arial", Font.BOLD, 60));

    String title = "MYTICKÁ ZVÍŘÁTKA";
    FontMetrics fm = g2.getFontMetrics();

    int x = (getWidth() - fm.stringWidth(title)) / 2;

    // text shadow
    g2.setColor(new Color(150, 0, 80));
    g2.drawString(title, x + 4, 150 + 4);

    g2.setColor(Color.WHITE);
    g2.drawString(title, x, 150);

    drawButton(g2, newGameButton, "NOVÁ HRA", newHover);
    drawButton(g2, loadGameButton, "NAČÍST HRU", loadHover);

    g2.dispose();
  }

  private void drawButton(Graphics2D g2, Rectangle button, String text, boolean hover) {

    if (hover) {
      g2.setColor(new Color(255, 130, 200));
    } else {
      g2.setColor(new Color(255, 80, 170));
    }

    g2.fillRoundRect(button.x, button.y, button.width, button.height, 30, 30);

    g2.setColor(Color.WHITE);
    g2.setFont(new Font("Arial", Font.BOLD, 22));

    FontMetrics fm = g2.getFontMetrics();

    int textX = button.x + (button.width - fm.stringWidth(text)) / 2;
    int textY = button.y + ((button.height - fm.getHeight()) / 2) + fm.getAscent();

    g2.drawString(text, textX, textY);
  }

}
