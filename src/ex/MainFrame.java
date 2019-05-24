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
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.ImageIcon;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField text1;
	

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
					
		JButton defineBtn = new JButton("确定");
		defineBtn.setBounds(9, 219, 95, 27);
		panel.add(defineBtn);
		
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
		
		JLabel 显示图 = new JLabel("");
		显示图.setBounds(494, 0, 480, 480);
		showImg.add(显示图);
		
		text1 = new JTextField();
		text1.setBounds(360, 14, 317, 24);
		panel.add(text1);
		text1.setColumns(10);
		
		JTree tree = new JTree();
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("选择功能") {
				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("实验二");
						node_1.add(new DefaultMutableTreeNode("图片平移"));
						node_1.add(new DefaultMutableTreeNode("图片旋转"));
						node_1.add(new DefaultMutableTreeNode("图片缩放"));
						node_1.add(new DefaultMutableTreeNode("傅里叶变换"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("实验三");
						node_1.add(new DefaultMutableTreeNode("basketball"));
						node_1.add(new DefaultMutableTreeNode("soccer"));
						node_1.add(new DefaultMutableTreeNode("football"));
						node_1.add(new DefaultMutableTreeNode("hockey"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("实验四");
						node_1.add(new DefaultMutableTreeNode("hot dogs"));
						node_1.add(new DefaultMutableTreeNode("pizza"));
						node_1.add(new DefaultMutableTreeNode("ravioli"));
						node_1.add(new DefaultMutableTreeNode("bananas"));
					add(node_1);
				}
			}
		));
		tree.setBounds(9, 13, 144, 193);
		panel.add(tree);
		

		SelectImgBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if (e.getActionCommand().equals(SelectImgBtn)){
					jfc.setFileSelectionMode(0);// 设定只能选择到文件
					int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
					if (state == 1) {
						return;// 撤销则返回
					} else {
						File f = jfc.getSelectedFile();// f为选择到的文件
						text1.setText(f.getAbsolutePath());
						yuantu.setIcon(new ImageIcon(f.getAbsolutePath()));
					}
				//}

			}
		});

	}
}
