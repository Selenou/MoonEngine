package core.utils;

import java.util.HashMap;

public class TextureCache {

    private static TextureCache instance = null;

    private HashMap<String, Integer> textureMap;

    public static TextureCache getInstance() {
        if(instance == null){
            instance = new TextureCache();
        }
        return instance;
    }

    private TextureCache() {
        this.textureMap = new HashMap<>();
    }

    public HashMap<String, Integer> getTextureMap() {
        return this.textureMap;
    }

    public boolean contains(String textureName) {
        return this.textureMap.containsKey(textureName);
    }

    public int getTextureId(String textureName) {
        return this.textureMap.get(textureName);
    }
}
