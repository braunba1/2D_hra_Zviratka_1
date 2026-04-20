package entity;

import controller.KeyHandler;
import controller.MovementHandler;
import tile.TileController;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Třída AnimalOfPlayer reprezentuje zvíře, které patří hráči
 * Třída rozšiřuje základní třídu {@link Animal} a přidává vlastní logiku (například v budoucnu trainAnimal, atd, ještě nevím)
 * pro pohyb a vykreslování zvířete hráče
 */
public class AnimalOfPlayer extends Animal {

  private KeyHandler keyH; // Instance pro zpracování vstupu z klávesnice
  private Player player;
  public MovementHandler movementHandler; // Správa pohybu zvířete
  private TileController tileController;
  public final int screenX;
  public final int screenY;
  // public int worldX;
  // public int worldY;
  private static final int ANIMAL_OFFSET_X = 46;
  private static final int ANIMAL_OFFSET_Y = -33;

  public AnimalOfPlayer(GamePanel gp, KeyHandler keyH, Player player, TileController tileController) {

    super(gp, keyH, tileController);
    this.keyH = keyH;
    this.tileController = tileController;
    this.player = player;
    //umistuji na základě pozice hráče:
    this.movementHandler = new MovementHandler(this, player.movementHandler.getX() + 46, player.movementHandler.getY() - 33, 3, tileController, gp);
    getAnimalOfPlayerImage();
    allEntities.add(this);
    name = "AnimalOfPlayer";

    //screen souřadnice != worldSouřadnice
    //postava je zasazena do poloviny obrazovky, zvíře kousek od něj
    //TODO: Možná budu muset pořešit, aby mohli procházet oba když budou blízko sebe dlaždice s kolizí true
    screenX = gamePanel.screenWidth / 2 - (gp.tileSize / 2) + 45;
    screenY = gamePanel.screenHeight / 2 - (gp.tileSize / 2) - 30;

    solidArea = new Rectangle(8, 16, 32, 32);
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
  }

  /**
   * Načítá obrázky pro animace zvířete hráče.
   * Obrázky ukládám ve složce /res/animal/
   */

  private void getAnimalOfPlayerImage() {


      if (keyH.changeAnimal == 1) {
      try {
        down1Animal = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Animal_down1.png"));
        down2Animal = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Animal_down2.png"));
        up1Animal = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Animal_up1.png"));
        up2Animal = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Animal_up2.png"));
        left1Animal = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Animal_left1.png"));
        left2Animal = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Animal_left2.png"));
        right1Animal = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Animal_right1.png"));
        right2Animal = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/Animal_right2.png"));
      }
      catch (IOException e) {
        e.printStackTrace();
      }}
      else {
        try {
          down1Animal = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/BadAnimal_down1.png"));
          down2Animal = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/BadAnimal_down2.png"));
          up1Animal = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/BadAnimal_up1.png"));
          up2Animal = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/BadAnimal_up2.png"));
          left1Animal = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/BadAnimal_left1.png"));
          left2Animal = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/BadAnimal_left2.png"));
          right1Animal = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/BadAnimal_right1.png"));
          right2Animal = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/BadAnimal_right2.png"));
        }
        catch (IOException e) {
          e.printStackTrace();
        }
      }


  }


  public void update() {

    if(keyH.animalChanged && player.animalNumber == 2){
      getAnimalOfPlayerImage();
      keyH.animalChanged = false;
    }

    movementHandler.update(keyH);
    this.worldX = movementHandler.getX();
    this.worldY = movementHandler.getY();
    this.direction = movementHandler.getDirection();
  } //Aktualizuje stav zvířete na základě vstupu z klávesnice.

  /**
   * Vykresluje zvíře hráče na herní plátno.
   * Obrázek je určen na základě toho, jaký je aktuální směr pohybu a fáze animace (changeSprite)
   */
  public void draw(Graphics2D g2) {

    BufferedImage image = null;
    String direction = movementHandler.getDirection();
    int spriteNum = movementHandler.getSpriteNum();

    if (direction.equals("down")) {
      if (spriteNum == 1) {
        image = down1Animal;
      } else if (spriteNum == 2) {
        image = down2Animal;
      }
    }

    if (direction.equals("up")) {
      if (spriteNum == 1) {
        image = up1Animal;
      } else if (spriteNum == 2) {
        image = up2Animal;
      }
    }

    if (direction.equals("left")) {
      if (spriteNum == 1) {
        image = left1Animal;
      } else if (spriteNum == 2) {
        image = left2Animal;
      }
    }

    if (direction.equals("right")) {
      if (spriteNum == 1) {
        image = right1Animal;
      } else if (spriteNum == 2) {
        image = right2Animal;
      }
    }

    int drawX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
    int drawY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

    g2.drawImage(image, drawX, drawY, gamePanel.tileSize, gamePanel.tileSize, null);

  }
}
