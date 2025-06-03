package com.inzi.mes.be.example.async;

import java.util.List;

public interface StreamingExampleService {

	public List<MaterialInfo> searchByRange (int from, int to) ;
	
	public int getTotalCount();
}
