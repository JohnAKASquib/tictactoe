package tictactoe;

// Core Swing.AWT packages 
import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.*;

/**
 * 
 *  View of MVC implementation for TTT Game 
 *  Extends a Frame - expected to be run as standalone program 
 */
public class TTTGameView extends JFrame {
	
	// Static Data Members 
	private static String VERSION = "0.1";
	private static String EMPTY_POSITION_VALUE = "~";
	private static String DEFAULT_TITLE = "Tic Tac Toe (" + VERSION + ")";
	
	private static Integer DEFAULT_X = 200; 
	private static Integer DEFAULT_Y = 200; 
	private static Integer DEFAULT_W = 500; 
	private static Integer DEFAULT_H = 275; 
	
	private static Dimension BOARD_DIM = new Dimension(80, 80);
	
	private static Integer BOARD_POSITIONS = 9;

	/**
	 * Constructor that takes Listener (aka Controller of MVC) as a parameter 
	 * @param listener
	 */
	public TTTGameView(TTTController listener) { // throws HeadlessException {
		actionListener = listener; 
		windowListener = listener; 
		guiInit();
	}
	
	/**
	 * Default constructor - note: all constructors call guiInit() 
	 */
	public TTTGameView() { // throws HeadlessException {
		guiInit();
	}

	/**
	 * 
	 * @param gc
	 */
	public TTTGameView(GraphicsConfiguration gc) {
		super(gc);
		guiInit();
	}

	/*
	public TTTGameView(String title) throws HeadlessException {
		super(title);
		guiInit();
	}

	public TTTGameView(String title, GraphicsConfiguration gc) {
		super(title, gc);
		guiInit();
	}
	*/
	
	/////////////////////////////////////////////////
	// GUI component instance data members 
	private JPanel mainPanel; 
	private JPanel boardPanel; 
	private JPanel upperPanel;
	private JPanel infoPanel; 
	private JPanel controlPanel; 
	private JPanel displayPanel; 
	
	private JButton [] boardPositions;
	private JButton resetButton;
	private JButton modeButton;
	
	private JLabel  currentPlayer;
	private JLabel  currentSymbol;
	private JLabel  currentGame;
	private JLabel  currentGameValue;
	private JLabel  gameOutcome;
	private JLabel  gameOutcomeValue;
	private JLabel  currentMode;
	// END OF GUI components
	/////////////////////////////////////////////////
	
	// data elements that drive logic 
	private int currentGameIndex;
	
	// Listeners (Controllers)  
	private ActionListener actionListener;  
	private WindowListener windowListener;
	
	
	//  METHODS 
	///////////////////////// 
	// GUI Component Getters
	public JLabel getCurentPlayer() {
		return this.currentPlayer;
	}
	
	public JButton getResetButton() {
		return resetButton;
	}

	public JButton getModeButton() {
		return modeButton;
	}
	
	
	

	// other non-public  instance methods 
	private void init() {
		currentGameIndex = 1;
		this.setSize(DEFAULT_W, DEFAULT_H);
		this.setLocation(DEFAULT_X, DEFAULT_X);
		this.setTitle(DEFAULT_TITLE);

	}
	
	private void disableBoard() {
		for(int i = 0 ; i < boardPositions.length ; ++i) {
			boardPositions[i].setEnabled(false);
		}

	}
	
