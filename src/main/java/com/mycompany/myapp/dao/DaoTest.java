package com.mycompany.myapp.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;







import com.mycompany.myapp.domain.CalendarUser;
import com.mycompany.myapp.domain.Event;

public class DaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ApplicationContext context = new GenericXmlApplicationContext("com/mycompany/myapp/applicationContext.xml");
		
		CalendarUserDao calendarUserDao = context.getBean("JdbcCalendarUserDao", CalendarUserDao.class); 
		//UserDao dao = context.getBean("userDao", UserDao.class);
		EventDao eventDao = context.getBean("JdbcEventDao", JdbcEventDao.class); 
		
		//1. 디폴트로 등록된 CalendarUser 3명 출력 (패스워드 제외한 모든 내용 출력)	
		
		for (int i = 1; i <=3; i++)
		{
			System.out.format("ID:%d Name: %s Email: %s \n",calendarUserDao.getUser(i).getId(), calendarUserDao.getUser(i).getName(), calendarUserDao.getUser(i).getEmail());
		}
		System.out.println();
		
		//2. 디폴트로 등록된 Event 3개 출력 (owner와 attendee는 해당 사용자의 이메일과 이름을 출력)
		
		for (int i = 100; i<=102; i++)
		{
			System.out.format("ID:%d Summary:%s Description:%s \nOwner(Email:%s Name:%s) Attendee(Email:%s Name:%s)\n",eventDao.getEvent(i).getId(), eventDao.getEvent(i).getSummary(),
					eventDao.getEvent(i).getDescription(), eventDao.getEvent(i).getOwner().getEmail(), eventDao.getEvent(i).getOwner().getName(),
					eventDao.getEvent(i).getAttendee().getEmail(), eventDao.getEvent(i).getAttendee().getName());
			System.out.println();
		}
	
		//3. 새로운 CalendarUser 2명 등록 및 각각 id 추출
		CalendarUser newUser1 = new CalendarUser();

		newUser1.setName("테스트 뉴유저1");
		newUser1.setEmail("TestNewUser1@newuser.com");
		newUser1.setPassword("testnewuser");
		calendarUserDao.createUser(newUser1);
		System.out.format("ID:%d Name: %s Email: %s \n",calendarUserDao.findUserByEmail(newUser1.getEmail()).getId(), 
				calendarUserDao.findUserByEmail(newUser1.getEmail()).getName(), calendarUserDao.findUserByEmail(newUser1.getEmail()).getEmail());
		
		
		CalendarUser newUser2 = new CalendarUser();

		newUser2.setName("테스트 뉴유저2");
		newUser2.setEmail("TestNewUser2@newuser.com");
		newUser2.setPassword("testnewuser2");
		calendarUserDao.createUser(newUser2);
		System.out.println(newUser1.getName());
		System.out.format("ID:%d Name: %s Email: %s \n",calendarUserDao.findUserByEmail(newUser2.getEmail()).getId(),
				calendarUserDao.findUserByEmail(newUser2.getEmail()).getName(), calendarUserDao.findUserByEmail(newUser2.getEmail()).getEmail());
		System.out.println();
		
		
		
		//4. 추출된 id와 함께 새로운 CalendarUser 2명을 DB에서 가져와 (getUser 메소드 사용) 방금 등록된 2명의 사용자와 내용 (이메일, 이름, 패스워드)이 일치하는 지 비교
		
		//equals 문을 통해 데이터 베이스에서 추출한 내용과 기존 입력을 위해 생성한 객체 사이에 값을 비교하였다. 
		if (!calendarUserDao.findUserByEmail(newUser1.getEmail()).getName().equals(newUser1.getName())){
			System.out.println("새로운 유저1 테스트 실패 (name)");
		} 
		else if(!calendarUserDao.findUserByEmail(newUser1.getEmail()).getEmail().equals(newUser1.getEmail())){
			System.out.println("새로운 유저1 테스트 실패 (Email)");
		} else if(!calendarUserDao.findUserByEmail(newUser1.getEmail()).getPassword().equals(newUser1.getPassword())){
			System.out.println("새로운 유저1 테스트 실패 (Password)");
		} else {
			System.out.println("새로운 유저1 조회 테스트 성공");
		}		
		
		if (!calendarUserDao.findUserByEmail(newUser2.getEmail()).getName().equals(newUser2.getName())){
			System.out.println("새로운 유저2 테스트 실패 (name)");
		} 
		else if(!calendarUserDao.findUserByEmail(newUser2.getEmail()).getEmail().equals(newUser2.getEmail())){
			System.out.println("새로운 유저2 테스트 실패 (Email)");
		} else if(!calendarUserDao.findUserByEmail(newUser2.getEmail()).getPassword().equals(newUser2.getPassword())){
			System.out.println("새로운 유저2 테스트 실패 (Password)");
		} else {
			System.out.println("새로운 유저2 조회 테스트 성공");
		}	
		
		System.out.println();		
		
		//5. 5명의 CalendarUser 모두 출력 (패스워드 제외한 모든 내용 출력)
		List<CalendarUser> calendarUserList = new ArrayList<CalendarUser>();
		calendarUserList = calendarUserDao.findUsersByEmail("@");
		
		
		for (int i = 0; i < calendarUserList.size(); i++)
		{
			System.out.format("ID:%d Name: %s Email: %s \n", calendarUserList.get(i).getId(), calendarUserList.get(i).getName(), calendarUserList.get(i).getEmail());
		}						
		System.out.println();
		//6. 새로운 Event 2개 등록 및 각각 id 추출
		
		
		Event event = new Event();
		
		Calendar cal = Calendar.getInstance();				
		event.setWhen(cal);
		event.setDescription("테스트 이벤트1 Description");
		event.setSummary("데스트 이벤트1 Summary");
		event.setOwner(calendarUserDao.getUser(3));
		event.setAttendee(calendarUserDao.getUser(1));
		eventDao.createEvent(event);		
		
		Event event2 = new Event();			
		event2.setWhen(cal);
		event2.setDescription("테스트 이벤트2 Description");
		event2.setSummary("데스트 이벤트2 Summary");
		event2.setOwner(calendarUserDao.getUser(1));
		event2.setAttendee(calendarUserDao.getUser(2));
		eventDao.createEvent(event2);	
		int eventID1 = 0;
		int eventID2 = 0;
		
		
		
		for (int i = 0; i<eventDao.getEvents().size(); i++){
			
			if(event.getDescription().equals(eventDao.getEvents().get(i).getDescription())){
				eventID1 = eventDao.getEvents().get(i).getId();
			}
			
			if(event2.getDescription().equals(eventDao.getEvents().get(i).getDescription())){
				eventID2 = eventDao.getEvents().get(i).getId();
			}					
		}
		System.out.format("새로입력한 Event Id 1 출력: %d\n", eventID1);
		System.out.format("새로입력한 Event Id 2 출력: %d\n", eventID2);	
		System.out.println();		
		


		
		//7. 추출된 id와 함께 새로운 Event 2개를 DB에서 가져와 (getEvent 메소드 사용) 방금 추가한 2개의 이벤트와 내용 (when, summary, description, owner, attendee)이 일치하는 지 비교
		
		//equals 문을 통해 데이터 베이스에서 추출한 내용과 기존 입력을 위해 생성한 객체 사이에 값을 비교하였다. 
		if (!eventDao.getEvent(eventID1).getAttendee().equals(event.getAttendee())){
			System.out.println("새로운 유저1 테스트 실패 (Attendee)"); 
		} else if(!eventDao.getEvent(eventID1).getSummary().equals(event.getSummary())){
			System.out.println("새로운 이벤트1 테스트 실패 (Summary)");
		} else if (!eventDao.getEvent(eventID1).getDescription().equals(event.getDescription())){
			System.out.println("새로운 유저1 테스트 실패 (Description)");
		} else if (!eventDao.getEvent(eventID1).getOwner().equals(event.getOwner())){
			System.out.println("새로운 유저1 테스트 실패 (Owner)");
		} else if (!eventDao.getEvent(eventID1).getWhen().equals(event.getWhen())){
			System.out.println("새로운 이벤트1 테스트 실패 (when)");
		} else {
			System.out.println("새로운 유저1 조회 테스트 성공");
		}
		
		if (!eventDao.getEvent(eventID2).getAttendee().equals(event2.getAttendee())){
			System.out.println("새로운 유저2 테스트 실패 (Attendee)");
		} else if(!eventDao.getEvent(eventID2).getSummary().equals(event2.getSummary())){
			System.out.println("새로운 이벤트2 테스트 실패 (Summary)");
		} else if (!eventDao.getEvent(eventID2).getDescription().equals(event2.getDescription())){
			System.out.println("새로운 유저2 테스트 실패 (Description)");
		} else if (!eventDao.getEvent(eventID2).getOwner().equals(event2.getOwner())){
			System.out.println("새로운 유저2 테스트 실패 (Owner)");
		} else if (!eventDao.getEvent(eventID2).getWhen().equals(event2.getWhen())){
			System.out.println("새로운 이벤트2 테스트 실패 (when)");
		} else {
			System.out.println("새로운 유저2 조회 테스트 성공");
		}
		System.out.println();
		
		//8. 5개의 Event 모두 출력 (owner와 attendee는 해당 사용자의 이메일과 이름을 출력)
		
		for (int i =0; i<eventDao.getEvents().size(); i++ ){
			System.out.format("ID: %d Owner(Email: %s, Name: %d) Attendee(Email: %s Name:%d \n", eventDao.getEvents().get(i).getId(), 
					eventDao.getEvents().get(i).getOwner().getEmail(),eventDao.getEvents().get(i).getOwner().getId(), 
					eventDao.getEvents().get(i).getAttendee().getEmail(),eventDao.getEvents().get(i).getAttendee().getId());			
			
		}
	}
}
