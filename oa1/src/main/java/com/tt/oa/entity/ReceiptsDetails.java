package com.tt.oa.entity;

public class ReceiptsDetails {
    private Integer id;
    private String costType;
    private Double money;
    private String detail;
    private Integer receiptsId;

    public Integer getReceiptsId() {
        return receiptsId;
    }

    public void setReceiptsId(Integer receiptsId) {
        this.receiptsId = receiptsId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "ReceiptsDetails{" +
                "id=" + id +
                ", costType='" + costType + '\'' +
                ", money=" + money +
                ", detail='" + detail + '\'' +
                ", receiptsId=" + receiptsId +
                '}';
    }
}
