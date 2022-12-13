package flappyBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Flappy extends KeyAdapter implements ActionListener{

	public static Flappy flappybird;
	public final int WIDTH=800,HEIGHT=800;
	public Renderer render; 
	public ArrayList<Rectangle> columns;
	public Random ran;
	public int ticks,ymotion,score=0;
	public boolean gameover=false,started=false;
	Rectangle bird;
	public Flappy()
	{
		 JFrame jframe=new JFrame();
		 Timer timer=new Timer(20,this);
		 render=new Renderer();
		 ran=new Random();
		 jframe.setSize(WIDTH, HEIGHT);
		 jframe.setResizable(false);
		 jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 jframe.addKeyListener(this);
		 jframe.add(render);
		 jframe.setVisible(true);
		 
		 bird = new Rectangle(WIDTH/2-60, HEIGHT/2-10, 20, 20);
		 columns=new ArrayList<Rectangle>();
		 
		 addcolumn(true);
		 addcolumn(true);
		 addcolumn(true);
		 addcolumn(true);
		 
		 timer.start();
		 
	}
	
	
	
	public void repaint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.cyan);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.orange);
		g.fillRect(0, HEIGHT-150, WIDTH, 150);
		g.setColor(Color.green);
		g.fillRect(0, HEIGHT-150, WIDTH, 20);
		
		g.setColor(Color.red);
		g.fillRect(bird.x,bird.y,bird.width,bird.height);
		
		for(Rectangle colum: columns)
		{
			paintColumn(g, colum);
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));
		if(!started)
		{
			g.drawString("Click to start!",90,HEIGHT/2-50);
		}
		if(gameover)
		{
			g.drawString("Game over!",100,HEIGHT/2-50);
		}
		if(!gameover && started)
		{
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("Score:"+score,WIDTH/2-100,100);

		}
					
	}
	public void paintColumn(Graphics g,Rectangle coloumn)
	{
		g.setColor(Color.green.darker());
		g.fillRect(coloumn.x,coloumn.y,coloumn.width, coloumn.height);
	}
	public void addcolumn(boolean start)
	{
		int space=300;
		int width=100,heigth=50+ran.nextInt(350);
		if (start)
		{
			columns.add(new Rectangle(WIDTH+width+columns.size() * 300,HEIGHT- heigth-150,width,heigth));
			columns.add(new Rectangle(WIDTH+width+(columns.size()-1)*300,0,width,HEIGHT-heigth-space));
		}
		else
		{
			columns.add(new Rectangle(columns.get(columns.size()-1).x+600,HEIGHT- heigth-150,width,heigth));
			columns.add(new Rectangle(columns.get(columns.size()-1).x,0,width,HEIGHT- heigth-space));
		}
		
	}
	public void keyPressed(KeyEvent e)
	{
		if(gameover)
		{
			if(e.getKeyCode()==KeyEvent.VK_SPACE)
			{
				bird = new Rectangle(WIDTH/2-60, HEIGHT/2-10, 20, 20);
				 columns.clear();
				 ymotion=0;
				 score=0;
				 
				 addcolumn(true);
				 addcolumn(true);
				 addcolumn(true);
				 addcolumn(true);
				gameover=false;
			}
		}
		if(!started)
		{
			if(e.getKeyCode()==KeyEvent.VK_SPACE)
			{
				started=true;
			}
		}
		else if(!gameover &&started)
		{
			if(ymotion>0)
				ymotion=0;
			ymotion-=13;
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ticks++;
		int speed=10;
		if(started)
		{
			for(int i=0;i<columns.size();i++)
			{
				Rectangle col=columns.get(i);
				col.x-=speed; 
			}
			
			if(ticks%2==0 && ymotion<15)
			{
				ymotion+=2;
			}
			
			for(int i=0;i<columns.size();i++)
			{
				Rectangle col=columns.get(i);
				if(col.x+col.width<0)
				{
					columns.remove(col);
					if(col.y==0)
					{
						addcolumn(false);
					}
				}
				
			}
			bird.y+=ymotion;
			
			for(Rectangle col:columns)
			{
				if(col.y==0 && bird.x+ bird.width/2>col.x+col.width/2 -10 && bird.x+ bird.width/2<col.x+col.width/2 +10)
					score++;
					
				if(col.intersects(bird))
				{
					gameover=true;
					if(bird.x<=col.x)
						bird.x=col.x-bird.width;
					else
					{
						if(col.y!=0)
							bird.y=col.y-bird.height;
						else if(bird.y<col.height)
							bird.y=col.height;
					}
				}
			}
			if(bird.y>HEIGHT-160||bird.y<0)
			{
				gameover=true;
			}
			if(bird.y+ymotion>=HEIGHT-150)
			{
				bird.y=HEIGHT-150-bird.height;
			}
		}
		
	
		render.repaint();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		flappybird=new Flappy();
	}

}
