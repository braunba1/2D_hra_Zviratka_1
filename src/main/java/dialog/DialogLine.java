package dialog;

import java.awt.image.BufferedImage;

public class DialogLine {

  public String text;
  public BufferedImage image;
  public String speaker;

  public DialogLine(String text, BufferedImage image, String speaker){
    this.text = text;
    this.image = image;
    this.speaker = speaker;
  }

}