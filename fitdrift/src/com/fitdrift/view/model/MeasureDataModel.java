package com.fitdrift.view.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.fitdrift.domain.activity.Activity;
import com.fitdrift.domain.activity.Measure;
import com.fitdrift.model.AthleticgisFacade;

public class MeasureDataModel extends LazyDataModel<Measure> {
	private static final long serialVersionUID = -2219857311817350955L;
	
	private List<Measure> measures;
	private Long user_id;
	private boolean isAdmin;

	public MeasureDataModel(Long user_id, boolean isAdmin) {
		this.user_id = user_id;
		this.isAdmin = isAdmin;
	}

	@Override
	public Measure getRowData(String measureId) {
		Long id = Long.valueOf(measureId);
		
		for (Measure m : measures) {
			if (id.equals(m.getMeasure_id()))
				return m;
		}

		return null;
	}

	@Override
	public Object getRowKey(Measure m) {
		return m.getMeasure_id();
	}

	@Override
	public List<Measure> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {

		
		Integer dataSize;
		if(!isAdmin) {
			measures = AthleticgisFacade.findMeasuresByUserIdPaginated(user_id, first, pageSize);

			// set the total number of measures
			dataSize = AthleticgisFacade.findMeasureCountByUserId(user_id).intValue(); 
		} else {
			measures = AthleticgisFacade.findAllMeasuresPaginated(first, pageSize);
			// set the total number of measures
			dataSize = AthleticgisFacade.findAllMeasureCount().intValue(); 
		}
		
		this.setRowCount(dataSize);
		
		this.setPageSize(pageSize);

		return measures;
	}

}
