package com.rentalcar.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentalcar.dao.CarRentalRepo;
import com.rentalcar.entity.CarEntity;

@Component
public class CarRentalPostConstructBean {

	private static Log logger = LogFactory.getLog(CarRentalPostConstructBean.class);

	@Autowired
	CarRentalRepo carRentalDao;

	@PostConstruct
	public void init() {
		logger.info("Init method of postconstruct");
		// read json and write to db
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<CarEntity>> typeReference = new TypeReference<List<CarEntity>>() {};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/json/cars_data.json");
		try {
			List<CarEntity> cars = mapper.readValue(inputStream, typeReference);
			for (CarEntity car : cars) {
				carRentalDao.saveCar(car);
			}
		} catch (IOException e) {
			logger.info("Unable to save users: " + e.getMessage());
		}

	}
}