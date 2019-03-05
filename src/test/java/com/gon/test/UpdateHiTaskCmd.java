package com.gon.test;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.task.Task;

/**
 * 更新历史任务信息
 */
public class UpdateHiTaskCmd implements Command<Void> {

    protected String taskId;

    public UpdateHiTaskCmd(){}

    public UpdateHiTaskCmd(String taskId){
        this.taskId = taskId;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        ProcessEngineConfigurationImpl pec = commandContext.getProcessEngineConfiguration();
        HistoryService historyService = pec.getHistoryService();
        TaskService taskService = pec.getTaskService();
        Task newTask = taskService.createTaskQuery().taskId(taskId).singleResult();
        HistoricTaskInstanceEntity historicTaskInstance = (HistoricTaskInstanceEntity)historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        historicTaskInstance.setProcessDefinitionId(newTask.getProcessDefinitionId());
        historicTaskInstance.setExecutionId(newTask.getExecutionId());
        historicTaskInstance.setProcessInstanceId(newTask.getProcessInstanceId());
        commandContext.getDbSqlSession().update(historicTaskInstance);
        return null;
    }
}
