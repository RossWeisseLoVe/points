package com.dragon.flow.model.aggregators;

import lombok.Data;
import org.bson.Document;

import java.util.List;
import java.util.Map;

@Data
public class AggregatorsType1 {

    private Document dataMap;

    private String dataType;

    private  Document result;

    public Document toDocument() {
        return new Document()
                .append("dataMap", dataMap)
                .append("DataType", dataType)
                .append("result", result);
    }

    public static AggregatorsType1 fromDocument(Document doc) {
        AggregatorsType1 aggregatorsType1 = new AggregatorsType1();
        aggregatorsType1.setDataMap(doc.get("dataMap",Document.class));
        aggregatorsType1.setDataType(doc.getString("dataType"));
        aggregatorsType1.setResult(doc.get("result",Document.class));
        return aggregatorsType1;
    }

}
