package org.lzj.dynamicDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * 动态数据源上下文.
 *
 * @author Angel
 * @version v.0.1
 */
public class DynamicDataSourceContextHolder {
   
    /*
     * 当使用ThreadLocal维护变量时，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
     * 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    /*
     * 管理所有的数据源id;
     * 主要是为了判断数据源是否存在;
     */
    public static List<String> dataSourceIds = new ArrayList<String>();
    
    private static Map<Object,Object> dataSourceMap = new HashMap<>();
 
    /**
     * 使用setDataSourceType设置当前的
     * @param dataSourceType
     */
    public static void setDataSourceType(String dataSourceType) {
       contextHolder.set(dataSourceType);
    }
 
    public static String getDataSourceType() {
       return contextHolder.get();
    }
 
    public static void clearDataSourceType() {
       contextHolder.remove();
    }
   
    /**
     * 判断指定DataSrouce当前是否存在
     *
     * @param dataSourceId
     * @return
     * @author SHANHY
     * @create  2016年1月24日
     */
    public static boolean containsDataSource(String dataSourceId){
        return dataSourceIds.contains(dataSourceId);
    }
    
    
    public static void setDataSourceMap(Map<Object,Object> dsMap){
    	dataSourceMap = dsMap;
    }
    
    public static Map<Object,Object> getDataSourceMap(){
    	return dataSourceMap;
    }
    
    public static List<String> getDataSourceList(){
    	return dataSourceIds;
    }
}