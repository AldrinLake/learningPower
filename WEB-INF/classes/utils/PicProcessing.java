package utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.opencv.core.Mat;
import javax.imageio.ImageIO;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


import utils.OCR;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import utils.BaiDuOCR;
public class PicProcessing {
	static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);};  //用来调用OpenCV库文件,必须添加
	/**
	 * @category图片裁剪：把学习强国的界面裁剪出只有分数的图片，不保留原图
	 * @param args
	 */
	public static Mat CutPic(Mat anima) {
		//对原图进行裁剪，这个尺寸(400, 350,300, 150)的位置正好是学习强国截图界面分数的位置
		Rect rect = new Rect(380, 320,320, 120);
        Mat ROI2 = new Mat(anima, rect);
        return ROI2;//返回裁剪后的图片（Mat形式）
	}
	/**
	 * @category对原图进行 高斯滤波 处理
	 * @param args
	 */
	public static Mat GaussianBlur(Mat src) {
		Mat dst_GaussianBlur = new Mat();
        Imgproc.GaussianBlur(src, dst_GaussianBlur, new Size(5, 5), 0);
        return dst_GaussianBlur;
	}
	/**
	 * @category二值化处理：把图片变成黑白色，方便识别
	 * @param args
	 */
	public static Mat BinaryZation(Mat anima) {
		Mat ipr = new Mat();
        Imgproc.threshold(anima, ipr,0, 255, 3);//二值化
        return ipr;
	}
	/**
	 * @category调用图像识别包进行识别(自动裁剪)
	 * 输入：未裁剪截图的地址
	 * 输出：图片上数字内容
	 * @param srcPic
	 */
	public static String startOCR_accordCut(String srcPic) {
		//修改图片像素到1080×1920
		try {
			BufferedImage source = ImageIO.read(new File(srcPic));
			source = resizeBufferedImage(source, 1080, 1920, false);
			ImageIO.write(source,"png",new File(srcPic)); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取原图并且灰度处理
		Mat anima = Imgcodecs.imread(srcPic, Imgcodecs.IMREAD_GRAYSCALE);
		anima=CutPic(anima);//裁剪
		//anima=GaussianBlur(anima);//高斯滤波处理
		//anima=BinaryZation(anima);//二值化处理
		Imgcodecs.imwrite(srcPic+"deal.png", anima);//把处理完的anima放进srcPic的图片路径
		//String text = OCR.OCR_recognition(srcPic+"deal.png");//这行是精度超低的垃圾算法，
		return BaiDuOCR.startOCR_Heigh(srcPic+"deal.png");//这行是精度超高的百度接口，
	}
	/**
	 * @category调用图像识别包进行识别(用户已经手动裁剪的图片)
	 * 输入：用户已经手动裁剪的图片的地址
	 * 输出：图片上数字内容
	 * @param srcPic
	 */
	public static String startOCR_alreadyCut(String srcPic) {
		/*
		//获取原图并且灰度处理
		Mat anima = Imgcodecs.imread(srcPic, Imgcodecs.IMREAD_GRAYSCALE);
		anima=GaussianBlur(anima);//高斯滤波处理
		anima=BinaryZation(anima);//二值化处理
		Imgcodecs.imwrite(srcPic+"deal.jpg", anima);//把处理完的anima放进srcPic的图片路径
		String text = OCR.OCR_recognition(srcPic+"deal.jpg");
		return text;
		*/
		//以上是精度超低的垃圾算法，
		//以下是精度超高的百度接口
		return BaiDuOCR.startOCR_Heigh(srcPic);
	}
    
    /**
     * @deprecated 调整bufferedimage大小
	 * @param source BufferedImage 原始image
	 * @param targetW int  目标宽（像素）
	 * @param targetH int  目标高（像素）
	 * @param flag boolean 是否同比例调整
	 * @return BufferedImage  返回新image
     */
    private static BufferedImage resizeBufferedImage(BufferedImage source, int targetW, int targetH, boolean flag) {
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		if (flag && sx > sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else if(flag && sx <= sy){
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}
		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			target = new BufferedImage(targetW, targetH, type);
		}
		Graphics2D g = target.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}
    public static void main(String[] args){   
    	String srcPic = "C:\\Users\\lenovo\\Desktop\\ab\\670.png";
    	String test = startOCR_accordCut(srcPic);//由程序自动帮助裁剪图片
    	System.out.println(test);
    	//startOCR_alreadyCut(srcPic);//用户手动裁剪好了分数图片

    }
	
 
 }
