package entity;

//hlavní třída pro herní entity, ze které pak budou dědit zvířátka, NPC postavy, etc

import controller.KeyHandler;
import tile.TileController;
import view.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Entity {

  public String name;
  GamePanel gamePanel;
  KeyHandler keyH;
  TileController tileController;
  public int worldX;
  public int worldY;
  public int speed = 2;
  public int spriteNum = 1;
  private int spriteCounter = 0;
  public boolean moved = false;
  public String direction = "down";

  public BufferedImage down1, down2, left1, left2, up1, up2, right1, right2;
  public BufferedImage down1Animal, down2Animal, up1Animal, up2Animal, left1Animal, left2Animal, right1Animal, right2Animal;
  public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
  public boolean collisionOn = false;
  public static ArrayList<Entity> allEntities = new ArrayList<>();
  public int solidAreaDefaultX = solidArea.x;
  public int solidAreaDefaultY = solidArea.y;

  public int actionCounter = 0;
  public int actionLockCounter = 0;

  public Entity(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }


  public Entity(GamePanel gamePanel, KeyHandler keyH, TileController tileController) {
    this.gamePanel = gamePanel;
    this.keyH = keyH;
    this.tileController = tileController;
  }

  public void setAction() {

    actionLockCounter++;

    Random random = new Random();
    int actionCounter = random.nextInt(101);


    if (actionLockCounter >= 120) {

      if (actionCounter < 25) {
        direction = "right";
      } else if (actionCounter > 25 && actionCounter < 50) {
        direction = "up";
      } else if (actionCounter < 75 && actionCounter > 50) {
        direction = "left";
      } else if (actionCounter < 100 && actionCounter > 75) {
        direction = "down";
      }

      actionLockCounter = 0;
    }

  }

  public void update() {

    collisionOn = false;
    moved = false;

    setAction();
    gamePanel.collisionChecker.checkCollision(this);
    gamePanel.collisionChecker.checkPlayerOrAnimal(this, gamePanel.animalOfPlayer);
    gamePanel.collisionChecker.checkPlayerOrAnimal(this, gamePanel.player);
    gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.npc);
    gamePanel.collisionChecker.checkObjectCollision(this,false, false);

    if (!collisionOn) {

      switch (direction) {

        case "up":
          worldY -= speed;
          moved = true;
          break;
        case "down":
          worldY += speed;
          moved = true;
          break;
        case "left":
          worldX -= speed;
          moved = true;
          break;
        case "right":
          worldX += speed;
          moved = true;
          break;

      }

      if (moved) {
        changeSprite();
      }
    }
  }

  public void draw(Graphics g2) {

    BufferedImage image = null;
    int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
    int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

    if (direction.equals("down")) {
      if (spriteNum == 1) {
        image = down1;
      }
      if (spriteNum == 2) {
        image = down2;
      }
    }

    if (direction.equals("left")) {
      if (spriteNum == 1) {
        image = left1;
      }
      if (spriteNum == 2) {
        image = left2;
      }
    }

    if (direction.equals("up")) {
      if (spriteNum == 1) {
        image = up1;
      }
      if (spriteNum == 2) {
        image = up2;
      }
    }

    if (direction.equals("right")) {
      if (spriteNum == 1) {
        image = right1;
      }
      if (spriteNum == 2) {
        image = right2;
      }
    }

    g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
  }

  public static ArrayList<Entity> getAllEntities() {
    return allEntities;
  }

  public void addEntity(Entity entity) {
    allEntities.add(entity);
  }

  public boolean isCollisionOn() {
    return collisionOn;
  }

  public void changeSprite() {
    spriteCounter++;
    if (spriteCounter > 18) { // Počítadlo pro střídání sprite
      spriteNum = (spriteNum == 1) ? 2 : 1;
      spriteCounter = 0;
    }
  }

}
