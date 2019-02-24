package com.test.server.controllers;

import com.test.api.beans.DocumentDto;
import com.test.server.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PutMapping("/document/{id}")
    public @ResponseBody DocumentDto put(@PathVariable(value = "id") String id, @RequestBody DocumentDto documentDto) {
        documentService.put(documentDto);
        return documentDto;
    }

    @GetMapping("/document/{id}")
    public @ResponseBody DocumentDto get(@PathVariable(value = "id") String id) {
        return documentService.get(id);
    }
}
