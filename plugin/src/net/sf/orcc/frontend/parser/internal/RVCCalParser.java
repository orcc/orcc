// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g 2009-10-21 22:27:08

package net.sf.orcc.frontend.parser.internal;

// @SuppressWarnings({"unchecked", "unused"})


import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedSetException;
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

@SuppressWarnings({"unchecked", "unused"})
public class RVCCalParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACTOR", "INPUTS", "OUTPUTS", "PARAMETER", "PARAMETERS", "ACTOR_DECLS", "STATE_VAR", "TRANSITION", "TRANSITIONS", "INEQUALITY", "GUARDS", "TAG", "STATEMENTS", "VARS", "EXPR", "EXPR_BINARY", "EXPR_UNARY", "OP", "EXPR_LIST", "EXPR_IF", "EXPR_CALL", "EXPR_IDX", "EXPR_VAR", "EXPR_BOOL", "EXPR_FLOAT", "EXPR_INT", "EXPR_STRING", "VAR", "TYPE", "TYPE_ATTRS", "ASSIGNABLE", "NON_ASSIGNABLE", "QID", "ID", "ACTION", "INITIALIZE", "FUNCTION", "PROCEDURE", "OR", "AND", "BITOR", "BITAND", "EQ", "NE", "LT", "GT", "LE", "GE", "SHIFT_LEFT", "SHIFT_RIGHT", "PLUS", "MINUS", "DIV", "DIV_INT", "MOD", "TIMES", "EXP", "NOT", "NUM_ELTS", "FLOAT", "INTEGER", "STRING", "PRIORITY", "SCHEDULE", "LETTER", "Exponent", "EscapeSequence", "OctalEscape", "LINE_COMMENT", "MULTI_LINE_COMMENT", "WHITESPACE", "'guard'", "':'", "'['", "']'", "','", "'repeat'", "'do'", "'actor'", "'('", "')'", "'==>'", "'end'", "'.'", "'var'", "':='", "';'", "'-->'", "'begin'", "'import'", "'all'", "'if'", "'then'", "'else'", "'true'", "'false'", "'for'", "'in'", "'fsm'", "'foreach'", "'..'", "'while'"
    };
    public static final int FUNCTION=40;
    public static final int EXPR_BOOL=27;
    public static final int LT=48;
    public static final int TRANSITION=11;
    public static final int OUTPUTS=6;
    public static final int EXPR_VAR=26;
    public static final int LETTER=68;
    public static final int MOD=58;
    public static final int EXPR_CALL=24;
    public static final int NOT=61;
    public static final int INPUTS=5;
    public static final int EXPR_UNARY=20;
    public static final int EOF=-1;
    public static final int ACTION=38;
    public static final int TYPE=32;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int TYPE_ATTRS=33;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__90=90;
    public static final int EXP=60;
    public static final int PARAMETER=7;
    public static final int STATE_VAR=10;
    public static final int GUARDS=14;
    public static final int VAR=31;
    public static final int EQ=46;
    public static final int T__99=99;
    public static final int T__98=98;
    public static final int T__97=97;
    public static final int T__96=96;
    public static final int T__95=95;
    public static final int NE=47;
    public static final int ASSIGNABLE=34;
    public static final int GE=51;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int INITIALIZE=39;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int LINE_COMMENT=72;
    public static final int DIV_INT=57;
    public static final int WHITESPACE=74;
    public static final int INEQUALITY=13;
    public static final int NON_ASSIGNABLE=35;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int EXPR_IDX=25;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int SHIFT_LEFT=52;
    public static final int SHIFT_RIGHT=53;
    public static final int BITOR=44;
    public static final int PRIORITY=66;
    public static final int ACTOR_DECLS=9;
    public static final int OP=21;
    public static final int ACTOR=4;
    public static final int OR=42;
    public static final int STATEMENTS=16;
    public static final int GT=49;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int EscapeSequence=70;
    public static final int T__79=79;
    public static final int T__78=78;
    public static final int T__77=77;
    public static final int PARAMETERS=8;
    public static final int EXPR_BINARY=19;
    public static final int SCHEDULE=67;
    public static final int Exponent=69;
    public static final int FLOAT=63;
    public static final int EXPR_FLOAT=28;
    public static final int ID=37;
    public static final int AND=43;
    public static final int BITAND=45;
    public static final int EXPR_LIST=22;
    public static final int EXPR=18;
    public static final int EXPR_STRING=30;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int T__105=105;
    public static final int NUM_ELTS=62;
    public static final int PLUS=54;
    public static final int EXPR_INT=29;
    public static final int INTEGER=64;
    public static final int TRANSITIONS=12;
    public static final int VARS=17;
    public static final int T__102=102;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int MINUS=55;
    public static final int EXPR_IF=23;
    public static final int MULTI_LINE_COMMENT=73;
    public static final int PROCEDURE=41;
    public static final int TAG=15;
    public static final int QID=36;
    public static final int DIV=56;
    public static final int TIMES=59;
    public static final int OctalEscape=71;
    public static final int LE=50;
    public static final int STRING=65;

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
    public String getGrammarFileName() { return "D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g"; }


    public static class actionGuards_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionGuards"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:110:1: actionGuards : 'guard' expressions -> expressions ;
    public final RVCCalParser.actionGuards_return actionGuards() throws RecognitionException {
        RVCCalParser.actionGuards_return retval = new RVCCalParser.actionGuards_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal1=null;
        RVCCalParser.expressions_return expressions2 = null;


        Object string_literal1_tree=null;
        RewriteRuleTokenStream stream_75=new RewriteRuleTokenStream(adaptor,"token 75");
        RewriteRuleSubtreeStream stream_expressions=new RewriteRuleSubtreeStream(adaptor,"rule expressions");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:110:13: ( 'guard' expressions -> expressions )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:110:15: 'guard' expressions
            {
            string_literal1=(Token)match(input,75,FOLLOW_75_in_actionGuards302);  
            stream_75.add(string_literal1);

            pushFollow(FOLLOW_expressions_in_actionGuards304);
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
            // 110:35: -> expressions
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:1: actionInput : ( ID ':' )? '[' idents ']' ( actionRepeat )? ;
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
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:12: ( ( ID ':' )? '[' idents ']' ( actionRepeat )? )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:113:2: ( ID ':' )? '[' idents ']' ( actionRepeat )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:113:2: ( ID ':' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==ID) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:113:3: ID ':'
                    {
                    ID3=(Token)match(input,ID,FOLLOW_ID_in_actionInput317); 
                    ID3_tree = (Object)adaptor.create(ID3);
                    adaptor.addChild(root_0, ID3_tree);

                    char_literal4=(Token)match(input,76,FOLLOW_76_in_actionInput319); 
                    char_literal4_tree = (Object)adaptor.create(char_literal4);
                    adaptor.addChild(root_0, char_literal4_tree);


                    }
                    break;

            }

            char_literal5=(Token)match(input,77,FOLLOW_77_in_actionInput323); 
            char_literal5_tree = (Object)adaptor.create(char_literal5);
            adaptor.addChild(root_0, char_literal5_tree);

            pushFollow(FOLLOW_idents_in_actionInput325);
            idents6=idents();

            state._fsp--;

            adaptor.addChild(root_0, idents6.getTree());
            char_literal7=(Token)match(input,78,FOLLOW_78_in_actionInput327); 
            char_literal7_tree = (Object)adaptor.create(char_literal7);
            adaptor.addChild(root_0, char_literal7_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:113:27: ( actionRepeat )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==80) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:113:27: actionRepeat
                    {
                    pushFollow(FOLLOW_actionRepeat_in_actionInput329);
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:1: actionInputs : actionInput ( ',' actionInput )* -> ( actionInput )+ ;
    public final RVCCalParser.actionInputs_return actionInputs() throws RecognitionException {
        RVCCalParser.actionInputs_return retval = new RVCCalParser.actionInputs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal10=null;
        RVCCalParser.actionInput_return actionInput9 = null;

        RVCCalParser.actionInput_return actionInput11 = null;


        Object char_literal10_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleSubtreeStream stream_actionInput=new RewriteRuleSubtreeStream(adaptor,"rule actionInput");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:13: ( actionInput ( ',' actionInput )* -> ( actionInput )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:15: actionInput ( ',' actionInput )*
            {
            pushFollow(FOLLOW_actionInput_in_actionInputs340);
            actionInput9=actionInput();

            state._fsp--;

            stream_actionInput.add(actionInput9.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:27: ( ',' actionInput )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==79) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:28: ',' actionInput
            	    {
            	    char_literal10=(Token)match(input,79,FOLLOW_79_in_actionInputs343);  
            	    stream_79.add(char_literal10);

            	    pushFollow(FOLLOW_actionInput_in_actionInputs345);
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
            // 116:46: -> ( actionInput )+
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:118:1: actionOutput : ( ID ':' )? '[' expressions ']' ( actionRepeat )? ;
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
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:118:13: ( ( ID ':' )? '[' expressions ']' ( actionRepeat )? )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:2: ( ID ':' )? '[' expressions ']' ( actionRepeat )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:2: ( ID ':' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==ID) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:3: ID ':'
                    {
                    ID12=(Token)match(input,ID,FOLLOW_ID_in_actionOutput361); 
                    ID12_tree = (Object)adaptor.create(ID12);
                    adaptor.addChild(root_0, ID12_tree);

                    char_literal13=(Token)match(input,76,FOLLOW_76_in_actionOutput363); 
                    char_literal13_tree = (Object)adaptor.create(char_literal13);
                    adaptor.addChild(root_0, char_literal13_tree);


                    }
                    break;

            }

            char_literal14=(Token)match(input,77,FOLLOW_77_in_actionOutput367); 
            char_literal14_tree = (Object)adaptor.create(char_literal14);
            adaptor.addChild(root_0, char_literal14_tree);

            pushFollow(FOLLOW_expressions_in_actionOutput369);
            expressions15=expressions();

            state._fsp--;

            adaptor.addChild(root_0, expressions15.getTree());
            char_literal16=(Token)match(input,78,FOLLOW_78_in_actionOutput371); 
            char_literal16_tree = (Object)adaptor.create(char_literal16);
            adaptor.addChild(root_0, char_literal16_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:32: ( actionRepeat )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==80) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:32: actionRepeat
                    {
                    pushFollow(FOLLOW_actionRepeat_in_actionOutput373);
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:122:1: actionOutputs : actionOutput ( ',' actionOutput )* -> ( actionOutput )+ ;
    public final RVCCalParser.actionOutputs_return actionOutputs() throws RecognitionException {
        RVCCalParser.actionOutputs_return retval = new RVCCalParser.actionOutputs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal19=null;
        RVCCalParser.actionOutput_return actionOutput18 = null;

        RVCCalParser.actionOutput_return actionOutput20 = null;


        Object char_literal19_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleSubtreeStream stream_actionOutput=new RewriteRuleSubtreeStream(adaptor,"rule actionOutput");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:122:14: ( actionOutput ( ',' actionOutput )* -> ( actionOutput )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:122:16: actionOutput ( ',' actionOutput )*
            {
            pushFollow(FOLLOW_actionOutput_in_actionOutputs384);
            actionOutput18=actionOutput();

            state._fsp--;

            stream_actionOutput.add(actionOutput18.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:122:29: ( ',' actionOutput )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==79) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:122:30: ',' actionOutput
            	    {
            	    char_literal19=(Token)match(input,79,FOLLOW_79_in_actionOutputs387);  
            	    stream_79.add(char_literal19);

            	    pushFollow(FOLLOW_actionOutput_in_actionOutputs389);
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
            // 122:49: -> ( actionOutput )+
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:124:1: actionRepeat : 'repeat' expression -> expression ;
    public final RVCCalParser.actionRepeat_return actionRepeat() throws RecognitionException {
        RVCCalParser.actionRepeat_return retval = new RVCCalParser.actionRepeat_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal21=null;
        RVCCalParser.expression_return expression22 = null;


        Object string_literal21_tree=null;
        RewriteRuleTokenStream stream_80=new RewriteRuleTokenStream(adaptor,"token 80");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:124:13: ( 'repeat' expression -> expression )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:124:15: 'repeat' expression
            {
            string_literal21=(Token)match(input,80,FOLLOW_80_in_actionRepeat403);  
            stream_80.add(string_literal21);

            pushFollow(FOLLOW_expression_in_actionRepeat405);
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
            // 124:35: -> expression
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:1: actionStatements : 'do' ( statement )* -> ( statement )* ;
    public final RVCCalParser.actionStatements_return actionStatements() throws RecognitionException {
        RVCCalParser.actionStatements_return retval = new RVCCalParser.actionStatements_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal23=null;
        RVCCalParser.statement_return statement24 = null;


        Object string_literal23_tree=null;
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:17: ( 'do' ( statement )* -> ( statement )* )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:19: 'do' ( statement )*
            {
            string_literal23=(Token)match(input,81,FOLLOW_81_in_actionStatements416);  
            stream_81.add(string_literal23);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:24: ( statement )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==ID||LA7_0==92||LA7_0==95||LA7_0==103||LA7_0==105) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:24: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_actionStatements418);
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
            // 126:35: -> ( statement )*
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:38: ( statement )*
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:128:1: actor : ( actorImport )* 'actor' id= ID ( '[' ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' ( actorDeclarations )? 'end' EOF -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS ( actorDeclarations )? ) ;
    public final RVCCalParser.actor_return actor() throws RecognitionException {
        RVCCalParser.actor_return retval = new RVCCalParser.actor_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token id=null;
        Token string_literal26=null;
        Token char_literal27=null;
        Token char_literal28=null;
        Token char_literal29=null;
        Token char_literal31=null;
        Token string_literal32=null;
        Token char_literal33=null;
        Token string_literal35=null;
        Token EOF36=null;
        RVCCalParser.actorPortDecls_return inputs = null;

        RVCCalParser.actorPortDecls_return outputs = null;

        RVCCalParser.actorImport_return actorImport25 = null;

        RVCCalParser.actorParameters_return actorParameters30 = null;

        RVCCalParser.actorDeclarations_return actorDeclarations34 = null;


        Object id_tree=null;
        Object string_literal26_tree=null;
        Object char_literal27_tree=null;
        Object char_literal28_tree=null;
        Object char_literal29_tree=null;
        Object char_literal31_tree=null;
        Object string_literal32_tree=null;
        Object char_literal33_tree=null;
        Object string_literal35_tree=null;
        Object EOF36_tree=null;
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_77=new RewriteRuleTokenStream(adaptor,"token 77");
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleTokenStream stream_85=new RewriteRuleTokenStream(adaptor,"token 85");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleSubtreeStream stream_actorImport=new RewriteRuleSubtreeStream(adaptor,"rule actorImport");
        RewriteRuleSubtreeStream stream_actorDeclarations=new RewriteRuleSubtreeStream(adaptor,"rule actorDeclarations");
        RewriteRuleSubtreeStream stream_actorPortDecls=new RewriteRuleSubtreeStream(adaptor,"rule actorPortDecls");
        RewriteRuleSubtreeStream stream_actorParameters=new RewriteRuleSubtreeStream(adaptor,"rule actorParameters");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:6: ( ( actorImport )* 'actor' id= ID ( '[' ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' ( actorDeclarations )? 'end' EOF -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS ( actorDeclarations )? ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:8: ( actorImport )* 'actor' id= ID ( '[' ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' ( actorDeclarations )? 'end' EOF
            {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:8: ( actorImport )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==93) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:8: actorImport
            	    {
            	    pushFollow(FOLLOW_actorImport_in_actor436);
            	    actorImport25=actorImport();

            	    state._fsp--;

            	    stream_actorImport.add(actorImport25.getTree());

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            string_literal26=(Token)match(input,82,FOLLOW_82_in_actor439);  
            stream_82.add(string_literal26);

            id=(Token)match(input,ID,FOLLOW_ID_in_actor443);  
            stream_ID.add(id);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:35: ( '[' ']' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==77) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:36: '[' ']'
                    {
                    char_literal27=(Token)match(input,77,FOLLOW_77_in_actor446);  
                    stream_77.add(char_literal27);

                    char_literal28=(Token)match(input,78,FOLLOW_78_in_actor448);  
                    stream_78.add(char_literal28);


                    }
                    break;

            }

            char_literal29=(Token)match(input,83,FOLLOW_83_in_actor452);  
            stream_83.add(char_literal29);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:50: ( actorParameters )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==ID) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:50: actorParameters
                    {
                    pushFollow(FOLLOW_actorParameters_in_actor454);
                    actorParameters30=actorParameters();

                    state._fsp--;

                    stream_actorParameters.add(actorParameters30.getTree());

                    }
                    break;

            }

            char_literal31=(Token)match(input,84,FOLLOW_84_in_actor457);  
            stream_84.add(char_literal31);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:132:8: (inputs= actorPortDecls )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==ID) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:132:8: inputs= actorPortDecls
                    {
                    pushFollow(FOLLOW_actorPortDecls_in_actor462);
                    inputs=actorPortDecls();

                    state._fsp--;

                    stream_actorPortDecls.add(inputs.getTree());

                    }
                    break;

            }

            string_literal32=(Token)match(input,85,FOLLOW_85_in_actor465);  
            stream_85.add(string_literal32);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:132:38: (outputs= actorPortDecls )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==ID) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:132:38: outputs= actorPortDecls
                    {
                    pushFollow(FOLLOW_actorPortDecls_in_actor469);
                    outputs=actorPortDecls();

                    state._fsp--;

                    stream_actorPortDecls.add(outputs.getTree());

                    }
                    break;

            }

            char_literal33=(Token)match(input,76,FOLLOW_76_in_actor472);  
            stream_76.add(char_literal33);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:133:2: ( actorDeclarations )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0>=ID && LA13_0<=PROCEDURE)||(LA13_0>=PRIORITY && LA13_0<=SCHEDULE)) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:133:2: actorDeclarations
                    {
                    pushFollow(FOLLOW_actorDeclarations_in_actor475);
                    actorDeclarations34=actorDeclarations();

                    state._fsp--;

                    stream_actorDeclarations.add(actorDeclarations34.getTree());

                    }
                    break;

            }

            string_literal35=(Token)match(input,86,FOLLOW_86_in_actor478);  
            stream_86.add(string_literal35);

            EOF36=(Token)match(input,EOF,FOLLOW_EOF_in_actor480);  
            stream_EOF.add(EOF36);



            // AST REWRITE
            // elements: id, 82, actorParameters, inputs, outputs, actorDeclarations
            // token labels: id
            // rule labels: retval, inputs, outputs
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_id=new RewriteRuleTokenStream(adaptor,"token id",id);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_inputs=new RewriteRuleSubtreeStream(adaptor,"rule inputs",inputs!=null?inputs.tree:null);
            RewriteRuleSubtreeStream stream_outputs=new RewriteRuleSubtreeStream(adaptor,"rule outputs",outputs!=null?outputs.tree:null);

            root_0 = (Object)adaptor.nil();
            // 134:2: -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS ( actorDeclarations )? )
            {
                adaptor.addChild(root_0, stream_82.nextNode());
                adaptor.addChild(root_0, stream_id.nextNode());
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:135:2: ^( PARAMETERS ( actorParameters )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETERS, "PARAMETERS"), root_1);

                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:135:15: ( actorParameters )?
                if ( stream_actorParameters.hasNext() ) {
                    adaptor.addChild(root_1, stream_actorParameters.nextTree());

                }
                stream_actorParameters.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:2: ^( INPUTS ( $inputs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_1);

                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:11: ( $inputs)?
                if ( stream_inputs.hasNext() ) {
                    adaptor.addChild(root_1, stream_inputs.nextTree());

                }
                stream_inputs.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:137:2: ^( OUTPUTS ( $outputs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_1);

                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:137:12: ( $outputs)?
                if ( stream_outputs.hasNext() ) {
                    adaptor.addChild(root_1, stream_outputs.nextTree());

                }
                stream_outputs.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:138:2: ^( ACTOR_DECLS ( actorDeclarations )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ACTOR_DECLS, "ACTOR_DECLS"), root_1);

                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:138:16: ( actorDeclarations )?
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

    public static class actorDeclaration_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actorDeclaration"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:140:1: actorDeclaration : ( ID ( ( ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' ) | ACTION ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | priorityOrder -> priorityOrder | FUNCTION ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end' -> FUNCTION | PROCEDURE ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end' -> PROCEDURE );
    public final RVCCalParser.actorDeclaration_return actorDeclaration() throws RecognitionException {
        RVCCalParser.actorDeclaration_return retval = new RVCCalParser.actorDeclaration_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token varName=null;
        Token ID37=null;
        Token char_literal38=null;
        Token char_literal39=null;
        Token ACTION40=null;
        Token string_literal41=null;
        Token string_literal42=null;
        Token string_literal45=null;
        Token INITIALIZE46=null;
        Token string_literal47=null;
        Token string_literal50=null;
        Token string_literal53=null;
        Token char_literal54=null;
        Token char_literal55=null;
        Token char_literal56=null;
        Token string_literal58=null;
        Token char_literal60=null;
        Token ACTION61=null;
        Token string_literal63=null;
        Token string_literal66=null;
        Token string_literal69=null;
        Token INITIALIZE70=null;
        Token string_literal71=null;
        Token string_literal74=null;
        Token string_literal77=null;
        Token FUNCTION79=null;
        Token ID80=null;
        Token char_literal81=null;
        Token char_literal83=null;
        Token char_literal85=null;
        Token string_literal86=null;
        Token string_literal88=null;
        Token char_literal90=null;
        Token string_literal92=null;
        Token PROCEDURE93=null;
        Token ID94=null;
        Token char_literal95=null;
        Token char_literal97=null;
        Token char_literal99=null;
        Token string_literal100=null;
        Token string_literal102=null;
        Token string_literal104=null;
        Token tag=null;
        List list_tag=null;
        RVCCalParser.actionInputs_return inputs = null;

        RVCCalParser.actionOutputs_return outputs = null;

        RVCCalParser.actionGuards_return guards = null;

        RVCCalParser.typeAttrs_return attrs = null;

        RVCCalParser.varDecls_return varDecls43 = null;

        RVCCalParser.actionStatements_return actionStatements44 = null;

        RVCCalParser.actionOutputs_return actionOutputs48 = null;

        RVCCalParser.actionGuards_return actionGuards49 = null;

        RVCCalParser.varDecls_return varDecls51 = null;

        RVCCalParser.actionStatements_return actionStatements52 = null;

        RVCCalParser.expression_return expression57 = null;

        RVCCalParser.expression_return expression59 = null;

        RVCCalParser.actionInputs_return actionInputs62 = null;

        RVCCalParser.actionOutputs_return actionOutputs64 = null;

        RVCCalParser.actionGuards_return actionGuards65 = null;

        RVCCalParser.varDecls_return varDecls67 = null;

        RVCCalParser.actionStatements_return actionStatements68 = null;

        RVCCalParser.actionOutputs_return actionOutputs72 = null;

        RVCCalParser.actionGuards_return actionGuards73 = null;

        RVCCalParser.varDecls_return varDecls75 = null;

        RVCCalParser.actionStatements_return actionStatements76 = null;

        RVCCalParser.priorityOrder_return priorityOrder78 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr82 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr84 = null;

        RVCCalParser.typeDef_return typeDef87 = null;

        RVCCalParser.varDecls_return varDecls89 = null;

        RVCCalParser.expression_return expression91 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr96 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr98 = null;

        RVCCalParser.varDecls_return varDecls101 = null;

        RVCCalParser.statement_return statement103 = null;


        Object varName_tree=null;
        Object ID37_tree=null;
        Object char_literal38_tree=null;
        Object char_literal39_tree=null;
        Object ACTION40_tree=null;
        Object string_literal41_tree=null;
        Object string_literal42_tree=null;
        Object string_literal45_tree=null;
        Object INITIALIZE46_tree=null;
        Object string_literal47_tree=null;
        Object string_literal50_tree=null;
        Object string_literal53_tree=null;
        Object char_literal54_tree=null;
        Object char_literal55_tree=null;
        Object char_literal56_tree=null;
        Object string_literal58_tree=null;
        Object char_literal60_tree=null;
        Object ACTION61_tree=null;
        Object string_literal63_tree=null;
        Object string_literal66_tree=null;
        Object string_literal69_tree=null;
        Object INITIALIZE70_tree=null;
        Object string_literal71_tree=null;
        Object string_literal74_tree=null;
        Object string_literal77_tree=null;
        Object FUNCTION79_tree=null;
        Object ID80_tree=null;
        Object char_literal81_tree=null;
        Object char_literal83_tree=null;
        Object char_literal85_tree=null;
        Object string_literal86_tree=null;
        Object string_literal88_tree=null;
        Object char_literal90_tree=null;
        Object string_literal92_tree=null;
        Object PROCEDURE93_tree=null;
        Object ID94_tree=null;
        Object char_literal95_tree=null;
        Object char_literal97_tree=null;
        Object char_literal99_tree=null;
        Object string_literal100_tree=null;
        Object string_literal102_tree=null;
        Object string_literal104_tree=null;
        Object tag_tree=null;
        RewriteRuleTokenStream stream_FUNCTION=new RewriteRuleTokenStream(adaptor,"token FUNCTION");
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleTokenStream stream_INITIALIZE=new RewriteRuleTokenStream(adaptor,"token INITIALIZE");
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleTokenStream stream_91=new RewriteRuleTokenStream(adaptor,"token 91");
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleTokenStream stream_EQ=new RewriteRuleTokenStream(adaptor,"token EQ");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_PROCEDURE=new RewriteRuleTokenStream(adaptor,"token PROCEDURE");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_ACTION=new RewriteRuleTokenStream(adaptor,"token ACTION");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleTokenStream stream_88=new RewriteRuleTokenStream(adaptor,"token 88");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleTokenStream stream_85=new RewriteRuleTokenStream(adaptor,"token 85");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
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
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:143:17: ( ID ( ( ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' ) | ACTION ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | priorityOrder -> priorityOrder | FUNCTION ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end' -> FUNCTION | PROCEDURE ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end' -> PROCEDURE )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:3: ID ( ( ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' )
                    {
                    ID37=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration546);  
                    stream_ID.add(ID37);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:6: ( ( ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' )
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==76||LA27_0==87) ) {
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
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:5: ( ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) )
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:5: ( ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) )
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:6: ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) )
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:6: ( ( '.' tag+= ID )* )
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:7: ( '.' tag+= ID )*
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:7: ( '.' tag+= ID )*
                            loop14:
                            do {
                                int alt14=2;
                                int LA14_0 = input.LA(1);

                                if ( (LA14_0==87) ) {
                                    alt14=1;
                                }


                                switch (alt14) {
                            	case 1 :
                            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:8: '.' tag+= ID
                            	    {
                            	    char_literal38=(Token)match(input,87,FOLLOW_87_in_actorDeclaration557);  
                            	    stream_87.add(char_literal38);

                            	    tag=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration561);  
                            	    stream_ID.add(tag);

                            	    if (list_tag==null) list_tag=new ArrayList();
                            	    list_tag.add(tag);


                            	    }
                            	    break;

                            	default :
                            	    break loop14;
                                }
                            } while (true);


                            }

                            char_literal39=(Token)match(input,76,FOLLOW_76_in_actorDeclaration566);  
                            stream_76.add(char_literal39);

                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:7: ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) )
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
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:8: ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                                    {
                                    ACTION40=(Token)match(input,ACTION,FOLLOW_ACTION_in_actorDeclaration575);  
                                    stream_ACTION.add(ACTION40);

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:21: (inputs= actionInputs )?
                                    int alt15=2;
                                    int LA15_0 = input.LA(1);

                                    if ( (LA15_0==ID||LA15_0==77) ) {
                                        alt15=1;
                                    }
                                    switch (alt15) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:21: inputs= actionInputs
                                            {
                                            pushFollow(FOLLOW_actionInputs_in_actorDeclaration579);
                                            inputs=actionInputs();

                                            state._fsp--;

                                            stream_actionInputs.add(inputs.getTree());

                                            }
                                            break;

                                    }

                                    string_literal41=(Token)match(input,85,FOLLOW_85_in_actorDeclaration582);  
                                    stream_85.add(string_literal41);

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:49: (outputs= actionOutputs )?
                                    int alt16=2;
                                    int LA16_0 = input.LA(1);

                                    if ( (LA16_0==ID||LA16_0==77) ) {
                                        alt16=1;
                                    }
                                    switch (alt16) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:49: outputs= actionOutputs
                                            {
                                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration586);
                                            outputs=actionOutputs();

                                            state._fsp--;

                                            stream_actionOutputs.add(outputs.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:71: (guards= actionGuards )?
                                    int alt17=2;
                                    int LA17_0 = input.LA(1);

                                    if ( (LA17_0==75) ) {
                                        alt17=1;
                                    }
                                    switch (alt17) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:71: guards= actionGuards
                                            {
                                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration591);
                                            guards=actionGuards();

                                            state._fsp--;

                                            stream_actionGuards.add(guards.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:86: ( 'var' varDecls )?
                                    int alt18=2;
                                    int LA18_0 = input.LA(1);

                                    if ( (LA18_0==88) ) {
                                        alt18=1;
                                    }
                                    switch (alt18) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:87: 'var' varDecls
                                            {
                                            string_literal42=(Token)match(input,88,FOLLOW_88_in_actorDeclaration595);  
                                            stream_88.add(string_literal42);

                                            pushFollow(FOLLOW_varDecls_in_actorDeclaration597);
                                            varDecls43=varDecls();

                                            state._fsp--;

                                            stream_varDecls.add(varDecls43.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:104: ( actionStatements )?
                                    int alt19=2;
                                    int LA19_0 = input.LA(1);

                                    if ( (LA19_0==81) ) {
                                        alt19=1;
                                    }
                                    switch (alt19) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:104: actionStatements
                                            {
                                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration601);
                                            actionStatements44=actionStatements();

                                            state._fsp--;

                                            stream_actionStatements.add(actionStatements44.getTree());

                                            }
                                            break;

                                    }

                                    string_literal45=(Token)match(input,86,FOLLOW_86_in_actorDeclaration604);  
                                    stream_86.add(string_literal45);



                                    // AST REWRITE
                                    // elements: guards, varDecls, outputs, inputs, ACTION, ID, actionStatements, tag
                                    // token labels: 
                                    // rule labels: retval, inputs, guards, outputs
                                    // token list labels: tag
                                    // rule list labels: 
                                    // wildcard labels: 
                                    retval.tree = root_0;
                                    RewriteRuleTokenStream stream_tag=new RewriteRuleTokenStream(adaptor,"token tag", list_tag);
                                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                                    RewriteRuleSubtreeStream stream_inputs=new RewriteRuleSubtreeStream(adaptor,"rule inputs",inputs!=null?inputs.tree:null);
                                    RewriteRuleSubtreeStream stream_guards=new RewriteRuleSubtreeStream(adaptor,"rule guards",guards!=null?guards.tree:null);
                                    RewriteRuleSubtreeStream stream_outputs=new RewriteRuleSubtreeStream(adaptor,"rule outputs",outputs!=null?outputs.tree:null);

                                    root_0 = (Object)adaptor.nil();
                                    // 151:9: -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                    {
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:12: ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot(stream_ACTION.nextNode(), root_1);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:21: ^( TAG ID ( $tag)* )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TAG, "TAG"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:30: ( $tag)*
                                        while ( stream_tag.hasNext() ) {
                                            adaptor.addChild(root_2, stream_tag.nextNode());

                                        }
                                        stream_tag.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:37: ^( INPUTS ( $inputs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_2);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:46: ( $inputs)?
                                        if ( stream_inputs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_inputs.nextTree());

                                        }
                                        stream_inputs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:56: ^( OUTPUTS ( $outputs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:66: ( $outputs)?
                                        if ( stream_outputs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_outputs.nextTree());

                                        }
                                        stream_outputs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:77: ^( GUARDS ( $guards)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:86: ( $guards)?
                                        if ( stream_guards.hasNext() ) {
                                            adaptor.addChild(root_2, stream_guards.nextTree());

                                        }
                                        stream_guards.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:96: ^( VARS ( varDecls )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARS, "VARS"), root_2);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:103: ( varDecls )?
                                        if ( stream_varDecls.hasNext() ) {
                                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                                        }
                                        stream_varDecls.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:114: ^( STATEMENTS ( actionStatements )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:127: ( actionStatements )?
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
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:7: INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                                    {
                                    INITIALIZE46=(Token)match(input,INITIALIZE,FOLLOW_INITIALIZE_in_actorDeclaration675);  
                                    stream_INITIALIZE.add(INITIALIZE46);

                                    string_literal47=(Token)match(input,85,FOLLOW_85_in_actorDeclaration677);  
                                    stream_85.add(string_literal47);

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:24: ( actionOutputs )?
                                    int alt20=2;
                                    int LA20_0 = input.LA(1);

                                    if ( (LA20_0==ID||LA20_0==77) ) {
                                        alt20=1;
                                    }
                                    switch (alt20) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:24: actionOutputs
                                            {
                                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration679);
                                            actionOutputs48=actionOutputs();

                                            state._fsp--;

                                            stream_actionOutputs.add(actionOutputs48.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:39: ( actionGuards )?
                                    int alt21=2;
                                    int LA21_0 = input.LA(1);

                                    if ( (LA21_0==75) ) {
                                        alt21=1;
                                    }
                                    switch (alt21) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:39: actionGuards
                                            {
                                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration682);
                                            actionGuards49=actionGuards();

                                            state._fsp--;

                                            stream_actionGuards.add(actionGuards49.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:53: ( 'var' varDecls )?
                                    int alt22=2;
                                    int LA22_0 = input.LA(1);

                                    if ( (LA22_0==88) ) {
                                        alt22=1;
                                    }
                                    switch (alt22) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:54: 'var' varDecls
                                            {
                                            string_literal50=(Token)match(input,88,FOLLOW_88_in_actorDeclaration686);  
                                            stream_88.add(string_literal50);

                                            pushFollow(FOLLOW_varDecls_in_actorDeclaration688);
                                            varDecls51=varDecls();

                                            state._fsp--;

                                            stream_varDecls.add(varDecls51.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:71: ( actionStatements )?
                                    int alt23=2;
                                    int LA23_0 = input.LA(1);

                                    if ( (LA23_0==81) ) {
                                        alt23=1;
                                    }
                                    switch (alt23) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:71: actionStatements
                                            {
                                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration692);
                                            actionStatements52=actionStatements();

                                            state._fsp--;

                                            stream_actionStatements.add(actionStatements52.getTree());

                                            }
                                            break;

                                    }

                                    string_literal53=(Token)match(input,86,FOLLOW_86_in_actorDeclaration695);  
                                    stream_86.add(string_literal53);



                                    // AST REWRITE
                                    // elements: guards, outputs, INITIALIZE, actionStatements, ID, varDecls, tag
                                    // token labels: 
                                    // rule labels: retval, guards, outputs
                                    // token list labels: tag
                                    // rule list labels: 
                                    // wildcard labels: 
                                    retval.tree = root_0;
                                    RewriteRuleTokenStream stream_tag=new RewriteRuleTokenStream(adaptor,"token tag", list_tag);
                                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                                    RewriteRuleSubtreeStream stream_guards=new RewriteRuleSubtreeStream(adaptor,"rule guards",guards!=null?guards.tree:null);
                                    RewriteRuleSubtreeStream stream_outputs=new RewriteRuleSubtreeStream(adaptor,"rule outputs",outputs!=null?outputs.tree:null);

                                    root_0 = (Object)adaptor.nil();
                                    // 154:9: -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                    {
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:12: ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot(stream_INITIALIZE.nextNode(), root_1);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:25: ^( TAG ID ( $tag)* )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TAG, "TAG"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:34: ( $tag)*
                                        while ( stream_tag.hasNext() ) {
                                            adaptor.addChild(root_2, stream_tag.nextNode());

                                        }
                                        stream_tag.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        adaptor.addChild(root_1, (Object)adaptor.create(INPUTS, "INPUTS"));
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:48: ^( OUTPUTS ( $outputs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:58: ( $outputs)?
                                        if ( stream_outputs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_outputs.nextTree());

                                        }
                                        stream_outputs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:69: ^( GUARDS ( $guards)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:78: ( $guards)?
                                        if ( stream_guards.hasNext() ) {
                                            adaptor.addChild(root_2, stream_guards.nextTree());

                                        }
                                        stream_guards.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:88: ^( VARS ( varDecls )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARS, "VARS"), root_2);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:95: ( varDecls )?
                                        if ( stream_varDecls.hasNext() ) {
                                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                                        }
                                        stream_varDecls.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:106: ^( STATEMENTS ( actionStatements )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:119: ( actionStatements )?
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
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:160:5: ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';'
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:160:5: ( '(' attrs= typeAttrs ')' )?
                            int alt25=2;
                            int LA25_0 = input.LA(1);

                            if ( (LA25_0==83) ) {
                                alt25=1;
                            }
                            switch (alt25) {
                                case 1 :
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:160:6: '(' attrs= typeAttrs ')'
                                    {
                                    char_literal54=(Token)match(input,83,FOLLOW_83_in_actorDeclaration774);  
                                    stream_83.add(char_literal54);

                                    pushFollow(FOLLOW_typeAttrs_in_actorDeclaration778);
                                    attrs=typeAttrs();

                                    state._fsp--;

                                    stream_typeAttrs.add(attrs.getTree());
                                    char_literal55=(Token)match(input,84,FOLLOW_84_in_actorDeclaration780);  
                                    stream_84.add(char_literal55);


                                    }
                                    break;

                            }

                            varName=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration790);  
                            stream_ID.add(varName);

                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:5: ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) )
                            int alt26=3;
                            switch ( input.LA(1) ) {
                            case EQ:
                                {
                                alt26=1;
                                }
                                break;
                            case 89:
                                {
                                alt26=2;
                                }
                                break;
                            case 90:
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
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:8: '=' expression
                                    {
                                    char_literal56=(Token)match(input,EQ,FOLLOW_EQ_in_actorDeclaration799);  
                                    stream_EQ.add(char_literal56);

                                    pushFollow(FOLLOW_expression_in_actorDeclaration801);
                                    expression57=expression();

                                    state._fsp--;

                                    stream_expression.add(expression57.getTree());


                                    // AST REWRITE
                                    // elements: attrs, ID, varName, expression
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
                                    // 162:23: -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression )
                                    {
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:26: ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:38: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:48: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:61: ( $attrs)?
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
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:8: ':=' expression
                                    {
                                    string_literal58=(Token)match(input,89,FOLLOW_89_in_actorDeclaration837);  
                                    stream_89.add(string_literal58);

                                    pushFollow(FOLLOW_expression_in_actorDeclaration839);
                                    expression59=expression();

                                    state._fsp--;

                                    stream_expression.add(expression59.getTree());


                                    // AST REWRITE
                                    // elements: ID, expression, attrs, varName
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
                                    // 163:24: -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression )
                                    {
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:27: ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:39: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:49: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:62: ( $attrs)?
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
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:8: 
                                    {

                                    // AST REWRITE
                                    // elements: varName, ID, attrs
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
                                    // 164:8: -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE )
                                    {
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:11: ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:23: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:33: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:46: ( $attrs)?
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

                            char_literal60=(Token)match(input,90,FOLLOW_90_in_actorDeclaration901);  
                            stream_90.add(char_literal60);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:3: ACTION ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                    {
                    ACTION61=(Token)match(input,ACTION,FOLLOW_ACTION_in_actorDeclaration911);  
                    stream_ACTION.add(ACTION61);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:10: ( actionInputs )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==ID||LA28_0==77) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:10: actionInputs
                            {
                            pushFollow(FOLLOW_actionInputs_in_actorDeclaration913);
                            actionInputs62=actionInputs();

                            state._fsp--;

                            stream_actionInputs.add(actionInputs62.getTree());

                            }
                            break;

                    }

                    string_literal63=(Token)match(input,85,FOLLOW_85_in_actorDeclaration916);  
                    stream_85.add(string_literal63);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:30: ( actionOutputs )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==ID||LA29_0==77) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:30: actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration918);
                            actionOutputs64=actionOutputs();

                            state._fsp--;

                            stream_actionOutputs.add(actionOutputs64.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:45: ( actionGuards )?
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==75) ) {
                        alt30=1;
                    }
                    switch (alt30) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:45: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration921);
                            actionGuards65=actionGuards();

                            state._fsp--;

                            stream_actionGuards.add(actionGuards65.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:59: ( 'var' varDecls )?
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==88) ) {
                        alt31=1;
                    }
                    switch (alt31) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:60: 'var' varDecls
                            {
                            string_literal66=(Token)match(input,88,FOLLOW_88_in_actorDeclaration925);  
                            stream_88.add(string_literal66);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration927);
                            varDecls67=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls67.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:77: ( actionStatements )?
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==81) ) {
                        alt32=1;
                    }
                    switch (alt32) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:77: actionStatements
                            {
                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration931);
                            actionStatements68=actionStatements();

                            state._fsp--;

                            stream_actionStatements.add(actionStatements68.getTree());

                            }
                            break;

                    }

                    string_literal69=(Token)match(input,86,FOLLOW_86_in_actorDeclaration934);  
                    stream_86.add(string_literal69);



                    // AST REWRITE
                    // elements: actionStatements, guards, inputs, ACTION, varDecls, outputs
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
                    // 169:3: -> ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:6: ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_ACTION.nextNode(), root_1);

                        adaptor.addChild(root_1, (Object)adaptor.create(TAG, "TAG"));
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:19: ^( INPUTS ( $inputs)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_2);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:28: ( $inputs)?
                        if ( stream_inputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_inputs.nextTree());

                        }
                        stream_inputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:38: ^( OUTPUTS ( $outputs)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:48: ( $outputs)?
                        if ( stream_outputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_outputs.nextTree());

                        }
                        stream_outputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:59: ^( GUARDS ( $guards)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:68: ( $guards)?
                        if ( stream_guards.hasNext() ) {
                            adaptor.addChild(root_2, stream_guards.nextTree());

                        }
                        stream_guards.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:78: ^( VARS ( varDecls )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARS, "VARS"), root_2);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:85: ( varDecls )?
                        if ( stream_varDecls.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                        }
                        stream_varDecls.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:96: ^( STATEMENTS ( actionStatements )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:109: ( actionStatements )?
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:3: INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                    {
                    INITIALIZE70=(Token)match(input,INITIALIZE,FOLLOW_INITIALIZE_in_actorDeclaration988);  
                    stream_INITIALIZE.add(INITIALIZE70);

                    string_literal71=(Token)match(input,85,FOLLOW_85_in_actorDeclaration990);  
                    stream_85.add(string_literal71);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:20: ( actionOutputs )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==ID||LA33_0==77) ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:20: actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration992);
                            actionOutputs72=actionOutputs();

                            state._fsp--;

                            stream_actionOutputs.add(actionOutputs72.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:35: ( actionGuards )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==75) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:35: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration995);
                            actionGuards73=actionGuards();

                            state._fsp--;

                            stream_actionGuards.add(actionGuards73.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:49: ( 'var' varDecls )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==88) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:50: 'var' varDecls
                            {
                            string_literal74=(Token)match(input,88,FOLLOW_88_in_actorDeclaration999);  
                            stream_88.add(string_literal74);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration1001);
                            varDecls75=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls75.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:67: ( actionStatements )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==81) ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:67: actionStatements
                            {
                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration1005);
                            actionStatements76=actionStatements();

                            state._fsp--;

                            stream_actionStatements.add(actionStatements76.getTree());

                            }
                            break;

                    }

                    string_literal77=(Token)match(input,86,FOLLOW_86_in_actorDeclaration1008);  
                    stream_86.add(string_literal77);



                    // AST REWRITE
                    // elements: outputs, guards, INITIALIZE, varDecls, actionStatements
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
                    // 173:3: -> ^( INITIALIZE TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:6: ^( INITIALIZE TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_INITIALIZE.nextNode(), root_1);

                        adaptor.addChild(root_1, (Object)adaptor.create(TAG, "TAG"));
                        adaptor.addChild(root_1, (Object)adaptor.create(INPUTS, "INPUTS"));
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:30: ^( OUTPUTS ( $outputs)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:40: ( $outputs)?
                        if ( stream_outputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_outputs.nextTree());

                        }
                        stream_outputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:51: ^( GUARDS ( $guards)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:60: ( $guards)?
                        if ( stream_guards.hasNext() ) {
                            adaptor.addChild(root_2, stream_guards.nextTree());

                        }
                        stream_guards.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:70: ^( VARS ( varDecls )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARS, "VARS"), root_2);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:77: ( varDecls )?
                        if ( stream_varDecls.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                        }
                        stream_varDecls.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:88: ^( STATEMENTS ( actionStatements )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:101: ( actionStatements )?
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:3: priorityOrder
                    {
                    pushFollow(FOLLOW_priorityOrder_in_actorDeclaration1055);
                    priorityOrder78=priorityOrder();

                    state._fsp--;

                    stream_priorityOrder.add(priorityOrder78.getTree());


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
                    // 175:17: -> priorityOrder
                    {
                        adaptor.addChild(root_0, stream_priorityOrder.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:177:3: FUNCTION ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end'
                    {
                    FUNCTION79=(Token)match(input,FUNCTION,FOLLOW_FUNCTION_in_actorDeclaration1064);  
                    stream_FUNCTION.add(FUNCTION79);

                    ID80=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration1066);  
                    stream_ID.add(ID80);

                    char_literal81=(Token)match(input,83,FOLLOW_83_in_actorDeclaration1068);  
                    stream_83.add(char_literal81);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:177:19: ( varDeclNoExpr ( ',' varDeclNoExpr )* )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==ID) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:177:20: varDeclNoExpr ( ',' varDeclNoExpr )*
                            {
                            pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration1071);
                            varDeclNoExpr82=varDeclNoExpr();

                            state._fsp--;

                            stream_varDeclNoExpr.add(varDeclNoExpr82.getTree());
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:177:34: ( ',' varDeclNoExpr )*
                            loop37:
                            do {
                                int alt37=2;
                                int LA37_0 = input.LA(1);

                                if ( (LA37_0==79) ) {
                                    alt37=1;
                                }


                                switch (alt37) {
                            	case 1 :
                            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:177:35: ',' varDeclNoExpr
                            	    {
                            	    char_literal83=(Token)match(input,79,FOLLOW_79_in_actorDeclaration1074);  
                            	    stream_79.add(char_literal83);

                            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration1076);
                            	    varDeclNoExpr84=varDeclNoExpr();

                            	    state._fsp--;

                            	    stream_varDeclNoExpr.add(varDeclNoExpr84.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop37;
                                }
                            } while (true);


                            }
                            break;

                    }

                    char_literal85=(Token)match(input,84,FOLLOW_84_in_actorDeclaration1082);  
                    stream_84.add(char_literal85);

                    string_literal86=(Token)match(input,91,FOLLOW_91_in_actorDeclaration1084);  
                    stream_91.add(string_literal86);

                    pushFollow(FOLLOW_typeDef_in_actorDeclaration1086);
                    typeDef87=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef87.getTree());
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:5: ( 'var' varDecls )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==88) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:6: 'var' varDecls
                            {
                            string_literal88=(Token)match(input,88,FOLLOW_88_in_actorDeclaration1093);  
                            stream_88.add(string_literal88);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration1095);
                            varDecls89=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls89.getTree());

                            }
                            break;

                    }

                    char_literal90=(Token)match(input,76,FOLLOW_76_in_actorDeclaration1099);  
                    stream_76.add(char_literal90);

                    pushFollow(FOLLOW_expression_in_actorDeclaration1107);
                    expression91=expression();

                    state._fsp--;

                    stream_expression.add(expression91.getTree());
                    string_literal92=(Token)match(input,86,FOLLOW_86_in_actorDeclaration1113);  
                    stream_86.add(string_literal92);



                    // AST REWRITE
                    // elements: FUNCTION
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 181:2: -> FUNCTION
                    {
                        adaptor.addChild(root_0, stream_FUNCTION.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 6 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:3: PROCEDURE ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end'
                    {
                    PROCEDURE93=(Token)match(input,PROCEDURE,FOLLOW_PROCEDURE_in_actorDeclaration1123);  
                    stream_PROCEDURE.add(PROCEDURE93);

                    ID94=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration1125);  
                    stream_ID.add(ID94);

                    char_literal95=(Token)match(input,83,FOLLOW_83_in_actorDeclaration1127);  
                    stream_83.add(char_literal95);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:20: ( varDeclNoExpr ( ',' varDeclNoExpr )* )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==ID) ) {
                        alt41=1;
                    }
                    switch (alt41) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:21: varDeclNoExpr ( ',' varDeclNoExpr )*
                            {
                            pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration1130);
                            varDeclNoExpr96=varDeclNoExpr();

                            state._fsp--;

                            stream_varDeclNoExpr.add(varDeclNoExpr96.getTree());
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:35: ( ',' varDeclNoExpr )*
                            loop40:
                            do {
                                int alt40=2;
                                int LA40_0 = input.LA(1);

                                if ( (LA40_0==79) ) {
                                    alt40=1;
                                }


                                switch (alt40) {
                            	case 1 :
                            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:36: ',' varDeclNoExpr
                            	    {
                            	    char_literal97=(Token)match(input,79,FOLLOW_79_in_actorDeclaration1133);  
                            	    stream_79.add(char_literal97);

                            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration1135);
                            	    varDeclNoExpr98=varDeclNoExpr();

                            	    state._fsp--;

                            	    stream_varDeclNoExpr.add(varDeclNoExpr98.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop40;
                                }
                            } while (true);


                            }
                            break;

                    }

                    char_literal99=(Token)match(input,84,FOLLOW_84_in_actorDeclaration1141);  
                    stream_84.add(char_literal99);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:5: ( 'var' varDecls )?
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==88) ) {
                        alt42=1;
                    }
                    switch (alt42) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:6: 'var' varDecls
                            {
                            string_literal100=(Token)match(input,88,FOLLOW_88_in_actorDeclaration1148);  
                            stream_88.add(string_literal100);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration1150);
                            varDecls101=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls101.getTree());

                            }
                            break;

                    }

                    string_literal102=(Token)match(input,92,FOLLOW_92_in_actorDeclaration1158);  
                    stream_92.add(string_literal102);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:185:13: ( statement )*
                    loop43:
                    do {
                        int alt43=2;
                        int LA43_0 = input.LA(1);

                        if ( (LA43_0==ID||LA43_0==92||LA43_0==95||LA43_0==103||LA43_0==105) ) {
                            alt43=1;
                        }


                        switch (alt43) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:185:13: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_actorDeclaration1160);
                    	    statement103=statement();

                    	    state._fsp--;

                    	    stream_statement.add(statement103.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop43;
                        }
                    } while (true);

                    string_literal104=(Token)match(input,86,FOLLOW_86_in_actorDeclaration1163);  
                    stream_86.add(string_literal104);



                    // AST REWRITE
                    // elements: PROCEDURE
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 186:2: -> PROCEDURE
                    {
                        adaptor.addChild(root_0, stream_PROCEDURE.nextNode());

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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:1: actorDeclarations : ( ( actorDeclaration )+ ( schedule ( actorDeclaration )* )? -> ( actorDeclaration )+ ( schedule )? | schedule ( actorDeclaration )* -> ( actorDeclaration )* schedule );
    public final RVCCalParser.actorDeclarations_return actorDeclarations() throws RecognitionException {
        RVCCalParser.actorDeclarations_return retval = new RVCCalParser.actorDeclarations_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        RVCCalParser.actorDeclaration_return actorDeclaration105 = null;

        RVCCalParser.schedule_return schedule106 = null;

        RVCCalParser.actorDeclaration_return actorDeclaration107 = null;

        RVCCalParser.schedule_return schedule108 = null;

        RVCCalParser.actorDeclaration_return actorDeclaration109 = null;


        RewriteRuleSubtreeStream stream_schedule=new RewriteRuleSubtreeStream(adaptor,"rule schedule");
        RewriteRuleSubtreeStream stream_actorDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule actorDeclaration");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:18: ( ( actorDeclaration )+ ( schedule ( actorDeclaration )* )? -> ( actorDeclaration )+ ( schedule )? | schedule ( actorDeclaration )* -> ( actorDeclaration )* schedule )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( ((LA49_0>=ID && LA49_0<=PROCEDURE)||LA49_0==PRIORITY) ) {
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:20: ( actorDeclaration )+ ( schedule ( actorDeclaration )* )?
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:20: ( actorDeclaration )+
                    int cnt45=0;
                    loop45:
                    do {
                        int alt45=2;
                        int LA45_0 = input.LA(1);

                        if ( ((LA45_0>=ID && LA45_0<=PROCEDURE)||LA45_0==PRIORITY) ) {
                            alt45=1;
                        }


                        switch (alt45) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:20: actorDeclaration
                    	    {
                    	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations1175);
                    	    actorDeclaration105=actorDeclaration();

                    	    state._fsp--;

                    	    stream_actorDeclaration.add(actorDeclaration105.getTree());

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

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:38: ( schedule ( actorDeclaration )* )?
                    int alt47=2;
                    int LA47_0 = input.LA(1);

                    if ( (LA47_0==SCHEDULE) ) {
                        alt47=1;
                    }
                    switch (alt47) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:39: schedule ( actorDeclaration )*
                            {
                            pushFollow(FOLLOW_schedule_in_actorDeclarations1179);
                            schedule106=schedule();

                            state._fsp--;

                            stream_schedule.add(schedule106.getTree());
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:48: ( actorDeclaration )*
                            loop46:
                            do {
                                int alt46=2;
                                int LA46_0 = input.LA(1);

                                if ( ((LA46_0>=ID && LA46_0<=PROCEDURE)||LA46_0==PRIORITY) ) {
                                    alt46=1;
                                }


                                switch (alt46) {
                            	case 1 :
                            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:48: actorDeclaration
                            	    {
                            	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations1181);
                            	    actorDeclaration107=actorDeclaration();

                            	    state._fsp--;

                            	    stream_actorDeclaration.add(actorDeclaration107.getTree());

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
                    // 188:68: -> ( actorDeclaration )+ ( schedule )?
                    {
                        if ( !(stream_actorDeclaration.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_actorDeclaration.hasNext() ) {
                            adaptor.addChild(root_0, stream_actorDeclaration.nextTree());

                        }
                        stream_actorDeclaration.reset();
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:89: ( schedule )?
                        if ( stream_schedule.hasNext() ) {
                            adaptor.addChild(root_0, stream_schedule.nextTree());

                        }
                        stream_schedule.reset();

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:5: schedule ( actorDeclaration )*
                    {
                    pushFollow(FOLLOW_schedule_in_actorDeclarations1198);
                    schedule108=schedule();

                    state._fsp--;

                    stream_schedule.add(schedule108.getTree());
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:14: ( actorDeclaration )*
                    loop48:
                    do {
                        int alt48=2;
                        int LA48_0 = input.LA(1);

                        if ( ((LA48_0>=ID && LA48_0<=PROCEDURE)||LA48_0==PRIORITY) ) {
                            alt48=1;
                        }


                        switch (alt48) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:14: actorDeclaration
                    	    {
                    	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations1200);
                    	    actorDeclaration109=actorDeclaration();

                    	    state._fsp--;

                    	    stream_actorDeclaration.add(actorDeclaration109.getTree());

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
                    // 189:32: -> ( actorDeclaration )* schedule
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:35: ( actorDeclaration )*
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:191:1: actorImport : 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) ;
    public final RVCCalParser.actorImport_return actorImport() throws RecognitionException {
        RVCCalParser.actorImport_return retval = new RVCCalParser.actorImport_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal110=null;
        Token string_literal111=null;
        Token char_literal113=null;
        Token char_literal115=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent112 = null;

        RVCCalParser.qualifiedIdent_return qualifiedIdent114 = null;


        Object string_literal110_tree=null;
        Object string_literal111_tree=null;
        Object char_literal113_tree=null;
        Object char_literal115_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:194:12: ( 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:194:14: 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            {
            root_0 = (Object)adaptor.nil();

            string_literal110=(Token)match(input,93,FOLLOW_93_in_actorImport1220); 
            string_literal110_tree = (Object)adaptor.create(string_literal110);
            adaptor.addChild(root_0, string_literal110_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:3: ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==94) ) {
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:4: 'all' qualifiedIdent ';'
                    {
                    string_literal111=(Token)match(input,94,FOLLOW_94_in_actorImport1225); 
                    string_literal111_tree = (Object)adaptor.create(string_literal111);
                    adaptor.addChild(root_0, string_literal111_tree);

                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport1227);
                    qualifiedIdent112=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent112.getTree());
                    char_literal113=(Token)match(input,90,FOLLOW_90_in_actorImport1229); 
                    char_literal113_tree = (Object)adaptor.create(char_literal113);
                    adaptor.addChild(root_0, char_literal113_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:196:3: qualifiedIdent ';'
                    {
                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport1235);
                    qualifiedIdent114=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent114.getTree());
                    char_literal115=(Token)match(input,90,FOLLOW_90_in_actorImport1237); 
                    char_literal115_tree = (Object)adaptor.create(char_literal115);
                    adaptor.addChild(root_0, char_literal115_tree);

                     

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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:198:1: actorParameter : typeDef ID ( '=' expression )? -> ^( PARAMETER typeDef ID ( expression )? ) ;
    public final RVCCalParser.actorParameter_return actorParameter() throws RecognitionException {
        RVCCalParser.actorParameter_return retval = new RVCCalParser.actorParameter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID117=null;
        Token char_literal118=null;
        RVCCalParser.typeDef_return typeDef116 = null;

        RVCCalParser.expression_return expression119 = null;


        Object ID117_tree=null;
        Object char_literal118_tree=null;
        RewriteRuleTokenStream stream_EQ=new RewriteRuleTokenStream(adaptor,"token EQ");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:15: ( typeDef ID ( '=' expression )? -> ^( PARAMETER typeDef ID ( expression )? ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:201:2: typeDef ID ( '=' expression )?
            {
            pushFollow(FOLLOW_typeDef_in_actorParameter1252);
            typeDef116=typeDef();

            state._fsp--;

            stream_typeDef.add(typeDef116.getTree());
            ID117=(Token)match(input,ID,FOLLOW_ID_in_actorParameter1254);  
            stream_ID.add(ID117);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:201:13: ( '=' expression )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==EQ) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:201:14: '=' expression
                    {
                    char_literal118=(Token)match(input,EQ,FOLLOW_EQ_in_actorParameter1257);  
                    stream_EQ.add(char_literal118);

                    pushFollow(FOLLOW_expression_in_actorParameter1259);
                    expression119=expression();

                    state._fsp--;

                    stream_expression.add(expression119.getTree());

                    }
                    break;

            }



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
            // 201:31: -> ^( PARAMETER typeDef ID ( expression )? )
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:201:34: ^( PARAMETER typeDef ID ( expression )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETER, "PARAMETER"), root_1);

                adaptor.addChild(root_1, stream_typeDef.nextTree());
                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:201:57: ( expression )?
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:203:1: actorParameters : actorParameter ( ',' actorParameter )* -> ( actorParameter )+ ;
    public final RVCCalParser.actorParameters_return actorParameters() throws RecognitionException {
        RVCCalParser.actorParameters_return retval = new RVCCalParser.actorParameters_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal121=null;
        RVCCalParser.actorParameter_return actorParameter120 = null;

        RVCCalParser.actorParameter_return actorParameter122 = null;


        Object char_literal121_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleSubtreeStream stream_actorParameter=new RewriteRuleSubtreeStream(adaptor,"rule actorParameter");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:203:16: ( actorParameter ( ',' actorParameter )* -> ( actorParameter )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:203:18: actorParameter ( ',' actorParameter )*
            {
            pushFollow(FOLLOW_actorParameter_in_actorParameters1281);
            actorParameter120=actorParameter();

            state._fsp--;

            stream_actorParameter.add(actorParameter120.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:203:33: ( ',' actorParameter )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==79) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:203:34: ',' actorParameter
            	    {
            	    char_literal121=(Token)match(input,79,FOLLOW_79_in_actorParameters1284);  
            	    stream_79.add(char_literal121);

            	    pushFollow(FOLLOW_actorParameter_in_actorParameters1286);
            	    actorParameter122=actorParameter();

            	    state._fsp--;

            	    stream_actorParameter.add(actorParameter122.getTree());

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
            // 203:55: -> ( actorParameter )+
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:205:1: actorPortDecls : varDeclNoExpr ( ',' varDeclNoExpr )* -> ( varDeclNoExpr )+ ;
    public final RVCCalParser.actorPortDecls_return actorPortDecls() throws RecognitionException {
        RVCCalParser.actorPortDecls_return retval = new RVCCalParser.actorPortDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal124=null;
        RVCCalParser.varDeclNoExpr_return varDeclNoExpr123 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr125 = null;


        Object char_literal124_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleSubtreeStream stream_varDeclNoExpr=new RewriteRuleSubtreeStream(adaptor,"rule varDeclNoExpr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:208:15: ( varDeclNoExpr ( ',' varDeclNoExpr )* -> ( varDeclNoExpr )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:208:17: varDeclNoExpr ( ',' varDeclNoExpr )*
            {
            pushFollow(FOLLOW_varDeclNoExpr_in_actorPortDecls1305);
            varDeclNoExpr123=varDeclNoExpr();

            state._fsp--;

            stream_varDeclNoExpr.add(varDeclNoExpr123.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:208:31: ( ',' varDeclNoExpr )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==79) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:208:32: ',' varDeclNoExpr
            	    {
            	    char_literal124=(Token)match(input,79,FOLLOW_79_in_actorPortDecls1308);  
            	    stream_79.add(char_literal124);

            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorPortDecls1310);
            	    varDeclNoExpr125=varDeclNoExpr();

            	    state._fsp--;

            	    stream_varDeclNoExpr.add(varDeclNoExpr125.getTree());

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
            // 208:52: -> ( varDeclNoExpr )+
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:210:1: expression : un_expr ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr ) ;
    public final RVCCalParser.expression_return expression() throws RecognitionException {
        RVCCalParser.expression_return retval = new RVCCalParser.expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        RVCCalParser.un_expr_return un_expr126 = null;

        RVCCalParser.bop_return bop127 = null;

        RVCCalParser.un_expr_return un_expr128 = null;


        RewriteRuleSubtreeStream stream_bop=new RewriteRuleSubtreeStream(adaptor,"rule bop");
        RewriteRuleSubtreeStream stream_un_expr=new RewriteRuleSubtreeStream(adaptor,"rule un_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:11: ( un_expr ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:13: un_expr ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr )
            {
            pushFollow(FOLLOW_un_expr_in_expression1331);
            un_expr126=un_expr();

            state._fsp--;

            stream_un_expr.add(un_expr126.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:21: ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr )
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( ((LA55_0>=OR && LA55_0<=EXP)) ) {
                alt55=1;
            }
            else if ( ((LA55_0>=75 && LA55_0<=76)||(LA55_0>=78 && LA55_0<=79)||LA55_0==81||(LA55_0>=84 && LA55_0<=86)||LA55_0==88||LA55_0==90||LA55_0==92||(LA55_0>=96 && LA55_0<=97)||LA55_0==104) ) {
                alt55=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;
            }
            switch (alt55) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:22: ( bop un_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:22: ( bop un_expr )+
                    int cnt54=0;
                    loop54:
                    do {
                        int alt54=2;
                        int LA54_0 = input.LA(1);

                        if ( ((LA54_0>=OR && LA54_0<=EXP)) ) {
                            alt54=1;
                        }


                        switch (alt54) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:23: bop un_expr
                    	    {
                    	    pushFollow(FOLLOW_bop_in_expression1335);
                    	    bop127=bop();

                    	    state._fsp--;

                    	    stream_bop.add(bop127.getTree());
                    	    pushFollow(FOLLOW_un_expr_in_expression1337);
                    	    un_expr128=un_expr();

                    	    state._fsp--;

                    	    stream_un_expr.add(un_expr128.getTree());

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
                    // 215:37: -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:40: ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BINARY, "EXPR_BINARY"), root_1);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:54: ^( EXPR ( un_expr )+ )
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
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:71: ^( OP ( bop )+ )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:85: 
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
                    // 215:85: -> un_expr
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:217:1: bop : ( OR | AND | BITOR | BITAND | EQ | NE | LT | GT | LE | GE | SHIFT_LEFT | SHIFT_RIGHT | PLUS | MINUS | DIV | DIV_INT | MOD | TIMES | EXP );
    public final RVCCalParser.bop_return bop() throws RecognitionException {
        RVCCalParser.bop_return retval = new RVCCalParser.bop_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set129=null;

        Object set129_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:217:4: ( OR | AND | BITOR | BITAND | EQ | NE | LT | GT | LE | GE | SHIFT_LEFT | SHIFT_RIGHT | PLUS | MINUS | DIV | DIV_INT | MOD | TIMES | EXP )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:
            {
            root_0 = (Object)adaptor.nil();

            set129=(Token)input.LT(1);
            if ( (input.LA(1)>=OR && input.LA(1)<=EXP) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set129));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
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
    // $ANTLR end "bop"

    public static class un_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "un_expr"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:1: un_expr : ( postfix_expression -> postfix_expression | (op= MINUS | op= NOT | op= NUM_ELTS ) un_expr -> ^( EXPR_UNARY $op un_expr ) );
    public final RVCCalParser.un_expr_return un_expr() throws RecognitionException {
        RVCCalParser.un_expr_return retval = new RVCCalParser.un_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        RVCCalParser.postfix_expression_return postfix_expression130 = null;

        RVCCalParser.un_expr_return un_expr131 = null;


        Object op_tree=null;
        RewriteRuleTokenStream stream_NUM_ELTS=new RewriteRuleTokenStream(adaptor,"token NUM_ELTS");
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleTokenStream stream_MINUS=new RewriteRuleTokenStream(adaptor,"token MINUS");
        RewriteRuleSubtreeStream stream_postfix_expression=new RewriteRuleSubtreeStream(adaptor,"rule postfix_expression");
        RewriteRuleSubtreeStream stream_un_expr=new RewriteRuleSubtreeStream(adaptor,"rule un_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:8: ( postfix_expression -> postfix_expression | (op= MINUS | op= NOT | op= NUM_ELTS ) un_expr -> ^( EXPR_UNARY $op un_expr ) )
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==ID||(LA57_0>=FLOAT && LA57_0<=STRING)||LA57_0==77||LA57_0==83||LA57_0==95||(LA57_0>=98 && LA57_0<=99)) ) {
                alt57=1;
            }
            else if ( (LA57_0==MINUS||(LA57_0>=NOT && LA57_0<=NUM_ELTS)) ) {
                alt57=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;
            }
            switch (alt57) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:10: postfix_expression
                    {
                    pushFollow(FOLLOW_postfix_expression_in_un_expr1453);
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
                    // 228:29: -> postfix_expression
                    {
                        adaptor.addChild(root_0, stream_postfix_expression.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:4: (op= MINUS | op= NOT | op= NUM_ELTS ) un_expr
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:4: (op= MINUS | op= NOT | op= NUM_ELTS )
                    int alt56=3;
                    switch ( input.LA(1) ) {
                    case MINUS:
                        {
                        alt56=1;
                        }
                        break;
                    case NOT:
                        {
                        alt56=2;
                        }
                        break;
                    case NUM_ELTS:
                        {
                        alt56=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 56, 0, input);

                        throw nvae;
                    }

                    switch (alt56) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:5: op= MINUS
                            {
                            op=(Token)match(input,MINUS,FOLLOW_MINUS_in_un_expr1465);  
                            stream_MINUS.add(op);


                            }
                            break;
                        case 2 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:16: op= NOT
                            {
                            op=(Token)match(input,NOT,FOLLOW_NOT_in_un_expr1471);  
                            stream_NOT.add(op);


                            }
                            break;
                        case 3 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:25: op= NUM_ELTS
                            {
                            op=(Token)match(input,NUM_ELTS,FOLLOW_NUM_ELTS_in_un_expr1477);  
                            stream_NUM_ELTS.add(op);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_un_expr_in_un_expr1480);
                    un_expr131=un_expr();

                    state._fsp--;

                    stream_un_expr.add(un_expr131.getTree());


                    // AST REWRITE
                    // elements: op, un_expr
                    // token labels: op
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_op=new RewriteRuleTokenStream(adaptor,"token op",op);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 229:46: -> ^( EXPR_UNARY $op un_expr )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:49: ^( EXPR_UNARY $op un_expr )
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:1: postfix_expression : ( '[' e= expressions ( ':' g= expressionGenerators )? ']' -> ^( EXPR_LIST $e ( $g)? ) | 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end' -> ^( EXPR_IF $e1 $e2 $e3) | constant -> constant | '(' expression ')' -> expression | var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) ) );
    public final RVCCalParser.postfix_expression_return postfix_expression() throws RecognitionException {
        RVCCalParser.postfix_expression_return retval = new RVCCalParser.postfix_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token var=null;
        Token char_literal132=null;
        Token char_literal133=null;
        Token char_literal134=null;
        Token string_literal135=null;
        Token string_literal136=null;
        Token string_literal137=null;
        Token string_literal138=null;
        Token char_literal140=null;
        Token char_literal142=null;
        Token char_literal143=null;
        Token char_literal145=null;
        Token char_literal146=null;
        Token char_literal148=null;
        RVCCalParser.expressions_return e = null;

        RVCCalParser.expressionGenerators_return g = null;

        RVCCalParser.expression_return e1 = null;

        RVCCalParser.expression_return e2 = null;

        RVCCalParser.expression_return e3 = null;

        RVCCalParser.constant_return constant139 = null;

        RVCCalParser.expression_return expression141 = null;

        RVCCalParser.expressions_return expressions144 = null;

        RVCCalParser.expressions_return expressions147 = null;


        Object var_tree=null;
        Object char_literal132_tree=null;
        Object char_literal133_tree=null;
        Object char_literal134_tree=null;
        Object string_literal135_tree=null;
        Object string_literal136_tree=null;
        Object string_literal137_tree=null;
        Object string_literal138_tree=null;
        Object char_literal140_tree=null;
        Object char_literal142_tree=null;
        Object char_literal143_tree=null;
        Object char_literal145_tree=null;
        Object char_literal146_tree=null;
        Object char_literal148_tree=null;
        RewriteRuleTokenStream stream_97=new RewriteRuleTokenStream(adaptor,"token 97");
        RewriteRuleTokenStream stream_96=new RewriteRuleTokenStream(adaptor,"token 96");
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_95=new RewriteRuleTokenStream(adaptor,"token 95");
        RewriteRuleTokenStream stream_77=new RewriteRuleTokenStream(adaptor,"token 77");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_constant=new RewriteRuleSubtreeStream(adaptor,"rule constant");
        RewriteRuleSubtreeStream stream_expressionGenerators=new RewriteRuleSubtreeStream(adaptor,"rule expressionGenerators");
        RewriteRuleSubtreeStream stream_expressions=new RewriteRuleSubtreeStream(adaptor,"rule expressions");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:19: ( '[' e= expressions ( ':' g= expressionGenerators )? ']' -> ^( EXPR_LIST $e ( $g)? ) | 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end' -> ^( EXPR_IF $e1 $e2 $e3) | constant -> constant | '(' expression ')' -> expression | var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) ) )
            int alt62=5;
            switch ( input.LA(1) ) {
            case 77:
                {
                alt62=1;
                }
                break;
            case 95:
                {
                alt62=2;
                }
                break;
            case FLOAT:
            case INTEGER:
            case STRING:
            case 98:
            case 99:
                {
                alt62=3;
                }
                break;
            case 83:
                {
                alt62=4;
                }
                break;
            case ID:
                {
                alt62=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                throw nvae;
            }

            switch (alt62) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:3: '[' e= expressions ( ':' g= expressionGenerators )? ']'
                    {
                    char_literal132=(Token)match(input,77,FOLLOW_77_in_postfix_expression1500);  
                    stream_77.add(char_literal132);

                    pushFollow(FOLLOW_expressions_in_postfix_expression1504);
                    e=expressions();

                    state._fsp--;

                    stream_expressions.add(e.getTree());
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:21: ( ':' g= expressionGenerators )?
                    int alt58=2;
                    int LA58_0 = input.LA(1);

                    if ( (LA58_0==76) ) {
                        alt58=1;
                    }
                    switch (alt58) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:22: ':' g= expressionGenerators
                            {
                            char_literal133=(Token)match(input,76,FOLLOW_76_in_postfix_expression1507);  
                            stream_76.add(char_literal133);

                            pushFollow(FOLLOW_expressionGenerators_in_postfix_expression1511);
                            g=expressionGenerators();

                            state._fsp--;

                            stream_expressionGenerators.add(g.getTree());

                            }
                            break;

                    }

                    char_literal134=(Token)match(input,78,FOLLOW_78_in_postfix_expression1515);  
                    stream_78.add(char_literal134);



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
                    // 232:55: -> ^( EXPR_LIST $e ( $g)? )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:58: ^( EXPR_LIST $e ( $g)? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_LIST, "EXPR_LIST"), root_1);

                        adaptor.addChild(root_1, stream_e.nextTree());
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:73: ( $g)?
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:3: 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end'
                    {
                    string_literal135=(Token)match(input,95,FOLLOW_95_in_postfix_expression1532);  
                    stream_95.add(string_literal135);

                    pushFollow(FOLLOW_expression_in_postfix_expression1536);
                    e1=expression();

                    state._fsp--;

                    stream_expression.add(e1.getTree());
                    string_literal136=(Token)match(input,96,FOLLOW_96_in_postfix_expression1538);  
                    stream_96.add(string_literal136);

                    pushFollow(FOLLOW_expression_in_postfix_expression1542);
                    e2=expression();

                    state._fsp--;

                    stream_expression.add(e2.getTree());
                    string_literal137=(Token)match(input,97,FOLLOW_97_in_postfix_expression1544);  
                    stream_97.add(string_literal137);

                    pushFollow(FOLLOW_expression_in_postfix_expression1548);
                    e3=expression();

                    state._fsp--;

                    stream_expression.add(e3.getTree());
                    string_literal138=(Token)match(input,86,FOLLOW_86_in_postfix_expression1550);  
                    stream_86.add(string_literal138);



                    // AST REWRITE
                    // elements: e3, e1, e2
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
                    // 233:70: -> ^( EXPR_IF $e1 $e2 $e3)
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:73: ^( EXPR_IF $e1 $e2 $e3)
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:234:3: constant
                    {
                    pushFollow(FOLLOW_constant_in_postfix_expression1569);
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
                    // 234:12: -> constant
                    {
                        adaptor.addChild(root_0, stream_constant.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:3: '(' expression ')'
                    {
                    char_literal140=(Token)match(input,83,FOLLOW_83_in_postfix_expression1577);  
                    stream_83.add(char_literal140);

                    pushFollow(FOLLOW_expression_in_postfix_expression1579);
                    expression141=expression();

                    state._fsp--;

                    stream_expression.add(expression141.getTree());
                    char_literal142=(Token)match(input,84,FOLLOW_84_in_postfix_expression1581);  
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
                    // 235:22: -> expression
                    {
                        adaptor.addChild(root_0, stream_expression.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:3: var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) )
                    {
                    var=(Token)match(input,ID,FOLLOW_ID_in_postfix_expression1591);  
                    stream_ID.add(var);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:10: ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) )
                    int alt61=3;
                    switch ( input.LA(1) ) {
                    case 83:
                        {
                        alt61=1;
                        }
                        break;
                    case 77:
                        {
                        alt61=2;
                        }
                        break;
                    case OR:
                    case AND:
                    case BITOR:
                    case BITAND:
                    case EQ:
                    case NE:
                    case LT:
                    case GT:
                    case LE:
                    case GE:
                    case SHIFT_LEFT:
                    case SHIFT_RIGHT:
                    case PLUS:
                    case MINUS:
                    case DIV:
                    case DIV_INT:
                    case MOD:
                    case TIMES:
                    case EXP:
                    case 75:
                    case 76:
                    case 78:
                    case 79:
                    case 81:
                    case 84:
                    case 85:
                    case 86:
                    case 88:
                    case 90:
                    case 92:
                    case 96:
                    case 97:
                    case 104:
                        {
                        alt61=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 61, 0, input);

                        throw nvae;
                    }

                    switch (alt61) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:5: '(' ( expressions )? ')'
                            {
                            char_literal143=(Token)match(input,83,FOLLOW_83_in_postfix_expression1599);  
                            stream_83.add(char_literal143);

                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:9: ( expressions )?
                            int alt59=2;
                            int LA59_0 = input.LA(1);

                            if ( (LA59_0==ID||LA59_0==MINUS||(LA59_0>=NOT && LA59_0<=STRING)||LA59_0==77||LA59_0==83||LA59_0==95||(LA59_0>=98 && LA59_0<=99)) ) {
                                alt59=1;
                            }
                            switch (alt59) {
                                case 1 :
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:9: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_postfix_expression1601);
                                    expressions144=expressions();

                                    state._fsp--;

                                    stream_expressions.add(expressions144.getTree());

                                    }
                                    break;

                            }

                            char_literal145=(Token)match(input,84,FOLLOW_84_in_postfix_expression1604);  
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
                            // 237:26: -> ^( EXPR_CALL $var ( expressions )? )
                            {
                                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:29: ^( EXPR_CALL $var ( expressions )? )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_CALL, "EXPR_CALL"), root_1);

                                adaptor.addChild(root_1, stream_var.nextNode());
                                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:46: ( expressions )?
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
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:6: ( '[' expressions ']' )+
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:6: ( '[' expressions ']' )+
                            int cnt60=0;
                            loop60:
                            do {
                                int alt60=2;
                                int LA60_0 = input.LA(1);

                                if ( (LA60_0==77) ) {
                                    alt60=1;
                                }


                                switch (alt60) {
                            	case 1 :
                            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:7: '[' expressions ']'
                            	    {
                            	    char_literal146=(Token)match(input,77,FOLLOW_77_in_postfix_expression1624);  
                            	    stream_77.add(char_literal146);

                            	    pushFollow(FOLLOW_expressions_in_postfix_expression1626);
                            	    expressions147=expressions();

                            	    state._fsp--;

                            	    stream_expressions.add(expressions147.getTree());
                            	    char_literal148=(Token)match(input,78,FOLLOW_78_in_postfix_expression1628);  
                            	    stream_78.add(char_literal148);


                            	    }
                            	    break;

                            	default :
                            	    if ( cnt60 >= 1 ) break loop60;
                                        EarlyExitException eee =
                                            new EarlyExitException(60, input);
                                        throw eee;
                                }
                                cnt60++;
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
                            // 238:29: -> ^( EXPR_IDX $var ( expressions )+ )
                            {
                                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:32: ^( EXPR_IDX $var ( expressions )+ )
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
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:5: 
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
                            // 239:5: -> ^( EXPR_VAR $var)
                            {
                                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:8: ^( EXPR_VAR $var)
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:241:1: constant : ( 'true' -> ^( EXPR_BOOL 'true' ) | 'false' -> ^( EXPR_BOOL 'false' ) | FLOAT -> ^( EXPR_FLOAT FLOAT ) | INTEGER -> ^( EXPR_INT INTEGER ) | STRING -> ^( EXPR_STRING STRING ) );
    public final RVCCalParser.constant_return constant() throws RecognitionException {
        RVCCalParser.constant_return retval = new RVCCalParser.constant_return();
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
        RewriteRuleTokenStream stream_98=new RewriteRuleTokenStream(adaptor,"token 98");
        RewriteRuleTokenStream stream_FLOAT=new RewriteRuleTokenStream(adaptor,"token FLOAT");
        RewriteRuleTokenStream stream_99=new RewriteRuleTokenStream(adaptor,"token 99");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:241:9: ( 'true' -> ^( EXPR_BOOL 'true' ) | 'false' -> ^( EXPR_BOOL 'false' ) | FLOAT -> ^( EXPR_FLOAT FLOAT ) | INTEGER -> ^( EXPR_INT INTEGER ) | STRING -> ^( EXPR_STRING STRING ) )
            int alt63=5;
            switch ( input.LA(1) ) {
            case 98:
                {
                alt63=1;
                }
                break;
            case 99:
                {
                alt63=2;
                }
                break;
            case FLOAT:
                {
                alt63=3;
                }
                break;
            case INTEGER:
                {
                alt63=4;
                }
                break;
            case STRING:
                {
                alt63=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }

            switch (alt63) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:3: 'true'
                    {
                    string_literal149=(Token)match(input,98,FOLLOW_98_in_constant1665);  
                    stream_98.add(string_literal149);



                    // AST REWRITE
                    // elements: 98
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 242:10: -> ^( EXPR_BOOL 'true' )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:13: ^( EXPR_BOOL 'true' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BOOL, "EXPR_BOOL"), root_1);

                        adaptor.addChild(root_1, stream_98.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:243:3: 'false'
                    {
                    string_literal150=(Token)match(input,99,FOLLOW_99_in_constant1677);  
                    stream_99.add(string_literal150);



                    // AST REWRITE
                    // elements: 99
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 243:11: -> ^( EXPR_BOOL 'false' )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:243:14: ^( EXPR_BOOL 'false' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BOOL, "EXPR_BOOL"), root_1);

                        adaptor.addChild(root_1, stream_99.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:3: FLOAT
                    {
                    FLOAT151=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_constant1689);  
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
                    // 244:9: -> ^( EXPR_FLOAT FLOAT )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:12: ^( EXPR_FLOAT FLOAT )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:3: INTEGER
                    {
                    INTEGER152=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_constant1701);  
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
                    // 245:11: -> ^( EXPR_INT INTEGER )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:14: ^( EXPR_INT INTEGER )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:246:3: STRING
                    {
                    STRING153=(Token)match(input,STRING,FOLLOW_STRING_in_constant1713);  
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
                    // 246:10: -> ^( EXPR_STRING STRING )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:246:13: ^( EXPR_STRING STRING )
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:248:1: expressionGenerator : 'for' typeDef ID 'in' expression ;
    public final RVCCalParser.expressionGenerator_return expressionGenerator() throws RecognitionException {
        RVCCalParser.expressionGenerator_return retval = new RVCCalParser.expressionGenerator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal154=null;
        Token ID156=null;
        Token string_literal157=null;
        RVCCalParser.typeDef_return typeDef155 = null;

        RVCCalParser.expression_return expression158 = null;


        Object string_literal154_tree=null;
        Object ID156_tree=null;
        Object string_literal157_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:248:20: ( 'for' typeDef ID 'in' expression )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:249:2: 'for' typeDef ID 'in' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal154=(Token)match(input,100,FOLLOW_100_in_expressionGenerator1729); 
            string_literal154_tree = (Object)adaptor.create(string_literal154);
            adaptor.addChild(root_0, string_literal154_tree);

            pushFollow(FOLLOW_typeDef_in_expressionGenerator1731);
            typeDef155=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef155.getTree());
            ID156=(Token)match(input,ID,FOLLOW_ID_in_expressionGenerator1733); 
            ID156_tree = (Object)adaptor.create(ID156);
            adaptor.addChild(root_0, ID156_tree);

            string_literal157=(Token)match(input,101,FOLLOW_101_in_expressionGenerator1735); 
            string_literal157_tree = (Object)adaptor.create(string_literal157);
            adaptor.addChild(root_0, string_literal157_tree);

            pushFollow(FOLLOW_expression_in_expressionGenerator1737);
            expression158=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression158.getTree());
             

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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:252:1: expressionGenerators : expressionGenerator ( ',' expressionGenerator )* -> ( expressionGenerator )+ ;
    public final RVCCalParser.expressionGenerators_return expressionGenerators() throws RecognitionException {
        RVCCalParser.expressionGenerators_return retval = new RVCCalParser.expressionGenerators_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal160=null;
        RVCCalParser.expressionGenerator_return expressionGenerator159 = null;

        RVCCalParser.expressionGenerator_return expressionGenerator161 = null;


        Object char_literal160_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleSubtreeStream stream_expressionGenerator=new RewriteRuleSubtreeStream(adaptor,"rule expressionGenerator");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:252:21: ( expressionGenerator ( ',' expressionGenerator )* -> ( expressionGenerator )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:252:23: expressionGenerator ( ',' expressionGenerator )*
            {
            pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators1747);
            expressionGenerator159=expressionGenerator();

            state._fsp--;

            stream_expressionGenerator.add(expressionGenerator159.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:252:43: ( ',' expressionGenerator )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==79) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:252:44: ',' expressionGenerator
            	    {
            	    char_literal160=(Token)match(input,79,FOLLOW_79_in_expressionGenerators1750);  
            	    stream_79.add(char_literal160);

            	    pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators1752);
            	    expressionGenerator161=expressionGenerator();

            	    state._fsp--;

            	    stream_expressionGenerator.add(expressionGenerator161.getTree());

            	    }
            	    break;

            	default :
            	    break loop64;
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
            // 252:70: -> ( expressionGenerator )+
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:1: expressions : expression ( ',' expression )* -> ( expression )+ ;
    public final RVCCalParser.expressions_return expressions() throws RecognitionException {
        RVCCalParser.expressions_return retval = new RVCCalParser.expressions_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal163=null;
        RVCCalParser.expression_return expression162 = null;

        RVCCalParser.expression_return expression164 = null;


        Object char_literal163_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:12: ( expression ( ',' expression )* -> ( expression )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:14: expression ( ',' expression )*
            {
            pushFollow(FOLLOW_expression_in_expressions1766);
            expression162=expression();

            state._fsp--;

            stream_expression.add(expression162.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:25: ( ',' expression )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==79) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:26: ',' expression
            	    {
            	    char_literal163=(Token)match(input,79,FOLLOW_79_in_expressions1769);  
            	    stream_79.add(char_literal163);

            	    pushFollow(FOLLOW_expression_in_expressions1771);
            	    expression164=expression();

            	    state._fsp--;

            	    stream_expression.add(expression164.getTree());

            	    }
            	    break;

            	default :
            	    break loop65;
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
            // 254:43: -> ( expression )+
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:1: idents : ID ( ',' ID )* ;
    public final RVCCalParser.idents_return idents() throws RecognitionException {
        RVCCalParser.idents_return retval = new RVCCalParser.idents_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID165=null;
        Token char_literal166=null;
        Token ID167=null;

        Object ID165_tree=null;
        Object char_literal166_tree=null;
        Object ID167_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:7: ( ID ( ',' ID )* )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:9: ID ( ',' ID )*
            {
            root_0 = (Object)adaptor.nil();

            ID165=(Token)match(input,ID,FOLLOW_ID_in_idents1790); 
            ID165_tree = (Object)adaptor.create(ID165);
            adaptor.addChild(root_0, ID165_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:12: ( ',' ID )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( (LA66_0==79) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:13: ',' ID
            	    {
            	    char_literal166=(Token)match(input,79,FOLLOW_79_in_idents1793); 
            	    char_literal166_tree = (Object)adaptor.create(char_literal166);
            	    adaptor.addChild(root_0, char_literal166_tree);

            	    ID167=(Token)match(input,ID,FOLLOW_ID_in_idents1795); 
            	    ID167_tree = (Object)adaptor.create(ID167);
            	    adaptor.addChild(root_0, ID167_tree);


            	    }
            	    break;

            	default :
            	    break loop66;
                }
            } while (true);

             

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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:261:1: priorityInequality : qualifiedIdent ( '>' qualifiedIdent )+ ';' -> ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ ) ;
    public final RVCCalParser.priorityInequality_return priorityInequality() throws RecognitionException {
        RVCCalParser.priorityInequality_return retval = new RVCCalParser.priorityInequality_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal169=null;
        Token char_literal171=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent168 = null;

        RVCCalParser.qualifiedIdent_return qualifiedIdent170 = null;


        Object char_literal169_tree=null;
        Object char_literal171_tree=null;
        RewriteRuleTokenStream stream_GT=new RewriteRuleTokenStream(adaptor,"token GT");
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleSubtreeStream stream_qualifiedIdent=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedIdent");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:264:19: ( qualifiedIdent ( '>' qualifiedIdent )+ ';' -> ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:264:21: qualifiedIdent ( '>' qualifiedIdent )+ ';'
            {
            pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality1811);
            qualifiedIdent168=qualifiedIdent();

            state._fsp--;

            stream_qualifiedIdent.add(qualifiedIdent168.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:264:36: ( '>' qualifiedIdent )+
            int cnt67=0;
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==GT) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:264:37: '>' qualifiedIdent
            	    {
            	    char_literal169=(Token)match(input,GT,FOLLOW_GT_in_priorityInequality1814);  
            	    stream_GT.add(char_literal169);

            	    pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality1816);
            	    qualifiedIdent170=qualifiedIdent();

            	    state._fsp--;

            	    stream_qualifiedIdent.add(qualifiedIdent170.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt67 >= 1 ) break loop67;
                        EarlyExitException eee =
                            new EarlyExitException(67, input);
                        throw eee;
                }
                cnt67++;
            } while (true);

            char_literal171=(Token)match(input,90,FOLLOW_90_in_priorityInequality1820);  
            stream_90.add(char_literal171);



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
            // 264:62: -> ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ )
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:264:65: ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ )
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:1: priorityOrder : PRIORITY ( priorityInequality )* 'end' -> ^( PRIORITY ( priorityInequality )* ) ;
    public final RVCCalParser.priorityOrder_return priorityOrder() throws RecognitionException {
        RVCCalParser.priorityOrder_return retval = new RVCCalParser.priorityOrder_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRIORITY172=null;
        Token string_literal174=null;
        RVCCalParser.priorityInequality_return priorityInequality173 = null;


        Object PRIORITY172_tree=null;
        Object string_literal174_tree=null;
        RewriteRuleTokenStream stream_PRIORITY=new RewriteRuleTokenStream(adaptor,"token PRIORITY");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleSubtreeStream stream_priorityInequality=new RewriteRuleSubtreeStream(adaptor,"rule priorityInequality");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:14: ( PRIORITY ( priorityInequality )* 'end' -> ^( PRIORITY ( priorityInequality )* ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:16: PRIORITY ( priorityInequality )* 'end'
            {
            PRIORITY172=(Token)match(input,PRIORITY,FOLLOW_PRIORITY_in_priorityOrder1839);  
            stream_PRIORITY.add(PRIORITY172);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:25: ( priorityInequality )*
            loop68:
            do {
                int alt68=2;
                int LA68_0 = input.LA(1);

                if ( (LA68_0==ID) ) {
                    alt68=1;
                }


                switch (alt68) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:25: priorityInequality
            	    {
            	    pushFollow(FOLLOW_priorityInequality_in_priorityOrder1841);
            	    priorityInequality173=priorityInequality();

            	    state._fsp--;

            	    stream_priorityInequality.add(priorityInequality173.getTree());

            	    }
            	    break;

            	default :
            	    break loop68;
                }
            } while (true);

            string_literal174=(Token)match(input,86,FOLLOW_86_in_priorityOrder1844);  
            stream_86.add(string_literal174);



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
            // 266:51: -> ^( PRIORITY ( priorityInequality )* )
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:54: ^( PRIORITY ( priorityInequality )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_PRIORITY.nextNode(), root_1);

                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:65: ( priorityInequality )*
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:268:1: qualifiedIdent : ID ( '.' ID )* -> ^( QID ( ID )+ ) ;
    public final RVCCalParser.qualifiedIdent_return qualifiedIdent() throws RecognitionException {
        RVCCalParser.qualifiedIdent_return retval = new RVCCalParser.qualifiedIdent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID175=null;
        Token char_literal176=null;
        Token ID177=null;

        Object ID175_tree=null;
        Object char_literal176_tree=null;
        Object ID177_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:271:15: ( ID ( '.' ID )* -> ^( QID ( ID )+ ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:271:17: ID ( '.' ID )*
            {
            ID175=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent1865);  
            stream_ID.add(ID175);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:271:20: ( '.' ID )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==87) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:271:21: '.' ID
            	    {
            	    char_literal176=(Token)match(input,87,FOLLOW_87_in_qualifiedIdent1868);  
            	    stream_87.add(char_literal176);

            	    ID177=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent1870);  
            	    stream_ID.add(ID177);


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
            // 271:30: -> ^( QID ( ID )+ )
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:271:33: ^( QID ( ID )+ )
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:273:1: schedule : SCHEDULE 'fsm' ID ':' ( stateTransition )* 'end' -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) ) ;
    public final RVCCalParser.schedule_return schedule() throws RecognitionException {
        RVCCalParser.schedule_return retval = new RVCCalParser.schedule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SCHEDULE178=null;
        Token string_literal179=null;
        Token ID180=null;
        Token char_literal181=null;
        Token string_literal183=null;
        RVCCalParser.stateTransition_return stateTransition182 = null;


        Object SCHEDULE178_tree=null;
        Object string_literal179_tree=null;
        Object ID180_tree=null;
        Object char_literal181_tree=null;
        Object string_literal183_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_SCHEDULE=new RewriteRuleTokenStream(adaptor,"token SCHEDULE");
        RewriteRuleTokenStream stream_102=new RewriteRuleTokenStream(adaptor,"token 102");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleSubtreeStream stream_stateTransition=new RewriteRuleSubtreeStream(adaptor,"rule stateTransition");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:9: ( SCHEDULE 'fsm' ID ':' ( stateTransition )* 'end' -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:3: SCHEDULE 'fsm' ID ':' ( stateTransition )* 'end'
            {
            SCHEDULE178=(Token)match(input,SCHEDULE,FOLLOW_SCHEDULE_in_schedule1895);  
            stream_SCHEDULE.add(SCHEDULE178);

            string_literal179=(Token)match(input,102,FOLLOW_102_in_schedule1897);  
            stream_102.add(string_literal179);

            ID180=(Token)match(input,ID,FOLLOW_ID_in_schedule1899);  
            stream_ID.add(ID180);

            char_literal181=(Token)match(input,76,FOLLOW_76_in_schedule1901);  
            stream_76.add(char_literal181);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:25: ( stateTransition )*
            loop70:
            do {
                int alt70=2;
                int LA70_0 = input.LA(1);

                if ( (LA70_0==ID) ) {
                    alt70=1;
                }


                switch (alt70) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:25: stateTransition
            	    {
            	    pushFollow(FOLLOW_stateTransition_in_schedule1903);
            	    stateTransition182=stateTransition();

            	    state._fsp--;

            	    stream_stateTransition.add(stateTransition182.getTree());

            	    }
            	    break;

            	default :
            	    break loop70;
                }
            } while (true);

            string_literal183=(Token)match(input,86,FOLLOW_86_in_schedule1906);  
            stream_86.add(string_literal183);



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
            // 277:48: -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) )
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:51: ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_SCHEDULE.nextNode(), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:65: ^( TRANSITIONS ( stateTransition )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TRANSITIONS, "TRANSITIONS"), root_2);

                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:79: ( stateTransition )*
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:279:1: stateTransition : ID '(' qualifiedIdent ')' '-->' ID ';' -> ^( TRANSITION ID qualifiedIdent ID ) ;
    public final RVCCalParser.stateTransition_return stateTransition() throws RecognitionException {
        RVCCalParser.stateTransition_return retval = new RVCCalParser.stateTransition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID184=null;
        Token char_literal185=null;
        Token char_literal187=null;
        Token string_literal188=null;
        Token ID189=null;
        Token char_literal190=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent186 = null;


        Object ID184_tree=null;
        Object char_literal185_tree=null;
        Object char_literal187_tree=null;
        Object string_literal188_tree=null;
        Object ID189_tree=null;
        Object char_literal190_tree=null;
        RewriteRuleTokenStream stream_91=new RewriteRuleTokenStream(adaptor,"token 91");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_qualifiedIdent=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedIdent");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:279:16: ( ID '(' qualifiedIdent ')' '-->' ID ';' -> ^( TRANSITION ID qualifiedIdent ID ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:280:2: ID '(' qualifiedIdent ')' '-->' ID ';'
            {
            ID184=(Token)match(input,ID,FOLLOW_ID_in_stateTransition1929);  
            stream_ID.add(ID184);

            char_literal185=(Token)match(input,83,FOLLOW_83_in_stateTransition1931);  
            stream_83.add(char_literal185);

            pushFollow(FOLLOW_qualifiedIdent_in_stateTransition1933);
            qualifiedIdent186=qualifiedIdent();

            state._fsp--;

            stream_qualifiedIdent.add(qualifiedIdent186.getTree());
            char_literal187=(Token)match(input,84,FOLLOW_84_in_stateTransition1935);  
            stream_84.add(char_literal187);

            string_literal188=(Token)match(input,91,FOLLOW_91_in_stateTransition1937);  
            stream_91.add(string_literal188);

            ID189=(Token)match(input,ID,FOLLOW_ID_in_stateTransition1939);  
            stream_ID.add(ID189);

            char_literal190=(Token)match(input,90,FOLLOW_90_in_stateTransition1941);  
            stream_90.add(char_literal190);



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
            // 280:41: -> ^( TRANSITION ID qualifiedIdent ID )
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:280:44: ^( TRANSITION ID qualifiedIdent ID )
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:282:1: statement : ( 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end' | 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end' | 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end' | 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end' | ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' ) );
    public final RVCCalParser.statement_return statement() throws RecognitionException {
        RVCCalParser.statement_return retval = new RVCCalParser.statement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal191=null;
        Token string_literal192=null;
        Token string_literal194=null;
        Token string_literal196=null;
        Token string_literal197=null;
        Token string_literal199=null;
        Token string_literal201=null;
        Token string_literal203=null;
        Token string_literal205=null;
        Token string_literal207=null;
        Token string_literal208=null;
        Token string_literal210=null;
        Token string_literal212=null;
        Token string_literal214=null;
        Token string_literal215=null;
        Token string_literal217=null;
        Token string_literal219=null;
        Token string_literal221=null;
        Token ID222=null;
        Token char_literal223=null;
        Token char_literal225=null;
        Token string_literal226=null;
        Token char_literal228=null;
        Token char_literal229=null;
        Token char_literal231=null;
        Token char_literal232=null;
        RVCCalParser.varDecls_return varDecls193 = null;

        RVCCalParser.statement_return statement195 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr198 = null;

        RVCCalParser.expression_return expression200 = null;

        RVCCalParser.expression_return expression202 = null;

        RVCCalParser.varDecls_return varDecls204 = null;

        RVCCalParser.statement_return statement206 = null;

        RVCCalParser.expression_return expression209 = null;

        RVCCalParser.statement_return statement211 = null;

        RVCCalParser.statement_return statement213 = null;

        RVCCalParser.expression_return expression216 = null;

        RVCCalParser.varDecls_return varDecls218 = null;

        RVCCalParser.statement_return statement220 = null;

        RVCCalParser.expressions_return expressions224 = null;

        RVCCalParser.expression_return expression227 = null;

        RVCCalParser.expressions_return expressions230 = null;


        Object string_literal191_tree=null;
        Object string_literal192_tree=null;
        Object string_literal194_tree=null;
        Object string_literal196_tree=null;
        Object string_literal197_tree=null;
        Object string_literal199_tree=null;
        Object string_literal201_tree=null;
        Object string_literal203_tree=null;
        Object string_literal205_tree=null;
        Object string_literal207_tree=null;
        Object string_literal208_tree=null;
        Object string_literal210_tree=null;
        Object string_literal212_tree=null;
        Object string_literal214_tree=null;
        Object string_literal215_tree=null;
        Object string_literal217_tree=null;
        Object string_literal219_tree=null;
        Object string_literal221_tree=null;
        Object ID222_tree=null;
        Object char_literal223_tree=null;
        Object char_literal225_tree=null;
        Object string_literal226_tree=null;
        Object char_literal228_tree=null;
        Object char_literal229_tree=null;
        Object char_literal231_tree=null;
        Object char_literal232_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:285:10: ( 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end' | 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end' | 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end' | 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end' | ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' ) )
            int alt84=5;
            switch ( input.LA(1) ) {
            case 92:
                {
                alt84=1;
                }
                break;
            case 103:
                {
                alt84=2;
                }
                break;
            case 95:
                {
                alt84=3;
                }
                break;
            case 105:
                {
                alt84=4;
                }
                break;
            case ID:
                {
                alt84=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 84, 0, input);

                throw nvae;
            }

            switch (alt84) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:3: 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal191=(Token)match(input,92,FOLLOW_92_in_statement1967); 
                    string_literal191_tree = (Object)adaptor.create(string_literal191);
                    adaptor.addChild(root_0, string_literal191_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:11: ( 'var' varDecls 'do' )?
                    int alt71=2;
                    int LA71_0 = input.LA(1);

                    if ( (LA71_0==88) ) {
                        alt71=1;
                    }
                    switch (alt71) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:12: 'var' varDecls 'do'
                            {
                            string_literal192=(Token)match(input,88,FOLLOW_88_in_statement1970); 
                            string_literal192_tree = (Object)adaptor.create(string_literal192);
                            adaptor.addChild(root_0, string_literal192_tree);

                            pushFollow(FOLLOW_varDecls_in_statement1972);
                            varDecls193=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls193.getTree());
                            string_literal194=(Token)match(input,81,FOLLOW_81_in_statement1974); 
                            string_literal194_tree = (Object)adaptor.create(string_literal194);
                            adaptor.addChild(root_0, string_literal194_tree);


                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:34: ( statement )*
                    loop72:
                    do {
                        int alt72=2;
                        int LA72_0 = input.LA(1);

                        if ( (LA72_0==ID||LA72_0==92||LA72_0==95||LA72_0==103||LA72_0==105) ) {
                            alt72=1;
                        }


                        switch (alt72) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:34: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement1978);
                    	    statement195=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement195.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop72;
                        }
                    } while (true);

                    string_literal196=(Token)match(input,86,FOLLOW_86_in_statement1981); 
                    string_literal196_tree = (Object)adaptor.create(string_literal196);
                    adaptor.addChild(root_0, string_literal196_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:287:3: 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal197=(Token)match(input,103,FOLLOW_103_in_statement1987); 
                    string_literal197_tree = (Object)adaptor.create(string_literal197);
                    adaptor.addChild(root_0, string_literal197_tree);

                    pushFollow(FOLLOW_varDeclNoExpr_in_statement1989);
                    varDeclNoExpr198=varDeclNoExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, varDeclNoExpr198.getTree());
                    string_literal199=(Token)match(input,101,FOLLOW_101_in_statement1991); 
                    string_literal199_tree = (Object)adaptor.create(string_literal199);
                    adaptor.addChild(root_0, string_literal199_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:287:32: ( expression ( '..' expression )? )
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:287:33: expression ( '..' expression )?
                    {
                    pushFollow(FOLLOW_expression_in_statement1994);
                    expression200=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression200.getTree());
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:287:44: ( '..' expression )?
                    int alt73=2;
                    int LA73_0 = input.LA(1);

                    if ( (LA73_0==104) ) {
                        alt73=1;
                    }
                    switch (alt73) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:287:45: '..' expression
                            {
                            string_literal201=(Token)match(input,104,FOLLOW_104_in_statement1997); 
                            string_literal201_tree = (Object)adaptor.create(string_literal201);
                            adaptor.addChild(root_0, string_literal201_tree);

                            pushFollow(FOLLOW_expression_in_statement1999);
                            expression202=expression();

                            state._fsp--;

                            adaptor.addChild(root_0, expression202.getTree());

                            }
                            break;

                    }


                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:287:64: ( 'var' varDecls )?
                    int alt74=2;
                    int LA74_0 = input.LA(1);

                    if ( (LA74_0==88) ) {
                        alt74=1;
                    }
                    switch (alt74) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:287:65: 'var' varDecls
                            {
                            string_literal203=(Token)match(input,88,FOLLOW_88_in_statement2005); 
                            string_literal203_tree = (Object)adaptor.create(string_literal203);
                            adaptor.addChild(root_0, string_literal203_tree);

                            pushFollow(FOLLOW_varDecls_in_statement2007);
                            varDecls204=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls204.getTree());

                            }
                            break;

                    }

                    string_literal205=(Token)match(input,81,FOLLOW_81_in_statement2011); 
                    string_literal205_tree = (Object)adaptor.create(string_literal205);
                    adaptor.addChild(root_0, string_literal205_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:287:87: ( statement )*
                    loop75:
                    do {
                        int alt75=2;
                        int LA75_0 = input.LA(1);

                        if ( (LA75_0==ID||LA75_0==92||LA75_0==95||LA75_0==103||LA75_0==105) ) {
                            alt75=1;
                        }


                        switch (alt75) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:287:87: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement2013);
                    	    statement206=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement206.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop75;
                        }
                    } while (true);

                    string_literal207=(Token)match(input,86,FOLLOW_86_in_statement2016); 
                    string_literal207_tree = (Object)adaptor.create(string_literal207);
                    adaptor.addChild(root_0, string_literal207_tree);

                     

                    }
                    break;
                case 3 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:3: 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal208=(Token)match(input,95,FOLLOW_95_in_statement2022); 
                    string_literal208_tree = (Object)adaptor.create(string_literal208);
                    adaptor.addChild(root_0, string_literal208_tree);

                    pushFollow(FOLLOW_expression_in_statement2024);
                    expression209=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression209.getTree());
                    string_literal210=(Token)match(input,96,FOLLOW_96_in_statement2026); 
                    string_literal210_tree = (Object)adaptor.create(string_literal210);
                    adaptor.addChild(root_0, string_literal210_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:26: ( statement )*
                    loop76:
                    do {
                        int alt76=2;
                        int LA76_0 = input.LA(1);

                        if ( (LA76_0==ID||LA76_0==92||LA76_0==95||LA76_0==103||LA76_0==105) ) {
                            alt76=1;
                        }


                        switch (alt76) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:26: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement2028);
                    	    statement211=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement211.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop76;
                        }
                    } while (true);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:37: ( 'else' ( statement )* )?
                    int alt78=2;
                    int LA78_0 = input.LA(1);

                    if ( (LA78_0==97) ) {
                        alt78=1;
                    }
                    switch (alt78) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:38: 'else' ( statement )*
                            {
                            string_literal212=(Token)match(input,97,FOLLOW_97_in_statement2032); 
                            string_literal212_tree = (Object)adaptor.create(string_literal212);
                            adaptor.addChild(root_0, string_literal212_tree);

                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:45: ( statement )*
                            loop77:
                            do {
                                int alt77=2;
                                int LA77_0 = input.LA(1);

                                if ( (LA77_0==ID||LA77_0==92||LA77_0==95||LA77_0==103||LA77_0==105) ) {
                                    alt77=1;
                                }


                                switch (alt77) {
                            	case 1 :
                            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:45: statement
                            	    {
                            	    pushFollow(FOLLOW_statement_in_statement2034);
                            	    statement213=statement();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, statement213.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop77;
                                }
                            } while (true);


                            }
                            break;

                    }

                    string_literal214=(Token)match(input,86,FOLLOW_86_in_statement2039); 
                    string_literal214_tree = (Object)adaptor.create(string_literal214);
                    adaptor.addChild(root_0, string_literal214_tree);

                      

                    }
                    break;
                case 4 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:289:3: 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal215=(Token)match(input,105,FOLLOW_105_in_statement2045); 
                    string_literal215_tree = (Object)adaptor.create(string_literal215);
                    adaptor.addChild(root_0, string_literal215_tree);

                    pushFollow(FOLLOW_expression_in_statement2047);
                    expression216=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression216.getTree());
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:289:22: ( 'var' varDecls )?
                    int alt79=2;
                    int LA79_0 = input.LA(1);

                    if ( (LA79_0==88) ) {
                        alt79=1;
                    }
                    switch (alt79) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:289:23: 'var' varDecls
                            {
                            string_literal217=(Token)match(input,88,FOLLOW_88_in_statement2050); 
                            string_literal217_tree = (Object)adaptor.create(string_literal217);
                            adaptor.addChild(root_0, string_literal217_tree);

                            pushFollow(FOLLOW_varDecls_in_statement2052);
                            varDecls218=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls218.getTree());

                            }
                            break;

                    }

                    string_literal219=(Token)match(input,81,FOLLOW_81_in_statement2056); 
                    string_literal219_tree = (Object)adaptor.create(string_literal219);
                    adaptor.addChild(root_0, string_literal219_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:289:45: ( statement )*
                    loop80:
                    do {
                        int alt80=2;
                        int LA80_0 = input.LA(1);

                        if ( (LA80_0==ID||LA80_0==92||LA80_0==95||LA80_0==103||LA80_0==105) ) {
                            alt80=1;
                        }


                        switch (alt80) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:289:45: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement2058);
                    	    statement220=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement220.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop80;
                        }
                    } while (true);

                    string_literal221=(Token)match(input,86,FOLLOW_86_in_statement2061); 
                    string_literal221_tree = (Object)adaptor.create(string_literal221);
                    adaptor.addChild(root_0, string_literal221_tree);

                      

                    }
                    break;
                case 5 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:291:3: ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' )
                    {
                    root_0 = (Object)adaptor.nil();

                    ID222=(Token)match(input,ID,FOLLOW_ID_in_statement2068); 
                    ID222_tree = (Object)adaptor.create(ID222);
                    adaptor.addChild(root_0, ID222_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:291:6: ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' )
                    int alt83=2;
                    int LA83_0 = input.LA(1);

                    if ( (LA83_0==77||LA83_0==89) ) {
                        alt83=1;
                    }
                    else if ( (LA83_0==83) ) {
                        alt83=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 83, 0, input);

                        throw nvae;
                    }
                    switch (alt83) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:292:5: ( ( '[' expressions ']' )? ':=' expression ';' )
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:292:5: ( ( '[' expressions ']' )? ':=' expression ';' )
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:292:6: ( '[' expressions ']' )? ':=' expression ';'
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:292:6: ( '[' expressions ']' )?
                            int alt81=2;
                            int LA81_0 = input.LA(1);

                            if ( (LA81_0==77) ) {
                                alt81=1;
                            }
                            switch (alt81) {
                                case 1 :
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:292:7: '[' expressions ']'
                                    {
                                    char_literal223=(Token)match(input,77,FOLLOW_77_in_statement2078); 
                                    char_literal223_tree = (Object)adaptor.create(char_literal223);
                                    adaptor.addChild(root_0, char_literal223_tree);

                                    pushFollow(FOLLOW_expressions_in_statement2080);
                                    expressions224=expressions();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expressions224.getTree());
                                    char_literal225=(Token)match(input,78,FOLLOW_78_in_statement2082); 
                                    char_literal225_tree = (Object)adaptor.create(char_literal225);
                                    adaptor.addChild(root_0, char_literal225_tree);


                                    }
                                    break;

                            }

                            string_literal226=(Token)match(input,89,FOLLOW_89_in_statement2086); 
                            string_literal226_tree = (Object)adaptor.create(string_literal226);
                            adaptor.addChild(root_0, string_literal226_tree);

                            pushFollow(FOLLOW_expression_in_statement2088);
                            expression227=expression();

                            state._fsp--;

                            adaptor.addChild(root_0, expression227.getTree());
                            char_literal228=(Token)match(input,90,FOLLOW_90_in_statement2090); 
                            char_literal228_tree = (Object)adaptor.create(char_literal228);
                            adaptor.addChild(root_0, char_literal228_tree);


                            }

                             

                            }
                            break;
                        case 2 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:293:6: '(' ( expressions )? ')' ';'
                            {
                            char_literal229=(Token)match(input,83,FOLLOW_83_in_statement2100); 
                            char_literal229_tree = (Object)adaptor.create(char_literal229);
                            adaptor.addChild(root_0, char_literal229_tree);

                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:293:10: ( expressions )?
                            int alt82=2;
                            int LA82_0 = input.LA(1);

                            if ( (LA82_0==ID||LA82_0==MINUS||(LA82_0>=NOT && LA82_0<=STRING)||LA82_0==77||LA82_0==83||LA82_0==95||(LA82_0>=98 && LA82_0<=99)) ) {
                                alt82=1;
                            }
                            switch (alt82) {
                                case 1 :
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:293:10: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_statement2102);
                                    expressions230=expressions();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expressions230.getTree());

                                    }
                                    break;

                            }

                            char_literal231=(Token)match(input,84,FOLLOW_84_in_statement2105); 
                            char_literal231_tree = (Object)adaptor.create(char_literal231);
                            adaptor.addChild(root_0, char_literal231_tree);

                            char_literal232=(Token)match(input,90,FOLLOW_90_in_statement2107); 
                            char_literal232_tree = (Object)adaptor.create(char_literal232);
                            adaptor.addChild(root_0, char_literal232_tree);

                             

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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:295:1: typeAttr : ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) ) ;
    public final RVCCalParser.typeAttr_return typeAttr() throws RecognitionException {
        RVCCalParser.typeAttr_return retval = new RVCCalParser.typeAttr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID233=null;
        Token char_literal234=null;
        Token char_literal236=null;
        RVCCalParser.typeDef_return typeDef235 = null;

        RVCCalParser.expression_return expression237 = null;


        Object ID233_tree=null;
        Object char_literal234_tree=null;
        Object char_literal236_tree=null;
        RewriteRuleTokenStream stream_EQ=new RewriteRuleTokenStream(adaptor,"token EQ");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:302:9: ( ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:302:11: ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) )
            {
            ID233=(Token)match(input,ID,FOLLOW_ID_in_typeAttr2128);  
            stream_ID.add(ID233);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:302:14: ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) )
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==76) ) {
                alt85=1;
            }
            else if ( (LA85_0==EQ) ) {
                alt85=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 85, 0, input);

                throw nvae;
            }
            switch (alt85) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:302:15: ':' typeDef
                    {
                    char_literal234=(Token)match(input,76,FOLLOW_76_in_typeAttr2131);  
                    stream_76.add(char_literal234);

                    pushFollow(FOLLOW_typeDef_in_typeAttr2133);
                    typeDef235=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef235.getTree());


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
                    // 302:27: -> ^( TYPE ID typeDef )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:302:30: ^( TYPE ID typeDef )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:302:51: '=' expression
                    {
                    char_literal236=(Token)match(input,EQ,FOLLOW_EQ_in_typeAttr2147);  
                    stream_EQ.add(char_literal236);

                    pushFollow(FOLLOW_expression_in_typeAttr2149);
                    expression237=expression();

                    state._fsp--;

                    stream_expression.add(expression237.getTree());


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
                    // 302:66: -> ^( EXPR ID expression )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:302:69: ^( EXPR ID expression )
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:304:1: typeAttrs : typeAttr ( ',' typeAttr )* -> ( typeAttr )+ ;
    public final RVCCalParser.typeAttrs_return typeAttrs() throws RecognitionException {
        RVCCalParser.typeAttrs_return retval = new RVCCalParser.typeAttrs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal239=null;
        RVCCalParser.typeAttr_return typeAttr238 = null;

        RVCCalParser.typeAttr_return typeAttr240 = null;


        Object char_literal239_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleSubtreeStream stream_typeAttr=new RewriteRuleSubtreeStream(adaptor,"rule typeAttr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:304:10: ( typeAttr ( ',' typeAttr )* -> ( typeAttr )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:304:12: typeAttr ( ',' typeAttr )*
            {
            pushFollow(FOLLOW_typeAttr_in_typeAttrs2168);
            typeAttr238=typeAttr();

            state._fsp--;

            stream_typeAttr.add(typeAttr238.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:304:21: ( ',' typeAttr )*
            loop86:
            do {
                int alt86=2;
                int LA86_0 = input.LA(1);

                if ( (LA86_0==79) ) {
                    alt86=1;
                }


                switch (alt86) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:304:22: ',' typeAttr
            	    {
            	    char_literal239=(Token)match(input,79,FOLLOW_79_in_typeAttrs2171);  
            	    stream_79.add(char_literal239);

            	    pushFollow(FOLLOW_typeAttr_in_typeAttrs2173);
            	    typeAttr240=typeAttr();

            	    state._fsp--;

            	    stream_typeAttr.add(typeAttr240.getTree());

            	    }
            	    break;

            	default :
            	    break loop86;
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
            // 304:37: -> ( typeAttr )+
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:307:1: typeDef : ID ( '(' attrs= typeAttrs ')' )? -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) ;
    public final RVCCalParser.typeDef_return typeDef() throws RecognitionException {
        RVCCalParser.typeDef_return retval = new RVCCalParser.typeDef_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID241=null;
        Token char_literal242=null;
        Token char_literal243=null;
        RVCCalParser.typeAttrs_return attrs = null;


        Object ID241_tree=null;
        Object char_literal242_tree=null;
        Object char_literal243_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_typeAttrs=new RewriteRuleSubtreeStream(adaptor,"rule typeAttrs");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:307:8: ( ID ( '(' attrs= typeAttrs ')' )? -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:307:10: ID ( '(' attrs= typeAttrs ')' )?
            {
            ID241=(Token)match(input,ID,FOLLOW_ID_in_typeDef2190);  
            stream_ID.add(ID241);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:307:13: ( '(' attrs= typeAttrs ')' )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==83) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:307:14: '(' attrs= typeAttrs ')'
                    {
                    char_literal242=(Token)match(input,83,FOLLOW_83_in_typeDef2193);  
                    stream_83.add(char_literal242);

                    pushFollow(FOLLOW_typeAttrs_in_typeDef2197);
                    attrs=typeAttrs();

                    state._fsp--;

                    stream_typeAttrs.add(attrs.getTree());
                    char_literal243=(Token)match(input,84,FOLLOW_84_in_typeDef2199);  
                    stream_84.add(char_literal243);


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
            // 307:40: -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:307:43: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:307:53: ^( TYPE_ATTRS ( $attrs)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_2);

                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:307:66: ( $attrs)?
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:1: varDecl : typeDef ID ( '=' expression | ':=' expression )? ;
    public final RVCCalParser.varDecl_return varDecl() throws RecognitionException {
        RVCCalParser.varDecl_return retval = new RVCCalParser.varDecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID245=null;
        Token char_literal246=null;
        Token string_literal248=null;
        RVCCalParser.typeDef_return typeDef244 = null;

        RVCCalParser.expression_return expression247 = null;

        RVCCalParser.expression_return expression249 = null;


        Object ID245_tree=null;
        Object char_literal246_tree=null;
        Object string_literal248_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:313:8: ( typeDef ID ( '=' expression | ':=' expression )? )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:313:10: typeDef ID ( '=' expression | ':=' expression )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_typeDef_in_varDecl2231);
            typeDef244=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef244.getTree());
            ID245=(Token)match(input,ID,FOLLOW_ID_in_varDecl2233); 
            ID245_tree = (Object)adaptor.create(ID245);
            adaptor.addChild(root_0, ID245_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:313:21: ( '=' expression | ':=' expression )?
            int alt88=3;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==EQ) ) {
                alt88=1;
            }
            else if ( (LA88_0==89) ) {
                alt88=2;
            }
            switch (alt88) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:313:22: '=' expression
                    {
                    char_literal246=(Token)match(input,EQ,FOLLOW_EQ_in_varDecl2236); 
                    char_literal246_tree = (Object)adaptor.create(char_literal246);
                    adaptor.addChild(root_0, char_literal246_tree);

                    pushFollow(FOLLOW_expression_in_varDecl2238);
                    expression247=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression247.getTree());

                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:313:39: ':=' expression
                    {
                    string_literal248=(Token)match(input,89,FOLLOW_89_in_varDecl2242); 
                    string_literal248_tree = (Object)adaptor.create(string_literal248);
                    adaptor.addChild(root_0, string_literal248_tree);

                    pushFollow(FOLLOW_expression_in_varDecl2244);
                    expression249=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression249.getTree());

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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:315:1: varDeclNoExpr : typeDef ID -> ^( VAR typeDef ID ) ;
    public final RVCCalParser.varDeclNoExpr_return varDeclNoExpr() throws RecognitionException {
        RVCCalParser.varDeclNoExpr_return retval = new RVCCalParser.varDeclNoExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID251=null;
        RVCCalParser.typeDef_return typeDef250 = null;


        Object ID251_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:315:14: ( typeDef ID -> ^( VAR typeDef ID ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:315:16: typeDef ID
            {
            pushFollow(FOLLOW_typeDef_in_varDeclNoExpr2255);
            typeDef250=typeDef();

            state._fsp--;

            stream_typeDef.add(typeDef250.getTree());
            ID251=(Token)match(input,ID,FOLLOW_ID_in_varDeclNoExpr2257);  
            stream_ID.add(ID251);



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
            // 315:27: -> ^( VAR typeDef ID )
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:315:30: ^( VAR typeDef ID )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(VAR, "VAR"), root_1);

                adaptor.addChild(root_1, stream_typeDef.nextTree());
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
    // $ANTLR end "varDeclNoExpr"

    public static class varDecls_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "varDecls"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:317:1: varDecls : varDecl ( ',' varDecl )* -> ( varDecl )+ ;
    public final RVCCalParser.varDecls_return varDecls() throws RecognitionException {
        RVCCalParser.varDecls_return retval = new RVCCalParser.varDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal253=null;
        RVCCalParser.varDecl_return varDecl252 = null;

        RVCCalParser.varDecl_return varDecl254 = null;


        Object char_literal253_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleSubtreeStream stream_varDecl=new RewriteRuleSubtreeStream(adaptor,"rule varDecl");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:317:9: ( varDecl ( ',' varDecl )* -> ( varDecl )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:317:11: varDecl ( ',' varDecl )*
            {
            pushFollow(FOLLOW_varDecl_in_varDecls2274);
            varDecl252=varDecl();

            state._fsp--;

            stream_varDecl.add(varDecl252.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:317:19: ( ',' varDecl )*
            loop89:
            do {
                int alt89=2;
                int LA89_0 = input.LA(1);

                if ( (LA89_0==79) ) {
                    alt89=1;
                }


                switch (alt89) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:317:20: ',' varDecl
            	    {
            	    char_literal253=(Token)match(input,79,FOLLOW_79_in_varDecls2277);  
            	    stream_79.add(char_literal253);

            	    pushFollow(FOLLOW_varDecl_in_varDecls2279);
            	    varDecl254=varDecl();

            	    state._fsp--;

            	    stream_varDecl.add(varDecl254.getTree());

            	    }
            	    break;

            	default :
            	    break loop89;
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
            // 317:34: -> ( varDecl )+
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


 

    public static final BitSet FOLLOW_75_in_actionGuards302 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expressions_in_actionGuards304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_actionInput317 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_actionInput319 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_actionInput323 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_idents_in_actionInput325 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_actionInput327 = new BitSet(new long[]{0x0000000000000002L,0x0000000000010000L});
    public static final BitSet FOLLOW_actionRepeat_in_actionInput329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionInput_in_actionInputs340 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_actionInputs343 = new BitSet(new long[]{0x0000002000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_actionInput_in_actionInputs345 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_actionOutput361 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_actionOutput363 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_actionOutput367 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expressions_in_actionOutput369 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_actionOutput371 = new BitSet(new long[]{0x0000000000000002L,0x0000000000010000L});
    public static final BitSet FOLLOW_actionRepeat_in_actionOutput373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionOutput_in_actionOutputs384 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_actionOutputs387 = new BitSet(new long[]{0x0000002000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_actionOutput_in_actionOutputs389 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_80_in_actionRepeat403 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_actionRepeat405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_actionStatements416 = new BitSet(new long[]{0x0000002000000002L,0x0000028090000000L});
    public static final BitSet FOLLOW_statement_in_actionStatements418 = new BitSet(new long[]{0x0000002000000002L,0x0000028090000000L});
    public static final BitSet FOLLOW_actorImport_in_actor436 = new BitSet(new long[]{0x0000000000000000L,0x0000000020040000L});
    public static final BitSet FOLLOW_82_in_actor439 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_actor443 = new BitSet(new long[]{0x0000000000000000L,0x0000000000082000L});
    public static final BitSet FOLLOW_77_in_actor446 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_actor448 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_actor452 = new BitSet(new long[]{0x0000002000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_actorParameters_in_actor454 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_actor457 = new BitSet(new long[]{0x0000002000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_actorPortDecls_in_actor462 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actor465 = new BitSet(new long[]{0x0000002000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_actorPortDecls_in_actor469 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_actor472 = new BitSet(new long[]{0x000003E000000000L,0x000000000040000CL});
    public static final BitSet FOLLOW_actorDeclarations_in_actor475 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actor478 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_actor480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration546 = new BitSet(new long[]{0x0000002000000000L,0x0000000000881000L});
    public static final BitSet FOLLOW_87_in_actorDeclaration557 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration561 = new BitSet(new long[]{0x0000000000000000L,0x0000000000801000L});
    public static final BitSet FOLLOW_76_in_actorDeclaration566 = new BitSet(new long[]{0x000000C000000000L});
    public static final BitSet FOLLOW_ACTION_in_actorDeclaration575 = new BitSet(new long[]{0x0000002000000000L,0x0000000000202000L});
    public static final BitSet FOLLOW_actionInputs_in_actorDeclaration579 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actorDeclaration582 = new BitSet(new long[]{0x0000002000000000L,0x0000000001422800L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration586 = new BitSet(new long[]{0x0000000000000000L,0x0000000001420800L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration591 = new BitSet(new long[]{0x0000000000000000L,0x0000000001420000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration595 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration597 = new BitSet(new long[]{0x0000000000000000L,0x0000000000420000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration601 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INITIALIZE_in_actorDeclaration675 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actorDeclaration677 = new BitSet(new long[]{0x0000002000000000L,0x0000000001422800L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration679 = new BitSet(new long[]{0x0000000000000000L,0x0000000001420800L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration682 = new BitSet(new long[]{0x0000000000000000L,0x0000000001420000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration686 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration688 = new BitSet(new long[]{0x0000000000000000L,0x0000000000420000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration692 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration695 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_actorDeclaration774 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typeAttrs_in_actorDeclaration778 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_actorDeclaration780 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration790 = new BitSet(new long[]{0x0000400000000000L,0x0000000006000000L});
    public static final BitSet FOLLOW_EQ_in_actorDeclaration799 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration801 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_89_in_actorDeclaration837 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration839 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_actorDeclaration901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ACTION_in_actorDeclaration911 = new BitSet(new long[]{0x0000002000000000L,0x0000000000202000L});
    public static final BitSet FOLLOW_actionInputs_in_actorDeclaration913 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actorDeclaration916 = new BitSet(new long[]{0x0000002000000000L,0x0000000001422800L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration918 = new BitSet(new long[]{0x0000000000000000L,0x0000000001420800L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration921 = new BitSet(new long[]{0x0000000000000000L,0x0000000001420000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration925 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration927 = new BitSet(new long[]{0x0000000000000000L,0x0000000000420000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration931 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration934 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INITIALIZE_in_actorDeclaration988 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actorDeclaration990 = new BitSet(new long[]{0x0000002000000000L,0x0000000001422800L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration992 = new BitSet(new long[]{0x0000000000000000L,0x0000000001420800L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration995 = new BitSet(new long[]{0x0000000000000000L,0x0000000001420000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration999 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration1001 = new BitSet(new long[]{0x0000000000000000L,0x0000000000420000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration1005 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration1008 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_priorityOrder_in_actorDeclaration1055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FUNCTION_in_actorDeclaration1064 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration1066 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_actorDeclaration1068 = new BitSet(new long[]{0x0000002000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration1071 = new BitSet(new long[]{0x0000000000000000L,0x0000000000108000L});
    public static final BitSet FOLLOW_79_in_actorDeclaration1074 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration1076 = new BitSet(new long[]{0x0000000000000000L,0x0000000000108000L});
    public static final BitSet FOLLOW_84_in_actorDeclaration1082 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_actorDeclaration1084 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typeDef_in_actorDeclaration1086 = new BitSet(new long[]{0x0000000000000000L,0x0000000001001000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration1093 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration1095 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_actorDeclaration1099 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration1107 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration1113 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROCEDURE_in_actorDeclaration1123 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration1125 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_actorDeclaration1127 = new BitSet(new long[]{0x0000002000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration1130 = new BitSet(new long[]{0x0000000000000000L,0x0000000000108000L});
    public static final BitSet FOLLOW_79_in_actorDeclaration1133 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration1135 = new BitSet(new long[]{0x0000000000000000L,0x0000000000108000L});
    public static final BitSet FOLLOW_84_in_actorDeclaration1141 = new BitSet(new long[]{0x0000000000000000L,0x0000000011000000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration1148 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration1150 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_actorDeclaration1158 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_statement_in_actorDeclaration1160 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration1163 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations1175 = new BitSet(new long[]{0x000003E000000002L,0x000000000000000CL});
    public static final BitSet FOLLOW_schedule_in_actorDeclarations1179 = new BitSet(new long[]{0x000003E000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations1181 = new BitSet(new long[]{0x000003E000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_schedule_in_actorDeclarations1198 = new BitSet(new long[]{0x000003E000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations1200 = new BitSet(new long[]{0x000003E000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_93_in_actorImport1220 = new BitSet(new long[]{0x0000002000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_actorImport1225 = new BitSet(new long[]{0x0000002000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport1227 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_actorImport1229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport1235 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_actorImport1237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_actorParameter1252 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_actorParameter1254 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_EQ_in_actorParameter1257 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_actorParameter1259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters1281 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_actorParameters1284 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters1286 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorPortDecls1305 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_actorPortDecls1308 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorPortDecls1310 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_un_expr_in_expression1331 = new BitSet(new long[]{0x1FFFFC0000000002L});
    public static final BitSet FOLLOW_bop_in_expression1335 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_un_expr_in_expression1337 = new BitSet(new long[]{0x1FFFFC0000000002L});
    public static final BitSet FOLLOW_set_in_bop0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfix_expression_in_un_expr1453 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_un_expr1465 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_NOT_in_un_expr1471 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_NUM_ELTS_in_un_expr1477 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_un_expr_in_un_expr1480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_postfix_expression1500 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1504 = new BitSet(new long[]{0x0000000000000000L,0x0000000000005000L});
    public static final BitSet FOLLOW_76_in_postfix_expression1507 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_expressionGenerators_in_postfix_expression1511 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_postfix_expression1515 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_95_in_postfix_expression1532 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1536 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_postfix_expression1538 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1542 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_97_in_postfix_expression1544 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1548 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_postfix_expression1550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_postfix_expression1569 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_postfix_expression1577 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1579 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_postfix_expression1581 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_postfix_expression1591 = new BitSet(new long[]{0x0000000000000002L,0x0000000000082000L});
    public static final BitSet FOLLOW_83_in_postfix_expression1599 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80182003L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1601 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_postfix_expression1604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_postfix_expression1624 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1626 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_postfix_expression1628 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_98_in_constant1665 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_constant1677 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_constant1689 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_constant1701 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_constant1713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_100_in_expressionGenerator1729 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typeDef_in_expressionGenerator1731 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_expressionGenerator1733 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_expressionGenerator1735 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_expressionGenerator1737 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators1747 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_expressionGenerators1750 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators1752 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_expression_in_expressions1766 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_expressions1769 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_expressions1771 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_idents1790 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_idents1793 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_idents1795 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality1811 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_GT_in_priorityInequality1814 = new BitSet(new long[]{0x0000002000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality1816 = new BitSet(new long[]{0x0002000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_priorityInequality1820 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRIORITY_in_priorityOrder1839 = new BitSet(new long[]{0x0000002000000000L,0x0000000040400000L});
    public static final BitSet FOLLOW_priorityInequality_in_priorityOrder1841 = new BitSet(new long[]{0x0000002000000000L,0x0000000040400000L});
    public static final BitSet FOLLOW_86_in_priorityOrder1844 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent1865 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_qualifiedIdent1868 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent1870 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_SCHEDULE_in_schedule1895 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_102_in_schedule1897 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_schedule1899 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_schedule1901 = new BitSet(new long[]{0x0000002000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_stateTransition_in_schedule1903 = new BitSet(new long[]{0x0000002000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_schedule1906 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stateTransition1929 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_stateTransition1931 = new BitSet(new long[]{0x0000002000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_stateTransition1933 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_stateTransition1935 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_stateTransition1937 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_stateTransition1939 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_stateTransition1941 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_statement1967 = new BitSet(new long[]{0x0000002000000000L,0x0000028091400000L});
    public static final BitSet FOLLOW_88_in_statement1970 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecls_in_statement1972 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_statement1974 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_statement_in_statement1978 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_86_in_statement1981 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_statement1987 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_statement1989 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_statement1991 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_statement1994 = new BitSet(new long[]{0x0000000000000000L,0x0000010001020000L});
    public static final BitSet FOLLOW_104_in_statement1997 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_statement1999 = new BitSet(new long[]{0x0000000000000000L,0x0000000001020000L});
    public static final BitSet FOLLOW_88_in_statement2005 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecls_in_statement2007 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_statement2011 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_statement_in_statement2013 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_86_in_statement2016 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_95_in_statement2022 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_statement2024 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_statement2026 = new BitSet(new long[]{0x0000002000000000L,0x0000028290400000L});
    public static final BitSet FOLLOW_statement_in_statement2028 = new BitSet(new long[]{0x0000002000000000L,0x0000028290400000L});
    public static final BitSet FOLLOW_97_in_statement2032 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_statement_in_statement2034 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_86_in_statement2039 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_statement2045 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_statement2047 = new BitSet(new long[]{0x0000000000000000L,0x0000000001020000L});
    public static final BitSet FOLLOW_88_in_statement2050 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecls_in_statement2052 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_statement2056 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_statement_in_statement2058 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_86_in_statement2061 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_statement2068 = new BitSet(new long[]{0x0000000000000000L,0x0000000002082000L});
    public static final BitSet FOLLOW_77_in_statement2078 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expressions_in_statement2080 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_statement2082 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_statement2086 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_statement2088 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_statement2090 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_statement2100 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80182003L});
    public static final BitSet FOLLOW_expressions_in_statement2102 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_statement2105 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_statement2107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_typeAttr2128 = new BitSet(new long[]{0x0000400000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_typeAttr2131 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typeDef_in_typeAttr2133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EQ_in_typeAttr2147 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_typeAttr2149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs2168 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_typeAttrs2171 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs2173 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_typeDef2190 = new BitSet(new long[]{0x0000000000000002L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_typeDef2193 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typeAttrs_in_typeDef2197 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_typeDef2199 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_varDecl2231 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_varDecl2233 = new BitSet(new long[]{0x0000400000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_EQ_in_varDecl2236 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_varDecl2238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_varDecl2242 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_varDecl2244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_varDeclNoExpr2255 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_varDeclNoExpr2257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varDecl_in_varDecls2274 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_varDecls2277 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecl_in_varDecls2279 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});

}