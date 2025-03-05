package com.CloneShopee.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "OrderItems")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "orderId")
	private Order order;
	@ManyToOne
	@JoinColumn(name = "productId")
	private ProductVariant product;
	@Min(value = 1, message = "Số lượng đặt hàng ít nhất 1 sản phẩm")
	private Integer quantity;
	private Double price;

	public OrderItem() {

	}

	public OrderItem(ProductVariant product, Order order, Integer quantity, Double price) {
		this.product = product;
		this.order = order;
		this.quantity = quantity;
		this.price = price;
	}

	public Double caculatePrice() {
		return this.quantity * this.price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public ProductVariant getProduct() {
		return product;
	}

	public void setProduct(ProductVariant product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
