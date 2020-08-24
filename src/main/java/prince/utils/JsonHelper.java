package prince.utils;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class JsonHelper {
    public static Map<String, Object> getMap(String path) {
        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject object = new JSONObject(Files.readString(Path.of(path)));
            map = object.toMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
