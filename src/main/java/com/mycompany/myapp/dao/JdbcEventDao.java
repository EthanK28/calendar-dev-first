package com.mycompany.myapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Calendar;

import javax.sql.DataSource;

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
		
		Event event = new Event();
		
		PreparedStatement ps2 = c.prepareStatement("select * from calendar_users where id = ?");
		ps2.setInt(1, rs.getInt("owner"));
		ResultSet rs2 = ps2.executeQuery();
		rs2.next();
		CalendarUser calendarUser = new CalendarUser();
		calendarUser.setId(rs2.getInt("id"));
		calendarUser.setName(rs2.getString("name"));
		calendarUser.setEmail(rs2.getString("email"));
		calendarUser.setPassword(rs2.getString("password"));
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
		event.setAttendee(calendarUser);
		
		event.setId(rs.getInt("id"));	
		event.setSummary(rs.getString("summary"));
		event.setDescription(rs.getString("description"));
		
		//event.setWhen(Calendar.getInstance(rs.getTimestamp("when")));
		
		//Cal	endarUser calendarUser = new CalendarUser();
		
		
		rs.close();
		ps.close();
		c.close();		
    	return event;
        //return null;
    }

    @Override
    public int createEvent(final Event event) {
        return 0;
    }

    @Override
    public List<Event> findForUser(int userId) {
        return null;
    }

    @Override
    public List<Event> getEvents() {
        return null;
    }

    /*
    private static final String EVENT_QUERY = "select e.id, e.summary, e.description, e.when, " +
            "owner.id as owner_id, owner.email as owner_email, owner.password as owner_password, owner.name as owner_name, " +
            "attendee.id as attendee_id, attendee.email as attendee_email, attendee.password as attendee_password, attendee.name as attendee_name " +
            "from events as e, calendar_users as owner, calendar_users as attendee " +
            "where e.owner = owner.id and e.attendee = attendee.id";
     */
}
