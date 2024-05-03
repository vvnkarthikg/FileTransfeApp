import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JSeparator;

import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;


import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class welcome {

	private JFrame frame;
	private CardLayout cardLayout;
	private JPanel panel;
	private JPanel card3send;
	private CardLayout tnrcdlayout;
	private JPanel panel_1;
	private JPanel rcvpnl;
	private JTextField textField_3;
	private JTextField textField_4;
	private static JTextField textField_6;
	private JFileChooser flchooser;
	String stflname;
	
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					welcome window = new welcome();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public welcome() {
		initialize();
	}


	private void initialize() {
		frame = new JFrame();
		frame.setSize(720,720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("File Transfer App");
		lblNewLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 20));
		lblNewLabel.setBounds(264, 26, 174, 52);
		frame.getContentPane().add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(163, 76, 391, 10);
		frame.getContentPane().add(separator);
		
		cardLayout=new CardLayout();
		
		panel = new JPanel();
		panel.setBounds(10, 105, 686, 501);
		frame.getContentPane().add(panel);
		panel.setLayout(cardLayout);
		
		card3send=new JPanel();
		card3send.setBackground(Color.CYAN);
		card3send.setLayout(null);
		
		JButton btnsendcd3 = new JButton("Send");
		btnsendcd3.setFont(new Font("Bahnschrift", Font.BOLD, 18));
		btnsendcd3.setBounds(10, 158, 100, 35);
		card3send.add(btnsendcd3);
		
		btnsendcd3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tnrcdlayout.show(panel_1, "sndpnl");
			}
		});
		
		
		
		JButton btnReceive = new JButton("Receive");
		btnReceive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tnrcdlayout.show(panel_1,"name_286468410705700");
			}
		});
		btnReceive.setFont(new Font("Bahnschrift", Font.BOLD, 18));
		btnReceive.setBounds(10, 206, 100, 35);
		card3send.add(btnReceive);
		
		tnrcdlayout=new CardLayout();
		
		panel_1 = new JPanel();
		panel_1.setBounds(129, 41, 547, 404);
		card3send.add(panel_1);
		panel_1.setLayout(tnrcdlayout);
		
		JLabel lblNewLabel_2 = new JLabel("Click to send or Receive FIles");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(144, 10, 462, 21);
		card3send.add(lblNewLabel_2);
		
		
		
		
		addpanels();
		insidepanels();
		
	}
	
	void insidepanels() {
		JPanel sndpnl=new JPanel();
		sndpnl.setBackground(Color.green);
		
		
		panel_1.add(sndpnl,"sndpnl");
		sndpnl.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Select file");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				flchooser=new JFileChooser(FileSystemView.getFileSystemView());
				if(JFileChooser.APPROVE_OPTION==flchooser.showOpenDialog(panel_1)) {
					String filechoosen=flchooser.getSelectedFile().getAbsolutePath();
					textField_3.setText(filechoosen);
				}
				
			}
		});
		btnNewButton_1.setFont(new Font("Bahnschrift", Font.BOLD, 14));
		btnNewButton_1.setBounds(36, 109, 121, 29);
		sndpnl.add(btnNewButton_1);
		
		textField_3 = new JTextField();
		textField_3.setToolTipText("Selected file");
		textField_3.setColumns(10);
		textField_3.setBounds(181, 108, 311, 29);
		sndpnl.add(textField_3);
		
		
		JButton btnSendin = new JButton("Send");
		btnSendin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filename=textField_3.getText();
				String ipid=textField_4.getText();
				boolean flnmsnt=fle_transfer.sendFilename(ipid, filename);
				if(flnmsnt) {
					System.out.println("Line 402 file name sent scfl");
					boolean rcvrack=fle_transfer.waitForReceiverAck();
					if(rcvrack) {
						System.out.println("receiver accepted file transfer line 405");
						boolean mnbfl=fle_transfer.sendFile(filename, ipid);
						if(mnbfl) {
							JOptionPane.showMessageDialog(sndpnl, "Done");
						}else {
							System.out.println("line 410 problem in sendfile");
						}
					}else {
						System.out.println("Problem in 411 wait for ack");
					}
				}else {
					System.out.println("line 412 problem sending file name");
				}
			}
		});
		btnSendin.setFont(new Font("Bahnschrift", Font.BOLD, 18));
		btnSendin.setBounds(118, 203, 100, 35);
		sndpnl.add(btnSendin);
		
		JLabel lblNewLabel_1 = new JLabel("Receiver ID\\ip: ");
		lblNewLabel_1.setFont(new Font("Bahnschrift", Font.BOLD, 15));
		lblNewLabel_1.setBounds(46, 148, 116, 35);
		sndpnl.add(lblNewLabel_1);
		
		textField_4 = new JTextField();
		textField_4.setToolTipText("Selected file");
		textField_4.setColumns(10);
		textField_4.setBounds(180, 147, 255, 40);
		sndpnl.add(textField_4);
		
		
		
		rcvpnl = new JPanel();
		rcvpnl.setLayout(null);
		panel_1.add(rcvpnl, "name_286468410705700");
		
		textField_6 = new JTextField();
		textField_6.setBounds(228, 155, 212, 42);
		rcvpnl.add(textField_6);
		
		JButton btnReceive = new JButton("Receive");
		btnReceive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stflname=fle_transfer.receiveFilename();
				if(stflname.length()>0) {
					System.out.println("line 285 file name received :"+stflname);
					int opt=JOptionPane.showConfirmDialog(rcvpnl, "you want to receive this file :"+stflname,null, JOptionPane.YES_NO_OPTION );
					if(opt==JOptionPane.YES_OPTION) {
						System.out.println("you accepted this file line 288");
						String filepath="";
						JFileChooser flc=new JFileChooser(FileSystemView.getFileSystemView());
						flc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						if(JFileChooser.APPROVE_OPTION==flc.showOpenDialog(panel_1)) {
							filepath=flc.getSelectedFile().getAbsolutePath();
							textField_3.setText(filepath);
							fle_transfer.sendReceiverAck(true);
							if(fle_transfer.receiveFile(stflname,filepath)) {
								int a=JOptionPane.showConfirmDialog(rcvpnl, "File Received Successfully\n You want to open the file?",null, JOptionPane.YES_NO_OPTION);
								if(a==JOptionPane.YES_OPTION) {
									try {
							            File file = new File(filepath,stflname);
							            Desktop.getDesktop().open(file);
							        } catch (IOException e1) {
							            e1.printStackTrace(); // Handle any IO exceptions
							        }
								}
							}
						}else {
							System.out.println("Select dirs only line 297");
						}
						
					}else {
						System.out.println("You reject line 291");
					}
				}
			}
		});
		btnReceive.setFont(new Font("Bahnschrift", Font.BOLD, 18));
		btnReceive.setBounds(160, 97, 128, 35);
		rcvpnl.add(btnReceive);
		
		JLabel lblNewLabel_3 = new JLabel("Path to Save:");
		lblNewLabel_3.setFont(new Font("Sylfaen", Font.BOLD, 18));
		lblNewLabel_3.setBounds(103, 161, 115, 32);
		rcvpnl.add(lblNewLabel_3);
		
		
		
	}
	
	void addpanels() {
		panel.add(card3send,"send_page_card");
		
	}

}
