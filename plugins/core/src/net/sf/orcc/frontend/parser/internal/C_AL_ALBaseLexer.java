// $ANTLR 3.2 Sep 23, 2009 12:02:23 ALBaseLexer.g 2009-12-08 17:52:55

package net.sf.orcc.frontend.parser.internal;


import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class C_AL_ALBaseLexer extends Lexer {
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
    // delegators
    public C_ALLexer gC_AL;
    public C_ALLexer gParent;

    public C_AL_ALBaseLexer() {;} 
    public C_AL_ALBaseLexer(CharStream input, C_ALLexer gC_AL) {
        this(input, new RecognizerSharedState(), gC_AL);
    }
    public C_AL_ALBaseLexer(CharStream input, RecognizerSharedState state, C_ALLexer gC_AL) {
        super(input,state);

        this.gC_AL = gC_AL;
        gParent = gC_AL;
    }
    public String getGrammarFileName() { return "ALBaseLexer.g"; }

    // $ANTLR start "ACTION"
    public final void mACTION() throws RecognitionException {
        try {
            int _type = ACTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ALBaseLexer.g:115:7: ( 'action' )
            // ALBaseLexer.g:115:9: 'action'
            {
            match("action"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ACTION"

    // $ANTLR start "ACTOR"
    public final void mACTOR() throws RecognitionException {
        try {
            int _type = ACTOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ALBaseLexer.g:116:6: ( 'actor' )
            // ALBaseLexer.g:116:8: 'actor'
            {
            match("actor"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ACTOR"

    // $ANTLR start "FUNCTION"
    public final void mFUNCTION() throws RecognitionException {
        try {
            int _type = FUNCTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ALBaseLexer.g:117:9: ( 'function' )
            // ALBaseLexer.g:117:11: 'function'
            {
            match("function"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FUNCTION"

    // $ANTLR start "GUARD"
    public final void mGUARD() throws RecognitionException {
        try {
            int _type = GUARD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ALBaseLexer.g:118:6: ( 'guard' )
            // ALBaseLexer.g:118:8: 'guard'
            {
            match("guard"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GUARD"

    // $ANTLR start "INITIALIZE"
    public final void mINITIALIZE() throws RecognitionException {
        try {
            int _type = INITIALIZE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ALBaseLexer.g:119:11: ( 'initialize' )
            // ALBaseLexer.g:119:13: 'initialize'
            {
            match("initialize"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INITIALIZE"

    // $ANTLR start "PRIORITY"
    public final void mPRIORITY() throws RecognitionException {
        try {
            int _type = PRIORITY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ALBaseLexer.g:120:9: ( 'priority' )
            // ALBaseLexer.g:120:11: 'priority'
            {
            match("priority"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PRIORITY"

    // $ANTLR start "PROCEDURE"
    public final void mPROCEDURE() throws RecognitionException {
        try {
            int _type = PROCEDURE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ALBaseLexer.g:121:10: ( 'procedure' )
            // ALBaseLexer.g:121:12: 'procedure'
            {
            match("procedure"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PROCEDURE"

    // $ANTLR start "REPEAT"
    public final void mREPEAT() throws RecognitionException {
        try {
            int _type = REPEAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ALBaseLexer.g:122:7: ( 'repeat' )
            // ALBaseLexer.g:122:9: 'repeat'
            {
            match("repeat"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REPEAT"

    // $ANTLR start "SCHEDULE"
    public final void mSCHEDULE() throws RecognitionException {
        try {
            int _type = SCHEDULE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ALBaseLexer.g:123:9: ( 'schedule' )
            // ALBaseLexer.g:123:11: 'schedule'
            {
            match("schedule"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SCHEDULE"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ALBaseLexer.g:126:5: ( '+' )
            // ALBaseLexer.g:126:7: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ALBaseLexer.g:127:6: ( '-' )
            // ALBaseLexer.g:127:8: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "TIMES"
    public final void mTIMES() throws RecognitionException {
        try {
            int _type = TIMES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ALBaseLexer.g:128:6: ( '*' )
            // ALBaseLexer.g:128:8: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIMES"

    // $ANTLR start "DIV"
    public final void mDIV() throws RecognitionException {
        try {
            int _type = DIV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ALBaseLexer.g:129:4: ( '/' )
            // ALBaseLexer.g:129:6: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DIV"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ALBaseLexer.g:132:3: ( LETTER ( LETTER | '0' .. '9' )* )
            // ALBaseLexer.g:132:5: LETTER ( LETTER | '0' .. '9' )*
            {
            mLETTER(); 
            // ALBaseLexer.g:132:12: ( LETTER | '0' .. '9' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='$'||(LA1_0>='0' && LA1_0<='9')||(LA1_0>='A' && LA1_0<='Z')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ALBaseLexer.g:
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
            // ALBaseLexer.g:135:7: ( '$' | 'A' .. 'Z' | 'a' .. 'z' | '_' )
            // ALBaseLexer.g:
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
            // ALBaseLexer.g:137:6: ( ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )? | '.' ( '0' .. '9' )+ ( Exponent )? | ( '0' .. '9' )+ Exponent ) )
            // ALBaseLexer.g:137:8: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )? | '.' ( '0' .. '9' )+ ( Exponent )? | ( '0' .. '9' )+ Exponent )
            {
            // ALBaseLexer.g:137:8: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )? | '.' ( '0' .. '9' )+ ( Exponent )? | ( '0' .. '9' )+ Exponent )
            int alt8=3;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // ALBaseLexer.g:137:9: ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )?
                    {
                    // ALBaseLexer.g:137:9: ( '0' .. '9' )+
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
                    	    // ALBaseLexer.g:137:10: '0' .. '9'
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
                    // ALBaseLexer.g:137:25: ( '0' .. '9' )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // ALBaseLexer.g:137:26: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);

                    // ALBaseLexer.g:137:37: ( Exponent )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0=='E'||LA4_0=='e') ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // ALBaseLexer.g:137:37: Exponent
                            {
                            mExponent(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // ALBaseLexer.g:138:4: '.' ( '0' .. '9' )+ ( Exponent )?
                    {
                    match('.'); 
                    // ALBaseLexer.g:138:8: ( '0' .. '9' )+
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
                    	    // ALBaseLexer.g:138:9: '0' .. '9'
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

                    // ALBaseLexer.g:138:20: ( Exponent )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0=='E'||LA6_0=='e') ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // ALBaseLexer.g:138:20: Exponent
                            {
                            mExponent(); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // ALBaseLexer.g:139:4: ( '0' .. '9' )+ Exponent
                    {
                    // ALBaseLexer.g:139:4: ( '0' .. '9' )+
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
                    	    // ALBaseLexer.g:139:5: '0' .. '9'
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
            // ALBaseLexer.g:142:10: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // ALBaseLexer.g:142:12: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // ALBaseLexer.g:142:22: ( '+' | '-' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='+'||LA9_0=='-') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ALBaseLexer.g:
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

            // ALBaseLexer.g:142:33: ( '0' .. '9' )+
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
            	    // ALBaseLexer.g:142:34: '0' .. '9'
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

    // $ANTLR start "Decimal"
    public final void mDecimal() throws RecognitionException {
        try {
            // ALBaseLexer.g:145:8: ( ( '0' | '1' .. '9' ( '0' .. '9' )* ) )
            // ALBaseLexer.g:145:10: ( '0' | '1' .. '9' ( '0' .. '9' )* )
            {
            // ALBaseLexer.g:145:10: ( '0' | '1' .. '9' ( '0' .. '9' )* )
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
                    // ALBaseLexer.g:145:11: '0'
                    {
                    match('0'); 

                    }
                    break;
                case 2 :
                    // ALBaseLexer.g:145:17: '1' .. '9' ( '0' .. '9' )*
                    {
                    matchRange('1','9'); 
                    // ALBaseLexer.g:145:26: ( '0' .. '9' )*
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // ALBaseLexer.g:145:26: '0' .. '9'
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

        }
        finally {
        }
    }
    // $ANTLR end "Decimal"

    // $ANTLR start "HexDigit"
    public final void mHexDigit() throws RecognitionException {
        try {
            // ALBaseLexer.g:148:9: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // ALBaseLexer.g:148:11: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
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
    // $ANTLR end "HexDigit"

    // $ANTLR start "Hexadecimal"
    public final void mHexadecimal() throws RecognitionException {
        try {
            // ALBaseLexer.g:151:12: ( '0' ( 'x' | 'X' ) ( HexDigit )+ )
            // ALBaseLexer.g:151:14: '0' ( 'x' | 'X' ) ( HexDigit )+
            {
            match('0'); 
            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // ALBaseLexer.g:151:28: ( HexDigit )+
            int cnt13=0;
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>='0' && LA13_0<='9')||(LA13_0>='A' && LA13_0<='F')||(LA13_0>='a' && LA13_0<='f')) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // ALBaseLexer.g:151:28: HexDigit
            	    {
            	    mHexDigit(); 

            	    }
            	    break;

            	default :
            	    if ( cnt13 >= 1 ) break loop13;
                        EarlyExitException eee =
                            new EarlyExitException(13, input);
                        throw eee;
                }
                cnt13++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "Hexadecimal"

    // $ANTLR start "INTEGER"
    public final void mINTEGER() throws RecognitionException {
        try {
            int _type = INTEGER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ALBaseLexer.g:153:8: ( Decimal | Hexadecimal )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0=='0') ) {
                int LA14_1 = input.LA(2);

                if ( (LA14_1=='X'||LA14_1=='x') ) {
                    alt14=2;
                }
                else {
                    alt14=1;}
            }
            else if ( ((LA14_0>='1' && LA14_0<='9')) ) {
                alt14=1;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // ALBaseLexer.g:153:10: Decimal
                    {
                    mDecimal(); 

                    }
                    break;
                case 2 :
                    // ALBaseLexer.g:153:20: Hexadecimal
                    {
                    mHexadecimal(); 

                    }
                    break;

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
            // ALBaseLexer.g:155:7: ( '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"' )
            // ALBaseLexer.g:155:9: '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"'
            {
            match('\"'); 
            // ALBaseLexer.g:155:13: ( EscapeSequence | ~ ( '\\\\' | '\"' ) )*
            loop15:
            do {
                int alt15=3;
                int LA15_0 = input.LA(1);

                if ( (LA15_0=='\\') ) {
                    alt15=1;
                }
                else if ( ((LA15_0>='\u0000' && LA15_0<='!')||(LA15_0>='#' && LA15_0<='[')||(LA15_0>=']' && LA15_0<='\uFFFF')) ) {
                    alt15=2;
                }


                switch (alt15) {
            	case 1 :
            	    // ALBaseLexer.g:155:15: EscapeSequence
            	    {
            	    mEscapeSequence(); 

            	    }
            	    break;
            	case 2 :
            	    // ALBaseLexer.g:155:32: ~ ( '\\\\' | '\"' )
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
            	    break loop15;
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
            // ALBaseLexer.g:159:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | OctalEscape )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0=='\\') ) {
                int LA16_1 = input.LA(2);

                if ( (LA16_1=='\"'||LA16_1=='\''||LA16_1=='\\'||LA16_1=='b'||LA16_1=='f'||LA16_1=='n'||LA16_1=='r'||LA16_1=='t') ) {
                    alt16=1;
                }
                else if ( ((LA16_1>='0' && LA16_1<='7')) ) {
                    alt16=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 16, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // ALBaseLexer.g:159:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
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
                    // ALBaseLexer.g:160:9: OctalEscape
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
            // ALBaseLexer.g:165:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt17=3;
            int LA17_0 = input.LA(1);

            if ( (LA17_0=='\\') ) {
                int LA17_1 = input.LA(2);

                if ( ((LA17_1>='0' && LA17_1<='3')) ) {
                    int LA17_2 = input.LA(3);

                    if ( ((LA17_2>='0' && LA17_2<='7')) ) {
                        int LA17_4 = input.LA(4);

                        if ( ((LA17_4>='0' && LA17_4<='7')) ) {
                            alt17=1;
                        }
                        else {
                            alt17=2;}
                    }
                    else {
                        alt17=3;}
                }
                else if ( ((LA17_1>='4' && LA17_1<='7')) ) {
                    int LA17_3 = input.LA(3);

                    if ( ((LA17_3>='0' && LA17_3<='7')) ) {
                        alt17=2;
                    }
                    else {
                        alt17=3;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 17, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // ALBaseLexer.g:165:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // ALBaseLexer.g:165:14: ( '0' .. '3' )
                    // ALBaseLexer.g:165:15: '0' .. '3'
                    {
                    matchRange('0','3'); 

                    }

                    // ALBaseLexer.g:165:25: ( '0' .. '7' )
                    // ALBaseLexer.g:165:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // ALBaseLexer.g:165:36: ( '0' .. '7' )
                    // ALBaseLexer.g:165:37: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 2 :
                    // ALBaseLexer.g:166:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // ALBaseLexer.g:166:14: ( '0' .. '7' )
                    // ALBaseLexer.g:166:15: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // ALBaseLexer.g:166:25: ( '0' .. '7' )
                    // ALBaseLexer.g:166:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 3 :
                    // ALBaseLexer.g:167:9: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 
                    // ALBaseLexer.g:167:14: ( '0' .. '7' )
                    // ALBaseLexer.g:167:15: '0' .. '7'
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
            // ALBaseLexer.g:170:13: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // ALBaseLexer.g:170:15: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            // ALBaseLexer.g:170:20: (~ ( '\\n' | '\\r' ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>='\u0000' && LA18_0<='\t')||(LA18_0>='\u000B' && LA18_0<='\f')||(LA18_0>='\u000E' && LA18_0<='\uFFFF')) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // ALBaseLexer.g:170:20: ~ ( '\\n' | '\\r' )
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
            	    break loop18;
                }
            } while (true);

            // ALBaseLexer.g:170:34: ( '\\r' )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0=='\r') ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // ALBaseLexer.g:170:34: '\\r'
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
            // ALBaseLexer.g:171:19: ( '/*' ( . )* '*/' )
            // ALBaseLexer.g:171:21: '/*' ( . )* '*/'
            {
            match("/*"); 

            // ALBaseLexer.g:171:26: ( . )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0=='*') ) {
                    int LA20_1 = input.LA(2);

                    if ( (LA20_1=='/') ) {
                        alt20=2;
                    }
                    else if ( ((LA20_1>='\u0000' && LA20_1<='.')||(LA20_1>='0' && LA20_1<='\uFFFF')) ) {
                        alt20=1;
                    }


                }
                else if ( ((LA20_0>='\u0000' && LA20_0<=')')||(LA20_0>='+' && LA20_0<='\uFFFF')) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // ALBaseLexer.g:171:26: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop20;
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
            // ALBaseLexer.g:172:11: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // ALBaseLexer.g:172:13: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
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
        // ALBaseLexer.g:1:8: ( ACTION | ACTOR | FUNCTION | GUARD | INITIALIZE | PRIORITY | PROCEDURE | REPEAT | SCHEDULE | PLUS | MINUS | TIMES | DIV | ID | FLOAT | INTEGER | STRING | LINE_COMMENT | MULTI_LINE_COMMENT | WHITESPACE )
        int alt21=20;
        alt21 = dfa21.predict(input);
        switch (alt21) {
            case 1 :
                // ALBaseLexer.g:1:10: ACTION
                {
                mACTION(); 

                }
                break;
            case 2 :
                // ALBaseLexer.g:1:17: ACTOR
                {
                mACTOR(); 

                }
                break;
            case 3 :
                // ALBaseLexer.g:1:23: FUNCTION
                {
                mFUNCTION(); 

                }
                break;
            case 4 :
                // ALBaseLexer.g:1:32: GUARD
                {
                mGUARD(); 

                }
                break;
            case 5 :
                // ALBaseLexer.g:1:38: INITIALIZE
                {
                mINITIALIZE(); 

                }
                break;
            case 6 :
                // ALBaseLexer.g:1:49: PRIORITY
                {
                mPRIORITY(); 

                }
                break;
            case 7 :
                // ALBaseLexer.g:1:58: PROCEDURE
                {
                mPROCEDURE(); 

                }
                break;
            case 8 :
                // ALBaseLexer.g:1:68: REPEAT
                {
                mREPEAT(); 

                }
                break;
            case 9 :
                // ALBaseLexer.g:1:75: SCHEDULE
                {
                mSCHEDULE(); 

                }
                break;
            case 10 :
                // ALBaseLexer.g:1:84: PLUS
                {
                mPLUS(); 

                }
                break;
            case 11 :
                // ALBaseLexer.g:1:89: MINUS
                {
                mMINUS(); 

                }
                break;
            case 12 :
                // ALBaseLexer.g:1:95: TIMES
                {
                mTIMES(); 

                }
                break;
            case 13 :
                // ALBaseLexer.g:1:101: DIV
                {
                mDIV(); 

                }
                break;
            case 14 :
                // ALBaseLexer.g:1:105: ID
                {
                mID(); 

                }
                break;
            case 15 :
                // ALBaseLexer.g:1:108: FLOAT
                {
                mFLOAT(); 

                }
                break;
            case 16 :
                // ALBaseLexer.g:1:114: INTEGER
                {
                mINTEGER(); 

                }
                break;
            case 17 :
                // ALBaseLexer.g:1:122: STRING
                {
                mSTRING(); 

                }
                break;
            case 18 :
                // ALBaseLexer.g:1:129: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;
            case 19 :
                // ALBaseLexer.g:1:142: MULTI_LINE_COMMENT
                {
                mMULTI_LINE_COMMENT(); 

                }
                break;
            case 20 :
                // ALBaseLexer.g:1:161: WHITESPACE
                {
                mWHITESPACE(); 

                }
                break;

        }

    }


    protected DFA8 dfa8 = new DFA8(this);
    protected DFA21 dfa21 = new DFA21(this);
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
            return "137:8: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )? | '.' ( '0' .. '9' )+ ( Exponent )? | ( '0' .. '9' )+ Exponent )";
        }
    }
    static final String DFA21_eotS =
        "\1\uffff\7\14\3\uffff\1\33\1\uffff\1\34\1\uffff\1\34\2\uffff\7"+
        "\14\4\uffff\1\34\22\14\1\71\1\14\1\73\5\14\1\101\1\uffff\1\14\1"+
        "\uffff\3\14\1\106\1\14\1\uffff\4\14\1\uffff\1\14\1\115\1\14\1\117"+
        "\1\14\1\121\1\uffff\1\14\1\uffff\1\123\1\uffff\1\124\2\uffff";
    static final String DFA21_eofS =
        "\125\uffff";
    static final String DFA21_minS =
        "\1\11\1\143\2\165\1\156\1\162\1\145\1\143\3\uffff\1\52\1\uffff"+
        "\1\56\1\uffff\1\56\2\uffff\1\164\1\156\1\141\2\151\1\160\1\150\4"+
        "\uffff\1\56\1\151\1\143\1\162\1\164\1\157\1\143\2\145\1\157\1\162"+
        "\1\164\1\144\1\151\1\162\1\145\1\141\1\144\1\156\1\44\1\151\1\44"+
        "\1\141\1\151\1\144\1\164\1\165\1\44\1\uffff\1\157\1\uffff\1\154"+
        "\1\164\1\165\1\44\1\154\1\uffff\1\156\1\151\1\171\1\162\1\uffff"+
        "\1\145\1\44\1\172\1\44\1\145\1\44\1\uffff\1\145\1\uffff\1\44\1\uffff"+
        "\1\44\2\uffff";
    static final String DFA21_maxS =
        "\1\172\1\143\2\165\1\156\1\162\1\145\1\143\3\uffff\1\57\1\uffff"+
        "\1\145\1\uffff\1\145\2\uffff\1\164\1\156\1\141\1\151\1\157\1\160"+
        "\1\150\4\uffff\1\145\1\157\1\143\1\162\1\164\1\157\1\143\2\145\1"+
        "\157\1\162\1\164\1\144\1\151\1\162\1\145\1\141\1\144\1\156\1\172"+
        "\1\151\1\172\1\141\1\151\1\144\1\164\1\165\1\172\1\uffff\1\157\1"+
        "\uffff\1\154\1\164\1\165\1\172\1\154\1\uffff\1\156\1\151\1\171\1"+
        "\162\1\uffff\1\145\3\172\1\145\1\172\1\uffff\1\145\1\uffff\1\172"+
        "\1\uffff\1\172\2\uffff";
    static final String DFA21_acceptS =
        "\10\uffff\1\12\1\13\1\14\1\uffff\1\16\1\uffff\1\17\1\uffff\1\21"+
        "\1\24\7\uffff\1\22\1\23\1\15\1\20\34\uffff\1\2\1\uffff\1\4\5\uffff"+
        "\1\1\4\uffff\1\10\6\uffff\1\3\1\uffff\1\6\1\uffff\1\11\1\uffff\1"+
        "\7\1\5";
    static final String DFA21_specialS =
        "\125\uffff}>";
    static final String[] DFA21_transitionS = {
            "\2\21\1\uffff\2\21\22\uffff\1\21\1\uffff\1\20\1\uffff\1\14"+
            "\5\uffff\1\12\1\10\1\uffff\1\11\1\16\1\13\1\15\11\17\7\uffff"+
            "\32\14\4\uffff\1\14\1\uffff\1\1\4\14\1\2\1\3\1\14\1\4\6\14\1"+
            "\5\1\14\1\6\1\7\7\14",
            "\1\22",
            "\1\23",
            "\1\24",
            "\1\25",
            "\1\26",
            "\1\27",
            "\1\30",
            "",
            "",
            "",
            "\1\32\4\uffff\1\31",
            "",
            "\1\16\1\uffff\12\16\13\uffff\1\16\37\uffff\1\16",
            "",
            "\1\16\1\uffff\12\35\13\uffff\1\16\37\uffff\1\16",
            "",
            "",
            "\1\36",
            "\1\37",
            "\1\40",
            "\1\41",
            "\1\42\5\uffff\1\43",
            "\1\44",
            "\1\45",
            "",
            "",
            "",
            "",
            "\1\16\1\uffff\12\35\13\uffff\1\16\37\uffff\1\16",
            "\1\46\5\uffff\1\47",
            "\1\50",
            "\1\51",
            "\1\52",
            "\1\53",
            "\1\54",
            "\1\55",
            "\1\56",
            "\1\57",
            "\1\60",
            "\1\61",
            "\1\62",
            "\1\63",
            "\1\64",
            "\1\65",
            "\1\66",
            "\1\67",
            "\1\70",
            "\1\14\13\uffff\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32"+
            "\14",
            "\1\72",
            "\1\14\13\uffff\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32"+
            "\14",
            "\1\74",
            "\1\75",
            "\1\76",
            "\1\77",
            "\1\100",
            "\1\14\13\uffff\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32"+
            "\14",
            "",
            "\1\102",
            "",
            "\1\103",
            "\1\104",
            "\1\105",
            "\1\14\13\uffff\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32"+
            "\14",
            "\1\107",
            "",
            "\1\110",
            "\1\111",
            "\1\112",
            "\1\113",
            "",
            "\1\114",
            "\1\14\13\uffff\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32"+
            "\14",
            "\1\116",
            "\1\14\13\uffff\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32"+
            "\14",
            "\1\120",
            "\1\14\13\uffff\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32"+
            "\14",
            "",
            "\1\122",
            "",
            "\1\14\13\uffff\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32"+
            "\14",
            "",
            "\1\14\13\uffff\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32"+
            "\14",
            "",
            ""
    };

    static final short[] DFA21_eot = DFA.unpackEncodedString(DFA21_eotS);
    static final short[] DFA21_eof = DFA.unpackEncodedString(DFA21_eofS);
    static final char[] DFA21_min = DFA.unpackEncodedStringToUnsignedChars(DFA21_minS);
    static final char[] DFA21_max = DFA.unpackEncodedStringToUnsignedChars(DFA21_maxS);
    static final short[] DFA21_accept = DFA.unpackEncodedString(DFA21_acceptS);
    static final short[] DFA21_special = DFA.unpackEncodedString(DFA21_specialS);
    static final short[][] DFA21_transition;

    static {
        int numStates = DFA21_transitionS.length;
        DFA21_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA21_transition[i] = DFA.unpackEncodedString(DFA21_transitionS[i]);
        }
    }

    class DFA21 extends DFA {

        public DFA21(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 21;
            this.eot = DFA21_eot;
            this.eof = DFA21_eof;
            this.min = DFA21_min;
            this.max = DFA21_max;
            this.accept = DFA21_accept;
            this.special = DFA21_special;
            this.transition = DFA21_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( ACTION | ACTOR | FUNCTION | GUARD | INITIALIZE | PRIORITY | PROCEDURE | REPEAT | SCHEDULE | PLUS | MINUS | TIMES | DIV | ID | FLOAT | INTEGER | STRING | LINE_COMMENT | MULTI_LINE_COMMENT | WHITESPACE );";
        }
    }
 

}