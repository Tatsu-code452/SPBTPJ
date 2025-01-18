package com.manage.helper.SPBTPJ1.BusinessLogic.Logic;

import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.Dao.FilePathGroupDao;
import com.manage.helper.Dao.DaoModel.FilePathGroupDto;
import com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertGroupBDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SPBTPJ1_InsertGroupLogic extends BaseLogic<SPBTPJ1_InsertGroupBDto> {
    private final FilePathGroupDao filePathGroupDao;

    private List<FilePathGroupDto> filePathGroupList;

    @Override
    protected boolean loadData(SPBTPJ1_InsertGroupBDto dto) {
        boolean result = true;
        filePathGroupList = filePathGroupDao.readAll();
        return result;
    }

    @Override
    protected boolean createData(SPBTPJ1_InsertGroupBDto dto) {
        boolean result = true;
        filePathGroupList.add(
                new FilePathGroupDto(getGroupId(), dto.getGroup()));
        filePathGroupList = filePathGroupList.stream()
                .sorted(Comparator.comparing(FilePathGroupDto::getGroupId)).collect(Collectors.toList());
        return result;
    }

    private String getGroupId() {
        List<Integer> workList = filePathGroupList.stream()
                .map(n -> Integer.valueOf(n.getGroupId()))
                .sorted()
                .collect(Collectors.toList());
        OptionalInt optGroupId = IntStream.range(0, workList.size() - 1)
                .filter(n -> workList.get(n + 1) - workList.get(n) > 1)
                .min();

        return String.valueOf(
                (optGroupId.isPresent() ?
                        workList.get(optGroupId.getAsInt()) + 1 :
                        workList.size() + 1));

    }

    @Override
    protected boolean saveData(SPBTPJ1_InsertGroupBDto dto) {
        boolean result = true;
        result = filePathGroupDao.writeAll(filePathGroupList);
        return result;
    }

}
