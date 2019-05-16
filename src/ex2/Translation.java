package ex2;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import ex1.ImageViewer;

public class Translation {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
         
        Mat src=Imgcodecs.imread("img/1.jpg");
        //��ȡͼ�񵽾�����,ȡ�Ҷ�ͼ��
        if(src.empty()){
            return ;
        }
        try{
            Mat dst=src.clone();
            //���ƾ������dst
            Mat Trans = new Mat(2,3,CvType.CV_32FC1);
            Trans.put(0, 0 ,new float[] {1,0,100,0,1,50} );
            //����ƽ�ƾ���
            Imgproc.warpAffine(src, dst, Trans, dst.size(),Imgproc.INTER_NEAREST);
            Imgcodecs.imwrite("img/100_50.jpg",dst);

        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        //��ʾƽ�ƺ��ͼƬ
        // ����dll�������ȼ��أ�
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //���ļ�����ΪOpenCV��Mat��ʽ��ע�����ʱ��·����Ҫ��������
        Mat src1 = Imgcodecs.imread("img/100_50.jpg");
        if(src1.dataAddr()==0){
            System.out.println("���ļ�����");
        }
        ImageViewer imageViewer = new ImageViewer(src1, "��һ��ͼƬ");
        imageViewer.imshow();
    }
}
