package ru.vagapov.userapi;

import ru.vagapov.userapi.dao.Impl.UserDaoJDBCImpl;
import ru.vagapov.userapi.dao.UserDao;
import ru.vagapov.userapi.entity.UserEntity;
import ru.vagapov.userapi.service.UserService;
import ru.vagapov.userapi.service.impl.UserServiceImpl;
import java.time.LocalDate;





public class UserApiApplication {

    public static void main(String[] args) {
        UserDao userDao = new UserDaoJDBCImpl();
        UserService userService = new UserServiceImpl(userDao);
        userService.createUsersTable();
        userService.saveUser("Dayan",
                "Isyangulov",
                LocalDate.of(1995, 9, 10),
                "Uchaly",
                (byte) 28);
        UserEntity user = userService.getAllUsers().get(0);
        System.out.println("User с именем " + user.getFirstName() + " добавлен в таблицу");
        userService.saveUser("Ilgam",
                "Isyangulov",
                LocalDate.of(1995, 9, 10),
                "Uchaly",
                (byte) 28);
        user = userService.getAllUsers().get(1);
        System.out.println("User с именем " + user.getFirstName() + " добавлен в таблицу");
        userService.saveUser("Vadim",
                "Vagapov",
                LocalDate.of(1996, 3, 19),
                "Isyan",
                (byte) 28);
        user = userService.getAllUsers().get(2);
        System.out.println("User с именем " + user.getFirstName() + " добавлен в таблицу");
        userService.saveUser("Ильнур",
                "Хасанов",
                LocalDate.of(1995, 2, 16),
                "Исян",
                (byte) 29);
        user = userService.getAllUsers().get(3);
        System.out.println("User с именем " + user.getFirstName() + " добавлен в таблицу");
        for (UserEntity u : userService.getAllUsers()) {
            System.out.println(u);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

}
