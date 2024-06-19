package chatting;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.net.*;
import java.io.*;

public class Client implements ActionListener{
    JTextField text;
    static JPanel a1;
    static Box vertical= Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f=new JFrame();
    Client()
    {
        f.setSize(450, 700);
        f.setLocation(800,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.white);
        
        f.setLayout(null);

        JPanel p1=new JPanel();
        p1.setBackground(new Color(140,140,140));
        p1.setBounds(0, 0, 450,70);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon li= new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2=li.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel back= new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });

        ImageIcon i4= new ImageIcon(ClassLoader.getSystemResource("icons/meetme.png"));
        Image i5=i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
        JLabel profile= new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);

        JLabel name= new JLabel("Adhil");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIF", Font.BOLD,18));
        p1.add(name);

        JLabel status= new JLabel("Active now");
        status.setBounds(110, 35, 100, 18);
        status.setForeground(Color.white);
        status.setFont(new Font("SAN_SERIF", Font.BOLD,14));
        p1.add(status);

        a1= new JPanel();
        a1.setBounds(5,75,440,570);
        f.add(a1);

        text=new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(text);
        
        JButton send=new JButton("SEND");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(48,163,230));
        send.setForeground(Color.white);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(send);




        f.setVisible(true );
    }
    public void actionPerformed(ActionEvent ae)
    {
        try{
        String out=text.getText();
        a1.setLayout(new BorderLayout());


        JPanel p2=formatLabel(out);

        JPanel right=new JPanel(new BorderLayout());
        right.add(p2, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));

        a1.add(vertical,BorderLayout.PAGE_START);

        dout.writeUTF(out);

        text.setText("");

        f.repaint();
        f.invalidate();
        f.validate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        };

    }

    public static JPanel formatLabel(String out)
    {
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS ));

        JLabel output=new JLabel(out);
        output.setFont(new Font("Tahoma", Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));


        panel.add(output);

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");

        JLabel time= new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);


        return panel;
    }
    public static void main(String[] args) {
        new Client();
        try{
            Socket s=new Socket("127.0.0.1",6001);
            DataInputStream din=new DataInputStream(s.getInputStream());
            dout=new DataOutputStream(s.getOutputStream());
            while(true)
                {
                    a1.setLayout(new BorderLayout());

                    String msg=din.readUTF();
                    JPanel panel=formatLabel(msg);

                    JPanel left= new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);

                    vertical.add(Box.createVerticalStrut(15));
                    a1.add(vertical,BorderLayout.PAGE_START);
                    f.validate();
                
                }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        };
    }
}