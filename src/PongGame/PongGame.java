package PongGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;


public class PongGame extends JFrame {
    public PongGame() {
        setTitle("Pong Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        PongPanel pongPanel = new PongPanel();
        add(pongPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PongGame();
    }
}

class PongPanel extends JPanel implements ActionListener {
    private final Timer timer;
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int PADDLE_WIDTH = 15;
    private final int PADDLE_HEIGHT = 100;
    private final int BALL_SIZE = 20;

    private int paddle1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int paddle2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int ballX = WIDTH / 2 - BALL_SIZE / 2;
    private int ballY = HEIGHT / 2 - BALL_SIZE / 2;
    private int ballSpeedX = 4;
    private int ballSpeedY = 2;
    
    private int leftScore = 0;
    private int rightScore = 0;
    
    private final double ACCELERATION_FACTOR = 1.3;
    private final int MAX_BALL_SPEED = 15;
    
	private final int INITIAL_BALL_SPEED_X = 4;
	private final int INITIAL_BALL_SPEED_Y = 2;
	
	private boolean gameStarted = false;
	
	private Set<Integer> pressedKeys = new HashSet<>();



    public PongPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        

        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                pressedKeys.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pressedKeys.remove(e.getKeyCode());
            }
        });
                
            

        timer = new Timer(1000 / 120, this); // 120 FPS game loop
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(WIDTH - PADDLE_WIDTH, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        
     // Draw the ball only if the game has started
        if (gameStarted) {
            g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);
        }

        // Draw the score board
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString(Integer.toString(leftScore), WIDTH / 2 - 60, 30);
        g.drawString(Integer.toString(rightScore), WIDTH / 2 + 40, 30);

        // Draw "click space to start" message
        if (!gameStarted) {
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Click SPACE to start", WIDTH / 2 - 100, HEIGHT / 2);
        }
    }

    
    private void resetBall() {
        if (ballX < 0) {
            rightScore++;
        } else if (ballX + BALL_SIZE > WIDTH) {
            leftScore++;
        }
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT / 2 - BALL_SIZE / 2;
        ballSpeedX = 0;
        ballSpeedY = 0;
        gameStarted = false;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle paddle movement
        if (gameStarted) {
            if (pressedKeys.contains(KeyEvent.VK_UP)) {
                paddle2Y -= 10;
            }
            if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
                paddle2Y += 10;
            }

            if (pressedKeys.contains(KeyEvent.VK_W)) {
                paddle1Y -= 10;
            }
            if (pressedKeys.contains(KeyEvent.VK_S)) {
                paddle1Y += 10;
            }
        }

        if (pressedKeys.contains(KeyEvent.VK_SPACE) && !gameStarted) {
            gameStarted = true;
            ballSpeedX = INITIAL_BALL_SPEED_X;
            ballSpeedY = INITIAL_BALL_SPEED_Y;
        }
        
    	if(gameStarted) {
    	
	    	ballX += ballSpeedX;
	        ballY += ballSpeedY;
	
	        // Ball collision with top and bottom
	        if (ballY < 0 || ballY + BALL_SIZE > HEIGHT) {
	            ballSpeedY = -ballSpeedY;
	        }
	
	        // Ball collision with paddles
	        if ((ballX < PADDLE_WIDTH && ballY + BALL_SIZE > paddle1Y && ballY < paddle1Y + PADDLE_HEIGHT) ||
	            (ballX + BALL_SIZE > WIDTH - PADDLE_WIDTH && ballY + BALL_SIZE > paddle2Y && ballY < paddle2Y + PADDLE_HEIGHT)) {
	
	            int paddleY = (ballX < PADDLE_WIDTH) ? paddle1Y : paddle2Y;
	            double impactFactor = ((double) ballY + BALL_SIZE / 2 - paddleY) / PADDLE_HEIGHT - 0.5;
	
	            // Accelerate the ball
	            ballSpeedX = (int) Math.signum(ballSpeedX) * Math.min(MAX_BALL_SPEED, Math.abs((int) (ballSpeedX * ACCELERATION_FACTOR)));
	
	            ballSpeedX = -ballSpeedX;
	            ballSpeedY = (int) (impactFactor * 8);
	        }
	
	        // Ball out of bounds
	        if (ballX < 0 || ballX + BALL_SIZE > WIDTH) {
	            resetBall();
	        }
	
	        // Paddle bounds
	        paddle1Y = Math.max(0, Math.min(paddle1Y, HEIGHT - PADDLE_HEIGHT));
	        paddle2Y = Math.max(0, Math.min(paddle2Y, HEIGHT - PADDLE_HEIGHT));
	
	        repaint();
    	}
    }
}
