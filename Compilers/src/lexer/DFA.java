package lexer;

public class DFA {
	static int getNextState(Lexer lexer) {
		int state = lexer.state;
		char peek = lexer.peek;
		if (state == 0) {
			switch (peek) {
			case '>':
			case '<':
			case '=':
			case '!':
				state = 1;
				break;
			case '"':
				state = 11;
				break;
			case '+':
				state = 12;
				break;
			case '-':
				state = 13;
				break;
			case '&':
				state = 14;
				break;
			case '|':
				state = 15;
				break;
			case '*':
			case '/':
			case '.':
			case ':':
				state = 2;
				lexer.tag = Tag.OPER;
				break;
			case '[':
			case ']':
			case '(':
			case ')':
			case '{':
			case '}':
			case ',':
			case ';':
				state = 2;
				lexer.tag = Tag.SYMBOL;
				break;
			default:
				state = -1;
				lexer.tag = Tag.ERROR;
				break;
			}

			if (IsDigit(peek)) {
				state = 5;
			}
			if (IsLetter(peek)) {
				state = 4;
			}
		} else if (state == 1) {
			lexer.tag = Tag.COMP;
			if (peek == '=')
				state = 2;
			else {
				state = 3;
				if (lexer.buffer.toString().equals("="))
					lexer.tag = Tag.OPER;
			}
		} else if (state == 4) {
			if (!IsDigit(peek) && !IsLetter(peek) && peek != '_') {
				state = 3;
				lexer.tag = Tag.ID;
			}
		} else if (state == 5) {
			if (peek == 'E')
				state = 8;
			else if(peek == '.') {
				state = 6;
			}
			else if (!IsDigit(peek)) {
				state = 3;
				lexer.tag = Tag.INT;
			}

		} else if (state == 6) {
			if (IsDigit(peek))
				state = 7;
			else {
				state = -1;
				lexer.tag = Tag.ERROR;
			}
		} else if (state == 7) {
			if (IsDigit(peek))
				state = 7;
			else if (peek == 'E')
				state = 8;
			else {
				state = 3;
				lexer.tag = Tag.FLOAT;
			}
			
		} else if (state == 8) {
			if (peek == '+' || peek == '-')
				state = 9;
			else {
				state = -1;
				lexer.tag = Tag.ERROR;
			}
		} else if (state == 9) {
			if (IsDigit(peek))
				state = 10;
			else {
				state = -1;
				lexer.tag = Tag.ERROR;
			}
		} else if (state == 10) {
			if (!IsDigit(peek)) {
				state = 3;
				lexer.tag = Tag.FLOAT;
			}
		}
		else if (state == 11) {
			if (peek=='"') {
				state = 2;
				lexer.tag = Tag.STR;
			}
		}else if (state == 12) {
			lexer.tag = Tag.OPER;
			if (peek == '+')
				state = 2;
			else {
				state = 3;
			}
		}else if (state == 13) {
			lexer.tag = Tag.OPER;
			if (peek == '-')
				state = 2;
			else {
				state = 3;
			}
		}else if (state == 14) {
			lexer.tag = Tag.OPER;
			if (peek == '&')
				state = 2;
			else {
				state = 3;
			}
		}else if (state == 15) {
			lexer.tag = Tag.OPER;
			if (peek == '|')
				state = 2;
			else {
				state = 3;
			}
		}
		return state;
	}

	private static boolean IsLetter(char peek) {
		return (peek >= 'A' && peek <= 'Z' || peek >= 'a' && peek <= 'z');
	}

	private static boolean IsDigit(char peek) {
		return (peek >= '0' && peek <= '9');
	}
}