package com.manage.helper.Dao;

import java.util.ArrayList;
import java.util.List;

import com.manage.helper.Dao.DaoModel.FilePathGroupDto;

public class FilePathGroupDaoTest {

    public FilePathGroupDto createData1(Integer groupId) {
        String sGroupId = groupId.toString();

        return new FilePathGroupDto(sGroupId, "group" + sGroupId);
    }

    public List<FilePathGroupDto> createData2() {
        List<FilePathGroupDto> result = new ArrayList<FilePathGroupDto>();

        result.add(createData1(1));
        result.add(createData1(2));
        result.add(createData1(3));

        return result;
    }

}
