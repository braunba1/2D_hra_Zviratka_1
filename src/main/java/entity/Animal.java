package entity;

import controller.KeyHandler;
import tile.TileController;
import view.GamePanel;

//Řeší pro Mytická zvířátka
public class Animal extends Entity {


  public Animal(GamePanel gamePanel) {
    super(gamePanel);
  }

  public Animal(GamePanel gamePanel, KeyHandler keyH, TileController tileController) {
    super(gamePanel, keyH, tileController);
  }
}
