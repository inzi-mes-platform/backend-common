package com.inzi.mes.be.example.todo.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.inzi.mes.be.example.todo.TodoInfo;
import com.inzi.mes.be.example.todo.TodoService;
import com.inzi.mes.be.framework.Condition;
import com.inzi.mes.be.framework.Condition.PageInfo;
import com.inzi.mes.be.framework.Condition.RangeInfo;
import com.inzi.mes.be.framework.Condition.SortDirection;
import com.inzi.mes.be.framework.persist.PageResponse;
import com.inzi.mes.be.framework.persist.PersistentService;
import com.inzi.mes.be.framework.persist.RangeResponse;
import com.inzi.mes.be.framework.persist.Rangeable;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TodoServiceImpl implements TodoService, PersistentService<TodoInfo, TodoEntity> {
	
	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private TodoRangeRepository todoRangeRepository;


	@Transactional
	@Override
	public void registerTodo(TodoInfo todoInfo) {
		log.debug("");
		TodoEntity todoEntity=new TodoEntity();
		todoEntity.from(todoInfo);
		todoRepository.save(todoEntity);
	}

	@Transactional
	@Override
	public void updateTodo(TodoInfo todoInfo) {
		log.debug("");
		TodoEntity todoEntity=todoRepository.findById(todoInfo.getId()).get();
		todoEntity.from(todoInfo);
		todoRepository.save(todoEntity);
	}

	@Transactional
	@Override
	public void clearTodo(long id) {
		log.debug("");
		TodoEntity todoEntity=todoRepository.findById(id).get();
		todoEntity.setCleared(true);
		todoRepository.save(todoEntity);
	}

	@Transactional
	@Override
	public void clearTodoByName(String name) {
		log.debug("");
		TodoEntity probe=new TodoEntity();
		probe.setName(name);
		
		ExampleMatcher matcher = ExampleMatcher.matchingAny();
		matcher=matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.exact());
		Example<TodoEntity> example=Example.of(probe, matcher);

		TodoEntity todoEntity = todoRepository.findOne(example).get();
		todoEntity.setCleared(true);
		
		todoRepository.save(todoEntity);
	}

	@Override
	public void deleteTodo(long id) {
		log.debug("");
		todoRepository.deleteById(id);
		
	}

	@Override
	public void deleteTodos(List<Long> idArr) {
		log.debug("");
		todoRepository.deleteAllByIdInBatch(idArr);
	}

	@Override
	public void deleteTodoByName(String name) {
		log.debug("");
		TodoEntity probe=new TodoEntity();
		probe.setName(name);
		
		ExampleMatcher matcher = ExampleMatcher.matchingAny();
		matcher=matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.exact());
		Example<TodoEntity> example=Example.of(probe, matcher);

		todoRepository.delete(todoRepository.findOne(example).get());
	}

	@Override
	public TodoInfo findTodoById(long id) {
		log.debug("");
		return todoRepository.findById(id).get().info();
	}

	@Override
	public TodoInfo findTodoByName(String name) {
		log.debug("");
		TodoEntity probe=new TodoEntity();
		probe.setName(name);
		
		ExampleMatcher matcher = ExampleMatcher.matchingAny();
		matcher=matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.exact());
		Example<TodoEntity> example=Example.of(probe, matcher);
		
		return todoRepository.findOne(example).get().info();
	}

	@Override
	public List<TodoInfo> searchTodo() {
		log.debug("");
		List<TodoEntity> todoEntityList = todoRepository.findAll();
		List<TodoInfo> todoInfoList = new ArrayList<>();
		todoEntityList.forEach(todo->{
			todoInfoList.add(todo.info());
		});
		return todoInfoList;
	}
	
	@Override
	public List<TodoInfo> searchTodo(PageInfo pInfo) {
		log.debug("");
		return infos(findToPage(pInfo).getContent());
	}

	@Override
	public List<TodoInfo> searchTodo(Condition cond) {
		log.debug("");
		TodoSpecs specs=new TodoSpecs();
		List<TodoEntity> todoEntityList=null;
		Specification<TodoEntity> specification=specs.createSpecification(cond);

		if(cond.getPageInfo()==null) {
			todoEntityList=todoRepository.findAll(specification);
		} else {
			PageInfo pInfo=cond.getPageInfo();
			Page<TodoEntity> page=findToPage(specification, pInfo);
			todoEntityList=page.getContent();
		}
		return infos(todoEntityList);
	}

	@Override
	public List<TodoInfo> searchTodo(Condition cond, PageInfo pInfo) {
		log.debug("");
		cond.setPageInfo(pInfo);
		return searchTodo(cond);
	}

	@Override
	public PageResponse<TodoInfo> searchTodoToPageResponse(PageInfo pInfo) {
		log.debug("");
		PageResponse<TodoInfo> pageResp=new PageResponse<>();
		Page<TodoEntity> page=findToPage(pInfo);
		pageResp.setPageInfo(pInfo);
		pageResp.setTotalRowsCount(page.getTotalElements());
		pageResp.setTotalPages(page.getTotalPages());
		pageResp.setRows(infos(page.getContent()));
		
		return pageResp;

	}

	@Override
	public PageResponse<TodoInfo> searchTodoToPageResponse(Condition cond) {
		log.debug("");
		TodoSpecs specs=new TodoSpecs();
		List<TodoEntity> todlEntityList=null;
		Specification<TodoEntity> specification=specs.createSpecification(cond);

		PageResponse<TodoInfo> pageResp=new PageResponse<>();
		if(cond.getPageInfo()==null) {
			todlEntityList=todoRepository.findAll(specification);
			pageResp.setTotalPages(todlEntityList.size());
			pageResp.setPageInfo(null);
		} else {
			PageInfo pInfo=cond.getPageInfo();
			Page<TodoEntity> page=findToPage(specification, pInfo);
			todlEntityList=page.getContent();
			pageResp.setTotalRowsCount(page.getTotalElements());
			pageResp.setTotalPages(page.getTotalPages());
			pageResp.setPageInfo(pInfo);
		}
		pageResp.setRows(infos(todlEntityList));
		return pageResp;

	}

	@Override
	public PageResponse<TodoInfo> searchTodoToPageResponse(Condition cond, PageInfo pInfo) {
		log.debug("");
		cond.setPageInfo(pInfo);
		return searchTodoToPageResponse(cond);	
	}

	@Override
	public RangeResponse<TodoInfo> searchTodoByRange(Condition cond) {
		log.debug("");
		TodoSpecs specs=new TodoSpecs();
		List<TodoEntity> todoEntityList=null;
		Specification<TodoEntity> specification=specs.createSpecification(cond);
		
		RangeResponse<TodoInfo> rangeResp=new RangeResponse<>();
		if(cond.getRangeInfo()==null) {
			todoEntityList=todoRepository.findAll(specification);
			rangeResp.setTotalRowsCount(todoEntityList.size());
			rangeResp.setRangeInfo(null);
		} else {
			RangeInfo rInfo=cond.getRangeInfo();
			Sort.Direction dir=rInfo.getSortDirection()==SortDirection.ASCENDING?Sort.Direction.ASC:Sort.Direction.DESC;
			Rangeable rangeRequest=new Rangeable(rInfo.getStart(), rInfo.getLimit(), Sort.by(dir, rInfo.getSortBy()));
			todoEntityList=todoRangeRepository.findAllByRange(specification, rangeRequest);
			rangeResp.setTotalRowsCount(todoRepository.count(specification));
			rangeResp.setRangeInfo(rInfo);
			rangeResp.setRows(infos(todoEntityList));
		}
		return rangeResp;

	}

	@Override
	public RangeResponse<TodoInfo> searchTodoByRange(Condition cond, RangeInfo rInfo) {
		log.debug("");
		cond.setRangeInfo(rInfo);
		return searchTodoByRange(cond);
	}
	
	private Page<TodoEntity> findToPage(Specification<TodoEntity> specification, PageInfo pageInfo) {
		log.debug("");
		if(pageInfo==null) return null;
		
		Sort.Direction dir=pageInfo.getSortDirection()==SortDirection.ASCENDING?Sort.Direction.ASC:Sort.Direction.DESC;
		PageRequest prequest=PageRequest.of(pageInfo.getPage(), pageInfo.getSize(), dir, pageInfo.getSortBy());
		return todoRepository.findAll(specification, prequest);
	}
	
	private Page<TodoEntity> findToPage(PageInfo pageInfo) {
		log.debug("");
		Sort.Direction dir=pageInfo.getSortDirection()==SortDirection.ASCENDING?Sort.Direction.ASC:Sort.Direction.DESC;
		PageRequest prequest=PageRequest.of(pageInfo.getPage(), pageInfo.getSize(), dir, pageInfo.getSortBy());
		return todoRepository.findAll(prequest);
	}
}
