package com.clearviewlive.api.integrations.controller;

import com.clearviewlive.api.integrations.model.TicketNew;
import com.clearviewlive.api.integrations.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private FileService fileService;

    @GetMapping()
    public String getAllTickets()
    {
        return fileService.getAllTickets();
    }

    @PostMapping()
    public String saveTicket(@RequestBody String json)
    {
        return fileService.saveFile(json);
    }
}