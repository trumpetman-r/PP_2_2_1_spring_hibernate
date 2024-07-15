package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   // Константы для SQL-запросов
   private static final String HQL_SELECT_ALL_USERS = "FROM User";
   private static final String HQL_FIND_USERS_BY_CAR_MODEL_AND_SERIES = "FROM User u WHERE u.car.model = :model AND u.car.series = :series";

   @Override
   public void addUser(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> getUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(HQL_SELECT_ALL_USERS);
      return query.getResultList();
   }

   @Override
   public List<User> findUsersByCarModelAndSeries(String model, int series) {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(HQL_FIND_USERS_BY_CAR_MODEL_AND_SERIES, User.class);
      query.setParameter("model", model);
      query.setParameter("series", series);
      return query.getResultList();
   }
}