import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FontFormatException;

public class App{
    private JFrame frame;
    private CardLayout mainCardLayout;
    private JPanel mainPanel;
    private JTextField selectedFilePathField;
    private JTextField receiverIdField;
    private JFileChooser fileChooser;
    private JPanel sidePanel;
    private JPanel sendFilePanel;
    private JPanel receiveFilePanel;
    private JButton sendButton;
    private JButton receiveButton;
    String receivedFileName;
    String currentCard;
    Font interFont;

    

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    App app = new App();
                    app.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public App() {
        try{
        interFont = Font.createFont(Font.TRUETYPE_FONT,new File("Inter-VariableFont_opsz,wght.ttf"));
    }
    catch(IOException | FontFormatException e){
        e.printStackTrace();
    }
        initialize();
    }

    public void initialize() {


        frame = new JFrame("File transfer app");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

       sidePanel = new JPanel();
       sidePanel.setBounds(0,0,200,600);
       sidePanel.setBackground(new Color(49,140,239));
       sidePanel.setLayout(null);
       frame.getContentPane().add(sidePanel);


       //send Button on side panel
       sendButton = new JButton("Send");
       sendButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
            mainCardLayout.show(mainPanel, "sendFilePanel");
            currentCard = "sendFilePanel";
            updateColors();
           }
        });
       sendButton.setFont(interFont.deriveFont(14f));
       sendButton.setFocusPainted(false); // setFocusPainted removes the small border around the text on the button
       sendButton.setForeground(Color.white);
       sendButton.setBackground(new Color(12,8,86));
       sendButton.setBounds(30, 200, 120, 40);
       sidePanel.add(sendButton);


       //receive Button on side panel
       receiveButton = new JButton("Receive");
       receiveButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
            mainCardLayout.show(mainPanel, "receiveFilePanel");
            currentCard = "receiveFilePanel";
            updateColors();
           }
        });
       receiveButton.setFont(interFont.deriveFont(14f));
       receiveButton.setFocusPainted(false);
       receiveButton.setBounds(30, 260, 120, 40);
       sidePanel.add(receiveButton);


       mainCardLayout = new CardLayout();
       mainPanel = new JPanel();
       mainPanel.setBounds(200,0,800,600);
       mainPanel.setLayout(mainCardLayout);
       frame.getContentPane().add(mainPanel);


       initializePanels();


}

    private void initializePanels() {
        sendFilePanel = new JPanel();
        sendFilePanel.setLayout(null);
        sendFilePanel.setBackground(new Color(240,253,253));
        mainPanel.add(sendFilePanel,"sendFilePanel");

       
       
       JLabel heading = new JLabel("Let's send a File!");
       heading.setFont(interFont.deriveFont(Font.BOLD,16f));
       heading.setForeground(Color.darkGray);
       heading.setBounds(320, 100, 150, 45);
       sendFilePanel.add(heading);

       JLabel sendFileStep1 = new JLabel(" Step-1: To send a file just click upload to select a file from your computer. ");
       sendFileStep1.setBounds(140, 140, 800, 45);
       sendFileStep1.setFont(interFont.deriveFont(14f));
       sendFilePanel.add(sendFileStep1);

       JLabel sendFileStep2 = new JLabel(" Step-2: Then enter the receiver IP address and click send.");
       sendFileStep2.setBounds(140, 170, 800, 45);
       sendFileStep2.setFont(interFont.deriveFont(14f));

       sendFilePanel.add(sendFileStep2);

        selectedFilePathField = new JTextField();
        selectedFilePathField.setToolTipText("Selected file");
        selectedFilePathField.setColumns(10);
        selectedFilePathField.setBounds(140, 230, 350, 40);
        sendFilePanel.add(selectedFilePathField);

        JButton uploadButton = new JButton("Upload");
        uploadButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(sendFilePanel)) { // Changed from receiverPanel to appPanel
                    String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    selectedFilePathField.setText(selectedFilePath);
                }
            }
        });

        uploadButton.setFont(interFont.deriveFont(Font.BOLD,12f));
        uploadButton.setBounds(500, 230, 100, 35);
        uploadButton.setFocusPainted(false);

        uploadButton.setForeground(Color.white);
        uploadButton.setBackground(new Color(34,28,161));
        sendFilePanel.add(uploadButton);

        JLabel receiverIdLabel = new JLabel("Receiver IP:");
        receiverIdLabel.setFont(interFont.deriveFont(16f));
        receiverIdLabel.setBounds(140, 280, 120, 45);
        sendFilePanel.add(receiverIdLabel);

        receiverIdField = new JTextField();
        receiverIdField.setToolTipText("Receiver ID or IP");
        receiverIdField.setColumns(10);
        receiverIdField.setBounds(260, 285, 350, 40);
        sendFilePanel.add(receiverIdField);

        JButton sendFileButton = new JButton("Send File");
        sendFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String receiverId = receiverIdField.getText();
                String filename = selectedFilePathField.getText();  // Use selectedFilePathField.getText() here
                boolean isFileNameSent = fileTransfer.sendFilename(receiverId, filename);  // Corrected the variable name
                if (isFileNameSent) {
                    System.out.println("File name sent successfully");
                    boolean receiverAck = fileTransfer.waitForReceiverAck();
                    if (receiverAck) {
                        System.out.println("Receiver accepted file transfer");
                        boolean isFileSent = fileTransfer.sendFile(filename, receiverId);
                        if (isFileSent) {
                            JOptionPane.showMessageDialog(sendFilePanel, "File Transfer Done");
                        } else {
                            System.out.println("Error in sending file");
                        }
                    } else {
                        System.out.println("Problem waiting for acknowledgment.");
                    }
                } else {
                    System.out.println("Error sending file name");
                }
            }
        });

        sendFileButton.setFont(interFont.deriveFont(Font.BOLD,12f));
        sendFileButton.setFocusPainted(false);
        sendFileButton.setBounds(300, 350, 100, 35);
        sendFileButton.setForeground(Color.white);
        sendFileButton.setBackground(new Color(52,162,27));
        sendFilePanel.add(sendFileButton);

        JLabel title = new JLabel("File Transfer App");
       title.setFont(interFont.deriveFont(Font.BOLD,24f));
       title.setBounds(280, 40, 400, 45);
       title.setForeground(new Color(34,28,161));
       sendFilePanel.add(title);

        //receiveFilePanel

        receiveFilePanel = new JPanel();
        receiveFilePanel.setLayout(null);
        receiveFilePanel.setBackground(new Color(240,250,255));
        mainPanel.add(receiveFilePanel,"receiveFilePanel");

        JLabel title2 = new JLabel("File Transfer App");
       title2.setFont(interFont.deriveFont(Font.BOLD,24f));
       title2.setBounds(280, 40, 400, 45);
       title2.setForeground(new Color(34,28,161));
       receiveFilePanel.add(title2);


        JLabel receiveText = new JLabel("Receive File");
        receiveText.setFont(interFont.deriveFont(Font.BOLD,16f));
        receiveText.setForeground(Color.darkGray);
        receiveText.setBounds(320, 100, 150, 45);
        receiveFilePanel.add(receiveText);

        JLabel receiveFileStep1 = new JLabel("Step-1 :- After clicking send on the send peer click receive here. ");
        receiveFileStep1.setBounds(140, 140, 800, 45);
        receiveFileStep1.setFont(interFont.deriveFont(14f));
        receiveFilePanel.add(receiveFileStep1);

        JLabel receiveFileStep2 = new JLabel("Step-2 :- A pop-up will be displayed to select the location to save the file");
        receiveFileStep2.setBounds(140, 170, 800, 45);
        receiveFileStep2.setFont(interFont.deriveFont(14f));

        receiveFilePanel.add(receiveFileStep2);



        // JLabel savePathLabel = new JLabel("Path to Save:");
        // savePathLabel.setFont(new Font("Bahnschrift", Font.BOLD, 18));
        // savePathLabel.setBounds(140, 250, 144, 65);
        // receiveFilePanel.add(savePathLabel);

        // savePathField = new JTextField();
        // receiverIdField.setToolTipText("Receiver ID or IP");
        // receiverIdField.setColumns(10);
        // savePathField.setBounds(260, 260, 350, 50);
        // receiveFilePanel.add(savePathField);

        JButton receiveFileButton = new JButton("Receive File");
        receiveFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                receivedFileName = fileTransfer.receiveFilename();
                if (receivedFileName.length() > 0) {
                    System.out.println("Received file name: " + receivedFileName);
                    int option = JOptionPane.showConfirmDialog(receiveFilePanel, "Do you want to receive this file: " + receivedFileName, null, JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        System.out.println("File received accepted.");
                        String saveDirectory = "";
                        JFileChooser directoryChooser = new JFileChooser(FileSystemView.getFileSystemView());
                        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        if (JFileChooser.APPROVE_OPTION == directoryChooser.showOpenDialog(sendFilePanel)) {  
                            saveDirectory = directoryChooser.getSelectedFile().getAbsolutePath();
                            fileTransfer.sendReceiverAck(true);
                            if (fileTransfer.receiveFile(receivedFileName, saveDirectory)) {
                                int confirmation = JOptionPane.showConfirmDialog(receiveFilePanel, "File received successfully.\nDo you want to open the file?", null, JOptionPane.YES_NO_OPTION);
                                if (confirmation == JOptionPane.YES_OPTION) {
                                    try {
                                        File file = new File(saveDirectory, receivedFileName);
                                        Desktop.getDesktop().open(file);
                                    } catch (IOException e1) {
                                        e1.printStackTrace(); 
                                    }
                                }
                            }
                        } else {
                            System.out.println("Please select a directory.");
                        }
                    } else {
                        System.out.println("File transfer rejected.");
                    }
                }
            }
        });

        receiveFileButton.setFont(interFont.deriveFont(Font.BOLD,12f));
        receiveFileButton.setFocusPainted(false);
        receiveFileButton.setBounds(300, 240, 130, 35);
        receiveFileButton.setForeground(Color.white);
        receiveFileButton.setBackground(new Color(34,28,161));
        receiveFilePanel.add(receiveFileButton);


    }

    private void updateColors() {
        if(currentCard.equals("sendFilePanel")){
            sendButton.setBackground(new Color(0,33,71));
            sendButton.setForeground(Color.white);
            sendButton.setFont(interFont.deriveFont(14f));
            receiveButton.setBackground(Color.white);
            receiveButton.setForeground(new Color(0,33,71));
            receiveButton.setFont(interFont.deriveFont(14f));


        }
        else{
            sendButton.setBackground(Color.white);
            sendButton.setForeground(new Color(0,33,71));
            sendButton.setFont(interFont.deriveFont(14f));

            receiveButton.setBackground(new Color(0,33,71));
            receiveButton.setForeground(Color.white);
            receiveButton.setFont(interFont.deriveFont(14f));
        }
    }
}