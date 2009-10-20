// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g 2009-10-21 00:49:47

package net.sf.orcc.frontend.parser.internal;

// @SuppressWarnings({"unchecked", "unused"})


import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.BitSet;
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

@SuppressWarnings({"unchecked", "unused"})
public class RVCCalParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACTOR", "INPUTS", "OUTPUTS", "PARAMETER", "PARAMETERS", "ACTOR_DECLS", "STATE_VAR", "TRANSITION", "TRANSITIONS", "INEQUALITY", "GUARDS", "TAG", "STATEMENTS", "VARS", "EXPR", "EXPR_BINARY", "EXPR_UNARY", "EXPR_LIST", "EXPR_IF", "EXPR_CALL", "EXPR_IDX", "EXPR_VAR", "EXPR_BOOL", "EXPR_FLOAT", "EXPR_INT", "EXPR_STRING", "VAR", "TYPE", "TYPE_ATTRS", "ASSIGNABLE", "NON_ASSIGNABLE", "QID", "ID", "ACTION", "INITIALIZE", "FUNCTION", "PROCEDURE", "OR", "AND", "BITOR", "BITAND", "EQ", "NE", "LT", "GT", "LE", "GE", "SHIFT_LEFT", "SHIFT_RIGHT", "PLUS", "MINUS", "DIV", "DIV_INT", "MOD", "TIMES", "EXP", "NOT", "NUM_ELTS", "FLOAT", "INTEGER", "STRING", "PRIORITY", "SCHEDULE", "LETTER", "Exponent", "EscapeSequence", "OctalEscape", "LINE_COMMENT", "MULTI_LINE_COMMENT", "WHITESPACE", "'guard'", "':'", "'['", "']'", "','", "'repeat'", "'do'", "'actor'", "'('", "')'", "'==>'", "'end'", "'.'", "'var'", "':='", "';'", "'-->'", "'begin'", "'import'", "'all'", "'if'", "'then'", "'else'", "'true'", "'false'", "'for'", "'in'", "'fsm'", "'foreach'", "'..'", "'while'"
    };
    public static final int FUNCTION=39;
    public static final int EXPR_BOOL=26;
    public static final int LT=47;
    public static final int TRANSITION=11;
    public static final int OUTPUTS=6;
    public static final int EXPR_VAR=25;
    public static final int LETTER=67;
    public static final int MOD=57;
    public static final int EXPR_CALL=23;
    public static final int INPUTS=5;
    public static final int NOT=60;
    public static final int EXPR_UNARY=20;
    public static final int EOF=-1;
    public static final int ACTION=37;
    public static final int TYPE=31;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int TYPE_ATTRS=32;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__90=90;
    public static final int EXP=59;
    public static final int PARAMETER=7;
    public static final int STATE_VAR=10;
    public static final int GUARDS=14;
    public static final int VAR=30;
    public static final int EQ=45;
    public static final int T__99=99;
    public static final int T__98=98;
    public static final int T__97=97;
    public static final int T__96=96;
    public static final int T__95=95;
    public static final int NE=46;
    public static final int ASSIGNABLE=33;
    public static final int GE=50;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int INITIALIZE=38;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int LINE_COMMENT=71;
    public static final int DIV_INT=56;
    public static final int WHITESPACE=73;
    public static final int INEQUALITY=13;
    public static final int NON_ASSIGNABLE=34;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int EXPR_IDX=24;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int SHIFT_LEFT=51;
    public static final int SHIFT_RIGHT=52;
    public static final int BITOR=43;
    public static final int PRIORITY=65;
    public static final int ACTOR_DECLS=9;
    public static final int ACTOR=4;
    public static final int OR=41;
    public static final int STATEMENTS=16;
    public static final int GT=48;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int T__74=74;
    public static final int EscapeSequence=69;
    public static final int T__79=79;
    public static final int T__78=78;
    public static final int T__77=77;
    public static final int PARAMETERS=8;
    public static final int EXPR_BINARY=19;
    public static final int SCHEDULE=66;
    public static final int Exponent=68;
    public static final int FLOAT=62;
    public static final int EXPR_FLOAT=27;
    public static final int ID=36;
    public static final int AND=42;
    public static final int BITAND=44;
    public static final int EXPR_LIST=21;
    public static final int EXPR=18;
    public static final int EXPR_STRING=29;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int NUM_ELTS=61;
    public static final int PLUS=53;
    public static final int EXPR_INT=28;
    public static final int INTEGER=63;
    public static final int TRANSITIONS=12;
    public static final int VARS=17;
    public static final int T__102=102;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int MINUS=54;
    public static final int EXPR_IF=22;
    public static final int MULTI_LINE_COMMENT=72;
    public static final int PROCEDURE=40;
    public static final int TAG=15;
    public static final int QID=35;
    public static final int DIV=55;
    public static final int TIMES=58;
    public static final int OctalEscape=70;
    public static final int LE=49;
    public static final int STRING=64;

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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:1: actionGuards : 'guard' expressions -> expressions ;
    public final RVCCalParser.actionGuards_return actionGuards() throws RecognitionException {
        RVCCalParser.actionGuards_return retval = new RVCCalParser.actionGuards_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal1=null;
        RVCCalParser.expressions_return expressions2 = null;


        Object string_literal1_tree=null;
        RewriteRuleTokenStream stream_74=new RewriteRuleTokenStream(adaptor,"token 74");
        RewriteRuleSubtreeStream stream_expressions=new RewriteRuleSubtreeStream(adaptor,"rule expressions");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:13: ( 'guard' expressions -> expressions )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:15: 'guard' expressions
            {
            string_literal1=(Token)match(input,74,FOLLOW_74_in_actionGuards297);  
            stream_74.add(string_literal1);

            pushFollow(FOLLOW_expressions_in_actionGuards299);
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
            // 109:35: -> expressions
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:111:1: actionInput : ( ID ':' )? '[' idents ']' ( actionRepeat )? ;
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
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:111:12: ( ( ID ':' )? '[' idents ']' ( actionRepeat )? )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:2: ( ID ':' )? '[' idents ']' ( actionRepeat )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:2: ( ID ':' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==ID) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:3: ID ':'
                    {
                    ID3=(Token)match(input,ID,FOLLOW_ID_in_actionInput312); 
                    ID3_tree = (Object)adaptor.create(ID3);
                    adaptor.addChild(root_0, ID3_tree);

                    char_literal4=(Token)match(input,75,FOLLOW_75_in_actionInput314); 
                    char_literal4_tree = (Object)adaptor.create(char_literal4);
                    adaptor.addChild(root_0, char_literal4_tree);


                    }
                    break;

            }

            char_literal5=(Token)match(input,76,FOLLOW_76_in_actionInput318); 
            char_literal5_tree = (Object)adaptor.create(char_literal5);
            adaptor.addChild(root_0, char_literal5_tree);

            pushFollow(FOLLOW_idents_in_actionInput320);
            idents6=idents();

            state._fsp--;

            adaptor.addChild(root_0, idents6.getTree());
            char_literal7=(Token)match(input,77,FOLLOW_77_in_actionInput322); 
            char_literal7_tree = (Object)adaptor.create(char_literal7);
            adaptor.addChild(root_0, char_literal7_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:27: ( actionRepeat )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==79) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:27: actionRepeat
                    {
                    pushFollow(FOLLOW_actionRepeat_in_actionInput324);
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:1: actionInputs : actionInput ( ',' actionInput )* -> ( actionInput )+ ;
    public final RVCCalParser.actionInputs_return actionInputs() throws RecognitionException {
        RVCCalParser.actionInputs_return retval = new RVCCalParser.actionInputs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal10=null;
        RVCCalParser.actionInput_return actionInput9 = null;

        RVCCalParser.actionInput_return actionInput11 = null;


        Object char_literal10_tree=null;
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleSubtreeStream stream_actionInput=new RewriteRuleSubtreeStream(adaptor,"rule actionInput");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:13: ( actionInput ( ',' actionInput )* -> ( actionInput )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:15: actionInput ( ',' actionInput )*
            {
            pushFollow(FOLLOW_actionInput_in_actionInputs335);
            actionInput9=actionInput();

            state._fsp--;

            stream_actionInput.add(actionInput9.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:27: ( ',' actionInput )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==78) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:28: ',' actionInput
            	    {
            	    char_literal10=(Token)match(input,78,FOLLOW_78_in_actionInputs338);  
            	    stream_78.add(char_literal10);

            	    pushFollow(FOLLOW_actionInput_in_actionInputs340);
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
            // 115:46: -> ( actionInput )+
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:1: actionOutput : ( ID ':' )? '[' expressions ']' ( actionRepeat )? ;
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
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:13: ( ( ID ':' )? '[' expressions ']' ( actionRepeat )? )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:118:2: ( ID ':' )? '[' expressions ']' ( actionRepeat )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:118:2: ( ID ':' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==ID) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:118:3: ID ':'
                    {
                    ID12=(Token)match(input,ID,FOLLOW_ID_in_actionOutput356); 
                    ID12_tree = (Object)adaptor.create(ID12);
                    adaptor.addChild(root_0, ID12_tree);

                    char_literal13=(Token)match(input,75,FOLLOW_75_in_actionOutput358); 
                    char_literal13_tree = (Object)adaptor.create(char_literal13);
                    adaptor.addChild(root_0, char_literal13_tree);


                    }
                    break;

            }

            char_literal14=(Token)match(input,76,FOLLOW_76_in_actionOutput362); 
            char_literal14_tree = (Object)adaptor.create(char_literal14);
            adaptor.addChild(root_0, char_literal14_tree);

            pushFollow(FOLLOW_expressions_in_actionOutput364);
            expressions15=expressions();

            state._fsp--;

            adaptor.addChild(root_0, expressions15.getTree());
            char_literal16=(Token)match(input,77,FOLLOW_77_in_actionOutput366); 
            char_literal16_tree = (Object)adaptor.create(char_literal16);
            adaptor.addChild(root_0, char_literal16_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:118:32: ( actionRepeat )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==79) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:118:32: actionRepeat
                    {
                    pushFollow(FOLLOW_actionRepeat_in_actionOutput368);
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:1: actionOutputs : actionOutput ( ',' actionOutput )* -> ( actionOutput )+ ;
    public final RVCCalParser.actionOutputs_return actionOutputs() throws RecognitionException {
        RVCCalParser.actionOutputs_return retval = new RVCCalParser.actionOutputs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal19=null;
        RVCCalParser.actionOutput_return actionOutput18 = null;

        RVCCalParser.actionOutput_return actionOutput20 = null;


        Object char_literal19_tree=null;
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleSubtreeStream stream_actionOutput=new RewriteRuleSubtreeStream(adaptor,"rule actionOutput");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:14: ( actionOutput ( ',' actionOutput )* -> ( actionOutput )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:16: actionOutput ( ',' actionOutput )*
            {
            pushFollow(FOLLOW_actionOutput_in_actionOutputs379);
            actionOutput18=actionOutput();

            state._fsp--;

            stream_actionOutput.add(actionOutput18.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:29: ( ',' actionOutput )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==78) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:30: ',' actionOutput
            	    {
            	    char_literal19=(Token)match(input,78,FOLLOW_78_in_actionOutputs382);  
            	    stream_78.add(char_literal19);

            	    pushFollow(FOLLOW_actionOutput_in_actionOutputs384);
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
            // 121:49: -> ( actionOutput )+
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:123:1: actionRepeat : 'repeat' expression -> expression ;
    public final RVCCalParser.actionRepeat_return actionRepeat() throws RecognitionException {
        RVCCalParser.actionRepeat_return retval = new RVCCalParser.actionRepeat_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal21=null;
        RVCCalParser.expression_return expression22 = null;


        Object string_literal21_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:123:13: ( 'repeat' expression -> expression )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:123:15: 'repeat' expression
            {
            string_literal21=(Token)match(input,79,FOLLOW_79_in_actionRepeat398);  
            stream_79.add(string_literal21);

            pushFollow(FOLLOW_expression_in_actionRepeat400);
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
            // 123:35: -> expression
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:1: actionStatements : 'do' ( statement )* -> ( statement )* ;
    public final RVCCalParser.actionStatements_return actionStatements() throws RecognitionException {
        RVCCalParser.actionStatements_return retval = new RVCCalParser.actionStatements_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal23=null;
        RVCCalParser.statement_return statement24 = null;


        Object string_literal23_tree=null;
        RewriteRuleTokenStream stream_80=new RewriteRuleTokenStream(adaptor,"token 80");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:17: ( 'do' ( statement )* -> ( statement )* )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:19: 'do' ( statement )*
            {
            string_literal23=(Token)match(input,80,FOLLOW_80_in_actionStatements411);  
            stream_80.add(string_literal23);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:24: ( statement )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==ID||LA7_0==91||LA7_0==94||LA7_0==102||LA7_0==104) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:24: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_actionStatements413);
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
            // 125:35: -> ( statement )*
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:38: ( statement )*
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:127:1: actor : ( actorImport )* 'actor' id= ID ( '[' ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' actorDeclarations 'end' EOF -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS actorDeclarations ) ;
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
        RewriteRuleTokenStream stream_77=new RewriteRuleTokenStream(adaptor,"token 77");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleTokenStream stream_75=new RewriteRuleTokenStream(adaptor,"token 75");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleTokenStream stream_85=new RewriteRuleTokenStream(adaptor,"token 85");
        RewriteRuleSubtreeStream stream_actorImport=new RewriteRuleSubtreeStream(adaptor,"rule actorImport");
        RewriteRuleSubtreeStream stream_actorDeclarations=new RewriteRuleSubtreeStream(adaptor,"rule actorDeclarations");
        RewriteRuleSubtreeStream stream_actorPortDecls=new RewriteRuleSubtreeStream(adaptor,"rule actorPortDecls");
        RewriteRuleSubtreeStream stream_actorParameters=new RewriteRuleSubtreeStream(adaptor,"rule actorParameters");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:6: ( ( actorImport )* 'actor' id= ID ( '[' ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' actorDeclarations 'end' EOF -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS actorDeclarations ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:8: ( actorImport )* 'actor' id= ID ( '[' ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' actorDeclarations 'end' EOF
            {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:8: ( actorImport )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==92) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:8: actorImport
            	    {
            	    pushFollow(FOLLOW_actorImport_in_actor431);
            	    actorImport25=actorImport();

            	    state._fsp--;

            	    stream_actorImport.add(actorImport25.getTree());

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            string_literal26=(Token)match(input,81,FOLLOW_81_in_actor434);  
            stream_81.add(string_literal26);

            id=(Token)match(input,ID,FOLLOW_ID_in_actor438);  
            stream_ID.add(id);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:35: ( '[' ']' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==76) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:36: '[' ']'
                    {
                    char_literal27=(Token)match(input,76,FOLLOW_76_in_actor441);  
                    stream_76.add(char_literal27);

                    char_literal28=(Token)match(input,77,FOLLOW_77_in_actor443);  
                    stream_77.add(char_literal28);


                    }
                    break;

            }

            char_literal29=(Token)match(input,82,FOLLOW_82_in_actor447);  
            stream_82.add(char_literal29);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:50: ( actorParameters )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==ID) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:50: actorParameters
                    {
                    pushFollow(FOLLOW_actorParameters_in_actor449);
                    actorParameters30=actorParameters();

                    state._fsp--;

                    stream_actorParameters.add(actorParameters30.getTree());

                    }
                    break;

            }

            char_literal31=(Token)match(input,83,FOLLOW_83_in_actor452);  
            stream_83.add(char_literal31);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:8: (inputs= actorPortDecls )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==ID) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:8: inputs= actorPortDecls
                    {
                    pushFollow(FOLLOW_actorPortDecls_in_actor457);
                    inputs=actorPortDecls();

                    state._fsp--;

                    stream_actorPortDecls.add(inputs.getTree());

                    }
                    break;

            }

            string_literal32=(Token)match(input,84,FOLLOW_84_in_actor460);  
            stream_84.add(string_literal32);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:38: (outputs= actorPortDecls )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==ID) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:38: outputs= actorPortDecls
                    {
                    pushFollow(FOLLOW_actorPortDecls_in_actor464);
                    outputs=actorPortDecls();

                    state._fsp--;

                    stream_actorPortDecls.add(outputs.getTree());

                    }
                    break;

            }

            char_literal33=(Token)match(input,75,FOLLOW_75_in_actor467);  
            stream_75.add(char_literal33);

            pushFollow(FOLLOW_actorDeclarations_in_actor470);
            actorDeclarations34=actorDeclarations();

            state._fsp--;

            stream_actorDeclarations.add(actorDeclarations34.getTree());
            string_literal35=(Token)match(input,85,FOLLOW_85_in_actor472);  
            stream_85.add(string_literal35);

            EOF36=(Token)match(input,EOF,FOLLOW_EOF_in_actor474);  
            stream_EOF.add(EOF36);



            // AST REWRITE
            // elements: outputs, actorParameters, 81, inputs, id, actorDeclarations
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
            // 133:2: -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS actorDeclarations )
            {
                adaptor.addChild(root_0, stream_81.nextNode());
                adaptor.addChild(root_0, stream_id.nextNode());
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:2: ^( PARAMETERS ( actorParameters )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETERS, "PARAMETERS"), root_1);

                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:15: ( actorParameters )?
                if ( stream_actorParameters.hasNext() ) {
                    adaptor.addChild(root_1, stream_actorParameters.nextTree());

                }
                stream_actorParameters.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:135:2: ^( INPUTS ( $inputs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_1);

                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:135:11: ( $inputs)?
                if ( stream_inputs.hasNext() ) {
                    adaptor.addChild(root_1, stream_inputs.nextTree());

                }
                stream_inputs.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:2: ^( OUTPUTS ( $outputs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_1);

                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:12: ( $outputs)?
                if ( stream_outputs.hasNext() ) {
                    adaptor.addChild(root_1, stream_outputs.nextTree());

                }
                stream_outputs.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:137:2: ^( ACTOR_DECLS actorDeclarations )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ACTOR_DECLS, "ACTOR_DECLS"), root_1);

                adaptor.addChild(root_1, stream_actorDeclarations.nextTree());

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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:139:1: actorDeclaration : ( ID ( ( ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' ) | ACTION ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | priorityOrder -> priorityOrder | FUNCTION ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end' -> FUNCTION | PROCEDURE ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end' -> PROCEDURE );
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
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_INITIALIZE=new RewriteRuleTokenStream(adaptor,"token INITIALIZE");
        RewriteRuleTokenStream stream_91=new RewriteRuleTokenStream(adaptor,"token 91");
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleTokenStream stream_EQ=new RewriteRuleTokenStream(adaptor,"token EQ");
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_PROCEDURE=new RewriteRuleTokenStream(adaptor,"token PROCEDURE");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_ACTION=new RewriteRuleTokenStream(adaptor,"token ACTION");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleTokenStream stream_88=new RewriteRuleTokenStream(adaptor,"token 88");
        RewriteRuleTokenStream stream_75=new RewriteRuleTokenStream(adaptor,"token 75");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleTokenStream stream_85=new RewriteRuleTokenStream(adaptor,"token 85");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
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
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:142:17: ( ID ( ( ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' ) | ACTION ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | priorityOrder -> priorityOrder | FUNCTION ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end' -> FUNCTION | PROCEDURE ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end' -> PROCEDURE )
            int alt43=6;
            switch ( input.LA(1) ) {
            case ID:
                {
                alt43=1;
                }
                break;
            case ACTION:
                {
                alt43=2;
                }
                break;
            case INITIALIZE:
                {
                alt43=3;
                }
                break;
            case PRIORITY:
                {
                alt43=4;
                }
                break;
            case FUNCTION:
                {
                alt43=5;
                }
                break;
            case PROCEDURE:
                {
                alt43=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 43, 0, input);

                throw nvae;
            }

            switch (alt43) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:147:3: ID ( ( ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' )
                    {
                    ID37=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration539);  
                    stream_ID.add(ID37);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:147:6: ( ( ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' )
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==75||LA26_0==86) ) {
                        alt26=1;
                    }
                    else if ( (LA26_0==ID||LA26_0==82) ) {
                        alt26=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 26, 0, input);

                        throw nvae;
                    }
                    switch (alt26) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:5: ( ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) )
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:5: ( ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) )
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:6: ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) )
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:6: ( ( '.' tag+= ID )* )
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:7: ( '.' tag+= ID )*
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:7: ( '.' tag+= ID )*
                            loop13:
                            do {
                                int alt13=2;
                                int LA13_0 = input.LA(1);

                                if ( (LA13_0==86) ) {
                                    alt13=1;
                                }


                                switch (alt13) {
                            	case 1 :
                            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:8: '.' tag+= ID
                            	    {
                            	    char_literal38=(Token)match(input,86,FOLLOW_86_in_actorDeclaration550);  
                            	    stream_86.add(char_literal38);

                            	    tag=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration554);  
                            	    stream_ID.add(tag);

                            	    if (list_tag==null) list_tag=new ArrayList();
                            	    list_tag.add(tag);


                            	    }
                            	    break;

                            	default :
                            	    break loop13;
                                }
                            } while (true);


                            }

                            char_literal39=(Token)match(input,75,FOLLOW_75_in_actorDeclaration559);  
                            stream_75.add(char_literal39);

                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:7: ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) )
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
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:8: ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                                    {
                                    ACTION40=(Token)match(input,ACTION,FOLLOW_ACTION_in_actorDeclaration568);  
                                    stream_ACTION.add(ACTION40);

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:21: (inputs= actionInputs )?
                                    int alt14=2;
                                    int LA14_0 = input.LA(1);

                                    if ( (LA14_0==ID||LA14_0==76) ) {
                                        alt14=1;
                                    }
                                    switch (alt14) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:21: inputs= actionInputs
                                            {
                                            pushFollow(FOLLOW_actionInputs_in_actorDeclaration572);
                                            inputs=actionInputs();

                                            state._fsp--;

                                            stream_actionInputs.add(inputs.getTree());

                                            }
                                            break;

                                    }

                                    string_literal41=(Token)match(input,84,FOLLOW_84_in_actorDeclaration575);  
                                    stream_84.add(string_literal41);

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:49: (outputs= actionOutputs )?
                                    int alt15=2;
                                    int LA15_0 = input.LA(1);

                                    if ( (LA15_0==ID||LA15_0==76) ) {
                                        alt15=1;
                                    }
                                    switch (alt15) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:49: outputs= actionOutputs
                                            {
                                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration579);
                                            outputs=actionOutputs();

                                            state._fsp--;

                                            stream_actionOutputs.add(outputs.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:71: (guards= actionGuards )?
                                    int alt16=2;
                                    int LA16_0 = input.LA(1);

                                    if ( (LA16_0==74) ) {
                                        alt16=1;
                                    }
                                    switch (alt16) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:71: guards= actionGuards
                                            {
                                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration584);
                                            guards=actionGuards();

                                            state._fsp--;

                                            stream_actionGuards.add(guards.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:86: ( 'var' varDecls )?
                                    int alt17=2;
                                    int LA17_0 = input.LA(1);

                                    if ( (LA17_0==87) ) {
                                        alt17=1;
                                    }
                                    switch (alt17) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:87: 'var' varDecls
                                            {
                                            string_literal42=(Token)match(input,87,FOLLOW_87_in_actorDeclaration588);  
                                            stream_87.add(string_literal42);

                                            pushFollow(FOLLOW_varDecls_in_actorDeclaration590);
                                            varDecls43=varDecls();

                                            state._fsp--;

                                            stream_varDecls.add(varDecls43.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:104: ( actionStatements )?
                                    int alt18=2;
                                    int LA18_0 = input.LA(1);

                                    if ( (LA18_0==80) ) {
                                        alt18=1;
                                    }
                                    switch (alt18) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:104: actionStatements
                                            {
                                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration594);
                                            actionStatements44=actionStatements();

                                            state._fsp--;

                                            stream_actionStatements.add(actionStatements44.getTree());

                                            }
                                            break;

                                    }

                                    string_literal45=(Token)match(input,85,FOLLOW_85_in_actorDeclaration597);  
                                    stream_85.add(string_literal45);



                                    // AST REWRITE
                                    // elements: ID, actionStatements, tag, inputs, ACTION, guards, outputs, varDecls
                                    // token labels: 
                                    // rule labels: retval, guards, inputs, outputs
                                    // token list labels: tag
                                    // rule list labels: 
                                    // wildcard labels: 
                                    retval.tree = root_0;
                                    RewriteRuleTokenStream stream_tag=new RewriteRuleTokenStream(adaptor,"token tag", list_tag);
                                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                                    RewriteRuleSubtreeStream stream_guards=new RewriteRuleSubtreeStream(adaptor,"rule guards",guards!=null?guards.tree:null);
                                    RewriteRuleSubtreeStream stream_inputs=new RewriteRuleSubtreeStream(adaptor,"rule inputs",inputs!=null?inputs.tree:null);
                                    RewriteRuleSubtreeStream stream_outputs=new RewriteRuleSubtreeStream(adaptor,"rule outputs",outputs!=null?outputs.tree:null);

                                    root_0 = (Object)adaptor.nil();
                                    // 150:9: -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                    {
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:12: ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot(stream_ACTION.nextNode(), root_1);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:21: ^( TAG ID ( $tag)* )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TAG, "TAG"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:30: ( $tag)*
                                        while ( stream_tag.hasNext() ) {
                                            adaptor.addChild(root_2, stream_tag.nextNode());

                                        }
                                        stream_tag.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:37: ^( INPUTS ( $inputs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_2);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:46: ( $inputs)?
                                        if ( stream_inputs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_inputs.nextTree());

                                        }
                                        stream_inputs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:56: ^( OUTPUTS ( $outputs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:66: ( $outputs)?
                                        if ( stream_outputs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_outputs.nextTree());

                                        }
                                        stream_outputs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:77: ^( GUARDS ( $guards)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:86: ( $guards)?
                                        if ( stream_guards.hasNext() ) {
                                            adaptor.addChild(root_2, stream_guards.nextTree());

                                        }
                                        stream_guards.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:96: ^( VARS ( varDecls )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARS, "VARS"), root_2);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:103: ( varDecls )?
                                        if ( stream_varDecls.hasNext() ) {
                                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                                        }
                                        stream_varDecls.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:114: ^( STATEMENTS ( actionStatements )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:127: ( actionStatements )?
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
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:7: INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                                    {
                                    INITIALIZE46=(Token)match(input,INITIALIZE,FOLLOW_INITIALIZE_in_actorDeclaration668);  
                                    stream_INITIALIZE.add(INITIALIZE46);

                                    string_literal47=(Token)match(input,84,FOLLOW_84_in_actorDeclaration670);  
                                    stream_84.add(string_literal47);

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:24: ( actionOutputs )?
                                    int alt19=2;
                                    int LA19_0 = input.LA(1);

                                    if ( (LA19_0==ID||LA19_0==76) ) {
                                        alt19=1;
                                    }
                                    switch (alt19) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:24: actionOutputs
                                            {
                                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration672);
                                            actionOutputs48=actionOutputs();

                                            state._fsp--;

                                            stream_actionOutputs.add(actionOutputs48.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:39: ( actionGuards )?
                                    int alt20=2;
                                    int LA20_0 = input.LA(1);

                                    if ( (LA20_0==74) ) {
                                        alt20=1;
                                    }
                                    switch (alt20) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:39: actionGuards
                                            {
                                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration675);
                                            actionGuards49=actionGuards();

                                            state._fsp--;

                                            stream_actionGuards.add(actionGuards49.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:53: ( 'var' varDecls )?
                                    int alt21=2;
                                    int LA21_0 = input.LA(1);

                                    if ( (LA21_0==87) ) {
                                        alt21=1;
                                    }
                                    switch (alt21) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:54: 'var' varDecls
                                            {
                                            string_literal50=(Token)match(input,87,FOLLOW_87_in_actorDeclaration679);  
                                            stream_87.add(string_literal50);

                                            pushFollow(FOLLOW_varDecls_in_actorDeclaration681);
                                            varDecls51=varDecls();

                                            state._fsp--;

                                            stream_varDecls.add(varDecls51.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:71: ( actionStatements )?
                                    int alt22=2;
                                    int LA22_0 = input.LA(1);

                                    if ( (LA22_0==80) ) {
                                        alt22=1;
                                    }
                                    switch (alt22) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:71: actionStatements
                                            {
                                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration685);
                                            actionStatements52=actionStatements();

                                            state._fsp--;

                                            stream_actionStatements.add(actionStatements52.getTree());

                                            }
                                            break;

                                    }

                                    string_literal53=(Token)match(input,85,FOLLOW_85_in_actorDeclaration688);  
                                    stream_85.add(string_literal53);



                                    // AST REWRITE
                                    // elements: actionStatements, tag, outputs, guards, varDecls, INITIALIZE, ID
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
                                    // 153:9: -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                    {
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:12: ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot(stream_INITIALIZE.nextNode(), root_1);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:25: ^( TAG ID ( $tag)* )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TAG, "TAG"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:34: ( $tag)*
                                        while ( stream_tag.hasNext() ) {
                                            adaptor.addChild(root_2, stream_tag.nextNode());

                                        }
                                        stream_tag.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        adaptor.addChild(root_1, (Object)adaptor.create(INPUTS, "INPUTS"));
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:48: ^( OUTPUTS ( $outputs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:58: ( $outputs)?
                                        if ( stream_outputs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_outputs.nextTree());

                                        }
                                        stream_outputs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:69: ^( GUARDS ( $guards)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:78: ( $guards)?
                                        if ( stream_guards.hasNext() ) {
                                            adaptor.addChild(root_2, stream_guards.nextTree());

                                        }
                                        stream_guards.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:88: ^( VARS ( varDecls )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARS, "VARS"), root_2);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:95: ( varDecls )?
                                        if ( stream_varDecls.hasNext() ) {
                                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                                        }
                                        stream_varDecls.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:106: ^( STATEMENTS ( actionStatements )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:119: ( actionStatements )?
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
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:159:5: ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';'
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:159:5: ( '(' attrs= typeAttrs ')' )?
                            int alt24=2;
                            int LA24_0 = input.LA(1);

                            if ( (LA24_0==82) ) {
                                alt24=1;
                            }
                            switch (alt24) {
                                case 1 :
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:159:6: '(' attrs= typeAttrs ')'
                                    {
                                    char_literal54=(Token)match(input,82,FOLLOW_82_in_actorDeclaration767);  
                                    stream_82.add(char_literal54);

                                    pushFollow(FOLLOW_typeAttrs_in_actorDeclaration771);
                                    attrs=typeAttrs();

                                    state._fsp--;

                                    stream_typeAttrs.add(attrs.getTree());
                                    char_literal55=(Token)match(input,83,FOLLOW_83_in_actorDeclaration773);  
                                    stream_83.add(char_literal55);


                                    }
                                    break;

                            }

                            varName=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration783);  
                            stream_ID.add(varName);

                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:5: ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) )
                            int alt25=3;
                            switch ( input.LA(1) ) {
                            case EQ:
                                {
                                alt25=1;
                                }
                                break;
                            case 88:
                                {
                                alt25=2;
                                }
                                break;
                            case 89:
                                {
                                alt25=3;
                                }
                                break;
                            default:
                                NoViableAltException nvae =
                                    new NoViableAltException("", 25, 0, input);

                                throw nvae;
                            }

                            switch (alt25) {
                                case 1 :
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:8: '=' expression
                                    {
                                    char_literal56=(Token)match(input,EQ,FOLLOW_EQ_in_actorDeclaration792);  
                                    stream_EQ.add(char_literal56);

                                    pushFollow(FOLLOW_expression_in_actorDeclaration794);
                                    expression57=expression();

                                    state._fsp--;

                                    stream_expression.add(expression57.getTree());


                                    // AST REWRITE
                                    // elements: attrs, varName, ID, expression
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
                                    // 161:23: -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression )
                                    {
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:26: ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:38: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:48: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:61: ( $attrs)?
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
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:8: ':=' expression
                                    {
                                    string_literal58=(Token)match(input,88,FOLLOW_88_in_actorDeclaration830);  
                                    stream_88.add(string_literal58);

                                    pushFollow(FOLLOW_expression_in_actorDeclaration832);
                                    expression59=expression();

                                    state._fsp--;

                                    stream_expression.add(expression59.getTree());


                                    // AST REWRITE
                                    // elements: ID, attrs, expression, varName
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
                                    // 162:24: -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression )
                                    {
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:27: ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:39: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:49: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:62: ( $attrs)?
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
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:8: 
                                    {

                                    // AST REWRITE
                                    // elements: attrs, varName, ID
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
                                    // 163:8: -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE )
                                    {
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:11: ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:23: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:33: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:46: ( $attrs)?
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

                            char_literal60=(Token)match(input,89,FOLLOW_89_in_actorDeclaration894);  
                            stream_89.add(char_literal60);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:3: ACTION ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                    {
                    ACTION61=(Token)match(input,ACTION,FOLLOW_ACTION_in_actorDeclaration904);  
                    stream_ACTION.add(ACTION61);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:10: ( actionInputs )?
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==ID||LA27_0==76) ) {
                        alt27=1;
                    }
                    switch (alt27) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:10: actionInputs
                            {
                            pushFollow(FOLLOW_actionInputs_in_actorDeclaration906);
                            actionInputs62=actionInputs();

                            state._fsp--;

                            stream_actionInputs.add(actionInputs62.getTree());

                            }
                            break;

                    }

                    string_literal63=(Token)match(input,84,FOLLOW_84_in_actorDeclaration909);  
                    stream_84.add(string_literal63);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:30: ( actionOutputs )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==ID||LA28_0==76) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:30: actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration911);
                            actionOutputs64=actionOutputs();

                            state._fsp--;

                            stream_actionOutputs.add(actionOutputs64.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:45: ( actionGuards )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==74) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:45: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration914);
                            actionGuards65=actionGuards();

                            state._fsp--;

                            stream_actionGuards.add(actionGuards65.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:59: ( 'var' varDecls )?
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==87) ) {
                        alt30=1;
                    }
                    switch (alt30) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:60: 'var' varDecls
                            {
                            string_literal66=(Token)match(input,87,FOLLOW_87_in_actorDeclaration918);  
                            stream_87.add(string_literal66);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration920);
                            varDecls67=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls67.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:77: ( actionStatements )?
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==80) ) {
                        alt31=1;
                    }
                    switch (alt31) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:167:77: actionStatements
                            {
                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration924);
                            actionStatements68=actionStatements();

                            state._fsp--;

                            stream_actionStatements.add(actionStatements68.getTree());

                            }
                            break;

                    }

                    string_literal69=(Token)match(input,85,FOLLOW_85_in_actorDeclaration927);  
                    stream_85.add(string_literal69);



                    // AST REWRITE
                    // elements: outputs, inputs, ACTION, guards, actionStatements, varDecls
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
                    // 168:3: -> ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:6: ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_ACTION.nextNode(), root_1);

                        adaptor.addChild(root_1, (Object)adaptor.create(TAG, "TAG"));
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:19: ^( INPUTS ( $inputs)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_2);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:28: ( $inputs)?
                        if ( stream_inputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_inputs.nextTree());

                        }
                        stream_inputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:38: ^( OUTPUTS ( $outputs)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:48: ( $outputs)?
                        if ( stream_outputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_outputs.nextTree());

                        }
                        stream_outputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:59: ^( GUARDS ( $guards)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:68: ( $guards)?
                        if ( stream_guards.hasNext() ) {
                            adaptor.addChild(root_2, stream_guards.nextTree());

                        }
                        stream_guards.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:78: ^( VARS ( varDecls )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARS, "VARS"), root_2);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:85: ( varDecls )?
                        if ( stream_varDecls.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                        }
                        stream_varDecls.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:96: ^( STATEMENTS ( actionStatements )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:109: ( actionStatements )?
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:171:3: INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                    {
                    INITIALIZE70=(Token)match(input,INITIALIZE,FOLLOW_INITIALIZE_in_actorDeclaration981);  
                    stream_INITIALIZE.add(INITIALIZE70);

                    string_literal71=(Token)match(input,84,FOLLOW_84_in_actorDeclaration983);  
                    stream_84.add(string_literal71);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:171:20: ( actionOutputs )?
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==ID||LA32_0==76) ) {
                        alt32=1;
                    }
                    switch (alt32) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:171:20: actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration985);
                            actionOutputs72=actionOutputs();

                            state._fsp--;

                            stream_actionOutputs.add(actionOutputs72.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:171:35: ( actionGuards )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==74) ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:171:35: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration988);
                            actionGuards73=actionGuards();

                            state._fsp--;

                            stream_actionGuards.add(actionGuards73.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:171:49: ( 'var' varDecls )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==87) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:171:50: 'var' varDecls
                            {
                            string_literal74=(Token)match(input,87,FOLLOW_87_in_actorDeclaration992);  
                            stream_87.add(string_literal74);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration994);
                            varDecls75=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls75.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:171:67: ( actionStatements )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==80) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:171:67: actionStatements
                            {
                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration998);
                            actionStatements76=actionStatements();

                            state._fsp--;

                            stream_actionStatements.add(actionStatements76.getTree());

                            }
                            break;

                    }

                    string_literal77=(Token)match(input,85,FOLLOW_85_in_actorDeclaration1001);  
                    stream_85.add(string_literal77);



                    // AST REWRITE
                    // elements: actionStatements, varDecls, guards, INITIALIZE, outputs
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
                    // 172:3: -> ^( INITIALIZE TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:6: ^( INITIALIZE TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_INITIALIZE.nextNode(), root_1);

                        adaptor.addChild(root_1, (Object)adaptor.create(TAG, "TAG"));
                        adaptor.addChild(root_1, (Object)adaptor.create(INPUTS, "INPUTS"));
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:30: ^( OUTPUTS ( $outputs)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:40: ( $outputs)?
                        if ( stream_outputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_outputs.nextTree());

                        }
                        stream_outputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:51: ^( GUARDS ( $guards)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:60: ( $guards)?
                        if ( stream_guards.hasNext() ) {
                            adaptor.addChild(root_2, stream_guards.nextTree());

                        }
                        stream_guards.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:70: ^( VARS ( varDecls )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARS, "VARS"), root_2);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:77: ( varDecls )?
                        if ( stream_varDecls.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                        }
                        stream_varDecls.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:88: ^( STATEMENTS ( actionStatements )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:101: ( actionStatements )?
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:174:3: priorityOrder
                    {
                    pushFollow(FOLLOW_priorityOrder_in_actorDeclaration1048);
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
                    // 174:17: -> priorityOrder
                    {
                        adaptor.addChild(root_0, stream_priorityOrder.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:3: FUNCTION ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end'
                    {
                    FUNCTION79=(Token)match(input,FUNCTION,FOLLOW_FUNCTION_in_actorDeclaration1057);  
                    stream_FUNCTION.add(FUNCTION79);

                    ID80=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration1059);  
                    stream_ID.add(ID80);

                    char_literal81=(Token)match(input,82,FOLLOW_82_in_actorDeclaration1061);  
                    stream_82.add(char_literal81);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:19: ( varDeclNoExpr ( ',' varDeclNoExpr )* )?
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==ID) ) {
                        alt37=1;
                    }
                    switch (alt37) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:20: varDeclNoExpr ( ',' varDeclNoExpr )*
                            {
                            pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration1064);
                            varDeclNoExpr82=varDeclNoExpr();

                            state._fsp--;

                            stream_varDeclNoExpr.add(varDeclNoExpr82.getTree());
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:34: ( ',' varDeclNoExpr )*
                            loop36:
                            do {
                                int alt36=2;
                                int LA36_0 = input.LA(1);

                                if ( (LA36_0==78) ) {
                                    alt36=1;
                                }


                                switch (alt36) {
                            	case 1 :
                            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:35: ',' varDeclNoExpr
                            	    {
                            	    char_literal83=(Token)match(input,78,FOLLOW_78_in_actorDeclaration1067);  
                            	    stream_78.add(char_literal83);

                            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration1069);
                            	    varDeclNoExpr84=varDeclNoExpr();

                            	    state._fsp--;

                            	    stream_varDeclNoExpr.add(varDeclNoExpr84.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop36;
                                }
                            } while (true);


                            }
                            break;

                    }

                    char_literal85=(Token)match(input,83,FOLLOW_83_in_actorDeclaration1075);  
                    stream_83.add(char_literal85);

                    string_literal86=(Token)match(input,90,FOLLOW_90_in_actorDeclaration1077);  
                    stream_90.add(string_literal86);

                    pushFollow(FOLLOW_typeDef_in_actorDeclaration1079);
                    typeDef87=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef87.getTree());
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:177:5: ( 'var' varDecls )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==87) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:177:6: 'var' varDecls
                            {
                            string_literal88=(Token)match(input,87,FOLLOW_87_in_actorDeclaration1086);  
                            stream_87.add(string_literal88);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration1088);
                            varDecls89=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls89.getTree());

                            }
                            break;

                    }

                    char_literal90=(Token)match(input,75,FOLLOW_75_in_actorDeclaration1092);  
                    stream_75.add(char_literal90);

                    pushFollow(FOLLOW_expression_in_actorDeclaration1100);
                    expression91=expression();

                    state._fsp--;

                    stream_expression.add(expression91.getTree());
                    string_literal92=(Token)match(input,85,FOLLOW_85_in_actorDeclaration1106);  
                    stream_85.add(string_literal92);



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
                    // 180:2: -> FUNCTION
                    {
                        adaptor.addChild(root_0, stream_FUNCTION.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 6 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:182:3: PROCEDURE ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end'
                    {
                    PROCEDURE93=(Token)match(input,PROCEDURE,FOLLOW_PROCEDURE_in_actorDeclaration1116);  
                    stream_PROCEDURE.add(PROCEDURE93);

                    ID94=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration1118);  
                    stream_ID.add(ID94);

                    char_literal95=(Token)match(input,82,FOLLOW_82_in_actorDeclaration1120);  
                    stream_82.add(char_literal95);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:182:20: ( varDeclNoExpr ( ',' varDeclNoExpr )* )?
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( (LA40_0==ID) ) {
                        alt40=1;
                    }
                    switch (alt40) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:182:21: varDeclNoExpr ( ',' varDeclNoExpr )*
                            {
                            pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration1123);
                            varDeclNoExpr96=varDeclNoExpr();

                            state._fsp--;

                            stream_varDeclNoExpr.add(varDeclNoExpr96.getTree());
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:182:35: ( ',' varDeclNoExpr )*
                            loop39:
                            do {
                                int alt39=2;
                                int LA39_0 = input.LA(1);

                                if ( (LA39_0==78) ) {
                                    alt39=1;
                                }


                                switch (alt39) {
                            	case 1 :
                            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:182:36: ',' varDeclNoExpr
                            	    {
                            	    char_literal97=(Token)match(input,78,FOLLOW_78_in_actorDeclaration1126);  
                            	    stream_78.add(char_literal97);

                            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration1128);
                            	    varDeclNoExpr98=varDeclNoExpr();

                            	    state._fsp--;

                            	    stream_varDeclNoExpr.add(varDeclNoExpr98.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop39;
                                }
                            } while (true);


                            }
                            break;

                    }

                    char_literal99=(Token)match(input,83,FOLLOW_83_in_actorDeclaration1134);  
                    stream_83.add(char_literal99);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:5: ( 'var' varDecls )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==87) ) {
                        alt41=1;
                    }
                    switch (alt41) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:6: 'var' varDecls
                            {
                            string_literal100=(Token)match(input,87,FOLLOW_87_in_actorDeclaration1141);  
                            stream_87.add(string_literal100);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration1143);
                            varDecls101=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls101.getTree());

                            }
                            break;

                    }

                    string_literal102=(Token)match(input,91,FOLLOW_91_in_actorDeclaration1151);  
                    stream_91.add(string_literal102);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:13: ( statement )*
                    loop42:
                    do {
                        int alt42=2;
                        int LA42_0 = input.LA(1);

                        if ( (LA42_0==ID||LA42_0==91||LA42_0==94||LA42_0==102||LA42_0==104) ) {
                            alt42=1;
                        }


                        switch (alt42) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:13: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_actorDeclaration1153);
                    	    statement103=statement();

                    	    state._fsp--;

                    	    stream_statement.add(statement103.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop42;
                        }
                    } while (true);

                    string_literal104=(Token)match(input,85,FOLLOW_85_in_actorDeclaration1156);  
                    stream_85.add(string_literal104);



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
                    // 185:2: -> PROCEDURE
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:187:1: actorDeclarations : ( actorDeclaration )* ( schedule ( actorDeclaration )* )? -> ( actorDeclaration )* ( schedule )? ;
    public final RVCCalParser.actorDeclarations_return actorDeclarations() throws RecognitionException {
        RVCCalParser.actorDeclarations_return retval = new RVCCalParser.actorDeclarations_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        RVCCalParser.actorDeclaration_return actorDeclaration105 = null;

        RVCCalParser.schedule_return schedule106 = null;

        RVCCalParser.actorDeclaration_return actorDeclaration107 = null;


        RewriteRuleSubtreeStream stream_schedule=new RewriteRuleSubtreeStream(adaptor,"rule schedule");
        RewriteRuleSubtreeStream stream_actorDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule actorDeclaration");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:187:18: ( ( actorDeclaration )* ( schedule ( actorDeclaration )* )? -> ( actorDeclaration )* ( schedule )? )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:187:20: ( actorDeclaration )* ( schedule ( actorDeclaration )* )?
            {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:187:20: ( actorDeclaration )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( ((LA44_0>=ID && LA44_0<=PROCEDURE)||LA44_0==PRIORITY) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:187:20: actorDeclaration
            	    {
            	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations1168);
            	    actorDeclaration105=actorDeclaration();

            	    state._fsp--;

            	    stream_actorDeclaration.add(actorDeclaration105.getTree());

            	    }
            	    break;

            	default :
            	    break loop44;
                }
            } while (true);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:187:38: ( schedule ( actorDeclaration )* )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==SCHEDULE) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:187:39: schedule ( actorDeclaration )*
                    {
                    pushFollow(FOLLOW_schedule_in_actorDeclarations1172);
                    schedule106=schedule();

                    state._fsp--;

                    stream_schedule.add(schedule106.getTree());
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:187:48: ( actorDeclaration )*
                    loop45:
                    do {
                        int alt45=2;
                        int LA45_0 = input.LA(1);

                        if ( ((LA45_0>=ID && LA45_0<=PROCEDURE)||LA45_0==PRIORITY) ) {
                            alt45=1;
                        }


                        switch (alt45) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:187:48: actorDeclaration
                    	    {
                    	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations1174);
                    	    actorDeclaration107=actorDeclaration();

                    	    state._fsp--;

                    	    stream_actorDeclaration.add(actorDeclaration107.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop45;
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
            // 187:68: -> ( actorDeclaration )* ( schedule )?
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:187:71: ( actorDeclaration )*
                while ( stream_actorDeclaration.hasNext() ) {
                    adaptor.addChild(root_0, stream_actorDeclaration.nextTree());

                }
                stream_actorDeclaration.reset();
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:187:89: ( schedule )?
                if ( stream_schedule.hasNext() ) {
                    adaptor.addChild(root_0, stream_schedule.nextTree());

                }
                stream_schedule.reset();

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
    // $ANTLR end "actorDeclarations"

    public static class actorImport_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actorImport"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:1: actorImport : 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) ;
    public final RVCCalParser.actorImport_return actorImport() throws RecognitionException {
        RVCCalParser.actorImport_return retval = new RVCCalParser.actorImport_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal108=null;
        Token string_literal109=null;
        Token char_literal111=null;
        Token char_literal113=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent110 = null;

        RVCCalParser.qualifiedIdent_return qualifiedIdent112 = null;


        Object string_literal108_tree=null;
        Object string_literal109_tree=null;
        Object char_literal111_tree=null;
        Object char_literal113_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:192:12: ( 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:192:14: 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            {
            root_0 = (Object)adaptor.nil();

            string_literal108=(Token)match(input,92,FOLLOW_92_in_actorImport1197); 
            string_literal108_tree = (Object)adaptor.create(string_literal108);
            adaptor.addChild(root_0, string_literal108_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:193:3: ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==93) ) {
                alt47=1;
            }
            else if ( (LA47_0==ID) ) {
                alt47=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 47, 0, input);

                throw nvae;
            }
            switch (alt47) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:193:4: 'all' qualifiedIdent ';'
                    {
                    string_literal109=(Token)match(input,93,FOLLOW_93_in_actorImport1202); 
                    string_literal109_tree = (Object)adaptor.create(string_literal109);
                    adaptor.addChild(root_0, string_literal109_tree);

                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport1204);
                    qualifiedIdent110=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent110.getTree());
                    char_literal111=(Token)match(input,89,FOLLOW_89_in_actorImport1206); 
                    char_literal111_tree = (Object)adaptor.create(char_literal111);
                    adaptor.addChild(root_0, char_literal111_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:194:3: qualifiedIdent ';'
                    {
                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport1212);
                    qualifiedIdent112=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent112.getTree());
                    char_literal113=(Token)match(input,89,FOLLOW_89_in_actorImport1214); 
                    char_literal113_tree = (Object)adaptor.create(char_literal113);
                    adaptor.addChild(root_0, char_literal113_tree);

                     

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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:196:1: actorParameter : typeDef ID ( '=' expression )? -> ^( PARAMETER typeDef ID ( expression )? ) ;
    public final RVCCalParser.actorParameter_return actorParameter() throws RecognitionException {
        RVCCalParser.actorParameter_return retval = new RVCCalParser.actorParameter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID115=null;
        Token char_literal116=null;
        RVCCalParser.typeDef_return typeDef114 = null;

        RVCCalParser.expression_return expression117 = null;


        Object ID115_tree=null;
        Object char_literal116_tree=null;
        RewriteRuleTokenStream stream_EQ=new RewriteRuleTokenStream(adaptor,"token EQ");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:198:15: ( typeDef ID ( '=' expression )? -> ^( PARAMETER typeDef ID ( expression )? ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:199:2: typeDef ID ( '=' expression )?
            {
            pushFollow(FOLLOW_typeDef_in_actorParameter1229);
            typeDef114=typeDef();

            state._fsp--;

            stream_typeDef.add(typeDef114.getTree());
            ID115=(Token)match(input,ID,FOLLOW_ID_in_actorParameter1231);  
            stream_ID.add(ID115);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:199:13: ( '=' expression )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==EQ) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:199:14: '=' expression
                    {
                    char_literal116=(Token)match(input,EQ,FOLLOW_EQ_in_actorParameter1234);  
                    stream_EQ.add(char_literal116);

                    pushFollow(FOLLOW_expression_in_actorParameter1236);
                    expression117=expression();

                    state._fsp--;

                    stream_expression.add(expression117.getTree());

                    }
                    break;

            }



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
            // 199:31: -> ^( PARAMETER typeDef ID ( expression )? )
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:199:34: ^( PARAMETER typeDef ID ( expression )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETER, "PARAMETER"), root_1);

                adaptor.addChild(root_1, stream_typeDef.nextTree());
                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:199:57: ( expression )?
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:201:1: actorParameters : actorParameter ( ',' actorParameter )* -> ( actorParameter )+ ;
    public final RVCCalParser.actorParameters_return actorParameters() throws RecognitionException {
        RVCCalParser.actorParameters_return retval = new RVCCalParser.actorParameters_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal119=null;
        RVCCalParser.actorParameter_return actorParameter118 = null;

        RVCCalParser.actorParameter_return actorParameter120 = null;


        Object char_literal119_tree=null;
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleSubtreeStream stream_actorParameter=new RewriteRuleSubtreeStream(adaptor,"rule actorParameter");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:201:16: ( actorParameter ( ',' actorParameter )* -> ( actorParameter )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:201:18: actorParameter ( ',' actorParameter )*
            {
            pushFollow(FOLLOW_actorParameter_in_actorParameters1258);
            actorParameter118=actorParameter();

            state._fsp--;

            stream_actorParameter.add(actorParameter118.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:201:33: ( ',' actorParameter )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==78) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:201:34: ',' actorParameter
            	    {
            	    char_literal119=(Token)match(input,78,FOLLOW_78_in_actorParameters1261);  
            	    stream_78.add(char_literal119);

            	    pushFollow(FOLLOW_actorParameter_in_actorParameters1263);
            	    actorParameter120=actorParameter();

            	    state._fsp--;

            	    stream_actorParameter.add(actorParameter120.getTree());

            	    }
            	    break;

            	default :
            	    break loop49;
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
            // 201:55: -> ( actorParameter )+
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:203:1: actorPortDecls : varDeclNoExpr ( ',' varDeclNoExpr )* -> ( varDeclNoExpr )+ ;
    public final RVCCalParser.actorPortDecls_return actorPortDecls() throws RecognitionException {
        RVCCalParser.actorPortDecls_return retval = new RVCCalParser.actorPortDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal122=null;
        RVCCalParser.varDeclNoExpr_return varDeclNoExpr121 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr123 = null;


        Object char_literal122_tree=null;
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleSubtreeStream stream_varDeclNoExpr=new RewriteRuleSubtreeStream(adaptor,"rule varDeclNoExpr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:206:15: ( varDeclNoExpr ( ',' varDeclNoExpr )* -> ( varDeclNoExpr )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:206:17: varDeclNoExpr ( ',' varDeclNoExpr )*
            {
            pushFollow(FOLLOW_varDeclNoExpr_in_actorPortDecls1282);
            varDeclNoExpr121=varDeclNoExpr();

            state._fsp--;

            stream_varDeclNoExpr.add(varDeclNoExpr121.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:206:31: ( ',' varDeclNoExpr )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==78) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:206:32: ',' varDeclNoExpr
            	    {
            	    char_literal122=(Token)match(input,78,FOLLOW_78_in_actorPortDecls1285);  
            	    stream_78.add(char_literal122);

            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorPortDecls1287);
            	    varDeclNoExpr123=varDeclNoExpr();

            	    state._fsp--;

            	    stream_varDeclNoExpr.add(varDeclNoExpr123.getTree());

            	    }
            	    break;

            	default :
            	    break loop50;
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
            // 206:52: -> ( varDeclNoExpr )+
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:208:1: expression : e1= and_expr ( ( OR e2= and_expr )+ -> ^( EXPR_BINARY $e1 ( OR $e2)+ ) | -> $e1) ;
    public final RVCCalParser.expression_return expression() throws RecognitionException {
        RVCCalParser.expression_return retval = new RVCCalParser.expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OR124=null;
        RVCCalParser.and_expr_return e1 = null;

        RVCCalParser.and_expr_return e2 = null;


        Object OR124_tree=null;
        RewriteRuleTokenStream stream_OR=new RewriteRuleTokenStream(adaptor,"token OR");
        RewriteRuleSubtreeStream stream_and_expr=new RewriteRuleSubtreeStream(adaptor,"rule and_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:11: (e1= and_expr ( ( OR e2= and_expr )+ -> ^( EXPR_BINARY $e1 ( OR $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:13: e1= and_expr ( ( OR e2= and_expr )+ -> ^( EXPR_BINARY $e1 ( OR $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_and_expr_in_expression1312);
            e1=and_expr();

            state._fsp--;

            stream_and_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:25: ( ( OR e2= and_expr )+ -> ^( EXPR_BINARY $e1 ( OR $e2)+ ) | -> $e1)
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==OR) ) {
                alt52=1;
            }
            else if ( ((LA52_0>=74 && LA52_0<=75)||(LA52_0>=77 && LA52_0<=78)||LA52_0==80||(LA52_0>=83 && LA52_0<=85)||LA52_0==87||LA52_0==89||LA52_0==91||(LA52_0>=95 && LA52_0<=96)||LA52_0==103) ) {
                alt52=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 52, 0, input);

                throw nvae;
            }
            switch (alt52) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:26: ( OR e2= and_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:26: ( OR e2= and_expr )+
                    int cnt51=0;
                    loop51:
                    do {
                        int alt51=2;
                        int LA51_0 = input.LA(1);

                        if ( (LA51_0==OR) ) {
                            alt51=1;
                        }


                        switch (alt51) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:27: OR e2= and_expr
                    	    {
                    	    OR124=(Token)match(input,OR,FOLLOW_OR_in_expression1316);  
                    	    stream_OR.add(OR124);

                    	    pushFollow(FOLLOW_and_expr_in_expression1320);
                    	    e2=and_expr();

                    	    state._fsp--;

                    	    stream_and_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt51 >= 1 ) break loop51;
                                EarlyExitException eee =
                                    new EarlyExitException(51, input);
                                throw eee;
                        }
                        cnt51++;
                    } while (true);



                    // AST REWRITE
                    // elements: e1, e2, OR
                    // token labels: 
                    // rule labels: retval, e1, e2
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
                    RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 215:44: -> ^( EXPR_BINARY $e1 ( OR $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:47: ^( EXPR_BINARY $e1 ( OR $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BINARY, "EXPR_BINARY"), root_1);

                        adaptor.addChild(root_1, stream_e1.nextTree());
                        if ( !(stream_e2.hasNext()||stream_OR.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_e2.hasNext()||stream_OR.hasNext() ) {
                            adaptor.addChild(root_1, stream_OR.nextNode());
                            adaptor.addChild(root_1, stream_e2.nextTree());

                        }
                        stream_e2.reset();
                        stream_OR.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:78: 
                    {

                    // AST REWRITE
                    // elements: e1
                    // token labels: 
                    // rule labels: retval, e1
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 215:78: -> $e1
                    {
                        adaptor.addChild(root_0, stream_e1.nextTree());

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

    public static class and_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "and_expr"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:217:1: and_expr : e1= bitor_expr ( ( AND e2= bitor_expr )+ -> ^( EXPR_BINARY $e1 ( AND $e2)+ ) | -> $e1) ;
    public final RVCCalParser.and_expr_return and_expr() throws RecognitionException {
        RVCCalParser.and_expr_return retval = new RVCCalParser.and_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token AND125=null;
        RVCCalParser.bitor_expr_return e1 = null;

        RVCCalParser.bitor_expr_return e2 = null;


        Object AND125_tree=null;
        RewriteRuleTokenStream stream_AND=new RewriteRuleTokenStream(adaptor,"token AND");
        RewriteRuleSubtreeStream stream_bitor_expr=new RewriteRuleSubtreeStream(adaptor,"rule bitor_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:217:9: (e1= bitor_expr ( ( AND e2= bitor_expr )+ -> ^( EXPR_BINARY $e1 ( AND $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:217:11: e1= bitor_expr ( ( AND e2= bitor_expr )+ -> ^( EXPR_BINARY $e1 ( AND $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_bitor_expr_in_and_expr1357);
            e1=bitor_expr();

            state._fsp--;

            stream_bitor_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:217:25: ( ( AND e2= bitor_expr )+ -> ^( EXPR_BINARY $e1 ( AND $e2)+ ) | -> $e1)
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==AND) ) {
                alt54=1;
            }
            else if ( (LA54_0==OR||(LA54_0>=74 && LA54_0<=75)||(LA54_0>=77 && LA54_0<=78)||LA54_0==80||(LA54_0>=83 && LA54_0<=85)||LA54_0==87||LA54_0==89||LA54_0==91||(LA54_0>=95 && LA54_0<=96)||LA54_0==103) ) {
                alt54=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 54, 0, input);

                throw nvae;
            }
            switch (alt54) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:217:26: ( AND e2= bitor_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:217:26: ( AND e2= bitor_expr )+
                    int cnt53=0;
                    loop53:
                    do {
                        int alt53=2;
                        int LA53_0 = input.LA(1);

                        if ( (LA53_0==AND) ) {
                            alt53=1;
                        }


                        switch (alt53) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:217:27: AND e2= bitor_expr
                    	    {
                    	    AND125=(Token)match(input,AND,FOLLOW_AND_in_and_expr1361);  
                    	    stream_AND.add(AND125);

                    	    pushFollow(FOLLOW_bitor_expr_in_and_expr1365);
                    	    e2=bitor_expr();

                    	    state._fsp--;

                    	    stream_bitor_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt53 >= 1 ) break loop53;
                                EarlyExitException eee =
                                    new EarlyExitException(53, input);
                                throw eee;
                        }
                        cnt53++;
                    } while (true);



                    // AST REWRITE
                    // elements: AND, e1, e2
                    // token labels: 
                    // rule labels: retval, e1, e2
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
                    RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 217:47: -> ^( EXPR_BINARY $e1 ( AND $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:217:50: ^( EXPR_BINARY $e1 ( AND $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BINARY, "EXPR_BINARY"), root_1);

                        adaptor.addChild(root_1, stream_e1.nextTree());
                        if ( !(stream_AND.hasNext()||stream_e2.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_AND.hasNext()||stream_e2.hasNext() ) {
                            adaptor.addChild(root_1, stream_AND.nextNode());
                            adaptor.addChild(root_1, stream_e2.nextTree());

                        }
                        stream_AND.reset();
                        stream_e2.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:217:82: 
                    {

                    // AST REWRITE
                    // elements: e1
                    // token labels: 
                    // rule labels: retval, e1
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 217:82: -> $e1
                    {
                        adaptor.addChild(root_0, stream_e1.nextTree());

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
    // $ANTLR end "and_expr"

    public static class bitor_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bitor_expr"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:1: bitor_expr : e1= bitand_expr ( ( BITOR e2= bitand_expr )+ -> ^( EXPR_BINARY $e1 ( BITOR $e2)+ ) | -> $e1) ;
    public final RVCCalParser.bitor_expr_return bitor_expr() throws RecognitionException {
        RVCCalParser.bitor_expr_return retval = new RVCCalParser.bitor_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token BITOR126=null;
        RVCCalParser.bitand_expr_return e1 = null;

        RVCCalParser.bitand_expr_return e2 = null;


        Object BITOR126_tree=null;
        RewriteRuleTokenStream stream_BITOR=new RewriteRuleTokenStream(adaptor,"token BITOR");
        RewriteRuleSubtreeStream stream_bitand_expr=new RewriteRuleSubtreeStream(adaptor,"rule bitand_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:11: (e1= bitand_expr ( ( BITOR e2= bitand_expr )+ -> ^( EXPR_BINARY $e1 ( BITOR $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:13: e1= bitand_expr ( ( BITOR e2= bitand_expr )+ -> ^( EXPR_BINARY $e1 ( BITOR $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_bitand_expr_in_bitor_expr1402);
            e1=bitand_expr();

            state._fsp--;

            stream_bitand_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:28: ( ( BITOR e2= bitand_expr )+ -> ^( EXPR_BINARY $e1 ( BITOR $e2)+ ) | -> $e1)
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==BITOR) ) {
                alt56=1;
            }
            else if ( ((LA56_0>=OR && LA56_0<=AND)||(LA56_0>=74 && LA56_0<=75)||(LA56_0>=77 && LA56_0<=78)||LA56_0==80||(LA56_0>=83 && LA56_0<=85)||LA56_0==87||LA56_0==89||LA56_0==91||(LA56_0>=95 && LA56_0<=96)||LA56_0==103) ) {
                alt56=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 56, 0, input);

                throw nvae;
            }
            switch (alt56) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:29: ( BITOR e2= bitand_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:29: ( BITOR e2= bitand_expr )+
                    int cnt55=0;
                    loop55:
                    do {
                        int alt55=2;
                        int LA55_0 = input.LA(1);

                        if ( (LA55_0==BITOR) ) {
                            alt55=1;
                        }


                        switch (alt55) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:30: BITOR e2= bitand_expr
                    	    {
                    	    BITOR126=(Token)match(input,BITOR,FOLLOW_BITOR_in_bitor_expr1406);  
                    	    stream_BITOR.add(BITOR126);

                    	    pushFollow(FOLLOW_bitand_expr_in_bitor_expr1410);
                    	    e2=bitand_expr();

                    	    state._fsp--;

                    	    stream_bitand_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt55 >= 1 ) break loop55;
                                EarlyExitException eee =
                                    new EarlyExitException(55, input);
                                throw eee;
                        }
                        cnt55++;
                    } while (true);



                    // AST REWRITE
                    // elements: BITOR, e2, e1
                    // token labels: 
                    // rule labels: retval, e1, e2
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
                    RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 219:53: -> ^( EXPR_BINARY $e1 ( BITOR $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:56: ^( EXPR_BINARY $e1 ( BITOR $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BINARY, "EXPR_BINARY"), root_1);

                        adaptor.addChild(root_1, stream_e1.nextTree());
                        if ( !(stream_BITOR.hasNext()||stream_e2.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_BITOR.hasNext()||stream_e2.hasNext() ) {
                            adaptor.addChild(root_1, stream_BITOR.nextNode());
                            adaptor.addChild(root_1, stream_e2.nextTree());

                        }
                        stream_BITOR.reset();
                        stream_e2.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:90: 
                    {

                    // AST REWRITE
                    // elements: e1
                    // token labels: 
                    // rule labels: retval, e1
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 219:90: -> $e1
                    {
                        adaptor.addChild(root_0, stream_e1.nextTree());

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
    // $ANTLR end "bitor_expr"

    public static class bitand_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bitand_expr"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:1: bitand_expr : e1= eq_expr ( ( BITAND e2= eq_expr )+ -> ^( EXPR_BINARY $e1 ( BITAND $e2)+ ) | -> $e1) ;
    public final RVCCalParser.bitand_expr_return bitand_expr() throws RecognitionException {
        RVCCalParser.bitand_expr_return retval = new RVCCalParser.bitand_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token BITAND127=null;
        RVCCalParser.eq_expr_return e1 = null;

        RVCCalParser.eq_expr_return e2 = null;


        Object BITAND127_tree=null;
        RewriteRuleTokenStream stream_BITAND=new RewriteRuleTokenStream(adaptor,"token BITAND");
        RewriteRuleSubtreeStream stream_eq_expr=new RewriteRuleSubtreeStream(adaptor,"rule eq_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:12: (e1= eq_expr ( ( BITAND e2= eq_expr )+ -> ^( EXPR_BINARY $e1 ( BITAND $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:14: e1= eq_expr ( ( BITAND e2= eq_expr )+ -> ^( EXPR_BINARY $e1 ( BITAND $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_eq_expr_in_bitand_expr1447);
            e1=eq_expr();

            state._fsp--;

            stream_eq_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:25: ( ( BITAND e2= eq_expr )+ -> ^( EXPR_BINARY $e1 ( BITAND $e2)+ ) | -> $e1)
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==BITAND) ) {
                alt58=1;
            }
            else if ( ((LA58_0>=OR && LA58_0<=BITOR)||(LA58_0>=74 && LA58_0<=75)||(LA58_0>=77 && LA58_0<=78)||LA58_0==80||(LA58_0>=83 && LA58_0<=85)||LA58_0==87||LA58_0==89||LA58_0==91||(LA58_0>=95 && LA58_0<=96)||LA58_0==103) ) {
                alt58=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 58, 0, input);

                throw nvae;
            }
            switch (alt58) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:26: ( BITAND e2= eq_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:26: ( BITAND e2= eq_expr )+
                    int cnt57=0;
                    loop57:
                    do {
                        int alt57=2;
                        int LA57_0 = input.LA(1);

                        if ( (LA57_0==BITAND) ) {
                            alt57=1;
                        }


                        switch (alt57) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:27: BITAND e2= eq_expr
                    	    {
                    	    BITAND127=(Token)match(input,BITAND,FOLLOW_BITAND_in_bitand_expr1451);  
                    	    stream_BITAND.add(BITAND127);

                    	    pushFollow(FOLLOW_eq_expr_in_bitand_expr1455);
                    	    e2=eq_expr();

                    	    state._fsp--;

                    	    stream_eq_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt57 >= 1 ) break loop57;
                                EarlyExitException eee =
                                    new EarlyExitException(57, input);
                                throw eee;
                        }
                        cnt57++;
                    } while (true);



                    // AST REWRITE
                    // elements: e2, e1, BITAND
                    // token labels: 
                    // rule labels: retval, e1, e2
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
                    RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 221:47: -> ^( EXPR_BINARY $e1 ( BITAND $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:50: ^( EXPR_BINARY $e1 ( BITAND $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BINARY, "EXPR_BINARY"), root_1);

                        adaptor.addChild(root_1, stream_e1.nextTree());
                        if ( !(stream_e2.hasNext()||stream_BITAND.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_e2.hasNext()||stream_BITAND.hasNext() ) {
                            adaptor.addChild(root_1, stream_BITAND.nextNode());
                            adaptor.addChild(root_1, stream_e2.nextTree());

                        }
                        stream_e2.reset();
                        stream_BITAND.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:85: 
                    {

                    // AST REWRITE
                    // elements: e1
                    // token labels: 
                    // rule labels: retval, e1
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 221:85: -> $e1
                    {
                        adaptor.addChild(root_0, stream_e1.nextTree());

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
    // $ANTLR end "bitand_expr"

    public static class eq_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "eq_expr"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:1: eq_expr : e1= rel_expr ( ( (op= EQ | op= NE ) e2= rel_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1) ;
    public final RVCCalParser.eq_expr_return eq_expr() throws RecognitionException {
        RVCCalParser.eq_expr_return retval = new RVCCalParser.eq_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        RVCCalParser.rel_expr_return e1 = null;

        RVCCalParser.rel_expr_return e2 = null;


        Object op_tree=null;
        RewriteRuleTokenStream stream_EQ=new RewriteRuleTokenStream(adaptor,"token EQ");
        RewriteRuleTokenStream stream_NE=new RewriteRuleTokenStream(adaptor,"token NE");
        RewriteRuleSubtreeStream stream_rel_expr=new RewriteRuleSubtreeStream(adaptor,"rule rel_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:8: (e1= rel_expr ( ( (op= EQ | op= NE ) e2= rel_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:10: e1= rel_expr ( ( (op= EQ | op= NE ) e2= rel_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_rel_expr_in_eq_expr1492);
            e1=rel_expr();

            state._fsp--;

            stream_rel_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:22: ( ( (op= EQ | op= NE ) e2= rel_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1)
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( ((LA61_0>=EQ && LA61_0<=NE)) ) {
                alt61=1;
            }
            else if ( ((LA61_0>=OR && LA61_0<=BITAND)||(LA61_0>=74 && LA61_0<=75)||(LA61_0>=77 && LA61_0<=78)||LA61_0==80||(LA61_0>=83 && LA61_0<=85)||LA61_0==87||LA61_0==89||LA61_0==91||(LA61_0>=95 && LA61_0<=96)||LA61_0==103) ) {
                alt61=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 61, 0, input);

                throw nvae;
            }
            switch (alt61) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:23: ( (op= EQ | op= NE ) e2= rel_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:23: ( (op= EQ | op= NE ) e2= rel_expr )+
                    int cnt60=0;
                    loop60:
                    do {
                        int alt60=2;
                        int LA60_0 = input.LA(1);

                        if ( ((LA60_0>=EQ && LA60_0<=NE)) ) {
                            alt60=1;
                        }


                        switch (alt60) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:24: (op= EQ | op= NE ) e2= rel_expr
                    	    {
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:24: (op= EQ | op= NE )
                    	    int alt59=2;
                    	    int LA59_0 = input.LA(1);

                    	    if ( (LA59_0==EQ) ) {
                    	        alt59=1;
                    	    }
                    	    else if ( (LA59_0==NE) ) {
                    	        alt59=2;
                    	    }
                    	    else {
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 59, 0, input);

                    	        throw nvae;
                    	    }
                    	    switch (alt59) {
                    	        case 1 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:25: op= EQ
                    	            {
                    	            op=(Token)match(input,EQ,FOLLOW_EQ_in_eq_expr1499);  
                    	            stream_EQ.add(op);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:33: op= NE
                    	            {
                    	            op=(Token)match(input,NE,FOLLOW_NE_in_eq_expr1505);  
                    	            stream_NE.add(op);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_rel_expr_in_eq_expr1510);
                    	    e2=rel_expr();

                    	    state._fsp--;

                    	    stream_rel_expr.add(e2.getTree());

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
                    // elements: e1, op, e2
                    // token labels: op
                    // rule labels: retval, e1, e2
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_op=new RewriteRuleTokenStream(adaptor,"token op",op);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
                    RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 223:54: -> ^( EXPR_BINARY $e1 ( $op $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:57: ^( EXPR_BINARY $e1 ( $op $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BINARY, "EXPR_BINARY"), root_1);

                        adaptor.addChild(root_1, stream_e1.nextTree());
                        if ( !(stream_op.hasNext()||stream_e2.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_op.hasNext()||stream_e2.hasNext() ) {
                            adaptor.addChild(root_1, stream_op.nextNode());
                            adaptor.addChild(root_1, stream_e2.nextTree());

                        }
                        stream_op.reset();
                        stream_e2.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:89: 
                    {

                    // AST REWRITE
                    // elements: e1
                    // token labels: 
                    // rule labels: retval, e1
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 223:89: -> $e1
                    {
                        adaptor.addChild(root_0, stream_e1.nextTree());

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
    // $ANTLR end "eq_expr"

    public static class rel_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rel_expr"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:1: rel_expr : e1= shift_expr ( ( (op= LT | op= GT | op= LE | op= GE ) e2= shift_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1) ;
    public final RVCCalParser.rel_expr_return rel_expr() throws RecognitionException {
        RVCCalParser.rel_expr_return retval = new RVCCalParser.rel_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        RVCCalParser.shift_expr_return e1 = null;

        RVCCalParser.shift_expr_return e2 = null;


        Object op_tree=null;
        RewriteRuleTokenStream stream_GT=new RewriteRuleTokenStream(adaptor,"token GT");
        RewriteRuleTokenStream stream_GE=new RewriteRuleTokenStream(adaptor,"token GE");
        RewriteRuleTokenStream stream_LT=new RewriteRuleTokenStream(adaptor,"token LT");
        RewriteRuleTokenStream stream_LE=new RewriteRuleTokenStream(adaptor,"token LE");
        RewriteRuleSubtreeStream stream_shift_expr=new RewriteRuleSubtreeStream(adaptor,"rule shift_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:9: (e1= shift_expr ( ( (op= LT | op= GT | op= LE | op= GE ) e2= shift_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:11: e1= shift_expr ( ( (op= LT | op= GT | op= LE | op= GE ) e2= shift_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_shift_expr_in_rel_expr1548);
            e1=shift_expr();

            state._fsp--;

            stream_shift_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:25: ( ( (op= LT | op= GT | op= LE | op= GE ) e2= shift_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1)
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( ((LA64_0>=LT && LA64_0<=GE)) ) {
                alt64=1;
            }
            else if ( ((LA64_0>=OR && LA64_0<=NE)||(LA64_0>=74 && LA64_0<=75)||(LA64_0>=77 && LA64_0<=78)||LA64_0==80||(LA64_0>=83 && LA64_0<=85)||LA64_0==87||LA64_0==89||LA64_0==91||(LA64_0>=95 && LA64_0<=96)||LA64_0==103) ) {
                alt64=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 64, 0, input);

                throw nvae;
            }
            switch (alt64) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:26: ( (op= LT | op= GT | op= LE | op= GE ) e2= shift_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:26: ( (op= LT | op= GT | op= LE | op= GE ) e2= shift_expr )+
                    int cnt63=0;
                    loop63:
                    do {
                        int alt63=2;
                        int LA63_0 = input.LA(1);

                        if ( ((LA63_0>=LT && LA63_0<=GE)) ) {
                            alt63=1;
                        }


                        switch (alt63) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:27: (op= LT | op= GT | op= LE | op= GE ) e2= shift_expr
                    	    {
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:27: (op= LT | op= GT | op= LE | op= GE )
                    	    int alt62=4;
                    	    switch ( input.LA(1) ) {
                    	    case LT:
                    	        {
                    	        alt62=1;
                    	        }
                    	        break;
                    	    case GT:
                    	        {
                    	        alt62=2;
                    	        }
                    	        break;
                    	    case LE:
                    	        {
                    	        alt62=3;
                    	        }
                    	        break;
                    	    case GE:
                    	        {
                    	        alt62=4;
                    	        }
                    	        break;
                    	    default:
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 62, 0, input);

                    	        throw nvae;
                    	    }

                    	    switch (alt62) {
                    	        case 1 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:28: op= LT
                    	            {
                    	            op=(Token)match(input,LT,FOLLOW_LT_in_rel_expr1555);  
                    	            stream_LT.add(op);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:36: op= GT
                    	            {
                    	            op=(Token)match(input,GT,FOLLOW_GT_in_rel_expr1561);  
                    	            stream_GT.add(op);


                    	            }
                    	            break;
                    	        case 3 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:44: op= LE
                    	            {
                    	            op=(Token)match(input,LE,FOLLOW_LE_in_rel_expr1567);  
                    	            stream_LE.add(op);


                    	            }
                    	            break;
                    	        case 4 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:52: op= GE
                    	            {
                    	            op=(Token)match(input,GE,FOLLOW_GE_in_rel_expr1573);  
                    	            stream_GE.add(op);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_shift_expr_in_rel_expr1578);
                    	    e2=shift_expr();

                    	    state._fsp--;

                    	    stream_shift_expr.add(e2.getTree());

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
                    // elements: e1, e2, op
                    // token labels: op
                    // rule labels: retval, e1, e2
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_op=new RewriteRuleTokenStream(adaptor,"token op",op);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
                    RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 225:75: -> ^( EXPR_BINARY $e1 ( $op $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:78: ^( EXPR_BINARY $e1 ( $op $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BINARY, "EXPR_BINARY"), root_1);

                        adaptor.addChild(root_1, stream_e1.nextTree());
                        if ( !(stream_e2.hasNext()||stream_op.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_e2.hasNext()||stream_op.hasNext() ) {
                            adaptor.addChild(root_1, stream_op.nextNode());
                            adaptor.addChild(root_1, stream_e2.nextTree());

                        }
                        stream_e2.reset();
                        stream_op.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:110: 
                    {

                    // AST REWRITE
                    // elements: e1
                    // token labels: 
                    // rule labels: retval, e1
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 225:110: -> $e1
                    {
                        adaptor.addChild(root_0, stream_e1.nextTree());

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
    // $ANTLR end "rel_expr"

    public static class shift_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "shift_expr"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:1: shift_expr : e1= add_expr ( ( (op= SHIFT_LEFT | op= SHIFT_RIGHT ) e2= add_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1) ;
    public final RVCCalParser.shift_expr_return shift_expr() throws RecognitionException {
        RVCCalParser.shift_expr_return retval = new RVCCalParser.shift_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        RVCCalParser.add_expr_return e1 = null;

        RVCCalParser.add_expr_return e2 = null;


        Object op_tree=null;
        RewriteRuleTokenStream stream_SHIFT_LEFT=new RewriteRuleTokenStream(adaptor,"token SHIFT_LEFT");
        RewriteRuleTokenStream stream_SHIFT_RIGHT=new RewriteRuleTokenStream(adaptor,"token SHIFT_RIGHT");
        RewriteRuleSubtreeStream stream_add_expr=new RewriteRuleSubtreeStream(adaptor,"rule add_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:11: (e1= add_expr ( ( (op= SHIFT_LEFT | op= SHIFT_RIGHT ) e2= add_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:13: e1= add_expr ( ( (op= SHIFT_LEFT | op= SHIFT_RIGHT ) e2= add_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_add_expr_in_shift_expr1615);
            e1=add_expr();

            state._fsp--;

            stream_add_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:25: ( ( (op= SHIFT_LEFT | op= SHIFT_RIGHT ) e2= add_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1)
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( ((LA67_0>=SHIFT_LEFT && LA67_0<=SHIFT_RIGHT)) ) {
                alt67=1;
            }
            else if ( ((LA67_0>=OR && LA67_0<=GE)||(LA67_0>=74 && LA67_0<=75)||(LA67_0>=77 && LA67_0<=78)||LA67_0==80||(LA67_0>=83 && LA67_0<=85)||LA67_0==87||LA67_0==89||LA67_0==91||(LA67_0>=95 && LA67_0<=96)||LA67_0==103) ) {
                alt67=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;
            }
            switch (alt67) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:26: ( (op= SHIFT_LEFT | op= SHIFT_RIGHT ) e2= add_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:26: ( (op= SHIFT_LEFT | op= SHIFT_RIGHT ) e2= add_expr )+
                    int cnt66=0;
                    loop66:
                    do {
                        int alt66=2;
                        int LA66_0 = input.LA(1);

                        if ( ((LA66_0>=SHIFT_LEFT && LA66_0<=SHIFT_RIGHT)) ) {
                            alt66=1;
                        }


                        switch (alt66) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:27: (op= SHIFT_LEFT | op= SHIFT_RIGHT ) e2= add_expr
                    	    {
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:27: (op= SHIFT_LEFT | op= SHIFT_RIGHT )
                    	    int alt65=2;
                    	    int LA65_0 = input.LA(1);

                    	    if ( (LA65_0==SHIFT_LEFT) ) {
                    	        alt65=1;
                    	    }
                    	    else if ( (LA65_0==SHIFT_RIGHT) ) {
                    	        alt65=2;
                    	    }
                    	    else {
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 65, 0, input);

                    	        throw nvae;
                    	    }
                    	    switch (alt65) {
                    	        case 1 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:28: op= SHIFT_LEFT
                    	            {
                    	            op=(Token)match(input,SHIFT_LEFT,FOLLOW_SHIFT_LEFT_in_shift_expr1622);  
                    	            stream_SHIFT_LEFT.add(op);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:44: op= SHIFT_RIGHT
                    	            {
                    	            op=(Token)match(input,SHIFT_RIGHT,FOLLOW_SHIFT_RIGHT_in_shift_expr1628);  
                    	            stream_SHIFT_RIGHT.add(op);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_add_expr_in_shift_expr1633);
                    	    e2=add_expr();

                    	    state._fsp--;

                    	    stream_add_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt66 >= 1 ) break loop66;
                                EarlyExitException eee =
                                    new EarlyExitException(66, input);
                                throw eee;
                        }
                        cnt66++;
                    } while (true);



                    // AST REWRITE
                    // elements: e2, op, e1
                    // token labels: op
                    // rule labels: retval, e1, e2
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_op=new RewriteRuleTokenStream(adaptor,"token op",op);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
                    RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 227:74: -> ^( EXPR_BINARY $e1 ( $op $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:77: ^( EXPR_BINARY $e1 ( $op $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BINARY, "EXPR_BINARY"), root_1);

                        adaptor.addChild(root_1, stream_e1.nextTree());
                        if ( !(stream_e2.hasNext()||stream_op.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_e2.hasNext()||stream_op.hasNext() ) {
                            adaptor.addChild(root_1, stream_op.nextNode());
                            adaptor.addChild(root_1, stream_e2.nextTree());

                        }
                        stream_e2.reset();
                        stream_op.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:109: 
                    {

                    // AST REWRITE
                    // elements: e1
                    // token labels: 
                    // rule labels: retval, e1
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 227:109: -> $e1
                    {
                        adaptor.addChild(root_0, stream_e1.nextTree());

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
    // $ANTLR end "shift_expr"

    public static class add_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "add_expr"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:1: add_expr : e1= mul_expr ( ( (op= PLUS | op= MINUS ) e2= mul_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1) ;
    public final RVCCalParser.add_expr_return add_expr() throws RecognitionException {
        RVCCalParser.add_expr_return retval = new RVCCalParser.add_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        RVCCalParser.mul_expr_return e1 = null;

        RVCCalParser.mul_expr_return e2 = null;


        Object op_tree=null;
        RewriteRuleTokenStream stream_PLUS=new RewriteRuleTokenStream(adaptor,"token PLUS");
        RewriteRuleTokenStream stream_MINUS=new RewriteRuleTokenStream(adaptor,"token MINUS");
        RewriteRuleSubtreeStream stream_mul_expr=new RewriteRuleSubtreeStream(adaptor,"rule mul_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:9: (e1= mul_expr ( ( (op= PLUS | op= MINUS ) e2= mul_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:11: e1= mul_expr ( ( (op= PLUS | op= MINUS ) e2= mul_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_mul_expr_in_add_expr1671);
            e1=mul_expr();

            state._fsp--;

            stream_mul_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:23: ( ( (op= PLUS | op= MINUS ) e2= mul_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1)
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( ((LA70_0>=PLUS && LA70_0<=MINUS)) ) {
                alt70=1;
            }
            else if ( ((LA70_0>=OR && LA70_0<=SHIFT_RIGHT)||(LA70_0>=74 && LA70_0<=75)||(LA70_0>=77 && LA70_0<=78)||LA70_0==80||(LA70_0>=83 && LA70_0<=85)||LA70_0==87||LA70_0==89||LA70_0==91||(LA70_0>=95 && LA70_0<=96)||LA70_0==103) ) {
                alt70=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }
            switch (alt70) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:24: ( (op= PLUS | op= MINUS ) e2= mul_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:24: ( (op= PLUS | op= MINUS ) e2= mul_expr )+
                    int cnt69=0;
                    loop69:
                    do {
                        int alt69=2;
                        int LA69_0 = input.LA(1);

                        if ( ((LA69_0>=PLUS && LA69_0<=MINUS)) ) {
                            alt69=1;
                        }


                        switch (alt69) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:25: (op= PLUS | op= MINUS ) e2= mul_expr
                    	    {
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:25: (op= PLUS | op= MINUS )
                    	    int alt68=2;
                    	    int LA68_0 = input.LA(1);

                    	    if ( (LA68_0==PLUS) ) {
                    	        alt68=1;
                    	    }
                    	    else if ( (LA68_0==MINUS) ) {
                    	        alt68=2;
                    	    }
                    	    else {
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 68, 0, input);

                    	        throw nvae;
                    	    }
                    	    switch (alt68) {
                    	        case 1 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:26: op= PLUS
                    	            {
                    	            op=(Token)match(input,PLUS,FOLLOW_PLUS_in_add_expr1678);  
                    	            stream_PLUS.add(op);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:36: op= MINUS
                    	            {
                    	            op=(Token)match(input,MINUS,FOLLOW_MINUS_in_add_expr1684);  
                    	            stream_MINUS.add(op);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_mul_expr_in_add_expr1689);
                    	    e2=mul_expr();

                    	    state._fsp--;

                    	    stream_mul_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt69 >= 1 ) break loop69;
                                EarlyExitException eee =
                                    new EarlyExitException(69, input);
                                throw eee;
                        }
                        cnt69++;
                    } while (true);



                    // AST REWRITE
                    // elements: e2, op, e1
                    // token labels: op
                    // rule labels: retval, e1, e2
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_op=new RewriteRuleTokenStream(adaptor,"token op",op);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
                    RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 229:60: -> ^( EXPR_BINARY $e1 ( $op $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:63: ^( EXPR_BINARY $e1 ( $op $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BINARY, "EXPR_BINARY"), root_1);

                        adaptor.addChild(root_1, stream_e1.nextTree());
                        if ( !(stream_e2.hasNext()||stream_op.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_e2.hasNext()||stream_op.hasNext() ) {
                            adaptor.addChild(root_1, stream_op.nextNode());
                            adaptor.addChild(root_1, stream_e2.nextTree());

                        }
                        stream_e2.reset();
                        stream_op.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:95: 
                    {

                    // AST REWRITE
                    // elements: e1
                    // token labels: 
                    // rule labels: retval, e1
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 229:95: -> $e1
                    {
                        adaptor.addChild(root_0, stream_e1.nextTree());

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
    // $ANTLR end "add_expr"

    public static class mul_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "mul_expr"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:1: mul_expr : e1= exp_expr ( ( (op= DIV | op= DIV_INT | op= MOD | op= TIMES ) e2= exp_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1) ;
    public final RVCCalParser.mul_expr_return mul_expr() throws RecognitionException {
        RVCCalParser.mul_expr_return retval = new RVCCalParser.mul_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        RVCCalParser.exp_expr_return e1 = null;

        RVCCalParser.exp_expr_return e2 = null;


        Object op_tree=null;
        RewriteRuleTokenStream stream_DIV=new RewriteRuleTokenStream(adaptor,"token DIV");
        RewriteRuleTokenStream stream_TIMES=new RewriteRuleTokenStream(adaptor,"token TIMES");
        RewriteRuleTokenStream stream_MOD=new RewriteRuleTokenStream(adaptor,"token MOD");
        RewriteRuleTokenStream stream_DIV_INT=new RewriteRuleTokenStream(adaptor,"token DIV_INT");
        RewriteRuleSubtreeStream stream_exp_expr=new RewriteRuleSubtreeStream(adaptor,"rule exp_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:9: (e1= exp_expr ( ( (op= DIV | op= DIV_INT | op= MOD | op= TIMES ) e2= exp_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:11: e1= exp_expr ( ( (op= DIV | op= DIV_INT | op= MOD | op= TIMES ) e2= exp_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_exp_expr_in_mul_expr1727);
            e1=exp_expr();

            state._fsp--;

            stream_exp_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:23: ( ( (op= DIV | op= DIV_INT | op= MOD | op= TIMES ) e2= exp_expr )+ -> ^( EXPR_BINARY $e1 ( $op $e2)+ ) | -> $e1)
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( ((LA73_0>=DIV && LA73_0<=TIMES)) ) {
                alt73=1;
            }
            else if ( ((LA73_0>=OR && LA73_0<=MINUS)||(LA73_0>=74 && LA73_0<=75)||(LA73_0>=77 && LA73_0<=78)||LA73_0==80||(LA73_0>=83 && LA73_0<=85)||LA73_0==87||LA73_0==89||LA73_0==91||(LA73_0>=95 && LA73_0<=96)||LA73_0==103) ) {
                alt73=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 73, 0, input);

                throw nvae;
            }
            switch (alt73) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:24: ( (op= DIV | op= DIV_INT | op= MOD | op= TIMES ) e2= exp_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:24: ( (op= DIV | op= DIV_INT | op= MOD | op= TIMES ) e2= exp_expr )+
                    int cnt72=0;
                    loop72:
                    do {
                        int alt72=2;
                        int LA72_0 = input.LA(1);

                        if ( ((LA72_0>=DIV && LA72_0<=TIMES)) ) {
                            alt72=1;
                        }


                        switch (alt72) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:25: (op= DIV | op= DIV_INT | op= MOD | op= TIMES ) e2= exp_expr
                    	    {
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:25: (op= DIV | op= DIV_INT | op= MOD | op= TIMES )
                    	    int alt71=4;
                    	    switch ( input.LA(1) ) {
                    	    case DIV:
                    	        {
                    	        alt71=1;
                    	        }
                    	        break;
                    	    case DIV_INT:
                    	        {
                    	        alt71=2;
                    	        }
                    	        break;
                    	    case MOD:
                    	        {
                    	        alt71=3;
                    	        }
                    	        break;
                    	    case TIMES:
                    	        {
                    	        alt71=4;
                    	        }
                    	        break;
                    	    default:
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 71, 0, input);

                    	        throw nvae;
                    	    }

                    	    switch (alt71) {
                    	        case 1 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:26: op= DIV
                    	            {
                    	            op=(Token)match(input,DIV,FOLLOW_DIV_in_mul_expr1734);  
                    	            stream_DIV.add(op);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:35: op= DIV_INT
                    	            {
                    	            op=(Token)match(input,DIV_INT,FOLLOW_DIV_INT_in_mul_expr1740);  
                    	            stream_DIV_INT.add(op);


                    	            }
                    	            break;
                    	        case 3 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:48: op= MOD
                    	            {
                    	            op=(Token)match(input,MOD,FOLLOW_MOD_in_mul_expr1746);  
                    	            stream_MOD.add(op);


                    	            }
                    	            break;
                    	        case 4 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:57: op= TIMES
                    	            {
                    	            op=(Token)match(input,TIMES,FOLLOW_TIMES_in_mul_expr1752);  
                    	            stream_TIMES.add(op);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_exp_expr_in_mul_expr1757);
                    	    e2=exp_expr();

                    	    state._fsp--;

                    	    stream_exp_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt72 >= 1 ) break loop72;
                                EarlyExitException eee =
                                    new EarlyExitException(72, input);
                                throw eee;
                        }
                        cnt72++;
                    } while (true);



                    // AST REWRITE
                    // elements: op, e1, e2
                    // token labels: op
                    // rule labels: retval, e1, e2
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_op=new RewriteRuleTokenStream(adaptor,"token op",op);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
                    RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 231:81: -> ^( EXPR_BINARY $e1 ( $op $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:84: ^( EXPR_BINARY $e1 ( $op $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BINARY, "EXPR_BINARY"), root_1);

                        adaptor.addChild(root_1, stream_e1.nextTree());
                        if ( !(stream_op.hasNext()||stream_e2.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_op.hasNext()||stream_e2.hasNext() ) {
                            adaptor.addChild(root_1, stream_op.nextNode());
                            adaptor.addChild(root_1, stream_e2.nextTree());

                        }
                        stream_op.reset();
                        stream_e2.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:116: 
                    {

                    // AST REWRITE
                    // elements: e1
                    // token labels: 
                    // rule labels: retval, e1
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 231:116: -> $e1
                    {
                        adaptor.addChild(root_0, stream_e1.nextTree());

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
    // $ANTLR end "mul_expr"

    public static class exp_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "exp_expr"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:1: exp_expr : e1= un_expr ( ( EXP e2= un_expr )+ -> ^( EXPR_BINARY $e1 ( EXP $e2)+ ) | -> $e1) ;
    public final RVCCalParser.exp_expr_return exp_expr() throws RecognitionException {
        RVCCalParser.exp_expr_return retval = new RVCCalParser.exp_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EXP128=null;
        RVCCalParser.un_expr_return e1 = null;

        RVCCalParser.un_expr_return e2 = null;


        Object EXP128_tree=null;
        RewriteRuleTokenStream stream_EXP=new RewriteRuleTokenStream(adaptor,"token EXP");
        RewriteRuleSubtreeStream stream_un_expr=new RewriteRuleSubtreeStream(adaptor,"rule un_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:9: (e1= un_expr ( ( EXP e2= un_expr )+ -> ^( EXPR_BINARY $e1 ( EXP $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:11: e1= un_expr ( ( EXP e2= un_expr )+ -> ^( EXPR_BINARY $e1 ( EXP $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_un_expr_in_exp_expr1795);
            e1=un_expr();

            state._fsp--;

            stream_un_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:22: ( ( EXP e2= un_expr )+ -> ^( EXPR_BINARY $e1 ( EXP $e2)+ ) | -> $e1)
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==EXP) ) {
                alt75=1;
            }
            else if ( ((LA75_0>=OR && LA75_0<=TIMES)||(LA75_0>=74 && LA75_0<=75)||(LA75_0>=77 && LA75_0<=78)||LA75_0==80||(LA75_0>=83 && LA75_0<=85)||LA75_0==87||LA75_0==89||LA75_0==91||(LA75_0>=95 && LA75_0<=96)||LA75_0==103) ) {
                alt75=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 75, 0, input);

                throw nvae;
            }
            switch (alt75) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:23: ( EXP e2= un_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:23: ( EXP e2= un_expr )+
                    int cnt74=0;
                    loop74:
                    do {
                        int alt74=2;
                        int LA74_0 = input.LA(1);

                        if ( (LA74_0==EXP) ) {
                            alt74=1;
                        }


                        switch (alt74) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:24: EXP e2= un_expr
                    	    {
                    	    EXP128=(Token)match(input,EXP,FOLLOW_EXP_in_exp_expr1799);  
                    	    stream_EXP.add(EXP128);

                    	    pushFollow(FOLLOW_un_expr_in_exp_expr1803);
                    	    e2=un_expr();

                    	    state._fsp--;

                    	    stream_un_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt74 >= 1 ) break loop74;
                                EarlyExitException eee =
                                    new EarlyExitException(74, input);
                                throw eee;
                        }
                        cnt74++;
                    } while (true);



                    // AST REWRITE
                    // elements: e2, EXP, e1
                    // token labels: 
                    // rule labels: retval, e1, e2
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
                    RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 233:41: -> ^( EXPR_BINARY $e1 ( EXP $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:44: ^( EXPR_BINARY $e1 ( EXP $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BINARY, "EXPR_BINARY"), root_1);

                        adaptor.addChild(root_1, stream_e1.nextTree());
                        if ( !(stream_e2.hasNext()||stream_EXP.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_e2.hasNext()||stream_EXP.hasNext() ) {
                            adaptor.addChild(root_1, stream_EXP.nextNode());
                            adaptor.addChild(root_1, stream_e2.nextTree());

                        }
                        stream_e2.reset();
                        stream_EXP.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:76: 
                    {

                    // AST REWRITE
                    // elements: e1
                    // token labels: 
                    // rule labels: retval, e1
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 233:76: -> $e1
                    {
                        adaptor.addChild(root_0, stream_e1.nextTree());

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
    // $ANTLR end "exp_expr"

    public static class un_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "un_expr"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:1: un_expr : ( postfix_expression -> postfix_expression | (op= MINUS | op= NOT | op= NUM_ELTS ) un_expr -> ^( EXPR_UNARY $op un_expr ) );
    public final RVCCalParser.un_expr_return un_expr() throws RecognitionException {
        RVCCalParser.un_expr_return retval = new RVCCalParser.un_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        RVCCalParser.postfix_expression_return postfix_expression129 = null;

        RVCCalParser.un_expr_return un_expr130 = null;


        Object op_tree=null;
        RewriteRuleTokenStream stream_NUM_ELTS=new RewriteRuleTokenStream(adaptor,"token NUM_ELTS");
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleTokenStream stream_MINUS=new RewriteRuleTokenStream(adaptor,"token MINUS");
        RewriteRuleSubtreeStream stream_postfix_expression=new RewriteRuleSubtreeStream(adaptor,"rule postfix_expression");
        RewriteRuleSubtreeStream stream_un_expr=new RewriteRuleSubtreeStream(adaptor,"rule un_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:8: ( postfix_expression -> postfix_expression | (op= MINUS | op= NOT | op= NUM_ELTS ) un_expr -> ^( EXPR_UNARY $op un_expr ) )
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==ID||(LA77_0>=FLOAT && LA77_0<=STRING)||LA77_0==76||LA77_0==82||LA77_0==94||(LA77_0>=97 && LA77_0<=98)) ) {
                alt77=1;
            }
            else if ( (LA77_0==MINUS||(LA77_0>=NOT && LA77_0<=NUM_ELTS)) ) {
                alt77=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 77, 0, input);

                throw nvae;
            }
            switch (alt77) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:10: postfix_expression
                    {
                    pushFollow(FOLLOW_postfix_expression_in_un_expr1838);
                    postfix_expression129=postfix_expression();

                    state._fsp--;

                    stream_postfix_expression.add(postfix_expression129.getTree());


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
                    // 235:29: -> postfix_expression
                    {
                        adaptor.addChild(root_0, stream_postfix_expression.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:4: (op= MINUS | op= NOT | op= NUM_ELTS ) un_expr
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:4: (op= MINUS | op= NOT | op= NUM_ELTS )
                    int alt76=3;
                    switch ( input.LA(1) ) {
                    case MINUS:
                        {
                        alt76=1;
                        }
                        break;
                    case NOT:
                        {
                        alt76=2;
                        }
                        break;
                    case NUM_ELTS:
                        {
                        alt76=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 76, 0, input);

                        throw nvae;
                    }

                    switch (alt76) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:5: op= MINUS
                            {
                            op=(Token)match(input,MINUS,FOLLOW_MINUS_in_un_expr1850);  
                            stream_MINUS.add(op);


                            }
                            break;
                        case 2 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:16: op= NOT
                            {
                            op=(Token)match(input,NOT,FOLLOW_NOT_in_un_expr1856);  
                            stream_NOT.add(op);


                            }
                            break;
                        case 3 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:25: op= NUM_ELTS
                            {
                            op=(Token)match(input,NUM_ELTS,FOLLOW_NUM_ELTS_in_un_expr1862);  
                            stream_NUM_ELTS.add(op);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_un_expr_in_un_expr1865);
                    un_expr130=un_expr();

                    state._fsp--;

                    stream_un_expr.add(un_expr130.getTree());


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
                    // 236:46: -> ^( EXPR_UNARY $op un_expr )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:49: ^( EXPR_UNARY $op un_expr )
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:1: postfix_expression : ( '[' e= expressions ( ':' g= expressionGenerators )? ']' -> ^( EXPR_LIST $e ( $g)? ) | 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end' -> ^( EXPR_IF $e1 $e2 $e3) | constant -> constant | '(' expression ')' -> expression | var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) ) );
    public final RVCCalParser.postfix_expression_return postfix_expression() throws RecognitionException {
        RVCCalParser.postfix_expression_return retval = new RVCCalParser.postfix_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token var=null;
        Token char_literal131=null;
        Token char_literal132=null;
        Token char_literal133=null;
        Token string_literal134=null;
        Token string_literal135=null;
        Token string_literal136=null;
        Token string_literal137=null;
        Token char_literal139=null;
        Token char_literal141=null;
        Token char_literal142=null;
        Token char_literal144=null;
        Token char_literal145=null;
        Token char_literal147=null;
        RVCCalParser.expressions_return e = null;

        RVCCalParser.expressionGenerators_return g = null;

        RVCCalParser.expression_return e1 = null;

        RVCCalParser.expression_return e2 = null;

        RVCCalParser.expression_return e3 = null;

        RVCCalParser.constant_return constant138 = null;

        RVCCalParser.expression_return expression140 = null;

        RVCCalParser.expressions_return expressions143 = null;

        RVCCalParser.expressions_return expressions146 = null;


        Object var_tree=null;
        Object char_literal131_tree=null;
        Object char_literal132_tree=null;
        Object char_literal133_tree=null;
        Object string_literal134_tree=null;
        Object string_literal135_tree=null;
        Object string_literal136_tree=null;
        Object string_literal137_tree=null;
        Object char_literal139_tree=null;
        Object char_literal141_tree=null;
        Object char_literal142_tree=null;
        Object char_literal144_tree=null;
        Object char_literal145_tree=null;
        Object char_literal147_tree=null;
        RewriteRuleTokenStream stream_96=new RewriteRuleTokenStream(adaptor,"token 96");
        RewriteRuleTokenStream stream_95=new RewriteRuleTokenStream(adaptor,"token 95");
        RewriteRuleTokenStream stream_77=new RewriteRuleTokenStream(adaptor,"token 77");
        RewriteRuleTokenStream stream_94=new RewriteRuleTokenStream(adaptor,"token 94");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_75=new RewriteRuleTokenStream(adaptor,"token 75");
        RewriteRuleTokenStream stream_85=new RewriteRuleTokenStream(adaptor,"token 85");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_constant=new RewriteRuleSubtreeStream(adaptor,"rule constant");
        RewriteRuleSubtreeStream stream_expressionGenerators=new RewriteRuleSubtreeStream(adaptor,"rule expressionGenerators");
        RewriteRuleSubtreeStream stream_expressions=new RewriteRuleSubtreeStream(adaptor,"rule expressions");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:19: ( '[' e= expressions ( ':' g= expressionGenerators )? ']' -> ^( EXPR_LIST $e ( $g)? ) | 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end' -> ^( EXPR_IF $e1 $e2 $e3) | constant -> constant | '(' expression ')' -> expression | var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) ) )
            int alt82=5;
            switch ( input.LA(1) ) {
            case 76:
                {
                alt82=1;
                }
                break;
            case 94:
                {
                alt82=2;
                }
                break;
            case FLOAT:
            case INTEGER:
            case STRING:
            case 97:
            case 98:
                {
                alt82=3;
                }
                break;
            case 82:
                {
                alt82=4;
                }
                break;
            case ID:
                {
                alt82=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 82, 0, input);

                throw nvae;
            }

            switch (alt82) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:3: '[' e= expressions ( ':' g= expressionGenerators )? ']'
                    {
                    char_literal131=(Token)match(input,76,FOLLOW_76_in_postfix_expression1885);  
                    stream_76.add(char_literal131);

                    pushFollow(FOLLOW_expressions_in_postfix_expression1889);
                    e=expressions();

                    state._fsp--;

                    stream_expressions.add(e.getTree());
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:21: ( ':' g= expressionGenerators )?
                    int alt78=2;
                    int LA78_0 = input.LA(1);

                    if ( (LA78_0==75) ) {
                        alt78=1;
                    }
                    switch (alt78) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:22: ':' g= expressionGenerators
                            {
                            char_literal132=(Token)match(input,75,FOLLOW_75_in_postfix_expression1892);  
                            stream_75.add(char_literal132);

                            pushFollow(FOLLOW_expressionGenerators_in_postfix_expression1896);
                            g=expressionGenerators();

                            state._fsp--;

                            stream_expressionGenerators.add(g.getTree());

                            }
                            break;

                    }

                    char_literal133=(Token)match(input,77,FOLLOW_77_in_postfix_expression1900);  
                    stream_77.add(char_literal133);



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
                    // 239:55: -> ^( EXPR_LIST $e ( $g)? )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:58: ^( EXPR_LIST $e ( $g)? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_LIST, "EXPR_LIST"), root_1);

                        adaptor.addChild(root_1, stream_e.nextTree());
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:73: ( $g)?
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:3: 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end'
                    {
                    string_literal134=(Token)match(input,94,FOLLOW_94_in_postfix_expression1917);  
                    stream_94.add(string_literal134);

                    pushFollow(FOLLOW_expression_in_postfix_expression1921);
                    e1=expression();

                    state._fsp--;

                    stream_expression.add(e1.getTree());
                    string_literal135=(Token)match(input,95,FOLLOW_95_in_postfix_expression1923);  
                    stream_95.add(string_literal135);

                    pushFollow(FOLLOW_expression_in_postfix_expression1927);
                    e2=expression();

                    state._fsp--;

                    stream_expression.add(e2.getTree());
                    string_literal136=(Token)match(input,96,FOLLOW_96_in_postfix_expression1929);  
                    stream_96.add(string_literal136);

                    pushFollow(FOLLOW_expression_in_postfix_expression1933);
                    e3=expression();

                    state._fsp--;

                    stream_expression.add(e3.getTree());
                    string_literal137=(Token)match(input,85,FOLLOW_85_in_postfix_expression1935);  
                    stream_85.add(string_literal137);



                    // AST REWRITE
                    // elements: e2, e3, e1
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
                    // 240:70: -> ^( EXPR_IF $e1 $e2 $e3)
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:73: ^( EXPR_IF $e1 $e2 $e3)
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:241:3: constant
                    {
                    pushFollow(FOLLOW_constant_in_postfix_expression1954);
                    constant138=constant();

                    state._fsp--;

                    stream_constant.add(constant138.getTree());


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
                    // 241:12: -> constant
                    {
                        adaptor.addChild(root_0, stream_constant.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:3: '(' expression ')'
                    {
                    char_literal139=(Token)match(input,82,FOLLOW_82_in_postfix_expression1962);  
                    stream_82.add(char_literal139);

                    pushFollow(FOLLOW_expression_in_postfix_expression1964);
                    expression140=expression();

                    state._fsp--;

                    stream_expression.add(expression140.getTree());
                    char_literal141=(Token)match(input,83,FOLLOW_83_in_postfix_expression1966);  
                    stream_83.add(char_literal141);



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
                    // 242:22: -> expression
                    {
                        adaptor.addChild(root_0, stream_expression.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:243:3: var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) )
                    {
                    var=(Token)match(input,ID,FOLLOW_ID_in_postfix_expression1976);  
                    stream_ID.add(var);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:243:10: ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) )
                    int alt81=3;
                    switch ( input.LA(1) ) {
                    case 82:
                        {
                        alt81=1;
                        }
                        break;
                    case 76:
                        {
                        alt81=2;
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
                    case 74:
                    case 75:
                    case 77:
                    case 78:
                    case 80:
                    case 83:
                    case 84:
                    case 85:
                    case 87:
                    case 89:
                    case 91:
                    case 95:
                    case 96:
                    case 103:
                        {
                        alt81=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 81, 0, input);

                        throw nvae;
                    }

                    switch (alt81) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:5: '(' ( expressions )? ')'
                            {
                            char_literal142=(Token)match(input,82,FOLLOW_82_in_postfix_expression1984);  
                            stream_82.add(char_literal142);

                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:9: ( expressions )?
                            int alt79=2;
                            int LA79_0 = input.LA(1);

                            if ( (LA79_0==ID||LA79_0==MINUS||(LA79_0>=NOT && LA79_0<=STRING)||LA79_0==76||LA79_0==82||LA79_0==94||(LA79_0>=97 && LA79_0<=98)) ) {
                                alt79=1;
                            }
                            switch (alt79) {
                                case 1 :
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:9: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_postfix_expression1986);
                                    expressions143=expressions();

                                    state._fsp--;

                                    stream_expressions.add(expressions143.getTree());

                                    }
                                    break;

                            }

                            char_literal144=(Token)match(input,83,FOLLOW_83_in_postfix_expression1989);  
                            stream_83.add(char_literal144);



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
                            // 244:26: -> ^( EXPR_CALL $var ( expressions )? )
                            {
                                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:29: ^( EXPR_CALL $var ( expressions )? )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_CALL, "EXPR_CALL"), root_1);

                                adaptor.addChild(root_1, stream_var.nextNode());
                                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:46: ( expressions )?
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
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:6: ( '[' expressions ']' )+
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:6: ( '[' expressions ']' )+
                            int cnt80=0;
                            loop80:
                            do {
                                int alt80=2;
                                int LA80_0 = input.LA(1);

                                if ( (LA80_0==76) ) {
                                    alt80=1;
                                }


                                switch (alt80) {
                            	case 1 :
                            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:7: '[' expressions ']'
                            	    {
                            	    char_literal145=(Token)match(input,76,FOLLOW_76_in_postfix_expression2009);  
                            	    stream_76.add(char_literal145);

                            	    pushFollow(FOLLOW_expressions_in_postfix_expression2011);
                            	    expressions146=expressions();

                            	    state._fsp--;

                            	    stream_expressions.add(expressions146.getTree());
                            	    char_literal147=(Token)match(input,77,FOLLOW_77_in_postfix_expression2013);  
                            	    stream_77.add(char_literal147);


                            	    }
                            	    break;

                            	default :
                            	    if ( cnt80 >= 1 ) break loop80;
                                        EarlyExitException eee =
                                            new EarlyExitException(80, input);
                                        throw eee;
                                }
                                cnt80++;
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
                            // 245:29: -> ^( EXPR_IDX $var ( expressions )+ )
                            {
                                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:32: ^( EXPR_IDX $var ( expressions )+ )
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
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:246:5: 
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
                            // 246:5: -> ^( EXPR_VAR $var)
                            {
                                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:246:8: ^( EXPR_VAR $var)
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:248:1: constant : ( 'true' -> ^( EXPR_BOOL 'true' ) | 'false' -> ^( EXPR_BOOL 'false' ) | FLOAT -> ^( EXPR_FLOAT FLOAT ) | INTEGER -> ^( EXPR_INT INTEGER ) | STRING -> ^( EXPR_STRING STRING ) );
    public final RVCCalParser.constant_return constant() throws RecognitionException {
        RVCCalParser.constant_return retval = new RVCCalParser.constant_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal148=null;
        Token string_literal149=null;
        Token FLOAT150=null;
        Token INTEGER151=null;
        Token STRING152=null;

        Object string_literal148_tree=null;
        Object string_literal149_tree=null;
        Object FLOAT150_tree=null;
        Object INTEGER151_tree=null;
        Object STRING152_tree=null;
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_98=new RewriteRuleTokenStream(adaptor,"token 98");
        RewriteRuleTokenStream stream_97=new RewriteRuleTokenStream(adaptor,"token 97");
        RewriteRuleTokenStream stream_FLOAT=new RewriteRuleTokenStream(adaptor,"token FLOAT");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:248:9: ( 'true' -> ^( EXPR_BOOL 'true' ) | 'false' -> ^( EXPR_BOOL 'false' ) | FLOAT -> ^( EXPR_FLOAT FLOAT ) | INTEGER -> ^( EXPR_INT INTEGER ) | STRING -> ^( EXPR_STRING STRING ) )
            int alt83=5;
            switch ( input.LA(1) ) {
            case 97:
                {
                alt83=1;
                }
                break;
            case 98:
                {
                alt83=2;
                }
                break;
            case FLOAT:
                {
                alt83=3;
                }
                break;
            case INTEGER:
                {
                alt83=4;
                }
                break;
            case STRING:
                {
                alt83=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 83, 0, input);

                throw nvae;
            }

            switch (alt83) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:249:3: 'true'
                    {
                    string_literal148=(Token)match(input,97,FOLLOW_97_in_constant2050);  
                    stream_97.add(string_literal148);



                    // AST REWRITE
                    // elements: 97
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 249:10: -> ^( EXPR_BOOL 'true' )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:249:13: ^( EXPR_BOOL 'true' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BOOL, "EXPR_BOOL"), root_1);

                        adaptor.addChild(root_1, stream_97.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:250:3: 'false'
                    {
                    string_literal149=(Token)match(input,98,FOLLOW_98_in_constant2062);  
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
                    // 250:11: -> ^( EXPR_BOOL 'false' )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:250:14: ^( EXPR_BOOL 'false' )
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
                case 3 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:3: FLOAT
                    {
                    FLOAT150=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_constant2074);  
                    stream_FLOAT.add(FLOAT150);



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
                    // 251:9: -> ^( EXPR_FLOAT FLOAT )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:12: ^( EXPR_FLOAT FLOAT )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:252:3: INTEGER
                    {
                    INTEGER151=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_constant2086);  
                    stream_INTEGER.add(INTEGER151);



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
                    // 252:11: -> ^( EXPR_INT INTEGER )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:252:14: ^( EXPR_INT INTEGER )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:253:3: STRING
                    {
                    STRING152=(Token)match(input,STRING,FOLLOW_STRING_in_constant2098);  
                    stream_STRING.add(STRING152);



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
                    // 253:10: -> ^( EXPR_STRING STRING )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:253:13: ^( EXPR_STRING STRING )
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:255:1: expressionGenerator : 'for' typeDef ID 'in' expression ;
    public final RVCCalParser.expressionGenerator_return expressionGenerator() throws RecognitionException {
        RVCCalParser.expressionGenerator_return retval = new RVCCalParser.expressionGenerator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal153=null;
        Token ID155=null;
        Token string_literal156=null;
        RVCCalParser.typeDef_return typeDef154 = null;

        RVCCalParser.expression_return expression157 = null;


        Object string_literal153_tree=null;
        Object ID155_tree=null;
        Object string_literal156_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:255:20: ( 'for' typeDef ID 'in' expression )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:2: 'for' typeDef ID 'in' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal153=(Token)match(input,99,FOLLOW_99_in_expressionGenerator2114); 
            string_literal153_tree = (Object)adaptor.create(string_literal153);
            adaptor.addChild(root_0, string_literal153_tree);

            pushFollow(FOLLOW_typeDef_in_expressionGenerator2116);
            typeDef154=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef154.getTree());
            ID155=(Token)match(input,ID,FOLLOW_ID_in_expressionGenerator2118); 
            ID155_tree = (Object)adaptor.create(ID155);
            adaptor.addChild(root_0, ID155_tree);

            string_literal156=(Token)match(input,100,FOLLOW_100_in_expressionGenerator2120); 
            string_literal156_tree = (Object)adaptor.create(string_literal156);
            adaptor.addChild(root_0, string_literal156_tree);

            pushFollow(FOLLOW_expression_in_expressionGenerator2122);
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:1: expressionGenerators : expressionGenerator ( ',' expressionGenerator )* -> ( expressionGenerator )+ ;
    public final RVCCalParser.expressionGenerators_return expressionGenerators() throws RecognitionException {
        RVCCalParser.expressionGenerators_return retval = new RVCCalParser.expressionGenerators_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal159=null;
        RVCCalParser.expressionGenerator_return expressionGenerator158 = null;

        RVCCalParser.expressionGenerator_return expressionGenerator160 = null;


        Object char_literal159_tree=null;
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleSubtreeStream stream_expressionGenerator=new RewriteRuleSubtreeStream(adaptor,"rule expressionGenerator");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:21: ( expressionGenerator ( ',' expressionGenerator )* -> ( expressionGenerator )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:23: expressionGenerator ( ',' expressionGenerator )*
            {
            pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators2132);
            expressionGenerator158=expressionGenerator();

            state._fsp--;

            stream_expressionGenerator.add(expressionGenerator158.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:43: ( ',' expressionGenerator )*
            loop84:
            do {
                int alt84=2;
                int LA84_0 = input.LA(1);

                if ( (LA84_0==78) ) {
                    alt84=1;
                }


                switch (alt84) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:44: ',' expressionGenerator
            	    {
            	    char_literal159=(Token)match(input,78,FOLLOW_78_in_expressionGenerators2135);  
            	    stream_78.add(char_literal159);

            	    pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators2137);
            	    expressionGenerator160=expressionGenerator();

            	    state._fsp--;

            	    stream_expressionGenerator.add(expressionGenerator160.getTree());

            	    }
            	    break;

            	default :
            	    break loop84;
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
            // 259:70: -> ( expressionGenerator )+
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:261:1: expressions : expression ( ',' expression )* -> ( expression )+ ;
    public final RVCCalParser.expressions_return expressions() throws RecognitionException {
        RVCCalParser.expressions_return retval = new RVCCalParser.expressions_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal162=null;
        RVCCalParser.expression_return expression161 = null;

        RVCCalParser.expression_return expression163 = null;


        Object char_literal162_tree=null;
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:261:12: ( expression ( ',' expression )* -> ( expression )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:261:14: expression ( ',' expression )*
            {
            pushFollow(FOLLOW_expression_in_expressions2151);
            expression161=expression();

            state._fsp--;

            stream_expression.add(expression161.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:261:25: ( ',' expression )*
            loop85:
            do {
                int alt85=2;
                int LA85_0 = input.LA(1);

                if ( (LA85_0==78) ) {
                    alt85=1;
                }


                switch (alt85) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:261:26: ',' expression
            	    {
            	    char_literal162=(Token)match(input,78,FOLLOW_78_in_expressions2154);  
            	    stream_78.add(char_literal162);

            	    pushFollow(FOLLOW_expression_in_expressions2156);
            	    expression163=expression();

            	    state._fsp--;

            	    stream_expression.add(expression163.getTree());

            	    }
            	    break;

            	default :
            	    break loop85;
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
            // 261:43: -> ( expression )+
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:263:1: idents : ID ( ',' ID )* ;
    public final RVCCalParser.idents_return idents() throws RecognitionException {
        RVCCalParser.idents_return retval = new RVCCalParser.idents_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID164=null;
        Token char_literal165=null;
        Token ID166=null;

        Object ID164_tree=null;
        Object char_literal165_tree=null;
        Object ID166_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:7: ( ID ( ',' ID )* )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:9: ID ( ',' ID )*
            {
            root_0 = (Object)adaptor.nil();

            ID164=(Token)match(input,ID,FOLLOW_ID_in_idents2175); 
            ID164_tree = (Object)adaptor.create(ID164);
            adaptor.addChild(root_0, ID164_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:12: ( ',' ID )*
            loop86:
            do {
                int alt86=2;
                int LA86_0 = input.LA(1);

                if ( (LA86_0==78) ) {
                    alt86=1;
                }


                switch (alt86) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:13: ',' ID
            	    {
            	    char_literal165=(Token)match(input,78,FOLLOW_78_in_idents2178); 
            	    char_literal165_tree = (Object)adaptor.create(char_literal165);
            	    adaptor.addChild(root_0, char_literal165_tree);

            	    ID166=(Token)match(input,ID,FOLLOW_ID_in_idents2180); 
            	    ID166_tree = (Object)adaptor.create(ID166);
            	    adaptor.addChild(root_0, ID166_tree);


            	    }
            	    break;

            	default :
            	    break loop86;
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:268:1: priorityInequality : qualifiedIdent ( '>' qualifiedIdent )+ ';' -> ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ ) ;
    public final RVCCalParser.priorityInequality_return priorityInequality() throws RecognitionException {
        RVCCalParser.priorityInequality_return retval = new RVCCalParser.priorityInequality_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal168=null;
        Token char_literal170=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent167 = null;

        RVCCalParser.qualifiedIdent_return qualifiedIdent169 = null;


        Object char_literal168_tree=null;
        Object char_literal170_tree=null;
        RewriteRuleTokenStream stream_GT=new RewriteRuleTokenStream(adaptor,"token GT");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleSubtreeStream stream_qualifiedIdent=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedIdent");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:271:19: ( qualifiedIdent ( '>' qualifiedIdent )+ ';' -> ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:271:21: qualifiedIdent ( '>' qualifiedIdent )+ ';'
            {
            pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality2196);
            qualifiedIdent167=qualifiedIdent();

            state._fsp--;

            stream_qualifiedIdent.add(qualifiedIdent167.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:271:36: ( '>' qualifiedIdent )+
            int cnt87=0;
            loop87:
            do {
                int alt87=2;
                int LA87_0 = input.LA(1);

                if ( (LA87_0==GT) ) {
                    alt87=1;
                }


                switch (alt87) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:271:37: '>' qualifiedIdent
            	    {
            	    char_literal168=(Token)match(input,GT,FOLLOW_GT_in_priorityInequality2199);  
            	    stream_GT.add(char_literal168);

            	    pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality2201);
            	    qualifiedIdent169=qualifiedIdent();

            	    state._fsp--;

            	    stream_qualifiedIdent.add(qualifiedIdent169.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt87 >= 1 ) break loop87;
                        EarlyExitException eee =
                            new EarlyExitException(87, input);
                        throw eee;
                }
                cnt87++;
            } while (true);

            char_literal170=(Token)match(input,89,FOLLOW_89_in_priorityInequality2205);  
            stream_89.add(char_literal170);



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
            // 271:62: -> ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ )
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:271:65: ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ )
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:273:1: priorityOrder : PRIORITY ( priorityInequality )* 'end' -> ^( PRIORITY ( priorityInequality )* ) ;
    public final RVCCalParser.priorityOrder_return priorityOrder() throws RecognitionException {
        RVCCalParser.priorityOrder_return retval = new RVCCalParser.priorityOrder_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRIORITY171=null;
        Token string_literal173=null;
        RVCCalParser.priorityInequality_return priorityInequality172 = null;


        Object PRIORITY171_tree=null;
        Object string_literal173_tree=null;
        RewriteRuleTokenStream stream_PRIORITY=new RewriteRuleTokenStream(adaptor,"token PRIORITY");
        RewriteRuleTokenStream stream_85=new RewriteRuleTokenStream(adaptor,"token 85");
        RewriteRuleSubtreeStream stream_priorityInequality=new RewriteRuleSubtreeStream(adaptor,"rule priorityInequality");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:273:14: ( PRIORITY ( priorityInequality )* 'end' -> ^( PRIORITY ( priorityInequality )* ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:273:16: PRIORITY ( priorityInequality )* 'end'
            {
            PRIORITY171=(Token)match(input,PRIORITY,FOLLOW_PRIORITY_in_priorityOrder2224);  
            stream_PRIORITY.add(PRIORITY171);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:273:25: ( priorityInequality )*
            loop88:
            do {
                int alt88=2;
                int LA88_0 = input.LA(1);

                if ( (LA88_0==ID) ) {
                    alt88=1;
                }


                switch (alt88) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:273:25: priorityInequality
            	    {
            	    pushFollow(FOLLOW_priorityInequality_in_priorityOrder2226);
            	    priorityInequality172=priorityInequality();

            	    state._fsp--;

            	    stream_priorityInequality.add(priorityInequality172.getTree());

            	    }
            	    break;

            	default :
            	    break loop88;
                }
            } while (true);

            string_literal173=(Token)match(input,85,FOLLOW_85_in_priorityOrder2229);  
            stream_85.add(string_literal173);



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
            // 273:51: -> ^( PRIORITY ( priorityInequality )* )
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:273:54: ^( PRIORITY ( priorityInequality )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_PRIORITY.nextNode(), root_1);

                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:273:65: ( priorityInequality )*
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:275:1: qualifiedIdent : ID ( '.' ID )* -> ^( QID ( ID )+ ) ;
    public final RVCCalParser.qualifiedIdent_return qualifiedIdent() throws RecognitionException {
        RVCCalParser.qualifiedIdent_return retval = new RVCCalParser.qualifiedIdent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID174=null;
        Token char_literal175=null;
        Token ID176=null;

        Object ID174_tree=null;
        Object char_literal175_tree=null;
        Object ID176_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:278:15: ( ID ( '.' ID )* -> ^( QID ( ID )+ ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:278:17: ID ( '.' ID )*
            {
            ID174=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent2250);  
            stream_ID.add(ID174);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:278:20: ( '.' ID )*
            loop89:
            do {
                int alt89=2;
                int LA89_0 = input.LA(1);

                if ( (LA89_0==86) ) {
                    alt89=1;
                }


                switch (alt89) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:278:21: '.' ID
            	    {
            	    char_literal175=(Token)match(input,86,FOLLOW_86_in_qualifiedIdent2253);  
            	    stream_86.add(char_literal175);

            	    ID176=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent2255);  
            	    stream_ID.add(ID176);


            	    }
            	    break;

            	default :
            	    break loop89;
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
            // 278:30: -> ^( QID ( ID )+ )
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:278:33: ^( QID ( ID )+ )
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:280:1: schedule : SCHEDULE 'fsm' ID ':' ( stateTransition )* 'end' -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) ) ;
    public final RVCCalParser.schedule_return schedule() throws RecognitionException {
        RVCCalParser.schedule_return retval = new RVCCalParser.schedule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SCHEDULE177=null;
        Token string_literal178=null;
        Token ID179=null;
        Token char_literal180=null;
        Token string_literal182=null;
        RVCCalParser.stateTransition_return stateTransition181 = null;


        Object SCHEDULE177_tree=null;
        Object string_literal178_tree=null;
        Object ID179_tree=null;
        Object char_literal180_tree=null;
        Object string_literal182_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_SCHEDULE=new RewriteRuleTokenStream(adaptor,"token SCHEDULE");
        RewriteRuleTokenStream stream_101=new RewriteRuleTokenStream(adaptor,"token 101");
        RewriteRuleTokenStream stream_75=new RewriteRuleTokenStream(adaptor,"token 75");
        RewriteRuleTokenStream stream_85=new RewriteRuleTokenStream(adaptor,"token 85");
        RewriteRuleSubtreeStream stream_stateTransition=new RewriteRuleSubtreeStream(adaptor,"rule stateTransition");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:283:9: ( SCHEDULE 'fsm' ID ':' ( stateTransition )* 'end' -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:284:3: SCHEDULE 'fsm' ID ':' ( stateTransition )* 'end'
            {
            SCHEDULE177=(Token)match(input,SCHEDULE,FOLLOW_SCHEDULE_in_schedule2280);  
            stream_SCHEDULE.add(SCHEDULE177);

            string_literal178=(Token)match(input,101,FOLLOW_101_in_schedule2282);  
            stream_101.add(string_literal178);

            ID179=(Token)match(input,ID,FOLLOW_ID_in_schedule2284);  
            stream_ID.add(ID179);

            char_literal180=(Token)match(input,75,FOLLOW_75_in_schedule2286);  
            stream_75.add(char_literal180);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:284:25: ( stateTransition )*
            loop90:
            do {
                int alt90=2;
                int LA90_0 = input.LA(1);

                if ( (LA90_0==ID) ) {
                    alt90=1;
                }


                switch (alt90) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:284:25: stateTransition
            	    {
            	    pushFollow(FOLLOW_stateTransition_in_schedule2288);
            	    stateTransition181=stateTransition();

            	    state._fsp--;

            	    stream_stateTransition.add(stateTransition181.getTree());

            	    }
            	    break;

            	default :
            	    break loop90;
                }
            } while (true);

            string_literal182=(Token)match(input,85,FOLLOW_85_in_schedule2291);  
            stream_85.add(string_literal182);



            // AST REWRITE
            // elements: ID, stateTransition, SCHEDULE
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 284:48: -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) )
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:284:51: ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_SCHEDULE.nextNode(), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:284:65: ^( TRANSITIONS ( stateTransition )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TRANSITIONS, "TRANSITIONS"), root_2);

                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:284:79: ( stateTransition )*
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:1: stateTransition : ID '(' qualifiedIdent ')' '-->' ID ';' -> ^( TRANSITION ID qualifiedIdent ID ) ;
    public final RVCCalParser.stateTransition_return stateTransition() throws RecognitionException {
        RVCCalParser.stateTransition_return retval = new RVCCalParser.stateTransition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID183=null;
        Token char_literal184=null;
        Token char_literal186=null;
        Token string_literal187=null;
        Token ID188=null;
        Token char_literal189=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent185 = null;


        Object ID183_tree=null;
        Object char_literal184_tree=null;
        Object char_literal186_tree=null;
        Object string_literal187_tree=null;
        Object ID188_tree=null;
        Object char_literal189_tree=null;
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleSubtreeStream stream_qualifiedIdent=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedIdent");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:16: ( ID '(' qualifiedIdent ')' '-->' ID ';' -> ^( TRANSITION ID qualifiedIdent ID ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:287:2: ID '(' qualifiedIdent ')' '-->' ID ';'
            {
            ID183=(Token)match(input,ID,FOLLOW_ID_in_stateTransition2314);  
            stream_ID.add(ID183);

            char_literal184=(Token)match(input,82,FOLLOW_82_in_stateTransition2316);  
            stream_82.add(char_literal184);

            pushFollow(FOLLOW_qualifiedIdent_in_stateTransition2318);
            qualifiedIdent185=qualifiedIdent();

            state._fsp--;

            stream_qualifiedIdent.add(qualifiedIdent185.getTree());
            char_literal186=(Token)match(input,83,FOLLOW_83_in_stateTransition2320);  
            stream_83.add(char_literal186);

            string_literal187=(Token)match(input,90,FOLLOW_90_in_stateTransition2322);  
            stream_90.add(string_literal187);

            ID188=(Token)match(input,ID,FOLLOW_ID_in_stateTransition2324);  
            stream_ID.add(ID188);

            char_literal189=(Token)match(input,89,FOLLOW_89_in_stateTransition2326);  
            stream_89.add(char_literal189);



            // AST REWRITE
            // elements: ID, ID, qualifiedIdent
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 287:41: -> ^( TRANSITION ID qualifiedIdent ID )
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:287:44: ^( TRANSITION ID qualifiedIdent ID )
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:289:1: statement : ( 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end' | 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end' | 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end' | 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end' | ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' ) );
    public final RVCCalParser.statement_return statement() throws RecognitionException {
        RVCCalParser.statement_return retval = new RVCCalParser.statement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal190=null;
        Token string_literal191=null;
        Token string_literal193=null;
        Token string_literal195=null;
        Token string_literal196=null;
        Token string_literal198=null;
        Token string_literal200=null;
        Token string_literal202=null;
        Token string_literal204=null;
        Token string_literal206=null;
        Token string_literal207=null;
        Token string_literal209=null;
        Token string_literal211=null;
        Token string_literal213=null;
        Token string_literal214=null;
        Token string_literal216=null;
        Token string_literal218=null;
        Token string_literal220=null;
        Token ID221=null;
        Token char_literal222=null;
        Token char_literal224=null;
        Token string_literal225=null;
        Token char_literal227=null;
        Token char_literal228=null;
        Token char_literal230=null;
        Token char_literal231=null;
        RVCCalParser.varDecls_return varDecls192 = null;

        RVCCalParser.statement_return statement194 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr197 = null;

        RVCCalParser.expression_return expression199 = null;

        RVCCalParser.expression_return expression201 = null;

        RVCCalParser.varDecls_return varDecls203 = null;

        RVCCalParser.statement_return statement205 = null;

        RVCCalParser.expression_return expression208 = null;

        RVCCalParser.statement_return statement210 = null;

        RVCCalParser.statement_return statement212 = null;

        RVCCalParser.expression_return expression215 = null;

        RVCCalParser.varDecls_return varDecls217 = null;

        RVCCalParser.statement_return statement219 = null;

        RVCCalParser.expressions_return expressions223 = null;

        RVCCalParser.expression_return expression226 = null;

        RVCCalParser.expressions_return expressions229 = null;


        Object string_literal190_tree=null;
        Object string_literal191_tree=null;
        Object string_literal193_tree=null;
        Object string_literal195_tree=null;
        Object string_literal196_tree=null;
        Object string_literal198_tree=null;
        Object string_literal200_tree=null;
        Object string_literal202_tree=null;
        Object string_literal204_tree=null;
        Object string_literal206_tree=null;
        Object string_literal207_tree=null;
        Object string_literal209_tree=null;
        Object string_literal211_tree=null;
        Object string_literal213_tree=null;
        Object string_literal214_tree=null;
        Object string_literal216_tree=null;
        Object string_literal218_tree=null;
        Object string_literal220_tree=null;
        Object ID221_tree=null;
        Object char_literal222_tree=null;
        Object char_literal224_tree=null;
        Object string_literal225_tree=null;
        Object char_literal227_tree=null;
        Object char_literal228_tree=null;
        Object char_literal230_tree=null;
        Object char_literal231_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:292:10: ( 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end' | 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end' | 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end' | 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end' | ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' ) )
            int alt104=5;
            switch ( input.LA(1) ) {
            case 91:
                {
                alt104=1;
                }
                break;
            case 102:
                {
                alt104=2;
                }
                break;
            case 94:
                {
                alt104=3;
                }
                break;
            case 104:
                {
                alt104=4;
                }
                break;
            case ID:
                {
                alt104=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 104, 0, input);

                throw nvae;
            }

            switch (alt104) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:293:3: 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal190=(Token)match(input,91,FOLLOW_91_in_statement2352); 
                    string_literal190_tree = (Object)adaptor.create(string_literal190);
                    adaptor.addChild(root_0, string_literal190_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:293:11: ( 'var' varDecls 'do' )?
                    int alt91=2;
                    int LA91_0 = input.LA(1);

                    if ( (LA91_0==87) ) {
                        alt91=1;
                    }
                    switch (alt91) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:293:12: 'var' varDecls 'do'
                            {
                            string_literal191=(Token)match(input,87,FOLLOW_87_in_statement2355); 
                            string_literal191_tree = (Object)adaptor.create(string_literal191);
                            adaptor.addChild(root_0, string_literal191_tree);

                            pushFollow(FOLLOW_varDecls_in_statement2357);
                            varDecls192=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls192.getTree());
                            string_literal193=(Token)match(input,80,FOLLOW_80_in_statement2359); 
                            string_literal193_tree = (Object)adaptor.create(string_literal193);
                            adaptor.addChild(root_0, string_literal193_tree);


                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:293:34: ( statement )*
                    loop92:
                    do {
                        int alt92=2;
                        int LA92_0 = input.LA(1);

                        if ( (LA92_0==ID||LA92_0==91||LA92_0==94||LA92_0==102||LA92_0==104) ) {
                            alt92=1;
                        }


                        switch (alt92) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:293:34: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement2363);
                    	    statement194=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement194.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop92;
                        }
                    } while (true);

                    string_literal195=(Token)match(input,85,FOLLOW_85_in_statement2366); 
                    string_literal195_tree = (Object)adaptor.create(string_literal195);
                    adaptor.addChild(root_0, string_literal195_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:294:3: 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal196=(Token)match(input,102,FOLLOW_102_in_statement2372); 
                    string_literal196_tree = (Object)adaptor.create(string_literal196);
                    adaptor.addChild(root_0, string_literal196_tree);

                    pushFollow(FOLLOW_varDeclNoExpr_in_statement2374);
                    varDeclNoExpr197=varDeclNoExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, varDeclNoExpr197.getTree());
                    string_literal198=(Token)match(input,100,FOLLOW_100_in_statement2376); 
                    string_literal198_tree = (Object)adaptor.create(string_literal198);
                    adaptor.addChild(root_0, string_literal198_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:294:32: ( expression ( '..' expression )? )
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:294:33: expression ( '..' expression )?
                    {
                    pushFollow(FOLLOW_expression_in_statement2379);
                    expression199=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression199.getTree());
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:294:44: ( '..' expression )?
                    int alt93=2;
                    int LA93_0 = input.LA(1);

                    if ( (LA93_0==103) ) {
                        alt93=1;
                    }
                    switch (alt93) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:294:45: '..' expression
                            {
                            string_literal200=(Token)match(input,103,FOLLOW_103_in_statement2382); 
                            string_literal200_tree = (Object)adaptor.create(string_literal200);
                            adaptor.addChild(root_0, string_literal200_tree);

                            pushFollow(FOLLOW_expression_in_statement2384);
                            expression201=expression();

                            state._fsp--;

                            adaptor.addChild(root_0, expression201.getTree());

                            }
                            break;

                    }


                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:294:64: ( 'var' varDecls )?
                    int alt94=2;
                    int LA94_0 = input.LA(1);

                    if ( (LA94_0==87) ) {
                        alt94=1;
                    }
                    switch (alt94) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:294:65: 'var' varDecls
                            {
                            string_literal202=(Token)match(input,87,FOLLOW_87_in_statement2390); 
                            string_literal202_tree = (Object)adaptor.create(string_literal202);
                            adaptor.addChild(root_0, string_literal202_tree);

                            pushFollow(FOLLOW_varDecls_in_statement2392);
                            varDecls203=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls203.getTree());

                            }
                            break;

                    }

                    string_literal204=(Token)match(input,80,FOLLOW_80_in_statement2396); 
                    string_literal204_tree = (Object)adaptor.create(string_literal204);
                    adaptor.addChild(root_0, string_literal204_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:294:87: ( statement )*
                    loop95:
                    do {
                        int alt95=2;
                        int LA95_0 = input.LA(1);

                        if ( (LA95_0==ID||LA95_0==91||LA95_0==94||LA95_0==102||LA95_0==104) ) {
                            alt95=1;
                        }


                        switch (alt95) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:294:87: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement2398);
                    	    statement205=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement205.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop95;
                        }
                    } while (true);

                    string_literal206=(Token)match(input,85,FOLLOW_85_in_statement2401); 
                    string_literal206_tree = (Object)adaptor.create(string_literal206);
                    adaptor.addChild(root_0, string_literal206_tree);

                     

                    }
                    break;
                case 3 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:295:3: 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal207=(Token)match(input,94,FOLLOW_94_in_statement2407); 
                    string_literal207_tree = (Object)adaptor.create(string_literal207);
                    adaptor.addChild(root_0, string_literal207_tree);

                    pushFollow(FOLLOW_expression_in_statement2409);
                    expression208=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression208.getTree());
                    string_literal209=(Token)match(input,95,FOLLOW_95_in_statement2411); 
                    string_literal209_tree = (Object)adaptor.create(string_literal209);
                    adaptor.addChild(root_0, string_literal209_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:295:26: ( statement )*
                    loop96:
                    do {
                        int alt96=2;
                        int LA96_0 = input.LA(1);

                        if ( (LA96_0==ID||LA96_0==91||LA96_0==94||LA96_0==102||LA96_0==104) ) {
                            alt96=1;
                        }


                        switch (alt96) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:295:26: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement2413);
                    	    statement210=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement210.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop96;
                        }
                    } while (true);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:295:37: ( 'else' ( statement )* )?
                    int alt98=2;
                    int LA98_0 = input.LA(1);

                    if ( (LA98_0==96) ) {
                        alt98=1;
                    }
                    switch (alt98) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:295:38: 'else' ( statement )*
                            {
                            string_literal211=(Token)match(input,96,FOLLOW_96_in_statement2417); 
                            string_literal211_tree = (Object)adaptor.create(string_literal211);
                            adaptor.addChild(root_0, string_literal211_tree);

                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:295:45: ( statement )*
                            loop97:
                            do {
                                int alt97=2;
                                int LA97_0 = input.LA(1);

                                if ( (LA97_0==ID||LA97_0==91||LA97_0==94||LA97_0==102||LA97_0==104) ) {
                                    alt97=1;
                                }


                                switch (alt97) {
                            	case 1 :
                            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:295:45: statement
                            	    {
                            	    pushFollow(FOLLOW_statement_in_statement2419);
                            	    statement212=statement();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, statement212.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop97;
                                }
                            } while (true);


                            }
                            break;

                    }

                    string_literal213=(Token)match(input,85,FOLLOW_85_in_statement2424); 
                    string_literal213_tree = (Object)adaptor.create(string_literal213);
                    adaptor.addChild(root_0, string_literal213_tree);

                      

                    }
                    break;
                case 4 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:296:3: 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal214=(Token)match(input,104,FOLLOW_104_in_statement2430); 
                    string_literal214_tree = (Object)adaptor.create(string_literal214);
                    adaptor.addChild(root_0, string_literal214_tree);

                    pushFollow(FOLLOW_expression_in_statement2432);
                    expression215=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression215.getTree());
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:296:22: ( 'var' varDecls )?
                    int alt99=2;
                    int LA99_0 = input.LA(1);

                    if ( (LA99_0==87) ) {
                        alt99=1;
                    }
                    switch (alt99) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:296:23: 'var' varDecls
                            {
                            string_literal216=(Token)match(input,87,FOLLOW_87_in_statement2435); 
                            string_literal216_tree = (Object)adaptor.create(string_literal216);
                            adaptor.addChild(root_0, string_literal216_tree);

                            pushFollow(FOLLOW_varDecls_in_statement2437);
                            varDecls217=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls217.getTree());

                            }
                            break;

                    }

                    string_literal218=(Token)match(input,80,FOLLOW_80_in_statement2441); 
                    string_literal218_tree = (Object)adaptor.create(string_literal218);
                    adaptor.addChild(root_0, string_literal218_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:296:45: ( statement )*
                    loop100:
                    do {
                        int alt100=2;
                        int LA100_0 = input.LA(1);

                        if ( (LA100_0==ID||LA100_0==91||LA100_0==94||LA100_0==102||LA100_0==104) ) {
                            alt100=1;
                        }


                        switch (alt100) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:296:45: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement2443);
                    	    statement219=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement219.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop100;
                        }
                    } while (true);

                    string_literal220=(Token)match(input,85,FOLLOW_85_in_statement2446); 
                    string_literal220_tree = (Object)adaptor.create(string_literal220);
                    adaptor.addChild(root_0, string_literal220_tree);

                      

                    }
                    break;
                case 5 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:298:3: ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' )
                    {
                    root_0 = (Object)adaptor.nil();

                    ID221=(Token)match(input,ID,FOLLOW_ID_in_statement2453); 
                    ID221_tree = (Object)adaptor.create(ID221);
                    adaptor.addChild(root_0, ID221_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:298:6: ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' )
                    int alt103=2;
                    int LA103_0 = input.LA(1);

                    if ( (LA103_0==76||LA103_0==88) ) {
                        alt103=1;
                    }
                    else if ( (LA103_0==82) ) {
                        alt103=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 103, 0, input);

                        throw nvae;
                    }
                    switch (alt103) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:299:5: ( ( '[' expressions ']' )? ':=' expression ';' )
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:299:5: ( ( '[' expressions ']' )? ':=' expression ';' )
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:299:6: ( '[' expressions ']' )? ':=' expression ';'
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:299:6: ( '[' expressions ']' )?
                            int alt101=2;
                            int LA101_0 = input.LA(1);

                            if ( (LA101_0==76) ) {
                                alt101=1;
                            }
                            switch (alt101) {
                                case 1 :
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:299:7: '[' expressions ']'
                                    {
                                    char_literal222=(Token)match(input,76,FOLLOW_76_in_statement2463); 
                                    char_literal222_tree = (Object)adaptor.create(char_literal222);
                                    adaptor.addChild(root_0, char_literal222_tree);

                                    pushFollow(FOLLOW_expressions_in_statement2465);
                                    expressions223=expressions();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expressions223.getTree());
                                    char_literal224=(Token)match(input,77,FOLLOW_77_in_statement2467); 
                                    char_literal224_tree = (Object)adaptor.create(char_literal224);
                                    adaptor.addChild(root_0, char_literal224_tree);


                                    }
                                    break;

                            }

                            string_literal225=(Token)match(input,88,FOLLOW_88_in_statement2471); 
                            string_literal225_tree = (Object)adaptor.create(string_literal225);
                            adaptor.addChild(root_0, string_literal225_tree);

                            pushFollow(FOLLOW_expression_in_statement2473);
                            expression226=expression();

                            state._fsp--;

                            adaptor.addChild(root_0, expression226.getTree());
                            char_literal227=(Token)match(input,89,FOLLOW_89_in_statement2475); 
                            char_literal227_tree = (Object)adaptor.create(char_literal227);
                            adaptor.addChild(root_0, char_literal227_tree);


                            }

                             

                            }
                            break;
                        case 2 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:300:6: '(' ( expressions )? ')' ';'
                            {
                            char_literal228=(Token)match(input,82,FOLLOW_82_in_statement2485); 
                            char_literal228_tree = (Object)adaptor.create(char_literal228);
                            adaptor.addChild(root_0, char_literal228_tree);

                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:300:10: ( expressions )?
                            int alt102=2;
                            int LA102_0 = input.LA(1);

                            if ( (LA102_0==ID||LA102_0==MINUS||(LA102_0>=NOT && LA102_0<=STRING)||LA102_0==76||LA102_0==82||LA102_0==94||(LA102_0>=97 && LA102_0<=98)) ) {
                                alt102=1;
                            }
                            switch (alt102) {
                                case 1 :
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:300:10: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_statement2487);
                                    expressions229=expressions();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expressions229.getTree());

                                    }
                                    break;

                            }

                            char_literal230=(Token)match(input,83,FOLLOW_83_in_statement2490); 
                            char_literal230_tree = (Object)adaptor.create(char_literal230);
                            adaptor.addChild(root_0, char_literal230_tree);

                            char_literal231=(Token)match(input,89,FOLLOW_89_in_statement2492); 
                            char_literal231_tree = (Object)adaptor.create(char_literal231);
                            adaptor.addChild(root_0, char_literal231_tree);

                             

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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:302:1: typeAttr : ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) ) ;
    public final RVCCalParser.typeAttr_return typeAttr() throws RecognitionException {
        RVCCalParser.typeAttr_return retval = new RVCCalParser.typeAttr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID232=null;
        Token char_literal233=null;
        Token char_literal235=null;
        RVCCalParser.typeDef_return typeDef234 = null;

        RVCCalParser.expression_return expression236 = null;


        Object ID232_tree=null;
        Object char_literal233_tree=null;
        Object char_literal235_tree=null;
        RewriteRuleTokenStream stream_EQ=new RewriteRuleTokenStream(adaptor,"token EQ");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_75=new RewriteRuleTokenStream(adaptor,"token 75");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:9: ( ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:11: ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) )
            {
            ID232=(Token)match(input,ID,FOLLOW_ID_in_typeAttr2513);  
            stream_ID.add(ID232);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:14: ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) )
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==75) ) {
                alt105=1;
            }
            else if ( (LA105_0==EQ) ) {
                alt105=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 105, 0, input);

                throw nvae;
            }
            switch (alt105) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:15: ':' typeDef
                    {
                    char_literal233=(Token)match(input,75,FOLLOW_75_in_typeAttr2516);  
                    stream_75.add(char_literal233);

                    pushFollow(FOLLOW_typeDef_in_typeAttr2518);
                    typeDef234=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef234.getTree());


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
                    // 309:27: -> ^( TYPE ID typeDef )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:30: ^( TYPE ID typeDef )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:51: '=' expression
                    {
                    char_literal235=(Token)match(input,EQ,FOLLOW_EQ_in_typeAttr2532);  
                    stream_EQ.add(char_literal235);

                    pushFollow(FOLLOW_expression_in_typeAttr2534);
                    expression236=expression();

                    state._fsp--;

                    stream_expression.add(expression236.getTree());


                    // AST REWRITE
                    // elements: ID, expression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 309:66: -> ^( EXPR ID expression )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:69: ^( EXPR ID expression )
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:311:1: typeAttrs : typeAttr ( ',' typeAttr )* -> ( typeAttr )+ ;
    public final RVCCalParser.typeAttrs_return typeAttrs() throws RecognitionException {
        RVCCalParser.typeAttrs_return retval = new RVCCalParser.typeAttrs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal238=null;
        RVCCalParser.typeAttr_return typeAttr237 = null;

        RVCCalParser.typeAttr_return typeAttr239 = null;


        Object char_literal238_tree=null;
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleSubtreeStream stream_typeAttr=new RewriteRuleSubtreeStream(adaptor,"rule typeAttr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:311:10: ( typeAttr ( ',' typeAttr )* -> ( typeAttr )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:311:12: typeAttr ( ',' typeAttr )*
            {
            pushFollow(FOLLOW_typeAttr_in_typeAttrs2553);
            typeAttr237=typeAttr();

            state._fsp--;

            stream_typeAttr.add(typeAttr237.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:311:21: ( ',' typeAttr )*
            loop106:
            do {
                int alt106=2;
                int LA106_0 = input.LA(1);

                if ( (LA106_0==78) ) {
                    alt106=1;
                }


                switch (alt106) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:311:22: ',' typeAttr
            	    {
            	    char_literal238=(Token)match(input,78,FOLLOW_78_in_typeAttrs2556);  
            	    stream_78.add(char_literal238);

            	    pushFollow(FOLLOW_typeAttr_in_typeAttrs2558);
            	    typeAttr239=typeAttr();

            	    state._fsp--;

            	    stream_typeAttr.add(typeAttr239.getTree());

            	    }
            	    break;

            	default :
            	    break loop106;
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
            // 311:37: -> ( typeAttr )+
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:314:1: typeDef : ID ( '(' attrs= typeAttrs ')' )? -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) ;
    public final RVCCalParser.typeDef_return typeDef() throws RecognitionException {
        RVCCalParser.typeDef_return retval = new RVCCalParser.typeDef_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID240=null;
        Token char_literal241=null;
        Token char_literal242=null;
        RVCCalParser.typeAttrs_return attrs = null;


        Object ID240_tree=null;
        Object char_literal241_tree=null;
        Object char_literal242_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleSubtreeStream stream_typeAttrs=new RewriteRuleSubtreeStream(adaptor,"rule typeAttrs");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:314:8: ( ID ( '(' attrs= typeAttrs ')' )? -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:314:10: ID ( '(' attrs= typeAttrs ')' )?
            {
            ID240=(Token)match(input,ID,FOLLOW_ID_in_typeDef2575);  
            stream_ID.add(ID240);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:314:13: ( '(' attrs= typeAttrs ')' )?
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==82) ) {
                alt107=1;
            }
            switch (alt107) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:314:14: '(' attrs= typeAttrs ')'
                    {
                    char_literal241=(Token)match(input,82,FOLLOW_82_in_typeDef2578);  
                    stream_82.add(char_literal241);

                    pushFollow(FOLLOW_typeAttrs_in_typeDef2582);
                    attrs=typeAttrs();

                    state._fsp--;

                    stream_typeAttrs.add(attrs.getTree());
                    char_literal242=(Token)match(input,83,FOLLOW_83_in_typeDef2584);  
                    stream_83.add(char_literal242);


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
            // 314:40: -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:314:43: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:314:53: ^( TYPE_ATTRS ( $attrs)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_2);

                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:314:66: ( $attrs)?
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:316:1: varDecl : typeDef ID ( '=' expression | ':=' expression )? ;
    public final RVCCalParser.varDecl_return varDecl() throws RecognitionException {
        RVCCalParser.varDecl_return retval = new RVCCalParser.varDecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID244=null;
        Token char_literal245=null;
        Token string_literal247=null;
        RVCCalParser.typeDef_return typeDef243 = null;

        RVCCalParser.expression_return expression246 = null;

        RVCCalParser.expression_return expression248 = null;


        Object ID244_tree=null;
        Object char_literal245_tree=null;
        Object string_literal247_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:320:8: ( typeDef ID ( '=' expression | ':=' expression )? )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:320:10: typeDef ID ( '=' expression | ':=' expression )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_typeDef_in_varDecl2616);
            typeDef243=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef243.getTree());
            ID244=(Token)match(input,ID,FOLLOW_ID_in_varDecl2618); 
            ID244_tree = (Object)adaptor.create(ID244);
            adaptor.addChild(root_0, ID244_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:320:21: ( '=' expression | ':=' expression )?
            int alt108=3;
            int LA108_0 = input.LA(1);

            if ( (LA108_0==EQ) ) {
                alt108=1;
            }
            else if ( (LA108_0==88) ) {
                alt108=2;
            }
            switch (alt108) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:320:22: '=' expression
                    {
                    char_literal245=(Token)match(input,EQ,FOLLOW_EQ_in_varDecl2621); 
                    char_literal245_tree = (Object)adaptor.create(char_literal245);
                    adaptor.addChild(root_0, char_literal245_tree);

                    pushFollow(FOLLOW_expression_in_varDecl2623);
                    expression246=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression246.getTree());

                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:320:39: ':=' expression
                    {
                    string_literal247=(Token)match(input,88,FOLLOW_88_in_varDecl2627); 
                    string_literal247_tree = (Object)adaptor.create(string_literal247);
                    adaptor.addChild(root_0, string_literal247_tree);

                    pushFollow(FOLLOW_expression_in_varDecl2629);
                    expression248=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression248.getTree());

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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:322:1: varDeclNoExpr : typeDef ID -> ^( VAR typeDef ID ) ;
    public final RVCCalParser.varDeclNoExpr_return varDeclNoExpr() throws RecognitionException {
        RVCCalParser.varDeclNoExpr_return retval = new RVCCalParser.varDeclNoExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID250=null;
        RVCCalParser.typeDef_return typeDef249 = null;


        Object ID250_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:322:14: ( typeDef ID -> ^( VAR typeDef ID ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:322:16: typeDef ID
            {
            pushFollow(FOLLOW_typeDef_in_varDeclNoExpr2640);
            typeDef249=typeDef();

            state._fsp--;

            stream_typeDef.add(typeDef249.getTree());
            ID250=(Token)match(input,ID,FOLLOW_ID_in_varDeclNoExpr2642);  
            stream_ID.add(ID250);



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
            // 322:27: -> ^( VAR typeDef ID )
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:322:30: ^( VAR typeDef ID )
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:324:1: varDecls : varDecl ( ',' varDecl )* -> ( varDecl )+ ;
    public final RVCCalParser.varDecls_return varDecls() throws RecognitionException {
        RVCCalParser.varDecls_return retval = new RVCCalParser.varDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal252=null;
        RVCCalParser.varDecl_return varDecl251 = null;

        RVCCalParser.varDecl_return varDecl253 = null;


        Object char_literal252_tree=null;
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleSubtreeStream stream_varDecl=new RewriteRuleSubtreeStream(adaptor,"rule varDecl");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:324:9: ( varDecl ( ',' varDecl )* -> ( varDecl )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:324:11: varDecl ( ',' varDecl )*
            {
            pushFollow(FOLLOW_varDecl_in_varDecls2659);
            varDecl251=varDecl();

            state._fsp--;

            stream_varDecl.add(varDecl251.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:324:19: ( ',' varDecl )*
            loop109:
            do {
                int alt109=2;
                int LA109_0 = input.LA(1);

                if ( (LA109_0==78) ) {
                    alt109=1;
                }


                switch (alt109) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:324:20: ',' varDecl
            	    {
            	    char_literal252=(Token)match(input,78,FOLLOW_78_in_varDecls2662);  
            	    stream_78.add(char_literal252);

            	    pushFollow(FOLLOW_varDecl_in_varDecls2664);
            	    varDecl253=varDecl();

            	    state._fsp--;

            	    stream_varDecl.add(varDecl253.getTree());

            	    }
            	    break;

            	default :
            	    break loop109;
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
            // 324:34: -> ( varDecl )+
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


 

    public static final BitSet FOLLOW_74_in_actionGuards297 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expressions_in_actionGuards299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_actionInput312 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_actionInput314 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_actionInput318 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_idents_in_actionInput320 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_actionInput322 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_actionRepeat_in_actionInput324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionInput_in_actionInputs335 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_actionInputs338 = new BitSet(new long[]{0x0000001000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_actionInput_in_actionInputs340 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_ID_in_actionOutput356 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_actionOutput358 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_actionOutput362 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expressions_in_actionOutput364 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_actionOutput366 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_actionRepeat_in_actionOutput368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionOutput_in_actionOutputs379 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_actionOutputs382 = new BitSet(new long[]{0x0000001000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_actionOutput_in_actionOutputs384 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_79_in_actionRepeat398 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_actionRepeat400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_actionStatements411 = new BitSet(new long[]{0x0000001000000002L,0x0000014048000000L});
    public static final BitSet FOLLOW_statement_in_actionStatements413 = new BitSet(new long[]{0x0000001000000002L,0x0000014048000000L});
    public static final BitSet FOLLOW_actorImport_in_actor431 = new BitSet(new long[]{0x0000000000000000L,0x0000000010020000L});
    public static final BitSet FOLLOW_81_in_actor434 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_ID_in_actor438 = new BitSet(new long[]{0x0000000000000000L,0x0000000000041000L});
    public static final BitSet FOLLOW_76_in_actor441 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_actor443 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_actor447 = new BitSet(new long[]{0x0000001000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_actorParameters_in_actor449 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_actor452 = new BitSet(new long[]{0x0000001000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_actorPortDecls_in_actor457 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_actor460 = new BitSet(new long[]{0x0000001000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_actorPortDecls_in_actor464 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_actor467 = new BitSet(new long[]{0x000001F000000000L,0x0000000000200006L});
    public static final BitSet FOLLOW_actorDeclarations_in_actor470 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actor472 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_actor474 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration539 = new BitSet(new long[]{0x0000001000000000L,0x0000000000440800L});
    public static final BitSet FOLLOW_86_in_actorDeclaration550 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration554 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400800L});
    public static final BitSet FOLLOW_75_in_actorDeclaration559 = new BitSet(new long[]{0x0000006000000000L});
    public static final BitSet FOLLOW_ACTION_in_actorDeclaration568 = new BitSet(new long[]{0x0000001000000000L,0x0000000000101000L});
    public static final BitSet FOLLOW_actionInputs_in_actorDeclaration572 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_actorDeclaration575 = new BitSet(new long[]{0x0000001000000000L,0x0000000000A11400L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration579 = new BitSet(new long[]{0x0000000000000000L,0x0000000000A10400L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration584 = new BitSet(new long[]{0x0000000000000000L,0x0000000000A10000L});
    public static final BitSet FOLLOW_87_in_actorDeclaration588 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration590 = new BitSet(new long[]{0x0000000000000000L,0x0000000000210000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration594 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actorDeclaration597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INITIALIZE_in_actorDeclaration668 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_actorDeclaration670 = new BitSet(new long[]{0x0000001000000000L,0x0000000000A11400L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration672 = new BitSet(new long[]{0x0000000000000000L,0x0000000000A10400L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration675 = new BitSet(new long[]{0x0000000000000000L,0x0000000000A10000L});
    public static final BitSet FOLLOW_87_in_actorDeclaration679 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration681 = new BitSet(new long[]{0x0000000000000000L,0x0000000000210000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration685 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actorDeclaration688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_actorDeclaration767 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_typeAttrs_in_actorDeclaration771 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_actorDeclaration773 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration783 = new BitSet(new long[]{0x0000200000000000L,0x0000000003000000L});
    public static final BitSet FOLLOW_EQ_in_actorDeclaration792 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration794 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration830 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration832 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_actorDeclaration894 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ACTION_in_actorDeclaration904 = new BitSet(new long[]{0x0000001000000000L,0x0000000000101000L});
    public static final BitSet FOLLOW_actionInputs_in_actorDeclaration906 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_actorDeclaration909 = new BitSet(new long[]{0x0000001000000000L,0x0000000000A11400L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration911 = new BitSet(new long[]{0x0000000000000000L,0x0000000000A10400L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration914 = new BitSet(new long[]{0x0000000000000000L,0x0000000000A10000L});
    public static final BitSet FOLLOW_87_in_actorDeclaration918 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration920 = new BitSet(new long[]{0x0000000000000000L,0x0000000000210000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration924 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actorDeclaration927 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INITIALIZE_in_actorDeclaration981 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_actorDeclaration983 = new BitSet(new long[]{0x0000001000000000L,0x0000000000A11400L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration985 = new BitSet(new long[]{0x0000000000000000L,0x0000000000A10400L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration988 = new BitSet(new long[]{0x0000000000000000L,0x0000000000A10000L});
    public static final BitSet FOLLOW_87_in_actorDeclaration992 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration994 = new BitSet(new long[]{0x0000000000000000L,0x0000000000210000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration998 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actorDeclaration1001 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_priorityOrder_in_actorDeclaration1048 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FUNCTION_in_actorDeclaration1057 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration1059 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_actorDeclaration1061 = new BitSet(new long[]{0x0000001000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration1064 = new BitSet(new long[]{0x0000000000000000L,0x0000000000084000L});
    public static final BitSet FOLLOW_78_in_actorDeclaration1067 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration1069 = new BitSet(new long[]{0x0000000000000000L,0x0000000000084000L});
    public static final BitSet FOLLOW_83_in_actorDeclaration1075 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_actorDeclaration1077 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_typeDef_in_actorDeclaration1079 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800800L});
    public static final BitSet FOLLOW_87_in_actorDeclaration1086 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration1088 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_actorDeclaration1092 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration1100 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actorDeclaration1106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROCEDURE_in_actorDeclaration1116 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration1118 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_actorDeclaration1120 = new BitSet(new long[]{0x0000001000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration1123 = new BitSet(new long[]{0x0000000000000000L,0x0000000000084000L});
    public static final BitSet FOLLOW_78_in_actorDeclaration1126 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration1128 = new BitSet(new long[]{0x0000000000000000L,0x0000000000084000L});
    public static final BitSet FOLLOW_83_in_actorDeclaration1134 = new BitSet(new long[]{0x0000000000000000L,0x0000000008800000L});
    public static final BitSet FOLLOW_87_in_actorDeclaration1141 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration1143 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_actorDeclaration1151 = new BitSet(new long[]{0x0000001000000000L,0x0000014048200000L});
    public static final BitSet FOLLOW_statement_in_actorDeclaration1153 = new BitSet(new long[]{0x0000001000000000L,0x0000014048200000L});
    public static final BitSet FOLLOW_85_in_actorDeclaration1156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations1168 = new BitSet(new long[]{0x000001F000000002L,0x0000000000000006L});
    public static final BitSet FOLLOW_schedule_in_actorDeclarations1172 = new BitSet(new long[]{0x000001F000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations1174 = new BitSet(new long[]{0x000001F000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_actorImport1197 = new BitSet(new long[]{0x0000001000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_93_in_actorImport1202 = new BitSet(new long[]{0x0000001000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport1204 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_actorImport1206 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport1212 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_actorImport1214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_actorParameter1229 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_ID_in_actorParameter1231 = new BitSet(new long[]{0x0000200000000002L});
    public static final BitSet FOLLOW_EQ_in_actorParameter1234 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_actorParameter1236 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters1258 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_actorParameters1261 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters1263 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorPortDecls1282 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_actorPortDecls1285 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorPortDecls1287 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_and_expr_in_expression1312 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_OR_in_expression1316 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_and_expr_in_expression1320 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_bitor_expr_in_and_expr1357 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_AND_in_and_expr1361 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_bitor_expr_in_and_expr1365 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_bitand_expr_in_bitor_expr1402 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_BITOR_in_bitor_expr1406 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_bitand_expr_in_bitor_expr1410 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_eq_expr_in_bitand_expr1447 = new BitSet(new long[]{0x0000100000000002L});
    public static final BitSet FOLLOW_BITAND_in_bitand_expr1451 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_eq_expr_in_bitand_expr1455 = new BitSet(new long[]{0x0000100000000002L});
    public static final BitSet FOLLOW_rel_expr_in_eq_expr1492 = new BitSet(new long[]{0x0000600000000002L});
    public static final BitSet FOLLOW_EQ_in_eq_expr1499 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_NE_in_eq_expr1505 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_rel_expr_in_eq_expr1510 = new BitSet(new long[]{0x0000600000000002L});
    public static final BitSet FOLLOW_shift_expr_in_rel_expr1548 = new BitSet(new long[]{0x0007800000000002L});
    public static final BitSet FOLLOW_LT_in_rel_expr1555 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_GT_in_rel_expr1561 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_LE_in_rel_expr1567 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_GE_in_rel_expr1573 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_shift_expr_in_rel_expr1578 = new BitSet(new long[]{0x0007800000000002L});
    public static final BitSet FOLLOW_add_expr_in_shift_expr1615 = new BitSet(new long[]{0x0018000000000002L});
    public static final BitSet FOLLOW_SHIFT_LEFT_in_shift_expr1622 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_SHIFT_RIGHT_in_shift_expr1628 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_add_expr_in_shift_expr1633 = new BitSet(new long[]{0x0018000000000002L});
    public static final BitSet FOLLOW_mul_expr_in_add_expr1671 = new BitSet(new long[]{0x0060000000000002L});
    public static final BitSet FOLLOW_PLUS_in_add_expr1678 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_MINUS_in_add_expr1684 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_mul_expr_in_add_expr1689 = new BitSet(new long[]{0x0060000000000002L});
    public static final BitSet FOLLOW_exp_expr_in_mul_expr1727 = new BitSet(new long[]{0x0780000000000002L});
    public static final BitSet FOLLOW_DIV_in_mul_expr1734 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_DIV_INT_in_mul_expr1740 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_MOD_in_mul_expr1746 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_TIMES_in_mul_expr1752 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_exp_expr_in_mul_expr1757 = new BitSet(new long[]{0x0780000000000002L});
    public static final BitSet FOLLOW_un_expr_in_exp_expr1795 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_EXP_in_exp_expr1799 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_un_expr_in_exp_expr1803 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_postfix_expression_in_un_expr1838 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_un_expr1850 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_NOT_in_un_expr1856 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_NUM_ELTS_in_un_expr1862 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_un_expr_in_un_expr1865 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_postfix_expression1885 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1889 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002800L});
    public static final BitSet FOLLOW_75_in_postfix_expression1892 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_expressionGenerators_in_postfix_expression1896 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_postfix_expression1900 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_postfix_expression1917 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1921 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_95_in_postfix_expression1923 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1927 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_postfix_expression1929 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1933 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_postfix_expression1935 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_postfix_expression1954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_postfix_expression1962 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1964 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_postfix_expression1966 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_postfix_expression1976 = new BitSet(new long[]{0x0000000000000002L,0x0000000000041000L});
    public static final BitSet FOLLOW_82_in_postfix_expression1984 = new BitSet(new long[]{0xF040001000000000L,0x00000006400C1001L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1986 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_postfix_expression1989 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_postfix_expression2009 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression2011 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_postfix_expression2013 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_97_in_constant2050 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_constant2062 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_constant2074 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_constant2086 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_constant2098 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_expressionGenerator2114 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_typeDef_in_expressionGenerator2116 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_ID_in_expressionGenerator2118 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_expressionGenerator2120 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_expressionGenerator2122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators2132 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_expressionGenerators2135 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators2137 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_expression_in_expressions2151 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_expressions2154 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_expressions2156 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_ID_in_idents2175 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_idents2178 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_ID_in_idents2180 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality2196 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_GT_in_priorityInequality2199 = new BitSet(new long[]{0x0000001000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality2201 = new BitSet(new long[]{0x0001000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_priorityInequality2205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRIORITY_in_priorityOrder2224 = new BitSet(new long[]{0x0000001000000000L,0x0000000020200000L});
    public static final BitSet FOLLOW_priorityInequality_in_priorityOrder2226 = new BitSet(new long[]{0x0000001000000000L,0x0000000020200000L});
    public static final BitSet FOLLOW_85_in_priorityOrder2229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent2250 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_qualifiedIdent2253 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent2255 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_SCHEDULE_in_schedule2280 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_schedule2282 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_ID_in_schedule2284 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_schedule2286 = new BitSet(new long[]{0x0000001000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_stateTransition_in_schedule2288 = new BitSet(new long[]{0x0000001000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_schedule2291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stateTransition2314 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_stateTransition2316 = new BitSet(new long[]{0x0000001000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_stateTransition2318 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_stateTransition2320 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_stateTransition2322 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_ID_in_stateTransition2324 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_stateTransition2326 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_91_in_statement2352 = new BitSet(new long[]{0x0000001000000000L,0x0000014048A00000L});
    public static final BitSet FOLLOW_87_in_statement2355 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_varDecls_in_statement2357 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_statement2359 = new BitSet(new long[]{0x0000001000000000L,0x0000014048200000L});
    public static final BitSet FOLLOW_statement_in_statement2363 = new BitSet(new long[]{0x0000001000000000L,0x0000014048200000L});
    public static final BitSet FOLLOW_85_in_statement2366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_statement2372 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_statement2374 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_statement2376 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_statement2379 = new BitSet(new long[]{0x0000000000000000L,0x0000008000810000L});
    public static final BitSet FOLLOW_103_in_statement2382 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_statement2384 = new BitSet(new long[]{0x0000000000000000L,0x0000000000810000L});
    public static final BitSet FOLLOW_87_in_statement2390 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_varDecls_in_statement2392 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_statement2396 = new BitSet(new long[]{0x0000001000000000L,0x0000014048200000L});
    public static final BitSet FOLLOW_statement_in_statement2398 = new BitSet(new long[]{0x0000001000000000L,0x0000014048200000L});
    public static final BitSet FOLLOW_85_in_statement2401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_statement2407 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_statement2409 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_95_in_statement2411 = new BitSet(new long[]{0x0000001000000000L,0x0000014148200000L});
    public static final BitSet FOLLOW_statement_in_statement2413 = new BitSet(new long[]{0x0000001000000000L,0x0000014148200000L});
    public static final BitSet FOLLOW_96_in_statement2417 = new BitSet(new long[]{0x0000001000000000L,0x0000014048200000L});
    public static final BitSet FOLLOW_statement_in_statement2419 = new BitSet(new long[]{0x0000001000000000L,0x0000014048200000L});
    public static final BitSet FOLLOW_85_in_statement2424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_104_in_statement2430 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_statement2432 = new BitSet(new long[]{0x0000000000000000L,0x0000000000810000L});
    public static final BitSet FOLLOW_87_in_statement2435 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_varDecls_in_statement2437 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_statement2441 = new BitSet(new long[]{0x0000001000000000L,0x0000014048200000L});
    public static final BitSet FOLLOW_statement_in_statement2443 = new BitSet(new long[]{0x0000001000000000L,0x0000014048200000L});
    public static final BitSet FOLLOW_85_in_statement2446 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_statement2453 = new BitSet(new long[]{0x0000000000000000L,0x0000000001041000L});
    public static final BitSet FOLLOW_76_in_statement2463 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expressions_in_statement2465 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_statement2467 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_88_in_statement2471 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_statement2473 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_statement2475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_statement2485 = new BitSet(new long[]{0xF040001000000000L,0x00000006400C1001L});
    public static final BitSet FOLLOW_expressions_in_statement2487 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_statement2490 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_statement2492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_typeAttr2513 = new BitSet(new long[]{0x0000200000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_typeAttr2516 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_typeDef_in_typeAttr2518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EQ_in_typeAttr2532 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_typeAttr2534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs2553 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_typeAttrs2556 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs2558 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_ID_in_typeDef2575 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_typeDef2578 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_typeAttrs_in_typeDef2582 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_typeDef2584 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_varDecl2616 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_ID_in_varDecl2618 = new BitSet(new long[]{0x0000200000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_EQ_in_varDecl2621 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_varDecl2623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_varDecl2627 = new BitSet(new long[]{0xF040001000000000L,0x0000000640041001L});
    public static final BitSet FOLLOW_expression_in_varDecl2629 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_varDeclNoExpr2640 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_ID_in_varDeclNoExpr2642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varDecl_in_varDecls2659 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_varDecls2662 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_varDecl_in_varDecls2664 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});

}