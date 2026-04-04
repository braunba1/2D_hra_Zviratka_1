package quest;

import view.GamePanel;

public class QuestManager {

  GamePanel gp;

  public ImageChoiceQuest imageQuest;
  public RememberingQuest rememberingQuest;
  public GuessingQuest guessingQuest;

  public QuestManager(GamePanel gp) {
    this.gp = gp;
    imageQuest = new ImageChoiceQuest(gp);
    rememberingQuest = new RememberingQuest(gp);
    guessingQuest = new GuessingQuest(gp);
  }

  public void startQuest(String npcName) {

    switch (npcName) {

      case "Bea":

        if (!imageQuest.isCompleted()) {
          imageQuest.start();
        }

        break;

      case "Fire Woman":

        if (!rememberingQuest.isCompleted()) {
          rememberingQuest.start();
        }
        break;

      case "Bad Animal":

        if (!guessingQuest.isCompleted()) {
          guessingQuest.start();
        }

        break;
    }

  }

}