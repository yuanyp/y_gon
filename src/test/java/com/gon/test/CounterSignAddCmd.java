package com.gon.test;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;

import java.util.Date;

/**
 * 多实例节点，动态增加实例
 */
public class CounterSignAddCmd implements Command<String> {

    protected String executionId;//task对应的执行实例ID
    protected String assignee;//办理人

    public CounterSignAddCmd(){}

    public CounterSignAddCmd(String executionId,String assignee){
        this.executionId = executionId;
        this.assignee = assignee;
    }

    @Override
    public String execute(CommandContext commandContext) {
        ProcessEngineConfigurationImpl pec = commandContext.getProcessEngineConfiguration();
        RuntimeService runtimeService = pec.getRuntimeService();
        TaskService taskService = pec.getTaskService();
        IdGenerator idGenerator = pec.getIdGenerator();
        HistoryService historyService = pec.getHistoryService();
        Execution execution = runtimeService.createExecutionQuery().executionId(executionId).singleResult();
        ExecutionEntity ee = (ExecutionEntity)execution;
        ExecutionEntity parent = ee.getParent();//获取父级ExecutionEntity实例对象
        ExecutionEntity newExecutionEntity = parent.createExecution();//创建新的执行实例对象
        newExecutionEntity.setActive(true);//设置为激活状态
        newExecutionEntity.setConcurrent(true);//设置并行状态
        newExecutionEntity.setScope(false);
//        newExecutionEntity.setProcessDefinitionId();
//        newExecutionEntity.setProcessInstance();
        Task newTask = taskService.createTaskQuery().executionId(executionId).singleResult();
        TaskEntity t = (TaskEntity)newTask;
        //创建新的task
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setCreateTime(new Date());
        taskEntity.setTaskDefinition(t.getTaskDefinition());
        taskEntity.setProcessDefinitionId(t.getProcessDefinitionId());
        taskEntity.setTaskDefinitionKey(t.getTaskDefinitionKey());
        taskEntity.setProcessInstanceId(t.getProcessInstanceId());
        taskEntity.setExecutionId(newExecutionEntity.getId());
        taskEntity.setName(newTask.getName());
        //获取的taskId
        String taskId = idGenerator.getNextId();
        taskEntity.setId(taskId);
        //设置task的执行实例
        taskEntity.setExecution(newExecutionEntity);
        taskEntity.setAssignee(assignee);
        taskService.saveTask(taskEntity);
        //更新多实例相关变量
        int loopCounter = LoopVariableUtils.getLoopVariable(newExecutionEntity,"nrOfInstances");
        int nrOfActiveInstances = LoopVariableUtils.getLoopVariable(newExecutionEntity,"nrOfActiveInstances");
        LoopVariableUtils.setLoopVariable(newExecutionEntity,"nrOfInstances",loopCounter + 1);
        LoopVariableUtils.setLoopVariable(newExecutionEntity,"nrOfActiveInstances",nrOfActiveInstances + 1);
        return taskId;
    }
}
