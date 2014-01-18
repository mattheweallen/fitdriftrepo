package com.fitdrift.view.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.fitdrift.domain.user.UserSignIn;
import com.fitdrift.model.AthleticgisFacade;

public class UserSignInDataModel extends LazyDataModel<UserSignIn> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -547369542622854808L;
	
	private List<UserSignIn> userSignIn;
	//private Long user_id;
	//private boolean isAdmin;

//	public UserSignInDataModel(Long user_id, boolean isAdmin) {
//		//this.user_id = user_id;
//		//this.isAdmin = isAdmin;
//	}

	@Override
	public UserSignIn getRowData(String uid) {
		Long id = Long.valueOf(uid);
		
		for (UserSignIn u : userSignIn) {
			if (id.equals(u.getId()))
				return u;
		}

		return null;
	}

	@Override
	public Object getRowKey(UserSignIn u) {
		return u.getId();
	}

	@Override
	public List<UserSignIn> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
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
//		if(!isAdmin) {
//			activities = AthleticgisFacade.findActivitiesByUserIdPaginated(user_id, first, pageSize);
//
//			// set the total number of activities
//			dataSize = AthleticgisFacade.findActivityCountByUserId(user_id).intValue(); 
//		} else {
			userSignIn = AthleticgisFacade.findUserSignInPaginated(first, pageSize);
			// set the total number of activities
			dataSize = AthleticgisFacade.findUserSignInCount().intValue(); 
//		}
		
		this.setRowCount(dataSize);
		
		this.setPageSize(pageSize);

		return userSignIn;
	}

}
