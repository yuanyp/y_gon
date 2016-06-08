package com.gon.custom.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gon.controller.util.AppContextServlet;
import com.gon.db.DBDialect;
import com.gon.db.util.DBUtil;
import com.manyit.common.util.NumberUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;
import freemarker.template.WrappingTemplateModel;
  
/** 
 * 自定义标签解析类 
 * @author Administrator 
 * 
 */  
public class QueryDirective implements TemplateDirectiveModel{  
  
    private static final String PARAM_SQL = "sql";
    private static final String PARAM_ID = "id";//ID
    private static final String PARAM_ISPAGING = "ispaging";//是否分页
    
    private Map<String,Object> paramMap = new HashMap<String, Object>();
    
    private int currentPage = 1;//当前页数
    
    private int pageSize = 10;//每页条数
    
    private String viewName = "";//模板名称
    
    
    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String,Object> paramMap) {
        this.paramMap = paramMap;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public QueryDirective() {
        super();
    }
    
    public QueryDirective(Map<String,Object> paramMap) {
        super();
        this.paramMap = paramMap;
    }
    
    public QueryDirective(String viewName,int currentPage,int pageSize,Map<String,Object> paramMap) {
        super();
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.paramMap = paramMap;
        this.viewName = viewName;
    }

      
    @Override  
    public void execute(Environment env, Map params,TemplateModel[] loopVars,  
            TemplateDirectiveBody body) throws TemplateException, IOException {  
        if(body==null){  
            throw new TemplateModelException("null body");  
        }else{  
            String sql = getString(PARAM_SQL, params);
            String controlid = getString(PARAM_ID, params);
            int ispaging = NumberUtil.convertToInt(getString(PARAM_ISPAGING, params));
            StringBuilder pagination = new StringBuilder();
            Writer out = env.getOut();
            if(StringUtils.isNotBlank(sql)){
                //替换变量
                List<Object> sqlParam = new ArrayList<Object>();
                sql = replaceVar(sql, sqlParam);
                JdbcTemplate jdbcTemplate = (JdbcTemplate)AppContextServlet.getBean("jdbcTemplate");
                if(ispaging == 1){//如果分页
                    if(pageSize == 0){
                        pageSize = 10;
                    }
                    int totalPage = 0;//总页数
                    DBDialect dbDialect = (DBDialect)AppContextServlet.getBean("dbDialect");
                    String countSql = dbDialect.getCountSql(sql);
                    sql = dbDialect.getPageSql(sql, currentPage, pageSize);
                    List<Integer> recordList = null;
                    if(sqlParam.size() > 0){
                        recordList = jdbcTemplate.queryForList(countSql, Integer.class,sqlParam.toArray());
                    }else{
                        recordList = jdbcTemplate.queryForList(countSql, Integer.class);    
                    }
                    
                    if(null != recordList && recordList.size() == 1){
                        totalPage = DBUtil.getTotalPages(recordList.get(0), pageSize);
                    }
                    List<Integer> ret = new Pagination(totalPage, currentPage, 1).start();
                    pagination.append("<ul>");
                    for(Integer page : ret){
                        if(page == -1){
                            pagination.append("<li>.</li>");
                        }else{
                            pagination.append("<li onclick='_go(\""+getViewNameByPage(viewName, page)+"\")'>" + page + "</li>");
                        }
                    }
                    pagination.append("</ul>");
                }
                List<Map<String,Object>> result = null;
                if(sqlParam.size() > 0){
                    result = jdbcTemplate.queryForList(sql,sqlParam.toArray());
                }else{
                    result = jdbcTemplate.queryForList(sql);
                }
                if(null != result && result.size() == 1){
                    loopVars[0] = WrappingTemplateModel.getDefaultObjectWrapper().wrap(result.get(0));
                }else{
                    loopVars[0] = WrappingTemplateModel.getDefaultObjectWrapper().wrap(result);    
                }
                // 执行标签内容(same as <#nested> in FTL).
                body.render(out);
            }
            if(ispaging == 1){
                if(pagination.length() > 0){
                    out.write(pagination.toString());                    
                }
            }
            
            /*Writer out = env.getOut();  
            out.write("从这里输出可以再页面看到具体的内容，就像document.writer写入操作一样。<br/>");  
            body.render(out); */ 
              
            /* 
            如果细心的话，会发现页面上是显示out.write（）输出的语句，然后再输出output的内容， 
            可见 在body在解析的时候会先把参数放入env中，在页面遇到对应的而来表单时的才会去取值 
            但是，如果该表单时不存在，就会报错，  我觉得这里freemarker没有做好，解析的时候更加会把错误暴露在页面上。 
            可以这样子弥补${output!"null"},始终感觉没有el表达式那样好。 
            */  
        }  
    }
    
    private String replaceVar(String sql,List<Object> sqlParam){
        String str = sql;
        if(StringUtils.isNotBlank(sql) && null != paramMap && paramMap.size() > 0){
            Pattern pattern = Pattern.compile("\\{\\{(.*?)}}",Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(sql);
            while (matcher.find()) {
                String item = matcher.group(0);
                String item1 = matcher.group(1);
                str = str.replace(item, "?");
                sqlParam.add(paramMap.get(item1));
            }
        }
        return str; 
    }
    
    private String getViewNameByPage(String viewName,int page){
        String ret = "";
        if(StringUtils.isNotBlank(viewName)){
            if(viewName.indexOf(".") != -1){
                ret = viewName.replace(".", "_" + page + ".");
            }
        }
        return ret;
    }
      
    /** 
     * 获取String类型的参数的值 
     * @param paramName 
     * @param paramMap 
     * @return 
     * @throws TemplateModelException 
     */  
    public static String getString(String paramName, Map<String, TemplateModel> paramMap) throws TemplateModelException{  
        TemplateModel model = paramMap.get(paramName);  
        if(model == null){  
            return null;  
        }  
        if(model instanceof TemplateScalarModel){  
            return ((TemplateScalarModel)model).getAsString();  
        }else if (model instanceof TemplateNumberModel) {  
            return ((TemplateNumberModel)model).getAsNumber().toString();  
        }else{  
            throw new TemplateModelException(paramName);  
        }  
    }
    
}