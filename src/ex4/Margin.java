package ex4;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Margin {
	/**
     * canny算法，边缘检测
     * 
     * @param src
     * @return
     */
    public static Mat canny(Mat src) {
    	//Mat src=Imgcodecs.imread(filename);
        Mat mat = src.clone();
        Imgproc.Canny(src, mat, 60, 200);
        //HandleImgUtils.saveImg(mat , "C:/Users/admin/Desktop/opencv/open/x/canny.jpg");
        Imgcodecs.imwrite("img/canny.jpg",mat);
        return mat;
    }
    /**
     * 返回边缘检测之后的最大矩形,并返回
     * 
     * @param cannyMat
     *            Canny之后的mat矩阵
     * @return
     */
    public static RotatedRect findMaxRect(Mat cannyMat) {

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();

        // 寻找轮廓
        Imgproc.findContours(cannyMat, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE,
                new Point(0, 0));

        // 找出匹配到的最大轮廓
        double area = Imgproc.boundingRect(contours.get(0)).area();
        int index = 0;

        // 找出匹配到的最大轮廓
        for (int i = 0; i < contours.size(); i++) {
            double tempArea = Imgproc.boundingRect(contours.get(i)).area();
            if (tempArea > area) {
                area = tempArea;
                index = i;
            }
        }

        MatOfPoint2f matOfPoint2f = new MatOfPoint2f(contours.get(index).toArray());

        RotatedRect rect = Imgproc.minAreaRect(matOfPoint2f);

        return rect;
    }
    /**
     * 旋转矩形
     * 
     * @param src
     *            mat矩阵
     * @param rect
     *            矩形
     * @return
     */
    public static Mat rotation(Mat cannyMat, RotatedRect rect) {
        // 获取矩形的四个顶点
        Point[] rectPoint = new Point[4];
        rect.points(rectPoint);

        double angle = rect.angle + 90;

        Point center = rect.center;

        Mat CorrectImg = new Mat(cannyMat.size(), cannyMat.type());

        cannyMat.copyTo(CorrectImg);

        // 得到旋转矩阵算子
        Mat matrix = Imgproc.getRotationMatrix2D(center, angle, 0.8);

        Imgproc.warpAffine(CorrectImg, CorrectImg, matrix, CorrectImg.size(), 1, 0, new Scalar(0, 0, 0));

        return CorrectImg;
    }
    /**
     * 把矫正后的图像切割出来
     * 
     * @param correctMat
     *            图像矫正后的Mat矩阵
     */
    public static void cutRect(Mat correctMat , Mat nativeCorrectMat) {
        // 获取最大矩形
        RotatedRect rect = findMaxRect(correctMat);

        Point[] rectPoint = new Point[4];
        rect.points(rectPoint);

        int startLeft = (int)Math.abs(rectPoint[0].x);
        int startUp = (int)Math.abs(rectPoint[0].y < rectPoint[1].y ? rectPoint[0].y : rectPoint[1].y);
        int width = (int)Math.abs(rectPoint[2].x - rectPoint[0].x);
        int height = (int)Math.abs(rectPoint[1].y - rectPoint[0].y);

        //System.out.println("startLeft = " + startLeft);
        //System.out.println("startUp = " + startUp);
        //System.out.println("width = " + width);
        //System.out.println("height = " + height);

/*        for(Point p : rectPoint) {
            System.out.println(p.x + " , " + p.y);
        }*/

        Mat temp = new Mat(nativeCorrectMat , new Rect(startLeft , startUp , width , height ));
        Mat t = new Mat();
        temp.copyTo(t);
        Imgcodecs.imwrite("img/9.jpg",t);
        //HandleImgUtils.saveImg(t , "C:/Users/admin/Desktop/opencv/open/x/cutRect.jpg");
    }
    /**
     * 矫正图像
     * 
     * @param src
     * @return
     */
    public static void correct(Mat src) {
        // Canny
    	//Mat src=Imgcodecs.imread(filename);
        Mat cannyMat = canny(src);

        // 获取最大矩形
        RotatedRect rect = findMaxRect(cannyMat);

        // 旋转矩形
        Mat CorrectImg = rotation(cannyMat , rect);
        Mat NativeCorrectImg = rotation(src , rect);


        //裁剪矩形
        cutRect(CorrectImg , NativeCorrectImg);
        //Imgcodecs.imwrite("img/srcImg.jpg",src);
        //HandleImgUtils.saveImg(src, "C:/Users/admin/Desktop/opencv/open/x/srcImg.jpg");
        Imgcodecs.imwrite("img/10.jpg",CorrectImg);
        //HandleImgUtils.saveImg(CorrectImg, "C:/Users/admin/Desktop/opencv/open/x/correct.jpg");
    }
    /**
     * 测试矫正图像
     */
    public static void testCorrect(String filename) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(filename);
        correct(src);
    }
}
