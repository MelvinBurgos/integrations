package com.clearviewlive.api.integrations.service;

import com.clearviewlive.api.integrations.model.Field;
import com.clearviewlive.api.integrations.model.SummaryState;
import com.clearviewlive.api.integrations.model.TicketNew;
import com.clearviewlive.api.integrations.model.Ticket;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileService {

    @Autowired
    Environment ev;  //variable de entorno

    public List<TicketNew> getAllTickets(){

        String fileSource = ev.getProperty("url.archive") + "IntegrationTest.json";
        String fileTarget = ev.getProperty("url.archive") + "outPutGet.txt";

        try {
            Reader input = new FileReader(fileSource);
            JsonElement obj = JsonParser.parseReader(input);
            return parseJson(obj, fileTarget);

        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
            return null;
        }
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
            return null;
        }
    }

    private List<TicketNew> parseJson(JsonElement obj, String urlFile) throws ParseException {
        List<TicketNew> ticList = new ArrayList<>();

        Gson gson = new Gson();

        if(obj.isJsonObject()) {// verifica que recibo un Objeto JSON
            JsonElement node1 = obj.getAsJsonObject().getAsJsonArray("Tickets"); //Elimina la etiqueta y guarda un Array JSON
            if (node1.isJsonArray()) {// verifica que es un Array JSON
                for (int i = 0; i < node1.getAsJsonArray().size(); i++) {//examina cada nodo dentro de Tickets
                    TicketNew tic = new TicketNew();  //esto permite que se cree uno nuevo por cada nodo

                    JsonElement json1 = node1.getAsJsonArray().get(i);
                    Ticket ticket = gson.fromJson(json1, Ticket.class);//convierto elemento a Objeto
                    tic.setTicketID(ticket.getId());
                    tic.setKey(ticket.getKey());

                    JsonElement node2 = json1.getAsJsonObject().getAsJsonArray("fields");
                    if (node2.isJsonArray()) {
                        JsonElement json2 = node2.getAsJsonArray().get(0);
                        Field field = gson.fromJson(json2, Field.class);

                        tic.setAgentName(field.getAgentName());
                        tic.setStartDate(field.getStartDate());
                        tic.setType(field.getType());
                        tic.setPriority(field.getPriority());
                        tic.setCompany(field.getCompany());
                        tic.setCompleted(field.isCompleted());

                        JsonElement node3 = json2.getAsJsonObject().getAsJsonArray("summaryStates");
                        if (node3.isJsonArray()) {
                            for (int j = 0; j < node3.getAsJsonArray().size(); j++) {
                                JsonElement json3 = node3.getAsJsonArray().get(j);
                                SummaryState ss = gson.fromJson(json3, SummaryState.class);

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

    private static List<String[]> createCsv(List<TicketNew> ticList) {

        List<String[]> list = new ArrayList<>();

        String[] header = {"ticketID", "key", "agentName", "startDate", "type", "priority", "company",
                            "completed", "totalDuration", "open", "inProgress", "waiting", "internalValidation"};
        list.add(header);

        for(TicketNew t: ticList){
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

            String ticketID = String.valueOf(t.getTicketID());
            String key = t.getKey();
            String agentName = t.getAgentName();
            String startDate = formatter.format(t.getStartDate());
            String type = t.getType();
            String priority = t.getPriority();
            String company = t.getCompany();
            String completed = String.valueOf(t.isCompleted());
            String totalDuration = String.valueOf(t.getTotalDuration());
            String open = String.valueOf(t.getOpen());
            String inProgress = String.valueOf(t.getInProgress());
            String waiting = String.valueOf(t.getWaiting());
            String internalValidation = String.valueOf(t.getInternalValidation());

            String[] item = {ticketID, key, agentName, startDate, type, priority, company,
                            completed, totalDuration, open, inProgress, waiting, internalValidation};

            list.add(item);
        }
        return list;
    }
}
