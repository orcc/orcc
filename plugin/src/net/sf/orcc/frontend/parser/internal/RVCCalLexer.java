// $ANTLR 3.2 Sep 23, 2009 12:02:23 RVCCal__.g 2009-11-14 17:11:52

package net.sf.orcc.frontend.parser.internal;


import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class RVCCalLexer extends Lexer {
    public static final int FUNCTION=59;
    public static final int EXPR_BOOL=26;
    public static final int LT=42;
    public static final int BITNOT=51;
    public static final int OUTPUTS=5;
    public static final int TRANSITION=12;
    public static final int EXPR_VAR=25;
    public static final int LOGIC_NOT=52;
    public static final int LETTER=70;
    public static final int MOD=49;
    public static final int EXPR_CALL=23;
    public static final int Decimal=74;
    public static final int INPUTS=4;
    public static final int EXPR_UNARY=19;
    public static final int EOF=-1;
    public static final int ACTION=57;
    public static final int TYPE=30;
    public static final int T__93=93;
    public static final int TYPE_ATTRS=31;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__90=90;
    public static final int EXP=50;
    public static final int STATE_VAR=11;
    public static final int GUARDS=15;
    public static final int EQ=40;
    public static final int T__99=99;
    public static final int T__98=98;
    public static final int T__97=97;
    public static final int T__96=96;
    public static final int NE=41;
    public static final int T__95=95;
    public static final int ASSIGNABLE=33;
    public static final int GE=45;
    public static final int Hexadecimal=76;
    public static final int INITIALIZE=61;
    public static final int LINE_COMMENT=81;
    public static final int DIV_INT=48;
    public static final int LOGIC_OR=35;
    public static final int WHITESPACE=83;
    public static final int INEQUALITY=14;
    public static final int NON_ASSIGNABLE=34;
    public static final int EXPRESSIONS=56;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int EXPR_IDX=24;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int T__126=126;
    public static final int SHIFT_LEFT=46;
    public static final int T__125=125;
    public static final int T__128=128;
    public static final int T__127=127;
    public static final int SHIFT_RIGHT=47;
    public static final int T__129=129;
    public static final int BITOR=37;
    public static final int PRIORITY=62;
    public static final int VARIABLE=8;
    public static final int ACTOR_DECLS=10;
    public static final int OP=20;
    public static final int ACTOR=58;
    public static final int STATEMENTS=7;
    public static final int GT=43;
    public static final int REPEAT=64;
    public static final int GUARD=60;
    public static final int CALL=55;
    public static final int T__130=130;
    public static final int EscapeSequence=78;
    public static final int PARAMETERS=6;
    public static final int EXPR_BINARY=18;
    public static final int T__118=118;
    public static final int SCHEDULE=65;
    public static final int T__119=119;
    public static final int T__116=116;
    public static final int T__117=117;
    public static final int T__114=114;
    public static final int T__115=115;
    public static final int T__124=124;
    public static final int T__123=123;
    public static final int Exponent=72;
    public static final int T__122=122;
    public static final int T__121=121;
    public static final int FLOAT=73;
    public static final int T__120=120;
    public static final int TYPE_LIST=32;
    public static final int EXPR_FLOAT=27;
    public static final int LOGIC_AND=36;
    public static final int ID=71;
    public static final int HexDigit=75;
    public static final int BITAND=39;
    public static final int EXPR_LIST=21;
    public static final int EXPR=17;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int T__109=109;
    public static final int EXPR_STRING=29;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int BITXOR=38;
    public static final int T__105=105;
    public static final int T__106=106;
    public static final int T__111=111;
    public static final int NUM_ELTS=53;
    public static final int T__110=110;
    public static final int T__113=113;
    public static final int T__112=112;
    public static final int PLUS=66;
    public static final int EXPR_INT=28;
    public static final int INTEGER=77;
    public static final int TRANSITIONS=13;
    public static final int T__102=102;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int MINUS=67;
    public static final int EXPR_IF=22;
    public static final int Tokens=131;
    public static final int PROCEDURE=63;
    public static final int MULTI_LINE_COMMENT=82;
    public static final int TAG=16;
    public static final int VARIABLES=9;
    public static final int ASSIGN=54;
    public static final int DIV=69;
    public static final int TIMES=68;
    public static final int OctalEscape=80;
    public static final int LE=44;
    public static final int STRING=79;

    // delegates
    public RVCCal_ALBaseLexer gALBaseLexer;
    // delegators

    public RVCCalLexer() {;} 
    public RVCCalLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public RVCCalLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
        gALBaseLexer = new RVCCal_ALBaseLexer(input, state, this);
    }
    public String getGrammarFileName() { return "RVCCal__.g"; }

    // $ANTLR start "T__84"
    public final void mT__84() throws RecognitionException {
        try {
            int _type = T__84;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RVCCal__.g:7:7: ( ':' )
            // RVCCal__.g:7:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__84"

    // $ANTLR start "T__85"
    public final void mT__85() throws RecognitionException {
        try {
            int _type = T__85;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RVCCal__.g:8:7: ( '[' )
            // RVCCal__.g:8:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__85"

    // $ANTLR start "T__86"
    public final void mT__86() throws RecognitionException {
        try {
            int _type = T__86;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RVCCal__.g:9:7: ( ']' )
            // RVCCal__.g:9:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__86"

    // $ANTLR start "T__87"
    public final void mT__87() throws RecognitionException {
        try {
            int _type = T__87;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RVCCal__.g:10:7: ( ',' )
            // RVCCal__.g:10:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__87"

    // $ANTLR start "T__88"
    public final void mT__88() throws RecognitionException {
        try {
            int _type = T__88;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // RVCCal__.g:11:7: ( 'do' )
            // RVCCal__.g:11:9: 'do'
            {
            match("do"); 


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
            // RVCCal__.g:12:7: ( '(' )
            // RVCCal__.g:12:9: '('
            {
            match('('); 

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
            // RVCCal__.g:13:7: ( ')' )
            // RVCCal__.g:13:9: ')'
            {
            match(')'); 

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
            // RVCCal__.g:14:7: ( '==>' )
            // RVCCal__.g:14:9: '==>'
            {
            match("==>"); 


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
            // RVCCal__.g:15:7: ( 'end' )
            // RVCCal__.g:15:9: 'end'
            {
            match("end"); 


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
            // RVCCal__.g:16:7: ( '.' )
            // RVCCal__.g:16:9: '.'
            {
            match('.'); 

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
            // RVCCal__.g:17:7: ( 'var' )
            // RVCCal__.g:17:9: 'var'
            {
            match("var"); 


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
            // RVCCal__.g:18:7: ( '=' )
            // RVCCal__.g:18:9: '='
            {
            match('='); 

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
            // RVCCal__.g:19:7: ( ':=' )
            // RVCCal__.g:19:9: ':='
            {
            match(":="); 


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
            // RVCCal__.g:20:7: ( ';' )
            // RVCCal__.g:20:9: ';'
            {
            match(';'); 

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
            // RVCCal__.g:21:7: ( '-->' )
            // RVCCal__.g:21:9: '-->'
            {
            match("-->"); 


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
            // RVCCal__.g:22:7: ( 'begin' )
            // RVCCal__.g:22:9: 'begin'
            {
            match("begin"); 


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
            // RVCCal__.g:23:8: ( 'import' )
            // RVCCal__.g:23:10: 'import'
            {
            match("import"); 


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
            // RVCCal__.g:24:8: ( 'all' )
            // RVCCal__.g:24:10: 'all'
            {
            match("all"); 


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
            // RVCCal__.g:25:8: ( 'or' )
            // RVCCal__.g:25:10: 'or'
            {
            match("or"); 


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
            // RVCCal__.g:26:8: ( '||' )
            // RVCCal__.g:26:10: '||'
            {
            match("||"); 


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
            // RVCCal__.g:27:8: ( 'and' )
            // RVCCal__.g:27:10: 'and'
            {
            match("and"); 


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
            // RVCCal__.g:28:8: ( '&&' )
            // RVCCal__.g:28:10: '&&'
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
            // RVCCal__.g:29:8: ( '|' )
            // RVCCal__.g:29:10: '|'
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
            // RVCCal__.g:30:8: ( '&' )
            // RVCCal__.g:30:10: '&'
            {
            match('&'); 

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
            // RVCCal__.g:31:8: ( '!=' )
            // RVCCal__.g:31:10: '!='
            {
            match("!="); 


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
            // RVCCal__.g:32:8: ( '<' )
            // RVCCal__.g:32:10: '<'
            {
            match('<'); 

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
            // RVCCal__.g:33:8: ( '>' )
            // RVCCal__.g:33:10: '>'
            {
            match('>'); 

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
            // RVCCal__.g:34:8: ( '<=' )
            // RVCCal__.g:34:10: '<='
            {
            match("<="); 


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
            // RVCCal__.g:35:8: ( '>=' )
            // RVCCal__.g:35:10: '>='
            {
            match(">="); 


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
            // RVCCal__.g:36:8: ( '<<' )
            // RVCCal__.g:36:10: '<<'
            {
            match("<<"); 


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
            // RVCCal__.g:37:8: ( '>>' )
            // RVCCal__.g:37:10: '>>'
            {
            match(">>"); 


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
            // RVCCal__.g:38:8: ( 'div' )
            // RVCCal__.g:38:10: 'div'
            {
            match("div"); 


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
            // RVCCal__.g:39:8: ( 'mod' )
            // RVCCal__.g:39:10: 'mod'
            {
            match("mod"); 


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
            // RVCCal__.g:40:8: ( '^' )
            // RVCCal__.g:40:10: '^'
            {
            match('^'); 

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
            // RVCCal__.g:41:8: ( 'not' )
            // RVCCal__.g:41:10: 'not'
            {
            match("not"); 


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
            // RVCCal__.g:42:8: ( '#' )
            // RVCCal__.g:42:10: '#'
            {
            match('#'); 

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
            // RVCCal__.g:43:8: ( 'if' )
            // RVCCal__.g:43:10: 'if'
            {
            match("if"); 


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
            // RVCCal__.g:44:8: ( 'then' )
            // RVCCal__.g:44:10: 'then'
            {
            match("then"); 


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
            // RVCCal__.g:45:8: ( 'else' )
            // RVCCal__.g:45:10: 'else'
            {
            match("else"); 


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
            // RVCCal__.g:46:8: ( 'true' )
            // RVCCal__.g:46:10: 'true'
            {
            match("true"); 


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
            // RVCCal__.g:47:8: ( 'false' )
            // RVCCal__.g:47:10: 'false'
            {
            match("false"); 


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
            // RVCCal__.g:48:8: ( 'for' )
            // RVCCal__.g:48:10: 'for'
            {
            match("for"); 


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
            // RVCCal__.g:49:8: ( 'in' )
            // RVCCal__.g:49:10: 'in'
            {
            match("in"); 


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
            // RVCCal__.g:50:8: ( 'fsm' )
            // RVCCal__.g:50:10: 'fsm'
            {
            match("fsm"); 


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
            // RVCCal__.g:51:8: ( 'foreach' )
            // RVCCal__.g:51:10: 'foreach'
            {
            match("foreach"); 


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
            // RVCCal__.g:52:8: ( '..' )
            // RVCCal__.g:52:10: '..'
            {
            match(".."); 


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
            // RVCCal__.g:53:8: ( 'while' )
            // RVCCal__.g:53:10: 'while'
            {
            match("while"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__130"

    public void mTokens() throws RecognitionException {
        // RVCCal__.g:1:8: ( T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | T__112 | T__113 | T__114 | T__115 | T__116 | T__117 | T__118 | T__119 | T__120 | T__121 | T__122 | T__123 | T__124 | T__125 | T__126 | T__127 | T__128 | T__129 | T__130 | ALBaseLexer. Tokens )
        int alt1=48;
        alt1 = dfa1.predict(input);
        switch (alt1) {
            case 1 :
                // RVCCal__.g:1:10: T__84
                {
                mT__84(); 

                }
                break;
            case 2 :
                // RVCCal__.g:1:16: T__85
                {
                mT__85(); 

                }
                break;
            case 3 :
                // RVCCal__.g:1:22: T__86
                {
                mT__86(); 

                }
                break;
            case 4 :
                // RVCCal__.g:1:28: T__87
                {
                mT__87(); 

                }
                break;
            case 5 :
                // RVCCal__.g:1:34: T__88
                {
                mT__88(); 

                }
                break;
            case 6 :
                // RVCCal__.g:1:40: T__89
                {
                mT__89(); 

                }
                break;
            case 7 :
                // RVCCal__.g:1:46: T__90
                {
                mT__90(); 

                }
                break;
            case 8 :
                // RVCCal__.g:1:52: T__91
                {
                mT__91(); 

                }
                break;
            case 9 :
                // RVCCal__.g:1:58: T__92
                {
                mT__92(); 

                }
                break;
            case 10 :
                // RVCCal__.g:1:64: T__93
                {
                mT__93(); 

                }
                break;
            case 11 :
                // RVCCal__.g:1:70: T__94
                {
                mT__94(); 

                }
                break;
            case 12 :
                // RVCCal__.g:1:76: T__95
                {
                mT__95(); 

                }
                break;
            case 13 :
                // RVCCal__.g:1:82: T__96
                {
                mT__96(); 

                }
                break;
            case 14 :
                // RVCCal__.g:1:88: T__97
                {
                mT__97(); 

                }
                break;
            case 15 :
                // RVCCal__.g:1:94: T__98
                {
                mT__98(); 

                }
                break;
            case 16 :
                // RVCCal__.g:1:100: T__99
                {
                mT__99(); 

                }
                break;
            case 17 :
                // RVCCal__.g:1:106: T__100
                {
                mT__100(); 

                }
                break;
            case 18 :
                // RVCCal__.g:1:113: T__101
                {
                mT__101(); 

                }
                break;
            case 19 :
                // RVCCal__.g:1:120: T__102
                {
                mT__102(); 

                }
                break;
            case 20 :
                // RVCCal__.g:1:127: T__103
                {
                mT__103(); 

                }
                break;
            case 21 :
                // RVCCal__.g:1:134: T__104
                {
                mT__104(); 

                }
                break;
            case 22 :
                // RVCCal__.g:1:141: T__105
                {
                mT__105(); 

                }
                break;
            case 23 :
                // RVCCal__.g:1:148: T__106
                {
                mT__106(); 

                }
                break;
            case 24 :
                // RVCCal__.g:1:155: T__107
                {
                mT__107(); 

                }
                break;
            case 25 :
                // RVCCal__.g:1:162: T__108
                {
                mT__108(); 

                }
                break;
            case 26 :
                // RVCCal__.g:1:169: T__109
                {
                mT__109(); 

                }
                break;
            case 27 :
                // RVCCal__.g:1:176: T__110
                {
                mT__110(); 

                }
                break;
            case 28 :
                // RVCCal__.g:1:183: T__111
                {
                mT__111(); 

                }
                break;
            case 29 :
                // RVCCal__.g:1:190: T__112
                {
                mT__112(); 

                }
                break;
            case 30 :
                // RVCCal__.g:1:197: T__113
                {
                mT__113(); 

                }
                break;
            case 31 :
                // RVCCal__.g:1:204: T__114
                {
                mT__114(); 

                }
                break;
            case 32 :
                // RVCCal__.g:1:211: T__115
                {
                mT__115(); 

                }
                break;
            case 33 :
                // RVCCal__.g:1:218: T__116
                {
                mT__116(); 

                }
                break;
            case 34 :
                // RVCCal__.g:1:225: T__117
                {
                mT__117(); 

                }
                break;
            case 35 :
                // RVCCal__.g:1:232: T__118
                {
                mT__118(); 

                }
                break;
            case 36 :
                // RVCCal__.g:1:239: T__119
                {
                mT__119(); 

                }
                break;
            case 37 :
                // RVCCal__.g:1:246: T__120
                {
                mT__120(); 

                }
                break;
            case 38 :
                // RVCCal__.g:1:253: T__121
                {
                mT__121(); 

                }
                break;
            case 39 :
                // RVCCal__.g:1:260: T__122
                {
                mT__122(); 

                }
                break;
            case 40 :
                // RVCCal__.g:1:267: T__123
                {
                mT__123(); 

                }
                break;
            case 41 :
                // RVCCal__.g:1:274: T__124
                {
                mT__124(); 

                }
                break;
            case 42 :
                // RVCCal__.g:1:281: T__125
                {
                mT__125(); 

                }
                break;
            case 43 :
                // RVCCal__.g:1:288: T__126
                {
                mT__126(); 

                }
                break;
            case 44 :
                // RVCCal__.g:1:295: T__127
                {
                mT__127(); 

                }
                break;
            case 45 :
                // RVCCal__.g:1:302: T__128
                {
                mT__128(); 

                }
                break;
            case 46 :
                // RVCCal__.g:1:309: T__129
                {
                mT__129(); 

                }
                break;
            case 47 :
                // RVCCal__.g:1:316: T__130
                {
                mT__130(); 

                }
                break;
            case 48 :
                // RVCCal__.g:1:323: ALBaseLexer. Tokens
                {
                gALBaseLexer.mTokens(); 

                }
                break;

        }

    }


    protected DFA1 dfa1 = new DFA1(this);
    static final String DFA1_eotS =
        "\1\uffff\1\40\3\uffff\1\36\2\uffff\1\44\1\36\1\50\1\36\1\uffff"+
        "\5\36\1\63\1\65\1\uffff\1\70\1\73\1\36\1\uffff\1\36\1\uffff\3\36"+
        "\3\uffff\1\104\1\36\2\uffff\2\36\2\uffff\1\36\1\uffff\2\36\1\113"+
        "\1\114\2\36\1\117\12\uffff\10\36\1\uffff\1\130\1\131\1\36\1\133"+
        "\2\36\2\uffff\1\136\1\137\1\uffff\1\140\1\141\3\36\1\146\1\147\1"+
        "\36\2\uffff\1\151\1\uffff\2\36\4\uffff\1\154\1\155\2\36\2\uffff"+
        "\1\36\1\uffff\1\161\1\36\2\uffff\1\163\1\36\1\165\1\uffff\1\166"+
        "\1\uffff\1\36\2\uffff\1\170\1\uffff";
    static final String DFA1_eofS =
        "\171\uffff";
    static final String DFA1_minS =
        "\1\11\1\75\3\uffff\1\151\2\uffff\1\75\1\154\1\56\1\141\1\uffff"+
        "\1\55\1\145\1\146\1\154\1\162\1\174\1\46\1\uffff\1\74\1\75\1\157"+
        "\1\uffff\1\157\1\uffff\1\150\1\141\1\150\3\uffff\1\44\1\166\2\uffff"+
        "\1\144\1\163\2\uffff\1\162\1\uffff\1\147\1\160\2\44\1\154\1\144"+
        "\1\44\12\uffff\1\144\1\164\1\145\1\165\1\154\1\162\1\155\1\151\1"+
        "\uffff\2\44\1\145\1\44\1\151\1\157\2\uffff\2\44\1\uffff\2\44\1\156"+
        "\1\145\1\163\2\44\1\154\2\uffff\1\44\1\uffff\1\156\1\162\4\uffff"+
        "\2\44\1\145\1\141\2\uffff\1\145\1\uffff\1\44\1\164\2\uffff\1\44"+
        "\1\143\1\44\1\uffff\1\44\1\uffff\1\150\2\uffff\1\44\1\uffff";
    static final String DFA1_maxS =
        "\1\174\1\75\3\uffff\1\157\2\uffff\1\75\1\156\1\71\1\141\1\uffff"+
        "\1\55\1\145\2\156\1\162\1\174\1\46\1\uffff\1\75\1\76\1\157\1\uffff"+
        "\1\157\1\uffff\1\162\1\163\1\150\3\uffff\1\172\1\166\2\uffff\1\144"+
        "\1\163\2\uffff\1\162\1\uffff\1\147\1\160\2\172\1\154\1\144\1\172"+
        "\12\uffff\1\144\1\164\1\145\1\165\1\154\1\162\1\155\1\151\1\uffff"+
        "\2\172\1\145\1\172\1\151\1\157\2\uffff\2\172\1\uffff\2\172\1\156"+
        "\1\145\1\163\2\172\1\154\2\uffff\1\172\1\uffff\1\156\1\162\4\uffff"+
        "\2\172\1\145\1\141\2\uffff\1\145\1\uffff\1\172\1\164\2\uffff\1\172"+
        "\1\143\1\172\1\uffff\1\172\1\uffff\1\150\2\uffff\1\172\1\uffff";
    static final String DFA1_acceptS =
        "\2\uffff\1\2\1\3\1\4\1\uffff\1\6\1\7\4\uffff\1\16\7\uffff\1\31"+
        "\3\uffff\1\42\1\uffff\1\44\3\uffff\1\60\1\15\1\1\2\uffff\1\10\1"+
        "\14\2\uffff\1\56\1\12\1\uffff\1\17\7\uffff\1\24\1\27\1\26\1\30\1"+
        "\34\1\36\1\32\1\35\1\37\1\33\10\uffff\1\5\6\uffff\1\45\1\53\2\uffff"+
        "\1\23\10\uffff\1\40\1\11\1\uffff\1\13\2\uffff\1\22\1\25\1\41\1\43"+
        "\4\uffff\1\52\1\54\1\uffff\1\47\2\uffff\1\46\1\50\3\uffff\1\20\1"+
        "\uffff\1\51\1\uffff\1\57\1\21\1\uffff\1\55";
    static final String DFA1_specialS =
        "\171\uffff}>";
    static final String[] DFA1_transitionS = {
            "\2\36\1\uffff\2\36\22\uffff\1\36\1\24\1\36\1\32\1\36\1\uffff"+
            "\1\23\1\uffff\1\6\1\7\2\36\1\4\1\15\1\12\13\36\1\1\1\14\1\25"+
            "\1\10\1\26\2\uffff\32\36\1\2\1\uffff\1\3\1\30\1\36\1\uffff\1"+
            "\20\1\16\1\36\1\5\1\11\1\34\2\36\1\17\3\36\1\27\1\31\1\21\4"+
            "\36\1\33\1\36\1\13\1\35\3\36\1\uffff\1\22",
            "\1\37",
            "",
            "",
            "",
            "\1\42\5\uffff\1\41",
            "",
            "",
            "\1\43",
            "\1\46\1\uffff\1\45",
            "\1\47\1\uffff\12\36",
            "\1\51",
            "",
            "\1\52",
            "\1\53",
            "\1\55\6\uffff\1\54\1\56",
            "\1\57\1\uffff\1\60",
            "\1\61",
            "\1\62",
            "\1\64",
            "",
            "\1\67\1\66",
            "\1\71\1\72",
            "\1\74",
            "",
            "\1\75",
            "",
            "\1\76\11\uffff\1\77",
            "\1\100\15\uffff\1\101\3\uffff\1\102",
            "\1\103",
            "",
            "",
            "",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\105",
            "",
            "",
            "\1\106",
            "\1\107",
            "",
            "",
            "\1\110",
            "",
            "\1\111",
            "\1\112",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\115",
            "\1\116",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
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
            "\1\120",
            "\1\121",
            "\1\122",
            "\1\123",
            "\1\124",
            "\1\125",
            "\1\126",
            "\1\127",
            "",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\132",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\134",
            "\1\135",
            "",
            "",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\142",
            "\1\143",
            "\1\144",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\4"+
            "\36\1\145\25\36",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\150",
            "",
            "",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "",
            "\1\152",
            "\1\153",
            "",
            "",
            "",
            "",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\156",
            "\1\157",
            "",
            "",
            "\1\160",
            "",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\162",
            "",
            "",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\164",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "",
            "\1\167",
            "",
            "",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
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
            return "1:1: Tokens : ( T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | T__112 | T__113 | T__114 | T__115 | T__116 | T__117 | T__118 | T__119 | T__120 | T__121 | T__122 | T__123 | T__124 | T__125 | T__126 | T__127 | T__128 | T__129 | T__130 | ALBaseLexer. Tokens );";
        }
    }
 

}