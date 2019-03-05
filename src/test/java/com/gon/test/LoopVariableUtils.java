package com.gon.test;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

public class LoopVariableUtils {

    public static void setLoopVariable(ExecutionEntity executionEntity,String varName,Object value){
        ExecutionEntity executionEntityParent = executionEntity.getParent();
        executionEntityParent.setVariableLocal(varName,value);
    }

    public static Integer getLoopVariable(ExecutionEntity executionEntity,String varName){
        Object value = executionEntity.getVariableLocal(varName);
        ExecutionEntity executionEntityParent = executionEntity.getParent();
        while(value == null && executionEntityParent != null){
            value = executionEntityParent.getVariableLocal(varName);
            executionEntityParent = executionEntityParent.getParent();
        }
        return (Integer)(value != null ? value : 0);
    }
}
