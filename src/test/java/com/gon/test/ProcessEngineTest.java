package com.gon.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private String processName = "WF_Test_title";

	/**
	 * 测试 testa
	 */
	@Test
	public void testSubmitTask() {
		long s = System.currentTimeMillis();
		String userid = "1001";
		List<org.activiti.engine.task.Task> list = pes.getProcessInstanceTask("10901");
		System.out.printf("用户任务列表>>" + list);
		List<String> assList = new ArrayList<String>();
		for(int i=0,j=1000;i<j;i++){
			assList.add("0"+ i + "");
		}
		Map<String, Object> assMap = new HashMap<String, Object>();
		assMap.put("node7_users", assList);
		pes.submitTask(list.get(0).getId(), assMap);
		long e = System.currentTimeMillis();
		System.out.printf("耗时>" + (e-s) + "\n");
	}

	//@Test
	public void testSubmitTaskItem() {
		long s = System.currentTimeMillis();
		pes.submitTask("1220", null);
		long e = System.currentTimeMillis();
		System.out.printf("耗时>" + (e-s) + "\n");
	}

	//@Test
	public void testDeploy() {
		try {
			InputStream is = ClassLoader.getSystemResourceAsStream("process/test_001.bpmn");
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
		System.out.printf("启动流程》》："+pes.startProcess(processName,assMap));
	}
}
