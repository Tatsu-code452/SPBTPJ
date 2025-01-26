/**
 * 
 */
package com.manage.helper.SPBTPJ1.BusinessLogic.Logic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.manage.helper.Dao.FilePathDaoDataProvider;
import com.manage.helper.Dao.DaoModel.FilePathDto;
import com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertBDto;
import com.manage.helper.com.TestCommon;

/**
 * @author Owner
 *
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SPBTPJ1_InsertLogicTest extends TestCommon<SPBTPJ1_InsertLogic> {

    @InjectMocks
    private SPBTPJ1_InsertLogic logic;

    private FilePathDaoDataProvider filePathDaoDataProvider = new FilePathDaoDataProvider();

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        super.fieldMap.put("filePathDao", filePathDaoDataProvider.getFilePathDao());
        super.setUp(logic);

    }

    /**
     * @throws java.lang.Exception
     */
    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * {@link com.manage.helper.SPBTPJ1.BusinessLogic.Logic.SPBTPJ1_InsertLogic#loadData(com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertBDto)} のためのテスト・メソッド。
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    @Test
    final void testLoadDataSPBTPJ1_InsertBDto()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        SPBTPJ1_InsertBDto result = new SPBTPJ1_InsertBDto();

        when(filePathDaoDataProvider.readAll())
                .thenReturn(filePathDaoDataProvider.createMultiFilePathDtoList());

        assertTrue(logic.loadData(result));
        assertEquals(expectedFilePathList(), getField(logic, "filePathList"));
    }

    /**
     * {@link com.manage.helper.SPBTPJ1.BusinessLogic.Logic.SPBTPJ1_InsertLogic#createData(com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertBDto)} のためのテスト・メソッド。
     */
    @Test
    final void testCreateDataSPBTPJ1_InsertBDto() {
    }

    /**
     * {@link com.manage.helper.SPBTPJ1.BusinessLogic.Logic.SPBTPJ1_InsertLogic#saveData(com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertBDto)} のためのテスト・メソッド。
     */
    @Test
    final void testSaveDataSPBTPJ1_InsertBDto() {
    }

    private List<FilePathDto> expectedFilePathList() {
        return filePathDaoDataProvider.createMultiFilePathDtoList();
    }

}
