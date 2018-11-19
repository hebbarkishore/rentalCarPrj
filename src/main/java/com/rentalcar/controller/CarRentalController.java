package com.rentalcar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.rentalcar.bean.CarRentalDto;
import com.rentalcar.bean.ApplicationException;
import com.rentalcar.entity.CarEntity;
import com.rentalcar.services.CarRentalService;
import java.text.ParseException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@RestController
@RequestMapping("/service")
public class CarRentalController {

	private static Log logger = LogFactory.getLog(CarRentalController.class);

	@Autowired
	CarRentalService carRentalSrvc;

	/**
	 * This fetches all the available cars in the application db.
	 * @return
	 */
	@RequestMapping(value = "/fetchAvailableCars", method = RequestMethod.GET)
	public ResponseEntity<List<CarEntity>> fetchAvailableCars() {
		logger.debug("Inside fetchAvailableCars");
		List<CarEntity> cars = carRentalSrvc.fetchAvailableCars();
		return new ResponseEntity<List<CarEntity>>(cars, HttpStatus.OK);
	}

	/**
	 * This fetches customer data information saved in the DB.
	 * Customer information includes Customer Name, Booking Start Date/time, End Date/Time, Number of Days requested, Car Make, Model requested.
	 * @return
	 */
	@RequestMapping(value = "/fetchCustomerData", method = RequestMethod.GET)
	public ResponseEntity<List<CarRentalDto>> fetchCustomerData() {
		List<CarRentalDto> rentalDtoList = carRentalSrvc.fetchCustomerData();
		return new ResponseEntity<List<CarRentalDto>>(rentalDtoList, HttpStatus.OK);
	}

	/**
	 * This is a Post request to book the Car for rental.
	 * Sample JSON request is as follows.
	 * {
			"customer_nam" : "kishore",
			"booking_date_time" : "2018-11-24T12:00:00",
			"tot_days_booked" : 1,
			"car_make" : "Toyota",
			"car_model" : "Camry"
		}
	 * @param carRentalReqst
	 * @return
	 * @throws ApplicationException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/carRentalRequest", method = RequestMethod.POST)
	public ResponseEntity<String> carRentalRequest(@RequestBody(required = true) CarRentalDto carRentalReqst) throws ApplicationException, ParseException			 {
		logger.info("Inside carRentalRequest");
		carRentalSrvc.carRentalRequest(carRentalReqst);
		return new ResponseEntity<String>("Rental booking successful.", HttpStatus.CREATED);
	}

}