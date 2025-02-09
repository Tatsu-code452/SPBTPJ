/**
 * 
 */
package com.manage.helper.SPBTPJ1.BusinessLogic.Logic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.manage.helper.DAO.FilePathGroupDaoDataProvider;
import com.manage.helper.DAO.DaoModel.FilePathGroupDto;
import com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertGroupBDto;
import com.manage.helper.com.TestCommon;

/**
 * @author Owner
 *
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SPBTPJ1_InsertGroupLogicTest extends TestCommon<SPBTPJ1_InsertGroupLogic> {

	@InjectMocks
	private SPBTPJ1_InsertGroupLogic instance;

	@InjectMocks
	private FilePathGroupDaoDataProvider filePathGroupDaoDataProvider;

	/**
	 * コンストラクタ
	 */
	public SPBTPJ1_InsertGroupLogicTest() {
		MockitoAnnotations.openMocks(this);
		// テスト対象ロジック作成
		super.testLogic = instance;
		// 依存するモックインスタンスを登録
		registerMock("filePathGroupDao", filePathGroupDaoDataProvider.getFilePathGroupDao());
	}

	/**
	 * {@link com.manage.helper.SPBTPJ1.BusinessLogic.Logic.SPBTPJ1_InsertGroupLogic#loadData(com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertGroupBDto)}
	 * のためのテスト・メソッド。
	 * 
	 * @throws Exception
	 */
	@Test
	void testLoadDataSPBTPJ1_InsertGroupBDto() throws Exception {
		SPBTPJ1_InsertGroupBDto input = new SPBTPJ1_InsertGroupBDto("group4");

		assertTrue(testLogic.loadData(input));
		assertEquals(expectedFilePathGroupDtoList(), getField("filePathGroupList"));
	}

	/**
	 * {@link com.manage.helper.SPBTPJ1.BusinessLogic.Logic.SPBTPJ1_InsertGroupLogic#createData(com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertGroupBDto)}
	 * のためのテスト・メソッド。
	 * 
	 * @throws Exception
	 */
	@Test
	void testCreateDataSPBTPJ1_InsertGroupBDto() throws Exception {
		SPBTPJ1_InsertGroupBDto input = new SPBTPJ1_InsertGroupBDto("group4");

		testLoadDataSPBTPJ1_InsertGroupBDto();
		assertTrue(testLogic.createData(input));
		assertEquals(expectedFilePathGroupDtoListAdded(), getField("filePathGroupList"));
	}

	/**
	 * {@link com.manage.helper.SPBTPJ1.BusinessLogic.Logic.SPBTPJ1_InsertGroupLogic#saveData(com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertGroupBDto)}
	 * のためのテスト・メソッド。
	 * 
	 * @throws Exception
	 */
	@Test
	void testSaveDataSPBTPJ1_InsertGroupBDto() throws Exception {
		SPBTPJ1_InsertGroupBDto input = new SPBTPJ1_InsertGroupBDto("group4");

		testLoadDataSPBTPJ1_InsertGroupBDto();
		testCreateDataSPBTPJ1_InsertGroupBDto();
		assertTrue(testLogic.saveData(input));
	}

	/**
	 * {@link com.manage.helper.COMMON.BaseLogic#execute(java.lang.Object)}
	 * のためのテスト・メソッド。
	 */
	@Test
	void testExecute() {
		SPBTPJ1_InsertGroupBDto input = new SPBTPJ1_InsertGroupBDto("group4");

		assertTrue(testLogic.execute(input));
	}

	private List<FilePathGroupDto> expectedFilePathGroupDtoList() {
		return filePathGroupDaoDataProvider.createFilePathGroupDtoList();
	}

	private List<FilePathGroupDto> expectedFilePathGroupDtoListAdded() {
		List<FilePathGroupDto> result = expectedFilePathGroupDtoList();
		result.add(new FilePathGroupDto("4", "group4", 4));
		return result;
	}

}
