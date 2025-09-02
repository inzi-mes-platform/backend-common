package com.inzi.mes.be.common.material;

import java.util.List;

import com.inzi.mes.be.framework.Condition;
import com.inzi.mes.be.framework.persist.PageResponse;
import com.inzi.mes.be.framework.persist.RangeResponse;

public interface MaterialService {

	public List<MaterialInfo> findMaterialsByConditionByAll(Condition condition) throws Exception;
	
	public PageResponse<MaterialInfo> findMaterialsByPage(Condition condition) throws Exception;
	
	public RangeResponse<MaterialInfo> findMaterialsByRange(Condition condition) throws Exception;
	
	public MaterialInfo findMaterialByCode(String code) throws Exception;
	
	public Long getTotalCount() throws Exception;
	
	public List<MaterialInfo> findByRange(int from, int to) throws Exception;
}
