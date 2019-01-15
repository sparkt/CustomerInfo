package com.wudi.model.admin;

import com.jfinal.plugin.activerecord.Model;

public class WXUntilsModel extends Model<WXUntilsModel>{

	public String getId() {
		return get("id");
	}

	public void setId(String id) {
		set("id", id);
	}

	public String getName() {
		return get("name");
	}

	public void setName(String name) {
		set("name", name);
	}

	public String getSex() {
		return get("sex");
	}

	public void setSex(String sex) {
		set("sex", sex);
	}

	public void setTel_no(String tel_no) {
		set("tel_no", tel_no);
	}

	public String getTel_no() {
		return get("tel_no");
	}

	public void setDisclose(String disclose) {
		set("disclose", disclose);
	}

	public String getDisclose() {
		return get("disclose");
	}

	public void setAge(String age) {
		set("age", age);
	}

	public String getnNation() {
		return get("nation");
	}
	public void setNation(String nation) {
		set("nation",nation);
	}
	
	public int getAge() {
		return get("age");
	}
	public void setWork_address(String work_address) {
		set("work_address",work_address);
	}
	public String getWork_address() {
		return get("work_address");
	}
	public void setComments(String comments) {
		set("comments",comments);
	}
	public String getComments() {
		return get("comments");
	}
	public void setPhone_no(String phone_no) {
		set("phone_no",phone_no);
	}
	public String getPhone_no() {
		return get("phone_no");
	}
	
	
}
