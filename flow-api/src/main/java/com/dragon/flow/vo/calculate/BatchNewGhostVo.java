package com.dragon.flow.vo.calculate;

import lombok.Data;

import java.util.List;

@Data
public class BatchNewGhostVo {

    private String targetRealRegionId;

    private String instanceId;

    private List<GhostItem> sourceRegionIds;
}

