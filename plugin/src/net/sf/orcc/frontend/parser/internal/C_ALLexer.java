// $ANTLR 3.2 Sep 23, 2009 12:02:23 C_AL__.g 2009-12-08 17:52:55

package net.sf.orcc.frontend.parser.internal;


import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class C_ALLexer extends Lexer {
    public static final int FUNCTION=64;
    public static final int EXPR_BOOL=34;
    public static final int LT=50;
    public static final int BITNOT=59;
    public static final int OUTPUTS=9;
    public static final int TRANSITION=18;
    public static final int EXPR_VAR=33;
    public static final int LOGIC_NOT=60;
    public static final int LETTER=74;
    public static final int MOD=57;
    public static final int EXPR_CALL=31;
    public static final int Decimal=78;
    public static final int INPUTS=7;
    public static final int EXPR_UNARY=27;
    public static final int EOF=-1;
    public static final int ACTION=62;
    public static final int TYPE=38;
    public static final int T__93=93;
    public static final int TYPE_ATTRS=39;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__90=90;
    public static final int EXP=58;
    public static final int STATE_VAR=17;
    public static final int GUARDS=21;
    public static final int EQ=48;
    public static final int T__99=99;
    public static final int T__98=98;
    public static final int T__97=97;
    public static final int T__96=96;
    public static final int T__95=95;
    public static final int NE=49;
    public static final int ASSIGNABLE=41;
    public static final int T__138=138;
    public static final int T__137=137;
    public static final int GE=53;
    public static final int T__136=136;
    public static final int Hexadecimal=80;
    public static final int INITIALIZE=66;
    public static final int LINE_COMMENT=85;
    public static final int DIV_INT=56;
    public static final int LOGIC_OR=43;
    public static final int WHITESPACE=87;
    public static final int NON_ASSIGNABLE=42;
    public static final int INEQUALITY=20;
    public static final int EXPRESSIONS=5;
    public static final int EXPR_IDX=32;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int T__126=126;
    public static final int SHIFT_LEFT=54;
    public static final int T__125=125;
    public static final int T__128=128;
    public static final int T__127=127;
    public static final int SHIFT_RIGHT=55;
    public static final int BITOR=45;
    public static final int T__129=129;
    public static final int PRIORITY=67;
    public static final int VARIABLE=14;
    public static final int ACTOR_DECLS=16;
    public static final int OP=28;
    public static final int ACTOR=63;
    public static final int GT=51;
    public static final int STATEMENTS=13;
    public static final int REPEAT=12;
    public static final int GUARD=65;
    public static final int CALL=24;
    public static final int T__130=130;
    public static final int EscapeSequence=82;
    public static final int T__131=131;
    public static final int OUTPUT=8;
    public static final int T__132=132;
    public static final int T__133=133;
    public static final int T__134=134;
    public static final int T__135=135;
    public static final int PARAMETERS=11;
    public static final int EXPR_BINARY=26;
    public static final int T__118=118;
    public static final int SCHEDULE=69;
    public static final int T__119=119;
    public static final int T__116=116;
    public static final int T__117=117;
    public static final int T__114=114;
    public static final int T__115=115;
    public static final int T__124=124;
    public static final int INPUT=6;
    public static final int T__123=123;
    public static final int T__122=122;
    public static final int Exponent=76;
    public static final int T__121=121;
    public static final int T__120=120;
    public static final int FLOAT=77;
    public static final int TYPE_LIST=40;
    public static final int EXPR_FLOAT=35;
    public static final int LOGIC_AND=44;
    public static final int ID=75;
    public static final int HexDigit=79;
    public static final int BITAND=47;
    public static final int EXPR_LIST=29;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int T__109=109;
    public static final int EXPR_STRING=37;
    public static final int GENERATORS=25;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int T__105=105;
    public static final int BITXOR=46;
    public static final int T__106=106;
    public static final int T__111=111;
    public static final int NUM_ELTS=61;
    public static final int T__110=110;
    public static final int T__113=113;
    public static final int T__112=112;
    public static final int PLUS=70;
    public static final int EXPR_INT=36;
    public static final int EXPRESSION=4;
    public static final int INTEGER=81;
    public static final int TRANSITIONS=19;
    public static final int PORT=10;
    public static final int T__102=102;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int MINUS=71;
    public static final int EXPR_IF=30;
    public static final int Tokens=139;
    public static final int PROCEDURE=68;
    public static final int MULTI_LINE_COMMENT=86;
    public static final int TAG=22;
    public static final int ASSIGN=23;
    public static final int VARIABLES=15;
    public static final int DIV=73;
    public static final int TIMES=72;
    public static final int OctalEscape=84;
    public static final int LE=52;
    public static final int STRING=83;

    // delegates
    public C_AL_ALBaseLexer gALBaseLexer;
    // delegators

    public C_ALLexer() {;} 
    public C_ALLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public C_ALLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
        gALBaseLexer = new C_AL_ALBaseLexer(input, state, this);
    }
    public String getGrammarFileName() { return "C_AL__.g"; }

    // $ANTLR start "T__88"
    public final void mT__88() throws RecognitionException {
        try {
            int _type = T__88;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:7:7: ( ':' )
            // C_AL__.g:7:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__88"

    // $ANTLR start "T__89"
    public final void mT__89() throws RecognitionException {
        try {
            int _type = T__89;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:8:7: ( '[' )
            // C_AL__.g:8:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__89"

    // $ANTLR start "T__90"
    public final void mT__90() throws RecognitionException {
        try {
            int _type = T__90;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:9:7: ( ']' )
            // C_AL__.g:9:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__90"

    // $ANTLR start "T__91"
    public final void mT__91() throws RecognitionException {
        try {
            int _type = T__91;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:10:7: ( ',' )
            // C_AL__.g:10:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__91"

    // $ANTLR start "T__92"
    public final void mT__92() throws RecognitionException {
        try {
            int _type = T__92;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:11:7: ( '(' )
            // C_AL__.g:11:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__92"

    // $ANTLR start "T__93"
    public final void mT__93() throws RecognitionException {
        try {
            int _type = T__93;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:12:7: ( ')' )
            // C_AL__.g:12:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__93"

    // $ANTLR start "T__94"
    public final void mT__94() throws RecognitionException {
        try {
            int _type = T__94;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:13:7: ( '==>' )
            // C_AL__.g:13:9: '==>'
            {
            match("==>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__94"

    // $ANTLR start "T__95"
    public final void mT__95() throws RecognitionException {
        try {
            int _type = T__95;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:14:7: ( '{' )
            // C_AL__.g:14:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__95"

    // $ANTLR start "T__96"
    public final void mT__96() throws RecognitionException {
        try {
            int _type = T__96;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:15:7: ( '}' )
            // C_AL__.g:15:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__96"

    // $ANTLR start "T__97"
    public final void mT__97() throws RecognitionException {
        try {
            int _type = T__97;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:16:7: ( 'const' )
            // C_AL__.g:16:9: 'const'
            {
            match("const"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__97"

    // $ANTLR start "T__98"
    public final void mT__98() throws RecognitionException {
        try {
            int _type = T__98;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:17:7: ( '=' )
            // C_AL__.g:17:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__98"

    // $ANTLR start "T__99"
    public final void mT__99() throws RecognitionException {
        try {
            int _type = T__99;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:18:7: ( ';' )
            // C_AL__.g:18:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__99"

    // $ANTLR start "T__100"
    public final void mT__100() throws RecognitionException {
        try {
            int _type = T__100;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:19:8: ( 'return' )
            // C_AL__.g:19:10: 'return'
            {
            match("return"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__100"

    // $ANTLR start "T__101"
    public final void mT__101() throws RecognitionException {
        try {
            int _type = T__101;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:20:8: ( 'void' )
            // C_AL__.g:20:10: 'void'
            {
            match("void"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__101"

    // $ANTLR start "T__102"
    public final void mT__102() throws RecognitionException {
        try {
            int _type = T__102;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:21:8: ( 'import' )
            // C_AL__.g:21:10: 'import'
            {
            match("import"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__102"

    // $ANTLR start "T__103"
    public final void mT__103() throws RecognitionException {
        try {
            int _type = T__103;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:22:8: ( 'all' )
            // C_AL__.g:22:10: 'all'
            {
            match("all"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__103"

    // $ANTLR start "T__104"
    public final void mT__104() throws RecognitionException {
        try {
            int _type = T__104;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:23:8: ( '||' )
            // C_AL__.g:23:10: '||'
            {
            match("||"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__104"

    // $ANTLR start "T__105"
    public final void mT__105() throws RecognitionException {
        try {
            int _type = T__105;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:24:8: ( '&&' )
            // C_AL__.g:24:10: '&&'
            {
            match("&&"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__105"

    // $ANTLR start "T__106"
    public final void mT__106() throws RecognitionException {
        try {
            int _type = T__106;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:25:8: ( '|' )
            // C_AL__.g:25:10: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__106"

    // $ANTLR start "T__107"
    public final void mT__107() throws RecognitionException {
        try {
            int _type = T__107;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:26:8: ( '^' )
            // C_AL__.g:26:10: '^'
            {
            match('^'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__107"

    // $ANTLR start "T__108"
    public final void mT__108() throws RecognitionException {
        try {
            int _type = T__108;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:27:8: ( '&' )
            // C_AL__.g:27:10: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__108"

    // $ANTLR start "T__109"
    public final void mT__109() throws RecognitionException {
        try {
            int _type = T__109;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:28:8: ( '==' )
            // C_AL__.g:28:10: '=='
            {
            match("=="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__109"

    // $ANTLR start "T__110"
    public final void mT__110() throws RecognitionException {
        try {
            int _type = T__110;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:29:8: ( '!=' )
            // C_AL__.g:29:10: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__110"

    // $ANTLR start "T__111"
    public final void mT__111() throws RecognitionException {
        try {
            int _type = T__111;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:30:8: ( '<' )
            // C_AL__.g:30:10: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__111"

    // $ANTLR start "T__112"
    public final void mT__112() throws RecognitionException {
        try {
            int _type = T__112;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:31:8: ( '>' )
            // C_AL__.g:31:10: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__112"

    // $ANTLR start "T__113"
    public final void mT__113() throws RecognitionException {
        try {
            int _type = T__113;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:32:8: ( '<=' )
            // C_AL__.g:32:10: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__113"

    // $ANTLR start "T__114"
    public final void mT__114() throws RecognitionException {
        try {
            int _type = T__114;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:33:8: ( '>=' )
            // C_AL__.g:33:10: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__114"

    // $ANTLR start "T__115"
    public final void mT__115() throws RecognitionException {
        try {
            int _type = T__115;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:34:8: ( '<<' )
            // C_AL__.g:34:10: '<<'
            {
            match("<<"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__115"

    // $ANTLR start "T__116"
    public final void mT__116() throws RecognitionException {
        try {
            int _type = T__116;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:35:8: ( '>>' )
            // C_AL__.g:35:10: '>>'
            {
            match(">>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__116"

    // $ANTLR start "T__117"
    public final void mT__117() throws RecognitionException {
        try {
            int _type = T__117;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:36:8: ( '%' )
            // C_AL__.g:36:10: '%'
            {
            match('%'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__117"

    // $ANTLR start "T__118"
    public final void mT__118() throws RecognitionException {
        try {
            int _type = T__118;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:37:8: ( '**' )
            // C_AL__.g:37:10: '**'
            {
            match("**"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__118"

    // $ANTLR start "T__119"
    public final void mT__119() throws RecognitionException {
        try {
            int _type = T__119;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:38:8: ( '~' )
            // C_AL__.g:38:10: '~'
            {
            match('~'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__119"

    // $ANTLR start "T__120"
    public final void mT__120() throws RecognitionException {
        try {
            int _type = T__120;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:39:8: ( '!' )
            // C_AL__.g:39:10: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__120"

    // $ANTLR start "T__121"
    public final void mT__121() throws RecognitionException {
        try {
            int _type = T__121;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:40:8: ( '#' )
            // C_AL__.g:40:10: '#'
            {
            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__121"

    // $ANTLR start "T__122"
    public final void mT__122() throws RecognitionException {
        try {
            int _type = T__122;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:41:8: ( 'for' )
            // C_AL__.g:41:10: 'for'
            {
            match("for"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__122"

    // $ANTLR start "T__123"
    public final void mT__123() throws RecognitionException {
        try {
            int _type = T__123;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:42:8: ( 'if' )
            // C_AL__.g:42:10: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__123"

    // $ANTLR start "T__124"
    public final void mT__124() throws RecognitionException {
        try {
            int _type = T__124;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:43:8: ( 'else' )
            // C_AL__.g:43:10: 'else'
            {
            match("else"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__124"

    // $ANTLR start "T__125"
    public final void mT__125() throws RecognitionException {
        try {
            int _type = T__125;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:44:8: ( 'true' )
            // C_AL__.g:44:10: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__125"

    // $ANTLR start "T__126"
    public final void mT__126() throws RecognitionException {
        try {
            int _type = T__126;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:45:8: ( 'false' )
            // C_AL__.g:45:10: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__126"

    // $ANTLR start "T__127"
    public final void mT__127() throws RecognitionException {
        try {
            int _type = T__127;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:46:8: ( '..' )
            // C_AL__.g:46:10: '..'
            {
            match(".."); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__127"

    // $ANTLR start "T__128"
    public final void mT__128() throws RecognitionException {
        try {
            int _type = T__128;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:47:8: ( 'end' )
            // C_AL__.g:47:10: 'end'
            {
            match("end"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__128"

    // $ANTLR start "T__129"
    public final void mT__129() throws RecognitionException {
        try {
            int _type = T__129;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:48:8: ( '.' )
            // C_AL__.g:48:10: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__129"

    // $ANTLR start "T__130"
    public final void mT__130() throws RecognitionException {
        try {
            int _type = T__130;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:49:8: ( 'fsm' )
            // C_AL__.g:49:10: 'fsm'
            {
            match("fsm"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__130"

    // $ANTLR start "T__131"
    public final void mT__131() throws RecognitionException {
        try {
            int _type = T__131;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:50:8: ( '-->' )
            // C_AL__.g:50:10: '-->'
            {
            match("-->"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__131"

    // $ANTLR start "T__132"
    public final void mT__132() throws RecognitionException {
        try {
            int _type = T__132;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:51:8: ( 'while' )
            // C_AL__.g:51:10: 'while'
            {
            match("while"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__132"

    // $ANTLR start "T__133"
    public final void mT__133() throws RecognitionException {
        try {
            int _type = T__133;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:52:8: ( 'bool' )
            // C_AL__.g:52:10: 'bool'
            {
            match("bool"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__133"

    // $ANTLR start "T__134"
    public final void mT__134() throws RecognitionException {
        try {
            int _type = T__134;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:53:8: ( 'char' )
            // C_AL__.g:53:10: 'char'
            {
            match("char"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__134"

    // $ANTLR start "T__135"
    public final void mT__135() throws RecognitionException {
        try {
            int _type = T__135;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:54:8: ( 'short' )
            // C_AL__.g:54:10: 'short'
            {
            match("short"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__135"

    // $ANTLR start "T__136"
    public final void mT__136() throws RecognitionException {
        try {
            int _type = T__136;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:55:8: ( 'int' )
            // C_AL__.g:55:10: 'int'
            {
            match("int"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__136"

    // $ANTLR start "T__137"
    public final void mT__137() throws RecognitionException {
        try {
            int _type = T__137;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:56:8: ( 'unsigned' )
            // C_AL__.g:56:10: 'unsigned'
            {
            match("unsigned"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__137"

    // $ANTLR start "T__138"
    public final void mT__138() throws RecognitionException {
        try {
            int _type = T__138;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C_AL__.g:57:8: ( 'float' )
            // C_AL__.g:57:10: 'float'
            {
            match("float"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__138"

    public void mTokens() throws RecognitionException {
        // C_AL__.g:1:8: ( T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | T__112 | T__113 | T__114 | T__115 | T__116 | T__117 | T__118 | T__119 | T__120 | T__121 | T__122 | T__123 | T__124 | T__125 | T__126 | T__127 | T__128 | T__129 | T__130 | T__131 | T__132 | T__133 | T__134 | T__135 | T__136 | T__137 | T__138 | ALBaseLexer. Tokens )
        int alt1=52;
        alt1 = dfa1.predict(input);
        switch (alt1) {
            case 1 :
                // C_AL__.g:1:10: T__88
                {
                mT__88(); 

                }
                break;
            case 2 :
                // C_AL__.g:1:16: T__89
                {
                mT__89(); 

                }
                break;
            case 3 :
                // C_AL__.g:1:22: T__90
                {
                mT__90(); 

                }
                break;
            case 4 :
                // C_AL__.g:1:28: T__91
                {
                mT__91(); 

                }
                break;
            case 5 :
                // C_AL__.g:1:34: T__92
                {
                mT__92(); 

                }
                break;
            case 6 :
                // C_AL__.g:1:40: T__93
                {
                mT__93(); 

                }
                break;
            case 7 :
                // C_AL__.g:1:46: T__94
                {
                mT__94(); 

                }
                break;
            case 8 :
                // C_AL__.g:1:52: T__95
                {
                mT__95(); 

                }
                break;
            case 9 :
                // C_AL__.g:1:58: T__96
                {
                mT__96(); 

                }
                break;
            case 10 :
                // C_AL__.g:1:64: T__97
                {
                mT__97(); 

                }
                break;
            case 11 :
                // C_AL__.g:1:70: T__98
                {
                mT__98(); 

                }
                break;
            case 12 :
                // C_AL__.g:1:76: T__99
                {
                mT__99(); 

                }
                break;
            case 13 :
                // C_AL__.g:1:82: T__100
                {
                mT__100(); 

                }
                break;
            case 14 :
                // C_AL__.g:1:89: T__101
                {
                mT__101(); 

                }
                break;
            case 15 :
                // C_AL__.g:1:96: T__102
                {
                mT__102(); 

                }
                break;
            case 16 :
                // C_AL__.g:1:103: T__103
                {
                mT__103(); 

                }
                break;
            case 17 :
                // C_AL__.g:1:110: T__104
                {
                mT__104(); 

                }
                break;
            case 18 :
                // C_AL__.g:1:117: T__105
                {
                mT__105(); 

                }
                break;
            case 19 :
                // C_AL__.g:1:124: T__106
                {
                mT__106(); 

                }
                break;
            case 20 :
                // C_AL__.g:1:131: T__107
                {
                mT__107(); 

                }
                break;
            case 21 :
                // C_AL__.g:1:138: T__108
                {
                mT__108(); 

                }
                break;
            case 22 :
                // C_AL__.g:1:145: T__109
                {
                mT__109(); 

                }
                break;
            case 23 :
                // C_AL__.g:1:152: T__110
                {
                mT__110(); 

                }
                break;
            case 24 :
                // C_AL__.g:1:159: T__111
                {
                mT__111(); 

                }
                break;
            case 25 :
                // C_AL__.g:1:166: T__112
                {
                mT__112(); 

                }
                break;
            case 26 :
                // C_AL__.g:1:173: T__113
                {
                mT__113(); 

                }
                break;
            case 27 :
                // C_AL__.g:1:180: T__114
                {
                mT__114(); 

                }
                break;
            case 28 :
                // C_AL__.g:1:187: T__115
                {
                mT__115(); 

                }
                break;
            case 29 :
                // C_AL__.g:1:194: T__116
                {
                mT__116(); 

                }
                break;
            case 30 :
                // C_AL__.g:1:201: T__117
                {
                mT__117(); 

                }
                break;
            case 31 :
                // C_AL__.g:1:208: T__118
                {
                mT__118(); 

                }
                break;
            case 32 :
                // C_AL__.g:1:215: T__119
                {
                mT__119(); 

                }
                break;
            case 33 :
                // C_AL__.g:1:222: T__120
                {
                mT__120(); 

                }
                break;
            case 34 :
                // C_AL__.g:1:229: T__121
                {
                mT__121(); 

                }
                break;
            case 35 :
                // C_AL__.g:1:236: T__122
                {
                mT__122(); 

                }
                break;
            case 36 :
                // C_AL__.g:1:243: T__123
                {
                mT__123(); 

                }
                break;
            case 37 :
                // C_AL__.g:1:250: T__124
                {
                mT__124(); 

                }
                break;
            case 38 :
                // C_AL__.g:1:257: T__125
                {
                mT__125(); 

                }
                break;
            case 39 :
                // C_AL__.g:1:264: T__126
                {
                mT__126(); 

                }
                break;
            case 40 :
                // C_AL__.g:1:271: T__127
                {
                mT__127(); 

                }
                break;
            case 41 :
                // C_AL__.g:1:278: T__128
                {
                mT__128(); 

                }
                break;
            case 42 :
                // C_AL__.g:1:285: T__129
                {
                mT__129(); 

                }
                break;
            case 43 :
                // C_AL__.g:1:292: T__130
                {
                mT__130(); 

                }
                break;
            case 44 :
                // C_AL__.g:1:299: T__131
                {
                mT__131(); 

                }
                break;
            case 45 :
                // C_AL__.g:1:306: T__132
                {
                mT__132(); 

                }
                break;
            case 46 :
                // C_AL__.g:1:313: T__133
                {
                mT__133(); 

                }
                break;
            case 47 :
                // C_AL__.g:1:320: T__134
                {
                mT__134(); 

                }
                break;
            case 48 :
                // C_AL__.g:1:327: T__135
                {
                mT__135(); 

                }
                break;
            case 49 :
                // C_AL__.g:1:334: T__136
                {
                mT__136(); 

                }
                break;
            case 50 :
                // C_AL__.g:1:341: T__137
                {
                mT__137(); 

                }
                break;
            case 51 :
                // C_AL__.g:1:348: T__138
                {
                mT__138(); 

                }
                break;
            case 52 :
                // C_AL__.g:1:355: ALBaseLexer. Tokens
                {
                gALBaseLexer.mTokens(); 

                }
                break;

        }

    }


    protected DFA1 dfa1 = new DFA1(this);
    static final String DFA1_eotS =
        "\7\uffff\1\45\2\uffff\1\43\1\uffff\4\43\1\57\1\61\1\uffff\1\63"+
        "\1\66\1\71\1\uffff\1\43\2\uffff\3\43\1\103\5\43\1\uffff\1\112\1"+
        "\uffff\5\43\1\120\2\43\15\uffff\7\43\3\uffff\4\43\2\uffff\5\43\1"+
        "\uffff\1\143\1\144\1\145\1\43\1\147\2\43\1\152\6\43\1\161\1\43\1"+
        "\163\1\43\3\uffff\1\43\1\uffff\1\43\1\167\1\uffff\1\170\1\43\1\172"+
        "\2\43\1\175\1\uffff\1\43\1\uffff\1\43\1\u0080\1\u0081\2\uffff\1"+
        "\u0082\1\uffff\1\u0083\1\43\1\uffff\1\u0085\1\u0086\4\uffff\1\43"+
        "\2\uffff\1\43\1\u0089\1\uffff";
    static final String DFA1_eofS =
        "\u008a\uffff";
    static final String DFA1_minS =
        "\1\11\6\uffff\1\75\2\uffff\1\150\1\uffff\1\145\1\157\1\146\1\154"+
        "\1\174\1\46\1\uffff\1\75\1\74\1\75\1\uffff\1\52\2\uffff\1\141\1"+
        "\154\1\162\1\56\1\55\1\150\1\157\1\150\1\156\1\uffff\1\76\1\uffff"+
        "\1\156\1\141\1\164\1\151\1\160\1\44\1\164\1\154\15\uffff\1\162\1"+
        "\154\1\155\1\157\1\163\1\144\1\165\3\uffff\1\151\2\157\1\163\2\uffff"+
        "\1\163\1\162\1\165\1\144\1\157\1\uffff\3\44\1\163\1\44\1\141\1\145"+
        "\1\44\1\145\2\154\1\162\1\151\1\164\1\44\1\162\1\44\1\162\3\uffff"+
        "\1\145\1\uffff\1\164\1\44\1\uffff\1\44\1\145\1\44\1\164\1\147\1"+
        "\44\1\uffff\1\156\1\uffff\1\164\2\44\2\uffff\1\44\1\uffff\1\44\1"+
        "\156\1\uffff\2\44\4\uffff\1\145\2\uffff\1\144\1\44\1\uffff";
    static final String DFA1_maxS =
        "\1\176\6\uffff\1\75\2\uffff\1\157\1\uffff\1\145\1\157\1\156\1\154"+
        "\1\174\1\46\1\uffff\2\75\1\76\1\uffff\1\52\2\uffff\1\163\1\156\1"+
        "\162\1\71\1\55\1\150\1\157\1\150\1\156\1\uffff\1\76\1\uffff\1\156"+
        "\1\141\1\164\1\151\1\160\1\172\1\164\1\154\15\uffff\1\162\1\154"+
        "\1\155\1\157\1\163\1\144\1\165\3\uffff\1\151\2\157\1\163\2\uffff"+
        "\1\163\1\162\1\165\1\144\1\157\1\uffff\3\172\1\163\1\172\1\141\1"+
        "\145\1\172\1\145\2\154\1\162\1\151\1\164\1\172\1\162\1\172\1\162"+
        "\3\uffff\1\145\1\uffff\1\164\1\172\1\uffff\1\172\1\145\1\172\1\164"+
        "\1\147\1\172\1\uffff\1\156\1\uffff\1\164\2\172\2\uffff\1\172\1\uffff"+
        "\1\172\1\156\1\uffff\2\172\4\uffff\1\145\2\uffff\1\144\1\172\1\uffff";
    static final String DFA1_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\uffff\1\10\1\11\1\uffff\1\14"+
        "\6\uffff\1\24\3\uffff\1\36\1\uffff\1\40\1\42\11\uffff\1\64\1\uffff"+
        "\1\13\10\uffff\1\21\1\23\1\22\1\25\1\27\1\41\1\32\1\34\1\30\1\33"+
        "\1\35\1\31\1\37\7\uffff\1\50\1\52\1\54\4\uffff\1\7\1\26\5\uffff"+
        "\1\44\22\uffff\1\61\1\20\1\43\1\uffff\1\53\2\uffff\1\51\6\uffff"+
        "\1\57\1\uffff\1\16\3\uffff\1\45\1\46\1\uffff\1\56\2\uffff\1\12\2"+
        "\uffff\1\47\1\63\1\55\1\60\1\uffff\1\15\1\17\2\uffff\1\62";
    static final String DFA1_specialS =
        "\u008a\uffff}>";
    static final String[] DFA1_transitionS = {
            "\2\43\1\uffff\2\43\22\uffff\1\43\1\23\1\43\1\31\1\43\1\26\1"+
            "\21\1\uffff\1\5\1\6\1\27\1\43\1\4\1\36\1\35\13\43\1\1\1\13\1"+
            "\24\1\7\1\25\2\uffff\32\43\1\2\1\uffff\1\3\1\22\1\43\1\uffff"+
            "\1\17\1\40\1\12\1\43\1\33\1\32\2\43\1\16\10\43\1\14\1\41\1\34"+
            "\1\42\1\15\1\37\3\43\1\10\1\20\1\11\1\30",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\44",
            "",
            "",
            "\1\47\6\uffff\1\46",
            "",
            "\1\50",
            "\1\51",
            "\1\53\6\uffff\1\52\1\54",
            "\1\55",
            "\1\56",
            "\1\60",
            "",
            "\1\62",
            "\1\65\1\64",
            "\1\67\1\70",
            "",
            "\1\72",
            "",
            "",
            "\1\74\12\uffff\1\76\2\uffff\1\73\3\uffff\1\75",
            "\1\77\1\uffff\1\100",
            "\1\101",
            "\1\102\1\uffff\12\43",
            "\1\104",
            "\1\105",
            "\1\106",
            "\1\107",
            "\1\110",
            "",
            "\1\111",
            "",
            "\1\113",
            "\1\114",
            "\1\115",
            "\1\116",
            "\1\117",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            "\1\121",
            "\1\122",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\123",
            "\1\124",
            "\1\125",
            "\1\126",
            "\1\127",
            "\1\130",
            "\1\131",
            "",
            "",
            "",
            "\1\132",
            "\1\133",
            "\1\134",
            "\1\135",
            "",
            "",
            "\1\136",
            "\1\137",
            "\1\140",
            "\1\141",
            "\1\142",
            "",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            "\1\146",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            "\1\150",
            "\1\151",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            "\1\153",
            "\1\154",
            "\1\155",
            "\1\156",
            "\1\157",
            "\1\160",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            "\1\162",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            "\1\164",
            "",
            "",
            "",
            "\1\165",
            "",
            "\1\166",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            "",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            "\1\171",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            "\1\173",
            "\1\174",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            "",
            "\1\176",
            "",
            "\1\177",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            "",
            "",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            "",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            "\1\u0084",
            "",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            "",
            "",
            "",
            "",
            "\1\u0087",
            "",
            "",
            "\1\u0088",
            "\1\43\13\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32"+
            "\43",
            ""
    };

    static final short[] DFA1_eot = DFA.unpackEncodedString(DFA1_eotS);
    static final short[] DFA1_eof = DFA.unpackEncodedString(DFA1_eofS);
    static final char[] DFA1_min = DFA.unpackEncodedStringToUnsignedChars(DFA1_minS);
    static final char[] DFA1_max = DFA.unpackEncodedStringToUnsignedChars(DFA1_maxS);
    static final short[] DFA1_accept = DFA.unpackEncodedString(DFA1_acceptS);
    static final short[] DFA1_special = DFA.unpackEncodedString(DFA1_specialS);
    static final short[][] DFA1_transition;

    static {
        int numStates = DFA1_transitionS.length;
        DFA1_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA1_transition[i] = DFA.unpackEncodedString(DFA1_transitionS[i]);
        }
    }

    class DFA1 extends DFA {

        public DFA1(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 1;
            this.eot = DFA1_eot;
            this.eof = DFA1_eof;
            this.min = DFA1_min;
            this.max = DFA1_max;
            this.accept = DFA1_accept;
            this.special = DFA1_special;
            this.transition = DFA1_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | T__112 | T__113 | T__114 | T__115 | T__116 | T__117 | T__118 | T__119 | T__120 | T__121 | T__122 | T__123 | T__124 | T__125 | T__126 | T__127 | T__128 | T__129 | T__130 | T__131 | T__132 | T__133 | T__134 | T__135 | T__136 | T__137 | T__138 | ALBaseLexer. Tokens );";
        }
    }
 

}