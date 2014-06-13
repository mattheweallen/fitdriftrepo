package com.fitdrift.view.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.fitdrift.domain.activity.Activity;
import com.fitdrift.model.AthleticgisFacade;

/**
 * This class is used as a model for pagination for table in dashboard page.
 * 
 * @author Matthew Allen
 * @version 20131207
 *
 */
public class ActivityDataModel extends LazyDataModel<Activity> {
	private static final long serialVersionUID = 5024184940806000630L;

	private List<Activity> activities;
	private Long user_id;
	private boolean isAdmin;

	public ActivityDataModel(Long user_id, boolean isAdmin) {
		this.user_id = user_id;
		this.isAdmin = isAdmin;
	}

	@Override
	public Activity getRowData(String activityId) {
		Long id = Long.valueOf(activityId);
		
		for (Activity activity : activities) {
			if (id.equals(activity.getActivity_id()))
				return activity;
		}

		return null;
	}

	@Override
	public Object getRowKey(Activity activity) {
		return activity.getActivity_id();
	}

	@Override
	public List<Activity> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		//	List<Activity> data = new ArrayList<Activity>();

//		// filter
//		for (Activity activity : datasource) {
//			boolean match = true;
//
//			for (Iterator<String> it = filters.keySet().iterator(); it
//					.hasNext();) {
//				try {
//					String filterProperty = it.next();
//					String filterValue = filters.get(filterProperty);
//					String fieldValue = String.valueOf(activity.getClass()
//							.getField(filterProperty).get(activity));
//
//					if (filterValue == null
//							|| fieldValue.startsWith(filterValue)) {
//						match = true;
//					} else {
//						match = false;
//						break;
//					}
//				} catch (Exception e) {
//					match = false;
//				}
//			}
//
//			if (match) {
//				data.add(activity);
//			}
//		}

//		// sort
//		if (sortField != null) {
//			Collections.sort(data, new LazySorter(sortField, sortOrder));
//		}
		
		//System.out.println("Loading....");
		
		Integer dataSize;
		if(!isAdmin) {
			activities = AthleticgisFacade.findActivitiesByUserIdPaginated(user_id, first, pageSize);

			// set the total number of activities
			dataSize = AthleticgisFacade.findActivityCountByUserId(user_id).intValue(); 
		} else {
			activities = AthleticgisFacade.findActivitiesPaginated(first, pageSize);
			// set the total number of activities
			dataSize = AthleticgisFacade.findActivityCount().intValue(); 
		}
		
		this.setRowCount(dataSize);
		
		this.setPageSize(pageSize);

		return activities;
	}

}
