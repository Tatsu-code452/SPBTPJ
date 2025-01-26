/**
 * 
 */
package com.manage.helper.SPBTPJ1.BusinessLogic.Logic;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.manage.helper.Dao.FilePathDaoDataProvider;
import com.manage.helper.Dao.DaoModel.FilePathDto;
import com.manage.helper.Dao.DaoModel.FilePathInfoDto;
import com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertBDto;
import com.manage.helper.com.TestCommon;

/**
 * @author Owner
 *
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SPBTPJ1_InsertLogicTest extends TestCommon<SPBTPJ1_InsertLogic> {

    @InjectMocks
    private SPBTPJ1_InsertLogic logic;

    private FilePathDaoDataProvider filePathDaoDataProvider = new FilePathDaoDataProvider();

    @Override
    public void createMockFieldMap() {
        super.fieldMap.put("filePathDao", filePathDaoDataProvider.getFilePathDao());
    }

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
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
    final void testLoadDataSPBTPJ1_InsertBDto() throws Exception {
        SPBTPJ1_InsertBDto input = new SPBTPJ1_InsertBDto();

        assertTrue(logic.loadData(input));
        assertEquals(expectedFilePathList(), getField(logic, "filePathList"));
    }

    /**
     * {@link com.manage.helper.SPBTPJ1.BusinessLogic.Logic.SPBTPJ1_InsertLogic#createData(com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertBDto)} のためのテスト・メソッド。
     */
    @Test
    final void testCreateDataSPBTPJ1_InsertBDto() throws Exception {
        SPBTPJ1_InsertBDto input = new SPBTPJ1_InsertBDto("4", "testName", "testPath\\path\\テスト");

        // TODO Stringソートのため、1-100-2-200の順となる
        testLoadDataSPBTPJ1_InsertBDto();

        assertTrue(logic.createData(input));
        assertEquals(expectedFilePathListAdded(input), getField(logic, "filePathList"));
    }

    /**
     * {@link com.manage.helper.SPBTPJ1.BusinessLogic.Logic.SPBTPJ1_InsertLogic#saveData(com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertBDto)} のためのテスト・メソッド。
     * @throws Exception 
     */
    @Test
    final void testSaveDataSPBTPJ1_InsertBDto() throws Exception {
        SPBTPJ1_InsertBDto input = new SPBTPJ1_InsertBDto("4", "testName", "testPath\\path\\テスト");
        testLoadDataSPBTPJ1_InsertBDto();
        testCreateDataSPBTPJ1_InsertBDto();
        assertTrue(logic.saveData(input));
    }

    /**
     * {@link com.manage.helper.COMMON.BaseLogic#execute(java.lang.Object)} のためのテスト・メソッド。
     */
    @Test
    final void testExecute() {
        SPBTPJ1_InsertBDto input = new SPBTPJ1_InsertBDto("4", "testName", "testPath\\path\\テスト");
        assertTrue(logic.execute(input));
    }

    private List<FilePathDto> expectedFilePathList() {
        return filePathDaoDataProvider.createMultiFilePathDtoList();
    }

    private List<FilePathDto> expectedFilePathListAdded(SPBTPJ1_InsertBDto input) {
        FilePathDto work = new FilePathDto(input.getGroupId(), new ArrayList<FilePathInfoDto>());
        work.getPathList().add(new FilePathInfoDto(input.getName(), input.getPath(),
                URLEncoder.encode(input.getPath(), StandardCharsets.UTF_8)));
        List<FilePathDto> expected = expectedFilePathList();
        expected.add(work);
        return expected;
    }

}
