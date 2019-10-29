package com.sjar.search.config;


import com.sjar.search.hdfs.HdfsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HDFS相关配置
 *
 * @author adminstrator
 * @since 1.0.0
 */
@Configuration
public class HdfsConfig {
    private String defaultHdfsUri = "hdfs://127.0.0.1:9000";

    @Bean
    public HdfsService getHbaseService(){
        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
        conf.set("fs.defaultFS",defaultHdfsUri);
        return new HdfsService(conf,defaultHdfsUri);
    }
}
