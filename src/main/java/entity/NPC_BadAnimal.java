package entity;

import view.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class NPC_BadAnimal extends Entity {


  public NPC_BadAnimal(GamePanel gamePanel) {
    super(gamePanel);
    getImage();
    name = "Bad Animal";
  }


  @Override
  public void setAction() {

    if (actionCounter < 50) {
      direction = "up";
    } else if (actionCounter < 98) {
      direction = "down";
    } else {
      actionCounter = 0;
    }

    actionCounter++;
  }

  @Override
  public void update() {

    moved = false;
    collisionOn = false;
    gamePanel.collisionChecker.checkCollision(this);
    gamePanel.collisionChecker.checkPlayerOrAnimal(this, gamePanel.player);

    setAction();

    if (!collisionOn) {

      switch (direction) {

        case "down":
          worldY += 1;
          moved = true;
          break;

        case "up":
          worldY -= 1;
          moved = true;
          break;

      }

      if (moved) {
        changeSprite();
      }
    }
  }

  private void getImage() { //Načítá obrázky (sprite) pro různé směry pohybu hráče.
    try {
      down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/BadAnimal_down1.png"));
      down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/BadAnimal_down2.png"));

      up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/BadAnimal_up1.png"));
      up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/BadAnimal_up2.png"));


    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
