package ex3;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

/**  
 * 
 * @Title: Image.java   
 * @Description: OpenCV-4.0.0 测试文件
 * @Package com.xu.test   
 * @author: xuhyacinth     
 * @date: 2019年5月7日12:13:13   
 * @version: V-1.0.0 
 * @Copyright: 2019 xuhyacinth
 *
 */
public class Image {

	static {
		//在使用OpenCV前必须加载Core.NATIVE_LIBRARY_NAME类,否则会报错
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		light();
	}

	/**
	 * OpenCV-4.0.0 图像混合
	 * @return: void  
	 * @date: 2019年5月7日12:16:55
	 */
	public static void addWeighted() {
		Mat src1 = Imgcodecs.imread("C:\\Users\\Administrator\\Pictures\\test01.png");
		Mat src2 = Imgcodecs.imread("C:\\Users\\Administrator\\Pictures\\test02.png");
		Mat image = new Mat(src1.size(), src1.type());
		double alpha = 0.5;
		Core.addWeighted(src1, 1 - alpha, src2, alpha, 0.0, image);
		HighGui.imshow("图像混合", image);
		HighGui.waitKey(1);
	}

	/**
	 * OpenCV-4.0.0 图像亮度和对比度调节 
	 * @return: void  
	 * @date: 2019年5月7日12:16:55
	 */
	public static void light(){
		Mat src = Imgcodecs.imread("C:\\Users\\Administrator\\Pictures\\3.jpeg");
		Mat dst = new Mat(src.size(), src.type());
		int channels = src.channels();//获取图像通道数
		double[] pixel = new double[3];
		float alpha=1.2f;
		float bate=30f;
		for (int i = 0, rlen = src.rows(); i < rlen; i++) {
			for (int j = 0, clen = src.cols(); j < clen; j++) {
				if (channels == 3) {//1 图片为3通道即平常的(R,G,B)
					pixel = src.get(i, j).clone();
					pixel[0] = pixel[0]*alpha+bate;//R
					pixel[1] = pixel[1]*alpha+bate;//G
					pixel[2] = pixel[2]*alpha+bate;//B
					dst.put(i, j, pixel);
				} else {//2 图片为单通道即灰度图
					pixel=src.get(i, j).clone();
					dst.put(i, j, pixel[0]*alpha+bate);
				}
			}
		}
		HighGui.imshow("图像亮度和对比度调节", dst);
		HighGui.waitKey(1);
	}

}



