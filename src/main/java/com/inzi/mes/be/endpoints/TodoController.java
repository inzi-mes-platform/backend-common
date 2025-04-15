package com.inzi.mes.be.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inzi.mes.be.example.todo.TodoInfo;
import com.inzi.mes.be.example.todo.TodoService;
import com.inzi.mes.be.framework.Condition;
import com.inzi.mes.be.framework.persist.PageResponse;
import com.inzi.mes.be.framework.persist.RangeResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/api/v1/todo")
//@CrossOrigin(origins="*", allowedHeaders="*")
@Slf4j
public class TodoController {

	@Autowired
	private TodoService todoService;

	@RequestMapping(value="/register", method=RequestMethod.POST)
//	@CrossOrigin
//	@ResponseStatus(value=HttpStatus.OK)
	public void registerTodo(@RequestBody TodoInfo todo) {
		log.debug("");
		todoService.registerTodo(todo);
	}
	
	@RequestMapping(value="/clear/{id}", method=RequestMethod.GET)
//	@ResponseStatus(value=HttpStatus.OK)
	public void clearTodo(@PathVariable("id") Long id) {
		log.debug("");
		todoService.clearTodo(id);
	}
	
	@RequestMapping(value="/clear/byName/{name}", method=RequestMethod.GET)
//	@ResponseStatus(value=HttpStatus.OK)
	public void clearTodoByName(@PathVariable("name") String name) {
		log.debug("");
		todoService.clearTodoByName(name);
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
//	@ResponseStatus(value=HttpStatus.OK)
	public void updateTodo(@RequestBody TodoInfo todo) {
		log.debug("");
		todoService.updateTodo(todo);
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
//	@ResponseStatus(value=HttpStatus.OK)
	public void deleteTodo(@PathVariable("id") Long id) {
		log.debug("");
		todoService.deleteTodo(id);
	}
	
	@RequestMapping(value="/delete/byName/{name}", method=RequestMethod.GET)
//	@ResponseStatus(value=HttpStatus.OK)
	public void deleteTodoByName(@PathVariable("name") String name) {
		log.debug("");
		todoService.deleteTodoByName(name);
	}
	
	@RequestMapping(value="/find/{id}", method=RequestMethod.GET)
	@ResponseBody
//	@ResponseStatus(value=HttpStatus.OK)
	public TodoInfo findById(@PathVariable("id") Long id) {
		log.debug("");
		return todoService.findTodoById(id);
	}
	
	@RequestMapping(value="/find/byName/{name}", method=RequestMethod.GET)
	@ResponseBody
//	@ResponseStatus(value=HttpStatus.OK)
	public TodoInfo findByName(@PathVariable("name") String name) {
		log.debug("");
		return todoService.findTodoByName(name);
	}

	@RequestMapping(value="/search/all", method=RequestMethod.GET)
//	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
//	@CrossOrigin(origins = "http://localhost:3001")
	public List<TodoInfo> searchTodoAll() {
		log.debug("");
		return todoService.searchTodo();
	}
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
//	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
//	@CrossOrigin(origins = "http://localhost:3001")
	public List<TodoInfo> searchTodo(@RequestBody Condition cond) {
		log.debug("");
		return todoService.searchTodo(cond);
	}

	@RequestMapping(value="/search/page", method=RequestMethod.POST)
//	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	public PageResponse<TodoInfo> searchTodoByPage(@RequestBody Condition cond) {
		log.debug("");
		return todoService.searchTodoToPageResponse(cond);
	}


	@RequestMapping(value="/search/range", method=RequestMethod.POST)
//	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	public RangeResponse<TodoInfo> searchTodoByRange(@RequestBody Condition cond) {
		log.debug("");
		return todoService.searchTodoByRange(cond);
	}}
