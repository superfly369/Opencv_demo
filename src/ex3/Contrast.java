package ex3;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Contrast {
	
	public static void contrast(){
		Mat src = Imgcodecs.imread("D:\\1.jpg");
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
		Imgcodecs.imwrite("img/5.jpg", dst);
	}
	public static void main(String[] args) {
		//��ʹ��OpenCVǰ�������Core.NATIVE_LIBRARY_NAME��,����ᱨ��
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		contrast();
	}
	
}
