package entity;

import view.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class NPC_FireWoman extends Entity {


  public NPC_FireWoman(GamePanel gamePanel) {
    super(gamePanel);
    getImage();
    name = "Fire Woman";
  }


  @Override
  public void update() {

  }

  private void getImage() { //Načítá obrázky (sprite) pro různé směry pohybu hráče.
    try {
      down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/FireWoman.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
