package com.dragon.flow.model.aggregators;

import lombok.Data;
import org.bson.Document;

import java.util.List;
import java.util.Map;

@Data
public class Average {

    private Map<String, Document> dataMap;

    private String DataType;

    private  Document average;

}
