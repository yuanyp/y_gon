package com.gon;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.management.TablePage;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class RuntimeTaskHelper extends AbstractHelper {

    /**
     * 开启一个mybatis的session，切记要关闭
     */
    protected SqlSession openSession() {
        ProcessEngineConfigurationImpl peci = (ProcessEngineConfigurationImpl) processEngineConfiguration;
        DbSqlSessionFactory dbSqlSessionFactory = (DbSqlSessionFactory) peci.getSessionFactories().get(DbSqlSession.class);
        SqlSessionFactory sqlSessionFactory = dbSqlSessionFactory.getSqlSessionFactory();
        return sqlSessionFactory.openSession();
    }
    /**
     * 多实例（会签）加签
     *
     * @param taskId
     *          任务ID，多实例任务的任何一个都可以
     * @param userIds
     *          加签的人员
     */
    public void addSign(String taskId, String... userIds) {

        // open new session
        SqlSession sqlSession = openSession();
        PreparedStatement ps = null;

        try {
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            String processInstanceId = task.getProcessInstanceId();
            String taskDefinitionKey = task.getTaskDefinitionKey();

            // 插入ACT_RU_EXECUTION
            // 获取针对多实例的运行时流程实例
            List<Execution> listExecution = (List<Execution>) runtimeService
                    .createNativeExecutionQuery()
                    .sql("select * from ACT_RU_EXECUTION where PARENT_ID_ = (select ID_ from ACT_RU_EXECUTION where PARENT_ID_ = '" + processInstanceId
                            + "')").list();
            if(null == listExecution || listExecution.size() == 0){
                return;
            }
            ExecutionEntity executionEntityOfMany = ((ExecutionEntity)listExecution.get(0));
            String processDefinitionId = executionEntityOfMany.getProcessDefinitionId();

            // 获取ID
            TablePage listPage = managementService.createTablePageQuery().tableName("ACT_GE_PROPERTY").listPage(2, 2);
            Map<String, Object> idMap = listPage.getRows().get(0);

            // 当前的ID
            Long nextId = Long.parseLong(idMap.get("VALUE_").toString());

            Connection connection = sqlSession.getConnection();

            for (String userId : userIds) {

                // 处理重复加签
                long count = taskService.createTaskQuery().taskDefinitionKey(taskDefinitionKey).processInstanceId(processInstanceId).taskAssignee(userId).count();
                if (count > 0) {
                    logger.warn("忽略重复加签，用户：{}, 任务：{}", userId, taskDefinitionKey);
                    continue;
                }

                // 插入一条新的运行时实例
                Long newExecutionId = ++nextId;

                int counter = 1;
                ps = connection.prepareStatement("insert into ACT_RU_EXECUTION(ID_,REV_,PROC_INST_ID_," +
                        "BUSINESS_KEY_,PARENT_ID_,PROC_DEF_ID_,SUPER_EXEC_,ACT_ID_,IS_ACTIVE_,IS_CONCURRENT_," +
                        "IS_SCOPE_,IS_EVENT_SCOPE_,SUSPENSION_STATE_,CACHED_ENT_STATE_) " +
                        "values(?, 1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                ps.setString(counter++, newExecutionId.toString()); // ID_
                ps.setString(counter++, processInstanceId); // PROC_INST_ID_
                ps.setString(counter++, executionEntityOfMany.getBusinessKey()); // BUSINESS_KEY_
                ps.setString(counter++, executionEntityOfMany.getParentId()); // PARENT_ID_
                ps.setString(counter++, processDefinitionId); // PROC_DEF_ID_
                ps.setString(counter++, executionEntityOfMany.getSuperExecutionId()); // SUPER_EXEC_
                ps.setString(counter++, executionEntityOfMany.getActivityId()); // ACT_ID_
                ps.setInt(counter++, 1); // IS_ACTIVE_
                ps.setInt(counter++, 1); // IS_CONCURRENT_
                ps.setInt(counter++, 0); // IS_SCOPE_
                ps.setInt(counter++, 0); // IS_EVENT_SCOPE_
                ps.setInt(counter++, 1); // SUSPENSION_STATE_
                ps.setInt(counter++, executionEntityOfMany.getCachedEntityState()); // CACHED_ENT_STATE_
                ps.executeUpdate();

                // 创建任务
                // runtime task
                Long newTaskId = ++nextId;
                counter = 1;
                ps = connection.prepareStatement("insert into act_ru_task(ID_,REV_,EXECUTION_ID_,PROC_INST_ID_,PROC_DEF_ID_," +
                        "NAME_,TASK_DEF_KEY_,ASSIGNEE_,PRIORITY_," +
                        "CREATE_TIME_,SUSPENSION_STATE_)" +
                        "values(?, 1, ?, ?, ?, ?, ?, ?, ?, ?,1)");
                ps.setString(counter++, newTaskId.toString());//ID_
                ps.setString(counter++, newExecutionId.toString());//EXECUTION_ID_
                ps.setString(counter++, processInstanceId);//PROC_INST_ID_
                ps.setString(counter++, processDefinitionId);//PROC_DEF_ID_
                ps.setString(counter++, task.getName());//NAME_
                ps.setString(counter++, taskDefinitionKey);//TASK_DEF_KEY_
                ps.setString(counter++, userId);//ASSIGNEE_
                ps.setInt(counter++, task.getPriority());//PRIORITY_
                ps.setTimestamp(counter++, new Timestamp(System.currentTimeMillis()));//CREATE_TIME_
                ps.executeUpdate();

                // history task
                counter = 1;
                ps = connection.prepareStatement("insert into act_hi_taskinst(ID_,PROC_DEF_ID_,TASK_DEF_KEY_," +
                        "PROC_INST_ID_,EXECUTION_ID_,NAME_,ASSIGNEE_,START_TIME_,PRIORITY_)" +
                        " values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
                ps.setString(counter++, newTaskId.toString());//ID_
                ps.setString(counter++, processDefinitionId);//PROC_DEF_ID_
                ps.setString(counter++, taskDefinitionKey);//TASK_DEF_KEY_
                ps.setString(counter++, processInstanceId);//PROC_INST_ID_
                ps.setString(counter++, newExecutionId.toString());//EXECUTION_ID_
                ps.setString(counter++, task.getName());//NAME_
                ps.setString(counter++, userId);//ASSIGNEE_
                ps.setTimestamp(counter++, new Timestamp(System.currentTimeMillis()));//START_TIME_
                ps.setInt(counter++, task.getPriority());//PRIORITY_
                ps.executeUpdate();

                // 更新主键ID
                String updateNextId = "update ACT_GE_PROPERTY set VALUE_ = ? where NAME_ = ?";
                ps = connection.prepareStatement(updateNextId);
                ps.setLong(1, nextId);
                ps.setString(2, "next.dbid");
                ps.executeUpdate();

                /*
                 * 更新多实例相关变量
                 */
                List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId)
                        .variableNameLike("nrOf%").list();
                Integer nrOfInstances = 0;
                Integer nrOfActiveInstances = 0;
                for (HistoricVariableInstance var : list) {
                    if (var.getVariableName().equals("nrOfInstances")) {
                        nrOfInstances = (Integer) var.getValue();
                    } else if (var.getVariableName().equals("nrOfActiveInstances")) {
                        nrOfActiveInstances = (Integer) var.getValue();
                    }
                }

                // 多实例变量加一
                nrOfInstances++;
                nrOfActiveInstances++;

                String updateVariablesOfMultiinstance = "update ACT_HI_VARINST set LONG_ = ?, TEXT_ = ? where EXECUTION_ID_ = ? and NAME_ = ?";
                ps = connection.prepareStatement(updateVariablesOfMultiinstance);
                ps.setLong(1, nrOfInstances);
                ps.setString(2, String.valueOf(nrOfInstances));
                ps.setString(3, executionEntityOfMany.getParentId());
                ps.setString(4, "nrOfInstances");
                ps.executeUpdate();

                ps.setLong(1, nrOfInstances);
                ps.setString(2, String.valueOf(nrOfActiveInstances));
                ps.setString(3, executionEntityOfMany.getParentId());
                ps.setString(4, "nrOfActiveInstances");
                ps.executeUpdate();

            }
            sqlSession.commit();
        } catch (Exception e) {
            logger.error("failed to add sign for countersign", e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    logger.error("failed on execute sql", e);
                }
            }
            sqlSession.close();
        }

    }
}
