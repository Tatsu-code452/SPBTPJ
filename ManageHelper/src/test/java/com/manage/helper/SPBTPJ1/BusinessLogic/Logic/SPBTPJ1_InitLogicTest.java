/**
 * 
 */
package com.manage.helper.SPBTPJ1.BusinessLogic.Logic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.manage.helper.Dao.FilePathDaoDataProvider;
import com.manage.helper.Dao.FilePathGroupDaoDataProvider;
import com.manage.helper.Dao.DaoModel.FilePathDto;
import com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InitBDto;
import com.manage.helper.SPBTPJ1.ViewModel.SPBTPJ1_ViewModel;
import com.manage.helper.com.TestCommon;

/**
 * @author Owner
 *
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SPBTPJ1_InitLogicTest extends TestCommon<SPBTPJ1_InitLogic> {

    @InjectMocks
    private SPBTPJ1_InitLogic instance;

    @InjectMocks
    private FilePathDaoDataProvider filePathDaoDataProvider;

    @InjectMocks
    private FilePathGroupDaoDataProvider filePathGroupDaoDataProvider;

    /**
     * コンストラクタ
     */
    public SPBTPJ1_InitLogicTest() {
        MockitoAnnotations.openMocks(this);
        // テスト対象ロジック作成
        super.testLogic = instance;
        // 依存するモックインスタンスを登録
        registerMock("filePathGroupDao",
                filePathGroupDaoDataProvider.getFilePathGroupDao());
        registerMock("filePathDao",
                filePathDaoDataProvider.getFilePathDao());
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

        assertTrue(testLogic.execute(result));
        assertEquals(expectedFilePathGroupMap(), result.getViewModel().getFilePathGroupMap());
        assertEquals(expectedFilePathList(), result.getViewModel().getFilePathList());
    }

    private Map<String, String> expectedFilePathGroupMap() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("1", "group1");
        result.put("2", "group2");
        result.put("3", "group3");
        return result;
    }

    private List<FilePathDto> expectedFilePathList() {
        return filePathDaoDataProvider.createMultiFilePathDtoList();
    }
}
