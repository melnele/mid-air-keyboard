import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JFrame;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.util.FPSAnimator;

public class keyboard1 {
	public static void main(String[] args) {
		JFrame window = new JFrame("Keyboard");
		GLCapabilities caps = new GLCapabilities(null);

		String[] words1 = { "sail", "size", "need", "glossy", "interested", "witty", "rail", "embarrassed", "decay",
				"kitten", "hesitant", "holiday", "quiz", "cows", "night", "naive", "sheep", "freeze", "board", "sea",
				"drag", "passenger", "nine", "lazy", "snore", "yoke", "brown", "develop", "spade", "expert" };
		String[] words2 = { "embarrassed", "develop", "lazy", "night", "kitten", "quiz", "size", "nine", "expert",
				"decay", "interested", "cows", "naive", "drag", "spade", "freeze", "board", "snore", "sea", "sheep",
				"need", "witty", "passenger", "rail", "yoke", "hesitant", "glossy", "brown", "holiday" };
		String[] words3 = { "freeze", "nine", "board", "night", "brown", "naive", "sea", "holiday", "snore", "expert",
				"lazy", "need", "decay", "kitten", "hesitant", "passenger", "embarrassed", "cows", "witty", "drag",
				"spade", "quiz", "yoke", "glossy", "interested", "develop", "rail", "size", "sheep" };

		Exp exps = new Exp(caps);
		exps.distances = new float[] { 34.30605866f, 29.98816063f, 28.44956492f, 27.08203015f, 27.92100217f,
				33.42780216f };
		exps.setDistance();

		System.out.println("Please enter user id:");
		Scanner sc = new Scanner(System.in);
		exps.userId = sc.nextInt();
		sc.close();

		exps.block1 = new ArrayList<String>(Arrays.asList(words1));
		exps.block2 = new ArrayList<String>(Arrays.asList(words2));
		exps.block3 = new ArrayList<String>(Arrays.asList(words3));

		exps.orderBlocks();

		window.add(exps);
		window.setSize(1300, 700); // Projector 1920,1100
		// window.pack();
		window.setLocation(50, 20);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		exps.requestFocusInWindow();
		FPSAnimator animator = new FPSAnimator(exps, 60);
		animator.start();

		try {
			exps.writer = new PrintWriter(new FileWriter("User" + exps.userId + "varKeyboard.csv", true));
			exps.writer.println("Block #,word,time,error");
		} catch (Exception e) {
			e.printStackTrace();
		}
		animator.setIgnoreExceptions(false);
		while (true) {
			// System.out.println(animator.getUncaughtExceptionHandler().toString());
		}
	}
}