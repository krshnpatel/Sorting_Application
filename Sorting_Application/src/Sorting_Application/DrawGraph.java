package code_kpate222_assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

@SuppressWarnings("serial")
public class DrawGraph extends JPanel implements ActionListener
{
	// Member variables that keep track of which button was clicked
	protected boolean scrambleClicked = false;
	protected boolean resetClicked = false;
	protected boolean startClicked = false;
	protected boolean nothingClicked = false;
	protected boolean selSortButton = false;
	protected boolean merSortButton = false;
	
	// Arrays that hold the end points of the lines to be drawn
	private int [] lines; // These are the lines that will be modified
	private int [] randomLines; // These hold the randomlines generated (This is used when reset is used, to draw the original lines before sorting the lines)
	private int [] tempMergeArray; // This is used for the merge sort algorithm
	
	// This counter helps us know how to display the lines
	private int counter = 0;
	
	// The random number generator
	private Random rnd = new Random(System.currentTimeMillis());
	
	// Timers for each sort
	private Timer selTimer = new Timer(35, this); // Selection sort timer
	private Timer merTimer = new Timer(35, this); // Merge sort timer
	
	// Default constructor
	public DrawGraph()
	{
		// Declares the three arrays
		lines = new int[256];
		randomLines = new int[256];
		tempMergeArray = new int[256];
		
		// Initializes the "lines" and "randomLines" array to display the perfect triangle initially
		for (int i = 0; i < 256; i++)
		{
			lines[i] = 510 - i;
			randomLines[i] = 510 - i;
		}
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if (startClicked) // If start button was clicked then...
		{
			// Draw all the lines
			int j = 0;
			for (int i = 767; i >= 0; i-=3)
			{
				g.drawLine(i,510,i,lines[j]);
				j++;
			}
			if (selSortButton) // If the selection sort button is selected then...
			{
				// Start the selection sort timer
				selTimer.start();
			}
			else if (merSortButton) // If the merge sort button is selected then...
			{
				// Start the merge sort timer
				merTimer.start();
			}
		}
		else if (resetClicked) // If the reset button is clicked then...
		{
			// Set the "lines" equal to "randomLines" and then draw each line
			int j = 0;
			for (int i = 767; i >= 0; i-=3)
			{
				lines[j] = randomLines[j];
				g.drawLine(i,510,i,lines[j]);
				j++;
			}
		}
		else if (scrambleClicked) // If the scramble button is clicked then...
		{
			// Randomize the array
			RandomizeArray();
			// Set "lines" equal to "randomLines"
			for (int i = 255; i >= 0; i--)
				lines[i] = randomLines[i];
			// Set scramble clicked to false and nothing clicked to true
			scrambleClicked = false;
			nothingClicked = true;
			// Repaint the graph
			repaint();
		}
		else if (nothingClicked)
		{
			// Increment the counter
			counter++;
			if (counter == 1) // If the counter is 1, it means the program just started so...
			{				
				// Draw 256 sorted lines
				int j = 0;	
				for (int i = 0; i < 768; i+=3)
				{
					g.drawLine(i,510,i,lines[j]);
					j++;
				}
			}
			else // Else, draw the current lines
			{
				int j = 0;	
				for (int i = 767; i >= 0; i-=3)
				{
					g.drawLine(i,510,i,lines[j]);
					j++;
				}
			}
		}
	}
	
	public void actionPerformed(ActionEvent event)
	{
		if (selSortButton) // If the selection sort button is selected then...
		{
			// Call the selection sort method to sort the lines
			SelectionSort();
			// Repaint the graph
			repaint();
		}
		else if (merSortButton) // If the merge sort button is selected then...
		{
			// Call the merge sort method to sort the lines
			MergeSort(0,255);
			// Repaint the graph by calling updateGraph()
			updateGraph();
		}
		selTimer.stop(); // Stop the selection sort timer
		merTimer.stop(); // Stop the merge sort timer
	}
	
	public void SelectionSort()
	{
		int currentMinIndex, temp;
		for (int i = 0; i < 255; i++)
		{
			currentMinIndex = i; // Set "i" to the current minimum index
			for (int j = i+1; j < 256; j++) // Go through the array after index "i"
			{
				if (lines[j] < lines[i]) // If the line's endpoint at "j" is less than the line's endpoint at "i" then...
				{
					currentMinIndex = j; // Set the current minimum index to "j"
				}
			}
			if (currentMinIndex != i) // If the current minimum index is not "i" then...
			{
				// Swap the lines at "i" and current minimum index
				temp = lines[i];
				lines[i] = lines[currentMinIndex];
				lines[currentMinIndex] = temp;
			}
		}
	}

	public void MergeSort(int lowIndex, int highIndex)
	{
		if (lowIndex < highIndex) // The lower index has to be lower than the higher index
		{
			int mid = lowIndex + (highIndex - lowIndex) / 2; // Calculate the middle index
			MergeSort(lowIndex, mid); // Call merge sort recursively for the first half
			MergeSort(mid + 1, highIndex); // Call merge sort recursively for the second half
			Merge(lowIndex, mid, highIndex); // Merge the two parts
		}
	}
	
	public void Merge(int lowIndex, int mid, int highIndex)
	{
		// Copy the lines into the temporary merged array
		for (int i = lowIndex; i <= highIndex; i++)
			tempMergeArray[i] = lines[i];
		
		int i = lowIndex;
		int j = mid + 1;
		int k = lowIndex;
		
		// For the second half...
		while (i <= mid && j <= highIndex)
		{
			if (tempMergeArray[i] <= tempMergeArray[j]) // If the value at "i" is less than or equal to the value at "j"...
			{
				// Set the value at "k" to the value at "i" in the temp merge array
				lines[k] = tempMergeArray[i];
				i++; // Increment "i"
				// Update the graph and pause to show this step
				updateGraph();
				myTimer();
			}
			else // Otherwise...
			{
				// Set the value at "k" to the value at "j" in the temp merge array
				lines[k] = tempMergeArray[j];
				j++; // Increment "j"
				// Update the graph and pause to show this step
				updateGraph();
				myTimer();
			}
			k++; // Increment "k"
		}

		// For the first half...
		while (i <= mid)
		{
			// Set the value at "k" to the value at "i" in the temp merge array
			lines[k] = tempMergeArray[i];
			k++; // Increment "k"
			i++; // Increment "i"
			// Update the graph and pause to show this step
			updateGraph();
			myTimer();
		}
	}
	
	public void RandomizeArray()
	{	
		int ranIndex;
		for (int i = 0; i < 256; i++) // Randomly swaps values at a random index for 256 times
		{
			ranIndex = RandomNumber(); // Get a random number from 0 to 255 (inclusive)
			// Swap the value at "i" with a random index
			int temp = randomLines[i];
			randomLines[i] = randomLines[ranIndex];
			randomLines[ranIndex] = temp;
		}
	}
	
	public int RandomNumber()
	{
		// Generate a random integer between 0 and 255 (inclusive) and return it
		return rnd.nextInt(256);
	}
	
	public void updateGraph()
	{
		// Update the graph by calling the paintComponent method of this class
		paintComponent(this.getGraphics());
	}
	
	public void myTimer() // Pauses the program for a few milliseconds when this function is called
	{
		long prevMillis = System.currentTimeMillis();
		while ((System.currentTimeMillis() - prevMillis) <= 5) // Runs for 5 milliseconds and does nothing
		{}
	}
}