package com.dragon.flow.vo.calculate;

import lombok.Data;

import java.util.List;

@Data
public class BatchNewGhostVo {

    //当regionType为othermodel时，该属性为othermodel内部中的真正源RegionId
    private String targetRealRegionId;

    //表面的regionId
    private String regionId;

    private String instanceId;

    private String regionType;

    private List<GhostItem> sourceRegions;
}

