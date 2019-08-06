package top.aldrinLake.Servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import top.aldrinLake.Service.Impl.UserServeImpl;
/**
 * 
 * @author XuFankang
 * @Time:Jul 18, 2019
 * TODO��ʱִ������ÿ���賿ִ�У�
 *
 */
@WebServlet(value={"/RegularTimeDeal"},loadOnStartup=1)
public class RegularTimeDeal extends HttpServlet  {
	/**
	 * 
	 */
	public static HttpServletRequest request;//excel���ϴ����������õ�·������Ķ���
	private static final long serialVersionUID = -617410660736717093L;
	//ʱ����(һ��)  
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
	@Override
	public void init() throws ServletException {
		System.out.println("��ʼ�ж�");
		Calendar calendar = Calendar.getInstance();  
        calendar.set(Calendar.HOUR_OF_DAY, 0); //�賿0��0��1��ִ��  
        calendar.set(Calendar.MINUTE, 0);  
        calendar.set(Calendar.SECOND, 1);  
        Date date=calendar.getTime(); //��һ��ִ�ж�ʱ�����ʱ��  
        //�����һ��ִ�ж�ʱ�����ʱ�� С�ڵ�ǰ��ʱ��  
        //��ʱҪ�� ��һ��ִ�ж�ʱ�����ʱ���һ�죬�Ա���������¸�ʱ���ִ�С��������һ�죬���������ִ�С�  
        if (date.before(new Date())) {  
            date = this.addDay(date, 1);  
        }  
        Timer timer = new Timer();  
        MyTask task = new MyTask();  
        //����ָ����������ָ����ʱ�俪ʼ�����ظ��Ĺ̶��ӳ�ִ�С�  
        timer.schedule(task,date,PERIOD_DAY);   
}  
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse arg1) throws ServletException, IOException {
		System.out.println("===Servlet����===");
	}
	// ���ӻ��������  
    public Date addDay(Date date, int num) {  
        Calendar startDT = Calendar.getInstance();  
        startDT.setTime(date);  
        startDT.add(Calendar.DAY_OF_MONTH, num);  
        return startDT.getTime();  
    }  

}
/**
 * ����������������һ����ʱ����
 * @author xfk
 * �Զ��嶨ʱ���񣬼̳�TimerTask
 * 
 */
class MyTask extends TimerTask{
    //��run�����е������Ƕ�ʱ����ִ��ʱ���е���䡣
	public static Log  log = LogFactory.getLog(MyTask.class);

	
    public void run() {
        System.out.println("Hello!! ���տ��� �����ǣ�"+new Date());
        
        UserServeImpl usi = new UserServeImpl();
        /*****��ǰһ���������������excel**********/
	    usi.putTodayInfoInExcel();
		/******************************************/
        try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {e.printStackTrace();}//��ͣ���룬����������Ϣһ��
        /****��ǰһ��δ�����˷Ž���һ�����ݱ�*****/
        usi.putPeopleUnQualifiedInTable();
        /*****��ʼ�����ʼ�****/
        usi.sendEmail_menUnqualified("1471600276@qq.com","һ֧��");//�����ʼ������췲����
        try {TimeUnit.SECONDS.sleep(8);} catch (InterruptedException e) {e.printStackTrace();}//��ͣ���룬��ֹ���䱻��
        //usi.sendEmail_menUnqualified("1550872304@qq.com","��֧��");//�����ʼ�������ΰ��
        //try {TimeUnit.SECONDS.sleep(8);} catch (InterruptedException e) {e.printStackTrace();}//��ͣ���룬��ֹ���䱻��
        usi.sendEmail_menUnqualified("1307278401@qq.com","һ֧��");//�����ʼ�����Ϳ��ܿ��
        //try {TimeUnit.SECONDS.sleep(8);} catch (InterruptedException e) {e.printStackTrace();}//��ͣ���룬��ֹ���䱻��
        //usi.sendEmail_menUnqualified("1577355947@qq.com","��֧��");//�����ʼ������쳿��
        /****�����ʼ�����****/
        usi.updateUserState();//���£�ˢ�£��û�״̬
    }

}
