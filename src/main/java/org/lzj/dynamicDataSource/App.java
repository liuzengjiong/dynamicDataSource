package org.lzj.dynamicDataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Hello world!
 *
 */
@SpringBootApplication
//注册动态多数据源
@Import({DynamicDataSourceRegister.class})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class App 
{
	public static void main(String[] args) {
	       SpringApplication.run(App.class, args);
	    }
}
