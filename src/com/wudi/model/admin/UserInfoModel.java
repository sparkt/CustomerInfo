package com.wudi.model.admin;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.util.StringUtil;

/**
 * 
* @ClassName: Userinfo
* @Description: TODO userinfo表模型
* @author zhang
* 
*
 */
public class UserInfoModel extends Model<UserInfoModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "userinfo";
	public static final UserInfoModel dao = new UserInfoModel();

	public String getId() {
		return get("id");
	}
	public void setId(String id) {
		set("id", id);
	}
	public String getUser_name() {
		return get("user_name");
	}
	public void setUser_name(String user_name) {
		set("user_name", user_name);
	}
	public String getUser_password() {
		return get("user_password");
	}
	public void setUser_password(String user_password) {
		set("user_password", user_password);
	}
	public String getUser_sex() {
		return get("user_sex");
	}
	public void setUser_sex(String user_sex) {
		set("user_sex", user_sex);
	}
	public String getPhone_no(String phone_no) {
		return get(phone_no);
	}
		public void setPhone_no(String phone_no) {
			set("phone_no", phone_no);
		}
		
		public String getType() {
			return get("type");
		}
		public void setType(String type) {
			set("type", type);
		}
	
	public String getVip_grade() {
		return get("vip_grade");
	}
	public void setVip_grade(String vip_grade) {
		set("vip_grade", vip_grade);
	}
	
	public String getStatus() {
		return get("status");
		
	}
	public void setStatus(String status) {
		set("status", status);
	}
	
	public String getGroup() {
		return get("groups");
		
	}
	public  void setGroup(String groups) {
		set("groups",groups);
		
	}
	/**
	 * 
	 * 注册用户 保存用户信息
	 * @author zhang zhiqiang
	 * @param user_name 
	 * @param user_password 
	 * @param user_sex
	 * @param phone_no
	 * @param vip_grade
	 * @param status
	 * @return
	 */
	public  boolean saveUserinfo(String user_name,String user_password, String user_sex,String phone_no,String vip_grade,String type) {
		UserInfoModel m=new UserInfoModel();
		m.setUser_name(user_name);
		m.setUser_password(user_password);
		m.setUser_sex(user_sex);
		m.setPhone_no(phone_no);
		m.setVip_grade(vip_grade);
		m.setStatus("0");//开始注册还没有审核
		m.setType(type);
		m.setGroup("0");//开始注册没有团体说以传0
		return m.save();
	}
	
	
	/*
	 * 根据用户号码修改用户信息
	 * @author zhang zhiqiang
	 * @param user_name 
	 * @param user_password 
	 * @param user_sex
	 * @param phone_no
	 * @param vip_grade
	 * @param status
	 * */
	public  boolean updataUserinfo(String user_name,String user_password, String user_sex,String phone_no,String vip_grade,String status ) {
		UserInfoModel m=dao.getphone_no(phone_no);
		if(m==null) {
			return false;
		}else {
			m.setUser_name(user_name);
			m.setUser_password(user_password);
			m.setUser_sex(user_sex);
			m.setVip_grade(vip_grade);
			m.setStatus(status);
			m.setGroup("0");
		}
		return m.update();
	
	}
	
	
	/**
	 *  用户加入团队
	 * @param captain_phone
	 * @param phone_no
	 * @return
	 */
	public boolean userJoinGroup(String captain_phone ,String phone_no) {
		GroupInfoModel m = new GroupInfoModel().getisGroup(captain_phone);
		UserInfoModel n = dao.getphone_no(phone_no);
		boolean  result =false;
		int count;
		if(m!=null) {
			count= Integer.parseInt(m.getGroup_headcount()); //获取当前团队人数
			m.setGroup_headcount(String.valueOf(count+1));//团队人数加1
			result= m.update();//更新团队人数
			n.setGroup(captain_phone); //更新用户团队字段为团队号码
			result= n.update(); //更新
			
		}
		return result;
	}
	/**
	 *  用户退队
	 * @param phone_no
	 * @return
	 */
	public boolean userQuitGroup(String phone_no) {
		UserInfoModel n = dao.getphone_no(phone_no);
		boolean  result =false;
		int count;
		
		if(n!=null && !n.getGroup().equals("0")) {
			GroupInfoModel m = new GroupInfoModel().getisGroup(n.getGroup());
			count= Integer.parseInt(m.getGroup_headcount()); //获取当前团队人数
			m.setGroup_headcount(String.valueOf(count-1));//团队人数减1
			result= m.update();//更新团队人数
			n.setGroup("0"); //更新用户团队字段为团队0
			result= n.update(); //更新
		}	
		
		return result;
	}
	/**
	 *删除队员
	 * @param captain_phone
	 * @param phone_no
	 */
	public boolean deleteMember(String captain_phone ,String phone_no){
		
		GroupInfoModel m = new GroupInfoModel().getisGroup(captain_phone);
		UserInfoModel n = dao.getphone_no(phone_no);
		boolean  result =false;
		int count;
		
		if(m!=null && n!=null) {
			count= Integer.parseInt(m.getGroup_headcount()); //获取当前团队人数
			m.setGroup_headcount(String.valueOf(count-1));//团队人数减1
			result= m.update();//更新团队人数
			n.setGroup("0"); //更新用户团队字段为团队0
			result= n.update(); //更新
		}
		return result;
	}
	
	/**
	 * 查询团队信息
	 * @param phone_no
	 * @param captain_phone
	 * @return
	 */
	public GroupInfoModel getUserGrouAllInfo(String captain_phone) {
		return GroupInfoModel.getGroupAllInfo(captain_phone);

	}	

	
	
	
	
	
	/**
	 * 根据phone_no删除用户数据数据
	 * @param phone_no
	 * @return
	 */
	public  boolean deleteUserinfo(String phone_no) {
		try {
			String delsql = "DELETE FROM " + tableName + " WHERE phone_no=?";
			int iRet = Db.update(delsql, phone_no);
			if (iRet > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * 查询号码
	 * @param phone_no
	 * @return
	 */
	public UserInfoModel getphone_no(String phone_no) {
		String selectsql = "SELECT * FROM " + tableName + " WHERE phone_no=?";
		return dao.findFirst(selectsql,phone_no);
		
	}
	/**
	 * 根据group字段查找该用户所在团队所有成员
	 * @param group
	 * @return
	 */
		public List<UserInfoModel> getGroupMemberAllInfo(String group) {
			UserInfoModel m=new UserInfoModel();
			List<UserInfoModel> list =null;
			if(!group.equals("0")) {
				String selectsql = "SELECT * FROM userinfo WHERE groups=?";
				 list = m.find(selectsql,group);
			}
			return list;
			
		}	
		
	/**
	 * 根据KEY关键字查询用户信息
	 * @param pageNumber
	 * @param pageSize
	 * @param key
	 * @return
	 */
	public  Page<UserInfoModel> getList(int pageNumber, int pageSize, String key) {
		String sele_sql = "select * ";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append("from ").append(tableName);
		if (!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append(" where user_name like '%" + key + "%'");
		}
		return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
	}
	
	
	/**
	 * 根据号码查找客户所有信息
	 * @param phone_no
	 * @return
	 */
	public List<UserInfoModel> getUserAllInfo(String phone_no) {
		UserInfoModel m=new UserInfoModel();
		String selectsql = "SELECT * FROM userinfo WHERE phone_no=?";
		List<UserInfoModel> list = m.find(selectsql,phone_no);
		
		return list;
	}	
	
}
