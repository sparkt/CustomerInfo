package com.wudi.model.admin;

import java.sql.SQLException;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wudi.util.StringUtil;
/**
 * 
 * @author 王驰
 *
 */
public class ForeignLanguageModel extends Model<ForeignLanguageModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "ForeignLanguage";
	public static final ForeignLanguageModel dao = new ForeignLanguageModel();

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
	/**
	 * 分页查询显示，就是查找
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param key
	 * @return
	 */
	public static Page<ForeignLanguageModel> getList(int pageNumber, int pageSize, String key) {
		String sele_sql = "select * ";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append("from ").append(tableName);
		if (!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append(" where name like '%" + key + "%'");
		}
		return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
	}

	/**
	 * 根据id查找
	 * 
	 * @param no
	 * @return
	 */
	public static ForeignLanguageModel getById(String id) {
		
		return dao.findFirst("select * from " + tableName + " where id = ? ", id);
	}

	/**
	 * 
	 * @Title: save @Description:保存，这里是以分别参数传下来的，还可以用对象的信息传下来 @param @param
	 *         no @param @param name @param @param cls @param @param
	 *         sex @param @return 参数 @return boolean 返回类型 @throws
	 */
	public static boolean save(String id, String name, String sex, String tel_no, String disclose,
			String age,String work_address,String comments,String phone_no,String nation) {
		ForeignLanguageModel model = new ForeignLanguageModel();
		model.setId(StringUtil.getId());
		model.setName(name);
		model.setAge(age);
		model.setNation(nation);
		model.setComments(comments);
		model.setDisclose(disclose);
		model.setSex(sex);
		model.setTel_no(tel_no);
		model.setWork_address(work_address);
		model.setPhone_no(phone_no);
		return model.save();
	}

	

	/**
	 * 更新
	 */
	public static boolean update(String id, String name, String sex, String tel_no, String disclose,
			String age,String work_address,String comments,String phone_no,String nation) {
		ForeignLanguageModel model = ForeignLanguageModel.getById(id);
		if (model == null) {
			return false;
		}
		model.setId(StringUtil.getId());
		model.setName(name);
		model.setAge(age);
		model.setComments(comments);
		model.setDisclose(disclose);
		model.setSex(sex);
		model.setTel_no(tel_no);
		model.setNation(nation);
		model.setWork_address(work_address);
		model.setPhone_no(phone_no);
		try {
			model.update();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 根据ID删除数据
	 * 
	 * @param no
	 * @return
	 */
	public static boolean delForeignLanguageById(String id) {
		try {
			String delsql = "DELETE FROM " + tableName + " WHERE id=?";
			int iRet = Db.update(delsql, id);
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

	 public static List<ForeignLanguageModel> findModelbyPhone_no(String phone_no) {
	    	String sql="select * from "+tableName+" where phone_no = ?";
	    	List<ForeignLanguageModel> list= dao.find(sql,phone_no);
	    	return list;
	    }
	 public static List<ForeignLanguageModel> getListAll() {
			StringBuffer sql=new StringBuffer();
			sql.append("select *  from ").append(tableName);
			return dao.find(sql.toString());
		}
}
