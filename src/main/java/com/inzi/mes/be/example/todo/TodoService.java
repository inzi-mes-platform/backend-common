package com.inzi.mes.be.example.todo;

import java.util.List;

import com.inzi.mes.be.framework.Condition;
import com.inzi.mes.be.framework.Condition.PageInfo;
import com.inzi.mes.be.framework.Condition.RangeInfo;
import com.inzi.mes.be.framework.persist.PageResponse;
import com.inzi.mes.be.framework.persist.RangeResponse;

public interface TodoService {
	
	public void registerTodo(TodoInfo todoInfo);
	
	public void updateTodo(TodoInfo todoInfo);
	
	public void clearTodo(long id);
	
	public void clearTodoByName(String name);
	
	public void deleteTodo(long id);
	
	public void deleteTodos(List<Long> idArr);
	
	public void deleteTodoByName(String name);
	
	public TodoInfo findTodoById(long id);
	
	public TodoInfo findTodoByName(String name);
	
	public List<TodoInfo> searchTodo();
	
	public List<TodoInfo> searchTodo(PageInfo pInfo);
	
	public List<TodoInfo> searchTodo(Condition cond);
	
	public List<TodoInfo> searchTodo(Condition cond, PageInfo pInfo);
	
	public PageResponse<TodoInfo> searchTodoToPageResponse(PageInfo pInfo);
	
	public PageResponse<TodoInfo> searchTodoToPageResponse(Condition cond);
	
	public PageResponse<TodoInfo> searchTodoToPageResponse(Condition cond, PageInfo pInfo);
	
	public RangeResponse<TodoInfo> searchTodoByRange(Condition cond);
	
	public RangeResponse<TodoInfo> searchTodoByRange(Condition cond, RangeInfo rInfo);
}
