package object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

//třída pro barák
public class Object_FireHouse extends SuperObject {

  public Object_FireHouse() {
    name = "Dům";
    solidArea = new Rectangle(0, 0, 48, 48);

    try {

      image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/FireHouse.png"));

    } catch (IOException e) {

      e.printStackTrace();
    }

    collision = true;
  }
}
