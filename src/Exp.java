import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.PrintWriter;

import java.util.ArrayList;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.InteractionBox;
import com.leapmotion.leap.Vector;

@SuppressWarnings("serial")
public class Exp extends GLCanvas implements GLEventListener, KeyListener, TapListener {
	ArrayList<ArrayList<String>> blocks = new ArrayList<ArrayList<String>>();
	ArrayList<String> block1 = new ArrayList<String>();
	ArrayList<String> block2 = new ArrayList<String>();
	ArrayList<String> block3 = new ArrayList<String>();
	StringBuffer out = new StringBuffer();
	int stable = 0;
	float shift1 = 61;
	float shift2 = 79.8f;
	float shift3 = 117.5f;
	private final float scale = 2;
	float[] distances;
	LeapListener listener;
	Controller controller;
	Cube[] cubes;
	boolean pause;

	PrintWriter writer;
	String typed = "";
	String word;
	int userId;
	char ready;
	boolean start = true;
	boolean done;
	long time;
	int count = 1;
	int error = 0;
	boolean fkey = false;

	public Exp(GLCapabilities capabilities) {
		super(capabilities);

		pause = false;
		done = false;

		cubes = new Cube[34];
		cubes[0] = new Cube("En", 75, 375, 100);
		cubes[1] = new Cube("A", 20, 375, 100);
		cubes[2] = new Cube("B", 45, 300, 100);
		cubes[3] = new Cube("C", 35, 300, 100);
		cubes[4] = new Cube("D", 30, 375, 100);
		cubes[5] = new Cube("E", 25, 450, 100);
		cubes[6] = new Cube("F", 35, 375, 100);
		cubes[7] = new Cube("G", 40, 375, 100);
		cubes[8] = new Cube("H", 45, 375, 100);
		cubes[9] = new Cube("I", 50, 450, 100);
		cubes[10] = new Cube("J", 50, 375, 100);
		cubes[11] = new Cube("K", 55, 375, 100);
		cubes[12] = new Cube("L", 60, 375, 100);
		cubes[13] = new Cube("M", 55, 300, 100);
		cubes[14] = new Cube("N", 50, 300, 100);
		cubes[15] = new Cube("O", 55, 450, 100);
		cubes[16] = new Cube("P", 60, 450, 100);
		cubes[17] = new Cube("Q", 15, 450, 100);
		cubes[18] = new Cube("R", 30, 450, 100);
		cubes[19] = new Cube("S", 25, 375, 100);
		cubes[20] = new Cube("T", 35, 450, 100);
		cubes[21] = new Cube("U", 45, 450, 100);
		cubes[22] = new Cube("V", 40, 300, 100);
		cubes[23] = new Cube("W", 20, 450, 100);
		cubes[24] = new Cube("X", 30, 300, 100);
		cubes[25] = new Cube("Y", 40, 450, 100);
		cubes[26] = new Cube("Z", 25, 300, 100);
		cubes[27] = new Cube("<", 60, 300, 100);
		cubes[28] = new Cube(">", 65, 300, 100);
		cubes[29] = new Cube("?", 70, 300, 100);
		cubes[30] = new Cube(";", 65, 375, 100);
		cubes[31] = new Cube("'", 70, 375, 100);
		cubes[32] = new Cube("[", 65, 450, 100);
		cubes[33] = new Cube("]", 70, 450, 100);
		// cubes[34] = new Cube("\\", 75, 30, 100);
		// cubes[35] = new Cube(" shift",75,20,100);

		listener = new LeapListener();
		listener.addListener(this);

		controller = new Controller();
		controller.config().setFloat("Gesture.KeyTap.MinDownVelocity", 35.0f);
		controller.config().setFloat("Gesture.KeyTap.HistorySeconds", .4f);
		controller.config().setFloat("Gesture.KeyTap.MinDistance", 0.75f);
		controller.config().save();
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);

