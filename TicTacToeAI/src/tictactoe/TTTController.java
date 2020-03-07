package tictactoe;

//Core Swing.AWT packages 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

/**
 *The Controller / Event Listener Class for TTT 
 */
public class TTTController implements 
		ActionListener, 
		WindowListener  {
	
	private TTTGame gameModel; // TTT Game Model instance 
	private TTTGameView gameView;  // The View 

	// default constructor 
	public TTTController() {
		init();
	}
	
	public void setGameView(TTTGameView gameView) {
		this.gameView = gameView;
	}
	
	
	// private instance methods 
	private void init() {
		gameModel = new TTTGame();
	}
	

	@Override
	public void actionPerformed(ActionEvent ae) {
		
		Object source = ae.getSource();
		System.out.println("TTTController actionPerformed: "  + source.getClass().getSimpleName());
		
		// reset button 
		if(source == this.gameView.getResetButton() )  {
			gameView.reset(gameModel.getWon());
			gameModel.resetGame();
		}
	
		else if(source == this.gameView.getModeButton()) {
			if (this.gameView.getModeButton().getText()=="2 Players") {
				this.gameView.getModeButton().setText("1 Player");
				this.gameModel.switchMode();
				this.gameView.reset(gameModel.getWon());
				this.gameModel.resetGame();
			}
			else
			{
				this.gameView.getModeButton().setText("2 Players");
				this.gameModel.switchMode();
				this.gameView.reset(gameModel.getWon());
				this.gameModel.resetGame();
			}
		}
		// One of the 9 TTT board button 
		else if(source.getClass().equals(JButton.class) ) {
			
			boardActionPerformed((JButton)source);
			if(!gameModel.isTwoPlayer()) {
				boolean stop = false;
				int position = 1;
				int[] spot=new int[2];
				spot=gameModel.pickBestMove(gameView.getCurentPlayer().getText().charAt(0));
				for(int i=0;i<3&&stop==false;i++)
					for(int j=0;j<3;j++) {
						if(i==spot[0]-1&&j==spot[1]-1) {
							stop=true;
							break;
						}
						else 
							position++;
					}
				gameView.takePosition(position,
						gameModel.getWon(), gameModel.getWinCombinationCode() );
				
				System.out.println(gameModel);
			}
		
		}
		
		
	}
	
	private void boardActionPerformed(JButton button) {
		// determine what board position has been selected.  
		System.out.println("TTTController actionPerformed: "  + button.getName());
		
		/**
		 * determine r,c for setPostion_() invocation 
		 * determine which setPosition_() method to invoke X or O based on gameView.getCurrentSymbol()
		 * 
		 */
		int position = Integer.parseInt(button.getName() );
		int mod = position % 3;
		int quo = position / 3;
		
		int r,c; 
		if(mod > 0)  {
			r = quo + 1;
			c = mod;
		}
		else {
			r = quo;
			c = 3;
		}
		
		
		if(gameView.getCurentPlayer().getText().equalsIgnoreCase("X") )  {
			gameModel.setPositionX(r, c);
		}
		else {
			gameModel.setPositionO(r, c);
		}
		
		gameView.takePosition(position,
				gameModel.getWon(), gameModel.getWinCombinationCode() );
		
		System.out.println(gameModel);
		
	}
	


	
	@Override
	public void windowOpened(WindowEvent e) {
		System.out.println("windowOpened() Event");

	}

	@Override
	public void windowClosing(WindowEvent e) {
		String ObjButtons[] = {"YES","NO"};
		
	    int rc = JOptionPane.showOptionDialog(null, 
	        "Are you sure you want to exit?", "Window Closing Event", 
	        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
	        ObjButtons,ObjButtons[1]);
	    
	    System.out.println("windowClosing() " + rc);
	    

	    if(rc == 0)   
	    	System.exit(0);  
	    // otherwise, stay alive

	}

	@Override
	public void windowClosed(WindowEvent e) {
        

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TTTGameView.present();

	}

}
