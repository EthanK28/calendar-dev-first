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
    	
		//Class.forName("com.mysql.jdbc.Driver");
		//Connection c = DriverManager.getConnection("jdbc:mysql://localhost/calendar?characterEncoding=UTF-8", "spring", "book");
    	Connection c = dataSource.getConnection();

		PreparedStatement ps = c.prepareStatement("select * from calendar_users where id = ?");
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
    	return calendarUser;
    }

    @Override
    public CalendarUser findUserByEmail(String email) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/calendar", "spring", "book");

		PreparedStatement ps = c.prepareStatement("SELECT email, name from calendar_users values(?)");
		ps.setString(1, email);		
		ps.executeUpdate();				
		ps.close();
		c.close();
		
		return null;
    	
    }

    @Override
    public List<CalendarUser> findUsersByEmail(String email) {
    	// SQL like 문 활용
    	return null;
    }

    @Override
    public int createUser(final CalendarUser userToAdd) {
    	CalendarUser calenderUser = new CalendarUser();
    	
    	
        return 0;
    }
}