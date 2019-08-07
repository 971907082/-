package org.wlxy.example.StaticData;

import org.wlxy.example.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserData {

    public static List<User> userList;

    static {
        userList = new ArrayList<User>();

        User user1 = new User();
        user1.setUserName("zhangsan");
        user1.setPassword("123");
        userList.add(user1);

        User user2 = new User();
        user2.setUserName("李四");
        user2.setPassword("456");
        userList.add(user2);

    }
}
