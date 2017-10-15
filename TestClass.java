
public class TestClass {
	public static void main(String[] args) {
		Tree t1 = new Tree(123, 456, "alIVe", "Fair", "birch", 99, "MaNhattan", 13.2, 2.39);
		Tree t2 = new Tree(560, 456, "alIve", "FaiR", "oAk", 2299, "Brooklyn", 1.23, 2.39);
		Tree t3 = new Tree(539, 456, "alIve", "FaiR", "linden", 2299, "Queens", 1.23, 2.39);
		
		TreeCollection tc = new TreeCollection();
		tc.add(t1);
		tc.add(t2);
		tc.add(t3);
		
		System.out.println(tc.toString());
	}
}
