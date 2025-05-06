package com.dragon.flow.vo.calculate;

import lombok.Data;

import java.util.List;

@Data
public class BatchNewGhostVo {

    //当regionType为othermodel时，该属性为othermodel内部中的真正源RegionId
    private String targetRealRegionId;

    private List<GhostItem> sourceRegions;
}

