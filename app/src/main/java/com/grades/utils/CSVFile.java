package com.grades.utils;

import com.grades.model.DistrictData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVFile {
    InputStream inputStream;

    public CSVFile(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<DistrictData> read() {
        List<DistrictData> resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {

                String[] row = csvLine.split(",");
                DistrictData districtData = new DistrictData();
                districtData.setDistrictName(row[0]);
                districtData.setDistrictCode(row[1]);
                districtData.setDistrictURL(row[2]);
                resultList.add(districtData);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
        return resultList;
    }
}
