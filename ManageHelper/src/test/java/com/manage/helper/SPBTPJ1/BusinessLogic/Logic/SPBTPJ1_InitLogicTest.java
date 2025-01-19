/**
 * 
 */
package com.manage.helper.SPBTPJ1.BusinessLogic.Logic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.manage.helper.Dao.FilePathDao;
import com.manage.helper.Dao.FilePathDaoTest;
import com.manage.helper.Dao.FilePathGroupDao;
import com.manage.helper.Dao.FilePathGroupDaoTest;
import com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InitBDto;
import com.manage.helper.SPBTPJ1.ViewModel.SPBTPJ1_ViewModel;

/**
 * @author Owner
 *
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SPBTPJ1_InitLogicTest {
    private AutoCloseable closeable;

    @Mock
    private FilePathDao filePathDao;
    @Mock
    private FilePathGroupDao filePathGroupDao;
    @InjectMocks
    private SPBTPJ1_InitLogic logic;

    private FilePathDaoTest filePathDaoTest;
    private FilePathGroupDaoTest filePathGroupDaoTest;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUpBeforeClass() throws Exception {
        closeable = MockitoAnnotations.openMocks(this);
        filePathGroupDaoTest = new FilePathGroupDaoTest();
        filePathDaoTest = new FilePathDaoTest();

        Map<String, Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("filePathGroupDao", filePathGroupDao);
        fieldMap.put("filePathDao", filePathDao);

        var mockedClass = SPBTPJ1_InitLogic.class;
        fieldMap.entrySet().forEach(m -> {
            try {
                Field field = mockedClass.getDeclaredField(m.getKey());
                field.setAccessible(true);
                field.set(logic, m.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterEach
    void tearDownAfterClass() throws Exception {
        closeable.close();
    }

    /**
     * {@link com.manage.helper.COMMON.BaseLogic#execute(java.lang.Object)} のためのテスト・メソッド。
     * @throws Exception 
     */
    @Test
    final void testExecute()
            throws Exception {
        SPBTPJ1_InitBDto result = new SPBTPJ1_InitBDto();
        result.setViewModel(new SPBTPJ1_ViewModel());

        when(filePathGroupDao.readAll()).thenReturn(filePathGroupDaoTest.createFilePathGroupDtoList());
        when(filePathDao.readAll()).thenReturn(filePathDaoTest.createMultiFilePathDtoList());
        logic.execute(result);

        assertEquals(createFilePathGroupMap(), result.getViewModel().getFilePathGroupMap());
        assertEquals(filePathDaoTest.createMultiFilePathDtoList(), result.getViewModel().getFilePathList());
    }

    private Map<String, String> createFilePathGroupMap() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("1", "group1");
        result.put("2", "group2");
        result.put("3", "group3");
        return result;
    }
}
