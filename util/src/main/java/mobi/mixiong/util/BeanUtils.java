package mobi.mixiong.util;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BeanUtils extends org.springframework.beans.BeanUtils {

    public static void copyMap(Map<String, ?> map, Object target) throws BeansException {
        copyMap(map, target, (String[]) null);
    }

    public static void copyMap(Map<String, ?> map, Object target, String... ignoreProperties) throws BeansException {

        Assert.notNull(map, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();

        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                try {
                    Object value = map.get(targetPd.getName());
                    if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                        writeMethod.setAccessible(true);
                    }
                    if (value != null) {
                        setValue(writeMethod, target, value);
                    } else {
                        String underLineName = underlining(targetPd.getName());
                        value = map.get(underLineName);
                        if (value != null) {
                            setValue(writeMethod, target, value);
                        }
                    }
                } catch (Throwable throwable) {
                    throw new FatalBeanException("Could not copy property ", throwable);
                }
            }
        }
    }

    private static void setValue(Method writeMethod, Object target, Object value) throws InvocationTargetException,
            IllegalAccessException {
        Class requiredClass = writeMethod.getParameterTypes()[0];
        Object realValue = value;
        if (requiredClass.isAssignableFrom(Byte.class) || requiredClass == Byte.TYPE) {
            realValue = Byte.valueOf(value.toString());
        } else if (requiredClass.isAssignableFrom(Boolean.class) || requiredClass == Boolean.TYPE) {
            realValue = Boolean.valueOf(value.toString());
        } else if (requiredClass.isAssignableFrom(Short.class) || requiredClass == Short.TYPE) {
            realValue = Short.valueOf(value.toString());
        } else if (requiredClass.isAssignableFrom(Integer.class) || requiredClass == Integer.TYPE) {
            realValue = Integer.valueOf(value.toString());
        } else if (requiredClass.isAssignableFrom(Long.class) || requiredClass == Long.TYPE) {
            realValue = Long.valueOf(value.toString());
        } else if (requiredClass.isAssignableFrom(Float.class) || requiredClass == Float.TYPE) {
            realValue = Float.valueOf(value.toString());
        } else if (requiredClass.isAssignableFrom(Double.class) || requiredClass == Double.TYPE) {
            realValue = Double.valueOf(value.toString());
        } else if (requiredClass.isAssignableFrom(Character.class) || requiredClass == Character.TYPE) {
            realValue = (char) value;
        } else if (requiredClass.isAssignableFrom(Date.class) && value instanceof Long) {
            realValue = new Date((long) value);
        }

        writeMethod.invoke(target, realValue);
    }

    public static void copyProperties(Object source, Object target) throws BeansException {
        copyProperties(source, target, null, (String[]) null);
    }

    /**
     * Copy the property values of the given source bean into the given target bean,
     * only setting properties defined in the given "editable" class (or interface).
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     * <p>This is just a convenience method. For more complex transfer needs,
     * consider using a full BeanWrapper.
     *
     * @param source   the source bean
     * @param target   the target bean
     * @param editable the class (or interface) to restrict property setting to
     * @throws BeansException if the copying failed
     */
    public static void copyProperties(Object source, Object target, Class<?> editable) throws BeansException {
        copyProperties(source, target, editable, (String[]) null);
    }

    /**
     * Copy the property values of the given source bean into the given target bean,
     * ignoring the given "ignoreProperties".
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     * <p>This is just a convenience method. For more complex transfer needs,
     * consider using a full BeanWrapper.
     *
     * @param source           the source bean
     * @param target           the target bean
     * @param ignoreProperties array of property names to ignore
     * @throws BeansException if the copying failed
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) throws BeansException {
        copyProperties(source, target, null, ignoreProperties);
    }

    /**
     * Copy the property values of the given source bean into the given target bean.
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     *
     * @param source           the source bean
     * @param target           the target bean
     * @param editable         the class (or interface) to restrict property setting to
     * @param ignoreProperties array of property names to ignore
     * @throws BeansException if the copying failed
     */
    private static void copyProperties(Object source, Object target, Class<?> editable, String... ignoreProperties)
            throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() + "] not assignable" +
                        " to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod
                            .getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            if (value != null) {
                                writeMethod.invoke(target, value);
                            }
                        } catch (Throwable ex) {
                            throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from " +
                                    "source to target", ex);
                        }
                    }
                }
            }
        }
    }

    public static Map<String, Object> propertiesToMap(Object object) throws BeansException {
        return propertiesToMap(object, "nullValue", "cacheKey", "class");
    }

    public static Map<String, Object> propertiesToMap(Object object, String... ignoreProperties) throws BeansException {
        Class<?> actualEditable = object.getClass();
        Map<String, Object> map = Maps.newHashMap();

        PropertyDescriptor[] sourcePds = org.springframework.beans.BeanUtils.getPropertyDescriptors(actualEditable);
        List<String> ignoreList = ignoreProperties != null ? Arrays.asList(ignoreProperties) : null;
        int pdCount = sourcePds.length;

        for (int i = 0; i < pdCount; ++i) {
            PropertyDescriptor sourcePd = sourcePds[i];
            if (ignoreList == null || !ignoreList.contains(sourcePd.getName())) {
                Method readMethod = sourcePd.getReadMethod();
                if (readMethod != null) {
                    try {
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }

                        Object value = readMethod.invoke(object);
                        map.put(underlining(sourcePd.getName()), value);
                    } catch (Throwable throwable) {
                        throw new FatalBeanException("Could not copy property ", throwable);
                    }
                }
            }
        }
        return map;
    }

    final static Pattern pattern = Pattern.compile("(([A-Z]*[^A-Z]+)|([A-Z]$))");
    final public static char UNDERLINING_CHAR = '_';

    public static String underlining(String s) {
        List<String> words = new ArrayList<>();
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            words.add(matcher.group());
        }
        return Joiner.on(UNDERLINING_CHAR).join(words).toLowerCase();
    }

    public static void main(String[] args) {
        System.out.println(underlining("aBcD"));
        System.out.println(underlining("aBcDe"));
        System.out.println(underlining("BcDe"));
    }
}
