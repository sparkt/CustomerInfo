package com.wudi.model.admin;

import java.util.List;
import java.util.Stack;
import java.util.UUID;

import com.jfinal.plugin.activerecord.DaoContainerFactory;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.model.NavsModel;
import com.wudi.util.StringUtil;

/**
 * 
* @ClassName: AdminInfoMode
* @Description: TODO AdminInfo表模型
* @author zhangzhiqiang
* 
*
 */
public class AdminInfoModel extends Model<AdminInfoModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "AdminInfo";
	public static final AdminInfoModel dao = new AdminInfoModel();

	public String getId() {
		return get("id");
	}
	public void setId(String id) {
		set("id", id);
	}
	public String getAdmin_name() {
		return get("admin_name");
	}
	public void setAdmin_name(String admin_name) {
		set("admin_name", admin_name);
	}
	public String getAdmin_password() {
		return get("admin_password");
	}
	public void setAdmin_password(String admin_password) {
		set("admin_password", admin_password);
	}
	public String getAdmin_sex() {
		return get("admin_sex");
	}
	public void setAdmin_sex(String admin_sex) {
		set("admin_sex", admin_sex);
	}
	public String getAdmin_Phone_no(String admin_phone_no) {
		return get(admin_phone_no);
	}
		public void setAdmin_Phone_no(String admin_phone_no) {
			set("admin_phone_no", admin_phone_no);
		}
		
	public String getType() {
		return get("type");
	}
	public void setType(String type) {
		set("type", type);
	}
	
	public String getStatus(String status) {
		return get("status");
		
	}
	public void setStatus(String status) {
		set("status", status);
	}
	/**
	 * 添加管理员  保存管理员信息
	 * @author zhang zhiqiang
	 * @param admin_name 
	 * @param admin_password 
	 * @param admin_sex
	 * @param phone_no
	 * @param type
	 * @param status
	 * @return
	 */
	public  boolean saveAdminInfo(String admin_name,String admin_password, String admin_sex,String admin_phone_no,String type,String status ) {
		AdminInfoModel m=new AdminInfoModel();
		m.setAdmin_name(admin_name);
		m.setAdmin_password(admin_password);
		m.setAdmin_sex(admin_sex);
		m.setAdmin_Phone_no(admin_phone_no);
		m.setType(type);
		m.setStatus(status);
		return m.save();
	}
	
	/*
	 * 根据用户号码修改管理员信息
	 * @author zhang zhiqiang
	 * @param admin_name 
	 * @param admin_password 
	 * @param admin_sex
	 * @param admin——phone_no
	 * @param vip_grade
	 * @param status
	 * */
	public  boolean updataAdminInfo(String admin_name,String admin_password, String admin_sex,String admin_phone_no,String type,String status) {
		AdminInfoModel m=dao.getphone_no(admin_phone_no);
		if(m==null) {
			return false;
		}else {
			m.setAdmin_name(admin_name);
			m.setAdmin_password(admin_password);
			m.setAdmin_sex(admin_sex);
			m.setType(type);
			m.setStatus(status);
		}
		return m.update();
	
	}
	
	/**
	 * 根据admin_phone_no删除管理员数据
	 * @param phone_no
	 * @author zhangzhiqiang
	 * @return
	 */
	public  boolean deleteAdminInfo(String admin_phone_no) {
		try {
			String delsql = "DELETE FROM " + tableName + " WHERE admin_phone_no=?";
			int iRet = Db.update(delsql, admin_phone_no);
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
	 * 
	 * 
	 * */
	public AdminInfoModel getphone_no(String admin_phone_no) {
		AdminInfoModel m=new AdminInfoModel();
		String selectsql = "SELECT * FROM " + tableName + " WHERE admin_phone_no=?";
		return m.findFirst(selectsql,admin_phone_no);
	
}
	
	/**
	 * 分页查询显示，就是查找
	 * @param pageNumber
	 * @param pageSize
	 * @param key
	 * @return
	 */
	public Page<AdminInfoModel> getList(int pageNumber, int pageSize,String key) {
		String sele_sql="select * ";
		StringBuffer from_sql=new StringBuffer();
		from_sql.append("from ").append(tableName);
		if(!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append(" where admin_name like '%"+key+"%'");
		}
		return dao.paginate(pageNumber,pageSize,sele_sql,from_sql.toString());
	} 
	//根据号码查找所有管理员信息
	public List<AdminInfoModel> getAdminAllInfo(String phone_no) {
		AdminInfoModel m=new AdminInfoModel();
		String selectsql = "SELECT * FROM AdminInfo WHERE admin_phone_no=?";
		List<AdminInfoModel> list = m.find(selectsql,phone_no);
		return list;
	}
}

	
	
