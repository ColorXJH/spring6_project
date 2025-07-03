package com.colorxjh;/**
 * @ClassName: TestUser
 * @Package: com.colorxjh
 * @Description:
 * @Datetime: 2025/7/3 21:32
 * @author: ColorXJH
 */

import com.colorxjh.spring6.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @description:
 * @author ColorXJH
 * @date 2025/7/3 21:32
 * @version 1.0
 */
public class TestUser {
    @Test
    public void test1() {
        System.out.println("TestUser.test1......");
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        User myUser = (User) context.getBean("myUser");
        myUser.add();
        User myUser2 = (User) context.getBean("myUser");
        System.out.println("myUser2==myUser......?:");
        System.out.println(myUser2==myUser);
        User myUser3 = new User();
        System.out.println("myUser2==myUser3......?:");
        System.out.println(myUser2==myUser3);
    }
}
