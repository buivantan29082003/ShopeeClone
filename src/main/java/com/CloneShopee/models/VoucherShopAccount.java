package com.CloneShopee.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "voucherShopAccount")
public class VoucherShopAccount {

	@EmbeddedId
	private VoucherShopAccountId id;
	@Column(name = "quantityUsed")
	private Integer quantityUsed;

	public VoucherShopAccountId getId() {
		return id;
	}

	public void setId(VoucherShopAccountId id) {
		this.id = id;
	}

	public Integer getQuantityUsed() {
		return quantityUsed;
	}

	public void setQuantityUsed(Integer quantityUsed) {
		this.quantityUsed = quantityUsed;
	}

}
