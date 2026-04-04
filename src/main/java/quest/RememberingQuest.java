package quest;

import dialog.AnimatedDialog;
import view.GamePanel;

public class RememberingQuest {

  GamePanel gp;
  public boolean completed = false;

  public String[] questions = {
      "Jakou barvu měly květiny na nápoj?",
      "Kde Tarin našel modrý krystal?",
      "Co Flamík udělal jako poslední?",
  };

  int[] correctAnswers = {0, 1, 1};

  public String[] stories = {
      "Malá víla Lumi žila u třpytivého jezírka v lese. Každé\n" +
          "ráno sbírala rosu z modrých květin, protože z ní \n" +
          "vyráběla kouzelný nápoj pro lesní zvířata. Jednoho dne\n" +
          "přišel ježek, který byl unavený po dlouhé cestě. Lumi\n" +
          "mu dala trochu kouzelného nápoje a ježek měl hned \n" +
          "více energie. Na oplátku jí ježek přinesl červené \n" +
          "jablko, které našel pod stromem.",
      "Mladý čaroděj Tarin se učil kouzlit ve staré věži na \n" +
          "kopci. Jednoho dne mu mistr zadal úkol: najít tři \n" +
          "věci, které pomáhají kouzlům růst v síle. Tarin se vydal \n" +
          "do lesa. Nejprve našel stříbrné pírko sovy, které \n" +
          "leželo na zemi. Potom objevil malý modrý krystal u\n" +
          "potoka. Nakonec utrhl zlatý list ze starého stromu.\n",
      "Jednoho rána se malý dráček Flamík rozhodl uklidit svou \n" +
          "jeskyni. Nejprve posbíral rozsypané kamínky ze země.\n" +
          "Dále si protáhl svá křídla a vymetl pavučiny.\n" +
          "Potom zametl podlahu velkým koštětem.\n" +
          "Pak zapálil kouzelnou lucernu, aby jeskyně krásně svítila.\n" +
          "Když bylo hotovo, Flamík si spokojeně sedl a odpočíval.\n"+
          "Ještě přemýšlel, že by si četl, ale na to už byl unavený."
  };


  public RememberingQuest(GamePanel gp) {
    this.gp = gp;
  }

  public void start() {
    gp.rememberingQuestUI.questionIndex = 0;
    gp.rememberingQuestUI.visible = true;
    gp.rememberingQuestUI.accepted = false;
    gp.rememberingQuestUI.showingStory = true;
    gp.rememberingQuestUI.storyStartTime = System.currentTimeMillis();

    gp.gameState = 0;
  }

  public void choose(int index) {

    int q = gp.rememberingQuestUI.questionIndex;
    gp.showDialog = false;

    if (index == correctAnswers[q]) {

      gp.player.movementHandler.fireWomanDialogShown = true;
      gp.rememberingQuestUI.questionIndex++;
      gp.rememberingQuestUI.showingStory = true;
      gp.rememberingQuestUI.storyStartTime = System.currentTimeMillis();

      if (gp.rememberingQuestUI.questionIndex >= correctAnswers.length) {

        gp.gameState = 1;
        completed = true;
        gp.rememberingQuestUI.visible = false;
        gp.dialog = new AnimatedDialog();
        gp.dialog.addMessage(
            "Výborně! Odpovědila jsi na všechno správně.",
            "NPC/FireWoman.png",
            "Vanessa"
        );


        gp.keyH.setDialog(gp.dialog);
        gp.showDialog = true;


      }

    } else {

      gp.gameState = 1;
      gp.dialog = new AnimatedDialog();
      gp.dialog.addMessage(
          "To není správně, zkus to znovu.",
          "NPC/FireWoman.png",
          "Vanessa"
      );

      gp.keyH.setDialog(gp.dialog);
      gp.showDialog = true;
    }

  }

  public boolean isCompleted() {
    return completed;
  }

}

