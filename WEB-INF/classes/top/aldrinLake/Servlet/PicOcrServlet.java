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
       
	// 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "upload";

    // 上传配置-单位字节
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

    
    /**
       * 上传数据及保存文件
     */

    	//负责有图片的文章入库
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException { 
    	
    	
    	//获取文件需要上传到的路径
    	String path = request.getSession().getServletContext().getRealPath("cutPaper");//这里定义图片存放位置路径，注意这里的uploadimage是自己定义的一个文件夹  	
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        System.out.println("path:"+path);
    	//获得磁盘文件条目工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //factory.setRepository(dir);//这一句会上传的照片额外产生tmp后缀文件
        //设置缓存的大小，当上传文件的容量超过该缓存时，直接放到暂时存储室
        //factory.setSizeThreshold(1024 * 1024);
        //高水平的API文件上传处理
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("ISO8859_1");//防止乱码
    	List<FileItem> list = null;
		try {
			list = upload.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        FileItem picture = null;
        for (FileItem item : list) {
            //获取表单的属性名字
            String name = item.getFieldName();
            //如果获取的表单信息是普通的 文本 信息
            System.out.println("name:"+name);
            if (item.isFormField()) {
                //获取用户具体输入的字符串
                String value = new String(item.getString().getBytes("ISO8859_1"),"UTF-8");
                System.out.println("value:"+value);
                request.setAttribute(name, value);
            } else {//如果是图片信息
                picture = item;
            }
        }
    	//自定义上传图片的名字为author_student_id.jpg
        String fileName = System.currentTimeMillis()+ ".png";
        String destPath = path +"\\"+ fileName;
        
        System.out.println("destPath=" + destPath);
        boolean flag = MyFile.SaveImage(picture, destPath);
        
    	if(flag) {
        	System.out.println("存截图成功了");
        	//System.out.println(BaiDuOCR.startOCR_Heigh(destPath));//由程序自动帮助裁剪图片
        	String text = BaiDuOCR.startOCR_Heigh(destPath);
        	new File(destPath).delete();//删除原先的图片 
        	System.out.println(text);
        	
        	response.setContentType("text/html;charset=UTF-8");//防止浏览器无法显示中文
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
