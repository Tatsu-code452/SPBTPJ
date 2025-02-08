/**
 * 
 */
package com.manage.helper.SPBTPJ1.BusinessLogic.Logic;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.manage.helper.DAO.FilePathDaoDataProvider;
import com.manage.helper.DAO.DaoModel.FilePathDto;
import com.manage.helper.DAO.DaoModel.FilePathInfoDto;
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
	private SPBTPJ1_InsertLogic instance;

	@InjectMocks
	private FilePathDaoDataProvider filePathDaoDataProvider;

	/**
	 * コンストラクタ
	 */
	public SPBTPJ1_InsertLogicTest() {
		MockitoAnnotations.openMocks(this);
		// テスト対象ロジック作成
		super.testLogic = instance;
		// 依存するモックインスタンスを登録
		registerMock("filePathDao", filePathDaoDataProvider.getFilePathDao());
	}

	/**
	 * {@link com.manage.helper.SPBTPJ1.BusinessLogic.Logic.SPBTPJ1_InsertLogic#loadData(com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertBDto)}
	 * のためのテスト・メソッド。
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@Test
	final void testLoadDataSPBTPJ1_InsertBDto() throws Exception {
		SPBTPJ1_InsertBDto input = new SPBTPJ1_InsertBDto();

		assertTrue(testLogic.loadData(input));
		assertEquals(expectedFilePathList(), getField("filePathList"));
	}

	/**
	 * {@link com.manage.helper.SPBTPJ1.BusinessLogic.Logic.SPBTPJ1_InsertLogic#createData(com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertBDto)}
	 * のためのテスト・メソッド。
	 */
	@Test
	final void testCreateDataSPBTPJ1_InsertBDto() throws Exception {
		SPBTPJ1_InsertBDto input = new SPBTPJ1_InsertBDto("4", "testName", "testPath\\path\\テスト");

		// TODO Stringソートのため、1-100-2-200の順となる
		testLoadDataSPBTPJ1_InsertBDto();

		assertTrue(testLogic.createData(input));
		assertEquals(expectedFilePathListAdded(input), getField("filePathList"));
	}

	/**
	 * {@link com.manage.helper.SPBTPJ1.BusinessLogic.Logic.SPBTPJ1_InsertLogic#saveData(com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertBDto)}
	 * のためのテスト・メソッド。
	 * 
	 * @throws Exception
	 */
	@Test
	final void testSaveDataSPBTPJ1_InsertBDto() throws Exception {
		SPBTPJ1_InsertBDto input = new SPBTPJ1_InsertBDto("4", "testName", "testPath\\path\\テスト");
		testLoadDataSPBTPJ1_InsertBDto();
		testCreateDataSPBTPJ1_InsertBDto();
		assertTrue(testLogic.saveData(input));
	}

	/**
	 * {@link com.manage.helper.COMMON.BaseLogic#execute(java.lang.Object)}
	 * のためのテスト・メソッド。
	 */
	@Test
	final void testExecute() {
		SPBTPJ1_InsertBDto input = new SPBTPJ1_InsertBDto("4", "testName", "testPath\\path\\テスト");
		assertTrue(testLogic.execute(input));
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
