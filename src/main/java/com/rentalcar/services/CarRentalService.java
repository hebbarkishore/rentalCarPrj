package com.rentalcar.services;

import java.util.List;

import com.rentalcar.bean.ApplicationException;
import com.rentalcar.bean.CarRentalDto;
import com.rentalcar.entity.CarEntity;

public interface CarRentalService {

	public List<CarEntity> fetchAvailableCars();
	
	public List<CarRentalDto> fetchCustomerData();
	
	public void carRentalRequest( CarRentalDto carRentalReqst) throws ApplicationException ;
}
