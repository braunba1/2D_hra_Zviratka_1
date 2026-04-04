package controller;

import dialog.AnimatedDialog;
import entity.Entity;
import entity.NPC_Bea;
import music.Sound;
import tile.CollisionChecker;
import tile.Tile;
import tile.TileController;
import view.GamePanel;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static entity.Entity.allEntities;

/**
 * Třída MovementHandler spravuje pohyb objektu na obrazovce.
 * Řeší pozici, směr pohybu, rychlost a animaci (změnu sprite).
 */


public class MovementHandler {

  private Entity owner;
  private Sound sound;
  private int worldX, worldY; // Aktuální souřadnice objektu
  public int speed; // Rychlost pohybu
  private String direction = "down"; // Aktuální směr pohybu
  private int spriteCounter = 0; // pro změnu sprite/animace
  private int spriteNum = 1; // Aktuální číslo sprite/animace (1 nebo 2)
  private TileController tileController;
  public GamePanel gamePanel;
  public int hasKey = 0;
  public boolean chestOpened = false;
  private boolean triedToOpenLockedChest = false;
  boolean moved = false;
  private boolean genevieveDialogShown = false;
  public boolean questKeyCompleted = false;
  private int answerCounter = 0;
  public boolean beaDialogShown = false;
  public boolean drogoDialog1Shown = false;
  public boolean drogoDialog2Shown = false;
  public boolean drogoDialog3Shown = false;
  public boolean drogoDialog4Shown = false;
  public boolean fireWomanDialogShown = false;
  public boolean firstBeaDialog = false;
  public boolean ZoroDialogShown = false;
  public boolean firstZoroDialog = false;


  public MovementHandler(Entity owner, int startX, int startY, int speed, TileController controller, GamePanel gamePanel) {
    this.owner = owner;
    owner.worldX = startX;
    owner.worldY = startY;
    this.speed = speed;
    this.tileController = controller;
    this.gamePanel = gamePanel;
  }


  //Aktualizuje stav pohybu objektu na základě stisknutých kláves.
  public void update(KeyHandler keyH) {

    moved = false;
    int pressedCount = 0; // Počet současně stisknutých kláves
    String lastDirection = ""; // Poslední směr, který byl stisknut

    if (keyH.upPressed) {
      pressedCount++;
      lastDirection = "up";
      moved = true;


    }
    if (keyH.downPressed) {
      pressedCount++;
      lastDirection = "down";


    }
    if (keyH.leftPressed) {
      pressedCount++;
      lastDirection = "left";

    }
    if (keyH.rightPressed) {
      pressedCount++;
      lastDirection = "right";

    }

    if (pressedCount == 1) {
      direction = lastDirection;
      owner.direction = direction;
      owner.speed = speed;
    }

    owner.collisionOn = false;

    gamePanel.collisionChecker.checkCollision(owner);
    int objectIndex = gamePanel.collisionChecker.checkObjectCollision(owner, true, false);
    int npcIndex = gamePanel.collisionChecker.checkEntityCollision(owner, gamePanel.npc);

    if (keyH.kolizeCancel) {

      owner.collisionOn = false;

    }
    pickUpObject(objectIndex);

    if(keyH.questStart){
      pickUpQuest(npcIndex);
    }


    // Pokud je stisknuta pouze jedna klávesa, změní směr a aktualizuje pozici
    if (pressedCount == 1) {

      direction = lastDirection;
      owner.direction = direction;
      owner.speed = speed;

      if (gamePanel.animalOfPlayer.collisionOn) {
        gamePanel.animalOfPlayer.worldX = gamePanel.player.worldX + 46;
        gamePanel.animalOfPlayer.worldY = gamePanel.player.worldY - 33;
      }

      if (!gamePanel.animalOfPlayer.collisionOn && !gamePanel.player.collisionOn) {

        switch (direction) {

          case "up":
            owner.worldY -= speed;
            moved = true;
            break;
          case "down":
            owner.worldY += speed;
            moved = true;
            break;
          case "left":
            owner.worldX -= speed;
            moved = true;
            break;
          case "right":
            owner.worldX += speed;
            moved = true;
            break;

        }

        changeSprite();

      }
      if (moved) {
        playStepSound();
      }
    }

  }


