package com.picusviridis.astparser.tokenizer;

import java.util.Arrays;

import com.picusviridis.astparser.utils.Result;

public class StringTokenizer implements ITokenizer
{
    private String        toParse;
    private int           pos;
    private int           maxPos;
    private TokenType     curToken;
    private double        doubleValue;
    private String        identifierValue;
    private String        errorValue;
    private StringBuilder buffer;

    public StringTokenizer(String s)
    {
        this(s, 0, s.length());
    }

    public StringTokenizer(String s, int startIndex)
    {
        this(s, startIndex, s.length());
    }

    public StringTokenizer(String s, int startIndex, int count)
    {
        this.curToken = TokenType.NONE;
        this.toParse = s;
        this.pos = startIndex;
        this.maxPos = startIndex + count;
        this.buffer = new StringBuilder();
    }

    // region Input reader

    char peek()
    {
        if (!this.isEnd())
        {
            return this.toParse.charAt(this.pos);
        }
        throw new IllegalAccessError("End of string, can't peek");
    }

    boolean read(int c)
    {
        if (this.peek() == c)
        {
            this.read();
            return true;
        }
        return false;
    }

    char read()
    {
        if (!this.isEnd())
        {
            return this.toParse.charAt(this.pos++);
        }
        throw new IllegalAccessError("End of string, can't read");
    }

    void forward()
    {
        if (!this.isEnd())
        {
            ++this.pos;
            return;
        }
        throw new IllegalAccessError("End of string, can't go forward");
    }

    boolean isEnd()
    {
        return this.pos >= this.maxPos;
    }

    // endregion

    @Override
    public TokenType currentToken()
    {
        return this.curToken;
    }

    @Override
    public boolean match(TokenType t)
    {
        if (this.curToken == t)
        {
            this.getNextToken();
            return true;
        }
        return false;
    }

    @Override
    public boolean matchOne(TokenType... t)
    {
        if (Arrays.asList(t).indexOf(this.curToken) > -1)
        {
            this.getNextToken();
            return true;
        }
        return false;
    }

    @Override
    public Result<Double> matchDouble()
    {
        Double value = this.doubleValue;
        if (this.curToken == TokenType.NUMBER)
        {
            this.getNextToken();
            return new Result<>(true, value);
        }
        return new Result<>(false, value);
    }

    @Override
    public Result<String> matchString()
    {
        String value = this.identifierValue;
        if (this.curToken == TokenType.STRING)
        {
            this.getNextToken();
            return new Result<>(true, value);
        }
        return new Result<>(false, value);
    }

    @Override
    public Result<String> matchVariable()
    {
        String value;
        if (this.curToken == TokenType.VARIABLE)
        {
            value = this.identifierValue;
            this.getNextToken();
            return new Result<>(true, value);
        }
        value = null;
        return new Result<>(false, value);
    }

    @Override
    public boolean matchInteger(int expectedValue)
    {
        if (this.curToken == TokenType.NUMBER && this.doubleValue < Integer.MAX_VALUE
                && (int) this.doubleValue == expectedValue)
        {
            this.getNextToken();
            return true;
        }
        return false;
    }

    @Override
    public Result<Integer> matchInteger()
    {
        Integer value;
        if (this.curToken == TokenType.NUMBER && this.doubleValue < Integer.MAX_VALUE)
        {
            value = (int) this.doubleValue;
            this.getNextToken();
            return new Result<>(true, value);
        }
        value = 0;
        return new Result<>(false, value);
    }

    public boolean matchString(String identifier)
    {
        if (this.curToken == TokenType.STRING && this.identifierValue == identifier)
        {
            this.getNextToken();
            return true;
        }
        return false;
    }

    @Override
    public Result<String> matchFunction()
    {
        String value;
        if (this.curToken == TokenType.FUNCTION)
        {
            value = this.identifierValue;
            this.getNextToken();
            return new Result<>(true, value);
        }
        value = null;
        return new Result<>(false, value);
    }

