package com.dragon.flow.vo.customer;

import com.dragon.flow.model.customer.Client;
import lombok.Data;

import java.io.Serializable;

@Data
public class ClientVo extends Client implements Serializable {

    private Integer point;

    private String representativeName;

    private String creatorName;

    private String updatorName;
}
