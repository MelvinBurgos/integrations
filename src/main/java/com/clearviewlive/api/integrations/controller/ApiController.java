package com.clearviewlive.api.integrations.controller;

import com.clearviewlive.api.integrations.model.Fields;
import com.clearviewlive.api.integrations.model.SummaryStates;
import com.clearviewlive.api.integrations.model.Ticket;
import com.clearviewlive.api.integrations.model.Tickets;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.Optional;

import com.google.gson.reflect.TypeToken;

import javax.swing.text.Element;

@RestController
@RequestMapping("/")
public class ApiController {

    @GetMapping("")
    public List<Ticket> getAllTickets()
    {
        List<Ticket> ticList = new ArrayList<Ticket>();

            // Convert JSON File to Java Object
// Instantiate FileReader for amy.json
        Reader input = null;
        try {
            input = new FileReader("C:\\Users\\Melvin\\Documents\\Technical Test\\IntegrationTest.json");

//Instantiate Gson
        Gson gson = new Gson();
// Create a Type via TypeToken for the Student class
        Type type = new TypeToken<SummaryStates[]>(){}.getType();

        JsonElement obj = JsonParser.parseReader(input);
            if(obj.isJsonObject()) {// Read the `input` and cast into `type`
                JsonElement object = obj.getAsJsonObject().getAsJsonArray("Tickets");
                if(object.isJsonArray()) {// Read the `input` and cast into `type`
                    for (int i = 0; i < object.getAsJsonArray().size(); i++) {
                        Ticket tic = new Ticket();  //esto permite que se cree uno nuevo por cada parse

                        JsonElement json = object.getAsJsonArray().get(i);
                        Tickets ti = new Gson().fromJson(json, Tickets.class);
                        tic.setTicketID(ti.getId());
                        tic.setKey(ti.getKey());
                        JsonElement object2 = json.getAsJsonObject().getAsJsonArray("fields");
                        if(object2.isJsonArray()){
                            JsonElement json2 = object2.getAsJsonArray().get(0);
                            Fields fie = new Gson().fromJson(json2, Fields.class);
                            tic.setAgentName(fie.getAgentName());
                            tic.setStartDate(fie.getStartDate());
                            tic.setType(fie.getType());
                            tic.setPriority(fie.getPriority());
                            tic.setCompany(fie.getCompany());
                            tic.setCompleted(fie.isCompleted());
                            JsonElement object3 = json2.getAsJsonObject().getAsJsonArray("summaryStates");
                            if(object3.isJsonArray()){
                                for (int j = 0; j < object3.getAsJsonArray().size(); j++){
                                    JsonElement json3 = object3.getAsJsonArray().get(j);
                                    SummaryStates ss = new Gson().fromJson(json3, SummaryStates.class);
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

// Print result

        input.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ticList;
    }

    @PostMapping("")
    public String postTickets()
    {
        Ticket ticket = new Ticket();
        return "OK";
    }
}