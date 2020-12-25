package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Enterprise;
import com.yealink.level1.domain.EnterpriseMapper;
import com.yealink.level1.service.EnterpriseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service
@Transactional
public class EnterpriseInfoServiceImpl implements EnterpriseInfoService {

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Override
    public Enterprise findEnterpriseById(String id) {
        return enterpriseMapper.findEnterpriseById(id);
    }

    @Override
    public int add(Enterprise enterprise) {
        long now = new Date().getTime();
        enterprise.setCreateTime(now);
        enterprise.setModifyTime(now);
        return enterpriseMapper.add(enterprise);
    }

    @Override
    public int delete(String id) {
        return enterpriseMapper.delete(id);
    }

    @Override
    public int update(Enterprise enterprise) {
        enterprise.setModifyTime(new Date().getTime());
        return enterpriseMapper.update(enterprise);
    }

    @Override
    public String findIdByName(String name) {
        return enterpriseMapper.findIdByName(name);
    }
}
