package ex;


import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
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
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

import java.awt.event.ItemListener;
import java.awt.image.ImageProducer;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField text1;
	private String filename;

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
	   public void Translate(String filename){
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
	           Trans.put(0, 0 ,new float[] {1,0,100,0,1,50} );
	           //创建平移矩阵
	           Imgproc.warpAffine(src, dst, Trans, dst.size(),Imgproc.INTER_NEAREST);
	           Imgcodecs.imwrite("img/2.jpg",dst);

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
		}
		 /**
		  * 图像旋转.
		  */
		 public void rotate(String filename){
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
			            Mat affineTrans=Imgproc.getRotationMatrix2D(center, 33.0, 1.0);
			             
			            Imgproc.warpAffine(src, dst, affineTrans, dst.size(),Imgproc.INTER_NEAREST);
			            Imgcodecs.imwrite("img/033.jpg",dst);
			        }catch(Exception e){
			            e.printStackTrace();
			        }
			   
		   }
		 
		 /**
		  * 对比度增强.
		  */ 
			public void contrast(String filename){
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				Mat src = Imgcodecs.imread(filename);
				Mat dst = new Mat(src.size(), src.type());
				int channels = src.channels();//获取图像通道数
				double[] pixel = new double[3];
				float alpha=1.2f;
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
				 	/*System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			    	Mat src = Imgcodecs.imread(filename);
			        Mat dst = src.clone();
			        Imgproc.cvtColor(dst, dst, Imgproc.COLOR_BGR2YCrCb);
			        List<Mat> list1 = new ArrayList<>();
			        Core.split(dst, list1);
			        Imgproc.equalizeHist(list1.get(0), list1.get(0));
			        Core.normalize(list1.get(0), list1.get(0), 0, 255, Core.NORM_MINMAX);
			        Core.merge(list1, dst);
			        Imgproc.cvtColor(dst, dst, Imgproc.COLOR_YCrCb2BGR);
			        Imgcodecs.imwrite("img/6.jpg", dst);*/
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
	 * Create the frame.
	 */
	public MainFrame() {
		JFileChooser jfc = new JFileChooser();// 文件选择器
		jfc.setCurrentDirectory(new File("d://"));// 文件选择器的初始目录定为d盘
		
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
		
		JRadioButton btn1 = new JRadioButton("\u56FE\u7247\u5E73\u79FB");
		btn1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				if(e.getSource() == btn1)
				{	Translate(filename);
				    ImageIcon icon = new ImageIcon("img/2.jpg");
				    icon.setImage(icon.getImage().getScaledInstance(480,480,Image.SCALE_DEFAULT));
				    xianshi.setIcon(icon);		
			}
			}
		});
		btn1.setBounds(0, 13, 157, 27);
		panel.add(btn1);
		
		JRadioButton btn2 = new JRadioButton("\u56FE\u7247\u65CB\u8F6C");
		btn2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				if(e.getSource() == btn2)
				{	rotate(filename);
					ImageIcon icon = new ImageIcon("img/033.jpg");
					icon.setImage(icon.getImage().getScaledInstance(480,480,Image.SCALE_DEFAULT));
				    xianshi.setIcon(icon);
				    	
			}
			}
		});
		btn2.setBounds(0, 45, 157, 27);
		panel.add(btn2);
		
		JRadioButton btn3 = new JRadioButton("\u56FE\u7247\u7F29\u653E");
		btn3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				if(e.getSource() == btn3)
				//Translate("img/1.jpg");
				{	resize(filename);
					ImageIcon icon = new ImageIcon("img/resize0.5.jpg");
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
					ImageIcon icon = new ImageIcon("img/2.png");
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
				if(e.getSource() == btn5)
				
				{	contrast(filename);
					ImageIcon icon = new ImageIcon("img/5.jpg");
				    xianshi.setIcon(icon);
				   
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
				    xianshi.setIcon(icon);
				   
			}
			}
		});
        btn8.setBounds(0, 239, 157, 27);
        panel.add(btn8);
		ButtonGroup group=new ButtonGroup();
        group.add(btn1);
        group.add(btn2);
        group.add(btn3);
        group.add(btn4);
        group.add(btn5);
        group.add(btn6);
        group.add(btn7);
        group.add(btn8);
		
	}
}
