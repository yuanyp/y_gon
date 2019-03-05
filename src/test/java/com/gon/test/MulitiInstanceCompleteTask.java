package com.gon.test;

import org.activiti.engine.delegate.DelegateExecution;

import java.io.Serializable;

/**
 * 多实例完成的条件判断
 * @author huan
 */
public class MulitiInstanceCompleteTask implements Serializable {

    private static final long serialVersionUID = 1L;

    public boolean completeTask(DelegateExecution execution) {
        System.out.println("completeTask..........");
        int nrOfInstances = Integer.parseInt("" + execution.getVariable("nrOfInstances"));
        int nrOfActiveInstances = Integer.parseInt("" + execution.getVariable("nrOfActiveInstances"));
        int nrOfCompletedInstances = Integer.parseInt("" + execution.getVariable("nrOfCompletedInstances"));
        System.out.println("总的会签任务数量：" + nrOfInstances + "当前获取的会签任务数量：" + nrOfActiveInstances + " - " +
                "已经完成的会签任务数量：" + nrOfCompletedInstances);
        boolean a = nrOfCompletedInstances/nrOfInstances >= 0.6;
        System.out.println("I am invoked.按照比例 60%的人办理就通过。" + a);
        return a;
    }
}