  /**
   * Zajišťuje změnu sprite (animace)
   * Střídá dva sprity (1 a 2) po určitém počtu aktualizací - aby to nevypadalo, že se moje postavička pohybuje jak při ADHD
   */
  private void changeSprite() {
    spriteCounter++;
    if (spriteCounter > 18) { // Počítadlo pro střídání sprite
      spriteNum = (spriteNum == 1) ? 2 : 1;
      spriteCounter = 0;
    }
  }

  private void playStepSound() {
    int col = owner.worldX / gamePanel.tileSize;
    int row = owner.worldY / gamePanel.tileSize;

    int tileNum = gamePanel.tileController.mapTileNumber[col][row];

    if (gamePanel.tileController.tile[tileNum].doesItMakeSound) {
      gamePanel.tileController.tile[tileNum].sound.playOnce();
    }
  }


  public int getX() {
    return owner.worldX;
  }

  public int getY() {
    return owner.worldY;
  }

  public String getDirection() {
    return direction;
  }

  public int getSpriteNum() {
    return spriteNum;
  }

  //metoda pro sbírání objektů
  public void pickUpObject(int i) {

    if (i != 666) {

      if (gamePanel.objects[i] == null) return;

      String objectName = gamePanel.objects[i].name;

      switch (objectName) {
        case "Klíč":

          sound = new Sound("/music/key_drop.wav");
          sound.playOnce();
          gamePanel.player.hasKey = true;
          gamePanel.objects[i] = null; //smazání klíče

          gamePanel.dialog = new AnimatedDialog();
          gamePanel.dialog.addMessage(
              "Výborně, našla jsi klíč! Teď dojdi zpátky k truhle!"
              ,
              "NPC/mainTeller.png",
              "Genevieve"
          );
          gamePanel.keyH.setDialog(gamePanel.dialog);
          gamePanel.showDialog = true;
          break;

        case "Truhla":

          System.out.println("hasKey = " + hasKey);
          if (gamePanel.player.hasKey && !chestOpened && !questKeyCompleted) {

            sound = new Sound("/music/locked.wav");
            sound.playOnce();
            chestOpened = true;
            gamePanel.objects[i] = null;
            gamePanel.dialog = new AnimatedDialog();
            gamePanel.dialog.addMessage(
                "Hurá! Ty jsi ho našla! Věděla jsem, že se na\n"
                    + "tebe můžu spolehnout. Přeci jen holky drží při sobě!\n"
                    + ". . . . . . ",
                "NPC/mainTeller.png",
                "Genevieve"
            );

            gamePanel.dialog.addMessage(
                "Á! Úplně jsem zapomněla. Tady je dárek pro tebe za to, \n"
                    + "že jsi mi pomohla!",
                "NPC/mainTeller.png",
                "Genevieve"
            );

            gamePanel.dialog.addMessage(
                "Tohle není jen tak obyčejný kámen! Díky němu \n"
                    + "budeš moct mluvit se zvířátky. Je jen tvůj! \n"
                ,
                "tiles/MagicStone.png",
                "Genevieve"
            );

            gamePanel.dialog.addMessage(
                ". . . . . . ",
                "animal/Drak_down1.png",
                "Drogo"
            );

            gamePanel.dialog.addMessage(
                "Konečně tě moc rád poznávám! Není nad to mít víc\n"
                    + "přátel, se kterými si můžu povídat!",
                "animal/Drak_down1.png",
                "Drogo"
            );

            gamePanel.dialog.addMessage(
                "To je zatím od nás vše! Věřím, že v Mýtickém světě je\n"
                    + "toho ještě spousty k prozkoumání!",
                "npc/mainTeller.png",
                "Genevieve"
            );

            gamePanel.keyH.setDialog(gamePanel.dialog);
            gamePanel.showDialog = true;
            questKeyCompleted = true;
            genevieveDialogShown = true;


          } else if (gamePanel.player.hasKey && !chestOpened && !triedToOpenLockedChest && genevieveDialogShown) {
            triedToOpenLockedChest = true; //aby se mi dokola nenastavovaly dialogy

            gamePanel.dialog = new AnimatedDialog();
            gamePanel.dialog.addMessage(
                "Nejdřív musíš najít klíč, jinak to nepůjde!"
                ,
                "NPC/mainTeller.png",
                "Genevieve"
            );
            gamePanel.keyH.setDialog(gamePanel.dialog);
            gamePanel.showDialog = true;
          }

          break;
      }
    }
  }

