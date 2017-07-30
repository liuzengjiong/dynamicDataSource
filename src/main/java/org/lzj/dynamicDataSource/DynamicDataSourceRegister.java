package org.lzj.dynamicDataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.test.autoconfigure.properties.AnnotationsPropertySource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.type.AnnotationMetadata;

import com.mchange.v2.c3p0.ComboPooledDataSource;
 
/**
 * 动态数据源注册;
 * @author Angel
 * @version v.0.1
 */
public class DynamicDataSourceRegister  implements ImportBeanDefinitionRegistrar, EnvironmentAware {
   
    private ConversionService conversionService = new DefaultConversionService();
    private PropertyValues dataSourcePropertyValues;
   
    private Map<String, DataSource> customDataSources = new HashMap<String, DataSource>();
    
    
    
    
    /**
     * 加载多数据源配置
     */
    public void setEnvironment(Environment environment) {
    	
    	try {
			PrefixPropertiesUtil.getDataSourcesMap();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
       System.out.println("DynamicDataSourceRegister.setEnvironment()");
        initCustomDataSources(environment);
    }
   
   
    /**
     * 初始化更多数据源
     *
     * @author SHANHY
     * @create 2016年1月24日
     */
    private void initCustomDataSources(Environment env) {
        // 读取配置文件获取数据源
        RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "custom.datasource.");
        String dsPrefixs = propertyResolver.getProperty("names");
        for (String dsPrefix : dsPrefixs.split(",")) {// 多个数据源
            Map<String, Object> dsMap = propertyResolver.getSubProperties(dsPrefix + ".");
            DataSource ds = buildDataSource(dsMap);
            customDataSources.put(dsPrefix, ds);
            dataBinder(ds, env);
        }
    }
   
    /**
     * 创建datasource.
     * @param dsMap
     * @return
     */
    public DataSource buildDataSource(Map<String, Object> dsMap) {
        Class<? extends DataSource> dataSourceType;
       
       dataSourceType = ComboPooledDataSource.class;
	   String driverClassName = dsMap.get("driverClassName").toString();
	   String url = dsMap.get("url").toString();
	   String username = dsMap.get("username").toString();
	   String password = dsMap.get("password").toString();
	   DataSourceBuilder factory =   DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username).password(password).type(dataSourceType);
	   return factory.build();
    }
   
    /**
     * 为DataSource绑定更多数据
     * @param dataSource
     * @param env
     */
    private void dataBinder(DataSource dataSource, Environment env){
       RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
       dataBinder.setConversionService(conversionService);
       dataBinder.setIgnoreNestedProperties(false);//false
        dataBinder.setIgnoreInvalidFields(false);//false
        dataBinder.setIgnoreUnknownFields(true);//true
       
        if(dataSourcePropertyValues == null){
            Map<String, Object> rpr = new RelaxedPropertyResolver(env, "spring.datasource").getSubProperties(".");
            Map<String, Object> values = new HashMap<>(rpr);
            // 排除已经设置的属性
            values.remove("type");
            values.remove("driverClassName");
            values.remove("url");
            values.remove("username");
            values.remove("password");
            dataSourcePropertyValues = new MutablePropertyValues(values);
        }
        dataBinder.bind(dataSourcePropertyValues);
       
    }
   
 
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
       Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        // 添加更多数据源
        targetDataSources.putAll(customDataSources);
        for (String key : customDataSources.keySet()) {
            DynamicDataSourceContextHolder.dataSourceIds.add(key);
        }
        DynamicDataSourceContextHolder.setDataSourceMap(targetDataSources);
        
    }
    
}
