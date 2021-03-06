package com.wudi.model.admin;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

 public class InformModel extends Model<InformModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public static final String tableName = "inform";
	public static final InformModel dao = new InformModel();
	
	public String getId() {
		return get("id");
	}
	public String gettime() {
		return get("time");
	}
	public void settime(String time) {
		set("time", time);
	}
	public String getinfo() {
		return get("info");
	}
	public void setinfo(String info) {
		set("info",info);
	}
	public String getphone_no() {
		return get("phone_no");
	}
	public void setphone_no(String phone_no) {
		set("phone_no", phone_no);
	}
	
	/**
	 * 查找号码
	 * @param phone_no
	 * @return
	 */
	public static InformModel getphone_no(String phone_no) {
		String selectsql = "SELECT * FROM " + tableName + " WHERE phone_no=?";
		return dao.findFirst(selectsql,phone_no);
		
	}
	/**
	 * 查找号码
	 * @param phone_no
	 * @return
	 */
	public static List<InformModel> getListByphone_no(String phone_no) {
		String selectsql = "SELECT * FROM " + tableName + " WHERE phone_no=?";
		return dao.find(selectsql,phone_no);
		
	}
	public static InformModel getByphone_no(String phone_no) {
		String selectsql = "SELECT * FROM " + tableName + " WHERE phone_no=?";
		return dao.findFirst(selectsql,phone_no);
		
	}
	/**
	 * 保存通知信息
	 * @param info
	 * @param phone_no
	 * @param time
	 * @return
	 */
	public boolean circularize( String info, String phone_no ,String time) {
		boolean result =false;
		InformModel save = new InformModel();
		
		InformModel m = InformModel.getphone_no(phone_no);
		//如果信息存在及更新，不存在即保存
		if(m!=null){
			m.setphone_no(phone_no);
			m.setinfo(info);
			m.settime(time);
			result = m.update();
		}else {
			save.setphone_no(phone_no);
			save.setinfo(info);
			save.settime(time);
			result = save.save();
		}
		
		return result;
	}
	
	
 }
