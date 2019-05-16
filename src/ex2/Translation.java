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
        //读取图像到矩阵中,取灰度图像
        if(src.empty()){
            return ;
        }
        try{
            Mat dst=src.clone();
            //复制矩阵进入dst
            Mat Trans = new Mat(2,3,CvType.CV_32FC1);
            Trans.put(0, 0 ,new float[] {1,0,100,0,1,50} );
            //创建平移矩阵
            Imgproc.warpAffine(src, dst, Trans, dst.size(),Imgproc.INTER_NEAREST);
            Imgcodecs.imwrite("img/100_50.jpg",dst);

        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        //显示平移后的图片
        // 载入dll（必须先加载）
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //将文件读入为OpenCV的Mat格式。注意测试时，路径不要包括中文
        Mat src1 = Imgcodecs.imread("img/100_50.jpg");
        if(src1.dataAddr()==0){
            System.out.println("打开文件出错");
        }
        ImageViewer imageViewer = new ImageViewer(src1, "第一幅图片");
        imageViewer.imshow();
    }
}
