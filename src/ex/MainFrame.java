package ex;


import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import ex4.Margin;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

import java.awt.event.ItemListener;
import java.awt.image.ImageProducer;
import javax.swing.JMenuBar;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField text1;
	private String filename;
	private int duibidu = 1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * image function.
	 * 图像平移
	 */
	   public void Translate(String filename , int x , int y){
		   System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	       Mat src=Imgcodecs.imread(filename);
	       //读取图像到矩阵中,取灰度图像
	       if(src.empty()){
	           return ;
	       }
	       try{
	           Mat dst=src.clone();
	           //复制矩阵进入dst
	           Mat Trans = new Mat(2,3,CvType.CV_32FC1);
	           Trans.put(0, 0 ,new float[] {1,0,x,0,1,y} );
	           //创建平移矩阵
	           Imgproc.warpAffine(src, dst, Trans, dst.size(),Imgproc.INTER_NEAREST);
	           Imgcodecs.imwrite("F://1.jpg",dst);

	       }catch(Exception e){
	           e.printStackTrace();
	       }
		   
	   }
	 /**
	  * 图像缩放.
	  */
		public void resize (String filename){
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			Mat src=Imgcodecs.imread(filename);
		    if(src.empty()){
		           return ;
		       }
		    try{
			//读取图像到矩阵中,取灰度图像
			Mat dst=src.clone();
			//复制矩阵进入dst
			
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
		           e.printStackTrace();
		       }
		}
		 /**
		  * 图像旋转.
		  */
		 public void rotate(String filename , int x){
			 System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			     Mat src=Imgcodecs.imread(filename);
			        //读取图像到矩阵中,取灰度图像
			        if(src.empty()){
			            return ;
			        }
			        try{
			            Mat dst=src.clone();
			            //复制矩阵进入dst
			            Point center =new Point(src.width()/2.0,src.height()/2.0);
			            Mat affineTrans=Imgproc.getRotationMatrix2D(center, x, 1.0);
			             
			            Imgproc.warpAffine(src, dst, affineTrans, dst.size(),Imgproc.INTER_NEAREST);
			            Imgcodecs.imwrite("img/3.jpg",dst);
			        }catch(Exception e){
			            e.printStackTrace();
			        }
			   
		   }
		/* 傅里叶变换*/
		 public void fouriertransform(String filename) {
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				Mat src=Imgcodecs.imread(filename);
				//读取图像到矩阵中,取灰度图像
				Mat dst=src.clone();
				//复制矩阵进入dst
				Mat greyImg = Imgcodecs.imread(filename, Imgcodecs.IMREAD_GRAYSCALE);
				greyImg.convertTo(greyImg, CvType.CV_32F);
				int w = Core.getOptimalDFTSize(greyImg.cols());
				int h = Core.getOptimalDFTSize(greyImg.rows());
				Mat padded = new Mat();
				Core.copyMakeBorder(greyImg, padded, 0, h-src.rows(), 0, w-src.cols(), Core.BORDER_CONSTANT, Scalar.all(0));
				List<Mat> plane = new ArrayList<Mat>();
				plane.add(padded);
				plane.add(Mat.zeros(padded.size(), CvType.CV_32FC1));
				
				Mat complexImg = new Mat();
				Core.merge(plane, complexImg);
				Core.dft(complexImg, complexImg);
				Core.split(complexImg, plane);
				Mat magnitudeImage = new Mat();
				Core.magnitude(plane.get(0), plane.get(1), magnitudeImage);
				//Core.add(magnitudeImage, Scalar.all(1), magnitudeImage);
				Core.log(magnitudeImage, magnitudeImage);
				magnitudeImage = new Mat(magnitudeImage, new Rect(0, 0, magnitudeImage.cols()&-2, magnitudeImage.rows()&-2));
				
				int cx = magnitudeImage.cols()/2;
				int cy = magnitudeImage.rows()/2;
				Mat part1 = new Mat(magnitudeImage, new Rect(0, 0, cx, cy));
				Mat part2 = new Mat(magnitudeImage, new Rect(cx, 0, cx, cy));
				Mat part3 = new Mat(magnitudeImage, new Rect(0, cy, cx, cy));
				Mat part4 = new Mat(magnitudeImage, new Rect(cx, cy, cx, cy));
				Mat temp = new Mat();
				part1.copyTo(temp);
				part4.copyTo(part1);
				temp.copyTo(part4);
			    part2.copyTo(temp);
			    part3.copyTo(part2);
			    temp.copyTo(part3);
			    
			    Core.normalize(magnitudeImage, dst, 0, 255, Core.NORM_MINMAX);//归一化处理
			    Imgcodecs.imwrite("img/4.jpg",dst);
			}
		 /**
		  * 对比度增强.
		  */ 
			public void contrast(String filename , int a){
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				Mat src = Imgcodecs.imread(filename);
				Mat dst = new Mat(src.size(), src.type());
				int channels = src.channels();//获取图像通道数
				double[] pixel = new double[3];
				float alpha=0.3f * a;
				float bate=30f;
				for (int i = 0, rlen = src.rows(); i < rlen; i++) {
					for (int j = 0, clen = src.cols(); j < clen; j++) {
						if (channels == 3) {//1 图片为3通道即平常的(R,G,B)
							pixel = src.get(i, j).clone();
							pixel[0] = pixel[0]*alpha+bate;//R
							pixel[1] = pixel[1]*alpha+bate;//G
							pixel[2] = pixel[2]*alpha+bate;//B
							dst.put(i, j, pixel);
						} else {//2 图片为单通道即灰度图
							pixel=src.get(i, j).clone();
							dst.put(i, j, pixel[0]*alpha+bate);
						}
					}
				}
				Imgcodecs.imwrite("img/5.jpg", dst);
			}
			 /**
			  * 对比度增强-直方图均衡.
			  */ 
			 public void histEqualize(String filename) {
				 System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			        Mat source = Imgcodecs.imread(filename);
			        Mat dst = new Mat();
			        List<Mat> mv = new ArrayList<Mat>();
			        Core.split(source, mv);

			        for (int i = 0; i < source.channels(); i++)
			        {
			            Imgproc.equalizeHist(mv.get(i), mv.get(i));
			        }
			        Core.merge(mv, dst);
			        Imgcodecs.imwrite("img/6.jpg", dst);
			    }
			 /**
			  * 均值平滑.
			  */ 
			 public static void junzhi(String filename) {
				    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

				    Mat srcImage = Imgcodecs.imread(filename);

				    Mat dstImage = srcImage.clone();

				    Imgproc.blur(srcImage, dstImage, new Size(9,9), new Point(-1, -1), Core.BORDER_DEFAULT);

				    Imgcodecs.imwrite("img/7.jpg", dstImage);
				}
			 
			 /**
			  * 图像锐化.
			  */ 
			 public static void ruihua(String filename) {
				
				        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

				        Mat src = Imgcodecs.imread(filename);

				        Mat dst = src.clone();
				        Mat dstx = src.clone();
				        Mat dsty = src.clone();
				        Imgproc.GaussianBlur(src, dst, new Size(3, 3), 0);

				        Imgproc.Sobel(dst, dstx, -1, 1, 0, 3, 1, 0, Core.BORDER_DEFAULT);
				        //Imgcodecs.imwrite("img/dstx.jpg", dstx);

				        Imgproc.Sobel(dst, dsty, -1, 0, 1, 3, 1, 0, Core.BORDER_DEFAULT);
				        //Imgcodecs.imwrite("img/dsty.jpg", dsty);

				        Core.addWeighted(dstx, 0.5, dsty, 0.5, 0, dst);
				        Core.addWeighted(dst, 1, src, 1, 0, dst);
				        Imgcodecs.imwrite("img/8.jpg", dst);
				    
				}
			 /**
			  * 边缘检测.
			  */ 
			 public static void edge(String filename , int threshold) {
				
			        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			        Mat src = Imgcodecs.imread(filename);
			        Mat img = new Mat();
			        src.copyTo(img); 
			        Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
			        //
			        Imgproc.Canny(img, img, threshold, threshold * 3, 3, true);
			        Imgcodecs.imwrite("img/9.jpg", img);
				    
				}
			 //边缘跟踪
			 public static void follow(String filename , int threshold){
				 System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				 Mat img = Imgcodecs.imread(filename);
			      Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
			      Imgproc.Canny(img, img, threshold, threshold * 3, 3, true);
			      List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
			        Mat hierarchy = new Mat();
			        // 寻找轮廓
			        Imgproc.findContours(img, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE,
			                new Point(0, 0));
			        Mat drawing = Mat.zeros(img.size(), CvType.CV_8UC3);
			        for( int i = 0; i< contours.size(); i++ )
			        {
			          Scalar color = new Scalar( Math.random()*255, Math.random()*255, Math.random()*255 );
			          Imgproc.drawContours( drawing, contours, i, color, 2, 8, hierarchy, 0);
			        }
			        Imgcodecs.imwrite("img/10.jpg", drawing);
			 }
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		JFileChooser jfc = new JFileChooser();// 文件选择器
		jfc.setCurrentDirectory(new File("c://"));// 文件选择器的初始目录定为d盘
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1293, 660);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1275, 616);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton SelectImgBtn = new JButton("选择图片");
		SelectImgBtn.setBounds(703, 13, 113, 27);
		panel.add(SelectImgBtn);
		
		JPanel showImg = new JPanel();
		showImg.setBounds(167, 59, 980, 480);
		panel.add(showImg);
		showImg.setLayout(null);
		
		JLabel yuantu = new JLabel("");
		yuantu.setBounds(0, 0, 480, 480);
		showImg.add(yuantu);
		
		JLabel xianshi = new JLabel("");
		xianshi.setBounds(494, 0, 480, 480);
		showImg.add(xianshi);
		
		text1 = new JTextField();
		text1.setEditable(false);
		text1.setBounds(360, 14, 317, 24);
		panel.add(text1);
		text1.setColumns(10);
        
        JSlider slider = new JSlider(0,5,1);
        // 设置主刻度间隔
        slider.setMajorTickSpacing(1);
        // 设置次刻度间隔
        //slider.setMinorTickSpacing(1);
        // 绘制 刻度 和 标签
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBounds(887, 10, 218, 48);
        panel.add(slider);
        
		SelectImgBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if (e.getActionCommand().equals(SelectImgBtn)){
					jfc.setFileSelectionMode(0);// 设定只能选择到文件
					int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
					if (state == 1) {
						return;// 撤销则返回
					} else {
						File f = jfc.getSelectedFile();// f为选择到的文件
						ImageIcon icon = new ImageIcon(f.getAbsolutePath());
						icon.setImage(icon.getImage().getScaledInstance(480,480,Image.SCALE_DEFAULT));
						yuantu.setIcon(icon);
						//将windows的绝对路径转化成java可以读取的形式
						filename = (f.getAbsolutePath()).replaceAll("\\\\","/");
						text1.setText(f.getAbsolutePath());
					}
				//}

			}
		});
		
		JRadioButton btn1 = new JRadioButton("图像平移");
		btn1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
			
				if(btn1.isSelected())
				{	int x,y = 0;
					String input1 = JOptionPane.showInputDialog("请输入横向平移的距离：");
					x = Integer.valueOf(input1).intValue();
					String input2 = JOptionPane.showInputDialog("请输入纵向平移的距离：");
					y = Integer.valueOf(input2).intValue();
					Translate(filename,x,y);
					ImageIcon icon = new ImageIcon("F://1.jpg");
				    icon.setImage(icon.getImage().getScaledInstance(480,480,Image.SCALE_DEFAULT));
				    xianshi.setIcon(icon);		
			}
			}
		});
		btn1.setBounds(0, 13, 157, 27);
		panel.add(btn1);
		
		JRadioButton btn2 = new JRadioButton("图像旋转");
		btn2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				
				if(btn2.isSelected())
				{	int x = 0;
					String input1 = JOptionPane.showInputDialog("请输入旋转的角度：");
					x = Integer.valueOf(input1).intValue();
					rotate(filename,x);
					ImageIcon icon = new ImageIcon("img/3.jpg");
					icon.setImage(icon.getImage().getScaledInstance(480,480,Image.SCALE_DEFAULT));
				    xianshi.setIcon(icon);
				    	
			}
			}
		});
		btn2.setBounds(0, 45, 157, 27);
		panel.add(btn2);
		
		JRadioButton btn3 = new JRadioButton("图像缩放");
		btn3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				
				if(btn3.isSelected())
				{	int a = 1;
					String inputValue = JOptionPane.showInputDialog("请输入缩小的倍数：");
					a = Integer.valueOf(inputValue).intValue();
					resize(filename);
					ImageIcon icon = new ImageIcon(filename);
					icon.setImage(icon.getImage().getScaledInstance(480/a,480/a,Image.SCALE_DEFAULT));
				    xianshi.setIcon(icon);
					
			}
			}
		});
		btn3.setBounds(0, 79, 157, 27);
		panel.add(btn3);
		
		JRadioButton btn4 = new JRadioButton("\u5085\u91CC\u53F6\u53D8\u6362");
		btn4.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				if(e.getSource() == btn4)
				{	
					fouriertransform(filename);
					ImageIcon icon = new ImageIcon("img/4.jpg");
				    icon.setImage(icon.getImage().getScaledInstance(480,480,Image.SCALE_DEFAULT));
				    xianshi.setIcon(icon);	
			}
			}
		});
		btn4.setBounds(0, 111, 157, 27);
		panel.add(btn4);
        
        JRadioButton btn5 = new JRadioButton("\u5BF9\u6BD4\u5EA6\u589E\u5F3A");
        btn5.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				if(btn5.isSelected())
				
				{		
						//String inputValue = JOptionPane.showInputDialog("请输入对比度的倍数(1~5)：");
						//a = Integer.valueOf(inputValue).intValue();
			        // 添加刻度改变监听器
			        slider.addChangeListener(new ChangeListener() {
			            @Override
			            public void stateChanged(ChangeEvent e) {
			                //System.out.println("当前值: " + slider.getValue());
			                duibidu = slider.getValue();
			                contrast(filename,duibidu);
			                ImageIcon icon = new ImageIcon("img/5.jpg");
			                icon.setImage(icon.getImage().getScaledInstance(480,480,Image.SCALE_DEFAULT));
			                xianshi.setIcon(icon);
			            }
			        });	
				
				  
				   
			}
			}
		});
        btn5.setBounds(0, 143, 157, 27);
        panel.add(btn5);
        
        JRadioButton btn6 = new JRadioButton("\u76F4\u65B9\u56FE\u5747\u8861");
        btn6.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				if(e.getSource() == btn6)
				
				{	histEqualize(filename);
					ImageIcon icon = new ImageIcon("img/6.jpg");
					icon.setImage(icon.getImage().getScaledInstance(480,480,Image.SCALE_DEFAULT));
				    xianshi.setIcon(icon);
				   
			}
			}
		});
        btn6.setBounds(0, 175, 157, 27);
        panel.add(btn6);

        JRadioButton btn7 = new JRadioButton("\u56FE\u50CF\u5E73\u6ED1");
        btn7.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				if(e.getSource() == btn7)
				
				{	junzhi(filename);
					ImageIcon icon = new ImageIcon("img/7.jpg");
					icon.setImage(icon.getImage().getScaledInstance(480,480,Image.SCALE_DEFAULT));
				    xianshi.setIcon(icon);
				   
			}
			}
		});
        btn7.setBounds(0, 207, 157, 27);
        panel.add(btn7);
        
        JRadioButton btn8 = new JRadioButton("\u56FE\u50CF\u9510\u5316");
        btn8.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				if(e.getSource() == btn8)
				
				{	ruihua(filename);
					ImageIcon icon = new ImageIcon("img/8.jpg");
					icon.setImage(icon.getImage().getScaledInstance(480,480,Image.SCALE_DEFAULT));
				    xianshi.setIcon(icon);
				   
			}
			}
		});
        btn8.setBounds(0, 239, 157, 27);
        panel.add(btn8);
        
        JRadioButton btn9 = new JRadioButton("边缘检测");
        btn9.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				if(e.getSource() == btn9)
				
				{	edge(filename,40);
					ImageIcon icon = new ImageIcon("img/9.jpg");
					icon.setImage(icon.getImage().getScaledInstance(480,480,Image.SCALE_DEFAULT));
				    xianshi.setIcon(icon);
				   
			}
			}
		});
        btn9.setBounds(0, 271, 157, 27);
        panel.add(btn9);
		JRadioButton btn10 = new JRadioButton("\u8FB9\u7F18\u68C0\u6D4B\u4E0E\u88C1\u526A");
        btn10.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				if(e.getSource() == btn10)
				
				{	follow(filename,20);
					ImageIcon icon = new ImageIcon("img/10.jpg");
					icon.setImage(icon.getImage().getScaledInstance(480,480,Image.SCALE_DEFAULT));
				    xianshi.setIcon(null);
				    xianshi.setIcon(icon);
				   
			}
			}
		});
        btn10.setBounds(0, 303, 157, 27);
        panel.add(btn10);
        
        ButtonGroup group=new ButtonGroup();
        group.add(btn1);
        group.add(btn2);
        group.add(btn3);
        group.add(btn4);
        group.add(btn5);
        group.add(btn6);
        group.add(btn7);
        group.add(btn8);
        group.add(btn9);
        group.add(btn10);
        



		
	}
}
