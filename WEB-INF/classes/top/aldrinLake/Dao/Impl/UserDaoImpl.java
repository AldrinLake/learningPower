package top.aldrinLake.Dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import top.aldrinLake.Dao.UserDao;
import top.aldrinLake.Model.user;
import utils.ResultSetToString;
import utils.conJDBC;
public class UserDaoImpl implements UserDao{

	@Override
	public user findUser(user user) throws SQLException {
		// TODO Auto-generated method stub
		try {
			Connection conn = conJDBC.getcon();
			String SQL = "SELECT * FROM users WHERE name = ? AND pwd = ?";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPwd());
			ResultSet res = pstmt.executeQuery();
			user u =null;
			u=new user();
			if(res.next()) {
				
				u.setIduser(res.getInt(1));
				u.setName(res.getString(2));
				u.setPwd(res.getString(3));
				u.setLast_grades(res.getInt(4));//上次提交成绩
				u.setGrades(res.getInt(5));//本次提交成绩
				u.setLast_submit_time(res.getString(6));//上次提交时间
				u.setSubmit_time(res.getString(7));//提交时间
				u.setNumber_of_failed(res.getInt(8));//分数不及格或者未提交的次数
				u.setSubmit_state(res.getString(9));//今日提交状态
				u.setQualifiedOrNot(res.getInt(10));//成绩是否合格
				return u;
			}else {
				return u;
			}
			
		}finally {
			
		}
	}

	@Override
	public void setScore(user user) throws SQLException {
		// TODO Auto-generated method stub
		int last_grades = 0;//初始化更新之前的成绩
		try {
			Connection conn = conJDBC.getcon();
			String SQL = "SELECT * FROM users WHERE iduser = ?";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, user.getIduser());
			ResultSet res = pstmt.executeQuery();
			if(res.next()) {		
				last_grades = res.getInt("last_grades");//更新之前的成绩
			}
			
		}finally {
			
		}
		try {
			Connection conn2 = conJDBC.getcon();
			String SQL="";
			if(user.getGrades()-last_grades>=30) {//如果进步大余30分，则设置qualifiedOrNot为1 代表合格
				SQL = "UPDATE users SET grades=? ,submit_state=1,qualifiedOrNot=1 WHERE iduser=?";
			}else {//否则为提交 但是不合格
				SQL = "UPDATE users SET grades=? ,submit_state=1,qualifiedOrNot=0 WHERE iduser=?";
			}

			PreparedStatement pstmt = conn2.prepareStatement(SQL);
			pstmt.setInt(1, user.getGrades());
			pstmt.setInt(2, user.getIduser());
			pstmt.executeUpdate();
		}finally {
			
		}
	}

	@Override
	public void setNumberOfFailed() throws SQLException {
		// TODO Auto-generated method stub
		try {
			Connection conn = conJDBC.getcon();
			String SQL ="update users inner join (select * from users where submit_state=0 or qualifiedOrNot=0) a on users.iduser=a.iduser set users.number_of_failed=users.number_of_failed+1";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();
		}finally {
			System.out.println("setNumberOfFailed ok");
		}
	}

	@Override
	public user selectUserInfo(user users) throws SQLException {
		// TODO Auto-generated method stub
				try {
					Connection conn = conJDBC.getcon();
					String SQL = "SELECT * FROM users WHERE iduser = ?";
					PreparedStatement pstmt = conn.prepareStatement(SQL);
					pstmt.setInt(1, users.getIduser());
					ResultSet res = pstmt.executeQuery();
					user u =null;
					u=new user();
					if(res.next()) {
						u.setIduser(res.getInt(1));
						u.setName(res.getString(2));
						u.setPwd(res.getString(3));
						u.setLast_grades(res.getInt(4));//上次提交成绩
						u.setGrades(res.getInt(5));//本次提交成绩
						u.setLast_submit_time(res.getString(6));//上次提交时间
						u.setSubmit_time(res.getString(7));//提交时间
						u.setNumber_of_failed(res.getInt(8));//分数不及格或者未提交的次数
						u.setSubmit_state(res.getString(9));//今日提交状态
						u.setQualifiedOrNot(res.getInt(10));//成绩是否合格
						return u;
					}else {
						return u;
					}
					
				}finally {
					
				}
				
			}

	@Override
	public void updateUserState() throws SQLException {
		// TODO Auto-generated method stub
		//更新用户的不达标次数
		this.setNumberOfFailed();
		try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}
		//每晚更新提交状态（把新时间放在旧时间，把新成绩放在旧成绩，并改变提交状态为0，改变当日合格状态为0）
		try {
			Connection conn = conJDBC.getcon();
			String SQL = "UPDATE users SET last_grades = grades,last_submit_time=submit_time,submit_state=0,qualifiedOrNot=0 WHERE iduser !=0";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();

		}finally {
		}
		
	}

	@Override
	public String selectPeopleUnQualified(String belong) throws SQLException {
		// TODO Auto-generated method stub
				String str="";
				try {
					Connection conn = conJDBC.getcon();
					String SQL = "SELECT * FROM learnforchina.users where (submit_state=0 or qualifiedOrNot = 0) and belong=?";
					PreparedStatement pstmt = conn.prepareStatement(SQL);
					pstmt.setString(1, belong);
					ResultSet res = pstmt.executeQuery();
					ArrayList<String> peopleList = new ArrayList<String>();
					while(res.next()) {
						peopleList.add(res.getString("name"));
					}
					for(int i=0;i<peopleList.size();i++) {
						str +=peopleList.get(i)+" ,";
					}
					str = str.substring(0, str.length()-1);//删除最后一个逗号
			        
				}finally {

				}
		return str;
	}

	@Override
	public void putPeopleUnQualifiedInTable() throws SQLException {
		// TODO Auto-generated method stub
		//////////////////////////前一天的日期///////////////////////////
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        Date start = c.getTime();
        String qyt= format.format(start);//前一天
        /////////////////////////////////////////////////////////////////
		try {
			Connection conn = conJDBC.getcon();
			String SQL = "SELECT * FROM learnforchina.users where submit_state=0 or qualifiedOrNot = 0;";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			ResultSet res = pstmt.executeQuery();
			String SQL2="";
			while(res.next()) {
				 SQL2 ="('"+res.getString("name")+"',"+"'"+qyt+"',"+res.getInt("submit_state")+","+res.getInt("qualifiedOrNot")+"),"+SQL2;
			}
			SQL2=SQL2.substring(0, SQL2.length()-1);//删除最后一个逗号
			SQL2 = "INSERT INTO peopleunqualified (name,time,submit_state,qualifiedOrNot) values"+SQL2;
			System.out.println(SQL2);
			Connection conn2 = conJDBC.getcon();
			PreparedStatement pstmt2 = conn2.prepareStatement(SQL2);
			pstmt2.executeUpdate();
		}finally {

		}
	}

	@Override
	public String getUnqualifedPeopleList(int page) throws SQLException {
		// TODO Auto-generated method stub
		//分页搜索未达标人员的信息名单,以json字符串的格式返回
		String str ="";
		try {
			Connection conn = conJDBC.getcon();
			String SQL = "SELECT peopleunqualified.*,users.belong FROM learnforchina.peopleunqualified join users on peopleunqualified.name = users.name  order by id desc limit ?,50";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, page*50);
			ResultSet res = pstmt.executeQuery();
			ResultSetToString rsts = new ResultSetToString(res);
			str = rsts.toString(); 	
		}finally {
		}
		return str;
	}

	@Override
	public String getMyUnQualifiedList(user user, int page) throws SQLException {
		// TODO Auto-generated method stub
		String str="";
		try {
			Connection conn = conJDBC.getcon();
			String SQL ="SELECT * FROM (SELECT peopleunqualified.*  FROM peopleunqualified left join users on peopleunqualified.name=users.name where users.iduser=?)AS A "
					+ "ORDER BY id desc limit ?,50";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, user.getIduser());
			pstmt.setInt(2, page*50);
			ResultSet res = pstmt.executeQuery();
			ResultSetToString rsts = new ResultSetToString(res);
			str = rsts.toString(); 	
			
		}finally {
			
		}
		return str;
	}

	@Override
	public String getHonorGradesList() throws SQLException {
		// TODO Auto-generated method stub
		String str="";
		try {
			Connection conn = conJDBC.getcon();
			String SQL ="SELECT iduser,name,last_grades,number_of_failed FROM learnforchina.users order by last_grades desc";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			ResultSet res = pstmt.executeQuery();
			ResultSetToString rsts = new ResultSetToString(res);
			str = rsts.toString(); 	
		}finally {	
		}
		return str;
	}

	@Override
	public boolean modifyThePwd(user u, String newPwd) throws SQLException {
		// TODO Auto-generated method stub
		try {
			Connection conn = conJDBC.getcon();
			String SQL ="update users set users.pwd=? WHERE iduser =?";
			System.out.println(SQL);
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, newPwd);
			pstmt.setInt(2,u.getIduser());
			pstmt.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}finally {
		}
	}

	@Override
	public String getAllPeopleTodayGrades() throws SQLException {
		// TODO Auto-generated method stub
		//获取所有人当天的成绩信息（姓名，上次分数，这次分数）
		String str="";
		try {
			Connection conn = conJDBC.getcon();
			String SQL ="SELECT * FROM users order by iduser";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			ResultSet res = pstmt.executeQuery();
			ResultSetToString rsts = new ResultSetToString(res);
			str = rsts.toString(); 	
		}finally {	
		}
		return str;
	}

	@Override
	public String getAllPropleTodayInfoUsedToStore() throws SQLException {
		// TODO Auto-generated method stub
		//获取所有用户的部分信息（姓名，上次提交成绩，今日提交成绩），用来存进excel		
		String str="";
		try {
			Connection conn = conJDBC.getcon();
			String SQL ="SELECT belong,name,last_grades,grades FROM users order by iduser";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			ResultSet res = pstmt.executeQuery();
			ResultSetToString rsts = new ResultSetToString(res);
			str = rsts.toString(); 	
		}finally {	
		}
		return str;
	}
}