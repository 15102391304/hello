package com.example.bootsecurity.mapper.genmybatis;

import org.junit.jupiter.api.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description mybatis逆向生成
 * @Author 陈彭伟 | Chen, Danny Pengwei (CN - Chongqing)
 * @Email dannypchen@deloitte.com.cn
 * @phone +86 15215029766
 * @CreatDate 2018/9/30 11:25
 */
public class MySQLRunner {

    @Test
    public void build() throws Exception {
        File configFile = new File("genmybatis/generatorConfig.xml");
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
