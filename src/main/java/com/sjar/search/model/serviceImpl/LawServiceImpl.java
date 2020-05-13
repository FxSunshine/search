package com.sjar.search.model.serviceImpl;

import com.sjar.search.model.entity.Law;
import com.sjar.search.model.mapper.LawMapper;
import com.sjar.search.model.service.LawService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuxiangjian
 * @since 2020-05-13
 */
@Service
public class LawServiceImpl extends ServiceImpl<LawMapper, Law> implements LawService {

}
