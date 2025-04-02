package com.dragon.flow.model.aggregators;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
public class Average {

    private List<Document> list;

    private  Document average;

}
