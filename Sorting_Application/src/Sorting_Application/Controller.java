package code_kpate222_assignment2;

import javax.swing.JFrame;

public class Controller
{
	// The main method
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				// Create a "ViewMyFrame" object in the main to view the frame
				ViewMyFrame f = new ViewMyFrame();
			}
		});
	}
}