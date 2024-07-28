package saludity.patient.Persistence.Utils;

import java.lang.reflect.Field;


public class PatchUtils {

    public static <T> Object applyPatch(T target, T patch) throws IllegalAccessException {
        Field[] fields = patch.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(patch);

            // Verifica si el valor es null o el valor predeterminado del tipo primitivo
            if (value != null && !isDefaultValue(field, value)) {
                field.set(target, value);
            }
            field.setAccessible(false);
        }
        return null;
    }

    private static boolean isDefaultValue(Field field, Object value) {
        if (field.getType().isPrimitive()) {
            if (field.getType() == byte.class && (byte) value == 0) {
                return true;
            }
        }
        return false;
    }
}