		controller.addListener(listener);
		setPreferredSize(new Dimension(1200, 650));
		addGLEventListener(this);
		addKeyListener(this);
	}

	public void orderBlocks() {
		int x = userId % 5;
		// System.out.println(x);
		switch (x) {
		case 0:
			blocks.add(block1);
			blocks.add(block2);
			blocks.add(block3);
			break;
		case 1:
			blocks.add(block1);
			blocks.add(block3);
			blocks.add(block2);
			break;
		case 2:
			blocks.add(block2);
			blocks.add(block1);
			blocks.add(block3);
			break;
		case 3:
			blocks.add(block2);
			blocks.add(block3);
			blocks.add(block1);
			break;
		case 4:
			blocks.add(block3);
			blocks.add(block1);
			blocks.add(block2);
			break;
		case 5:
			blocks.add(block3);
			blocks.add(block2);
			blocks.add(block1);
			break;
		default:
			blocks.add(block1);
			blocks.add(block2);
			blocks.add(block3);
			break;
		}

		word = blocks.get(0).remove(0);
		ready = word.charAt(0);
		word = word.substring(1);
	}

	public void setDistance() {
		float x = shift1;

		cubes[17].setX(x);
		x += 30f + distances[0] * scale;
		cubes[23].setX(x);
		x += 30f + distances[0] * scale;
		cubes[05].setX(x);
		x += 30f + distances[1] * scale;
		cubes[18].setX(x);
		x += 30f + distances[1] * scale;
		cubes[20].setX(x);
		x += 30f + distances[2] * scale;
		cubes[25].setX(x);
		x += 30f + distances[2] * scale;
		cubes[21].setX(x);
		x += 30f + distances[3] * scale;
		cubes[9].setX(x);
		x += 30f + distances[3] * scale;
		cubes[15].setX(x);
		x += 30f + distances[4] * scale;
		cubes[16].setX(x);
		x += 30f + distances[4] * scale;
		cubes[32].setX(x);
		x += 30f + distances[5] * scale;
		cubes[33].setX(x);

		x = shift2;
		cubes[01].setX(x);
		x += 30f + distances[0] * scale;
		cubes[19].setX(x);
		x += 30f + distances[1] * scale;
		cubes[04].setX(x);
		x += 30f + distances[1] * scale;
		cubes[06].setX(x);
		x += 30f + distances[2] * scale;
		cubes[07].setX(x);
		x += 30f + distances[2] * scale;
		cubes[8].setX(x);
		x += 30f + distances[3] * scale;
		cubes[10].setX(x);
		x += 30f + distances[3] * scale;
		cubes[11].setX(x);
		x += 30f + distances[4] * scale;
		cubes[12].setX(x);
		x += 30f + distances[4] * scale;
		cubes[30].setX(x);
		x += 30f + distances[5] * scale;
		cubes[31].setX(x);
		x += 30f + distances[5] * scale;
		cubes[00].setX(x);

		x = shift3;
		cubes[26].setX(x);
		x += 30f + distances[1] * scale;
		cubes[24].setX(x);
		x += 30f + distances[1] * scale;
		cubes[03].setX(x);
		x += 30f + distances[2] * scale;
		cubes[22].setX(x);
		x += 30f + distances[2] * scale;
		cubes[02].setX(x);
		x += 30f + distances[3] * scale;
		cubes[14].setX(x);
		x += 30f + distances[3] * scale;
		cubes[13].setX(x);
		x += 30f + distances[4] * scale;
		cubes[27].setX(x);
		x += 30f + distances[4] * scale;
		cubes[28].setX(x);
		x += 30f + distances[5] * scale;
		cubes[29].setX(x);

		// cubes[34].setX(shift1 + (30f + distance) * 12);
		// cubes[35].setX(shift3+(30f+distance)*10);
	}

	public void direction() {
		if (stable < 30) {
			stable++;
			return;
		}
		float direction = listener.direction(controller);
		stable = 0;

		if (direction < 1)
			distances = new float[] { 34.30605866f, 29.98816063f, 28.44956492f, 27.08203015f, 27.92100217f,
					33.42780216f };
		else if (direction < 11)
			distances = new float[] { 34.88882418f, 28.90249741f, 29.22661624f, 28.0715883f, 28.04164264f,
					32.86946342f };
		else if (direction < 21)
			distances = new float[] { 35.86414824f, 30.05314064f, 29.21126011f, 29.88440297f, 28.29822856f,
					32.8153151f };
		else if (direction < 31)
			distances = new float[] { 35.57825033f, 30.04424892f, 31.76791605f, 27.06489048f, 28.42215642f,
					31.95035031f };
		else if (direction < 41)
			distances = new float[] { 35.98336699f, 30.96481583f, 31.05363892f, 28.87050252f, 29.26285794f,
					33.09294734f };
		else
			distances = new float[] { 36.88771185f, 31.95630205f, 31.07999355f, 29.50233794f, 29.73864986f,
					34.23614287f };
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_P) {
			pause = !pause;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		if (!fkey) {
			direction();
			setDistance();
		}

		GLU glu = new GLU();
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl.glViewport(0, 0, 1200, 650);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();

		glu.gluOrtho2D(0, 1200, 0, 650);

		gl.glMatrixMode(GL2.GL_MODELVIEW); // To operate on Model-View matrix
		gl.glLoadIdentity();
		InteractionBox iBox = controller.frame().interactionBox();

		for (Finger f : controller.frame().fingers()) {
			if (f.type() == Finger.Type.TYPE_THUMB)
				continue;

			Vector normalizedPosition = iBox.normalizePoint(f.tipPosition());
			float x = normalizedPosition.getX() * 1150;
			float y = (1 - normalizedPosition.getZ()) * 650;

			gl.glBegin(GL.GL_TRIANGLE_FAN);
			gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
			gl.glVertex2f(x, y); // Center of circle
			int numSegments = 10;
			float angle;
			for (int i = 0; i <= numSegments; i++) {
				// Last vertex same as first vertex
				angle = (float) (i * 2.0f * Math.PI / numSegments);
				// 360 deg for all segments
				gl.glVertex2f((float) (Math.cos(angle) * 10) + x, (float) (Math.sin(angle) * 10) + y);
			}
			gl.glEnd();
		}

		gl.glPushMatrix();
		for (int i = 0; i < cubes.length; i++) {
			if (cubes[i].isSelected())
				gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
			else if (cubes[i].isHighlighted())
				gl.glColor3f(1, 0, 0.8f);
			else
				gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
			cubes[i].highlighted = false;
			gl.glBegin(GL2.GL_QUADS); // Each set of 4 vertices form a quad
			gl.glVertex2f(cubes[i].getX(), cubes[i].getY() + 100);
			// Define vertices in counter-clockwise (CCW) order so that the
			// normal (front-face) is facing you
			gl.glVertex2f(cubes[i].getX() + 60f, cubes[i].getY() + 100);
			gl.glVertex2f(cubes[i].getX() + 60f, cubes[i].getY() + 60f + 100);
			gl.glVertex2f(cubes[i].getX(), cubes[i].getY() + 60f + 100);
			gl.glEnd();
		}
		gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
		gl.glPopMatrix();

		TextRenderer renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 50));
		renderer.beginRendering(1200, 650);
		renderer.setColor(0, 0, 0, 0.8f);

		for (int i = 0; i < cubes.length; i++) {
			renderer.draw(cubes[i].text, (int) cubes[i].getX() + 5, (int) cubes[i].getY() + 110);
		}
		renderer.draw(ready + word, 100, 300);
		renderer.draw(typed, 100, 200);

		renderer.draw(start + "", 100, 100);
		renderer.draw(time + "", 800, 100);
		renderer.endRendering();
	}

	private void update(float x, float y) {
		for (Cube c : cubes) {
			c.highlighted = false;
			if (x >= c.getX() && x <= c.getX() + 60 && y >= c.getY() + 100 && y <= c.getY() + 175) {
				System.out.print(c.text + " ");
				if (c.text.equalsIgnoreCase("" + ready)) {
					if (start) {
						start = !start;
						time = System.currentTimeMillis();
					}
					typed += c.text.toLowerCase();
					c.highlighted = true;
					if (word.length() > 0) {
						ready = word.charAt(0);
						word = word.substring(1);
					} else {
						out.append(count + "," + typed + "," + ((System.currentTimeMillis() - time) / 1000.0) + ","
								+ error + "\n");
						typed = "";
						error = 0;
						if (blocks.get(0).size() > 0) {
							word = blocks.get(0).remove(0);
							ready = word.charAt(0);
							word = word.substring(1);
						} else {
							blocks.remove(0);
							if (blocks.size() > 0) {
								count++;
								word = blocks.get(0).remove(0);
								ready = word.charAt(0);
								word = word.substring(1);
							} else {
								typed = "finally done";
								word = "";
								writer.write(out.toString());
								writer.close();
								ready = Character.MIN_VALUE;
								done = true;
								return;
							}
						}
						start = true;
					}
				} else {
					error++;
					System.out.print("error" + error);
				}
				return;
			}
		}
		System.out.print("out of range");
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(0.8F, 0.8F, 0.8F, 1.0F);
		gl.glEnable(GL.GL_DEPTH_TEST);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	}

	@Override
	public void onTap(Vector v, Finger f) {
		if (done)
			return;
		if (pause) {
			start = true;
			return;
		}
		System.out.print(f.type() + " ");
		float x = v.getX() * 1150;
		float z = (1 - v.getZ()) * 650;
		update(x, z);

		System.out.println();
	}

	// show fingers
	// rectangle around keyboard
	// keyboard switch with text
}