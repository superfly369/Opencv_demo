package ex1;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class main {


	public static void main(String[] args) {
        // 载入dll（必须先加载）
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //将文件读入为OpenCV的Mat格式。注意测试时，路径不要包括中文
        Mat src = Imgcodecs.imread("img/1.bmp");
        if(src.dataAddr()==0){
            System.out.println("打开文件出错");
        }
        ImageViewer imageViewer = new ImageViewer(src, "第一幅图片");
        imageViewer.imshow();
    }

}
