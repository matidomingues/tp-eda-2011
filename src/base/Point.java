package base;

public class Point {
	int x;
	int y;
	
	public Point(){
		new Point(0,0);
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void sumPoint(Point x){
		this.x += x.x;
		this.y += x.y;
	}
	public void setPoint(int x, int y){
		this.x = x;
		this.y = y;
	}
	@Override
	public int hashCode() {
		return x * 7 + y * 13;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
