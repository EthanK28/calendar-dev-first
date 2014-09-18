package com.mycompany.myapp.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.mycompany.myapp.dao.CalendarUserDao;
import com.mycompany.myapp.domain.CalendarUser;

import java.sql.*;

//import com.mysql.*;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.SimpleDriverDataSource;


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
    	Connection c = dataSource.getConnection();	

		PreparedStatement ps = c.prepareStatement("SELECT * from calendar_users where email = ?");
		ps.setString(1, "email");
		
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
    public List<CalendarUser> findUsersByEmail(String email) throws ClassNotFoundException, SQLException {
    	// SQL like 문 활용
    	Connection c = dataSource.getConnection();
    	PreparedStatement ps = c.prepareStatement("SELECT * from calendar_users where email like '%?%'");
    	ps.setString(1, "email");
    	
    	ResultSet rs = ps.executeQuery();
    	rs.next();
    	List<CalendarUser> Array = new ArrayList<CalendarUser>();
    	CalendarUser calendarUser = new CalendarUser();
    	while(rs.next()){    		
    		calendarUser.setId(rs.getInt("id"));
    		calendarUser.setName(rs.getString("name"));
    		calendarUser.setEmail(rs.getString("email"));
    		calendarUser.setPassword(rs.getString("password"));
    		Array.add(calendarUser);
    	}   	
    	
    	
    	return Array;
    }

    @Override
    public int createUser(final CalendarUser userToAdd) throws ClassNotFoundException, SQLException {
    	//CalendarUser calenderUser = new CalendarUser();
    	Connection c = dataSource.getConnection();
		//PreparedStatement ps = c.prepareStatement("insert into calendar_users(id, email, name, password) values(?,?,?,?)");
    	PreparedStatement ps = c.prepareStatement("insert into calendar_users(email, name, password) values(?,?,?)");
		//ps.setInt(1, userToAdd.getId());
		ps.setString(1, userToAdd.getEmail());
		ps.setString(2, userToAdd.getName());
		ps.setString(3, userToAdd.getPassword());
		ps.executeUpdate();

		ps.close();
		c.close();	
    	
    	
        return 0;
    }
}