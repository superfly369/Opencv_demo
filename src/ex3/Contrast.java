package ex3;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Contrast {
	
	public static void contrast(){
		Mat src = Imgcodecs.imread("D:\\1.jpg");
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
		Imgcodecs.imwrite("img/5.jpg", dst);
	}
	public static void main(String[] args) {
		//在使用OpenCV前必须加载Core.NATIVE_LIBRARY_NAME类,否则会报错
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		contrast();
	}
	
}
