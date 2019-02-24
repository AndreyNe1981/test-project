package com.test.server.service;

import com.test.api.beans.DocumentDto;

public interface DocumentService {

    void put(DocumentDto documentDto);

    DocumentDto get(String id);

    void clean();
}
