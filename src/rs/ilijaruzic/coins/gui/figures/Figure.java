package rs.ilijaruzic.coins.gui.figures;

import java.awt.*;

public abstract class Figure {
    protected int x, y, width;

    public Figure(int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Figure)) {
            return false;
        }
        Figure figure = (Figure) object;
        return x == figure.x && y == figure.y;
    }

    public abstract void draw(Graphics graphics);

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
