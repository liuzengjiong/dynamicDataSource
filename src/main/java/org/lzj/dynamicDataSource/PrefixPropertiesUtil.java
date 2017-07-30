package org.lzj.dynamicDataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年7月30日
 */
public class PrefixPropertiesUtil {
	
	private final static String datasourceNames = "custom.datasource.names";
	private final static String datasourcePrefix = "custom.datasource";
	
	
	
	
	private static Properties getProperties() throws IOException{
		Resource resource = new ClassPathResource("/application.properties");
		Properties props = PropertiesLoaderUtils.loadProperties(resource);
		System.out.println(props.get("custom.datasource.names"));
		
		return props;
	}
	
	public static List<Map<Object,Object>> getDataSourcesMap() throws IOException{
		
		List<Map<Object,Object>> dsList = new ArrayList<Map<Object,Object>>();
		
		Properties props = getProperties();
		String dbNames[] = props.getProperty(datasourceNames).split(",");
		for(String dbName : dbNames){
			Map<Object,Object> dsConfigMap = getSubProperties(props, datasourcePrefix+"."+dbName);
			dsList.add(dsConfigMap);
		}
		return dsList;
	}
	
	public static Map<Object,Object> getSubProperties(Properties properties,String prefix){
		
		Map<Object,Object> result = new HashMap<>();
		
		for(Object key : properties.keySet()){
			String subKey = getSubKey(key.toString(), prefix);
			if(subKey!=null && !result.containsKey(subKey)){
				System.out.println(subKey+":"+properties.get(key));
				result.put(subKey, properties.get(key));
			}
		}
		
		return result;
	}
	
	private static String getSubKey(String key,String prefix){
		
		if(key.startsWith(prefix)){
			//有个.号，需要加1
			return key.substring(prefix.length() + 1);
		}
		return null;
	}
}
