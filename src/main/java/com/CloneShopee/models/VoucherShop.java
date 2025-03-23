package com.CloneShopee.models;

import java.util.Date;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.hibernate.boot.beanvalidation.IntegrationException;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "VoucherShop")
@Inheritance(strategy = InheritanceType.JOINED)
public class VoucherShop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank
	@NotNull(message = "Không được để trống tên voucher")
	private String voucherName;
	@NotBlank
	@NotNull(message = "Không được để trống mã voucher")
	@Length(min = 1, max = 5, message = "Tối thiểu 1 ký tự, tối đa 5 ký tự")
	private String voucherCode;
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	private String voucherType;
	private Double discountValue;
	@NotNull(message = "Ko được để trống trường này")
	@Min(value = 1, message = "Tối thiểu 1 lược sử dụng")
	private Integer limitUsage;
	private Double limitValue;
	private Integer isActive;
	// private Integer quantityUsed;
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "shopId")
	private Shop shop;
	@JsonIgnore
	@OneToMany(mappedBy = "voucherShop", fetch = FetchType.LAZY)
	private List<VoucherShopItem> voucherItems;
	private Double minimumPurchase;

	private String voucherStyle;

	public VoucherShop() {

	}

	public void canculateOrder(Order order, Double ammountNotDiscount, Double totalAmmountInVoucher) {
		if (ammountNotDiscount >= minimumPurchase) {
			switch (this.voucherType) {
				case "PERSENT":
					order.setTotalAmount(order.getTotalAmount() - (totalAmmountInVoucher * (1 - this.discountValue)));
					break;
				default:
					Double ammount = totalAmmountInVoucher - this.discountValue;
					order.setTotalAmount(ammount < 0 ? order.getTotalAmount() - totalAmmountInVoucher
							: order.getTotalAmount() - ammount);
					break;
			}
		} else {
			throw new IntegrationException("Số tiền hiện tại không thể áp dụng voucher bnayf");
		}
	}

	public VoucherShop(Integer id, Double discountValue, String voucherType, Double minimumPurchase) {
		this.id = id;
		this.discountValue = discountValue;
		this.voucherType = voucherType;
		this.minimumPurchase = minimumPurchase;
	}

	public String getVoucherName() {
		return voucherName;
	}

	public void setVoucherName(String voucherName) {
		this.voucherName = voucherName;
	}

	public List<VoucherShopItem> getVoucherItems() {
		return voucherItems;
	}

	public void setVoucherItems(List<VoucherShopItem> voucherItems) {
		this.voucherItems = voucherItems;
	}

	public Integer getId() {
		return id;
	}

	// public Integer getQuantityUsed() {
	// return quantityUsed;
	// }

	// public void setQuantityUsed(Integer quantityUsed) {
	// this.quantityUsed = quantityUsed;
	// }

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVouhcherName() {
		return voucherName;
	}

	public void setVouhcherName(String vouhcherName) {
		this.voucherName = vouhcherName;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(Double discountValue) {
		this.discountValue = discountValue;
	}

	public Integer getLimitUsage() {
		return limitUsage;
	}

	public void setLimitUsage(Integer limitUsage) {
		this.limitUsage = limitUsage;
	}

	public Double getLimitValue() {
		return limitValue;
	}

	public void setLimitValue(Double limitValue) {
		this.limitValue = limitValue;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

	public Double getMinimumPurchase() {
		return minimumPurchase;
	}

	public void setMinimumPurchase(Double minimumPurchase) {
		this.minimumPurchase = minimumPurchase;
	}

	public String getVoucherStyle() {
		return voucherStyle;
	}

	public void setVoucherStyle(String voucherStyle) {
		this.voucherStyle = voucherStyle;
	}

}
