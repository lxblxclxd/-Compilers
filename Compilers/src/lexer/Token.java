package lexer;

public class Token {
	public final int tag;
	public Token(int t) {tag=t;}
	public String toString() { return ""+(char)tag; }
	public String show() { return Tag.getTag(tag)+" : "+(char)tag; }
	public String getTag() { return Tag.getTag(tag); }
}
