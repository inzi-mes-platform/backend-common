package com.inzi.mes.be.common.wflow;

import java.util.List;
import java.util.Map;

import com.inzi.mes.be.framework.workflow.HistoricActivityInstanceInfo;
import com.inzi.mes.be.framework.workflow.HistoricProcessInstanceInfo;
import com.inzi.mes.be.framework.workflow.HistoricProcessInstanceQueryInfo;
import com.inzi.mes.be.framework.workflow.HistoricTaskInfo;
import com.inzi.mes.be.framework.workflow.HistoricTaskQueryInfo;
import com.inzi.mes.be.framework.workflow.HistoricVariableInstanceInfo;
import com.inzi.mes.be.framework.workflow.ProcessDefinitionInfo;
import com.inzi.mes.be.framework.workflow.ProcessDefinitionQueryInfo;
import com.inzi.mes.be.framework.workflow.ProcessInstanceInfo;
import com.inzi.mes.be.framework.workflow.ProcessInstanceQueryInfo;
import com.inzi.mes.be.framework.workflow.TaskInfo;
import com.inzi.mes.be.framework.workflow.TaskQueryInfo;
import com.inzi.mes.be.framework.workflow.VariableValueInfo;

public interface WorkflowService {
	
	public ProcessDefinitionInfo findProcessDefinitionById(String processDefinitionId);
	
	public String getProcessDefinitionDiagramByBpmn20Xml(String processDefinitionId);
	
	public List<ProcessDefinitionInfo> findProcessDefinitions(ProcessDefinitionQueryInfo query);
	
	public List<ProcessInstanceInfo> findProcessInstances(ProcessInstanceQueryInfo query);
	
	public ProcessInstanceInfo findProcessInstanceById(String processInstanceId);
	
	public boolean suspendProcessInstance(String processInstanceId);
	
	public boolean activateProcessInstance(String processInstanceId);
	
	public boolean stopProcessInstanceByforce(String processInstanceId);
	
	public String startNewProcessInstance(String processDefinitionId, String businessKey, Map<String, VariableValueInfo> variables);
	
	public List<HistoricProcessInstanceInfo> findHistoricProcessInstancesByQuery(HistoricProcessInstanceQueryInfo query);
	
	public List<TaskInfo> findTasksByQuery(TaskQueryInfo query);
	
	public boolean completeTask(String taskId, Map<String, VariableValueInfo> params);
	
	public boolean claimTask(String taskId, String assignee);
	
	public boolean unclaimTask(String taskId);
	
	public List<HistoricTaskInfo> findHistoricTasksByQuery(HistoricTaskQueryInfo query);
	
	public List<HistoricActivityInstanceInfo> traceProcessInstanceExecution(String processInstanceId);
	
	public Map<String, List<HistoricVariableInstanceInfo>> traceAllVariableInstancesPath(String processInstanceId);
    
    public List<HistoricVariableInstanceInfo> traceVariableInstancePathByVariableInstanceId(String processInstanceId, String variableInstanceId);
}
