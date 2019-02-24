package com.test.server.conversion;

import com.test.api.beans.DocumentDto;
import com.test.server.model.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DocumentToDtoConverter implements Converter<Document, DocumentDto> {

    @Override
    public DocumentDto convert(Document source) {
        return new DocumentDto(
            source.getId(),
            source.getText()
        );
    }
}
