package ex2;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
 
public class Rotate {
 
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
         
        Mat src=Imgcodecs.imread("img/1.jpg");
        //读取图像到矩阵中,取灰度图像
        if(src.empty()){
            return ;
        }
        try{
            Mat dst=src.clone();
            //复制矩阵进入dst
            Point center =new Point(src.width()/2.0,src.height()/2.0);
            Mat affineTrans=Imgproc.getRotationMatrix2D(center, 33.0, 1.0);
             
            Imgproc.warpAffine(src, dst, affineTrans, dst.size(),Imgproc.INTER_NEAREST);
            Imgcodecs.imwrite("img/033.jpg",dst);
             
            affineTrans=Imgproc.getRotationMatrix2D(center, 110.0, 1.1);
            Imgproc.warpAffine(src,dst,affineTrans,dst.size(),Imgproc.INTER_NEAREST);
            Imgcodecs.imwrite("img/110.jpg",dst);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
 
}