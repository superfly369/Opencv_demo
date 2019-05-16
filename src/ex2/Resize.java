package ex2;

import org.opencv.core.*;
import org.opencv.imgcodecs.*;
import org.opencv.imgproc.*;
 
public class Resize {
 
	public static void main(String[] args) {
		try{
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			
			Mat src=Imgcodecs.imread("img/1.jpg");
			//��ȡͼ�񵽾�����,ȡ�Ҷ�ͼ��
			if(src.empty()){
				throw new Exception("no file");
			}
			
			Mat dst=src.clone();
			//���ƾ������dst
			
			float scale=0.5f;
			float width=src.width();
			float height=src.height();
			
			Imgproc.resize(src, dst, new Size(width*scale,height*scale));
			Imgcodecs.imwrite("img/resize0.5.jpg",dst);
		
			scale=1.5f;
			Imgproc.resize(src, dst, new Size(width*scale,height*scale));
			Imgcodecs.imwrite("img/resize1.5.jpg",dst);
			
			Imgproc.resize(src, dst, new Size(400,400));
			Imgcodecs.imwrite("img/resize400.jpg", dst);
		}catch(Exception e){
			System.out.println("���⣺" + e);
		}
 
	}
 
}
