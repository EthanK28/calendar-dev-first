package com.mycompany.myapp.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.mycompany.myapp.dao.CalendarUserDao;
import com.mycompany.myapp.domain.CalendarUser;

import java.sql.*;
import com.mysql.*;


@Repository
public class JdbcCalendarUserDao implements CalendarUserDao {

	private DataSource dataSource;

    // --- constructors ---
    public JdbcCalendarUserDao()   {    	
    	
    }
    	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}

    // --- CalendarUserDao methods ---
    @Override
    public CalendarUser getUser(int id) throws ClassNotFoundException, SQLException   {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/springbook?characterEncoding=utf8", "spring", "book");

		PreparedStatement ps = c.prepareStatement("select email, name from calendar_users values(?)");
		ps.setInt(1, id);
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		CalendarUser calendarUser = new CalendarUser();
		calendarUser.setId(rs.getInt("id"));
		calendarUser.setName(rs.getString("name"));
		calendarUser.setEmail(rs.getString("email"));
		calendarUser.setPassword(rs.getString("password"));
		
		rs.close();
		ps.close();
		c.close();		
    	return null;
    }

    @Override
    public CalendarUser findUserByEmail(String email) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/springbook?characterEncoding=utf8", "spring", "book");

		PreparedStatement ps = c.prepareStatement("iselect email, name from calendar_users values(?)");
		ps.setString(1, email);		
		ps.executeUpdate();				
		ps.close();
		c.close();
		
		return null;
    	
    }

    @Override
    public List<CalendarUser> findUsersByEmail(String email) {
    	return null;
    }

    @Override
    public int createUser(final CalendarUser userToAdd) {
    	
        return 0;
    }
}