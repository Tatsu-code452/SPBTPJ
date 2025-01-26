package com.manage.helper.COMMON;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BaseDao<T> {
    protected String fileName;
    protected TypeReference<T> typeRef;
    protected TypeReference<List<T>> typeRefList;

    public List<T> readAll() {
        List<T> result;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            result = objectMapper.readValue(new File(fileName), typeRefList);
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
            result = new ArrayList<T>();
        }
        return result;
    }

    public boolean writeAll(List<T> t) {
        boolean result = true;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), t);
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

}
