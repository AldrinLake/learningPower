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
			System.out.println("��¼��ⱨ��");
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
			System.out.println("���³ɼ�����");
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
			System.out.println("���¡�δ�ύ/����ꡱ����ʧ��");
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
			System.out.println(" �����û�ID �����û���Ϣʧ��");
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
				//������˶���ȷ
				
				if(udi.modifyThePwd(u, newPwd)) {//�޸ĳɹ�����true
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
		//////////////////////////ǰһ�������///////////////////////////
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, -1);
		Date start = c.getTime();
		String qyt= format.format(start);//ǰһ��
		/////////////////////////////////////////////////////////////////
		sendEmail email= new sendEmail(emailAddress, qyt+"����㱨", belong+"δ�����Ա��"+this.selectPeopleUnQualified(belong));
		email.send();
	}

	@Override
	public String getAllPeopleTodayGrades() {
		// TODO Auto-generated method stub
		//��ȡ�����˵���ĳɼ���Ϣ���������ϴη�������η��������Ұ�����������һ��excel��
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
		/*************************ǰһ�������*****************************/
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	 	Calendar c = Calendar.getInstance();
	 	c.setTime(new Date());
	 	c.add(Calendar.DATE, -1);
	 	Date start = c.getTime();
	 	String qyt= format.format(start);//ǰһ��
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
			//��java�ļ��ľ���·��
			//String path = Thread.currentThread().getContextClassLoader().getResource(".").getPath()+"\\excelStore";//���ﶨ��ͼƬ���λ��·����ע�������uploadimage���Լ������һ���ļ���
			//��Ŀ�ľ���·��
			String path = getClass().getResource("/").getFile().toString();
			path= path.substring(0, path.length()-16)+"//excelStore";
			File dir = new File(path);//������·��
			System.out.println(path);
	        if (!dir.exists()) {
	            dir.mkdir();
	        }
			String FilePath=path+"//"+qyt+"-submit-situation.xls";
			System.out.println(FilePath);
			StoreInExcel.store(FilePath, jsonStr);//�ļ�·��qyt��ʾǰһ��
			ExcelServeImpl esi = new ExcelServeImpl();
			esi.storeFilePath(qyt+"-submit-situation.xls");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	


}
