package com.inzi.mes.be.example.async.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.inzi.mes.be.example.async.MaterialInfo;
import com.inzi.mes.be.example.async.StreamingExampleService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StreamingExampleServiceImpl implements StreamingExampleService {
	
	private List<MaterialInfo> mList;
	
	@PostConstruct
	public void init() {
		mList=new ArrayList<>();
		for(int i=0; i<1000; i++) {
			int index=i+1;
			mList.add(new MaterialInfo("code-"+index, "name-"+index, "{ 'spec':'spec-" + index + "' }"));
		}
	}

	@Override
	public List<MaterialInfo> searchByRange (int from, int to) {
		log.info("####### searchByRange from: {}, to: {}", from, to);
		return mList.subList(from, to+1);
	}
	
	@Override
	public int getTotalCount() {
		return mList.size();
	}
}