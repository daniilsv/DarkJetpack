package ru.daniils.darkjetpack;

import android.app.Activity;
import android.os.Bundle;
import ru.daniils.darkjetpack.graphics.GameView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView gv = new GameView(this);
        setContentView(gv);
        Core core = Core.getInstance();
        core.setContext(this);
        core.start(gv);
    }

    @Override
    public void onBackPressed() {
        if (Core.working) {
            Core.mainThread.gameView.curScene.onBackPressed();
        } else
            super.onBackPressed();
    }
}
