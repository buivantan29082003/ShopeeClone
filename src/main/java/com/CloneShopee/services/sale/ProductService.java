package com.CloneShopee.services.sale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.hibernate.boot.beanvalidation.IntegrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.CloneShopee.DTO.User.PromotionInProductSearch;
import com.CloneShopee.ExceptionGlobal.ConstraintException;
import com.CloneShopee.models.Product;
import com.CloneShopee.models.ProductVariant;
import com.CloneShopee.models.Property;
import com.CloneShopee.models.PropertyItem;
import com.CloneShopee.models.VariantTier;
import com.CloneShopee.repository.BrandRepository;
import com.CloneShopee.repository.Categoryrepository;
import com.CloneShopee.repository.ProductImagesReopository;
import com.CloneShopee.repository.ProductRepository;
import com.CloneShopee.repository.ProductVariantRepository;
import com.CloneShopee.repository.PromotionRepository;
import com.CloneShopee.repository.PropertyItemsRepository;
import com.CloneShopee.repository.PropertyRepository;
import com.CloneShopee.repository.VariantTierRepository;
import com.CloneShopee.repository.SpecificationBuilder.ProductSpecification;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepo;
	@Autowired
	PropertyRepository propertyRepo;
	@Autowired
	BrandRepository brandRepo;
	@Autowired
	Categoryrepository categoryRepo;
	@Autowired
	VariantTierRepository variantTierRepo;
	@Autowired
	ProductVariantRepository productVariantRepo;
	@Autowired
	PropertyItemsRepository propertyItemRepo;
	@Autowired
	ProductImagesReopository productImagesRepo;
	@Autowired
	PromotionRepository promotionRepo;

	private final Map<String, Integer> statusProduct = new HashMap<>(Map.of(
			"ACTIVE", 1,
			"HIDDEN", 0,
			"DELETE", 2));

	public Product getProductById(Integer id) {
		return productRepo.findById(id).orElse(null);
	}

	public List<PromotionInProductSearch> getPromotionInfoOfProduct(List<Product> products) {
		return promotionRepo.getPromotionInfoInListProduct(products);
	}

	public List<Product> filterProducts(String name, List<Integer> categoryIds, Integer status,
			List<Integer> brandIds, Double minPrice, Double maxPrice) {
		Specification<Product> spec = ProductSpecification.filterProductsWithJoinfetch(name, categoryIds, status,
				brandIds, minPrice, maxPrice);
		return productRepo.findAll(spec);
	}

	public List<Product> findAllProducts() {
		return productRepo.findAll();
	}

	public Product findByIdFullProperties(Integer productId) {
		Product product = productRepo.findByIdFullProperties(productId).orElse(null);
		if (product != null) {
			product.setVariantTiers(null);
			product.getProductImage();
			product.getProductVariants();
			product.setPromotionItems(null);
		}
		return product;
	}

	public List<PromotionInProductSearch> getPromotionOfProductId(Integer productId) {
		return promotionRepo.getPromotionInfoByProductid(productId);
	}

	public void updateStatusProduct(String status, Integer productId, Integer shopId) {
		if (statusProduct.get(status) != null) {
			Integer product = productRepo.getIdProductByProductIdAndShopId(productId, shopId);
			if (product != null) {
				productRepo.updateStatusByProductId(productId, statusProduct.get(status));
			} else {
				throw new ConstraintException("product", "Product not of shop !!!");
			}
		}
		throw new ConstraintException("status", "Status change is not valid !!!");
	}

	public void updateProperties(Product product, List<PropertyItem> propertyItems, Integer categoryOld) {
		if (propertyItems == null || propertyItems.isEmpty()) {
			return;
		}
		if (categoryOld.equals(product.getCategory().getId())) {
			checkProperties(propertyItems, product);
			List<Integer> ids = propertyItems.stream()
					.map(PropertyItem::getId)
					.filter(id -> id != null)
					.collect(Collectors.toList());
			Integer countPropertyCheck = propertyItemRepo.countPropertyByProductId(product.getId(), ids);
			if (countPropertyCheck != ids.size()) {
				throw new RuntimeException("Property update Item not match");
			}
		} else {
			deleteAllPropertyByProductId(product.getId());
			checkProperties(propertyItems, product);
		}
		propertyItems.forEach(v -> {
			v.setProduct(product);
		});
		propertyItemRepo.saveAll(propertyItems);
	}

	public void updateVariantTier(List<VariantTier> variantTiers, List<ProductVariant> productVariants,
			Product product, boolean t) {
		Integer countVariantTier = variantTierRepo.getCountVariantTierByProductId(
				variantTiers.stream().filter((v -> v.getId() != null)).toList(), product.getId());
		Integer countIdNotNull = 0;
		for (VariantTier v : variantTiers) {
			if (v.getId() != null) {
				countIdNotNull++;
			}
		}
		if (countVariantTier == countIdNotNull) {
			countIdNotNull = 0;
			Integer countProductVariant = productVariantRepo.getCountProductVariantByProductId(
					productVariants.stream().filter((v -> v.getId() != null)).toList(),
					product.getId());
			for (ProductVariant v : productVariants) {
				if (v.getId() != null) {
					countIdNotNull++;
				}
			}
			if (countProductVariant == countIdNotNull) {
				checkVariant(variantTiers, productVariants, product);
				variantTierRepo.saveAll(variantTiers);
				productVariantRepo.saveAll(productVariants);
				variantTierRepo.deleteVariantTierNotInList(variantTiers);
				productVariantRepo.deleteVariantTierNotInList(productVariants);
			} else {
				throw new ConstraintException("productVariants", "productVariants update not valid");
			}
		} else {
			throw new ConstraintException("variantTiers", "Variant update not valid");
		}
	}

	public void updateProduct(Product product) {
		productRepo.save(product);
	}

	public void deleteAllPropertyByProductId(Integer productId) {
		propertyItemRepo.deleteAllPropertyByProductId(productId);
	}

	public void checkProperties(List<PropertyItem> propertyItems, Product p) {
		List<Integer> propertyIds = propertyItems.stream()
				.map(item -> item.getProperty().getId())
				.collect(Collectors.toList());
		List<Property> properties = propertyRepo.getPropertyInList(propertyIds);
		Map<Integer, String> propertyMap = properties.stream()
				.collect(Collectors.toMap(Property::getId, Property::getHashValue));
		propertyItems.forEach(v -> {
			String value = propertyMap.get(v.getProperty().getId());
			if (value != null && value.contains("-" + v.getPropertyValue().getId() + "-")) {
				v.setProduct(p);
			} else {
				throw new IntegrationException("Property and value not match");
			}
		});
	}

	public void checkBrand(Integer id) throws ConstraintException {
		if (brandRepo.findById(id).orElse(null) == null) {
			throw new ConstraintException("brand", "Brand your choose is not valid");
		}
	}

	public void saveProduct(Product product) {
		productRepo.save(product);
		propertyItemRepo.saveAll(product.getProperties());
		variantTierRepo.saveAll(product.getVariantTiers());
		productVariantRepo.saveAll(product.getProductVariants());
		product.getProductImages().forEach(v -> v.setProduct(product));
		productImagesRepo.saveAll(product.getProductImages());
	}

	public void checkCategory(Integer id) throws ConstraintException {
		if (categoryRepo.findById(id).orElse(null) == null) {
			throw new ConstraintException("category", "Category your choose is not valid");
		}
	}

	public void checkVariant(List<VariantTier> variantTiers, List<ProductVariant> productVariant, Product product)
			throws ConstraintException {
		if (variantTiers.size() == 1) {
			if (productVariant.get(0).getIsDefault() == 1) {
				productVariant.get(0).setVariantName("");
				productVariant.get(0).setIndex("0-0");
				return;
			}
			generateVariantSingle(variantTiers.get(0).getValueList().split("-"), productVariant);
		} else {
			generateVariantNotSingle(variantTiers.get(0).getValueList().split("-"),
					variantTiers.get(1).getValueList().split("-"), productVariant);
		}
		productVariant.forEach(v -> {
			v.setProduct(product);
		});
		variantTiers.forEach(v -> {
			v.setProduct(product);
		});
	}

	public void generateVariantSingle(String[] variantValues, List<ProductVariant> productVariants)
			throws ConstraintException {
		ProductVariant p;

		if (variantValues.length == productVariants.size()) {
			for (int i = 0; i < variantValues.length; i++) {
				p = productVariants.get(i);
				p.setVariantName(variantValues[i]);
				p.setIndex(i + "-" + 0);
			}
		} else {
			throw new ConstraintException("variantiers", "Not match variantTier with productVariant");
		}
	}

	public void generateVariantNotSingle(String[] variantValue1, String[] variantValue2,
			List<ProductVariant> productVariants) throws ConstraintException {

		ProductVariant p;
		int index = variantValue2.length;
		if (variantValue1.length * variantValue2.length == productVariants.size()) {
			for (int i = 0; i < variantValue1.length; i++) {
				for (int k = 0; k < variantValue2.length; k++) {
					p = productVariants.get(i * index + k);
					p.setVariantName(variantValue1[i] + "-" + variantValue2[k]);
					p.setIndex(i + "-" + k);
				}
			}
		} else {
			throw new ConstraintException("variantiers", "Not match variantTier with productVariant");
		}
	}

	public void generatevariant(String[] variantValue1, String[] variantValue2, List<ProductVariant> productVariants,
			String format) {
		ProductVariant p;
		if (productVariants.size() == 1 && productVariants.get(0).getIsDefault() == 1) {
			productVariants.get(0).setVariantName("");
			productVariants.get(0).setIndex("0-0");
			return;
		}
		Integer lenght = variantValue2.length;
		for (int i = 0; i < variantValue1.length; i++) {
			for (int k = 0; k < variantValue2.length; k++) {
				p = productVariants.get(i * lenght + k);
				p.setIndex(i + "-" + k);
				p.setVariantName(variantValue1[i] + "" + (format + variantValue2[k]));
			}
		}
	}

}
