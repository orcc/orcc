// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g 2009-11-30 13:50:02

package net.sf.orcc.frontend.parser.internal;

// @SuppressWarnings("unused")


import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.RewriteEarlyExitException;
import org.antlr.runtime.tree.RewriteRuleSubtreeStream;
import org.antlr.runtime.tree.RewriteRuleTokenStream;
import org.antlr.runtime.tree.TreeAdaptor;

@SuppressWarnings("unused")
public class RVCCalParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "INPUT", "INPUTS", "OUTPUT", "OUTPUTS", "PORT", "PARAMETERS", "REPEAT", "STATEMENTS", "VARIABLE", "VARIABLES", "ACTOR_DECLS", "STATE_VAR", "TRANSITION", "TRANSITIONS", "INEQUALITY", "GUARDS", "TAG", "EXPR", "EXPR_BINARY", "EXPR_UNARY", "OP", "EXPR_LIST", "EXPR_IF", "EXPR_CALL", "EXPR_IDX", "EXPR_VAR", "EXPR_BOOL", "EXPR_FLOAT", "EXPR_INT", "EXPR_STRING", "TYPE", "TYPE_ATTRS", "TYPE_LIST", "ASSIGNABLE", "NON_ASSIGNABLE", "LOGIC_OR", "LOGIC_AND", "BITOR", "BITXOR", "BITAND", "EQ", "NE", "LT", "GT", "LE", "GE", "SHIFT_LEFT", "SHIFT_RIGHT", "DIV_INT", "MOD", "EXP", "BITNOT", "LOGIC_NOT", "NUM_ELTS", "ASSIGN", "CALL", "EXPRESSIONS", "ACTION", "ACTOR", "FUNCTION", "GUARD", "INITIALIZE", "PRIORITY", "PROCEDURE", "SCHEDULE", "PLUS", "MINUS", "TIMES", "DIV", "LETTER", "ID", "Exponent", "FLOAT", "Decimal", "HexDigit", "Hexadecimal", "INTEGER", "EscapeSequence", "STRING", "OctalEscape", "LINE_COMMENT", "MULTI_LINE_COMMENT", "WHITESPACE", "':'", "'['", "']'", "','", "'do'", "'('", "')'", "'==>'", "'end'", "'.'", "'var'", "'='", "':='", "';'", "'-->'", "'begin'", "'import'", "'all'", "'or'", "'||'", "'and'", "'&&'", "'|'", "'&'", "'!='", "'<'", "'>'", "'<='", "'>='", "'<<'", "'>>'", "'div'", "'mod'", "'^'", "'not'", "'#'", "'if'", "'then'", "'else'", "'true'", "'false'", "'for'", "'in'", "'fsm'", "'foreach'", "'..'", "'while'"
    };
    public static final int FUNCTION=63;
    public static final int EXPR_BOOL=30;
    public static final int BITNOT=55;
    public static final int LT=46;
    public static final int OUTPUTS=7;
    public static final int TRANSITION=16;
    public static final int EXPR_VAR=29;
    public static final int LOGIC_NOT=56;
    public static final int LETTER=73;
    public static final int MOD=53;
    public static final int EXPR_CALL=27;
    public static final int Decimal=77;
    public static final int INPUTS=5;
    public static final int EXPR_UNARY=23;
    public static final int EOF=-1;
    public static final int ACTION=61;
    public static final int TYPE=34;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int TYPE_ATTRS=35;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__90=90;
    public static final int EXP=54;
    public static final int STATE_VAR=15;
    public static final int GUARDS=19;
    public static final int EQ=44;
    public static final int T__99=99;
    public static final int T__98=98;
    public static final int T__97=97;
    public static final int T__96=96;
    public static final int NE=45;
    public static final int T__95=95;
    public static final int ASSIGNABLE=37;
    public static final int GE=49;
    public static final int Hexadecimal=79;
    public static final int INITIALIZE=65;
    public static final int LINE_COMMENT=84;
    public static final int DIV_INT=52;
    public static final int LOGIC_OR=39;
    public static final int WHITESPACE=86;
    public static final int INEQUALITY=18;
    public static final int NON_ASSIGNABLE=38;
    public static final int EXPRESSIONS=60;
    public static final int EXPR_IDX=28;
    public static final int T__87=87;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int T__126=126;
    public static final int SHIFT_LEFT=50;
    public static final int T__125=125;
    public static final int T__128=128;
    public static final int T__127=127;
    public static final int SHIFT_RIGHT=51;
    public static final int T__129=129;
    public static final int BITOR=41;
    public static final int PRIORITY=66;
    public static final int VARIABLE=12;
    public static final int ACTOR_DECLS=14;
    public static final int OP=24;
    public static final int ACTOR=62;
    public static final int GT=47;
    public static final int STATEMENTS=11;
    public static final int REPEAT=10;
    public static final int GUARD=64;
    public static final int CALL=59;
    public static final int T__130=130;
    public static final int EscapeSequence=81;
    public static final int T__131=131;
    public static final int OUTPUT=6;
    public static final int T__132=132;
    public static final int T__133=133;
    public static final int PARAMETERS=9;
    public static final int EXPR_BINARY=22;
    public static final int T__118=118;
    public static final int SCHEDULE=68;
    public static final int T__119=119;
    public static final int T__116=116;
    public static final int T__117=117;
    public static final int T__114=114;
    public static final int T__115=115;
    public static final int T__124=124;
    public static final int INPUT=4;
    public static final int T__123=123;
    public static final int Exponent=75;
    public static final int T__122=122;
    public static final int T__121=121;
    public static final int FLOAT=76;
    public static final int T__120=120;
    public static final int TYPE_LIST=36;
    public static final int EXPR_FLOAT=31;
    public static final int LOGIC_AND=40;
    public static final int ID=74;
    public static final int HexDigit=78;
    public static final int BITAND=43;
    public static final int EXPR_LIST=25;
    public static final int EXPR=21;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int T__109=109;
    public static final int EXPR_STRING=33;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int T__105=105;
    public static final int BITXOR=42;
    public static final int T__106=106;
    public static final int NUM_ELTS=57;
    public static final int T__111=111;
    public static final int T__110=110;
    public static final int T__113=113;
    public static final int PLUS=69;
    public static final int T__112=112;
    public static final int EXPR_INT=32;
    public static final int INTEGER=80;
    public static final int TRANSITIONS=17;
    public static final int PORT=8;
    public static final int T__102=102;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int MINUS=70;
    public static final int EXPR_IF=26;
    public static final int MULTI_LINE_COMMENT=85;
    public static final int PROCEDURE=67;
    public static final int TAG=20;
    public static final int ASSIGN=58;
    public static final int VARIABLES=13;
    public static final int DIV=72;
    public static final int TIMES=71;
    public static final int OctalEscape=83;
    public static final int LE=48;
    public static final int STRING=82;

    // delegates
    // delegators


        public RVCCalParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public RVCCalParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return RVCCalParser.tokenNames; }
    public String getGrammarFileName() { return "D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g"; }


    public static class actionGuards_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionGuards"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:51:1: actionGuards : GUARD expressions -> expressions ;
    public final RVCCalParser.actionGuards_return actionGuards() throws RecognitionException {
        RVCCalParser.actionGuards_return retval = new RVCCalParser.actionGuards_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token GUARD1=null;
        RVCCalParser.expressions_return expressions2 = null;


        Object GUARD1_tree=null;
        RewriteRuleTokenStream stream_GUARD=new RewriteRuleTokenStream(adaptor,"token GUARD");
        RewriteRuleSubtreeStream stream_expressions=new RewriteRuleSubtreeStream(adaptor,"rule expressions");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:51:13: ( GUARD expressions -> expressions )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:51:15: GUARD expressions
            {
            GUARD1=(Token)match(input,GUARD,FOLLOW_GUARD_in_actionGuards61);  
            stream_GUARD.add(GUARD1);

            pushFollow(FOLLOW_expressions_in_actionGuards63);
            expressions2=expressions();

            state._fsp--;

            stream_expressions.add(expressions2.getTree());


            // AST REWRITE
            // elements: expressions
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 51:33: -> expressions
            {
                adaptor.addChild(root_0, stream_expressions.nextTree());

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "actionGuards"

    public static class actionInput_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionInput"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:53:1: actionInput : ( ID ':' )? '[' idents ']' ( actionRepeat )? -> ^( INPUT ^( PORT ( ID )? ) ^( INPUTS idents ) ^( REPEAT ( actionRepeat )? ) ) ;
    public final RVCCalParser.actionInput_return actionInput() throws RecognitionException {
        RVCCalParser.actionInput_return retval = new RVCCalParser.actionInput_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID3=null;
        Token char_literal4=null;
        Token char_literal5=null;
        Token char_literal7=null;
        RVCCalParser.idents_return idents6 = null;

        RVCCalParser.actionRepeat_return actionRepeat8 = null;


        Object ID3_tree=null;
        Object char_literal4_tree=null;
        Object char_literal5_tree=null;
        Object char_literal7_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleTokenStream stream_88=new RewriteRuleTokenStream(adaptor,"token 88");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleSubtreeStream stream_actionRepeat=new RewriteRuleSubtreeStream(adaptor,"rule actionRepeat");
        RewriteRuleSubtreeStream stream_idents=new RewriteRuleSubtreeStream(adaptor,"rule idents");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:53:12: ( ( ID ':' )? '[' idents ']' ( actionRepeat )? -> ^( INPUT ^( PORT ( ID )? ) ^( INPUTS idents ) ^( REPEAT ( actionRepeat )? ) ) )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:54:2: ( ID ':' )? '[' idents ']' ( actionRepeat )?
            {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:54:2: ( ID ':' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==ID) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:54:3: ID ':'
                    {
                    ID3=(Token)match(input,ID,FOLLOW_ID_in_actionInput76);  
                    stream_ID.add(ID3);

                    char_literal4=(Token)match(input,87,FOLLOW_87_in_actionInput78);  
                    stream_87.add(char_literal4);


                    }
                    break;

            }

            char_literal5=(Token)match(input,88,FOLLOW_88_in_actionInput82);  
            stream_88.add(char_literal5);

            pushFollow(FOLLOW_idents_in_actionInput84);
            idents6=idents();

            state._fsp--;

            stream_idents.add(idents6.getTree());
            char_literal7=(Token)match(input,89,FOLLOW_89_in_actionInput86);  
            stream_89.add(char_literal7);

            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:54:27: ( actionRepeat )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==REPEAT) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:54:27: actionRepeat
                    {
                    pushFollow(FOLLOW_actionRepeat_in_actionInput88);
                    actionRepeat8=actionRepeat();

                    state._fsp--;

                    stream_actionRepeat.add(actionRepeat8.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: actionRepeat, idents, ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 55:2: -> ^( INPUT ^( PORT ( ID )? ) ^( INPUTS idents ) ^( REPEAT ( actionRepeat )? ) )
            {
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:55:5: ^( INPUT ^( PORT ( ID )? ) ^( INPUTS idents ) ^( REPEAT ( actionRepeat )? ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUT, "INPUT"), root_1);

                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:55:13: ^( PORT ( ID )? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PORT, "PORT"), root_2);

                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:55:20: ( ID )?
                if ( stream_ID.hasNext() ) {
                    adaptor.addChild(root_2, stream_ID.nextNode());

                }
                stream_ID.reset();

                adaptor.addChild(root_1, root_2);
                }
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:55:25: ^( INPUTS idents )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_2);

                adaptor.addChild(root_2, stream_idents.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:55:42: ^( REPEAT ( actionRepeat )? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(REPEAT, "REPEAT"), root_2);

                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:55:51: ( actionRepeat )?
                if ( stream_actionRepeat.hasNext() ) {
                    adaptor.addChild(root_2, stream_actionRepeat.nextTree());

                }
                stream_actionRepeat.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "actionInput"

    public static class actionInputs_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionInputs"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:57:1: actionInputs : actionInput ( ',' actionInput )* -> ( actionInput )+ ;
    public final RVCCalParser.actionInputs_return actionInputs() throws RecognitionException {
        RVCCalParser.actionInputs_return retval = new RVCCalParser.actionInputs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal10=null;
        RVCCalParser.actionInput_return actionInput9 = null;

        RVCCalParser.actionInput_return actionInput11 = null;


        Object char_literal10_tree=null;
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleSubtreeStream stream_actionInput=new RewriteRuleSubtreeStream(adaptor,"rule actionInput");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:57:13: ( actionInput ( ',' actionInput )* -> ( actionInput )+ )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:57:15: actionInput ( ',' actionInput )*
            {
            pushFollow(FOLLOW_actionInput_in_actionInputs123);
            actionInput9=actionInput();

            state._fsp--;

            stream_actionInput.add(actionInput9.getTree());
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:57:27: ( ',' actionInput )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==90) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:57:28: ',' actionInput
            	    {
            	    char_literal10=(Token)match(input,90,FOLLOW_90_in_actionInputs126);  
            	    stream_90.add(char_literal10);

            	    pushFollow(FOLLOW_actionInput_in_actionInputs128);
            	    actionInput11=actionInput();

            	    state._fsp--;

            	    stream_actionInput.add(actionInput11.getTree());

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);



            // AST REWRITE
            // elements: actionInput
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 57:46: -> ( actionInput )+
            {
                if ( !(stream_actionInput.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_actionInput.hasNext() ) {
                    adaptor.addChild(root_0, stream_actionInput.nextTree());

                }
                stream_actionInput.reset();

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "actionInputs"

    public static class actionOutput_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionOutput"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:59:1: actionOutput : ( ID ':' )? '[' expressions ']' ( actionRepeat )? -> ^( OUTPUT ^( PORT ( ID )? ) ^( OUTPUTS expressions ) ^( REPEAT ( actionRepeat )? ) ) ;
    public final RVCCalParser.actionOutput_return actionOutput() throws RecognitionException {
        RVCCalParser.actionOutput_return retval = new RVCCalParser.actionOutput_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID12=null;
        Token char_literal13=null;
        Token char_literal14=null;
        Token char_literal16=null;
        RVCCalParser.expressions_return expressions15 = null;

        RVCCalParser.actionRepeat_return actionRepeat17 = null;


        Object ID12_tree=null;
        Object char_literal13_tree=null;
        Object char_literal14_tree=null;
        Object char_literal16_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleTokenStream stream_88=new RewriteRuleTokenStream(adaptor,"token 88");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleSubtreeStream stream_actionRepeat=new RewriteRuleSubtreeStream(adaptor,"rule actionRepeat");
        RewriteRuleSubtreeStream stream_expressions=new RewriteRuleSubtreeStream(adaptor,"rule expressions");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:59:13: ( ( ID ':' )? '[' expressions ']' ( actionRepeat )? -> ^( OUTPUT ^( PORT ( ID )? ) ^( OUTPUTS expressions ) ^( REPEAT ( actionRepeat )? ) ) )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:60:2: ( ID ':' )? '[' expressions ']' ( actionRepeat )?
            {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:60:2: ( ID ':' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==ID) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:60:3: ID ':'
                    {
                    ID12=(Token)match(input,ID,FOLLOW_ID_in_actionOutput144);  
                    stream_ID.add(ID12);

                    char_literal13=(Token)match(input,87,FOLLOW_87_in_actionOutput146);  
                    stream_87.add(char_literal13);


                    }
                    break;

            }

            char_literal14=(Token)match(input,88,FOLLOW_88_in_actionOutput150);  
            stream_88.add(char_literal14);

            pushFollow(FOLLOW_expressions_in_actionOutput152);
            expressions15=expressions();

            state._fsp--;

            stream_expressions.add(expressions15.getTree());
            char_literal16=(Token)match(input,89,FOLLOW_89_in_actionOutput154);  
            stream_89.add(char_literal16);

            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:60:32: ( actionRepeat )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==REPEAT) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:60:32: actionRepeat
                    {
                    pushFollow(FOLLOW_actionRepeat_in_actionOutput156);
                    actionRepeat17=actionRepeat();

                    state._fsp--;

                    stream_actionRepeat.add(actionRepeat17.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: actionRepeat, ID, expressions
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 61:2: -> ^( OUTPUT ^( PORT ( ID )? ) ^( OUTPUTS expressions ) ^( REPEAT ( actionRepeat )? ) )
            {
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:61:5: ^( OUTPUT ^( PORT ( ID )? ) ^( OUTPUTS expressions ) ^( REPEAT ( actionRepeat )? ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUT, "OUTPUT"), root_1);

                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:61:14: ^( PORT ( ID )? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PORT, "PORT"), root_2);

                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:61:21: ( ID )?
                if ( stream_ID.hasNext() ) {
                    adaptor.addChild(root_2, stream_ID.nextNode());

                }
                stream_ID.reset();

                adaptor.addChild(root_1, root_2);
                }
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:61:26: ^( OUTPUTS expressions )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                adaptor.addChild(root_2, stream_expressions.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:61:49: ^( REPEAT ( actionRepeat )? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(REPEAT, "REPEAT"), root_2);

                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:61:58: ( actionRepeat )?
                if ( stream_actionRepeat.hasNext() ) {
                    adaptor.addChild(root_2, stream_actionRepeat.nextTree());

                }
                stream_actionRepeat.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "actionOutput"

    public static class actionOutputs_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionOutputs"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:63:1: actionOutputs : actionOutput ( ',' actionOutput )* -> ( actionOutput )+ ;
    public final RVCCalParser.actionOutputs_return actionOutputs() throws RecognitionException {
        RVCCalParser.actionOutputs_return retval = new RVCCalParser.actionOutputs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal19=null;
        RVCCalParser.actionOutput_return actionOutput18 = null;

        RVCCalParser.actionOutput_return actionOutput20 = null;


        Object char_literal19_tree=null;
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleSubtreeStream stream_actionOutput=new RewriteRuleSubtreeStream(adaptor,"rule actionOutput");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:63:14: ( actionOutput ( ',' actionOutput )* -> ( actionOutput )+ )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:63:16: actionOutput ( ',' actionOutput )*
            {
            pushFollow(FOLLOW_actionOutput_in_actionOutputs191);
            actionOutput18=actionOutput();

            state._fsp--;

            stream_actionOutput.add(actionOutput18.getTree());
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:63:29: ( ',' actionOutput )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==90) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:63:30: ',' actionOutput
            	    {
            	    char_literal19=(Token)match(input,90,FOLLOW_90_in_actionOutputs194);  
            	    stream_90.add(char_literal19);

            	    pushFollow(FOLLOW_actionOutput_in_actionOutputs196);
            	    actionOutput20=actionOutput();

            	    state._fsp--;

            	    stream_actionOutput.add(actionOutput20.getTree());

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);



            // AST REWRITE
            // elements: actionOutput
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 63:49: -> ( actionOutput )+
            {
                if ( !(stream_actionOutput.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_actionOutput.hasNext() ) {
                    adaptor.addChild(root_0, stream_actionOutput.nextTree());

                }
                stream_actionOutput.reset();

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "actionOutputs"

    public static class actionRepeat_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionRepeat"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:65:1: actionRepeat : REPEAT expression -> expression ;
    public final RVCCalParser.actionRepeat_return actionRepeat() throws RecognitionException {
        RVCCalParser.actionRepeat_return retval = new RVCCalParser.actionRepeat_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token REPEAT21=null;
        RVCCalParser.expression_return expression22 = null;


        Object REPEAT21_tree=null;
        RewriteRuleTokenStream stream_REPEAT=new RewriteRuleTokenStream(adaptor,"token REPEAT");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:65:13: ( REPEAT expression -> expression )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:65:15: REPEAT expression
            {
            REPEAT21=(Token)match(input,REPEAT,FOLLOW_REPEAT_in_actionRepeat210);  
            stream_REPEAT.add(REPEAT21);

            pushFollow(FOLLOW_expression_in_actionRepeat212);
            expression22=expression();

            state._fsp--;

            stream_expression.add(expression22.getTree());


            // AST REWRITE
            // elements: expression
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 65:33: -> expression
            {
                adaptor.addChild(root_0, stream_expression.nextTree());

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "actionRepeat"

    public static class actionStatements_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionStatements"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:67:1: actionStatements : 'do' ( statement )* -> ( statement )* ;
    public final RVCCalParser.actionStatements_return actionStatements() throws RecognitionException {
        RVCCalParser.actionStatements_return retval = new RVCCalParser.actionStatements_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal23=null;
        RVCCalParser.statement_return statement24 = null;


        Object string_literal23_tree=null;
        RewriteRuleTokenStream stream_91=new RewriteRuleTokenStream(adaptor,"token 91");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:67:17: ( 'do' ( statement )* -> ( statement )* )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:67:19: 'do' ( statement )*
            {
            string_literal23=(Token)match(input,91,FOLLOW_91_in_actionStatements223);  
            stream_91.add(string_literal23);

            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:67:24: ( statement )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==ID||LA7_0==102||LA7_0==123||LA7_0==131||LA7_0==133) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:67:24: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_actionStatements225);
            	    statement24=statement();

            	    state._fsp--;

            	    stream_statement.add(statement24.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);



            // AST REWRITE
            // elements: statement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 67:35: -> ( statement )*
            {
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:67:38: ( statement )*
                while ( stream_statement.hasNext() ) {
                    adaptor.addChild(root_0, stream_statement.nextTree());

                }
                stream_statement.reset();

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "actionStatements"

    public static class actor_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actor"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:69:1: actor : ( actorImport )* ACTOR ID ( '[' ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' ( actorDeclarations )? 'end' EOF -> ACTOR ID ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS ( actorDeclarations )? ) ;
    public final RVCCalParser.actor_return actor() throws RecognitionException {
        RVCCalParser.actor_return retval = new RVCCalParser.actor_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ACTOR26=null;
        Token ID27=null;
        Token char_literal28=null;
        Token char_literal29=null;
        Token char_literal30=null;
        Token char_literal32=null;
        Token string_literal33=null;
        Token char_literal34=null;
        Token string_literal36=null;
        Token EOF37=null;
        RVCCalParser.actorPortDecls_return inputs = null;

        RVCCalParser.actorPortDecls_return outputs = null;

        RVCCalParser.actorImport_return actorImport25 = null;

        RVCCalParser.actorParameters_return actorParameters31 = null;

        RVCCalParser.actorDeclarations_return actorDeclarations35 = null;


        Object ACTOR26_tree=null;
        Object ID27_tree=null;
        Object char_literal28_tree=null;
        Object char_literal29_tree=null;
        Object char_literal30_tree=null;
        Object char_literal32_tree=null;
        Object string_literal33_tree=null;
        Object char_literal34_tree=null;
        Object string_literal36_tree=null;
        Object EOF37_tree=null;
        RewriteRuleTokenStream stream_95=new RewriteRuleTokenStream(adaptor,"token 95");
        RewriteRuleTokenStream stream_94=new RewriteRuleTokenStream(adaptor,"token 94");
        RewriteRuleTokenStream stream_93=new RewriteRuleTokenStream(adaptor,"token 93");
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleTokenStream stream_ACTOR=new RewriteRuleTokenStream(adaptor,"token ACTOR");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleTokenStream stream_88=new RewriteRuleTokenStream(adaptor,"token 88");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleSubtreeStream stream_actorImport=new RewriteRuleSubtreeStream(adaptor,"rule actorImport");
        RewriteRuleSubtreeStream stream_actorDeclarations=new RewriteRuleSubtreeStream(adaptor,"rule actorDeclarations");
        RewriteRuleSubtreeStream stream_actorPortDecls=new RewriteRuleSubtreeStream(adaptor,"rule actorPortDecls");
        RewriteRuleSubtreeStream stream_actorParameters=new RewriteRuleSubtreeStream(adaptor,"rule actorParameters");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:72:6: ( ( actorImport )* ACTOR ID ( '[' ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' ( actorDeclarations )? 'end' EOF -> ACTOR ID ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS ( actorDeclarations )? ) )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:72:8: ( actorImport )* ACTOR ID ( '[' ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' ( actorDeclarations )? 'end' EOF
            {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:72:8: ( actorImport )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==103) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:72:8: actorImport
            	    {
            	    pushFollow(FOLLOW_actorImport_in_actor243);
            	    actorImport25=actorImport();

            	    state._fsp--;

            	    stream_actorImport.add(actorImport25.getTree());

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            ACTOR26=(Token)match(input,ACTOR,FOLLOW_ACTOR_in_actor246);  
            stream_ACTOR.add(ACTOR26);

            ID27=(Token)match(input,ID,FOLLOW_ID_in_actor248);  
            stream_ID.add(ID27);

            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:72:30: ( '[' ']' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==88) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:72:31: '[' ']'
                    {
                    char_literal28=(Token)match(input,88,FOLLOW_88_in_actor251);  
                    stream_88.add(char_literal28);

                    char_literal29=(Token)match(input,89,FOLLOW_89_in_actor253);  
                    stream_89.add(char_literal29);


                    }
                    break;

            }

            char_literal30=(Token)match(input,92,FOLLOW_92_in_actor257);  
            stream_92.add(char_literal30);

            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:72:45: ( actorParameters )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==ID) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:72:45: actorParameters
                    {
                    pushFollow(FOLLOW_actorParameters_in_actor259);
                    actorParameters31=actorParameters();

                    state._fsp--;

                    stream_actorParameters.add(actorParameters31.getTree());

                    }
                    break;

            }

            char_literal32=(Token)match(input,93,FOLLOW_93_in_actor262);  
            stream_93.add(char_literal32);

            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:73:8: (inputs= actorPortDecls )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==ID) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:73:8: inputs= actorPortDecls
                    {
                    pushFollow(FOLLOW_actorPortDecls_in_actor267);
                    inputs=actorPortDecls();

                    state._fsp--;

                    stream_actorPortDecls.add(inputs.getTree());

                    }
                    break;

            }

            string_literal33=(Token)match(input,94,FOLLOW_94_in_actor270);  
            stream_94.add(string_literal33);

            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:73:38: (outputs= actorPortDecls )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==ID) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:73:38: outputs= actorPortDecls
                    {
                    pushFollow(FOLLOW_actorPortDecls_in_actor274);
                    outputs=actorPortDecls();

                    state._fsp--;

                    stream_actorPortDecls.add(outputs.getTree());

                    }
                    break;

            }

            char_literal34=(Token)match(input,87,FOLLOW_87_in_actor277);  
            stream_87.add(char_literal34);

            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:74:2: ( actorDeclarations )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==ACTION||LA13_0==FUNCTION||(LA13_0>=INITIALIZE && LA13_0<=SCHEDULE)||LA13_0==ID) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:74:2: actorDeclarations
                    {
                    pushFollow(FOLLOW_actorDeclarations_in_actor280);
                    actorDeclarations35=actorDeclarations();

                    state._fsp--;

                    stream_actorDeclarations.add(actorDeclarations35.getTree());

                    }
                    break;

            }

            string_literal36=(Token)match(input,95,FOLLOW_95_in_actor283);  
            stream_95.add(string_literal36);

            EOF37=(Token)match(input,EOF,FOLLOW_EOF_in_actor285);  
            stream_EOF.add(EOF37);



            // AST REWRITE
            // elements: inputs, actorParameters, actorDeclarations, outputs, ID, ACTOR
            // token labels: 
            // rule labels: retval, inputs, outputs
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_inputs=new RewriteRuleSubtreeStream(adaptor,"rule inputs",inputs!=null?inputs.tree:null);
            RewriteRuleSubtreeStream stream_outputs=new RewriteRuleSubtreeStream(adaptor,"rule outputs",outputs!=null?outputs.tree:null);

            root_0 = (Object)adaptor.nil();
            // 75:2: -> ACTOR ID ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS ( actorDeclarations )? )
            {
                adaptor.addChild(root_0, stream_ACTOR.nextNode());
                adaptor.addChild(root_0, stream_ID.nextNode());
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:76:2: ^( PARAMETERS ( actorParameters )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETERS, "PARAMETERS"), root_1);

                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:76:15: ( actorParameters )?
                if ( stream_actorParameters.hasNext() ) {
                    adaptor.addChild(root_1, stream_actorParameters.nextTree());

                }
                stream_actorParameters.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:77:2: ^( INPUTS ( $inputs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_1);

                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:77:11: ( $inputs)?
                if ( stream_inputs.hasNext() ) {
                    adaptor.addChild(root_1, stream_inputs.nextTree());

                }
                stream_inputs.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:78:2: ^( OUTPUTS ( $outputs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_1);

                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:78:12: ( $outputs)?
                if ( stream_outputs.hasNext() ) {
                    adaptor.addChild(root_1, stream_outputs.nextTree());

                }
                stream_outputs.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:79:2: ^( ACTOR_DECLS ( actorDeclarations )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ACTOR_DECLS, "ACTOR_DECLS"), root_1);

                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:79:16: ( actorDeclarations )?
                if ( stream_actorDeclarations.hasNext() ) {
                    adaptor.addChild(root_1, stream_actorDeclarations.nextTree());

                }
                stream_actorDeclarations.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "actor"

    public static class id_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "id"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:81:1: id : ID ;
    public final RVCCalParser.id_return id() throws RecognitionException {
        RVCCalParser.id_return retval = new RVCCalParser.id_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID38=null;

        Object ID38_tree=null;

        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:87:3: ( ID )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:87:5: ID
            {
            root_0 = (Object)adaptor.nil();

            ID38=(Token)match(input,ID,FOLLOW_ID_in_id341); 
            ID38_tree = (Object)adaptor.create(ID38);
            adaptor.addChild(root_0, ID38_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "id"

    public static class actorDeclaration_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actorDeclaration"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:89:1: actorDeclaration : ( id ( ( ( ( '.' ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName ASSIGNABLE ) ) ';' ) | ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' (outputs= actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | priorityOrder -> priorityOrder | FUNCTION ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end' -> ^( FUNCTION ID ^( PARAMETERS ( varDeclNoExpr )* ) ^( VARIABLES ( varDecls )? ) expression ) | PROCEDURE ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end' -> ^( PROCEDURE ID ^( PARAMETERS ( varDeclNoExpr )* ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( statement )* ) ) );
    public final RVCCalParser.actorDeclaration_return actorDeclaration() throws RecognitionException {
        RVCCalParser.actorDeclaration_return retval = new RVCCalParser.actorDeclaration_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token varName=null;
        Token char_literal40=null;
        Token ID41=null;
        Token char_literal42=null;
        Token ACTION43=null;
        Token string_literal44=null;
        Token string_literal45=null;
        Token string_literal48=null;
        Token INITIALIZE49=null;
        Token string_literal50=null;
        Token string_literal53=null;
        Token string_literal56=null;
        Token char_literal57=null;
        Token char_literal58=null;
        Token char_literal59=null;
        Token string_literal61=null;
        Token char_literal63=null;
        Token ACTION64=null;
        Token string_literal65=null;
        Token string_literal66=null;
        Token string_literal69=null;
        Token INITIALIZE70=null;
        Token string_literal71=null;
        Token string_literal73=null;
        Token string_literal76=null;
        Token FUNCTION78=null;
        Token ID79=null;
        Token char_literal80=null;
        Token char_literal82=null;
        Token char_literal84=null;
        Token string_literal85=null;
        Token string_literal87=null;
        Token char_literal89=null;
        Token string_literal91=null;
        Token PROCEDURE92=null;
        Token ID93=null;
        Token char_literal94=null;
        Token char_literal96=null;
        Token char_literal98=null;
        Token string_literal99=null;
        Token string_literal101=null;
        Token string_literal103=null;
        RVCCalParser.actionInputs_return inputs = null;

        RVCCalParser.actionOutputs_return outputs = null;

        RVCCalParser.actionGuards_return guards = null;

        RVCCalParser.typeAttrs_return attrs = null;

        RVCCalParser.id_return id39 = null;

        RVCCalParser.varDecls_return varDecls46 = null;

        RVCCalParser.actionStatements_return actionStatements47 = null;

        RVCCalParser.actionOutputs_return actionOutputs51 = null;

        RVCCalParser.actionGuards_return actionGuards52 = null;

        RVCCalParser.varDecls_return varDecls54 = null;

        RVCCalParser.actionStatements_return actionStatements55 = null;

        RVCCalParser.expression_return expression60 = null;

        RVCCalParser.expression_return expression62 = null;

        RVCCalParser.varDecls_return varDecls67 = null;

        RVCCalParser.actionStatements_return actionStatements68 = null;

        RVCCalParser.actionGuards_return actionGuards72 = null;

        RVCCalParser.varDecls_return varDecls74 = null;

        RVCCalParser.actionStatements_return actionStatements75 = null;

        RVCCalParser.priorityOrder_return priorityOrder77 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr81 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr83 = null;

        RVCCalParser.typeDef_return typeDef86 = null;

        RVCCalParser.varDecls_return varDecls88 = null;

        RVCCalParser.expression_return expression90 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr95 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr97 = null;

        RVCCalParser.varDecls_return varDecls100 = null;

        RVCCalParser.statement_return statement102 = null;


        Object varName_tree=null;
        Object char_literal40_tree=null;
        Object ID41_tree=null;
        Object char_literal42_tree=null;
        Object ACTION43_tree=null;
        Object string_literal44_tree=null;
        Object string_literal45_tree=null;
        Object string_literal48_tree=null;
        Object INITIALIZE49_tree=null;
        Object string_literal50_tree=null;
        Object string_literal53_tree=null;
        Object string_literal56_tree=null;
        Object char_literal57_tree=null;
        Object char_literal58_tree=null;
        Object char_literal59_tree=null;
        Object string_literal61_tree=null;
        Object char_literal63_tree=null;
        Object ACTION64_tree=null;
        Object string_literal65_tree=null;
        Object string_literal66_tree=null;
        Object string_literal69_tree=null;
        Object INITIALIZE70_tree=null;
        Object string_literal71_tree=null;
        Object string_literal73_tree=null;
        Object string_literal76_tree=null;
        Object FUNCTION78_tree=null;
        Object ID79_tree=null;
        Object char_literal80_tree=null;
        Object char_literal82_tree=null;
        Object char_literal84_tree=null;
        Object string_literal85_tree=null;
        Object string_literal87_tree=null;
        Object char_literal89_tree=null;
        Object string_literal91_tree=null;
        Object PROCEDURE92_tree=null;
        Object ID93_tree=null;
        Object char_literal94_tree=null;
        Object char_literal96_tree=null;
        Object char_literal98_tree=null;
        Object string_literal99_tree=null;
        Object string_literal101_tree=null;
        Object string_literal103_tree=null;
        RewriteRuleTokenStream stream_FUNCTION=new RewriteRuleTokenStream(adaptor,"token FUNCTION");
        RewriteRuleTokenStream stream_98=new RewriteRuleTokenStream(adaptor,"token 98");
        RewriteRuleTokenStream stream_97=new RewriteRuleTokenStream(adaptor,"token 97");
        RewriteRuleTokenStream stream_96=new RewriteRuleTokenStream(adaptor,"token 96");
        RewriteRuleTokenStream stream_95=new RewriteRuleTokenStream(adaptor,"token 95");
        RewriteRuleTokenStream stream_94=new RewriteRuleTokenStream(adaptor,"token 94");
        RewriteRuleTokenStream stream_INITIALIZE=new RewriteRuleTokenStream(adaptor,"token INITIALIZE");
        RewriteRuleTokenStream stream_93=new RewriteRuleTokenStream(adaptor,"token 93");
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_PROCEDURE=new RewriteRuleTokenStream(adaptor,"token PROCEDURE");
        RewriteRuleTokenStream stream_102=new RewriteRuleTokenStream(adaptor,"token 102");
        RewriteRuleTokenStream stream_99=new RewriteRuleTokenStream(adaptor,"token 99");
        RewriteRuleTokenStream stream_ACTION=new RewriteRuleTokenStream(adaptor,"token ACTION");
        RewriteRuleTokenStream stream_101=new RewriteRuleTokenStream(adaptor,"token 101");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleTokenStream stream_100=new RewriteRuleTokenStream(adaptor,"token 100");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_varDecls=new RewriteRuleSubtreeStream(adaptor,"rule varDecls");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_actionOutputs=new RewriteRuleSubtreeStream(adaptor,"rule actionOutputs");
        RewriteRuleSubtreeStream stream_actionInputs=new RewriteRuleSubtreeStream(adaptor,"rule actionInputs");
        RewriteRuleSubtreeStream stream_priorityOrder=new RewriteRuleSubtreeStream(adaptor,"rule priorityOrder");
        RewriteRuleSubtreeStream stream_actionGuards=new RewriteRuleSubtreeStream(adaptor,"rule actionGuards");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        RewriteRuleSubtreeStream stream_actionStatements=new RewriteRuleSubtreeStream(adaptor,"rule actionStatements");
        RewriteRuleSubtreeStream stream_typeAttrs=new RewriteRuleSubtreeStream(adaptor,"rule typeAttrs");
        RewriteRuleSubtreeStream stream_varDeclNoExpr=new RewriteRuleSubtreeStream(adaptor,"rule varDeclNoExpr");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:89:17: ( id ( ( ( ( '.' ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName ASSIGNABLE ) ) ';' ) | ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' (outputs= actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | priorityOrder -> priorityOrder | FUNCTION ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end' -> ^( FUNCTION ID ^( PARAMETERS ( varDeclNoExpr )* ) ^( VARIABLES ( varDecls )? ) expression ) | PROCEDURE ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end' -> ^( PROCEDURE ID ^( PARAMETERS ( varDeclNoExpr )* ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( statement )* ) ) )
            int alt44=6;
            switch ( input.LA(1) ) {
            case ID:
                {
                alt44=1;
                }
                break;
            case ACTION:
                {
                alt44=2;
                }
                break;
            case INITIALIZE:
                {
                alt44=3;
                }
                break;
            case PRIORITY:
                {
                alt44=4;
                }
                break;
            case FUNCTION:
                {
                alt44=5;
                }
                break;
            case PROCEDURE:
                {
                alt44=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 44, 0, input);

                throw nvae;
            }

            switch (alt44) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:94:3: id ( ( ( ( '.' ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName ASSIGNABLE ) ) ';' )
                    {
                    pushFollow(FOLLOW_id_in_actorDeclaration360);
                    id39=id();

                    state._fsp--;

                    stream_id.add(id39.getTree());
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:94:6: ( ( ( ( '.' ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName ASSIGNABLE ) ) ';' )
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==87||LA27_0==96) ) {
                        alt27=1;
                    }
                    else if ( (LA27_0==ID||LA27_0==92) ) {
                        alt27=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 27, 0, input);

                        throw nvae;
                    }
                    switch (alt27) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:95:5: ( ( ( '.' ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) )
                            {
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:95:5: ( ( ( '.' ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) )
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:95:6: ( ( '.' ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) )
                            {
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:95:6: ( ( '.' ID )* )
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:95:7: ( '.' ID )*
                            {
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:95:7: ( '.' ID )*
                            loop14:
                            do {
                                int alt14=2;
                                int LA14_0 = input.LA(1);

                                if ( (LA14_0==96) ) {
                                    alt14=1;
                                }


                                switch (alt14) {
                            	case 1 :
                            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:95:8: '.' ID
                            	    {
                            	    char_literal40=(Token)match(input,96,FOLLOW_96_in_actorDeclaration371);  
                            	    stream_96.add(char_literal40);

                            	    ID41=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration373);  
                            	    stream_ID.add(ID41);


                            	    }
                            	    break;

                            	default :
                            	    break loop14;
                                }
                            } while (true);


                            }

                            char_literal42=(Token)match(input,87,FOLLOW_87_in_actorDeclaration378);  
                            stream_87.add(char_literal42);

                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:7: ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) )
                            int alt24=2;
                            int LA24_0 = input.LA(1);

                            if ( (LA24_0==ACTION) ) {
                                alt24=1;
                            }
                            else if ( (LA24_0==INITIALIZE) ) {
                                alt24=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 24, 0, input);

                                throw nvae;
                            }
                            switch (alt24) {
                                case 1 :
                                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:8: ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                                    {
                                    ACTION43=(Token)match(input,ACTION,FOLLOW_ACTION_in_actorDeclaration387);  
                                    stream_ACTION.add(ACTION43);

                                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:21: (inputs= actionInputs )?
                                    int alt15=2;
                                    int LA15_0 = input.LA(1);

                                    if ( (LA15_0==ID||LA15_0==88) ) {
                                        alt15=1;
                                    }
                                    switch (alt15) {
                                        case 1 :
                                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:21: inputs= actionInputs
                                            {
                                            pushFollow(FOLLOW_actionInputs_in_actorDeclaration391);
                                            inputs=actionInputs();

                                            state._fsp--;

                                            stream_actionInputs.add(inputs.getTree());

                                            }
                                            break;

                                    }

                                    string_literal44=(Token)match(input,94,FOLLOW_94_in_actorDeclaration394);  
                                    stream_94.add(string_literal44);

                                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:49: (outputs= actionOutputs )?
                                    int alt16=2;
                                    int LA16_0 = input.LA(1);

                                    if ( (LA16_0==ID||LA16_0==88) ) {
                                        alt16=1;
                                    }
                                    switch (alt16) {
                                        case 1 :
                                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:49: outputs= actionOutputs
                                            {
                                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration398);
                                            outputs=actionOutputs();

                                            state._fsp--;

                                            stream_actionOutputs.add(outputs.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:71: (guards= actionGuards )?
                                    int alt17=2;
                                    int LA17_0 = input.LA(1);

                                    if ( (LA17_0==GUARD) ) {
                                        alt17=1;
                                    }
                                    switch (alt17) {
                                        case 1 :
                                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:71: guards= actionGuards
                                            {
                                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration403);
                                            guards=actionGuards();

                                            state._fsp--;

                                            stream_actionGuards.add(guards.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:86: ( 'var' varDecls )?
                                    int alt18=2;
                                    int LA18_0 = input.LA(1);

                                    if ( (LA18_0==97) ) {
                                        alt18=1;
                                    }
                                    switch (alt18) {
                                        case 1 :
                                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:87: 'var' varDecls
                                            {
                                            string_literal45=(Token)match(input,97,FOLLOW_97_in_actorDeclaration407);  
                                            stream_97.add(string_literal45);

                                            pushFollow(FOLLOW_varDecls_in_actorDeclaration409);
                                            varDecls46=varDecls();

                                            state._fsp--;

                                            stream_varDecls.add(varDecls46.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:104: ( actionStatements )?
                                    int alt19=2;
                                    int LA19_0 = input.LA(1);

                                    if ( (LA19_0==91) ) {
                                        alt19=1;
                                    }
                                    switch (alt19) {
                                        case 1 :
                                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:104: actionStatements
                                            {
                                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration413);
                                            actionStatements47=actionStatements();

                                            state._fsp--;

                                            stream_actionStatements.add(actionStatements47.getTree());

                                            }
                                            break;

                                    }

                                    string_literal48=(Token)match(input,95,FOLLOW_95_in_actorDeclaration416);  
                                    stream_95.add(string_literal48);



                                    // AST REWRITE
                                    // elements: id, outputs, inputs, actionStatements, guards, ID, ACTION, varDecls
                                    // token labels: 
                                    // rule labels: retval, guards, inputs, outputs
                                    // token list labels: 
                                    // rule list labels: 
                                    // wildcard labels: 
                                    retval.tree = root_0;
                                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                                    RewriteRuleSubtreeStream stream_guards=new RewriteRuleSubtreeStream(adaptor,"rule guards",guards!=null?guards.tree:null);
                                    RewriteRuleSubtreeStream stream_inputs=new RewriteRuleSubtreeStream(adaptor,"rule inputs",inputs!=null?inputs.tree:null);
                                    RewriteRuleSubtreeStream stream_outputs=new RewriteRuleSubtreeStream(adaptor,"rule outputs",outputs!=null?outputs.tree:null);

                                    root_0 = (Object)adaptor.nil();
                                    // 97:9: -> ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                    {
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:97:12: ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot(stream_ACTION.nextNode(), root_1);

                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:97:21: ^( TAG id ( ID )* )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TAG, "TAG"), root_2);

                                        adaptor.addChild(root_2, stream_id.nextTree());
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:97:30: ( ID )*
                                        while ( stream_ID.hasNext() ) {
                                            adaptor.addChild(root_2, stream_ID.nextNode());

                                        }
                                        stream_ID.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:97:35: ^( INPUTS ( $inputs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_2);

                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:97:44: ( $inputs)?
                                        if ( stream_inputs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_inputs.nextTree());

                                        }
                                        stream_inputs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:97:54: ^( OUTPUTS ( $outputs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:97:64: ( $outputs)?
                                        if ( stream_outputs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_outputs.nextTree());

                                        }
                                        stream_outputs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:98:9: ^( GUARDS ( $guards)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:98:18: ( $guards)?
                                        if ( stream_guards.hasNext() ) {
                                            adaptor.addChild(root_2, stream_guards.nextTree());

                                        }
                                        stream_guards.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:98:28: ^( VARIABLES ( varDecls )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLES, "VARIABLES"), root_2);

                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:98:40: ( varDecls )?
                                        if ( stream_varDecls.hasNext() ) {
                                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                                        }
                                        stream_varDecls.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:98:51: ^( STATEMENTS ( actionStatements )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:98:64: ( actionStatements )?
                                        if ( stream_actionStatements.hasNext() ) {
                                            adaptor.addChild(root_2, stream_actionStatements.nextTree());

                                        }
                                        stream_actionStatements.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }

                                        adaptor.addChild(root_0, root_1);
                                        }

                                    }

                                    retval.tree = root_0;
                                    }
                                    break;
                                case 2 :
                                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:7: INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                                    {
                                    INITIALIZE49=(Token)match(input,INITIALIZE,FOLLOW_INITIALIZE_in_actorDeclaration494);  
                                    stream_INITIALIZE.add(INITIALIZE49);

                                    string_literal50=(Token)match(input,94,FOLLOW_94_in_actorDeclaration496);  
                                    stream_94.add(string_literal50);

                                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:24: ( actionOutputs )?
                                    int alt20=2;
                                    int LA20_0 = input.LA(1);

                                    if ( (LA20_0==ID||LA20_0==88) ) {
                                        alt20=1;
                                    }
                                    switch (alt20) {
                                        case 1 :
                                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:24: actionOutputs
                                            {
                                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration498);
                                            actionOutputs51=actionOutputs();

                                            state._fsp--;

                                            stream_actionOutputs.add(actionOutputs51.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:39: ( actionGuards )?
                                    int alt21=2;
                                    int LA21_0 = input.LA(1);

                                    if ( (LA21_0==GUARD) ) {
                                        alt21=1;
                                    }
                                    switch (alt21) {
                                        case 1 :
                                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:39: actionGuards
                                            {
                                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration501);
                                            actionGuards52=actionGuards();

                                            state._fsp--;

                                            stream_actionGuards.add(actionGuards52.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:53: ( 'var' varDecls )?
                                    int alt22=2;
                                    int LA22_0 = input.LA(1);

                                    if ( (LA22_0==97) ) {
                                        alt22=1;
                                    }
                                    switch (alt22) {
                                        case 1 :
                                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:54: 'var' varDecls
                                            {
                                            string_literal53=(Token)match(input,97,FOLLOW_97_in_actorDeclaration505);  
                                            stream_97.add(string_literal53);

                                            pushFollow(FOLLOW_varDecls_in_actorDeclaration507);
                                            varDecls54=varDecls();

                                            state._fsp--;

                                            stream_varDecls.add(varDecls54.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:71: ( actionStatements )?
                                    int alt23=2;
                                    int LA23_0 = input.LA(1);

                                    if ( (LA23_0==91) ) {
                                        alt23=1;
                                    }
                                    switch (alt23) {
                                        case 1 :
                                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:71: actionStatements
                                            {
                                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration511);
                                            actionStatements55=actionStatements();

                                            state._fsp--;

                                            stream_actionStatements.add(actionStatements55.getTree());

                                            }
                                            break;

                                    }

                                    string_literal56=(Token)match(input,95,FOLLOW_95_in_actorDeclaration514);  
                                    stream_95.add(string_literal56);



                                    // AST REWRITE
                                    // elements: varDecls, id, outputs, ID, guards, actionStatements, INITIALIZE
                                    // token labels: 
                                    // rule labels: retval, guards, outputs
                                    // token list labels: 
                                    // rule list labels: 
                                    // wildcard labels: 
                                    retval.tree = root_0;
                                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                                    RewriteRuleSubtreeStream stream_guards=new RewriteRuleSubtreeStream(adaptor,"rule guards",guards!=null?guards.tree:null);
                                    RewriteRuleSubtreeStream stream_outputs=new RewriteRuleSubtreeStream(adaptor,"rule outputs",outputs!=null?outputs.tree:null);

                                    root_0 = (Object)adaptor.nil();
                                    // 101:9: -> ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                    {
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:101:12: ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot(stream_INITIALIZE.nextNode(), root_1);

                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:101:25: ^( TAG id ( ID )* )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TAG, "TAG"), root_2);

                                        adaptor.addChild(root_2, stream_id.nextTree());
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:101:34: ( ID )*
                                        while ( stream_ID.hasNext() ) {
                                            adaptor.addChild(root_2, stream_ID.nextNode());

                                        }
                                        stream_ID.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        adaptor.addChild(root_1, (Object)adaptor.create(INPUTS, "INPUTS"));
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:101:46: ^( OUTPUTS ( $outputs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:101:56: ( $outputs)?
                                        if ( stream_outputs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_outputs.nextTree());

                                        }
                                        stream_outputs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:102:9: ^( GUARDS ( $guards)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:102:18: ( $guards)?
                                        if ( stream_guards.hasNext() ) {
                                            adaptor.addChild(root_2, stream_guards.nextTree());

                                        }
                                        stream_guards.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:102:28: ^( VARIABLES ( varDecls )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLES, "VARIABLES"), root_2);

                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:102:40: ( varDecls )?
                                        if ( stream_varDecls.hasNext() ) {
                                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                                        }
                                        stream_varDecls.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:102:51: ^( STATEMENTS ( actionStatements )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:102:64: ( actionStatements )?
                                        if ( stream_actionStatements.hasNext() ) {
                                            adaptor.addChild(root_2, stream_actionStatements.nextTree());

                                        }
                                        stream_actionStatements.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }

                                        adaptor.addChild(root_0, root_1);
                                        }

                                    }

                                    retval.tree = root_0;
                                    }
                                    break;

                            }


                            }


                            }
                            break;
                        case 2 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:108:5: ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName ASSIGNABLE ) ) ';'
                            {
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:108:5: ( '(' attrs= typeAttrs ')' )?
                            int alt25=2;
                            int LA25_0 = input.LA(1);

                            if ( (LA25_0==92) ) {
                                alt25=1;
                            }
                            switch (alt25) {
                                case 1 :
                                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:108:6: '(' attrs= typeAttrs ')'
                                    {
                                    char_literal57=(Token)match(input,92,FOLLOW_92_in_actorDeclaration600);  
                                    stream_92.add(char_literal57);

                                    pushFollow(FOLLOW_typeAttrs_in_actorDeclaration604);
                                    attrs=typeAttrs();

                                    state._fsp--;

                                    stream_typeAttrs.add(attrs.getTree());
                                    char_literal58=(Token)match(input,93,FOLLOW_93_in_actorDeclaration606);  
                                    stream_93.add(char_literal58);


                                    }
                                    break;

                            }

                            varName=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration616);  
                            stream_ID.add(varName);

                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:110:5: ( '=' expression -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName ASSIGNABLE ) )
                            int alt26=3;
                            switch ( input.LA(1) ) {
                            case 98:
                                {
                                alt26=1;
                                }
                                break;
                            case 99:
                                {
                                alt26=2;
                                }
                                break;
                            case 100:
                                {
                                alt26=3;
                                }
                                break;
                            default:
                                NoViableAltException nvae =
                                    new NoViableAltException("", 26, 0, input);

                                throw nvae;
                            }

                            switch (alt26) {
                                case 1 :
                                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:110:8: '=' expression
                                    {
                                    char_literal59=(Token)match(input,98,FOLLOW_98_in_actorDeclaration625);  
                                    stream_98.add(char_literal59);

                                    pushFollow(FOLLOW_expression_in_actorDeclaration627);
                                    expression60=expression();

                                    state._fsp--;

                                    stream_expression.add(expression60.getTree());


                                    // AST REWRITE
                                    // elements: varName, expression, id, attrs
                                    // token labels: varName
                                    // rule labels: retval, attrs
                                    // token list labels: 
                                    // rule list labels: 
                                    // wildcard labels: 
                                    retval.tree = root_0;
                                    RewriteRuleTokenStream stream_varName=new RewriteRuleTokenStream(adaptor,"token varName",varName);
                                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                                    RewriteRuleSubtreeStream stream_attrs=new RewriteRuleSubtreeStream(adaptor,"rule attrs",attrs!=null?attrs.tree:null);

                                    root_0 = (Object)adaptor.nil();
                                    // 110:23: -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName NON_ASSIGNABLE expression )
                                    {
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:110:26: ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName NON_ASSIGNABLE expression )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:110:38: ^( TYPE id ( $attrs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_id.nextTree());
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:110:48: ( $attrs)?
                                        if ( stream_attrs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_attrs.nextTree());

                                        }
                                        stream_attrs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        adaptor.addChild(root_1, stream_varName.nextNode());
                                        adaptor.addChild(root_1, (Object)adaptor.create(NON_ASSIGNABLE, "NON_ASSIGNABLE"));
                                        adaptor.addChild(root_1, stream_expression.nextTree());

                                        adaptor.addChild(root_0, root_1);
                                        }

                                    }

                                    retval.tree = root_0;
                                    }
                                    break;
                                case 2 :
                                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:111:8: ':=' expression
                                    {
                                    string_literal61=(Token)match(input,99,FOLLOW_99_in_actorDeclaration659);  
                                    stream_99.add(string_literal61);

                                    pushFollow(FOLLOW_expression_in_actorDeclaration661);
                                    expression62=expression();

                                    state._fsp--;

                                    stream_expression.add(expression62.getTree());


                                    // AST REWRITE
                                    // elements: attrs, expression, varName, id
                                    // token labels: varName
                                    // rule labels: retval, attrs
                                    // token list labels: 
                                    // rule list labels: 
                                    // wildcard labels: 
                                    retval.tree = root_0;
                                    RewriteRuleTokenStream stream_varName=new RewriteRuleTokenStream(adaptor,"token varName",varName);
                                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                                    RewriteRuleSubtreeStream stream_attrs=new RewriteRuleSubtreeStream(adaptor,"rule attrs",attrs!=null?attrs.tree:null);

                                    root_0 = (Object)adaptor.nil();
                                    // 111:24: -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName ASSIGNABLE expression )
                                    {
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:111:27: ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName ASSIGNABLE expression )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:111:39: ^( TYPE id ( $attrs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_id.nextTree());
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:111:49: ( $attrs)?
                                        if ( stream_attrs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_attrs.nextTree());

                                        }
                                        stream_attrs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        adaptor.addChild(root_1, stream_varName.nextNode());
                                        adaptor.addChild(root_1, (Object)adaptor.create(ASSIGNABLE, "ASSIGNABLE"));
                                        adaptor.addChild(root_1, stream_expression.nextTree());

                                        adaptor.addChild(root_0, root_1);
                                        }

                                    }

                                    retval.tree = root_0;
                                    }
                                    break;
                                case 3 :
                                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:8: 
                                    {

                                    // AST REWRITE
                                    // elements: id, attrs, varName
                                    // token labels: varName
                                    // rule labels: retval, attrs
                                    // token list labels: 
                                    // rule list labels: 
                                    // wildcard labels: 
                                    retval.tree = root_0;
                                    RewriteRuleTokenStream stream_varName=new RewriteRuleTokenStream(adaptor,"token varName",varName);
                                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                                    RewriteRuleSubtreeStream stream_attrs=new RewriteRuleSubtreeStream(adaptor,"rule attrs",attrs!=null?attrs.tree:null);

                                    root_0 = (Object)adaptor.nil();
                                    // 112:8: -> ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName ASSIGNABLE )
                                    {
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:11: ^( STATE_VAR ^( TYPE id ( $attrs)? ) $varName ASSIGNABLE )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:23: ^( TYPE id ( $attrs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_id.nextTree());
                                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:33: ( $attrs)?
                                        if ( stream_attrs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_attrs.nextTree());

                                        }
                                        stream_attrs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        adaptor.addChild(root_1, stream_varName.nextNode());
                                        adaptor.addChild(root_1, (Object)adaptor.create(ASSIGNABLE, "ASSIGNABLE"));

                                        adaptor.addChild(root_0, root_1);
                                        }

                                    }

                                    retval.tree = root_0;
                                    }
                                    break;

                            }

                            char_literal63=(Token)match(input,100,FOLLOW_100_in_actorDeclaration715);  
                            stream_100.add(char_literal63);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:3: ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                    {
                    ACTION64=(Token)match(input,ACTION,FOLLOW_ACTION_in_actorDeclaration725);  
                    stream_ACTION.add(ACTION64);

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:16: (inputs= actionInputs )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==ID||LA28_0==88) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:16: inputs= actionInputs
                            {
                            pushFollow(FOLLOW_actionInputs_in_actorDeclaration729);
                            inputs=actionInputs();

                            state._fsp--;

                            stream_actionInputs.add(inputs.getTree());

                            }
                            break;

                    }

                    string_literal65=(Token)match(input,94,FOLLOW_94_in_actorDeclaration732);  
                    stream_94.add(string_literal65);

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:44: (outputs= actionOutputs )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==ID||LA29_0==88) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:44: outputs= actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration736);
                            outputs=actionOutputs();

                            state._fsp--;

                            stream_actionOutputs.add(outputs.getTree());

                            }
                            break;

                    }

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:66: (guards= actionGuards )?
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==GUARD) ) {
                        alt30=1;
                    }
                    switch (alt30) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:66: guards= actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration741);
                            guards=actionGuards();

                            state._fsp--;

                            stream_actionGuards.add(guards.getTree());

                            }
                            break;

                    }

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:81: ( 'var' varDecls )?
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==97) ) {
                        alt31=1;
                    }
                    switch (alt31) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:82: 'var' varDecls
                            {
                            string_literal66=(Token)match(input,97,FOLLOW_97_in_actorDeclaration745);  
                            stream_97.add(string_literal66);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration747);
                            varDecls67=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls67.getTree());

                            }
                            break;

                    }

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:99: ( actionStatements )?
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==91) ) {
                        alt32=1;
                    }
                    switch (alt32) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:99: actionStatements
                            {
                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration751);
                            actionStatements68=actionStatements();

                            state._fsp--;

                            stream_actionStatements.add(actionStatements68.getTree());

                            }
                            break;

                    }

                    string_literal69=(Token)match(input,95,FOLLOW_95_in_actorDeclaration754);  
                    stream_95.add(string_literal69);



                    // AST REWRITE
                    // elements: ACTION, inputs, outputs, guards, actionStatements, varDecls
                    // token labels: 
                    // rule labels: retval, guards, inputs, outputs
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_guards=new RewriteRuleSubtreeStream(adaptor,"rule guards",guards!=null?guards.tree:null);
                    RewriteRuleSubtreeStream stream_inputs=new RewriteRuleSubtreeStream(adaptor,"rule inputs",inputs!=null?inputs.tree:null);
                    RewriteRuleSubtreeStream stream_outputs=new RewriteRuleSubtreeStream(adaptor,"rule outputs",outputs!=null?outputs.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 117:3: -> ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:6: ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_ACTION.nextNode(), root_1);

                        adaptor.addChild(root_1, (Object)adaptor.create(TAG, "TAG"));
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:19: ^( INPUTS ( $inputs)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_2);

                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:28: ( $inputs)?
                        if ( stream_inputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_inputs.nextTree());

                        }
                        stream_inputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:38: ^( OUTPUTS ( $outputs)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:48: ( $outputs)?
                        if ( stream_outputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_outputs.nextTree());

                        }
                        stream_outputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:59: ^( GUARDS ( $guards)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:68: ( $guards)?
                        if ( stream_guards.hasNext() ) {
                            adaptor.addChild(root_2, stream_guards.nextTree());

                        }
                        stream_guards.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:78: ^( VARIABLES ( varDecls )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLES, "VARIABLES"), root_2);

                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:90: ( varDecls )?
                        if ( stream_varDecls.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                        }
                        stream_varDecls.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:101: ^( STATEMENTS ( actionStatements )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:114: ( actionStatements )?
                        if ( stream_actionStatements.hasNext() ) {
                            adaptor.addChild(root_2, stream_actionStatements.nextTree());

                        }
                        stream_actionStatements.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:3: INITIALIZE '==>' (outputs= actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                    {
                    INITIALIZE70=(Token)match(input,INITIALIZE,FOLLOW_INITIALIZE_in_actorDeclaration808);  
                    stream_INITIALIZE.add(INITIALIZE70);

                    string_literal71=(Token)match(input,94,FOLLOW_94_in_actorDeclaration810);  
                    stream_94.add(string_literal71);

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:27: (outputs= actionOutputs )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==ID||LA33_0==88) ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:27: outputs= actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration814);
                            outputs=actionOutputs();

                            state._fsp--;

                            stream_actionOutputs.add(outputs.getTree());

                            }
                            break;

                    }

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:43: ( actionGuards )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==GUARD) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:43: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration817);
                            actionGuards72=actionGuards();

                            state._fsp--;

                            stream_actionGuards.add(actionGuards72.getTree());

                            }
                            break;

                    }

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:57: ( 'var' varDecls )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==97) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:58: 'var' varDecls
                            {
                            string_literal73=(Token)match(input,97,FOLLOW_97_in_actorDeclaration821);  
                            stream_97.add(string_literal73);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration823);
                            varDecls74=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls74.getTree());

                            }
                            break;

                    }

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:75: ( actionStatements )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==91) ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:75: actionStatements
                            {
                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration827);
                            actionStatements75=actionStatements();

                            state._fsp--;

                            stream_actionStatements.add(actionStatements75.getTree());

                            }
                            break;

                    }

                    string_literal76=(Token)match(input,95,FOLLOW_95_in_actorDeclaration830);  
                    stream_95.add(string_literal76);



                    // AST REWRITE
                    // elements: actionStatements, varDecls, guards, outputs, INITIALIZE
                    // token labels: 
                    // rule labels: retval, guards, outputs
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_guards=new RewriteRuleSubtreeStream(adaptor,"rule guards",guards!=null?guards.tree:null);
                    RewriteRuleSubtreeStream stream_outputs=new RewriteRuleSubtreeStream(adaptor,"rule outputs",outputs!=null?outputs.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 121:3: -> ^( INITIALIZE TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:6: ^( INITIALIZE TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_INITIALIZE.nextNode(), root_1);

                        adaptor.addChild(root_1, (Object)adaptor.create(TAG, "TAG"));
                        adaptor.addChild(root_1, (Object)adaptor.create(INPUTS, "INPUTS"));
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:30: ^( OUTPUTS ( $outputs)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:40: ( $outputs)?
                        if ( stream_outputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_outputs.nextTree());

                        }
                        stream_outputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:51: ^( GUARDS ( $guards)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:60: ( $guards)?
                        if ( stream_guards.hasNext() ) {
                            adaptor.addChild(root_2, stream_guards.nextTree());

                        }
                        stream_guards.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:70: ^( VARIABLES ( varDecls )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLES, "VARIABLES"), root_2);

                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:82: ( varDecls )?
                        if ( stream_varDecls.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                        }
                        stream_varDecls.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:93: ^( STATEMENTS ( actionStatements )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:106: ( actionStatements )?
                        if ( stream_actionStatements.hasNext() ) {
                            adaptor.addChild(root_2, stream_actionStatements.nextTree());

                        }
                        stream_actionStatements.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:123:3: priorityOrder
                    {
                    pushFollow(FOLLOW_priorityOrder_in_actorDeclaration877);
                    priorityOrder77=priorityOrder();

                    state._fsp--;

                    stream_priorityOrder.add(priorityOrder77.getTree());


                    // AST REWRITE
                    // elements: priorityOrder
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 123:17: -> priorityOrder
                    {
                        adaptor.addChild(root_0, stream_priorityOrder.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:3: FUNCTION ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end'
                    {
                    FUNCTION78=(Token)match(input,FUNCTION,FOLLOW_FUNCTION_in_actorDeclaration886);  
                    stream_FUNCTION.add(FUNCTION78);

                    ID79=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration888);  
                    stream_ID.add(ID79);

                    char_literal80=(Token)match(input,92,FOLLOW_92_in_actorDeclaration890);  
                    stream_92.add(char_literal80);

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:19: ( varDeclNoExpr ( ',' varDeclNoExpr )* )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==ID) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:20: varDeclNoExpr ( ',' varDeclNoExpr )*
                            {
                            pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration893);
                            varDeclNoExpr81=varDeclNoExpr();

                            state._fsp--;

                            stream_varDeclNoExpr.add(varDeclNoExpr81.getTree());
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:34: ( ',' varDeclNoExpr )*
                            loop37:
                            do {
                                int alt37=2;
                                int LA37_0 = input.LA(1);

                                if ( (LA37_0==90) ) {
                                    alt37=1;
                                }


                                switch (alt37) {
                            	case 1 :
                            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:35: ',' varDeclNoExpr
                            	    {
                            	    char_literal82=(Token)match(input,90,FOLLOW_90_in_actorDeclaration896);  
                            	    stream_90.add(char_literal82);

                            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration898);
                            	    varDeclNoExpr83=varDeclNoExpr();

                            	    state._fsp--;

                            	    stream_varDeclNoExpr.add(varDeclNoExpr83.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop37;
                                }
                            } while (true);


                            }
                            break;

                    }

                    char_literal84=(Token)match(input,93,FOLLOW_93_in_actorDeclaration904);  
                    stream_93.add(char_literal84);

                    string_literal85=(Token)match(input,101,FOLLOW_101_in_actorDeclaration906);  
                    stream_101.add(string_literal85);

                    pushFollow(FOLLOW_typeDef_in_actorDeclaration908);
                    typeDef86=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef86.getTree());
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:5: ( 'var' varDecls )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==97) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:6: 'var' varDecls
                            {
                            string_literal87=(Token)match(input,97,FOLLOW_97_in_actorDeclaration915);  
                            stream_97.add(string_literal87);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration917);
                            varDecls88=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls88.getTree());

                            }
                            break;

                    }

                    char_literal89=(Token)match(input,87,FOLLOW_87_in_actorDeclaration921);  
                    stream_87.add(char_literal89);

                    pushFollow(FOLLOW_expression_in_actorDeclaration929);
                    expression90=expression();

                    state._fsp--;

                    stream_expression.add(expression90.getTree());
                    string_literal91=(Token)match(input,95,FOLLOW_95_in_actorDeclaration935);  
                    stream_95.add(string_literal91);



                    // AST REWRITE
                    // elements: ID, varDecls, expression, FUNCTION, varDeclNoExpr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 129:2: -> ^( FUNCTION ID ^( PARAMETERS ( varDeclNoExpr )* ) ^( VARIABLES ( varDecls )? ) expression )
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:129:5: ^( FUNCTION ID ^( PARAMETERS ( varDeclNoExpr )* ) ^( VARIABLES ( varDecls )? ) expression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_FUNCTION.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_ID.nextNode());
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:129:19: ^( PARAMETERS ( varDeclNoExpr )* )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETERS, "PARAMETERS"), root_2);

                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:129:32: ( varDeclNoExpr )*
                        while ( stream_varDeclNoExpr.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDeclNoExpr.nextTree());

                        }
                        stream_varDeclNoExpr.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:129:48: ^( VARIABLES ( varDecls )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLES, "VARIABLES"), root_2);

                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:129:60: ( varDecls )?
                        if ( stream_varDecls.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                        }
                        stream_varDecls.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        adaptor.addChild(root_1, stream_expression.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 6 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:3: PROCEDURE ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end'
                    {
                    PROCEDURE92=(Token)match(input,PROCEDURE,FOLLOW_PROCEDURE_in_actorDeclaration965);  
                    stream_PROCEDURE.add(PROCEDURE92);

                    ID93=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration967);  
                    stream_ID.add(ID93);

                    char_literal94=(Token)match(input,92,FOLLOW_92_in_actorDeclaration969);  
                    stream_92.add(char_literal94);

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:20: ( varDeclNoExpr ( ',' varDeclNoExpr )* )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==ID) ) {
                        alt41=1;
                    }
                    switch (alt41) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:21: varDeclNoExpr ( ',' varDeclNoExpr )*
                            {
                            pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration972);
                            varDeclNoExpr95=varDeclNoExpr();

                            state._fsp--;

                            stream_varDeclNoExpr.add(varDeclNoExpr95.getTree());
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:35: ( ',' varDeclNoExpr )*
                            loop40:
                            do {
                                int alt40=2;
                                int LA40_0 = input.LA(1);

                                if ( (LA40_0==90) ) {
                                    alt40=1;
                                }


                                switch (alt40) {
                            	case 1 :
                            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:36: ',' varDeclNoExpr
                            	    {
                            	    char_literal96=(Token)match(input,90,FOLLOW_90_in_actorDeclaration975);  
                            	    stream_90.add(char_literal96);

                            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration977);
                            	    varDeclNoExpr97=varDeclNoExpr();

                            	    state._fsp--;

                            	    stream_varDeclNoExpr.add(varDeclNoExpr97.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop40;
                                }
                            } while (true);


                            }
                            break;

                    }

                    char_literal98=(Token)match(input,93,FOLLOW_93_in_actorDeclaration983);  
                    stream_93.add(char_literal98);

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:132:5: ( 'var' varDecls )?
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==97) ) {
                        alt42=1;
                    }
                    switch (alt42) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:132:6: 'var' varDecls
                            {
                            string_literal99=(Token)match(input,97,FOLLOW_97_in_actorDeclaration990);  
                            stream_97.add(string_literal99);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration992);
                            varDecls100=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls100.getTree());

                            }
                            break;

                    }

                    string_literal101=(Token)match(input,102,FOLLOW_102_in_actorDeclaration1000);  
                    stream_102.add(string_literal101);

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:133:13: ( statement )*
                    loop43:
                    do {
                        int alt43=2;
                        int LA43_0 = input.LA(1);

                        if ( (LA43_0==ID||LA43_0==102||LA43_0==123||LA43_0==131||LA43_0==133) ) {
                            alt43=1;
                        }


                        switch (alt43) {
                    	case 1 :
                    	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:133:13: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_actorDeclaration1002);
                    	    statement102=statement();

                    	    state._fsp--;

                    	    stream_statement.add(statement102.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop43;
                        }
                    } while (true);

                    string_literal103=(Token)match(input,95,FOLLOW_95_in_actorDeclaration1005);  
                    stream_95.add(string_literal103);



                    // AST REWRITE
                    // elements: statement, ID, varDecls, varDeclNoExpr, PROCEDURE
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 134:2: -> ^( PROCEDURE ID ^( PARAMETERS ( varDeclNoExpr )* ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( statement )* ) )
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:5: ^( PROCEDURE ID ^( PARAMETERS ( varDeclNoExpr )* ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( statement )* ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_PROCEDURE.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_ID.nextNode());
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:20: ^( PARAMETERS ( varDeclNoExpr )* )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETERS, "PARAMETERS"), root_2);

                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:33: ( varDeclNoExpr )*
                        while ( stream_varDeclNoExpr.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDeclNoExpr.nextTree());

                        }
                        stream_varDeclNoExpr.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:49: ^( VARIABLES ( varDecls )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLES, "VARIABLES"), root_2);

                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:61: ( varDecls )?
                        if ( stream_varDecls.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                        }
                        stream_varDecls.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:72: ^( STATEMENTS ( statement )* )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:85: ( statement )*
                        while ( stream_statement.hasNext() ) {
                            adaptor.addChild(root_2, stream_statement.nextTree());

                        }
                        stream_statement.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "actorDeclaration"

    public static class actorDeclarations_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actorDeclarations"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:1: actorDeclarations : ( ( actorDeclaration )+ ( schedule ( actorDeclaration )* )? -> ( actorDeclaration )+ ( schedule )? | schedule ( actorDeclaration )* -> ( actorDeclaration )* schedule );
    public final RVCCalParser.actorDeclarations_return actorDeclarations() throws RecognitionException {
        RVCCalParser.actorDeclarations_return retval = new RVCCalParser.actorDeclarations_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        RVCCalParser.actorDeclaration_return actorDeclaration104 = null;

        RVCCalParser.schedule_return schedule105 = null;

        RVCCalParser.actorDeclaration_return actorDeclaration106 = null;

        RVCCalParser.schedule_return schedule107 = null;

        RVCCalParser.actorDeclaration_return actorDeclaration108 = null;


        RewriteRuleSubtreeStream stream_schedule=new RewriteRuleSubtreeStream(adaptor,"rule schedule");
        RewriteRuleSubtreeStream stream_actorDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule actorDeclaration");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:18: ( ( actorDeclaration )+ ( schedule ( actorDeclaration )* )? -> ( actorDeclaration )+ ( schedule )? | schedule ( actorDeclaration )* -> ( actorDeclaration )* schedule )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==ACTION||LA49_0==FUNCTION||(LA49_0>=INITIALIZE && LA49_0<=PROCEDURE)||LA49_0==ID) ) {
                alt49=1;
            }
            else if ( (LA49_0==SCHEDULE) ) {
                alt49=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;
            }
            switch (alt49) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:20: ( actorDeclaration )+ ( schedule ( actorDeclaration )* )?
                    {
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:20: ( actorDeclaration )+
                    int cnt45=0;
                    loop45:
                    do {
                        int alt45=2;
                        int LA45_0 = input.LA(1);

                        if ( (LA45_0==ACTION||LA45_0==FUNCTION||(LA45_0>=INITIALIZE && LA45_0<=PROCEDURE)||LA45_0==ID) ) {
                            alt45=1;
                        }


                        switch (alt45) {
                    	case 1 :
                    	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:20: actorDeclaration
                    	    {
                    	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations1042);
                    	    actorDeclaration104=actorDeclaration();

                    	    state._fsp--;

                    	    stream_actorDeclaration.add(actorDeclaration104.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt45 >= 1 ) break loop45;
                                EarlyExitException eee =
                                    new EarlyExitException(45, input);
                                throw eee;
                        }
                        cnt45++;
                    } while (true);

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:38: ( schedule ( actorDeclaration )* )?
                    int alt47=2;
                    int LA47_0 = input.LA(1);

                    if ( (LA47_0==SCHEDULE) ) {
                        alt47=1;
                    }
                    switch (alt47) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:39: schedule ( actorDeclaration )*
                            {
                            pushFollow(FOLLOW_schedule_in_actorDeclarations1046);
                            schedule105=schedule();

                            state._fsp--;

                            stream_schedule.add(schedule105.getTree());
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:48: ( actorDeclaration )*
                            loop46:
                            do {
                                int alt46=2;
                                int LA46_0 = input.LA(1);

                                if ( (LA46_0==ACTION||LA46_0==FUNCTION||(LA46_0>=INITIALIZE && LA46_0<=PROCEDURE)||LA46_0==ID) ) {
                                    alt46=1;
                                }


                                switch (alt46) {
                            	case 1 :
                            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:48: actorDeclaration
                            	    {
                            	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations1048);
                            	    actorDeclaration106=actorDeclaration();

                            	    state._fsp--;

                            	    stream_actorDeclaration.add(actorDeclaration106.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop46;
                                }
                            } while (true);


                            }
                            break;

                    }



                    // AST REWRITE
                    // elements: schedule, actorDeclaration
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 136:68: -> ( actorDeclaration )+ ( schedule )?
                    {
                        if ( !(stream_actorDeclaration.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_actorDeclaration.hasNext() ) {
                            adaptor.addChild(root_0, stream_actorDeclaration.nextTree());

                        }
                        stream_actorDeclaration.reset();
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:89: ( schedule )?
                        if ( stream_schedule.hasNext() ) {
                            adaptor.addChild(root_0, stream_schedule.nextTree());

                        }
                        stream_schedule.reset();

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:137:5: schedule ( actorDeclaration )*
                    {
                    pushFollow(FOLLOW_schedule_in_actorDeclarations1065);
                    schedule107=schedule();

                    state._fsp--;

                    stream_schedule.add(schedule107.getTree());
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:137:14: ( actorDeclaration )*
                    loop48:
                    do {
                        int alt48=2;
                        int LA48_0 = input.LA(1);

                        if ( (LA48_0==ACTION||LA48_0==FUNCTION||(LA48_0>=INITIALIZE && LA48_0<=PROCEDURE)||LA48_0==ID) ) {
                            alt48=1;
                        }


                        switch (alt48) {
                    	case 1 :
                    	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:137:14: actorDeclaration
                    	    {
                    	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations1067);
                    	    actorDeclaration108=actorDeclaration();

                    	    state._fsp--;

                    	    stream_actorDeclaration.add(actorDeclaration108.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop48;
                        }
                    } while (true);



                    // AST REWRITE
                    // elements: actorDeclaration, schedule
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 137:32: -> ( actorDeclaration )* schedule
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:137:35: ( actorDeclaration )*
                        while ( stream_actorDeclaration.hasNext() ) {
                            adaptor.addChild(root_0, stream_actorDeclaration.nextTree());

                        }
                        stream_actorDeclaration.reset();
                        adaptor.addChild(root_0, stream_schedule.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "actorDeclarations"

    public static class actorImport_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actorImport"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:139:1: actorImport : 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) ;
    public final RVCCalParser.actorImport_return actorImport() throws RecognitionException {
        RVCCalParser.actorImport_return retval = new RVCCalParser.actorImport_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal109=null;
        Token string_literal110=null;
        Token char_literal112=null;
        Token char_literal114=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent111 = null;

        RVCCalParser.qualifiedIdent_return qualifiedIdent113 = null;


        Object string_literal109_tree=null;
        Object string_literal110_tree=null;
        Object char_literal112_tree=null;
        Object char_literal114_tree=null;

        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:142:12: ( 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:142:14: 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            {
            root_0 = (Object)adaptor.nil();

            string_literal109=(Token)match(input,103,FOLLOW_103_in_actorImport1087); 
            string_literal109_tree = (Object)adaptor.create(string_literal109);
            adaptor.addChild(root_0, string_literal109_tree);

            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:143:3: ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==104) ) {
                alt50=1;
            }
            else if ( (LA50_0==ID) ) {
                alt50=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 50, 0, input);

                throw nvae;
            }
            switch (alt50) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:143:4: 'all' qualifiedIdent ';'
                    {
                    string_literal110=(Token)match(input,104,FOLLOW_104_in_actorImport1092); 
                    string_literal110_tree = (Object)adaptor.create(string_literal110);
                    adaptor.addChild(root_0, string_literal110_tree);

                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport1094);
                    qualifiedIdent111=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent111.getTree());
                    char_literal112=(Token)match(input,100,FOLLOW_100_in_actorImport1096); 
                    char_literal112_tree = (Object)adaptor.create(char_literal112);
                    adaptor.addChild(root_0, char_literal112_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:3: qualifiedIdent ';'
                    {
                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport1102);
                    qualifiedIdent113=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent113.getTree());
                    char_literal114=(Token)match(input,100,FOLLOW_100_in_actorImport1104); 
                    char_literal114_tree = (Object)adaptor.create(char_literal114);
                    adaptor.addChild(root_0, char_literal114_tree);

                     

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "actorImport"

    public static class actorParameter_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actorParameter"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:146:1: actorParameter : typeDef ID ( '=' expression )? -> ^( VARIABLE typeDef ID ( expression )? ) ;
    public final RVCCalParser.actorParameter_return actorParameter() throws RecognitionException {
        RVCCalParser.actorParameter_return retval = new RVCCalParser.actorParameter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID116=null;
        Token char_literal117=null;
        RVCCalParser.typeDef_return typeDef115 = null;

        RVCCalParser.expression_return expression118 = null;


        Object ID116_tree=null;
        Object char_literal117_tree=null;
        RewriteRuleTokenStream stream_98=new RewriteRuleTokenStream(adaptor,"token 98");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:15: ( typeDef ID ( '=' expression )? -> ^( VARIABLE typeDef ID ( expression )? ) )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:2: typeDef ID ( '=' expression )?
            {
            pushFollow(FOLLOW_typeDef_in_actorParameter1119);
            typeDef115=typeDef();

            state._fsp--;

            stream_typeDef.add(typeDef115.getTree());
            ID116=(Token)match(input,ID,FOLLOW_ID_in_actorParameter1121);  
            stream_ID.add(ID116);

            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:13: ( '=' expression )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==98) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:14: '=' expression
                    {
                    char_literal117=(Token)match(input,98,FOLLOW_98_in_actorParameter1124);  
                    stream_98.add(char_literal117);

                    pushFollow(FOLLOW_expression_in_actorParameter1126);
                    expression118=expression();

                    state._fsp--;

                    stream_expression.add(expression118.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: expression, typeDef, ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 149:31: -> ^( VARIABLE typeDef ID ( expression )? )
            {
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:34: ^( VARIABLE typeDef ID ( expression )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLE, "VARIABLE"), root_1);

                adaptor.addChild(root_1, stream_typeDef.nextTree());
                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:56: ( expression )?
                if ( stream_expression.hasNext() ) {
                    adaptor.addChild(root_1, stream_expression.nextTree());

                }
                stream_expression.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "actorParameter"

    public static class actorParameters_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actorParameters"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:1: actorParameters : actorParameter ( ',' actorParameter )* -> ( actorParameter )+ ;
    public final RVCCalParser.actorParameters_return actorParameters() throws RecognitionException {
        RVCCalParser.actorParameters_return retval = new RVCCalParser.actorParameters_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal120=null;
        RVCCalParser.actorParameter_return actorParameter119 = null;

        RVCCalParser.actorParameter_return actorParameter121 = null;


        Object char_literal120_tree=null;
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleSubtreeStream stream_actorParameter=new RewriteRuleSubtreeStream(adaptor,"rule actorParameter");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:16: ( actorParameter ( ',' actorParameter )* -> ( actorParameter )+ )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:18: actorParameter ( ',' actorParameter )*
            {
            pushFollow(FOLLOW_actorParameter_in_actorParameters1148);
            actorParameter119=actorParameter();

            state._fsp--;

            stream_actorParameter.add(actorParameter119.getTree());
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:33: ( ',' actorParameter )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==90) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:34: ',' actorParameter
            	    {
            	    char_literal120=(Token)match(input,90,FOLLOW_90_in_actorParameters1151);  
            	    stream_90.add(char_literal120);

            	    pushFollow(FOLLOW_actorParameter_in_actorParameters1153);
            	    actorParameter121=actorParameter();

            	    state._fsp--;

            	    stream_actorParameter.add(actorParameter121.getTree());

            	    }
            	    break;

            	default :
            	    break loop52;
                }
            } while (true);



            // AST REWRITE
            // elements: actorParameter
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 151:55: -> ( actorParameter )+
            {
                if ( !(stream_actorParameter.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_actorParameter.hasNext() ) {
                    adaptor.addChild(root_0, stream_actorParameter.nextTree());

                }
                stream_actorParameter.reset();

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "actorParameters"

    public static class actorPortDecls_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actorPortDecls"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:1: actorPortDecls : varDeclNoExpr ( ',' varDeclNoExpr )* -> ( varDeclNoExpr )+ ;
    public final RVCCalParser.actorPortDecls_return actorPortDecls() throws RecognitionException {
        RVCCalParser.actorPortDecls_return retval = new RVCCalParser.actorPortDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal123=null;
        RVCCalParser.varDeclNoExpr_return varDeclNoExpr122 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr124 = null;


        Object char_literal123_tree=null;
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleSubtreeStream stream_varDeclNoExpr=new RewriteRuleSubtreeStream(adaptor,"rule varDeclNoExpr");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:156:15: ( varDeclNoExpr ( ',' varDeclNoExpr )* -> ( varDeclNoExpr )+ )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:156:17: varDeclNoExpr ( ',' varDeclNoExpr )*
            {
            pushFollow(FOLLOW_varDeclNoExpr_in_actorPortDecls1172);
            varDeclNoExpr122=varDeclNoExpr();

            state._fsp--;

            stream_varDeclNoExpr.add(varDeclNoExpr122.getTree());
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:156:31: ( ',' varDeclNoExpr )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==90) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:156:32: ',' varDeclNoExpr
            	    {
            	    char_literal123=(Token)match(input,90,FOLLOW_90_in_actorPortDecls1175);  
            	    stream_90.add(char_literal123);

            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorPortDecls1177);
            	    varDeclNoExpr124=varDeclNoExpr();

            	    state._fsp--;

            	    stream_varDeclNoExpr.add(varDeclNoExpr124.getTree());

            	    }
            	    break;

            	default :
            	    break loop53;
                }
            } while (true);



            // AST REWRITE
            // elements: varDeclNoExpr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 156:52: -> ( varDeclNoExpr )+
            {
                if ( !(stream_varDeclNoExpr.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_varDeclNoExpr.hasNext() ) {
                    adaptor.addChild(root_0, stream_varDeclNoExpr.nextTree());

                }
                stream_varDeclNoExpr.reset();

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "actorPortDecls"

    public static class expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:158:1: expression : un_expr ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr ) ;
    public final RVCCalParser.expression_return expression() throws RecognitionException {
        RVCCalParser.expression_return retval = new RVCCalParser.expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        RVCCalParser.un_expr_return un_expr125 = null;

        RVCCalParser.bop_return bop126 = null;

        RVCCalParser.un_expr_return un_expr127 = null;


        RewriteRuleSubtreeStream stream_bop=new RewriteRuleSubtreeStream(adaptor,"rule bop");
        RewriteRuleSubtreeStream stream_un_expr=new RewriteRuleSubtreeStream(adaptor,"rule un_expr");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:11: ( un_expr ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr ) )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:13: un_expr ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr )
            {
            pushFollow(FOLLOW_un_expr_in_expression1198);
            un_expr125=un_expr();

            state._fsp--;

            stream_un_expr.add(un_expr125.getTree());
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:3: ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr )
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( ((LA55_0>=PLUS && LA55_0<=DIV)||LA55_0==98||(LA55_0>=105 && LA55_0<=120)) ) {
                alt55=1;
            }
            else if ( (LA55_0==GUARD||LA55_0==87||(LA55_0>=89 && LA55_0<=91)||(LA55_0>=93 && LA55_0<=95)||LA55_0==97||LA55_0==100||LA55_0==102||(LA55_0>=124 && LA55_0<=125)||LA55_0==132) ) {
                alt55=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;
            }
            switch (alt55) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:4: ( bop un_expr )+
                    {
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:4: ( bop un_expr )+
                    int cnt54=0;
                    loop54:
                    do {
                        int alt54=2;
                        int LA54_0 = input.LA(1);

                        if ( ((LA54_0>=PLUS && LA54_0<=DIV)||LA54_0==98||(LA54_0>=105 && LA54_0<=120)) ) {
                            alt54=1;
                        }


                        switch (alt54) {
                    	case 1 :
                    	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:5: bop un_expr
                    	    {
                    	    pushFollow(FOLLOW_bop_in_expression1204);
                    	    bop126=bop();

                    	    state._fsp--;

                    	    stream_bop.add(bop126.getTree());
                    	    pushFollow(FOLLOW_un_expr_in_expression1206);
                    	    un_expr127=un_expr();

                    	    state._fsp--;

                    	    stream_un_expr.add(un_expr127.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt54 >= 1 ) break loop54;
                                EarlyExitException eee =
                                    new EarlyExitException(54, input);
                                throw eee;
                        }
                        cnt54++;
                    } while (true);



                    // AST REWRITE
                    // elements: un_expr, bop
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 164:19: -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) )
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:22: ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BINARY, "EXPR_BINARY"), root_1);

                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:36: ^( EXPR ( un_expr )+ )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR, "EXPR"), root_2);

                        if ( !(stream_un_expr.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_un_expr.hasNext() ) {
                            adaptor.addChild(root_2, stream_un_expr.nextTree());

                        }
                        stream_un_expr.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:53: ^( OP ( bop )+ )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OP, "OP"), root_2);

                        if ( !(stream_bop.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_bop.hasNext() ) {
                            adaptor.addChild(root_2, stream_bop.nextTree());

                        }
                        stream_bop.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:165:5: 
                    {

                    // AST REWRITE
                    // elements: un_expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 165:5: -> un_expr
                    {
                        adaptor.addChild(root_0, stream_un_expr.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expression"

    public static class bop_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bop"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:1: bop : ( ( 'or' | '||' ) -> LOGIC_OR | ( 'and' | '&&' ) -> LOGIC_AND | '|' -> BITOR | '&' -> BITAND | '=' -> EQ | '!=' -> NE | '<' -> LT | '>' -> GT | '<=' -> LE | '>=' -> GE | '<<' -> SHIFT_LEFT | '>>' -> SHIFT_RIGHT | PLUS -> PLUS | MINUS -> MINUS | DIV -> DIV | 'div' -> DIV_INT | 'mod' -> MOD | TIMES -> TIMES | '^' -> EXP );
    public final RVCCalParser.bop_return bop() throws RecognitionException {
        RVCCalParser.bop_return retval = new RVCCalParser.bop_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal128=null;
        Token string_literal129=null;
        Token string_literal130=null;
        Token string_literal131=null;
        Token char_literal132=null;
        Token char_literal133=null;
        Token char_literal134=null;
        Token string_literal135=null;
        Token char_literal136=null;
        Token char_literal137=null;
        Token string_literal138=null;
        Token string_literal139=null;
        Token string_literal140=null;
        Token string_literal141=null;
        Token PLUS142=null;
        Token MINUS143=null;
        Token DIV144=null;
        Token string_literal145=null;
        Token string_literal146=null;
        Token TIMES147=null;
        Token char_literal148=null;

        Object string_literal128_tree=null;
        Object string_literal129_tree=null;
        Object string_literal130_tree=null;
        Object string_literal131_tree=null;
        Object char_literal132_tree=null;
        Object char_literal133_tree=null;
        Object char_literal134_tree=null;
        Object string_literal135_tree=null;
        Object char_literal136_tree=null;
        Object char_literal137_tree=null;
        Object string_literal138_tree=null;
        Object string_literal139_tree=null;
        Object string_literal140_tree=null;
        Object string_literal141_tree=null;
        Object PLUS142_tree=null;
        Object MINUS143_tree=null;
        Object DIV144_tree=null;
        Object string_literal145_tree=null;
        Object string_literal146_tree=null;
        Object TIMES147_tree=null;
        Object char_literal148_tree=null;
        RewriteRuleTokenStream stream_98=new RewriteRuleTokenStream(adaptor,"token 98");
        RewriteRuleTokenStream stream_116=new RewriteRuleTokenStream(adaptor,"token 116");
        RewriteRuleTokenStream stream_117=new RewriteRuleTokenStream(adaptor,"token 117");
        RewriteRuleTokenStream stream_114=new RewriteRuleTokenStream(adaptor,"token 114");
        RewriteRuleTokenStream stream_115=new RewriteRuleTokenStream(adaptor,"token 115");
        RewriteRuleTokenStream stream_112=new RewriteRuleTokenStream(adaptor,"token 112");
        RewriteRuleTokenStream stream_113=new RewriteRuleTokenStream(adaptor,"token 113");
        RewriteRuleTokenStream stream_110=new RewriteRuleTokenStream(adaptor,"token 110");
        RewriteRuleTokenStream stream_111=new RewriteRuleTokenStream(adaptor,"token 111");
        RewriteRuleTokenStream stream_118=new RewriteRuleTokenStream(adaptor,"token 118");
        RewriteRuleTokenStream stream_119=new RewriteRuleTokenStream(adaptor,"token 119");
        RewriteRuleTokenStream stream_PLUS=new RewriteRuleTokenStream(adaptor,"token PLUS");
        RewriteRuleTokenStream stream_109=new RewriteRuleTokenStream(adaptor,"token 109");
        RewriteRuleTokenStream stream_108=new RewriteRuleTokenStream(adaptor,"token 108");
        RewriteRuleTokenStream stream_DIV=new RewriteRuleTokenStream(adaptor,"token DIV");
        RewriteRuleTokenStream stream_MINUS=new RewriteRuleTokenStream(adaptor,"token MINUS");
        RewriteRuleTokenStream stream_107=new RewriteRuleTokenStream(adaptor,"token 107");
        RewriteRuleTokenStream stream_TIMES=new RewriteRuleTokenStream(adaptor,"token TIMES");
        RewriteRuleTokenStream stream_106=new RewriteRuleTokenStream(adaptor,"token 106");
        RewriteRuleTokenStream stream_105=new RewriteRuleTokenStream(adaptor,"token 105");
        RewriteRuleTokenStream stream_120=new RewriteRuleTokenStream(adaptor,"token 120");

        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:4: ( ( 'or' | '||' ) -> LOGIC_OR | ( 'and' | '&&' ) -> LOGIC_AND | '|' -> BITOR | '&' -> BITAND | '=' -> EQ | '!=' -> NE | '<' -> LT | '>' -> GT | '<=' -> LE | '>=' -> GE | '<<' -> SHIFT_LEFT | '>>' -> SHIFT_RIGHT | PLUS -> PLUS | MINUS -> MINUS | DIV -> DIV | 'div' -> DIV_INT | 'mod' -> MOD | TIMES -> TIMES | '^' -> EXP )
            int alt58=19;
            alt58 = dfa58.predict(input);
            switch (alt58) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:6: ( 'or' | '||' )
                    {
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:6: ( 'or' | '||' )
                    int alt56=2;
                    int LA56_0 = input.LA(1);

                    if ( (LA56_0==105) ) {
                        alt56=1;
                    }
                    else if ( (LA56_0==106) ) {
                        alt56=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 56, 0, input);

                        throw nvae;
                    }
                    switch (alt56) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:7: 'or'
                            {
                            string_literal128=(Token)match(input,105,FOLLOW_105_in_bop1245);  
                            stream_105.add(string_literal128);


                            }
                            break;
                        case 2 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:14: '||'
                            {
                            string_literal129=(Token)match(input,106,FOLLOW_106_in_bop1249);  
                            stream_106.add(string_literal129);


                            }
                            break;

                    }



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 167:20: -> LOGIC_OR
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(LOGIC_OR, "LOGIC_OR"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:3: ( 'and' | '&&' )
                    {
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:3: ( 'and' | '&&' )
                    int alt57=2;
                    int LA57_0 = input.LA(1);

                    if ( (LA57_0==107) ) {
                        alt57=1;
                    }
                    else if ( (LA57_0==108) ) {
                        alt57=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 57, 0, input);

                        throw nvae;
                    }
                    switch (alt57) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:4: 'and'
                            {
                            string_literal130=(Token)match(input,107,FOLLOW_107_in_bop1259);  
                            stream_107.add(string_literal130);


                            }
                            break;
                        case 2 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:12: '&&'
                            {
                            string_literal131=(Token)match(input,108,FOLLOW_108_in_bop1263);  
                            stream_108.add(string_literal131);


                            }
                            break;

                    }



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 168:18: -> LOGIC_AND
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(LOGIC_AND, "LOGIC_AND"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:3: '|'
                    {
                    char_literal132=(Token)match(input,109,FOLLOW_109_in_bop1272);  
                    stream_109.add(char_literal132);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 169:7: -> BITOR
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(BITOR, "BITOR"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:3: '&'
                    {
                    char_literal133=(Token)match(input,110,FOLLOW_110_in_bop1280);  
                    stream_110.add(char_literal133);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 170:7: -> BITAND
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(BITAND, "BITAND"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:171:3: '='
                    {
                    char_literal134=(Token)match(input,98,FOLLOW_98_in_bop1288);  
                    stream_98.add(char_literal134);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 171:7: -> EQ
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(EQ, "EQ"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 6 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:171:15: '!='
                    {
                    string_literal135=(Token)match(input,111,FOLLOW_111_in_bop1296);  
                    stream_111.add(string_literal135);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 171:20: -> NE
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(NE, "NE"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 7 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:3: '<'
                    {
                    char_literal136=(Token)match(input,112,FOLLOW_112_in_bop1304);  
                    stream_112.add(char_literal136);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 172:7: -> LT
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(LT, "LT"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 8 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:15: '>'
                    {
                    char_literal137=(Token)match(input,113,FOLLOW_113_in_bop1312);  
                    stream_113.add(char_literal137);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 172:19: -> GT
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(GT, "GT"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 9 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:27: '<='
                    {
                    string_literal138=(Token)match(input,114,FOLLOW_114_in_bop1320);  
                    stream_114.add(string_literal138);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 172:32: -> LE
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(LE, "LE"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 10 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:40: '>='
                    {
                    string_literal139=(Token)match(input,115,FOLLOW_115_in_bop1328);  
                    stream_115.add(string_literal139);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 172:45: -> GE
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(GE, "GE"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 11 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:3: '<<'
                    {
                    string_literal140=(Token)match(input,116,FOLLOW_116_in_bop1336);  
                    stream_116.add(string_literal140);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 173:8: -> SHIFT_LEFT
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(SHIFT_LEFT, "SHIFT_LEFT"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 12 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:24: '>>'
                    {
                    string_literal141=(Token)match(input,117,FOLLOW_117_in_bop1344);  
                    stream_117.add(string_literal141);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 173:29: -> SHIFT_RIGHT
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(SHIFT_RIGHT, "SHIFT_RIGHT"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 13 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:174:3: PLUS
                    {
                    PLUS142=(Token)match(input,PLUS,FOLLOW_PLUS_in_bop1352);  
                    stream_PLUS.add(PLUS142);



                    // AST REWRITE
                    // elements: PLUS
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 174:8: -> PLUS
                    {
                        adaptor.addChild(root_0, stream_PLUS.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 14 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:174:18: MINUS
                    {
                    MINUS143=(Token)match(input,MINUS,FOLLOW_MINUS_in_bop1360);  
                    stream_MINUS.add(MINUS143);



                    // AST REWRITE
                    // elements: MINUS
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 174:24: -> MINUS
                    {
                        adaptor.addChild(root_0, stream_MINUS.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 15 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:3: DIV
                    {
                    DIV144=(Token)match(input,DIV,FOLLOW_DIV_in_bop1368);  
                    stream_DIV.add(DIV144);



                    // AST REWRITE
                    // elements: DIV
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 175:7: -> DIV
                    {
                        adaptor.addChild(root_0, stream_DIV.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 16 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:16: 'div'
                    {
                    string_literal145=(Token)match(input,118,FOLLOW_118_in_bop1376);  
                    stream_118.add(string_literal145);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 175:22: -> DIV_INT
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(DIV_INT, "DIV_INT"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 17 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:35: 'mod'
                    {
                    string_literal146=(Token)match(input,119,FOLLOW_119_in_bop1384);  
                    stream_119.add(string_literal146);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 175:41: -> MOD
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(MOD, "MOD"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 18 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:50: TIMES
                    {
                    TIMES147=(Token)match(input,TIMES,FOLLOW_TIMES_in_bop1392);  
                    stream_TIMES.add(TIMES147);



                    // AST REWRITE
                    // elements: TIMES
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 175:56: -> TIMES
                    {
                        adaptor.addChild(root_0, stream_TIMES.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 19 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:3: '^'
                    {
                    char_literal148=(Token)match(input,120,FOLLOW_120_in_bop1400);  
                    stream_120.add(char_literal148);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 176:7: -> EXP
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(EXP, "EXP"));

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "bop"

    public static class un_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "un_expr"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:1: un_expr : ( postfix_expression -> postfix_expression | un_op un_expr -> ^( EXPR_UNARY un_op un_expr ) );
    public final RVCCalParser.un_expr_return un_expr() throws RecognitionException {
        RVCCalParser.un_expr_return retval = new RVCCalParser.un_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        RVCCalParser.postfix_expression_return postfix_expression149 = null;

        RVCCalParser.un_op_return un_op150 = null;

        RVCCalParser.un_expr_return un_expr151 = null;


        RewriteRuleSubtreeStream stream_postfix_expression=new RewriteRuleSubtreeStream(adaptor,"rule postfix_expression");
        RewriteRuleSubtreeStream stream_un_expr=new RewriteRuleSubtreeStream(adaptor,"rule un_expr");
        RewriteRuleSubtreeStream stream_un_op=new RewriteRuleSubtreeStream(adaptor,"rule un_op");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:8: ( postfix_expression -> postfix_expression | un_op un_expr -> ^( EXPR_UNARY un_op un_expr ) )
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==ID||LA59_0==FLOAT||LA59_0==INTEGER||LA59_0==STRING||LA59_0==88||LA59_0==92||LA59_0==123||(LA59_0>=126 && LA59_0<=127)) ) {
                alt59=1;
            }
            else if ( (LA59_0==MINUS||(LA59_0>=121 && LA59_0<=122)) ) {
                alt59=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }
            switch (alt59) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:10: postfix_expression
                    {
                    pushFollow(FOLLOW_postfix_expression_in_un_expr1411);
                    postfix_expression149=postfix_expression();

                    state._fsp--;

                    stream_postfix_expression.add(postfix_expression149.getTree());


                    // AST REWRITE
                    // elements: postfix_expression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 178:29: -> postfix_expression
                    {
                        adaptor.addChild(root_0, stream_postfix_expression.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:179:5: un_op un_expr
                    {
                    pushFollow(FOLLOW_un_op_in_un_expr1421);
                    un_op150=un_op();

                    state._fsp--;

                    stream_un_op.add(un_op150.getTree());
                    pushFollow(FOLLOW_un_expr_in_un_expr1423);
                    un_expr151=un_expr();

                    state._fsp--;

                    stream_un_expr.add(un_expr151.getTree());


                    // AST REWRITE
                    // elements: un_expr, un_op
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 179:19: -> ^( EXPR_UNARY un_op un_expr )
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:179:22: ^( EXPR_UNARY un_op un_expr )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_UNARY, "EXPR_UNARY"), root_1);

                        adaptor.addChild(root_1, stream_un_op.nextTree());
                        adaptor.addChild(root_1, stream_un_expr.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "un_expr"

    public static class un_op_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "un_op"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:1: un_op : ( MINUS -> MINUS | 'not' -> LOGIC_NOT | '#' -> NUM_ELTS );
    public final RVCCalParser.un_op_return un_op() throws RecognitionException {
        RVCCalParser.un_op_return retval = new RVCCalParser.un_op_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MINUS152=null;
        Token string_literal153=null;
        Token char_literal154=null;

        Object MINUS152_tree=null;
        Object string_literal153_tree=null;
        Object char_literal154_tree=null;
        RewriteRuleTokenStream stream_121=new RewriteRuleTokenStream(adaptor,"token 121");
        RewriteRuleTokenStream stream_122=new RewriteRuleTokenStream(adaptor,"token 122");
        RewriteRuleTokenStream stream_MINUS=new RewriteRuleTokenStream(adaptor,"token MINUS");

        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:6: ( MINUS -> MINUS | 'not' -> LOGIC_NOT | '#' -> NUM_ELTS )
            int alt60=3;
            switch ( input.LA(1) ) {
            case MINUS:
                {
                alt60=1;
                }
                break;
            case 121:
                {
                alt60=2;
                }
                break;
            case 122:
                {
                alt60=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;
            }

            switch (alt60) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:8: MINUS
                    {
                    MINUS152=(Token)match(input,MINUS,FOLLOW_MINUS_in_un_op1440);  
                    stream_MINUS.add(MINUS152);



                    // AST REWRITE
                    // elements: MINUS
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 181:14: -> MINUS
                    {
                        adaptor.addChild(root_0, stream_MINUS.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:25: 'not'
                    {
                    string_literal153=(Token)match(input,121,FOLLOW_121_in_un_op1448);  
                    stream_121.add(string_literal153);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 181:31: -> LOGIC_NOT
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(LOGIC_NOT, "LOGIC_NOT"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:46: '#'
                    {
                    char_literal154=(Token)match(input,122,FOLLOW_122_in_un_op1456);  
                    stream_122.add(char_literal154);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 181:50: -> NUM_ELTS
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(NUM_ELTS, "NUM_ELTS"));

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "un_op"

    public static class postfix_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "postfix_expression"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:1: postfix_expression : ( '[' e= expressions ( ':' g= expressionGenerators )? ']' -> ^( EXPR_LIST $e ( $g)? ) | 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end' -> ^( EXPR_IF $e1 $e2 $e3) | constant -> constant | '(' expression ')' -> expression | var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) ) );
    public final RVCCalParser.postfix_expression_return postfix_expression() throws RecognitionException {
        RVCCalParser.postfix_expression_return retval = new RVCCalParser.postfix_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token var=null;
        Token char_literal155=null;
        Token char_literal156=null;
        Token char_literal157=null;
        Token string_literal158=null;
        Token string_literal159=null;
        Token string_literal160=null;
        Token string_literal161=null;
        Token char_literal163=null;
        Token char_literal165=null;
        Token char_literal166=null;
        Token char_literal168=null;
        Token char_literal169=null;
        Token char_literal171=null;
        RVCCalParser.expressions_return e = null;

        RVCCalParser.expressionGenerators_return g = null;

        RVCCalParser.expression_return e1 = null;

        RVCCalParser.expression_return e2 = null;

        RVCCalParser.expression_return e3 = null;

        RVCCalParser.constant_return constant162 = null;

        RVCCalParser.expression_return expression164 = null;

        RVCCalParser.expressions_return expressions167 = null;

        RVCCalParser.expressions_return expressions170 = null;


        Object var_tree=null;
        Object char_literal155_tree=null;
        Object char_literal156_tree=null;
        Object char_literal157_tree=null;
        Object string_literal158_tree=null;
        Object string_literal159_tree=null;
        Object string_literal160_tree=null;
        Object string_literal161_tree=null;
        Object char_literal163_tree=null;
        Object char_literal165_tree=null;
        Object char_literal166_tree=null;
        Object char_literal168_tree=null;
        Object char_literal169_tree=null;
        Object char_literal171_tree=null;
        RewriteRuleTokenStream stream_125=new RewriteRuleTokenStream(adaptor,"token 125");
        RewriteRuleTokenStream stream_95=new RewriteRuleTokenStream(adaptor,"token 95");
        RewriteRuleTokenStream stream_93=new RewriteRuleTokenStream(adaptor,"token 93");
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleTokenStream stream_123=new RewriteRuleTokenStream(adaptor,"token 123");
        RewriteRuleTokenStream stream_124=new RewriteRuleTokenStream(adaptor,"token 124");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleTokenStream stream_88=new RewriteRuleTokenStream(adaptor,"token 88");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_constant=new RewriteRuleSubtreeStream(adaptor,"rule constant");
        RewriteRuleSubtreeStream stream_expressionGenerators=new RewriteRuleSubtreeStream(adaptor,"rule expressionGenerators");
        RewriteRuleSubtreeStream stream_expressions=new RewriteRuleSubtreeStream(adaptor,"rule expressions");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:19: ( '[' e= expressions ( ':' g= expressionGenerators )? ']' -> ^( EXPR_LIST $e ( $g)? ) | 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end' -> ^( EXPR_IF $e1 $e2 $e3) | constant -> constant | '(' expression ')' -> expression | var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) ) )
            int alt65=5;
            switch ( input.LA(1) ) {
            case 88:
                {
                alt65=1;
                }
                break;
            case 123:
                {
                alt65=2;
                }
                break;
            case FLOAT:
            case INTEGER:
            case STRING:
            case 126:
            case 127:
                {
                alt65=3;
                }
                break;
            case 92:
                {
                alt65=4;
                }
                break;
            case ID:
                {
                alt65=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 65, 0, input);

                throw nvae;
            }

            switch (alt65) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:3: '[' e= expressions ( ':' g= expressionGenerators )? ']'
                    {
                    char_literal155=(Token)match(input,88,FOLLOW_88_in_postfix_expression1469);  
                    stream_88.add(char_literal155);

                    pushFollow(FOLLOW_expressions_in_postfix_expression1473);
                    e=expressions();

                    state._fsp--;

                    stream_expressions.add(e.getTree());
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:21: ( ':' g= expressionGenerators )?
                    int alt61=2;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==87) ) {
                        alt61=1;
                    }
                    switch (alt61) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:22: ':' g= expressionGenerators
                            {
                            char_literal156=(Token)match(input,87,FOLLOW_87_in_postfix_expression1476);  
                            stream_87.add(char_literal156);

                            pushFollow(FOLLOW_expressionGenerators_in_postfix_expression1480);
                            g=expressionGenerators();

                            state._fsp--;

                            stream_expressionGenerators.add(g.getTree());

                            }
                            break;

                    }

                    char_literal157=(Token)match(input,89,FOLLOW_89_in_postfix_expression1484);  
                    stream_89.add(char_literal157);



                    // AST REWRITE
                    // elements: g, e
                    // token labels: 
                    // rule labels: g, retval, e
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_g=new RewriteRuleSubtreeStream(adaptor,"rule g",g!=null?g.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e=new RewriteRuleSubtreeStream(adaptor,"rule e",e!=null?e.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 184:55: -> ^( EXPR_LIST $e ( $g)? )
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:58: ^( EXPR_LIST $e ( $g)? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_LIST, "EXPR_LIST"), root_1);

                        adaptor.addChild(root_1, stream_e.nextTree());
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:73: ( $g)?
                        if ( stream_g.hasNext() ) {
                            adaptor.addChild(root_1, stream_g.nextTree());

                        }
                        stream_g.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:185:3: 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end'
                    {
                    string_literal158=(Token)match(input,123,FOLLOW_123_in_postfix_expression1501);  
                    stream_123.add(string_literal158);

                    pushFollow(FOLLOW_expression_in_postfix_expression1505);
                    e1=expression();

                    state._fsp--;

                    stream_expression.add(e1.getTree());
                    string_literal159=(Token)match(input,124,FOLLOW_124_in_postfix_expression1507);  
                    stream_124.add(string_literal159);

                    pushFollow(FOLLOW_expression_in_postfix_expression1511);
                    e2=expression();

                    state._fsp--;

                    stream_expression.add(e2.getTree());
                    string_literal160=(Token)match(input,125,FOLLOW_125_in_postfix_expression1513);  
                    stream_125.add(string_literal160);

                    pushFollow(FOLLOW_expression_in_postfix_expression1517);
                    e3=expression();

                    state._fsp--;

                    stream_expression.add(e3.getTree());
                    string_literal161=(Token)match(input,95,FOLLOW_95_in_postfix_expression1519);  
                    stream_95.add(string_literal161);



                    // AST REWRITE
                    // elements: e2, e1, e3
                    // token labels: 
                    // rule labels: e3, retval, e1, e2
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_e3=new RewriteRuleSubtreeStream(adaptor,"rule e3",e3!=null?e3.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
                    RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 185:70: -> ^( EXPR_IF $e1 $e2 $e3)
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:185:73: ^( EXPR_IF $e1 $e2 $e3)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_IF, "EXPR_IF"), root_1);

                        adaptor.addChild(root_1, stream_e1.nextTree());
                        adaptor.addChild(root_1, stream_e2.nextTree());
                        adaptor.addChild(root_1, stream_e3.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:186:3: constant
                    {
                    pushFollow(FOLLOW_constant_in_postfix_expression1538);
                    constant162=constant();

                    state._fsp--;

                    stream_constant.add(constant162.getTree());


                    // AST REWRITE
                    // elements: constant
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 186:12: -> constant
                    {
                        adaptor.addChild(root_0, stream_constant.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:187:3: '(' expression ')'
                    {
                    char_literal163=(Token)match(input,92,FOLLOW_92_in_postfix_expression1546);  
                    stream_92.add(char_literal163);

                    pushFollow(FOLLOW_expression_in_postfix_expression1548);
                    expression164=expression();

                    state._fsp--;

                    stream_expression.add(expression164.getTree());
                    char_literal165=(Token)match(input,93,FOLLOW_93_in_postfix_expression1550);  
                    stream_93.add(char_literal165);



                    // AST REWRITE
                    // elements: expression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 187:22: -> expression
                    {
                        adaptor.addChild(root_0, stream_expression.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:3: var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) )
                    {
                    var=(Token)match(input,ID,FOLLOW_ID_in_postfix_expression1560);  
                    stream_ID.add(var);

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:10: ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) )
                    int alt64=3;
                    switch ( input.LA(1) ) {
                    case 92:
                        {
                        alt64=1;
                        }
                        break;
                    case 88:
                        {
                        alt64=2;
                        }
                        break;
                    case GUARD:
                    case PLUS:
                    case MINUS:
                    case TIMES:
                    case DIV:
                    case 87:
                    case 89:
                    case 90:
                    case 91:
                    case 93:
                    case 94:
                    case 95:
                    case 97:
                    case 98:
                    case 100:
                    case 102:
                    case 105:
                    case 106:
                    case 107:
                    case 108:
                    case 109:
                    case 110:
                    case 111:
                    case 112:
                    case 113:
                    case 114:
                    case 115:
                    case 116:
                    case 117:
                    case 118:
                    case 119:
                    case 120:
                    case 124:
                    case 125:
                    case 132:
                        {
                        alt64=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 64, 0, input);

                        throw nvae;
                    }

                    switch (alt64) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:5: '(' ( expressions )? ')'
                            {
                            char_literal166=(Token)match(input,92,FOLLOW_92_in_postfix_expression1568);  
                            stream_92.add(char_literal166);

                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:9: ( expressions )?
                            int alt62=2;
                            int LA62_0 = input.LA(1);

                            if ( (LA62_0==MINUS||LA62_0==ID||LA62_0==FLOAT||LA62_0==INTEGER||LA62_0==STRING||LA62_0==88||LA62_0==92||(LA62_0>=121 && LA62_0<=123)||(LA62_0>=126 && LA62_0<=127)) ) {
                                alt62=1;
                            }
                            switch (alt62) {
                                case 1 :
                                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:9: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_postfix_expression1570);
                                    expressions167=expressions();

                                    state._fsp--;

                                    stream_expressions.add(expressions167.getTree());

                                    }
                                    break;

                            }

                            char_literal168=(Token)match(input,93,FOLLOW_93_in_postfix_expression1573);  
                            stream_93.add(char_literal168);



                            // AST REWRITE
                            // elements: var, expressions
                            // token labels: var
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleTokenStream stream_var=new RewriteRuleTokenStream(adaptor,"token var",var);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 189:26: -> ^( EXPR_CALL $var ( expressions )? )
                            {
                                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:29: ^( EXPR_CALL $var ( expressions )? )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_CALL, "EXPR_CALL"), root_1);

                                adaptor.addChild(root_1, stream_var.nextNode());
                                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:46: ( expressions )?
                                if ( stream_expressions.hasNext() ) {
                                    adaptor.addChild(root_1, stream_expressions.nextTree());

                                }
                                stream_expressions.reset();

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;
                            }
                            break;
                        case 2 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:190:6: ( '[' expressions ']' )+
                            {
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:190:6: ( '[' expressions ']' )+
                            int cnt63=0;
                            loop63:
                            do {
                                int alt63=2;
                                int LA63_0 = input.LA(1);

                                if ( (LA63_0==88) ) {
                                    alt63=1;
                                }


                                switch (alt63) {
                            	case 1 :
                            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:190:7: '[' expressions ']'
                            	    {
                            	    char_literal169=(Token)match(input,88,FOLLOW_88_in_postfix_expression1593);  
                            	    stream_88.add(char_literal169);

                            	    pushFollow(FOLLOW_expressions_in_postfix_expression1595);
                            	    expressions170=expressions();

                            	    state._fsp--;

                            	    stream_expressions.add(expressions170.getTree());
                            	    char_literal171=(Token)match(input,89,FOLLOW_89_in_postfix_expression1597);  
                            	    stream_89.add(char_literal171);


                            	    }
                            	    break;

                            	default :
                            	    if ( cnt63 >= 1 ) break loop63;
                                        EarlyExitException eee =
                                            new EarlyExitException(63, input);
                                        throw eee;
                                }
                                cnt63++;
                            } while (true);



                            // AST REWRITE
                            // elements: var, expressions
                            // token labels: var
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleTokenStream stream_var=new RewriteRuleTokenStream(adaptor,"token var",var);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 190:29: -> ^( EXPR_IDX $var ( expressions )+ )
                            {
                                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:190:32: ^( EXPR_IDX $var ( expressions )+ )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_IDX, "EXPR_IDX"), root_1);

                                adaptor.addChild(root_1, stream_var.nextNode());
                                if ( !(stream_expressions.hasNext()) ) {
                                    throw new RewriteEarlyExitException();
                                }
                                while ( stream_expressions.hasNext() ) {
                                    adaptor.addChild(root_1, stream_expressions.nextTree());

                                }
                                stream_expressions.reset();

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;
                            }
                            break;
                        case 3 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:191:5: 
                            {

                            // AST REWRITE
                            // elements: var
                            // token labels: var
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleTokenStream stream_var=new RewriteRuleTokenStream(adaptor,"token var",var);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 191:5: -> ^( EXPR_VAR $var)
                            {
                                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:191:8: ^( EXPR_VAR $var)
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_VAR, "EXPR_VAR"), root_1);

                                adaptor.addChild(root_1, stream_var.nextNode());

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;
                            }
                            break;

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "postfix_expression"

    public static class constant_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "constant"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:193:1: constant : ( 'true' -> ^( EXPR_BOOL 'true' ) | 'false' -> ^( EXPR_BOOL 'false' ) | FLOAT -> ^( EXPR_FLOAT FLOAT ) | INTEGER -> ^( EXPR_INT INTEGER ) | STRING -> ^( EXPR_STRING STRING ) );
    public final RVCCalParser.constant_return constant() throws RecognitionException {
        RVCCalParser.constant_return retval = new RVCCalParser.constant_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal172=null;
        Token string_literal173=null;
        Token FLOAT174=null;
        Token INTEGER175=null;
        Token STRING176=null;

        Object string_literal172_tree=null;
        Object string_literal173_tree=null;
        Object FLOAT174_tree=null;
        Object INTEGER175_tree=null;
        Object STRING176_tree=null;
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_126=new RewriteRuleTokenStream(adaptor,"token 126");
        RewriteRuleTokenStream stream_127=new RewriteRuleTokenStream(adaptor,"token 127");
        RewriteRuleTokenStream stream_FLOAT=new RewriteRuleTokenStream(adaptor,"token FLOAT");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:193:9: ( 'true' -> ^( EXPR_BOOL 'true' ) | 'false' -> ^( EXPR_BOOL 'false' ) | FLOAT -> ^( EXPR_FLOAT FLOAT ) | INTEGER -> ^( EXPR_INT INTEGER ) | STRING -> ^( EXPR_STRING STRING ) )
            int alt66=5;
            switch ( input.LA(1) ) {
            case 126:
                {
                alt66=1;
                }
                break;
            case 127:
                {
                alt66=2;
                }
                break;
            case FLOAT:
                {
                alt66=3;
                }
                break;
            case INTEGER:
                {
                alt66=4;
                }
                break;
            case STRING:
                {
                alt66=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 66, 0, input);

                throw nvae;
            }

            switch (alt66) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:194:3: 'true'
                    {
                    string_literal172=(Token)match(input,126,FOLLOW_126_in_constant1634);  
                    stream_126.add(string_literal172);



                    // AST REWRITE
                    // elements: 126
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 194:10: -> ^( EXPR_BOOL 'true' )
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:194:13: ^( EXPR_BOOL 'true' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BOOL, "EXPR_BOOL"), root_1);

                        adaptor.addChild(root_1, stream_126.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:3: 'false'
                    {
                    string_literal173=(Token)match(input,127,FOLLOW_127_in_constant1646);  
                    stream_127.add(string_literal173);



                    // AST REWRITE
                    // elements: 127
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 195:11: -> ^( EXPR_BOOL 'false' )
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:14: ^( EXPR_BOOL 'false' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BOOL, "EXPR_BOOL"), root_1);

                        adaptor.addChild(root_1, stream_127.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:196:3: FLOAT
                    {
                    FLOAT174=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_constant1658);  
                    stream_FLOAT.add(FLOAT174);



                    // AST REWRITE
                    // elements: FLOAT
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 196:9: -> ^( EXPR_FLOAT FLOAT )
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:196:12: ^( EXPR_FLOAT FLOAT )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_FLOAT, "EXPR_FLOAT"), root_1);

                        adaptor.addChild(root_1, stream_FLOAT.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:197:3: INTEGER
                    {
                    INTEGER175=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_constant1670);  
                    stream_INTEGER.add(INTEGER175);



                    // AST REWRITE
                    // elements: INTEGER
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 197:11: -> ^( EXPR_INT INTEGER )
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:197:14: ^( EXPR_INT INTEGER )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_INT, "EXPR_INT"), root_1);

                        adaptor.addChild(root_1, stream_INTEGER.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:198:3: STRING
                    {
                    STRING176=(Token)match(input,STRING,FOLLOW_STRING_in_constant1682);  
                    stream_STRING.add(STRING176);



                    // AST REWRITE
                    // elements: STRING
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 198:10: -> ^( EXPR_STRING STRING )
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:198:13: ^( EXPR_STRING STRING )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_STRING, "EXPR_STRING"), root_1);

                        adaptor.addChild(root_1, stream_STRING.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "constant"

    public static class expressionGenerator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expressionGenerator"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:1: expressionGenerator : 'for' typeDef ID 'in' expression ;
    public final RVCCalParser.expressionGenerator_return expressionGenerator() throws RecognitionException {
        RVCCalParser.expressionGenerator_return retval = new RVCCalParser.expressionGenerator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal177=null;
        Token ID179=null;
        Token string_literal180=null;
        RVCCalParser.typeDef_return typeDef178 = null;

        RVCCalParser.expression_return expression181 = null;


        Object string_literal177_tree=null;
        Object ID179_tree=null;
        Object string_literal180_tree=null;

        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:20: ( 'for' typeDef ID 'in' expression )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:201:2: 'for' typeDef ID 'in' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal177=(Token)match(input,128,FOLLOW_128_in_expressionGenerator1698); 
            string_literal177_tree = (Object)adaptor.create(string_literal177);
            adaptor.addChild(root_0, string_literal177_tree);

            pushFollow(FOLLOW_typeDef_in_expressionGenerator1700);
            typeDef178=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef178.getTree());
            ID179=(Token)match(input,ID,FOLLOW_ID_in_expressionGenerator1702); 
            ID179_tree = (Object)adaptor.create(ID179);
            adaptor.addChild(root_0, ID179_tree);

            string_literal180=(Token)match(input,129,FOLLOW_129_in_expressionGenerator1704); 
            string_literal180_tree = (Object)adaptor.create(string_literal180);
            adaptor.addChild(root_0, string_literal180_tree);

            pushFollow(FOLLOW_expression_in_expressionGenerator1706);
            expression181=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression181.getTree());
             

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expressionGenerator"

    public static class expressionGenerators_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expressionGenerators"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:204:1: expressionGenerators : expressionGenerator ( ',' expressionGenerator )* -> ( expressionGenerator )+ ;
    public final RVCCalParser.expressionGenerators_return expressionGenerators() throws RecognitionException {
        RVCCalParser.expressionGenerators_return retval = new RVCCalParser.expressionGenerators_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal183=null;
        RVCCalParser.expressionGenerator_return expressionGenerator182 = null;

        RVCCalParser.expressionGenerator_return expressionGenerator184 = null;


        Object char_literal183_tree=null;
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleSubtreeStream stream_expressionGenerator=new RewriteRuleSubtreeStream(adaptor,"rule expressionGenerator");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:204:21: ( expressionGenerator ( ',' expressionGenerator )* -> ( expressionGenerator )+ )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:204:23: expressionGenerator ( ',' expressionGenerator )*
            {
            pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators1716);
            expressionGenerator182=expressionGenerator();

            state._fsp--;

            stream_expressionGenerator.add(expressionGenerator182.getTree());
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:204:43: ( ',' expressionGenerator )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==90) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:204:44: ',' expressionGenerator
            	    {
            	    char_literal183=(Token)match(input,90,FOLLOW_90_in_expressionGenerators1719);  
            	    stream_90.add(char_literal183);

            	    pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators1721);
            	    expressionGenerator184=expressionGenerator();

            	    state._fsp--;

            	    stream_expressionGenerator.add(expressionGenerator184.getTree());

            	    }
            	    break;

            	default :
            	    break loop67;
                }
            } while (true);



            // AST REWRITE
            // elements: expressionGenerator
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 204:70: -> ( expressionGenerator )+
            {
                if ( !(stream_expressionGenerator.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_expressionGenerator.hasNext() ) {
                    adaptor.addChild(root_0, stream_expressionGenerator.nextTree());

                }
                stream_expressionGenerator.reset();

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expressionGenerators"

    public static class expressions_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expressions"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:206:1: expressions : expression ( ',' expression )* -> ( expression )+ ;
    public final RVCCalParser.expressions_return expressions() throws RecognitionException {
        RVCCalParser.expressions_return retval = new RVCCalParser.expressions_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal186=null;
        RVCCalParser.expression_return expression185 = null;

        RVCCalParser.expression_return expression187 = null;


        Object char_literal186_tree=null;
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:206:12: ( expression ( ',' expression )* -> ( expression )+ )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:206:14: expression ( ',' expression )*
            {
            pushFollow(FOLLOW_expression_in_expressions1735);
            expression185=expression();

            state._fsp--;

            stream_expression.add(expression185.getTree());
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:206:25: ( ',' expression )*
            loop68:
            do {
                int alt68=2;
                int LA68_0 = input.LA(1);

                if ( (LA68_0==90) ) {
                    alt68=1;
                }


                switch (alt68) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:206:26: ',' expression
            	    {
            	    char_literal186=(Token)match(input,90,FOLLOW_90_in_expressions1738);  
            	    stream_90.add(char_literal186);

            	    pushFollow(FOLLOW_expression_in_expressions1740);
            	    expression187=expression();

            	    state._fsp--;

            	    stream_expression.add(expression187.getTree());

            	    }
            	    break;

            	default :
            	    break loop68;
                }
            } while (true);



            // AST REWRITE
            // elements: expression
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 206:43: -> ( expression )+
            {
                if ( !(stream_expression.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_expression.hasNext() ) {
                    adaptor.addChild(root_0, stream_expression.nextTree());

                }
                stream_expression.reset();

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expressions"

    public static class idents_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "idents"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:208:1: idents : ID ( ',' ID )* -> ( ID )+ ;
    public final RVCCalParser.idents_return idents() throws RecognitionException {
        RVCCalParser.idents_return retval = new RVCCalParser.idents_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID188=null;
        Token char_literal189=null;
        Token ID190=null;

        Object ID188_tree=null;
        Object char_literal189_tree=null;
        Object ID190_tree=null;
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");

        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:211:7: ( ID ( ',' ID )* -> ( ID )+ )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:211:9: ID ( ',' ID )*
            {
            ID188=(Token)match(input,ID,FOLLOW_ID_in_idents1759);  
            stream_ID.add(ID188);

            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:211:12: ( ',' ID )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==90) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:211:13: ',' ID
            	    {
            	    char_literal189=(Token)match(input,90,FOLLOW_90_in_idents1762);  
            	    stream_90.add(char_literal189);

            	    ID190=(Token)match(input,ID,FOLLOW_ID_in_idents1764);  
            	    stream_ID.add(ID190);


            	    }
            	    break;

            	default :
            	    break loop69;
                }
            } while (true);



            // AST REWRITE
            // elements: ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 211:22: -> ( ID )+
            {
                if ( !(stream_ID.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_ID.hasNext() ) {
                    adaptor.addChild(root_0, stream_ID.nextNode());

                }
                stream_ID.reset();

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "idents"

    public static class priorityInequality_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "priorityInequality"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:213:1: priorityInequality : qualifiedIdent ( '>' qualifiedIdent )+ ';' -> ^( INEQUALITY ( qualifiedIdent )+ ) ;
    public final RVCCalParser.priorityInequality_return priorityInequality() throws RecognitionException {
        RVCCalParser.priorityInequality_return retval = new RVCCalParser.priorityInequality_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal192=null;
        Token char_literal194=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent191 = null;

        RVCCalParser.qualifiedIdent_return qualifiedIdent193 = null;


        Object char_literal192_tree=null;
        Object char_literal194_tree=null;
        RewriteRuleTokenStream stream_113=new RewriteRuleTokenStream(adaptor,"token 113");
        RewriteRuleTokenStream stream_100=new RewriteRuleTokenStream(adaptor,"token 100");
        RewriteRuleSubtreeStream stream_qualifiedIdent=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedIdent");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:216:19: ( qualifiedIdent ( '>' qualifiedIdent )+ ';' -> ^( INEQUALITY ( qualifiedIdent )+ ) )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:216:21: qualifiedIdent ( '>' qualifiedIdent )+ ';'
            {
            pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality1783);
            qualifiedIdent191=qualifiedIdent();

            state._fsp--;

            stream_qualifiedIdent.add(qualifiedIdent191.getTree());
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:216:36: ( '>' qualifiedIdent )+
            int cnt70=0;
            loop70:
            do {
                int alt70=2;
                int LA70_0 = input.LA(1);

                if ( (LA70_0==113) ) {
                    alt70=1;
                }


                switch (alt70) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:216:37: '>' qualifiedIdent
            	    {
            	    char_literal192=(Token)match(input,113,FOLLOW_113_in_priorityInequality1786);  
            	    stream_113.add(char_literal192);

            	    pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality1788);
            	    qualifiedIdent193=qualifiedIdent();

            	    state._fsp--;

            	    stream_qualifiedIdent.add(qualifiedIdent193.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt70 >= 1 ) break loop70;
                        EarlyExitException eee =
                            new EarlyExitException(70, input);
                        throw eee;
                }
                cnt70++;
            } while (true);

            char_literal194=(Token)match(input,100,FOLLOW_100_in_priorityInequality1792);  
            stream_100.add(char_literal194);



            // AST REWRITE
            // elements: qualifiedIdent
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 216:62: -> ^( INEQUALITY ( qualifiedIdent )+ )
            {
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:216:65: ^( INEQUALITY ( qualifiedIdent )+ )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(INEQUALITY, "INEQUALITY"), root_1);

                if ( !(stream_qualifiedIdent.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_qualifiedIdent.hasNext() ) {
                    adaptor.addChild(root_1, stream_qualifiedIdent.nextTree());

                }
                stream_qualifiedIdent.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "priorityInequality"

    public static class priorityOrder_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "priorityOrder"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:218:1: priorityOrder : PRIORITY ( priorityInequality )* 'end' -> ^( PRIORITY ( priorityInequality )* ) ;
    public final RVCCalParser.priorityOrder_return priorityOrder() throws RecognitionException {
        RVCCalParser.priorityOrder_return retval = new RVCCalParser.priorityOrder_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRIORITY195=null;
        Token string_literal197=null;
        RVCCalParser.priorityInequality_return priorityInequality196 = null;


        Object PRIORITY195_tree=null;
        Object string_literal197_tree=null;
        RewriteRuleTokenStream stream_95=new RewriteRuleTokenStream(adaptor,"token 95");
        RewriteRuleTokenStream stream_PRIORITY=new RewriteRuleTokenStream(adaptor,"token PRIORITY");
        RewriteRuleSubtreeStream stream_priorityInequality=new RewriteRuleSubtreeStream(adaptor,"rule priorityInequality");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:218:14: ( PRIORITY ( priorityInequality )* 'end' -> ^( PRIORITY ( priorityInequality )* ) )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:218:16: PRIORITY ( priorityInequality )* 'end'
            {
            PRIORITY195=(Token)match(input,PRIORITY,FOLLOW_PRIORITY_in_priorityOrder1809);  
            stream_PRIORITY.add(PRIORITY195);

            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:218:25: ( priorityInequality )*
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( (LA71_0==ID) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:218:25: priorityInequality
            	    {
            	    pushFollow(FOLLOW_priorityInequality_in_priorityOrder1811);
            	    priorityInequality196=priorityInequality();

            	    state._fsp--;

            	    stream_priorityInequality.add(priorityInequality196.getTree());

            	    }
            	    break;

            	default :
            	    break loop71;
                }
            } while (true);

            string_literal197=(Token)match(input,95,FOLLOW_95_in_priorityOrder1814);  
            stream_95.add(string_literal197);



            // AST REWRITE
            // elements: PRIORITY, priorityInequality
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 218:51: -> ^( PRIORITY ( priorityInequality )* )
            {
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:218:54: ^( PRIORITY ( priorityInequality )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_PRIORITY.nextNode(), root_1);

                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:218:65: ( priorityInequality )*
                while ( stream_priorityInequality.hasNext() ) {
                    adaptor.addChild(root_1, stream_priorityInequality.nextTree());

                }
                stream_priorityInequality.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "priorityOrder"

    public static class qualifiedIdent_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "qualifiedIdent"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:220:1: qualifiedIdent : ID ( '.' ID )* -> ^( TAG ( ID )+ ) ;
    public final RVCCalParser.qualifiedIdent_return qualifiedIdent() throws RecognitionException {
        RVCCalParser.qualifiedIdent_return retval = new RVCCalParser.qualifiedIdent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID198=null;
        Token char_literal199=null;
        Token ID200=null;

        Object ID198_tree=null;
        Object char_literal199_tree=null;
        Object ID200_tree=null;
        RewriteRuleTokenStream stream_96=new RewriteRuleTokenStream(adaptor,"token 96");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");

        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:15: ( ID ( '.' ID )* -> ^( TAG ( ID )+ ) )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:17: ID ( '.' ID )*
            {
            ID198=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent1835);  
            stream_ID.add(ID198);

            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:20: ( '.' ID )*
            loop72:
            do {
                int alt72=2;
                int LA72_0 = input.LA(1);

                if ( (LA72_0==96) ) {
                    alt72=1;
                }


                switch (alt72) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:21: '.' ID
            	    {
            	    char_literal199=(Token)match(input,96,FOLLOW_96_in_qualifiedIdent1838);  
            	    stream_96.add(char_literal199);

            	    ID200=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent1840);  
            	    stream_ID.add(ID200);


            	    }
            	    break;

            	default :
            	    break loop72;
                }
            } while (true);



            // AST REWRITE
            // elements: ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 223:30: -> ^( TAG ( ID )+ )
            {
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:33: ^( TAG ( ID )+ )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TAG, "TAG"), root_1);

                if ( !(stream_ID.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_ID.hasNext() ) {
                    adaptor.addChild(root_1, stream_ID.nextNode());

                }
                stream_ID.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "qualifiedIdent"

    public static class schedule_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "schedule"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:1: schedule : SCHEDULE 'fsm' ID ':' ( stateTransition )* 'end' -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) ) ;
    public final RVCCalParser.schedule_return schedule() throws RecognitionException {
        RVCCalParser.schedule_return retval = new RVCCalParser.schedule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SCHEDULE201=null;
        Token string_literal202=null;
        Token ID203=null;
        Token char_literal204=null;
        Token string_literal206=null;
        RVCCalParser.stateTransition_return stateTransition205 = null;


        Object SCHEDULE201_tree=null;
        Object string_literal202_tree=null;
        Object ID203_tree=null;
        Object char_literal204_tree=null;
        Object string_literal206_tree=null;
        RewriteRuleTokenStream stream_95=new RewriteRuleTokenStream(adaptor,"token 95");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_SCHEDULE=new RewriteRuleTokenStream(adaptor,"token SCHEDULE");
        RewriteRuleTokenStream stream_130=new RewriteRuleTokenStream(adaptor,"token 130");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleSubtreeStream stream_stateTransition=new RewriteRuleSubtreeStream(adaptor,"rule stateTransition");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:9: ( SCHEDULE 'fsm' ID ':' ( stateTransition )* 'end' -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) ) )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:3: SCHEDULE 'fsm' ID ':' ( stateTransition )* 'end'
            {
            SCHEDULE201=(Token)match(input,SCHEDULE,FOLLOW_SCHEDULE_in_schedule1865);  
            stream_SCHEDULE.add(SCHEDULE201);

            string_literal202=(Token)match(input,130,FOLLOW_130_in_schedule1867);  
            stream_130.add(string_literal202);

            ID203=(Token)match(input,ID,FOLLOW_ID_in_schedule1869);  
            stream_ID.add(ID203);

            char_literal204=(Token)match(input,87,FOLLOW_87_in_schedule1871);  
            stream_87.add(char_literal204);

            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:25: ( stateTransition )*
            loop73:
            do {
                int alt73=2;
                int LA73_0 = input.LA(1);

                if ( (LA73_0==ID) ) {
                    alt73=1;
                }


                switch (alt73) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:25: stateTransition
            	    {
            	    pushFollow(FOLLOW_stateTransition_in_schedule1873);
            	    stateTransition205=stateTransition();

            	    state._fsp--;

            	    stream_stateTransition.add(stateTransition205.getTree());

            	    }
            	    break;

            	default :
            	    break loop73;
                }
            } while (true);

            string_literal206=(Token)match(input,95,FOLLOW_95_in_schedule1876);  
            stream_95.add(string_literal206);



            // AST REWRITE
            // elements: SCHEDULE, ID, stateTransition
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 229:48: -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) )
            {
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:51: ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_SCHEDULE.nextNode(), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:65: ^( TRANSITIONS ( stateTransition )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TRANSITIONS, "TRANSITIONS"), root_2);

                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:79: ( stateTransition )*
                while ( stream_stateTransition.hasNext() ) {
                    adaptor.addChild(root_2, stream_stateTransition.nextTree());

                }
                stream_stateTransition.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "schedule"

    public static class stateTransition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stateTransition"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:1: stateTransition : ID '(' qualifiedIdent ')' '-->' ID ';' -> ^( TRANSITION ID qualifiedIdent ID ) ;
    public final RVCCalParser.stateTransition_return stateTransition() throws RecognitionException {
        RVCCalParser.stateTransition_return retval = new RVCCalParser.stateTransition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID207=null;
        Token char_literal208=null;
        Token char_literal210=null;
        Token string_literal211=null;
        Token ID212=null;
        Token char_literal213=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent209 = null;


        Object ID207_tree=null;
        Object char_literal208_tree=null;
        Object char_literal210_tree=null;
        Object string_literal211_tree=null;
        Object ID212_tree=null;
        Object char_literal213_tree=null;
        RewriteRuleTokenStream stream_93=new RewriteRuleTokenStream(adaptor,"token 93");
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_101=new RewriteRuleTokenStream(adaptor,"token 101");
        RewriteRuleTokenStream stream_100=new RewriteRuleTokenStream(adaptor,"token 100");
        RewriteRuleSubtreeStream stream_qualifiedIdent=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedIdent");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:16: ( ID '(' qualifiedIdent ')' '-->' ID ';' -> ^( TRANSITION ID qualifiedIdent ID ) )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:2: ID '(' qualifiedIdent ')' '-->' ID ';'
            {
            ID207=(Token)match(input,ID,FOLLOW_ID_in_stateTransition1899);  
            stream_ID.add(ID207);

            char_literal208=(Token)match(input,92,FOLLOW_92_in_stateTransition1901);  
            stream_92.add(char_literal208);

            pushFollow(FOLLOW_qualifiedIdent_in_stateTransition1903);
            qualifiedIdent209=qualifiedIdent();

            state._fsp--;

            stream_qualifiedIdent.add(qualifiedIdent209.getTree());
            char_literal210=(Token)match(input,93,FOLLOW_93_in_stateTransition1905);  
            stream_93.add(char_literal210);

            string_literal211=(Token)match(input,101,FOLLOW_101_in_stateTransition1907);  
            stream_101.add(string_literal211);

            ID212=(Token)match(input,ID,FOLLOW_ID_in_stateTransition1909);  
            stream_ID.add(ID212);

            char_literal213=(Token)match(input,100,FOLLOW_100_in_stateTransition1911);  
            stream_100.add(char_literal213);



            // AST REWRITE
            // elements: qualifiedIdent, ID, ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 232:41: -> ^( TRANSITION ID qualifiedIdent ID )
            {
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:44: ^( TRANSITION ID qualifiedIdent ID )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TRANSITION, "TRANSITION"), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                adaptor.addChild(root_1, stream_qualifiedIdent.nextTree());
                adaptor.addChild(root_1, stream_ID.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "stateTransition"

    public static class statement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statement"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:234:1: statement : ( 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end' | 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end' | 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end' | 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end' | ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) -> ^( ASSIGN ID ^( EXPRESSIONS ( expressions )? ) expression ) | '(' ( expressions )? ')' ';' -> ^( CALL ID ^( EXPRESSIONS ( expressions )? ) ) ) );
    public final RVCCalParser.statement_return statement() throws RecognitionException {
        RVCCalParser.statement_return retval = new RVCCalParser.statement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal214=null;
        Token string_literal215=null;
        Token string_literal217=null;
        Token string_literal219=null;
        Token string_literal220=null;
        Token string_literal222=null;
        Token string_literal224=null;
        Token string_literal226=null;
        Token string_literal228=null;
        Token string_literal230=null;
        Token string_literal231=null;
        Token string_literal233=null;
        Token string_literal235=null;
        Token string_literal237=null;
        Token string_literal238=null;
        Token string_literal240=null;
        Token string_literal242=null;
        Token string_literal244=null;
        Token ID245=null;
        Token char_literal246=null;
        Token char_literal248=null;
        Token string_literal249=null;
        Token char_literal251=null;
        Token char_literal252=null;
        Token char_literal254=null;
        Token char_literal255=null;
        RVCCalParser.varDecls_return varDecls216 = null;

        RVCCalParser.statement_return statement218 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr221 = null;

        RVCCalParser.expression_return expression223 = null;

        RVCCalParser.expression_return expression225 = null;

        RVCCalParser.varDecls_return varDecls227 = null;

        RVCCalParser.statement_return statement229 = null;

        RVCCalParser.expression_return expression232 = null;

        RVCCalParser.statement_return statement234 = null;

        RVCCalParser.statement_return statement236 = null;

        RVCCalParser.expression_return expression239 = null;

        RVCCalParser.varDecls_return varDecls241 = null;

        RVCCalParser.statement_return statement243 = null;

        RVCCalParser.expressions_return expressions247 = null;

        RVCCalParser.expression_return expression250 = null;

        RVCCalParser.expressions_return expressions253 = null;


        Object string_literal214_tree=null;
        Object string_literal215_tree=null;
        Object string_literal217_tree=null;
        Object string_literal219_tree=null;
        Object string_literal220_tree=null;
        Object string_literal222_tree=null;
        Object string_literal224_tree=null;
        Object string_literal226_tree=null;
        Object string_literal228_tree=null;
        Object string_literal230_tree=null;
        Object string_literal231_tree=null;
        Object string_literal233_tree=null;
        Object string_literal235_tree=null;
        Object string_literal237_tree=null;
        Object string_literal238_tree=null;
        Object string_literal240_tree=null;
        Object string_literal242_tree=null;
        Object string_literal244_tree=null;
        Object ID245_tree=null;
        Object char_literal246_tree=null;
        Object char_literal248_tree=null;
        Object string_literal249_tree=null;
        Object char_literal251_tree=null;
        Object char_literal252_tree=null;
        Object char_literal254_tree=null;
        Object char_literal255_tree=null;
        RewriteRuleTokenStream stream_93=new RewriteRuleTokenStream(adaptor,"token 93");
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_99=new RewriteRuleTokenStream(adaptor,"token 99");
        RewriteRuleTokenStream stream_88=new RewriteRuleTokenStream(adaptor,"token 88");
        RewriteRuleTokenStream stream_100=new RewriteRuleTokenStream(adaptor,"token 100");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_expressions=new RewriteRuleSubtreeStream(adaptor,"rule expressions");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:10: ( 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end' | 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end' | 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end' | 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end' | ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) -> ^( ASSIGN ID ^( EXPRESSIONS ( expressions )? ) expression ) | '(' ( expressions )? ')' ';' -> ^( CALL ID ^( EXPRESSIONS ( expressions )? ) ) ) )
            int alt87=5;
            switch ( input.LA(1) ) {
            case 102:
                {
                alt87=1;
                }
                break;
            case 131:
                {
                alt87=2;
                }
                break;
            case 123:
                {
                alt87=3;
                }
                break;
            case 133:
                {
                alt87=4;
                }
                break;
            case ID:
                {
                alt87=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 87, 0, input);

                throw nvae;
            }

            switch (alt87) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:3: 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal214=(Token)match(input,102,FOLLOW_102_in_statement1937); 
                    string_literal214_tree = (Object)adaptor.create(string_literal214);
                    adaptor.addChild(root_0, string_literal214_tree);

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:11: ( 'var' varDecls 'do' )?
                    int alt74=2;
                    int LA74_0 = input.LA(1);

                    if ( (LA74_0==97) ) {
                        alt74=1;
                    }
                    switch (alt74) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:12: 'var' varDecls 'do'
                            {
                            string_literal215=(Token)match(input,97,FOLLOW_97_in_statement1940); 
                            string_literal215_tree = (Object)adaptor.create(string_literal215);
                            adaptor.addChild(root_0, string_literal215_tree);

                            pushFollow(FOLLOW_varDecls_in_statement1942);
                            varDecls216=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls216.getTree());
                            string_literal217=(Token)match(input,91,FOLLOW_91_in_statement1944); 
                            string_literal217_tree = (Object)adaptor.create(string_literal217);
                            adaptor.addChild(root_0, string_literal217_tree);


                            }
                            break;

                    }

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:34: ( statement )*
                    loop75:
                    do {
                        int alt75=2;
                        int LA75_0 = input.LA(1);

                        if ( (LA75_0==ID||LA75_0==102||LA75_0==123||LA75_0==131||LA75_0==133) ) {
                            alt75=1;
                        }


                        switch (alt75) {
                    	case 1 :
                    	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:34: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement1948);
                    	    statement218=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement218.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop75;
                        }
                    } while (true);

                    string_literal219=(Token)match(input,95,FOLLOW_95_in_statement1951); 
                    string_literal219_tree = (Object)adaptor.create(string_literal219);
                    adaptor.addChild(root_0, string_literal219_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:3: 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal220=(Token)match(input,131,FOLLOW_131_in_statement1957); 
                    string_literal220_tree = (Object)adaptor.create(string_literal220);
                    adaptor.addChild(root_0, string_literal220_tree);

                    pushFollow(FOLLOW_varDeclNoExpr_in_statement1959);
                    varDeclNoExpr221=varDeclNoExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, varDeclNoExpr221.getTree());
                    string_literal222=(Token)match(input,129,FOLLOW_129_in_statement1961); 
                    string_literal222_tree = (Object)adaptor.create(string_literal222);
                    adaptor.addChild(root_0, string_literal222_tree);

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:32: ( expression ( '..' expression )? )
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:33: expression ( '..' expression )?
                    {
                    pushFollow(FOLLOW_expression_in_statement1964);
                    expression223=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression223.getTree());
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:44: ( '..' expression )?
                    int alt76=2;
                    int LA76_0 = input.LA(1);

                    if ( (LA76_0==132) ) {
                        alt76=1;
                    }
                    switch (alt76) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:45: '..' expression
                            {
                            string_literal224=(Token)match(input,132,FOLLOW_132_in_statement1967); 
                            string_literal224_tree = (Object)adaptor.create(string_literal224);
                            adaptor.addChild(root_0, string_literal224_tree);

                            pushFollow(FOLLOW_expression_in_statement1969);
                            expression225=expression();

                            state._fsp--;

                            adaptor.addChild(root_0, expression225.getTree());

                            }
                            break;

                    }


                    }

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:64: ( 'var' varDecls )?
                    int alt77=2;
                    int LA77_0 = input.LA(1);

                    if ( (LA77_0==97) ) {
                        alt77=1;
                    }
                    switch (alt77) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:65: 'var' varDecls
                            {
                            string_literal226=(Token)match(input,97,FOLLOW_97_in_statement1975); 
                            string_literal226_tree = (Object)adaptor.create(string_literal226);
                            adaptor.addChild(root_0, string_literal226_tree);

                            pushFollow(FOLLOW_varDecls_in_statement1977);
                            varDecls227=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls227.getTree());

                            }
                            break;

                    }

                    string_literal228=(Token)match(input,91,FOLLOW_91_in_statement1981); 
                    string_literal228_tree = (Object)adaptor.create(string_literal228);
                    adaptor.addChild(root_0, string_literal228_tree);

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:87: ( statement )*
                    loop78:
                    do {
                        int alt78=2;
                        int LA78_0 = input.LA(1);

                        if ( (LA78_0==ID||LA78_0==102||LA78_0==123||LA78_0==131||LA78_0==133) ) {
                            alt78=1;
                        }


                        switch (alt78) {
                    	case 1 :
                    	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:87: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement1983);
                    	    statement229=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement229.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop78;
                        }
                    } while (true);

                    string_literal230=(Token)match(input,95,FOLLOW_95_in_statement1986); 
                    string_literal230_tree = (Object)adaptor.create(string_literal230);
                    adaptor.addChild(root_0, string_literal230_tree);

                     

                    }
                    break;
                case 3 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:3: 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal231=(Token)match(input,123,FOLLOW_123_in_statement1992); 
                    string_literal231_tree = (Object)adaptor.create(string_literal231);
                    adaptor.addChild(root_0, string_literal231_tree);

                    pushFollow(FOLLOW_expression_in_statement1994);
                    expression232=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression232.getTree());
                    string_literal233=(Token)match(input,124,FOLLOW_124_in_statement1996); 
                    string_literal233_tree = (Object)adaptor.create(string_literal233);
                    adaptor.addChild(root_0, string_literal233_tree);

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:26: ( statement )*
                    loop79:
                    do {
                        int alt79=2;
                        int LA79_0 = input.LA(1);

                        if ( (LA79_0==ID||LA79_0==102||LA79_0==123||LA79_0==131||LA79_0==133) ) {
                            alt79=1;
                        }


                        switch (alt79) {
                    	case 1 :
                    	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:26: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement1998);
                    	    statement234=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement234.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop79;
                        }
                    } while (true);

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:37: ( 'else' ( statement )* )?
                    int alt81=2;
                    int LA81_0 = input.LA(1);

                    if ( (LA81_0==125) ) {
                        alt81=1;
                    }
                    switch (alt81) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:38: 'else' ( statement )*
                            {
                            string_literal235=(Token)match(input,125,FOLLOW_125_in_statement2002); 
                            string_literal235_tree = (Object)adaptor.create(string_literal235);
                            adaptor.addChild(root_0, string_literal235_tree);

                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:45: ( statement )*
                            loop80:
                            do {
                                int alt80=2;
                                int LA80_0 = input.LA(1);

                                if ( (LA80_0==ID||LA80_0==102||LA80_0==123||LA80_0==131||LA80_0==133) ) {
                                    alt80=1;
                                }


                                switch (alt80) {
                            	case 1 :
                            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:45: statement
                            	    {
                            	    pushFollow(FOLLOW_statement_in_statement2004);
                            	    statement236=statement();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, statement236.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop80;
                                }
                            } while (true);


                            }
                            break;

                    }

                    string_literal237=(Token)match(input,95,FOLLOW_95_in_statement2009); 
                    string_literal237_tree = (Object)adaptor.create(string_literal237);
                    adaptor.addChild(root_0, string_literal237_tree);

                      

                    }
                    break;
                case 4 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:241:3: 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal238=(Token)match(input,133,FOLLOW_133_in_statement2015); 
                    string_literal238_tree = (Object)adaptor.create(string_literal238);
                    adaptor.addChild(root_0, string_literal238_tree);

                    pushFollow(FOLLOW_expression_in_statement2017);
                    expression239=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression239.getTree());
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:241:22: ( 'var' varDecls )?
                    int alt82=2;
                    int LA82_0 = input.LA(1);

                    if ( (LA82_0==97) ) {
                        alt82=1;
                    }
                    switch (alt82) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:241:23: 'var' varDecls
                            {
                            string_literal240=(Token)match(input,97,FOLLOW_97_in_statement2020); 
                            string_literal240_tree = (Object)adaptor.create(string_literal240);
                            adaptor.addChild(root_0, string_literal240_tree);

                            pushFollow(FOLLOW_varDecls_in_statement2022);
                            varDecls241=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls241.getTree());

                            }
                            break;

                    }

                    string_literal242=(Token)match(input,91,FOLLOW_91_in_statement2026); 
                    string_literal242_tree = (Object)adaptor.create(string_literal242);
                    adaptor.addChild(root_0, string_literal242_tree);

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:241:45: ( statement )*
                    loop83:
                    do {
                        int alt83=2;
                        int LA83_0 = input.LA(1);

                        if ( (LA83_0==ID||LA83_0==102||LA83_0==123||LA83_0==131||LA83_0==133) ) {
                            alt83=1;
                        }


                        switch (alt83) {
                    	case 1 :
                    	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:241:45: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement2028);
                    	    statement243=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement243.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop83;
                        }
                    } while (true);

                    string_literal244=(Token)match(input,95,FOLLOW_95_in_statement2031); 
                    string_literal244_tree = (Object)adaptor.create(string_literal244);
                    adaptor.addChild(root_0, string_literal244_tree);

                      

                    }
                    break;
                case 5 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:243:3: ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) -> ^( ASSIGN ID ^( EXPRESSIONS ( expressions )? ) expression ) | '(' ( expressions )? ')' ';' -> ^( CALL ID ^( EXPRESSIONS ( expressions )? ) ) )
                    {
                    ID245=(Token)match(input,ID,FOLLOW_ID_in_statement2038);  
                    stream_ID.add(ID245);

                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:243:6: ( ( ( '[' expressions ']' )? ':=' expression ';' ) -> ^( ASSIGN ID ^( EXPRESSIONS ( expressions )? ) expression ) | '(' ( expressions )? ')' ';' -> ^( CALL ID ^( EXPRESSIONS ( expressions )? ) ) )
                    int alt86=2;
                    int LA86_0 = input.LA(1);

                    if ( (LA86_0==88||LA86_0==99) ) {
                        alt86=1;
                    }
                    else if ( (LA86_0==92) ) {
                        alt86=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 86, 0, input);

                        throw nvae;
                    }
                    switch (alt86) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:5: ( ( '[' expressions ']' )? ':=' expression ';' )
                            {
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:5: ( ( '[' expressions ']' )? ':=' expression ';' )
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:6: ( '[' expressions ']' )? ':=' expression ';'
                            {
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:6: ( '[' expressions ']' )?
                            int alt84=2;
                            int LA84_0 = input.LA(1);

                            if ( (LA84_0==88) ) {
                                alt84=1;
                            }
                            switch (alt84) {
                                case 1 :
                                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:7: '[' expressions ']'
                                    {
                                    char_literal246=(Token)match(input,88,FOLLOW_88_in_statement2048);  
                                    stream_88.add(char_literal246);

                                    pushFollow(FOLLOW_expressions_in_statement2050);
                                    expressions247=expressions();

                                    state._fsp--;

                                    stream_expressions.add(expressions247.getTree());
                                    char_literal248=(Token)match(input,89,FOLLOW_89_in_statement2052);  
                                    stream_89.add(char_literal248);


                                    }
                                    break;

                            }

                            string_literal249=(Token)match(input,99,FOLLOW_99_in_statement2056);  
                            stream_99.add(string_literal249);

                            pushFollow(FOLLOW_expression_in_statement2058);
                            expression250=expression();

                            state._fsp--;

                            stream_expression.add(expression250.getTree());
                            char_literal251=(Token)match(input,100,FOLLOW_100_in_statement2060);  
                            stream_100.add(char_literal251);


                            }



                            // AST REWRITE
                            // elements: expression, expressions, ID
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 244:50: -> ^( ASSIGN ID ^( EXPRESSIONS ( expressions )? ) expression )
                            {
                                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:53: ^( ASSIGN ID ^( EXPRESSIONS ( expressions )? ) expression )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ASSIGN, "ASSIGN"), root_1);

                                adaptor.addChild(root_1, stream_ID.nextNode());
                                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:65: ^( EXPRESSIONS ( expressions )? )
                                {
                                Object root_2 = (Object)adaptor.nil();
                                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPRESSIONS, "EXPRESSIONS"), root_2);

                                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:79: ( expressions )?
                                if ( stream_expressions.hasNext() ) {
                                    adaptor.addChild(root_2, stream_expressions.nextTree());

                                }
                                stream_expressions.reset();

                                adaptor.addChild(root_1, root_2);
                                }
                                adaptor.addChild(root_1, stream_expression.nextTree());

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;
                            }
                            break;
                        case 2 :
                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:6: '(' ( expressions )? ')' ';'
                            {
                            char_literal252=(Token)match(input,92,FOLLOW_92_in_statement2085);  
                            stream_92.add(char_literal252);

                            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:10: ( expressions )?
                            int alt85=2;
                            int LA85_0 = input.LA(1);

                            if ( (LA85_0==MINUS||LA85_0==ID||LA85_0==FLOAT||LA85_0==INTEGER||LA85_0==STRING||LA85_0==88||LA85_0==92||(LA85_0>=121 && LA85_0<=123)||(LA85_0>=126 && LA85_0<=127)) ) {
                                alt85=1;
                            }
                            switch (alt85) {
                                case 1 :
                                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:10: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_statement2087);
                                    expressions253=expressions();

                                    state._fsp--;

                                    stream_expressions.add(expressions253.getTree());

                                    }
                                    break;

                            }

                            char_literal254=(Token)match(input,93,FOLLOW_93_in_statement2090);  
                            stream_93.add(char_literal254);

                            char_literal255=(Token)match(input,100,FOLLOW_100_in_statement2092);  
                            stream_100.add(char_literal255);



                            // AST REWRITE
                            // elements: ID, expressions
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 245:31: -> ^( CALL ID ^( EXPRESSIONS ( expressions )? ) )
                            {
                                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:34: ^( CALL ID ^( EXPRESSIONS ( expressions )? ) )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(CALL, "CALL"), root_1);

                                adaptor.addChild(root_1, stream_ID.nextNode());
                                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:44: ^( EXPRESSIONS ( expressions )? )
                                {
                                Object root_2 = (Object)adaptor.nil();
                                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPRESSIONS, "EXPRESSIONS"), root_2);

                                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:58: ( expressions )?
                                if ( stream_expressions.hasNext() ) {
                                    adaptor.addChild(root_2, stream_expressions.nextTree());

                                }
                                stream_expressions.reset();

                                adaptor.addChild(root_1, root_2);
                                }

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;
                            }
                            break;

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "statement"

    public static class typeAttr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typeAttr"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:247:1: typeAttr : ID ( ':' typeDef -> ^( TYPE typeDef ID ) | '=' expression -> ^( EXPR expression ID ) ) ;
    public final RVCCalParser.typeAttr_return typeAttr() throws RecognitionException {
        RVCCalParser.typeAttr_return retval = new RVCCalParser.typeAttr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID256=null;
        Token char_literal257=null;
        Token char_literal259=null;
        RVCCalParser.typeDef_return typeDef258 = null;

        RVCCalParser.expression_return expression260 = null;


        Object ID256_tree=null;
        Object char_literal257_tree=null;
        Object char_literal259_tree=null;
        RewriteRuleTokenStream stream_98=new RewriteRuleTokenStream(adaptor,"token 98");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:9: ( ID ( ':' typeDef -> ^( TYPE typeDef ID ) | '=' expression -> ^( EXPR expression ID ) ) )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:11: ID ( ':' typeDef -> ^( TYPE typeDef ID ) | '=' expression -> ^( EXPR expression ID ) )
            {
            ID256=(Token)match(input,ID,FOLLOW_ID_in_typeAttr2126);  
            stream_ID.add(ID256);

            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:14: ( ':' typeDef -> ^( TYPE typeDef ID ) | '=' expression -> ^( EXPR expression ID ) )
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==87) ) {
                alt88=1;
            }
            else if ( (LA88_0==98) ) {
                alt88=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 88, 0, input);

                throw nvae;
            }
            switch (alt88) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:15: ':' typeDef
                    {
                    char_literal257=(Token)match(input,87,FOLLOW_87_in_typeAttr2129);  
                    stream_87.add(char_literal257);

                    pushFollow(FOLLOW_typeDef_in_typeAttr2131);
                    typeDef258=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef258.getTree());


                    // AST REWRITE
                    // elements: ID, typeDef
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 254:27: -> ^( TYPE typeDef ID )
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:30: ^( TYPE typeDef ID )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_1);

                        adaptor.addChild(root_1, stream_typeDef.nextTree());
                        adaptor.addChild(root_1, stream_ID.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:51: '=' expression
                    {
                    char_literal259=(Token)match(input,98,FOLLOW_98_in_typeAttr2145);  
                    stream_98.add(char_literal259);

                    pushFollow(FOLLOW_expression_in_typeAttr2147);
                    expression260=expression();

                    state._fsp--;

                    stream_expression.add(expression260.getTree());


                    // AST REWRITE
                    // elements: expression, ID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 254:66: -> ^( EXPR expression ID )
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:69: ^( EXPR expression ID )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR, "EXPR"), root_1);

                        adaptor.addChild(root_1, stream_expression.nextTree());
                        adaptor.addChild(root_1, stream_ID.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "typeAttr"

    public static class typeAttrs_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typeAttrs"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:1: typeAttrs : typeAttr ( ',' typeAttr )* -> ( typeAttr )+ ;
    public final RVCCalParser.typeAttrs_return typeAttrs() throws RecognitionException {
        RVCCalParser.typeAttrs_return retval = new RVCCalParser.typeAttrs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal262=null;
        RVCCalParser.typeAttr_return typeAttr261 = null;

        RVCCalParser.typeAttr_return typeAttr263 = null;


        Object char_literal262_tree=null;
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleSubtreeStream stream_typeAttr=new RewriteRuleSubtreeStream(adaptor,"rule typeAttr");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:10: ( typeAttr ( ',' typeAttr )* -> ( typeAttr )+ )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:12: typeAttr ( ',' typeAttr )*
            {
            pushFollow(FOLLOW_typeAttr_in_typeAttrs2166);
            typeAttr261=typeAttr();

            state._fsp--;

            stream_typeAttr.add(typeAttr261.getTree());
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:21: ( ',' typeAttr )*
            loop89:
            do {
                int alt89=2;
                int LA89_0 = input.LA(1);

                if ( (LA89_0==90) ) {
                    alt89=1;
                }


                switch (alt89) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:22: ',' typeAttr
            	    {
            	    char_literal262=(Token)match(input,90,FOLLOW_90_in_typeAttrs2169);  
            	    stream_90.add(char_literal262);

            	    pushFollow(FOLLOW_typeAttr_in_typeAttrs2171);
            	    typeAttr263=typeAttr();

            	    state._fsp--;

            	    stream_typeAttr.add(typeAttr263.getTree());

            	    }
            	    break;

            	default :
            	    break loop89;
                }
            } while (true);



            // AST REWRITE
            // elements: typeAttr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 256:37: -> ( typeAttr )+
            {
                if ( !(stream_typeAttr.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_typeAttr.hasNext() ) {
                    adaptor.addChild(root_0, stream_typeAttr.nextTree());

                }
                stream_typeAttr.reset();

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "typeAttrs"

    public static class typeDef_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typeDef"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:1: typeDef : ID ( '(' attrs= typeAttrs ')' )? -> ^( TYPE ID ( $attrs)? ) ;
    public final RVCCalParser.typeDef_return typeDef() throws RecognitionException {
        RVCCalParser.typeDef_return retval = new RVCCalParser.typeDef_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID264=null;
        Token char_literal265=null;
        Token char_literal266=null;
        RVCCalParser.typeAttrs_return attrs = null;


        Object ID264_tree=null;
        Object char_literal265_tree=null;
        Object char_literal266_tree=null;
        RewriteRuleTokenStream stream_93=new RewriteRuleTokenStream(adaptor,"token 93");
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_typeAttrs=new RewriteRuleSubtreeStream(adaptor,"rule typeAttrs");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:8: ( ID ( '(' attrs= typeAttrs ')' )? -> ^( TYPE ID ( $attrs)? ) )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:10: ID ( '(' attrs= typeAttrs ')' )?
            {
            ID264=(Token)match(input,ID,FOLLOW_ID_in_typeDef2188);  
            stream_ID.add(ID264);

            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:13: ( '(' attrs= typeAttrs ')' )?
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==92) ) {
                alt90=1;
            }
            switch (alt90) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:14: '(' attrs= typeAttrs ')'
                    {
                    char_literal265=(Token)match(input,92,FOLLOW_92_in_typeDef2191);  
                    stream_92.add(char_literal265);

                    pushFollow(FOLLOW_typeAttrs_in_typeDef2195);
                    attrs=typeAttrs();

                    state._fsp--;

                    stream_typeAttrs.add(attrs.getTree());
                    char_literal266=(Token)match(input,93,FOLLOW_93_in_typeDef2197);  
                    stream_93.add(char_literal266);


                    }
                    break;

            }



            // AST REWRITE
            // elements: ID, attrs
            // token labels: 
            // rule labels: retval, attrs
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_attrs=new RewriteRuleSubtreeStream(adaptor,"rule attrs",attrs!=null?attrs.tree:null);

            root_0 = (Object)adaptor.nil();
            // 259:40: -> ^( TYPE ID ( $attrs)? )
            {
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:43: ^( TYPE ID ( $attrs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:53: ( $attrs)?
                if ( stream_attrs.hasNext() ) {
                    adaptor.addChild(root_1, stream_attrs.nextTree());

                }
                stream_attrs.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "typeDef"

    public static class varDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "varDecl"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:261:1: varDecl : typeDef ID ( '=' expression -> ^( VARIABLE typeDef ID NON_ASSIGNABLE expression ) | ':=' expression -> ^( VARIABLE typeDef ID ASSIGNABLE expression ) | -> ^( VARIABLE typeDef ID ASSIGNABLE ) ) ;
    public final RVCCalParser.varDecl_return varDecl() throws RecognitionException {
        RVCCalParser.varDecl_return retval = new RVCCalParser.varDecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID268=null;
        Token char_literal269=null;
        Token string_literal271=null;
        RVCCalParser.typeDef_return typeDef267 = null;

        RVCCalParser.expression_return expression270 = null;

        RVCCalParser.expression_return expression272 = null;


        Object ID268_tree=null;
        Object char_literal269_tree=null;
        Object string_literal271_tree=null;
        RewriteRuleTokenStream stream_98=new RewriteRuleTokenStream(adaptor,"token 98");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_99=new RewriteRuleTokenStream(adaptor,"token 99");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:8: ( typeDef ID ( '=' expression -> ^( VARIABLE typeDef ID NON_ASSIGNABLE expression ) | ':=' expression -> ^( VARIABLE typeDef ID ASSIGNABLE expression ) | -> ^( VARIABLE typeDef ID ASSIGNABLE ) ) )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:10: typeDef ID ( '=' expression -> ^( VARIABLE typeDef ID NON_ASSIGNABLE expression ) | ':=' expression -> ^( VARIABLE typeDef ID ASSIGNABLE expression ) | -> ^( VARIABLE typeDef ID ASSIGNABLE ) )
            {
            pushFollow(FOLLOW_typeDef_in_varDecl2225);
            typeDef267=typeDef();

            state._fsp--;

            stream_typeDef.add(typeDef267.getTree());
            ID268=(Token)match(input,ID,FOLLOW_ID_in_varDecl2227);  
            stream_ID.add(ID268);

            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:3: ( '=' expression -> ^( VARIABLE typeDef ID NON_ASSIGNABLE expression ) | ':=' expression -> ^( VARIABLE typeDef ID ASSIGNABLE expression ) | -> ^( VARIABLE typeDef ID ASSIGNABLE ) )
            int alt91=3;
            switch ( input.LA(1) ) {
            case 98:
                {
                alt91=1;
                }
                break;
            case 99:
                {
                alt91=2;
                }
                break;
            case 87:
            case 90:
            case 91:
            case 95:
            case 102:
                {
                alt91=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 91, 0, input);

                throw nvae;
            }

            switch (alt91) {
                case 1 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:4: '=' expression
                    {
                    char_literal269=(Token)match(input,98,FOLLOW_98_in_varDecl2232);  
                    stream_98.add(char_literal269);

                    pushFollow(FOLLOW_expression_in_varDecl2234);
                    expression270=expression();

                    state._fsp--;

                    stream_expression.add(expression270.getTree());


                    // AST REWRITE
                    // elements: typeDef, ID, expression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 266:19: -> ^( VARIABLE typeDef ID NON_ASSIGNABLE expression )
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:22: ^( VARIABLE typeDef ID NON_ASSIGNABLE expression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLE, "VARIABLE"), root_1);

                        adaptor.addChild(root_1, stream_typeDef.nextTree());
                        adaptor.addChild(root_1, stream_ID.nextNode());
                        adaptor.addChild(root_1, (Object)adaptor.create(NON_ASSIGNABLE, "NON_ASSIGNABLE"));
                        adaptor.addChild(root_1, stream_expression.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:267:5: ':=' expression
                    {
                    string_literal271=(Token)match(input,99,FOLLOW_99_in_varDecl2254);  
                    stream_99.add(string_literal271);

                    pushFollow(FOLLOW_expression_in_varDecl2256);
                    expression272=expression();

                    state._fsp--;

                    stream_expression.add(expression272.getTree());


                    // AST REWRITE
                    // elements: ID, typeDef, expression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 267:21: -> ^( VARIABLE typeDef ID ASSIGNABLE expression )
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:267:24: ^( VARIABLE typeDef ID ASSIGNABLE expression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLE, "VARIABLE"), root_1);

                        adaptor.addChild(root_1, stream_typeDef.nextTree());
                        adaptor.addChild(root_1, stream_ID.nextNode());
                        adaptor.addChild(root_1, (Object)adaptor.create(ASSIGNABLE, "ASSIGNABLE"));
                        adaptor.addChild(root_1, stream_expression.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:268:5: 
                    {

                    // AST REWRITE
                    // elements: ID, typeDef
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 268:5: -> ^( VARIABLE typeDef ID ASSIGNABLE )
                    {
                        // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:268:8: ^( VARIABLE typeDef ID ASSIGNABLE )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLE, "VARIABLE"), root_1);

                        adaptor.addChild(root_1, stream_typeDef.nextTree());
                        adaptor.addChild(root_1, stream_ID.nextNode());
                        adaptor.addChild(root_1, (Object)adaptor.create(ASSIGNABLE, "ASSIGNABLE"));

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "varDecl"

    public static class varDeclNoExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "varDeclNoExpr"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:270:1: varDeclNoExpr : typeDef ID -> ^( VARIABLE typeDef ID ASSIGNABLE ) ;
    public final RVCCalParser.varDeclNoExpr_return varDeclNoExpr() throws RecognitionException {
        RVCCalParser.varDeclNoExpr_return retval = new RVCCalParser.varDeclNoExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID274=null;
        RVCCalParser.typeDef_return typeDef273 = null;


        Object ID274_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:270:14: ( typeDef ID -> ^( VARIABLE typeDef ID ASSIGNABLE ) )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:270:16: typeDef ID
            {
            pushFollow(FOLLOW_typeDef_in_varDeclNoExpr2294);
            typeDef273=typeDef();

            state._fsp--;

            stream_typeDef.add(typeDef273.getTree());
            ID274=(Token)match(input,ID,FOLLOW_ID_in_varDeclNoExpr2296);  
            stream_ID.add(ID274);



            // AST REWRITE
            // elements: typeDef, ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 270:27: -> ^( VARIABLE typeDef ID ASSIGNABLE )
            {
                // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:270:30: ^( VARIABLE typeDef ID ASSIGNABLE )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLE, "VARIABLE"), root_1);

                adaptor.addChild(root_1, stream_typeDef.nextTree());
                adaptor.addChild(root_1, stream_ID.nextNode());
                adaptor.addChild(root_1, (Object)adaptor.create(ASSIGNABLE, "ASSIGNABLE"));

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "varDeclNoExpr"

    public static class varDecls_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "varDecls"
    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:272:1: varDecls : varDecl ( ',' varDecl )* -> ( varDecl )+ ;
    public final RVCCalParser.varDecls_return varDecls() throws RecognitionException {
        RVCCalParser.varDecls_return retval = new RVCCalParser.varDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal276=null;
        RVCCalParser.varDecl_return varDecl275 = null;

        RVCCalParser.varDecl_return varDecl277 = null;


        Object char_literal276_tree=null;
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleSubtreeStream stream_varDecl=new RewriteRuleSubtreeStream(adaptor,"rule varDecl");
        try {
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:272:9: ( varDecl ( ',' varDecl )* -> ( varDecl )+ )
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:272:11: varDecl ( ',' varDecl )*
            {
            pushFollow(FOLLOW_varDecl_in_varDecls2315);
            varDecl275=varDecl();

            state._fsp--;

            stream_varDecl.add(varDecl275.getTree());
            // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:272:19: ( ',' varDecl )*
            loop92:
            do {
                int alt92=2;
                int LA92_0 = input.LA(1);

                if ( (LA92_0==90) ) {
                    alt92=1;
                }


                switch (alt92) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:272:20: ',' varDecl
            	    {
            	    char_literal276=(Token)match(input,90,FOLLOW_90_in_varDecls2318);  
            	    stream_90.add(char_literal276);

            	    pushFollow(FOLLOW_varDecl_in_varDecls2320);
            	    varDecl277=varDecl();

            	    state._fsp--;

            	    stream_varDecl.add(varDecl277.getTree());

            	    }
            	    break;

            	default :
            	    break loop92;
                }
            } while (true);



            // AST REWRITE
            // elements: varDecl
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 272:34: -> ( varDecl )+
            {
                if ( !(stream_varDecl.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_varDecl.hasNext() ) {
                    adaptor.addChild(root_0, stream_varDecl.nextTree());

                }
                stream_varDecl.reset();

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "varDecls"

    // Delegated rules


    protected DFA58 dfa58 = new DFA58(this);
    static final String DFA58_eotS =
        "\24\uffff";
    static final String DFA58_eofS =
        "\24\uffff";
    static final String DFA58_minS =
        "\1\105\23\uffff";
    static final String DFA58_maxS =
        "\1\170\23\uffff";
    static final String DFA58_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1"+
        "\15\1\16\1\17\1\20\1\21\1\22\1\23";
    static final String DFA58_specialS =
        "\24\uffff}>";
    static final String[] DFA58_transitionS = {
            "\1\15\1\16\1\22\1\17\31\uffff\1\5\6\uffff\2\1\2\2\1\3\1\4\1"+
            "\6\1\7\1\10\1\11\1\12\1\13\1\14\1\20\1\21\1\23",
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
            "",
            ""
    };

    static final short[] DFA58_eot = DFA.unpackEncodedString(DFA58_eotS);
    static final short[] DFA58_eof = DFA.unpackEncodedString(DFA58_eofS);
    static final char[] DFA58_min = DFA.unpackEncodedStringToUnsignedChars(DFA58_minS);
    static final char[] DFA58_max = DFA.unpackEncodedStringToUnsignedChars(DFA58_maxS);
    static final short[] DFA58_accept = DFA.unpackEncodedString(DFA58_acceptS);
    static final short[] DFA58_special = DFA.unpackEncodedString(DFA58_specialS);
    static final short[][] DFA58_transition;

    static {
        int numStates = DFA58_transitionS.length;
        DFA58_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA58_transition[i] = DFA.unpackEncodedString(DFA58_transitionS[i]);
        }
    }

    class DFA58 extends DFA {

        public DFA58(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 58;
            this.eot = DFA58_eot;
            this.eof = DFA58_eof;
            this.min = DFA58_min;
            this.max = DFA58_max;
            this.accept = DFA58_accept;
            this.special = DFA58_special;
            this.transition = DFA58_transition;
        }
        public String getDescription() {
            return "167:1: bop : ( ( 'or' | '||' ) -> LOGIC_OR | ( 'and' | '&&' ) -> LOGIC_AND | '|' -> BITOR | '&' -> BITAND | '=' -> EQ | '!=' -> NE | '<' -> LT | '>' -> GT | '<=' -> LE | '>=' -> GE | '<<' -> SHIFT_LEFT | '>>' -> SHIFT_RIGHT | PLUS -> PLUS | MINUS -> MINUS | DIV -> DIV | 'div' -> DIV_INT | 'mod' -> MOD | TIMES -> TIMES | '^' -> EXP );";
        }
    }
 

    public static final BitSet FOLLOW_GUARD_in_actionGuards61 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expressions_in_actionGuards63 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_actionInput76 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_actionInput78 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_88_in_actionInput82 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_idents_in_actionInput84 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_actionInput86 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_actionRepeat_in_actionInput88 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionInput_in_actionInputs123 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_actionInputs126 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000400L});
    public static final BitSet FOLLOW_actionInput_in_actionInputs128 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_ID_in_actionOutput144 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_actionOutput146 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_88_in_actionOutput150 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expressions_in_actionOutput152 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_actionOutput154 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_actionRepeat_in_actionOutput156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionOutput_in_actionOutputs191 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_actionOutputs194 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000400L});
    public static final BitSet FOLLOW_actionOutput_in_actionOutputs196 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_REPEAT_in_actionRepeat210 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_actionRepeat212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_91_in_actionStatements223 = new BitSet(new long[]{0x0000000000000002L,0x0800004000000400L,0x0000000000000028L});
    public static final BitSet FOLLOW_statement_in_actionStatements225 = new BitSet(new long[]{0x0000000000000002L,0x0800004000000400L,0x0000000000000028L});
    public static final BitSet FOLLOW_actorImport_in_actor243 = new BitSet(new long[]{0x4000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_ACTOR_in_actor246 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_ID_in_actor248 = new BitSet(new long[]{0x0000000000000000L,0x0000000011000000L});
    public static final BitSet FOLLOW_88_in_actor251 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_actor253 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_actor257 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000400L});
    public static final BitSet FOLLOW_actorParameters_in_actor259 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_93_in_actor262 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000400L});
    public static final BitSet FOLLOW_actorPortDecls_in_actor267 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_actor270 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800400L});
    public static final BitSet FOLLOW_actorPortDecls_in_actor274 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_actor277 = new BitSet(new long[]{0xA000000000000000L,0x000000008000041EL});
    public static final BitSet FOLLOW_actorDeclarations_in_actor280 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_95_in_actor283 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_actor285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_id341 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_actorDeclaration360 = new BitSet(new long[]{0x0000000000000000L,0x0000000110800400L});
    public static final BitSet FOLLOW_96_in_actorDeclaration371 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration373 = new BitSet(new long[]{0x0000000000000000L,0x0000000100800000L});
    public static final BitSet FOLLOW_87_in_actorDeclaration378 = new BitSet(new long[]{0x2000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_ACTION_in_actorDeclaration387 = new BitSet(new long[]{0x0000000000000000L,0x0000000041000400L});
    public static final BitSet FOLLOW_actionInputs_in_actorDeclaration391 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_actorDeclaration394 = new BitSet(new long[]{0x0000000000000000L,0x0000000289000401L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration398 = new BitSet(new long[]{0x0000000000000000L,0x0000000288000001L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration403 = new BitSet(new long[]{0x0000000000000000L,0x0000000288000000L});
    public static final BitSet FOLLOW_97_in_actorDeclaration407 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration409 = new BitSet(new long[]{0x0000000000000000L,0x0000000088000000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration413 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_95_in_actorDeclaration416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INITIALIZE_in_actorDeclaration494 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_actorDeclaration496 = new BitSet(new long[]{0x0000000000000000L,0x0000000289000401L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration498 = new BitSet(new long[]{0x0000000000000000L,0x0000000288000001L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration501 = new BitSet(new long[]{0x0000000000000000L,0x0000000288000000L});
    public static final BitSet FOLLOW_97_in_actorDeclaration505 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration507 = new BitSet(new long[]{0x0000000000000000L,0x0000000088000000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration511 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_95_in_actorDeclaration514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_actorDeclaration600 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_typeAttrs_in_actorDeclaration604 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_93_in_actorDeclaration606 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration616 = new BitSet(new long[]{0x0000000000000000L,0x0000001C00000000L});
    public static final BitSet FOLLOW_98_in_actorDeclaration625 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration627 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_99_in_actorDeclaration659 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration661 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_actorDeclaration715 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ACTION_in_actorDeclaration725 = new BitSet(new long[]{0x0000000000000000L,0x0000000041000400L});
    public static final BitSet FOLLOW_actionInputs_in_actorDeclaration729 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_actorDeclaration732 = new BitSet(new long[]{0x0000000000000000L,0x0000000289000401L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration736 = new BitSet(new long[]{0x0000000000000000L,0x0000000288000001L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration741 = new BitSet(new long[]{0x0000000000000000L,0x0000000288000000L});
    public static final BitSet FOLLOW_97_in_actorDeclaration745 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration747 = new BitSet(new long[]{0x0000000000000000L,0x0000000088000000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration751 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_95_in_actorDeclaration754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INITIALIZE_in_actorDeclaration808 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_actorDeclaration810 = new BitSet(new long[]{0x0000000000000000L,0x0000000289000401L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration814 = new BitSet(new long[]{0x0000000000000000L,0x0000000288000001L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration817 = new BitSet(new long[]{0x0000000000000000L,0x0000000288000000L});
    public static final BitSet FOLLOW_97_in_actorDeclaration821 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration823 = new BitSet(new long[]{0x0000000000000000L,0x0000000088000000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration827 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_95_in_actorDeclaration830 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_priorityOrder_in_actorDeclaration877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FUNCTION_in_actorDeclaration886 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration888 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_actorDeclaration890 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000400L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration893 = new BitSet(new long[]{0x0000000000000000L,0x0000000024000000L});
    public static final BitSet FOLLOW_90_in_actorDeclaration896 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration898 = new BitSet(new long[]{0x0000000000000000L,0x0000000024000000L});
    public static final BitSet FOLLOW_93_in_actorDeclaration904 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_actorDeclaration906 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_typeDef_in_actorDeclaration908 = new BitSet(new long[]{0x0000000000000000L,0x0000000200800000L});
    public static final BitSet FOLLOW_97_in_actorDeclaration915 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration917 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_actorDeclaration921 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration929 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_95_in_actorDeclaration935 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROCEDURE_in_actorDeclaration965 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration967 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_actorDeclaration969 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000400L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration972 = new BitSet(new long[]{0x0000000000000000L,0x0000000024000000L});
    public static final BitSet FOLLOW_90_in_actorDeclaration975 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration977 = new BitSet(new long[]{0x0000000000000000L,0x0000000024000000L});
    public static final BitSet FOLLOW_93_in_actorDeclaration983 = new BitSet(new long[]{0x0000000000000000L,0x0000004200000000L});
    public static final BitSet FOLLOW_97_in_actorDeclaration990 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration992 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_102_in_actorDeclaration1000 = new BitSet(new long[]{0x0000000000000000L,0x0800004080000400L,0x0000000000000028L});
    public static final BitSet FOLLOW_statement_in_actorDeclaration1002 = new BitSet(new long[]{0x0000000000000000L,0x0800004080000400L,0x0000000000000028L});
    public static final BitSet FOLLOW_95_in_actorDeclaration1005 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations1042 = new BitSet(new long[]{0xA000000000000002L,0x000000000000041EL});
    public static final BitSet FOLLOW_schedule_in_actorDeclarations1046 = new BitSet(new long[]{0xA000000000000002L,0x000000000000040EL});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations1048 = new BitSet(new long[]{0xA000000000000002L,0x000000000000040EL});
    public static final BitSet FOLLOW_schedule_in_actorDeclarations1065 = new BitSet(new long[]{0xA000000000000002L,0x000000000000040EL});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations1067 = new BitSet(new long[]{0xA000000000000002L,0x000000000000040EL});
    public static final BitSet FOLLOW_103_in_actorImport1087 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000400L});
    public static final BitSet FOLLOW_104_in_actorImport1092 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000400L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport1094 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_actorImport1096 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport1102 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_actorImport1104 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_actorParameter1119 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_ID_in_actorParameter1121 = new BitSet(new long[]{0x0000000000000002L,0x0000000400000000L});
    public static final BitSet FOLLOW_98_in_actorParameter1124 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_actorParameter1126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters1148 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_actorParameters1151 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters1153 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorPortDecls1172 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_actorPortDecls1175 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorPortDecls1177 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_un_expr_in_expression1198 = new BitSet(new long[]{0x0000000000000002L,0x01FFFE04000001E0L});
    public static final BitSet FOLLOW_bop_in_expression1204 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_un_expr_in_expression1206 = new BitSet(new long[]{0x0000000000000002L,0x01FFFE04000001E0L});
    public static final BitSet FOLLOW_105_in_bop1245 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_106_in_bop1249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_107_in_bop1259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_108_in_bop1263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_109_in_bop1272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_110_in_bop1280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_bop1288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_111_in_bop1296 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_112_in_bop1304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_113_in_bop1312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_bop1320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_115_in_bop1328 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_116_in_bop1336 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_117_in_bop1344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_in_bop1352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_bop1360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DIV_in_bop1368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_118_in_bop1376 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_119_in_bop1384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TIMES_in_bop1392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_120_in_bop1400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfix_expression_in_un_expr1411 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_un_op_in_un_expr1421 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_un_expr_in_un_expr1423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_un_op1440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_121_in_un_op1448 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_122_in_un_op1456 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_postfix_expression1469 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1473 = new BitSet(new long[]{0x0000000000000000L,0x0000000002800000L});
    public static final BitSet FOLLOW_87_in_postfix_expression1476 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_expressionGenerators_in_postfix_expression1480 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_postfix_expression1484 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_123_in_postfix_expression1501 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1505 = new BitSet(new long[]{0x0000000000000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_124_in_postfix_expression1507 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1511 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_125_in_postfix_expression1513 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1517 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_95_in_postfix_expression1519 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_postfix_expression1538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_postfix_expression1546 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1548 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_93_in_postfix_expression1550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_postfix_expression1560 = new BitSet(new long[]{0x0000000000000002L,0x0000000011000000L});
    public static final BitSet FOLLOW_92_in_postfix_expression1568 = new BitSet(new long[]{0x0000000000000000L,0xCE00000031051440L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1570 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_93_in_postfix_expression1573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_postfix_expression1593 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1595 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_postfix_expression1597 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_126_in_constant1634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_127_in_constant1646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_constant1658 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_constant1670 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_constant1682 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_expressionGenerator1698 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_typeDef_in_expressionGenerator1700 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_ID_in_expressionGenerator1702 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_129_in_expressionGenerator1704 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_expressionGenerator1706 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators1716 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_expressionGenerators1719 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators1721 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_expression_in_expressions1735 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_expressions1738 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_expressions1740 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_ID_in_idents1759 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_idents1762 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_ID_in_idents1764 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality1783 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_113_in_priorityInequality1786 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000400L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality1788 = new BitSet(new long[]{0x0000000000000000L,0x0002001000000000L});
    public static final BitSet FOLLOW_100_in_priorityInequality1792 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRIORITY_in_priorityOrder1809 = new BitSet(new long[]{0x0000000000000000L,0x0000010080000400L});
    public static final BitSet FOLLOW_priorityInequality_in_priorityOrder1811 = new BitSet(new long[]{0x0000000000000000L,0x0000010080000400L});
    public static final BitSet FOLLOW_95_in_priorityOrder1814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent1835 = new BitSet(new long[]{0x0000000000000002L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_qualifiedIdent1838 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent1840 = new BitSet(new long[]{0x0000000000000002L,0x0000000100000000L});
    public static final BitSet FOLLOW_SCHEDULE_in_schedule1865 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_130_in_schedule1867 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_ID_in_schedule1869 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_schedule1871 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000400L});
    public static final BitSet FOLLOW_stateTransition_in_schedule1873 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000400L});
    public static final BitSet FOLLOW_95_in_schedule1876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stateTransition1899 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_stateTransition1901 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000400L});
    public static final BitSet FOLLOW_qualifiedIdent_in_stateTransition1903 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_93_in_stateTransition1905 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_stateTransition1907 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_ID_in_stateTransition1909 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_stateTransition1911 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_statement1937 = new BitSet(new long[]{0x0000000000000000L,0x0800004280000400L,0x0000000000000028L});
    public static final BitSet FOLLOW_97_in_statement1940 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_varDecls_in_statement1942 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_statement1944 = new BitSet(new long[]{0x0000000000000000L,0x0800004080000400L,0x0000000000000028L});
    public static final BitSet FOLLOW_statement_in_statement1948 = new BitSet(new long[]{0x0000000000000000L,0x0800004080000400L,0x0000000000000028L});
    public static final BitSet FOLLOW_95_in_statement1951 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_131_in_statement1957 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_statement1959 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_129_in_statement1961 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_statement1964 = new BitSet(new long[]{0x0000000000000000L,0x0000000208000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_132_in_statement1967 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_statement1969 = new BitSet(new long[]{0x0000000000000000L,0x0000000208000000L});
    public static final BitSet FOLLOW_97_in_statement1975 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_varDecls_in_statement1977 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_statement1981 = new BitSet(new long[]{0x0000000000000000L,0x0800004080000400L,0x0000000000000028L});
    public static final BitSet FOLLOW_statement_in_statement1983 = new BitSet(new long[]{0x0000000000000000L,0x0800004080000400L,0x0000000000000028L});
    public static final BitSet FOLLOW_95_in_statement1986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_123_in_statement1992 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_statement1994 = new BitSet(new long[]{0x0000000000000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_124_in_statement1996 = new BitSet(new long[]{0x0000000000000000L,0x2800004080000400L,0x0000000000000028L});
    public static final BitSet FOLLOW_statement_in_statement1998 = new BitSet(new long[]{0x0000000000000000L,0x2800004080000400L,0x0000000000000028L});
    public static final BitSet FOLLOW_125_in_statement2002 = new BitSet(new long[]{0x0000000000000000L,0x0800004080000400L,0x0000000000000028L});
    public static final BitSet FOLLOW_statement_in_statement2004 = new BitSet(new long[]{0x0000000000000000L,0x0800004080000400L,0x0000000000000028L});
    public static final BitSet FOLLOW_95_in_statement2009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_133_in_statement2015 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_statement2017 = new BitSet(new long[]{0x0000000000000000L,0x0000000208000000L});
    public static final BitSet FOLLOW_97_in_statement2020 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_varDecls_in_statement2022 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_statement2026 = new BitSet(new long[]{0x0000000000000000L,0x0800004080000400L,0x0000000000000028L});
    public static final BitSet FOLLOW_statement_in_statement2028 = new BitSet(new long[]{0x0000000000000000L,0x0800004080000400L,0x0000000000000028L});
    public static final BitSet FOLLOW_95_in_statement2031 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_statement2038 = new BitSet(new long[]{0x0000000000000000L,0x0000000811000000L});
    public static final BitSet FOLLOW_88_in_statement2048 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expressions_in_statement2050 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_statement2052 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_99_in_statement2056 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_statement2058 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_statement2060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_statement2085 = new BitSet(new long[]{0x0000000000000000L,0xCE00000031051440L});
    public static final BitSet FOLLOW_expressions_in_statement2087 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_93_in_statement2090 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_statement2092 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_typeAttr2126 = new BitSet(new long[]{0x0000000000000000L,0x0000000400800000L});
    public static final BitSet FOLLOW_87_in_typeAttr2129 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_typeDef_in_typeAttr2131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_typeAttr2145 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_typeAttr2147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs2166 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_typeAttrs2169 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs2171 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_ID_in_typeDef2188 = new BitSet(new long[]{0x0000000000000002L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_typeDef2191 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_typeAttrs_in_typeDef2195 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_93_in_typeDef2197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_varDecl2225 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_ID_in_varDecl2227 = new BitSet(new long[]{0x0000000000000002L,0x0000000C00000000L});
    public static final BitSet FOLLOW_98_in_varDecl2232 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_varDecl2234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_varDecl2254 = new BitSet(new long[]{0x0000000000000000L,0xCE00000011051440L});
    public static final BitSet FOLLOW_expression_in_varDecl2256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_varDeclNoExpr2294 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_ID_in_varDeclNoExpr2296 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varDecl_in_varDecls2315 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_varDecls2318 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_varDecl_in_varDecls2320 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});

}