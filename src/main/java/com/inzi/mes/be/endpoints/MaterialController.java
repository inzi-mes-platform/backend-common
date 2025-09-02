package com.inzi.mes.be.endpoints;

import java.util.List;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import com.inzi.mes.be.common.material.MaterialInfo;
import com.inzi.mes.be.common.material.MaterialService;
import com.inzi.mes.be.framework.Condition;
import com.inzi.mes.be.framework.persist.PageResponse;
import com.inzi.mes.be.framework.persist.RangeResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/api/v1/material")
@Slf4j
public class MaterialController {
	
	@Autowired
	private MaterialService materialService;
	
	@RequestMapping(value="/find/{code}", method=RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(value=HttpStatus.OK)
	@CrossOrigin
	public MaterialInfo findById(@PathVariable("code") String code) throws Exception {
		log.debug("");
		return materialService.findMaterialByCode(code);
	}
	
	@RequestMapping(value="/find/all", method=RequestMethod.GET)
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	@CrossOrigin
	public List<MaterialInfo> findMaterialsAll() throws Exception {
		log.debug("");
		return materialService.findMaterialsByConditionByAll(new Condition());
	}
	
	@RequestMapping(value="/find", method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	@CrossOrigin
	public List<MaterialInfo> findMaterialByCondition(@RequestBody Condition cond) throws Exception {
		log.debug("");
		return materialService.findMaterialsByConditionByAll(cond);
	}

	@RequestMapping(value="/find/byPage", method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	@CrossOrigin
	public PageResponse<MaterialInfo> searchMaterialsByPage(@RequestBody Condition cond) throws Exception {
		log.debug("");
		return materialService.findMaterialsByPage(cond);
	}


	@RequestMapping(value="/find/byRange", method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	@CrossOrigin
	public RangeResponse<MaterialInfo> searchMaterialsByRange(@RequestBody Condition cond) throws Exception {
		log.debug("");
		return materialService.findMaterialsByRange(cond);
	}
	
	@Autowired
	private ExecutorService executor;
	
	private final int divider=2;
	
	@RequestMapping(value="/find/all/as-stream", method=RequestMethod.GET)
	@CrossOrigin
//	@PreAuthorize("hasAuthority('ADMIN')")
//	@PreAuthorize("permitAll()")
//	@PostAuthorize("permitAll()")
//	@Async("asyncExecutor")
	public ResponseBodyEmitter findAllByStreaming() throws Exception {
		log.debug("");

		ResponseBodyEmitter emitter = new ResponseBodyEmitter(-1L);
		long totalCount= materialService.getTotalCount();
		int quotient=(int)totalCount/divider, remainder=(int)totalCount%divider;
		
		executor.submit(new DelegatingSecurityContextRunnable(()->{
			try {
				int from=0, to=0;
				List<MaterialInfo> sub=null;
				for(int i=0; i<quotient; i++) {
					from=i*divider; to=from+(divider-1);
					sub=materialService.findByRange(from, to);
					emitter.send(sub);
					emitter.send("\r\n");
				}
				if(remainder>0) {
					from=to+1; to=from+remainder-1;
					sub=materialService.findByRange(from, to);
					emitter.send(sub);
					emitter.send("\r\n");
				}
				emitter.complete();
			} catch (Exception e) {
				emitter.completeWithError(e);
			}
		}));
		return emitter;
	}
}
