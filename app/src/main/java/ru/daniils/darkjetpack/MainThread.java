package ru.daniils.darkjetpack;


import ru.daniils.darkjetpack.graphics.GameView;
import ru.daniils.darkjetpack.graphics.Scene;
import ru.daniils.darkjetpack.scenes.GameScene;

public class MainThread extends Thread {
    public GameView gameView;
    public Scene newScene;
    private SceneLoader sLoader;
    private boolean replacingScenes = false;

    MainThread(GameView _gameView) {
        gameView = _gameView;
    }

    public void run() {
        try {
            while (gameView.getWidth() == 0)
                Thread.sleep(Core.MPT);
        } catch (InterruptedException ignored) {
        }
        replaceScene(new GameScene());
        while (Core.working) {
            tick();
            try {
                Thread.sleep(Core.MPT);
            } catch (InterruptedException ignored) {
            }
        }
        Core.activity.finish();
        System.exit(0);
    }

    private void tick() {
        if (replacingScenes) {
            if (newScene.loaded && (gameView.curScene == null || gameView.curScene.fadeLayer.state == 0)) {
                gameView.curScene = newScene;
                replacingScenes = false;
                gameView.curScene.fadeLayer.in();
            }
        }
        if (gameView.curScene != null)
            gameView.curScene.onTick();

    }

    void replaceScene(Scene s) {
        if (s == null)
            return;
        replacingScenes = true;
        newScene = s;
        sLoader = new SceneLoader(newScene);
        if (gameView.curScene != null) {
            gameView.curScene.fadeLayer.out();
            newScene.fadeLayer.setColors(gameView.curScene.fadeLayer.outColor, -1);
        }
    }

    private class SceneLoader extends Thread {
        Scene s;

        SceneLoader(Scene _s) {
            s = _s;
            start();
        }

        public void run() {
            s.onLoad();
        }
    }
}