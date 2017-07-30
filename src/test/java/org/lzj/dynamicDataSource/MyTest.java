package org.lzj.dynamicDataSource;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年7月30日
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class MyTest {
	
	@Autowired
	private ITestService service;
	@Test
	public void testDataSource() throws SQLException{
		
		System.out.println(DynamicDataSourceContextHolder.containsDataSource("db1"));
		System.out.println(DynamicDataSourceContextHolder.getDataSourceList().toString());
		
		DynamicDataSourceContextHolder.setDataSourceType("db1");
//		for(int i = 0;i<5;i++){
//        	int value = i+25;
//        	int selector = value % DynamicDataSourceContextHolder.getDataSourceMap().size();
//        	DynamicDataSourceContextHolder.setDataSourceType("db" + (selector+1));
//        	System.out.println(service.testAdd(value));
//        }
	}
}
