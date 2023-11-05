package com.driver.io.entity;

import org.hibernate.annotations.IndexColumn;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity(name = "orders")
public class OrderEntity {

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String orderId;

	@Column(nullable = false)
	private float cost;

    @ElementCollection
	@CollectionTable(name = "order_items",joinColumns = @JoinColumn(name = "order_id"))
	@Column(name = "item",nullable = false)
	@IndexColumn(name = "S.No")
	@Lob
	private String[] items;

	@Column(nullable = false)
	private String userId;
	
	@Column(nullable = false)
	private boolean status;

	@ManyToOne
	@JoinColumn
	UserEntity userEntity;

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public String[] getItems() {
		return items;
	}

	public void setItems(String[] items) {
		this.items = items;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
