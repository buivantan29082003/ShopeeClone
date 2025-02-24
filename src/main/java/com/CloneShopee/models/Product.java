package com.CloneShopee.models;

import java.util.Date;
import java.util.List;
import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Product")

public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank(message = "Tên sản phẩm ko được trống")
	@Length(min = 10, message = "Tên sản phẩm ít nhất 10 ký tự")
	private String productName;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shopId")
	private Shop shop;
	private String discription;
	@NotBlank(message = "Hình ảnh sản phẩm ko được trống")
	@NotNull(message = "Ko được bỏ trống ảnh sản phẩm")
	private String productImage;
	private Integer status;
	private String video;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brandId")
	@NotNull(message = "Vui lòng chọn brand cho sản phẩm")
	private Brand brand;
	@NotNull(message = "Vui lòng chọn mặt hàng cho sản phẩm")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryId")
	@JsonIgnore
	private Category category;
	private Date createdDate;
	private Double minPrice;
	private Double maxPrice;
	@Size(min = 1, message = "Sản phẩm ít nhất một variantTiers")
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<VariantTier> variantTiers;

	@Size(min = 1, message = "Sản phẩm ít nhất một variant")
	public List<ProductVariant> getProductVariants() {
		return productVariants;
	}

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<ProductVariant> productVariants;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<PropertyItem> properties;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<PromotionItem> promotionItems;

	// CONSTRUCTORS
	public Product() {

	}

	public Product(Integer id) {
		this.id = id;
	}

	// GETTERS/ SETTERS

	public void setProductVariants(List<ProductVariant> productVariants) {
		this.productVariants = productVariants;
	}

	public List<PropertyItem> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyItem> properties) {
		this.properties = properties;
	}

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<ProductImage> productImages;

	public List<ProductImage> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}

	public List<VariantTier> getVariantTiers() {
		return variantTiers;
	}

	public void setVariantTiers(List<VariantTier> variantTiers) {
		this.variantTiers = variantTiers;
	}

	public Integer getId() {
		return id;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<PromotionItem> getPromotionItems() {
		return promotionItems;
	}

	public void setPromotionItems(List<PromotionItem> promotionItems) {
		this.promotionItems = promotionItems;
	}
}
