package org.lzj.dynamicDataSource;

import java.util.Map;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年7月30日
 */
public interface DataSourceChangeListener {
	
	void dealChange(Map<Object,Object> newDataSources);
	
}
