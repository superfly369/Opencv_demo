package ex3;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

/**  
 * 
 * @Title: Image.java   
 * @Description: OpenCV-4.0.0 �����ļ�
 * @Package com.xu.test   
 * @author: xuhyacinth     
 * @date: 2019��5��7��12:13:13   
 * @version: V-1.0.0 
 * @Copyright: 2019 xuhyacinth
 *
 */
public class Image {

	static {
		//��ʹ��OpenCVǰ�������Core.NATIVE_LIBRARY_NAME��,����ᱨ��
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		light();
	}

	/**
	 * OpenCV-4.0.0 ͼ����
	 * @return: void  
	 * @date: 2019��5��7��12:16:55
	 */
	public static void addWeighted() {
		Mat src1 = Imgcodecs.imread("C:\\Users\\Administrator\\Pictures\\test01.png");
		Mat src2 = Imgcodecs.imread("C:\\Users\\Administrator\\Pictures\\test02.png");
		Mat image = new Mat(src1.size(), src1.type());
		double alpha = 0.5;
		Core.addWeighted(src1, 1 - alpha, src2, alpha, 0.0, image);
		HighGui.imshow("ͼ����", image);
		HighGui.waitKey(1);
	}

	/**
	 * OpenCV-4.0.0 ͼ�����ȺͶԱȶȵ��� 
	 * @return: void  
	 * @date: 2019��5��7��12:16:55
	 */
	public static void light(){
		Mat src = Imgcodecs.imread("C:\\Users\\Administrator\\Pictures\\3.jpeg");
		Mat dst = new Mat(src.size(), src.type());
		int channels = src.channels();//��ȡͼ��ͨ����
		double[] pixel = new double[3];
		float alpha=1.2f;
		float bate=30f;
		for (int i = 0, rlen = src.rows(); i < rlen; i++) {
			for (int j = 0, clen = src.cols(); j < clen; j++) {
				if (channels == 3) {//1 ͼƬΪ3ͨ����ƽ����(R,G,B)
					pixel = src.get(i, j).clone();
					pixel[0] = pixel[0]*alpha+bate;//R
					pixel[1] = pixel[1]*alpha+bate;//G
					pixel[2] = pixel[2]*alpha+bate;//B
					dst.put(i, j, pixel);
				} else {//2 ͼƬΪ��ͨ�����Ҷ�ͼ
					pixel=src.get(i, j).clone();
					dst.put(i, j, pixel[0]*alpha+bate);
				}
			}
		}
		HighGui.imshow("ͼ�����ȺͶԱȶȵ���", dst);
		HighGui.waitKey(1);
	}

}



