package com.inzi.mes.be.common.wflow.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inzi.mes.be.common.wflow.WorkflowService;
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
import com.inzi.mes.be.framework.workflow.WorkflowManager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WorkflowServiceImpl implements WorkflowService {
	
	@Autowired
	private WorkflowManager workflowManager;

	@Override
	public ProcessDefinitionInfo findProcessDefinitionById(String processDefinitionId) {
		log.debug("");
		return workflowManager.findProcessDefinitionById(processDefinitionId);
	}

	@Override
	public String getProcessDefinitionDiagramByBpmn20Xml(String processDefinitionId) {
		log.debug("");
		return workflowManager.getProcessDefintionDiagramByBpmn20Xml(processDefinitionId);
	}

	@Override
	public List<ProcessDefinitionInfo> findProcessDefinitions(ProcessDefinitionQueryInfo query) {
		log.debug("");
		Integer pageNum=query.getPageNum(), 
				rowsPerPage=query.getRowsPerPage(), 
				firstResult=query.getFirstResult(), 
				maxResults=query.getMaxResults();
		if(firstResult==null || maxResults==null) {
			if( pageNum==null ) pageNum=1;
			if( rowsPerPage==null ) rowsPerPage=5;
			query.setFirstResult( (pageNum-1)*rowsPerPage );
			query.setMaxResults( query.getFirstResult()+rowsPerPage-1 );
		}
		return workflowManager.findProcessDefinitionsByQuery(query);
	}

	@Override
	public List<ProcessInstanceInfo> findProcessInstances(ProcessInstanceQueryInfo query) {
		log.debug("");
		Integer pageNum=query.getPageNum(), 
				rowsPerPage=query.getRowsPerPage(), 
				firstResult=query.getFirstResult(), 
				maxResults=query.getMaxResults();
		if(firstResult==null || maxResults==null) {
			if( pageNum==null ) pageNum=1;
			if( rowsPerPage==null ) rowsPerPage=5;
			query.setFirstResult( (pageNum-1)*rowsPerPage );
			query.setMaxResults( query.getFirstResult()+rowsPerPage-1 );
		}
		return workflowManager.findProcessInstanceByQuery(query);
	}

	@Override
	public ProcessInstanceInfo findProcessInstanceById(String processInstanceId) {
		log.debug("");
		return workflowManager.findProcessInstanceById(processInstanceId);
	}

	@Override
	public boolean suspendProcessInstance(String processInstanceId) {
		log.debug("");
		return workflowManager.suspendProcessInstance(processInstanceId);
	}

	@Override
	public boolean activateProcessInstance(String processInstanceId) {
		log.debug("");
		return workflowManager.activateProcessInstance(processInstanceId);
	}

	@Override
	public boolean stopProcessInstanceByforce(String processInstanceId) {
		log.debug("");
		return workflowManager.stopProcessInstance(processInstanceId);
	}

	@Override
	public String startNewProcessInstance(String processDefinitionId, String businessKey, Map<String, VariableValueInfo> variables) {
		log.debug("");
		return workflowManager.startNewProcessInstance(processDefinitionId, businessKey, variables);
	}

	@Override
	public List<HistoricProcessInstanceInfo> findHistoricProcessInstancesByQuery(HistoricProcessInstanceQueryInfo query) {
		log.debug("");
		Integer pageNum=query.getPageNum(), 
				rowsPerPage=query.getRowsPerPage(), 
				firstResult=query.getFirstResult(), 
				maxResults=query.getMaxResults();
		if(firstResult==null || maxResults==null) {
			if( pageNum==null ) pageNum=1;
			if( rowsPerPage==null ) rowsPerPage=5;
			query.setFirstResult( (pageNum-1)*rowsPerPage );
			query.setMaxResults( query.getFirstResult()+rowsPerPage-1 );
		}
		return workflowManager.findHistoricProcessInstanceByQuery(query);
	}

	@Override
	public List<TaskInfo> findTasksByQuery(TaskQueryInfo query) {
		log.debug("");
		Integer pageNum=query.getPageNum(), 
				rowsPerPage=query.getRowsPerPage(), 
				firstResult=query.getFirstResult(), 
				maxResults=query.getMaxResults();
		if(firstResult==null || maxResults==null) {
			if( pageNum==null ) pageNum=1;
			if( rowsPerPage==null ) rowsPerPage=5;
			query.setFirstResult( (pageNum-1)*rowsPerPage );
			query.setMaxResults( query.getFirstResult()+rowsPerPage-1 );
		}
		return workflowManager.findTasksByQuery(query);
	}

	@Override
	public boolean completeTask(String taskId, Map<String, VariableValueInfo> params) {
		log.debug("");
		return workflowManager.completeTask(null, taskId, params);
	}

	@Override
	public boolean claimTask(String taskId, String assignee) {
		log.debug("");
		return workflowManager.claimTask(taskId, assignee);
	}

	@Override
	public boolean unclaimTask(String taskId) {
		log.debug("");
		return workflowManager.unclaimTask(taskId);
	}
	
	@Override
	public List<HistoricTaskInfo> findHistoricTasksByQuery(HistoricTaskQueryInfo query) {
		log.debug("");
		Integer pageNum=query.getPageNum(), 
				rowsPerPage=query.getRowsPerPage(), 
				firstResult=query.getFirstResult(), 
				maxResults=query.getMaxResults();
		if(firstResult==null || maxResults==null) {
			if( pageNum==null ) pageNum=1;
			if( rowsPerPage==null ) rowsPerPage=5;
			query.setFirstResult( (pageNum-1)*rowsPerPage );
			query.setMaxResults( query.getFirstResult()+rowsPerPage-1 );
		}
		return workflowManager.findHistoricTasksByQuery(query);
	}

	@Override
	public List<HistoricActivityInstanceInfo> traceProcessInstanceExecution(String processInstanceId) {
		log.debug("");
		return workflowManager.traceProcessInstanceExecution(processInstanceId);
	}
	
	@Override
	public Map<String, List<HistoricVariableInstanceInfo>> traceAllVariableInstancesPath(String processInstanceId) {
		log.debug("");
		return workflowManager.traceAllVariableInstancesPath(processInstanceId);
	}
    
	@Override
    public List<HistoricVariableInstanceInfo> traceVariableInstancePathByVariableInstanceId(String processInstanceId, String variableInstanceId) {
		log.debug("");
		return workflowManager.traceVariableInstancePathByVariableInstanceId(processInstanceId, variableInstanceId);
    }
}