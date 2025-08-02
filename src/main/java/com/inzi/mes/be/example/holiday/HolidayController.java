package com.inzi.mes.be.example.holiday;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inzi.mes.be.framework.workflow.HistoricActivityInstanceInfo;
import com.inzi.mes.be.framework.workflow.HistoricProcessInstanceInfo;
import com.inzi.mes.be.framework.workflow.HistoricTaskInfo;
import com.inzi.mes.be.framework.workflow.ProcessDefinitionInfo;
import com.inzi.mes.be.framework.workflow.ProcessInstanceInfo;
import com.inzi.mes.be.framework.workflow.TaskInfo;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/api/v1")
@Slf4j
public class HolidayController {

	@Autowired
	private HolidayService holidayService;
	
	@PostMapping(value="/service1")
	@CrossOrigin
	public void service1(@RequestBody String req) {
		log.debug("service1 data arrived ...");
	}
	
	@GetMapping(value="/process-definition/{processDefinitionId}")
	@CrossOrigin
	public ProcessDefinitionInfo getProcessDefinition(@PathVariable("processDefinitionId") String processDefinitionId) {
		log.debug("process definition request arrived ...");
		return holidayService.getProcessDefinition(processDefinitionId);
	}
	
	@GetMapping(value="/process-definition/list")
	@CrossOrigin
	public List<ProcessDefinitionInfo> getProcessList() {
		log.debug("process definition list request arrived ...");
		return holidayService.getProcessDefinitionListByAll();
	}

	@GetMapping(value="/process-instance/list/{processDefinitionId}")
	@CrossOrigin
	public List<ProcessInstanceInfo> getProcessInstanceList(@PathVariable("processDefinitionId") String processDefinitionId) {
		log.debug("process instance list request arrived ...");
		return holidayService.getProcessInstanceList(processDefinitionId);
	}
	
	@GetMapping(value="/historic-process-instance/list/{processDefinitionId}")
	@CrossOrigin
	public List<HistoricProcessInstanceInfo> getHistoricProcessInstanceList(@PathVariable("processDefinitionId") String processDefinitionId) {
		log.debug("historic process instance list request arrived ...");
		return holidayService.searchHistoricProcessInstanceList(processDefinitionId);
	}
	
	@GetMapping(value="/process-instance/start/{pdId}/{businessKey}")
	@CrossOrigin
	public void startProcessInstance(@PathVariable("pdId") String pdId, @PathVariable("businessKey") String businessKey) {
		log.debug("process instance list request arrived ...");
		holidayService.startProcessInstance(pdId, businessKey);
	}
	
	@GetMapping(value="/diagramXml/{processDefinitionId}")
	@CrossOrigin
	public String getDiagramXml(@PathVariable("processDefinitionId") String processDefinitionId) {
		log.debug("diagramXml request arrived ...");
		String xml = holidayService.getProcessDefinitionDiagram(processDefinitionId);
		return xml;
	}
	
	@GetMapping(value="/activity-instance/list/{processInstanceId}")
	@CrossOrigin
	public List<HistoricActivityInstanceInfo> findActivityInstances(@PathVariable("processInstanceId") String processInstanceId) {
		log.debug("activity instance list request arrived ...");
		return holidayService.findActivityInstances(processInstanceId);
	}
	
	@GetMapping(value="/my-task/list/{userId}")
	@CrossOrigin
	public List<TaskInfo> service2(@PathVariable("userId") String userId) {
		log.debug("todo-list request arrived ...");
		return holidayService.getActiveHumanTaskList(userId);
	}
	
	@GetMapping(value="/my-task/historic/{taskId}")
	@CrossOrigin
	public HistoricTaskInfo getHistoricHumanTask(@PathVariable("taskId") String taskId) {
		log.debug("");
		return holidayService.getHistoricHumanTask(taskId);
	}
	
	@GetMapping(value="/do-work/check-if-holiday/{taskId}/{isHoliday}")
	@CrossOrigin
	public Response checkHoliday(@PathVariable String taskId, @PathVariable Boolean isHoliday) {
		Response res=new Response();
		res.setResult(holidayService.checkIfHoliday(taskId, isHoliday));
		return res;
	}
	
	@PostMapping(value="/do-work/check-if-holiday")
	@CrossOrigin
	public Response checkHoliday(@RequestBody HolidayCheckInfo holidayCheck) {
		Response res=new Response();
		res.setResult(holidayService.checkIfHoliday(holidayCheck.getTaskId(), holidayCheck.getIsHoliday()));
		return res;
	}
	
	@PostMapping(value="/do-work/pack-for-holiday/{taskId}")
	@CrossOrigin
	public Response packForHoliday(@PathVariable String taskId) {
		Response res=new Response();
		res.setResult(holidayService.packForHoliday(taskId));
		return res;
	}
	
	@PostMapping(value="/do-work/pack-for-work/{taskId}")
	@CrossOrigin
	public Response packForWork(@PathVariable String taskId) {
		Response res=new Response();
		res.setResult(holidayService.packForWork(taskId));
		return res;
	}
}
