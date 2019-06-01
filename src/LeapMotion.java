
//import java.io.IOException;
import java.util.ArrayList;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Gesture.State;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.InteractionBox;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Pointable;
//import com.leapmotion.leap.Vector;
import com.leapmotion.leap.Vector;

class LeapListener extends Listener {
	ArrayList<TapListener> listeners = new ArrayList<TapListener>();

	public void addListener(TapListener toAdd) {
		listeners.add(toAdd);
	}

	@Override
	public void onInit(Controller c) {
		System.out.println("Initialized");
	}

	@Override
	public void onConnect(Controller c) {
		System.out.println("Connected to motion sensor");
	}

	@Override
	public void onDisconnect(Controller c) {
		System.out.println("Motion sensor disconnected");
	}

	@Override
	public void onExit(Controller c) {
		System.out.println("Exited");
	}

	public float direction(Controller c) {
		Frame frame = c.frame();
		for (Hand h : frame.hands()) {
			if (h.isLeft())
				continue;
			return (float) (h.direction().pitch() / Math.PI) * 180;
		}
		return 0;
	}

	@Override
	public void onFrame(Controller controller) {
		for (Gesture gesture : controller.frame().gestures()) {
			if (gesture.type() == Gesture.Type.TYPE_KEY_TAP && gesture.isValid() && gesture.state() == State.STATE_STOP)
				for (Pointable p : gesture.pointables()) {
					Finger finger = new Finger(p);
					if (p.isFinger() && p.isValid() && finger.type() != Finger.Type.TYPE_THUMB)
						for (TapListener t : listeners) {
							InteractionBox iBox = controller.frame ().interactionBox();
							Vector normalizedPosition = iBox.normalizePoint(p.stabilizedTipPosition());
							t.onTap(normalizedPosition, finger);
							return;
						}
				}
		}
	}

	// public boolean isDownRight(Controller c, Finger f) {
	// Frame frame = c.frame();
	// for (Hand hand : frame.hands()) {
	// if (hand.isLeft())
	// continue;
	// for (Finger finger : hand.fingers()) {
	// if (finger.type() != Finger.Type.TYPE_INDEX)
	// continue;
	// float tmp = hand.palmPosition().distanceTo(finger.tipPosition());
	// if (tmp >= 88 && tmp <= 96)
	// return true;
	// }
	// }
	// return false;
	// }

	// public boolean handClosed(Controller c) {
	// Frame frame = c.frame();
	//
	// for (Hand hand : frame.hands()) {
	// if (hand.grabStrength() == 1) {
	// return true;
	// }
	// }
	// return false;
	// }

	// public boolean handOpened(Controller c) {
	// Frame frame = c.frame();
	// if (frame.fingers().isEmpty())
	// return false;
	// for (Finger finger : frame.fingers()) {
	// if (finger.type() == Finger.Type.TYPE_THUMB || finger.type() ==
	// Finger.Type.TYPE_PINKY)
	// continue;
	// if (!finger.isExtended()) {
	// return false;
	// }
	// }
	// return true;
	// }

	// public Vector palmPosition(Controller c) {
	// Frame frame = c.frame();
	// return frame.hands().get(0).palmPosition();
	// }

	// public boolean indexAsCursor(Controller c) {
	// com.leapmotion.leap.Frame frame = c.frame();
	// for (Finger finger : frame.fingers()) {
	// if (finger.type() != Finger.Type.TYPE_INDEX && finger.isExtended()) {
	// return false;
	// } else {
	// if (finger.isExtended())
	// return true;
	// }
	// }
	// return false;
	// }

	// public boolean indexAndMiddleToSelect(Controller c) {
	// com.leapmotion.leap.Frame frame = c.frame();
	// return
	// (frame.fingers().fingerType(Finger.Type.TYPE_INDEX).get(0).isExtended()
	// &&
	// frame.fingers().fingerType(Finger.Type.TYPE_MIDDLE).get(0).isExtended()
	// &&
	// !frame.fingers().fingerType(Finger.Type.TYPE_THUMB).get(0).isExtended()
	// &&
	// !frame.fingers().fingerType(Finger.Type.TYPE_PINKY).get(0).isExtended()
	// && !frame.fingers().fingerType(Finger.Type.TYPE_RING).get(0).isExtended()
	// &&
	// frame.fingers().fingerType(Finger.Type.TYPE_INDEX).get(0).direction().getY()
	// > 0
	// &&
	// frame.fingers().fingerType(Finger.Type.TYPE_MIDDLE).get(0).direction().getY()
	// > 0);
	// }

	// public boolean parallel(Vector u, Vector v) {
	// Vector zero = new Vector(0, 0, 0);
	// Vector uXv = new Vector(u.getY() * v.getZ() - u.getZ() * v.getY(),
	// u.getZ() * v.getX() - u.getX() * v.getZ(),
	// u.getX() * v.getY() - u.getY() * v.getX());
	// return zero == uXv;
	// }

	// public boolean perpendicular(Vector u, Vector v) {
	// return (u.getX() * v.getX() + u.getY() * v.getY() + u.getZ() * v.getZ())
	// == 0;
	// }
}

public class LeapMotion {
	// public static void main(String[] args) {
	// LeapListener listener = new LeapListener();
	// Controller controller = new Controller();
	//
	// controller.addListener(listener);
	//
	// System.out.println("Press enter to quit");
	//
	// try {
	// System.in.read();
	//
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }
}
