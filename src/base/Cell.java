package base;

public enum Cell {
	Empty, Black, White;

	public Cell oposite(){
		if(this == Black){
			return White;
		}
		else{
			return Black;
		}
	}
}