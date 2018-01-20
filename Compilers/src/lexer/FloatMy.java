package lexer;

public class FloatMy extends Token {
	public final float value;
	public FloatMy(float v) {super(Tag.FLOAT);value=v;}
	public String toString() {return ""+value;}
	public String show() {return Tag.getTag(tag)+"\t:\t"+value;}
}
