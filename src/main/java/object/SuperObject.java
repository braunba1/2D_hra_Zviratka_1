package object;

import view.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

//superior class haha, z které dědí všechn objekty

public class SuperObject {

  public BufferedImage image;
  public String name;
  public boolean collision;
  public int worldX;
  public int worldY;
  public int scale = 1;
  public Rectangle solidArea = new Rectangle(0, 0, 48, 48); //pro kontrolu kolizí
  public int solidAreaDefaultX = solidArea.x;
  public int solidAreaDefaultY = solidArea.y;


  public void draw(Graphics g2, GamePanel gamePanel) {

    int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
    int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

    if (worldX + 3 * gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
        worldX - 3 * gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
        worldY + 3 * gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
        worldY - 3 * gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

      g2.drawImage(image, screenX, screenY, gamePanel.tileSize * scale, gamePanel.tileSize * scale, null);
    }

  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }
}
