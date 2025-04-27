package com.dragon.flow.service.calculate.impl;

import com.dragon.flow.model.calculate.InstanceModel;
import com.dragon.flow.service.calculate.InstanceRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstanceImpl {

    private final MongoTemplate mongoTemplate;

    private final InstanceRepository instanceRepository;


    public List<InstanceModel> getRootsWithSubtrees(Page<InstanceModel> rootPage) {


        List<InstanceModel> roots = rootPage.getContent();

        // 2. 收集根节点ID
        List<String> rootIds = roots.stream()
                .map(InstanceModel::getId)
                .collect(Collectors.toList());

        // 3. 一次性查询所有相关节点(使用path_idx索引)
        Criteria subtreeCriteria = new Criteria().orOperator(
                Criteria.where("fid").is(null).and("_id").in(rootIds), // 根节点自身
                Criteria.where("path").in(rootIds.stream()
                        .map(id -> Pattern.compile("^" + id + "(,|$)"))
                        .collect(Collectors.toList())) // 所有子树节点
        );

        List<InstanceModel> allNodes = mongoTemplate.find(
                Query.query(subtreeCriteria), InstanceModel.class);

        // 4. 构建内存中的节点映射
        Map<String, InstanceModel> nodeMap = allNodes.stream()
                .collect(Collectors.toMap(InstanceModel::getId, Function.identity()));

        // 5. 构建树形结构
        List<InstanceModel> result = new ArrayList<>();

        for (InstanceModel node : allNodes) {
            if (node.getFid() == null) {
                // 如果是根节点，添加到结果列表
                result.add(node);
            } else {
                // 如果是子节点，找到其父节点并添加到父节点的children列表
                InstanceModel parent = nodeMap.get(node.getFid());
                if (parent != null) {
                    parent.addChild(node);
                }
            }
        }
        return result;
    }

}

