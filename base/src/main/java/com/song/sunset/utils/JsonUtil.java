package com.song.sunset.utils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Song on 2016/11/4.
 */
public class JsonUtil {

    private static Gson gson;

    private static final JsonParser PARSER = new JsonParser();

    static {
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String gsonToString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        try {
            if (gson != null) {
                t = gson.fromJson(gsonString, cls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> gsonToList(String gsonString, Class<T[]> cls) {
        return Arrays.asList(new Gson().fromJson(gsonString, cls));
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> gsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            if (gson != null) {
                list = gson.fromJson(gsonString,
                        new TypeToken<List<Map<String, T>>>() {
                        }.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> gsonToMaps(String gsonString) {
        Map<String, T> map = null;
        try {
            if (gson != null) {
                map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
                }.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * JsonArray字串转换成普通List对象
     * @param jsonArrayStr
     * @param elementCls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonStringToList(
            String jsonArrayStr, @NonNull Class<T> elementCls) {
        return jsonStringToGenericList(jsonArrayStr, elementCls);
    }

    /**
     * JsonArray字串转换成泛型List对象
     * @param jsonArrayStr
     * @param elementType 泛型对象类型，一般需要配合TypeToken，
     *                    比如ArrayList&lt;String&gt;，
     *                    则需要传new TypeToken&lt;ArrayList&lt;String&gt;&gt; { }.getType()
     * @param <T>
     * @return
     */
    @NonNull
    public static <T> List<T> jsonStringToGenericList(String jsonArrayStr, @NonNull Type elementType) {
        JsonArray array = stringToJsonArray(jsonArrayStr);
        return jsonArrayToGenericList(array, elementType);
    }

    /**
     * JsonArray字串转换成JsonArray
     * @param str
     * @return
     */
    public static JsonArray stringToJsonArray(String str) {
        try {
            return PARSER.parse(str).getAsJsonArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * JsonArray字串转换成泛型List对象
     * @param array
     * @param elementType 泛型对象类型，一般需要配合TypeToken，
     *                    比如ArrayList&lt;String&gt;，
     *                    则需要传new TypeToken&lt;ArrayList&lt;String&gt;&gt; { }.getType()
     * @param <T>
     * @return
     */
    @NonNull
    public static <T> List<T> jsonArrayToGenericList(JsonArray array, @NonNull Type elementType) {
        List<T> result = new ArrayList<>();
        if (array == null) {
            return result;
        }

        try {
            for (JsonElement element : array) {
                result.add((T) gson.fromJson(element, elementType));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 泛型对象列表转换成Json字串
     * @param objectList 泛型对象列表
     * @param elementType 泛型对象类型，一般需要配合TypeToken，
     *                    比如ArrayList&lt;String&gt;，
     *                    则需要传new TypeToken&lt;ArrayList&lt;String&gt;&gt; { }.getType()
     * @return
     */
    @NonNull
    public static <T> String genericListToJsonString(
            List<T> objectList, @NonNull Type elementType) {
        JsonArray array = new JsonArray();
        try {
            for (T object : objectList) {
                array.add(genericObjectToJson(object, elementType));
            }
            return jsonArrayToString(array);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 泛型object转换成JsonObject
     * @param obj
     * @param type 泛型对象类型，一般需要配合TypeToken，
     *             比如ArrayList&lt;String&gt;，
     *             则需要传new TypeToken&lt;ArrayList&lt;String&gt;&gt; { }.getType()
     * @return
     */
    public static JsonElement genericObjectToJson(Object obj, @NonNull Type type) {
        try {
            return stringToJson(genericObjectToJsonString(obj, type));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * JsonArray转换成字串
     * @param jsonArray
     * @return
     */
    public static String jsonArrayToString(@NonNull JsonArray jsonArray) {
        return jsonArray.toString();
    }

    /**
     * Json字串转换成JsonObject
     * @param str
     * @return
     */
    public static JsonElement stringToJson(String str) {
        try {
            return PARSER.parse(str);
        } catch (Exception e) {
            return gson.toJsonTree(str);
        }
    }

    /**
     * 泛型对象转换成Json字串
     * @param obj 需要转换的Object对象
     * @param type 一般需要配合TypeToken，
     *             比如ArrayList&lt;String&gt;，
     *             则需要传new TypeToken&lt;ArrayList&lt;String&gt;&gt; { }.getType()
     * @return 返回string格式的json
     */
    @Deprecated
    public static String genericObjectToJsonString(Object obj, @NonNull Type type) {
        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
