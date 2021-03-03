package com.shireesha.casestudy.utilities;

public class DBQueries {
   // public static final String PHOTORANGEQUERY = "select a from Photo a where a.takenOn <= :takenOn";
    
    //public static final String FINDBYNAMECONTAINING="SELECT g FROM galleries g WHERE g.name LIKE %:name%";
    
    public static final String FINDEMAIL="SELECT u FROM User u WHERE u.email = ?1";
}
