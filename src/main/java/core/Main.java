package core;

import core.kernel.CoreEngine;
import core.kernel.Game;

public class Main {

    public static void main(String[] args) {
        CoreEngine engine = new CoreEngine(800, 600, new Game ());
        engine.start();
    }
}
