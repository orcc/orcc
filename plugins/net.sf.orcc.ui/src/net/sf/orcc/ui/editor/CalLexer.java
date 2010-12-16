// $ANTLR 3.3 Nov 30, 2010 12:45:30 C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g 2010-12-16 01:20:01

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
    public static final int PACKAGE=58;
    public static final int IMPORT=59;
    public static final int QID_WILDCARD=60;
    public static final int MULTI=61;
    public static final int LT=62;
    public static final int MINUS=63;
    public static final int NOT=64;
    public static final int PLUS=65;
    public static final int TIMES=66;
    public static final int DIV=67;
    public static final int XOR=68;
    public static final int FLOAT=69;
    public static final int INTEGER=70;
    public static final int STRING=71;
    public static final int TRUE=72;
    public static final int FALSE=73;
    public static final int ALL=74;
    public static final int ID=75;
    public static final int LINE_COMMENT=76;
    public static final int MULTI_LINE_COMMENT=77;
    public static final int WHITESPACE=78;
    public static final int GE=79;
    public static final int GT=80;
    public static final int LE=81;
    public static final int NE=82;
    public static final int ARROW=83;
    public static final int DOUBLE_DOT=84;
    public static final int AND=85;
    public static final int OR=86;
    public static final int SHARP=87;

    // delegates
    // delegators

    public CalLexer() {;} 
    public CalLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public CalLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g"; }

    // $ANTLR start "ALL"
    public final void mALL() throws RecognitionException {
        try {
            int _type = ALL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:222:4: ( 'all' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:222:6: 'all'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:223:6: ( 'actor' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:223:8: 'actor'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:224:4: ( 'end' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:224:6: 'end'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:225:9: ( 'entities' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:225:11: 'entities'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:226:7: ( 'import' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:226:9: 'import'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:227:6: ( 'multi' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:227:8: 'multi'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:228:8: ( 'mutable' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:228:10: 'mutable'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:229:8: ( 'network' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:229:10: 'network'
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

    // $ANTLR start "PACKAGE"
    public final void mPACKAGE() throws RecognitionException {
        try {
            int _type = PACKAGE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:230:9: ( 'package' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:230:11: 'package'
            {
            match("package"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PACKAGE"

    // $ANTLR start "STRUCTURE"
    public final void mSTRUCTURE() throws RecognitionException {
        try {
            int _type = STRUCTURE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:231:10: ( 'structure' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:231:12: 'structure'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:232:4: ( 'not' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:232:6: 'not'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:233:4: ( 'var' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:233:6: 'var'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:235:5: ( 'true' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:235:7: 'true'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:236:6: ( 'false' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:236:8: 'false'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:239:3: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '$' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '$' | '0' .. '9' )* )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:239:5: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '$' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '$' | '0' .. '9' )*
            {
            if ( input.LA(1)=='$'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:239:39: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '$' | '0' .. '9' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='$'||(LA1_0>='0' && LA1_0<='9')||(LA1_0>='A' && LA1_0<='Z')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:241:4: ( ID ( DOT ID )* )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:241:6: ID ( DOT ID )*
            {
            mID(); 
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:241:9: ( DOT ID )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='.') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:241:10: DOT ID
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

    // $ANTLR start "QID_WILDCARD"
    public final void mQID_WILDCARD() throws RecognitionException {
        try {
            int _type = QID_WILDCARD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:243:13: ( ID ( DOT ID )* ( '.*' )? )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:243:15: ID ( DOT ID )* ( '.*' )?
            {
            mID(); 
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:243:18: ( DOT ID )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='.') ) {
                    int LA3_1 = input.LA(2);

                    if ( (LA3_1=='$'||(LA3_1>='A' && LA3_1<='Z')||LA3_1=='_'||(LA3_1>='a' && LA3_1<='z')) ) {
                        alt3=1;
                    }


                }


                switch (alt3) {
            	case 1 :
            	    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:243:19: DOT ID
            	    {
            	    mDOT(); 
            	    mID(); 

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:243:28: ( '.*' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='.') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:243:28: '.*'
                    {
                    match(".*"); 


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
    // $ANTLR end "QID_WILDCARD"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:245:6: ( ( '-' )? ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )? | '.' ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )? | ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ ) ) )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:245:8: ( '-' )? ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )? | '.' ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )? | ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ ) )
            {
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:245:8: ( '-' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='-') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:245:8: '-'
                    {
                    match('-'); 

                    }
                    break;

            }

            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:245:13: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )? | '.' ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )? | ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ ) )
            int alt18=3;
            alt18 = dfa18.predict(input);
            switch (alt18) {
                case 1 :
                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:245:14: ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )?
                    {
                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:245:14: ( '0' .. '9' )+
                    int cnt6=0;
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:245:15: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt6 >= 1 ) break loop6;
                                EarlyExitException eee =
                                    new EarlyExitException(6, input);
                                throw eee;
                        }
                        cnt6++;
                    } while (true);

                    match('.'); 
                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:245:30: ( '0' .. '9' )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( ((LA7_0>='0' && LA7_0<='9')) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:245:31: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:245:42: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0=='E'||LA10_0=='e') ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:245:43: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
                            {
                            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                                input.consume();

                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;}

                            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:245:55: ( '+' | '-' )?
                            int alt8=2;
                            int LA8_0 = input.LA(1);

                            if ( (LA8_0=='+'||LA8_0=='-') ) {
                                alt8=1;
                            }
                            switch (alt8) {
                                case 1 :
                                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:
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

                            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:245:68: ( '0' .. '9' )+
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
                            	    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:245:69: '0' .. '9'
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


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:246:4: '.' ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )?
                    {
                    match('.'); 
                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:246:8: ( '0' .. '9' )+
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
                    	    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:246:9: '0' .. '9'
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

                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:246:20: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0=='E'||LA14_0=='e') ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:246:21: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
                            {
                            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                                input.consume();

                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;}

                            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:246:33: ( '+' | '-' )?
                            int alt12=2;
                            int LA12_0 = input.LA(1);

                            if ( (LA12_0=='+'||LA12_0=='-') ) {
                                alt12=1;
                            }
                            switch (alt12) {
                                case 1 :
                                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:
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

                            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:246:46: ( '0' .. '9' )+
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
                            	    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:246:47: '0' .. '9'
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


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:247:4: ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
                    {
                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:247:4: ( '0' .. '9' )+
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
                    	    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:247:5: '0' .. '9'
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

                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:247:16: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:247:17: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
                    {
                    if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:247:29: ( '+' | '-' )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0=='+'||LA16_0=='-') ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:
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

                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:247:42: ( '0' .. '9' )+
                    int cnt17=0;
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( ((LA17_0>='0' && LA17_0<='9')) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:247:43: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt17 >= 1 ) break loop17;
                                EarlyExitException eee =
                                    new EarlyExitException(17, input);
                                throw eee;
                        }
                        cnt17++;
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:248:8: ( ( '-' )? ( '0' .. '9' )+ )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:248:10: ( '-' )? ( '0' .. '9' )+
            {
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:248:10: ( '-' )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0=='-') ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:248:10: '-'
                    {
                    match('-'); 

                    }
                    break;

            }

            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:248:15: ( '0' .. '9' )+
            int cnt20=0;
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>='0' && LA20_0<='9')) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:248:16: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt20 >= 1 ) break loop20;
                        EarlyExitException eee =
                            new EarlyExitException(20, input);
                        throw eee;
                }
                cnt20++;
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:249:7: ( '\\\"' ( . )* '\\\"' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:249:9: '\\\"' ( . )* '\\\"'
            {
            match('\"'); 
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:249:14: ( . )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0=='\"') ) {
                    alt21=2;
                }
                else if ( ((LA21_0>='\u0000' && LA21_0<='!')||(LA21_0>='#' && LA21_0<='\uFFFF')) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:249:14: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop21;
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:251:13: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:251:15: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:251:20: (~ ( '\\n' | '\\r' ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( ((LA22_0>='\u0000' && LA22_0<='\t')||(LA22_0>='\u000B' && LA22_0<='\f')||(LA22_0>='\u000E' && LA22_0<='\uFFFF')) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:251:20: ~ ( '\\n' | '\\r' )
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
            	    break loop22;
                }
            } while (true);

            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:251:34: ( '\\r' )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0=='\r') ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:251:34: '\\r'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:252:19: ( '/*' ( . )* '*/' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:252:21: '/*' ( . )* '*/'
            {
            match("/*"); 

            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:252:26: ( . )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0=='*') ) {
                    int LA24_1 = input.LA(2);

                    if ( (LA24_1=='/') ) {
                        alt24=2;
                    }
                    else if ( ((LA24_1>='\u0000' && LA24_1<='.')||(LA24_1>='0' && LA24_1<='\uFFFF')) ) {
                        alt24=1;
                    }


                }
                else if ( ((LA24_0>='\u0000' && LA24_0<=')')||(LA24_0>='+' && LA24_0<='\uFFFF')) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:252:26: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop24;
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:253:11: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:253:13: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:255:3: ( '=' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:255:5: '='
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:256:3: ( '>=' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:256:5: '>='
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:257:3: ( '>' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:257:5: '>'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:258:3: ( '<=' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:258:5: '<='
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:259:3: ( '<' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:259:5: '<'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:260:3: ( '!=' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:260:5: '!='
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:262:6: ( '->' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:262:8: '->'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:263:6: ( ':' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:263:8: ':'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:264:12: ( ':=' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:264:14: ':='
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:265:6: ( ',' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:265:8: ','
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:268:4: ( '.' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:268:6: '.'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:270:18: ( '-->' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:270:20: '-->'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:271:19: ( '==>' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:271:21: '==>'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:272:11: ( '..' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:272:13: '..'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:274:7: ( '{' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:274:9: '{'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:275:7: ( '}' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:275:9: '}'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:276:9: ( '[' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:276:11: '['
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:277:9: ( ']' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:277:11: ']'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:278:7: ( '(' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:278:9: '('
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:279:7: ( ')' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:279:9: ')'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:281:4: ( '^' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:281:6: '^'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:282:4: ( '&' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:282:6: '&'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:283:3: ( '|' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:283:5: '|'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:284:4: ( '/' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:284:6: '/'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:285:6: ( '-' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:285:8: '-'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:286:5: ( '+' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:286:7: '+'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:287:6: ( '*' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:287:8: '*'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:289:10: ( ';' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:289:12: ';'
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
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:290:6: ( '#' )
            // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:290:8: '#'
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
        // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:8: ( ALL | ACTOR | END | ENTITIES | IMPORT | MULTI | MUTABLE | NETWORK | PACKAGE | STRUCTURE | NOT | VAR | TRUE | FALSE | QID | QID_WILDCARD | FLOAT | INTEGER | STRING | LINE_COMMENT | MULTI_LINE_COMMENT | WHITESPACE | EQ | GE | GT | LE | LT | NE | ARROW | COLON | COLON_EQUAL | COMMA | DOUBLE_DASH_ARROW | DOUBLE_EQUAL_ARROW | DOUBLE_DOT | LBRACE | RBRACE | LBRACKET | RBRACKET | LPAREN | RPAREN | XOR | AND | OR | DIV | MINUS | PLUS | TIMES | SEMICOLON | SHARP )
        int alt25=50;
        alt25 = dfa25.predict(input);
        switch (alt25) {
            case 1 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:10: ALL
                {
                mALL(); 

                }
                break;
            case 2 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:14: ACTOR
                {
                mACTOR(); 

                }
                break;
            case 3 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:20: END
                {
                mEND(); 

                }
                break;
            case 4 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:24: ENTITIES
                {
                mENTITIES(); 

                }
                break;
            case 5 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:33: IMPORT
                {
                mIMPORT(); 

                }
                break;
            case 6 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:40: MULTI
                {
                mMULTI(); 

                }
                break;
            case 7 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:46: MUTABLE
                {
                mMUTABLE(); 

                }
                break;
            case 8 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:54: NETWORK
                {
                mNETWORK(); 

                }
                break;
            case 9 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:62: PACKAGE
                {
                mPACKAGE(); 

                }
                break;
            case 10 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:70: STRUCTURE
                {
                mSTRUCTURE(); 

                }
                break;
            case 11 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:80: NOT
                {
                mNOT(); 

                }
                break;
            case 12 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:84: VAR
                {
                mVAR(); 

                }
                break;
            case 13 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:88: TRUE
                {
                mTRUE(); 

                }
                break;
            case 14 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:93: FALSE
                {
                mFALSE(); 

                }
                break;
            case 15 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:99: QID
                {
                mQID(); 

                }
                break;
            case 16 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:103: QID_WILDCARD
                {
                mQID_WILDCARD(); 

                }
                break;
            case 17 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:116: FLOAT
                {
                mFLOAT(); 

                }
                break;
            case 18 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:122: INTEGER
                {
                mINTEGER(); 

                }
                break;
            case 19 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:130: STRING
                {
                mSTRING(); 

                }
                break;
            case 20 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:137: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;
            case 21 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:150: MULTI_LINE_COMMENT
                {
                mMULTI_LINE_COMMENT(); 

                }
                break;
            case 22 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:169: WHITESPACE
                {
                mWHITESPACE(); 

                }
                break;
            case 23 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:180: EQ
                {
                mEQ(); 

                }
                break;
            case 24 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:183: GE
                {
                mGE(); 

                }
                break;
            case 25 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:186: GT
                {
                mGT(); 

                }
                break;
            case 26 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:189: LE
                {
                mLE(); 

                }
                break;
            case 27 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:192: LT
                {
                mLT(); 

                }
                break;
            case 28 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:195: NE
                {
                mNE(); 

                }
                break;
            case 29 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:198: ARROW
                {
                mARROW(); 

                }
                break;
            case 30 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:204: COLON
                {
                mCOLON(); 

                }
                break;
            case 31 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:210: COLON_EQUAL
                {
                mCOLON_EQUAL(); 

                }
                break;
            case 32 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:222: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 33 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:228: DOUBLE_DASH_ARROW
                {
                mDOUBLE_DASH_ARROW(); 

                }
                break;
            case 34 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:246: DOUBLE_EQUAL_ARROW
                {
                mDOUBLE_EQUAL_ARROW(); 

                }
                break;
            case 35 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:265: DOUBLE_DOT
                {
                mDOUBLE_DOT(); 

                }
                break;
            case 36 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:276: LBRACE
                {
                mLBRACE(); 

                }
                break;
            case 37 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:283: RBRACE
                {
                mRBRACE(); 

                }
                break;
            case 38 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:290: LBRACKET
                {
                mLBRACKET(); 

                }
                break;
            case 39 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:299: RBRACKET
                {
                mRBRACKET(); 

                }
                break;
            case 40 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:308: LPAREN
                {
                mLPAREN(); 

                }
                break;
            case 41 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:315: RPAREN
                {
                mRPAREN(); 

                }
                break;
            case 42 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:322: XOR
                {
                mXOR(); 

                }
                break;
            case 43 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:326: AND
                {
                mAND(); 

                }
                break;
            case 44 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:330: OR
                {
                mOR(); 

                }
                break;
            case 45 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:333: DIV
                {
                mDIV(); 

                }
                break;
            case 46 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:337: MINUS
                {
                mMINUS(); 

                }
                break;
            case 47 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:343: PLUS
                {
                mPLUS(); 

                }
                break;
            case 48 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:348: TIMES
                {
                mTIMES(); 

                }
                break;
            case 49 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:354: SEMICOLON
                {
                mSEMICOLON(); 

                }
                break;
            case 50 :
                // C:\\Work\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:1:364: SHARP
                {
                mSHARP(); 

                }
                break;

        }

    }


    protected DFA18 dfa18 = new DFA18(this);
    protected DFA25 dfa25 = new DFA25(this);
    static final String DFA18_eotS =
        "\5\uffff";
    static final String DFA18_eofS =
        "\5\uffff";
    static final String DFA18_minS =
        "\2\56\3\uffff";
    static final String DFA18_maxS =
        "\1\71\1\145\3\uffff";
    static final String DFA18_acceptS =
        "\2\uffff\1\2\1\1\1\3";
    static final String DFA18_specialS =
        "\5\uffff}>";
    static final String[] DFA18_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\3\1\uffff\12\1\13\uffff\1\4\37\uffff\1\4",
            "",
            "",
            ""
    };

    static final short[] DFA18_eot = DFA.unpackEncodedString(DFA18_eotS);
    static final short[] DFA18_eof = DFA.unpackEncodedString(DFA18_eofS);
    static final char[] DFA18_min = DFA.unpackEncodedStringToUnsignedChars(DFA18_minS);
    static final char[] DFA18_max = DFA.unpackEncodedStringToUnsignedChars(DFA18_maxS);
    static final short[] DFA18_accept = DFA.unpackEncodedString(DFA18_acceptS);
    static final short[] DFA18_special = DFA.unpackEncodedString(DFA18_specialS);
    static final short[][] DFA18_transition;

    static {
        int numStates = DFA18_transitionS.length;
        DFA18_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA18_transition[i] = DFA.unpackEncodedString(DFA18_transitionS[i]);
        }
    }

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = DFA18_eot;
            this.eof = DFA18_eof;
            this.min = DFA18_min;
            this.max = DFA18_max;
            this.accept = DFA18_accept;
            this.special = DFA18_special;
            this.transition = DFA18_transition;
        }
        public String getDescription() {
            return "245:13: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )? | '.' ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )? | ( '0' .. '9' )+ ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ ) )";
        }
    }
    static final String DFA25_eotS =
        "\1\uffff\13\50\1\67\1\70\2\uffff\1\74\1\uffff\1\76\1\100\1\102"+
        "\1\uffff\1\104\16\uffff\3\50\2\uffff\12\50\21\uffff\1\125\1\50\1"+
        "\uffff\1\50\1\130\5\50\1\136\2\50\1\141\2\50\1\uffff\2\50\1\uffff"+
        "\5\50\1\uffff\2\50\1\uffff\1\154\1\50\1\156\2\50\1\161\4\50\1\uffff"+
        "\1\166\1\uffff\1\50\1\170\1\uffff\4\50\1\uffff\1\50\1\uffff\1\176"+
        "\1\177\1\u0080\1\50\1\u0082\3\uffff\1\50\1\uffff\1\u0084\1\uffff";
    static final String DFA25_eofS =
        "\u0085\uffff";
    static final String DFA25_minS =
        "\1\11\13\44\1\55\2\56\1\uffff\1\52\1\uffff\3\75\1\uffff\1\75\16"+
        "\uffff\3\44\1\uffff\13\44\21\uffff\2\44\1\uffff\15\44\1\uffff\2"+
        "\44\1\uffff\5\44\1\uffff\2\44\1\uffff\12\44\1\uffff\1\44\1\uffff"+
        "\2\44\1\uffff\4\44\1\uffff\1\44\1\uffff\5\44\3\uffff\1\44\1\uffff"+
        "\1\44\1\uffff";
    static final String DFA25_maxS =
        "\1\175\13\172\1\76\1\145\1\71\1\uffff\1\57\1\uffff\3\75\1\uffff"+
        "\1\75\16\uffff\3\172\1\uffff\13\172\21\uffff\2\172\1\uffff\15\172"+
        "\1\uffff\2\172\1\uffff\5\172\1\uffff\2\172\1\uffff\12\172\1\uffff"+
        "\1\172\1\uffff\2\172\1\uffff\4\172\1\uffff\1\172\1\uffff\5\172\3"+
        "\uffff\1\172\1\uffff\1\172\1\uffff";
    static final String DFA25_acceptS =
        "\17\uffff\1\23\1\uffff\1\26\3\uffff\1\34\1\uffff\1\40\1\44\1\45"+
        "\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\57\1\60\1\61\1\62\3\uffff"+
        "\1\17\13\uffff\1\35\1\41\1\21\1\56\1\22\1\43\1\24\1\25\1\55\1\42"+
        "\1\27\1\30\1\31\1\32\1\33\1\37\1\36\2\uffff\1\20\15\uffff\1\1\2"+
        "\uffff\1\3\5\uffff\1\13\2\uffff\1\14\12\uffff\1\15\1\uffff\1\2\2"+
        "\uffff\1\6\4\uffff\1\16\1\uffff\1\5\5\uffff\1\7\1\10\1\11\1\uffff"+
        "\1\4\1\uffff\1\12";
    static final String DFA25_specialS =
        "\u0085\uffff}>";
    static final String[] DFA25_transitionS = {
            "\2\21\1\uffff\2\21\22\uffff\1\21\1\25\1\17\1\44\1\13\1\uffff"+
            "\1\37\1\uffff\1\34\1\35\1\42\1\41\1\27\1\14\1\16\1\20\12\15"+
            "\1\26\1\43\1\24\1\22\1\23\2\uffff\32\13\1\32\1\uffff\1\33\1"+
            "\36\1\13\1\uffff\1\1\3\13\1\2\1\12\2\13\1\3\3\13\1\4\1\5\1\13"+
            "\1\6\2\13\1\7\1\11\1\13\1\10\4\13\1\30\1\40\1\31",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\2\47\1\46\10\47\1\45\16\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\15\47\1\52\14\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\14\47\1\53\15\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\24\47\1\54\5\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\4\47\1\55\11\47\1\56\13\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\1\57\31\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\23\47\1\60\6\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\1\61\31\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\21\47\1\62\10\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\1\63\31\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\32\47",
            "\1\65\1\66\1\uffff\12\15\4\uffff\1\64",
            "\1\66\1\uffff\12\15\13\uffff\1\66\37\uffff\1\66",
            "\1\71\1\uffff\12\66",
            "",
            "\1\73\4\uffff\1\72",
            "",
            "\1\75",
            "\1\77",
            "\1\101",
            "",
            "\1\103",
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
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\13\47\1\105\16\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\23\47\1\106\6\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\32\47",
            "",
            "\1\110\5\uffff\1\107\26\uffff\32\110\4\uffff\1\110\1\uffff"+
            "\32\110",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\3\47\1\111\17\47\1\112\6\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\17\47\1\113\12\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\13\47\1\114\7\47\1\115\6\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\23\47\1\116\6\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\23\47\1\117\6\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\2\47\1\120\27\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\21\47\1\121\10\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\21\47\1\122\10\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\24\47\1\123\5\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\13\47\1\124\16\47",
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
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\32\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\16\47\1\126\13\47",
            "",
            "\1\127\11\uffff\1\51\1\uffff\12\127\7\uffff\32\127\4\uffff"+
            "\1\127\1\uffff\32\127",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\32\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\10\47\1\131\21\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\16\47\1\132\13\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\23\47\1\133\6\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\1\134\31\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\26\47\1\135\3\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\32\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\12\47\1\137\17\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\24\47\1\140\5\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\32\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\4\47\1\142\25\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\22\47\1\143\7\47",
            "",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\21\47\1\144\10\47",
            "\1\127\11\uffff\1\51\1\uffff\12\127\7\uffff\32\127\4\uffff"+
            "\1\127\1\uffff\32\127",
            "",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\23\47\1\145\6\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\21\47\1\146\10\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\10\47\1\147\21\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\1\47\1\150\30\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\16\47\1\151\13\47",
            "",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\1\152\31\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\2\47\1\153\27\47",
            "",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\32\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\4\47\1\155\25\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\32\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\10\47\1\157\21\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\23\47\1\160\6\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\32\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\13\47\1\162\16\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\21\47\1\163\10\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\6\47\1\164\23\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\23\47\1\165\6\47",
            "",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\32\47",
            "",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\4\47\1\167\25\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\32\47",
            "",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\4\47\1\171\25\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\12\47\1\172\17\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\4\47\1\173\25\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\24\47\1\174\5\47",
            "",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\22\47\1\175\7\47",
            "",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\32\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\32\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\32\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\21\47\1\u0081\10\47",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\32\47",
            "",
            "",
            "",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\4\47\1\u0083\25\47",
            "",
            "\1\47\11\uffff\1\51\1\uffff\12\47\7\uffff\32\47\4\uffff\1"+
            "\47\1\uffff\32\47",
            ""
    };

    static final short[] DFA25_eot = DFA.unpackEncodedString(DFA25_eotS);
    static final short[] DFA25_eof = DFA.unpackEncodedString(DFA25_eofS);
    static final char[] DFA25_min = DFA.unpackEncodedStringToUnsignedChars(DFA25_minS);
    static final char[] DFA25_max = DFA.unpackEncodedStringToUnsignedChars(DFA25_maxS);
    static final short[] DFA25_accept = DFA.unpackEncodedString(DFA25_acceptS);
    static final short[] DFA25_special = DFA.unpackEncodedString(DFA25_specialS);
    static final short[][] DFA25_transition;

    static {
        int numStates = DFA25_transitionS.length;
        DFA25_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA25_transition[i] = DFA.unpackEncodedString(DFA25_transitionS[i]);
        }
    }

    class DFA25 extends DFA {

        public DFA25(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 25;
            this.eot = DFA25_eot;
            this.eof = DFA25_eof;
            this.min = DFA25_min;
            this.max = DFA25_max;
            this.accept = DFA25_accept;
            this.special = DFA25_special;
            this.transition = DFA25_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( ALL | ACTOR | END | ENTITIES | IMPORT | MULTI | MUTABLE | NETWORK | PACKAGE | STRUCTURE | NOT | VAR | TRUE | FALSE | QID | QID_WILDCARD | FLOAT | INTEGER | STRING | LINE_COMMENT | MULTI_LINE_COMMENT | WHITESPACE | EQ | GE | GT | LE | LT | NE | ARROW | COLON | COLON_EQUAL | COMMA | DOUBLE_DASH_ARROW | DOUBLE_EQUAL_ARROW | DOUBLE_DOT | LBRACE | RBRACE | LBRACKET | RBRACKET | LPAREN | RPAREN | XOR | AND | OR | DIV | MINUS | PLUS | TIMES | SEMICOLON | SHARP );";
        }
    }
 

}