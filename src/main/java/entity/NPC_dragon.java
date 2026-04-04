package entity;

import dialog.AnimatedDialog;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class NPC_dragon extends Animal {


  public NPC_dragon(GamePanel gamePanel) {
    super(gamePanel);
    getImage();
    name = "Drogo";
  }

  @Override
  public void setAction() {

    if (actionCounter < 33) {
      direction = "right";
    } else if (actionCounter < 65) {
      direction = "left";
    } else if (actionCounter < 99) {
      direction = "down";
    } else {
      actionCounter = 0;
    }

    actionCounter++;
  }

  @Override
  public void update() {

    collisionOn = false;
    setAction();
    gamePanel.collisionChecker.checkCollision(this);
    gamePanel.collisionChecker.checkPlayerOrAnimal(this, gamePanel.animalOfPlayer);
    gamePanel.collisionChecker.checkPlayerOrAnimal(this, gamePanel.player);
    gamePanel.collisionChecker.checkEntityCollision(this,gamePanel.npc);


    if (!collisionOn) {

      switch (direction) {

        case "left":
          worldX -= speed;
          moved = true;
          break;

        case "right":
          worldX += speed;
          moved = true;
          break;

        case "down":
          // stojí na místě
          break;
      }

      if (moved) {
        changeSprite();
      }
    }
  }


  private void getImage() { //Načítá obrázky (sprite) pro různé směry pohybu hráče.
    try {
      down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Drak_down1.png"));
      down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Drak_down2.png"));

      up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Drak_up1.png"));
      up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Drak_up2.png"));

      left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Drak_left1.png"));
      left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Drak_left2.png"));

      right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Drak_right1.png"));
      right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Drak_right2.png"));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
