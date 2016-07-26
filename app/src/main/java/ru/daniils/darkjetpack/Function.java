package ru.daniils.darkjetpack;

/**
 * Created by DVS on 08.06.2016.
 */
public class Function {
    public boolean takeArgs;

    public Function(boolean _takeArgs) {
        takeArgs = _takeArgs;
    }

    public void doThis() {
    }

    public void doThis(Object... args) {
    }
}
