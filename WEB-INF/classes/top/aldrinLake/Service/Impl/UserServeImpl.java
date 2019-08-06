package top.aldrinLake.Service.Impl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import top.aldrinLake.Dao.Impl.UserDaoImpl;
import top.aldrinLake.Model.user;
import top.aldrinLake.Service.UserServe;
import utils.StoreInExcel;
import utils.sendEmail;
public class UserServeImpl implements UserServe{

	@Override
	public user checkLogin(user u) {
		// TODO Auto-generated method stub
		UserDaoImpl udi = new UserDaoImpl();
		try {
			u = udi.findUser(u);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("登录检测报错");
		}
		return u;
	}

	@Override
	public void setScore(user u) {
		// TODO Auto-generated method stub
		UserDaoImpl udi = new UserDaoImpl();
		try {
			udi.setScore(u);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("更新成绩报错");
		}
	}

	@Override
	public void setNumberOfFailed() {
		// TODO Auto-generated method stub
		UserDaoImpl udi = new UserDaoImpl();
		try {
			udi.setNumberOfFailed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("更新“未提交/不达标”次数失败");
		}
	}
	@Override
	public user selectUserInfo(user u) {
		UserDaoImpl udi = new UserDaoImpl();
		try {
			u = udi.selectUserInfo(u);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(" 根据用户ID 检索用户信息失败");
		}
		
		return u;
		
	}

	@Override
	public void updateUserState() {
		// TODO Auto-generated method stub
		UserDaoImpl udi = new UserDaoImpl();
		try {
			udi.updateUserState();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public String selectPeopleUnQualified(String belong) {
		// TODO Auto-generated method stub
		UserDaoImpl udi = new UserDaoImpl();
		String peopleList="";
		try {
			 peopleList = udi.selectPeopleUnQualified(belong);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return peopleList;
	}

	@Override
	public void putPeopleUnQualifiedInTable() {
		// TODO Auto-generated method stub
		UserDaoImpl udi = new UserDaoImpl();
		try {
			udi.putPeopleUnQualifiedInTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getUnqualifedPeopleList(int page) {
		// TODO Auto-generated method stub
		UserDaoImpl udi = new UserDaoImpl();
		String str="";
		try {
			str = udi.getUnqualifedPeopleList(page);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public String getMyUnQualifiedList(user user, int page) {
		// TODO Auto-generated method stub
		UserDaoImpl udi = new UserDaoImpl();
		String str="";
		try {
			str = udi.getMyUnQualifiedList(user, page);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public String getHonorGradesList() {
		// TODO Auto-generated method stub
		UserDaoImpl udi = new UserDaoImpl();
		String str="";
		try {
			str = udi.getHonorGradesList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public boolean modifyThePwd(user u, String oldPwd, String newPwd) {
		// TODO Auto-generated method stub
		UserDaoImpl udi = new UserDaoImpl();
		u = this.selectUserInfo(u);

		try {
			if(u.getPwd().equals(oldPwd)) {
				//旧密码核对正确
				
				if(udi.modifyThePwd(u, newPwd)) {//修改成功返回true
					return true;
				}else {
					return false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public void sendEmail_menUnqualified(String emailAddress,String belong) {
		// TODO Auto-generated method stub
		//////////////////////////前一天的日期///////////////////////////
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, -1);
		Date start = c.getTime();
		String qyt= format.format(start);//前一天
		/////////////////////////////////////////////////////////////////
		sendEmail email= new sendEmail(emailAddress, qyt+"情况汇报", belong+"未达标人员："+this.selectPeopleUnQualified(belong));
		email.send();
	}

	@Override
	public String getAllPeopleTodayGrades() {
		// TODO Auto-generated method stub
		//获取所有人当天的成绩信息（姓名，上次分数，这次分数）并且把情况单独存进一张excel表
		UserDaoImpl udi = new UserDaoImpl();
		String str = "";
		try {
			str = udi.getAllPeopleTodayGrades();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public void putTodayInfoInExcel(){
		// TODO Auto-generated method stub
		/*************************前一天的日期*****************************/
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	 	Calendar c = Calendar.getInstance();
	 	c.setTime(new Date());
	 	c.add(Calendar.DATE, -1);
	 	Date start = c.getTime();
	 	String qyt= format.format(start);//前一天
	 	/******************************************************************/
		UserDaoImpl udi = new UserDaoImpl();
		String jsonStr="";
		try {
			jsonStr = udi.getAllPropleTodayInfoUsedToStore();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			//此java文件的绝对路径
			//String path = Thread.currentThread().getContextClassLoader().getResource(".").getPath()+"\\excelStore";//这里定义图片存放位置路径，注意这里的uploadimage是自己定义的一个文件夹
			//项目的绝对路径
			String path = getClass().getResource("/").getFile().toString();
			path= path.substring(0, path.length()-16)+"//excelStore";
			File dir = new File(path);//创建该路径
			System.out.println(path);
	        if (!dir.exists()) {
	            dir.mkdir();
	        }
			String FilePath=path+"//"+qyt+"-submit-situation.xls";
			System.out.println(FilePath);
			StoreInExcel.store(FilePath, jsonStr);//文件路径qyt表示前一天
			ExcelServeImpl esi = new ExcelServeImpl();
			esi.storeFilePath(qyt+"-submit-situation.xls");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	


}
