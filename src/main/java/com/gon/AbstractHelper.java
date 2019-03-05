/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gon;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractHelper {

  protected Logger logger = LoggerFactory.getLogger(getClass());

  protected ProcessEngine processEngine;
  protected ProcessEngineConfiguration processEngineConfiguration;
  protected RepositoryService repositoryService = null;
  protected RuntimeService runtimeService = null;
  protected HistoryService historyService = null;
  protected IdentityService identityService = null;
  protected TaskService taskService = null;
  protected FormService formService = null;
  protected ManagementService managementService = null;

  /**
   * 开启一个mybatis的session，切记要关闭
   */
  protected SqlSession openSession() {
    ProcessEngineConfigurationImpl peci = (ProcessEngineConfigurationImpl) processEngineConfiguration;
    DbSqlSessionFactory dbSqlSessionFactory = (DbSqlSessionFactory) peci.getSessionFactories().get(DbSqlSession.class);
    SqlSessionFactory sqlSessionFactory = dbSqlSessionFactory.getSqlSessionFactory();
    return sqlSessionFactory.openSession();
  }

  // -- getter and setter --//
  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  public ProcessEngineConfiguration getProcessEngineConfiguration() {
    return processEngineConfiguration;
  }

  public void setProcessEngineConfiguration(ProcessEngineConfiguration processEngineConfiguration) {
    this.processEngineConfiguration = processEngineConfiguration;
  }

  public RepositoryService getRepositoryService() {
    return repositoryService;
  }

  public void setRepositoryService(RepositoryService repositoryService) {
    this.repositoryService = repositoryService;
  }

  public RuntimeService getRuntimeService() {
    return runtimeService;
  }

  public void setRuntimeService(RuntimeService runtimeService) {
    this.runtimeService = runtimeService;
  }

  public HistoryService getHistoryService() {
    return historyService;
  }

  public void setHistoryService(HistoryService historyService) {
    this.historyService = historyService;
  }

  public IdentityService getIdentityService() {
    return identityService;
  }

  public void setIdentityService(IdentityService identityService) {
    this.identityService = identityService;
  }

  public TaskService getTaskService() {
    return taskService;
  }

  public void setTaskService(TaskService taskService) {
    this.taskService = taskService;
  }

  public FormService getFormService() {
    return formService;
  }

  public void setFormService(FormService formService) {
    this.formService = formService;
  }

  public ManagementService getManagementService() {
    return managementService;
  }

  public void setManagementService(ManagementService managementService) {
    this.managementService = managementService;
  }

}
