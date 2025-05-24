# FileTransfeApp
This Java-based application allows users to send and receive files over a network. The application features a GUI built with Swing, providing a straightforward interface for selecting files, inputting a receiver’s IP address, and transferring files seamlessly. The application includes a side panel to switch between the “Send” and “Receive” functionalities.

Features:
    File Selection: Choose a file from your computer to send.
    IP Input: Input the receiver's IP address to establish the connection.
    File Transfer: Send files to another peer connected over the network.
    Receive Files: Accept file transfers and choose where to save them.
    File Preview: Optionally open the file after receiving it.
    Status Updates: Visual feedback on the transfer status, including error messages.

Getting Started

  Prerequisites:
    Java 8 or above installed on both the sender's and receiver's machines.
    Both computers must be connected to the same network for IP-based file transfer.
    
  Running the Application:
    Clone the repository or download the source code.
  
    Compile the application using your preferred Java IDE or the command line.
    
    Run the App.java file to start the application.
  
    javac App.java fileTransfer.java
    java App
    
  GUI Overview

    Send Panel:
      Upload Button: Select the file to send.
      Receiver IP: Enter the IP address of the receiver.
      Send File Button: Initiates the file transfer.

    Receive Panel:
      Receive File Button: Start listening for incoming file transfers.
      Save Location: Choose where to save the incoming file.

  How to Use

    Sending a File
      Launch the application and navigate to the Send panel by clicking the Send button on the side panel.
      Click Upload to choose a file to send.
      Enter the Receiver IP (receiver's IP address).
      Click Send File to initiate the transfer.
      
    Receiving a File
      Launch the application and navigate to the Receive panel by clicking the Receive button on the side panel.
      Wait for the sender to initiate a transfer.
      When prompted, choose a directory to save the incoming file.
      After the transfer completes, confirm if you would like to open the received file.