package ex;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

	private JPanel contentPane;

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
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 868, 553);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JRadioButton showImgBtn = new JRadioButton("\u663E\u793A\u56FE\u7247");
		showImgBtn.setBounds(0, 42, 157, 27);
		panel.add(showImgBtn);
		
		JLabel selectLbl = new JLabel("\u529F\u80FD\u9009\u62E9");
		selectLbl.setBounds(14, 13, 72, 18);
		panel.add(selectLbl);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("\u56FE\u7247\u5E73\u79FB");
		rdbtnNewRadioButton_1.setBounds(0, 74, 157, 27);
		panel.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("\u56FE\u7247\u7F29\u653E");
		rdbtnNewRadioButton_2.setBounds(0, 107, 157, 27);
		panel.add(rdbtnNewRadioButton_2);
		
		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("\u56FE\u7247\u65CB\u8F6C");
		rdbtnNewRadioButton_3.setBounds(0, 139, 157, 27);
		panel.add(rdbtnNewRadioButton_3);
		
		JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("\u5085\u91CC\u53F6\u53D8\u6362");
		rdbtnNewRadioButton_4.setBounds(0, 171, 157, 27);
		panel.add(rdbtnNewRadioButton_4);
		
		JButton defineBtn = new JButton("\u786E\u5B9A");
		defineBtn.setBounds(0, 218, 95, 27);
		panel.add(defineBtn);
		
		JButton SelectImgBtn = new JButton("选择图片");
		SelectImgBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("open")){
					JFileChooser jf = new JFileChooser();
					jf.showOpenDialog(this);//显示打开的文件对话框
					File f =  jf.getSelectedFile();//使用文件类获取选择器选择的文件
					String s = f.getAbsolutePath();//返回路径名
					//JOptionPane弹出对话框类，显示绝对路径名
					JOptionPane.showMessageDialog(null, s, "标题",JOptionPane.WARNING_MESSAGE);  
				}

			}
		});
		SelectImgBtn.setBounds(703, 13, 113, 27);
		panel.add(SelectImgBtn);
		
		JPanel showImg = new JPanel();
		showImg.setBounds(167, 59, 701, 481);
		panel.add(showImg);
	}
}
