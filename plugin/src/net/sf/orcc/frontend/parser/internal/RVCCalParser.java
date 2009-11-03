// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g 2009-11-03 16:24:54

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "INPUTS", "OUTPUTS", "PARAMETERS", "STATEMENTS", "VARIABLE", "VARIABLES", "ACTOR_DECLS", "STATE_VAR", "TRANSITION", "TRANSITIONS", "INEQUALITY", "GUARDS", "TAG", "EXPR", "EXPR_BINARY", "EXPR_UNARY", "OP", "EXPR_LIST", "EXPR_IF", "EXPR_CALL", "EXPR_IDX", "EXPR_VAR", "EXPR_BOOL", "EXPR_FLOAT", "EXPR_INT", "EXPR_STRING", "TYPE", "TYPE_ATTRS", "ASSIGNABLE", "NON_ASSIGNABLE", "QID", "LOGIC_OR", "LOGIC_AND", "BITOR", "BITXOR", "BITAND", "EQ", "NE", "LT", "GT", "LE", "GE", "SHIFT_LEFT", "SHIFT_RIGHT", "DIV_INT", "MOD", "EXP", "BITNOT", "LOGIC_NOT", "NUM_ELTS", "ACTION", "ACTOR", "FUNCTION", "GUARD", "INITIALIZE", "PRIORITY", "PROCEDURE", "REPEAT", "SCHEDULE", "PLUS", "MINUS", "TIMES", "DIV", "LETTER", "ID", "Exponent", "FLOAT", "INTEGER", "EscapeSequence", "STRING", "OctalEscape", "LINE_COMMENT", "MULTI_LINE_COMMENT", "WHITESPACE", "':'", "'['", "']'", "','", "'do'", "'('", "')'", "'==>'", "'end'", "'.'", "'var'", "'='", "':='", "';'", "'-->'", "'begin'", "'import'", "'all'", "'or'", "'||'", "'and'", "'&&'", "'|'", "'&'", "'!='", "'<'", "'>'", "'<='", "'>='", "'<<'", "'>>'", "'div'", "'mod'", "'^'", "'not'", "'#'", "'if'", "'then'", "'else'", "'true'", "'false'", "'for'", "'in'", "'fsm'", "'foreach'", "'..'", "'while'"
    };
    public static final int FUNCTION=56;
    public static final int EXPR_BOOL=26;
    public static final int BITNOT=51;
    public static final int LT=42;
    public static final int OUTPUTS=5;
    public static final int TRANSITION=12;
    public static final int EXPR_VAR=25;
    public static final int LOGIC_NOT=52;
    public static final int LETTER=67;
    public static final int MOD=49;
    public static final int EXPR_CALL=23;
    public static final int INPUTS=4;
    public static final int EXPR_UNARY=19;
    public static final int EOF=-1;
    public static final int ACTION=54;
    public static final int TYPE=30;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int TYPE_ATTRS=31;
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
    public static final int T__95=95;
    public static final int NE=41;
    public static final int ASSIGNABLE=32;
    public static final int GE=45;
    public static final int T__80=80;
    public static final int INITIALIZE=58;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int LINE_COMMENT=75;
    public static final int DIV_INT=48;
    public static final int LOGIC_OR=35;
    public static final int WHITESPACE=77;
    public static final int INEQUALITY=14;
    public static final int NON_ASSIGNABLE=33;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int EXPR_IDX=24;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int SHIFT_LEFT=46;
    public static final int SHIFT_RIGHT=47;
    public static final int BITOR=37;
    public static final int PRIORITY=59;
    public static final int VARIABLE=8;
    public static final int ACTOR_DECLS=10;
    public static final int OP=20;
    public static final int ACTOR=55;
    public static final int STATEMENTS=7;
    public static final int GT=43;
    public static final int REPEAT=61;
    public static final int GUARD=57;
    public static final int EscapeSequence=72;
    public static final int T__79=79;
    public static final int T__78=78;
    public static final int PARAMETERS=6;
    public static final int EXPR_BINARY=18;
    public static final int T__118=118;
    public static final int SCHEDULE=62;
    public static final int T__119=119;
    public static final int T__116=116;
    public static final int T__117=117;
    public static final int T__114=114;
    public static final int T__115=115;
    public static final int T__124=124;
    public static final int T__123=123;
    public static final int Exponent=69;
    public static final int T__122=122;
    public static final int T__121=121;
    public static final int FLOAT=70;
    public static final int T__120=120;
    public static final int EXPR_FLOAT=27;
    public static final int LOGIC_AND=36;
    public static final int ID=68;
    public static final int BITAND=39;
    public static final int EXPR_LIST=21;
    public static final int EXPR=17;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int T__109=109;
    public static final int EXPR_STRING=29;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int T__105=105;
    public static final int BITXOR=38;
    public static final int T__106=106;
    public static final int NUM_ELTS=53;
    public static final int T__111=111;
    public static final int T__110=110;
    public static final int T__113=113;
    public static final int PLUS=63;
    public static final int T__112=112;
    public static final int EXPR_INT=28;
    public static final int INTEGER=71;
    public static final int TRANSITIONS=13;
    public static final int T__102=102;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int MINUS=64;
    public static final int EXPR_IF=22;
    public static final int MULTI_LINE_COMMENT=76;
    public static final int PROCEDURE=60;
    public static final int TAG=16;
    public static final int QID=34;
    public static final int VARIABLES=9;
    public static final int DIV=66;
    public static final int TIMES=65;
    public static final int OctalEscape=74;
    public static final int LE=44;
    public static final int STRING=73;

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
    public String getGrammarFileName() { return "D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g"; }


    public static class actionGuards_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionGuards"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:51:1: actionGuards : GUARD expressions -> expressions ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:51:13: ( GUARD expressions -> expressions )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:51:15: GUARD expressions
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:53:1: actionInput : ( ID ':' )? '[' idents ']' ( actionRepeat )? ;
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

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:53:12: ( ( ID ':' )? '[' idents ']' ( actionRepeat )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:54:2: ( ID ':' )? '[' idents ']' ( actionRepeat )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:54:2: ( ID ':' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==ID) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:54:3: ID ':'
                    {
                    ID3=(Token)match(input,ID,FOLLOW_ID_in_actionInput76); 
                    ID3_tree = (Object)adaptor.create(ID3);
                    adaptor.addChild(root_0, ID3_tree);

                    char_literal4=(Token)match(input,78,FOLLOW_78_in_actionInput78); 
                    char_literal4_tree = (Object)adaptor.create(char_literal4);
                    adaptor.addChild(root_0, char_literal4_tree);


                    }
                    break;

            }

            char_literal5=(Token)match(input,79,FOLLOW_79_in_actionInput82); 
            char_literal5_tree = (Object)adaptor.create(char_literal5);
            adaptor.addChild(root_0, char_literal5_tree);

            pushFollow(FOLLOW_idents_in_actionInput84);
            idents6=idents();

            state._fsp--;

            adaptor.addChild(root_0, idents6.getTree());
            char_literal7=(Token)match(input,80,FOLLOW_80_in_actionInput86); 
            char_literal7_tree = (Object)adaptor.create(char_literal7);
            adaptor.addChild(root_0, char_literal7_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:54:27: ( actionRepeat )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==REPEAT) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:54:27: actionRepeat
                    {
                    pushFollow(FOLLOW_actionRepeat_in_actionInput88);
                    actionRepeat8=actionRepeat();

                    state._fsp--;

                    adaptor.addChild(root_0, actionRepeat8.getTree());

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
    // $ANTLR end "actionInput"

    public static class actionInputs_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionInputs"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:57:1: actionInputs : actionInput ( ',' actionInput )* -> ( actionInput )+ ;
    public final RVCCalParser.actionInputs_return actionInputs() throws RecognitionException {
        RVCCalParser.actionInputs_return retval = new RVCCalParser.actionInputs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal10=null;
        RVCCalParser.actionInput_return actionInput9 = null;

        RVCCalParser.actionInput_return actionInput11 = null;


        Object char_literal10_tree=null;
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleSubtreeStream stream_actionInput=new RewriteRuleSubtreeStream(adaptor,"rule actionInput");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:57:13: ( actionInput ( ',' actionInput )* -> ( actionInput )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:57:15: actionInput ( ',' actionInput )*
            {
            pushFollow(FOLLOW_actionInput_in_actionInputs99);
            actionInput9=actionInput();

            state._fsp--;

            stream_actionInput.add(actionInput9.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:57:27: ( ',' actionInput )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==81) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:57:28: ',' actionInput
            	    {
            	    char_literal10=(Token)match(input,81,FOLLOW_81_in_actionInputs102);  
            	    stream_81.add(char_literal10);

            	    pushFollow(FOLLOW_actionInput_in_actionInputs104);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:59:1: actionOutput : ( ID ':' )? '[' expressions ']' ( actionRepeat )? ;
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

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:59:13: ( ( ID ':' )? '[' expressions ']' ( actionRepeat )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:60:2: ( ID ':' )? '[' expressions ']' ( actionRepeat )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:60:2: ( ID ':' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==ID) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:60:3: ID ':'
                    {
                    ID12=(Token)match(input,ID,FOLLOW_ID_in_actionOutput120); 
                    ID12_tree = (Object)adaptor.create(ID12);
                    adaptor.addChild(root_0, ID12_tree);

                    char_literal13=(Token)match(input,78,FOLLOW_78_in_actionOutput122); 
                    char_literal13_tree = (Object)adaptor.create(char_literal13);
                    adaptor.addChild(root_0, char_literal13_tree);


                    }
                    break;

            }

            char_literal14=(Token)match(input,79,FOLLOW_79_in_actionOutput126); 
            char_literal14_tree = (Object)adaptor.create(char_literal14);
            adaptor.addChild(root_0, char_literal14_tree);

            pushFollow(FOLLOW_expressions_in_actionOutput128);
            expressions15=expressions();

            state._fsp--;

            adaptor.addChild(root_0, expressions15.getTree());
            char_literal16=(Token)match(input,80,FOLLOW_80_in_actionOutput130); 
            char_literal16_tree = (Object)adaptor.create(char_literal16);
            adaptor.addChild(root_0, char_literal16_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:60:32: ( actionRepeat )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==REPEAT) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:60:32: actionRepeat
                    {
                    pushFollow(FOLLOW_actionRepeat_in_actionOutput132);
                    actionRepeat17=actionRepeat();

                    state._fsp--;

                    adaptor.addChild(root_0, actionRepeat17.getTree());

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
    // $ANTLR end "actionOutput"

    public static class actionOutputs_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionOutputs"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:63:1: actionOutputs : actionOutput ( ',' actionOutput )* -> ( actionOutput )+ ;
    public final RVCCalParser.actionOutputs_return actionOutputs() throws RecognitionException {
        RVCCalParser.actionOutputs_return retval = new RVCCalParser.actionOutputs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal19=null;
        RVCCalParser.actionOutput_return actionOutput18 = null;

        RVCCalParser.actionOutput_return actionOutput20 = null;


        Object char_literal19_tree=null;
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleSubtreeStream stream_actionOutput=new RewriteRuleSubtreeStream(adaptor,"rule actionOutput");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:63:14: ( actionOutput ( ',' actionOutput )* -> ( actionOutput )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:63:16: actionOutput ( ',' actionOutput )*
            {
            pushFollow(FOLLOW_actionOutput_in_actionOutputs143);
            actionOutput18=actionOutput();

            state._fsp--;

            stream_actionOutput.add(actionOutput18.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:63:29: ( ',' actionOutput )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==81) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:63:30: ',' actionOutput
            	    {
            	    char_literal19=(Token)match(input,81,FOLLOW_81_in_actionOutputs146);  
            	    stream_81.add(char_literal19);

            	    pushFollow(FOLLOW_actionOutput_in_actionOutputs148);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:65:1: actionRepeat : REPEAT expression -> expression ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:65:13: ( REPEAT expression -> expression )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:65:15: REPEAT expression
            {
            REPEAT21=(Token)match(input,REPEAT,FOLLOW_REPEAT_in_actionRepeat162);  
            stream_REPEAT.add(REPEAT21);

            pushFollow(FOLLOW_expression_in_actionRepeat164);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:67:1: actionStatements : 'do' ( statement )* -> ( statement )* ;
    public final RVCCalParser.actionStatements_return actionStatements() throws RecognitionException {
        RVCCalParser.actionStatements_return retval = new RVCCalParser.actionStatements_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal23=null;
        RVCCalParser.statement_return statement24 = null;


        Object string_literal23_tree=null;
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:67:17: ( 'do' ( statement )* -> ( statement )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:67:19: 'do' ( statement )*
            {
            string_literal23=(Token)match(input,82,FOLLOW_82_in_actionStatements175);  
            stream_82.add(string_literal23);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:67:24: ( statement )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==ID||LA7_0==93||LA7_0==114||LA7_0==122||LA7_0==124) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:67:24: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_actionStatements177);
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
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:67:38: ( statement )*
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:69:1: actor : ( actorImport )* ACTOR ID ( '[' ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' ( actorDeclarations )? 'end' EOF -> ACTOR ID ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS ( actorDeclarations )? ) ;
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
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_80=new RewriteRuleTokenStream(adaptor,"token 80");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_ACTOR=new RewriteRuleTokenStream(adaptor,"token ACTOR");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleTokenStream stream_85=new RewriteRuleTokenStream(adaptor,"token 85");
        RewriteRuleSubtreeStream stream_actorImport=new RewriteRuleSubtreeStream(adaptor,"rule actorImport");
        RewriteRuleSubtreeStream stream_actorDeclarations=new RewriteRuleSubtreeStream(adaptor,"rule actorDeclarations");
        RewriteRuleSubtreeStream stream_actorPortDecls=new RewriteRuleSubtreeStream(adaptor,"rule actorPortDecls");
        RewriteRuleSubtreeStream stream_actorParameters=new RewriteRuleSubtreeStream(adaptor,"rule actorParameters");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:72:6: ( ( actorImport )* ACTOR ID ( '[' ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' ( actorDeclarations )? 'end' EOF -> ACTOR ID ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS ( actorDeclarations )? ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:72:8: ( actorImport )* ACTOR ID ( '[' ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' ( actorDeclarations )? 'end' EOF
            {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:72:8: ( actorImport )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==94) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:72:8: actorImport
            	    {
            	    pushFollow(FOLLOW_actorImport_in_actor195);
            	    actorImport25=actorImport();

            	    state._fsp--;

            	    stream_actorImport.add(actorImport25.getTree());

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            ACTOR26=(Token)match(input,ACTOR,FOLLOW_ACTOR_in_actor198);  
            stream_ACTOR.add(ACTOR26);

            ID27=(Token)match(input,ID,FOLLOW_ID_in_actor200);  
            stream_ID.add(ID27);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:72:30: ( '[' ']' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==79) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:72:31: '[' ']'
                    {
                    char_literal28=(Token)match(input,79,FOLLOW_79_in_actor203);  
                    stream_79.add(char_literal28);

                    char_literal29=(Token)match(input,80,FOLLOW_80_in_actor205);  
                    stream_80.add(char_literal29);


                    }
                    break;

            }

            char_literal30=(Token)match(input,83,FOLLOW_83_in_actor209);  
            stream_83.add(char_literal30);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:72:45: ( actorParameters )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==ID) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:72:45: actorParameters
                    {
                    pushFollow(FOLLOW_actorParameters_in_actor211);
                    actorParameters31=actorParameters();

                    state._fsp--;

                    stream_actorParameters.add(actorParameters31.getTree());

                    }
                    break;

            }

            char_literal32=(Token)match(input,84,FOLLOW_84_in_actor214);  
            stream_84.add(char_literal32);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:73:8: (inputs= actorPortDecls )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==ID) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:73:8: inputs= actorPortDecls
                    {
                    pushFollow(FOLLOW_actorPortDecls_in_actor219);
                    inputs=actorPortDecls();

                    state._fsp--;

                    stream_actorPortDecls.add(inputs.getTree());

                    }
                    break;

            }

            string_literal33=(Token)match(input,85,FOLLOW_85_in_actor222);  
            stream_85.add(string_literal33);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:73:38: (outputs= actorPortDecls )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==ID) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:73:38: outputs= actorPortDecls
                    {
                    pushFollow(FOLLOW_actorPortDecls_in_actor226);
                    outputs=actorPortDecls();

                    state._fsp--;

                    stream_actorPortDecls.add(outputs.getTree());

                    }
                    break;

            }

            char_literal34=(Token)match(input,78,FOLLOW_78_in_actor229);  
            stream_78.add(char_literal34);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:74:2: ( actorDeclarations )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==ACTION||LA13_0==FUNCTION||(LA13_0>=INITIALIZE && LA13_0<=PROCEDURE)||LA13_0==SCHEDULE||LA13_0==ID) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:74:2: actorDeclarations
                    {
                    pushFollow(FOLLOW_actorDeclarations_in_actor232);
                    actorDeclarations35=actorDeclarations();

                    state._fsp--;

                    stream_actorDeclarations.add(actorDeclarations35.getTree());

                    }
                    break;

            }

            string_literal36=(Token)match(input,86,FOLLOW_86_in_actor235);  
            stream_86.add(string_literal36);

            EOF37=(Token)match(input,EOF,FOLLOW_EOF_in_actor237);  
            stream_EOF.add(EOF37);



            // AST REWRITE
            // elements: ID, actorDeclarations, ACTOR, actorParameters, inputs, outputs
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
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:76:2: ^( PARAMETERS ( actorParameters )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETERS, "PARAMETERS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:76:15: ( actorParameters )?
                if ( stream_actorParameters.hasNext() ) {
                    adaptor.addChild(root_1, stream_actorParameters.nextTree());

                }
                stream_actorParameters.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:77:2: ^( INPUTS ( $inputs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:77:11: ( $inputs)?
                if ( stream_inputs.hasNext() ) {
                    adaptor.addChild(root_1, stream_inputs.nextTree());

                }
                stream_inputs.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:78:2: ^( OUTPUTS ( $outputs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:78:12: ( $outputs)?
                if ( stream_outputs.hasNext() ) {
                    adaptor.addChild(root_1, stream_outputs.nextTree());

                }
                stream_outputs.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:79:2: ^( ACTOR_DECLS ( actorDeclarations )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ACTOR_DECLS, "ACTOR_DECLS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:79:16: ( actorDeclarations )?
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:81:1: id : ID ;
    public final RVCCalParser.id_return id() throws RecognitionException {
        RVCCalParser.id_return retval = new RVCCalParser.id_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID38=null;

        Object ID38_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:87:3: ( ID )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:87:5: ID
            {
            root_0 = (Object)adaptor.nil();

            ID38=(Token)match(input,ID,FOLLOW_ID_in_id293); 
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:89:1: actorDeclaration : ( id ( ( ( ( '.' ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' ) | ACTION ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | priorityOrder -> priorityOrder | FUNCTION ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end' -> ^( FUNCTION ID ^( PARAMETERS ( varDeclNoExpr )* ) ^( VARIABLES ( varDecls )? ) expression ) | PROCEDURE ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end' -> ^( PROCEDURE ID ^( PARAMETERS ( varDeclNoExpr )* ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( statement )* ) ) );
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
        Token string_literal66=null;
        Token string_literal69=null;
        Token string_literal72=null;
        Token INITIALIZE73=null;
        Token string_literal74=null;
        Token string_literal77=null;
        Token string_literal80=null;
        Token FUNCTION82=null;
        Token ID83=null;
        Token char_literal84=null;
        Token char_literal86=null;
        Token char_literal88=null;
        Token string_literal89=null;
        Token string_literal91=null;
        Token char_literal93=null;
        Token string_literal95=null;
        Token PROCEDURE96=null;
        Token ID97=null;
        Token char_literal98=null;
        Token char_literal100=null;
        Token char_literal102=null;
        Token string_literal103=null;
        Token string_literal105=null;
        Token string_literal107=null;
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

        RVCCalParser.actionInputs_return actionInputs65 = null;

        RVCCalParser.actionOutputs_return actionOutputs67 = null;

        RVCCalParser.actionGuards_return actionGuards68 = null;

        RVCCalParser.varDecls_return varDecls70 = null;

        RVCCalParser.actionStatements_return actionStatements71 = null;

        RVCCalParser.actionOutputs_return actionOutputs75 = null;

        RVCCalParser.actionGuards_return actionGuards76 = null;

        RVCCalParser.varDecls_return varDecls78 = null;

        RVCCalParser.actionStatements_return actionStatements79 = null;

        RVCCalParser.priorityOrder_return priorityOrder81 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr85 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr87 = null;

        RVCCalParser.typeDef_return typeDef90 = null;

        RVCCalParser.varDecls_return varDecls92 = null;

        RVCCalParser.expression_return expression94 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr99 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr101 = null;

        RVCCalParser.varDecls_return varDecls104 = null;

        RVCCalParser.statement_return statement106 = null;


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
        Object string_literal66_tree=null;
        Object string_literal69_tree=null;
        Object string_literal72_tree=null;
        Object INITIALIZE73_tree=null;
        Object string_literal74_tree=null;
        Object string_literal77_tree=null;
        Object string_literal80_tree=null;
        Object FUNCTION82_tree=null;
        Object ID83_tree=null;
        Object char_literal84_tree=null;
        Object char_literal86_tree=null;
        Object char_literal88_tree=null;
        Object string_literal89_tree=null;
        Object string_literal91_tree=null;
        Object char_literal93_tree=null;
        Object string_literal95_tree=null;
        Object PROCEDURE96_tree=null;
        Object ID97_tree=null;
        Object char_literal98_tree=null;
        Object char_literal100_tree=null;
        Object char_literal102_tree=null;
        Object string_literal103_tree=null;
        Object string_literal105_tree=null;
        Object string_literal107_tree=null;
        RewriteRuleTokenStream stream_FUNCTION=new RewriteRuleTokenStream(adaptor,"token FUNCTION");
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_93=new RewriteRuleTokenStream(adaptor,"token 93");
        RewriteRuleTokenStream stream_INITIALIZE=new RewriteRuleTokenStream(adaptor,"token INITIALIZE");
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleTokenStream stream_91=new RewriteRuleTokenStream(adaptor,"token 91");
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_PROCEDURE=new RewriteRuleTokenStream(adaptor,"token PROCEDURE");
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_ACTION=new RewriteRuleTokenStream(adaptor,"token ACTION");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleTokenStream stream_88=new RewriteRuleTokenStream(adaptor,"token 88");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleTokenStream stream_85=new RewriteRuleTokenStream(adaptor,"token 85");
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:89:17: ( id ( ( ( ( '.' ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' ) | ACTION ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | priorityOrder -> priorityOrder | FUNCTION ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end' -> ^( FUNCTION ID ^( PARAMETERS ( varDeclNoExpr )* ) ^( VARIABLES ( varDecls )? ) expression ) | PROCEDURE ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end' -> ^( PROCEDURE ID ^( PARAMETERS ( varDeclNoExpr )* ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( statement )* ) ) )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:94:3: id ( ( ( ( '.' ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' )
                    {
                    pushFollow(FOLLOW_id_in_actorDeclaration312);
                    id39=id();

                    state._fsp--;

                    stream_id.add(id39.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:94:6: ( ( ( ( '.' ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' )
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==78||LA27_0==87) ) {
                        alt27=1;
                    }
                    else if ( (LA27_0==ID||LA27_0==83) ) {
                        alt27=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 27, 0, input);

                        throw nvae;
                    }
                    switch (alt27) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:95:5: ( ( ( '.' ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) )
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:95:5: ( ( ( '.' ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) )
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:95:6: ( ( '.' ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) )
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:95:6: ( ( '.' ID )* )
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:95:7: ( '.' ID )*
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:95:7: ( '.' ID )*
                            loop14:
                            do {
                                int alt14=2;
                                int LA14_0 = input.LA(1);

                                if ( (LA14_0==87) ) {
                                    alt14=1;
                                }


                                switch (alt14) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:95:8: '.' ID
                            	    {
                            	    char_literal40=(Token)match(input,87,FOLLOW_87_in_actorDeclaration323);  
                            	    stream_87.add(char_literal40);

                            	    ID41=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration325);  
                            	    stream_ID.add(ID41);


                            	    }
                            	    break;

                            	default :
                            	    break loop14;
                                }
                            } while (true);


                            }

                            char_literal42=(Token)match(input,78,FOLLOW_78_in_actorDeclaration330);  
                            stream_78.add(char_literal42);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:7: ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) )
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
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:8: ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                                    {
                                    ACTION43=(Token)match(input,ACTION,FOLLOW_ACTION_in_actorDeclaration339);  
                                    stream_ACTION.add(ACTION43);

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:21: (inputs= actionInputs )?
                                    int alt15=2;
                                    int LA15_0 = input.LA(1);

                                    if ( (LA15_0==ID||LA15_0==79) ) {
                                        alt15=1;
                                    }
                                    switch (alt15) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:21: inputs= actionInputs
                                            {
                                            pushFollow(FOLLOW_actionInputs_in_actorDeclaration343);
                                            inputs=actionInputs();

                                            state._fsp--;

                                            stream_actionInputs.add(inputs.getTree());

                                            }
                                            break;

                                    }

                                    string_literal44=(Token)match(input,85,FOLLOW_85_in_actorDeclaration346);  
                                    stream_85.add(string_literal44);

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:49: (outputs= actionOutputs )?
                                    int alt16=2;
                                    int LA16_0 = input.LA(1);

                                    if ( (LA16_0==ID||LA16_0==79) ) {
                                        alt16=1;
                                    }
                                    switch (alt16) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:49: outputs= actionOutputs
                                            {
                                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration350);
                                            outputs=actionOutputs();

                                            state._fsp--;

                                            stream_actionOutputs.add(outputs.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:71: (guards= actionGuards )?
                                    int alt17=2;
                                    int LA17_0 = input.LA(1);

                                    if ( (LA17_0==GUARD) ) {
                                        alt17=1;
                                    }
                                    switch (alt17) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:71: guards= actionGuards
                                            {
                                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration355);
                                            guards=actionGuards();

                                            state._fsp--;

                                            stream_actionGuards.add(guards.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:86: ( 'var' varDecls )?
                                    int alt18=2;
                                    int LA18_0 = input.LA(1);

                                    if ( (LA18_0==88) ) {
                                        alt18=1;
                                    }
                                    switch (alt18) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:87: 'var' varDecls
                                            {
                                            string_literal45=(Token)match(input,88,FOLLOW_88_in_actorDeclaration359);  
                                            stream_88.add(string_literal45);

                                            pushFollow(FOLLOW_varDecls_in_actorDeclaration361);
                                            varDecls46=varDecls();

                                            state._fsp--;

                                            stream_varDecls.add(varDecls46.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:104: ( actionStatements )?
                                    int alt19=2;
                                    int LA19_0 = input.LA(1);

                                    if ( (LA19_0==82) ) {
                                        alt19=1;
                                    }
                                    switch (alt19) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:104: actionStatements
                                            {
                                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration365);
                                            actionStatements47=actionStatements();

                                            state._fsp--;

                                            stream_actionStatements.add(actionStatements47.getTree());

                                            }
                                            break;

                                    }

                                    string_literal48=(Token)match(input,86,FOLLOW_86_in_actorDeclaration368);  
                                    stream_86.add(string_literal48);



                                    // AST REWRITE
                                    // elements: outputs, inputs, guards, actionStatements, id, ACTION, varDecls, ID
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
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:97:12: ^( ACTION ^( TAG id ( ID )* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot(stream_ACTION.nextNode(), root_1);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:97:21: ^( TAG id ( ID )* )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TAG, "TAG"), root_2);

                                        adaptor.addChild(root_2, stream_id.nextTree());
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:97:30: ( ID )*
                                        while ( stream_ID.hasNext() ) {
                                            adaptor.addChild(root_2, stream_ID.nextNode());

                                        }
                                        stream_ID.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:97:35: ^( INPUTS ( $inputs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:97:44: ( $inputs)?
                                        if ( stream_inputs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_inputs.nextTree());

                                        }
                                        stream_inputs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:97:54: ^( OUTPUTS ( $outputs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:97:64: ( $outputs)?
                                        if ( stream_outputs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_outputs.nextTree());

                                        }
                                        stream_outputs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:98:9: ^( GUARDS ( $guards)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:98:18: ( $guards)?
                                        if ( stream_guards.hasNext() ) {
                                            adaptor.addChild(root_2, stream_guards.nextTree());

                                        }
                                        stream_guards.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:98:28: ^( VARIABLES ( varDecls )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLES, "VARIABLES"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:98:40: ( varDecls )?
                                        if ( stream_varDecls.hasNext() ) {
                                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                                        }
                                        stream_varDecls.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:98:51: ^( STATEMENTS ( actionStatements )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:98:64: ( actionStatements )?
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
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:7: INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                                    {
                                    INITIALIZE49=(Token)match(input,INITIALIZE,FOLLOW_INITIALIZE_in_actorDeclaration446);  
                                    stream_INITIALIZE.add(INITIALIZE49);

                                    string_literal50=(Token)match(input,85,FOLLOW_85_in_actorDeclaration448);  
                                    stream_85.add(string_literal50);

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:24: ( actionOutputs )?
                                    int alt20=2;
                                    int LA20_0 = input.LA(1);

                                    if ( (LA20_0==ID||LA20_0==79) ) {
                                        alt20=1;
                                    }
                                    switch (alt20) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:24: actionOutputs
                                            {
                                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration450);
                                            actionOutputs51=actionOutputs();

                                            state._fsp--;

                                            stream_actionOutputs.add(actionOutputs51.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:39: ( actionGuards )?
                                    int alt21=2;
                                    int LA21_0 = input.LA(1);

                                    if ( (LA21_0==GUARD) ) {
                                        alt21=1;
                                    }
                                    switch (alt21) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:39: actionGuards
                                            {
                                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration453);
                                            actionGuards52=actionGuards();

                                            state._fsp--;

                                            stream_actionGuards.add(actionGuards52.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:53: ( 'var' varDecls )?
                                    int alt22=2;
                                    int LA22_0 = input.LA(1);

                                    if ( (LA22_0==88) ) {
                                        alt22=1;
                                    }
                                    switch (alt22) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:54: 'var' varDecls
                                            {
                                            string_literal53=(Token)match(input,88,FOLLOW_88_in_actorDeclaration457);  
                                            stream_88.add(string_literal53);

                                            pushFollow(FOLLOW_varDecls_in_actorDeclaration459);
                                            varDecls54=varDecls();

                                            state._fsp--;

                                            stream_varDecls.add(varDecls54.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:71: ( actionStatements )?
                                    int alt23=2;
                                    int LA23_0 = input.LA(1);

                                    if ( (LA23_0==82) ) {
                                        alt23=1;
                                    }
                                    switch (alt23) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:71: actionStatements
                                            {
                                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration463);
                                            actionStatements55=actionStatements();

                                            state._fsp--;

                                            stream_actionStatements.add(actionStatements55.getTree());

                                            }
                                            break;

                                    }

                                    string_literal56=(Token)match(input,86,FOLLOW_86_in_actorDeclaration466);  
                                    stream_86.add(string_literal56);



                                    // AST REWRITE
                                    // elements: actionStatements, outputs, id, guards, INITIALIZE, varDecls, ID
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
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:101:12: ^( INITIALIZE ^( TAG id ( ID )* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot(stream_INITIALIZE.nextNode(), root_1);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:101:25: ^( TAG id ( ID )* )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TAG, "TAG"), root_2);

                                        adaptor.addChild(root_2, stream_id.nextTree());
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:101:34: ( ID )*
                                        while ( stream_ID.hasNext() ) {
                                            adaptor.addChild(root_2, stream_ID.nextNode());

                                        }
                                        stream_ID.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        adaptor.addChild(root_1, (Object)adaptor.create(INPUTS, "INPUTS"));
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:101:46: ^( OUTPUTS ( $outputs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:101:56: ( $outputs)?
                                        if ( stream_outputs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_outputs.nextTree());

                                        }
                                        stream_outputs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:102:9: ^( GUARDS ( $guards)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:102:18: ( $guards)?
                                        if ( stream_guards.hasNext() ) {
                                            adaptor.addChild(root_2, stream_guards.nextTree());

                                        }
                                        stream_guards.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:102:28: ^( VARIABLES ( varDecls )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLES, "VARIABLES"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:102:40: ( varDecls )?
                                        if ( stream_varDecls.hasNext() ) {
                                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                                        }
                                        stream_varDecls.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:102:51: ^( STATEMENTS ( actionStatements )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:102:64: ( actionStatements )?
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
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:108:5: ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';'
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:108:5: ( '(' attrs= typeAttrs ')' )?
                            int alt25=2;
                            int LA25_0 = input.LA(1);

                            if ( (LA25_0==83) ) {
                                alt25=1;
                            }
                            switch (alt25) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:108:6: '(' attrs= typeAttrs ')'
                                    {
                                    char_literal57=(Token)match(input,83,FOLLOW_83_in_actorDeclaration552);  
                                    stream_83.add(char_literal57);

                                    pushFollow(FOLLOW_typeAttrs_in_actorDeclaration556);
                                    attrs=typeAttrs();

                                    state._fsp--;

                                    stream_typeAttrs.add(attrs.getTree());
                                    char_literal58=(Token)match(input,84,FOLLOW_84_in_actorDeclaration558);  
                                    stream_84.add(char_literal58);


                                    }
                                    break;

                            }

                            varName=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration568);  
                            stream_ID.add(varName);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:110:5: ( '=' expression -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) )
                            int alt26=3;
                            switch ( input.LA(1) ) {
                            case 89:
                                {
                                alt26=1;
                                }
                                break;
                            case 90:
                                {
                                alt26=2;
                                }
                                break;
                            case 91:
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
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:110:8: '=' expression
                                    {
                                    char_literal59=(Token)match(input,89,FOLLOW_89_in_actorDeclaration577);  
                                    stream_89.add(char_literal59);

                                    pushFollow(FOLLOW_expression_in_actorDeclaration579);
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
                                    // 110:23: -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression )
                                    {
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:110:26: ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:110:38: ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_id.nextTree());
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:110:48: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:110:61: ( $attrs)?
                                        if ( stream_attrs.hasNext() ) {
                                            adaptor.addChild(root_3, stream_attrs.nextTree());

                                        }
                                        stream_attrs.reset();

                                        adaptor.addChild(root_2, root_3);
                                        }

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
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:111:8: ':=' expression
                                    {
                                    string_literal61=(Token)match(input,90,FOLLOW_90_in_actorDeclaration615);  
                                    stream_90.add(string_literal61);

                                    pushFollow(FOLLOW_expression_in_actorDeclaration617);
                                    expression62=expression();

                                    state._fsp--;

                                    stream_expression.add(expression62.getTree());


                                    // AST REWRITE
                                    // elements: id, varName, expression, attrs
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
                                    // 111:24: -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression )
                                    {
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:111:27: ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:111:39: ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_id.nextTree());
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:111:49: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:111:62: ( $attrs)?
                                        if ( stream_attrs.hasNext() ) {
                                            adaptor.addChild(root_3, stream_attrs.nextTree());

                                        }
                                        stream_attrs.reset();

                                        adaptor.addChild(root_2, root_3);
                                        }

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
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:8: 
                                    {

                                    // AST REWRITE
                                    // elements: varName, attrs, id
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
                                    // 112:8: -> ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE )
                                    {
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:11: ^( STATE_VAR ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:23: ^( TYPE id ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_id.nextTree());
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:33: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:46: ( $attrs)?
                                        if ( stream_attrs.hasNext() ) {
                                            adaptor.addChild(root_3, stream_attrs.nextTree());

                                        }
                                        stream_attrs.reset();

                                        adaptor.addChild(root_2, root_3);
                                        }

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

                            char_literal63=(Token)match(input,91,FOLLOW_91_in_actorDeclaration679);  
                            stream_91.add(char_literal63);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:3: ACTION ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                    {
                    ACTION64=(Token)match(input,ACTION,FOLLOW_ACTION_in_actorDeclaration689);  
                    stream_ACTION.add(ACTION64);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:10: ( actionInputs )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==ID||LA28_0==79) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:10: actionInputs
                            {
                            pushFollow(FOLLOW_actionInputs_in_actorDeclaration691);
                            actionInputs65=actionInputs();

                            state._fsp--;

                            stream_actionInputs.add(actionInputs65.getTree());

                            }
                            break;

                    }

                    string_literal66=(Token)match(input,85,FOLLOW_85_in_actorDeclaration694);  
                    stream_85.add(string_literal66);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:30: ( actionOutputs )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==ID||LA29_0==79) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:30: actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration696);
                            actionOutputs67=actionOutputs();

                            state._fsp--;

                            stream_actionOutputs.add(actionOutputs67.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:45: ( actionGuards )?
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==GUARD) ) {
                        alt30=1;
                    }
                    switch (alt30) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:45: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration699);
                            actionGuards68=actionGuards();

                            state._fsp--;

                            stream_actionGuards.add(actionGuards68.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:59: ( 'var' varDecls )?
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==88) ) {
                        alt31=1;
                    }
                    switch (alt31) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:60: 'var' varDecls
                            {
                            string_literal69=(Token)match(input,88,FOLLOW_88_in_actorDeclaration703);  
                            stream_88.add(string_literal69);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration705);
                            varDecls70=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls70.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:77: ( actionStatements )?
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==82) ) {
                        alt32=1;
                    }
                    switch (alt32) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:77: actionStatements
                            {
                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration709);
                            actionStatements71=actionStatements();

                            state._fsp--;

                            stream_actionStatements.add(actionStatements71.getTree());

                            }
                            break;

                    }

                    string_literal72=(Token)match(input,86,FOLLOW_86_in_actorDeclaration712);  
                    stream_86.add(string_literal72);



                    // AST REWRITE
                    // elements: guards, inputs, ACTION, actionStatements, varDecls, outputs
                    // token labels: 
                    // rule labels: retval, inputs, guards, outputs
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_inputs=new RewriteRuleSubtreeStream(adaptor,"rule inputs",inputs!=null?inputs.tree:null);
                    RewriteRuleSubtreeStream stream_guards=new RewriteRuleSubtreeStream(adaptor,"rule guards",guards!=null?guards.tree:null);
                    RewriteRuleSubtreeStream stream_outputs=new RewriteRuleSubtreeStream(adaptor,"rule outputs",outputs!=null?outputs.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 117:3: -> ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:6: ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_ACTION.nextNode(), root_1);

                        adaptor.addChild(root_1, (Object)adaptor.create(TAG, "TAG"));
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:19: ^( INPUTS ( $inputs)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:28: ( $inputs)?
                        if ( stream_inputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_inputs.nextTree());

                        }
                        stream_inputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:38: ^( OUTPUTS ( $outputs)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:48: ( $outputs)?
                        if ( stream_outputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_outputs.nextTree());

                        }
                        stream_outputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:59: ^( GUARDS ( $guards)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:68: ( $guards)?
                        if ( stream_guards.hasNext() ) {
                            adaptor.addChild(root_2, stream_guards.nextTree());

                        }
                        stream_guards.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:78: ^( VARIABLES ( varDecls )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLES, "VARIABLES"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:90: ( varDecls )?
                        if ( stream_varDecls.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                        }
                        stream_varDecls.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:101: ^( STATEMENTS ( actionStatements )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:114: ( actionStatements )?
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:3: INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                    {
                    INITIALIZE73=(Token)match(input,INITIALIZE,FOLLOW_INITIALIZE_in_actorDeclaration766);  
                    stream_INITIALIZE.add(INITIALIZE73);

                    string_literal74=(Token)match(input,85,FOLLOW_85_in_actorDeclaration768);  
                    stream_85.add(string_literal74);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:20: ( actionOutputs )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==ID||LA33_0==79) ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:20: actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration770);
                            actionOutputs75=actionOutputs();

                            state._fsp--;

                            stream_actionOutputs.add(actionOutputs75.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:35: ( actionGuards )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==GUARD) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:35: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration773);
                            actionGuards76=actionGuards();

                            state._fsp--;

                            stream_actionGuards.add(actionGuards76.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:49: ( 'var' varDecls )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==88) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:50: 'var' varDecls
                            {
                            string_literal77=(Token)match(input,88,FOLLOW_88_in_actorDeclaration777);  
                            stream_88.add(string_literal77);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration779);
                            varDecls78=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls78.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:67: ( actionStatements )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==82) ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:67: actionStatements
                            {
                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration783);
                            actionStatements79=actionStatements();

                            state._fsp--;

                            stream_actionStatements.add(actionStatements79.getTree());

                            }
                            break;

                    }

                    string_literal80=(Token)match(input,86,FOLLOW_86_in_actorDeclaration786);  
                    stream_86.add(string_literal80);



                    // AST REWRITE
                    // elements: INITIALIZE, outputs, varDecls, guards, actionStatements
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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:6: ^( INITIALIZE TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_INITIALIZE.nextNode(), root_1);

                        adaptor.addChild(root_1, (Object)adaptor.create(TAG, "TAG"));
                        adaptor.addChild(root_1, (Object)adaptor.create(INPUTS, "INPUTS"));
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:30: ^( OUTPUTS ( $outputs)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:40: ( $outputs)?
                        if ( stream_outputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_outputs.nextTree());

                        }
                        stream_outputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:51: ^( GUARDS ( $guards)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:60: ( $guards)?
                        if ( stream_guards.hasNext() ) {
                            adaptor.addChild(root_2, stream_guards.nextTree());

                        }
                        stream_guards.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:70: ^( VARIABLES ( varDecls )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLES, "VARIABLES"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:82: ( varDecls )?
                        if ( stream_varDecls.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                        }
                        stream_varDecls.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:93: ^( STATEMENTS ( actionStatements )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:106: ( actionStatements )?
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:123:3: priorityOrder
                    {
                    pushFollow(FOLLOW_priorityOrder_in_actorDeclaration833);
                    priorityOrder81=priorityOrder();

                    state._fsp--;

                    stream_priorityOrder.add(priorityOrder81.getTree());


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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:3: FUNCTION ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end'
                    {
                    FUNCTION82=(Token)match(input,FUNCTION,FOLLOW_FUNCTION_in_actorDeclaration842);  
                    stream_FUNCTION.add(FUNCTION82);

                    ID83=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration844);  
                    stream_ID.add(ID83);

                    char_literal84=(Token)match(input,83,FOLLOW_83_in_actorDeclaration846);  
                    stream_83.add(char_literal84);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:19: ( varDeclNoExpr ( ',' varDeclNoExpr )* )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==ID) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:20: varDeclNoExpr ( ',' varDeclNoExpr )*
                            {
                            pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration849);
                            varDeclNoExpr85=varDeclNoExpr();

                            state._fsp--;

                            stream_varDeclNoExpr.add(varDeclNoExpr85.getTree());
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:34: ( ',' varDeclNoExpr )*
                            loop37:
                            do {
                                int alt37=2;
                                int LA37_0 = input.LA(1);

                                if ( (LA37_0==81) ) {
                                    alt37=1;
                                }


                                switch (alt37) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:35: ',' varDeclNoExpr
                            	    {
                            	    char_literal86=(Token)match(input,81,FOLLOW_81_in_actorDeclaration852);  
                            	    stream_81.add(char_literal86);

                            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration854);
                            	    varDeclNoExpr87=varDeclNoExpr();

                            	    state._fsp--;

                            	    stream_varDeclNoExpr.add(varDeclNoExpr87.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop37;
                                }
                            } while (true);


                            }
                            break;

                    }

                    char_literal88=(Token)match(input,84,FOLLOW_84_in_actorDeclaration860);  
                    stream_84.add(char_literal88);

                    string_literal89=(Token)match(input,92,FOLLOW_92_in_actorDeclaration862);  
                    stream_92.add(string_literal89);

                    pushFollow(FOLLOW_typeDef_in_actorDeclaration864);
                    typeDef90=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef90.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:5: ( 'var' varDecls )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==88) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:6: 'var' varDecls
                            {
                            string_literal91=(Token)match(input,88,FOLLOW_88_in_actorDeclaration871);  
                            stream_88.add(string_literal91);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration873);
                            varDecls92=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls92.getTree());

                            }
                            break;

                    }

                    char_literal93=(Token)match(input,78,FOLLOW_78_in_actorDeclaration877);  
                    stream_78.add(char_literal93);

                    pushFollow(FOLLOW_expression_in_actorDeclaration885);
                    expression94=expression();

                    state._fsp--;

                    stream_expression.add(expression94.getTree());
                    string_literal95=(Token)match(input,86,FOLLOW_86_in_actorDeclaration891);  
                    stream_86.add(string_literal95);



                    // AST REWRITE
                    // elements: expression, varDecls, FUNCTION, ID, varDeclNoExpr
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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:129:5: ^( FUNCTION ID ^( PARAMETERS ( varDeclNoExpr )* ) ^( VARIABLES ( varDecls )? ) expression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_FUNCTION.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_ID.nextNode());
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:129:19: ^( PARAMETERS ( varDeclNoExpr )* )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETERS, "PARAMETERS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:129:32: ( varDeclNoExpr )*
                        while ( stream_varDeclNoExpr.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDeclNoExpr.nextTree());

                        }
                        stream_varDeclNoExpr.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:129:48: ^( VARIABLES ( varDecls )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLES, "VARIABLES"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:129:60: ( varDecls )?
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:3: PROCEDURE ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end'
                    {
                    PROCEDURE96=(Token)match(input,PROCEDURE,FOLLOW_PROCEDURE_in_actorDeclaration921);  
                    stream_PROCEDURE.add(PROCEDURE96);

                    ID97=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration923);  
                    stream_ID.add(ID97);

                    char_literal98=(Token)match(input,83,FOLLOW_83_in_actorDeclaration925);  
                    stream_83.add(char_literal98);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:20: ( varDeclNoExpr ( ',' varDeclNoExpr )* )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==ID) ) {
                        alt41=1;
                    }
                    switch (alt41) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:21: varDeclNoExpr ( ',' varDeclNoExpr )*
                            {
                            pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration928);
                            varDeclNoExpr99=varDeclNoExpr();

                            state._fsp--;

                            stream_varDeclNoExpr.add(varDeclNoExpr99.getTree());
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:35: ( ',' varDeclNoExpr )*
                            loop40:
                            do {
                                int alt40=2;
                                int LA40_0 = input.LA(1);

                                if ( (LA40_0==81) ) {
                                    alt40=1;
                                }


                                switch (alt40) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:36: ',' varDeclNoExpr
                            	    {
                            	    char_literal100=(Token)match(input,81,FOLLOW_81_in_actorDeclaration931);  
                            	    stream_81.add(char_literal100);

                            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration933);
                            	    varDeclNoExpr101=varDeclNoExpr();

                            	    state._fsp--;

                            	    stream_varDeclNoExpr.add(varDeclNoExpr101.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop40;
                                }
                            } while (true);


                            }
                            break;

                    }

                    char_literal102=(Token)match(input,84,FOLLOW_84_in_actorDeclaration939);  
                    stream_84.add(char_literal102);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:132:5: ( 'var' varDecls )?
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==88) ) {
                        alt42=1;
                    }
                    switch (alt42) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:132:6: 'var' varDecls
                            {
                            string_literal103=(Token)match(input,88,FOLLOW_88_in_actorDeclaration946);  
                            stream_88.add(string_literal103);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration948);
                            varDecls104=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls104.getTree());

                            }
                            break;

                    }

                    string_literal105=(Token)match(input,93,FOLLOW_93_in_actorDeclaration956);  
                    stream_93.add(string_literal105);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:133:13: ( statement )*
                    loop43:
                    do {
                        int alt43=2;
                        int LA43_0 = input.LA(1);

                        if ( (LA43_0==ID||LA43_0==93||LA43_0==114||LA43_0==122||LA43_0==124) ) {
                            alt43=1;
                        }


                        switch (alt43) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:133:13: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_actorDeclaration958);
                    	    statement106=statement();

                    	    state._fsp--;

                    	    stream_statement.add(statement106.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop43;
                        }
                    } while (true);

                    string_literal107=(Token)match(input,86,FOLLOW_86_in_actorDeclaration961);  
                    stream_86.add(string_literal107);



                    // AST REWRITE
                    // elements: statement, varDeclNoExpr, varDecls, ID, PROCEDURE
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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:5: ^( PROCEDURE ID ^( PARAMETERS ( varDeclNoExpr )* ) ^( VARIABLES ( varDecls )? ) ^( STATEMENTS ( statement )* ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_PROCEDURE.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_ID.nextNode());
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:20: ^( PARAMETERS ( varDeclNoExpr )* )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETERS, "PARAMETERS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:33: ( varDeclNoExpr )*
                        while ( stream_varDeclNoExpr.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDeclNoExpr.nextTree());

                        }
                        stream_varDeclNoExpr.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:49: ^( VARIABLES ( varDecls )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLES, "VARIABLES"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:61: ( varDecls )?
                        if ( stream_varDecls.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                        }
                        stream_varDecls.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:72: ^( STATEMENTS ( statement )* )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:85: ( statement )*
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:1: actorDeclarations : ( ( actorDeclaration )+ ( schedule ( actorDeclaration )* )? -> ( actorDeclaration )+ ( schedule )? | schedule ( actorDeclaration )* -> ( actorDeclaration )* schedule );
    public final RVCCalParser.actorDeclarations_return actorDeclarations() throws RecognitionException {
        RVCCalParser.actorDeclarations_return retval = new RVCCalParser.actorDeclarations_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        RVCCalParser.actorDeclaration_return actorDeclaration108 = null;

        RVCCalParser.schedule_return schedule109 = null;

        RVCCalParser.actorDeclaration_return actorDeclaration110 = null;

        RVCCalParser.schedule_return schedule111 = null;

        RVCCalParser.actorDeclaration_return actorDeclaration112 = null;


        RewriteRuleSubtreeStream stream_schedule=new RewriteRuleSubtreeStream(adaptor,"rule schedule");
        RewriteRuleSubtreeStream stream_actorDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule actorDeclaration");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:18: ( ( actorDeclaration )+ ( schedule ( actorDeclaration )* )? -> ( actorDeclaration )+ ( schedule )? | schedule ( actorDeclaration )* -> ( actorDeclaration )* schedule )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:20: ( actorDeclaration )+ ( schedule ( actorDeclaration )* )?
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:20: ( actorDeclaration )+
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
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:20: actorDeclaration
                    	    {
                    	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations998);
                    	    actorDeclaration108=actorDeclaration();

                    	    state._fsp--;

                    	    stream_actorDeclaration.add(actorDeclaration108.getTree());

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

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:38: ( schedule ( actorDeclaration )* )?
                    int alt47=2;
                    int LA47_0 = input.LA(1);

                    if ( (LA47_0==SCHEDULE) ) {
                        alt47=1;
                    }
                    switch (alt47) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:39: schedule ( actorDeclaration )*
                            {
                            pushFollow(FOLLOW_schedule_in_actorDeclarations1002);
                            schedule109=schedule();

                            state._fsp--;

                            stream_schedule.add(schedule109.getTree());
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:48: ( actorDeclaration )*
                            loop46:
                            do {
                                int alt46=2;
                                int LA46_0 = input.LA(1);

                                if ( (LA46_0==ACTION||LA46_0==FUNCTION||(LA46_0>=INITIALIZE && LA46_0<=PROCEDURE)||LA46_0==ID) ) {
                                    alt46=1;
                                }


                                switch (alt46) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:48: actorDeclaration
                            	    {
                            	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations1004);
                            	    actorDeclaration110=actorDeclaration();

                            	    state._fsp--;

                            	    stream_actorDeclaration.add(actorDeclaration110.getTree());

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
                    // elements: actorDeclaration, schedule
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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:89: ( schedule )?
                        if ( stream_schedule.hasNext() ) {
                            adaptor.addChild(root_0, stream_schedule.nextTree());

                        }
                        stream_schedule.reset();

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:137:5: schedule ( actorDeclaration )*
                    {
                    pushFollow(FOLLOW_schedule_in_actorDeclarations1021);
                    schedule111=schedule();

                    state._fsp--;

                    stream_schedule.add(schedule111.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:137:14: ( actorDeclaration )*
                    loop48:
                    do {
                        int alt48=2;
                        int LA48_0 = input.LA(1);

                        if ( (LA48_0==ACTION||LA48_0==FUNCTION||(LA48_0>=INITIALIZE && LA48_0<=PROCEDURE)||LA48_0==ID) ) {
                            alt48=1;
                        }


                        switch (alt48) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:137:14: actorDeclaration
                    	    {
                    	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations1023);
                    	    actorDeclaration112=actorDeclaration();

                    	    state._fsp--;

                    	    stream_actorDeclaration.add(actorDeclaration112.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop48;
                        }
                    } while (true);



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
                    // 137:32: -> ( actorDeclaration )* schedule
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:137:35: ( actorDeclaration )*
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:139:1: actorImport : 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) ;
    public final RVCCalParser.actorImport_return actorImport() throws RecognitionException {
        RVCCalParser.actorImport_return retval = new RVCCalParser.actorImport_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal113=null;
        Token string_literal114=null;
        Token char_literal116=null;
        Token char_literal118=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent115 = null;

        RVCCalParser.qualifiedIdent_return qualifiedIdent117 = null;


        Object string_literal113_tree=null;
        Object string_literal114_tree=null;
        Object char_literal116_tree=null;
        Object char_literal118_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:142:12: ( 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:142:14: 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            {
            root_0 = (Object)adaptor.nil();

            string_literal113=(Token)match(input,94,FOLLOW_94_in_actorImport1043); 
            string_literal113_tree = (Object)adaptor.create(string_literal113);
            adaptor.addChild(root_0, string_literal113_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:143:3: ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==95) ) {
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:143:4: 'all' qualifiedIdent ';'
                    {
                    string_literal114=(Token)match(input,95,FOLLOW_95_in_actorImport1048); 
                    string_literal114_tree = (Object)adaptor.create(string_literal114);
                    adaptor.addChild(root_0, string_literal114_tree);

                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport1050);
                    qualifiedIdent115=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent115.getTree());
                    char_literal116=(Token)match(input,91,FOLLOW_91_in_actorImport1052); 
                    char_literal116_tree = (Object)adaptor.create(char_literal116);
                    adaptor.addChild(root_0, char_literal116_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:3: qualifiedIdent ';'
                    {
                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport1058);
                    qualifiedIdent117=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent117.getTree());
                    char_literal118=(Token)match(input,91,FOLLOW_91_in_actorImport1060); 
                    char_literal118_tree = (Object)adaptor.create(char_literal118);
                    adaptor.addChild(root_0, char_literal118_tree);

                     

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:146:1: actorParameter : typeDef ID ( '=' expression )? -> ^( VARIABLE typeDef ID ( expression )? ) ;
    public final RVCCalParser.actorParameter_return actorParameter() throws RecognitionException {
        RVCCalParser.actorParameter_return retval = new RVCCalParser.actorParameter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID120=null;
        Token char_literal121=null;
        RVCCalParser.typeDef_return typeDef119 = null;

        RVCCalParser.expression_return expression122 = null;


        Object ID120_tree=null;
        Object char_literal121_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:15: ( typeDef ID ( '=' expression )? -> ^( VARIABLE typeDef ID ( expression )? ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:2: typeDef ID ( '=' expression )?
            {
            pushFollow(FOLLOW_typeDef_in_actorParameter1075);
            typeDef119=typeDef();

            state._fsp--;

            stream_typeDef.add(typeDef119.getTree());
            ID120=(Token)match(input,ID,FOLLOW_ID_in_actorParameter1077);  
            stream_ID.add(ID120);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:13: ( '=' expression )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==89) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:14: '=' expression
                    {
                    char_literal121=(Token)match(input,89,FOLLOW_89_in_actorParameter1080);  
                    stream_89.add(char_literal121);

                    pushFollow(FOLLOW_expression_in_actorParameter1082);
                    expression122=expression();

                    state._fsp--;

                    stream_expression.add(expression122.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: typeDef, expression, ID
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
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:34: ^( VARIABLE typeDef ID ( expression )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLE, "VARIABLE"), root_1);

                adaptor.addChild(root_1, stream_typeDef.nextTree());
                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:56: ( expression )?
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:1: actorParameters : actorParameter ( ',' actorParameter )* -> ( actorParameter )+ ;
    public final RVCCalParser.actorParameters_return actorParameters() throws RecognitionException {
        RVCCalParser.actorParameters_return retval = new RVCCalParser.actorParameters_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal124=null;
        RVCCalParser.actorParameter_return actorParameter123 = null;

        RVCCalParser.actorParameter_return actorParameter125 = null;


        Object char_literal124_tree=null;
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleSubtreeStream stream_actorParameter=new RewriteRuleSubtreeStream(adaptor,"rule actorParameter");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:16: ( actorParameter ( ',' actorParameter )* -> ( actorParameter )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:18: actorParameter ( ',' actorParameter )*
            {
            pushFollow(FOLLOW_actorParameter_in_actorParameters1104);
            actorParameter123=actorParameter();

            state._fsp--;

            stream_actorParameter.add(actorParameter123.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:33: ( ',' actorParameter )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==81) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:34: ',' actorParameter
            	    {
            	    char_literal124=(Token)match(input,81,FOLLOW_81_in_actorParameters1107);  
            	    stream_81.add(char_literal124);

            	    pushFollow(FOLLOW_actorParameter_in_actorParameters1109);
            	    actorParameter125=actorParameter();

            	    state._fsp--;

            	    stream_actorParameter.add(actorParameter125.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:1: actorPortDecls : varDeclNoExpr ( ',' varDeclNoExpr )* -> ( varDeclNoExpr )+ ;
    public final RVCCalParser.actorPortDecls_return actorPortDecls() throws RecognitionException {
        RVCCalParser.actorPortDecls_return retval = new RVCCalParser.actorPortDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal127=null;
        RVCCalParser.varDeclNoExpr_return varDeclNoExpr126 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr128 = null;


        Object char_literal127_tree=null;
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleSubtreeStream stream_varDeclNoExpr=new RewriteRuleSubtreeStream(adaptor,"rule varDeclNoExpr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:156:15: ( varDeclNoExpr ( ',' varDeclNoExpr )* -> ( varDeclNoExpr )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:156:17: varDeclNoExpr ( ',' varDeclNoExpr )*
            {
            pushFollow(FOLLOW_varDeclNoExpr_in_actorPortDecls1128);
            varDeclNoExpr126=varDeclNoExpr();

            state._fsp--;

            stream_varDeclNoExpr.add(varDeclNoExpr126.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:156:31: ( ',' varDeclNoExpr )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==81) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:156:32: ',' varDeclNoExpr
            	    {
            	    char_literal127=(Token)match(input,81,FOLLOW_81_in_actorPortDecls1131);  
            	    stream_81.add(char_literal127);

            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorPortDecls1133);
            	    varDeclNoExpr128=varDeclNoExpr();

            	    state._fsp--;

            	    stream_varDeclNoExpr.add(varDeclNoExpr128.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:158:1: expression : un_expr ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr ) ;
    public final RVCCalParser.expression_return expression() throws RecognitionException {
        RVCCalParser.expression_return retval = new RVCCalParser.expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        RVCCalParser.un_expr_return un_expr129 = null;

        RVCCalParser.bop_return bop130 = null;

        RVCCalParser.un_expr_return un_expr131 = null;


        RewriteRuleSubtreeStream stream_bop=new RewriteRuleSubtreeStream(adaptor,"rule bop");
        RewriteRuleSubtreeStream stream_un_expr=new RewriteRuleSubtreeStream(adaptor,"rule un_expr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:11: ( un_expr ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:13: un_expr ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr )
            {
            pushFollow(FOLLOW_un_expr_in_expression1154);
            un_expr129=un_expr();

            state._fsp--;

            stream_un_expr.add(un_expr129.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:3: ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr )
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( ((LA55_0>=PLUS && LA55_0<=DIV)||LA55_0==89||(LA55_0>=96 && LA55_0<=111)) ) {
                alt55=1;
            }
            else if ( (LA55_0==GUARD||LA55_0==78||(LA55_0>=80 && LA55_0<=82)||(LA55_0>=84 && LA55_0<=86)||LA55_0==88||LA55_0==91||LA55_0==93||(LA55_0>=115 && LA55_0<=116)||LA55_0==123) ) {
                alt55=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;
            }
            switch (alt55) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:4: ( bop un_expr )+
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:4: ( bop un_expr )+
                    int cnt54=0;
                    loop54:
                    do {
                        int alt54=2;
                        int LA54_0 = input.LA(1);

                        if ( ((LA54_0>=PLUS && LA54_0<=DIV)||LA54_0==89||(LA54_0>=96 && LA54_0<=111)) ) {
                            alt54=1;
                        }


                        switch (alt54) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:5: bop un_expr
                    	    {
                    	    pushFollow(FOLLOW_bop_in_expression1160);
                    	    bop130=bop();

                    	    state._fsp--;

                    	    stream_bop.add(bop130.getTree());
                    	    pushFollow(FOLLOW_un_expr_in_expression1162);
                    	    un_expr131=un_expr();

                    	    state._fsp--;

                    	    stream_un_expr.add(un_expr131.getTree());

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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:22: ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BINARY, "EXPR_BINARY"), root_1);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:36: ^( EXPR ( un_expr )+ )
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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:53: ^( OP ( bop )+ )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:165:5: 
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:1: bop : ( ( 'or' | '||' ) -> LOGIC_OR | ( 'and' | '&&' ) -> LOGIC_AND | '|' -> BITOR | '&' -> BITAND | '=' -> EQ | '!=' -> NE | '<' -> LT | '>' -> GT | '<=' -> LE | '>=' -> GE | '<<' -> SHIFT_LEFT | '>>' -> SHIFT_RIGHT | PLUS -> PLUS | MINUS -> MINUS | DIV -> DIV | 'div' -> DIV_INT | 'mod' -> MOD | TIMES -> TIMES | '^' -> EXP );
    public final RVCCalParser.bop_return bop() throws RecognitionException {
        RVCCalParser.bop_return retval = new RVCCalParser.bop_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal132=null;
        Token string_literal133=null;
        Token string_literal134=null;
        Token string_literal135=null;
        Token char_literal136=null;
        Token char_literal137=null;
        Token char_literal138=null;
        Token string_literal139=null;
        Token char_literal140=null;
        Token char_literal141=null;
        Token string_literal142=null;
        Token string_literal143=null;
        Token string_literal144=null;
        Token string_literal145=null;
        Token PLUS146=null;
        Token MINUS147=null;
        Token DIV148=null;
        Token string_literal149=null;
        Token string_literal150=null;
        Token TIMES151=null;
        Token char_literal152=null;

        Object string_literal132_tree=null;
        Object string_literal133_tree=null;
        Object string_literal134_tree=null;
        Object string_literal135_tree=null;
        Object char_literal136_tree=null;
        Object char_literal137_tree=null;
        Object char_literal138_tree=null;
        Object string_literal139_tree=null;
        Object char_literal140_tree=null;
        Object char_literal141_tree=null;
        Object string_literal142_tree=null;
        Object string_literal143_tree=null;
        Object string_literal144_tree=null;
        Object string_literal145_tree=null;
        Object PLUS146_tree=null;
        Object MINUS147_tree=null;
        Object DIV148_tree=null;
        Object string_literal149_tree=null;
        Object string_literal150_tree=null;
        Object TIMES151_tree=null;
        Object char_literal152_tree=null;
        RewriteRuleTokenStream stream_98=new RewriteRuleTokenStream(adaptor,"token 98");
        RewriteRuleTokenStream stream_97=new RewriteRuleTokenStream(adaptor,"token 97");
        RewriteRuleTokenStream stream_96=new RewriteRuleTokenStream(adaptor,"token 96");
        RewriteRuleTokenStream stream_110=new RewriteRuleTokenStream(adaptor,"token 110");
        RewriteRuleTokenStream stream_111=new RewriteRuleTokenStream(adaptor,"token 111");
        RewriteRuleTokenStream stream_PLUS=new RewriteRuleTokenStream(adaptor,"token PLUS");
        RewriteRuleTokenStream stream_109=new RewriteRuleTokenStream(adaptor,"token 109");
        RewriteRuleTokenStream stream_108=new RewriteRuleTokenStream(adaptor,"token 108");
        RewriteRuleTokenStream stream_DIV=new RewriteRuleTokenStream(adaptor,"token DIV");
        RewriteRuleTokenStream stream_MINUS=new RewriteRuleTokenStream(adaptor,"token MINUS");
        RewriteRuleTokenStream stream_107=new RewriteRuleTokenStream(adaptor,"token 107");
        RewriteRuleTokenStream stream_TIMES=new RewriteRuleTokenStream(adaptor,"token TIMES");
        RewriteRuleTokenStream stream_106=new RewriteRuleTokenStream(adaptor,"token 106");
        RewriteRuleTokenStream stream_105=new RewriteRuleTokenStream(adaptor,"token 105");
        RewriteRuleTokenStream stream_104=new RewriteRuleTokenStream(adaptor,"token 104");
        RewriteRuleTokenStream stream_103=new RewriteRuleTokenStream(adaptor,"token 103");
        RewriteRuleTokenStream stream_99=new RewriteRuleTokenStream(adaptor,"token 99");
        RewriteRuleTokenStream stream_102=new RewriteRuleTokenStream(adaptor,"token 102");
        RewriteRuleTokenStream stream_101=new RewriteRuleTokenStream(adaptor,"token 101");
        RewriteRuleTokenStream stream_100=new RewriteRuleTokenStream(adaptor,"token 100");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:4: ( ( 'or' | '||' ) -> LOGIC_OR | ( 'and' | '&&' ) -> LOGIC_AND | '|' -> BITOR | '&' -> BITAND | '=' -> EQ | '!=' -> NE | '<' -> LT | '>' -> GT | '<=' -> LE | '>=' -> GE | '<<' -> SHIFT_LEFT | '>>' -> SHIFT_RIGHT | PLUS -> PLUS | MINUS -> MINUS | DIV -> DIV | 'div' -> DIV_INT | 'mod' -> MOD | TIMES -> TIMES | '^' -> EXP )
            int alt58=19;
            alt58 = dfa58.predict(input);
            switch (alt58) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:6: ( 'or' | '||' )
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:6: ( 'or' | '||' )
                    int alt56=2;
                    int LA56_0 = input.LA(1);

                    if ( (LA56_0==96) ) {
                        alt56=1;
                    }
                    else if ( (LA56_0==97) ) {
                        alt56=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 56, 0, input);

                        throw nvae;
                    }
                    switch (alt56) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:7: 'or'
                            {
                            string_literal132=(Token)match(input,96,FOLLOW_96_in_bop1201);  
                            stream_96.add(string_literal132);


                            }
                            break;
                        case 2 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:14: '||'
                            {
                            string_literal133=(Token)match(input,97,FOLLOW_97_in_bop1205);  
                            stream_97.add(string_literal133);


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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:3: ( 'and' | '&&' )
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:3: ( 'and' | '&&' )
                    int alt57=2;
                    int LA57_0 = input.LA(1);

                    if ( (LA57_0==98) ) {
                        alt57=1;
                    }
                    else if ( (LA57_0==99) ) {
                        alt57=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 57, 0, input);

                        throw nvae;
                    }
                    switch (alt57) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:4: 'and'
                            {
                            string_literal134=(Token)match(input,98,FOLLOW_98_in_bop1215);  
                            stream_98.add(string_literal134);


                            }
                            break;
                        case 2 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:12: '&&'
                            {
                            string_literal135=(Token)match(input,99,FOLLOW_99_in_bop1219);  
                            stream_99.add(string_literal135);


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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:3: '|'
                    {
                    char_literal136=(Token)match(input,100,FOLLOW_100_in_bop1228);  
                    stream_100.add(char_literal136);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:3: '&'
                    {
                    char_literal137=(Token)match(input,101,FOLLOW_101_in_bop1236);  
                    stream_101.add(char_literal137);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:171:3: '='
                    {
                    char_literal138=(Token)match(input,89,FOLLOW_89_in_bop1244);  
                    stream_89.add(char_literal138);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:171:15: '!='
                    {
                    string_literal139=(Token)match(input,102,FOLLOW_102_in_bop1252);  
                    stream_102.add(string_literal139);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:3: '<'
                    {
                    char_literal140=(Token)match(input,103,FOLLOW_103_in_bop1260);  
                    stream_103.add(char_literal140);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:15: '>'
                    {
                    char_literal141=(Token)match(input,104,FOLLOW_104_in_bop1268);  
                    stream_104.add(char_literal141);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:27: '<='
                    {
                    string_literal142=(Token)match(input,105,FOLLOW_105_in_bop1276);  
                    stream_105.add(string_literal142);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:40: '>='
                    {
                    string_literal143=(Token)match(input,106,FOLLOW_106_in_bop1284);  
                    stream_106.add(string_literal143);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:3: '<<'
                    {
                    string_literal144=(Token)match(input,107,FOLLOW_107_in_bop1292);  
                    stream_107.add(string_literal144);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:24: '>>'
                    {
                    string_literal145=(Token)match(input,108,FOLLOW_108_in_bop1300);  
                    stream_108.add(string_literal145);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:174:3: PLUS
                    {
                    PLUS146=(Token)match(input,PLUS,FOLLOW_PLUS_in_bop1308);  
                    stream_PLUS.add(PLUS146);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:174:18: MINUS
                    {
                    MINUS147=(Token)match(input,MINUS,FOLLOW_MINUS_in_bop1316);  
                    stream_MINUS.add(MINUS147);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:3: DIV
                    {
                    DIV148=(Token)match(input,DIV,FOLLOW_DIV_in_bop1324);  
                    stream_DIV.add(DIV148);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:16: 'div'
                    {
                    string_literal149=(Token)match(input,109,FOLLOW_109_in_bop1332);  
                    stream_109.add(string_literal149);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:35: 'mod'
                    {
                    string_literal150=(Token)match(input,110,FOLLOW_110_in_bop1340);  
                    stream_110.add(string_literal150);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:50: TIMES
                    {
                    TIMES151=(Token)match(input,TIMES,FOLLOW_TIMES_in_bop1348);  
                    stream_TIMES.add(TIMES151);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:3: '^'
                    {
                    char_literal152=(Token)match(input,111,FOLLOW_111_in_bop1356);  
                    stream_111.add(char_literal152);



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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:1: un_expr : ( postfix_expression -> postfix_expression | un_op un_expr -> ^( EXPR_UNARY un_op un_expr ) );
    public final RVCCalParser.un_expr_return un_expr() throws RecognitionException {
        RVCCalParser.un_expr_return retval = new RVCCalParser.un_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        RVCCalParser.postfix_expression_return postfix_expression153 = null;

        RVCCalParser.un_op_return un_op154 = null;

        RVCCalParser.un_expr_return un_expr155 = null;


        RewriteRuleSubtreeStream stream_postfix_expression=new RewriteRuleSubtreeStream(adaptor,"rule postfix_expression");
        RewriteRuleSubtreeStream stream_un_expr=new RewriteRuleSubtreeStream(adaptor,"rule un_expr");
        RewriteRuleSubtreeStream stream_un_op=new RewriteRuleSubtreeStream(adaptor,"rule un_op");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:8: ( postfix_expression -> postfix_expression | un_op un_expr -> ^( EXPR_UNARY un_op un_expr ) )
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==ID||(LA59_0>=FLOAT && LA59_0<=INTEGER)||LA59_0==STRING||LA59_0==79||LA59_0==83||LA59_0==114||(LA59_0>=117 && LA59_0<=118)) ) {
                alt59=1;
            }
            else if ( (LA59_0==MINUS||(LA59_0>=112 && LA59_0<=113)) ) {
                alt59=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }
            switch (alt59) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:10: postfix_expression
                    {
                    pushFollow(FOLLOW_postfix_expression_in_un_expr1367);
                    postfix_expression153=postfix_expression();

                    state._fsp--;

                    stream_postfix_expression.add(postfix_expression153.getTree());


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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:179:5: un_op un_expr
                    {
                    pushFollow(FOLLOW_un_op_in_un_expr1377);
                    un_op154=un_op();

                    state._fsp--;

                    stream_un_op.add(un_op154.getTree());
                    pushFollow(FOLLOW_un_expr_in_un_expr1379);
                    un_expr155=un_expr();

                    state._fsp--;

                    stream_un_expr.add(un_expr155.getTree());


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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:179:22: ^( EXPR_UNARY un_op un_expr )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:1: un_op : ( MINUS -> MINUS | 'not' -> LOGIC_NOT | '#' -> NUM_ELTS );
    public final RVCCalParser.un_op_return un_op() throws RecognitionException {
        RVCCalParser.un_op_return retval = new RVCCalParser.un_op_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MINUS156=null;
        Token string_literal157=null;
        Token char_literal158=null;

        Object MINUS156_tree=null;
        Object string_literal157_tree=null;
        Object char_literal158_tree=null;
        RewriteRuleTokenStream stream_112=new RewriteRuleTokenStream(adaptor,"token 112");
        RewriteRuleTokenStream stream_113=new RewriteRuleTokenStream(adaptor,"token 113");
        RewriteRuleTokenStream stream_MINUS=new RewriteRuleTokenStream(adaptor,"token MINUS");

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:6: ( MINUS -> MINUS | 'not' -> LOGIC_NOT | '#' -> NUM_ELTS )
            int alt60=3;
            switch ( input.LA(1) ) {
            case MINUS:
                {
                alt60=1;
                }
                break;
            case 112:
                {
                alt60=2;
                }
                break;
            case 113:
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:8: MINUS
                    {
                    MINUS156=(Token)match(input,MINUS,FOLLOW_MINUS_in_un_op1396);  
                    stream_MINUS.add(MINUS156);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:25: 'not'
                    {
                    string_literal157=(Token)match(input,112,FOLLOW_112_in_un_op1404);  
                    stream_112.add(string_literal157);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:46: '#'
                    {
                    char_literal158=(Token)match(input,113,FOLLOW_113_in_un_op1412);  
                    stream_113.add(char_literal158);



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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:1: postfix_expression : ( '[' e= expressions ( ':' g= expressionGenerators )? ']' -> ^( EXPR_LIST $e ( $g)? ) | 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end' -> ^( EXPR_IF $e1 $e2 $e3) | constant -> constant | '(' expression ')' -> expression | var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) ) );
    public final RVCCalParser.postfix_expression_return postfix_expression() throws RecognitionException {
        RVCCalParser.postfix_expression_return retval = new RVCCalParser.postfix_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token var=null;
        Token char_literal159=null;
        Token char_literal160=null;
        Token char_literal161=null;
        Token string_literal162=null;
        Token string_literal163=null;
        Token string_literal164=null;
        Token string_literal165=null;
        Token char_literal167=null;
        Token char_literal169=null;
        Token char_literal170=null;
        Token char_literal172=null;
        Token char_literal173=null;
        Token char_literal175=null;
        RVCCalParser.expressions_return e = null;

        RVCCalParser.expressionGenerators_return g = null;

        RVCCalParser.expression_return e1 = null;

        RVCCalParser.expression_return e2 = null;

        RVCCalParser.expression_return e3 = null;

        RVCCalParser.constant_return constant166 = null;

        RVCCalParser.expression_return expression168 = null;

        RVCCalParser.expressions_return expressions171 = null;

        RVCCalParser.expressions_return expressions174 = null;


        Object var_tree=null;
        Object char_literal159_tree=null;
        Object char_literal160_tree=null;
        Object char_literal161_tree=null;
        Object string_literal162_tree=null;
        Object string_literal163_tree=null;
        Object string_literal164_tree=null;
        Object string_literal165_tree=null;
        Object char_literal167_tree=null;
        Object char_literal169_tree=null;
        Object char_literal170_tree=null;
        Object char_literal172_tree=null;
        Object char_literal173_tree=null;
        Object char_literal175_tree=null;
        RewriteRuleTokenStream stream_116=new RewriteRuleTokenStream(adaptor,"token 116");
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleTokenStream stream_114=new RewriteRuleTokenStream(adaptor,"token 114");
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_115=new RewriteRuleTokenStream(adaptor,"token 115");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_80=new RewriteRuleTokenStream(adaptor,"token 80");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_constant=new RewriteRuleSubtreeStream(adaptor,"rule constant");
        RewriteRuleSubtreeStream stream_expressionGenerators=new RewriteRuleSubtreeStream(adaptor,"rule expressionGenerators");
        RewriteRuleSubtreeStream stream_expressions=new RewriteRuleSubtreeStream(adaptor,"rule expressions");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:19: ( '[' e= expressions ( ':' g= expressionGenerators )? ']' -> ^( EXPR_LIST $e ( $g)? ) | 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end' -> ^( EXPR_IF $e1 $e2 $e3) | constant -> constant | '(' expression ')' -> expression | var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) ) )
            int alt65=5;
            switch ( input.LA(1) ) {
            case 79:
                {
                alt65=1;
                }
                break;
            case 114:
                {
                alt65=2;
                }
                break;
            case FLOAT:
            case INTEGER:
            case STRING:
            case 117:
            case 118:
                {
                alt65=3;
                }
                break;
            case 83:
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:3: '[' e= expressions ( ':' g= expressionGenerators )? ']'
                    {
                    char_literal159=(Token)match(input,79,FOLLOW_79_in_postfix_expression1425);  
                    stream_79.add(char_literal159);

                    pushFollow(FOLLOW_expressions_in_postfix_expression1429);
                    e=expressions();

                    state._fsp--;

                    stream_expressions.add(e.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:21: ( ':' g= expressionGenerators )?
                    int alt61=2;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==78) ) {
                        alt61=1;
                    }
                    switch (alt61) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:22: ':' g= expressionGenerators
                            {
                            char_literal160=(Token)match(input,78,FOLLOW_78_in_postfix_expression1432);  
                            stream_78.add(char_literal160);

                            pushFollow(FOLLOW_expressionGenerators_in_postfix_expression1436);
                            g=expressionGenerators();

                            state._fsp--;

                            stream_expressionGenerators.add(g.getTree());

                            }
                            break;

                    }

                    char_literal161=(Token)match(input,80,FOLLOW_80_in_postfix_expression1440);  
                    stream_80.add(char_literal161);



                    // AST REWRITE
                    // elements: e, g
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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:58: ^( EXPR_LIST $e ( $g)? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_LIST, "EXPR_LIST"), root_1);

                        adaptor.addChild(root_1, stream_e.nextTree());
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:73: ( $g)?
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:185:3: 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end'
                    {
                    string_literal162=(Token)match(input,114,FOLLOW_114_in_postfix_expression1457);  
                    stream_114.add(string_literal162);

                    pushFollow(FOLLOW_expression_in_postfix_expression1461);
                    e1=expression();

                    state._fsp--;

                    stream_expression.add(e1.getTree());
                    string_literal163=(Token)match(input,115,FOLLOW_115_in_postfix_expression1463);  
                    stream_115.add(string_literal163);

                    pushFollow(FOLLOW_expression_in_postfix_expression1467);
                    e2=expression();

                    state._fsp--;

                    stream_expression.add(e2.getTree());
                    string_literal164=(Token)match(input,116,FOLLOW_116_in_postfix_expression1469);  
                    stream_116.add(string_literal164);

                    pushFollow(FOLLOW_expression_in_postfix_expression1473);
                    e3=expression();

                    state._fsp--;

                    stream_expression.add(e3.getTree());
                    string_literal165=(Token)match(input,86,FOLLOW_86_in_postfix_expression1475);  
                    stream_86.add(string_literal165);



                    // AST REWRITE
                    // elements: e3, e2, e1
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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:185:73: ^( EXPR_IF $e1 $e2 $e3)
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:186:3: constant
                    {
                    pushFollow(FOLLOW_constant_in_postfix_expression1494);
                    constant166=constant();

                    state._fsp--;

                    stream_constant.add(constant166.getTree());


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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:187:3: '(' expression ')'
                    {
                    char_literal167=(Token)match(input,83,FOLLOW_83_in_postfix_expression1502);  
                    stream_83.add(char_literal167);

                    pushFollow(FOLLOW_expression_in_postfix_expression1504);
                    expression168=expression();

                    state._fsp--;

                    stream_expression.add(expression168.getTree());
                    char_literal169=(Token)match(input,84,FOLLOW_84_in_postfix_expression1506);  
                    stream_84.add(char_literal169);



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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:3: var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) )
                    {
                    var=(Token)match(input,ID,FOLLOW_ID_in_postfix_expression1516);  
                    stream_ID.add(var);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:10: ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) )
                    int alt64=3;
                    switch ( input.LA(1) ) {
                    case 83:
                        {
                        alt64=1;
                        }
                        break;
                    case 79:
                        {
                        alt64=2;
                        }
                        break;
                    case GUARD:
                    case PLUS:
                    case MINUS:
                    case TIMES:
                    case DIV:
                    case 78:
                    case 80:
                    case 81:
                    case 82:
                    case 84:
                    case 85:
                    case 86:
                    case 88:
                    case 89:
                    case 91:
                    case 93:
                    case 96:
                    case 97:
                    case 98:
                    case 99:
                    case 100:
                    case 101:
                    case 102:
                    case 103:
                    case 104:
                    case 105:
                    case 106:
                    case 107:
                    case 108:
                    case 109:
                    case 110:
                    case 111:
                    case 115:
                    case 116:
                    case 123:
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
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:5: '(' ( expressions )? ')'
                            {
                            char_literal170=(Token)match(input,83,FOLLOW_83_in_postfix_expression1524);  
                            stream_83.add(char_literal170);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:9: ( expressions )?
                            int alt62=2;
                            int LA62_0 = input.LA(1);

                            if ( (LA62_0==MINUS||LA62_0==ID||(LA62_0>=FLOAT && LA62_0<=INTEGER)||LA62_0==STRING||LA62_0==79||LA62_0==83||(LA62_0>=112 && LA62_0<=114)||(LA62_0>=117 && LA62_0<=118)) ) {
                                alt62=1;
                            }
                            switch (alt62) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:9: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_postfix_expression1526);
                                    expressions171=expressions();

                                    state._fsp--;

                                    stream_expressions.add(expressions171.getTree());

                                    }
                                    break;

                            }

                            char_literal172=(Token)match(input,84,FOLLOW_84_in_postfix_expression1529);  
                            stream_84.add(char_literal172);



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
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:29: ^( EXPR_CALL $var ( expressions )? )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_CALL, "EXPR_CALL"), root_1);

                                adaptor.addChild(root_1, stream_var.nextNode());
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:46: ( expressions )?
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
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:190:6: ( '[' expressions ']' )+
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:190:6: ( '[' expressions ']' )+
                            int cnt63=0;
                            loop63:
                            do {
                                int alt63=2;
                                int LA63_0 = input.LA(1);

                                if ( (LA63_0==79) ) {
                                    alt63=1;
                                }


                                switch (alt63) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:190:7: '[' expressions ']'
                            	    {
                            	    char_literal173=(Token)match(input,79,FOLLOW_79_in_postfix_expression1549);  
                            	    stream_79.add(char_literal173);

                            	    pushFollow(FOLLOW_expressions_in_postfix_expression1551);
                            	    expressions174=expressions();

                            	    state._fsp--;

                            	    stream_expressions.add(expressions174.getTree());
                            	    char_literal175=(Token)match(input,80,FOLLOW_80_in_postfix_expression1553);  
                            	    stream_80.add(char_literal175);


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
                            // elements: expressions, var
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
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:190:32: ^( EXPR_IDX $var ( expressions )+ )
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
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:191:5: 
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
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:191:8: ^( EXPR_VAR $var)
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:193:1: constant : ( 'true' -> ^( EXPR_BOOL 'true' ) | 'false' -> ^( EXPR_BOOL 'false' ) | FLOAT -> ^( EXPR_FLOAT FLOAT ) | INTEGER -> ^( EXPR_INT INTEGER ) | STRING -> ^( EXPR_STRING STRING ) );
    public final RVCCalParser.constant_return constant() throws RecognitionException {
        RVCCalParser.constant_return retval = new RVCCalParser.constant_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal176=null;
        Token string_literal177=null;
        Token FLOAT178=null;
        Token INTEGER179=null;
        Token STRING180=null;

        Object string_literal176_tree=null;
        Object string_literal177_tree=null;
        Object FLOAT178_tree=null;
        Object INTEGER179_tree=null;
        Object STRING180_tree=null;
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_117=new RewriteRuleTokenStream(adaptor,"token 117");
        RewriteRuleTokenStream stream_FLOAT=new RewriteRuleTokenStream(adaptor,"token FLOAT");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleTokenStream stream_118=new RewriteRuleTokenStream(adaptor,"token 118");

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:193:9: ( 'true' -> ^( EXPR_BOOL 'true' ) | 'false' -> ^( EXPR_BOOL 'false' ) | FLOAT -> ^( EXPR_FLOAT FLOAT ) | INTEGER -> ^( EXPR_INT INTEGER ) | STRING -> ^( EXPR_STRING STRING ) )
            int alt66=5;
            switch ( input.LA(1) ) {
            case 117:
                {
                alt66=1;
                }
                break;
            case 118:
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:194:3: 'true'
                    {
                    string_literal176=(Token)match(input,117,FOLLOW_117_in_constant1590);  
                    stream_117.add(string_literal176);



                    // AST REWRITE
                    // elements: 117
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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:194:13: ^( EXPR_BOOL 'true' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BOOL, "EXPR_BOOL"), root_1);

                        adaptor.addChild(root_1, stream_117.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:3: 'false'
                    {
                    string_literal177=(Token)match(input,118,FOLLOW_118_in_constant1602);  
                    stream_118.add(string_literal177);



                    // AST REWRITE
                    // elements: 118
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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:14: ^( EXPR_BOOL 'false' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BOOL, "EXPR_BOOL"), root_1);

                        adaptor.addChild(root_1, stream_118.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:196:3: FLOAT
                    {
                    FLOAT178=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_constant1614);  
                    stream_FLOAT.add(FLOAT178);



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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:196:12: ^( EXPR_FLOAT FLOAT )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:197:3: INTEGER
                    {
                    INTEGER179=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_constant1626);  
                    stream_INTEGER.add(INTEGER179);



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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:197:14: ^( EXPR_INT INTEGER )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:198:3: STRING
                    {
                    STRING180=(Token)match(input,STRING,FOLLOW_STRING_in_constant1638);  
                    stream_STRING.add(STRING180);



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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:198:13: ^( EXPR_STRING STRING )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:1: expressionGenerator : 'for' typeDef ID 'in' expression ;
    public final RVCCalParser.expressionGenerator_return expressionGenerator() throws RecognitionException {
        RVCCalParser.expressionGenerator_return retval = new RVCCalParser.expressionGenerator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal181=null;
        Token ID183=null;
        Token string_literal184=null;
        RVCCalParser.typeDef_return typeDef182 = null;

        RVCCalParser.expression_return expression185 = null;


        Object string_literal181_tree=null;
        Object ID183_tree=null;
        Object string_literal184_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:20: ( 'for' typeDef ID 'in' expression )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:201:2: 'for' typeDef ID 'in' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal181=(Token)match(input,119,FOLLOW_119_in_expressionGenerator1654); 
            string_literal181_tree = (Object)adaptor.create(string_literal181);
            adaptor.addChild(root_0, string_literal181_tree);

            pushFollow(FOLLOW_typeDef_in_expressionGenerator1656);
            typeDef182=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef182.getTree());
            ID183=(Token)match(input,ID,FOLLOW_ID_in_expressionGenerator1658); 
            ID183_tree = (Object)adaptor.create(ID183);
            adaptor.addChild(root_0, ID183_tree);

            string_literal184=(Token)match(input,120,FOLLOW_120_in_expressionGenerator1660); 
            string_literal184_tree = (Object)adaptor.create(string_literal184);
            adaptor.addChild(root_0, string_literal184_tree);

            pushFollow(FOLLOW_expression_in_expressionGenerator1662);
            expression185=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression185.getTree());
             

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:204:1: expressionGenerators : expressionGenerator ( ',' expressionGenerator )* -> ( expressionGenerator )+ ;
    public final RVCCalParser.expressionGenerators_return expressionGenerators() throws RecognitionException {
        RVCCalParser.expressionGenerators_return retval = new RVCCalParser.expressionGenerators_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal187=null;
        RVCCalParser.expressionGenerator_return expressionGenerator186 = null;

        RVCCalParser.expressionGenerator_return expressionGenerator188 = null;


        Object char_literal187_tree=null;
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleSubtreeStream stream_expressionGenerator=new RewriteRuleSubtreeStream(adaptor,"rule expressionGenerator");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:204:21: ( expressionGenerator ( ',' expressionGenerator )* -> ( expressionGenerator )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:204:23: expressionGenerator ( ',' expressionGenerator )*
            {
            pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators1672);
            expressionGenerator186=expressionGenerator();

            state._fsp--;

            stream_expressionGenerator.add(expressionGenerator186.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:204:43: ( ',' expressionGenerator )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==81) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:204:44: ',' expressionGenerator
            	    {
            	    char_literal187=(Token)match(input,81,FOLLOW_81_in_expressionGenerators1675);  
            	    stream_81.add(char_literal187);

            	    pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators1677);
            	    expressionGenerator188=expressionGenerator();

            	    state._fsp--;

            	    stream_expressionGenerator.add(expressionGenerator188.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:206:1: expressions : expression ( ',' expression )* -> ( expression )+ ;
    public final RVCCalParser.expressions_return expressions() throws RecognitionException {
        RVCCalParser.expressions_return retval = new RVCCalParser.expressions_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal190=null;
        RVCCalParser.expression_return expression189 = null;

        RVCCalParser.expression_return expression191 = null;


        Object char_literal190_tree=null;
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:206:12: ( expression ( ',' expression )* -> ( expression )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:206:14: expression ( ',' expression )*
            {
            pushFollow(FOLLOW_expression_in_expressions1691);
            expression189=expression();

            state._fsp--;

            stream_expression.add(expression189.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:206:25: ( ',' expression )*
            loop68:
            do {
                int alt68=2;
                int LA68_0 = input.LA(1);

                if ( (LA68_0==81) ) {
                    alt68=1;
                }


                switch (alt68) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:206:26: ',' expression
            	    {
            	    char_literal190=(Token)match(input,81,FOLLOW_81_in_expressions1694);  
            	    stream_81.add(char_literal190);

            	    pushFollow(FOLLOW_expression_in_expressions1696);
            	    expression191=expression();

            	    state._fsp--;

            	    stream_expression.add(expression191.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:208:1: idents : ID ( ',' ID )* -> ( ID )+ ;
    public final RVCCalParser.idents_return idents() throws RecognitionException {
        RVCCalParser.idents_return retval = new RVCCalParser.idents_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID192=null;
        Token char_literal193=null;
        Token ID194=null;

        Object ID192_tree=null;
        Object char_literal193_tree=null;
        Object ID194_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:211:7: ( ID ( ',' ID )* -> ( ID )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:211:9: ID ( ',' ID )*
            {
            ID192=(Token)match(input,ID,FOLLOW_ID_in_idents1715);  
            stream_ID.add(ID192);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:211:12: ( ',' ID )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==81) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:211:13: ',' ID
            	    {
            	    char_literal193=(Token)match(input,81,FOLLOW_81_in_idents1718);  
            	    stream_81.add(char_literal193);

            	    ID194=(Token)match(input,ID,FOLLOW_ID_in_idents1720);  
            	    stream_ID.add(ID194);


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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:213:1: priorityInequality : qualifiedIdent ( '>' qualifiedIdent )+ ';' -> ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ ) ;
    public final RVCCalParser.priorityInequality_return priorityInequality() throws RecognitionException {
        RVCCalParser.priorityInequality_return retval = new RVCCalParser.priorityInequality_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal196=null;
        Token char_literal198=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent195 = null;

        RVCCalParser.qualifiedIdent_return qualifiedIdent197 = null;


        Object char_literal196_tree=null;
        Object char_literal198_tree=null;
        RewriteRuleTokenStream stream_91=new RewriteRuleTokenStream(adaptor,"token 91");
        RewriteRuleTokenStream stream_104=new RewriteRuleTokenStream(adaptor,"token 104");
        RewriteRuleSubtreeStream stream_qualifiedIdent=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedIdent");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:216:19: ( qualifiedIdent ( '>' qualifiedIdent )+ ';' -> ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:216:21: qualifiedIdent ( '>' qualifiedIdent )+ ';'
            {
            pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality1739);
            qualifiedIdent195=qualifiedIdent();

            state._fsp--;

            stream_qualifiedIdent.add(qualifiedIdent195.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:216:36: ( '>' qualifiedIdent )+
            int cnt70=0;
            loop70:
            do {
                int alt70=2;
                int LA70_0 = input.LA(1);

                if ( (LA70_0==104) ) {
                    alt70=1;
                }


                switch (alt70) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:216:37: '>' qualifiedIdent
            	    {
            	    char_literal196=(Token)match(input,104,FOLLOW_104_in_priorityInequality1742);  
            	    stream_104.add(char_literal196);

            	    pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality1744);
            	    qualifiedIdent197=qualifiedIdent();

            	    state._fsp--;

            	    stream_qualifiedIdent.add(qualifiedIdent197.getTree());

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

            char_literal198=(Token)match(input,91,FOLLOW_91_in_priorityInequality1748);  
            stream_91.add(char_literal198);



            // AST REWRITE
            // elements: qualifiedIdent, qualifiedIdent
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 216:62: -> ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:216:65: ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(INEQUALITY, "INEQUALITY"), root_1);

                adaptor.addChild(root_1, stream_qualifiedIdent.nextTree());
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:218:1: priorityOrder : PRIORITY ( priorityInequality )* 'end' -> ^( PRIORITY ( priorityInequality )* ) ;
    public final RVCCalParser.priorityOrder_return priorityOrder() throws RecognitionException {
        RVCCalParser.priorityOrder_return retval = new RVCCalParser.priorityOrder_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRIORITY199=null;
        Token string_literal201=null;
        RVCCalParser.priorityInequality_return priorityInequality200 = null;


        Object PRIORITY199_tree=null;
        Object string_literal201_tree=null;
        RewriteRuleTokenStream stream_PRIORITY=new RewriteRuleTokenStream(adaptor,"token PRIORITY");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleSubtreeStream stream_priorityInequality=new RewriteRuleSubtreeStream(adaptor,"rule priorityInequality");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:218:14: ( PRIORITY ( priorityInequality )* 'end' -> ^( PRIORITY ( priorityInequality )* ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:218:16: PRIORITY ( priorityInequality )* 'end'
            {
            PRIORITY199=(Token)match(input,PRIORITY,FOLLOW_PRIORITY_in_priorityOrder1767);  
            stream_PRIORITY.add(PRIORITY199);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:218:25: ( priorityInequality )*
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( (LA71_0==ID) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:218:25: priorityInequality
            	    {
            	    pushFollow(FOLLOW_priorityInequality_in_priorityOrder1769);
            	    priorityInequality200=priorityInequality();

            	    state._fsp--;

            	    stream_priorityInequality.add(priorityInequality200.getTree());

            	    }
            	    break;

            	default :
            	    break loop71;
                }
            } while (true);

            string_literal201=(Token)match(input,86,FOLLOW_86_in_priorityOrder1772);  
            stream_86.add(string_literal201);



            // AST REWRITE
            // elements: priorityInequality, PRIORITY
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
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:218:54: ^( PRIORITY ( priorityInequality )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_PRIORITY.nextNode(), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:218:65: ( priorityInequality )*
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:220:1: qualifiedIdent : ID ( '.' ID )* -> ^( QID ( ID )+ ) ;
    public final RVCCalParser.qualifiedIdent_return qualifiedIdent() throws RecognitionException {
        RVCCalParser.qualifiedIdent_return retval = new RVCCalParser.qualifiedIdent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID202=null;
        Token char_literal203=null;
        Token ID204=null;

        Object ID202_tree=null;
        Object char_literal203_tree=null;
        Object ID204_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:15: ( ID ( '.' ID )* -> ^( QID ( ID )+ ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:17: ID ( '.' ID )*
            {
            ID202=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent1793);  
            stream_ID.add(ID202);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:20: ( '.' ID )*
            loop72:
            do {
                int alt72=2;
                int LA72_0 = input.LA(1);

                if ( (LA72_0==87) ) {
                    alt72=1;
                }


                switch (alt72) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:21: '.' ID
            	    {
            	    char_literal203=(Token)match(input,87,FOLLOW_87_in_qualifiedIdent1796);  
            	    stream_87.add(char_literal203);

            	    ID204=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent1798);  
            	    stream_ID.add(ID204);


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
            // 223:30: -> ^( QID ( ID )+ )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:33: ^( QID ( ID )+ )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(QID, "QID"), root_1);

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:1: schedule : SCHEDULE 'fsm' ID ':' ( stateTransition )* 'end' -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) ) ;
    public final RVCCalParser.schedule_return schedule() throws RecognitionException {
        RVCCalParser.schedule_return retval = new RVCCalParser.schedule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SCHEDULE205=null;
        Token string_literal206=null;
        Token ID207=null;
        Token char_literal208=null;
        Token string_literal210=null;
        RVCCalParser.stateTransition_return stateTransition209 = null;


        Object SCHEDULE205_tree=null;
        Object string_literal206_tree=null;
        Object ID207_tree=null;
        Object char_literal208_tree=null;
        Object string_literal210_tree=null;
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_121=new RewriteRuleTokenStream(adaptor,"token 121");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_SCHEDULE=new RewriteRuleTokenStream(adaptor,"token SCHEDULE");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleSubtreeStream stream_stateTransition=new RewriteRuleSubtreeStream(adaptor,"rule stateTransition");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:9: ( SCHEDULE 'fsm' ID ':' ( stateTransition )* 'end' -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:3: SCHEDULE 'fsm' ID ':' ( stateTransition )* 'end'
            {
            SCHEDULE205=(Token)match(input,SCHEDULE,FOLLOW_SCHEDULE_in_schedule1823);  
            stream_SCHEDULE.add(SCHEDULE205);

            string_literal206=(Token)match(input,121,FOLLOW_121_in_schedule1825);  
            stream_121.add(string_literal206);

            ID207=(Token)match(input,ID,FOLLOW_ID_in_schedule1827);  
            stream_ID.add(ID207);

            char_literal208=(Token)match(input,78,FOLLOW_78_in_schedule1829);  
            stream_78.add(char_literal208);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:25: ( stateTransition )*
            loop73:
            do {
                int alt73=2;
                int LA73_0 = input.LA(1);

                if ( (LA73_0==ID) ) {
                    alt73=1;
                }


                switch (alt73) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:25: stateTransition
            	    {
            	    pushFollow(FOLLOW_stateTransition_in_schedule1831);
            	    stateTransition209=stateTransition();

            	    state._fsp--;

            	    stream_stateTransition.add(stateTransition209.getTree());

            	    }
            	    break;

            	default :
            	    break loop73;
                }
            } while (true);

            string_literal210=(Token)match(input,86,FOLLOW_86_in_schedule1834);  
            stream_86.add(string_literal210);



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
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:51: ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_SCHEDULE.nextNode(), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:65: ^( TRANSITIONS ( stateTransition )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TRANSITIONS, "TRANSITIONS"), root_2);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:79: ( stateTransition )*
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:1: stateTransition : ID '(' qualifiedIdent ')' '-->' ID ';' -> ^( TRANSITION ID qualifiedIdent ID ) ;
    public final RVCCalParser.stateTransition_return stateTransition() throws RecognitionException {
        RVCCalParser.stateTransition_return retval = new RVCCalParser.stateTransition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID211=null;
        Token char_literal212=null;
        Token char_literal214=null;
        Token string_literal215=null;
        Token ID216=null;
        Token char_literal217=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent213 = null;


        Object ID211_tree=null;
        Object char_literal212_tree=null;
        Object char_literal214_tree=null;
        Object string_literal215_tree=null;
        Object ID216_tree=null;
        Object char_literal217_tree=null;
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleTokenStream stream_91=new RewriteRuleTokenStream(adaptor,"token 91");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_qualifiedIdent=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedIdent");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:16: ( ID '(' qualifiedIdent ')' '-->' ID ';' -> ^( TRANSITION ID qualifiedIdent ID ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:2: ID '(' qualifiedIdent ')' '-->' ID ';'
            {
            ID211=(Token)match(input,ID,FOLLOW_ID_in_stateTransition1857);  
            stream_ID.add(ID211);

            char_literal212=(Token)match(input,83,FOLLOW_83_in_stateTransition1859);  
            stream_83.add(char_literal212);

            pushFollow(FOLLOW_qualifiedIdent_in_stateTransition1861);
            qualifiedIdent213=qualifiedIdent();

            state._fsp--;

            stream_qualifiedIdent.add(qualifiedIdent213.getTree());
            char_literal214=(Token)match(input,84,FOLLOW_84_in_stateTransition1863);  
            stream_84.add(char_literal214);

            string_literal215=(Token)match(input,92,FOLLOW_92_in_stateTransition1865);  
            stream_92.add(string_literal215);

            ID216=(Token)match(input,ID,FOLLOW_ID_in_stateTransition1867);  
            stream_ID.add(ID216);

            char_literal217=(Token)match(input,91,FOLLOW_91_in_stateTransition1869);  
            stream_91.add(char_literal217);



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
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:44: ^( TRANSITION ID qualifiedIdent ID )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:234:1: statement : ( 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end' | 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end' | 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end' | 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end' | ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' ) );
    public final RVCCalParser.statement_return statement() throws RecognitionException {
        RVCCalParser.statement_return retval = new RVCCalParser.statement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal218=null;
        Token string_literal219=null;
        Token string_literal221=null;
        Token string_literal223=null;
        Token string_literal224=null;
        Token string_literal226=null;
        Token string_literal228=null;
        Token string_literal230=null;
        Token string_literal232=null;
        Token string_literal234=null;
        Token string_literal235=null;
        Token string_literal237=null;
        Token string_literal239=null;
        Token string_literal241=null;
        Token string_literal242=null;
        Token string_literal244=null;
        Token string_literal246=null;
        Token string_literal248=null;
        Token ID249=null;
        Token char_literal250=null;
        Token char_literal252=null;
        Token string_literal253=null;
        Token char_literal255=null;
        Token char_literal256=null;
        Token char_literal258=null;
        Token char_literal259=null;
        RVCCalParser.varDecls_return varDecls220 = null;

        RVCCalParser.statement_return statement222 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr225 = null;

        RVCCalParser.expression_return expression227 = null;

        RVCCalParser.expression_return expression229 = null;

        RVCCalParser.varDecls_return varDecls231 = null;

        RVCCalParser.statement_return statement233 = null;

        RVCCalParser.expression_return expression236 = null;

        RVCCalParser.statement_return statement238 = null;

        RVCCalParser.statement_return statement240 = null;

        RVCCalParser.expression_return expression243 = null;

        RVCCalParser.varDecls_return varDecls245 = null;

        RVCCalParser.statement_return statement247 = null;

        RVCCalParser.expressions_return expressions251 = null;

        RVCCalParser.expression_return expression254 = null;

        RVCCalParser.expressions_return expressions257 = null;


        Object string_literal218_tree=null;
        Object string_literal219_tree=null;
        Object string_literal221_tree=null;
        Object string_literal223_tree=null;
        Object string_literal224_tree=null;
        Object string_literal226_tree=null;
        Object string_literal228_tree=null;
        Object string_literal230_tree=null;
        Object string_literal232_tree=null;
        Object string_literal234_tree=null;
        Object string_literal235_tree=null;
        Object string_literal237_tree=null;
        Object string_literal239_tree=null;
        Object string_literal241_tree=null;
        Object string_literal242_tree=null;
        Object string_literal244_tree=null;
        Object string_literal246_tree=null;
        Object string_literal248_tree=null;
        Object ID249_tree=null;
        Object char_literal250_tree=null;
        Object char_literal252_tree=null;
        Object string_literal253_tree=null;
        Object char_literal255_tree=null;
        Object char_literal256_tree=null;
        Object char_literal258_tree=null;
        Object char_literal259_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:10: ( 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end' | 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end' | 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end' | 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end' | ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' ) )
            int alt87=5;
            switch ( input.LA(1) ) {
            case 93:
                {
                alt87=1;
                }
                break;
            case 122:
                {
                alt87=2;
                }
                break;
            case 114:
                {
                alt87=3;
                }
                break;
            case 124:
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:3: 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal218=(Token)match(input,93,FOLLOW_93_in_statement1895); 
                    string_literal218_tree = (Object)adaptor.create(string_literal218);
                    adaptor.addChild(root_0, string_literal218_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:11: ( 'var' varDecls 'do' )?
                    int alt74=2;
                    int LA74_0 = input.LA(1);

                    if ( (LA74_0==88) ) {
                        alt74=1;
                    }
                    switch (alt74) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:12: 'var' varDecls 'do'
                            {
                            string_literal219=(Token)match(input,88,FOLLOW_88_in_statement1898); 
                            string_literal219_tree = (Object)adaptor.create(string_literal219);
                            adaptor.addChild(root_0, string_literal219_tree);

                            pushFollow(FOLLOW_varDecls_in_statement1900);
                            varDecls220=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls220.getTree());
                            string_literal221=(Token)match(input,82,FOLLOW_82_in_statement1902); 
                            string_literal221_tree = (Object)adaptor.create(string_literal221);
                            adaptor.addChild(root_0, string_literal221_tree);


                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:34: ( statement )*
                    loop75:
                    do {
                        int alt75=2;
                        int LA75_0 = input.LA(1);

                        if ( (LA75_0==ID||LA75_0==93||LA75_0==114||LA75_0==122||LA75_0==124) ) {
                            alt75=1;
                        }


                        switch (alt75) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:34: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement1906);
                    	    statement222=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement222.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop75;
                        }
                    } while (true);

                    string_literal223=(Token)match(input,86,FOLLOW_86_in_statement1909); 
                    string_literal223_tree = (Object)adaptor.create(string_literal223);
                    adaptor.addChild(root_0, string_literal223_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:3: 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal224=(Token)match(input,122,FOLLOW_122_in_statement1915); 
                    string_literal224_tree = (Object)adaptor.create(string_literal224);
                    adaptor.addChild(root_0, string_literal224_tree);

                    pushFollow(FOLLOW_varDeclNoExpr_in_statement1917);
                    varDeclNoExpr225=varDeclNoExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, varDeclNoExpr225.getTree());
                    string_literal226=(Token)match(input,120,FOLLOW_120_in_statement1919); 
                    string_literal226_tree = (Object)adaptor.create(string_literal226);
                    adaptor.addChild(root_0, string_literal226_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:32: ( expression ( '..' expression )? )
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:33: expression ( '..' expression )?
                    {
                    pushFollow(FOLLOW_expression_in_statement1922);
                    expression227=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression227.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:44: ( '..' expression )?
                    int alt76=2;
                    int LA76_0 = input.LA(1);

                    if ( (LA76_0==123) ) {
                        alt76=1;
                    }
                    switch (alt76) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:45: '..' expression
                            {
                            string_literal228=(Token)match(input,123,FOLLOW_123_in_statement1925); 
                            string_literal228_tree = (Object)adaptor.create(string_literal228);
                            adaptor.addChild(root_0, string_literal228_tree);

                            pushFollow(FOLLOW_expression_in_statement1927);
                            expression229=expression();

                            state._fsp--;

                            adaptor.addChild(root_0, expression229.getTree());

                            }
                            break;

                    }


                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:64: ( 'var' varDecls )?
                    int alt77=2;
                    int LA77_0 = input.LA(1);

                    if ( (LA77_0==88) ) {
                        alt77=1;
                    }
                    switch (alt77) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:65: 'var' varDecls
                            {
                            string_literal230=(Token)match(input,88,FOLLOW_88_in_statement1933); 
                            string_literal230_tree = (Object)adaptor.create(string_literal230);
                            adaptor.addChild(root_0, string_literal230_tree);

                            pushFollow(FOLLOW_varDecls_in_statement1935);
                            varDecls231=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls231.getTree());

                            }
                            break;

                    }

                    string_literal232=(Token)match(input,82,FOLLOW_82_in_statement1939); 
                    string_literal232_tree = (Object)adaptor.create(string_literal232);
                    adaptor.addChild(root_0, string_literal232_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:87: ( statement )*
                    loop78:
                    do {
                        int alt78=2;
                        int LA78_0 = input.LA(1);

                        if ( (LA78_0==ID||LA78_0==93||LA78_0==114||LA78_0==122||LA78_0==124) ) {
                            alt78=1;
                        }


                        switch (alt78) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:87: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement1941);
                    	    statement233=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement233.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop78;
                        }
                    } while (true);

                    string_literal234=(Token)match(input,86,FOLLOW_86_in_statement1944); 
                    string_literal234_tree = (Object)adaptor.create(string_literal234);
                    adaptor.addChild(root_0, string_literal234_tree);

                     

                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:3: 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal235=(Token)match(input,114,FOLLOW_114_in_statement1950); 
                    string_literal235_tree = (Object)adaptor.create(string_literal235);
                    adaptor.addChild(root_0, string_literal235_tree);

                    pushFollow(FOLLOW_expression_in_statement1952);
                    expression236=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression236.getTree());
                    string_literal237=(Token)match(input,115,FOLLOW_115_in_statement1954); 
                    string_literal237_tree = (Object)adaptor.create(string_literal237);
                    adaptor.addChild(root_0, string_literal237_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:26: ( statement )*
                    loop79:
                    do {
                        int alt79=2;
                        int LA79_0 = input.LA(1);

                        if ( (LA79_0==ID||LA79_0==93||LA79_0==114||LA79_0==122||LA79_0==124) ) {
                            alt79=1;
                        }


                        switch (alt79) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:26: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement1956);
                    	    statement238=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement238.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop79;
                        }
                    } while (true);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:37: ( 'else' ( statement )* )?
                    int alt81=2;
                    int LA81_0 = input.LA(1);

                    if ( (LA81_0==116) ) {
                        alt81=1;
                    }
                    switch (alt81) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:38: 'else' ( statement )*
                            {
                            string_literal239=(Token)match(input,116,FOLLOW_116_in_statement1960); 
                            string_literal239_tree = (Object)adaptor.create(string_literal239);
                            adaptor.addChild(root_0, string_literal239_tree);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:45: ( statement )*
                            loop80:
                            do {
                                int alt80=2;
                                int LA80_0 = input.LA(1);

                                if ( (LA80_0==ID||LA80_0==93||LA80_0==114||LA80_0==122||LA80_0==124) ) {
                                    alt80=1;
                                }


                                switch (alt80) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:45: statement
                            	    {
                            	    pushFollow(FOLLOW_statement_in_statement1962);
                            	    statement240=statement();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, statement240.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop80;
                                }
                            } while (true);


                            }
                            break;

                    }

                    string_literal241=(Token)match(input,86,FOLLOW_86_in_statement1967); 
                    string_literal241_tree = (Object)adaptor.create(string_literal241);
                    adaptor.addChild(root_0, string_literal241_tree);

                      

                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:241:3: 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal242=(Token)match(input,124,FOLLOW_124_in_statement1973); 
                    string_literal242_tree = (Object)adaptor.create(string_literal242);
                    adaptor.addChild(root_0, string_literal242_tree);

                    pushFollow(FOLLOW_expression_in_statement1975);
                    expression243=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression243.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:241:22: ( 'var' varDecls )?
                    int alt82=2;
                    int LA82_0 = input.LA(1);

                    if ( (LA82_0==88) ) {
                        alt82=1;
                    }
                    switch (alt82) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:241:23: 'var' varDecls
                            {
                            string_literal244=(Token)match(input,88,FOLLOW_88_in_statement1978); 
                            string_literal244_tree = (Object)adaptor.create(string_literal244);
                            adaptor.addChild(root_0, string_literal244_tree);

                            pushFollow(FOLLOW_varDecls_in_statement1980);
                            varDecls245=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls245.getTree());

                            }
                            break;

                    }

                    string_literal246=(Token)match(input,82,FOLLOW_82_in_statement1984); 
                    string_literal246_tree = (Object)adaptor.create(string_literal246);
                    adaptor.addChild(root_0, string_literal246_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:241:45: ( statement )*
                    loop83:
                    do {
                        int alt83=2;
                        int LA83_0 = input.LA(1);

                        if ( (LA83_0==ID||LA83_0==93||LA83_0==114||LA83_0==122||LA83_0==124) ) {
                            alt83=1;
                        }


                        switch (alt83) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:241:45: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement1986);
                    	    statement247=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement247.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop83;
                        }
                    } while (true);

                    string_literal248=(Token)match(input,86,FOLLOW_86_in_statement1989); 
                    string_literal248_tree = (Object)adaptor.create(string_literal248);
                    adaptor.addChild(root_0, string_literal248_tree);

                      

                    }
                    break;
                case 5 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:243:3: ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' )
                    {
                    root_0 = (Object)adaptor.nil();

                    ID249=(Token)match(input,ID,FOLLOW_ID_in_statement1996); 
                    ID249_tree = (Object)adaptor.create(ID249);
                    adaptor.addChild(root_0, ID249_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:243:6: ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' )
                    int alt86=2;
                    int LA86_0 = input.LA(1);

                    if ( (LA86_0==79||LA86_0==90) ) {
                        alt86=1;
                    }
                    else if ( (LA86_0==83) ) {
                        alt86=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 86, 0, input);

                        throw nvae;
                    }
                    switch (alt86) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:5: ( ( '[' expressions ']' )? ':=' expression ';' )
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:5: ( ( '[' expressions ']' )? ':=' expression ';' )
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:6: ( '[' expressions ']' )? ':=' expression ';'
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:6: ( '[' expressions ']' )?
                            int alt84=2;
                            int LA84_0 = input.LA(1);

                            if ( (LA84_0==79) ) {
                                alt84=1;
                            }
                            switch (alt84) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:7: '[' expressions ']'
                                    {
                                    char_literal250=(Token)match(input,79,FOLLOW_79_in_statement2006); 
                                    char_literal250_tree = (Object)adaptor.create(char_literal250);
                                    adaptor.addChild(root_0, char_literal250_tree);

                                    pushFollow(FOLLOW_expressions_in_statement2008);
                                    expressions251=expressions();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expressions251.getTree());
                                    char_literal252=(Token)match(input,80,FOLLOW_80_in_statement2010); 
                                    char_literal252_tree = (Object)adaptor.create(char_literal252);
                                    adaptor.addChild(root_0, char_literal252_tree);


                                    }
                                    break;

                            }

                            string_literal253=(Token)match(input,90,FOLLOW_90_in_statement2014); 
                            string_literal253_tree = (Object)adaptor.create(string_literal253);
                            adaptor.addChild(root_0, string_literal253_tree);

                            pushFollow(FOLLOW_expression_in_statement2016);
                            expression254=expression();

                            state._fsp--;

                            adaptor.addChild(root_0, expression254.getTree());
                            char_literal255=(Token)match(input,91,FOLLOW_91_in_statement2018); 
                            char_literal255_tree = (Object)adaptor.create(char_literal255);
                            adaptor.addChild(root_0, char_literal255_tree);


                            }

                             

                            }
                            break;
                        case 2 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:6: '(' ( expressions )? ')' ';'
                            {
                            char_literal256=(Token)match(input,83,FOLLOW_83_in_statement2028); 
                            char_literal256_tree = (Object)adaptor.create(char_literal256);
                            adaptor.addChild(root_0, char_literal256_tree);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:10: ( expressions )?
                            int alt85=2;
                            int LA85_0 = input.LA(1);

                            if ( (LA85_0==MINUS||LA85_0==ID||(LA85_0>=FLOAT && LA85_0<=INTEGER)||LA85_0==STRING||LA85_0==79||LA85_0==83||(LA85_0>=112 && LA85_0<=114)||(LA85_0>=117 && LA85_0<=118)) ) {
                                alt85=1;
                            }
                            switch (alt85) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:10: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_statement2030);
                                    expressions257=expressions();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expressions257.getTree());

                                    }
                                    break;

                            }

                            char_literal258=(Token)match(input,84,FOLLOW_84_in_statement2033); 
                            char_literal258_tree = (Object)adaptor.create(char_literal258);
                            adaptor.addChild(root_0, char_literal258_tree);

                            char_literal259=(Token)match(input,91,FOLLOW_91_in_statement2035); 
                            char_literal259_tree = (Object)adaptor.create(char_literal259);
                            adaptor.addChild(root_0, char_literal259_tree);

                             

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:247:1: typeAttr : ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) ) ;
    public final RVCCalParser.typeAttr_return typeAttr() throws RecognitionException {
        RVCCalParser.typeAttr_return retval = new RVCCalParser.typeAttr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID260=null;
        Token char_literal261=null;
        Token char_literal263=null;
        RVCCalParser.typeDef_return typeDef262 = null;

        RVCCalParser.expression_return expression264 = null;


        Object ID260_tree=null;
        Object char_literal261_tree=null;
        Object char_literal263_tree=null;
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:9: ( ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:11: ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) )
            {
            ID260=(Token)match(input,ID,FOLLOW_ID_in_typeAttr2056);  
            stream_ID.add(ID260);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:14: ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) )
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==78) ) {
                alt88=1;
            }
            else if ( (LA88_0==89) ) {
                alt88=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 88, 0, input);

                throw nvae;
            }
            switch (alt88) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:15: ':' typeDef
                    {
                    char_literal261=(Token)match(input,78,FOLLOW_78_in_typeAttr2059);  
                    stream_78.add(char_literal261);

                    pushFollow(FOLLOW_typeDef_in_typeAttr2061);
                    typeDef262=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef262.getTree());


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
                    // 254:27: -> ^( TYPE ID typeDef )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:30: ^( TYPE ID typeDef )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_1);

                        adaptor.addChild(root_1, stream_ID.nextNode());
                        adaptor.addChild(root_1, stream_typeDef.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:51: '=' expression
                    {
                    char_literal263=(Token)match(input,89,FOLLOW_89_in_typeAttr2075);  
                    stream_89.add(char_literal263);

                    pushFollow(FOLLOW_expression_in_typeAttr2077);
                    expression264=expression();

                    state._fsp--;

                    stream_expression.add(expression264.getTree());


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
                    // 254:66: -> ^( EXPR ID expression )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:69: ^( EXPR ID expression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR, "EXPR"), root_1);

                        adaptor.addChild(root_1, stream_ID.nextNode());
                        adaptor.addChild(root_1, stream_expression.nextTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:1: typeAttrs : typeAttr ( ',' typeAttr )* -> ( typeAttr )+ ;
    public final RVCCalParser.typeAttrs_return typeAttrs() throws RecognitionException {
        RVCCalParser.typeAttrs_return retval = new RVCCalParser.typeAttrs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal266=null;
        RVCCalParser.typeAttr_return typeAttr265 = null;

        RVCCalParser.typeAttr_return typeAttr267 = null;


        Object char_literal266_tree=null;
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleSubtreeStream stream_typeAttr=new RewriteRuleSubtreeStream(adaptor,"rule typeAttr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:10: ( typeAttr ( ',' typeAttr )* -> ( typeAttr )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:12: typeAttr ( ',' typeAttr )*
            {
            pushFollow(FOLLOW_typeAttr_in_typeAttrs2096);
            typeAttr265=typeAttr();

            state._fsp--;

            stream_typeAttr.add(typeAttr265.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:21: ( ',' typeAttr )*
            loop89:
            do {
                int alt89=2;
                int LA89_0 = input.LA(1);

                if ( (LA89_0==81) ) {
                    alt89=1;
                }


                switch (alt89) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:22: ',' typeAttr
            	    {
            	    char_literal266=(Token)match(input,81,FOLLOW_81_in_typeAttrs2099);  
            	    stream_81.add(char_literal266);

            	    pushFollow(FOLLOW_typeAttr_in_typeAttrs2101);
            	    typeAttr267=typeAttr();

            	    state._fsp--;

            	    stream_typeAttr.add(typeAttr267.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:1: typeDef : ID ( '(' attrs= typeAttrs ')' )? -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) ;
    public final RVCCalParser.typeDef_return typeDef() throws RecognitionException {
        RVCCalParser.typeDef_return retval = new RVCCalParser.typeDef_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID268=null;
        Token char_literal269=null;
        Token char_literal270=null;
        RVCCalParser.typeAttrs_return attrs = null;


        Object ID268_tree=null;
        Object char_literal269_tree=null;
        Object char_literal270_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_typeAttrs=new RewriteRuleSubtreeStream(adaptor,"rule typeAttrs");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:8: ( ID ( '(' attrs= typeAttrs ')' )? -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:10: ID ( '(' attrs= typeAttrs ')' )?
            {
            ID268=(Token)match(input,ID,FOLLOW_ID_in_typeDef2118);  
            stream_ID.add(ID268);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:13: ( '(' attrs= typeAttrs ')' )?
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==83) ) {
                alt90=1;
            }
            switch (alt90) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:14: '(' attrs= typeAttrs ')'
                    {
                    char_literal269=(Token)match(input,83,FOLLOW_83_in_typeDef2121);  
                    stream_83.add(char_literal269);

                    pushFollow(FOLLOW_typeAttrs_in_typeDef2125);
                    attrs=typeAttrs();

                    state._fsp--;

                    stream_typeAttrs.add(attrs.getTree());
                    char_literal270=(Token)match(input,84,FOLLOW_84_in_typeDef2127);  
                    stream_84.add(char_literal270);


                    }
                    break;

            }



            // AST REWRITE
            // elements: attrs, ID
            // token labels: 
            // rule labels: retval, attrs
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_attrs=new RewriteRuleSubtreeStream(adaptor,"rule attrs",attrs!=null?attrs.tree:null);

            root_0 = (Object)adaptor.nil();
            // 259:40: -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:43: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:53: ^( TYPE_ATTRS ( $attrs)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_2);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:66: ( $attrs)?
                if ( stream_attrs.hasNext() ) {
                    adaptor.addChild(root_2, stream_attrs.nextTree());

                }
                stream_attrs.reset();

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
    // $ANTLR end "typeDef"

    public static class varDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "varDecl"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:261:1: varDecl : typeDef ID ( '=' expression -> ^( VARIABLE typeDef ID NON_ASSIGNABLE expression ) | ':=' expression -> ^( VARIABLE typeDef ID ASSIGNABLE expression ) | -> ^( VARIABLE typeDef ID ASSIGNABLE ) ) ;
    public final RVCCalParser.varDecl_return varDecl() throws RecognitionException {
        RVCCalParser.varDecl_return retval = new RVCCalParser.varDecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID272=null;
        Token char_literal273=null;
        Token string_literal275=null;
        RVCCalParser.typeDef_return typeDef271 = null;

        RVCCalParser.expression_return expression274 = null;

        RVCCalParser.expression_return expression276 = null;


        Object ID272_tree=null;
        Object char_literal273_tree=null;
        Object string_literal275_tree=null;
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:8: ( typeDef ID ( '=' expression -> ^( VARIABLE typeDef ID NON_ASSIGNABLE expression ) | ':=' expression -> ^( VARIABLE typeDef ID ASSIGNABLE expression ) | -> ^( VARIABLE typeDef ID ASSIGNABLE ) ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:10: typeDef ID ( '=' expression -> ^( VARIABLE typeDef ID NON_ASSIGNABLE expression ) | ':=' expression -> ^( VARIABLE typeDef ID ASSIGNABLE expression ) | -> ^( VARIABLE typeDef ID ASSIGNABLE ) )
            {
            pushFollow(FOLLOW_typeDef_in_varDecl2159);
            typeDef271=typeDef();

            state._fsp--;

            stream_typeDef.add(typeDef271.getTree());
            ID272=(Token)match(input,ID,FOLLOW_ID_in_varDecl2161);  
            stream_ID.add(ID272);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:3: ( '=' expression -> ^( VARIABLE typeDef ID NON_ASSIGNABLE expression ) | ':=' expression -> ^( VARIABLE typeDef ID ASSIGNABLE expression ) | -> ^( VARIABLE typeDef ID ASSIGNABLE ) )
            int alt91=3;
            switch ( input.LA(1) ) {
            case 89:
                {
                alt91=1;
                }
                break;
            case 90:
                {
                alt91=2;
                }
                break;
            case 78:
            case 81:
            case 82:
            case 86:
            case 93:
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:4: '=' expression
                    {
                    char_literal273=(Token)match(input,89,FOLLOW_89_in_varDecl2166);  
                    stream_89.add(char_literal273);

                    pushFollow(FOLLOW_expression_in_varDecl2168);
                    expression274=expression();

                    state._fsp--;

                    stream_expression.add(expression274.getTree());


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
                    // 266:19: -> ^( VARIABLE typeDef ID NON_ASSIGNABLE expression )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:22: ^( VARIABLE typeDef ID NON_ASSIGNABLE expression )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:267:5: ':=' expression
                    {
                    string_literal275=(Token)match(input,90,FOLLOW_90_in_varDecl2188);  
                    stream_90.add(string_literal275);

                    pushFollow(FOLLOW_expression_in_varDecl2190);
                    expression276=expression();

                    state._fsp--;

                    stream_expression.add(expression276.getTree());


                    // AST REWRITE
                    // elements: ID, expression, typeDef
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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:267:24: ^( VARIABLE typeDef ID ASSIGNABLE expression )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:268:5: 
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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:268:8: ^( VARIABLE typeDef ID ASSIGNABLE )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:270:1: varDeclNoExpr : typeDef ID -> ^( VARIABLE typeDef ID ASSIGNABLE ) ;
    public final RVCCalParser.varDeclNoExpr_return varDeclNoExpr() throws RecognitionException {
        RVCCalParser.varDeclNoExpr_return retval = new RVCCalParser.varDeclNoExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID278=null;
        RVCCalParser.typeDef_return typeDef277 = null;


        Object ID278_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:270:14: ( typeDef ID -> ^( VARIABLE typeDef ID ASSIGNABLE ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:270:16: typeDef ID
            {
            pushFollow(FOLLOW_typeDef_in_varDeclNoExpr2228);
            typeDef277=typeDef();

            state._fsp--;

            stream_typeDef.add(typeDef277.getTree());
            ID278=(Token)match(input,ID,FOLLOW_ID_in_varDeclNoExpr2230);  
            stream_ID.add(ID278);



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
            // 270:27: -> ^( VARIABLE typeDef ID ASSIGNABLE )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:270:30: ^( VARIABLE typeDef ID ASSIGNABLE )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:272:1: varDecls : varDecl ( ',' varDecl )* -> ( varDecl )+ ;
    public final RVCCalParser.varDecls_return varDecls() throws RecognitionException {
        RVCCalParser.varDecls_return retval = new RVCCalParser.varDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal280=null;
        RVCCalParser.varDecl_return varDecl279 = null;

        RVCCalParser.varDecl_return varDecl281 = null;


        Object char_literal280_tree=null;
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleSubtreeStream stream_varDecl=new RewriteRuleSubtreeStream(adaptor,"rule varDecl");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:272:9: ( varDecl ( ',' varDecl )* -> ( varDecl )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:272:11: varDecl ( ',' varDecl )*
            {
            pushFollow(FOLLOW_varDecl_in_varDecls2249);
            varDecl279=varDecl();

            state._fsp--;

            stream_varDecl.add(varDecl279.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:272:19: ( ',' varDecl )*
            loop92:
            do {
                int alt92=2;
                int LA92_0 = input.LA(1);

                if ( (LA92_0==81) ) {
                    alt92=1;
                }


                switch (alt92) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:272:20: ',' varDecl
            	    {
            	    char_literal280=(Token)match(input,81,FOLLOW_81_in_varDecls2252);  
            	    stream_81.add(char_literal280);

            	    pushFollow(FOLLOW_varDecl_in_varDecls2254);
            	    varDecl281=varDecl();

            	    state._fsp--;

            	    stream_varDecl.add(varDecl281.getTree());

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
        "\1\77\23\uffff";
    static final String DFA58_maxS =
        "\1\157\23\uffff";
    static final String DFA58_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1"+
        "\15\1\16\1\17\1\20\1\21\1\22\1\23";
    static final String DFA58_specialS =
        "\24\uffff}>";
    static final String[] DFA58_transitionS = {
            "\1\15\1\16\1\22\1\17\26\uffff\1\5\6\uffff\2\1\2\2\1\3\1\4\1"+
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
 

    public static final BitSet FOLLOW_GUARD_in_actionGuards61 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expressions_in_actionGuards63 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_actionInput76 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_actionInput78 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_actionInput82 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_idents_in_actionInput84 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_actionInput86 = new BitSet(new long[]{0x2000000000000002L});
    public static final BitSet FOLLOW_actionRepeat_in_actionInput88 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionInput_in_actionInputs99 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_actionInputs102 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008010L});
    public static final BitSet FOLLOW_actionInput_in_actionInputs104 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_ID_in_actionOutput120 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_actionOutput122 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_actionOutput126 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expressions_in_actionOutput128 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_actionOutput130 = new BitSet(new long[]{0x2000000000000002L});
    public static final BitSet FOLLOW_actionRepeat_in_actionOutput132 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionOutput_in_actionOutputs143 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_actionOutputs146 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008010L});
    public static final BitSet FOLLOW_actionOutput_in_actionOutputs148 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_REPEAT_in_actionRepeat162 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_actionRepeat164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_actionStatements175 = new BitSet(new long[]{0x0000000000000002L,0x1404000020000010L});
    public static final BitSet FOLLOW_statement_in_actionStatements177 = new BitSet(new long[]{0x0000000000000002L,0x1404000020000010L});
    public static final BitSet FOLLOW_actorImport_in_actor195 = new BitSet(new long[]{0x0080000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_ACTOR_in_actor198 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_actor200 = new BitSet(new long[]{0x0000000000000000L,0x0000000000088000L});
    public static final BitSet FOLLOW_79_in_actor203 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_actor205 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_actor209 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100010L});
    public static final BitSet FOLLOW_actorParameters_in_actor211 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_actor214 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200010L});
    public static final BitSet FOLLOW_actorPortDecls_in_actor219 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actor222 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004010L});
    public static final BitSet FOLLOW_actorPortDecls_in_actor226 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_actor229 = new BitSet(new long[]{0x5D40000000000000L,0x0000000000400010L});
    public static final BitSet FOLLOW_actorDeclarations_in_actor232 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actor235 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_actor237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_id293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_actorDeclaration312 = new BitSet(new long[]{0x0000000000000000L,0x0000000000884010L});
    public static final BitSet FOLLOW_87_in_actorDeclaration323 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration325 = new BitSet(new long[]{0x0000000000000000L,0x0000000000804000L});
    public static final BitSet FOLLOW_78_in_actorDeclaration330 = new BitSet(new long[]{0x0440000000000000L});
    public static final BitSet FOLLOW_ACTION_in_actorDeclaration339 = new BitSet(new long[]{0x0000000000000000L,0x0000000000208010L});
    public static final BitSet FOLLOW_actionInputs_in_actorDeclaration343 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actorDeclaration346 = new BitSet(new long[]{0x0200000000000000L,0x0000000001448010L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration350 = new BitSet(new long[]{0x0200000000000000L,0x0000000001440000L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration355 = new BitSet(new long[]{0x0000000000000000L,0x0000000001440000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration359 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration361 = new BitSet(new long[]{0x0000000000000000L,0x0000000000440000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration365 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INITIALIZE_in_actorDeclaration446 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actorDeclaration448 = new BitSet(new long[]{0x0200000000000000L,0x0000000001448010L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration450 = new BitSet(new long[]{0x0200000000000000L,0x0000000001440000L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration453 = new BitSet(new long[]{0x0000000000000000L,0x0000000001440000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration457 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration459 = new BitSet(new long[]{0x0000000000000000L,0x0000000000440000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration463 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_actorDeclaration552 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_typeAttrs_in_actorDeclaration556 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_actorDeclaration558 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration568 = new BitSet(new long[]{0x0000000000000000L,0x000000000E000000L});
    public static final BitSet FOLLOW_89_in_actorDeclaration577 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration579 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_90_in_actorDeclaration615 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration617 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_actorDeclaration679 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ACTION_in_actorDeclaration689 = new BitSet(new long[]{0x0000000000000000L,0x0000000000208010L});
    public static final BitSet FOLLOW_actionInputs_in_actorDeclaration691 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actorDeclaration694 = new BitSet(new long[]{0x0200000000000000L,0x0000000001448010L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration696 = new BitSet(new long[]{0x0200000000000000L,0x0000000001440000L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration699 = new BitSet(new long[]{0x0000000000000000L,0x0000000001440000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration703 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration705 = new BitSet(new long[]{0x0000000000000000L,0x0000000000440000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration709 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration712 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INITIALIZE_in_actorDeclaration766 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actorDeclaration768 = new BitSet(new long[]{0x0200000000000000L,0x0000000001448010L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration770 = new BitSet(new long[]{0x0200000000000000L,0x0000000001440000L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration773 = new BitSet(new long[]{0x0000000000000000L,0x0000000001440000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration777 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration779 = new BitSet(new long[]{0x0000000000000000L,0x0000000000440000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration783 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_priorityOrder_in_actorDeclaration833 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FUNCTION_in_actorDeclaration842 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration844 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_actorDeclaration846 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100010L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration849 = new BitSet(new long[]{0x0000000000000000L,0x0000000000120000L});
    public static final BitSet FOLLOW_81_in_actorDeclaration852 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration854 = new BitSet(new long[]{0x0000000000000000L,0x0000000000120000L});
    public static final BitSet FOLLOW_84_in_actorDeclaration860 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_actorDeclaration862 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_typeDef_in_actorDeclaration864 = new BitSet(new long[]{0x0000000000000000L,0x0000000001004000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration871 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration873 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_actorDeclaration877 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration885 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROCEDURE_in_actorDeclaration921 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration923 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_actorDeclaration925 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100010L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration928 = new BitSet(new long[]{0x0000000000000000L,0x0000000000120000L});
    public static final BitSet FOLLOW_81_in_actorDeclaration931 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration933 = new BitSet(new long[]{0x0000000000000000L,0x0000000000120000L});
    public static final BitSet FOLLOW_84_in_actorDeclaration939 = new BitSet(new long[]{0x0000000000000000L,0x0000000021000000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration946 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration948 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_93_in_actorDeclaration956 = new BitSet(new long[]{0x0000000000000000L,0x1404000020400010L});
    public static final BitSet FOLLOW_statement_in_actorDeclaration958 = new BitSet(new long[]{0x0000000000000000L,0x1404000020400010L});
    public static final BitSet FOLLOW_86_in_actorDeclaration961 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations998 = new BitSet(new long[]{0x5D40000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_schedule_in_actorDeclarations1002 = new BitSet(new long[]{0x1D40000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations1004 = new BitSet(new long[]{0x1D40000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_schedule_in_actorDeclarations1021 = new BitSet(new long[]{0x1D40000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations1023 = new BitSet(new long[]{0x1D40000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_94_in_actorImport1043 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000010L});
    public static final BitSet FOLLOW_95_in_actorImport1048 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000010L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport1050 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_actorImport1052 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport1058 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_actorImport1060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_actorParameter1075 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_actorParameter1077 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_actorParameter1080 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_actorParameter1082 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters1104 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_actorParameters1107 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters1109 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorPortDecls1128 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_actorPortDecls1131 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorPortDecls1133 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_un_expr_in_expression1154 = new BitSet(new long[]{0x8000000000000002L,0x0000FFFF02000007L});
    public static final BitSet FOLLOW_bop_in_expression1160 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_un_expr_in_expression1162 = new BitSet(new long[]{0x8000000000000002L,0x0000FFFF02000007L});
    public static final BitSet FOLLOW_96_in_bop1201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_97_in_bop1205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_bop1215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_bop1219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_100_in_bop1228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_bop1236 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_bop1244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_bop1252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_bop1260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_104_in_bop1268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_bop1276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_106_in_bop1284 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_107_in_bop1292 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_108_in_bop1300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_in_bop1308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_bop1316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DIV_in_bop1324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_109_in_bop1332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_110_in_bop1340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TIMES_in_bop1348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_111_in_bop1356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfix_expression_in_un_expr1367 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_un_op_in_un_expr1377 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_un_expr_in_un_expr1379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_un_op1396 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_112_in_un_op1404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_113_in_un_op1412 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_postfix_expression1425 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1429 = new BitSet(new long[]{0x0000000000000000L,0x0000000000014000L});
    public static final BitSet FOLLOW_78_in_postfix_expression1432 = new BitSet(new long[]{0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_expressionGenerators_in_postfix_expression1436 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_postfix_expression1440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_postfix_expression1457 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1461 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_115_in_postfix_expression1463 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1467 = new BitSet(new long[]{0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_116_in_postfix_expression1469 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1473 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_postfix_expression1475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_postfix_expression1494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_postfix_expression1502 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1504 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_postfix_expression1506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_postfix_expression1516 = new BitSet(new long[]{0x0000000000000002L,0x0000000000088000L});
    public static final BitSet FOLLOW_83_in_postfix_expression1524 = new BitSet(new long[]{0x0000000000000000L,0x00670000001882D1L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1526 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_postfix_expression1529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_postfix_expression1549 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1551 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_postfix_expression1553 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_117_in_constant1590 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_118_in_constant1602 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_constant1614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_constant1626 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_constant1638 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_119_in_expressionGenerator1654 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_typeDef_in_expressionGenerator1656 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_expressionGenerator1658 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_120_in_expressionGenerator1660 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_expressionGenerator1662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators1672 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_expressionGenerators1675 = new BitSet(new long[]{0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators1677 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_expression_in_expressions1691 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_expressions1694 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_expressions1696 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_ID_in_idents1715 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_idents1718 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_idents1720 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality1739 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_104_in_priorityInequality1742 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000010L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality1744 = new BitSet(new long[]{0x0000000000000000L,0x0000010008000000L});
    public static final BitSet FOLLOW_91_in_priorityInequality1748 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRIORITY_in_priorityOrder1767 = new BitSet(new long[]{0x0000000000000000L,0x0000000080400010L});
    public static final BitSet FOLLOW_priorityInequality_in_priorityOrder1769 = new BitSet(new long[]{0x0000000000000000L,0x0000000080400010L});
    public static final BitSet FOLLOW_86_in_priorityOrder1772 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent1793 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_qualifiedIdent1796 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent1798 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_SCHEDULE_in_schedule1823 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_121_in_schedule1825 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_schedule1827 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_schedule1829 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400010L});
    public static final BitSet FOLLOW_stateTransition_in_schedule1831 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400010L});
    public static final BitSet FOLLOW_86_in_schedule1834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stateTransition1857 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_stateTransition1859 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000010L});
    public static final BitSet FOLLOW_qualifiedIdent_in_stateTransition1861 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_stateTransition1863 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_stateTransition1865 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_stateTransition1867 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_stateTransition1869 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_statement1895 = new BitSet(new long[]{0x0000000000000000L,0x1404000021400010L});
    public static final BitSet FOLLOW_88_in_statement1898 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_varDecls_in_statement1900 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_statement1902 = new BitSet(new long[]{0x0000000000000000L,0x1404000020400010L});
    public static final BitSet FOLLOW_statement_in_statement1906 = new BitSet(new long[]{0x0000000000000000L,0x1404000020400010L});
    public static final BitSet FOLLOW_86_in_statement1909 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_122_in_statement1915 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_statement1917 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_120_in_statement1919 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_statement1922 = new BitSet(new long[]{0x0000000000000000L,0x0800000001040000L});
    public static final BitSet FOLLOW_123_in_statement1925 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_statement1927 = new BitSet(new long[]{0x0000000000000000L,0x0000000001040000L});
    public static final BitSet FOLLOW_88_in_statement1933 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_varDecls_in_statement1935 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_statement1939 = new BitSet(new long[]{0x0000000000000000L,0x1404000020400010L});
    public static final BitSet FOLLOW_statement_in_statement1941 = new BitSet(new long[]{0x0000000000000000L,0x1404000020400010L});
    public static final BitSet FOLLOW_86_in_statement1944 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_statement1950 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_statement1952 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_115_in_statement1954 = new BitSet(new long[]{0x0000000000000000L,0x1414000020400010L});
    public static final BitSet FOLLOW_statement_in_statement1956 = new BitSet(new long[]{0x0000000000000000L,0x1414000020400010L});
    public static final BitSet FOLLOW_116_in_statement1960 = new BitSet(new long[]{0x0000000000000000L,0x1404000020400010L});
    public static final BitSet FOLLOW_statement_in_statement1962 = new BitSet(new long[]{0x0000000000000000L,0x1404000020400010L});
    public static final BitSet FOLLOW_86_in_statement1967 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_124_in_statement1973 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_statement1975 = new BitSet(new long[]{0x0000000000000000L,0x0000000001040000L});
    public static final BitSet FOLLOW_88_in_statement1978 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_varDecls_in_statement1980 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_statement1984 = new BitSet(new long[]{0x0000000000000000L,0x1404000020400010L});
    public static final BitSet FOLLOW_statement_in_statement1986 = new BitSet(new long[]{0x0000000000000000L,0x1404000020400010L});
    public static final BitSet FOLLOW_86_in_statement1989 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_statement1996 = new BitSet(new long[]{0x0000000000000000L,0x0000000004088000L});
    public static final BitSet FOLLOW_79_in_statement2006 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expressions_in_statement2008 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_statement2010 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_statement2014 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_statement2016 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_statement2018 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_statement2028 = new BitSet(new long[]{0x0000000000000000L,0x00670000001882D1L});
    public static final BitSet FOLLOW_expressions_in_statement2030 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_statement2033 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_statement2035 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_typeAttr2056 = new BitSet(new long[]{0x0000000000000000L,0x0000000002004000L});
    public static final BitSet FOLLOW_78_in_typeAttr2059 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_typeDef_in_typeAttr2061 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_typeAttr2075 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_typeAttr2077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs2096 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_typeAttrs2099 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs2101 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_ID_in_typeDef2118 = new BitSet(new long[]{0x0000000000000002L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_typeDef2121 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_typeAttrs_in_typeDef2125 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_typeDef2127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_varDecl2159 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_varDecl2161 = new BitSet(new long[]{0x0000000000000002L,0x0000000006000000L});
    public static final BitSet FOLLOW_89_in_varDecl2166 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_varDecl2168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_varDecl2188 = new BitSet(new long[]{0x0000000000000000L,0x00670000000882D1L});
    public static final BitSet FOLLOW_expression_in_varDecl2190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_varDeclNoExpr2228 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_varDeclNoExpr2230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varDecl_in_varDecls2249 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_varDecls2252 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_varDecl_in_varDecls2254 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});

}