package com.java.project.vo;

import com.java.project.entity.Image;
import com.java.project.entity.KnowledgeBasic;
import com.java.project.entity.KnowledgeDesc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgeVo {
    KnowledgeBasic knowledge;
    List<KnowledgeDesc> knowledgeDescList;
    Image audoFilePath;
}
