import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Geometry.CartesianCoordinate;
import drawing.*;
import agent.*;
import utilities.Utils;

public class FlockingTest {
	
	private Canvas canvas;
	private ArrayList<DynamicAgent> flock;
	private JButton addAgentButton;
	private JButton removeAgentButton;
	private JSlider agentSpeed;
	private boolean continueRunning = true;
	
	//Image to hold the background picture
	public Image sunset;
	
	public FlockingTest() {
		//Setup for canvas
		JFrame frame = new JFrame();
		frame.setTitle("Flocking Simulation");
		frame.setSize(1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		//Set sunset as the background image, catch exception if image not found
		//File backgroundImage = new File("sunset.jpg");
		//try {
		//	sunset = ImageIO.read(backgroundImage);
		//}
		//catch (IOException ex) {
		//	System.out.println("File not found: \"" + backgroundImage + "\". Terminating!");
		//	System.exit(0);
		//}
		
		//Attempt to add background image
		//canvas = new BackgroundImage(sunset);
		//frame.add(canvas, BorderLayout.CENTER);
		
		//Adding canvas to the centre of the frame
		canvas = new Canvas();
		frame.add(canvas, BorderLayout.CENTER);
		
		
		//Adding a panel within the frame in the south location for buttons/sliders
		JPanel lowerPanel = new JPanel();
		frame.add(lowerPanel, BorderLayout.SOUTH);
		
		//Instantiation of scale for speed slider
		int FPS_Min = 0;
		int FPS_Max = 1500;
		int FPS_Init = 100;
		
		//Instantiation of add/remove agent buttons & speed slider
		addAgentButton = new JButton("Add Agent"); 
		removeAgentButton = new JButton("Remove Agent");
		agentSpeed = new JSlider(JSlider.HORIZONTAL, FPS_Min, FPS_Max, FPS_Init);
		agentSpeed.setPreferredSize(new Dimension(500, 35)); //Adjusting slider size
		
		//Custom labels for speed scale
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer(0), new JLabel("Slowest"));
		labelTable.put(new Integer(FPS_Max/2), new JLabel("50%"));
		labelTable.put(new Integer(FPS_Max), new JLabel("Fastest"));
		agentSpeed.setPaintLabels(true);
		agentSpeed.setLabelTable(labelTable);
		
		//Adding buttons and slider to the lower panel
		lowerPanel.add(addAgentButton);
		lowerPanel.add(agentSpeed);
		lowerPanel.add(removeAgentButton);
		
		//ArrayList holding all agents for management over entire flock
		flock = new ArrayList<DynamicAgent>();
		
		//Anonymous class providing an implementation for abstract actionPerformed method
		addAgentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CartesianCoordinate startPosition = new CartesianCoordinate(300, 300);
				flock.add(new DynamicAgent(canvas, startPosition));
				//Set newest agent to have the correct speed upon being added to the canvas
				flock.get(flock.size()-1).setSpeed((int)agentSpeed.getValue());
			}
		});
		
		//Anonymous class implementing remove agent button
		removeAgentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Make sure there is an agent to remove
				if (flock.size() >= 1) {
					//Undraw most recent agent so no ghost is left behind
					flock.get(flock.size()-1).undraw();
					//Remove the most recent agent
					flock.remove(flock.size()-1);
				}
				//Testing print, remove
				else {
					System.out.println("No agents to remove");
				}
			}	
		});
		
		//Anonymous class implementing the slider
		agentSpeed.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent a) {
				for (DynamicAgent agent : flock) {
					agent.setSpeed((int)agentSpeed.getValue());
				}
			}
		});
		
		frame.revalidate();
		gameLoop();
	}
	
	public static void main(String[] args) {
		System.out.println("Running Flocking program...");
		new FlockingTest();
	}
	
	private void gameLoop() {
		
		int deltaTime = 20;
		
		//Sum of neighbour agents position vectors, used in cohesion/separation
		double xTotal;
		double yTotal;
		
		//Distance between agent in question and another
		double agentDistance;
		
		//Sum of neighbour agents direction, used in alignment
		double totalDirection;
		
		//Number of agents within a given agents neighbourhood
		int neighbours;
		
		//Centre of mass of all agents within a given agents neighbourhood
		CartesianCoordinate centreOfMass = new CartesianCoordinate(0,0);
		
		//Flocking Behaviour angles (To be turned)
		double thetaC; //Cohesion
		double thetaS; //Separation
		double thetaA; //Alignment
		int angleToTurn = 0;
		
		//TESTING Adding agents without button
		for (int i = 0; i < 10 ; i++) {
			flock.add(new DynamicAgent(canvas, new CartesianCoordinate(10 + 100*i, 300)));
			flock.add(new DynamicAgent(canvas, new CartesianCoordinate(10 + 80*i, 200)));
			flock.add(new DynamicAgent(canvas, new CartesianCoordinate(10 + 50*i, 100)));
		}

		while (continueRunning) {
			
			for (DynamicAgent agent : flock) {		
				agent.undraw();				 	
			}
			
			 for (DynamicAgent agent : flock) {
				xTotal = 0;
				yTotal = 0;
				totalDirection = 0;
				neighbours = 0;
				
				//Loop running for flock size, for calculating flocking behaviour angles
				for (int i = 0 ; i < flock.size(); i++) {
					CartesianCoordinate agentPosition = new CartesianCoordinate(flock.get(i).xPosition, flock.get(i).yPosition);
					agentDistance = agent.checkDistance(agentPosition);
					
					//Check for neighbours and sum position vectors and direction
					if (agentDistance <= 50) {
						totalDirection = totalDirection + flock.get(i).direction;
						xTotal = xTotal + flock.get(i).xPosition;
						yTotal = yTotal + flock.get(i).yPosition;
						neighbours++;
					}
					
					//Calculate agents centre of mass
					if (i == flock.size()-1) {
						centreOfMass = new CartesianCoordinate(xTotal/neighbours, yTotal/neighbours);
					}
				}
				
				//If no neighbours, don't change direction
				if (neighbours > 1) {
					thetaC = agent.cohesion(centreOfMass);
					thetaS = agent.separation(centreOfMass);
					thetaA = agent.alignment(totalDirection/neighbours);
					angleToTurn = (int)thetaC + (int)thetaS + (int)thetaA;
				}
				else {
					angleToTurn = 0;
				}
				
				//Hold the angle for each agent to be turned next loop
				agent.angleToBeTurned = angleToTurn;
			 }
			 
			 //Loop to update every agents direction and position
			 for (DynamicAgent agent : flock) {
				 agent.update(deltaTime, agent.angleToBeTurned);
			 }
			 
			 //Loop to redraw agents and also make sure they don't leave the screen
			 for (DynamicAgent agent : flock) {
				 agent.draw();
				 //Wrap agent position making movement toroidal
				 agent.wrapPosition(canvas.getWidth(), canvas.getHeight());
			 }
			 Utils.pause(deltaTime);
		 }
	}

}
