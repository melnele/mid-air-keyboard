
public class Cube {
	public boolean receiver, selected, visible, highlighted, inCircle;
	private float x, y, z, gridX, gridY, gridZ;
	private int id;
	float speedX;
	float speedY;
	String finger;
	int dirX;
	String text;
	int dirY;
	float maxX;
	float maxY;
	float oldX, oldY, oldZ;

	public Cube(String t, float x, float y, float z) {
		finger = "";
		this.x = x;
		this.y = y;
		this.z = z;
		text = t;
		inCircle = false;
		selected = false;
		receiver = false;
		highlighted = false;
		visible = true;
		maxX = 0;
		maxY = 0;
		speedX = 0;
		speedY = 0;
		dirX = 1;
		dirY = 1;
		oldY = y;
		oldX = x;
		oldZ = z;
	}

	public Cube(float x, float y, float z, int id) {
		this.x = x;
		this.y = y;
		this.z = z;

		inCircle = false;
		selected = false;
		receiver = false;
		highlighted = false;
		this.id = id;
		gridY = 4 - (id / 9);
		gridX = (id % 9) - 4;
		gridZ = 0;
		visible = true;
		maxX = 0;
		maxY = 0;
		speedX = 0;
		speedY = 0;
		dirX = 1;
		dirY = 1;
		oldY = y;
		oldX = x;
		oldZ = z;
	}

	public boolean isHighlighted() {
		return highlighted;
	}

	public void mark() {
		highlighted = true;
	}

	public void unmark() {
		highlighted = false;
	}

	public boolean isVisible() {
		return visible;
	}

	public void hide() {
		visible = false;
	}

	public void unhide() {
		visible = true;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getGridX() {
		return gridX;
	}

	public float getGridY() {
		return gridY;
	}

	public int getId() {
		return id;
	}

	public void setGridX(float gridX) {
		this.gridX = gridX;
	}

	public void setGridY(float gridY) {
		this.gridY = gridY;
	}

	public boolean isReceiver() {
		return receiver;
	}

	public void setReceiver(boolean receiver) {
		this.receiver = receiver;
	}

	public float getGridZ() {
		return gridZ;
	}

	public void setGridZ(float gridZ) {
		this.gridZ = gridZ;
	}

	public boolean isInCircle() {
		return inCircle;
	}

	public void setInCircle(boolean inCircle) {
		this.inCircle = inCircle;
	}
}