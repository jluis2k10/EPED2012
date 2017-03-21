package ped.eped.tads;

public abstract class ComparatorBase<T> implements ComparatorIF<T> {
	
	public abstract int compare(T e1, T e2);
	
	public boolean isLess(T e1, T e2) {
		return compare(e1, e2) == LESS;
	}
	
	public boolean isGreater(T e1, T e2) {
		return compare(e1, e2) == GREATER;
	}
	
	public boolean isEqual(T e1, T e2) {
		return compare(e1, e2) == EQUAL;
	}
}
