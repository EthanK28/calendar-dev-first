package com.mycompany.myapp.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;


import com.mycompany.myapp.domain.CalendarUser;

public class DaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ApplicationContext context = new GenericXmlApplicationContext("com/mycompany/myapp/applicationContext.xml");
		
		CalendarUserDao calendarUserDao = context.getBean("JdbcCalendarUserDao", CalendarUserDao.class); 
		//UserDao dao = context.getBean("userDao", UserDao.class);
		//EventDao eventDao = JdbcEventDao();
		
		//1. 디폴트로 등록된 CalendarUser 3명 출력 (패스워드 제외한 모든 내용 출력)				
		for (int i = 1; i <=3; i++)
		{
			System.out.format("ID:%d Name: %s Email: %s \n",calendarUserDao.getUser(i).getId(), calendarUserDao.getUser(i).getName(), calendarUserDao.getUser(i).getEmail());
		}
		System.out.println();
		
		//2. 디폴트로 등록된 Event 3개 출력 (owner와 attendee는 해당 사용자의 이메일과 이름을 출력)
		EventDao eventDao = context.getBean("JdbcEventDao", JdbcEventDao.class);
		for (int i = 100; i<=102; i++)
		{
			System.out.format("ID:%d Summary:%s Description:%s \nOwner(Email:%s Name:%s) Attendee(Email:%s Name:%s)\n",eventDao.getEvent(i).getId(), eventDao.getEvent(i).getSummary(),
					eventDao.getEvent(i).getDescription(), eventDao.getEvent(i).getOwner().getEmail(), eventDao.getEvent(i).getOwner().getName(),
					eventDao.getEvent(i).getAttendee().getEmail(), eventDao.getEvent(i).getAttendee().getName());
			System.out.println();
		}
		//System.out.format("ID:%d Description: %s ",eventDao.getEvent(100).getId(), eventDao.getEvent(100).getSummary());
		//System.out.println(eventDao.getEvent(100).getId());
		
		//3. 새로운 CalendarUser 2명 등록 및 각각 id 추출
		CalendarUser newUser1 = new CalendarUser();
		//newUser1.setId(4);
		newUser1.setName("테스트 뉴유저1");
		newUser1.setEmail("TestNewUser1@newuser.com");
		newUser1.setPassword("testnewuser");
		calendarUserDao.createUser(newUser1);
		System.out.format("ID:%d Name: %s Email: %s \n",calendarUserDao.findUserByEmail(newUser1.getEmail()).getId(), calendarUserDao.getUser(4).getName(), calendarUserDao.getUser(4).getEmail());
		System.out.println("테스트 뉴유저1 입력 성공");
		
		CalendarUser newUser2 = new CalendarUser();
		//newUser2.setId(5);
		newUser2.setName("테스트 뉴유저2");
		newUser2.setEmail("TestNewUser2@newuser.com");
		newUser2.setPassword("testnewuser2");
		calendarUserDao.createUser(newUser2);
		System.out.format("ID:%d Name: %s Email: %s \n",calendarUserDao.getUser(5).getId(), calendarUserDao.getUser(5).getName(), calendarUserDao.getUser(5).getEmail());
		System.out.println();
		
		
		
		//4. 추출된 id와 함께 새로운 CalendarUser 2명을 DB에서 가져와 (getUser 메소드 사용) 방금 등록된 2명의 사용자와 내용 (이메일, 이름, 패스워드)이 일치하는 지 비교
		if (!calendarUserDao.getUser(4).getName().equals(newUser1.getName())){
			System.out.println("새로운 유저1 테스트 실패 (name)");
		} 
		else if(!calendarUserDao.getUser(4).getEmail().equals(newUser1.getEmail())){
			System.out.println("새로운 유저1 테스트 실패 (Email)");
		} else if(!calendarUserDao.getUser(4).getPassword().equals(newUser1.getPassword())){
			System.out.println("새로운 유저1 테스트 실패 (Password)");
		} else {
			System.out.println("새로운 유저1 조회 테스트 성공");
		}
		
		
		
		if (!calendarUserDao.getUser(5).getName().equals(newUser2.getName())){
			System.out.println("새로운 유저2 테스트 실패 (name)");
		} 
		else if(!calendarUserDao.getUser(4).getEmail().equals(newUser2.getEmail())){
			System.out.println("새로운 유저2 테스트 실패 (Email)");
		} else if(!calendarUserDao.getUser(4).getPassword().equals(newUser2.getPassword())){
			System.out.println("새로운 유저2 테스트 실패 (Password)");
		} else {
			System.out.println("새로운 유저2 조회 테스트 성공");
		}	
		
		
		
		//5. 5명의 CalendarUser 모두 출력 (패스워드 제외한 모든 내용 출력)
		for (int i = 1; i <=5; i++)
		{
			System.out.format("ID:%d Name: %s Email: %s \n",calendarUserDao.getUser(i).getId(), calendarUserDao.getUser(i).getName(), calendarUserDao.getUser(i).getEmail());
		}				
						
		
		//6. 새로운 Event 2개 등록 및 각각 id 추출
		
		//7. 추출된 id와 함께 새로운 Event 2개를 DB에서 가져와 (getEvent 메소드 사용) 방금 추가한 2개의 이벤트와 내용 (when, summary, description, owner, attendee)이 일치하는 지 비교
		//8. 5개의 Event 모두 출력 (owner와 attendee는 해당 사용자의 이메일과 이름을 출력)
	}
}
