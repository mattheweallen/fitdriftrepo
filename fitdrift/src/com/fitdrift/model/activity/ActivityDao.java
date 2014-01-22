package com.fitdrift.model.activity;

import java.io.Serializable;
import java.sql.Timestamp;
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
import com.fitdrift.util.model.EntityManagerUtil;

/**
 * @author Matthew Allen
 * @version 20131206 This is a DAO related to Activities. It is intended as a
 *          class to do the data CRUD for data related to Activities. It lies
 *          behind the AthleticgisFacade.
 */
public class ActivityDao implements Serializable {
	private static final long serialVersionUID = 5546876539130483879L;

	// SELECT DISTINCT mag FROM Magazine mag JOIN mag.articles art JOIN
	// art.author auth WHERE auth.firstName = 'John'

	/**
	 * 
	 * @param activityId
	 * @return
	 */
	public static List<ActivityPoint> findActivityPointsByActivityId(
			Long activityId) {
		EntityManager em = EntityManagerUtil.getEntityManager();

		TypedQuery<ActivityPoint> query = em.createQuery(
				"SELECT ap FROM ActivityPoint ap where ap.activity.activity_id="
						+ activityId + " order by ap.id asc",
				ActivityPoint.class);
		List<ActivityPoint> activityPoints = query.getResultList();

		em.close();
		return activityPoints;
	}

	/**
	 * 
	 * 
	 * @param user_id
	 * @return
	 */
	public static List<Activity> findActivitiesByUserId(Long user_id) {
		EntityManager em = EntityManagerUtil.getEntityManager();

		TypedQuery<Activity> query = em.createQuery(
				"SELECT a FROM Activity a where a.user.user_id=" + user_id
						+ " order by a.date", Activity.class);
		List<Activity> activities = query.getResultList();

		em.close();
		return activities;
	}

	/**
	 * 
	 * @param entity
	 */
	public static void persist(Object entity) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * @param activityId
	 */
	public static void remove(Long activityId) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		Activity a = em.find(Activity.class, activityId);
		em.remove(a);
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * @param id
	 * @return
	 */
	public static Activity findById(Long id) {
		EntityManager em = EntityManagerUtil.getEntityManager();

		Activity a = em.find(Activity.class, id);

		em.close();
		return a;
	}

	/**
	 * @param a
	 * @param activityPoints
	 */
	public static void persistActivityAndActivityPoints(Activity a,
			List<ActivityPoint> activityPoints) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		if (activityPoints != null) {
			a.setActivitypoints(activityPoints);
		}

		// set the timestamp of the activity to the timestamp of the first
		// waypoint
		// TODO this is a little strange? maybe rethink
		if (activityPoints != null && !activityPoints.isEmpty()
				&& !a.getUseMyMap()) {
			a.setDate(activityPoints.get(0).getTime());
		}

		em.persist(a);
		if (activityPoints != null) {
			for (ActivityPoint ap : activityPoints) {
				ap.setActivity(a);
				em.persist(ap);
			}
		}

