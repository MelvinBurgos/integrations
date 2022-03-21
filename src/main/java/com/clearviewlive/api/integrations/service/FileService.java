package com.clearviewlive.api.integrations.service;

import com.clearviewlive.api.integrations.dto.FileDto;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;

@Service
public class FileService {

    @Autowired
    Environment ev;  //variable de entorno

    FileDto fileDto = new FileDto();

    public String getAllTickets(){

        String fileSource = ev.getProperty("url.archive") + "IntegrationTest.json";
        String fileTarget = ev.getProperty("url.archive") + "outPutGet.txt";

        try {
            Reader input = new FileReader(fileSource);
            JsonElement obj = JsonParser.parseReader(input);
            fileDto.parseJson(obj, fileTarget);
            return "Data processed successfully";

        } catch (NullPointerException | ParseException | IOException e) {
            e.printStackTrace();
            return "An error has occurred, please check." + e.getMessage();
        }
    }

    public String saveFile(String json){

        String fileTarget = ev.getProperty("url.archive") + "outPutPost.txt";
        Gson gson = new Gson();

        try {
            JsonElement obj = JsonParser.parseString(json);
            fileDto.parseJson(obj, fileTarget);
            return "Data saved successfully";
        }
        catch (NullPointerException | ParseException | IOException e)
        {
            e.printStackTrace();
            return "An error has occurred, please check." + e.getMessage();
        }
    }
}
