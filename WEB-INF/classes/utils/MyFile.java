package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.fileupload.FileItem;
/**
 * 
 * @author Aldrin Lake
 * @version Use to store images in the specified folder 2019-4-24
 */

public class MyFile {
	
	public static boolean SaveImage(FileItem picture,String destPath)  {
		/**
		 * If the upload is successful, return true, otherwise return false
		 */
    	File file = new File(destPath);
    	OutputStream out;
		try {
			
			out = new FileOutputStream(file);
			InputStream in = picture.getInputStream();
            int length = 0;
            byte[] buf = new byte[1024];
            // in.read(buf) ÿ�ζ��������ݴ����buf ������
            while ((length = in.read(buf)) != -1) {
                //��buf������ȡ������д�����������������
                out.write(buf, 0, length);
            }
            in.close();
            out.close();
    		return true;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("MyFile.java: Upload failed");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("MyFile.java: Upload failed");
				e.printStackTrace();
			}
            
		return false; 
	}
}
