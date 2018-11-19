package com.rentalcar.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.springframework.util.StringUtils;

@Entity
public class CustomerEntity {
	public CustomerEntity() {
		super();
	}
	public CustomerEntity(Long id, String customer_nam, Date booking_date_time, int tot_days_booked, Date end_date_time,
			Long car_id) {
		super();
		this.id = id;
		this.customer_nam = customer_nam;
		this.booking_date_time = booking_date_time;
		this.tot_days_booked = tot_days_booked;
		this.end_date_time = end_date_time;
		this.car_id = car_id;
	}

	@Id
	@GeneratedValue
	private Long id;

	private String customer_nam;
	private Date booking_date_time;
	private int tot_days_booked;
	private Date end_date_time;
	private Long car_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomer_nam() {
		return customer_nam;
	}

	public void setCustomer_nam(String customer_nam) {
		this.customer_nam = StringUtils.isEmpty(customer_nam) ? null: customer_nam.toUpperCase();
	}

	public Date getBooking_date_time() {
		return booking_date_time;
	}

	public void setBooking_date_time(Date booking_date_time) {
		this.booking_date_time = booking_date_time;
	}

	public int getTot_days_booked() {
		return tot_days_booked;
	}

	public void setTot_days_booked(int tot_days_booked) {
		this.tot_days_booked = tot_days_booked;
	}

	public Long getCar_id() {
		return car_id;
	}

	public void setCar_id(Long car_id) {
		this.car_id = car_id;
	}

	public Date getEnd_date_time() {
		return end_date_time;
	}

	public void setEnd_date_time(Date end_date_time) {
		this.end_date_time = end_date_time;
	}

	@Override
	public String toString() {
		return "CustomerEntity [id=" + id + ", customer_nam=" + customer_nam + ", booking_date_time="
				+ booking_date_time + ", tot_days_booked=" + tot_days_booked + ", end_date_time=" + end_date_time
				+ ", car_id=" + car_id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((booking_date_time == null) ? 0 : booking_date_time.hashCode());
		result = prime * result + ((car_id == null) ? 0 : car_id.hashCode());
		result = prime * result + ((customer_nam == null) ? 0 : customer_nam.hashCode());
		result = prime * result + ((end_date_time == null) ? 0 : end_date_time.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		CustomerEntity other = (CustomerEntity) obj;
		if (booking_date_time == null) {
			if (other.booking_date_time != null)
				return false;
		} else if (!booking_date_time.equals(other.booking_date_time))
			return false;
		if (car_id == null) {
			if (other.car_id != null)
				return false;
		} else if (!car_id.equals(other.car_id))
			return false;
		if (customer_nam == null) {
			if (other.customer_nam != null)
				return false;
		} else if (!customer_nam.equals(other.customer_nam))
			return false;
		if (end_date_time == null) {
			if (other.end_date_time != null)
				return false;
		} else if (!end_date_time.equals(other.end_date_time))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tot_days_booked != other.tot_days_booked)
			return false;
		return true;
	}
	
	
}
