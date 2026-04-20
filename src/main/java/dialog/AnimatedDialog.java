package dialog;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Třída pro řešení dialogů, momentálně mám jen úvodní dialog

public class AnimatedDialog {

  private List<DialogLine> messages; // Seznam zpráv v dialogu
  private int currentMessageIndex = 0; // Index aktuální zprávy
  private String currentText = ""; // Text aktuálně zobrazený na obrazovce
  private int charIndex = 0; // Index aktuálního znaku v animaci
  private int animationSpeed = 2; // Rychlost a
  // nimace (počet snímků na znak)
  private int frameCounter = 2; // Počítadlo snímků


  public AnimatedDialog() {
    messages = new ArrayList<>();
  }

  public void addMessage(String text, String imagePath, String speaker) {

    BufferedImage img = null;

    try {
      java.io.InputStream stream = getClass().getResourceAsStream("/" + imagePath);

      if (stream == null) {
        System.out.println("IMAGE NOT FOUND: " + imagePath);
      } else {
        img = ImageIO.read(stream);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    messages.add(new DialogLine(text, img, speaker));
  }


  /**
   * Aktualizuje animaci textu.
   * Zajišťuje postupné zobrazování textu po písmenkách.
   */

  public void update() {
    if (!isComplete()) {
      frameCounter++;
      if (frameCounter % animationSpeed == 0) {
        if (frameCounter >= animationSpeed && charIndex < getCurrentMessage().text.length()) {
          currentText += getCurrentMessage().text.charAt(charIndex);
          charIndex++;
          frameCounter = 0;
        }
      }
    }
  }

  //vykreslování dialogového okna na obrazovku
  public void draw(Graphics2D g2, int x, int y, int width, int height) {

    BufferedImage characterImage = getCurrentMessage().image;
    String speaker = getCurrentMessage().speaker;

    // Vykreslení pozadí dialogového okna
    g2.setColor(new Color(0, 0, 0, 200)); //alfa kanál je pro průhlednost
    g2.fillRoundRect(x, y, width, height, 20, 20);

    // Vykreslení okraje
    g2.setColor(Color.WHITE);
    g2.drawRoundRect(x, y, width, height, 20, 20);

    if (characterImage != null) {
      int imageX = x + width - 140; // Umístění X obrázku (140 px od pravého okraje)
      int imageY = y + height - 140; // Umístění Y obrázku (140 px od spodního okraje)
      int imageWidth = 130; // šířka obrázku
      int imageHeight = 130; // výška obrázku

      // Vykreslení obrázku
      g2.drawImage(characterImage, imageX, imageY, imageWidth, imageHeight, null);
    }

    g2.setColor(Color.WHITE);


    g2.setFont(new Font("Arial", Font.PLAIN, 20));

    if (speaker != null) {


      g2.drawString(speaker, x + 10, y + 27);

      Color pink = new Color(247, 155, 227);
      g2.setColor(pink);
      g2.drawString(speaker, x + 9, y + 26);

    }

    // Vykreslení aktuálního textu
    g2.setFont(new Font("Arial", Font.PLAIN, 18));
    g2.setColor(Color.WHITE);
    int textX = x + 20;
    int textY = y + 60;

    //TODO: pořešit, protože mě to nebaví ručně sekat
    // Rozdělení textu na řádky (pro případ, že by byl příliš dlouhý)
    int lineHeight = 25; //mezera mezi jednotlivými řádky
    String[] lines = currentText.split("\n");
    for (String line : lines) {
      g2.drawString(line, textX, textY);
      textY += lineHeight;
    }
  }


  public void nextMessage() {


    if (isComplete() && currentMessageIndex < messages.size() - 1) {
      currentMessageIndex++;
      resetAnimation();
    }
  }


  public boolean isDialogFinished() {
    return currentMessageIndex == messages.size() - 1;
  }

  public boolean isComplete() {
    return charIndex >= getCurrentMessage().text.length();
  }

  private DialogLine getCurrentMessage() {
    return messages.get(currentMessageIndex);
  }

  private void resetAnimation() {
    currentText = "";
    charIndex = 0;
    frameCounter = 0;
  }


}
