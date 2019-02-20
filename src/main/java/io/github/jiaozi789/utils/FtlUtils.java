package io.github.jiaozi789.utils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 廖敏
 * @Date 2019-02-20 11:23
 **/
public class FtlUtils {
    static Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
    static {

        cfg.setClassLoaderForTemplateLoading(FtlUtils.class.getClassLoader(),"/");
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_22));
    }

    /**
     * 通过模板名称和对象获取最终生成的内容
     * @param templateName 模板名称
     * @param root 对象
     * @return
     * @throws Exception
     */
    public static String genernate(String templateName,Object root) throws Exception {
        Template temp = cfg.getTemplate(templateName);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(baos);
        temp.process(root, out);
        return new String(baos.toByteArray());
    }

}
