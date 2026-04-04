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
  public void update() {

  }

  private void getImage() { //Načítá obrázky (sprite) pro různé směry pohybu hráče.
    try {
      down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("animal/BadAnimal_down1.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
