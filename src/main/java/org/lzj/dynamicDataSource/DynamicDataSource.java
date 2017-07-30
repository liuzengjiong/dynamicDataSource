package org.lzj.dynamicDataSource;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;


 
/**
 * 动态数据源.
 * @author Angel
 * @version v.0.1
 */
@Component
public class DynamicDataSource extends AbstractRoutingDataSource implements DataSourceChangeListener{
   
	
	
    @Override
    protected Object determineCurrentLookupKey() {
    	
       return DynamicDataSourceContextHolder.getDataSourceType();
    }
    
    public void setTargetDataSources() {  
        super.setTargetDataSources(DynamicDataSourceContextHolder.getDataSourceMap());  
        super.afterPropertiesSet();  
    }
    
    
    @PostConstruct  
    public void initMethod() throws Exception {  
        System.out.println("initMethod 被执行");  
        setTargetDataSources();
    }  

	public void dealChange(Map<Object, Object> newDataSources) {
		System.out.println("receive and deal msg");
	}  
}