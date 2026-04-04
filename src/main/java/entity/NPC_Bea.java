package entity;

import view.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class NPC_Bea extends Entity {

  int actionCounter = 0;

  public NPC_Bea(GamePanel gamePanel) {
    super(gamePanel);
    getImage();
    speed = 2;
    name = "Bea";
  }

  @Override
  public void setAction() {

    if (actionCounter < 25) {
      direction = "right";
    } else if (actionCounter < 50) {
      direction = "left";
    } else if (actionCounter < 75) {
      direction = "down";
    } else if (actionCounter < 100) {
      direction = "up";
    } else {
      actionCounter = 0;
    }

    actionCounter++;
  }

  private void getImage() { //Načítá obrázky (sprite) pro různé směry pohybu hráče.
    try {
      down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/Bea_down1.png"));
      down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/Bea_down2.png"));

      up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/Bea_up1.png"));
      up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/Bea_up2.png"));

      left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/Bea_left1.png"));
      left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/Bea_left2.png"));

      right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/Bea_right1.png"));
      right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/Bea_right2.png"));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
