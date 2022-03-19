package com.clearviewlive.api.integrations.service;

import com.clearviewlive.api.integrations.model.Fields;
import com.clearviewlive.api.integrations.model.SummaryStates;
import com.clearviewlive.api.integrations.model.Ticket;
import com.clearviewlive.api.integrations.model.Tickets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    public List<Ticket> getAllTickets(){
        Reader input = null;
        JsonElement obj = null;
        try {
            input = new FileReader("C:\\Users\\Melvin\\Documents\\Technical Test\\IntegrationTest.json");
            obj = JsonParser.parseReader(input);
            return parseJson(obj);
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
        try {
            JsonElement obj = JsonParser.parseString(json);
            parseJson(obj);
            return "Saved";
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private List<Ticket> parseJson(JsonElement obj) throws ParseException {
        List<Ticket> ticList = new ArrayList<Ticket>();

//Instantiate Gson
            //Gson gson = new Gson();
            Gson gson = new GsonBuilder()
                .setDateFormat("MMM d, yyyy HH:mm:ss").create();

// Create a Type via TypeToken for the Student class
            //Type type = new TypeToken<SummaryStates[]>(){}.getType();

            //JsonElement obj = JsonParser.parseReader(input);
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
                                    if (ss.getName().equals("Internal Validation"))
                                        tic.setInternalValidation(ss.getDuration());
                                }
                            }
                        }
                        ticList.add(tic);
                    }
                }
            }
        try {
            String contenido = gson.toJson(ticList);
            File file = new File("C:\\Users\\Melvin\\Documents\\Technical Test\\outPutPost.txt");
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenido);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticList;
    }
}
