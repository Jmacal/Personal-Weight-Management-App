import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel{
    public Panel(int width, int height, Color colour) {
        Dimension size = new Dimension(width, height);
        setPreferredSize(size);
        setBackground(colour);
    }
}
