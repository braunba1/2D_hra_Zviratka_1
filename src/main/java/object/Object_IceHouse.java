package object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

//třída pro barák
public class Object_IceHouse extends SuperObject {

  public Object_IceHouse() {
    name = "Dům";
    solidArea = new Rectangle();
    solidArea.x = 0;
    solidArea.y = 0;
    solidArea.width = 48 * 3;
    solidArea.height = 48 * 3;

    try {

      image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/IceHouse.png"));

    } catch (IOException e) {

      e.printStackTrace();
    }

    collision = true;
  }
}
