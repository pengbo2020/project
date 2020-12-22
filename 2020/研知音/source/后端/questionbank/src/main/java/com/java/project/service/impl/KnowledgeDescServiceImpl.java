package com.java.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.project.entity.KnowledgeDesc;
import com.java.project.mapper.KnowledgeDescMapper;
import com.java.project.service.KnowledgeDescService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 知识点的描述内容 服务实现类
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@Service
public class KnowledgeDescServiceImpl extends ServiceImpl<KnowledgeDescMapper, KnowledgeDesc> implements KnowledgeDescService {

}
