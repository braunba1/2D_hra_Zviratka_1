package UI;

import data.DataStorage;
import view.GamePanel;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class UI {

  GamePanel gp;

  Rectangle saveButton = new Rectangle(10, 10, 140, 35);
  Rectangle stopButton = new Rectangle(155, 10, 140, 35);
  Rectangle exitButton = new Rectangle(300, 10, 140, 35);
  Rectangle helpButton = new Rectangle(445, 10, 140, 35);


  public boolean saveHover = false;
  public boolean stopHover = false;
  public boolean exitHover = false;
  public boolean helpHover = false;


  public boolean showStopButton = true;
  public boolean showExitDialog = false;
  public boolean showSaveDialog = false;
  public boolean showHelpDialog = false;

  Rectangle yesButton = new Rectangle(0, 0, 120, 40);
  Rectangle noButton = new Rectangle(0, 0, 120, 40);


  Rectangle yesButtonSave = new Rectangle(0, 0, 120, 40);
  Rectangle noButtonSave = new Rectangle(0, 0, 120, 40);

  public UI(GamePanel gp) {
    this.gp = gp;

    gp.addMouseMotionListener(new MouseAdapter() {
      public void mouseMoved(MouseEvent e) {
        checkHover(e.getX(), e.getY());
      }
    });

    gp.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {

        if (showSaveDialog) {

          if (yesButtonSave.contains(e.getPoint())) {
            save();
            showSaveDialog = false;
            gp.gameState = 1;
          }

          if (noButtonSave.contains(e.getPoint())) {
            showSaveDialog = false;
            gp.gameState = 1;
          }

          return;
        }

        if (showExitDialog) {

          if (yesButton.contains(e.getPoint())) {
            System.exit(0);
          }

          if (noButton.contains(e.getPoint())) {
            showExitDialog = false;
            gp.gameState = 1;
          }

          return;
        }

        if (showHelpDialog) {
          showHelpDialog = false;
          gp.gameState = 1;
          return;
        }

        if (saveButton.contains(e.getPoint())) {
          showSaveDialog = true;
          gp.gameState = 0;
        }

        if (stopButton.contains(e.getPoint())) {
          if (showStopButton) {
            showStopButton = false;
            gp.gameState = 0;
          } else {
            showStopButton = true;
            gp.gameState = 1;
          }
        }

        if (exitButton.contains(e.getPoint())) {
          showExitDialog = true;
          gp.gameState = 0;
        }

        if (helpButton.contains(e.getPoint())) {
          showHelpDialog = true;
          gp.gameState = 0;
        }
      }
    });
  }


  public void draw(Graphics2D g2) {

    // hladší grafika
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

    drawButton(g2, saveButton, "ULOŽIT", saveHover);
    drawButton(g2, helpButton, "NÁPOVĚDA", helpHover);

    if (showStopButton) {
      drawButton(g2, stopButton, "POZASTAVIT", stopHover);
    } else {
      drawButton(g2, stopButton, "POKRAČOVAT", stopHover);
      drawStopDialog(g2);
    }
    drawButton(g2, exitButton, "UKONČIT", exitHover);

    if (showExitDialog) {
      drawExitDialog(g2);
    }

    if (showSaveDialog) {
      drawSaveDialog(g2);
    }

    if (showHelpDialog) {
      drawHelpDialog(g2);
    }


  }

  private void drawButton(Graphics2D g2, Rectangle button, String text, boolean hover) {

    // 💗 růžové barvy
    Color normalColor = new Color(255, 80, 170);
    Color hoverColor = new Color(255, 130, 200);

    if (hover) {
      g2.setColor(hoverColor);
    } else {
      g2.setColor(normalColor);
    }

    // tlačítko
    g2.fillRoundRect(button.x, button.y, button.width, button.height, 25, 25);

    // světlejší outline
    g2.setColor(new Color(255, 200, 230));
    g2.drawRoundRect(button.x, button.y, button.width, button.height, 25, 25);

    // font
    g2.setFont(new Font("Arial", Font.BOLD, 14));
    FontMetrics fm = g2.getFontMetrics();

    int textX = button.x + (button.width - fm.stringWidth(text)) / 2;
    int textY = button.y + ((button.height - fm.getHeight()) / 2) + fm.getAscent();

    // ✨ stín textu
    g2.setColor(new Color(120, 0, 80));
    g2.drawString(text, textX + 2, textY + 2);

    g2.setColor(Color.WHITE);
    g2.drawString(text, textX, textY);
  }

  public void checkHover(int mouseX, int mouseY) {

    saveHover = saveButton.contains(mouseX, mouseY);
    stopHover = stopButton.contains(mouseX, mouseY);
    exitHover = exitButton.contains(mouseX, mouseY);
    helpHover = helpButton.contains(mouseX, mouseY);

  }

  private void drawExitDialog(Graphics2D g2) {

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
    g2.drawString("Opravdu si přejete ukončit hru?", x + 80, y + 60);

    g2.setColor(Color.WHITE);
    g2.setFont(new Font("Arial", Font.PLAIN, 14));
    g2.drawString("Váš progres nebude uložen.", x + 90, y + 90);


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

  private void drawStopDialog(Graphics2D g2) {

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
    g2.drawString("Vaše hra byla pozastavena!", x + 80, y + 60);

    g2.setColor(Color.WHITE);
    g2.setFont(new Font("Arial", Font.PLAIN, 14));
    g2.drawString("Pro pokračování klikni na Pokračovat", x + 90, y + 90);

  }

  private void drawSaveDialog(Graphics2D g2) {

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
    g2.drawString("Opravdu si přejete uložit hru?", x + 80, y + 60);

    g2.setColor(Color.WHITE);
    g2.setFont(new Font("Arial", Font.PLAIN, 14));
    g2.drawString("Váš progres bude uložen.", x + 90, y + 90);


    // tlačítka
    yesButtonSave.setBounds(x + 80, y + 130, 100, 40);
    noButtonSave.setBounds(x + 220, y + 130, 100, 40);

    g2.setColor(new Color(255, 80, 170));
    g2.fillRoundRect(yesButtonSave.x, yesButtonSave.y, yesButtonSave.width, yesButtonSave.height, 20, 20);

    g2.fillRoundRect(noButtonSave.x, noButtonSave.y, noButtonSave.width, noButtonSave.height, 20, 20);

    g2.setColor(Color.WHITE);
    g2.drawString("ANO", yesButtonSave.x + 30, yesButtonSave.y + 25);
    g2.drawString("NE", noButtonSave.x + 35, noButtonSave.y + 25);
  }


  public void save() {

    try {
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
      DataStorage ds = new DataStorage();

      ds.chestOpened = gp.objects[1] == null;
      ds.keyFound = gp.objects[2] == null;
      if (gp.objects[2] == null) {
        ds.hasKey = 1;
      } else {
        ds.hasKey = 0;
      }
      ds.x = gp.player.worldX;
      ds.y = gp.player.worldY;
      ds.isBeaCompleted = gp.questManager.imageQuest.isCompleted();
      ds.isFireWomanCompleted = gp.questManager.rememberingQuest.isCompleted();
      ds.isZoroCompleted = gp.questManager.guessingQuest.isCompleted();

      oos.writeObject(ds);
    } catch (Exception e) {
    }
  }

  private void drawHelpDialog(Graphics2D g2) {

    int width = 500;
    int height = 300;

    int x = gp.getWidth() / 2 - width / 2;
    int y = gp.getHeight() / 2 - height / 2;

    g2.setColor(new Color(40, 40, 40));
    g2.fillRoundRect(x, y, width, height, 30, 30);

    g2.setColor(Color.WHITE);
    g2.drawRoundRect(x, y, width, height, 30, 30);

    g2.setColor(new Color(255, 80, 170));
    g2.setFont(new Font("Arial", Font.BOLD, 18));
    g2.drawString("NÁPOVĚDA", x + 180, y + 40);

    g2.setColor(Color.WHITE);
    g2.setFont(new Font("Arial", Font.PLAIN, 16));

    g2.drawString("ŠIPKY - pohyb postavy", x + 50, y + 90);
    g2.drawString("ENTER - další dialog", x + 50, y + 120);
    g2.drawString("Q - spuštění úkolu / dialogu ", x + 50, y + 150);
    g2.drawString("C - změna zvířete (pouze po splnění úkolu Bea)", x + 50, y + 180);
    g2.drawString("N - vypnutí kolize", x + 50, y + 210);

    g2.drawString("Klikni kamkoliv pro zavření", x + 120, y + 260);
  }

}