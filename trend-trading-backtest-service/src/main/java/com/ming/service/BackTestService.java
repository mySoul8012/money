package com.ming.service;

import com.ming.client.IndexDataClient;
import com.ming.pojo.IndexData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BackTestService {
    @Autowired
    IndexDataClient indexDataClient;

    public List<IndexData> listIndexData(String code){
        List<IndexData> res = indexDataClient.getIndexData(code);
        Collections.reverse(res);

        for(IndexData indexData: res){
            System.out.println(indexData.getDate());
        }

        return res;
    }
}
