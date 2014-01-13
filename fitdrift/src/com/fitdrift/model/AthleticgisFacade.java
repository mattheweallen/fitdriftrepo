package com.fitdrift.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.fitdrift.domain.activity.Activity;
import com.fitdrift.domain.activity.ActivityPoint;
import com.fitdrift.domain.activity.ActivitySubType;
import com.fitdrift.domain.activity.ActivityType;
import com.fitdrift.domain.activity.Equipment;
import com.fitdrift.domain.activity.Measure;
import com.fitdrift.domain.activity.MyMap;
import com.fitdrift.domain.activity.MyMapMarker;
import com.fitdrift.domain.user.User;
import com.fitdrift.model.activity.ActivityDao;
import com.fitdrift.model.user.UserDao;
import com.fitdrift.util.model.EntityManagerUtil;

/**
 * @author Matthew Allen
 * @version 20131206
 * Facade for Athletic GIS data access.
 * The facade is a pattern that is intended to abstact the underlying DAOs.
 * It also allows just one reference in classes using it versus multiple DAO references.
 * 
 */
public class AthleticgisFacade implements Serializable {
	private static final long serialVersionUID = -6486526059229360289L;
	//private UserDao userDao = new UserDao();
	//private ActivityDao activityDao = new ActivityDao();
	
	public void persistUser(User u) {
		UserDao userDao = new UserDao();
		userDao.persist(u);
	}
	
	public static void persistActivity(Activity a) {
		ActivityDao.persist(a);
	}
	
	public static void removeActivity(Long activityId) {
		ActivityDao.remove(activityId);
	}
	
	public String testFromServlet() {
		return "Hello Matthew!";
	}
	
	//remove
	public User findUserById(Long id) {
		UserDao userDao = new UserDao();
		return userDao.findById(id);
	}
	
	public static void persistActivityAndActivityPoints(Activity a, List<ActivityPoint> activityPoints) {
		ActivityDao.persistActivityAndActivityPoints(a, activityPoints);
	}
	
	public static List<ActivityPoint> findActivityPointsByActivityId(Long activityId) {
		return ActivityDao.findActivityPointsByActivityId(activityId);
	}
	
	public static List<Activity> findActivitiesByUserId(Long user_id) {
		return ActivityDao.findActivitiesByUserId(user_id);
	}
	
	public static User findUserByUsername(String username) {
		return UserDao.findUserByUsername(username);
	}
	
	//may remove if not being used
//	public static List<Activity> findAllActivities() {
//		return ActivityDao.findAllActivities();
//	}
	
	public static void mergeActivity(String activityName, Long activityId) {
		ActivityDao.mergeActivity(activityName, activityId);
	}
	
	public static Activity findActivityById(Long id) {
		return ActivityDao.findById(id);
	}
	
	public static Long findActivityCountByUserId(Long user_id) {
		return ActivityDao.findActivityCountByUserId(user_id);
	}
	
	public static List<Activity> findActivitiesByUserIdPaginated(Long user_id, int start, int max) {
		return ActivityDao.findActivitiesByUserIdPaginated(user_id, start, max);
	}
	
//	public static void updateTheme(String theme, Long user_id) {
//		UserDao.updateTheme(theme, user_id);
//	}
	
	public static void updateUserTheme(Long user_id, String theme) {
		UserDao.updateUserTheme(user_id, theme);
	}
	
	/**
	 * This method persists a user created myMap along with its markers.
	 * 
	 * @param myMap
	 * @param myMapMarkers
	 */
	public static void persistMapAndMyMapMarkers(MyMap myMap, List<MyMapMarker> myMapMarkers) {
		ActivityDao.persistMapAndMyMapMarkers(myMap, myMapMarkers);
	}
	
	/**
	 * Count the number of maps created by user_id.
	 * 
	 * @param user_id
	 * @return
	 */
	public static Long findMyMapCountByUserId(Long user_id) {
		return ActivityDao.findMyMapCountByUserId(user_id);
	}
	
