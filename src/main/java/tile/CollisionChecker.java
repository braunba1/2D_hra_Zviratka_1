    package tile;

import entity.Entity;
import view.GamePanel;

import java.awt.*;

// třída pro kontrolu kolizí
public class CollisionChecker {

  GamePanel gamePanel;

  public CollisionChecker(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  public void checkCollision(Entity entity) {

    int entityLeftX = entity.worldX + entity.solidArea.x;
    int entityRightX = entity.worldX + entity.solidArea.x + entity.solidArea.width - 1;
    int entityTopY = entity.worldY + entity.solidArea.y;
    int entityBottomY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

    int entityLeftCol = entityLeftX / gamePanel.tileSize;
    int entityRightCol = entityRightX / gamePanel.tileSize;
    int entityTopRow = entityTopY / gamePanel.tileSize;
    int entityBottomRow = entityBottomY / gamePanel.tileSize;

    int tile1;
    int tile2;

    if (entity.direction != null) {

      switch (entity.direction) {

        case "up":
          entityTopRow = (entityTopY - entity.speed) / gamePanel.tileSize;
          tile1 = gamePanel.tileController.mapTileNumber[entityLeftCol][entityTopRow];
          tile2 = gamePanel.tileController.mapTileNumber[entityRightCol][entityTopRow];

          if (gamePanel.tileController.tile[tile1].collision ||
              gamePanel.tileController.tile[tile2].collision) {
            entity.collisionOn = true;
          }
          break;

        case "down":
          entityBottomRow = (entityBottomY + entity.speed) / gamePanel.tileSize;
          tile1 = gamePanel.tileController.mapTileNumber[entityLeftCol][entityBottomRow];
          tile2 = gamePanel.tileController.mapTileNumber[entityRightCol][entityBottomRow];

          if (gamePanel.tileController.tile[tile1].collision ||
              gamePanel.tileController.tile[tile2].collision) {
            entity.collisionOn = true;
          }
          break;

        case "left":
          entityLeftCol = (entityLeftX - entity.speed) / gamePanel.tileSize;
          tile1 = gamePanel.tileController.mapTileNumber[entityLeftCol][entityTopRow];
          tile2 = gamePanel.tileController.mapTileNumber[entityLeftCol][entityBottomRow];

          if (gamePanel.tileController.tile[tile1].collision ||
              gamePanel.tileController.tile[tile2].collision) {
            entity.collisionOn = true;
          }
          break;

        case "right":
          entityRightCol = (entityRightX + entity.speed) / gamePanel.tileSize;
          tile1 = gamePanel.tileController.mapTileNumber[entityRightCol][entityTopRow];
          tile2 = gamePanel.tileController.mapTileNumber[entityRightCol][entityBottomRow];

          if (gamePanel.tileController.tile[tile1].collision ||
              gamePanel.tileController.tile[tile2].collision) {
            entity.collisionOn = true;
          }
          break;
      }
    }
  }

  public int checkEntityCollision(Entity entity, Entity[] target) {

    int index = 666;

    for (int i = 0; i < target.length; i++) {

      if (target[i] != null) {

        if (entity.direction != null) {

          if (entity.name.equals(target[i].name) ||
              entity.name.equals("Player") && target[i].name.equals("AnimalOfPlayer") ||
              entity.name.equals("AnimalOfPlayer") && target[i].name.equals("Player")) {
            continue;
          }

          Rectangle entityArea = new Rectangle(
              entity.worldX + entity.solidArea.x,
              entity.worldY + entity.solidArea.y,
              entity.solidArea.width,
              entity.solidArea.height
          );

          Rectangle targetArea = new Rectangle(
              target[i].worldX + target[i].solidArea.x,
              target[i].worldY + target[i].solidArea.y,
              target[i].solidArea.width,
              target[i].solidArea.height
          );

          switch (entity.direction) {
            case "up":
              entityArea.y -= entity.speed;
              break;
            case "down":
              entityArea.y += entity.speed;
              break;
            case "left":
              entityArea.x -= entity.speed;
              break;
            case "right":
              entityArea.x += entity.speed;
              break;
          }

          if (entityArea.intersects(targetArea)) {
            entity.collisionOn = true;

            if (gamePanel.npc[i].name.equals("Genevieve")) index = 0;
            if (gamePanel.npc[i].name.equals("Drogo")) index = 1;
            if (gamePanel.npc[i].name.equals("Bea")) index = 2;
            if (gamePanel.npc[i].name.equals("Fire Woman")) index = 3;
            if (gamePanel.npc[i].name.equals("Lachtan")) index = 4;
            if (gamePanel.npc[i].name.equals("Bad Animal")) index = 5;
          }
        }
      }
    }

    return index;
  }

  public int checkObjectCollision(Entity entity, boolean player, boolean animalOfPlayer) {

    int index = 666;

    for (int i = 0; i < gamePanel.objects.length; i++) {

      if (gamePanel.objects[i] != null) {

        Rectangle entityArea = new Rectangle(
            entity.worldX + entity.solidArea.x,
            entity.worldY + entity.solidArea.y,
            entity.solidArea.width,
            entity.solidArea.height
        );

        Rectangle objectArea = new Rectangle(
            gamePanel.objects[i].worldX + gamePanel.objects[i].solidArea.x,
            gamePanel.objects[i].worldY + gamePanel.objects[i].solidArea.y,
            gamePanel.objects[i].solidArea.width,
            gamePanel.objects[i].solidArea.height
        );

        if (entity.direction != null) {

          switch (entity.direction) {
            case "up":
              entityArea.y -= entity.speed;
              break;
            case "down":
              entityArea.y += entity.speed;
              break;
            case "left":
              entityArea.x -= entity.speed;
              break;
            case "right":
              entityArea.x += entity.speed;
              break;
          }

          if (entityArea.intersects(objectArea)) {
            if (gamePanel.objects[i].collision) {
              entity.collisionOn = true;

              if (player || animalOfPlayer) {
                if (gamePanel.objects[i].name.equals("Klíč")) {
                  index = 2;
                }
                if (gamePanel.objects[i].name.equals("Truhla")) {
                  index = 1;
                }
              }
            }
          }
        }
      }
    }

    return index;
  }

  public void checkPlayerOrAnimal(Entity entity, Entity target) {

    Rectangle entityArea = new Rectangle(
        entity.worldX + entity.solidArea.x,
        entity.worldY + entity.solidArea.y,
        entity.solidArea.width,
        entity.solidArea.height
    );

    Rectangle targetArea = new Rectangle(
        target.worldX + target.solidArea.x,
        target.worldY + target.solidArea.y,
        target.solidArea.width,
        target.solidArea.height
    );

    if (entity.direction != null) {

      switch (entity.direction) {
        case "up":
          entityArea.y -= entity.speed;
          break;
        case "down":
          entityArea.y += entity.speed;
          break;
        case "left":
          entityArea.x -= entity.speed;
          break;
        case "right":
          entityArea.x += entity.speed;
          break;
      }

      if (entityArea.intersects(targetArea)) {
        entity.collisionOn = true;
      }
    }
  }
}
