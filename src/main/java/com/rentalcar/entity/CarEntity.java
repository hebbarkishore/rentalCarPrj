package com.rentalcar.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.util.StringUtils;

@Entity
public class CarEntity {
	@Id
	@GeneratedValue
	private Long id;
	private String car_make;
	private String car_model;

	public CarEntity() {
	}

	public CarEntity(Long id, String car_make, String car_model) {
		this.id = id;
		this.car_make = car_make;
		this.car_model = car_model;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCar_make() {
		return car_make;
	}

	public void setCar_make(String car_make) {
		this.car_make = StringUtils.isEmpty(car_make) ? null: car_make.toUpperCase();
	}

	public String getCar_model() {
		return car_model;
	}

	public void setCar_model(String car_model) {
		this.car_model = StringUtils.isEmpty(car_model) ? null: car_model.toUpperCase();;
	}

	@Override
	public String toString() {
		return "CarEntity [id=" + id + ", car_make=" + car_make + ", car_model=" + car_model + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((car_make == null) ? 0 : car_make.hashCode());
		result = prime * result + ((car_model == null) ? 0 : car_model.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		CarEntity other = (CarEntity) obj;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}