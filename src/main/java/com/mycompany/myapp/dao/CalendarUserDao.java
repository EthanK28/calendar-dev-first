package com.mycompany.myapp.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.mycompany.myapp.domain.CalendarUser;

public interface CalendarUserDao {
    CalendarUser getUser(int id) throws ClassNotFoundException, SQLException;

    CalendarUser findUserByEmail(String email) throws ClassNotFoundException, SQLException;

    List<CalendarUser> findUsersByEmail(String partialEmail);

    int createUser(CalendarUser user)  throws ClassNotFoundException, SQLException;
    
    //public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}

