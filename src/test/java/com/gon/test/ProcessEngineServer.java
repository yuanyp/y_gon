package com.gon.test;

import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessEngineServer {

	static private Logger logger = Logger.getLogger(ProcessEngineServer.class);

	@Autowired
	public RepositoryService repositoryService;

	@Autowired
	public FormService formService;

	@Autowired
	public RuntimeService runtimeService;

	@Autowired
	public IdentityService identityService;

	@Autowired
	public TaskService taskService;

	@Autowired
	public HistoryService historyService;

	@Autowired
	public ManagementService managementService;

	public ProcessEngineServer() {
		logger.debug("ProcessEngineServer 构造方法");
	}

	/**
	 * 启动流程
	 * @param processName
	 * @param params
	 * @return 流程实例ID
	 */
	public String startProcess(String processName,Map<String, Object> params){
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(processName,params);
		if (pi != null){
			return pi.getProcessInstanceId();
		}else{
			return null;
		}
	}

	/**
	 * 根据用户ID ，获取用户任务列表
	 * @param userid
	 * @param processName
	 * @return 用户任务列表
	 */
	public List<Task> getAssignTaskList(String userid, String processName) {
		TaskService ts = taskService;
		try{
			List<Task> list_task = ts.createTaskQuery().taskAssignee(userid).processDefinitionKey(processName).list();
			return list_task;
		}catch(Exception e){
			return null;
		}
	}
	public List<Task> getProcessInstanceTask(String processInstanceId){
		return taskService.createTaskQuery().processInstanceId(processInstanceId).list();
	}
	/**
	 * 完成任务
	 * @param task
	 * @param input
	 * @return true or false
	 */
	public boolean submitTask(String taskId, Map<String, Object> input) {
		taskService.complete(taskId, input);
		return true;
	}
	public void jumpTask(String taskId,Map<String, Object> input,String destinationTaskKey){
		TaskServiceImpl taskServiceImpl = (TaskServiceImpl)taskService;
		//taskServiceImpl.completeWithDest(taskId, input, destinationTaskKey);
	}
	public void backTask(String taskId,Map<String, Object> input,Connection con){
		try {
			TaskServiceImpl taskServiceImpl = (TaskServiceImpl)taskService;
			//taskServiceImpl.rejecttoPreTask(taskId, input, con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean deploy(InputStream fileInputStream) {
		String fileName = "testa.bpmn";
		try {
			String extension = FilenameUtils.getExtension(fileName);
			if (extension.equals("zip") || extension.equals("bar")) {
				ZipInputStream zip = new ZipInputStream(fileInputStream);
				repositoryService.createDeployment().addZipInputStream(zip)
						.deploy();
			} else if (extension.equals("png")) {
				repositoryService.createDeployment()
						.addInputStream(fileName, fileInputStream).deploy();
			} else if (fileName.indexOf("bpmn20.xml") != -1) {
				repositoryService.createDeployment()
						.addInputStream(fileName, fileInputStream).deploy();
			} else if (extension.equals("bpmn")) {
				/*
				 * bpmn扩展名特殊处理，转换为bpmn20.xml
				 */
				String baseName = FilenameUtils.getBaseName(fileName);
				repositoryService
						.createDeployment()
						.addInputStream(baseName + ".bpmn20.xml",
								fileInputStream).deploy();
			} else {
				throw new ActivitiException("no support file type of "
						+ extension);
			}
		} catch (Exception e) {
			logger.error(
					"error on deploy process, because of file input stream", e);
			return false;
		}
		return true;
	}
}
