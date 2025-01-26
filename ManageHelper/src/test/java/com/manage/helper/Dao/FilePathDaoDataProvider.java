/**
 * 
 */
package com.manage.helper.Dao;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.manage.helper.Dao.DaoModel.FilePathDto;
import com.manage.helper.Dao.DaoModel.FilePathInfoDto;

import lombok.Data;

/**
 * @author Owner
 *
 */
@Data
public class FilePathDaoDataProvider {
    @Mock
    private FilePathDao filePathDao;

    public FilePathDaoDataProvider() {
        MockitoAnnotations.openMocks(this);
        when(filePathDao.readAll())
                .thenReturn(createMultiFilePathDtoList());
        when(filePathDao.writeAll(anyList()))
                .thenReturn(true);
    }

    public FilePathInfoDto createFilePathInfoDto(Integer ren) {
        String sRen = ren.toString();
        return new FilePathInfoDto("NAME" + sRen, "PATH" + sRen, "ENCPATH" + sRen);
    }

    public List<FilePathDto> createOneFilePathDtoList(String groupId, Integer startRen) {
        List<FilePathDto> result = new ArrayList<FilePathDto>();

        List<FilePathInfoDto> wkFilePathInfoDtoList = new ArrayList<FilePathInfoDto>();
        wkFilePathInfoDtoList.add(createFilePathInfoDto(startRen));
        wkFilePathInfoDtoList.add(createFilePathInfoDto(startRen++));
        wkFilePathInfoDtoList.add(createFilePathInfoDto(startRen++));
        result.add(new FilePathDto(groupId, wkFilePathInfoDtoList));

        return result;
    }

    public List<FilePathDto> createMultiFilePathDtoList() {
        List<FilePathDto> result = new ArrayList<FilePathDto>();

        result.addAll(createOneFilePathDtoList("1", 1));
        result.addAll(createOneFilePathDtoList("2", 5));
        result.addAll(createOneFilePathDtoList("3", 10));

        return result;
    }

}
