package com.github.frostedglass.mybatis.security.strategy;

import com.github.frostedglass.mybatis.security.strategy.params.BaseSecurityParams;

/**
 * 安全策略接口，定义数据加密、解密、脱敏等操作
 * 
 * @author frostedglass
 */
public interface IStrategy<T extends BaseSecurityParams>    {

  void init(T params);

}