  public void pickUpQuest(int i) {

    if (gamePanel.showDialog) return;

    if (i != 666) {

      String npcName = gamePanel.npc[i].name;

      switch (npcName) {

        case "Lachtan":
          gamePanel.dialog = new AnimatedDialog();
          gamePanel.dialog.addMessage(
              "Zdar. Nemáš náhodou rybu? Mám docela hlad. \n"
                  + "Ne? Nevadí, seženu si ji někde jinde.\n"
                  + ". . . .\n"
                  + "Jé! Kdo ty jsi? Nemáš náhodou rybu?",
              "animal/BadAnimal_down1.png",
              "Zoro"
          );

          gamePanel.showDialog = true;
          gamePanel.keyH.setDialog(gamePanel.dialog);
          break;
        case "Bad Animal":

          if (ZoroDialogShown && firstZoroDialog && !gamePanel.questManager.guessingQuest.isCompleted()) {
            gamePanel.questManager.startQuest("Bad Animal");
          }
          if (!ZoroDialogShown && !firstZoroDialog && !gamePanel.questManager.guessingQuest.isCompleted()) {

            firstZoroDialog = true;
            ZoroDialogShown = true;
            gamePanel.dialog = new AnimatedDialog();
            gamePanel.dialog.addMessage(
                "Baf. Mé jméno je Zoro a nikomu nepatřím. Lidi nemám \n"
                    + "rád a převážně se jim vyhýbám. Divím se, že jsi mě\n"
                + "našla. Pokud splníš můj úkol, možná udělám vyjímku.\n"
                    + "Po skončení dialogu znovu zmáčkni tlačítko Q.",
                "animal/BadAnimal_down1.png",
                "Zoro"
            );

            gamePanel.showDialog = true;
            gamePanel.keyH.setDialog(gamePanel.dialog);

          }
          break;

        case "Fire Woman":


          if (fireWomanDialogShown && !gamePanel.questManager.rememberingQuest.isCompleted()) {
            gamePanel.questManager.startQuest("Fire Woman");
          }


            if (!fireWomanDialogShown && !gamePanel.questManager.rememberingQuest.isCompleted()) {

            fireWomanDialogShown = true;
            gamePanel.dialog = new AnimatedDialog();
            gamePanel.dialog.addMessage(
                "Zdravím tě, hráčko! Moc ráda tě poznávám. Neměla bys chuť \n"
                    + "si se mnou zahrát takovou hru?",
                "NPC/FireWoman.png",
                "Vanessa"
            );

            gamePanel.dialog.addMessage(
                "Můj nejlepší kamárad vedle mě, Azure, často zapomíná. \n"
                    + "A potřebuji si s ním procvičovat věci na pamět. Pokud\n"
                    + "bys chtěla s námi, pojďme na to!",
                "NPC/FireWoman.png",
                "Vanessa"
            );

            gamePanel.dialog.addMessage(
                "Je to tak. Sice jsem mladý, ale furt něco zapomínám.\n"
                    + "Minule jsem si zapomněl i kartáček! Což u takových\n"
                    + "zubů, jako mám já, není nic příjemného. Přidej se k nám!\n"
                    + "Po skončení dialogu znovu zmáčkni tlačítko Q.",
                "animal/Lachtan_down1.png",
                "Azure"
            );
            gamePanel.showDialog = true;
            gamePanel.keyH.setDialog(gamePanel.dialog);
          }
          break;
        case "Bea":

          if (!fireWomanDialogShown && !gamePanel.questManager.rememberingQuest.isCompleted() && !gamePanel.questManager.imageQuest.isCompleted()) {


            gamePanel.dialog = new AnimatedDialog();
            gamePanel.dialog.addMessage(
                "Ah! Nová tvář! Ráda tě poznávám, já jsem Bea. \n"
                    + "Máš moc krásného společníka! Není ale překrásný?",
                "NPC/Bea_down1.png",
                "Bea"
            );
            gamePanel.dialog.addMessage(
                "Ráda bych ti zadala úkol, ale nejdřív musíš splnit \n"
                    + "úkol od Vanessy, která se nachází kousek ode mě!\n" +
                    "Pak se vrať, až ho splníš!",
                "NPC/Bea_down1.png",
                "Bea"
            );
            gamePanel.showDialog = true;
            gamePanel.keyH.setDialog(gamePanel.dialog);

          }

          if (beaDialogShown && !gamePanel.questManager.imageQuest.isCompleted()) {
            gamePanel.questManager.startQuest("Bea");
          }
          if (!beaDialogShown && fireWomanDialogShown && !gamePanel.questManager.imageQuest.isCompleted() && gamePanel.questManager.rememberingQuest.isCompleted()) {
            beaDialogShown = true;
            gamePanel.dialog = new AnimatedDialog();
            gamePanel.dialog.addMessage(
                "Měla bych tu pro tebe jeden takový menší úkol,\n"
                    + "pokud si na něj samozřejmě troufáš! Po skončení \n"
                    + "dialogu zmáčkni tlačítko Q.",
                "NPC/Bea_down1.png",
                "Bea"
            );
            gamePanel.showDialog = true;
            gamePanel.keyH.setDialog(gamePanel.dialog);

          }
          break;

        case "Drogo":
          if (questKeyCompleted) {
            if (!drogoDialog1Shown) {
              drogoDialog1Shown = true;
              gamePanel.dialog = new AnimatedDialog();
              gamePanel.dialog.addMessage(
                  "Bohužel já pro tebe žádný úkol nemám.",
                  "animal/Drak_down1.png",
                  "Drogo"
              );
              gamePanel.showDialog = true;
              gamePanel.keyH.setDialog(gamePanel.dialog);

            } else if (!drogoDialog2Shown) {
              drogoDialog2Shown = true;
              gamePanel.dialog = new AnimatedDialog();
              gamePanel.dialog.addMessage(
                  "Věděl jsi, že podle někoho by drak měl mít jen dvě nohy a křídla?",
                  "animal/Drak_down1.png",
                  "Drogo"
              );
              gamePanel.showDialog = true;
              gamePanel.keyH.setDialog(gamePanel.dialog);
            } else if (!drogoDialog3Shown) {
              drogoDialog3Shown = true;
              gamePanel.dialog = new AnimatedDialog();
              gamePanel.dialog.addMessage(
                  "Mým největším vzorem je Charizard.",
                  "animal/Drak_down1.png",
                  "Drogo"
              );
              gamePanel.showDialog = true;
              gamePanel.keyH.setDialog(gamePanel.dialog);
            } else if (!drogoDialog4Shown) {
              drogoDialog4Shown = true;
              gamePanel.dialog = new AnimatedDialog();
              gamePanel.dialog.addMessage(
                  "Moje nejoblíbenější ovoce je mango.",
                  "animal/Drak_down1.png",
                  "Drogo"
              );
              gamePanel.showDialog = true;
              gamePanel.keyH.setDialog(gamePanel.dialog);

            }


          } else {
            gamePanel.dialog = new AnimatedDialog();
            gamePanel.dialog.addMessage(
                ". . . . . .",
                "animal/Drak_down1.png",
                "Drogo"
            );
            gamePanel.showDialog = true;
            gamePanel.keyH.setDialog(gamePanel.dialog);
          }

          break;

        case "Genevieve":


          if (questKeyCompleted) return;

          if (genevieveDialogShown) {
            return;
          }

          if (!genevieveDialogShown) {

            genevieveDialogShown = true;
            gamePanel.dialog = new AnimatedDialog();

            gamePanel.dialog.addMessage(
                "Tak jsi mě našla! Výborně! Ráda bych ti někoho představila!\n"
                    + "Tady vedle mě je Drogo, můj nejlepší kamarád!",
                "NPC/mainTeller.png",
                "Genevieve"
            );

            gamePanel.dialog.addMessage(
                ". . . . . .",
                "animal/Drak_down1.png",
                "Drogo"
            );

            gamePanel.dialog.addMessage(
                ". . . Á! Já zapomněla, že ještě nemůžeš mluvit se zvířátky!\n"
                    + "Což mě přivádí k úkolu, který pro tebe mám!\n"
                    + "Vidíš tu truhlu vedle mě?!",
                "NPC/mainTeller.png",
                "Genevieve"
            );

            gamePanel.dialog.addMessage(
                "Nachází se v ní něco velmi důležitého. Bohužel \n"
                    + "jsem někde v Magickém lese zratila klíč a nemohu ho najít.\n"
                    + "Spolehám tedy na tebe, že mi ho pomůžeš vypátrat.",
                "NPC/mainTeller.png",
                "Genevieve"
            );

            gamePanel.dialog.addMessage(
                "Až ho najdeš, vrať se sem. Dávej na sebe pozor!",
                "NPC/mainTeller.png",
                "Genevieve"
            );

            gamePanel.showDialog = true;


            break;




          }
      }
    }
  }


}
