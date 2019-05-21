package mobi.mixiong.util;

import java.util.List;

public class ListUtils {

    public static <V> boolean isEmpty(List<V> sourceList) {
        return sourceList == null || sourceList.isEmpty();
    }

    public static <V> boolean isNotEmpty(List<V> sourceList) {
        return sourceList != null && !sourceList.isEmpty();
    }
}