	private void markWin(short code) {

		switch(code)  {
			case 1:
				// top row
				boardPositions[0].setFont( new Font("Dialog", Font.BOLD, 44) );
				boardPositions[1].setFont( new Font("Dialog", Font.BOLD, 44) );
				boardPositions[2].setFont( new Font("Dialog", Font.BOLD, 44) );
				break;
			case 2:
				// middle row
				boardPositions[3].setFont( new Font("Dialog", Font.BOLD, 44) );
				boardPositions[4].setFont( new Font("Dialog", Font.BOLD, 44) );
				boardPositions[5].setFont( new Font("Dialog", Font.BOLD, 44) );
				break;
			case 3:
				// bottom row
				boardPositions[6].setFont( new Font("Dialog", Font.BOLD, 44) );
				boardPositions[7].setFont( new Font("Dialog", Font.BOLD, 44) );
				boardPositions[8].setFont( new Font("Dialog", Font.BOLD, 44) );
				break;
			case 4:
				// left col
				boardPositions[0].setFont( new Font("Dialog", Font.BOLD, 44) );
				boardPositions[3].setFont( new Font("Dialog", Font.BOLD, 44) );
				boardPositions[6].setFont( new Font("Dialog", Font.BOLD, 44) );
				break;
			case 5:
				// middle col
				boardPositions[1].setFont( new Font("Dialog", Font.BOLD, 44) );
				boardPositions[4].setFont( new Font("Dialog", Font.BOLD, 44) );
				boardPositions[7].setFont( new Font("Dialog", Font.BOLD, 44) );
				break;
			case 6:
				// right col
				boardPositions[2].setFont( new Font("Dialog", Font.BOLD, 44) );
				boardPositions[5].setFont( new Font("Dialog", Font.BOLD, 44) );
				boardPositions[8].setFont( new Font("Dialog", Font.BOLD, 44) );
				break;
			case 7:
				// down diag
				boardPositions[0].setFont( new Font("Dialog", Font.BOLD, 44) );
				boardPositions[4].setFont( new Font("Dialog", Font.BOLD, 44) );
				boardPositions[8].setFont( new Font("Dialog", Font.BOLD, 44) );
				break;
			case 8:
				// up diag
				boardPositions[6].setFont( new Font("Dialog", Font.BOLD, 44) );
				boardPositions[4].setFont( new Font("Dialog", Font.BOLD, 44) );
				boardPositions[2].setFont( new Font("Dialog", Font.BOLD, 44) );
				break;
			default:
				break;
		}

	}
	
	private void refreshBoard() {
		for(int i = 0 ; i < boardPositions.length ; ++i) {
			boardPositions[i].setEnabled(true);
			boardPositions[i].setText(EMPTY_POSITION_VALUE);
			boardPositions[i].setFont( new Font("Dialog", Font.PLAIN, 40) );
			// boardPositions[i].setBorder(BorderFactory.createEmptyBorder());
			boardPositions[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}

	}
	
	public void reset(boolean winState)  {
		this.refreshBoard();
		
		
		if(winState)
			currentGameIndex++;
		
		this.currentGameValue.setText(String.format("%d", currentGameIndex));
		this.gameOutcomeValue.setText(" ");
		
	}

	
	/**
	 * perform all actions required when a board position is consumed 
	 * @param position
	 */
	public void takePosition(int position, boolean won, short code)  {
		
		boardPositions[position-1].setText(currentPlayer.getText());
		boardPositions[position-1].setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		
		if(won == false) {
			boardPositions[position-1].setEnabled(false);
			
			toggleCurrentPlayer();  
		}
		else {
			markWin(code); 
			disableBoard();
			this.gameOutcomeValue.setText(this.currentPlayer.getText());
		}
		
	}
	
	
	
	
	private void toggleCurrentPlayer() {
		if(currentPlayer.getText().equalsIgnoreCase("X") ) {
			currentPlayer.setText("O");
		}
		else {
			currentPlayer.setText("X");
		}
		
	}
	
	private void guiInit() {
		init(); 
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainPanel = new JPanel(new BorderLayout(), true); 
		boardPanel = new JPanel(new GridLayout(3,3), true); 
		
		this.boardPositions = new JButton[BOARD_POSITIONS];
		
		for(int i = 0 ; i < boardPositions.length ; ++i) {
			boardPositions[i] = new JButton(EMPTY_POSITION_VALUE);
			boardPositions[i].setName( Integer.toString(i + 1) );
			boardPositions[i].setFont( new Font("Dialog", Font.PLAIN, 40) );
			boardPositions[i].setPreferredSize(BOARD_DIM);
			boardPositions[i].setBackground(Color.WHITE);
			boardPanel.add( boardPositions[i] );
		}
		
		upperPanel= new JPanel(new FlowLayout(),true);
		
		infoPanel = new JPanel(new GridLayout(2,1), true); 
		// infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT), true);
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2, true) );
		displayPanel = new JPanel(new GridLayout(3,2), true); 
		
		
		// controlPanel = new JPanel(new GridLayout(1, 2, 1, 1), true); 
		controlPanel = new JPanel(new GridLayout(1, 2), true); 
		
