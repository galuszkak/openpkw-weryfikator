package org.openpkw.services;

import org.springframework.batch.item.file.LineMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wojciech.szostak on 15.08.2015.
 */
public class CsvParser<T>
{
    private LineMapper<T> lineMapper;

    public CsvParser(LineMapper<T> lineMapper) {
        this.lineMapper = lineMapper;
    }

    public List<T> parse(File file) throws Exception
    {
        List<T> result = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(file));

        int lineNumber = 1;
        boolean header = true;
        for(String line; (line = br.readLine()) != null; ) {
            if (header) {
                header = false;
                continue;
            }

            T fieldSet = lineMapper.mapLine(line, lineNumber);
            if (fieldSet != null) {
                result.add(fieldSet);
            }
        }

        return result;
    }
}