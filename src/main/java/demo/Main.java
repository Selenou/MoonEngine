package demo;

import core.kernel.CoreEngine;

public class Main {

    public static void main(String[] args) {
        CoreEngine engine = new CoreEngine();
        DemoGame game = new DemoGame();

        // We do not instance the game in the core engine's constructor since we need some opengl's init stuff before the fun begins
        engine.setGame(game);

        engine.start();
    }
}
