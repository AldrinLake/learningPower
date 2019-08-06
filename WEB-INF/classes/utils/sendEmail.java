package utils;
 
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Transport;
import java.util.Date;
import java.util.Properties;
import java.text.SimpleDateFormat;
 
/**
 * ���������
 * <B>�� Ŀ��ǿ������</B> 
 * <B>����֧�֣�</B>�췲�� (c) 2018
 * 
 * @version 1.0 2019��7��16��
 * @author  AldrinLake
 * @since JDK1.8
 */
public class sendEmail {
	
    // �����˵� ���� �� ���루�滻Ϊ�Լ�����������룩
    // PS: ĳЩ���������Ϊ���������䱾������İ�ȫ�ԣ��� SMTP �ͻ��������˶������루�е������Ϊ����Ȩ�롱��, 
    //     ���ڿ����˶������������, ����������������ʹ������������루��Ȩ�룩��
    public static String myEmailAccount = "1175881875@qq.com";
    public static String myEmailPassword = "fotnqidxpztvgefa";//��Ȩ��
 
    // ����������� SMTP ��������ַ, ����׼ȷ, ��ͬ�ʼ���������ַ��ͬ, һ��(ֻ��һ��, ���Ǿ���)��ʽΪ: smtp.xxx.com
    // QQ����� SMTP ��������ַΪ: smtp.qq.com
    public static String myEmailSMTPHost = "smtp.qq.com";

    // �ռ������䣨�滻Ϊ�Լ�֪������Ч���䣩
    public static String receiveMailAccount="";
    		//"1175881875@qq.com";
    //������Ϣ�ı���
    public static String title;
    //���͵���Ϣ����
    public static String messages="";
	
