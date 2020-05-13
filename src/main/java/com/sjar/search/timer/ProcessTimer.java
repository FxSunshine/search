package com.sjar.search.timer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.sjar.search.lucene.LuceneService;
import com.sjar.search.model.dto.Conditions;
import com.sjar.search.model.dto.SearchLaw;
import com.sjar.search.model.entity.Law;
import com.sjar.search.model.service.LawService;
import com.sjar.search.util.HttpCall;
import com.sjar.search.util.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.Map;

/***
 * @author xuxiangjian
 * 爬虫定时任务
 */
@Component
public class ProcessTimer {

    /**
     * log
     */
    private final Logger logger = LoggerFactory.getLogger(ProcessTimer.class);

    public static final String [] LAW_WORDS = new String[]{"知识产权","劳动纠纷","婚姻","教育","财产保护","劳动合同","未成年保护"};

    int page = 1;

    @Autowired
    private LawService lawService;

    @Autowired
    private LuceneService luceneService;


    @Scheduled(fixedRate= 10* 1000)
    public void fixedProcess() throws Exception {
        System.out.println("拾律网，法律爬取开始");
        System.out.println("【爬虫开始】");

        for (int i = 0; i < LAW_WORDS.length; i++) {
            String words = LAW_WORDS[i];
            SearchLaw searchLaw = new SearchLaw();
            searchLaw.setPage(page);
            searchLaw.setPage_number(page);
            searchLaw.setPage_size(100);
            searchLaw.setSize(100);
            searchLaw.setSearch_type("all");
            searchLaw.setSort_type(1);
            Conditions conditions = new Conditions();
            conditions.setMust_extend_words(words);
            searchLaw.setConditions(conditions);
            String str = HttpCall.sendPost("https://t-picklaw.aegis-info.com/law/api/v1.2/search", JSONArray.toJSONString(searchLaw));
            System.out.println("【爬虫结束】");
            JSONObject object = JSON.parseObject(str);
            String data = object.getString("data");
            JSONArray array = JSON.parseArray(data);
            for (int j = 0; j < array.size(); j++) {
                JSONObject obj = array.getJSONObject(j);
                saveLaw(obj);
            }
            System.out.println("拾律网，法律爬取关键词「" + words + "」，已保存到数据库.");
        }
        page++;
    }

    public void saveLaw(JSONObject object) throws Exception {

        String codeId =  object.getString("code_id");
        QueryWrapper<Law> wrapper = new QueryWrapper();
        wrapper.eq("code_id",codeId);
        Law law = lawService.getOne(wrapper);
        if (ObjectUtils.isNullOrEmpty(law)){
            law = new Law();
            law.setCodeId(codeId);
            law.setCodeName(object.getString("code_name"));
            law.setImplementDate(object.getString("implement_date"));
            law.setIsStar(object.getString("is_star"));
            law.setIssuingAgency(object.getString("issuing_agency"));
            law.setIssuingNumber(object.getString("issuing_number"));
            law.setLaws(object.getString("laws"));
            law.setLawType(object.getString("law_type"));
            law.setLocation(object.getString("location"));
            law.setReleaseDate(object.getString("release_date"));
            law.setSubjectCategory(object.getString("subject_category"));
            law.setTimeliness(object.getString("timeliness"));
            law.setUrl("https://t-picklaw.aegis-info.com/law/api/v1.2/search/"+ codeId);

            lawService.save(law);

            //写入Lucene
            Map<String,Object> indexMaps = Maps.newHashMap();
            indexMaps.put("id",codeId);
            indexMaps.put("code_name",law.getCodeName());
            indexMaps.put("laws",law.getLaws());

            luceneService.createIndex(indexMaps);
        }
    }

}
/**
 * -------------------_ooOoo_
 * ------------------o8888888o
 * ------------------88" . "88
 * ------------------(| -_- |)
 * ------------------O\  =  /O
 * ---------------____/`---'\____
 * -------------.'  \\|     |//  `.
 * ------------/  \\|||  :  |||//  \
 * -----------/  _||||| -:- |||||-  \
 * -----------|   | \\\  -  /// |   |
 * -----------| \_|  ''\---/''  |   |
 * -----------\  .-\__  `-`  ___/-. /
 * ---------___`. .'  /--.--\  `. . __
 * ------."" '<  `.___\_<|>_/___.'  >'"".
 * -----| | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * -----\  \ `-.   \_ __\ /__ _/   .-` /  /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * -------------------`=---='
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * ------------佛祖保佑-------永无BUG-------------
 */