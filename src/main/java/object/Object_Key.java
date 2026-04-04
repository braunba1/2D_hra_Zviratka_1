package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Object_Key extends SuperObject {

  public Object_Key() {
    name = "Klíč";

    try {

      image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/key.png"));

    } catch (IOException e) {

      e.printStackTrace();
    }

    collision = true;
  }
}
