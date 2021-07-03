package myGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Logic extends JFrame implements ActionListener{
	JLabel heading, clockLabel;
	JPanel mainpanel;
	
	JButton [] btns = new JButton[9];
	
	
	//game instance variables..
	
	int gamechances [] = {2,2,2,2,2,2,2,2,2};
	int activeplayer = 0;
	
	int wps [][] = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
	
	int winner = 2;
	boolean gameover = false;
	
	Font font = new Font("",Font.BOLD,40);
	Logic()
	{
		System.out.println("Started...");
		setTitle("Tic-Tac-Toe");
		setSize(850,850);
		ImageIcon icon = new ImageIcon("src/Img/Tictoe.png");
		setIconImage(icon.getImage());
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createGUI();
		setVisible(true);
	}
	
	private void createGUI()
	{	
		this.getContentPane().setBackground(Color.decode("#393E46"));
		this.setLayout(new BorderLayout());
		
		//north heading
		
		heading = new JLabel("Tic Tac Toe");
		heading.setIcon(new ImageIcon("src/Img/ti.png"));
		heading.setFont(font);
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		heading.setForeground(Color.white);
		heading.setHorizontalTextPosition(SwingConstants.CENTER);
		heading.setVerticalTextPosition(SwingConstants.BOTTOM);
		this.add(heading,BorderLayout.NORTH);
		
		
		//creating JLabel for clock
		
		clockLabel = new JLabel("clock");
		clockLabel.setFont(font);
		clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
		clockLabel.setForeground(Color.white);
		this.add(clockLabel,BorderLayout.SOUTH);
		
		
		Thread t = new Thread() {
			public void run()
			{
				 try
				 {
					 while(true)
					 {
						 String datetime = new Date().toLocaleString();
						 clockLabel.setText(datetime);
						 
						 Thread.sleep(1000);
					 }
					 
				 }catch(Exception e)
				 {
					 e.printStackTrace();
				 }
				
			}
		};
		t.start();
		
		//////////////Panel Section
		
		mainpanel = new JPanel();
		
		mainpanel.setLayout(new GridLayout(3, 3));
		
		for(int i = 1; i <= 9; i++)
		{
			JButton btn = new JButton();
			//btn.setIcon(new ImageIcon("src/Img/playO.png"));
			btn.setBackground(Color.decode("#DDDDDD"));
			btn.setFont(font);
			mainpanel.add(btn);
			btns[i - 1] = btn;
			btn.addActionListener(this);
			btn.setName(String.valueOf(i-1));
		}
		
		this.add(mainpanel,BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton currentbutton = (JButton) e.getSource();
		String namestr = currentbutton.getName();
		
		int name = Integer.parseInt(namestr.trim());
		
		
		if(gameover) 
		{
			JOptionPane.showMessageDialog(this, "Game Over!");
			return;
		}
		
		if(gamechances[name] == 2)
		{
			if(activeplayer == 1)
			{
				currentbutton.setIcon(new ImageIcon("src/Img/x.png"));//x
				
				gamechances[name] = activeplayer;
				activeplayer = 0;
			}else
			{
				currentbutton.setIcon(new ImageIcon("src/Img/o.png"));//o
				
				gamechances[name] = activeplayer;
				activeplayer = 1;
			}
			
			//find the winner...
			
			for(int [] temp : wps)
			{
				if((gamechances[temp[0]] == gamechances[temp[1]]) &&
						(gamechances[temp[1]] == gamechances[temp[2]]) && (gamechances[temp[2]]!=2))
				{
					winner  = gamechances[temp[0]];
					gameover = true;
					JOptionPane.showMessageDialog(null, "Player "+ winner + " has won the match");
					int i =  JOptionPane.showConfirmDialog(this, "Want to play one more round?");
					if(i == 0)
					{
						this.setVisible(false);
						new Logic();
					}
					else if(i == 1)
					{
						System.exit(0);
					}else
					{
						
					}
					System.out.println(i);
					break;
				}	
				
			}
			// draw logic
			int count = 0;
			for(int x : gamechances)
			{
				if(x == 2)
				{
					count++;
					break;
				}
			}
			if(count == 0 && gameover == false)
			{
				JOptionPane.showMessageDialog(this, "It's Draw...");
				int i = JOptionPane.showConfirmDialog(this, "Want to play one more round?");
				
				if(i==0)
				{
					this.setVisible(false);
					new Logic();
				}else if(i == 1)
				{
					System.exit(00);
				}else
				{}
				gameover = true;
			}
		}else
		{
			JOptionPane.showMessageDialog(this, "position already occupied");
		}
	}
}
