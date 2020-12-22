package com.java.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.project.entity.Temp;
import com.java.project.mapper.TempMapper;
import com.java.project.service.TempService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zcj
 * @since 2020-06-13
 */
@Service
public class TempServiceImpl extends ServiceImpl<TempMapper, Temp> implements TempService {

}