    @Override
    public Result<String> matchError()
    {
        String value;
        if (this.curToken == TokenType.ERROR)
        {
            value = this.errorValue;
            this.getNextToken();
            return new Result<>(true, value);
        }
        value = null;
        return new Result<>(false, value);
    }

    @Override
    public TokenType getNextToken()
    {
        if (this.isEnd())
        {
            return this.curToken = TokenType.END_OF_INPUT;
        }
        char c = this.read();
        while (Character.isWhitespace(c))
        {
            if (this.isEnd())
            {
                return this.curToken = TokenType.END_OF_INPUT;
            }
            c = this.read();
        }
        switch (c)
        {
            case '+':
                this.curToken = TokenType.PLUS;
                break;
            case '-':
                this.curToken = TokenType.MINUS;
                break;
            case '*':
                this.curToken = TokenType.MULT;
                break;
            case '/':
                this.curToken = TokenType.DIV;
                break;
            case '%':
                this.curToken = TokenType.MOD;
                break;
            case '!':
                if (!this.isEnd() && this.peek() == '=')
                {
                    this.curToken = TokenType.NOT_EQ;
                    this.forward();
                }
                else
                {
                    this.curToken = TokenType.NOT;
                }
                break;
            case '|':
                if (!this.isEnd() && this.read() == '|')
                {
                    this.curToken = TokenType.OR;
                }
                else
                {
                    this.curToken = TokenType.ERROR;
                    this.errorValue = "Unknown operator |. Did you mean || ?";
                }
                break;
            case '&':
                if (!this.isEnd() && this.read() == '&')
                {
                    this.curToken = TokenType.AND;
                }
                else
                {
                    this.curToken = TokenType.ERROR;
                    this.errorValue = "Unknown operator &. Did you mean && ?";
                }
                break;
            case '>':
                if (!this.isEnd() && this.peek() == '=')
                {
                    this.curToken = TokenType.SUP_EQ;
                    this.forward();
                }
                else
                {
                    this.curToken = TokenType.SUP;
                }
                break;
            case '<':
                if (!this.isEnd() && this.peek() == '=')
                {
                    this.curToken = TokenType.INF_EQ;
                    this.forward();
                }
                else
                {
                    this.curToken = TokenType.INF;
                }
                break;
            case '=':
                if (!this.isEnd() && this.read() == '=')
                {
                    this.curToken = TokenType.EQ;
                }
                else
                {
                    this.curToken = TokenType.ERROR;
                    this.errorValue = "Unknown operator =. Did you mean == ?";
                }
                break;
            case '^':
                this.curToken = TokenType.POW;
                break;
            case ':':
                this.curToken = TokenType.COLON;
                break;
            case '?':
                this.curToken = TokenType.QUESTION_MARK;
                break;
            case ',':
                this.curToken = TokenType.COMMA;
                break;
            case '(':
                this.curToken = TokenType.OPEN_PAR;
                break;
            case ')':
                this.curToken = TokenType.CLOSE_PAR;
                break;
            case '.':
                // A number can start with a dot.
                this.curToken = TokenType.NUMBER;
                int ic = fromDecDigit(this.peek());
                if (ic >= 0)
                {
                    this.read();
                    this.curToken = this.readNumber(ic, true);
                }
                break;
            default:
                int digit = fromDecDigit(c);
                if (digit >= 0)
                {
                    this.curToken = this.readNumber(digit, false);
                }
                else if (c == '"')
                {
                    this.curToken = TokenType.STRING;
                    this.buffer = new StringBuilder();

                    while (!this.isEnd() && (c = this.peek()) != '"')
                    {
                        this.buffer.append(c);
                        this.forward();
                    }

                    // To skip last quotation mark
                    if (!this.isEnd())
                    {
                        this.forward();
                    }

                    this.identifierValue = this.buffer.toString();
                }
                else if (Character.isLetter(c) || c == '_')
                {
                    this.buffer = new StringBuilder();

                    this.buffer.append(c);
                    while (!this.isEnd() && (Character.isDigit(c = this.peek()) || Character.isLetter(c) || c == '_'))
                    {
                        this.buffer.append(c);
                        this.forward();
                    }

                    if (!this.isEnd() && this.peek() == '(')
                    {
                        this.curToken = TokenType.FUNCTION;
                    }
                    else
                    {
                        this.curToken = TokenType.VARIABLE;
                    }

                    this.identifierValue = this.buffer.toString();
                }
                else
                {
                    this.curToken = TokenType.ERROR;
                    this.errorValue = String.format(
                            "Error while parsing token: %s is not a valid token value (current character : %s)",
                            this.buffer.toString(), c);
                }

        }
        return this.curToken;
    }

