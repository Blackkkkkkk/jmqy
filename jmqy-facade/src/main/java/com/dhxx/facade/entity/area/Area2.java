package com.dhxx.facade.entity.area;

import com.dhxx.facade.entity.SysBaseEntity;

/**
 * @author hanrs
 * @date 2017/11/2
 * @description
 * 起止点的省市区
 */
public class Area2 extends SysBaseEntity {

    private String quiz1;
    private String quiz2;
    private String quiz3;
    private String quiz4;
    private String quiz5;
    private String quiz6;
    private String startProvince;
    private String startCity;
    private String startArea;
    private String endProvince;
    private String endCity;
    private String endArea;
    
    public String getStartArea() {
		return startProvince+startCity+startArea;
	}
    
    public String getEndArea() {
		return endProvince+endCity+endArea;
	}
    
	public String getQuiz1() {
		return quiz1;
	}
	public void setQuiz1(String quiz1) {
		this.quiz1 = quiz1;
	}
	public String getQuiz2() {
		return quiz2;
	}
	public void setQuiz2(String quiz2) {
		this.quiz2 = quiz2;
	}
	public String getQuiz3() {
		return quiz3;
	}
	public void setQuiz3(String quiz3) {
		this.quiz3 = quiz3;
	}
	public String getQuiz4() {
		return quiz4;
	}
	public void setQuiz4(String quiz4) {
		this.quiz4 = quiz4;
	}
	public String getQuiz5() {
		return quiz5;
	}
	public void setQuiz5(String quiz5) {
		this.quiz5 = quiz5;
	}
	public String getQuiz6() {
		return quiz6;
	}
	public void setQuiz6(String quiz6) {
		this.quiz6 = quiz6;
	}

	public String getStartProvince() {
		return startProvince;
	}

	public void setStartProvince(String startProvince) {
		this.startProvince = startProvince;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getEndProvince() {
		return endProvince;
	}

	public void setEndProvince(String endProvince) {
		this.endProvince = endProvince;
	}

	public String getEndCity() {
		return endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}

	public void setStartArea(String startArea) {
		this.startArea = startArea;
	}

	public void setEndArea(String endArea) {
		this.endArea = endArea;
	}

}
