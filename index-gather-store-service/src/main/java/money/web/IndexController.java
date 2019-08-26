package money.web;

import cn.hutool.core.collection.CollectionUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import money.pojo.Index;
import money.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {
    @Autowired
    IndexService indexService;

    @GetMapping("/getCodes")
    @HystrixCommand(fallbackMethod = "third_part_not_connected")
    public List<Index> get() throws Exception {
        return indexService.fetch_indexes_from_third_part();
    }

    public List<Index> third_part_not_connected(){
        System.out.println("third_part_not_connected()");
        Index index= new Index();
        index.setCode("000000");
        index.setName("无效指数代码");
        return CollectionUtil.toList(index);
    }
}
