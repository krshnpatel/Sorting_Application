package code_kpate222_assignment2;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class ViewMyFrame
{
	// Declaration of User Interface member varibles
	private JFrame myFrame; // The main window
	private JPanel menuPanel; // Menu panel that contains three other panels (panel1, panel2, panel3)
	private JPanel graphPanel; // Graph panel that will contain the graph with lines
	private JPanel panel1; // Consists of the Scramble button
	private JPanel panel2; // Consists of the sort radio buttons
	private JPanel panel3; // Consists of the Start and Rest buttons
	private JButton scrambleButton; // The scramble button; scrambles the lines in the graph
	private JButton startButton; // The start button; starts the animation of the sort
	private JButton resetButton; // The reset button; resets the lines as it were before the sort
	private JRadioButton selectionSortButton; // The selection sort radio button; if selected, animation will use selection sort
	private JRadioButton mergeSortButton; // The merge sort radio button; if selected, animation will use merge sort
	private DrawGraph graph; // The graph in which the lines are drawn
	
	// Declaration of two integers that will keep track of the number of times each sort is used before clicking Scramble again
	private int mergeSortUsed; // This will count the amount of times merge sort is used before lines are scrambled again
	private int selectionSortUsed; // This will count the amount of times selection sort is used before lines are scrambled again
	
	// The default constructor
	public ViewMyFrame()
	{
		gui(); // Call the gui() function
	}
	
	public void gui()
	{
		// Creates a new JFrame and (1) assigns a title, (2) sets it visible, (3) sets the size, (4) sets close operation, (5) sets the layout of the frame
		myFrame = new JFrame("Assignment 2: Sorting Animations"); //(1)
		myFrame.setVisible(true); //(2)
		myFrame.setSize(1055,600); //(3)
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //(4)
		myFrame.getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT)); //(5)
		
		// Creates a new panel and (1) sets the layout, (2) sets a border with a title, and (3) sets the preferred size
		menuPanel = new JPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS)); //(1)
		menuPanel.setBorder(new TitledBorder("Menu")); //(2)
		menuPanel.setPreferredSize(new Dimension(250,550)); //(3)
		
		// Creates a new panel and a new DrawGraph, and (1) sets nothingClicked to true, (2) sets layout of the panel, (3) sets border of the panel with a title, (4) sets size of the panel
		graphPanel = new JPanel();
		graph = new DrawGraph();
		graph.nothingClicked = true; //(1)
		graphPanel.setLayout(new BoxLayout(graphPanel,BoxLayout.Y_AXIS)); //(2)
		graphPanel.setBorder(new TitledBorder("Graph")); //(3)
		graphPanel.setPreferredSize(new Dimension(775,550)); //(4)
		graphPanel.add(graph); // Adds the DrawGraph component to the graph panel
		
		// Creates three new panels
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		
		// Sets the layout of the three panels
		panel1.setLayout(new GridBagLayout());
		panel2.setLayout(new GridBagLayout());
		panel3.setLayout(new GridBagLayout());
		
		// Creates a variable for the constraints on the GridBagLayout
		GridBagConstraints cons = new GridBagConstraints();
		cons.gridy = 1;
		cons.insets = new Insets(15, 0, 0, 0);
		
		// Creates a button with text, (1) sets the size of the button, (2) and adds the button to the first panel
		scrambleButton = new JButton("Scramble Lines");
		scrambleButton.setPreferredSize(new Dimension(200,50)); // (1)
		panel1.add(scrambleButton); // (2)
		
		// Creates a radio button with text, (1) sets the button as selected initially
		selectionSortButton = new JRadioButton("Selection Sort");
		selectionSortButton.setSelected(true);
		// Creates a radio button with text
		mergeSortButton = new JRadioButton("Merge Sort");
		// Sets the border of the second panel with a title
		panel2.setBorder(new TitledBorder("Sorting Algorithms"));	
		// Adds the two radio buttons to the second panel
		panel2.add(selectionSortButton);
		panel2.add(mergeSortButton);

		// Creates a button with text, (1) sets the size of the button, and (2) disables the button initially
		startButton = new JButton("Start Animation");
		startButton.setPreferredSize(new Dimension(200,50)); // (1)
		startButton.setEnabled(false); // (2)
		// Creates a button with text, (1) sets the size of the button, and (2) disables the button initially
		resetButton = new JButton("Reset");
		resetButton.setPreferredSize(new Dimension(200,50)); // (1)
		resetButton.setEnabled(false); // (2)
		// Adds the two buttons to the third panel
		panel3.add(startButton);
		panel3.add(resetButton,cons);
		
		// Creates an event handler named handler and assigns a mouse listener to each of the buttons previously created
		Handler handler = new Handler();
		selectionSortButton.addMouseListener(handler);
		mergeSortButton.addMouseListener(handler);
		scrambleButton.addMouseListener(handler);
		startButton.addMouseListener(handler);
		resetButton.addMouseListener(handler);
		
		// Adds the three panels to the menu panel
		menuPanel.add(panel1);
		menuPanel.add(panel2);
		menuPanel.add(panel3);
		
		// Adds the graph and menu panel to the frame
		myFrame.add(graphPanel);
		myFrame.add(menuPanel);
	}
	
	// Handles all the mouse listener events
	public class Handler implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent event) // When the mouse is clicked...
		{
			// If the scramble button was clicked and is enabled then...
			if (event.getSource() == scrambleButton && scrambleButton.isEnabled())
			{
				// Set all the variables representing each button to false except the scramble button
				graph.nothingClicked = false;
				graph.resetClicked = false;
				graph.startClicked = false;
				graph.scrambleClicked = true;
				// Call the paint method in "graph"
				graph.repaint();
				// Disable the scramble button and enable the start button
				scrambleButton.setEnabled(false);
				startButton.setEnabled(true);
			}
			
			// If the reset button was clicked and is enabled then...
			if (event.getSource() == resetButton && resetButton.isEnabled())
			{
				// Set all the variables representing each button to false except the reset button
				graph.nothingClicked = false;
				graph.scrambleClicked = false;
				graph.startClicked = false;
				graph.resetClicked = true;
				// Call the paint method in "graph"
				graph.repaint();
				// Disable the reset button and enable the start button depending on the scramble button
				resetButton.setEnabled(false);
				if (!scrambleButton.isEnabled()) // This if-statement makes sure that the scramble button and start button are not enabled or disabled at the same time
					startButton.setEnabled(true);
			}
			
			// If the start button was clicked and is enabled then...
			if (event.getSource() == startButton && startButton.isEnabled())
			{
				// Set all the variables representing each button to false except the start button
				graph.nothingClicked = false;
				graph.scrambleClicked = false;
				graph.resetClicked = false;
				graph.startClicked = true;
				
				// Check which radio button is currently selected...
				if (selectionSortButton.isSelected()) // If the selection sort button is selection then...
				{
					// Let the graph know that the selection sort button is selected and not the merge sort button
					graph.selSortButton = true;
					graph.merSortButton = false;
					// Call the paint method in "graph"
					graph.repaint();
					// Increment the number of times the selection sort is used
					selectionSortUsed++;
				}
				else if (mergeSortButton.isSelected()) // If the merge sort button is selected then...
				{
					// Let the graph know that the merge sort button is selected and not the selection sort button
					graph.merSortButton = true;
					graph.selSortButton = false;
					// Call the paint method in "graph"
					graph.repaint();
					// Increment the number of times the merge sort is used
					mergeSortUsed++;
				}
				
				 //Checks whether to enable Scramble Button again (Condition: User has to use selection sort and merge sort at least once each)
				if (selectionSortUsed >= 1 && mergeSortUsed >= 1) // If each sort is used at least once then...
				{
					// Enable the scramble button and disable the start button
					scrambleButton.setEnabled(true);
					startButton.setEnabled(false);
					// Reset the counters for amount of times each sort is used
					selectionSortUsed = 0;
					mergeSortUsed = 0;
				}
				// Disable the start button and enable the reset button
				startButton.setEnabled(false);
				resetButton.setEnabled(true);
			}
		}

		@Override
		public void mouseEntered(MouseEvent event)
		{
			
		}

		@Override
		public void mouseExited(MouseEvent event)
		{
			
		}

		@Override
		public void mousePressed(MouseEvent event)
		{

		}

		@Override
		public void mouseReleased(MouseEvent event)
		{
			// This makes sure that at least one of the radio buttons is selected
			if (event.getSource() == selectionSortButton) // If the selection sort button is selected then...
			{
				// Keep the selection sort button enabled and disable the merge sort button
				selectionSortButton.setSelected(true);
				mergeSortButton.setSelected(false);
			}
			if (event.getSource() == mergeSortButton) // If the merge sort button is selected then...
			{
				// Keep the merge sort button enabled and disable the selection sort button
				mergeSortButton.setSelected(true);
				selectionSortButton.setSelected(false);
			}
		}
	}
}