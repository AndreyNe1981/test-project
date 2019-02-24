package com.test.server.service;

import com.test.api.beans.DocumentDto;
import com.test.server.index.IndexService;
import com.test.server.model.Document;
import com.test.server.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class SimpleDocumentService implements DocumentService {

    private final IndexService indexService;
    private final DocumentRepository documentRepository;
    private final ConversionService conversionService;

    @Autowired
    public SimpleDocumentService(
        IndexService indexService,
        DocumentRepository documentRepository,
        ConversionService conversionService
    ) {
        this.indexService = indexService;
        this.documentRepository = documentRepository;
        this.conversionService = conversionService;
    }

    //we might need transactional support in future or another pipeline approach
    @Override
    public void put(DocumentDto documentDto) {
        Document document = conversionService.convert(documentDto, Document.class);

        documentRepository.save(document);

        indexService.putToIndex(document);
    }

    @Override
    public DocumentDto get(String id) {
        Document document = documentRepository.get(id);
        return conversionService.convert(document, DocumentDto.class);
    }
}
