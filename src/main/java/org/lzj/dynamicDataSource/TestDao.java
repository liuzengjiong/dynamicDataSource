package org.lzj.dynamicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年7月30日
 */
@Repository
public class TestDao implements ITestDao{
	
	@Autowired
	private DynamicDataSource dataSource;
	
	public int testAdd(int i) throws SQLException{
		 String sql2 = "INSERT INTO `t_order_item` (`item_id`, `order_id`, `user_id`) VALUES (?, ?, ?)";
		 Connection conn = dataSource.getConnection();  
           	PreparedStatement pt = conn.prepareStatement(sql2);
           	pt.setInt(1, i);
           	pt.setInt(2, i);
           	pt.setInt(3, i);
          int result = pt.executeUpdate();
		return result;
	}
}
