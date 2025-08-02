package com.inzi.mes.be.example.holiday.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inzi.mes.be.example.holiday.HolidayService;
import com.inzi.mes.be.framework.workflow.HistoricActivityInstanceInfo;
import com.inzi.mes.be.framework.workflow.HistoricProcessInstanceInfo;
import com.inzi.mes.be.framework.workflow.HistoricTaskInfo;
import com.inzi.mes.be.framework.workflow.ProcessDefinitionInfo;
import com.inzi.mes.be.framework.workflow.ProcessInstanceInfo;
import com.inzi.mes.be.framework.workflow.TaskInfo;
import com.inzi.mes.be.framework.workflow.VariableValueInfo;
import com.inzi.mes.be.framework.workflow.WorkflowManager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HolidayServiceImpl implements HolidayService {
	
	@Autowired
	private WorkflowManager workflowManager;

	@Override
	public List<TaskInfo> getActiveHumanTaskList(String userId) {
		log.debug("");
		return workflowManager.findActiveHumanTaskList(userId);
	}

	@Override
	public List<HistoricTaskInfo> getHistoricHumanTaskList(String userId) {
		log.debug("");
		return workflowManager.findHistoricTaskListByAssignee(userId, 0, 100);
	}
	
	@Override
	public HistoricTaskInfo getHistoricHumanTask(String taskId) {
		log.debug("");
		return workflowManager.findHistoricHumanTaskByTaskId(taskId);
	}

	@Override
	public Boolean checkIfHoliday(String taskId, Boolean isHoliday) {
		log.debug("");
		VariableValueInfo var = new VariableValueInfo();
		var.setType("Boolean");
		var.setValue(isHoliday);
		Map<String, VariableValueInfo> params=new HashMap<>();
		params.put("isHoliday", var);
		return workflowManager.completeTask(null, taskId, params);
	}

	@Override
	public Boolean packForHoliday(String taskId) {
		log.debug("pack for holiday...");
		VariableValueInfo var = new VariableValueInfo();
		var.setType("Boolean");
		var.setValue(false);
		Map<String, VariableValueInfo> variables=new HashMap<>();
		variables.put("isHoliday", var);
		return workflowManager.completeTask(null, taskId, variables);
	}

	@Override
	public Boolean packForWork(String taskId) {
		log.debug("pack for work...");
		VariableValueInfo var = new VariableValueInfo();
		var.setType("Boolean");
		var.setValue(true);
		Map<String, VariableValueInfo> variables=new HashMap<>();
		variables.put("isHoliday", var);
		return workflowManager.completeTask(null, taskId, variables);
	}

	@Override
	public ProcessDefinitionInfo getProcessDefinition(String processDefinitionId) {
		log.debug("");
		return workflowManager.findProcessDefinitionById(processDefinitionId);
	}

	@Override
	public String getProcessDefinitionDiagram(String processDefinitionId) {
		log.debug("");
		return workflowManager.getProcessDefintionDiagramByBpmn20Xml(processDefinitionId);
	}

	@Override
	public List<ProcessDefinitionInfo> getProcessDefinitionListByAll() {
		log.debug("");
		return workflowManager.getProcessDefinitionsByAll();
	}

	@Override
	public List<ProcessInstanceInfo> getProcessInstanceList(String processDefinitionId) {
		log.debug("");
		return workflowManager.findProcessInstanceByProcessDefinitionId(processDefinitionId);
	}

	@Override
	public List<HistoricProcessInstanceInfo> searchHistoricProcessInstanceList(String processDefinitionId) {
		log.debug("");
		return workflowManager.findHistoricProcessInstanceByProcessDefinitionId(processDefinitionId);
	}

	@Override
	public void startProcessInstance(String processDefinitionId, String businessKey) {
		log.debug("");
		workflowManager.startNewProcessInstance(processDefinitionId, businessKey, null);
	}

	@Override
	public List<HistoricActivityInstanceInfo> findActivityInstances(String processInstanceId) {
		log.debug("");
		return workflowManager.findActivityInstanceByProcessInstanceId(processInstanceId);
	}
}