		/////////////////////////////////////////////////////
		resetButton = createControlButton("Reset");
		modeButton = createControlButton("2 Players");
		currentMode = createSymbolLabel("Mode: ");
		currentPlayer = createSymbolLabel("X");
		
		currentSymbol = createStatusLabel("Current: ");
		currentGame = createStatusLabel("Game: ");
		gameOutcome = createStatusLabel("Winner: ");
		
		///////////////////////////////////////////////////////////
		controlPanel.add(resetButton);
		
		upperPanel.add(currentMode);
		upperPanel.add(modeButton);
		
		// displayPanel.add(O);
		displayPanel.add(currentSymbol);
		displayPanel.add(currentPlayer);
		
		displayPanel.add(currentGame);
		// stub out empty value 
		currentGameValue = createSymbolLabel( String.format("%d", currentGameIndex) );
		displayPanel.add( currentGameValue);  //  change this to the actual game count 
		
		displayPanel.add(gameOutcome);
		gameOutcomeValue = createSymbolLabel(" ");
		displayPanel.add(gameOutcomeValue);
		
		infoPanel.add(displayPanel);	
		// infoPanel.add(new JPanel() );	
		// infoPanel.add(Box.createHorizontalStrut(1)); // Fixed width invisible separator.
		infoPanel.add(controlPanel);	
	
		mainPanel.add(boardPanel, BorderLayout.CENTER);
		mainPanel.add(infoPanel,BorderLayout.EAST);
		mainPanel.add(upperPanel, BorderLayout.NORTH);
		
		this.add(mainPanel);
		this.setResizable(false);
		this.pack();
		
		// start assembling the "canvas" / the presentation 
		System.out.println("After all GUI components have been created!");
		
		addActionListeners();
		addOtherListeners();
	}
	
	/**
	 * Add Action Listener for each button 
	 */
	private void addActionListeners() {
		if(this.actionListener == null)
				return; 
		
		this.resetButton.addActionListener(this.actionListener);
		this.modeButton.addActionListener(this.actionListener);
		
		for(int i = 0 ; i < boardPositions.length ; ++i) {
			boardPositions[i].addActionListener(this.actionListener);
		}
		
		
	}
	
	private void addOtherListeners() {
		addWindowListener(windowListener);
		
	}
	
	private JButton createControlButton(String label)  {
		JButton button = new JButton(label);
		button.setName( label );
		button.setFont( new Font("Dialog", Font.BOLD, 22) );
		button.setPreferredSize(new Dimension(120, 40));
		button.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		button.setBackground(Color.ORANGE);
		
		return button; 
	}
	
	private JLabel createSymbolLabel(String symbol)  {
		JLabel label = new JLabel(symbol);
		label.setName( symbol );
		label.setFont( new Font("Dialog", Font.BOLD, 22) );
		label.setPreferredSize(new Dimension(100, 35));
		//label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		label.setBackground(Color.PINK);
		
		return label; 
	}
	
	// CREATE STATUS LABEL  
	private JLabel createStatusLabel(String title)  {
		JLabel label = new JLabel(title);
		label.setName( title );
		label.setFont( new Font("Dialog", Font.BOLD, 18) );
		label.setPreferredSize(new Dimension(100, 35));
		//label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		label.setBackground(Color.YELLOW);
		
		return label; 
	}
	
	///////////////////////////////////////////////////////////////////
	// Static public methods 
	public static void present() {
		
		 // The recommended way of startin a Swing / GUI Thread 
	      EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	            	
	            	TTTController listener = new TTTController(); 
	            	TTTGameView game = new TTTGameView(listener);
	            	listener.setGameView(game);
	            	
	                game.setVisible(true);
	                
	            }
	        }
	      );
	      
	      
	}
	
	
	///////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		// create viewable instance 
		present();
  
	}

}
