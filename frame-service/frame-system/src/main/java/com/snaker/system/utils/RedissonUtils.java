package com.snaker.system.utils;

import com.snaker.common.enums.RedissonLockType;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class RedissonUtils {

    @Autowired
    @Lazy
    RedissonClient redissonClient;

    //为了统一管理分布式锁，请统一通过该接口获取分布式锁，如要增加锁类型，请在RedissonLockType枚举中加入
    public RLock getLock(RedissonLockType redissonLockType) {
        return redissonClient.getLock(redissonLockType.name());
    }

}