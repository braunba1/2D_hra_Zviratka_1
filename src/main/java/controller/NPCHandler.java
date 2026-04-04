package controller;

import entity.*;
import object.Object_House;
import object.Object_Key;
import object.Object_Truhla;
import view.GamePanel;

public class NPCHandler {

  GamePanel gamePanel;

  public NPCHandler(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  public void setNPC() {

    gamePanel.npc[0] = new NPC_mainTeller(gamePanel);
    gamePanel.npc[0].worldX = 31 * gamePanel.tileSize;
    gamePanel.npc[0].worldY = 7 * gamePanel.tileSize;

    gamePanel.npc[1] = new NPC_dragon(gamePanel);
    gamePanel.npc[1].worldX = 32 * gamePanel.tileSize;
    gamePanel.npc[1].worldY = 7 * gamePanel.tileSize;


    gamePanel.npc[2] = new NPC_Bea(gamePanel);
    gamePanel.npc[2].worldX = 50 * gamePanel.tileSize;
    gamePanel.npc[2].worldY = 17 * gamePanel.tileSize;


    gamePanel.npc[3] = new NPC_FireWoman(gamePanel);
    gamePanel.npc[3].worldX = 32 * gamePanel.tileSize;
    gamePanel.npc[3].worldY = 19 * gamePanel.tileSize;


    gamePanel.npc[4] = new NPC_lachtan(gamePanel);
    gamePanel.npc[4].worldX = 33 * gamePanel.tileSize;
    gamePanel.npc[4].worldY = 19 * gamePanel.tileSize;

    gamePanel.npc[5] = new NPC_BadAnimal(gamePanel);
    gamePanel.npc[5].worldX = 39 * gamePanel.tileSize;
    gamePanel.npc[5].worldY = 31 * gamePanel.tileSize;

  }
}
