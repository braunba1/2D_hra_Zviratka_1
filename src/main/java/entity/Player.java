package entity;

import controller.KeyHandler;
import controller.MovementHandler;
import tile.Tile;
import tile.TileController;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Třída Player reprezentuje hlavní postavu ve hře.
 * Zajišťuje načítání obrázků postavy, její pohyb a vykreslení na obrazovku.
 */

public class Player extends Entity {


  private KeyHandler keyH;
  public MovementHandler movementHandler; // Správa pohybu

  //worldSouradnice != screenSouradnice
  //worldSouradnice mi urcuji, kde se postava pohybuje v moji uzasne mapě, kterou jsem ještě nedokončila
  //  public int worldX;
  //public int worldY;

  //screenSouradnice určuji, že postava se bude nacházet v prostředku obrazovky
  public final int screenX;
  public final int screenY;
  public boolean hasKey = false;

  private TileController tileController;
  public int animalNumber = 1;

  public Player(GamePanel gp, KeyHandler keyH, TileController tileController) {

    super(gp, keyH, tileController);
    this.keyH = keyH;
    this.tileController = tileController;
    this.movementHandler = new MovementHandler(this, 500, 500, 3, tileController, gp);
    getPlayerImage();
    allEntities.add(this);
    name = "Player";


    //dosazení postavy do prostředku obrazovky
    //odečítám půlku cihličky, protože jinak ve středu je levý roh obrázku postavy
    screenX = gamePanel.screenWidth / 2 - (gp.tileSize / 2);
    screenY = gamePanel.screenHeight / 2 - (gp.tileSize / 2);

    solidArea = new Rectangle(8, 16, 32, 32);
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;

  }


  private void getPlayerImage() { //Načítá obrázky (sprite) pro různé směry pohybu hráče.
    try {

      down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Down1.png"));
      down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Down2.png"));
      left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Left1.png"));
      left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Left2.png"));
      up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Up1.png"));
      up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Up2.png"));
      right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Right1.png"));
      right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Right2.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Aktualizuje stav hráče.
   * zpracovává pohyb hráče na základě vstupů z klávesnice.
   */
  public void update() {

    movementHandler.update(keyH);
    this.worldX = movementHandler.getX();
    this.worldY = movementHandler.getY();
    this.direction = movementHandler.getDirection();

  }

  public void draw(Graphics2D g2) { // Vykresluje hráče na obrazovku podle jeho aktuální pozice a směru pohybu.
    //stejná metoda jako v AnimalOfPlayer
    //TODO: Předělat left a right obrázky, bcs it´s not giving walking

    BufferedImage image = null;
    String direction = movementHandler.getDirection();
    int spriteNum = movementHandler.getSpriteNum();

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

}
