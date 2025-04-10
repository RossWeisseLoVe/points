package com.dragon.flow.model.aggregators;

import lombok.Data;
import org.bson.Document;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class AggregatorsType1 {

    private Document dataMap;

    private String dataType;

    private Object result;

    public Document toDocument() {
        return new Document()
                .append("dataMap", dataMap)
                .append("dataType", dataType)
                .append("result", result);
    }

    public static AggregatorsType1 fromDocument(Document doc) {
        AggregatorsType1 aggregatorsType1 = new AggregatorsType1();
        aggregatorsType1.setDataMap(doc.get("dataMap",Document.class));
        aggregatorsType1.setDataType(doc.get("dataType",String.class));
        aggregatorsType1.setResult(doc.get("result",Object.class));
        return aggregatorsType1;
    }

    public void setResultAverage(){
        int count = 0;
        if(this.dataType.equals("Double")){
            Double sum = 0.0;
            for (Map.Entry<String, Object> entry : this.dataMap.entrySet()) {
                Object value = entry.getValue();
                Double cast = Double.class.cast(value);
                sum = sum+ cast;
                count++;
            }
            Double res = sum/count;
            this.setResult(res);
        }
    }


}
