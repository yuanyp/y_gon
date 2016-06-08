package com.gon.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gon.controller.util.AppContextServlet;
import com.gon.custom.directive.QueryDirective;
import com.gon.db.DBDialect;
import com.gon.db.util.DBUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 内容静页面态化 TODO <功能简述> <br/>
 * TODO <功能详细描述>
 * 
 * @author yuany
 */
@Component
public class Task {

    @Scheduled(cron = "0/5 * *  * * ? ")
    // 每5秒执行一次
    public void createContentHtml() {
        System.out.println("生成开始");
        try {
            String sql = "select a.ID,b.TITLE,date_format(b.PUBLISHDATE, '%Y-%m-%d') PUBLISHDATE "
                    + "from t_site_articles a "
                    + "inner join t_file b on a.ID = b.ID "
                    + "where b.STATUS >=0";
            int ispaging = 1;
            int pagesize = 10;
            JdbcTemplate jdbcTemplate = (JdbcTemplate)AppContextServlet.getBean("jdbcTemplate");
            DBDialect dbDialect = (DBDialect)AppContextServlet.getBean("dbDialect");
            if(ispaging == 1){//如果分页
                int totalPage = 0;//总页数
                String countSql = dbDialect.getCountSql(sql);
                List<Integer> recordList = null;
                recordList = jdbcTemplate.queryForList(countSql, Integer.class);    
                if(null != recordList && recordList.size() == 1){
                    totalPage = DBUtil.getTotalPages(recordList.get(0), pagesize);
                }
                
                // 创建一个合适的Configration对象
                Configuration configuration = new Configuration();
                configuration.setDirectoryForTemplateLoading(new File("C:\\Users\\yuanyp\\git\\y_gon\\src\\main\\webapp\\WEB-INF\\ftl"));
                configuration.setObjectWrapper(new DefaultObjectWrapper());
                configuration.setDefaultEncoding("UTF-8"); // 这个一定要设置，不然在生成的页面中 会乱码
                // 获取或创建一个模版。
                String strTemplate = "helloWord.ftl";
                Template template = configuration.getTemplate(strTemplate);
                Map<String, Object> paramMap = new HashMap<String, Object>();
                String viewName = "helloWord.html";
                String catname = "test";
                String f = "C:\\apache-tomcat-8.0.24\\webapps\\y_gon\\resource\\static\\"+catname;
                File file = new File(f);
                if(!file.exists()){
                    file.mkdirs();
                }
                paramMap.put("basePath", "http://localhost:8080/y_gon/");
                for(int i=1;i<=totalPage;i++){
                    paramMap.put("query", new QueryDirective(viewName,i,pagesize,null));
                    Writer writer = new OutputStreamWriter(
                            new FileOutputStream(f+"\\helloWord_"+i+".html"),"UTF-8");
                    template.process(paramMap, writer);
                    System.out.println("恭喜，生成成功~~");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        System.out.println("生成结束");
    }

    /*
     * @Scheduled(cron="0/5 * *  * * ? ") //每5秒执行一次 public void myTest1(){ System.out.println("进入测试1"); }
     */
}