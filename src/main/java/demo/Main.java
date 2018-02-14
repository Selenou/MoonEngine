package demo;

import core.kernel.CoreEngine;

public class Main {

    public static void main(String[] args) {
        CoreEngine engine = new CoreEngine(new DemoGame());
        engine.start();
    }
}
