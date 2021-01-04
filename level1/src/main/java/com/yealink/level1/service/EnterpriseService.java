package com.yealink.level1.service;

import com.yealink.level1.bean.Enterprise;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface EnterpriseService {

    void add(@Valid Enterprise enterprise);

    void delete(@Valid Enterprise enterprise);

    void update(@Valid Enterprise oldEnterprise,@Valid Enterprise newEnterprise);

    List<Enterprise> findEnterpriseByName(@Valid Enterprise enterprise);

    @NotNull(message = "企业不存在") Enterprise findEnterpriseByNo(@Valid Enterprise enterprise);

    boolean isEnterpriseExist(@Valid Enterprise enterprise);
}
