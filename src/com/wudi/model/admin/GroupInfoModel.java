package com.wudi.model.admin;
import java.util.List;
import java.util.Stack;
import java.util.UUID;
import com.jfinal.plugin.activerecord.DaoContainerFactory;
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
public class GroupInfoModel extends Model<GroupInfoModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "GroupInfo";
	public static final  GroupInfoModel dao = new  GroupInfoModel();
	
	
	public String getId() {
		return get("id");
	}
	public void setId(String id) {
		set("id", id);
	}
	public String getGroup_name() {
		return get("group_name");
	}
	public void setGroup_name(String group_name) {
		set("group_name", group_name);
	}
	public String getCaptain_name() {
		return get("captain_name");
	}
	public void setCaptain_name(String captain_name) {
		set("captain_name", captain_name);
	}
	public String getCaptain_phone() {
		return get("captain_phone");
	}
	public void setCaptain_phone(String captain_phone) {
		set("captain_phone", captain_phone);
	}
	public String getGroup_info(String group_info) {
		return get(group_info);
	}
		public void setGroup_info(String group_info) {
			set("group_info", group_info);
		}
		
		public String getGroup_headcount() {
			return get("group_headcount");
		}
		public void setGroup_headcount(String group_headcount) {
			set("group_headcount", group_headcount);
		}
	
		/**
		 * 添加团队
		 * @author zhangzhiqiang
		 * @return
		 */
		public  boolean saveGroupinfo(String group_name,String captain_name, String captain_phone,String group_info) {
			GroupInfoModel m=new GroupInfoModel();
			m.setGroup_name(group_name);
			m.setCaptain_name(captain_name);
			m.setCaptain_phone(captain_phone);
			m.setGroup_info(group_info);
			m.setGroup_headcount("1");//刚创建团对团对人数初始为1
			return m.save();
		}
		/**
		 * 根据队长phone_no删除团队数据数据
		 * @param phone_no
		 * @author zhangzhiqiang
		 * @return
		 */
		public  boolean deleteGroupinfo(String phone_no) {
			try {
				String delsql = "DELETE FROM " + tableName + " WHERE captain_phone=?";
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
		 * 根据队长号码查询团队是否存在
		 * @param phone_no
		 * 
		 * 
		 * */
		public GroupInfoModel getisGroup(String phone_no) {
			GroupInfoModel m=new GroupInfoModel();
			String selectsql = "SELECT * FROM " + tableName + " WHERE captain_phone=?";
			return m.findFirst(selectsql,phone_no);
			
		}
		
		//根据队长号码返回团队信息
		public List<GroupInfoModel> getGroupAllInfo(String phone_no) {
			GroupInfoModel m=new GroupInfoModel();
			String selectsql = "SELECT * FROM GroupInfo WHERE captain_phone=?";
			List<GroupInfoModel> list = m.find(selectsql,phone_no);
			return list;
		}	
		
		
		/*
		 * 根据关键字key分页查询团队信息
		 * 
		 * */
		public  Page<GroupInfoModel> getList(int pageNumber, int pageSize, String key) {
			String sele_sql = "select * ";
			StringBuffer from_sql = new StringBuffer();
			from_sql.append("from ").append(tableName);
			if (!StringUtil.isBlankOrEmpty(key)) {
				from_sql.append(" where captain_phone like '%" + key + "%'");
			}
			return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
		}
}
	