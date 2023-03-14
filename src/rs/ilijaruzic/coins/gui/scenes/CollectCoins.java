package rs.ilijaruzic.coins.gui.scenes;

import rs.ilijaruzic.coins.util.Timer;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CollectCoins extends Frame {
    private final GameScene gameScene = new GameScene(this);
    private final Panel settingsPanel = new Panel();
    Label elapsedTimeLabel = new Label();
    Panel gamePanel = new Panel();
    TextArea logTextArea = new TextArea();
    Label scoreLabel = new Label();
    TextField coinsTextField = new TextField();
    Timer timer;

    public CollectCoins() {
        setBounds(700, 200, 800, 800);
        setTitle("Collect coins");
        setHowTo();
        populate();
        setResizable(true);
        pack();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gameScene.pack();
                gameScene.repaint();
                pack();
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (timer != null) {
                    timer.getThread().interrupt();
                }
                dispose();
            }
        });
        setVisible(true);
    }

    private void setHowTo() {
        Dialog howToDialog = new Dialog(this, Dialog.ModalityType.APPLICATION_MODAL);
        howToDialog.setBounds(700, 200, 400, 200);
        howToDialog.setResizable(false);
        howToDialog.setTitle("How to play?");
        howToDialog.add(new Label("Use WASD to move and start collecting coins.", Label.CENTER));
        howToDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                howToDialog.dispose();
            }
        });
        howToDialog.setVisible(true);
    }

    void setVictory(String elapsedTime, int score) {
        Dialog victoryDialog = new Dialog(this, Dialog.ModalityType.APPLICATION_MODAL);
        victoryDialog.setBounds(700, 200, 400, 200);
        victoryDialog.setResizable(false);
        victoryDialog.setTitle("You won!");
        victoryDialog.add(new Label("Congrats! You won (score:" + score + ", elapsed time: " + elapsedTime + ")", Label.CENTER));
        victoryDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                victoryDialog.dispose();
            }
        });
        victoryDialog.setVisible(true);
    }

    private void populate() {
        logTextArea.setColumns(20);
        logTextArea.setRows(1);
        logTextArea.setEditable(false);
        add(logTextArea, BorderLayout.EAST);

        int dimension = (getWidth() / 2) / gameScene.getRows() * gameScene.getRows();
        gameScene.setBackground(Color.LIGHT_GRAY);
        gameScene.setPreferredSize(new Dimension(dimension, dimension));
        gamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        gamePanel.add(gameScene);
        add(gamePanel, BorderLayout.CENTER);

        coinsTextField = new TextField("10");
        Button tossButton = new Button("Toss!");
        tossButton.addActionListener(e -> {
            gameScene.tossCoins(Integer.parseInt(coinsTextField.getText()));
            gameScene.repaint();
            if (timer != null) {
                timer.getThread().interrupt();
            }
            timer = new Timer(elapsedTimeLabel);
            timer.getThread().start();
            timer.startTimer();
            gameScene.requestFocus();
        });
        settingsPanel.add(new Label("Elapsed time:"));
        settingsPanel.add(elapsedTimeLabel);
        settingsPanel.add(new Label("Score:"));
        settingsPanel.add(scoreLabel);
        settingsPanel.add(new Label("Coins:"));
        settingsPanel.add(coinsTextField);
        settingsPanel.add(tossButton);
        add(settingsPanel, BorderLayout.SOUTH);
    }
}
