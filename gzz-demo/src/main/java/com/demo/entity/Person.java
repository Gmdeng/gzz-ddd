package com.demo.entity;

import java.util.List;

public class Person {
	private Long id;
	private Integer age;
	private String name;
	private String address;
	private List<String> hobby;
	private SexTypeEnum sex;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getHobby() {
		return hobby;
	}

	public void setHobby(List<String> hobby) {
		this.hobby = hobby;
	}

	public SexTypeEnum getSex() {
		return sex;
	}

	public void setSex(SexTypeEnum sex) {
		this.sex = sex;
	}
}
