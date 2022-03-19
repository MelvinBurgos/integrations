package com.clearviewlive.api.integrations.controller;

import com.clearviewlive.api.integrations.model.Ticket;
import com.clearviewlive.api.integrations.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ApiController {

    @Autowired
    private FileService fileService;

    @GetMapping("")
    public List<Ticket> getAllTickets()
    {
        return fileService.getAllTickets();
    }

    @PostMapping("")
    public String saveTicket(@RequestBody String json)
    {
        return fileService.saveFile(json);
    }
}