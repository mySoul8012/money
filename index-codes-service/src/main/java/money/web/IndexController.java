package money.web;

import money.config.IpConfiguration;
import money.pojo.Index;
import money.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {
    @Autowired
    IndexService indexService;
    @Autowired
    IpConfiguration ipConfiguration;

    @GetMapping("/codes")
    // 允许跨域
    @CrossOrigin
    public List<Index> codes() throws Exception{
        System.out.println("current instance's port is "+ ipConfiguration.getPort());
        return indexService.get();
    }
}
