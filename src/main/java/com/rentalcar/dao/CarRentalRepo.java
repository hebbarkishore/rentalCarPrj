package com.rentalcar.dao;

import java.util.List;

import com.rentalcar.bean.ApplicationException;
import com.rentalcar.entity.CarEntity;
import com.rentalcar.entity.CustomerEntity;

public interface CarRentalRepo {

	public Long saveCar(CarEntity car);

	public List<CarEntity> getAllCars();

	public Long getCarId(String make, String model) throws ApplicationException;

	public CarEntity getCarById(Long id);

	public Long saveCustomer(CustomerEntity customer);

	public List<CustomerEntity> getAllCustomers();

	public List<CustomerEntity> getCustomersByCarId(Long car_id);

}
