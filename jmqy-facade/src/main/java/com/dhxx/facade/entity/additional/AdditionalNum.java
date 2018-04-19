package com.dhxx.facade.entity.additional;

import com.dhxx.facade.entity.SysBaseEntity;

public class AdditionalNum  extends SysBaseEntity {

    private  Long    id;
    private  String  companycode;   //对应的企业编码
    private  String  meals;         //餐费
    private  String  accommodation; //住宿费
    private  String  highway;       //高速路费
    private  String  insurance;     //保险费
    private  String  water;         //水费

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getMeals() {
        return meals;
    }

    public void setMeals(String meals) {
        this.meals = meals;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public String getHighway() {
        return highway;
    }

    public void setHighway(String highway) {
        this.highway = highway;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }
}
