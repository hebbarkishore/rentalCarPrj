package com.rentalcar.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarRentalDto {
	
	public CarRentalDto() {
		super();
	}
	public CarRentalDto(String customer_nam, String booking_date_time, int tot_days_booked, String car_make,
			String car_model) {
		super();
		this.customer_nam = customer_nam;
		this.booking_date_time = booking_date_time;
		this.tot_days_booked = tot_days_booked;
		this.car_make = car_make;
		this.car_model = car_model;
	}

	private String customer_nam;
	private String booking_date_time;
	private int tot_days_booked;
	private String car_make;
	private String car_model;
	
	public String getCustomer_nam() {
		return customer_nam;
	}
	public void setCustomer_nam(String customer_nam) {
		this.customer_nam = customer_nam;
	}
	public String getBooking_date_time() {
		return booking_date_time;
	}
	public void setBooking_date_time(String booking_date_time) {
		this.booking_date_time = booking_date_time;
	}
	public int getTot_days_booked() {
		return tot_days_booked;
	}
	public void setTot_days_booked(int tot_days_booked) {
		this.tot_days_booked = tot_days_booked;
	}
	public String getCar_make() {
		return car_make;
	}
	public void setCar_make(String car_make) {
		this.car_make = car_make;
	}
	public String getCar_model() {
		return car_model;
	}
	public void setCar_model(String car_model) {
		this.car_model = car_model;
	}
	
	@Override
	public String toString() {
		return "CarRentalRequest [customer_nam=" + customer_nam + ", booking_date_time=" + booking_date_time
				+ ", tot_days_booked=" + tot_days_booked + ", car_make=" + car_make + ", car_model=" + car_model + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((booking_date_time == null) ? 0 : booking_date_time.hashCode());
		result = prime * result + ((car_make == null) ? 0 : car_make.hashCode());
		result = prime * result + ((car_model == null) ? 0 : car_model.hashCode());
		result = prime * result + ((customer_nam == null) ? 0 : customer_nam.hashCode());
		result = prime * result + tot_days_booked;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarRentalDto other = (CarRentalDto) obj;
		if (booking_date_time == null) {
			if (other.booking_date_time != null)
				return false;
		} else if (!booking_date_time.equals(other.booking_date_time))
			return false;
		if (car_make == null) {
			if (other.car_make != null)
				return false;
		} else if (!car_make.equals(other.car_make))
			return false;
		if (car_model == null) {
			if (other.car_model != null)
				return false;
		} else if (!car_model.equals(other.car_model))
			return false;
		if (customer_nam == null) {
			if (other.customer_nam != null)
				return false;
		} else if (!customer_nam.equals(other.customer_nam))
			return false;
		if (tot_days_booked != other.tot_days_booked)
			return false;
		return true;
	}
	
	
}
