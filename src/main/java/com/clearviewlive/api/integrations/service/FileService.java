package com.clearviewlive.api.integrations.service;

import com.clearviewlive.api.integrations.model.Fields;
import com.clearviewlive.api.integrations.model.SummaryStates;
import com.clearviewlive.api.integrations.model.Ticket;
import com.clearviewlive.api.integrations.model.Tickets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    @Autowired
    Environment ev;  //variable de entorno

    public List<Ticket> getAllTickets(){
        Reader input = null;
        JsonElement obj = null;
        String fileSource = ev.getProperty("url.archive") + "IntegrationTest.json";
        String fileTarget = ev.getProperty("url.archive") + "outPutGet.txt";

        try {
            input = new FileReader(fileSource);
            obj = JsonParser.parseReader(input);
            return parseJson(obj, fileTarget);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String saveFile(String json){
        String fileTarget = ev.getProperty("url.archive") + "outPutPost.txt";

        try {
            JsonElement obj = JsonParser.parseString(json);
            parseJson(obj, fileTarget);
            return "Saved";
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private List<Ticket> parseJson(JsonElement obj, String urlFile) throws ParseException {
        List<Ticket> ticList = new ArrayList<Ticket>();

        Gson gson = new GsonBuilder()
                        .setDateFormat("MMM d, yyyy HH:mm:ss").create();

        if(obj.isJsonObject()) {// Read the `input` and cast into `type`
            JsonElement object = obj.getAsJsonObject().getAsJsonArray("Tickets");
            if (object.isJsonArray()) {// Read the `input` and cast into `type`
                for (int i = 0; i < object.getAsJsonArray().size(); i++) {
                    Ticket tic = new Ticket();  //esto permite que se cree uno nuevo por cada parse

                    JsonElement json = object.getAsJsonArray().get(i);
                    Tickets ti = gson.fromJson(json, Tickets.class);
                    tic.setTicketID(ti.getId());
                    tic.setKey(ti.getKey());
                    JsonElement object2 = json.getAsJsonObject().getAsJsonArray("fields");
                    if (object2.isJsonArray()) {
                        JsonElement json2 = object2.getAsJsonArray().get(0);
                        Fields fie = gson.fromJson(json2, Fields.class);
                        tic.setAgentName(fie.getAgentName());
                        tic.setStartDate(fie.getStartDate());
                        tic.setType(fie.getType());
                        tic.setPriority(fie.getPriority());
                        tic.setCompany(fie.getCompany());
                        tic.setCompleted(fie.isCompleted());
                        JsonElement object3 = json2.getAsJsonObject().getAsJsonArray("summaryStates");
                        if (object3.isJsonArray()) {
                            for (int j = 0; j < object3.getAsJsonArray().size(); j++) {
                                JsonElement json3 = object3.getAsJsonArray().get(j);
                                SummaryStates ss = gson.fromJson(json3, SummaryStates.class);
                                if (ss.getName().equals("Open")) tic.setOpen(ss.getDuration());
                                if (ss.getName().equals("In Progress")) tic.setInProgress(ss.getDuration());
                                if (ss.getName().equals("Waiting")) tic.setWaiting(ss.getDuration());
                                if (ss.getName().equals("Internal Validation")) tic.setInternalValidation(ss.getDuration());
                            }
                        }
                    }
                    ticList.add(tic);
                }
            }
        }

        try {

            try (CSVWriter writer = new CSVWriter(new FileWriter(urlFile))) {
                writer.writeAll(createCsv(ticList),false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticList;
    }

    private static List<String[]> createCsv(List<Ticket> ticList) {

        List<String[]> list = new ArrayList<>();

        String[] header = {"ticketID", "key", "agentName", "startDate", "type", "priority", "company",
                            "completed", "totalDuration", "open", "inProgress", "waiting", "internalValidation"};
        list.add(header);

        for(Ticket t: ticList){
            String[] item = {String.valueOf(t.getTicketID()), t.getKey(), t.getAgentName(), String.valueOf(t.getStartDate()),
                            t.getType(), t.getPriority(), t.getCompany(), String.valueOf(t.isCompleted()),
                            String.valueOf(t.getTotalDuration()), String.valueOf(t.getOpen()), String.valueOf(t.getInProgress()),
                            String.valueOf(t.getWaiting()), String.valueOf(t.getInternalValidation())};
            list.add(item);
        }
        return list;
    }
}
