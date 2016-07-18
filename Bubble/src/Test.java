import javax.swing.*;
import java.awt.*;
public class Test extends JFrame implements Runnable{
	private ImageIcon imageBG;
	private Thread thread1;
	private int smallBubbleCount = 15;
	private int bigBubbleCount = 6;
	private int timerX[] = new int[smallBubbleCount+bigBubbleCount];
	private int timerY[] = new int[smallBubbleCount+bigBubbleCount];
	
	private int[] arraySpeedX = new int[smallBubbleCount+bigBubbleCount];
	private int[] arraySpeedY = new int[smallBubbleCount+bigBubbleCount];
	private int[] arrayPositionX = new int[smallBubbleCount+bigBubbleCount];
	private int[] arrayPositionY = new int[smallBubbleCount+bigBubbleCount];
	private ImageIcon[] arrayImageSmallBubble = new ImageIcon[smallBubbleCount+bigBubbleCount];
	//private ImageIcon[] arrayImageBigBubble = new ImageIcon[bigBubbleCount];
	Test()
	{
		//this.setUndecorated(true);
		this.setSize(1500,1500);
		this.setTitle("Screen");
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		imageBG = new ImageIcon("Images\\bubble_002.png");
		thread1 = new Thread(this);
		thread1.start();
		this.setVisible(true);
		
		for(int i =0;i<arrayPositionX.length;i++){
			arrayPositionX[i] = (int)(Math.random()*1000+32);
			arrayPositionY[i] += (int)(Math.random()*1000+32);
			arraySpeedX[i] = (int)(Math.random()*15+5);
			arraySpeedY[i] = (int)(Math.random()*15+5);
			arrayImageSmallBubble[i] = new ImageIcon("Images\\bubble_001.png");
		}
	}
	public void paint(Graphics g){
        super.paint(g);
        g.drawImage(imageBG.getImage(), 0,0,this.getWidth(),this.getHeight(), null);
        for(int i =0;i<arrayPositionX.length;i++){
        	if(i<arrayPositionX.length-bigBubbleCount){
        	   g.drawImage(arrayImageSmallBubble[i].getImage(),arrayPositionX[i],arrayPositionY[i],64,64,null);
        	}else{
        	   g.drawImage(arrayImageSmallBubble[i].getImage(),arrayPositionX[i],arrayPositionY[i],128,128,null);
        	}
		}
	}
	//线程
	public void run(){	
		while(true){
			repaint();
		    ColloderBubble();
			for(int i=0;i<arrayPositionX.length;i++){
			      BubbleMove(i);
			}
		   try{
			   Thread.sleep(150);
			}catch(Exception e){}
		}	
	}
	//泡泡碰撞的处理机制
	public void  ColloderBubble(){
		for(int i=0;i<arrayImageSmallBubble.length;i++){
			for(int j=0;j<arrayImageSmallBubble.length&j!=i;j++){
				int offsetX = (arrayPositionX[i] - arrayPositionX[j])*(arrayPositionX[i] - arrayPositionX[j]);
				int offsetY = (arrayPositionY[i] - arrayPositionY[j])*(arrayPositionY[i] - arrayPositionY[j]);
				if(offsetX + offsetY <= (64*64)){
					arrayPositionX[i] -= arraySpeedX[i];
					arrayPositionX[j] += arraySpeedX[j];
					arrayPositionY[i] -= arraySpeedY[i];
					arrayPositionY[j] += arraySpeedY[j];
				}
			}
		}
	}
	//泡泡移动的处理机制
	public void BubbleMove(int i){	
		 if(arrayPositionX[i]>64 && arrayPositionX[i] <this.getWidth()-64){ 
	        	if(timerX[i] == 0){
	        		arrayPositionX[i] += arraySpeedX[i];
	        		if(arrayPositionY[i]<=64){
	        			arrayPositionY[i]=64;
	        			arrayPositionY[i] += arraySpeedY[i];
	        			timerY[i] = 0;
	        		}else if(arrayPositionY[i]>= this.getHeight()-64){
	        			arrayPositionY[i]=this.getHeight()-64;
	        			arrayPositionY[i] -= arraySpeedY[i];
     				    timerY[i] =1;
     			    }else{
	     				if(timerY[i] == 0){
	     					
	 					    arrayPositionY[i] += arraySpeedY[i];
		        		}else{
		        			arrayPositionY[i] -= arraySpeedY[i];
	        		    }
     			   }		
	        	}
	        	else{
	        		arrayPositionX[i] -= arraySpeedX[i];
	        	}
	        }
	        else{
	        	if(arrayPositionX[i] >= this.getWidth()-64){
	        		arrayPositionX[i] =this.getWidth()-64;
	        		arrayPositionX[i] -= arraySpeedX[i];
	        		timerX[i]= 1;			   	        		
	        	}
	        	else{
	        		arrayPositionX[i] = 64;
	        		arrayPositionX[i] += arraySpeedX[i];
	        		timerX[i] = 0 ;
	        	}
	        }	 
		}
	public static void main(String[] args){
		new Test();
	}
}
