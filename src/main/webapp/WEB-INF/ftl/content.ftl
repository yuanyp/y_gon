<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <#include "common.ftl"/>
</head>
<body>
<#include "top.ftl"/>
    <@query id="test" sql="
    select a.ID,a.CONTENT,b.PUBLISHDATE,b.TITLE
    from t_site_articles a 
    inner join t_file b on a.ID = b.ID 
    where a.id = {{id}} 
    and b.STATUS >=0"; result>
          <p>${result.title}</p>
          <div>${result.CONTENT}</div>
          <p>${result.PUBLISHDATE}</p>
    </@query>
</body>
</html>