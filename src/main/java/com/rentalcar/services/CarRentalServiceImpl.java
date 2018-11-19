package com.rentalcar.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rentalcar.bean.ApplicationException;
import com.rentalcar.bean.CarRentalDto;
import com.rentalcar.dao.CarRentalRepo;
import com.rentalcar.entity.CarEntity;
import com.rentalcar.entity.CustomerEntity;

@Service
public class CarRentalServiceImpl implements CarRentalService {

	private static Log logger = LogFactory.getLog(CarRentalServiceImpl.class);

	@Autowired
	CarRentalRepo carRentalDao;

	/**
	 * Fetch all the Available cars from the DB
	 * @return
	 */
	public List<CarEntity> fetchAvailableCars() {
		return carRentalDao.getAllCars();
	}

	
	/**
	 * Fetch all the customer data saved in the DB
	 * @return
	 */
	public List<CarRentalDto> fetchCustomerData() {
		List<CustomerEntity> customers = carRentalDao.getAllCustomers();
		List<CarRentalDto> rentalDtoList = new ArrayList<>();
		if (customers != null) {
			for (CustomerEntity entity : customers) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				String booking_date_time = df.format(entity.getBooking_date_time());
				CarRentalDto dto = new CarRentalDto();
				BeanUtils.copyProperties(entity, dto);
				dto.setBooking_date_time(booking_date_time);
				CarEntity carent = carRentalDao.getCarById(entity.getCar_id());
				dto.setCar_make(carent.getCar_make());
				dto.setCar_model(carent.getCar_model());
				rentalDtoList.add(dto);
			}
		}
		return rentalDtoList;
	}

	/**
	 * Adding the rental request to DB
	 * @param carRentalReqst
	 * @throws ApplicationException
	 */
	public void carRentalRequest(CarRentalDto carRentalReqst) throws ApplicationException {
		logger.info("Inside carRentalRequest");
		CustomerEntity custEntity = new CustomerEntity();
		BeanUtils.copyProperties(carRentalReqst, custEntity);
		Long carId = carRentalDao.getCarId(carRentalReqst.getCar_make(), carRentalReqst.getCar_model());
		custEntity.setCar_id(carId);

		// Input expected as "2018-11-18T11:36:08";
		try {
			SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date start_date = inFormat.parse(carRentalReqst.getBooking_date_time());
			Calendar c = Calendar.getInstance();
			c.setTime(start_date);
			c.add(Calendar.DATE, carRentalReqst.getTot_days_booked());
			String output = inFormat.format(c.getTime());
			Date end_dte = inFormat.parse(output);

			custEntity.setBooking_date_time(start_date);
			custEntity.setEnd_date_time(end_dte);
		} catch (ParseException e) {
			throw new ApplicationException("Invalid Date/Time format of Booking_date_time.");
		}

		List<CustomerEntity> exstngCustmrs = carRentalDao.getCustomersByCarId(carId);

		if (exstngCustmrs != null) {
			for (CustomerEntity dbRcrds : exstngCustmrs) {
				if (dbRcrds != null) {
					if ((dbRcrds.getBooking_date_time().compareTo(custEntity.getBooking_date_time()) == 0)
							|| (dbRcrds.getBooking_date_time().compareTo(custEntity.getBooking_date_time()) < 0
									&& dbRcrds.getEnd_date_time().compareTo(custEntity.getBooking_date_time()) > 0)
							|| (dbRcrds.getBooking_date_time().compareTo(custEntity.getBooking_date_time()) > 0
									&& custEntity.getEnd_date_time().compareTo(dbRcrds.getBooking_date_time()) > 0)) {
						throw new ApplicationException("Requested Car already booked from|"
								+ dbRcrds.getBooking_date_time() + "|, till |" + dbRcrds.getEnd_date_time() + "|");
					}
				}
			}
		}
		carRentalDao.saveCustomer(custEntity);
	}

}
