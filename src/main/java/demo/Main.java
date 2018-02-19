package demo;

import engine.core.CoreEngine;

public class Main {

    public static void main(String[] args) {
        CoreEngine engine = CoreEngine.getInstance();
        DemoGame game = new DemoGame();
        engine.setGame(game);
        engine.start();
    }
}
