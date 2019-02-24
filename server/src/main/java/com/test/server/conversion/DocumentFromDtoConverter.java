package com.test.server.conversion;

import com.test.api.beans.DocumentDto;
import com.test.server.model.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DocumentFromDtoConverter implements Converter<DocumentDto, Document> {

    @Override
    public Document convert(DocumentDto source) {
        return new Document(
            source.getId(),
            source.getText()
        );
    }
}
