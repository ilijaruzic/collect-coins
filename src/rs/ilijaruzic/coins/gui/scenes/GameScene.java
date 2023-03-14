package rs.ilijaruzic.coins.gui.scenes;

import rs.ilijaruzic.coins.gui.figures.Coin;
import rs.ilijaruzic.coins.gui.figures.Figure;
import rs.ilijaruzic.coins.gui.figures.Player;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameScene extends Canvas {
    private static final int ROWS = 10;

    private final CollectCoins owner;
    private final int rows = ROWS;
    private int fieldWidth;
    private int score = 0;

    private java.util.List<Figure> figures = new ArrayList<>();
    private Player player;

    public GameScene(CollectCoins owner) {
        this.owner = owner;

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = Character.toUpperCase(e.getKeyChar());
                switch (key) {
                    case KeyEvent.VK_W: {
                        int y = player.getY() - fieldWidth;
                        player.setY(y < 0 ? player.getY() : y);
                        break;
                    }
                    case KeyEvent.VK_S: {
                        int y = player.getY() + fieldWidth;
                        player.setY(y > getHeight() ? player.getY() : y);
                        break;
                    }
                    case KeyEvent.VK_A: {
                        int x = player.getX() - fieldWidth;
                        player.setX(x < 0 ? player.getX() : x);
                        break;
                    }
                    case KeyEvent.VK_D: {
                        int x = player.getX() + fieldWidth;
                        player.setX(x > getWidth() ? player.getX() : x);
                        break;
                    }
                }
                repaint();
            }
        });
    }

    public int getRows() {
        return rows;
    }

    @Override
    public void paint(Graphics graphics) {
        fieldWidth = getDimension() / rows;
        adjustScore();
        drawLines();
        drawFigures();
    }

    public void pack() {
        int dimension = getDimension();
        int oldFieldWidth = fieldWidth;
        fieldWidth = dimension / rows;
        int x, y;
        for (Figure figure : figures) {
            x = ((int) (figure.getX() / 1.0 / oldFieldWidth) * fieldWidth) + fieldWidth / 2;
            y = ((int) (figure.getY() / 1.0 / oldFieldWidth) * fieldWidth) + fieldWidth / 2;
            figure.setX(x);
            figure.setY(y);
            figure.setWidth(fieldWidth / 2);
        }
        if (player != null) {
            player.setWidth(fieldWidth);
        }
        setPreferredSize(new Dimension(dimension, dimension));
    }

    public void tossCoins(int coins) {
        owner.scoreLabel.setText("");
        owner.logTextArea.setText("");
        player = null;
        figures = new ArrayList<>();
        score = 0;

        while (coins >= rows * rows) {
            coins /= 4;
        }
        owner.coinsTextField.setText("" + coins);

        int x, y, radius = fieldWidth / 2;
        for (int i = 0; i < coins; ++i) {
            x = (int) (Math.random() * rows) * fieldWidth + radius;
            y = (int) (Math.random() * rows) * fieldWidth + radius;
            Coin coin = new Coin(x, y, radius);
            if (figures.contains(coin)) {
                i--;
                continue;
            }
            figures.add(coin);
        }

        do {
            x = (int) (Math.random() * rows) * fieldWidth + fieldWidth / 2;
            y = (int) (Math.random() * rows) * fieldWidth + fieldWidth / 2;
            player = new Player(x, y, fieldWidth);
        } while (figures.contains(player));
        figures.add(player);
    }

    private void adjustScore() {
        for (Figure figure : figures) {
            if (player.equals(figure) && player != figure) {
                ++score;
                owner.scoreLabel.setText("" + score);
                owner.logTextArea.append("Coin collected at: " + owner.timer + "\n");
                figures.remove(figure);
                if (figures.size() == 1) {
                    owner.timer.getThread().interrupt();
                    owner.setVictory(owner.timer.toString(), score);
                    owner.scoreLabel.setText("");
                    owner.logTextArea.setText("");
                    owner.elapsedTimeLabel.setText("");
                    figures.remove(player);
                    figures = null;
                    player = null;
                }
                break;
            }
        }
    }

    private void drawLines() {
        int dimension = getDimension();
        int step = dimension / rows;
        Graphics graphics = getGraphics();
        graphics.setColor(Color.GRAY);
        for (int i = 0; i < dimension; i += step) {
            graphics.drawLine(0, i, dimension - 1, i);
            graphics.drawLine(i, 0, i, dimension - 1);
        }
    }

    private void drawFigures() {
        if (player == null) {
        }
        for (Figure figure : figures) {
            figure.draw(getGraphics());
        }
        player.draw(getGraphics());
    }

    private int getDimension() {
        int ownerWidth = owner.gamePanel.getWidth();
        int ownerHeight = owner.gamePanel.getHeight();
        int width = ownerWidth / rows * rows;
        int height = ownerHeight / rows * rows;
        return Math.max(width, height);
    }
}
