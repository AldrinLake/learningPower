package top.aldrinLake.Servlet;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;

import utils.BaiDuOCR;
import utils.MyFile;
import utils.PicProcessing;
import top.aldrinLake.Model.user;
import top.aldrinLake.*;  

/**
 * Servlet implementation class PicOcrServlet
 */
@WebServlet("/PicOcrServlet")
public class PicOcrServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// �ϴ��ļ��洢Ŀ¼
    private static final String UPLOAD_DIRECTORY = "upload";

    // �ϴ�����-��λ�ֽ�
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

    
    /**
       * �ϴ����ݼ������ļ�
     */

    	//������ͼƬ���������
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException { 
    	
    	
    	//��ȡ�ļ���Ҫ�ϴ�����·��
    	String path = request.getSession().getServletContext().getRealPath("cutPaper");//���ﶨ��ͼƬ���λ��·����ע�������uploadimage���Լ������һ���ļ���  	
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        System.out.println("path:"+path);
    	//��ô����ļ���Ŀ����
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //factory.setRepository(dir);//��һ����ϴ�����Ƭ�������tmp��׺�ļ�
        //���û���Ĵ�С�����ϴ��ļ������������û���ʱ��ֱ�ӷŵ���ʱ�洢��
        //factory.setSizeThreshold(1024 * 1024);
        //��ˮƽ��API�ļ��ϴ�����
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("ISO8859_1");//��ֹ����
    	List<FileItem> list = null;
		try {
			list = upload.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        FileItem picture = null;
        for (FileItem item : list) {
            //��ȡ������������
            String name = item.getFieldName();
            //�����ȡ�ı���Ϣ����ͨ�� �ı� ��Ϣ
            System.out.println("name:"+name);
            if (item.isFormField()) {
                //��ȡ�û�����������ַ���
                String value = new String(item.getString().getBytes("ISO8859_1"),"UTF-8");
                System.out.println("value:"+value);
                request.setAttribute(name, value);
            } else {//�����ͼƬ��Ϣ
                picture = item;
            }
        }
    	//�Զ����ϴ�ͼƬ������Ϊauthor_student_id.jpg
        String fileName = System.currentTimeMillis()+ ".png";
        String destPath = path +"\\"+ fileName;
        
        System.out.println("destPath=" + destPath);
        boolean flag = MyFile.SaveImage(picture, destPath);
        
    	if(flag) {
        	System.out.println("���ͼ�ɹ���");
        	//System.out.println(BaiDuOCR.startOCR_Heigh(destPath));//�ɳ����Զ������ü�ͼƬ
        	String text = BaiDuOCR.startOCR_Heigh(destPath);
        	new File(destPath).delete();//ɾ��ԭ�ȵ�ͼƬ 
        	System.out.println(text);
        	
        	response.setContentType("text/html;charset=UTF-8");//��ֹ������޷���ʾ����
        	Writer out = response.getWriter(); 
        	out.write(text);
        }   	
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
