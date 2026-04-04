package entity;

import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class NPC_mainTeller extends Entity {


  public NPC_mainTeller(GamePanel gamePanel) {
    super(gamePanel);
    getImage();
    name = "Genevieve";
  }


  @Override
  public void update() {

  }

  private void getImage() { //Načítá obrázky (sprite) pro různé směry pohybu hráče.
    try {
      down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/mainTellerBody.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
