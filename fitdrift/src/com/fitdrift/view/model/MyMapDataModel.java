package com.fitdrift.view.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.fitdrift.domain.activity.MyMap;
import com.fitdrift.model.AthleticgisFacade;

/**
 * This class is used as a model for pagination for table in mymaps page.
 * 
 * @author Matthew Allen
 * @version 20131207
 *
 */
public class MyMapDataModel extends LazyDataModel<MyMap> {
	private static final long serialVersionUID = 4332807581817979390L;
	private List<MyMap> myMaps;
	private Long user_id;
	private boolean isAdmin;

	/**
	 * 
	 * @param user_id
	 * @param isAdmin
	 */
	public MyMapDataModel(Long user_id, boolean isAdmin) {
		this.user_id = user_id;
		this.isAdmin = isAdmin;
	}

	@Override
	public MyMap getRowData(String myMapId) {
		Long id = Long.valueOf(myMapId);
		
		for (MyMap myMap : myMaps) {
			if (id.equals(myMap.getMymap_id()))
				return myMap;
		}
		return null;
	}

	@Override
	public Object getRowKey(MyMap myMap) {
		return myMap.getMymap_id();
	}

	@Override
	public List<MyMap> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
			
		//System.out.println("Loading....");
		
		Integer dataSize;
		if(!isAdmin) {
			myMaps = AthleticgisFacade.findMyMapsByUserIdPaginated(user_id, first, pageSize);
		
		
			// set the total number of activities
			dataSize = AthleticgisFacade.findMyMapCountByUserId(user_id).intValue(); 
		} else {
			myMaps = AthleticgisFacade.findMyMapsPaginated(first, pageSize);
			
			
			// set the total number of activities
			dataSize = AthleticgisFacade.findMyMapCount().intValue(); 
		}
		this.setRowCount(dataSize);
		
		this.setPageSize(pageSize);

		return myMaps;
	}

}
