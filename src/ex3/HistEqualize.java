package ex3;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class HistEqualize {
    /**
     * 增强对比度
     *
     * @param src BGR格式图像
     * @return
     */
    public void histEqualize(String filename) {
    	Mat src = Imgcodecs.imread(filename);
        Mat dst = src.clone();
        Imgproc.cvtColor(dst, dst, Imgproc.COLOR_BGR2YCrCb);
        List<Mat> list1 = new ArrayList<>();
        Core.split(dst, list1);
        Imgproc.equalizeHist(list1.get(0), list1.get(0));
        Core.normalize(list1.get(0), list1.get(0), 0, 255, Core.NORM_MINMAX);
        Core.merge(list1, dst);
        Imgproc.cvtColor(dst, dst, Imgproc.COLOR_YCrCb2BGR);
        Imgcodecs.imwrite("img/6.jpg", dst);
   
    }

}
