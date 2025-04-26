package com.dragon.flow.model.calculate;

import com.dragon.tools.common.BaseModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "instance")
@CompoundIndexes({
        @CompoundIndex(name = "fid_idx", def = "{'fid': 1}"),          // 加速父子关系查询
        @CompoundIndex(name = "path_idx", def = "{'path': 1}"),       // 加速路径查询
        @CompoundIndex(name = "fid_null_idx", def = "{'fid': 1}", partialFilter = "{'fid': null}") // 专门优化根节点查询
})
public class InstanceModel extends BaseModel {

    @Id
    private String id;

    private String fid;

    private String modelId;

    private String name;

    private String description;

    private String type;

    //物化路径
    private String path;
    //树的层级
    private Integer level;

    @Transient
    private List<RegionInstanceModel> data;

    @Transient
    private List<InstanceModel> children = new ArrayList<>();

    public void addChild(InstanceModel child) {
        this.children.add(child);
    }

}
