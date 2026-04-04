package controller;

import object.*;
import view.GamePanel;

//ako jméno naznačuje, pracuje s objekty
public class ObjectHandler {

  GamePanel gamePanel;

  public ObjectHandler(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  public void setObject() {

    gamePanel.objects[0] = new Object_House();
    gamePanel.objects[0].worldX = 29 * gamePanel.tileSize;
    gamePanel.objects[0].worldY = 5 * gamePanel.tileSize;
    gamePanel.objects[0].scale = 3; //aby byl větší

    gamePanel.objects[1] = new Object_Truhla();
    gamePanel.objects[1].worldX = 29 * gamePanel.tileSize;
    gamePanel.objects[1].worldY = 7 * gamePanel.tileSize;

    gamePanel.objects[2] = new Object_Key();
    gamePanel.objects[2].worldX = 9 * gamePanel.tileSize;
    gamePanel.objects[2].worldY = 25 * gamePanel.tileSize;

    gamePanel.objects[3] = new Object_House();
    gamePanel.objects[3].worldX = 17 * gamePanel.tileSize;
    gamePanel.objects[3].worldY = 25 * gamePanel.tileSize;
    gamePanel.objects[3].scale = 3; //aby byl větší

    gamePanel.objects[4] = new Object_House();
    gamePanel.objects[4].worldX = 8 * gamePanel.tileSize;
    gamePanel.objects[4].worldY = 13 * gamePanel.tileSize;
    gamePanel.objects[4].scale = 3; //aby byl větší

    gamePanel.objects[5] = new Object_IceHouse();
    gamePanel.objects[5].worldX = 41 * gamePanel.tileSize;
    gamePanel.objects[5].worldY = 5 * gamePanel.tileSize;
    gamePanel.objects[5].scale = 3; //aby byl větší

    gamePanel.objects[6] = new Object_IceHouse();
    gamePanel.objects[6].worldX = 29 * gamePanel.tileSize;
    gamePanel.objects[6].worldY = 17 * gamePanel.tileSize;
    gamePanel.objects[6].scale = 3; //aby byl větší

    gamePanel.objects[7] = new Object_IceHouse();
    gamePanel.objects[7].worldX = 47 * gamePanel.tileSize;
    gamePanel.objects[7].worldY = 15 * gamePanel.tileSize;
    gamePanel.objects[7].scale = 3; //aby byl větší

    gamePanel.objects[8] = new Object_FireHouse();
    gamePanel.objects[8].worldX = 12 * gamePanel.tileSize;
    gamePanel.objects[8].worldY = 37 * gamePanel.tileSize;
    gamePanel.objects[8].scale = 3; //aby byl větší

    gamePanel.objects[9] = new Object_FireHouse();
    gamePanel.objects[9].worldX = 51 * gamePanel.tileSize;
    gamePanel.objects[9].worldY = 32 * gamePanel.tileSize;
    gamePanel.objects[9].scale = 3; //aby byl větší


  }

}
