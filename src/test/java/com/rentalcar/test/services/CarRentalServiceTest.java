package com.rentalcar.test.services;

import javax.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.rentalcar.bean.ApplicationException;
import com.rentalcar.bean.CarRentalDto;
import com.rentalcar.dao.CarRentalRepo;
import com.rentalcar.entity.CarEntity;
import com.rentalcar.entity.CustomerEntity;
import com.rentalcar.services.CarRentalService;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import static org.hamcrest.CoreMatchers.*;

@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CarRentalServiceTest {

	@Autowired
	CarRentalRepo carRentalDao;

	@Autowired
	private CarRentalService rentalService;

	private CarRentalDto reqDto = new CarRentalDto();

	@Before
	public void setUp() {
		reqDto = new CarRentalDto("KISHORE", "2018-11-18T11:00:00", 2, "TOYOTA", "CAMRY");
	}

	/**
	 * This tests the available cars for selection.
	 * We load 4 cars at the beginning, Toyota-Camry, Honda-Civic, Nissan-Altima, Kia-Optima
	 */
	@Test
	public void availableCarsTest() {
		assertThat(rentalService.fetchAvailableCars(), hasItems(new CarEntity(1L, "TOYOTA", "CAMRY")));
		assertThat(rentalService.fetchAvailableCars().size(), is(4));
	}

	/**
	 * This tests the fetch method to retrieve the saved customer data. 
	 * @throws Exception
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void fetchCustomerDataTest() throws Exception {
		SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		CustomerEntity customerEntity = new CustomerEntity(null, "KISHORE", inFormat.parse("2018-11-18T11:00:00"), 1,
				inFormat.parse("2018-11-19T11:00:00"), 1L);
		carRentalDao.saveCustomer(customerEntity);
		assertThat(rentalService.fetchCustomerData(),
				hasItems(new CarRentalDto("KISHORE", "2018-11-18T11:00:00", 1, "TOYOTA", "CAMRY")));
	}

	
	/**
	 * This tests the positive scenario where all the request parameter is good and no overlapping date.
	 * @throws Exception
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void carRentalReqNewRecrd_Test() throws Exception {
		rentalService.carRentalRequest(reqDto);
	}

	
	/**
	 * This tests the case where we get the duplicate request with the same parameter.
	 * @throws Exception
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void carRentalReqDupRqst_Test() throws Exception {
		rentalService.carRentalRequest(reqDto);
		try {
			rentalService.carRentalRequest(reqDto);
			fail("Expected an ApplicationException to be thrown");
		} catch (ApplicationException e) {
			assertThat(e.getMessage(), is(
					"Requested Car already booked from|Sun Nov 18 11:00:00 EST 2018|, till |Tue Nov 20 11:00:00 EST 2018|"));
		}
	}

	
	/**
	 * This test the case where the requesting car is same but it is overlapping with the previous request,
	 * here we expect application to send the error message back.
	 * @throws Exception
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void carRentalReqOvrLapDte_Test() throws Exception {
		reqDto = new CarRentalDto("KISHORE", "2018-11-20T11:00:00", 4, "TOYOTA", "CAMRY");
		rentalService.carRentalRequest(reqDto);
		try {
			reqDto = new CarRentalDto("KISHORE", "2018-11-19T11:00:00", 3, "TOYOTA", "CAMRY");
			rentalService.carRentalRequest(reqDto);
			fail("Expected an ApplicationException to be thrown");
		} catch (ApplicationException e) {
			assertThat(e.getMessage(), is(
					"Requested Car already booked from|Tue Nov 20 11:00:00 EST 2018|, till |Sat Nov 24 11:00:00 EST 2018|"));
		}
	}
	
	
	/**
	 * This test the case where user sending the invalid date/time in the post rest call.
	 * Expected format is yyyy-MM-dd'T'HH:mm:ss.
	 * @throws Exception
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void carRentalInvalidDtReq_Test() throws Exception {
		try {
			reqDto = new CarRentalDto("KISHORE", "2018-11-1911:00:00", 3, "TOYOTA", "CAMRY");
			rentalService.carRentalRequest(reqDto);
			fail("Expected an ApplicationException to be thrown");
		} catch (ApplicationException e) {
			assertThat(e.getMessage(), is(
					"Invalid Date/Time format of Booking_date_time."));
		}
	}
	
	
	/**
	 * This tests the case where we get the different request for the same car but for different no overlapping date/time.
	 * Expecting no issue with the result.
	 * @throws Exception
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void carRentalReqSamCarDifDt_Test() throws Exception {
		reqDto = new CarRentalDto("KISHORE", "2018-11-20T11:00:00", 4, "TOYOTA", "CAMRY");
		rentalService.carRentalRequest(reqDto);
		reqDto = new CarRentalDto("KISHORE", "2018-11-24T11:00:00", 3, "TOYOTA", "CAMRY");
		rentalService.carRentalRequest(reqDto);
	}
	
	
	/**
	 * This tests the case where we get the different request for the same car, no overlapping date/time and also different customer.
	 * Expecting no issue with the result.
	 * @throws Exception
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void carRentalReqDiffCustDifDt_Test() throws Exception {
		reqDto = new CarRentalDto("KISHORE", "2018-11-20T11:00:00", 4, "TOYOTA", "CAMRY");
		rentalService.carRentalRequest(reqDto);
		reqDto = new CarRentalDto("HEBBAR", "2018-11-24T11:00:00", 3, "TOYOTA", "CAMRY");
		rentalService.carRentalRequest(reqDto);
	}
	
	
	/**
	 * This tests the case where we get the different Customers, different cars.
	 * Expecting no issue with the result.
	 * @throws Exception
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void carRentalReqDiffCustDifCar_Test() throws Exception {
		reqDto = new CarRentalDto("KISHORE", "2018-11-20T11:00:00", 4, "TOYOTA", "CAMRY");
		rentalService.carRentalRequest(reqDto);
		reqDto = new CarRentalDto("HEBBAR", "2018-11-24T11:00:00", 3, "HONDA", "CIVIC");
		rentalService.carRentalRequest(reqDto);
	}
	
	/**
	 * This tests the case where user is requesting with the Invalid cars or the car which is not in the Application DB.
	 * We expect Application to throw the error back to user.
	 * @throws Exception
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void carRentalReqInvalidCar_Test() throws Exception {
		try {
			reqDto = new CarRentalDto("KISHORE", "2018-11-20T11:00:00", 4, "TOYOTA", "CAMRY");
			rentalService.carRentalRequest(reqDto);
			reqDto = new CarRentalDto("HEBBAR", "2018-11-24T11:00:00", 3, "JAGUAR", "XE");
			rentalService.carRentalRequest(reqDto);
			fail("Expected an ApplicationException to be thrown");
		} catch (ApplicationException e) {
			assertThat(e.getMessage(), is(
					"Invalid car selection."));
		}
	}

}
