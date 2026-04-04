package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Object_Truhla extends SuperObject {


  public Object_Truhla() {
    name = "Truhla";
    try {

      image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/truhla.png"));

    } catch (IOException e) {

      e.printStackTrace();
    }

    collision = true;
  }
}
