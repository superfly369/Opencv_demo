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
     * canny�㷨����Ե���
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
     * ���ر�Ե���֮���������,������
     * 
     * @param cannyMat
     *            Canny֮���mat����
     * @return
     */
    public static RotatedRect findMaxRect(Mat cannyMat) {

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();

        // Ѱ������
        Imgproc.findContours(cannyMat, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE,
                new Point(0, 0));

        // �ҳ�ƥ�䵽���������
        double area = Imgproc.boundingRect(contours.get(0)).area();
        int index = 0;

        // �ҳ�ƥ�䵽���������
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
     * ��ת����
     * 
     * @param src
     *            mat����
     * @param rect
     *            ����
     * @return
     */
    public static Mat rotation(Mat cannyMat, RotatedRect rect) {
        // ��ȡ���ε��ĸ�����
        Point[] rectPoint = new Point[4];
        rect.points(rectPoint);

        double angle = rect.angle + 90;

        Point center = rect.center;

        Mat CorrectImg = new Mat(cannyMat.size(), cannyMat.type());

        cannyMat.copyTo(CorrectImg);

        // �õ���ת��������
        Mat matrix = Imgproc.getRotationMatrix2D(center, angle, 0.8);

        Imgproc.warpAffine(CorrectImg, CorrectImg, matrix, CorrectImg.size(), 1, 0, new Scalar(0, 0, 0));

        return CorrectImg;
    }
    /**
     * �ѽ������ͼ���и����
     * 
     * @param correctMat
     *            ͼ��������Mat����
     */
    public static void cutRect(Mat correctMat , Mat nativeCorrectMat) {
        // ��ȡ������
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
     * ����ͼ��
     * 
     * @param src
     * @return
     */
    public static void correct(Mat src) {
        // Canny
    	//Mat src=Imgcodecs.imread(filename);
        Mat cannyMat = canny(src);

        // ��ȡ������
        RotatedRect rect = findMaxRect(cannyMat);

        // ��ת����
        Mat CorrectImg = rotation(cannyMat , rect);
        Mat NativeCorrectImg = rotation(src , rect);


        //�ü�����
        cutRect(CorrectImg , NativeCorrectImg);
        //Imgcodecs.imwrite("img/srcImg.jpg",src);
        //HandleImgUtils.saveImg(src, "C:/Users/admin/Desktop/opencv/open/x/srcImg.jpg");
        Imgcodecs.imwrite("img/10.jpg",CorrectImg);
        //HandleImgUtils.saveImg(CorrectImg, "C:/Users/admin/Desktop/opencv/open/x/correct.jpg");
    }
    /**
     * ���Խ���ͼ��
     */
    public static void testCorrect(String filename) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(filename);
        correct(src);
    }
}
