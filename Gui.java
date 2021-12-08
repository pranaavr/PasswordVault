import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Gui {

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
                createNewFile();
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



    public void createNewFile() {
        JFrame frame = new JFrame("New File created");
		frame.setSize(450, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
        JLabel prompt = new JLabel("Creating new File");
		prompt.setBounds(150, 50, 200, 100);
		frame.add(prompt);

        JButton next = new JButton("Next");
		next.setBounds(140,215,125,75);
		frame.add(next);
        next.addActionListener(new ActionListener() {
            @Override
             public void actionPerformed(ActionEvent e) {
                 dataInput(); 
                 frame.setVisible(false);               
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


    public void dataInput() {
        JFrame mainFrame = new JFrame("Input Data");
		mainFrame.setSize(450, 450);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		mainFrame.setLayout(null);
		mainFrame.setLocationRelativeTo(null);
        JLabel prompt = new JLabel("Creating new File");
		prompt.setBounds(150, 50, 200, 100);
		mainFrame.add(prompt);
    }
}