package org.lzj.dynamicDataSource;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年7月30日
 */
@Service
public class TestService implements ITestService{

	@Autowired
	private ITestDao dao;
	
	public int testAdd(int i) throws SQLException {
		
		return dao.testAdd(i);
	}

}
