package com.manage.helper.Dao;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Component;

import com.manage.helper.Dao.DaoModel.FilePathGroupDto;

import lombok.Data;

@Data
@Component
@ExtendWith(MockitoExtension.class)
public class FilePathGroupDaoDataProvider {
    @Mock
    private FilePathGroupDao filePathGroupDao;

    public FilePathGroupDaoDataProvider() {
        MockitoAnnotations.openMocks(this);
        when(filePathGroupDao.readAll())
                .thenReturn(createFilePathGroupDtoList());
        when(filePathGroupDao.writeAll(anyList()))
                .thenReturn(true);
    }

    public FilePathGroupDto createFilePathGroupDto(Integer groupId) {
        String sGroupId = groupId.toString();
        Integer order = groupId;
        return new FilePathGroupDto(sGroupId, "group" + sGroupId, order);
    }

    public List<FilePathGroupDto> createFilePathGroupDtoList() {
        List<FilePathGroupDto> result = new ArrayList<FilePathGroupDto>();

        result.add(createFilePathGroupDto(1));
        result.add(createFilePathGroupDto(2));
        result.add(createFilePathGroupDto(3));

        return result;
    }

}
