package com.clearviewlive.api.integrations.dto;

import com.clearviewlive.api.integrations.model.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FileDto {
    public List<TicketNew> parseJson(JsonElement obj, String urlFile) throws NullPointerException, ParseException, IOException {
        List<TicketNew> ticList = new ArrayList<>();

        Gson gson = new Gson();

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

            try (CSVWriter writer = new CSVWriter(new FileWriter(urlFile))) {
                writer.writeAll(createCsv(ticList), false);
            }
        }
        return ticList;
    }

    private List<String[]> createCsv(List<TicketNew> ticList) {

        List<String[]> list = new ArrayList<>();

        String[] header = {"ticketID", "key", "agentName", "startDate", "type", "priority", "company",
                "completed", "totalDuration", "open", "inProgress", "waiting", "internalValidation"};
        list.add(header);

        for(TicketNew t: ticList){
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
