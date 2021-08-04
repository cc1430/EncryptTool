package chenchen.demo.utils;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @date 2020/4/20 16:55
 * @create ltx
 * @description
 */
public class Sign {
    /**
     * 生成sign
     *
     * @return String
     */
    public static String generateSign(String body) {
        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
        JsonObject system = jsonObject.getAsJsonObject("system");
        JsonObject params = jsonObject.getAsJsonObject("params");
        String security = jointKeyValue(system) + jointKeyValue(params) + GlobalDef.CLIENT_SECRET;
        return MD5.getEncode(security);
    }

    /**
     * 拼接对象
     *
     * @param jsonObject jsonObject
     * @return String
     */
    private static String jointKeyValue(JsonObject jsonObject) {
        if (jsonObject == null) {
            return "";
        }

        Set<String> set = jsonObject.keySet();
        Set<String> keySet = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        for (String key : set) {
            keySet.add(key);
        }

        StringBuilder data = new StringBuilder();
        for (String key : keySet) {
            if (key.equals("sign")) {
                continue;
            }
            String value = null;
            JsonElement jsonElement = jsonObject.get(key);
            if (null != jsonElement && jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                if (null != jsonArray && jsonArray.size() > 0) {
                    value = jsonArray.toString();
                }
            } else if (null != jsonElement && jsonElement.isJsonPrimitive()) {
                value = jsonElement.getAsJsonPrimitive().getAsString();
            } else if (null != jsonElement && jsonElement.isJsonObject()) {
                value = jsonElement.getAsJsonObject().toString();
            }
            if (null != value && !value.isEmpty()) {
                data.append(key).append("=").append(value).append("||");
            }
        }
        return data.toString();
    }

}
