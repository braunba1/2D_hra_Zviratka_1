package quest;

import dialog.AnimatedDialog;
import entity.NPC_BadAnimal;
import view.GamePanel;

public class GuessingQuest {

  GamePanel gp;
  public boolean completed = false;

  public String[] questions = {
      "Vyber správnou odpověď:",
      "Vyber správnou odpověď:",
      "Vyber správnou odpověď:"
  };

  int[] correctAnswers = {0, 1, 1};

  public String[] stories = {
      "Ve dne svítí na obloze,\n" +
          "zahřeje nás po cestě i v lese.\n" +
          "Večer se schová za hory.\n" +
      "Co je to?",
      "Má dlouhé uši, skáče v trávě,\n" +
          "mrkvičku má nejraději právě.\n" +
          "Co je to?",
      "Padá z nebe v zimě tiše,\n" +
          "na střechy i na silnice.\n" +
          "Je studený a bílý.\n" +
          "Co je to?",
  };


  public GuessingQuest(GamePanel gp) {
    this.gp = gp;
  }

  public void start() {
    gp.guessingQuestUI.questionIndex = 0;
    gp.guessingQuestUI.visible = true;
    gp.guessingQuestUI.accepted = false;
    gp.guessingQuestUI.showingStory = true;
    gp.guessingQuestUI.storyStartTime = System.currentTimeMillis();

    gp.gameState = 0;
  }

  public void choose(int index) {

    int q = gp.guessingQuestUI.questionIndex;
    gp.showDialog = false;

    if (index == correctAnswers[q]) {

      gp.player.movementHandler.ZoroDialogShown = true;
      gp.guessingQuestUI.questionIndex++;
      gp.guessingQuestUI.showingStory = true;
      gp.guessingQuestUI.storyStartTime = System.currentTimeMillis();

      if (gp.guessingQuestUI.questionIndex >= correctAnswers.length) {

        gp.gameState = 1;
        completed = true;
        gp.guessingQuestUI.visible = false;
        gp.dialog = new AnimatedDialog();
        gp.dialog.addMessage(
            "Hmmm. Splnila jsi všechno správně. Možná nejsi \n"
            +" jako všichni ostatní. Přidám se k tvojí výpravě.\n"
                + "Když stiskneš klávesu C, prohodím se s tvým mazlíčkem.",
            "animal/BadAnimal_down1.png",
            "Zero"
        );


        gp.keyH.setDialog(gp.dialog);
        gp.showDialog = true;
        gp.npc[5] = null;
        gp.player.animalNumber = 2;



      }

    } else {

      gp.gameState = 1;
      gp.dialog = new AnimatedDialog();
      gp.dialog.addMessage(
          "To není správně, zkus to znovu.",
          "animal/BadAnimal_down1.png",
          "Zoro"
      );

      gp.keyH.setDialog(gp.dialog);
      gp.showDialog = true;
    }

  }

  public boolean isCompleted() {
    return completed;
  }

}

