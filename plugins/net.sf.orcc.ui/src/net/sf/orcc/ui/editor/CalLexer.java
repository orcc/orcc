// $ANTLR 3.3 Nov 30, 2010 12:45:30 D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g 2010-12-13 15:54:16

package net.sf.orcc.ui.editor;


import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class CalLexer extends Lexer {
    public static final int EOF=-1;
    public static final int Attribute=4;
    public static final int Connector=5;
    public static final int EntityDecl=6;
    public static final int EntityExpr=7;
    public static final int EntityPar=8;
    public static final int Network=9;
    public static final int StructureStmt=10;
    public static final int VarDecl=11;
    public static final int Actor=12;
    public static final int Dot=13;
    public static final int Empty=14;
    public static final int Name=15;
    public static final int Inputs=16;
    public static final int Outputs=17;
    public static final int PortDecl=18;
    public static final int QualifiedId=19;
    public static final int Parameter=20;
    public static final int Type=21;
    public static final int TypeAttr=22;
    public static final int ExprAttr=23;
    public static final int TypePar=24;
    public static final int BinOp=25;
    public static final int Boolean=26;
    public static final int Expression=27;
    public static final int Integer=28;
    public static final int List=29;
    public static final int Minus=30;
    public static final int Not=31;
    public static final int Real=32;
    public static final int String=33;
    public static final int UnOp=34;
    public static final int Var=35;
    public static final int NETWORK=36;
    public static final int QID=37;
    public static final int LBRACKET=38;
    public static final int RBRACKET=39;
    public static final int LPAREN=40;
    public static final int RPAREN=41;
    public static final int COLON=42;
    public static final int END=43;
    public static final int DOUBLE_EQUAL_ARROW=44;
    public static final int VAR=45;
    public static final int MUTABLE=46;
    public static final int EQ=47;
    public static final int COLON_EQUAL=48;
    public static final int SEMICOLON=49;
    public static final int ENTITIES=50;
    public static final int COMMA=51;
    public static final int STRUCTURE=52;
    public static final int DOUBLE_DASH_ARROW=53;
    public static final int DOT=54;
    public static final int LBRACE=55;
    public static final int RBRACE=56;
    public static final int ACTOR=57;
    public static final int IMPORT=58;
    public static final int TIMES=59;
    public static final int MULTI=60;
    public static final int LT=61;
    public static final int MINUS=62;
    public static final int NOT=63;
    public static final int PLUS=64;
    public static final int DIV=65;
    public static final int XOR=66;
    public static final int FLOAT=67;
    public static final int INTEGER=68;
    public static final int STRING=69;
    public static final int TRUE=70;
    public static final int FALSE=71;
    public static final int ALL=72;
    public static final int ID=73;
    public static final int LINE_COMMENT=74;
    public static final int MULTI_LINE_COMMENT=75;
    public static final int WHITESPACE=76;
    public static final int GE=77;
    public static final int GT=78;
    public static final int LE=79;
    public static final int NE=80;
    public static final int ARROW=81;
    public static final int DOUBLE_DOT=82;
    public static final int AND=83;
    public static final int OR=84;
    public static final int SHARP=85;

    // delegates
    // delegators

    public CalLexer() {;} 
    public CalLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public CalLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g"; }

    // $ANTLR start "ALL"
    public final void mALL() throws RecognitionException {
        try {
            int _type = ALL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:220:4: ( 'all' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:220:6: 'all'
            {
            match("all"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ALL"

    // $ANTLR start "ACTOR"
    public final void mACTOR() throws RecognitionException {
        try {
            int _type = ACTOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:221:6: ( 'actor' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:221:8: 'actor'
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

    // $ANTLR start "END"
    public final void mEND() throws RecognitionException {
        try {
            int _type = END;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:222:4: ( 'end' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:222:6: 'end'
            {
            match("end"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END"

    // $ANTLR start "ENTITIES"
    public final void mENTITIES() throws RecognitionException {
        try {
            int _type = ENTITIES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:223:9: ( 'entities' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:223:11: 'entities'
            {
            match("entities"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ENTITIES"

    // $ANTLR start "IMPORT"
    public final void mIMPORT() throws RecognitionException {
        try {
            int _type = IMPORT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:224:7: ( 'import' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:224:9: 'import'
            {
            match("import"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IMPORT"

    // $ANTLR start "MULTI"
    public final void mMULTI() throws RecognitionException {
        try {
            int _type = MULTI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:225:6: ( 'multi' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:225:8: 'multi'
            {
            match("multi"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MULTI"

    // $ANTLR start "MUTABLE"
    public final void mMUTABLE() throws RecognitionException {
        try {
            int _type = MUTABLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:226:8: ( 'mutable' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:226:10: 'mutable'
            {
            match("mutable"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MUTABLE"

    // $ANTLR start "NETWORK"
    public final void mNETWORK() throws RecognitionException {
        try {
            int _type = NETWORK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:227:8: ( 'network' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:227:10: 'network'
            {
            match("network"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NETWORK"

    // $ANTLR start "STRUCTURE"
    public final void mSTRUCTURE() throws RecognitionException {
        try {
            int _type = STRUCTURE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:228:10: ( 'structure' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:228:12: 'structure'
            {
            match("structure"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRUCTURE"

    // $ANTLR start "NOT"
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:229:4: ( 'not' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:229:6: 'not'
            {
            match("not"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT"

    // $ANTLR start "VAR"
    public final void mVAR() throws RecognitionException {
        try {
            int _type = VAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:230:4: ( 'var' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:230:6: 'var'
            {
            match("var"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VAR"

    // $ANTLR start "TRUE"
    public final void mTRUE() throws RecognitionException {
        try {
            int _type = TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:232:5: ( 'true' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:232:7: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TRUE"

    // $ANTLR start "FALSE"
    public final void mFALSE() throws RecognitionException {
        try {
            int _type = FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:233:6: ( 'false' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:233:8: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FALSE"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:236:3: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '$' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '$' | '0' .. '9' )* )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:236:5: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '$' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '$' | '0' .. '9' )*
            {
            if ( input.LA(1)=='$'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:236:39: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '$' | '0' .. '9' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='$'||(LA1_0>='0' && LA1_0<='9')||(LA1_0>='A' && LA1_0<='Z')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:
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

        }
        finally {
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "QID"
    public final void mQID() throws RecognitionException {
        try {
            int _type = QID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:238:4: ( ID ( DOT ID )* )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:238:6: ID ( DOT ID )*
            {
            mID(); 
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:238:9: ( DOT ID )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='.') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:238:10: DOT ID
            	    {
            	    mDOT(); 
            	    mID(); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "QID"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:240:6: ( ( '-' )? ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )? | '.' ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )? | ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ ) ) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:240:8: ( '-' )? ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )? | '.' ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )? | ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ ) )
            {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:240:8: ( '-' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='-') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:240:8: '-'
                    {
                    match('-'); 

                    }
                    break;

            }

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:240:13: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )? | '.' ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )? | ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ ) )
            int alt16=3;
            alt16 = dfa16.predict(input);
            switch (alt16) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:240:14: ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )?
                    {
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:240:14: ( '0' .. '9' )+
                    int cnt4=0;
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:240:15: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt4 >= 1 ) break loop4;
                                EarlyExitException eee =
                                    new EarlyExitException(4, input);
                                throw eee;
                        }
                        cnt4++;
                    } while (true);

                    match('.'); 
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:240:30: ( '0' .. '9' )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:240:31: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);

                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:240:42: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='E'||LA8_0=='e') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:240:43: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
                            {
                            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                                input.consume();

                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;}

                            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:240:55: ( '+' | '-' )?
                            int alt6=2;
                            int LA6_0 = input.LA(1);

                            if ( (LA6_0=='+'||LA6_0=='-') ) {
                                alt6=1;
                            }
                            switch (alt6) {
                                case 1 :
                                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:
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

                            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:240:68: ( '0' .. '9' )+
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
                            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:240:69: '0' .. '9'
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


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:241:4: '.' ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )?
                    {
                    match('.'); 
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:241:8: ( '0' .. '9' )+
                    int cnt9=0;
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0>='0' && LA9_0<='9')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:241:9: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt9 >= 1 ) break loop9;
                                EarlyExitException eee =
                                    new EarlyExitException(9, input);
                                throw eee;
                        }
                        cnt9++;
                    } while (true);

                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:241:20: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0=='E'||LA12_0=='e') ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:241:21: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
                            {
                            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                                input.consume();

                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;}

                            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:241:33: ( '+' | '-' )?
                            int alt10=2;
                            int LA10_0 = input.LA(1);

                            if ( (LA10_0=='+'||LA10_0=='-') ) {
                                alt10=1;
                            }
                            switch (alt10) {
                                case 1 :
                                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:
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

                            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:241:46: ( '0' .. '9' )+
                            int cnt11=0;
                            loop11:
                            do {
                                int alt11=2;
                                int LA11_0 = input.LA(1);

                                if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                                    alt11=1;
                                }


                                switch (alt11) {
                            	case 1 :
                            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:241:47: '0' .. '9'
                            	    {
                            	    matchRange('0','9'); 

                            	    }
                            	    break;

                            	default :
                            	    if ( cnt11 >= 1 ) break loop11;
                                        EarlyExitException eee =
                                            new EarlyExitException(11, input);
                                        throw eee;
                                }
                                cnt11++;
                            } while (true);


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:242:4: ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
                    {
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:242:4: ( '0' .. '9' )+
                    int cnt13=0;
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( ((LA13_0>='0' && LA13_0<='9')) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:242:5: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

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

                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:242:16: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:242:17: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
                    {
                    if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:242:29: ( '+' | '-' )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0=='+'||LA14_0=='-') ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:
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

                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:242:42: ( '0' .. '9' )+
                    int cnt15=0;
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( ((LA15_0>='0' && LA15_0<='9')) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:242:43: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt15 >= 1 ) break loop15;
                                EarlyExitException eee =
                                    new EarlyExitException(15, input);
                                throw eee;
                        }
                        cnt15++;
                    } while (true);


                    }


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

    // $ANTLR start "INTEGER"
    public final void mINTEGER() throws RecognitionException {
        try {
            int _type = INTEGER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:243:8: ( ( '-' )? ( '0' .. '9' )+ )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:243:10: ( '-' )? ( '0' .. '9' )+
            {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:243:10: ( '-' )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0=='-') ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:243:10: '-'
                    {
                    match('-'); 

                    }
                    break;

            }

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:243:15: ( '0' .. '9' )+
            int cnt18=0;
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>='0' && LA18_0<='9')) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:243:16: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt18 >= 1 ) break loop18;
                        EarlyExitException eee =
                            new EarlyExitException(18, input);
                        throw eee;
                }
                cnt18++;
            } while (true);


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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:244:7: ( '\\\"' ( . )* '\\\"' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:244:9: '\\\"' ( . )* '\\\"'
            {
            match('\"'); 
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:244:14: ( . )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0=='\"') ) {
                    alt19=2;
                }
                else if ( ((LA19_0>='\u0000' && LA19_0<='!')||(LA19_0>='#' && LA19_0<='\uFFFF')) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:244:14: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop19;
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

    // $ANTLR start "LINE_COMMENT"
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            int _type = LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:246:13: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:246:15: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:246:20: (~ ( '\\n' | '\\r' ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>='\u0000' && LA20_0<='\t')||(LA20_0>='\u000B' && LA20_0<='\f')||(LA20_0>='\u000E' && LA20_0<='\uFFFF')) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:246:20: ~ ( '\\n' | '\\r' )
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
            	    break loop20;
                }
            } while (true);

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:246:34: ( '\\r' )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0=='\r') ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:246:34: '\\r'
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:247:19: ( '/*' ( . )* '*/' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:247:21: '/*' ( . )* '*/'
            {
            match("/*"); 

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:247:26: ( . )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0=='*') ) {
                    int LA22_1 = input.LA(2);

                    if ( (LA22_1=='/') ) {
                        alt22=2;
                    }
                    else if ( ((LA22_1>='\u0000' && LA22_1<='.')||(LA22_1>='0' && LA22_1<='\uFFFF')) ) {
                        alt22=1;
                    }


                }
                else if ( ((LA22_0>='\u0000' && LA22_0<=')')||(LA22_0>='+' && LA22_0<='\uFFFF')) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:247:26: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop22;
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:248:11: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:248:13: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
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

    // $ANTLR start "EQ"
    public final void mEQ() throws RecognitionException {
        try {
            int _type = EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:250:3: ( '=' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:250:5: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQ"

    // $ANTLR start "GE"
    public final void mGE() throws RecognitionException {
        try {
            int _type = GE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:251:3: ( '>=' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:251:5: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GE"

    // $ANTLR start "GT"
    public final void mGT() throws RecognitionException {
        try {
            int _type = GT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:252:3: ( '>' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:252:5: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GT"

    // $ANTLR start "LE"
    public final void mLE() throws RecognitionException {
        try {
            int _type = LE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:253:3: ( '<=' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:253:5: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LE"

    // $ANTLR start "LT"
    public final void mLT() throws RecognitionException {
        try {
            int _type = LT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:254:3: ( '<' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:254:5: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LT"

    // $ANTLR start "NE"
    public final void mNE() throws RecognitionException {
        try {
            int _type = NE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:255:3: ( '!=' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:255:5: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NE"

    // $ANTLR start "ARROW"
    public final void mARROW() throws RecognitionException {
        try {
            int _type = ARROW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:257:6: ( '->' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:257:8: '->'
            {
            match("->"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ARROW"

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:258:6: ( ':' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:258:8: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "COLON_EQUAL"
    public final void mCOLON_EQUAL() throws RecognitionException {
        try {
            int _type = COLON_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:259:12: ( ':=' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:259:14: ':='
            {
            match(":="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COLON_EQUAL"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:260:6: ( ',' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:260:8: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:263:4: ( '.' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:263:6: '.'
            {
            match('.'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "DOUBLE_DASH_ARROW"
    public final void mDOUBLE_DASH_ARROW() throws RecognitionException {
        try {
            int _type = DOUBLE_DASH_ARROW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:265:18: ( '-->' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:265:20: '-->'
            {
            match("-->"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOUBLE_DASH_ARROW"

    // $ANTLR start "DOUBLE_EQUAL_ARROW"
    public final void mDOUBLE_EQUAL_ARROW() throws RecognitionException {
        try {
            int _type = DOUBLE_EQUAL_ARROW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:266:19: ( '==>' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:266:21: '==>'
            {
            match("==>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOUBLE_EQUAL_ARROW"

    // $ANTLR start "DOUBLE_DOT"
    public final void mDOUBLE_DOT() throws RecognitionException {
        try {
            int _type = DOUBLE_DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:267:11: ( '..' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:267:13: '..'
            {
            match(".."); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOUBLE_DOT"

    // $ANTLR start "LBRACE"
    public final void mLBRACE() throws RecognitionException {
        try {
            int _type = LBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:269:7: ( '{' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:269:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LBRACE"

    // $ANTLR start "RBRACE"
    public final void mRBRACE() throws RecognitionException {
        try {
            int _type = RBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:270:7: ( '}' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:270:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RBRACE"

    // $ANTLR start "LBRACKET"
    public final void mLBRACKET() throws RecognitionException {
        try {
            int _type = LBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:271:9: ( '[' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:271:11: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LBRACKET"

    // $ANTLR start "RBRACKET"
    public final void mRBRACKET() throws RecognitionException {
        try {
            int _type = RBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:272:9: ( ']' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:272:11: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RBRACKET"

    // $ANTLR start "LPAREN"
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:273:7: ( '(' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:273:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LPAREN"

    // $ANTLR start "RPAREN"
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:274:7: ( ')' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:274:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RPAREN"

    // $ANTLR start "XOR"
    public final void mXOR() throws RecognitionException {
        try {
            int _type = XOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:276:4: ( '^' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:276:6: '^'
            {
            match('^'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "XOR"

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:277:4: ( '&' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:277:6: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AND"

    // $ANTLR start "OR"
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:278:3: ( '|' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:278:5: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OR"

    // $ANTLR start "DIV"
    public final void mDIV() throws RecognitionException {
        try {
            int _type = DIV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:279:4: ( '/' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:279:6: '/'
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

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:280:6: ( '-' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:280:8: '-'
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

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:281:5: ( '+' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:281:7: '+'
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

    // $ANTLR start "TIMES"
    public final void mTIMES() throws RecognitionException {
        try {
            int _type = TIMES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:282:6: ( '*' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:282:8: '*'
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

    // $ANTLR start "SEMICOLON"
    public final void mSEMICOLON() throws RecognitionException {
        try {
            int _type = SEMICOLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:284:10: ( ';' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:284:12: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SEMICOLON"

    // $ANTLR start "SHARP"
    public final void mSHARP() throws RecognitionException {
        try {
            int _type = SHARP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:285:6: ( '#' )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:285:8: '#'
            {
            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SHARP"

    public void mTokens() throws RecognitionException {
        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:8: ( ALL | ACTOR | END | ENTITIES | IMPORT | MULTI | MUTABLE | NETWORK | STRUCTURE | NOT | VAR | TRUE | FALSE | QID | FLOAT | INTEGER | STRING | LINE_COMMENT | MULTI_LINE_COMMENT | WHITESPACE | EQ | GE | GT | LE | LT | NE | ARROW | COLON | COLON_EQUAL | COMMA | DOUBLE_DASH_ARROW | DOUBLE_EQUAL_ARROW | DOUBLE_DOT | LBRACE | RBRACE | LBRACKET | RBRACKET | LPAREN | RPAREN | XOR | AND | OR | DIV | MINUS | PLUS | TIMES | SEMICOLON | SHARP )
        int alt23=48;
        alt23 = dfa23.predict(input);
        switch (alt23) {
            case 1 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:10: ALL
                {
                mALL(); 

                }
                break;
            case 2 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:14: ACTOR
                {
                mACTOR(); 

                }
                break;
            case 3 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:20: END
                {
                mEND(); 

                }
                break;
            case 4 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:24: ENTITIES
                {
                mENTITIES(); 

                }
                break;
            case 5 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:33: IMPORT
                {
                mIMPORT(); 

                }
                break;
            case 6 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:40: MULTI
                {
                mMULTI(); 

                }
                break;
            case 7 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:46: MUTABLE
                {
                mMUTABLE(); 

                }
                break;
            case 8 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:54: NETWORK
                {
                mNETWORK(); 

                }
                break;
            case 9 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:62: STRUCTURE
                {
                mSTRUCTURE(); 

                }
                break;
            case 10 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:72: NOT
                {
                mNOT(); 

                }
                break;
            case 11 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:76: VAR
                {
                mVAR(); 

                }
                break;
            case 12 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:80: TRUE
                {
                mTRUE(); 

                }
                break;
            case 13 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:85: FALSE
                {
                mFALSE(); 

                }
                break;
            case 14 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:91: QID
                {
                mQID(); 

                }
                break;
            case 15 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:95: FLOAT
                {
                mFLOAT(); 

                }
                break;
            case 16 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:101: INTEGER
                {
                mINTEGER(); 

                }
                break;
            case 17 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:109: STRING
                {
                mSTRING(); 

                }
                break;
            case 18 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:116: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;
            case 19 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:129: MULTI_LINE_COMMENT
                {
                mMULTI_LINE_COMMENT(); 

                }
                break;
            case 20 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:148: WHITESPACE
                {
                mWHITESPACE(); 

                }
                break;
            case 21 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:159: EQ
                {
                mEQ(); 

                }
                break;
            case 22 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:162: GE
                {
                mGE(); 

                }
                break;
            case 23 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:165: GT
                {
                mGT(); 

                }
                break;
            case 24 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:168: LE
                {
                mLE(); 

                }
                break;
            case 25 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:171: LT
                {
                mLT(); 

                }
                break;
            case 26 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:174: NE
                {
                mNE(); 

                }
                break;
            case 27 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:177: ARROW
                {
                mARROW(); 

                }
                break;
            case 28 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:183: COLON
                {
                mCOLON(); 

                }
                break;
            case 29 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:189: COLON_EQUAL
                {
                mCOLON_EQUAL(); 

                }
                break;
            case 30 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:201: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 31 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:207: DOUBLE_DASH_ARROW
                {
                mDOUBLE_DASH_ARROW(); 

                }
                break;
            case 32 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:225: DOUBLE_EQUAL_ARROW
                {
                mDOUBLE_EQUAL_ARROW(); 

                }
                break;
            case 33 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:244: DOUBLE_DOT
                {
                mDOUBLE_DOT(); 

                }
                break;
            case 34 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:255: LBRACE
                {
                mLBRACE(); 

                }
                break;
            case 35 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:262: RBRACE
                {
                mRBRACE(); 

                }
                break;
            case 36 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:269: LBRACKET
                {
                mLBRACKET(); 

                }
                break;
            case 37 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:278: RBRACKET
                {
                mRBRACKET(); 

                }
                break;
            case 38 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:287: LPAREN
                {
                mLPAREN(); 

                }
                break;
            case 39 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:294: RPAREN
                {
                mRPAREN(); 

                }
                break;
            case 40 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:301: XOR
                {
                mXOR(); 

                }
                break;
            case 41 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:305: AND
                {
                mAND(); 

                }
                break;
            case 42 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:309: OR
                {
                mOR(); 

                }
                break;
            case 43 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:312: DIV
                {
                mDIV(); 

                }
                break;
            case 44 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:316: MINUS
                {
                mMINUS(); 

                }
                break;
            case 45 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:322: PLUS
                {
                mPLUS(); 

                }
                break;
            case 46 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:327: TIMES
                {
                mTIMES(); 

                }
                break;
            case 47 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:333: SEMICOLON
                {
                mSEMICOLON(); 

                }
                break;
            case 48 :
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:343: SHARP
                {
                mSHARP(); 

                }
                break;

        }

    }


    protected DFA16 dfa16 = new DFA16(this);
    protected DFA23 dfa23 = new DFA23(this);
    static final String DFA16_eotS =
        "\5\uffff";
    static final String DFA16_eofS =
        "\5\uffff";
    static final String DFA16_minS =
        "\2\56\3\uffff";
    static final String DFA16_maxS =
        "\1\71\1\145\3\uffff";
    static final String DFA16_acceptS =
        "\2\uffff\1\2\1\1\1\3";
    static final String DFA16_specialS =
        "\5\uffff}>";
    static final String[] DFA16_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\3\1\uffff\12\1\13\uffff\1\4\37\uffff\1\4",
            "",
            "",
            ""
    };

    static final short[] DFA16_eot = DFA.unpackEncodedString(DFA16_eotS);
    static final short[] DFA16_eof = DFA.unpackEncodedString(DFA16_eofS);
    static final char[] DFA16_min = DFA.unpackEncodedStringToUnsignedChars(DFA16_minS);
    static final char[] DFA16_max = DFA.unpackEncodedStringToUnsignedChars(DFA16_maxS);
    static final short[] DFA16_accept = DFA.unpackEncodedString(DFA16_acceptS);
    static final short[] DFA16_special = DFA.unpackEncodedString(DFA16_specialS);
    static final short[][] DFA16_transition;

    static {
        int numStates = DFA16_transitionS.length;
        DFA16_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA16_transition[i] = DFA.unpackEncodedString(DFA16_transitionS[i]);
        }
    }

    class DFA16 extends DFA {

        public DFA16(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 16;
            this.eot = DFA16_eot;
            this.eof = DFA16_eof;
            this.min = DFA16_min;
            this.max = DFA16_max;
            this.accept = DFA16_accept;
            this.special = DFA16_special;
            this.transition = DFA16_transition;
        }
        public String getDescription() {
            return "240:13: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )? | '.' ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )? | ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ ) )";
        }
    }
    static final String DFA23_eotS =
        "\1\uffff\11\12\1\uffff\1\62\1\63\2\uffff\1\67\1\uffff\1\71\1\73"+
        "\1\75\1\uffff\1\77\16\uffff\13\12\21\uffff\1\115\1\12\1\117\5\12"+
        "\1\125\1\12\1\127\2\12\1\uffff\1\12\1\uffff\5\12\1\uffff\1\12\1"+
        "\uffff\1\141\1\12\1\143\2\12\1\146\3\12\1\uffff\1\152\1\uffff\1"+
        "\12\1\154\1\uffff\3\12\1\uffff\1\12\1\uffff\1\161\1\162\1\12\1\164"+
        "\2\uffff\1\12\1\uffff\1\166\1\uffff";
    static final String DFA23_eofS =
        "\167\uffff";
    static final String DFA23_minS =
        "\1\11\1\143\1\156\1\155\1\165\1\145\1\164\1\141\1\162\1\141\1\uffff"+
        "\1\55\2\56\1\uffff\1\52\1\uffff\3\75\1\uffff\1\75\16\uffff\1\154"+
        "\1\164\1\144\1\160\1\154\2\164\2\162\1\165\1\154\21\uffff\1\44\1"+
        "\157\1\44\1\151\1\157\1\164\1\141\1\167\1\44\1\165\1\44\1\145\1"+
        "\163\1\uffff\1\162\1\uffff\1\164\1\162\1\151\1\142\1\157\1\uffff"+
        "\1\143\1\uffff\1\44\1\145\1\44\1\151\1\164\1\44\1\154\1\162\1\164"+
        "\1\uffff\1\44\1\uffff\1\145\1\44\1\uffff\1\145\1\153\1\165\1\uffff"+
        "\1\163\1\uffff\2\44\1\162\1\44\2\uffff\1\145\1\uffff\1\44\1\uffff";
    static final String DFA23_maxS =
        "\1\175\1\154\1\156\1\155\1\165\1\157\1\164\1\141\1\162\1\141\1"+
        "\uffff\1\76\1\145\1\71\1\uffff\1\57\1\uffff\3\75\1\uffff\1\75\16"+
        "\uffff\1\154\2\164\1\160\3\164\2\162\1\165\1\154\21\uffff\1\172"+
        "\1\157\1\172\1\151\1\157\1\164\1\141\1\167\1\172\1\165\1\172\1\145"+
        "\1\163\1\uffff\1\162\1\uffff\1\164\1\162\1\151\1\142\1\157\1\uffff"+
        "\1\143\1\uffff\1\172\1\145\1\172\1\151\1\164\1\172\1\154\1\162\1"+
        "\164\1\uffff\1\172\1\uffff\1\145\1\172\1\uffff\1\145\1\153\1\165"+
        "\1\uffff\1\163\1\uffff\2\172\1\162\1\172\2\uffff\1\145\1\uffff\1"+
        "\172\1\uffff";
    static final String DFA23_acceptS =
        "\12\uffff\1\16\3\uffff\1\21\1\uffff\1\24\3\uffff\1\32\1\uffff\1"+
        "\36\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\55\1\56\1\57"+
        "\1\60\13\uffff\1\33\1\37\1\17\1\54\1\20\1\41\1\22\1\23\1\53\1\40"+
        "\1\25\1\26\1\27\1\30\1\31\1\35\1\34\15\uffff\1\1\1\uffff\1\3\5\uffff"+
        "\1\12\1\uffff\1\13\11\uffff\1\14\1\uffff\1\2\2\uffff\1\6\3\uffff"+
        "\1\15\1\uffff\1\5\4\uffff\1\7\1\10\1\uffff\1\4\1\uffff\1\11";
    static final String DFA23_specialS =
        "\167\uffff}>";
    static final String[] DFA23_transitionS = {
            "\2\20\1\uffff\2\20\22\uffff\1\20\1\24\1\16\1\43\1\12\1\uffff"+
            "\1\36\1\uffff\1\33\1\34\1\41\1\40\1\26\1\13\1\15\1\17\12\14"+
            "\1\25\1\42\1\23\1\21\1\22\2\uffff\32\12\1\31\1\uffff\1\32\1"+
            "\35\1\12\1\uffff\1\1\3\12\1\2\1\11\2\12\1\3\3\12\1\4\1\5\4\12"+
            "\1\6\1\10\1\12\1\7\4\12\1\27\1\37\1\30",
            "\1\45\10\uffff\1\44",
            "\1\46",
            "\1\47",
            "\1\50",
            "\1\51\11\uffff\1\52",
            "\1\53",
            "\1\54",
            "\1\55",
            "\1\56",
            "",
            "\1\60\1\61\1\uffff\12\14\4\uffff\1\57",
            "\1\61\1\uffff\12\14\13\uffff\1\61\37\uffff\1\61",
            "\1\64\1\uffff\12\61",
            "",
            "\1\66\4\uffff\1\65",
            "",
            "\1\70",
            "\1\72",
            "\1\74",
            "",
            "\1\76",
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
            "",
            "\1\100",
            "\1\101",
            "\1\102\17\uffff\1\103",
            "\1\104",
            "\1\105\7\uffff\1\106",
            "\1\107",
            "\1\110",
            "\1\111",
            "\1\112",
            "\1\113",
            "\1\114",
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
            "",
            "",
            "",
            "",
            "\1\12\11\uffff\1\12\1\uffff\12\12\7\uffff\32\12\4\uffff\1"+
            "\12\1\uffff\32\12",
            "\1\116",
            "\1\12\11\uffff\1\12\1\uffff\12\12\7\uffff\32\12\4\uffff\1"+
            "\12\1\uffff\32\12",
            "\1\120",
            "\1\121",
            "\1\122",
            "\1\123",
            "\1\124",
            "\1\12\11\uffff\1\12\1\uffff\12\12\7\uffff\32\12\4\uffff\1"+
            "\12\1\uffff\32\12",
            "\1\126",
            "\1\12\11\uffff\1\12\1\uffff\12\12\7\uffff\32\12\4\uffff\1"+
            "\12\1\uffff\32\12",
            "\1\130",
            "\1\131",
            "",
            "\1\132",
            "",
            "\1\133",
            "\1\134",
            "\1\135",
            "\1\136",
            "\1\137",
            "",
            "\1\140",
            "",
            "\1\12\11\uffff\1\12\1\uffff\12\12\7\uffff\32\12\4\uffff\1"+
            "\12\1\uffff\32\12",
            "\1\142",
            "\1\12\11\uffff\1\12\1\uffff\12\12\7\uffff\32\12\4\uffff\1"+
            "\12\1\uffff\32\12",
            "\1\144",
            "\1\145",
            "\1\12\11\uffff\1\12\1\uffff\12\12\7\uffff\32\12\4\uffff\1"+
            "\12\1\uffff\32\12",
            "\1\147",
            "\1\150",
            "\1\151",
            "",
            "\1\12\11\uffff\1\12\1\uffff\12\12\7\uffff\32\12\4\uffff\1"+
            "\12\1\uffff\32\12",
            "",
            "\1\153",
            "\1\12\11\uffff\1\12\1\uffff\12\12\7\uffff\32\12\4\uffff\1"+
            "\12\1\uffff\32\12",
            "",
            "\1\155",
            "\1\156",
            "\1\157",
            "",
            "\1\160",
            "",
            "\1\12\11\uffff\1\12\1\uffff\12\12\7\uffff\32\12\4\uffff\1"+
            "\12\1\uffff\32\12",
            "\1\12\11\uffff\1\12\1\uffff\12\12\7\uffff\32\12\4\uffff\1"+
            "\12\1\uffff\32\12",
            "\1\163",
            "\1\12\11\uffff\1\12\1\uffff\12\12\7\uffff\32\12\4\uffff\1"+
            "\12\1\uffff\32\12",
            "",
            "",
            "\1\165",
            "",
            "\1\12\11\uffff\1\12\1\uffff\12\12\7\uffff\32\12\4\uffff\1"+
            "\12\1\uffff\32\12",
            ""
    };

    static final short[] DFA23_eot = DFA.unpackEncodedString(DFA23_eotS);
    static final short[] DFA23_eof = DFA.unpackEncodedString(DFA23_eofS);
    static final char[] DFA23_min = DFA.unpackEncodedStringToUnsignedChars(DFA23_minS);
    static final char[] DFA23_max = DFA.unpackEncodedStringToUnsignedChars(DFA23_maxS);
    static final short[] DFA23_accept = DFA.unpackEncodedString(DFA23_acceptS);
    static final short[] DFA23_special = DFA.unpackEncodedString(DFA23_specialS);
    static final short[][] DFA23_transition;

    static {
        int numStates = DFA23_transitionS.length;
        DFA23_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA23_transition[i] = DFA.unpackEncodedString(DFA23_transitionS[i]);
        }
    }

    class DFA23 extends DFA {

        public DFA23(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 23;
            this.eot = DFA23_eot;
            this.eof = DFA23_eof;
            this.min = DFA23_min;
            this.max = DFA23_max;
            this.accept = DFA23_accept;
            this.special = DFA23_special;
            this.transition = DFA23_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( ALL | ACTOR | END | ENTITIES | IMPORT | MULTI | MUTABLE | NETWORK | STRUCTURE | NOT | VAR | TRUE | FALSE | QID | FLOAT | INTEGER | STRING | LINE_COMMENT | MULTI_LINE_COMMENT | WHITESPACE | EQ | GE | GT | LE | LT | NE | ARROW | COLON | COLON_EQUAL | COMMA | DOUBLE_DASH_ARROW | DOUBLE_EQUAL_ARROW | DOUBLE_DOT | LBRACE | RBRACE | LBRACKET | RBRACKET | LPAREN | RPAREN | XOR | AND | OR | DIV | MINUS | PLUS | TIMES | SEMICOLON | SHARP );";
        }
    }
 

}