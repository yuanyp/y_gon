package com.gon.test;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateExecution;

public class CHO implements Serializable{
	public boolean choNode(DelegateExecution e){
		System.out.println(e.getVariables());
		return true;
	}
}
