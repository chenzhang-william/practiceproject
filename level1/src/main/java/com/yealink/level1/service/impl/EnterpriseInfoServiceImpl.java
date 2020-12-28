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
    public int delete(String name) {
        return enterpriseMapper.delete(findIdByName(name));
    }

    @Override
    public int update(Enterprise enterprise,String name) {
        enterprise.setId(findIdByName(name));
        enterprise.setModifyTime(new Date().getTime());
        return enterpriseMapper.update(enterprise);
    }

    @Override
    public String findIdByName(String name) {
        return enterpriseMapper.findIdByName(name);
    }

    @Override
    public Enterprise findByNo(String no) {
        return enterpriseMapper.findByNo(no);
    }

    @Override
    public Enterprise findByName(String name) {
        return findEnterpriseById(findIdByName(name));
    }
}
