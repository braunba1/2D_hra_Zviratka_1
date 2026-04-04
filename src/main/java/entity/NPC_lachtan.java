package entity;


import dialog.AnimatedDialog;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class NPC_lachtan extends Animal {


  public NPC_lachtan(GamePanel gamePanel) {
    super(gamePanel);
    getImage();
    name = "Lachtan";
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
    gamePanel.collisionChecker.checkPlayerOrAnimal(this, gamePanel.animalOfPlayer);
    gamePanel.collisionChecker.checkPlayerOrAnimal(this, gamePanel.player);


    setAction();

    if (!collisionOn) {

      switch (direction) {

        case "down":
          worldY += speed;
          moved = true;
          break;

        case "up":
          worldY -= speed;
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
      down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Lachtan_down1.png"));
      down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Lachtan_down2.png"));

      up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Lachtan_up1.png"));
      up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Lachtan_up2.png"));


    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
