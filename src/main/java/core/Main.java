package core;

import core.kernel.CoreEngine;

public class Main {

    public static void main(String[] args) {
        CoreEngine engine = new CoreEngine(1280, 720, 30);
        engine.start();
    }
}
