package com.wudi.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.util.Util;

/**
 * 
 * @ClassName: NavsModel
 * @Description: TODO navs表模型
 * @author xiao
 * @date 2018年11月15日下午2:45:13
 *
 */
public class UserModel extends Model<UserModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "user";
	public static final UserModel dao = new UserModel();

	public String getId() {
		return get("id");
	}

	public void setId(String id) {
		set("id", id);
	}

	public String getusername() {
		return get("username");
	}

	public void setusername(String username) {
		set("username", username);
	}

	public String getpassword() {
		return get("password");
	}

	public void setpassword(String password) {
		set("password", password);
	}

	public int getstatus() {
		return get("status");
	}

	public void setstatus(String status) {
		set("status", status);
	}

	/**
	 * TODO:xiao 根据id查找信息
	 * 
	 * @return list
	 */
	public static UserModel getModeById(String id) {
		return dao.findById(id);
	}

	/**
	 * 查询列表 @Title: getList @Description: TODO(这里用一句话描述这个方法的作用) @param @return
	 * 参数 @return List<NavsModel> 返回类型 @throws
	 */
	public static List<UserModel> getList() {
		String sql = "select * from " + tableName + "";
		List<UserModel> list = dao.find(sql);
		return list;
	}

	public static UserModel getModeByUsername(String username) {
		String sql = "select * from " + tableName + " where username=?";
		UserModel m = dao.findFirst(sql, username);
		return m;
	}

	/**
	 * 分页查询显示，就是查找
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param key
	 * @return
	 */
	public static Page<UserModel> getList(int pageNumber, int pageSize, String key) {
		String sele_sql = "select * ";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append("from ").append(tableName);
		if (!Util.isBlankOrEmpty(key)) {
			from_sql.append(" where title like '%" + key + "%'");
		}
		return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
	}

}
