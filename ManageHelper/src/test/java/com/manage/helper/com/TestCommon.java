package com.manage.helper.com;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.mockito.MockitoAnnotations;

public abstract class TestCommon<T> {
    protected AutoCloseable closeable;
    protected Map<String, Object> fieldMap = new HashMap<String, Object>();
    protected boolean isSetup = false;

    public void setUp(T logic) {
        if (isSetup) {
            return;
        }

        closeable = MockitoAnnotations.openMocks(this);

        var mockedClass = logic.getClass();
        createMockFieldMap();
        fieldMap.entrySet().forEach(m -> {
            try {
                Field field = mockedClass.getDeclaredField(m.getKey());
                field.setAccessible(true);
                field.set(logic, m.getValue());

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        isSetup = true;
    }

    public void createMockFieldMap() {

    }

    public void tearDown() throws Exception {
        closeable.close();
    }

    public Object getField(T logic, String fieldName)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field field = logic.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(logic);
    }
}
