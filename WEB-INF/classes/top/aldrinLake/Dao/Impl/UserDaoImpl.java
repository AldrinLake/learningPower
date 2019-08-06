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
				u.setLast_grades(res.getInt(4));//�ϴ��ύ�ɼ�
				u.setGrades(res.getInt(5));//�����ύ�ɼ�
				u.setLast_submit_time(res.getString(6));//�ϴ��ύʱ��
				u.setSubmit_time(res.getString(7));//�ύʱ��
				u.setNumber_of_failed(res.getInt(8));//�������������δ�ύ�Ĵ���
				u.setSubmit_state(res.getString(9));//�����ύ״̬
				u.setQualifiedOrNot(res.getInt(10));//�ɼ��Ƿ�ϸ�
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
		int last_grades = 0;//��ʼ������֮ǰ�ĳɼ�
		try {
			Connection conn = conJDBC.getcon();
			String SQL = "SELECT * FROM users WHERE iduser = ?";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, user.getIduser());
			ResultSet res = pstmt.executeQuery();
			if(res.next()) {		
				last_grades = res.getInt("last_grades");//����֮ǰ�ĳɼ�
			}
			
		}finally {
			
		}
		try {
			Connection conn2 = conJDBC.getcon();
			String SQL="";
			if(user.getGrades()-last_grades>=30) {//�����������30�֣�������qualifiedOrNotΪ1 ����ϸ�
				SQL = "UPDATE users SET grades=? ,submit_state=1,qualifiedOrNot=1 WHERE iduser=?";
			}else {//����Ϊ�ύ ���ǲ��ϸ�
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
						u.setLast_grades(res.getInt(4));//�ϴ��ύ�ɼ�
						u.setGrades(res.getInt(5));//�����ύ�ɼ�
						u.setLast_submit_time(res.getString(6));//�ϴ��ύʱ��
						u.setSubmit_time(res.getString(7));//�ύʱ��
						u.setNumber_of_failed(res.getInt(8));//�������������δ�ύ�Ĵ���
						u.setSubmit_state(res.getString(9));//�����ύ״̬
						u.setQualifiedOrNot(res.getInt(10));//�ɼ��Ƿ�ϸ�
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
		//�����û��Ĳ�������
		this.setNumberOfFailed();
		try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}
		//ÿ������ύ״̬������ʱ����ھ�ʱ�䣬���³ɼ����ھɳɼ������ı��ύ״̬Ϊ0���ı䵱�պϸ�״̬Ϊ0��
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
					str = str.substring(0, str.length()-1);//ɾ�����һ������
			        
				}finally {

				}
		return str;
	}

	@Override
	public void putPeopleUnQualifiedInTable() throws SQLException {
		// TODO Auto-generated method stub
		//////////////////////////ǰһ�������///////////////////////////
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        Date start = c.getTime();
        String qyt= format.format(start);//ǰһ��
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
			SQL2=SQL2.substring(0, SQL2.length()-1);//ɾ�����һ������
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
		//��ҳ����δ�����Ա����Ϣ����,��json�ַ����ĸ�ʽ����
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
		//��ȡ�����˵���ĳɼ���Ϣ���������ϴη�������η�����
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
		//��ȡ�����û��Ĳ�����Ϣ���������ϴ��ύ�ɼ��������ύ�ɼ������������excel		
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