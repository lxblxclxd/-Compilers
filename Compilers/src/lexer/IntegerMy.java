package lexer;

public class IntegerMy extends Token {
	public final int value;
	public IntegerMy(int v) {super(Tag.INT);value=v;}
	public String toString() {return ""+value;}
	public String show() {return Tag.getTag(tag)+"\t:\t"+value;}
}
