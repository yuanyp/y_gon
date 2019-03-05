package com.gon.test;

import com.gon.RuntimeTaskHelper;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.management.TablePage;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * 测试流程引擎
 *
 * @author yuanyp
 *
 */
@TransactionConfiguration(defaultRollback=false)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext*.xml" })
public class ProcessEngineTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	static private Logger log = Logger.getLogger(ProcessEngineTest.class);

	@Autowired
	private ProcessEngineServer pes;

	@Autowired
	private RuntimeTaskHelper runtimeTaskHelper;

	private String processName = "WF_QB";//"WF_Test_title";

	/**
	 * 测试 testa
	 */
	//@Test
	public void testSubmitTask() {
		long s = System.currentTimeMillis();
		String userid = "1001";
		List<org.activiti.engine.task.Task> list = pes.getProcessInstanceTask("172509");
		System.out.printf("用户任务列表>>" + list);
		List<String> assList = new ArrayList<String>();
		for(int i=0,j=1;i<j;i++){
			assList.add("0"+ i + "");
		}
		Map<String, Object> assMap = new HashMap<String, Object>();
		assMap.put("node7_users", assList);
//		assMap.put("mulitiInstance", new MulitiInstanceCompleteTask());
		pes.submitTask(list.get(0).getId(), assMap);
		long e = System.currentTimeMillis();
		System.out.printf("耗时>" + (e-s) + "\n");
	}

	//@Test
	public void testSubmitTaskItem() {
		long s = System.currentTimeMillis();
		Map<String, Object> assMap = new HashMap<String, Object>();
//		assMap.put("node16_users", Arrays.asList("node16_1"));
//		assMap.put("gateway_node13", "node22");
		pes.submitTask("222536", assMap);
		long e = System.currentTimeMillis();
		System.out.printf("耗时>" + (e-s) + "\n");

	}

	//@Test
	public void testDeploy() {
		try {
			InputStream is = ClassLoader.getSystemResourceAsStream("process/test_003.bpmn");
			System.out.printf("is》》：" + is);
			System.out.printf("部署》》：" + pes.deploy(is));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@Test
	public void testStart(){
		List<String> assList = new ArrayList<String>();
		assList.add("1001");
		Map<String, Object> assMap = new HashMap<String, Object>();
		assMap.put("node4_users", assList);
		System.out.println("启动流程》》："+pes.startProcess(processName,assMap));
	}

	/**
	 * 测试流程 多部门会签时,自循环
	 */
	//@Test
	public void testZLC(){
		List<String> assList = new ArrayList<String>();
		assList.add("1001");
		Map<String, Object> assMap = new HashMap<String, Object>();
		assMap.put("node4_users", assList);
		String processInsId =  pes.startProcess(processName,assMap);
		System.out.println("启动流程》》流程实例ID："+processInsId);
		List<Task> list = pes.getProcessInstanceTask(processInsId);//查询当前流程实例的TASK
		System.out.printf("用户任务列表>>" + list);
		assMap.clear();
		assMap.put("node5_users", assList);
		pes.submitTask(list.get(0).getId(), assMap);

		list = pes.getProcessInstanceTask(processInsId);//查询当前流程实例的TASK
		assMap.clear();
		assList.clear();
		assList.add("001");
		assList.add("002");
		assMap.put("gateway_node13", "node20");
		assMap.put("node20_users", assList);
		pes.submitTask(list.get(0).getId(), assMap);//完成任务进入子流程

		//001用户发送给 001_1 001_2
		list = pes.getProcessInstanceTask(processInsId);//查询当前流程实例的TASK
		System.out.printf("用户任务列表>>" + list);
		for(Task task : list){
			List<IdentityLink> identityLinkList = pes.taskService.getIdentityLinksForTask(task.getId());
			System.out.printf("任务ID>>" + task.getId() + "的候选人：" + identityLinkList);
			System.out.printf("认领任务，任务ID>>" + task.getId() + ",候选人：" + identityLinkList.get(0).getUserId());
			pes.taskService.claim(task.getId(),identityLinkList.get(0).getUserId());
		}

		List<Task> tasks = pes.getAssignTaskList("001",processName);
		assMap.clear();
		assList.clear();
		assList.add("001_1");
		assList.add("001_2");
		assMap.put("node21_users", assList);
		pes.submitTask(tasks.get(0).getId(), assMap);

		//002用户发送给 002_1 002_2
		tasks = pes.getAssignTaskList("002",processName);
		assMap.clear();
		assList.clear();
		assList.add("002_1");
		assList.add("002_2");
		assList.add("002_3");
		assMap.put("node21_users", assList);
		pes.submitTask(tasks.get(0).getId(), assMap);
	}

	/**
	 * 注意需要先加签之后再提交当前任务
	 */
	@Test
	public void testAddTask(){
//		runtimeTaskHelper.addSign("105028","03");
		CounterSignAddCmd cmd = new CounterSignAddCmd("232608","001_1_1");
		String newTaskId = runtimeTaskHelper.getProcessEngine().getManagementService().executeCommand(cmd);
		//UpdateHiTaskCmd updateHiTaskCmd = new UpdateHiTaskCmd(newTaskId);
//		runtimeTaskHelper.getProcessEngine().getManagementService().executeCommand(updateHiTaskCmd);
//		historicTaskInstance.setProcessDefinitionId(t.getProcessDefinitionId());
//		historicTaskInstance.setExecutionId(newExecutionEntity.getId());
//		historicTaskInstance.setProcessInstanceId(t.getProcessInstanceId());
//		commandContext.getDbSqlSession().update(historicTaskInstance);
//		cmd = new CounterSignAddCmd("185021","001_2_2");
//		runtimeTaskHelper.getProcessEngine().getManagementService().executeCommand(cmd);
//		pes.submitTask("247510", null);
	}
}
