import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Gui {
    private String w = "w";
    private String u = "u";
    private String p = "p";
    private String key = "";


    public void mainFrame() {
        /*first main window GUI setup
		 * left button moves to create new file frame
		 * right button moves to login frame
		 */
		JFrame mainFrame = new JFrame("Welcome");
		mainFrame.setSize(450, 450);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		mainFrame.setLayout(null);
		mainFrame.setLocationRelativeTo(null);

		JLabel prompt = new JLabel("Welcome to Password Vault!");
		prompt.setBounds(135, 50, 200, 100);
		mainFrame.add(prompt);

		JLabel prompt2 = new JLabel("Choose a file option below:");
		prompt2.setBounds(137, 80, 200, 100);
		mainFrame.add(prompt2);

		JButton one = new JButton("Create new");
		one.setBounds(25,215,150,100);
		mainFrame.add(one);
        
		JButton two = new JButton("Add to existing");
		two.setBounds(250,215,150,100);
		mainFrame.add(two);
        
        one.addActionListener(new ActionListener() {
            @Override
             public void actionPerformed(ActionEvent e) { 
                try {
                    createNewFile();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                mainFrame.setVisible(false);                
             }
         }); 

        two.addActionListener(new ActionListener() {
            @Override
             public void actionPerformed(ActionEvent e) {
                 login(); 
                 mainFrame.setVisible(false);               
             }
         });
    }


    public void login() {
        JFrame mainFrame = new JFrame("Login");
		mainFrame.setSize(450, 450);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		mainFrame.setLayout(null);
		mainFrame.setLocationRelativeTo(null);
    }


    public void createNewFile() throws IOException {
        JFrame frame = new JFrame("New File created");
		frame.setSize(450, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
        JLabel prompt = new JLabel("Creating new File");
		prompt.setBounds(150, 50, 200, 100);
		frame.add(prompt);

        PasswordVault.createFile();

        JButton next = new JButton("Next");
		next.setBounds(140,215,125,75);
		frame.add(next);
        next.addActionListener(new ActionListener() {
            @Override
             public void actionPerformed(ActionEvent e) {
                try {
                    dataInput();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } 
                frame.setVisible(false);               
             }
         });
    }


    public void dataInput() throws IOException {
        JFrame frame = new JFrame("Input Data");
		frame.setSize(450, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
        
        JTextField website = new JTextField("Website",30);
        website.setBounds(50, 75, 200, 35);
        JTextField username = new JTextField("Username",30);
        username.setBounds(50, 125, 200, 35);
        JTextField password = new JTextField("Password",30);
        password.setBounds(50, 175, 200, 35);
        frame.add(website);
        frame.add(username);
        frame.add(password);
        website.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Gui.this.w = website.getText();
            }
        });
        username.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Gui.this.u = username.getText();
            }
        });
        password.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Gui.this.p = password.getText();
            }
        });

        JButton save = new JButton("Save");
		save.setBounds(140,275,125,75);
		frame.add(save);
        save.addActionListener(new ActionListener() {
            @Override
             public void actionPerformed(ActionEvent e) {
                try {
                    PasswordVault.dataInput(w, u, p);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                OptionPage(); 
                frame.setVisible(false);               
             }
         });
        
    }


    public void OptionPage() {
        JFrame frame = new JFrame("Main");
		frame.setSize(450, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);

		JLabel prompt = new JLabel("Click an option below");
		prompt.setBounds(145, 50, 200, 100);
		frame.add(prompt);

        
        JButton one = new JButton("View");
		one.setBounds(75,150,125,75);
		frame.add(one);
        
		JButton two = new JButton("Add");
		two.setBounds(215,150,125,75);
		frame.add(two);

        JButton three = new JButton("Delete");
		three.setBounds(75,250,125,75);
		frame.add(three);

        JButton four = new JButton("Exit");
		four.setBounds(215,250,125,75);
		frame.add(four);

        /*two.addActionListener(new ActionListener() {
            @Override
             public void actionPerformed(ActionEvent e) { 
                 frame.setVisible(false);               
             }
         });*/

        two.addActionListener(new ActionListener() {
            @Override
             public void actionPerformed(ActionEvent e) {
                 try {
                    dataInput();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } 
                 frame.setVisible(false);               
             }
        });

        /*three.addActionListener(new ActionListener() {
            @Override
             public void actionPerformed(ActionEvent e) { 
                 frame.setVisible(false);               
             }
        });*/

        four.addActionListener(new ActionListener() {
            @Override
             public void actionPerformed(ActionEvent e) {
                 createKey(); 
                 frame.setVisible(false);               
             }
         });
    }


    public void createKey() {
        JFrame frame = new JFrame("Create Key");
        frame.setSize(450, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        JLabel prompt = new JLabel("Please create 8 digit secret key to encrypt file: ");
		prompt.setBounds(80, 100, 275, 100);
		frame.add(prompt);

        JTextField keyin = new JTextField("8-digit key");
        keyin.setBounds(100, 175, 225, 35);
        frame.add(keyin);
        
        JButton enc = new JButton("Encrypt");
		enc.setBounds(140,275,125,75);
		frame.add(enc);
        
        keyin.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Gui.this.key = keyin.getText();
                PasswordVault.encryptFile(key);
                File file = new File("password.txt");
				file.delete();
                frame.setVisible(false); 
            }
        });

    }

}