package com.yeetdot.memoria.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Collects static fields from target class
 * <p>
 * Use only for classes such as Items and Blocks
 */
public class FieldCollector {
    public static List<Field> getStaticFields(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        List<Field> staticFields = new ArrayList<>();

        for (Field field : declaredFields) {
            if (Modifier.isStatic(field.getModifiers())) {
                staticFields.add(field);
            }
        }
        return staticFields;
    }
}
