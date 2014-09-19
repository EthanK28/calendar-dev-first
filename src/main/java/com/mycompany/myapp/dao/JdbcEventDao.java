package com.mycompany.myapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

import javax.sql.DataSource;
import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

import com.mycompany.myapp.domain.CalendarUser;
import com.mycompany.myapp.domain.Event;
import com.mycompany.myapp.dao.JdbcCalendarUserDao;

@Repository
public class JdbcEventDao implements EventDao {
    private DataSource dataSource;

    // --- constructors ---
    public JdbcEventDao() {    	
		
    	
    }

	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}

    // --- EventService ---
    @Override
    public Event getEvent(int eventId) throws ClassNotFoundException, SQLException  {
    	Connection c = dataSource.getConnection();    	

		PreparedStatement ps = c.prepareStatement("select * from events where id = ?");		
		ps.setInt(1, eventId);
		//JdbcCalendarUserDao jdbcCalendarUserDao = new JdbcCalendarUserDao();
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		Calendar cal = Calendar.getInstance();
		cal.setTime(rs.getTimestamp("when"));		
		Event event = new Event();
		event.setId(rs.getInt("id"));
		event.setDescription(rs.getString("description"));
		event.setSummary(rs.getString("summary"));
		event.setWhen(cal);
	
		
		
		PreparedStatement ps2 = c.prepareStatement("select * from calendar_users where id = ?");
		ps2.setInt(1, rs.getInt("owner"));
		ResultSet rs2 = ps2.executeQuery();
		rs2.next();
		CalendarUser calendarUser = new CalendarUser();
		calendarUser.setId(rs2.getInt("id"));
		calendarUser.setName(rs2.getString("name"));
		calendarUser.setEmail(rs2.getString("email"));
		calendarUser.setPassword(rs2.getString("password"));
		//Ownner 가져오기
		event.setOwner(calendarUser);		
		
		PreparedStatement ps3 = c.prepareStatement("select * from calendar_users where id = ?");
		ps3.setInt(1, rs.getInt("attendee"));
		ResultSet rs3 = ps3.executeQuery();
		rs3.next();
		CalendarUser calendarUser2 = new CalendarUser();
		calendarUser2.setId(rs3.getInt("id"));
		calendarUser2.setName(rs3.getString("name"));
		calendarUser2.setEmail(rs3.getString("email"));
		calendarUser2.setPassword(rs3.getString("password"));
		event.setAttendee(calendarUser2);		
		
		rs.close();
		ps.close();
		c.close();		
    	return event;
      
    }

    @Override
    public int createEvent(final Event event) throws ClassNotFoundException, SQLException { 	
    	    	
    	
    	Connection c = dataSource.getConnection();
    	
    	PreparedStatement ps = c.prepareStatement("insert into events(summary, description, owner, attendee) values(?,?,?,?)");
	
    	ps.setString(1, event.getSummary());
    	ps.setString(2, event.getDescription());    	
    	ps.setInt(3, event.getOwner().getId());    	
    	ps.setInt(4, event.getAttendee().getId());
    	//System.out.println(ps.toString());
    	ps.executeUpdate();
    	
    	
    	ps.close();
    	c.close();   	
    	
        return 0;
    }

    @Override
    public List<Event> findForUser(int userId) throws ClassNotFoundException, SQLException {
    	Connection c = dataSource.getConnection();
    	PreparedStatement ps = c.prepareStatement("select * from events where owner = ? or attendee = ?");
    	ps.setInt(1, userId);
    	ps.setInt(2, userId);
    	ResultSet rs = ps.executeQuery();
    	List<Event> Array = new ArrayList<Event>();  
    	
    	while(rs.next()){
    		Array.add(getEvent(rs.getInt("id")));    		
    	}  	
    	
    	ps.close();
    	c.close();
        return Array;
    }

    @Override
    public List<Event> getEvents() throws ClassNotFoundException, SQLException {
    	
    	Connection c = dataSource.getConnection();
    	PreparedStatement ps = c.prepareStatement("select * from events");    	
    	ResultSet rs = ps.executeQuery();
    	
    	List<Event> Array = new ArrayList<Event>();    	
    	
    	while(rs.next()){
    		Array.add(getEvent(rs.getInt("id")));    
    	}    	
    	ps.close();
    	c.close();
        return Array;
    }

    /*
    private static final String EVENT_QUERY = "select e.id, e.summary, e.description, e.when, " +
            "owner.id as owner_id, owner.email as owner_email, owner.password as owner_password, owner.name as owner_name, " +
            "attendee.id as attendee_id, attendee.email as attendee_email, attendee.password as attendee_password, attendee.name as attendee_name " +
            "from events as e, calendar_users as owner, calendar_users as attendee " +
            "where e.owner = owner.id and e.attendee = attendee.id";
     */
}
