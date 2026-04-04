package quest;

import dialog.AnimatedDialog;
import tile.TileController;
import view.GamePanel;

public class ImageChoiceQuest {

  GamePanel gp;
  public boolean completed = false;

  public String[] questions = {
      "Jaké zvířátko nám dává vlnu?",
      "Jaké číslo je menší než 6?",
      "Která z těchto věcí není živá?"
  };

  int correctAnswer = 1;
  int[] correctAnswers = {0, 2, 1};

  public ImageChoiceQuest(GamePanel gp) {
    this.gp = gp;
  }

  public void start() {
    gp.questUI.questionIndex = 0;
    gp.questUI.visible = true;
    gp.questUI.accepted = false;
    gp.gameState = 0;
  }

  public void choose(int index) {

    int q = gp.questUI.questionIndex;
    gp.showDialog = false;

    if (index == correctAnswers[q]) {

      gp.questUI.questionIndex++;
      gp.player.movementHandler.beaDialogShown = true;

      if (gp.questUI.questionIndex >= correctAnswers.length) {

        gp.gameState = 1;
        gp.questUI.visible = false;
        gp.dialog = new AnimatedDialog();
        gp.dialog.addMessage(
            "Výborně! Splnila jsi všechny otázky. Teď se můžeš\n"
                + "vydat do ohnivého lesa!",
            "NPC/Bea_down1.png",
            "Bea"
        );

        gp.keyH.setDialog(gp.dialog);
        gp.showDialog = true;
        gp.tileController.changeMap("maps/mapa_pozadi2.txt");
        completed = true;
      }

    } else {

      gp.gameState = 1;
      gp.dialog = new AnimatedDialog();
      gp.dialog.addMessage(
          "To není správně, zkus to znovu.",
          "NPC/Bea_down1.png",
          "Bea"
      );

      gp.keyH.setDialog(gp.dialog);
      gp.showDialog = true;
    }
  }

  public boolean isCompleted() {
    return completed;
  }

}