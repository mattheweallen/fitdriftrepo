package com.fitdrift.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.fitdrift.domain.activity.Activity;
import com.fitdrift.domain.user.User;
//import com.fitdrift.domain.user.UserDetail;
import com.fitdrift.domain.user.UserRole;
import com.fitdrift.domain.user.UserSignIn;
import com.fitdrift.model.Dao;
import com.fitdrift.util.model.EntityManagerUtil;

public class UserDao implements Dao<User>, Serializable {
	private static final long serialVersionUID = -5745737809950101174L;
	
	
	// this is for development, remove in production
	public void initializeDB() {
		
		EntityManager em = EntityManagerUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		User object0 = new User();
		User object1 = new User();

		UserRole admin = new UserRole();
		admin.setAuthority("ROLE_ADMIN");
		
		UserRole user = new UserRole();
		user.setAuthority("ROLE_USER");
		
		object0.setEnabled(1);
		object0.setUsername("admin");
		object0.setPassword("password");
		
		admin.setUser(object0);
		user.setUser(object0);
		Set<UserRole> roles0 = new HashSet<UserRole>();
		roles0.add(admin);
		roles0.add(user);
		object0.setUserroles(roles0);
		
		object1.setEnabled(1);
		object1.setUsername("cs402@uwlax.edu");
		object1.setPassword("wildflowers");
		
		UserRole user1 = new UserRole();
		user1.setUser(object1);
		Set<UserRole> roles1 = new HashSet<UserRole>();
		user1.setAuthority("ROLE_USER");
		roles1.add(user1);
		//roles0.add(user);
		object1.setUserroles(roles1);

		// IDs start as null
		// assertEquals((Long) null, object0.getUser_id());
		// assertEquals((Long) null, object1.getUser_id());

		em.persist(object0);
		em.persist(object1);
		em.persist(admin);
		em.persist(user);
		em.persist(user1);
		

		transaction.commit();
		
		
		
		//em.persist(admin);
		//em.persist(user);
		
		//user.setId(object1.getId());
		//em.persist(user);

//		System.out.println("Object 0");
//		System.out.println("Generated ID is: " + object0.getUser_id());
//		
//		System.out.println("Object 1");
//		System.out.println("Generated ID is: " + object1.getUser_id());

		em.close();
	}

	//Warning Not sure if this method is used or it correct
	public void persistUserAndRoles(User user, List<UserRole> roles) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		
		em.persist(user);
		
		//maybe persist user, then set userRole.user to new user meallen 20131203
		for(UserRole r : roles) {
			em.persist(r);
		}
		
		em.getTransaction().commit();
		em.close();
	}
	
	public static User findUserByUsername(String username) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		TypedQuery<User> query =
	      em.createQuery("SELECT u FROM User u where u.username='"+username+"'", User.class);
		List<User> users = query.getResultList();
		User u = null;
		if(users != null && !users.isEmpty()) {
			u = users.get(0);
		}
		
		em.close();
		return u;
	}

	@Override
	public void remove(User entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		//EntityTransaction transaction = em.getTransaction();
		//transaction.begin();
		//SELECT c.currency FROM Country AS c WHERE c.name LIKE 'I%'
//		TypedQuery<UserRole> query =
//			      em.createQuery("SELECT c FROM Country c", UserRole.class);
//		List<UserRole> userRoles = query.getResultList();
		
		
		User u = em.find(User.class, id);
		
		em.close();
		return u;
	}

	@Override
	public void persist(User entity) {
		// TODO Auto-generated method stub
		
	}
	
//	public static void updateTheme(String theme, Long user_id) {
//		EntityManager em = EntityManagerUtil.getEntityManagerFactory()
//				.createEntityManager();
//		User u = em.find(User.class, user_id);
//		em.getTransaction().begin();
//		if(u.getUserDetail() == null) {
//			UserDetail ud = new UserDetail();
//			ud.setTheme(theme);
//			ud.setUser(u);
//			em.persist(ud);
//			u.setUserDetail(ud);
//			em.merge(u);
//		} else {
//			UserDetail ud = em.find(UserDetail.class, u.getUserDetail().getUserdetail_id());
//			ud.setTheme(theme);
//			em.merge(ud);
//			u.setUserDetail(ud);
//			em.merge(u);
//		}
//		em.getTransaction().commit();
//		em.close();
//	}
	
	public static void updateUserTheme(Long user_id, String theme) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		User u = em.find(User.class, user_id);
		u.setTheme(theme);
		em.merge(u);
		em.getTransaction().commit();
		em.close();
	}
	
	/**
	 * Counts all UserSignIn.
	 * 
	 * @return
	 */
	public static Long findUserSignInCount() {
		EntityManager em = EntityManagerUtil.getEntityManager();

		Query query = em.createQuery("SELECT count(u) FROM UserSignIn u");
		Long count = (Long) query.getSingleResult();

		em.close();
		return count;
	}

	/**
	 * find subset of UserSignIn records.
	 * 
	 * @param start
	 * @param max
	 * @return
	 */
	public static List<UserSignIn> findUserSignInPaginated(int start, int max) {
		EntityManager em = EntityManagerUtil.getEntityManager();

		TypedQuery<UserSignIn> query = em
				.createQuery("SELECT u FROM UserSignIn u order by u.date desc",
						UserSignIn.class);
		query.setFirstResult(start);
		query.setMaxResults(max);

		List<UserSignIn> userSignIn = query.getResultList();

		em.close();
		return userSignIn;
	}
}
