package dbconnector;

import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class DBConnector {
    URL url5 = Main.class.getResource("/img/logo.png");
    URL url13 = Main.class.getResource("/img/dbconnection.png");
    URL url1 = Main.class.getResource("/img/connection.png");
    URL urlbackuppic = Main.class.getResource("/img/backup.png");

    // final ImageIcon icon2 = new ImageIcon(url1);
    Image iconimage = new ImageIcon(url5).getImage();
    final ImageIcon iconcon = new ImageIcon(url13);
    final ImageIcon icon2 = new ImageIcon(url1);
    final ImageIcon iconbackup = new ImageIcon(urlbackuppic);
    private static String address;
    private static int num = 0;
    public String addressFound;

    public void Get() {
        String ip = null;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    ip = addr.getHostAddress();
                    // System.out.println(iface.getDisplayName() + " " + ip);

                    if (num == 2) {
                        //System.out.println(ip);
                        fetchIP(ip);
                        addressFound = "jdbc:mysql://" + getAddress() + "/dbpharmacy?autoReconnect=true&useSSL=false";
                        break;
                    }
                    num++;
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }


    private void fetchIP(String IP) {
        setAddress(IP);
    }

    //get IP address
    private static String getAddress() {
        return address;
    }

    //set address
    private void setAddress(String address) {
        DBConnector.address = address;
    }


    private static final String USERNAME = "TechGuy";
    private static final String PASSWORD = "jobvinny";

    public static Connection getConnection(String CONN) throws SQLException {
        return DriverManager.getConnection(CONN, USERNAME, PASSWORD);
    }

    /**
     * reget the connection
     */
    public void restartXampp() {
        /**
         * Instantiate this class {@link Connect}
         * */
        DBConnector connect = new DBConnector();
        try {
            //call this function
            connect.Get();
            /**
             * Define String For Connection
             * */
            addressFound = "jdbc:mysql://" + getAddress() + "/dbpharmacy";
            Connection connection = getConnection(addressFound);
            if (connection != null) {
                JOptionPane.showMessageDialog(null, "Connected Successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Failed");
            }
        } catch (SQLException E) {
            JOptionPane.showMessageDialog(null, "A Network Error occurred");
        }
    }

    /**
     * Get a connection
     */
    public void getCon() {
        String[] option = {"Retry", "Ok"};
        Toolkit.getDefaultToolkit().beep();
        int dbstate = JOptionPane.showOptionDialog(null, "Database Connection Failure\nPlease Check The Network", "Notification", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon2, option, option[1]);
        if (dbstate == 0) {
            restartXampp();
        }
        if (dbstate == 1) {
            JOptionPane.showMessageDialog(null, "Please Establish Database Connection", "Notification", JOptionPane.INFORMATION_MESSAGE, icon2);
        }
    }
}
