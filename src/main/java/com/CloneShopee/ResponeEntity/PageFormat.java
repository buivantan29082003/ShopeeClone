package com.CloneShopee.ResponeEntity;

import org.springframework.data.domain.Page;

public class PageFormat {
    private Boolean isPre;
    private Boolean isNext;
    private Integer totalPage;
    private Long totalElement;
    private Boolean isEmpty;
    private Integer elementOfPage;
    private Object Data;

    public PageFormat(Page p) {
        this.setData(p.getContent());
        this.isNext = p.isLast() == false;
        this.isPre = p.isFirst() == false;
        this.totalElement = p.getTotalElements();
        this.totalPage = p.getTotalPages();
        this.isEmpty = p.isEmpty();
        this.elementOfPage = p.getNumberOfElements();
    }

    public Boolean getIsPre() {
        return isPre;
    }

    public void setIsPre(Boolean isPre) {
        this.isPre = isPre;
    }

    public Boolean getIsNext() {
        return isNext;
    }

    public void setIsNext(Boolean isNext) {
        this.isNext = isNext;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(Long totalElement) {
        this.totalElement = totalElement;
    }

    public Boolean getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(Boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public Integer getElementOfPage() {
        return elementOfPage;
    }

    public void setElementOfPage(Integer elementOfPage) {
        this.elementOfPage = elementOfPage;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }

}
