package ex1;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class main {


	public static void main(String[] args) {
        // ����dll�������ȼ��أ�
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //���ļ�����ΪOpenCV��Mat��ʽ��ע�����ʱ��·����Ҫ��������
        Mat src = Imgcodecs.imread("img/1.bmp");
        if(src.dataAddr()==0){
            System.out.println("���ļ�����");
        }
        ImageViewer imageViewer = new ImageViewer(src, "��һ��ͼƬ");
        imageViewer.imshow();
    }

}
