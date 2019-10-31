package com.sjar.search.config;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author xuxiangjian
 */
@Configuration
public class LuceneConfig {
    /**
     * lucene索引,存放位置
     * @author xuxiangjian
     * @since 1.0.0
     */
    @Value("${lucene.index.path:}")
    private  String LUCENE_INDEX_PATH;
    /**
     * 索引位置
     *
     * @return
     * @throws IOException
     */
    @Bean
    public Directory directory() throws IOException {

        Path path = Paths.get(LUCENE_INDEX_PATH);
        File file = path.toFile();
        if(!file.exists()) {
            //如果文件夹不存在,则创建
            file.mkdirs();
        }
        return FSDirectory.open(path);
    }

}

