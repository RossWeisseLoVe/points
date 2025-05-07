package com.dragon.flow.vo.calculate;

import lombok.Data;

import java.util.List;

@Data
public class GhostVo {

    //聚合器的regionId
    private String regionId;

    //聚合器的instanceId
    private String instanceId;

    //聚合器的RegionType（可能为othermodel）
    private String regionType;

    //聚合器的Id
    private String id;

    private List<BatchNewGhostVo> items;

}