		em.getTransaction().commit();
		em.close();
	}

	// //TODO this is done below comment out and see if effects program
	// //use findActivitiesByUserIdPaginated as an example
	// //need to update this for pagination
	// //this is the method that should be being called for an admin
	// public static List<Activity> findAllActivities() {
	// EntityManager em = EntityManagerUtil.getEntityManager();
	// TypedQuery<Activity> query =
	// em.createQuery("SELECT a FROM Activity a order by a.date",
	// Activity.class);
	// List<Activity> activities = query.getResultList();
	// em.close();
	// return activities;
	// }

	/**
	 * 
	 * 
	 * @param activityName
	 * @param activityId
	 */
	public static void mergeActivity(String activityName, Long activityId) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		Activity a = em.find(Activity.class, activityId);
		a.setName(activityName);

		em.merge(a);
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * @param user_id
	 * @return
	 */
	public static Long findActivityCountByUserId(Long user_id) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query = em
				.createQuery("SELECT count(a) FROM Activity a where a.user.user_id="
						+ user_id);
		Long count = (Long) query.getSingleResult();
		em.close();
		return count;
	}

	/**
	 * 
	 * @param user_id
	 * @param start
	 * @param max
	 * @return
	 */
	public static List<Activity> findActivitiesByUserIdPaginated(Long user_id,
			int start, int max) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		TypedQuery<Activity> query = em.createQuery(
				"SELECT a FROM Activity a where a.user.user_id=" + user_id
						+ " order by a.date desc", Activity.class);
		query.setFirstResult(start);
		query.setMaxResults(max);
		List<Activity> activities = query.getResultList();
		em.close();
		return activities;
	}

	/**
	 * This method persists a user created myMap along with its markers.
	 * 
	 * @param myMap
	 * @param myMapMarkers
	 */
	public static void persistMapAndMyMapMarkers(MyMap myMap,
			List<MyMapMarker> myMapMarkers) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		myMap.setMyMapMarkers(myMapMarkers);

		// set myMap date to the first marker date.
		// TODO maybe rethink?
		if (myMapMarkers != null && !myMapMarkers.isEmpty()) {
			myMap.setDate(myMapMarkers.get(0).getTime());
		}

		em.persist(myMap);
		for (MyMapMarker mm : myMapMarkers) {
			mm.setMyMap(myMap);
			em.persist(mm);
		}

		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Count the number of maps created by user_id.
	 * 
	 * @param user_id
	 * @return
	 */
	public static Long findMyMapCountByUserId(Long user_id) {
		EntityManager em = EntityManagerUtil.getEntityManager();

		Query query = em
				.createQuery("SELECT count(mm) FROM MyMap mm where mm.user.user_id="
						+ user_id);
		Long count = (Long) query.getSingleResult();

		em.close();
		return count;
	}

	/**
	 * This method selects a subset of all maps for a given user.
	 * 
	 * @param user_id
	 * @param start
	 * @param max
	 * @return
	 */
	public static List<MyMap> findMyMapsByUserIdPaginated(Long user_id,
			int start, int max) {
		EntityManager em = EntityManagerUtil.getEntityManager();

		TypedQuery<MyMap> query = em.createQuery(
				"SELECT mm FROM MyMap mm where mm.user.user_id=" + user_id
						+ " order by mm.mymap_id desc", MyMap.class);
		query.setFirstResult(start);
		query.setMaxResults(max);
		List<MyMap> myMaps = query.getResultList();

		em.close();
		return myMaps;
	}

	/**
	 * removes a map and its associated markers.
	 * 
	 * @param mymap_id
	 */
	public static void removeMyMap(Long mymap_id) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		MyMap mm = em.find(MyMap.class, mymap_id);
		em.remove(mm);
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * get MyMap entity for given id.
	 * 
	 * @param id
	 * @return
	 */
	public static MyMap findMyMapById(Long id) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		MyMap mm = em.find(MyMap.class, id);
		em.close();
		return mm;
	}

	/**
	 * update myMap name.
	 * 
	 * @param myMapName
	 * @param mymap_id
	 */
	public static void mergeMyMap(String myMapName, Long mymap_id) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		MyMap mm = em.find(MyMap.class, mymap_id);
		mm.setName(myMapName);
		em.merge(mm);
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * finds MapMarkers for a given map id
	 * 
	 * @param mymap_id
	 * @return
	 */
	public static List<MyMapMarker> findMyMapMarkersByMymap_id(Long mymap_id) {
		EntityManager em = EntityManagerUtil.getEntityManager();

		TypedQuery<MyMapMarker> query = em.createQuery(
				"SELECT m FROM MyMapMarker m where m.myMap.mymap_id="
						+ mymap_id + " order by m.id asc", MyMapMarker.class);
		List<MyMapMarker> myMapMarkers = query.getResultList();

		em.close();
		return myMapMarkers;
	}

	/**
	 * Counts all activities for admin user.
	 * 
	 * @return
	 */
	public static Long findActivityCount() {
		EntityManager em = EntityManagerUtil.getEntityManager();

		Query query = em.createQuery("SELECT count(a) FROM Activity a");
		Long count = (Long) query.getSingleResult();

		em.close();
		return count;
	}

	/**
	 * find all activity records for admin user.
	 * 
	 * @param start
	 * @param max
	 * @return
	 */
	public static List<Activity> findActivitiesPaginated(int start, int max) {
		EntityManager em = EntityManagerUtil.getEntityManager();

		TypedQuery<Activity> query = em
				.createQuery("SELECT a FROM Activity a order by a.date desc",
						Activity.class);
		query.setFirstResult(start);
		query.setMaxResults(max);

		List<Activity> activities = query.getResultList();

		em.close();
		return activities;
	}

	/**
	 * Count the total number of user defined maps.
	 * 
	 * @return
	 */
	public static Long findMyMapCount() {
		EntityManager em = EntityManagerUtil.getEntityManager();

		Query query = em.createQuery("SELECT count(mm) FROM MyMap mm");
		Long count = (Long) query.getSingleResult();

		em.close();
		return count;
	}

	/**
	 * This method selects a subset of all maps for a given user.
	 * 
	 * @param start
	 * @param max
	 * @return
	 */
	public static List<MyMap> findMyMapsPaginated(int start, int max) {
		EntityManager em = EntityManagerUtil.getEntityManager();

		TypedQuery<MyMap> query = em.createQuery(
				"SELECT mm FROM MyMap mm order by mm.mymap_id desc",
				MyMap.class);
		query.setFirstResult(start);
		query.setMaxResults(max);

		List<MyMap> myMaps = query.getResultList();

		em.close();
		return myMaps;
	}

	/**
	 * 
	 * @return
	 */
	public static List<ActivityType> findAllActivityType() {
		EntityManager em = EntityManagerUtil.getEntityManager();

		TypedQuery<ActivityType> query = em.createQuery(
				"SELECT at FROM ActivityType at order by at.rank asc",
				ActivityType.class);

		List<ActivityType> activityTypes = query.getResultList();

		em.close();
		return activityTypes;
	}

	public static List<ActivitySubType> findAllActivitySubTypeByActivitytype_id(
			Long activitytype_id) {
		EntityManager em = EntityManagerUtil.getEntityManager();

		TypedQuery<ActivitySubType> query = em.createQuery(
				"SELECT ast FROM ActivitySubType ast where ast.activitytype_id="
						+ activitytype_id, ActivitySubType.class);

		List<ActivitySubType> activitySubTypes = query.getResultList();

		em.close();
		return activitySubTypes;
	}

	public static List<Equipment> findAllEquipmentByActivitytype_idAndUserId(
			Long activitytype_id, Long userId) {
		EntityManager em = EntityManagerUtil.getEntityManager();

		TypedQuery<Equipment> query = em.createQuery(
				"SELECT e FROM Equipment e where e.activitytype_id="
						+ activitytype_id + " and e.user.user_id=" + userId, Equipment.class);

		List<Equipment> equipment = query.getResultList();

		em.close();
		return equipment;
	}

	/**
	 * @param user_id
	 * @return
	 */
	public static Long findMeasureCountByUserId(Long user_id) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query = em
				.createQuery("SELECT count(m) FROM Measure m where m.user.user_id="
						+ user_id);
		Long count = (Long) query.getSingleResult();
		em.close();
		return count;
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
		EntityManager em = EntityManagerUtil.getEntityManager();
		TypedQuery<Measure> query = em.createQuery(
				"SELECT m FROM Measure m where m.user.user_id=" + user_id
						+ " order by m.date desc", Measure.class);
		query.setFirstResult(start);
		query.setMaxResults(max);
		List<Measure> measures = query.getResultList();
		em.close();
		return measures;
	}
	
	/**
	 * @param user_id
	 * @return
	 */
	public static Long findAllMeasureCount() {
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query = em
				.createQuery("SELECT count(m) FROM Measure m");
		Long count = (Long) query.getSingleResult();
		em.close();
		return count;
	}

	/**
	 * 
	 * @param start
	 * @param max
	 * @return
	 */
	public static List<Measure> findAllMeasuresPaginated(int start, int max) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		TypedQuery<Measure> query = em.createQuery(
				"SELECT m FROM Measure m order by m.date desc", Measure.class);
		query.setFirstResult(start);
		query.setMaxResults(max);
		List<Measure> measures = query.getResultList();
		em.close();
		return measures;
	}
	
	public static List<Object[]> summarizeActivityByUserByTime(Long user_id, Timestamp startDate, Timestamp endDate) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		//divide by 60 to convert to minutes
		Query q = em.createQuery("select at.description, sum(a.endTime - a.startTime)/60 from ActivityType at, Activity a "
				+ "where a.activitytype_id = at.activitytype_id and a.user.user_id="+user_id+
				" and a.date >='"+startDate + "' and a.date <='"+endDate +"' and a.startTime is not null and a.endTime is not null" 
				+ " group by at.description order by sum(a.endTime - a.startTime) desc");
		@SuppressWarnings("unchecked")
		List<Object[]> results = q.getResultList();
		em.close();
		return results;
	}

}