	/**
	 * This method selects a subset of all maps for a given user.
	 * 
	 * @param user_id
	 * @param start
	 * @param max
	 * @return
	 */
	public static List<MyMap> findMyMapsByUserIdPaginated(Long user_id, int start, int max) {
		return ActivityDao.findMyMapsByUserIdPaginated(user_id, start, max);
	}
	
	/**
	 * removes a map and its associated markers.
	 * 
	 * @param mymap_id
	 */
	public static void removeMyMap(Long mymap_id) {
		ActivityDao.removeMyMap(mymap_id);
	}
	
	/**
	 * get MyMap entity for given id.
	 * 
	 * @param id
	 * @return
	 */
	public static MyMap findMyMapById(Long id) {
		return ActivityDao.findMyMapById(id);
	}
	
	/**
	 * update myMap name.
	 * 
	 * @param myMapName
	 * @param mymap_id
	 */
	public static void mergeMyMap(String myMapName, Long mymap_id) {
		ActivityDao.mergeMyMap(myMapName, mymap_id);
	}
	
	/**
	 * finds MapMarkers for a given map id
	 * 
	 * @param mymap_id
	 * @return
	 */
	public static List<MyMapMarker> findMyMapMarkersByMymap_id(Long mymap_id) {
		return ActivityDao.findMyMapMarkersByMymap_id(mymap_id);
	}
	
	/**
	 * Counts all activities for admin user.
	 * 
	 * @return
	 */
	public static Long findActivityCount() {
		return ActivityDao.findActivityCount();
	}
	
	/**
	 * find all activity records for admin user.
	 * 
	 * @param start
	 * @param max
	 * @return
	 */
	public static List<Activity> findActivitiesPaginated(int start, int max) {
		return ActivityDao.findActivitiesPaginated(start, max);
	}
	
	/**
	 * Count the total number of user defined maps.
	 * 
	 * @return
	 */
	public static Long findMyMapCount() {
		return ActivityDao.findMyMapCount();
	}
	
	/**
	 * This method selects a subset of all maps for a given user.
	 * 
	 * @param start
	 * @param max
	 * @return
	 */
	public static List<MyMap> findMyMapsPaginated(int start, int max) {
		return ActivityDao.findMyMapsPaginated(start, max);
	}
	
	/**
	 * 
	 * @return
	 */
	public static List<ActivityType> findAllActivityType() {
		return ActivityDao.findAllActivityType();
	}
	
	/**
	 * 
	 * @param activitytype_id
	 * @return
	 */
	public static List<ActivitySubType> findAllActivitySubTypeByActivitytype_id(Long activitytype_id) {
		return ActivityDao.findAllActivitySubTypeByActivitytype_id(activitytype_id);
	}
	
	/**
	 * 
	 * @param activitytype_id
	 * @return
	 */
	public static List<Equipment> findAllEquipmentByActivitytype_idAndUserId(Long activitytype_id, Long userId) {
		return ActivityDao.findAllEquipmentByActivitytype_idAndUserId(activitytype_id, userId);
	}
	
	/**
	 * 
	 * @param entity
	 */
	public static void persist(Object entity) {
		ActivityDao.persist(entity);
	}
	
	/**
	 * 
	 * @param user_id
	 * @param start
	 * @param max
	 * @return
	 */
	public static List<Measure> findMeasuresByUserIdPaginated(Long user_id,
			int start, int max) {
		return ActivityDao.findMeasuresByUserIdPaginated(user_id, start, max);
	}
	
	/**
	 * @param user_id
	 * @return
	 */
	public static Long findMeasureCountByUserId(Long user_id) {
		return ActivityDao.findMeasureCountByUserId(user_id);
		
	}
	
	/**
	 * @param user_id
	 * @return
	 */
	public static Long findAllMeasureCount() {
		return ActivityDao.findAllMeasureCount();
	}

	/**
	 * 
	 * @param start
	 * @param max
	 * @return
	 */
	public static List<Measure> findAllMeasuresPaginated(int start, int max) {
		return ActivityDao.findAllMeasuresPaginated(start, max);
	}
}
