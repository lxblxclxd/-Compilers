package lexer;

public class StringMy extends Token{
	public final String string;
	public StringMy(String s) {super(Tag.STR);string=s;}
	public String toString() {return string;}
	public String show() {return Tag.getTag(tag)+"\t:\t"+string;}
}
