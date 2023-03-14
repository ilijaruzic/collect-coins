package rs.ilijaruzic.coins.gui.figures;

import java.awt.*;

public class Player extends Figure {
    public Player(int x, int y, int width) {
        super(x, y, width);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawLine(x, y - width / 2, x, y + width / 2 - 1);
        graphics.drawLine(x - width / 2, y, x + width / 2 - 1, y);
        graphics.fillRect(x - width / 8, y - width / 8, width / 4, width / 4);
    }
}
