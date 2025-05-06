package com.dragon.flow.vo.calculate;

import lombok.Data;

import java.util.List;

@Data
public class GhostVo {

    //表面的regionId
    private String regionId;

    private String instanceId;

    private String regionType;

    private String id;

    private List<BatchNewGhostVo> items;

}
