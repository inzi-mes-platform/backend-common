package com.inzi.mes.be.endpoints;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/api/v1/workflow")
@Slf4j
public class WorkflowController {
	
	@Autowired
	private WorkflowService workflowService;

	@GetMapping(value="/process-definition/find/byId/{processDefinitionId}")
	@ResponseBody
	@CrossOrigin
	public ProcessDefinitionInfo findProcessDefinitionById(@PathVariable("processDefinitionId") String processDefinitionId) {
		log.debug("");
		return workflowService.findProcessDefinitionById(processDefinitionId);
	}

	@GetMapping(value="/process-definition/diagramXml/byId/{processDefinitionId}")
	@ResponseBody
	@CrossOrigin
	public String getProcessDefinitionDiagramByBpmn20Xml(@PathVariable("processDefinitionId") String processDefinitionId) {
		log.debug("");
		return workflowService.getProcessDefinitionDiagramByBpmn20Xml(processDefinitionId);
	}

	@PostMapping(value="/process-definition/find")
	@ResponseBody
	@CrossOrigin
	public List<ProcessDefinitionInfo> findProcessDefinitions(@RequestBody ProcessDefinitionQueryInfo query) {
		log.debug("");
		return workflowService.findProcessDefinitions(query);
	}
	
	@GetMapping(value="/process-instance/find/byPrpcessDefinition/{processDefinitionId}")
	@ResponseBody
	@CrossOrigin
	public List<ProcessInstanceInfo> findProcessInstances(@PathVariable("") String processDefinitionId) {
		log.debug("");
		ProcessInstanceQueryInfo query=new ProcessInstanceQueryInfo();
		query.setProcessDefinitionId(processDefinitionId);
		return workflowService.findProcessInstances(query);
	}

	@PostMapping(value="/process-instance/find")
	@ResponseBody
	@CrossOrigin
	public List<ProcessInstanceInfo> findProcessInstances(@RequestBody ProcessInstanceQueryInfo query) {
		log.debug("");
		return workflowService.findProcessInstances(query);
	}

	@GetMapping(value="/process-instance/find/byId/{processInstanceId}")
	@ResponseBody
	@CrossOrigin
	public ProcessInstanceInfo findProcessInstanceById(@PathVariable("processInstanceId") String processInstanceId) {
		log.debug("");
		return workflowService.findProcessInstanceById(processInstanceId);
	}

	@GetMapping(value="/process-instance/suspend/byId/{processInstanceId}")
	@CrossOrigin
	public void suspendProcessInstance(@PathVariable("processInstanceId") String processInstanceId) {
		log.debug("");
		workflowService.suspendProcessInstance(processInstanceId);
	}

	@GetMapping(value="/process-instance/activate/byId/{processInstanceId}")
	@CrossOrigin
	public void activateProcessInstance(@PathVariable("processInstanceId") String processInstanceId) {
		log.debug("");
		workflowService.activateProcessInstance(processInstanceId);
	}

	@GetMapping(value="/process-instance/stop/byId/{processInstanceId}")
	@CrossOrigin
	public void stopProcessInstanceByforce(@PathVariable("processInstanceId") String processInstanceId) {
		log.debug("");
		workflowService.stopProcessInstanceByforce(processInstanceId);
	}

	@GetMapping(value="/process-instance/start/{processDefinitionId}/{businessKey}")
	@CrossOrigin
	public String startNewProcessInstance(@PathVariable("processDefinitionId") String processDefinitionId, @PathVariable("businessKey") String businessKey) {
		log.debug("");
		return workflowService.startNewProcessInstance(processDefinitionId, businessKey, null);
	}
	
	@PostMapping(value="/process-instance/start/with-variables/{processDefinitionId}/{businessKey}")
	@CrossOrigin
	public String startNewProcessInstanceWitnVariables(
			@PathVariable("processInstanceId") String processDefinitionId, 
			@PathVariable("businessKey") String businessKey, 
			@RequestBody Map<String, VariableValueInfo> variables) {
		log.debug("");
		return workflowService.startNewProcessInstance(processDefinitionId, businessKey, variables);
	}

	@PostMapping(value="/process-instance/historic/find")
	@ResponseBody
	@CrossOrigin
	public List<HistoricProcessInstanceInfo> findHistoricProcessInstances(@RequestBody HistoricProcessInstanceQueryInfo query) {
		log.debug("");
		return workflowService.findHistoricProcessInstancesByQuery(query);
	}
	
	@GetMapping(value="/process-instance/trace/{processInstanceId}")
	@ResponseBody
	@CrossOrigin
	public List<HistoricActivityInstanceInfo> traceProcessInstanceExecution(@PathVariable("processInstanceId") String processInstanceId) {
		log.debug("");
		return workflowService.traceProcessInstanceExecution(processInstanceId);
	}

	@PostMapping(value="/task/find")
	@ResponseBody
	@CrossOrigin
	public List<TaskInfo> findTasksByQuery(@RequestBody TaskQueryInfo query) {
		log.debug("");
		return workflowService.findTasksByQuery(query);
	}

	@PostMapping(value="/task/complete/{taskId}")
	@CrossOrigin
	public void completeTask(@PathVariable("taskId") String taskId, @RequestBody Map<String, VariableValueInfo> params) {
		log.debug("");
		workflowService.completeTask(taskId, params);
	}

	@GetMapping(value="/task/human/claim/{taskId}/{assignee}")
	@CrossOrigin
	public void claimHumanTask(String taskId, String assignee) {
		log.debug("");
		workflowService.claimTask(taskId, assignee);
	}

	@GetMapping(value="/task/human/unclaim/{taskId}/{assignee}")
	@CrossOrigin
	public void unclaimHumanTask(String taskId, String assignee) {
		log.debug("");
		workflowService.unclaimTask(taskId);
	}

	@PostMapping(value="/task/historic/find")
	@ResponseBody
	@CrossOrigin
	public List<HistoricTaskInfo> findHistoricTasksByQuery(@RequestBody HistoricTaskQueryInfo query) {
		log.debug("");
		List<HistoricTaskInfo> hTasks=workflowService.findHistoricTasksByQuery(query);
		return hTasks;
	}
	
	@GetMapping(value="/variable-instance/trace/byProcessInstanceId/{processInstanceId}")
	@ResponseBody
	@CrossOrigin
	public Map<String, List<HistoricVariableInstanceInfo>> traceAllVariableInstancesPath(@PathVariable("processInstanceId") String processInstanceId) {
		log.debug("");
		return workflowService.traceAllVariableInstancesPath(processInstanceId);
	}

	@GetMapping(value="/variable-instance/trace/byVariableInstanceId/{processInstanceId}/{variableInstanceId}")
	@ResponseBody
	@CrossOrigin
	public List<HistoricVariableInstanceInfo> traceVariableInstancePathByVariableInstanceId(
			@PathVariable("processInstanceId") String processInstanceId, @PathVariable("variableInstanceId") String variableInstanceId) {
		log.debug("");
		return workflowService.traceVariableInstancePathByVariableInstanceId(processInstanceId, variableInstanceId);
	}
}
