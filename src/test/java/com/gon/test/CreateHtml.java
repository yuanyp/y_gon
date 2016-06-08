package com.gon.test;  
  
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gon.custom.directive.QueryDirective;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
  
public class CreateHtml {  
    public static void main(String[] args) {  
        try {  
            //创建一个合适的Configration对象  
            Configuration configuration = new Configuration();
            configuration.setDirectoryForTemplateLoading(new File("C:\\Users\\yuanyp\\git\\y_gon\\src\\main\\webapp\\WEB-INF\\ftl"));  
            configuration.setObjectWrapper(new DefaultObjectWrapper());  
            configuration.setDefaultEncoding("UTF-8");   //这个一定要设置，不然在生成的页面中 会乱码  
            //获取或创建一个模版。  
            Template template = configuration.getTemplate("helloWord.ftl");  
            Map<String, Object> paramMap = new HashMap<String, Object>();  
            paramMap.put("name", "哈哈哈哈！！！");  
            paramMap.put("query", new QueryDirective());  
            Writer writer  = new OutputStreamWriter(new FileOutputStream("C:\\Users\\yuanyp\\git\\y_gon\\src\\main\\webapp\\WEB-INF\\ftl\\success.ftl"),"UTF-8");  
            template.process(paramMap, writer);  
            System.out.println("恭喜，生成成功~~");  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (TemplateException e) {  
            e.printStackTrace();  
        }  
          
    }  
}  