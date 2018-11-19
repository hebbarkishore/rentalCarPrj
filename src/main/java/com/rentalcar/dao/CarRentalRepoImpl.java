package com.rentalcar.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.rentalcar.bean.ApplicationException;
import com.rentalcar.entity.CarEntity;
import com.rentalcar.entity.CustomerEntity;

@Repository
@Transactional
public class CarRentalRepoImpl implements CarRentalRepo{

	private static Log logger = LogFactory.getLog(CarRentalRepoImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	public Long saveCar(CarEntity car) {
		em.persist(car);
		return car.getId();
	}

	public List<CarEntity> getAllCars() {
		return em.createQuery("SELECT c FROM CarEntity c", CarEntity.class).getResultList();
	}
	
	public Long getCarId(String make, String model) throws ApplicationException{
		try {
			return em.createQuery("SELECT c FROM CarEntity c where c.car_make=:make and c.car_model=:model", CarEntity.class).setParameter("make", make.toUpperCase()).setParameter("model", model.toUpperCase()).getSingleResult().getId();
		} catch (Exception e) {
			throw new ApplicationException("Invalid car selection.");
		}
	}
	
	public CarEntity getCarById(Long id) {
		return em.createQuery("SELECT c FROM CarEntity c where c.id=:id", CarEntity.class).setParameter("id", id).getSingleResult();
	}

	public Long saveCustomer(CustomerEntity customer) {
		em.persist(customer);
		return customer.getId();
	}	

	public List<CustomerEntity> getAllCustomers() {
		return em.createQuery("SELECT c FROM CustomerEntity c", CustomerEntity.class).getResultList();
	}
	
	public List<CustomerEntity> getCustomersByCarId(Long car_id) {
		try {
			return em.createQuery("SELECT c FROM CustomerEntity c where c.car_id=:carId", CustomerEntity.class).setParameter("carId", car_id).getResultList();
		} catch (Exception e) {
			logger.warn("No entity bean registered yet");
			return null;
		}
	}
	
}