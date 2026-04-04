package UI;

import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class QuestUI {

  GamePanel gp;

  public boolean visible = false;
  public boolean accepted;

  Rectangle img1 = new Rectangle();
  Rectangle img2 = new Rectangle();
  Rectangle img3 = new Rectangle();

  BufferedImage[][] questions = new BufferedImage[3][3];

  Rectangle yesButton = new Rectangle(0, 0, 120, 40);
  Rectangle noButton = new Rectangle(0, 0, 120, 40);

  public int questionIndex = 0;

  public QuestUI(GamePanel gp) {
    this.gp = gp;

    try {

      questions[0][0] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("quest/sheep.jpg"));
      questions[0][1] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("quest/dog.png"));
      questions[0][2] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("quest/dinosaur.jpeg"));

      questions[1][0] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("quest/eight.jpg"));
      questions[1][1] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("quest/seven.jpg"));
      questions[1][2] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("quest/four.jpg"));

      questions[2][0] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/Tree.png"));
      questions[2][1] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/Snowman.png"));
      questions[2][2] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/Flower.png"));

    } catch (Exception e) {
      e.printStackTrace();
    }

    gp.addMouseMotionListener(new MouseAdapter() {
      public void mouseMoved(MouseEvent e) {
      }
    });

    gp.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {

        if (yesButton.contains(e.getPoint())) {
          visible = true;
          accepted = true;
        }

        if (noButton.contains(e.getPoint())) {
          visible = false;
          gp.gameState = 1;
          gp.player.movementHandler.beaDialogShown = false;
        }

        if (!visible) return;

        if (img1.contains(e.getPoint())) {
          gp.questManager.imageQuest.choose(0);
        }

        if (img2.contains(e.getPoint())) {
          gp.questManager.imageQuest.choose(1);
        }

        if (img3.contains(e.getPoint())) {
          gp.questManager.imageQuest.choose(2);
        }
      }
    });
  }

  public void drawAccepted(Graphics2D g2) {


    int width = 400;
    int height = 200;

    int x = gp.getWidth() / 2 - width / 2;
    int y = gp.getHeight() / 2 - height / 2;

    // pozadí
    g2.setColor(new Color(40, 40, 40));
    g2.fillRoundRect(x, y, width, height, 30, 30);

    // okraj
    g2.setColor(Color.WHITE);
    g2.drawRoundRect(x, y, width, height, 30, 30);

    // text
    g2.setColor(new Color(255, 80, 170));
    g2.setFont(new Font("Arial", Font.BOLD, 16));
    g2.drawString("Opravdu si přejete přijmout úkol?", x + 80, y + 60);

    g2.setColor(Color.WHITE);
    g2.setFont(new Font("Arial", Font.PLAIN, 14));
    g2.drawString("Pokud ne, lze ho odmítnout kliknutím na Ne.", x + 90, y + 90);


    // tlačítka
    yesButton.setBounds(x + 80, y + 130, 100, 40);
    noButton.setBounds(x + 220, y + 130, 100, 40);

    g2.setColor(new Color(255, 80, 170));
    g2.fillRoundRect(yesButton.x, yesButton.y, yesButton.width, yesButton.height, 20, 20);

    g2.fillRoundRect(noButton.x, noButton.y, noButton.width, noButton.height, 20, 20);

    g2.setColor(Color.WHITE);
    g2.drawString("ANO", yesButton.x + 30, yesButton.y + 25);
    g2.drawString("NE", noButton.x + 35, noButton.y + 25);
  }

  public void draw(Graphics2D g2) {


    if (!visible) return;
    drawAccepted(g2);

    if (accepted) {
      int width = 500;
      int height = 280;

      int x = gp.getWidth() / 2 - width / 2;
      int y = gp.getHeight() / 2 - height / 2;

      g2.setColor(new Color(40, 40, 40));
      g2.fillRoundRect(x, y - 35, width, height, 30, 30);

      g2.setColor(Color.WHITE);
      g2.drawRoundRect(x, y - 35, width, height, 30, 30);

      int q = questionIndex;
      String text = gp.questManager.imageQuest.questions[q];
      g2.setFont(new Font("Arial", Font.BOLD, 16));
      g2.drawString(text, x + 145, y + 60);


      int size = 80;

      img1.setBounds(x + 80, y + 120, size, size);
      img2.setBounds(x + 210, y + 120, size, size);
      img3.setBounds(x + 340, y + 120, size, size);

      g2.drawImage(questions[questionIndex][0], img1.x, img1.y, size, size, null);
      g2.drawImage(questions[questionIndex][1], img2.x, img2.y, size, size, null);
      g2.drawImage(questions[questionIndex][2], img3.x, img3.y, size, size, null);
    }
  }
}