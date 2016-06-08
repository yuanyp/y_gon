<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <#include "common.ftl"/>
</head>
<body>
<#include "top.ftl"/>
    <h1>say hello ${name!""}</h1><br/>
    <#--${Request.test_currentpage!"1"}-->
    <@query id="test" sql="
    select a.ID,b.TITLE,date_format(b.PUBLISHDATE, '%Y-%m-%d') PUBLISHDATE 
    from t_site_articles a 
    inner join t_file b on a.ID = b.ID
    where b.STATUS >=0" ispaging="1" pagesize="10"; result>
        <#list result as c>
          <div class="xyz">
              <a href="content/${c.PUBLISHDATE}/${c.ID}.html?id=${c.ID}">${c.TITLE}</a>
              <a href="javaScript:void(0)">${c.PUBLISHDATE}</a>
          </div>
        </#list>
    </@query>

</body>
</html>