    /**
     * May return an error code or a number token.
     * Whatever the read result is, the buffer contains the token.
     *
     * @param firstDigit
     * @param hasDot
     * @return
     */
    private TokenType readNumber(int firstDigit, boolean hasDot)
    {
        boolean hasExp = false;
        int nextRequired = 0;
        this.buffer = new StringBuilder();
        if (hasDot)
        {
            this.buffer.append("0.");
        }
        else
        {
            this.doubleValue = firstDigit;
        }
        this.buffer.append((char) (firstDigit + '0'));
        for (;;)
        {
            if (this.isEnd())
            {
                break;
            }
            int ic = this.peek();
            if (ic >= '0' && ic <= '9')
            {
                this.read();
                this.buffer.append((char) ic);
                if (!hasDot)
                {
                    this.doubleValue = this.doubleValue * 10 + (ic - '0');
                }
                else
                {
                    this.doubleValue = Double.parseDouble(this.buffer.toString());
                }
                nextRequired = 0;
                continue;
            }
            if (!hasExp && (ic == 'e' || ic == 'E'))
            {
                this.read();
                hasExp = hasDot = true;
                this.buffer.append('E');
                if (this.isEnd())
                {
                    this.errorValue = String.format("Invalid number value : %s", this.buffer);
                    return TokenType.ERROR;
                }
                if (this.read('-'))
                {
                    this.buffer.append('-');
                }
                else
                {
                    this.read('+');
                }
                // At least a digit is required.
                nextRequired = 1;
                continue;
            }
            if (ic == '.')
            {
                if (!hasDot)
                {
                    this.read();
                    hasDot = true;
                    this.buffer.append('.');
                    // Dot can be the last character.
                    // Use 2 to remember that dot has been found: we consider it as an integer value.
                    nextRequired = 2;
                    continue;
                }
                this.errorValue = "Number value is immediately followed by an identifier : " + this.buffer.toString();
                return TokenType.ERROR;
            }

            if (nextRequired == 1)
            {
                this.errorValue = "Unterminated number : " + this.buffer.toString();
                return TokenType.ERROR;
            }
            // To be valid, the number must be followed by an operator, a closing parenthesis or a white space (including new line)
            // We do not handle all cases here, except the 45DD.
            if (ic != ',' && ic != ')' && ic != '+' && ic != '-' && ic != '*' && ic != '/' && ic != '^' && ic != ' '
                    && ic != '\n' && ic != '\r' && ic != '&' && ic != '|' && ic != '<' && ic != '>' && ic != '!'
                    && ic != '=' && ic != '?' && ic != ':')
            {
                this.errorValue = "Number value is immediately followed by an identifier : " + this.buffer.toString();
                return TokenType.ERROR;
            }
            break;
        }
        // Consider number terminated by dot as integer.
        if (hasDot && nextRequired != 2)
        {
            return TokenType.NUMBER;
        }
        return TokenType.NUMBER;
    }

    private static int fromDecDigit(int c)
    {
        c -= '0';
        return c >= 0 && c <= 9 ? c : -1;
    }
}
