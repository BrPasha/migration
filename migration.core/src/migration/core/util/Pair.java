package migration.core.util;

public class Pair<F, S> {
	private F f;
	private S s;

	public Pair(F f, S s) {
		this.f = f;
		this.s = s;
	}
	
	public F first() {
		return f;
	}
	
	public S second() {
		return s;
	}
	
	public static <F, S> Pair<F, S> pair(F f, S s) {
		return new Pair<F, S>(f, s);
	}
}
