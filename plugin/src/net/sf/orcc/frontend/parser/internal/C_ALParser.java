// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g 2009-11-03 18:52:19

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
public class C_ALParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "INPUTS", "OUTPUTS", "PARAMETERS", "STATEMENTS", "VARIABLE", "VARIABLES", "ACTOR_DECLS", "STATE_VAR", "TRANSITION", "TRANSITIONS", "INEQUALITY", "GUARDS", "TAG", "EXPR", "EXPR_BINARY", "EXPR_UNARY", "OP", "EXPR_LIST", "EXPR_IF", "EXPR_CALL", "EXPR_IDX", "EXPR_VAR", "EXPR_BOOL", "EXPR_FLOAT", "EXPR_INT", "EXPR_STRING", "TYPE", "TYPE_ATTRS", "ASSIGNABLE", "NON_ASSIGNABLE", "QID", "LOGIC_OR", "LOGIC_AND", "BITOR", "BITXOR", "BITAND", "EQ", "NE", "LT", "GT", "LE", "GE", "SHIFT_LEFT", "SHIFT_RIGHT", "DIV_INT", "MOD", "EXP", "BITNOT", "LOGIC_NOT", "NUM_ELTS", "ACTION", "ACTOR", "FUNCTION", "GUARD", "INITIALIZE", "PRIORITY", "PROCEDURE", "REPEAT", "SCHEDULE", "SIZE", "PLUS", "MINUS", "TIMES", "DIV", "LETTER", "ID", "Exponent", "FLOAT", "INTEGER", "EscapeSequence", "STRING", "OctalEscape", "LINE_COMMENT", "MULTI_LINE_COMMENT", "WHITESPACE", "':'", "'['", "']'", "','", "'('", "')'", "'==>'", "'{'", "'}'", "'const'", "'='", "';'", "'return'", "'void'", "'import'", "'all'", "'||'", "'&&'", "'|'", "'^'", "'&'", "'=='", "'!='", "'<'", "'>'", "'<='", "'>='", "'<<'", "'>>'", "'%'", "'**'", "'~'", "'!'", "'#'", "'true'", "'false'", "'for'", "'in'", "'end'", "'.'", "'fsm'", "'-->'", "'int'", "'uint'", "'bool'", "'float'"
    };
    public static final int FUNCTION=56;
    public static final int EXPR_BOOL=26;
    public static final int BITNOT=51;
    public static final int LT=42;
    public static final int OUTPUTS=5;
    public static final int TRANSITION=12;
    public static final int EXPR_VAR=25;
    public static final int LOGIC_NOT=52;
    public static final int LETTER=68;
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
    public static final int LINE_COMMENT=76;
    public static final int DIV_INT=48;
    public static final int LOGIC_OR=35;
    public static final int WHITESPACE=78;
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
    public static final int EscapeSequence=73;
    public static final int SIZE=63;
    public static final int T__79=79;
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
    public static final int Exponent=70;
    public static final int T__122=122;
    public static final int T__121=121;
    public static final int FLOAT=71;
    public static final int T__120=120;
    public static final int EXPR_FLOAT=27;
    public static final int LOGIC_AND=36;
    public static final int ID=69;
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
    public static final int PLUS=64;
    public static final int T__112=112;
    public static final int EXPR_INT=28;
    public static final int INTEGER=72;
    public static final int TRANSITIONS=13;
    public static final int T__102=102;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int MINUS=65;
    public static final int EXPR_IF=22;
    public static final int MULTI_LINE_COMMENT=77;
    public static final int PROCEDURE=60;
    public static final int TAG=16;
    public static final int QID=34;
    public static final int VARIABLES=9;
    public static final int DIV=67;
    public static final int TIMES=66;
    public static final int OctalEscape=75;
    public static final int LE=44;
    public static final int STRING=74;

    // delegates
    // delegators


        public C_ALParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public C_ALParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return C_ALParser.tokenNames; }
    public String getGrammarFileName() { return "D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g"; }


    public static class actionGuards_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionGuards"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:55:1: actionGuards : GUARD expressions -> expressions ;
    public final C_ALParser.actionGuards_return actionGuards() throws RecognitionException {
        C_ALParser.actionGuards_return retval = new C_ALParser.actionGuards_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token GUARD1=null;
        C_ALParser.expressions_return expressions2 = null;


        Object GUARD1_tree=null;
        RewriteRuleTokenStream stream_GUARD=new RewriteRuleTokenStream(adaptor,"token GUARD");
        RewriteRuleSubtreeStream stream_expressions=new RewriteRuleSubtreeStream(adaptor,"rule expressions");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:55:13: ( GUARD expressions -> expressions )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:55:15: GUARD expressions
            {
            GUARD1=(Token)match(input,GUARD,FOLLOW_GUARD_in_actionGuards65);  
            stream_GUARD.add(GUARD1);

            pushFollow(FOLLOW_expressions_in_actionGuards67);
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
            // 55:33: -> expressions
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:57:1: actionInput : ( ID ':' )? '[' idents ']' ( actionRepeat )? ;
    public final C_ALParser.actionInput_return actionInput() throws RecognitionException {
        C_ALParser.actionInput_return retval = new C_ALParser.actionInput_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID3=null;
        Token char_literal4=null;
        Token char_literal5=null;
        Token char_literal7=null;
        C_ALParser.idents_return idents6 = null;

        C_ALParser.actionRepeat_return actionRepeat8 = null;


        Object ID3_tree=null;
        Object char_literal4_tree=null;
        Object char_literal5_tree=null;
        Object char_literal7_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:57:12: ( ( ID ':' )? '[' idents ']' ( actionRepeat )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:58:2: ( ID ':' )? '[' idents ']' ( actionRepeat )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:58:2: ( ID ':' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==ID) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:58:3: ID ':'
                    {
                    ID3=(Token)match(input,ID,FOLLOW_ID_in_actionInput80); 
                    ID3_tree = (Object)adaptor.create(ID3);
                    adaptor.addChild(root_0, ID3_tree);

                    char_literal4=(Token)match(input,79,FOLLOW_79_in_actionInput82); 
                    char_literal4_tree = (Object)adaptor.create(char_literal4);
                    adaptor.addChild(root_0, char_literal4_tree);


                    }
                    break;

            }

            char_literal5=(Token)match(input,80,FOLLOW_80_in_actionInput86); 
            char_literal5_tree = (Object)adaptor.create(char_literal5);
            adaptor.addChild(root_0, char_literal5_tree);

            pushFollow(FOLLOW_idents_in_actionInput88);
            idents6=idents();

            state._fsp--;

            adaptor.addChild(root_0, idents6.getTree());
            char_literal7=(Token)match(input,81,FOLLOW_81_in_actionInput90); 
            char_literal7_tree = (Object)adaptor.create(char_literal7);
            adaptor.addChild(root_0, char_literal7_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:58:27: ( actionRepeat )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==REPEAT) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:58:27: actionRepeat
                    {
                    pushFollow(FOLLOW_actionRepeat_in_actionInput92);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:61:1: actionInputs : actionInput ( ',' actionInput )* -> ( actionInput )+ ;
    public final C_ALParser.actionInputs_return actionInputs() throws RecognitionException {
        C_ALParser.actionInputs_return retval = new C_ALParser.actionInputs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal10=null;
        C_ALParser.actionInput_return actionInput9 = null;

        C_ALParser.actionInput_return actionInput11 = null;


        Object char_literal10_tree=null;
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleSubtreeStream stream_actionInput=new RewriteRuleSubtreeStream(adaptor,"rule actionInput");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:61:13: ( actionInput ( ',' actionInput )* -> ( actionInput )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:61:15: actionInput ( ',' actionInput )*
            {
            pushFollow(FOLLOW_actionInput_in_actionInputs103);
            actionInput9=actionInput();

            state._fsp--;

            stream_actionInput.add(actionInput9.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:61:27: ( ',' actionInput )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==82) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:61:28: ',' actionInput
            	    {
            	    char_literal10=(Token)match(input,82,FOLLOW_82_in_actionInputs106);  
            	    stream_82.add(char_literal10);

            	    pushFollow(FOLLOW_actionInput_in_actionInputs108);
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
            // 61:46: -> ( actionInput )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:63:1: actionOutput : ( ID ':' )? '[' expressions ']' ( actionRepeat )? ;
    public final C_ALParser.actionOutput_return actionOutput() throws RecognitionException {
        C_ALParser.actionOutput_return retval = new C_ALParser.actionOutput_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID12=null;
        Token char_literal13=null;
        Token char_literal14=null;
        Token char_literal16=null;
        C_ALParser.expressions_return expressions15 = null;

        C_ALParser.actionRepeat_return actionRepeat17 = null;


        Object ID12_tree=null;
        Object char_literal13_tree=null;
        Object char_literal14_tree=null;
        Object char_literal16_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:63:13: ( ( ID ':' )? '[' expressions ']' ( actionRepeat )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:64:2: ( ID ':' )? '[' expressions ']' ( actionRepeat )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:64:2: ( ID ':' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==ID) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:64:3: ID ':'
                    {
                    ID12=(Token)match(input,ID,FOLLOW_ID_in_actionOutput124); 
                    ID12_tree = (Object)adaptor.create(ID12);
                    adaptor.addChild(root_0, ID12_tree);

                    char_literal13=(Token)match(input,79,FOLLOW_79_in_actionOutput126); 
                    char_literal13_tree = (Object)adaptor.create(char_literal13);
                    adaptor.addChild(root_0, char_literal13_tree);


                    }
                    break;

            }

            char_literal14=(Token)match(input,80,FOLLOW_80_in_actionOutput130); 
            char_literal14_tree = (Object)adaptor.create(char_literal14);
            adaptor.addChild(root_0, char_literal14_tree);

            pushFollow(FOLLOW_expressions_in_actionOutput132);
            expressions15=expressions();

            state._fsp--;

            adaptor.addChild(root_0, expressions15.getTree());
            char_literal16=(Token)match(input,81,FOLLOW_81_in_actionOutput134); 
            char_literal16_tree = (Object)adaptor.create(char_literal16);
            adaptor.addChild(root_0, char_literal16_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:64:32: ( actionRepeat )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==REPEAT) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:64:32: actionRepeat
                    {
                    pushFollow(FOLLOW_actionRepeat_in_actionOutput136);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:67:1: actionOutputs : actionOutput ( ',' actionOutput )* -> ( actionOutput )+ ;
    public final C_ALParser.actionOutputs_return actionOutputs() throws RecognitionException {
        C_ALParser.actionOutputs_return retval = new C_ALParser.actionOutputs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal19=null;
        C_ALParser.actionOutput_return actionOutput18 = null;

        C_ALParser.actionOutput_return actionOutput20 = null;


        Object char_literal19_tree=null;
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleSubtreeStream stream_actionOutput=new RewriteRuleSubtreeStream(adaptor,"rule actionOutput");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:67:14: ( actionOutput ( ',' actionOutput )* -> ( actionOutput )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:67:16: actionOutput ( ',' actionOutput )*
            {
            pushFollow(FOLLOW_actionOutput_in_actionOutputs147);
            actionOutput18=actionOutput();

            state._fsp--;

            stream_actionOutput.add(actionOutput18.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:67:29: ( ',' actionOutput )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==82) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:67:30: ',' actionOutput
            	    {
            	    char_literal19=(Token)match(input,82,FOLLOW_82_in_actionOutputs150);  
            	    stream_82.add(char_literal19);

            	    pushFollow(FOLLOW_actionOutput_in_actionOutputs152);
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
            // 67:49: -> ( actionOutput )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:69:1: actionRepeat : REPEAT expression -> expression ;
    public final C_ALParser.actionRepeat_return actionRepeat() throws RecognitionException {
        C_ALParser.actionRepeat_return retval = new C_ALParser.actionRepeat_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token REPEAT21=null;
        C_ALParser.expression_return expression22 = null;


        Object REPEAT21_tree=null;
        RewriteRuleTokenStream stream_REPEAT=new RewriteRuleTokenStream(adaptor,"token REPEAT");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:69:13: ( REPEAT expression -> expression )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:69:15: REPEAT expression
            {
            REPEAT21=(Token)match(input,REPEAT,FOLLOW_REPEAT_in_actionRepeat166);  
            stream_REPEAT.add(REPEAT21);

            pushFollow(FOLLOW_expression_in_actionRepeat168);
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
            // 69:33: -> expression
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

    public static class actor_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actor"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:71:1: actor : ( actorImport )* ACTOR ID '(' ( actorParameters )? ')' '(' (inputs= parameters )? '==>' (outputs= parameters )? ')' '{' ( actorDeclarations )? '}' EOF -> ACTOR ID ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS ( actorDeclarations )? ) ;
    public final C_ALParser.actor_return actor() throws RecognitionException {
        C_ALParser.actor_return retval = new C_ALParser.actor_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ACTOR24=null;
        Token ID25=null;
        Token char_literal26=null;
        Token char_literal28=null;
        Token char_literal29=null;
        Token string_literal30=null;
        Token char_literal31=null;
        Token char_literal32=null;
        Token char_literal34=null;
        Token EOF35=null;
        C_ALParser.parameters_return inputs = null;

        C_ALParser.parameters_return outputs = null;

        C_ALParser.actorImport_return actorImport23 = null;

        C_ALParser.actorParameters_return actorParameters27 = null;

        C_ALParser.actorDeclarations_return actorDeclarations33 = null;


        Object ACTOR24_tree=null;
        Object ID25_tree=null;
        Object char_literal26_tree=null;
        Object char_literal28_tree=null;
        Object char_literal29_tree=null;
        Object string_literal30_tree=null;
        Object char_literal31_tree=null;
        Object char_literal32_tree=null;
        Object char_literal34_tree=null;
        Object EOF35_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_ACTOR=new RewriteRuleTokenStream(adaptor,"token ACTOR");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleTokenStream stream_85=new RewriteRuleTokenStream(adaptor,"token 85");
        RewriteRuleSubtreeStream stream_actorImport=new RewriteRuleSubtreeStream(adaptor,"rule actorImport");
        RewriteRuleSubtreeStream stream_parameters=new RewriteRuleSubtreeStream(adaptor,"rule parameters");
        RewriteRuleSubtreeStream stream_actorDeclarations=new RewriteRuleSubtreeStream(adaptor,"rule actorDeclarations");
        RewriteRuleSubtreeStream stream_actorParameters=new RewriteRuleSubtreeStream(adaptor,"rule actorParameters");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:74:6: ( ( actorImport )* ACTOR ID '(' ( actorParameters )? ')' '(' (inputs= parameters )? '==>' (outputs= parameters )? ')' '{' ( actorDeclarations )? '}' EOF -> ACTOR ID ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS ( actorDeclarations )? ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:74:8: ( actorImport )* ACTOR ID '(' ( actorParameters )? ')' '(' (inputs= parameters )? '==>' (outputs= parameters )? ')' '{' ( actorDeclarations )? '}' EOF
            {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:74:8: ( actorImport )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==93) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:74:8: actorImport
            	    {
            	    pushFollow(FOLLOW_actorImport_in_actor184);
            	    actorImport23=actorImport();

            	    state._fsp--;

            	    stream_actorImport.add(actorImport23.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            ACTOR24=(Token)match(input,ACTOR,FOLLOW_ACTOR_in_actor187);  
            stream_ACTOR.add(ACTOR24);

            ID25=(Token)match(input,ID,FOLLOW_ID_in_actor189);  
            stream_ID.add(ID25);

            char_literal26=(Token)match(input,83,FOLLOW_83_in_actor191);  
            stream_83.add(char_literal26);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:74:34: ( actorParameters )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0>=121 && LA8_0<=124)) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:74:34: actorParameters
                    {
                    pushFollow(FOLLOW_actorParameters_in_actor193);
                    actorParameters27=actorParameters();

                    state._fsp--;

                    stream_actorParameters.add(actorParameters27.getTree());

                    }
                    break;

            }

            char_literal28=(Token)match(input,84,FOLLOW_84_in_actor196);  
            stream_84.add(char_literal28);

            char_literal29=(Token)match(input,83,FOLLOW_83_in_actor199);  
            stream_83.add(char_literal29);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:75:12: (inputs= parameters )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( ((LA9_0>=121 && LA9_0<=124)) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:75:12: inputs= parameters
                    {
                    pushFollow(FOLLOW_parameters_in_actor203);
                    inputs=parameters();

                    state._fsp--;

                    stream_parameters.add(inputs.getTree());

                    }
                    break;

            }

            string_literal30=(Token)match(input,85,FOLLOW_85_in_actor206);  
            stream_85.add(string_literal30);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:75:38: (outputs= parameters )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( ((LA10_0>=121 && LA10_0<=124)) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:75:38: outputs= parameters
                    {
                    pushFollow(FOLLOW_parameters_in_actor210);
                    outputs=parameters();

                    state._fsp--;

                    stream_parameters.add(outputs.getTree());

                    }
                    break;

            }

            char_literal31=(Token)match(input,84,FOLLOW_84_in_actor213);  
            stream_84.add(char_literal31);

            char_literal32=(Token)match(input,86,FOLLOW_86_in_actor215);  
            stream_86.add(char_literal32);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:76:2: ( actorDeclarations )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==ACTION||(LA11_0>=INITIALIZE && LA11_0<=PRIORITY)||LA11_0==SCHEDULE||LA11_0==88||LA11_0==92||(LA11_0>=121 && LA11_0<=124)) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:76:2: actorDeclarations
                    {
                    pushFollow(FOLLOW_actorDeclarations_in_actor218);
                    actorDeclarations33=actorDeclarations();

                    state._fsp--;

                    stream_actorDeclarations.add(actorDeclarations33.getTree());

                    }
                    break;

            }

            char_literal34=(Token)match(input,87,FOLLOW_87_in_actor221);  
            stream_87.add(char_literal34);

            EOF35=(Token)match(input,EOF,FOLLOW_EOF_in_actor223);  
            stream_EOF.add(EOF35);



            // AST REWRITE
            // elements: actorDeclarations, inputs, ID, outputs, actorParameters, ACTOR
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
            // 77:2: -> ACTOR ID ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS ( actorDeclarations )? )
            {
                adaptor.addChild(root_0, stream_ACTOR.nextNode());
                adaptor.addChild(root_0, stream_ID.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:78:2: ^( PARAMETERS ( actorParameters )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETERS, "PARAMETERS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:78:15: ( actorParameters )?
                if ( stream_actorParameters.hasNext() ) {
                    adaptor.addChild(root_1, stream_actorParameters.nextTree());

                }
                stream_actorParameters.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:79:2: ^( INPUTS ( $inputs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:79:11: ( $inputs)?
                if ( stream_inputs.hasNext() ) {
                    adaptor.addChild(root_1, stream_inputs.nextTree());

                }
                stream_inputs.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:80:2: ^( OUTPUTS ( $outputs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:80:12: ( $outputs)?
                if ( stream_outputs.hasNext() ) {
                    adaptor.addChild(root_1, stream_outputs.nextTree());

                }
                stream_outputs.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:81:2: ^( ACTOR_DECLS ( actorDeclarations )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ACTOR_DECLS, "ACTOR_DECLS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:81:16: ( actorDeclarations )?
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

    public static class actionOrInitialize_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionOrInitialize"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:83:1: actionOrInitialize : ( ACTION ( qualifiedIdent )? '(' ( actionInputs )? '==>' ( actionOutputs )? ')' ( actionGuards )? '{' ( varDecl )* ( statement )* '}' -> ^( ACTION ^( TAG ( qualifiedIdent )? ) ^( INPUTS ( actionInputs )? ) ^( OUTPUTS ( actionOutputs )? ) ^( GUARDS ( actionGuards )? ) ^( VARIABLES ( varDecl )* ) ^( STATEMENTS ( statement )* ) ) | INITIALIZE ( qualifiedIdent )? '(' ( actionOutputs )? ')' ( actionGuards )? '{' ( varDecl )* ( statement )* '}' -> ^( INITIALIZE ^( TAG ( qualifiedIdent )? ) INPUTS ^( OUTPUTS ( actionOutputs )? ) ^( GUARDS ( actionGuards )? ) ^( VARIABLES ( varDecl )* ) ^( STATEMENTS ( statement )* ) ) );
    public final C_ALParser.actionOrInitialize_return actionOrInitialize() throws RecognitionException {
        C_ALParser.actionOrInitialize_return retval = new C_ALParser.actionOrInitialize_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ACTION36=null;
        Token char_literal38=null;
        Token string_literal40=null;
        Token char_literal42=null;
        Token char_literal44=null;
        Token char_literal47=null;
        Token INITIALIZE48=null;
        Token char_literal50=null;
        Token char_literal52=null;
        Token char_literal54=null;
        Token char_literal57=null;
        C_ALParser.qualifiedIdent_return qualifiedIdent37 = null;

        C_ALParser.actionInputs_return actionInputs39 = null;

        C_ALParser.actionOutputs_return actionOutputs41 = null;

        C_ALParser.actionGuards_return actionGuards43 = null;

        C_ALParser.varDecl_return varDecl45 = null;

        C_ALParser.statement_return statement46 = null;

        C_ALParser.qualifiedIdent_return qualifiedIdent49 = null;

        C_ALParser.actionOutputs_return actionOutputs51 = null;

        C_ALParser.actionGuards_return actionGuards53 = null;

        C_ALParser.varDecl_return varDecl55 = null;

        C_ALParser.statement_return statement56 = null;


        Object ACTION36_tree=null;
        Object char_literal38_tree=null;
        Object string_literal40_tree=null;
        Object char_literal42_tree=null;
        Object char_literal44_tree=null;
        Object char_literal47_tree=null;
        Object INITIALIZE48_tree=null;
        Object char_literal50_tree=null;
        Object char_literal52_tree=null;
        Object char_literal54_tree=null;
        Object char_literal57_tree=null;
        RewriteRuleTokenStream stream_INITIALIZE=new RewriteRuleTokenStream(adaptor,"token INITIALIZE");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_ACTION=new RewriteRuleTokenStream(adaptor,"token ACTION");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleTokenStream stream_85=new RewriteRuleTokenStream(adaptor,"token 85");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        RewriteRuleSubtreeStream stream_actionOutputs=new RewriteRuleSubtreeStream(adaptor,"rule actionOutputs");
        RewriteRuleSubtreeStream stream_actionInputs=new RewriteRuleSubtreeStream(adaptor,"rule actionInputs");
        RewriteRuleSubtreeStream stream_actionGuards=new RewriteRuleSubtreeStream(adaptor,"rule actionGuards");
        RewriteRuleSubtreeStream stream_varDecl=new RewriteRuleSubtreeStream(adaptor,"rule varDecl");
        RewriteRuleSubtreeStream stream_qualifiedIdent=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedIdent");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:89:19: ( ACTION ( qualifiedIdent )? '(' ( actionInputs )? '==>' ( actionOutputs )? ')' ( actionGuards )? '{' ( varDecl )* ( statement )* '}' -> ^( ACTION ^( TAG ( qualifiedIdent )? ) ^( INPUTS ( actionInputs )? ) ^( OUTPUTS ( actionOutputs )? ) ^( GUARDS ( actionGuards )? ) ^( VARIABLES ( varDecl )* ) ^( STATEMENTS ( statement )* ) ) | INITIALIZE ( qualifiedIdent )? '(' ( actionOutputs )? ')' ( actionGuards )? '{' ( varDecl )* ( statement )* '}' -> ^( INITIALIZE ^( TAG ( qualifiedIdent )? ) INPUTS ^( OUTPUTS ( actionOutputs )? ) ^( GUARDS ( actionGuards )? ) ^( VARIABLES ( varDecl )* ) ^( STATEMENTS ( statement )* ) ) )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==ACTION) ) {
                alt23=1;
            }
            else if ( (LA23_0==INITIALIZE) ) {
                alt23=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:91:3: ACTION ( qualifiedIdent )? '(' ( actionInputs )? '==>' ( actionOutputs )? ')' ( actionGuards )? '{' ( varDecl )* ( statement )* '}'
                    {
                    ACTION36=(Token)match(input,ACTION,FOLLOW_ACTION_in_actionOrInitialize284);  
                    stream_ACTION.add(ACTION36);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:91:10: ( qualifiedIdent )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==ID) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:91:10: qualifiedIdent
                            {
                            pushFollow(FOLLOW_qualifiedIdent_in_actionOrInitialize286);
                            qualifiedIdent37=qualifiedIdent();

                            state._fsp--;

                            stream_qualifiedIdent.add(qualifiedIdent37.getTree());

                            }
                            break;

                    }

                    char_literal38=(Token)match(input,83,FOLLOW_83_in_actionOrInitialize289);  
                    stream_83.add(char_literal38);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:91:30: ( actionInputs )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==ID||LA13_0==80) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:91:30: actionInputs
                            {
                            pushFollow(FOLLOW_actionInputs_in_actionOrInitialize291);
                            actionInputs39=actionInputs();

                            state._fsp--;

                            stream_actionInputs.add(actionInputs39.getTree());

                            }
                            break;

                    }

                    string_literal40=(Token)match(input,85,FOLLOW_85_in_actionOrInitialize294);  
                    stream_85.add(string_literal40);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:91:50: ( actionOutputs )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==ID||LA14_0==80) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:91:50: actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actionOrInitialize296);
                            actionOutputs41=actionOutputs();

                            state._fsp--;

                            stream_actionOutputs.add(actionOutputs41.getTree());

                            }
                            break;

                    }

                    char_literal42=(Token)match(input,84,FOLLOW_84_in_actionOrInitialize299);  
                    stream_84.add(char_literal42);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:92:5: ( actionGuards )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==GUARD) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:92:5: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actionOrInitialize305);
                            actionGuards43=actionGuards();

                            state._fsp--;

                            stream_actionGuards.add(actionGuards43.getTree());

                            }
                            break;

                    }

                    char_literal44=(Token)match(input,86,FOLLOW_86_in_actionOrInitialize308);  
                    stream_86.add(char_literal44);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:93:7: ( varDecl )*
                    loop16:
                    do {
                        int alt16=2;
                        int LA16_0 = input.LA(1);

                        if ( (LA16_0==88||(LA16_0>=121 && LA16_0<=124)) ) {
                            alt16=1;
                        }


                        switch (alt16) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:93:7: varDecl
                    	    {
                    	    pushFollow(FOLLOW_varDecl_in_actionOrInitialize316);
                    	    varDecl45=varDecl();

                    	    state._fsp--;

                    	    stream_varDecl.add(varDecl45.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop16;
                        }
                    } while (true);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:94:7: ( statement )*
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( (LA17_0==ID) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:94:7: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_actionOrInitialize325);
                    	    statement46=statement();

                    	    state._fsp--;

                    	    stream_statement.add(statement46.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop17;
                        }
                    } while (true);

                    char_literal47=(Token)match(input,87,FOLLOW_87_in_actionOrInitialize332);  
                    stream_87.add(char_literal47);



                    // AST REWRITE
                    // elements: statement, actionOutputs, qualifiedIdent, actionGuards, varDecl, ACTION, actionInputs
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 95:9: -> ^( ACTION ^( TAG ( qualifiedIdent )? ) ^( INPUTS ( actionInputs )? ) ^( OUTPUTS ( actionOutputs )? ) ^( GUARDS ( actionGuards )? ) ^( VARIABLES ( varDecl )* ) ^( STATEMENTS ( statement )* ) )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:96:7: ^( ACTION ^( TAG ( qualifiedIdent )? ) ^( INPUTS ( actionInputs )? ) ^( OUTPUTS ( actionOutputs )? ) ^( GUARDS ( actionGuards )? ) ^( VARIABLES ( varDecl )* ) ^( STATEMENTS ( statement )* ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_ACTION.nextNode(), root_1);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:96:16: ^( TAG ( qualifiedIdent )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TAG, "TAG"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:96:22: ( qualifiedIdent )?
                        if ( stream_qualifiedIdent.hasNext() ) {
                            adaptor.addChild(root_2, stream_qualifiedIdent.nextTree());

                        }
                        stream_qualifiedIdent.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:96:39: ^( INPUTS ( actionInputs )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:96:48: ( actionInputs )?
                        if ( stream_actionInputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_actionInputs.nextTree());

                        }
                        stream_actionInputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:96:63: ^( OUTPUTS ( actionOutputs )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:96:73: ( actionOutputs )?
                        if ( stream_actionOutputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_actionOutputs.nextTree());

                        }
                        stream_actionOutputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:97:7: ^( GUARDS ( actionGuards )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:97:16: ( actionGuards )?
                        if ( stream_actionGuards.hasNext() ) {
                            adaptor.addChild(root_2, stream_actionGuards.nextTree());

                        }
                        stream_actionGuards.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:98:7: ^( VARIABLES ( varDecl )* )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLES, "VARIABLES"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:98:19: ( varDecl )*
                        while ( stream_varDecl.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDecl.nextTree());

                        }
                        stream_varDecl.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:99:7: ^( STATEMENTS ( statement )* )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:99:20: ( statement )*
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
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:102:3: INITIALIZE ( qualifiedIdent )? '(' ( actionOutputs )? ')' ( actionGuards )? '{' ( varDecl )* ( statement )* '}'
                    {
                    INITIALIZE48=(Token)match(input,INITIALIZE,FOLLOW_INITIALIZE_in_actionOrInitialize418);  
                    stream_INITIALIZE.add(INITIALIZE48);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:102:14: ( qualifiedIdent )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==ID) ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:102:14: qualifiedIdent
                            {
                            pushFollow(FOLLOW_qualifiedIdent_in_actionOrInitialize420);
                            qualifiedIdent49=qualifiedIdent();

                            state._fsp--;

                            stream_qualifiedIdent.add(qualifiedIdent49.getTree());

                            }
                            break;

                    }

                    char_literal50=(Token)match(input,83,FOLLOW_83_in_actionOrInitialize423);  
                    stream_83.add(char_literal50);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:102:34: ( actionOutputs )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==ID||LA19_0==80) ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:102:34: actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actionOrInitialize425);
                            actionOutputs51=actionOutputs();

                            state._fsp--;

                            stream_actionOutputs.add(actionOutputs51.getTree());

                            }
                            break;

                    }

                    char_literal52=(Token)match(input,84,FOLLOW_84_in_actionOrInitialize428);  
                    stream_84.add(char_literal52);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:103:5: ( actionGuards )?
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( (LA20_0==GUARD) ) {
                        alt20=1;
                    }
                    switch (alt20) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:103:5: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actionOrInitialize434);
                            actionGuards53=actionGuards();

                            state._fsp--;

                            stream_actionGuards.add(actionGuards53.getTree());

                            }
                            break;

                    }

                    char_literal54=(Token)match(input,86,FOLLOW_86_in_actionOrInitialize437);  
                    stream_86.add(char_literal54);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:104:7: ( varDecl )*
                    loop21:
                    do {
                        int alt21=2;
                        int LA21_0 = input.LA(1);

                        if ( (LA21_0==88||(LA21_0>=121 && LA21_0<=124)) ) {
                            alt21=1;
                        }


                        switch (alt21) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:104:7: varDecl
                    	    {
                    	    pushFollow(FOLLOW_varDecl_in_actionOrInitialize445);
                    	    varDecl55=varDecl();

                    	    state._fsp--;

                    	    stream_varDecl.add(varDecl55.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop21;
                        }
                    } while (true);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:105:7: ( statement )*
                    loop22:
                    do {
                        int alt22=2;
                        int LA22_0 = input.LA(1);

                        if ( (LA22_0==ID) ) {
                            alt22=1;
                        }


                        switch (alt22) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:105:7: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_actionOrInitialize454);
                    	    statement56=statement();

                    	    state._fsp--;

                    	    stream_statement.add(statement56.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop22;
                        }
                    } while (true);

                    char_literal57=(Token)match(input,87,FOLLOW_87_in_actionOrInitialize461);  
                    stream_87.add(char_literal57);



                    // AST REWRITE
                    // elements: statement, actionGuards, INITIALIZE, actionOutputs, varDecl, qualifiedIdent
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 106:9: -> ^( INITIALIZE ^( TAG ( qualifiedIdent )? ) INPUTS ^( OUTPUTS ( actionOutputs )? ) ^( GUARDS ( actionGuards )? ) ^( VARIABLES ( varDecl )* ) ^( STATEMENTS ( statement )* ) )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:107:7: ^( INITIALIZE ^( TAG ( qualifiedIdent )? ) INPUTS ^( OUTPUTS ( actionOutputs )? ) ^( GUARDS ( actionGuards )? ) ^( VARIABLES ( varDecl )* ) ^( STATEMENTS ( statement )* ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_INITIALIZE.nextNode(), root_1);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:107:20: ^( TAG ( qualifiedIdent )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TAG, "TAG"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:107:26: ( qualifiedIdent )?
                        if ( stream_qualifiedIdent.hasNext() ) {
                            adaptor.addChild(root_2, stream_qualifiedIdent.nextTree());

                        }
                        stream_qualifiedIdent.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        adaptor.addChild(root_1, (Object)adaptor.create(INPUTS, "INPUTS"));
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:107:50: ^( OUTPUTS ( actionOutputs )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:107:60: ( actionOutputs )?
                        if ( stream_actionOutputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_actionOutputs.nextTree());

                        }
                        stream_actionOutputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:108:7: ^( GUARDS ( actionGuards )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:108:16: ( actionGuards )?
                        if ( stream_actionGuards.hasNext() ) {
                            adaptor.addChild(root_2, stream_actionGuards.nextTree());

                        }
                        stream_actionGuards.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:109:7: ^( VARIABLES ( varDecl )* )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLES, "VARIABLES"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:109:19: ( varDecl )*
                        while ( stream_varDecl.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDecl.nextTree());

                        }
                        stream_varDecl.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:110:7: ^( STATEMENTS ( statement )* )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:110:20: ( statement )*
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
    // $ANTLR end "actionOrInitialize"

    public static class actorDeclaration_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actorDeclaration"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:118:1: actorDeclaration : ( actionOrInitialize | priorityOrder | 'const' typeDef ID '=' expression ';' -> ^( STATE_VAR typeDef ID NON_ASSIGNABLE expression ) | typeDef ID ( '=' expression ';' -> ^( STATE_VAR typeDef ID ASSIGNABLE expression ) | ';' -> ^( STATE_VAR typeDef ID ASSIGNABLE ) | '(' ( parameters )? ')' '{' ( varDecl )* 'return' expression ';' '}' -> ^( FUNCTION ID ^( PARAMETERS ( parameters )? ) ^( VARIABLES ( varDecl )* ) expression ) ) | 'void' ID '(' ( parameters )? ')' '{' ( varDecl )* ( statement )* '}' -> ^( PROCEDURE ID ^( PARAMETERS ( parameters )? ) ^( VARIABLES ( varDecl )* ) ^( STATEMENTS ( statement )* ) ) );
    public final C_ALParser.actorDeclaration_return actorDeclaration() throws RecognitionException {
        C_ALParser.actorDeclaration_return retval = new C_ALParser.actorDeclaration_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal60=null;
        Token ID62=null;
        Token char_literal63=null;
        Token char_literal65=null;
        Token ID67=null;
        Token char_literal68=null;
        Token char_literal70=null;
        Token char_literal71=null;
        Token char_literal72=null;
        Token char_literal74=null;
        Token char_literal75=null;
        Token string_literal77=null;
        Token char_literal79=null;
        Token char_literal80=null;
        Token string_literal81=null;
        Token ID82=null;
        Token char_literal83=null;
        Token char_literal85=null;
        Token char_literal86=null;
        Token char_literal89=null;
        C_ALParser.actionOrInitialize_return actionOrInitialize58 = null;

        C_ALParser.priorityOrder_return priorityOrder59 = null;

        C_ALParser.typeDef_return typeDef61 = null;

        C_ALParser.expression_return expression64 = null;

        C_ALParser.typeDef_return typeDef66 = null;

        C_ALParser.expression_return expression69 = null;

        C_ALParser.parameters_return parameters73 = null;

        C_ALParser.varDecl_return varDecl76 = null;

        C_ALParser.expression_return expression78 = null;

        C_ALParser.parameters_return parameters84 = null;

        C_ALParser.varDecl_return varDecl87 = null;

        C_ALParser.statement_return statement88 = null;


        Object string_literal60_tree=null;
        Object ID62_tree=null;
        Object char_literal63_tree=null;
        Object char_literal65_tree=null;
        Object ID67_tree=null;
        Object char_literal68_tree=null;
        Object char_literal70_tree=null;
        Object char_literal71_tree=null;
        Object char_literal72_tree=null;
        Object char_literal74_tree=null;
        Object char_literal75_tree=null;
        Object string_literal77_tree=null;
        Object char_literal79_tree=null;
        Object char_literal80_tree=null;
        Object string_literal81_tree=null;
        Object ID82_tree=null;
        Object char_literal83_tree=null;
        Object char_literal85_tree=null;
        Object char_literal86_tree=null;
        Object char_literal89_tree=null;
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleTokenStream stream_91=new RewriteRuleTokenStream(adaptor,"token 91");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleTokenStream stream_88=new RewriteRuleTokenStream(adaptor,"token 88");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_varDecl=new RewriteRuleSubtreeStream(adaptor,"rule varDecl");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        RewriteRuleSubtreeStream stream_parameters=new RewriteRuleSubtreeStream(adaptor,"rule parameters");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:118:17: ( actionOrInitialize | priorityOrder | 'const' typeDef ID '=' expression ';' -> ^( STATE_VAR typeDef ID NON_ASSIGNABLE expression ) | typeDef ID ( '=' expression ';' -> ^( STATE_VAR typeDef ID ASSIGNABLE expression ) | ';' -> ^( STATE_VAR typeDef ID ASSIGNABLE ) | '(' ( parameters )? ')' '{' ( varDecl )* 'return' expression ';' '}' -> ^( FUNCTION ID ^( PARAMETERS ( parameters )? ) ^( VARIABLES ( varDecl )* ) expression ) ) | 'void' ID '(' ( parameters )? ')' '{' ( varDecl )* ( statement )* '}' -> ^( PROCEDURE ID ^( PARAMETERS ( parameters )? ) ^( VARIABLES ( varDecl )* ) ^( STATEMENTS ( statement )* ) ) )
            int alt30=5;
            switch ( input.LA(1) ) {
            case ACTION:
            case INITIALIZE:
                {
                alt30=1;
                }
                break;
            case PRIORITY:
                {
                alt30=2;
                }
                break;
            case 88:
                {
                alt30=3;
                }
                break;
            case 121:
            case 122:
            case 123:
            case 124:
                {
                alt30=4;
                }
                break;
            case 92:
                {
                alt30=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }

            switch (alt30) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:119:3: actionOrInitialize
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_actionOrInitialize_in_actorDeclaration543);
                    actionOrInitialize58=actionOrInitialize();

                    state._fsp--;

                    adaptor.addChild(root_0, actionOrInitialize58.getTree());

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:120:3: priorityOrder
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_priorityOrder_in_actorDeclaration547);
                    priorityOrder59=priorityOrder();

                    state._fsp--;

                    adaptor.addChild(root_0, priorityOrder59.getTree());

                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:122:3: 'const' typeDef ID '=' expression ';'
                    {
                    string_literal60=(Token)match(input,88,FOLLOW_88_in_actorDeclaration552);  
                    stream_88.add(string_literal60);

                    pushFollow(FOLLOW_typeDef_in_actorDeclaration554);
                    typeDef61=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef61.getTree());
                    ID62=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration556);  
                    stream_ID.add(ID62);

                    char_literal63=(Token)match(input,89,FOLLOW_89_in_actorDeclaration558);  
                    stream_89.add(char_literal63);

                    pushFollow(FOLLOW_expression_in_actorDeclaration560);
                    expression64=expression();

                    state._fsp--;

                    stream_expression.add(expression64.getTree());
                    char_literal65=(Token)match(input,90,FOLLOW_90_in_actorDeclaration562);  
                    stream_90.add(char_literal65);



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
                    // 122:41: -> ^( STATE_VAR typeDef ID NON_ASSIGNABLE expression )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:122:44: ^( STATE_VAR typeDef ID NON_ASSIGNABLE expression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

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
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:125:3: typeDef ID ( '=' expression ';' -> ^( STATE_VAR typeDef ID ASSIGNABLE expression ) | ';' -> ^( STATE_VAR typeDef ID ASSIGNABLE ) | '(' ( parameters )? ')' '{' ( varDecl )* 'return' expression ';' '}' -> ^( FUNCTION ID ^( PARAMETERS ( parameters )? ) ^( VARIABLES ( varDecl )* ) expression ) )
                    {
                    pushFollow(FOLLOW_typeDef_in_actorDeclaration584);
                    typeDef66=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef66.getTree());
                    ID67=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration586);  
                    stream_ID.add(ID67);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:126:3: ( '=' expression ';' -> ^( STATE_VAR typeDef ID ASSIGNABLE expression ) | ';' -> ^( STATE_VAR typeDef ID ASSIGNABLE ) | '(' ( parameters )? ')' '{' ( varDecl )* 'return' expression ';' '}' -> ^( FUNCTION ID ^( PARAMETERS ( parameters )? ) ^( VARIABLES ( varDecl )* ) expression ) )
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
                    case 83:
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
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:126:5: '=' expression ';'
                            {
                            char_literal68=(Token)match(input,89,FOLLOW_89_in_actorDeclaration592);  
                            stream_89.add(char_literal68);

                            pushFollow(FOLLOW_expression_in_actorDeclaration594);
                            expression69=expression();

                            state._fsp--;

                            stream_expression.add(expression69.getTree());
                            char_literal70=(Token)match(input,90,FOLLOW_90_in_actorDeclaration596);  
                            stream_90.add(char_literal70);



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
                            // 126:24: -> ^( STATE_VAR typeDef ID ASSIGNABLE expression )
                            {
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:126:27: ^( STATE_VAR typeDef ID ASSIGNABLE expression )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

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
                        case 2 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:127:5: ';'
                            {
                            char_literal71=(Token)match(input,90,FOLLOW_90_in_actorDeclaration616);  
                            stream_90.add(char_literal71);



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
                            // 127:9: -> ^( STATE_VAR typeDef ID ASSIGNABLE )
                            {
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:127:12: ^( STATE_VAR typeDef ID ASSIGNABLE )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                adaptor.addChild(root_1, stream_typeDef.nextTree());
                                adaptor.addChild(root_1, stream_ID.nextNode());
                                adaptor.addChild(root_1, (Object)adaptor.create(ASSIGNABLE, "ASSIGNABLE"));

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;
                            }
                            break;
                        case 3 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:129:5: '(' ( parameters )? ')' '{' ( varDecl )* 'return' expression ';' '}'
                            {
                            char_literal72=(Token)match(input,83,FOLLOW_83_in_actorDeclaration635);  
                            stream_83.add(char_literal72);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:129:9: ( parameters )?
                            int alt24=2;
                            int LA24_0 = input.LA(1);

                            if ( ((LA24_0>=121 && LA24_0<=124)) ) {
                                alt24=1;
                            }
                            switch (alt24) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:129:9: parameters
                                    {
                                    pushFollow(FOLLOW_parameters_in_actorDeclaration637);
                                    parameters73=parameters();

                                    state._fsp--;

                                    stream_parameters.add(parameters73.getTree());

                                    }
                                    break;

                            }

                            char_literal74=(Token)match(input,84,FOLLOW_84_in_actorDeclaration640);  
                            stream_84.add(char_literal74);

                            char_literal75=(Token)match(input,86,FOLLOW_86_in_actorDeclaration642);  
                            stream_86.add(char_literal75);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:130:7: ( varDecl )*
                            loop25:
                            do {
                                int alt25=2;
                                int LA25_0 = input.LA(1);

                                if ( (LA25_0==88||(LA25_0>=121 && LA25_0<=124)) ) {
                                    alt25=1;
                                }


                                switch (alt25) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:130:7: varDecl
                            	    {
                            	    pushFollow(FOLLOW_varDecl_in_actorDeclaration650);
                            	    varDecl76=varDecl();

                            	    state._fsp--;

                            	    stream_varDecl.add(varDecl76.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop25;
                                }
                            } while (true);

                            string_literal77=(Token)match(input,91,FOLLOW_91_in_actorDeclaration659);  
                            stream_91.add(string_literal77);

                            pushFollow(FOLLOW_expression_in_actorDeclaration661);
                            expression78=expression();

                            state._fsp--;

                            stream_expression.add(expression78.getTree());
                            char_literal79=(Token)match(input,90,FOLLOW_90_in_actorDeclaration663);  
                            stream_90.add(char_literal79);

                            char_literal80=(Token)match(input,87,FOLLOW_87_in_actorDeclaration669);  
                            stream_87.add(char_literal80);



                            // AST REWRITE
                            // elements: parameters, expression, varDecl, ID
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 132:9: -> ^( FUNCTION ID ^( PARAMETERS ( parameters )? ) ^( VARIABLES ( varDecl )* ) expression )
                            {
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:132:12: ^( FUNCTION ID ^( PARAMETERS ( parameters )? ) ^( VARIABLES ( varDecl )* ) expression )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(FUNCTION, "FUNCTION"), root_1);

                                adaptor.addChild(root_1, stream_ID.nextNode());
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:132:26: ^( PARAMETERS ( parameters )? )
                                {
                                Object root_2 = (Object)adaptor.nil();
                                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETERS, "PARAMETERS"), root_2);

                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:132:39: ( parameters )?
                                if ( stream_parameters.hasNext() ) {
                                    adaptor.addChild(root_2, stream_parameters.nextTree());

                                }
                                stream_parameters.reset();

                                adaptor.addChild(root_1, root_2);
                                }
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:132:52: ^( VARIABLES ( varDecl )* )
                                {
                                Object root_2 = (Object)adaptor.nil();
                                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLES, "VARIABLES"), root_2);

                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:132:64: ( varDecl )*
                                while ( stream_varDecl.hasNext() ) {
                                    adaptor.addChild(root_2, stream_varDecl.nextTree());

                                }
                                stream_varDecl.reset();

                                adaptor.addChild(root_1, root_2);
                                }
                                adaptor.addChild(root_1, stream_expression.nextTree());

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;
                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:135:3: 'void' ID '(' ( parameters )? ')' '{' ( varDecl )* ( statement )* '}'
                    {
                    string_literal81=(Token)match(input,92,FOLLOW_92_in_actorDeclaration702);  
                    stream_92.add(string_literal81);

                    ID82=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration704);  
                    stream_ID.add(ID82);

                    char_literal83=(Token)match(input,83,FOLLOW_83_in_actorDeclaration706);  
                    stream_83.add(char_literal83);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:135:17: ( parameters )?
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( ((LA27_0>=121 && LA27_0<=124)) ) {
                        alt27=1;
                    }
                    switch (alt27) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:135:17: parameters
                            {
                            pushFollow(FOLLOW_parameters_in_actorDeclaration708);
                            parameters84=parameters();

                            state._fsp--;

                            stream_parameters.add(parameters84.getTree());

                            }
                            break;

                    }

                    char_literal85=(Token)match(input,84,FOLLOW_84_in_actorDeclaration711);  
                    stream_84.add(char_literal85);

                    char_literal86=(Token)match(input,86,FOLLOW_86_in_actorDeclaration713);  
                    stream_86.add(char_literal86);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:136:7: ( varDecl )*
                    loop28:
                    do {
                        int alt28=2;
                        int LA28_0 = input.LA(1);

                        if ( (LA28_0==88||(LA28_0>=121 && LA28_0<=124)) ) {
                            alt28=1;
                        }


                        switch (alt28) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:136:7: varDecl
                    	    {
                    	    pushFollow(FOLLOW_varDecl_in_actorDeclaration721);
                    	    varDecl87=varDecl();

                    	    state._fsp--;

                    	    stream_varDecl.add(varDecl87.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop28;
                        }
                    } while (true);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:137:7: ( statement )*
                    loop29:
                    do {
                        int alt29=2;
                        int LA29_0 = input.LA(1);

                        if ( (LA29_0==ID) ) {
                            alt29=1;
                        }


                        switch (alt29) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:137:7: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_actorDeclaration730);
                    	    statement88=statement();

                    	    state._fsp--;

                    	    stream_statement.add(statement88.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop29;
                        }
                    } while (true);

                    char_literal89=(Token)match(input,87,FOLLOW_87_in_actorDeclaration737);  
                    stream_87.add(char_literal89);



                    // AST REWRITE
                    // elements: varDecl, statement, parameters, ID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 138:9: -> ^( PROCEDURE ID ^( PARAMETERS ( parameters )? ) ^( VARIABLES ( varDecl )* ) ^( STATEMENTS ( statement )* ) )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:138:12: ^( PROCEDURE ID ^( PARAMETERS ( parameters )? ) ^( VARIABLES ( varDecl )* ) ^( STATEMENTS ( statement )* ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PROCEDURE, "PROCEDURE"), root_1);

                        adaptor.addChild(root_1, stream_ID.nextNode());
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:138:27: ^( PARAMETERS ( parameters )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETERS, "PARAMETERS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:138:40: ( parameters )?
                        if ( stream_parameters.hasNext() ) {
                            adaptor.addChild(root_2, stream_parameters.nextTree());

                        }
                        stream_parameters.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:138:53: ^( VARIABLES ( varDecl )* )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLES, "VARIABLES"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:138:65: ( varDecl )*
                        while ( stream_varDecl.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDecl.nextTree());

                        }
                        stream_varDecl.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:138:75: ^( STATEMENTS ( statement )* )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:138:88: ( statement )*
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:146:1: actorDeclarations : ( ( actorDeclaration )+ ( schedule ( actorDeclaration )* )? -> ( actorDeclaration )+ ( schedule )? | schedule ( actorDeclaration )* -> ( actorDeclaration )* schedule );
    public final C_ALParser.actorDeclarations_return actorDeclarations() throws RecognitionException {
        C_ALParser.actorDeclarations_return retval = new C_ALParser.actorDeclarations_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        C_ALParser.actorDeclaration_return actorDeclaration90 = null;

        C_ALParser.schedule_return schedule91 = null;

        C_ALParser.actorDeclaration_return actorDeclaration92 = null;

        C_ALParser.schedule_return schedule93 = null;

        C_ALParser.actorDeclaration_return actorDeclaration94 = null;


        RewriteRuleSubtreeStream stream_schedule=new RewriteRuleSubtreeStream(adaptor,"rule schedule");
        RewriteRuleSubtreeStream stream_actorDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule actorDeclaration");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:146:18: ( ( actorDeclaration )+ ( schedule ( actorDeclaration )* )? -> ( actorDeclaration )+ ( schedule )? | schedule ( actorDeclaration )* -> ( actorDeclaration )* schedule )
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==ACTION||(LA35_0>=INITIALIZE && LA35_0<=PRIORITY)||LA35_0==88||LA35_0==92||(LA35_0>=121 && LA35_0<=124)) ) {
                alt35=1;
            }
            else if ( (LA35_0==SCHEDULE) ) {
                alt35=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;
            }
            switch (alt35) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:146:20: ( actorDeclaration )+ ( schedule ( actorDeclaration )* )?
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:146:20: ( actorDeclaration )+
                    int cnt31=0;
                    loop31:
                    do {
                        int alt31=2;
                        int LA31_0 = input.LA(1);

                        if ( (LA31_0==ACTION||(LA31_0>=INITIALIZE && LA31_0<=PRIORITY)||LA31_0==88||LA31_0==92||(LA31_0>=121 && LA31_0<=124)) ) {
                            alt31=1;
                        }


                        switch (alt31) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:146:20: actorDeclaration
                    	    {
                    	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations779);
                    	    actorDeclaration90=actorDeclaration();

                    	    state._fsp--;

                    	    stream_actorDeclaration.add(actorDeclaration90.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt31 >= 1 ) break loop31;
                                EarlyExitException eee =
                                    new EarlyExitException(31, input);
                                throw eee;
                        }
                        cnt31++;
                    } while (true);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:146:38: ( schedule ( actorDeclaration )* )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==SCHEDULE) ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:146:39: schedule ( actorDeclaration )*
                            {
                            pushFollow(FOLLOW_schedule_in_actorDeclarations783);
                            schedule91=schedule();

                            state._fsp--;

                            stream_schedule.add(schedule91.getTree());
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:146:48: ( actorDeclaration )*
                            loop32:
                            do {
                                int alt32=2;
                                int LA32_0 = input.LA(1);

                                if ( (LA32_0==ACTION||(LA32_0>=INITIALIZE && LA32_0<=PRIORITY)||LA32_0==88||LA32_0==92||(LA32_0>=121 && LA32_0<=124)) ) {
                                    alt32=1;
                                }


                                switch (alt32) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:146:48: actorDeclaration
                            	    {
                            	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations785);
                            	    actorDeclaration92=actorDeclaration();

                            	    state._fsp--;

                            	    stream_actorDeclaration.add(actorDeclaration92.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop32;
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
                    // 146:68: -> ( actorDeclaration )+ ( schedule )?
                    {
                        if ( !(stream_actorDeclaration.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_actorDeclaration.hasNext() ) {
                            adaptor.addChild(root_0, stream_actorDeclaration.nextTree());

                        }
                        stream_actorDeclaration.reset();
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:146:89: ( schedule )?
                        if ( stream_schedule.hasNext() ) {
                            adaptor.addChild(root_0, stream_schedule.nextTree());

                        }
                        stream_schedule.reset();

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:147:5: schedule ( actorDeclaration )*
                    {
                    pushFollow(FOLLOW_schedule_in_actorDeclarations802);
                    schedule93=schedule();

                    state._fsp--;

                    stream_schedule.add(schedule93.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:147:14: ( actorDeclaration )*
                    loop34:
                    do {
                        int alt34=2;
                        int LA34_0 = input.LA(1);

                        if ( (LA34_0==ACTION||(LA34_0>=INITIALIZE && LA34_0<=PRIORITY)||LA34_0==88||LA34_0==92||(LA34_0>=121 && LA34_0<=124)) ) {
                            alt34=1;
                        }


                        switch (alt34) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:147:14: actorDeclaration
                    	    {
                    	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations804);
                    	    actorDeclaration94=actorDeclaration();

                    	    state._fsp--;

                    	    stream_actorDeclaration.add(actorDeclaration94.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop34;
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
                    // 147:32: -> ( actorDeclaration )* schedule
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:147:35: ( actorDeclaration )*
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:149:1: actorImport : 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) ;
    public final C_ALParser.actorImport_return actorImport() throws RecognitionException {
        C_ALParser.actorImport_return retval = new C_ALParser.actorImport_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal95=null;
        Token string_literal96=null;
        Token char_literal98=null;
        Token char_literal100=null;
        C_ALParser.qualifiedIdent_return qualifiedIdent97 = null;

        C_ALParser.qualifiedIdent_return qualifiedIdent99 = null;


        Object string_literal95_tree=null;
        Object string_literal96_tree=null;
        Object char_literal98_tree=null;
        Object char_literal100_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:152:12: ( 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:152:14: 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            {
            root_0 = (Object)adaptor.nil();

            string_literal95=(Token)match(input,93,FOLLOW_93_in_actorImport824); 
            string_literal95_tree = (Object)adaptor.create(string_literal95);
            adaptor.addChild(root_0, string_literal95_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:153:3: ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==94) ) {
                alt36=1;
            }
            else if ( (LA36_0==ID) ) {
                alt36=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }
            switch (alt36) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:153:4: 'all' qualifiedIdent ';'
                    {
                    string_literal96=(Token)match(input,94,FOLLOW_94_in_actorImport829); 
                    string_literal96_tree = (Object)adaptor.create(string_literal96);
                    adaptor.addChild(root_0, string_literal96_tree);

                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport831);
                    qualifiedIdent97=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent97.getTree());
                    char_literal98=(Token)match(input,90,FOLLOW_90_in_actorImport833); 
                    char_literal98_tree = (Object)adaptor.create(char_literal98);
                    adaptor.addChild(root_0, char_literal98_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:154:3: qualifiedIdent ';'
                    {
                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport839);
                    qualifiedIdent99=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent99.getTree());
                    char_literal100=(Token)match(input,90,FOLLOW_90_in_actorImport841); 
                    char_literal100_tree = (Object)adaptor.create(char_literal100);
                    adaptor.addChild(root_0, char_literal100_tree);

                     

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:156:1: actorParameter : typeDef ID ( '=' expression )? -> ^( VARIABLE typeDef ID ( expression )? ) ;
    public final C_ALParser.actorParameter_return actorParameter() throws RecognitionException {
        C_ALParser.actorParameter_return retval = new C_ALParser.actorParameter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID102=null;
        Token char_literal103=null;
        C_ALParser.typeDef_return typeDef101 = null;

        C_ALParser.expression_return expression104 = null;


        Object ID102_tree=null;
        Object char_literal103_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:158:15: ( typeDef ID ( '=' expression )? -> ^( VARIABLE typeDef ID ( expression )? ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:159:2: typeDef ID ( '=' expression )?
            {
            pushFollow(FOLLOW_typeDef_in_actorParameter856);
            typeDef101=typeDef();

            state._fsp--;

            stream_typeDef.add(typeDef101.getTree());
            ID102=(Token)match(input,ID,FOLLOW_ID_in_actorParameter858);  
            stream_ID.add(ID102);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:159:13: ( '=' expression )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==89) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:159:14: '=' expression
                    {
                    char_literal103=(Token)match(input,89,FOLLOW_89_in_actorParameter861);  
                    stream_89.add(char_literal103);

                    pushFollow(FOLLOW_expression_in_actorParameter863);
                    expression104=expression();

                    state._fsp--;

                    stream_expression.add(expression104.getTree());

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
            // 159:31: -> ^( VARIABLE typeDef ID ( expression )? )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:159:34: ^( VARIABLE typeDef ID ( expression )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLE, "VARIABLE"), root_1);

                adaptor.addChild(root_1, stream_typeDef.nextTree());
                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:159:56: ( expression )?
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:161:1: actorParameters : actorParameter ( ',' actorParameter )* -> ( actorParameter )+ ;
    public final C_ALParser.actorParameters_return actorParameters() throws RecognitionException {
        C_ALParser.actorParameters_return retval = new C_ALParser.actorParameters_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal106=null;
        C_ALParser.actorParameter_return actorParameter105 = null;

        C_ALParser.actorParameter_return actorParameter107 = null;


        Object char_literal106_tree=null;
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleSubtreeStream stream_actorParameter=new RewriteRuleSubtreeStream(adaptor,"rule actorParameter");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:161:16: ( actorParameter ( ',' actorParameter )* -> ( actorParameter )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:161:18: actorParameter ( ',' actorParameter )*
            {
            pushFollow(FOLLOW_actorParameter_in_actorParameters885);
            actorParameter105=actorParameter();

            state._fsp--;

            stream_actorParameter.add(actorParameter105.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:161:33: ( ',' actorParameter )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==82) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:161:34: ',' actorParameter
            	    {
            	    char_literal106=(Token)match(input,82,FOLLOW_82_in_actorParameters888);  
            	    stream_82.add(char_literal106);

            	    pushFollow(FOLLOW_actorParameter_in_actorParameters890);
            	    actorParameter107=actorParameter();

            	    state._fsp--;

            	    stream_actorParameter.add(actorParameter107.getTree());

            	    }
            	    break;

            	default :
            	    break loop38;
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
            // 161:55: -> ( actorParameter )+
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

    public static class expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:163:1: expression : un_expr ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr ) ;
    public final C_ALParser.expression_return expression() throws RecognitionException {
        C_ALParser.expression_return retval = new C_ALParser.expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        C_ALParser.un_expr_return un_expr108 = null;

        C_ALParser.bop_return bop109 = null;

        C_ALParser.un_expr_return un_expr110 = null;


        RewriteRuleSubtreeStream stream_bop=new RewriteRuleSubtreeStream(adaptor,"rule bop");
        RewriteRuleSubtreeStream stream_un_expr=new RewriteRuleSubtreeStream(adaptor,"rule un_expr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:168:11: ( un_expr ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:168:13: un_expr ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr )
            {
            pushFollow(FOLLOW_un_expr_in_expression911);
            un_expr108=un_expr();

            state._fsp--;

            stream_un_expr.add(un_expr108.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:169:3: ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr )
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( ((LA40_0>=PLUS && LA40_0<=DIV)||(LA40_0>=95 && LA40_0<=109)) ) {
                alt40=1;
            }
            else if ( (LA40_0==79||(LA40_0>=81 && LA40_0<=82)||(LA40_0>=84 && LA40_0<=87)||LA40_0==90) ) {
                alt40=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;
            }
            switch (alt40) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:169:4: ( bop un_expr )+
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:169:4: ( bop un_expr )+
                    int cnt39=0;
                    loop39:
                    do {
                        int alt39=2;
                        int LA39_0 = input.LA(1);

                        if ( ((LA39_0>=PLUS && LA39_0<=DIV)||(LA39_0>=95 && LA39_0<=109)) ) {
                            alt39=1;
                        }


                        switch (alt39) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:169:5: bop un_expr
                    	    {
                    	    pushFollow(FOLLOW_bop_in_expression917);
                    	    bop109=bop();

                    	    state._fsp--;

                    	    stream_bop.add(bop109.getTree());
                    	    pushFollow(FOLLOW_un_expr_in_expression919);
                    	    un_expr110=un_expr();

                    	    state._fsp--;

                    	    stream_un_expr.add(un_expr110.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt39 >= 1 ) break loop39;
                                EarlyExitException eee =
                                    new EarlyExitException(39, input);
                                throw eee;
                        }
                        cnt39++;
                    } while (true);



                    // AST REWRITE
                    // elements: bop, un_expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 169:19: -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:169:22: ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BINARY, "EXPR_BINARY"), root_1);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:169:36: ^( EXPR ( un_expr )+ )
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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:169:53: ^( OP ( bop )+ )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:170:5: 
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
                    // 170:5: -> un_expr
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:172:1: bop : ( '||' -> LOGIC_OR | '&&' -> LOGIC_AND | '|' -> BITOR | '^' -> BITXOR | '&' -> BITAND | '==' -> EQ | '!=' -> NE | '<' -> LT | '>' -> GT | '<=' -> LE | '>=' -> GE | '<<' -> SHIFT_LEFT | '>>' -> SHIFT_RIGHT | PLUS -> PLUS | MINUS -> MINUS | DIV -> DIV | '%' -> MOD | TIMES -> TIMES | '**' -> EXP );
    public final C_ALParser.bop_return bop() throws RecognitionException {
        C_ALParser.bop_return retval = new C_ALParser.bop_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal111=null;
        Token string_literal112=null;
        Token char_literal113=null;
        Token char_literal114=null;
        Token char_literal115=null;
        Token string_literal116=null;
        Token string_literal117=null;
        Token char_literal118=null;
        Token char_literal119=null;
        Token string_literal120=null;
        Token string_literal121=null;
        Token string_literal122=null;
        Token string_literal123=null;
        Token PLUS124=null;
        Token MINUS125=null;
        Token DIV126=null;
        Token char_literal127=null;
        Token TIMES128=null;
        Token string_literal129=null;

        Object string_literal111_tree=null;
        Object string_literal112_tree=null;
        Object char_literal113_tree=null;
        Object char_literal114_tree=null;
        Object char_literal115_tree=null;
        Object string_literal116_tree=null;
        Object string_literal117_tree=null;
        Object char_literal118_tree=null;
        Object char_literal119_tree=null;
        Object string_literal120_tree=null;
        Object string_literal121_tree=null;
        Object string_literal122_tree=null;
        Object string_literal123_tree=null;
        Object PLUS124_tree=null;
        Object MINUS125_tree=null;
        Object DIV126_tree=null;
        Object char_literal127_tree=null;
        Object TIMES128_tree=null;
        Object string_literal129_tree=null;
        RewriteRuleTokenStream stream_98=new RewriteRuleTokenStream(adaptor,"token 98");
        RewriteRuleTokenStream stream_97=new RewriteRuleTokenStream(adaptor,"token 97");
        RewriteRuleTokenStream stream_96=new RewriteRuleTokenStream(adaptor,"token 96");
        RewriteRuleTokenStream stream_95=new RewriteRuleTokenStream(adaptor,"token 95");
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

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:172:4: ( '||' -> LOGIC_OR | '&&' -> LOGIC_AND | '|' -> BITOR | '^' -> BITXOR | '&' -> BITAND | '==' -> EQ | '!=' -> NE | '<' -> LT | '>' -> GT | '<=' -> LE | '>=' -> GE | '<<' -> SHIFT_LEFT | '>>' -> SHIFT_RIGHT | PLUS -> PLUS | MINUS -> MINUS | DIV -> DIV | '%' -> MOD | TIMES -> TIMES | '**' -> EXP )
            int alt41=19;
            alt41 = dfa41.predict(input);
            switch (alt41) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:172:6: '||'
                    {
                    string_literal111=(Token)match(input,95,FOLLOW_95_in_bop957);  
                    stream_95.add(string_literal111);



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
                    // 172:11: -> LOGIC_OR
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(LOGIC_OR, "LOGIC_OR"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:173:3: '&&'
                    {
                    string_literal112=(Token)match(input,96,FOLLOW_96_in_bop965);  
                    stream_96.add(string_literal112);



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
                    // 173:8: -> LOGIC_AND
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(LOGIC_AND, "LOGIC_AND"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:174:3: '|'
                    {
                    char_literal113=(Token)match(input,97,FOLLOW_97_in_bop973);  
                    stream_97.add(char_literal113);



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
                    // 174:7: -> BITOR
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(BITOR, "BITOR"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:175:3: '^'
                    {
                    char_literal114=(Token)match(input,98,FOLLOW_98_in_bop981);  
                    stream_98.add(char_literal114);



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
                    // 175:7: -> BITXOR
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(BITXOR, "BITXOR"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:176:3: '&'
                    {
                    char_literal115=(Token)match(input,99,FOLLOW_99_in_bop989);  
                    stream_99.add(char_literal115);



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
                    // 176:7: -> BITAND
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(BITAND, "BITAND"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 6 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:177:3: '=='
                    {
                    string_literal116=(Token)match(input,100,FOLLOW_100_in_bop997);  
                    stream_100.add(string_literal116);



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
                    // 177:8: -> EQ
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(EQ, "EQ"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 7 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:177:16: '!='
                    {
                    string_literal117=(Token)match(input,101,FOLLOW_101_in_bop1005);  
                    stream_101.add(string_literal117);



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
                    // 177:21: -> NE
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(NE, "NE"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 8 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:178:3: '<'
                    {
                    char_literal118=(Token)match(input,102,FOLLOW_102_in_bop1013);  
                    stream_102.add(char_literal118);



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
                    // 178:7: -> LT
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(LT, "LT"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 9 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:178:15: '>'
                    {
                    char_literal119=(Token)match(input,103,FOLLOW_103_in_bop1021);  
                    stream_103.add(char_literal119);



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
                    // 178:19: -> GT
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(GT, "GT"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 10 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:178:27: '<='
                    {
                    string_literal120=(Token)match(input,104,FOLLOW_104_in_bop1029);  
                    stream_104.add(string_literal120);



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
                    // 178:32: -> LE
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(LE, "LE"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 11 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:178:40: '>='
                    {
                    string_literal121=(Token)match(input,105,FOLLOW_105_in_bop1037);  
                    stream_105.add(string_literal121);



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
                    // 178:45: -> GE
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(GE, "GE"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 12 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:179:3: '<<'
                    {
                    string_literal122=(Token)match(input,106,FOLLOW_106_in_bop1045);  
                    stream_106.add(string_literal122);



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
                    // 179:8: -> SHIFT_LEFT
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(SHIFT_LEFT, "SHIFT_LEFT"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 13 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:179:24: '>>'
                    {
                    string_literal123=(Token)match(input,107,FOLLOW_107_in_bop1053);  
                    stream_107.add(string_literal123);



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
                    // 179:29: -> SHIFT_RIGHT
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(SHIFT_RIGHT, "SHIFT_RIGHT"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 14 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:180:3: PLUS
                    {
                    PLUS124=(Token)match(input,PLUS,FOLLOW_PLUS_in_bop1061);  
                    stream_PLUS.add(PLUS124);



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
                    // 180:8: -> PLUS
                    {
                        adaptor.addChild(root_0, stream_PLUS.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 15 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:180:18: MINUS
                    {
                    MINUS125=(Token)match(input,MINUS,FOLLOW_MINUS_in_bop1069);  
                    stream_MINUS.add(MINUS125);



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
                    // 180:24: -> MINUS
                    {
                        adaptor.addChild(root_0, stream_MINUS.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 16 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:181:3: DIV
                    {
                    DIV126=(Token)match(input,DIV,FOLLOW_DIV_in_bop1077);  
                    stream_DIV.add(DIV126);



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
                    // 181:7: -> DIV
                    {
                        adaptor.addChild(root_0, stream_DIV.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 17 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:181:16: '%'
                    {
                    char_literal127=(Token)match(input,108,FOLLOW_108_in_bop1085);  
                    stream_108.add(char_literal127);



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
                    // 181:20: -> MOD
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(MOD, "MOD"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 18 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:181:29: TIMES
                    {
                    TIMES128=(Token)match(input,TIMES,FOLLOW_TIMES_in_bop1093);  
                    stream_TIMES.add(TIMES128);



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
                    // 181:35: -> TIMES
                    {
                        adaptor.addChild(root_0, stream_TIMES.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 19 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:182:3: '**'
                    {
                    string_literal129=(Token)match(input,109,FOLLOW_109_in_bop1101);  
                    stream_109.add(string_literal129);



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
                    // 182:8: -> EXP
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:184:1: un_expr : ( postfix_expression -> postfix_expression | (op= ( MINUS -> MINUS ) | op= ( '~' -> BITNOT ) | op= ( '!' -> LOGIC_NOT ) | op= ( '#' -> NUM_ELTS ) ) un_expr -> ^( EXPR_UNARY $op un_expr ) );
    public final C_ALParser.un_expr_return un_expr() throws RecognitionException {
        C_ALParser.un_expr_return retval = new C_ALParser.un_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        Token MINUS131=null;
        Token char_literal132=null;
        Token char_literal133=null;
        Token char_literal134=null;
        C_ALParser.postfix_expression_return postfix_expression130 = null;

        C_ALParser.un_expr_return un_expr135 = null;


        Object op_tree=null;
        Object MINUS131_tree=null;
        Object char_literal132_tree=null;
        Object char_literal133_tree=null;
        Object char_literal134_tree=null;
        RewriteRuleTokenStream stream_112=new RewriteRuleTokenStream(adaptor,"token 112");
        RewriteRuleTokenStream stream_110=new RewriteRuleTokenStream(adaptor,"token 110");
        RewriteRuleTokenStream stream_MINUS=new RewriteRuleTokenStream(adaptor,"token MINUS");
        RewriteRuleTokenStream stream_111=new RewriteRuleTokenStream(adaptor,"token 111");
        RewriteRuleSubtreeStream stream_postfix_expression=new RewriteRuleSubtreeStream(adaptor,"rule postfix_expression");
        RewriteRuleSubtreeStream stream_un_expr=new RewriteRuleSubtreeStream(adaptor,"rule un_expr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:184:8: ( postfix_expression -> postfix_expression | (op= ( MINUS -> MINUS ) | op= ( '~' -> BITNOT ) | op= ( '!' -> LOGIC_NOT ) | op= ( '#' -> NUM_ELTS ) ) un_expr -> ^( EXPR_UNARY $op un_expr ) )
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==ID||(LA43_0>=FLOAT && LA43_0<=INTEGER)||LA43_0==STRING||LA43_0==83||LA43_0==86||(LA43_0>=113 && LA43_0<=114)) ) {
                alt43=1;
            }
            else if ( (LA43_0==MINUS||(LA43_0>=110 && LA43_0<=112)) ) {
                alt43=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 43, 0, input);

                throw nvae;
            }
            switch (alt43) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:184:10: postfix_expression
                    {
                    pushFollow(FOLLOW_postfix_expression_in_un_expr1112);
                    postfix_expression130=postfix_expression();

                    state._fsp--;

                    stream_postfix_expression.add(postfix_expression130.getTree());


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
                    // 184:29: -> postfix_expression
                    {
                        adaptor.addChild(root_0, stream_postfix_expression.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:185:5: (op= ( MINUS -> MINUS ) | op= ( '~' -> BITNOT ) | op= ( '!' -> LOGIC_NOT ) | op= ( '#' -> NUM_ELTS ) ) un_expr
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:185:5: (op= ( MINUS -> MINUS ) | op= ( '~' -> BITNOT ) | op= ( '!' -> LOGIC_NOT ) | op= ( '#' -> NUM_ELTS ) )
                    int alt42=4;
                    switch ( input.LA(1) ) {
                    case MINUS:
                        {
                        alt42=1;
                        }
                        break;
                    case 110:
                        {
                        alt42=2;
                        }
                        break;
                    case 111:
                        {
                        alt42=3;
                        }
                        break;
                    case 112:
                        {
                        alt42=4;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 42, 0, input);

                        throw nvae;
                    }

                    switch (alt42) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:185:6: op= ( MINUS -> MINUS )
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:185:9: ( MINUS -> MINUS )
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:185:10: MINUS
                            {
                            MINUS131=(Token)match(input,MINUS,FOLLOW_MINUS_in_un_expr1126);  
                            stream_MINUS.add(MINUS131);



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
                            // 185:16: -> MINUS
                            {
                                adaptor.addChild(root_0, stream_MINUS.nextNode());

                            }

                            retval.tree = root_0;
                            }


                            }
                            break;
                        case 2 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:186:7: op= ( '~' -> BITNOT )
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:186:10: ( '~' -> BITNOT )
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:186:11: '~'
                            {
                            char_literal132=(Token)match(input,110,FOLLOW_110_in_un_expr1142);  
                            stream_110.add(char_literal132);



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
                            // 186:15: -> BITNOT
                            {
                                adaptor.addChild(root_0, (Object)adaptor.create(BITNOT, "BITNOT"));

                            }

                            retval.tree = root_0;
                            }


                            }
                            break;
                        case 3 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:187:7: op= ( '!' -> LOGIC_NOT )
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:187:10: ( '!' -> LOGIC_NOT )
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:187:11: '!'
                            {
                            char_literal133=(Token)match(input,111,FOLLOW_111_in_un_expr1158);  
                            stream_111.add(char_literal133);



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
                            // 187:15: -> LOGIC_NOT
                            {
                                adaptor.addChild(root_0, (Object)adaptor.create(LOGIC_NOT, "LOGIC_NOT"));

                            }

                            retval.tree = root_0;
                            }


                            }
                            break;
                        case 4 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:188:7: op= ( '#' -> NUM_ELTS )
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:188:10: ( '#' -> NUM_ELTS )
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:188:11: '#'
                            {
                            char_literal134=(Token)match(input,112,FOLLOW_112_in_un_expr1174);  
                            stream_112.add(char_literal134);



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
                            // 188:15: -> NUM_ELTS
                            {
                                adaptor.addChild(root_0, (Object)adaptor.create(NUM_ELTS, "NUM_ELTS"));

                            }

                            retval.tree = root_0;
                            }


                            }
                            break;

                    }

                    pushFollow(FOLLOW_un_expr_in_un_expr1182);
                    un_expr135=un_expr();

                    state._fsp--;

                    stream_un_expr.add(un_expr135.getTree());


                    // AST REWRITE
                    // elements: un_expr, op
                    // token labels: op
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_op=new RewriteRuleTokenStream(adaptor,"token op",op);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 188:37: -> ^( EXPR_UNARY $op un_expr )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:188:40: ^( EXPR_UNARY $op un_expr )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_UNARY, "EXPR_UNARY"), root_1);

                        adaptor.addChild(root_1, stream_op.nextNode());
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

    public static class postfix_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "postfix_expression"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:190:1: postfix_expression : ( '{' e= expressions ( ':' g= expressionGenerators )? '}' -> ^( EXPR_LIST $e ( $g)? ) | constant -> constant | '(' expression ')' -> expression | var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expression ']' )+ -> ^( EXPR_IDX $var ( expression )+ ) | -> ^( EXPR_VAR $var) ) );
    public final C_ALParser.postfix_expression_return postfix_expression() throws RecognitionException {
        C_ALParser.postfix_expression_return retval = new C_ALParser.postfix_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token var=null;
        Token char_literal136=null;
        Token char_literal137=null;
        Token char_literal138=null;
        Token char_literal140=null;
        Token char_literal142=null;
        Token char_literal143=null;
        Token char_literal145=null;
        Token char_literal146=null;
        Token char_literal148=null;
        C_ALParser.expressions_return e = null;

        C_ALParser.expressionGenerators_return g = null;

        C_ALParser.constant_return constant139 = null;

        C_ALParser.expression_return expression141 = null;

        C_ALParser.expressions_return expressions144 = null;

        C_ALParser.expression_return expression147 = null;


        Object var_tree=null;
        Object char_literal136_tree=null;
        Object char_literal137_tree=null;
        Object char_literal138_tree=null;
        Object char_literal140_tree=null;
        Object char_literal142_tree=null;
        Object char_literal143_tree=null;
        Object char_literal145_tree=null;
        Object char_literal146_tree=null;
        Object char_literal148_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_80=new RewriteRuleTokenStream(adaptor,"token 80");
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_constant=new RewriteRuleSubtreeStream(adaptor,"rule constant");
        RewriteRuleSubtreeStream stream_expressionGenerators=new RewriteRuleSubtreeStream(adaptor,"rule expressionGenerators");
        RewriteRuleSubtreeStream stream_expressions=new RewriteRuleSubtreeStream(adaptor,"rule expressions");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:190:19: ( '{' e= expressions ( ':' g= expressionGenerators )? '}' -> ^( EXPR_LIST $e ( $g)? ) | constant -> constant | '(' expression ')' -> expression | var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expression ']' )+ -> ^( EXPR_IDX $var ( expression )+ ) | -> ^( EXPR_VAR $var) ) )
            int alt48=4;
            switch ( input.LA(1) ) {
            case 86:
                {
                alt48=1;
                }
                break;
            case FLOAT:
            case INTEGER:
            case STRING:
            case 113:
            case 114:
                {
                alt48=2;
                }
                break;
            case 83:
                {
                alt48=3;
                }
                break;
            case ID:
                {
                alt48=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 48, 0, input);

                throw nvae;
            }

            switch (alt48) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:191:3: '{' e= expressions ( ':' g= expressionGenerators )? '}'
                    {
                    char_literal136=(Token)match(input,86,FOLLOW_86_in_postfix_expression1202);  
                    stream_86.add(char_literal136);

                    pushFollow(FOLLOW_expressions_in_postfix_expression1206);
                    e=expressions();

                    state._fsp--;

                    stream_expressions.add(e.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:191:21: ( ':' g= expressionGenerators )?
                    int alt44=2;
                    int LA44_0 = input.LA(1);

                    if ( (LA44_0==79) ) {
                        alt44=1;
                    }
                    switch (alt44) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:191:22: ':' g= expressionGenerators
                            {
                            char_literal137=(Token)match(input,79,FOLLOW_79_in_postfix_expression1209);  
                            stream_79.add(char_literal137);

                            pushFollow(FOLLOW_expressionGenerators_in_postfix_expression1213);
                            g=expressionGenerators();

                            state._fsp--;

                            stream_expressionGenerators.add(g.getTree());

                            }
                            break;

                    }

                    char_literal138=(Token)match(input,87,FOLLOW_87_in_postfix_expression1217);  
                    stream_87.add(char_literal138);



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
                    // 191:55: -> ^( EXPR_LIST $e ( $g)? )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:191:58: ^( EXPR_LIST $e ( $g)? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_LIST, "EXPR_LIST"), root_1);

                        adaptor.addChild(root_1, stream_e.nextTree());
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:191:73: ( $g)?
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:193:6: constant
                    {
                    pushFollow(FOLLOW_constant_in_postfix_expression1236);
                    constant139=constant();

                    state._fsp--;

                    stream_constant.add(constant139.getTree());


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
                    // 193:15: -> constant
                    {
                        adaptor.addChild(root_0, stream_constant.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:194:3: '(' expression ')'
                    {
                    char_literal140=(Token)match(input,83,FOLLOW_83_in_postfix_expression1244);  
                    stream_83.add(char_literal140);

                    pushFollow(FOLLOW_expression_in_postfix_expression1246);
                    expression141=expression();

                    state._fsp--;

                    stream_expression.add(expression141.getTree());
                    char_literal142=(Token)match(input,84,FOLLOW_84_in_postfix_expression1248);  
                    stream_84.add(char_literal142);



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
                    // 194:22: -> expression
                    {
                        adaptor.addChild(root_0, stream_expression.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:195:3: var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expression ']' )+ -> ^( EXPR_IDX $var ( expression )+ ) | -> ^( EXPR_VAR $var) )
                    {
                    var=(Token)match(input,ID,FOLLOW_ID_in_postfix_expression1258);  
                    stream_ID.add(var);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:195:10: ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expression ']' )+ -> ^( EXPR_IDX $var ( expression )+ ) | -> ^( EXPR_VAR $var) )
                    int alt47=3;
                    switch ( input.LA(1) ) {
                    case 83:
                        {
                        alt47=1;
                        }
                        break;
                    case 80:
                        {
                        alt47=2;
                        }
                        break;
                    case PLUS:
                    case MINUS:
                    case TIMES:
                    case DIV:
                    case 79:
                    case 81:
                    case 82:
                    case 84:
                    case 85:
                    case 86:
                    case 87:
                    case 90:
                    case 95:
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
                        {
                        alt47=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 47, 0, input);

                        throw nvae;
                    }

                    switch (alt47) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:196:5: '(' ( expressions )? ')'
                            {
                            char_literal143=(Token)match(input,83,FOLLOW_83_in_postfix_expression1266);  
                            stream_83.add(char_literal143);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:196:9: ( expressions )?
                            int alt45=2;
                            int LA45_0 = input.LA(1);

                            if ( (LA45_0==MINUS||LA45_0==ID||(LA45_0>=FLOAT && LA45_0<=INTEGER)||LA45_0==STRING||LA45_0==83||LA45_0==86||(LA45_0>=110 && LA45_0<=114)) ) {
                                alt45=1;
                            }
                            switch (alt45) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:196:9: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_postfix_expression1268);
                                    expressions144=expressions();

                                    state._fsp--;

                                    stream_expressions.add(expressions144.getTree());

                                    }
                                    break;

                            }

                            char_literal145=(Token)match(input,84,FOLLOW_84_in_postfix_expression1271);  
                            stream_84.add(char_literal145);



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
                            // 196:26: -> ^( EXPR_CALL $var ( expressions )? )
                            {
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:196:29: ^( EXPR_CALL $var ( expressions )? )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_CALL, "EXPR_CALL"), root_1);

                                adaptor.addChild(root_1, stream_var.nextNode());
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:196:46: ( expressions )?
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
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:197:6: ( '[' expression ']' )+
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:197:6: ( '[' expression ']' )+
                            int cnt46=0;
                            loop46:
                            do {
                                int alt46=2;
                                int LA46_0 = input.LA(1);

                                if ( (LA46_0==80) ) {
                                    alt46=1;
                                }


                                switch (alt46) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:197:7: '[' expression ']'
                            	    {
                            	    char_literal146=(Token)match(input,80,FOLLOW_80_in_postfix_expression1291);  
                            	    stream_80.add(char_literal146);

                            	    pushFollow(FOLLOW_expression_in_postfix_expression1293);
                            	    expression147=expression();

                            	    state._fsp--;

                            	    stream_expression.add(expression147.getTree());
                            	    char_literal148=(Token)match(input,81,FOLLOW_81_in_postfix_expression1295);  
                            	    stream_81.add(char_literal148);


                            	    }
                            	    break;

                            	default :
                            	    if ( cnt46 >= 1 ) break loop46;
                                        EarlyExitException eee =
                                            new EarlyExitException(46, input);
                                        throw eee;
                                }
                                cnt46++;
                            } while (true);



                            // AST REWRITE
                            // elements: var, expression
                            // token labels: var
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleTokenStream stream_var=new RewriteRuleTokenStream(adaptor,"token var",var);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 197:28: -> ^( EXPR_IDX $var ( expression )+ )
                            {
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:197:31: ^( EXPR_IDX $var ( expression )+ )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_IDX, "EXPR_IDX"), root_1);

                                adaptor.addChild(root_1, stream_var.nextNode());
                                if ( !(stream_expression.hasNext()) ) {
                                    throw new RewriteEarlyExitException();
                                }
                                while ( stream_expression.hasNext() ) {
                                    adaptor.addChild(root_1, stream_expression.nextTree());

                                }
                                stream_expression.reset();

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;
                            }
                            break;
                        case 3 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:198:5: 
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
                            // 198:5: -> ^( EXPR_VAR $var)
                            {
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:198:8: ^( EXPR_VAR $var)
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:200:1: constant : ( 'true' -> ^( EXPR_BOOL 'true' ) | 'false' -> ^( EXPR_BOOL 'false' ) | FLOAT -> ^( EXPR_FLOAT FLOAT ) | INTEGER -> ^( EXPR_INT INTEGER ) | STRING -> ^( EXPR_STRING STRING ) );
    public final C_ALParser.constant_return constant() throws RecognitionException {
        C_ALParser.constant_return retval = new C_ALParser.constant_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal149=null;
        Token string_literal150=null;
        Token FLOAT151=null;
        Token INTEGER152=null;
        Token STRING153=null;

        Object string_literal149_tree=null;
        Object string_literal150_tree=null;
        Object FLOAT151_tree=null;
        Object INTEGER152_tree=null;
        Object STRING153_tree=null;
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_114=new RewriteRuleTokenStream(adaptor,"token 114");
        RewriteRuleTokenStream stream_FLOAT=new RewriteRuleTokenStream(adaptor,"token FLOAT");
        RewriteRuleTokenStream stream_113=new RewriteRuleTokenStream(adaptor,"token 113");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:200:9: ( 'true' -> ^( EXPR_BOOL 'true' ) | 'false' -> ^( EXPR_BOOL 'false' ) | FLOAT -> ^( EXPR_FLOAT FLOAT ) | INTEGER -> ^( EXPR_INT INTEGER ) | STRING -> ^( EXPR_STRING STRING ) )
            int alt49=5;
            switch ( input.LA(1) ) {
            case 113:
                {
                alt49=1;
                }
                break;
            case 114:
                {
                alt49=2;
                }
                break;
            case FLOAT:
                {
                alt49=3;
                }
                break;
            case INTEGER:
                {
                alt49=4;
                }
                break;
            case STRING:
                {
                alt49=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;
            }

            switch (alt49) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:201:3: 'true'
                    {
                    string_literal149=(Token)match(input,113,FOLLOW_113_in_constant1332);  
                    stream_113.add(string_literal149);



                    // AST REWRITE
                    // elements: 113
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 201:10: -> ^( EXPR_BOOL 'true' )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:201:13: ^( EXPR_BOOL 'true' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BOOL, "EXPR_BOOL"), root_1);

                        adaptor.addChild(root_1, stream_113.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:202:3: 'false'
                    {
                    string_literal150=(Token)match(input,114,FOLLOW_114_in_constant1344);  
                    stream_114.add(string_literal150);



                    // AST REWRITE
                    // elements: 114
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 202:11: -> ^( EXPR_BOOL 'false' )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:202:14: ^( EXPR_BOOL 'false' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BOOL, "EXPR_BOOL"), root_1);

                        adaptor.addChild(root_1, stream_114.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:203:3: FLOAT
                    {
                    FLOAT151=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_constant1356);  
                    stream_FLOAT.add(FLOAT151);



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
                    // 203:9: -> ^( EXPR_FLOAT FLOAT )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:203:12: ^( EXPR_FLOAT FLOAT )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:204:3: INTEGER
                    {
                    INTEGER152=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_constant1368);  
                    stream_INTEGER.add(INTEGER152);



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
                    // 204:11: -> ^( EXPR_INT INTEGER )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:204:14: ^( EXPR_INT INTEGER )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:205:3: STRING
                    {
                    STRING153=(Token)match(input,STRING,FOLLOW_STRING_in_constant1380);  
                    stream_STRING.add(STRING153);



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
                    // 205:10: -> ^( EXPR_STRING STRING )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:205:13: ^( EXPR_STRING STRING )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:207:1: expressionGenerator : 'for' parameter 'in' expression ;
    public final C_ALParser.expressionGenerator_return expressionGenerator() throws RecognitionException {
        C_ALParser.expressionGenerator_return retval = new C_ALParser.expressionGenerator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal154=null;
        Token string_literal156=null;
        C_ALParser.parameter_return parameter155 = null;

        C_ALParser.expression_return expression157 = null;


        Object string_literal154_tree=null;
        Object string_literal156_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:207:20: ( 'for' parameter 'in' expression )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:208:2: 'for' parameter 'in' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal154=(Token)match(input,115,FOLLOW_115_in_expressionGenerator1396); 
            string_literal154_tree = (Object)adaptor.create(string_literal154);
            adaptor.addChild(root_0, string_literal154_tree);

            pushFollow(FOLLOW_parameter_in_expressionGenerator1398);
            parameter155=parameter();

            state._fsp--;

            adaptor.addChild(root_0, parameter155.getTree());
            string_literal156=(Token)match(input,116,FOLLOW_116_in_expressionGenerator1400); 
            string_literal156_tree = (Object)adaptor.create(string_literal156);
            adaptor.addChild(root_0, string_literal156_tree);

            pushFollow(FOLLOW_expression_in_expressionGenerator1402);
            expression157=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression157.getTree());
             

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:211:1: expressionGenerators : expressionGenerator ( ',' expressionGenerator )* -> ( expressionGenerator )+ ;
    public final C_ALParser.expressionGenerators_return expressionGenerators() throws RecognitionException {
        C_ALParser.expressionGenerators_return retval = new C_ALParser.expressionGenerators_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal159=null;
        C_ALParser.expressionGenerator_return expressionGenerator158 = null;

        C_ALParser.expressionGenerator_return expressionGenerator160 = null;


        Object char_literal159_tree=null;
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleSubtreeStream stream_expressionGenerator=new RewriteRuleSubtreeStream(adaptor,"rule expressionGenerator");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:211:21: ( expressionGenerator ( ',' expressionGenerator )* -> ( expressionGenerator )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:211:23: expressionGenerator ( ',' expressionGenerator )*
            {
            pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators1412);
            expressionGenerator158=expressionGenerator();

            state._fsp--;

            stream_expressionGenerator.add(expressionGenerator158.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:211:43: ( ',' expressionGenerator )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==82) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:211:44: ',' expressionGenerator
            	    {
            	    char_literal159=(Token)match(input,82,FOLLOW_82_in_expressionGenerators1415);  
            	    stream_82.add(char_literal159);

            	    pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators1417);
            	    expressionGenerator160=expressionGenerator();

            	    state._fsp--;

            	    stream_expressionGenerator.add(expressionGenerator160.getTree());

            	    }
            	    break;

            	default :
            	    break loop50;
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
            // 211:70: -> ( expressionGenerator )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:213:1: expressions : expression ( ',' expression )* -> ( expression )+ ;
    public final C_ALParser.expressions_return expressions() throws RecognitionException {
        C_ALParser.expressions_return retval = new C_ALParser.expressions_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal162=null;
        C_ALParser.expression_return expression161 = null;

        C_ALParser.expression_return expression163 = null;


        Object char_literal162_tree=null;
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:213:12: ( expression ( ',' expression )* -> ( expression )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:213:14: expression ( ',' expression )*
            {
            pushFollow(FOLLOW_expression_in_expressions1431);
            expression161=expression();

            state._fsp--;

            stream_expression.add(expression161.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:213:25: ( ',' expression )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==82) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:213:26: ',' expression
            	    {
            	    char_literal162=(Token)match(input,82,FOLLOW_82_in_expressions1434);  
            	    stream_82.add(char_literal162);

            	    pushFollow(FOLLOW_expression_in_expressions1436);
            	    expression163=expression();

            	    state._fsp--;

            	    stream_expression.add(expression163.getTree());

            	    }
            	    break;

            	default :
            	    break loop51;
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
            // 213:43: -> ( expression )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:215:1: idents : ID ( ',' ID )* -> ( ID )+ ;
    public final C_ALParser.idents_return idents() throws RecognitionException {
        C_ALParser.idents_return retval = new C_ALParser.idents_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID164=null;
        Token char_literal165=null;
        Token ID166=null;

        Object ID164_tree=null;
        Object char_literal165_tree=null;
        Object ID166_tree=null;
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:218:7: ( ID ( ',' ID )* -> ( ID )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:218:9: ID ( ',' ID )*
            {
            ID164=(Token)match(input,ID,FOLLOW_ID_in_idents1455);  
            stream_ID.add(ID164);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:218:12: ( ',' ID )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==82) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:218:13: ',' ID
            	    {
            	    char_literal165=(Token)match(input,82,FOLLOW_82_in_idents1458);  
            	    stream_82.add(char_literal165);

            	    ID166=(Token)match(input,ID,FOLLOW_ID_in_idents1460);  
            	    stream_ID.add(ID166);


            	    }
            	    break;

            	default :
            	    break loop52;
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
            // 218:22: -> ( ID )+
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

    public static class parameter_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "parameter"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:220:1: parameter : typeDef ID -> ^( VARIABLE typeDef ID ASSIGNABLE ) ;
    public final C_ALParser.parameter_return parameter() throws RecognitionException {
        C_ALParser.parameter_return retval = new C_ALParser.parameter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID168=null;
        C_ALParser.typeDef_return typeDef167 = null;


        Object ID168_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:223:10: ( typeDef ID -> ^( VARIABLE typeDef ID ASSIGNABLE ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:223:12: typeDef ID
            {
            pushFollow(FOLLOW_typeDef_in_parameter1479);
            typeDef167=typeDef();

            state._fsp--;

            stream_typeDef.add(typeDef167.getTree());
            ID168=(Token)match(input,ID,FOLLOW_ID_in_parameter1481);  
            stream_ID.add(ID168);



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
            // 223:23: -> ^( VARIABLE typeDef ID ASSIGNABLE )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:223:26: ^( VARIABLE typeDef ID ASSIGNABLE )
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
    // $ANTLR end "parameter"

    public static class parameters_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "parameters"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:225:1: parameters : parameter ( ',' parameter )* -> ( parameter )+ ;
    public final C_ALParser.parameters_return parameters() throws RecognitionException {
        C_ALParser.parameters_return retval = new C_ALParser.parameters_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal170=null;
        C_ALParser.parameter_return parameter169 = null;

        C_ALParser.parameter_return parameter171 = null;


        Object char_literal170_tree=null;
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleSubtreeStream stream_parameter=new RewriteRuleSubtreeStream(adaptor,"rule parameter");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:225:11: ( parameter ( ',' parameter )* -> ( parameter )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:225:13: parameter ( ',' parameter )*
            {
            pushFollow(FOLLOW_parameter_in_parameters1500);
            parameter169=parameter();

            state._fsp--;

            stream_parameter.add(parameter169.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:225:23: ( ',' parameter )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==82) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:225:24: ',' parameter
            	    {
            	    char_literal170=(Token)match(input,82,FOLLOW_82_in_parameters1503);  
            	    stream_82.add(char_literal170);

            	    pushFollow(FOLLOW_parameter_in_parameters1505);
            	    parameter171=parameter();

            	    state._fsp--;

            	    stream_parameter.add(parameter171.getTree());

            	    }
            	    break;

            	default :
            	    break loop53;
                }
            } while (true);



            // AST REWRITE
            // elements: parameter
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 225:40: -> ( parameter )+
            {
                if ( !(stream_parameter.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_parameter.hasNext() ) {
                    adaptor.addChild(root_0, stream_parameter.nextTree());

                }
                stream_parameter.reset();

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
    // $ANTLR end "parameters"

    public static class priorityInequality_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "priorityInequality"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:227:1: priorityInequality : qualifiedIdent ( '>' qualifiedIdent )+ ';' -> ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ ) ;
    public final C_ALParser.priorityInequality_return priorityInequality() throws RecognitionException {
        C_ALParser.priorityInequality_return retval = new C_ALParser.priorityInequality_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal173=null;
        Token char_literal175=null;
        C_ALParser.qualifiedIdent_return qualifiedIdent172 = null;

        C_ALParser.qualifiedIdent_return qualifiedIdent174 = null;


        Object char_literal173_tree=null;
        Object char_literal175_tree=null;
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleTokenStream stream_103=new RewriteRuleTokenStream(adaptor,"token 103");
        RewriteRuleSubtreeStream stream_qualifiedIdent=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedIdent");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:230:19: ( qualifiedIdent ( '>' qualifiedIdent )+ ';' -> ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:230:21: qualifiedIdent ( '>' qualifiedIdent )+ ';'
            {
            pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality1524);
            qualifiedIdent172=qualifiedIdent();

            state._fsp--;

            stream_qualifiedIdent.add(qualifiedIdent172.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:230:36: ( '>' qualifiedIdent )+
            int cnt54=0;
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==103) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:230:37: '>' qualifiedIdent
            	    {
            	    char_literal173=(Token)match(input,103,FOLLOW_103_in_priorityInequality1527);  
            	    stream_103.add(char_literal173);

            	    pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality1529);
            	    qualifiedIdent174=qualifiedIdent();

            	    state._fsp--;

            	    stream_qualifiedIdent.add(qualifiedIdent174.getTree());

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

            char_literal175=(Token)match(input,90,FOLLOW_90_in_priorityInequality1533);  
            stream_90.add(char_literal175);



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
            // 230:62: -> ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:230:65: ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:232:1: priorityOrder : PRIORITY ( priorityInequality )* 'end' -> ^( PRIORITY ( priorityInequality )* ) ;
    public final C_ALParser.priorityOrder_return priorityOrder() throws RecognitionException {
        C_ALParser.priorityOrder_return retval = new C_ALParser.priorityOrder_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRIORITY176=null;
        Token string_literal178=null;
        C_ALParser.priorityInequality_return priorityInequality177 = null;


        Object PRIORITY176_tree=null;
        Object string_literal178_tree=null;
        RewriteRuleTokenStream stream_117=new RewriteRuleTokenStream(adaptor,"token 117");
        RewriteRuleTokenStream stream_PRIORITY=new RewriteRuleTokenStream(adaptor,"token PRIORITY");
        RewriteRuleSubtreeStream stream_priorityInequality=new RewriteRuleSubtreeStream(adaptor,"rule priorityInequality");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:232:14: ( PRIORITY ( priorityInequality )* 'end' -> ^( PRIORITY ( priorityInequality )* ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:232:16: PRIORITY ( priorityInequality )* 'end'
            {
            PRIORITY176=(Token)match(input,PRIORITY,FOLLOW_PRIORITY_in_priorityOrder1552);  
            stream_PRIORITY.add(PRIORITY176);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:232:25: ( priorityInequality )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==ID) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:232:25: priorityInequality
            	    {
            	    pushFollow(FOLLOW_priorityInequality_in_priorityOrder1554);
            	    priorityInequality177=priorityInequality();

            	    state._fsp--;

            	    stream_priorityInequality.add(priorityInequality177.getTree());

            	    }
            	    break;

            	default :
            	    break loop55;
                }
            } while (true);

            string_literal178=(Token)match(input,117,FOLLOW_117_in_priorityOrder1557);  
            stream_117.add(string_literal178);



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
            // 232:51: -> ^( PRIORITY ( priorityInequality )* )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:232:54: ^( PRIORITY ( priorityInequality )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_PRIORITY.nextNode(), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:232:65: ( priorityInequality )*
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:234:1: qualifiedIdent : ID ( '.' ID )* -> ( ID )+ ;
    public final C_ALParser.qualifiedIdent_return qualifiedIdent() throws RecognitionException {
        C_ALParser.qualifiedIdent_return retval = new C_ALParser.qualifiedIdent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID179=null;
        Token char_literal180=null;
        Token ID181=null;

        Object ID179_tree=null;
        Object char_literal180_tree=null;
        Object ID181_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_118=new RewriteRuleTokenStream(adaptor,"token 118");

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:237:15: ( ID ( '.' ID )* -> ( ID )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:237:17: ID ( '.' ID )*
            {
            ID179=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent1578);  
            stream_ID.add(ID179);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:237:20: ( '.' ID )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==118) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:237:21: '.' ID
            	    {
            	    char_literal180=(Token)match(input,118,FOLLOW_118_in_qualifiedIdent1581);  
            	    stream_118.add(char_literal180);

            	    ID181=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent1583);  
            	    stream_ID.add(ID181);


            	    }
            	    break;

            	default :
            	    break loop56;
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
            // 237:30: -> ( ID )+
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
    // $ANTLR end "qualifiedIdent"

    public static class schedule_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "schedule"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:239:1: schedule : SCHEDULE 'fsm' ID ':' ( stateTransition )* 'end' -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) ) ;
    public final C_ALParser.schedule_return schedule() throws RecognitionException {
        C_ALParser.schedule_return retval = new C_ALParser.schedule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SCHEDULE182=null;
        Token string_literal183=null;
        Token ID184=null;
        Token char_literal185=null;
        Token string_literal187=null;
        C_ALParser.stateTransition_return stateTransition186 = null;


        Object SCHEDULE182_tree=null;
        Object string_literal183_tree=null;
        Object ID184_tree=null;
        Object char_literal185_tree=null;
        Object string_literal187_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleTokenStream stream_117=new RewriteRuleTokenStream(adaptor,"token 117");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_SCHEDULE=new RewriteRuleTokenStream(adaptor,"token SCHEDULE");
        RewriteRuleTokenStream stream_119=new RewriteRuleTokenStream(adaptor,"token 119");
        RewriteRuleSubtreeStream stream_stateTransition=new RewriteRuleSubtreeStream(adaptor,"rule stateTransition");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:242:9: ( SCHEDULE 'fsm' ID ':' ( stateTransition )* 'end' -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:243:3: SCHEDULE 'fsm' ID ':' ( stateTransition )* 'end'
            {
            SCHEDULE182=(Token)match(input,SCHEDULE,FOLLOW_SCHEDULE_in_schedule1604);  
            stream_SCHEDULE.add(SCHEDULE182);

            string_literal183=(Token)match(input,119,FOLLOW_119_in_schedule1606);  
            stream_119.add(string_literal183);

            ID184=(Token)match(input,ID,FOLLOW_ID_in_schedule1608);  
            stream_ID.add(ID184);

            char_literal185=(Token)match(input,79,FOLLOW_79_in_schedule1610);  
            stream_79.add(char_literal185);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:243:25: ( stateTransition )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==ID) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:243:25: stateTransition
            	    {
            	    pushFollow(FOLLOW_stateTransition_in_schedule1612);
            	    stateTransition186=stateTransition();

            	    state._fsp--;

            	    stream_stateTransition.add(stateTransition186.getTree());

            	    }
            	    break;

            	default :
            	    break loop57;
                }
            } while (true);

            string_literal187=(Token)match(input,117,FOLLOW_117_in_schedule1615);  
            stream_117.add(string_literal187);



            // AST REWRITE
            // elements: stateTransition, SCHEDULE, ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 243:48: -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:243:51: ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_SCHEDULE.nextNode(), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:243:65: ^( TRANSITIONS ( stateTransition )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TRANSITIONS, "TRANSITIONS"), root_2);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:243:79: ( stateTransition )*
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:245:1: stateTransition : ID '(' qualifiedIdent ')' '-->' ID ';' -> ^( TRANSITION ID qualifiedIdent ID ) ;
    public final C_ALParser.stateTransition_return stateTransition() throws RecognitionException {
        C_ALParser.stateTransition_return retval = new C_ALParser.stateTransition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID188=null;
        Token char_literal189=null;
        Token char_literal191=null;
        Token string_literal192=null;
        Token ID193=null;
        Token char_literal194=null;
        C_ALParser.qualifiedIdent_return qualifiedIdent190 = null;


        Object ID188_tree=null;
        Object char_literal189_tree=null;
        Object char_literal191_tree=null;
        Object string_literal192_tree=null;
        Object ID193_tree=null;
        Object char_literal194_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_120=new RewriteRuleTokenStream(adaptor,"token 120");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_qualifiedIdent=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedIdent");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:245:16: ( ID '(' qualifiedIdent ')' '-->' ID ';' -> ^( TRANSITION ID qualifiedIdent ID ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:246:2: ID '(' qualifiedIdent ')' '-->' ID ';'
            {
            ID188=(Token)match(input,ID,FOLLOW_ID_in_stateTransition1638);  
            stream_ID.add(ID188);

            char_literal189=(Token)match(input,83,FOLLOW_83_in_stateTransition1640);  
            stream_83.add(char_literal189);

            pushFollow(FOLLOW_qualifiedIdent_in_stateTransition1642);
            qualifiedIdent190=qualifiedIdent();

            state._fsp--;

            stream_qualifiedIdent.add(qualifiedIdent190.getTree());
            char_literal191=(Token)match(input,84,FOLLOW_84_in_stateTransition1644);  
            stream_84.add(char_literal191);

            string_literal192=(Token)match(input,120,FOLLOW_120_in_stateTransition1646);  
            stream_120.add(string_literal192);

            ID193=(Token)match(input,ID,FOLLOW_ID_in_stateTransition1648);  
            stream_ID.add(ID193);

            char_literal194=(Token)match(input,90,FOLLOW_90_in_stateTransition1650);  
            stream_90.add(char_literal194);



            // AST REWRITE
            // elements: ID, qualifiedIdent, ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 246:41: -> ^( TRANSITION ID qualifiedIdent ID )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:246:44: ^( TRANSITION ID qualifiedIdent ID )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:248:1: statement : ID ';' ;
    public final C_ALParser.statement_return statement() throws RecognitionException {
        C_ALParser.statement_return retval = new C_ALParser.statement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID195=null;
        Token char_literal196=null;

        Object ID195_tree=null;
        Object char_literal196_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:251:10: ( ID ';' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:261:1: ID ';'
            {
            root_0 = (Object)adaptor.nil();

            ID195=(Token)match(input,ID,FOLLOW_ID_in_statement1684); 
            ID195_tree = (Object)adaptor.create(ID195);
            adaptor.addChild(root_0, ID195_tree);

            char_literal196=(Token)match(input,90,FOLLOW_90_in_statement1686); 
            char_literal196_tree = (Object)adaptor.create(char_literal196);
            adaptor.addChild(root_0, char_literal196_tree);


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

    public static class typeDef_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typeDef"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:263:1: typeDef : ( 'int' ( '(' expression ')' )? -> ^( TYPE 'int' ( ^( TYPE_ATTRS ^( EXPR SIZE expression ) ) )? ) | 'uint' ( '(' expression ')' )? -> ^( TYPE 'uint' ( ^( TYPE_ATTRS ^( EXPR SIZE expression ) ) )? ) | 'bool' -> ^( TYPE 'bool' ) | 'float' -> ^( TYPE 'float' ) );
    public final C_ALParser.typeDef_return typeDef() throws RecognitionException {
        C_ALParser.typeDef_return retval = new C_ALParser.typeDef_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal197=null;
        Token char_literal198=null;
        Token char_literal200=null;
        Token string_literal201=null;
        Token char_literal202=null;
        Token char_literal204=null;
        Token string_literal205=null;
        Token string_literal206=null;
        C_ALParser.expression_return expression199 = null;

        C_ALParser.expression_return expression203 = null;


        Object string_literal197_tree=null;
        Object char_literal198_tree=null;
        Object char_literal200_tree=null;
        Object string_literal201_tree=null;
        Object char_literal202_tree=null;
        Object char_literal204_tree=null;
        Object string_literal205_tree=null;
        Object string_literal206_tree=null;
        RewriteRuleTokenStream stream_121=new RewriteRuleTokenStream(adaptor,"token 121");
        RewriteRuleTokenStream stream_122=new RewriteRuleTokenStream(adaptor,"token 122");
        RewriteRuleTokenStream stream_123=new RewriteRuleTokenStream(adaptor,"token 123");
        RewriteRuleTokenStream stream_124=new RewriteRuleTokenStream(adaptor,"token 124");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:266:8: ( 'int' ( '(' expression ')' )? -> ^( TYPE 'int' ( ^( TYPE_ATTRS ^( EXPR SIZE expression ) ) )? ) | 'uint' ( '(' expression ')' )? -> ^( TYPE 'uint' ( ^( TYPE_ATTRS ^( EXPR SIZE expression ) ) )? ) | 'bool' -> ^( TYPE 'bool' ) | 'float' -> ^( TYPE 'float' ) )
            int alt60=4;
            switch ( input.LA(1) ) {
            case 121:
                {
                alt60=1;
                }
                break;
            case 122:
                {
                alt60=2;
                }
                break;
            case 123:
                {
                alt60=3;
                }
                break;
            case 124:
                {
                alt60=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;
            }

            switch (alt60) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:267:3: 'int' ( '(' expression ')' )?
                    {
                    string_literal197=(Token)match(input,121,FOLLOW_121_in_typeDef1700);  
                    stream_121.add(string_literal197);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:267:9: ( '(' expression ')' )?
                    int alt58=2;
                    int LA58_0 = input.LA(1);

                    if ( (LA58_0==83) ) {
                        alt58=1;
                    }
                    switch (alt58) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:267:10: '(' expression ')'
                            {
                            char_literal198=(Token)match(input,83,FOLLOW_83_in_typeDef1703);  
                            stream_83.add(char_literal198);

                            pushFollow(FOLLOW_expression_in_typeDef1705);
                            expression199=expression();

                            state._fsp--;

                            stream_expression.add(expression199.getTree());
                            char_literal200=(Token)match(input,84,FOLLOW_84_in_typeDef1707);  
                            stream_84.add(char_literal200);


                            }
                            break;

                    }



                    // AST REWRITE
                    // elements: 121, expression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 267:31: -> ^( TYPE 'int' ( ^( TYPE_ATTRS ^( EXPR SIZE expression ) ) )? )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:267:34: ^( TYPE 'int' ( ^( TYPE_ATTRS ^( EXPR SIZE expression ) ) )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_1);

                        adaptor.addChild(root_1, stream_121.nextNode());
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:267:47: ( ^( TYPE_ATTRS ^( EXPR SIZE expression ) ) )?
                        if ( stream_expression.hasNext() ) {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:267:47: ^( TYPE_ATTRS ^( EXPR SIZE expression ) )
                            {
                            Object root_2 = (Object)adaptor.nil();
                            root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_2);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:267:60: ^( EXPR SIZE expression )
                            {
                            Object root_3 = (Object)adaptor.nil();
                            root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR, "EXPR"), root_3);

                            adaptor.addChild(root_3, (Object)adaptor.create(SIZE, "SIZE"));
                            adaptor.addChild(root_3, stream_expression.nextTree());

                            adaptor.addChild(root_2, root_3);
                            }

                            adaptor.addChild(root_1, root_2);
                            }

                        }
                        stream_expression.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:268:3: 'uint' ( '(' expression ')' )?
                    {
                    string_literal201=(Token)match(input,122,FOLLOW_122_in_typeDef1734);  
                    stream_122.add(string_literal201);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:268:10: ( '(' expression ')' )?
                    int alt59=2;
                    int LA59_0 = input.LA(1);

                    if ( (LA59_0==83) ) {
                        alt59=1;
                    }
                    switch (alt59) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:268:11: '(' expression ')'
                            {
                            char_literal202=(Token)match(input,83,FOLLOW_83_in_typeDef1737);  
                            stream_83.add(char_literal202);

                            pushFollow(FOLLOW_expression_in_typeDef1739);
                            expression203=expression();

                            state._fsp--;

                            stream_expression.add(expression203.getTree());
                            char_literal204=(Token)match(input,84,FOLLOW_84_in_typeDef1741);  
                            stream_84.add(char_literal204);


                            }
                            break;

                    }



                    // AST REWRITE
                    // elements: expression, 122
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 268:32: -> ^( TYPE 'uint' ( ^( TYPE_ATTRS ^( EXPR SIZE expression ) ) )? )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:268:35: ^( TYPE 'uint' ( ^( TYPE_ATTRS ^( EXPR SIZE expression ) ) )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_1);

                        adaptor.addChild(root_1, stream_122.nextNode());
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:268:49: ( ^( TYPE_ATTRS ^( EXPR SIZE expression ) ) )?
                        if ( stream_expression.hasNext() ) {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:268:49: ^( TYPE_ATTRS ^( EXPR SIZE expression ) )
                            {
                            Object root_2 = (Object)adaptor.nil();
                            root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_2);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:268:62: ^( EXPR SIZE expression )
                            {
                            Object root_3 = (Object)adaptor.nil();
                            root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR, "EXPR"), root_3);

                            adaptor.addChild(root_3, (Object)adaptor.create(SIZE, "SIZE"));
                            adaptor.addChild(root_3, stream_expression.nextTree());

                            adaptor.addChild(root_2, root_3);
                            }

                            adaptor.addChild(root_1, root_2);
                            }

                        }
                        stream_expression.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:269:3: 'bool'
                    {
                    string_literal205=(Token)match(input,123,FOLLOW_123_in_typeDef1768);  
                    stream_123.add(string_literal205);



                    // AST REWRITE
                    // elements: 123
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 269:10: -> ^( TYPE 'bool' )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:269:13: ^( TYPE 'bool' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_1);

                        adaptor.addChild(root_1, stream_123.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:270:3: 'float'
                    {
                    string_literal206=(Token)match(input,124,FOLLOW_124_in_typeDef1780);  
                    stream_124.add(string_literal206);



                    // AST REWRITE
                    // elements: 124
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 270:11: -> ^( TYPE 'float' )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:270:14: ^( TYPE 'float' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_1);

                        adaptor.addChild(root_1, stream_124.nextNode());

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
    // $ANTLR end "typeDef"

    public static class varDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "varDecl"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:272:1: varDecl : ( 'const' typeDef ID '=' expression ';' -> ^( VARIABLE typeDef ID NON_ASSIGNABLE expression ) | typeDef ID ( '=' expression ';' -> ^( VARIABLE typeDef ID ASSIGNABLE expression ) | ';' -> ^( VARIABLE typeDef ID ASSIGNABLE ) ) );
    public final C_ALParser.varDecl_return varDecl() throws RecognitionException {
        C_ALParser.varDecl_return retval = new C_ALParser.varDecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal207=null;
        Token ID209=null;
        Token char_literal210=null;
        Token char_literal212=null;
        Token ID214=null;
        Token char_literal215=null;
        Token char_literal217=null;
        Token char_literal218=null;
        C_ALParser.typeDef_return typeDef208 = null;

        C_ALParser.expression_return expression211 = null;

        C_ALParser.typeDef_return typeDef213 = null;

        C_ALParser.expression_return expression216 = null;


        Object string_literal207_tree=null;
        Object ID209_tree=null;
        Object char_literal210_tree=null;
        Object char_literal212_tree=null;
        Object ID214_tree=null;
        Object char_literal215_tree=null;
        Object char_literal217_tree=null;
        Object char_literal218_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleTokenStream stream_88=new RewriteRuleTokenStream(adaptor,"token 88");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:276:8: ( 'const' typeDef ID '=' expression ';' -> ^( VARIABLE typeDef ID NON_ASSIGNABLE expression ) | typeDef ID ( '=' expression ';' -> ^( VARIABLE typeDef ID ASSIGNABLE expression ) | ';' -> ^( VARIABLE typeDef ID ASSIGNABLE ) ) )
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==88) ) {
                alt62=1;
            }
            else if ( ((LA62_0>=121 && LA62_0<=124)) ) {
                alt62=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                throw nvae;
            }
            switch (alt62) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:277:3: 'const' typeDef ID '=' expression ';'
                    {
                    string_literal207=(Token)match(input,88,FOLLOW_88_in_varDecl1804);  
                    stream_88.add(string_literal207);

                    pushFollow(FOLLOW_typeDef_in_varDecl1806);
                    typeDef208=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef208.getTree());
                    ID209=(Token)match(input,ID,FOLLOW_ID_in_varDecl1808);  
                    stream_ID.add(ID209);

                    char_literal210=(Token)match(input,89,FOLLOW_89_in_varDecl1810);  
                    stream_89.add(char_literal210);

                    pushFollow(FOLLOW_expression_in_varDecl1812);
                    expression211=expression();

                    state._fsp--;

                    stream_expression.add(expression211.getTree());
                    char_literal212=(Token)match(input,90,FOLLOW_90_in_varDecl1814);  
                    stream_90.add(char_literal212);



                    // AST REWRITE
                    // elements: expression, ID, typeDef
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 277:41: -> ^( VARIABLE typeDef ID NON_ASSIGNABLE expression )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:277:44: ^( VARIABLE typeDef ID NON_ASSIGNABLE expression )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:278:3: typeDef ID ( '=' expression ';' -> ^( VARIABLE typeDef ID ASSIGNABLE expression ) | ';' -> ^( VARIABLE typeDef ID ASSIGNABLE ) )
                    {
                    pushFollow(FOLLOW_typeDef_in_varDecl1832);
                    typeDef213=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef213.getTree());
                    ID214=(Token)match(input,ID,FOLLOW_ID_in_varDecl1834);  
                    stream_ID.add(ID214);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:279:3: ( '=' expression ';' -> ^( VARIABLE typeDef ID ASSIGNABLE expression ) | ';' -> ^( VARIABLE typeDef ID ASSIGNABLE ) )
                    int alt61=2;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==89) ) {
                        alt61=1;
                    }
                    else if ( (LA61_0==90) ) {
                        alt61=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 61, 0, input);

                        throw nvae;
                    }
                    switch (alt61) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:279:5: '=' expression ';'
                            {
                            char_literal215=(Token)match(input,89,FOLLOW_89_in_varDecl1840);  
                            stream_89.add(char_literal215);

                            pushFollow(FOLLOW_expression_in_varDecl1842);
                            expression216=expression();

                            state._fsp--;

                            stream_expression.add(expression216.getTree());
                            char_literal217=(Token)match(input,90,FOLLOW_90_in_varDecl1844);  
                            stream_90.add(char_literal217);



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
                            // 279:24: -> ^( VARIABLE typeDef ID ASSIGNABLE expression )
                            {
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:279:27: ^( VARIABLE typeDef ID ASSIGNABLE expression )
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
                        case 2 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:280:5: ';'
                            {
                            char_literal218=(Token)match(input,90,FOLLOW_90_in_varDecl1864);  
                            stream_90.add(char_literal218);



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
                            // 280:9: -> ^( VARIABLE typeDef ID ASSIGNABLE )
                            {
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\C_AL.g:280:12: ^( VARIABLE typeDef ID ASSIGNABLE )
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
    // $ANTLR end "varDecl"

    // Delegated rules


    protected DFA41 dfa41 = new DFA41(this);
    static final String DFA41_eotS =
        "\24\uffff";
    static final String DFA41_eofS =
        "\24\uffff";
    static final String DFA41_minS =
        "\1\100\23\uffff";
    static final String DFA41_maxS =
        "\1\155\23\uffff";
    static final String DFA41_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1"+
        "\15\1\16\1\17\1\20\1\21\1\22\1\23";
    static final String DFA41_specialS =
        "\24\uffff}>";
    static final String[] DFA41_transitionS = {
            "\1\16\1\17\1\22\1\20\33\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1"+
            "\10\1\11\1\12\1\13\1\14\1\15\1\21\1\23",
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

    static final short[] DFA41_eot = DFA.unpackEncodedString(DFA41_eotS);
    static final short[] DFA41_eof = DFA.unpackEncodedString(DFA41_eofS);
    static final char[] DFA41_min = DFA.unpackEncodedStringToUnsignedChars(DFA41_minS);
    static final char[] DFA41_max = DFA.unpackEncodedStringToUnsignedChars(DFA41_maxS);
    static final short[] DFA41_accept = DFA.unpackEncodedString(DFA41_acceptS);
    static final short[] DFA41_special = DFA.unpackEncodedString(DFA41_specialS);
    static final short[][] DFA41_transition;

    static {
        int numStates = DFA41_transitionS.length;
        DFA41_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA41_transition[i] = DFA.unpackEncodedString(DFA41_transitionS[i]);
        }
    }

    class DFA41 extends DFA {

        public DFA41(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 41;
            this.eot = DFA41_eot;
            this.eof = DFA41_eof;
            this.min = DFA41_min;
            this.max = DFA41_max;
            this.accept = DFA41_accept;
            this.special = DFA41_special;
            this.transition = DFA41_transition;
        }
        public String getDescription() {
            return "172:1: bop : ( '||' -> LOGIC_OR | '&&' -> LOGIC_AND | '|' -> BITOR | '^' -> BITXOR | '&' -> BITAND | '==' -> EQ | '!=' -> NE | '<' -> LT | '>' -> GT | '<=' -> LE | '>=' -> GE | '<<' -> SHIFT_LEFT | '>>' -> SHIFT_RIGHT | PLUS -> PLUS | MINUS -> MINUS | DIV -> DIV | '%' -> MOD | TIMES -> TIMES | '**' -> EXP );";
        }
    }
 

    public static final BitSet FOLLOW_GUARD_in_actionGuards65 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_expressions_in_actionGuards67 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_actionInput80 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_actionInput82 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_actionInput86 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_idents_in_actionInput88 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_actionInput90 = new BitSet(new long[]{0x2000000000000002L});
    public static final BitSet FOLLOW_actionRepeat_in_actionInput92 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionInput_in_actionInputs103 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_actionInputs106 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010020L});
    public static final BitSet FOLLOW_actionInput_in_actionInputs108 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_ID_in_actionOutput124 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_actionOutput126 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_actionOutput130 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_expressions_in_actionOutput132 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_actionOutput134 = new BitSet(new long[]{0x2000000000000002L});
    public static final BitSet FOLLOW_actionRepeat_in_actionOutput136 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionOutput_in_actionOutputs147 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_actionOutputs150 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010020L});
    public static final BitSet FOLLOW_actionOutput_in_actionOutputs152 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_REPEAT_in_actionRepeat166 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_expression_in_actionRepeat168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorImport_in_actor184 = new BitSet(new long[]{0x0080000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_ACTOR_in_actor187 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_actor189 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_actor191 = new BitSet(new long[]{0x0000000000000000L,0x1E00000000100000L});
    public static final BitSet FOLLOW_actorParameters_in_actor193 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_actor196 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_actor199 = new BitSet(new long[]{0x0000000000000000L,0x1E00000000200000L});
    public static final BitSet FOLLOW_parameters_in_actor203 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actor206 = new BitSet(new long[]{0x0000000000000000L,0x1E00000000100000L});
    public static final BitSet FOLLOW_parameters_in_actor210 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_actor213 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actor215 = new BitSet(new long[]{0x4C40000000000000L,0x1E00000011800000L});
    public static final BitSet FOLLOW_actorDeclarations_in_actor218 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_actor221 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_actor223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ACTION_in_actionOrInitialize284 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080020L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actionOrInitialize286 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_actionOrInitialize289 = new BitSet(new long[]{0x0000000000000000L,0x0000000000210020L});
    public static final BitSet FOLLOW_actionInputs_in_actionOrInitialize291 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actionOrInitialize294 = new BitSet(new long[]{0x0000000000000000L,0x0000000000110020L});
    public static final BitSet FOLLOW_actionOutputs_in_actionOrInitialize296 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_actionOrInitialize299 = new BitSet(new long[]{0x0200000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_actionGuards_in_actionOrInitialize305 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actionOrInitialize308 = new BitSet(new long[]{0x0000000000000000L,0x1E00000001800020L});
    public static final BitSet FOLLOW_varDecl_in_actionOrInitialize316 = new BitSet(new long[]{0x0000000000000000L,0x1E00000001800020L});
    public static final BitSet FOLLOW_statement_in_actionOrInitialize325 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800020L});
    public static final BitSet FOLLOW_87_in_actionOrInitialize332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INITIALIZE_in_actionOrInitialize418 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080020L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actionOrInitialize420 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_actionOrInitialize423 = new BitSet(new long[]{0x0000000000000000L,0x0000000000110020L});
    public static final BitSet FOLLOW_actionOutputs_in_actionOrInitialize425 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_actionOrInitialize428 = new BitSet(new long[]{0x0200000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_actionGuards_in_actionOrInitialize434 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actionOrInitialize437 = new BitSet(new long[]{0x0000000000000000L,0x1E00000001800020L});
    public static final BitSet FOLLOW_varDecl_in_actionOrInitialize445 = new BitSet(new long[]{0x0000000000000000L,0x1E00000001800020L});
    public static final BitSet FOLLOW_statement_in_actionOrInitialize454 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800020L});
    public static final BitSet FOLLOW_87_in_actionOrInitialize461 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionOrInitialize_in_actorDeclaration543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_priorityOrder_in_actorDeclaration547 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_actorDeclaration552 = new BitSet(new long[]{0x0000000000000000L,0x1E00000000000000L});
    public static final BitSet FOLLOW_typeDef_in_actorDeclaration554 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration556 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_actorDeclaration558 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration560 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_actorDeclaration562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_actorDeclaration584 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration586 = new BitSet(new long[]{0x0000000000000000L,0x0000000006080000L});
    public static final BitSet FOLLOW_89_in_actorDeclaration592 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration594 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_actorDeclaration596 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_actorDeclaration616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_actorDeclaration635 = new BitSet(new long[]{0x0000000000000000L,0x1E00000000100000L});
    public static final BitSet FOLLOW_parameters_in_actorDeclaration637 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_actorDeclaration640 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration642 = new BitSet(new long[]{0x0000000000000000L,0x1E00000009000000L});
    public static final BitSet FOLLOW_varDecl_in_actorDeclaration650 = new BitSet(new long[]{0x0000000000000000L,0x1E00000009000000L});
    public static final BitSet FOLLOW_91_in_actorDeclaration659 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration661 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_actorDeclaration663 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_actorDeclaration669 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_actorDeclaration702 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration704 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_actorDeclaration706 = new BitSet(new long[]{0x0000000000000000L,0x1E00000000100000L});
    public static final BitSet FOLLOW_parameters_in_actorDeclaration708 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_actorDeclaration711 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration713 = new BitSet(new long[]{0x0000000000000000L,0x1E00000001800020L});
    public static final BitSet FOLLOW_varDecl_in_actorDeclaration721 = new BitSet(new long[]{0x0000000000000000L,0x1E00000001800020L});
    public static final BitSet FOLLOW_statement_in_actorDeclaration730 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800020L});
    public static final BitSet FOLLOW_87_in_actorDeclaration737 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations779 = new BitSet(new long[]{0x4C40000000000002L,0x1E00000011000000L});
    public static final BitSet FOLLOW_schedule_in_actorDeclarations783 = new BitSet(new long[]{0x0C40000000000002L,0x1E00000011000000L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations785 = new BitSet(new long[]{0x0C40000000000002L,0x1E00000011000000L});
    public static final BitSet FOLLOW_schedule_in_actorDeclarations802 = new BitSet(new long[]{0x0C40000000000002L,0x1E00000011000000L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations804 = new BitSet(new long[]{0x0C40000000000002L,0x1E00000011000000L});
    public static final BitSet FOLLOW_93_in_actorImport824 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000020L});
    public static final BitSet FOLLOW_94_in_actorImport829 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport831 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_actorImport833 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport839 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_actorImport841 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_actorParameter856 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_actorParameter858 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_actorParameter861 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_expression_in_actorParameter863 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters885 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_actorParameters888 = new BitSet(new long[]{0x0000000000000000L,0x1E00000000000000L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters890 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_un_expr_in_expression911 = new BitSet(new long[]{0x0000000000000002L,0x00003FFF8000000FL});
    public static final BitSet FOLLOW_bop_in_expression917 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_un_expr_in_expression919 = new BitSet(new long[]{0x0000000000000002L,0x00003FFF8000000FL});
    public static final BitSet FOLLOW_95_in_bop957 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_bop965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_97_in_bop973 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_bop981 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_bop989 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_100_in_bop997 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_bop1005 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_bop1013 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_bop1021 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_104_in_bop1029 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_bop1037 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_106_in_bop1045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_107_in_bop1053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_in_bop1061 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_bop1069 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DIV_in_bop1077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_108_in_bop1085 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TIMES_in_bop1093 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_109_in_bop1101 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfix_expression_in_un_expr1112 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_un_expr1126 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_110_in_un_expr1142 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_111_in_un_expr1158 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_112_in_un_expr1174 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_un_expr_in_un_expr1182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_postfix_expression1202 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1206 = new BitSet(new long[]{0x0000000000000000L,0x0000000000808000L});
    public static final BitSet FOLLOW_79_in_postfix_expression1209 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_expressionGenerators_in_postfix_expression1213 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_postfix_expression1217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_postfix_expression1236 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_postfix_expression1244 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1246 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_postfix_expression1248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_postfix_expression1258 = new BitSet(new long[]{0x0000000000000002L,0x0000000000090000L});
    public static final BitSet FOLLOW_83_in_postfix_expression1266 = new BitSet(new long[]{0x0000000000000000L,0x0007C000005805A2L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1268 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_postfix_expression1271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_postfix_expression1291 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1293 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_postfix_expression1295 = new BitSet(new long[]{0x0000000000000002L,0x0000000000010000L});
    public static final BitSet FOLLOW_113_in_constant1332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_constant1344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_constant1356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_constant1368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_constant1380 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_115_in_expressionGenerator1396 = new BitSet(new long[]{0x0000000000000000L,0x1E00000000000000L});
    public static final BitSet FOLLOW_parameter_in_expressionGenerator1398 = new BitSet(new long[]{0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_116_in_expressionGenerator1400 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_expression_in_expressionGenerator1402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators1412 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_expressionGenerators1415 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators1417 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_expression_in_expressions1431 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_expressions1434 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_expression_in_expressions1436 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_ID_in_idents1455 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_idents1458 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_idents1460 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_typeDef_in_parameter1479 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_parameter1481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parameter_in_parameters1500 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_parameters1503 = new BitSet(new long[]{0x0000000000000000L,0x1E00000000000000L});
    public static final BitSet FOLLOW_parameter_in_parameters1505 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality1524 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_103_in_priorityInequality1527 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality1529 = new BitSet(new long[]{0x0000000000000000L,0x0000008004000000L});
    public static final BitSet FOLLOW_90_in_priorityInequality1533 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRIORITY_in_priorityOrder1552 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000020L});
    public static final BitSet FOLLOW_priorityInequality_in_priorityOrder1554 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000020L});
    public static final BitSet FOLLOW_117_in_priorityOrder1557 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent1578 = new BitSet(new long[]{0x0000000000000002L,0x0040000000000000L});
    public static final BitSet FOLLOW_118_in_qualifiedIdent1581 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent1583 = new BitSet(new long[]{0x0000000000000002L,0x0040000000000000L});
    public static final BitSet FOLLOW_SCHEDULE_in_schedule1604 = new BitSet(new long[]{0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_119_in_schedule1606 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_schedule1608 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_schedule1610 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000020L});
    public static final BitSet FOLLOW_stateTransition_in_schedule1612 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000020L});
    public static final BitSet FOLLOW_117_in_schedule1615 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stateTransition1638 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_stateTransition1640 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_qualifiedIdent_in_stateTransition1642 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_stateTransition1644 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_120_in_stateTransition1646 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_stateTransition1648 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_stateTransition1650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_statement1684 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_statement1686 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_121_in_typeDef1700 = new BitSet(new long[]{0x0000000000000002L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_typeDef1703 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_expression_in_typeDef1705 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_typeDef1707 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_122_in_typeDef1734 = new BitSet(new long[]{0x0000000000000002L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_typeDef1737 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_expression_in_typeDef1739 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_typeDef1741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_123_in_typeDef1768 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_124_in_typeDef1780 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_varDecl1804 = new BitSet(new long[]{0x0000000000000000L,0x1E00000000000000L});
    public static final BitSet FOLLOW_typeDef_in_varDecl1806 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_varDecl1808 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_varDecl1810 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_expression_in_varDecl1812 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_varDecl1814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_varDecl1832 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_varDecl1834 = new BitSet(new long[]{0x0000000000000000L,0x0000000006000000L});
    public static final BitSet FOLLOW_89_in_varDecl1840 = new BitSet(new long[]{0x0000000000000000L,0x0007C000004805A2L});
    public static final BitSet FOLLOW_expression_in_varDecl1842 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_varDecl1844 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_varDecl1864 = new BitSet(new long[]{0x0000000000000002L});

}