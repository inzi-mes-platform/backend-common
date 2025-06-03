package com.inzi.mes.be.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import com.inzi.mes.be.example.async.MaterialInfo;
import com.inzi.mes.be.example.async.StreamingExampleService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/test/streaming/examples")
@Slf4j
public class StreamingExampleController {
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	@Autowired
	private StreamingExampleService exampleService;
	
	private final int divider=100;

	@GetMapping("/search1")
	public ResponseBodyEmitter searchByRange1() throws Exception {
		log.info("");
		ResponseBodyEmitter emitter = new ResponseBodyEmitter();
		int size= exampleService.getTotalCount();
		int quotient=size/divider, remainder=size%divider;
		taskExecutor.execute(()->{
			try {
				int from=0, to=0;
				List<MaterialInfo> sub=null;
				for(int i=0; i<quotient; i++) {
					from=i*divider; to=from+(divider-1);
					sub=exampleService.searchByRange(from, to);
					emitter.send(sub);
					emitter.send("\r\n");
				}
				if(remainder>0) {
					sub=exampleService.searchByRange(from, to);
				}
				emitter.complete();
			} catch (Exception e) {
				emitter.completeWithError(e);
			}
		});
		return emitter;
	}
	
	@GetMapping("/search2")
	public ResponseEntity<ResponseBodyEmitter> searchByRange2() throws Exception {
		log.debug("");
		ResponseBodyEmitter emitter = new ResponseBodyEmitter();
		int size= exampleService.getTotalCount();
		int quotient=size/divider, remainder=size%divider;
		taskExecutor.execute(()->{
			try {
				int from=0, to=0;
				List<MaterialInfo> sub=null;
				for(int i=0; i<quotient; i++) {
					from=i*divider; to=from+(divider-1);
					sub=exampleService.searchByRange(from, to);
					Thread.sleep(500);
					emitter.send(sub);
				}
				if(remainder>0) {
					from=quotient*divider; to=from+(divider-1);
					sub=exampleService.searchByRange(from, to);
				}
				emitter.complete();
			} catch (Exception e) {
				emitter.completeWithError(e);
			}
		});

		return ResponseEntity
				.status(HttpStatus.I_AM_A_TEAPOT)
				.header("Custom-Header", "Custom-Value")
				.body(emitter);
	}
}
