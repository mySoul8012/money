package money.web;


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




	@GetMapping("/freshCodes")
	public String fresh() throws Exception {
		indexService.fresh();
		return "fresh codes successfully";
	}
	@GetMapping("/getCodes")
	public List<Index> get() throws Exception {
		return indexService.get();
	}
	@GetMapping("/removeCodes")
	public String remove() throws Exception {
		indexService.remove();
		return "remove codes successfully";
	}
}

