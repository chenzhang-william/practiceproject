package com.yealink.practiceproject.service.domain.impl;

import com.yealink.practiceproject.bean.Enterprise;
import com.yealink.practiceproject.dao.EnterpriseMapper;
import com.yealink.practiceproject.service.domain.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Validated
public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Override
    public void add(@Valid Enterprise enterprise) {

        long now = new Date().getTime();

        enterprise.setCreateTime(now);
        enterprise.setModifyTime(now);

        enterpriseMapper.add(enterprise);
    }

    @Override
    public void delete(@Valid Enterprise enterprise) {
        enterpriseMapper.delete(findEnterpriseByNo(enterprise).getId());
    }

    @Override
    public void update(@Valid Enterprise oldEnterprise, @Valid Enterprise newEnterprise) {

        newEnterprise.setId(findEnterpriseByNo(oldEnterprise).getId());
        newEnterprise.setModifyTime(new Date().getTime());

        enterpriseMapper.update(newEnterprise);
    }

    @Override
    public @NotNull(message = "无匹配企业") List<Enterprise> findEnterpriseByName(@Valid Enterprise enterprise) {
        return enterpriseMapper.findByName(enterprise.getName());
    }

    @Override
    public @NotNull(message = "企业不存在") Enterprise findEnterpriseByNo(@Valid Enterprise enterprise) {
        return enterpriseMapper.findByNo(enterprise.getNo());
    }

    @Override
    public boolean isEnterpriseExist(String no) {
        return enterpriseMapper.findIdByNo(no) != null;
    }


}
