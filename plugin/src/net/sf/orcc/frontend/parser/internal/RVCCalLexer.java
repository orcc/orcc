// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g 2009-10-09 18:24:37

package net.sf.orcc.frontend.parser.internal;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class RVCCalLexer extends Lexer {
    public static final int FUNCTION=10;
    public static final int EXPR_BOOL=39;
    public static final int OUTPUTS=6;
    public static final int EXPR_VAR=38;
    public static final int TRANSITION=14;
    public static final int LETTER=53;
    public static final int EXPR_CALL=36;
    public static final int INPUTS=5;
    public static final int EOF=-1;
    public static final int TYPE=44;
    public static final int EXPR_BITOR=25;
    public static final int T__93=93;
    public static final int TYPE_ATTRS=45;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__90=90;
    public static final int PARAMETER=7;
    public static final int STATE_VAR=12;
    public static final int GUARDS=18;
    public static final int VAR=43;
    public static final int EXPR_UN=33;
    public static final int T__99=99;
    public static final int T__98=98;
    public static final int T__97=97;
    public static final int T__96=96;
    public static final int T__95=95;
    public static final int ASSIGNABLE=46;
    public static final int EXPR_EQ=27;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int LINE_COMMENT=57;
    public static final int INEQUALITY=16;
    public static final int NON_ASSIGNABLE=47;
    public static final int WHITESPACE=59;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int EXPR_IDX=37;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int EXPR_ADD=30;
    public static final int EXPR_OR=23;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int PRIORITY=17;
    public static final int T__70=70;
    public static final int ACTOR_DECLS=9;
    public static final int ACTOR=4;
    public static final int STATEMENTS=20;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int T__74=74;
    public static final int EscapeSequence=55;
    public static final int T__73=73;
    public static final int EXPR_AND=24;
    public static final int T__79=79;
    public static final int T__78=78;
    public static final int T__77=77;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int EXPR_SHIFT=29;
    public static final int T__67=67;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int PARAMETERS=8;
    public static final int T__118=118;
    public static final int T__119=119;
    public static final int SCHEDULE=13;
    public static final int T__116=116;
    public static final int T__117=117;
    public static final int T__114=114;
    public static final int T__115=115;
    public static final int Exponent=54;
    public static final int FLOAT=50;
    public static final int EXPR_FLOAT=40;
    public static final int ID=49;
    public static final int T__61=61;
    public static final int EXPR_MUL=31;
    public static final int T__60=60;
    public static final int EXPR_LIST=34;
    public static final int EXPR=22;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int T__109=109;
    public static final int EXPR_STRING=42;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int T__105=105;
    public static final int T__106=106;
    public static final int T__111=111;
    public static final int T__110=110;
    public static final int T__113=113;
    public static final int T__112=112;
    public static final int EXPR_INT=41;
    public static final int INTEGER=51;
    public static final int TRANSITIONS=15;
    public static final int VARS=21;
    public static final int EXPR_EXP=32;
    public static final int T__102=102;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int EXPR_IF=35;
    public static final int EXPR_REL=28;
    public static final int PROCEDURE=11;
    public static final int MULTI_LINE_COMMENT=58;
    public static final int EXPR_BITAND=26;
    public static final int TAG=19;
    public static final int QID=48;
    public static final int OctalEscape=56;
    public static final int STRING=52;

    // delegates
    // delegators

    public RVCCalLexer() {;} 
    public RVCCalLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public RVCCalLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g"; }

    // $ANTLR start "T__60"
    public final void mT__60() throws RecognitionException {
        try {
            int _type = T__60;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:7:7: ( 'guard' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:7:9: 'guard'
            {
            match("guard"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__60"

    // $ANTLR start "T__61"
    public final void mT__61() throws RecognitionException {
        try {
            int _type = T__61;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:8:7: ( ':' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:8:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__61"

    // $ANTLR start "T__62"
    public final void mT__62() throws RecognitionException {
        try {
            int _type = T__62;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:9:7: ( '[' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:9:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__62"

    // $ANTLR start "T__63"
    public final void mT__63() throws RecognitionException {
        try {
            int _type = T__63;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:10:7: ( ']' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:10:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__63"

    // $ANTLR start "T__64"
    public final void mT__64() throws RecognitionException {
        try {
            int _type = T__64;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:11:7: ( ',' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:11:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__64"

    // $ANTLR start "T__65"
    public final void mT__65() throws RecognitionException {
        try {
            int _type = T__65;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:12:7: ( 'repeat' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:12:9: 'repeat'
            {
            match("repeat"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__65"

    // $ANTLR start "T__66"
    public final void mT__66() throws RecognitionException {
        try {
            int _type = T__66;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:13:7: ( 'do' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:13:9: 'do'
            {
            match("do"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__66"

    // $ANTLR start "T__67"
    public final void mT__67() throws RecognitionException {
        try {
            int _type = T__67;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:14:7: ( 'actor' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:14:9: 'actor'
            {
            match("actor"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__67"

    // $ANTLR start "T__68"
    public final void mT__68() throws RecognitionException {
        try {
            int _type = T__68;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:15:7: ( '(' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:15:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__68"

    // $ANTLR start "T__69"
    public final void mT__69() throws RecognitionException {
        try {
            int _type = T__69;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:16:7: ( ')' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:16:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__69"

    // $ANTLR start "T__70"
    public final void mT__70() throws RecognitionException {
        try {
            int _type = T__70;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:17:7: ( '==>' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:17:9: '==>'
            {
            match("==>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__70"

    // $ANTLR start "T__71"
    public final void mT__71() throws RecognitionException {
        try {
            int _type = T__71;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:18:7: ( 'end' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:18:9: 'end'
            {
            match("end"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__71"

    // $ANTLR start "T__72"
    public final void mT__72() throws RecognitionException {
        try {
            int _type = T__72;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:19:7: ( '.' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:19:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__72"

    // $ANTLR start "T__73"
    public final void mT__73() throws RecognitionException {
        try {
            int _type = T__73;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:20:7: ( 'action' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:20:9: 'action'
            {
            match("action"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__73"

    // $ANTLR start "T__74"
    public final void mT__74() throws RecognitionException {
        try {
            int _type = T__74;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:21:7: ( 'var' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:21:9: 'var'
            {
            match("var"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__74"

    // $ANTLR start "T__75"
    public final void mT__75() throws RecognitionException {
        try {
            int _type = T__75;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:22:7: ( 'initialize' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:22:9: 'initialize'
            {
            match("initialize"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__75"

    // $ANTLR start "T__76"
    public final void mT__76() throws RecognitionException {
        try {
            int _type = T__76;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:23:7: ( '=' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:23:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__76"

    // $ANTLR start "T__77"
    public final void mT__77() throws RecognitionException {
        try {
            int _type = T__77;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:24:7: ( ':=' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:24:9: ':='
            {
            match(":="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__77"

    // $ANTLR start "T__78"
    public final void mT__78() throws RecognitionException {
        try {
            int _type = T__78;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:25:7: ( ';' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:25:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__78"

    // $ANTLR start "T__79"
    public final void mT__79() throws RecognitionException {
        try {
            int _type = T__79;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:26:7: ( 'function' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:26:9: 'function'
            {
            match("function"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__79"

    // $ANTLR start "T__80"
    public final void mT__80() throws RecognitionException {
        try {
            int _type = T__80;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:27:7: ( '-->' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:27:9: '-->'
            {
            match("-->"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__80"

    // $ANTLR start "T__81"
    public final void mT__81() throws RecognitionException {
        try {
            int _type = T__81;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:28:7: ( 'procedure' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:28:9: 'procedure'
            {
            match("procedure"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__81"

    // $ANTLR start "T__82"
    public final void mT__82() throws RecognitionException {
        try {
            int _type = T__82;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:29:7: ( 'begin' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:29:9: 'begin'
            {
            match("begin"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__82"

    // $ANTLR start "T__83"
    public final void mT__83() throws RecognitionException {
        try {
            int _type = T__83;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:30:7: ( 'import' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:30:9: 'import'
            {
            match("import"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__83"

    // $ANTLR start "T__84"
    public final void mT__84() throws RecognitionException {
        try {
            int _type = T__84;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:31:7: ( 'all' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:31:9: 'all'
            {
            match("all"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:32:7: ( 'or' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:32:9: 'or'
            {
            match("or"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:33:7: ( '||' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:33:9: '||'
            {
            match("||"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:34:7: ( 'and' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:34:9: 'and'
            {
            match("and"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:35:7: ( '&&' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:35:9: '&&'
            {
            match("&&"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:36:7: ( '|' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:36:9: '|'
            {
            match('|'); 

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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:37:7: ( '&' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:37:9: '&'
            {
            match('&'); 

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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:38:7: ( '!=' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:38:9: '!='
            {
            match("!="); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:39:7: ( '<' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:39:9: '<'
            {
            match('<'); 

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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:40:7: ( '>' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:40:9: '>'
            {
            match('>'); 

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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:41:7: ( '<=' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:41:9: '<='
            {
            match("<="); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:42:7: ( '>=' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:42:9: '>='
            {
            match(">="); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:43:7: ( '<<' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:43:9: '<<'
            {
            match("<<"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:44:7: ( '>>' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:44:9: '>>'
            {
            match(">>"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:45:7: ( '+' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:45:9: '+'
            {
            match('+'); 

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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:46:7: ( '-' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:46:9: '-'
            {
            match('-'); 

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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:47:8: ( 'div' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:47:10: 'div'
            {
            match("div"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:48:8: ( 'mod' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:48:10: 'mod'
            {
            match("mod"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:49:8: ( '*' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:49:10: '*'
            {
            match('*'); 

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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:50:8: ( '/' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:50:10: '/'
            {
            match('/'); 

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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:51:8: ( '^' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:51:10: '^'
            {
            match('^'); 

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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:52:8: ( 'not' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:52:10: 'not'
            {
            match("not"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:53:8: ( '#' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:53:10: '#'
            {
            match('#'); 

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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:54:8: ( 'if' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:54:10: 'if'
            {
            match("if"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:55:8: ( 'then' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:55:10: 'then'
            {
            match("then"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:56:8: ( 'else' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:56:10: 'else'
            {
            match("else"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:57:8: ( 'true' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:57:10: 'true'
            {
            match("true"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:58:8: ( 'false' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:58:10: 'false'
            {
            match("false"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:59:8: ( 'for' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:59:10: 'for'
            {
            match("for"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:60:8: ( 'in' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:60:10: 'in'
            {
            match("in"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:61:8: ( 'priority' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:61:10: 'priority'
            {
            match("priority"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:62:8: ( 'schedule' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:62:10: 'schedule'
            {
            match("schedule"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:63:8: ( 'fsm' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:63:10: 'fsm'
            {
            match("fsm"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:64:8: ( 'foreach' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:64:10: 'foreach'
            {
            match("foreach"); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:65:8: ( '..' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:65:10: '..'
            {
            match(".."); 


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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:66:8: ( 'while' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:66:10: 'while'
            {
            match("while"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__119"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:344:3: ( LETTER ( LETTER | '0' .. '9' )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:344:5: LETTER ( LETTER | '0' .. '9' )*
            {
            mLETTER(); 
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:344:12: ( LETTER | '0' .. '9' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='$'||(LA1_0>='0' && LA1_0<='9')||(LA1_0>='A' && LA1_0<='Z')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:
            	    {
            	    if ( input.LA(1)=='$'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:347:7: ( '$' | 'A' .. 'Z' | 'a' .. 'z' | '_' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:
            {
            if ( input.LA(1)=='$'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "LETTER"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:349:6: ( ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )? | '.' ( '0' .. '9' )+ ( Exponent )? | ( '0' .. '9' )+ Exponent ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:349:8: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )? | '.' ( '0' .. '9' )+ ( Exponent )? | ( '0' .. '9' )+ Exponent )
            {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:349:8: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )? | '.' ( '0' .. '9' )+ ( Exponent )? | ( '0' .. '9' )+ Exponent )
            int alt8=3;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:349:9: ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )?
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:349:9: ( '0' .. '9' )+
                    int cnt2=0;
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:349:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt2 >= 1 ) break loop2;
                                EarlyExitException eee =
                                    new EarlyExitException(2, input);
                                throw eee;
                        }
                        cnt2++;
                    } while (true);

                    match('.'); 
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:349:25: ( '0' .. '9' )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:349:26: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:349:37: ( Exponent )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0=='E'||LA4_0=='e') ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:349:37: Exponent
                            {
                            mExponent(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:350:4: '.' ( '0' .. '9' )+ ( Exponent )?
                    {
                    match('.'); 
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:350:8: ( '0' .. '9' )+
                    int cnt5=0;
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:350:9: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt5 >= 1 ) break loop5;
                                EarlyExitException eee =
                                    new EarlyExitException(5, input);
                                throw eee;
                        }
                        cnt5++;
                    } while (true);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:350:20: ( Exponent )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0=='E'||LA6_0=='e') ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:350:20: Exponent
                            {
                            mExponent(); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:351:4: ( '0' .. '9' )+ Exponent
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:351:4: ( '0' .. '9' )+
                    int cnt7=0;
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( ((LA7_0>='0' && LA7_0<='9')) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:351:5: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt7 >= 1 ) break loop7;
                                EarlyExitException eee =
                                    new EarlyExitException(7, input);
                                throw eee;
                        }
                        cnt7++;
                    } while (true);

                    mExponent(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FLOAT"

    // $ANTLR start "Exponent"
    public final void mExponent() throws RecognitionException {
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:354:10: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:354:12: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:354:22: ( '+' | '-' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='+'||LA9_0=='-') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:354:33: ( '0' .. '9' )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='0' && LA10_0<='9')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:354:34: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "Exponent"

    // $ANTLR start "INTEGER"
    public final void mINTEGER() throws RecognitionException {
        try {
            int _type = INTEGER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:356:8: ( ( '0' | '1' .. '9' ( '0' .. '9' )* ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:356:10: ( '0' | '1' .. '9' ( '0' .. '9' )* )
            {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:356:10: ( '0' | '1' .. '9' ( '0' .. '9' )* )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0=='0') ) {
                alt12=1;
            }
            else if ( ((LA12_0>='1' && LA12_0<='9')) ) {
                alt12=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:356:11: '0'
                    {
                    match('0'); 

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:356:17: '1' .. '9' ( '0' .. '9' )*
                    {
                    matchRange('1','9'); 
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:356:26: ( '0' .. '9' )*
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:356:26: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop11;
                        }
                    } while (true);


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INTEGER"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:358:7: ( '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:358:9: '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"'
            {
            match('\"'); 
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:358:13: ( EscapeSequence | ~ ( '\\\\' | '\"' ) )*
            loop13:
            do {
                int alt13=3;
                int LA13_0 = input.LA(1);

                if ( (LA13_0=='\\') ) {
                    alt13=1;
                }
                else if ( ((LA13_0>='\u0000' && LA13_0<='!')||(LA13_0>='#' && LA13_0<='[')||(LA13_0>=']' && LA13_0<='\uFFFF')) ) {
                    alt13=2;
                }


                switch (alt13) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:358:15: EscapeSequence
            	    {
            	    mEscapeSequence(); 

            	    }
            	    break;
            	case 2 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:358:32: ~ ( '\\\\' | '\"' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "EscapeSequence"
    public final void mEscapeSequence() throws RecognitionException {
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:362:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | OctalEscape )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0=='\\') ) {
                int LA14_1 = input.LA(2);

                if ( (LA14_1=='\"'||LA14_1=='\''||LA14_1=='\\'||LA14_1=='b'||LA14_1=='f'||LA14_1=='n'||LA14_1=='r'||LA14_1=='t') ) {
                    alt14=1;
                }
                else if ( ((LA14_1>='0' && LA14_1<='7')) ) {
                    alt14=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 14, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:362:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
                    {
                    match('\\'); 
                    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:363:9: OctalEscape
                    {
                    mOctalEscape(); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "EscapeSequence"

    // $ANTLR start "OctalEscape"
    public final void mOctalEscape() throws RecognitionException {
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:368:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt15=3;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='\\') ) {
                int LA15_1 = input.LA(2);

                if ( ((LA15_1>='0' && LA15_1<='3')) ) {
                    int LA15_2 = input.LA(3);

                    if ( ((LA15_2>='0' && LA15_2<='7')) ) {
                        int LA15_5 = input.LA(4);

                        if ( ((LA15_5>='0' && LA15_5<='7')) ) {
                            alt15=1;
                        }
                        else {
                            alt15=2;}
                    }
                    else {
                        alt15=3;}
                }
                else if ( ((LA15_1>='4' && LA15_1<='7')) ) {
                    int LA15_3 = input.LA(3);

                    if ( ((LA15_3>='0' && LA15_3<='7')) ) {
                        alt15=2;
                    }
                    else {
                        alt15=3;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:368:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:368:14: ( '0' .. '3' )
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:368:15: '0' .. '3'
                    {
                    matchRange('0','3'); 

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:368:25: ( '0' .. '7' )
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:368:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:368:36: ( '0' .. '7' )
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:368:37: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:369:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:369:14: ( '0' .. '7' )
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:369:15: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:369:25: ( '0' .. '7' )
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:369:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:370:9: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:370:14: ( '0' .. '7' )
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:370:15: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "OctalEscape"

    // $ANTLR start "LINE_COMMENT"
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            int _type = LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:373:13: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:373:15: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:373:20: (~ ( '\\n' | '\\r' ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>='\u0000' && LA16_0<='\t')||(LA16_0>='\u000B' && LA16_0<='\f')||(LA16_0>='\u000E' && LA16_0<='\uFFFF')) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:373:20: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:373:34: ( '\\r' )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0=='\r') ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:373:34: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 
            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LINE_COMMENT"

    // $ANTLR start "MULTI_LINE_COMMENT"
    public final void mMULTI_LINE_COMMENT() throws RecognitionException {
        try {
            int _type = MULTI_LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:374:19: ( '/*' ( . )* '*/' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:374:21: '/*' ( . )* '*/'
            {
            match("/*"); 

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:374:26: ( . )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0=='*') ) {
                    int LA18_1 = input.LA(2);

                    if ( (LA18_1=='/') ) {
                        alt18=2;
                    }
                    else if ( ((LA18_1>='\u0000' && LA18_1<='.')||(LA18_1>='0' && LA18_1<='\uFFFF')) ) {
                        alt18=1;
                    }


                }
                else if ( ((LA18_0>='\u0000' && LA18_0<=')')||(LA18_0>='+' && LA18_0<='\uFFFF')) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:374:26: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

            match("*/"); 

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MULTI_LINE_COMMENT"

    // $ANTLR start "WHITESPACE"
    public final void mWHITESPACE() throws RecognitionException {
        try {
            int _type = WHITESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:375:11: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:375:13: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WHITESPACE"

    public void mTokens() throws RecognitionException {
        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:8: ( T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | T__112 | T__113 | T__114 | T__115 | T__116 | T__117 | T__118 | T__119 | ID | FLOAT | INTEGER | STRING | LINE_COMMENT | MULTI_LINE_COMMENT | WHITESPACE )
        int alt19=67;
        alt19 = dfa19.predict(input);
        switch (alt19) {
            case 1 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:10: T__60
                {
                mT__60(); 

                }
                break;
            case 2 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:16: T__61
                {
                mT__61(); 

                }
                break;
            case 3 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:22: T__62
                {
                mT__62(); 

                }
                break;
            case 4 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:28: T__63
                {
                mT__63(); 

                }
                break;
            case 5 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:34: T__64
                {
                mT__64(); 

                }
                break;
            case 6 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:40: T__65
                {
                mT__65(); 

                }
                break;
            case 7 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:46: T__66
                {
                mT__66(); 

                }
                break;
            case 8 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:52: T__67
                {
                mT__67(); 

                }
                break;
            case 9 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:58: T__68
                {
                mT__68(); 

                }
                break;
            case 10 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:64: T__69
                {
                mT__69(); 

                }
                break;
            case 11 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:70: T__70
                {
                mT__70(); 

                }
                break;
            case 12 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:76: T__71
                {
                mT__71(); 

                }
                break;
            case 13 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:82: T__72
                {
                mT__72(); 

                }
                break;
            case 14 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:88: T__73
                {
                mT__73(); 

                }
                break;
            case 15 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:94: T__74
                {
                mT__74(); 

                }
                break;
            case 16 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:100: T__75
                {
                mT__75(); 

                }
                break;
            case 17 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:106: T__76
                {
                mT__76(); 

                }
                break;
            case 18 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:112: T__77
                {
                mT__77(); 

                }
                break;
            case 19 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:118: T__78
                {
                mT__78(); 

                }
                break;
            case 20 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:124: T__79
                {
                mT__79(); 

                }
                break;
            case 21 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:130: T__80
                {
                mT__80(); 

                }
                break;
            case 22 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:136: T__81
                {
                mT__81(); 

                }
                break;
            case 23 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:142: T__82
                {
                mT__82(); 

                }
                break;
            case 24 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:148: T__83
                {
                mT__83(); 

                }
                break;
            case 25 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:154: T__84
                {
                mT__84(); 

                }
                break;
            case 26 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:160: T__85
                {
                mT__85(); 

                }
                break;
            case 27 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:166: T__86
                {
                mT__86(); 

                }
                break;
            case 28 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:172: T__87
                {
                mT__87(); 

                }
                break;
            case 29 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:178: T__88
                {
                mT__88(); 

                }
                break;
            case 30 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:184: T__89
                {
                mT__89(); 

                }
                break;
            case 31 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:190: T__90
                {
                mT__90(); 

                }
                break;
            case 32 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:196: T__91
                {
                mT__91(); 

                }
                break;
            case 33 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:202: T__92
                {
                mT__92(); 

                }
                break;
            case 34 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:208: T__93
                {
                mT__93(); 

                }
                break;
            case 35 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:214: T__94
                {
                mT__94(); 

                }
                break;
            case 36 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:220: T__95
                {
                mT__95(); 

                }
                break;
            case 37 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:226: T__96
                {
                mT__96(); 

                }
                break;
            case 38 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:232: T__97
                {
                mT__97(); 

                }
                break;
            case 39 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:238: T__98
                {
                mT__98(); 

                }
                break;
            case 40 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:244: T__99
                {
                mT__99(); 

                }
                break;
            case 41 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:250: T__100
                {
                mT__100(); 

                }
                break;
            case 42 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:257: T__101
                {
                mT__101(); 

                }
                break;
            case 43 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:264: T__102
                {
                mT__102(); 

                }
                break;
            case 44 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:271: T__103
                {
                mT__103(); 

                }
                break;
            case 45 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:278: T__104
                {
                mT__104(); 

                }
                break;
            case 46 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:285: T__105
                {
                mT__105(); 

                }
                break;
            case 47 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:292: T__106
                {
                mT__106(); 

                }
                break;
            case 48 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:299: T__107
                {
                mT__107(); 

                }
                break;
            case 49 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:306: T__108
                {
                mT__108(); 

                }
                break;
            case 50 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:313: T__109
                {
                mT__109(); 

                }
                break;
            case 51 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:320: T__110
                {
                mT__110(); 

                }
                break;
            case 52 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:327: T__111
                {
                mT__111(); 

                }
                break;
            case 53 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:334: T__112
                {
                mT__112(); 

                }
                break;
            case 54 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:341: T__113
                {
                mT__113(); 

                }
                break;
            case 55 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:348: T__114
                {
                mT__114(); 

                }
                break;
            case 56 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:355: T__115
                {
                mT__115(); 

                }
                break;
            case 57 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:362: T__116
                {
                mT__116(); 

                }
                break;
            case 58 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:369: T__117
                {
                mT__117(); 

                }
                break;
            case 59 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:376: T__118
                {
                mT__118(); 

                }
                break;
            case 60 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:383: T__119
                {
                mT__119(); 

                }
                break;
            case 61 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:390: ID
                {
                mID(); 

                }
                break;
            case 62 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:393: FLOAT
                {
                mFLOAT(); 

                }
                break;
            case 63 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:399: INTEGER
                {
                mINTEGER(); 

                }
                break;
            case 64 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:407: STRING
                {
                mSTRING(); 

                }
                break;
            case 65 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:414: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;
            case 66 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:427: MULTI_LINE_COMMENT
                {
                mMULTI_LINE_COMMENT(); 

                }
                break;
            case 67 :
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:1:446: WHITESPACE
                {
                mWHITESPACE(); 

                }
                break;

        }

    }


    protected DFA8 dfa8 = new DFA8(this);
    protected DFA19 dfa19 = new DFA19(this);
    static final String DFA8_eotS =
        "\5\uffff";
    static final String DFA8_eofS =
        "\5\uffff";
    static final String DFA8_minS =
        "\2\56\3\uffff";
    static final String DFA8_maxS =
        "\1\71\1\145\3\uffff";
    static final String DFA8_acceptS =
        "\2\uffff\1\2\1\3\1\1";
    static final String DFA8_specialS =
        "\5\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\4\1\uffff\12\1\13\uffff\1\3\37\uffff\1\3",
            "",
            "",
            ""
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "349:8: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )? | '.' ( '0' .. '9' )+ ( Exponent )? | ( '0' .. '9' )+ Exponent )";
        }
    }
    static final String DFA19_eotS =
        "\1\uffff\1\45\1\54\3\uffff\3\45\2\uffff\1\64\1\45\1\70\2\45\1\uffff"+
        "\1\45\1\103\3\45\1\110\1\112\1\uffff\1\115\1\120\1\uffff\1\45\1"+
        "\uffff\1\124\1\uffff\1\45\1\uffff\3\45\1\uffff\2\132\2\uffff\1\45"+
        "\2\uffff\1\45\1\136\4\45\2\uffff\2\45\3\uffff\1\45\1\147\1\45\1"+
        "\151\4\45\2\uffff\2\45\1\161\12\uffff\1\45\3\uffff\5\45\1\uffff"+
        "\1\132\2\45\1\uffff\1\172\1\45\1\175\1\176\1\177\1\45\1\u0081\1"+
        "\45\1\uffff\1\45\1\uffff\2\45\1\u0087\1\u0088\3\45\1\uffff\1\u008c"+
        "\1\u008d\6\45\1\uffff\2\45\3\uffff\1\u0096\1\uffff\5\45\2\uffff"+
        "\3\45\2\uffff\1\u009f\1\u00a0\2\45\1\u00a3\1\45\1\u00a5\1\45\1\uffff"+
        "\3\45\1\u00aa\3\45\1\u00ae\2\uffff\1\45\1\u00b0\1\uffff\1\u00b1"+
        "\1\uffff\1\u00b2\1\45\1\u00b4\1\45\1\uffff\3\45\1\uffff\1\45\3\uffff"+
        "\1\45\1\uffff\1\45\1\u00bc\4\45\1\u00c1\1\uffff\1\45\1\u00c3\1\u00c4"+
        "\1\45\1\uffff\1\u00c6\2\uffff\1\u00c7\2\uffff";
    static final String DFA19_eofS =
        "\u00c8\uffff";
    static final String DFA19_minS =
        "\1\11\1\165\1\75\3\uffff\1\145\1\151\1\143\2\uffff\1\75\1\154\1"+
        "\56\1\141\1\146\1\uffff\1\141\1\55\1\162\1\145\1\162\1\174\1\46"+
        "\1\uffff\1\74\1\75\1\uffff\1\157\1\uffff\1\52\1\uffff\1\157\1\uffff"+
        "\1\150\1\143\1\150\1\uffff\2\56\2\uffff\1\141\2\uffff\1\160\1\44"+
        "\1\166\1\164\1\154\1\144\2\uffff\1\144\1\163\3\uffff\1\162\1\44"+
        "\1\160\1\44\1\156\1\154\1\162\1\155\2\uffff\1\151\1\147\1\44\12"+
        "\uffff\1\144\3\uffff\1\164\1\145\1\165\1\150\1\151\1\uffff\1\56"+
        "\1\162\1\145\1\uffff\1\44\1\151\3\44\1\145\1\44\1\164\1\uffff\1"+
        "\157\1\uffff\1\143\1\163\2\44\1\143\1\157\1\151\1\uffff\2\44\1\156"+
        "\2\145\1\154\1\144\1\141\1\uffff\1\162\1\157\3\uffff\1\44\1\uffff"+
        "\1\151\1\162\1\164\1\145\1\141\2\uffff\1\145\1\162\1\156\2\uffff"+
        "\2\44\1\144\1\145\1\44\1\164\1\44\1\156\1\uffff\1\141\1\164\1\151"+
        "\1\44\1\143\1\144\1\151\1\44\2\uffff\1\165\1\44\1\uffff\1\44\1\uffff"+
        "\1\44\1\154\1\44\1\157\1\uffff\1\150\1\165\1\164\1\uffff\1\154\3"+
        "\uffff\1\151\1\uffff\1\156\1\44\1\162\1\171\1\145\1\172\1\44\1\uffff"+
        "\1\145\2\44\1\145\1\uffff\1\44\2\uffff\1\44\2\uffff";
    static final String DFA19_maxS =
        "\1\174\1\165\1\75\3\uffff\1\145\1\157\1\156\2\uffff\1\75\1\156"+
        "\1\71\1\141\1\156\1\uffff\1\165\1\55\1\162\1\145\1\162\1\174\1\46"+
        "\1\uffff\1\75\1\76\1\uffff\1\157\1\uffff\1\57\1\uffff\1\157\1\uffff"+
        "\1\162\1\143\1\150\1\uffff\2\145\2\uffff\1\141\2\uffff\1\160\1\172"+
        "\1\166\1\164\1\154\1\144\2\uffff\1\144\1\163\3\uffff\1\162\1\172"+
        "\1\160\1\172\1\156\1\154\1\162\1\155\2\uffff\1\157\1\147\1\172\12"+
        "\uffff\1\144\3\uffff\1\164\1\145\1\165\1\150\1\151\1\uffff\1\145"+
        "\1\162\1\145\1\uffff\1\172\1\157\3\172\1\145\1\172\1\164\1\uffff"+
        "\1\157\1\uffff\1\143\1\163\2\172\1\143\1\157\1\151\1\uffff\2\172"+
        "\1\156\2\145\1\154\1\144\1\141\1\uffff\1\162\1\157\3\uffff\1\172"+
        "\1\uffff\1\151\1\162\1\164\1\145\1\141\2\uffff\1\145\1\162\1\156"+
        "\2\uffff\2\172\1\144\1\145\1\172\1\164\1\172\1\156\1\uffff\1\141"+
        "\1\164\1\151\1\172\1\143\1\144\1\151\1\172\2\uffff\1\165\1\172\1"+
        "\uffff\1\172\1\uffff\1\172\1\154\1\172\1\157\1\uffff\1\150\1\165"+
        "\1\164\1\uffff\1\154\3\uffff\1\151\1\uffff\1\156\1\172\1\162\1\171"+
        "\1\145\2\172\1\uffff\1\145\2\172\1\145\1\uffff\1\172\2\uffff\1\172"+
        "\2\uffff";
    static final String DFA19_acceptS =
        "\3\uffff\1\3\1\4\1\5\3\uffff\1\11\1\12\5\uffff\1\23\7\uffff\1\40"+
        "\2\uffff\1\47\1\uffff\1\53\1\uffff\1\55\1\uffff\1\57\3\uffff\1\75"+
        "\2\uffff\1\100\1\103\1\uffff\1\22\1\2\6\uffff\1\13\1\21\2\uffff"+
        "\1\73\1\15\1\76\10\uffff\1\25\1\50\3\uffff\1\33\1\36\1\35\1\37\1"+
        "\43\1\45\1\41\1\44\1\46\1\42\1\uffff\1\101\1\102\1\54\5\uffff\1"+
        "\77\3\uffff\1\7\10\uffff\1\66\1\uffff\1\60\7\uffff\1\32\10\uffff"+
        "\1\51\2\uffff\1\31\1\34\1\14\1\uffff\1\17\5\uffff\1\65\1\71\3\uffff"+
        "\1\52\1\56\10\uffff\1\62\10\uffff\1\61\1\63\2\uffff\1\1\1\uffff"+
        "\1\10\4\uffff\1\64\3\uffff\1\27\1\uffff\1\74\1\6\1\16\1\uffff\1"+
        "\30\7\uffff\1\72\4\uffff\1\24\1\uffff\1\67\1\70\1\uffff\1\26\1\20";
    static final String DFA19_specialS =
        "\u00c8\uffff}>";
    static final String[] DFA19_transitionS = {
            "\2\51\1\uffff\2\51\22\uffff\1\51\1\30\1\50\1\41\1\45\1\uffff"+
            "\1\27\1\uffff\1\11\1\12\1\35\1\33\1\5\1\22\1\15\1\36\1\46\11"+
            "\47\1\2\1\20\1\31\1\13\1\32\2\uffff\32\45\1\3\1\uffff\1\4\1"+
            "\37\1\45\1\uffff\1\10\1\24\1\45\1\7\1\14\1\21\1\1\1\45\1\17"+
            "\3\45\1\34\1\40\1\25\1\23\1\45\1\6\1\43\1\42\1\45\1\16\1\44"+
            "\3\45\1\uffff\1\26",
            "\1\52",
            "\1\53",
            "",
            "",
            "",
            "\1\55",
            "\1\57\5\uffff\1\56",
            "\1\60\10\uffff\1\61\1\uffff\1\62",
            "",
            "",
            "\1\63",
            "\1\66\1\uffff\1\65",
            "\1\67\1\uffff\12\71",
            "\1\72",
            "\1\75\6\uffff\1\74\1\73",
            "",
            "\1\77\15\uffff\1\100\3\uffff\1\101\1\uffff\1\76",
            "\1\102",
            "\1\104",
            "\1\105",
            "\1\106",
            "\1\107",
            "\1\111",
            "",
            "\1\114\1\113",
            "\1\116\1\117",
            "",
            "\1\121",
            "",
            "\1\123\4\uffff\1\122",
            "",
            "\1\125",
            "",
            "\1\126\11\uffff\1\127",
            "\1\130",
            "\1\131",
            "",
            "\1\71\1\uffff\12\71\13\uffff\1\71\37\uffff\1\71",
            "\1\71\1\uffff\12\133\13\uffff\1\71\37\uffff\1\71",
            "",
            "",
            "\1\134",
            "",
            "",
            "\1\135",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\137",
            "\1\140",
            "\1\141",
            "\1\142",
            "",
            "",
            "\1\143",
            "\1\144",
            "",
            "",
            "",
            "\1\145",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\10"+
            "\45\1\146\21\45",
            "\1\150",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\152",
            "\1\153",
            "\1\154",
            "\1\155",
            "",
            "",
            "\1\157\5\uffff\1\156",
            "\1\160",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
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
            "\1\162",
            "",
            "",
            "",
            "\1\163",
            "\1\164",
            "\1\165",
            "\1\166",
            "\1\167",
            "",
            "\1\71\1\uffff\12\133\13\uffff\1\71\37\uffff\1\71",
            "\1\170",
            "\1\171",
            "",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\174\5\uffff\1\173",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\u0080",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\u0082",
            "",
            "\1\u0083",
            "",
            "\1\u0084",
            "\1\u0085",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\4"+
            "\45\1\u0086\25\45",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\u0089",
            "\1\u008a",
            "\1\u008b",
            "",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\u008e",
            "\1\u008f",
            "\1\u0090",
            "\1\u0091",
            "\1\u0092",
            "\1\u0093",
            "",
            "\1\u0094",
            "\1\u0095",
            "",
            "",
            "",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "",
            "\1\u0097",
            "\1\u0098",
            "\1\u0099",
            "\1\u009a",
            "\1\u009b",
            "",
            "",
            "\1\u009c",
            "\1\u009d",
            "\1\u009e",
            "",
            "",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\u00a1",
            "\1\u00a2",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\u00a4",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\u00a6",
            "",
            "\1\u00a7",
            "\1\u00a8",
            "\1\u00a9",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\u00ab",
            "\1\u00ac",
            "\1\u00ad",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "",
            "",
            "\1\u00af",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\u00b3",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\u00b5",
            "",
            "\1\u00b6",
            "\1\u00b7",
            "\1\u00b8",
            "",
            "\1\u00b9",
            "",
            "",
            "",
            "\1\u00ba",
            "",
            "\1\u00bb",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\u00bd",
            "\1\u00be",
            "\1\u00bf",
            "\1\u00c0",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "",
            "\1\u00c2",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "\1\u00c5",
            "",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "",
            "",
            "\1\45\13\uffff\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32"+
            "\45",
            "",
            ""
    };

    static final short[] DFA19_eot = DFA.unpackEncodedString(DFA19_eotS);
    static final short[] DFA19_eof = DFA.unpackEncodedString(DFA19_eofS);
    static final char[] DFA19_min = DFA.unpackEncodedStringToUnsignedChars(DFA19_minS);
    static final char[] DFA19_max = DFA.unpackEncodedStringToUnsignedChars(DFA19_maxS);
    static final short[] DFA19_accept = DFA.unpackEncodedString(DFA19_acceptS);
    static final short[] DFA19_special = DFA.unpackEncodedString(DFA19_specialS);
    static final short[][] DFA19_transition;

    static {
        int numStates = DFA19_transitionS.length;
        DFA19_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA19_transition[i] = DFA.unpackEncodedString(DFA19_transitionS[i]);
        }
    }

    class DFA19 extends DFA {

        public DFA19(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 19;
            this.eot = DFA19_eot;
            this.eof = DFA19_eof;
            this.min = DFA19_min;
            this.max = DFA19_max;
            this.accept = DFA19_accept;
            this.special = DFA19_special;
            this.transition = DFA19_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | T__112 | T__113 | T__114 | T__115 | T__116 | T__117 | T__118 | T__119 | ID | FLOAT | INTEGER | STRING | LINE_COMMENT | MULTI_LINE_COMMENT | WHITESPACE );";
        }
    }
 

}