    //���캯��
	public  sendEmail(String receiveMailAccount,String title ,String message){
		sendEmail.receiveMailAccount=receiveMailAccount;//ʹ����static����ʹ��thisָ��
		sendEmail.messages=message;//ʹ����static����ʹ��thisָ��
		sendEmail.title=title;
		//send();
	}
	
	
	public void send(){
		try{
			// 1. ������������, ���������ʼ��������Ĳ�������
	        Properties props = new Properties();                    // ��������
	        props.setProperty("mail.transport.protocol", "smtp");   // ʹ�õ�Э�飨JavaMail�淶Ҫ��
	        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // �����˵������ SMTP ��������ַ
	        props.setProperty("mail.smtp.auth", "true");            // ��Ҫ������֤
 
	        // PS: ĳЩ���������Ҫ�� SMTP ������Ҫʹ�� SSL ��ȫ��֤ (Ϊ����߰�ȫ��, ����֧��SSL����, Ҳ�����Լ�����),
	        //     ����޷������ʼ�������, ��ϸ�鿴����̨��ӡ�� log, ����������� ������ʧ��, Ҫ�� SSL ��ȫ���ӡ� �ȴ���,
	        //     ������ /* ... */ ֮���ע�ʹ���, ���� SSL ��ȫ���ӡ�
	        
	        // SMTP �������Ķ˿� (�� SSL ���ӵĶ˿�һ��Ĭ��Ϊ 25, ���Բ����, ��������� SSL ����,
	        //                  ��Ҫ��Ϊ��Ӧ����� SMTP �������Ķ˿�, ����ɲ鿴��Ӧ�������İ���,
	        //                  QQ�����SMTP(SLL)�˿�Ϊ465��587, ������������ȥ�鿴)
	        final String smtpPort = "465";
	        props.setProperty("mail.smtp.port", smtpPort);
	        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.setProperty("mail.smtp.socketFactory.fallback", "false");
	        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
	        
 
	        // 2. �������ô����Ự����, ���ں��ʼ�����������
	        Session session = Session.getInstance(props);
	        session.setDebug(true);                                 // ����Ϊdebugģʽ, ���Բ鿴��ϸ�ķ��� log
 
	        // 3. ����һ���ʼ�
	        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount);
 
	        // 4. ���� Session ��ȡ�ʼ��������
	        Transport transport = session.getTransport();
 
	        // 5. ʹ�� �����˺� �� ���� �����ʼ�������, ������֤����������� message �еķ���������һ��, ���򱨴�
	        // 
	        //    PS_01: �ɰܵ��жϹؼ��ڴ�һ��, ������ӷ�����ʧ��, �����ڿ���̨�����Ӧʧ��ԭ��� log,
	        //           ��ϸ�鿴ʧ��ԭ��, ��Щ����������᷵�ش������鿴�������͵�����, ���ݸ����Ĵ���
	        //           ���͵���Ӧ�ʼ��������İ�����վ�ϲ鿴����ʧ��ԭ��
	        //
	        //    PS_02: ����ʧ�ܵ�ԭ��ͨ��Ϊ���¼���, ��ϸ������:
	        //           (1) ����û�п��� SMTP ����;
	        //           (2) �����������, ����ĳЩ���俪���˶�������;
	        //           (3) ���������Ҫ�����Ҫʹ�� SSL ��ȫ����;
	        //           (4) �������Ƶ��������ԭ��, ���ʼ��������ܾ�����;
	        //           (5) ������ϼ��㶼ȷ������, ���ʼ���������վ���Ұ�����
	        //
	        //    PS_03: ��ϸ��log, ���濴log, ����log, ����ԭ����log��˵����
	        transport.connect(myEmailAccount, myEmailPassword);
 
	        // 6. �����ʼ�, �������е��ռ���ַ, message.getAllRecipients() ��ȡ�������ڴ����ʼ�����ʱ��ӵ������ռ���, ������, ������
	        transport.sendMessage(message, message.getAllRecipients());
 
	        // 7. �ر�����
	        transport.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
     * ����һ��ֻ�����ı��ļ��ʼ�
     *
     * @param session �ͷ����������ĻỰ
     * @param sendMail ����������
     * @param receiveMail �ռ�������
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
        // 1. ����һ���ʼ�
        MimeMessage message = new MimeMessage(session);
 
        // 2. From: �����ˣ��ǳ��й�����ɣ����ⱻ�ʼ�����������Ϊ���ķ������������ʧ�ܣ����޸��ǳƣ�
        message.setFrom(new InternetAddress(sendMail, "ǿ������", "UTF-8"));
 
        // 3. To: �ռ��ˣ��������Ӷ���ռ��ˡ����͡����ͣ�
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "XX�û�", "UTF-8"));
        //Cc: ���ͣ���ѡ��
        //message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress("2974112544@qq.com", "����", "UTF-8"));
        //Cc: ���ͣ���ѡ��
        //message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress("liangzhe@gdcattsoft.com", "USER_EE", "UTF-8"));
 
        // 4. Subject: �ʼ����⣨�����й�����ɣ����ⱻ�ʼ�����������Ϊ���ķ������������ʧ�ܣ����޸ı��⣩
        message.setSubject(title, "UTF-8");
           
        // 5. Content: �ʼ����ģ�����ʹ��html��ǩ���������й�����ɣ����ⱻ�ʼ�����������Ϊ���ķ������������ʧ�ܣ����޸ķ������ݣ�
        message.setContent(messages, "text/html;charset=UTF-8");
        
        // 6. ���÷���ʱ��
        message.setSentDate(new Date());
        
        // 7. ��������
        message.saveChanges();
        return message;
    }

    
	
	public static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��");  //ʱ���ʽ��
	    Date date = new Date();  
	    return sdf.format(date);//���ظ�ʽ���Ժ�Ľ��
	}
    public static void main(String[] args) {
        new sendEmail("2859119753@qq.com","���Ǳ���","����ע��Ϊ��ũ��������Ա��ע����Ϊ").send();
    	System.out.println("�ռ��ˣ�"+receiveMailAccount);
    	System.out.println(messages);
    	return;
    }
 
}