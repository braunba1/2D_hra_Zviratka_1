package view;

import UI.UI;
import controller.KeyHandler;
import controller.NPCHandler;
import controller.ObjectHandler;
import dialog.AnimatedDialog;
import entity.AnimalOfPlayer;
import entity.Entity;
import entity.Player;
import object.SuperObject;
import quest.QuestManager;
import tile.CollisionChecker;
import tile.TileController;
import music.Sound;
import UI.QuestUI;
import UI.RememberingQuestUI;
import UI.GuessingQuestUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


public class GamePanel extends JPanel implements Runnable {

  // nastavení obrazovky herního panelu

  final private int originalTileSize = 16; //16 x 16 pixelů, basic velikost herních postav v 2D retro hře a "cihliček" - viz tráva, voda, atd.
  final private int scale = 3; // jelikož retro hry byly dělané na jiné počítačové rozlišení, tak by to bylo moc tiny, tak to násobím

  final public int tileSize = originalTileSize * scale; // Aby postavička a zbytek byly lépe vidět - 48 pixel x 48 pixel

  //matice o 16 sloupcích a 12 řadách
  final public int maxScreenCol = 16;
  final public int maxScreenRow = 12;

  public AnimatedDialog dialog;
  public KeyHandler keyH = new KeyHandler();

  private Thread gameThread;
  public TileController tileController = new TileController(this);

  public Player player = new Player(this, keyH, tileController); //hlavní postava
  public AnimalOfPlayer animalOfPlayer = new AnimalOfPlayer(this, keyH, player, tileController); //zvířatko hlavní postavy

  public CollisionChecker collisionChecker = new CollisionChecker(this);


  public final int screenWidth = maxScreenCol * tileSize; //768 pixelů
  public final int screenHeight = maxScreenRow * tileSize; //576 pixelů

  //WORLD SETTINGS:

  public final int maxWorldCol = 63;
  public final int maxWorldRow = 47;

  public boolean showDialog = false;

  public SuperObject objects[] = new SuperObject[10];
  public Entity npc[] = new Entity[6];
  private ObjectHandler objectHandler = new ObjectHandler(this);
  private NPCHandler npcHandler = new NPCHandler(this);

  private static Sound bgMusic;
  public UI ui = new UI(this);

  public int gameState;
  public final int playingState = 1;
  public final int stopState = 0;

  public QuestUI questUI;
  public RememberingQuestUI rememberingQuestUI;
  public GuessingQuestUI guessingQuestUI;
  public QuestManager questManager;
  public boolean gameCompletedShown = false;

