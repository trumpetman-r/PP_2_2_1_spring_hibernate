package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      Car car1 = new Car("Toyota", 123);
      Car car2 = new Car("Honda", 456);
      Car car3 = new Car("Lada", 789);

      User user1 = new User("John", "Doe", "john.doe@example.com", car1);
      User user2 = new User("Jane", "Doe", "jane.doe@example.com", car2);
      User user3 = new User("Antony", "Hopkins", "antony.hopkins@example.com", car3);

      userService.add(user1);
      userService.add(user2);
      userService.add(user3);

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println("Car Model = " + user.getCar().getModel());
         System.out.println("Car Series = " + user.getCar().getSeries());
         if (user.getCar() != null) {
            System.out.println("Car Model = " + user.getCar().getModel());
            System.out.println("Car Series = " + user.getCar().getSeries());
         } else {
            System.out.println("No car assigned");
         }
         System.out.println();
      }

      List<User> foundUsers = userService.findUsersByCarModelAndSeries("Toyota", 123);
      if (!foundUsers.isEmpty()) {
         for (User foundUser : foundUsers) {
            System.out.println("Found User: " + foundUser.getFirstName() + " " + foundUser.getLastName() + " with car model Toyota and series 123");
         }
      } else {
         System.out.println("No user found with car model Toyota and series 123");
      }

      context.close();
   }
}
