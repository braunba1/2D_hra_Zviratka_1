package tile;

import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import music.Sound;

/**
 * Třída TileController zajišťuje správu a vykreslení cihliček na ploše
 * Načítá obrázky dlaždic, mapu dlaždic ze souboru a zajišťuje jejich zobrazení.
 */
public class TileController {

  private GamePanel gamePanel;
  public Tile[] tile; // Pole dlaždic
  public int[][] mapTileNumber; // Dvourozměrné pole pro uchování mapy dlaždic, matice
  public InputStream is;

  public TileController(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
    this.is = getClass().getClassLoader().getResourceAsStream("maps/mapa_pozadi.txt");
    tile = new Tile[19]; // Počet typů dlaždic, určitě budu zvyšovat, až nakreslím design zbytku mapy
    mapTileNumber = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow]; //počet sloupců mapy a počet řádků mapy
    getTileImage();
    loadMap();
  }

  /**
   * Načítá obrázky pro jednotlivé typy dlaždic.
   * Obrázky mám ve složce res/tiles/
   * otázka jestli jich nemám už trošku too much
   */

  private void getTileImage() {

    try {
      tile[0] = new Tile();
      tile[0].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/Grass.png"));
      tile[0].sound = new Sound("/music/grass_walk.wav");
      tile[0].doesItMakeSound = true;

      tile[1] = new Tile();
      tile[1].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/Wall.png"));
      tile[1].collision = true;

      tile[3] = new Tile();
      tile[3].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/Path.png"));

      tile[4] = new Tile();
      tile[4].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/Tree.png"));
      tile[4].collision = true;

      tile[2] = new Tile();
      tile[2].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/Snow.png"));
      tile[2].sound = new Sound("/music/snow_walk.wav");
      tile[2].doesItMakeSound = true;


      tile[5] = new Tile();
      tile[5].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/SnowPath.png"));

      tile[6] = new Tile();
      tile[6].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/Ice.png"));
      tile[6].collision = true;

      tile[7] = new Tile();
      tile[7].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/Snowman.png"));
      tile[7].collision = true;

      tile[8] = new Tile();
      tile[8].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/SnowWall.png"));
      tile[8].collision = true;

      tile[9] = new Tile();
      tile[9].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/FireGrass.png"));
      tile[9].sound = new Sound("/music/fire_walk.wav");
      tile[9].doesItMakeSound = true;

      tile[10] = new Tile();
      tile[10].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/FireWall.png"));
      tile[10].collision = true;

      tile[11] = new Tile();
      tile[11].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/FirePath.png"));

      tile[12] = new Tile();
      tile[12].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/Fire.png"));
      tile[12].collision = true;

      tile[13] = new Tile();
      tile[13].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/Sopka.png"));
      tile[13].collision = true;

      tile[14] = new Tile();
      tile[14].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/Darkness.png"));
      tile[14].collision = true;

      tile[15] = new Tile();
      tile[15].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/Ker.png"));
      tile[15].collision = true;

      tile[16] = new Tile();
      tile[16].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/SnowyTree.png"));
      tile[16].collision = true;

      tile[17] = new Tile();
      tile[17].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/Stone.png"));
      tile[17].collision = true;

      tile[18] = new Tile();
      tile[18].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/FireTree.png"));
      tile[18].collision = true;


    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadMap() {
    //Načítání mapy z textového souboru, kdy čísla reprezentují dlaždice
    int row = 0;
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));

      while (row < gamePanel.maxWorldRow) {

        String line = reader.readLine();
        if (line == null) break;

        String[] numbers = line.split(" ");

        for (int col = 0; col < numbers.length && col < gamePanel.maxWorldCol; col++) {
          int num = Integer.parseInt(numbers[col]);
          mapTileNumber[col][row] = num;
        }

        row++;
      }

      reader.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public void draw(Graphics2D g2) {

    int col = 0;
    int row = 0;

    while (row < gamePanel.maxWorldRow) {

      int tileNum = mapTileNumber[col][row];

      // Přepočítáme světové souřadnice této dlaždice
      int worldX = col * gamePanel.tileSize;
      int worldY = row * gamePanel.tileSize;

      // Přepočítáme souřadnice dlaždice na obrazovce (podle pozice hráče)
      int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
      int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

      // Podmínka pro vykreslení pouze těch dlaždic, které actually vidím
      if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
          worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
          worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
          worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

        if (tileNum < tile.length) {
          g2.drawImage(tile[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
      }

      col++; //přesuneme se na další sloupec


      // Pokud jsme na konci řádku, přesuneme se na nový řádek a resetujeme sloupec
      if (col == gamePanel.maxWorldCol) {
        col = 0;

        row++; //přesuneme se na další řádek

      }
    }
  }

  public void changeMap(String path) {

    is = getClass().getClassLoader().getResourceAsStream(path);
    loadMap();

  }

}
