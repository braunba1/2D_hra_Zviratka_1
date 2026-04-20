package controller;

import dialog.AnimatedDialog;
import view.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Třída KeyHandler řeší ovládání pomocí klávesnice
public class KeyHandler implements KeyListener {


  private GamePanel gp;
  public boolean upPressed;
  public boolean downPressed;
  public boolean leftPressed;
  public boolean rightPressed;

  public boolean enterPressed;
  public AnimatedDialog dialog;
  public boolean questStart;
  public boolean animalChanged = false;
  public int changeAnimal = 1;

  public void setDialog(AnimatedDialog dialog) {
    this.dialog = dialog; //inicializace dialogu
  }

  public void keyPressed(KeyEvent e) {
    //nastavování hodnot směrů na "true" na základě toho, jakou klávesu stisknu
    switch (e.getKeyCode()) {
      case KeyEvent.VK_UP:
        upPressed = true;
        break;
      case KeyEvent.VK_DOWN:
        downPressed = true;
        break;
      case KeyEvent.VK_LEFT:
        leftPressed = true;
        break;
      case KeyEvent.VK_RIGHT:
        rightPressed = true;
        break;
      case KeyEvent.VK_ENTER: //pro pokračování na další dialog
        enterPressed = true;
        if (dialog != null) {
          dialog.nextMessage(); // Dialog musí být inicializován
        }

        break;
      case KeyEvent.VK_Q:
        questStart = true;
        break;

      case KeyEvent.VK_C:
        if (changeAnimal == 1) {
          changeAnimal = 2;
        } else {
          changeAnimal = 1;
        }
        animalChanged = true;
        break;
    }
  }

  // Reaguje na uvolnění klávesy a nastaví příslušnou proměnnou na hodnotu "false".

  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_UP:
        upPressed = false;
        break;
      case KeyEvent.VK_DOWN:
        downPressed = false;
        break;
      case KeyEvent.VK_LEFT:
        leftPressed = false;
        break;
      case KeyEvent.VK_RIGHT:
        rightPressed = false;
        break;
      case KeyEvent.VK_ENTER:
        enterPressed = false;
        break;
      case KeyEvent.VK_Q:
        questStart = false;
        break;
      case KeyEvent.VK_C:
        break;
    }
  }

  public void keyTyped(KeyEvent e) {
    // prázdné, nevyužívané, možná v budoucnu při řešení úkolu, nevím nevím
  }
}
