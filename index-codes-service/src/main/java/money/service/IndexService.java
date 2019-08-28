package money.service;

import cn.hutool.core.collection.CollUtil;
import money.pojo.Index;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  从 Redis 中获取数据
 */
@Service
@CacheConfig(cacheNames = "indexes")
public class IndexService {
    private List<Index> indices;


    @Cacheable(key="'all_codes'")
    public List<Index> get(){
        Index index = new Index();
        index.setName("无效指数代码");
        index.setCode("0000000");
        return CollUtil.toList(index);
    }
}
