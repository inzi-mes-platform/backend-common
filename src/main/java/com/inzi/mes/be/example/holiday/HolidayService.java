package com.inzi.mes.be.example.holiday;

import java.util.List;

import com.inzi.mes.be.framework.workflow.HistoricActivityInstanceInfo;
import com.inzi.mes.be.framework.workflow.HistoricProcessInstanceInfo;
import com.inzi.mes.be.framework.workflow.HistoricTaskInfo;
import com.inzi.mes.be.framework.workflow.ProcessDefinitionInfo;
import com.inzi.mes.be.framework.workflow.ProcessInstanceInfo;
import com.inzi.mes.be.framework.workflow.TaskInfo;

public interface HolidayService {

	public List<TaskInfo> getActiveHumanTaskList(String userId);

	public List<HistoricTaskInfo> getHistoricHumanTaskList(String userId);
	
	public HistoricTaskInfo getHistoricHumanTask(String taskId);
	
	public Boolean checkIfHoliday(String taskId, Boolean isHoliday);
	
	public Boolean packForHoliday(String taskId);
	
	public Boolean packForWork(String taskId);
	
	public ProcessDefinitionInfo getProcessDefinition(String processDefinitionId);
	
	public String getProcessDefinitionDiagram(String processDefinitionId);
	
	public List<ProcessDefinitionInfo> getProcessDefinitionListByAll();
	
	public List<ProcessInstanceInfo> getProcessInstanceList(String processDefinitionId);
	
	public List<HistoricProcessInstanceInfo> searchHistoricProcessInstanceList(String processDefinitionId);
	
	public void startProcessInstance(String processDefinitionId, String businessKey);
	
	public List<HistoricActivityInstanceInfo> findActivityInstances(String processInstanceId);
}