  public GamePanel() {

    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setDoubleBuffered(true); //pro lepší vykreslovací proces
    questUI = new QuestUI(this);
    rememberingQuestUI = new RememberingQuestUI(this);
    guessingQuestUI = new GuessingQuestUI(this);
    questManager = new QuestManager(this);

    this.addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        ui.checkHover(e.getX(), e.getY());
      }
    });

    bgMusic = new Sound("/music/fairy-tale-loop-275534.wav");
    bgMusic.playLoop();

    this.addKeyListener(keyH);

    this.setFocusable(true);
    this.requestFocusInWindow();


  }

  public void startGameThread() {

    //automaticky volá metodu run

    gameThread = new Thread(this);
    gameThread.start();
  }

  public void setUpObjects() {
    objectHandler.setObject();
  }

  public void setUpNPC() {
    npcHandler.setNPC();
  }

  public void run() {

    //jedna ze dvou nejpopulárnějších game loops, druhá je delta, mně přišla jednodušší tahle

    gameState = playingState;
    final int FPS = 60; // 60 snímků za sekundu

    // Interval mezi jednotlivými snímky v nanosekundách
    double drawInterval = 1000000000 / FPS;

    // Čas v nanosekundách, kdy by se měl vykreslit další snímek
    double nextDrawTime = System.nanoTime() + drawInterval;

    // Hlavní herní smyčka - běží, dokud není gameThread ukončen
    while (gameThread != null) {

      update();
      repaint();

      try {
        // Výpočet zbývajícího času do vykreslení dalšího snímku
        double remainingTime = nextDrawTime - System.nanoTime();
        remainingTime = remainingTime / 1000000; // Převod na milisekundy

        // Pokud zbývající čas vyjde záporně(třeba kvůli zpoždění), nastavíme na nulu
        if (remainingTime < 0) {
          remainingTime = 0;
        }

        // Uspíme vlákno na vypočtený čas, aby FPS bylo konstantní
        Thread.sleep((long) remainingTime);

        // Nastavení času pro další vykreslení
        nextDrawTime += drawInterval;

      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }
  }

  public void update() {

    if (gameState == playingState) {
      player.update();
      animalOfPlayer.update();

      for (int i = 0; i < npc.length; i++) {
        if (npc[i] != null) {
          npc[i].update();
        }
      }
      //pokud se dialog vykresluje
      if (showDialog) {
        dialog.update();
      }
    }
    if (gameState == stopState) {
    }

  }

  public void paintComponent(Graphics g) {

    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    tileController.draw(g2); //nejdřív musím vykreslit cihličky než postavu

    for (int i = 0; i < objects.length; i++) {

      if (objects[i] != null) {
        objects[i].draw(g2, this);

      }
    }

    for (int j = 0; j < npc.length; j++) {

      if (npc[j] != null) {
        npc[j].draw(g2);
      }
    }

    player.draw(g2);
    animalOfPlayer.draw(g2);
    ui.draw(g2);
    questUI.draw(g2);
    rememberingQuestUI.draw(g2);
    guessingQuestUI.draw(g2);

    if (showDialog) {
      dialog.draw((Graphics2D) g, 50, 395, 670, 150);

      // Pokud je dialog kompletní, čeká se na enter pro přechod na další zprávu
      if (dialog.isComplete() && keyH.enterPressed) {
        dialog.nextMessage();
      }

      if (dialog.isComplete() && dialog.isDialogFinished() && keyH.enterPressed) {
        showDialog = false;
        player.movementHandler.speed = 3;
        animalOfPlayer.movementHandler.speed = 3;

        if (player.movementHandler.beaDialogShown && !questManager.imageQuest.isCompleted()) {
          questManager.startQuest("Bea");
        }

        if (player.movementHandler.fireWomanDialogShown && !questManager.rememberingQuest.isCompleted()) {
          questManager.startQuest("Fire Woman");
        }

        if (player.movementHandler.ZoroDialogShown && !questManager.guessingQuest.isCompleted()) {
          questManager.startQuest("Bad Animal");

        }

      }
/*
      if (!dialog.isComplete()) {
        player.movementHandler.speed = 0;
        animalOfPlayer.movementHandler.speed = 0;

      }
*/

    }

    g2.dispose(); // yeet g2 k uvolnění paměti, goodbye

  }

  public void showWelcomeBackDialog() {

    dialog = new AnimatedDialog();

    dialog.addMessage(
        "Vítej zpět! Chyběla jsi nám tu!",
        "NPC/mainTeller.png",
        "Genevieve"
    );

    showDialog = true;
  }

  public void showFirstDialog() {

    dialog = new AnimatedDialog();
    dialog.addMessage(
        "Vítám tě v Mýtickém světě! Jmenuji se Genevieve\n"
            + "a budu tě doprovázet na tvojí cestě!\n"
            + "Pro začátek, stistkni enter po pokračování dialogu.",
        "NPC/mainTeller.png",
        "Genevieve"
    );
    dialog.addMessage(
        "Potřebuji, aby jsi došla ke mně domů. Nacházím se v\n"
            + "domečku, který je v severo-východní části lesa.\n" +
               "Pro pohyb použij šipky. Až ke mně přijdeš",
        "NPC/mainTeller.png",
        "Genevieve"
    );

    dialog.addMessage(

             "zmáčkni klávesu Q. Zatím pa!",
        "NPC/mainTeller.png",
        "Genevieve"
    );

    showDialog = true;


  }
}
