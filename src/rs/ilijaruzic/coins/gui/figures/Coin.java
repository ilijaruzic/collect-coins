package rs.ilijaruzic.coins.gui.figures;

import java.awt.*;

public class Coin extends Figure {
    public Coin(int x, int y, int width) {
        super(x, y, width);
    }

    @Override
    public void draw(Graphics graphics) {
        Color oldColor = graphics.getColor();
        graphics.setColor(Color.BLACK);
        graphics.drawOval(x - width / 2, y - width / 2, width, width);
        graphics.setColor(Color.YELLOW);
        graphics.fillOval(x - width / 2, y - width / 2, width, width);
        graphics.setColor(oldColor);
    }
}
