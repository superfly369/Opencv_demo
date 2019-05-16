package ex2;

import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import ex1.ImageViewer;

public class dft {
    public static void main(String[] args) throws IOException,InterruptedException {
		String path = System.getProperty("user.dir");
    	String exe = "python";
        String command = path + "\\demo1.py";
        String[] cmdArr = new String[] {exe, command};
        Process process = Runtime.getRuntime().exec(cmdArr);
        process.waitFor();
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //���ļ�����ΪOpenCV��Mat��ʽ��ע�����ʱ��·����Ҫ��������
        Mat src1 = Imgcodecs.imread("D:\\2.png");
        if(src1.dataAddr()==0){
            System.out.println("���ļ�����");
        }
        ImageViewer imageViewer = new ImageViewer(src1, "��һ��ͼƬ");
        imageViewer.imshow();
    }
}