package UI;

import view.GamePanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class RememberingQuestUI {

  GamePanel gp;

  public boolean visible = false;
  public boolean accepted;

  Rectangle answer1 = new Rectangle();
  Rectangle answer2 = new Rectangle();
  Rectangle answer3 = new Rectangle();

  Rectangle yesButton = new Rectangle(0, 0, 120, 40);
  Rectangle noButton = new Rectangle(0, 0, 120, 40);

  public int questionIndex = 0;
  public boolean showingStory = true;
  public long storyStartTime = 0;
  public int storyDuration = 45000; //

  public RememberingQuestUI(GamePanel gp) {
    this.gp = gp;

    gp.addMouseMotionListener(new MouseAdapter() {
      public void mouseMoved(MouseEvent e) {
      }
    });

    gp.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {

        if (!visible) return;

        if (yesButton.contains(e.getPoint())) {
          visible = true;
          accepted = true;
        }

        if (noButton.contains(e.getPoint())) {
          visible = false;
          gp.gameState = 1;
          gp.player.movementHandler.fireWomanDialogShown = false;
        }

        if (answer1.contains(e.getPoint())) {
          gp.questManager.rememberingQuest.choose(0);
        }

        if (answer2.contains(e.getPoint())) {
          gp.questManager.rememberingQuest.choose(1);
        }

        if (answer3.contains(e.getPoint())) {
          gp.questManager.rememberingQuest.choose(2);

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

      if (showingStory) {

        if (System.currentTimeMillis() - storyStartTime > storyDuration) {
          showingStory = false;
        }

        int width = 500;
        int height = 250;

        int x = gp.getWidth() / 2 - width / 2;
        int y = gp.getHeight() / 2 - height / 2;

        g2.setColor(new Color(40, 40, 40));
        g2.fillRoundRect(x - 20, y - 20, width + 20, height + 20, 30, 30);

        g2.setColor(Color.WHITE);
        g2.drawRoundRect(x - 20, y - 20, width + 20, height + 20, 30, 30);

        g2.setColor(new Color(255, 80, 170));
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("ZAPAMATUJ SI, MÁŠ NA TO 45 SEKUND!", x + 23, y + 25);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 18));

        int q = questionIndex;
        String story = gp.questManager.rememberingQuest.stories[q];

        String[] lines = story.split("\n");

        int lineHeight = 22;
        int startY = y + 60;

        for (int i = 0; i < lines.length; i++) {
          g2.drawString(lines[i], x + 20, startY + (i * lineHeight));
        }

        return;

      }

      if (!showingStory) {

        int width = 500;
        int height = 280;

        int x = gp.getWidth() / 2 - width / 2;
        int y = gp.getHeight() / 2 - height / 2;

        g2.setColor(new Color(40, 40, 40));
        g2.fillRoundRect(x - 20, y - 20, width + 20, height + 20, 30, 30);

        g2.setColor(Color.WHITE);
        g2.drawRoundRect(x - 20, y - 20, width + 20, height + 20, 30, 30);

        int q = questionIndex;
        String text = gp.questManager.rememberingQuest.questions[q];
        g2.setColor(new Color(255, 80, 170));
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString(text, x + 140, y + 75);


        g2.setColor(Color.WHITE);

        answer1.setBounds(x + 40, y + 140, 140, 60);
        answer2.setBounds(x + 200, y + 140, 140, 60);
        answer3.setBounds(x + 360, y + 140, 140, 60);

        if (questionIndex == 0) {
          g2.drawString("A)Modrou", answer1.x, answer1.y + 45);
          g2.drawString("B)Červenou", answer2.x + 5, answer2.y + 45);
          g2.drawString("C)Žlutou", answer3.x - 15, answer3.y + 45);
        }


        if (questionIndex == 1) {
          g2.drawString("A)V jeskyni", answer1.x, answer1.y + 45);
          g2.drawString("B)U potoka", answer2.x - 5, answer2.y + 45);
          g2.drawString("C)Na vrcholu věže", answer3.x - 30, answer3.y + 45);
        }
      }

      if (questionIndex == 2) {
        g2.drawString("A)Zametl podlahu", answer1.x - 10, answer1.y + 45);
        g2.drawString("B)Zapálil lucernu", answer2.x - 10, answer2.y + 45);
        g2.drawString("C)Posbíral kamínky", answer3.x - 20, answer3.y + 45);
      }
    }
  }


}