package com.inzi.mes.be.common.material.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.inzi.mes.be.common.material.MaterialInfo;
import com.inzi.mes.be.common.material.MaterialService;
import com.inzi.mes.be.framework.Condition;
import com.inzi.mes.be.framework.Condition.PageInfo;
import com.inzi.mes.be.framework.Condition.RangeInfo;
import com.inzi.mes.be.framework.Condition.SortDirection;
import com.inzi.mes.be.framework.persist.PageResponse;
import com.inzi.mes.be.framework.persist.RangeResponse;
import com.inzi.mes.be.framework.persist.Rangeable;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MaterialServiceImpl implements MaterialService {
	
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private MaterialRangeableRepository materialRangeableRepository;
	
	private Page<MaterialEntity> findMaterialToPage(Specification<MaterialEntity> specification, PageInfo pageInfo) {
		log.debug("");
		if(pageInfo==null) return null;
		
		Sort.Direction dir=pageInfo.getSortDirection()==SortDirection.ASCENDING?Sort.Direction.ASC:Sort.Direction.DESC;
		PageRequest prequest=PageRequest.of(pageInfo.getPage(), pageInfo.getSize(), dir, pageInfo.getSortBy());
		return this.materialRepository.findAll(specification, prequest);
	}
	
	private List<MaterialInfo> toMaterialInfoList(List<MaterialEntity> prList) {
		List<MaterialInfo> infoList = new ArrayList<>();
		prList.forEach(entity->{
			infoList.add(entity.info());
		});
		return infoList;
	}

	@Override
	public List<MaterialInfo> findMaterialsByConditionByAll(Condition condition) throws Exception {
		log.debug("");
		MaterialSpec specs=new MaterialSpec();
		List<MaterialEntity> mList=null;
		Specification<MaterialEntity> specification=specs.createSpecification(condition);
		
		if(condition.getPageInfo() == null) {
			mList = this.materialRepository.findAll(specification);
		} else {
			PageInfo pInfo = condition.getPageInfo();
			Page<MaterialEntity> page = this.findMaterialToPage(specification, pInfo);
			mList = page.getContent();
		}
		return this.toMaterialInfoList(mList);
	}
	
	@Override
	public PageResponse<MaterialInfo> findMaterialsByPage(Condition condition) throws Exception {
		log.debug("");
		MaterialSpec specs = new MaterialSpec();
		List<MaterialEntity> mList = null;
		Specification<MaterialEntity> specification=specs.createSpecification(condition);
		
		PageResponse<MaterialInfo> pageResponse = new PageResponse<>();
		if( condition.getPageInfo() == null ) {
			mList = this.materialRepository.findAll(specification);
			pageResponse.setTotalPages(mList.size());
			pageResponse.setPageInfo(null);
		} else {
			PageInfo pInfo = condition.getPageInfo();
			Page<MaterialEntity> page = this.findMaterialToPage(specification, pInfo);
			mList = page.getContent();
			pageResponse.setTotalRowsCount(page.getTotalElements());
			pageResponse.setTotalPages(page.getTotalPages());
			pageResponse.setPageInfo(pInfo);
		}
		
		pageResponse.setRows( this.toMaterialInfoList(mList) );
		return pageResponse;
	}

	@Override
	public RangeResponse<MaterialInfo> findMaterialsByRange(Condition condition) throws Exception {
		log.debug("");
		MaterialSpec specs = new MaterialSpec();
		List<MaterialEntity> mList = null;
		Specification<MaterialEntity> specification = specs.createSpecification(condition);
		
		RangeResponse<MaterialInfo> rangeResponse = new RangeResponse<>();
		if(condition.getRangeInfo() == null) {
			mList = this.materialRepository.findAll(specification);
			rangeResponse.setTotalRowsCount(mList.size());
			rangeResponse.setRangeInfo(null);
		} else {
			RangeInfo rInfo=condition.getRangeInfo();
			Sort.Direction dir = 
					rInfo.getSortDirection() == SortDirection.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC;
			Rangeable rangeRequest = new Rangeable(rInfo.getStart(), rInfo.getLimit(), Sort.by(dir, rInfo.getSortBy()));
			mList = this.materialRangeableRepository.findAllByRange(specification, rangeRequest);
			rangeResponse.setTotalRowsCount(this.materialRepository.count(specification));
			rangeResponse.setRangeInfo(rInfo);
		}
		rangeResponse.setRows( this.toMaterialInfoList(mList) );
		return rangeResponse;
	}

	@Override
	public MaterialInfo findMaterialByCode(String code) throws Exception {
		log.debug("");
		Optional<MaterialEntity> optional=this.materialRepository.findById(code);
		if(optional.isEmpty()==false) {
			return optional.get().info();
		}
		return null;
	}

	@Override
	public Long getTotalCount() throws Exception {
		log.debug("");
		return materialRepository.count();
	}

	@Override
	public List<MaterialInfo> findByRange(int from, int to) throws Exception {
		log.debug("");
		MaterialSpec specs = new MaterialSpec();
		Specification<MaterialEntity> specification = specs.createSpecification(new Condition());
		Rangeable rangeRequest = new Rangeable(from, to-from+1, Sort.by(Sort.Direction.ASC, "code"));
		
		List<MaterialEntity> mList = null;
		mList=this.materialRangeableRepository.findAllByRange(specification, rangeRequest);
		return this.toMaterialInfoList(mList);
	}
}
