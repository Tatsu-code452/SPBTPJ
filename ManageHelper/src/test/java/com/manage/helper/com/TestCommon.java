package com.manage.helper.com;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class TestCommon<T> {
    protected AutoCloseable closeable;
    protected Map<String, Object> mockFieldMap = new HashMap<String, Object>();
    protected boolean isSetup = false;
    protected T testLogic;

    @BeforeEach
    public void setUp() {
        if (isSetup) {
            return;
        }
        closeable = MockitoAnnotations.openMocks(this);

        initMocks();
        isSetup = true;
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    protected void initMocks() {
        for (var entry : mockFieldMap.entrySet()) {
            try {
                Field field = testLogic.getClass().getDeclaredField(entry.getKey());
                field.setAccessible(true);
                field.set(testLogic, entry.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void registerMock(String fieldName, Object mockInstance) {
        mockFieldMap.put(fieldName, mockInstance);
    }

    public Object getField(String fieldName) throws Exception {
        Field field = testLogic.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(testLogic);
    }
}
