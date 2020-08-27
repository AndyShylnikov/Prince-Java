package prince.loaders;

import prince.utils.GameStorage;

public abstract class BaseLoader {
    protected GameStorage storage;

    public BaseLoader() {
        storage = GameStorage.getInstance();
    }

    public abstract void loadItems();
}
