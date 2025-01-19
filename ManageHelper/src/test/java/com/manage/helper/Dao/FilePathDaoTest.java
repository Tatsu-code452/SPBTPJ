/**
 * 
 */
package com.manage.helper.Dao;

import java.util.ArrayList;
import java.util.List;

import com.manage.helper.Dao.DaoModel.FilePathDto;
import com.manage.helper.Dao.DaoModel.FilePathInfoDto;

/**
 * @author Owner
 *
 */
public class FilePathDaoTest {

    public FilePathInfoDto createData1(Integer ren) {
        String sRen = ren.toString();
        return new FilePathInfoDto("NAME" + sRen, "PATH" + sRen, "ENCPATH" + sRen);
    }

    public List<FilePathDto> createData2(String groupId, Integer startRen) {
        List<FilePathDto> result = new ArrayList<FilePathDto>();

        List<FilePathInfoDto> wkFilePathInfoDtoList = new ArrayList<FilePathInfoDto>();
        wkFilePathInfoDtoList.add(createData1(startRen));
        wkFilePathInfoDtoList.add(createData1(startRen++));
        wkFilePathInfoDtoList.add(createData1(startRen++));
        result.add(new FilePathDto(groupId, wkFilePathInfoDtoList));

        return result;
    }

    public List<FilePathDto> createData3() {
        List<FilePathDto> result = new ArrayList<FilePathDto>();

        result.addAll(createData2("1", 1));
        result.addAll(createData2("2", 5));
        result.addAll(createData2("3", 10));

        return result;
    }

}
