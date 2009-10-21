// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g 2009-10-21 14:27:06

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
    public String getGrammarFileName() { return "D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g"; }


    public static class actionGuards_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionGuards"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:110:1: actionGuards : 'guard' expressions -> expressions ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:110:13: ( 'guard' expressions -> expressions )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:110:15: 'guard' expressions
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:1: actionInput : ( ID ':' )? '[' idents ']' ( actionRepeat )? ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:12: ( ( ID ':' )? '[' idents ']' ( actionRepeat )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:113:2: ( ID ':' )? '[' idents ']' ( actionRepeat )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:113:2: ( ID ':' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==ID) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:113:3: ID ':'
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

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:113:27: ( actionRepeat )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==80) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:113:27: actionRepeat
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:1: actionInputs : actionInput ( ',' actionInput )* -> ( actionInput )+ ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:13: ( actionInput ( ',' actionInput )* -> ( actionInput )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:15: actionInput ( ',' actionInput )*
            {
            pushFollow(FOLLOW_actionInput_in_actionInputs340);
            actionInput9=actionInput();

            state._fsp--;

            stream_actionInput.add(actionInput9.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:27: ( ',' actionInput )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==79) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:116:28: ',' actionInput
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:118:1: actionOutput : ( ID ':' )? '[' expressions ']' ( actionRepeat )? ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:118:13: ( ( ID ':' )? '[' expressions ']' ( actionRepeat )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:2: ( ID ':' )? '[' expressions ']' ( actionRepeat )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:2: ( ID ':' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==ID) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:3: ID ':'
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

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:32: ( actionRepeat )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==80) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:32: actionRepeat
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:122:1: actionOutputs : actionOutput ( ',' actionOutput )* -> ( actionOutput )+ ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:122:14: ( actionOutput ( ',' actionOutput )* -> ( actionOutput )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:122:16: actionOutput ( ',' actionOutput )*
            {
            pushFollow(FOLLOW_actionOutput_in_actionOutputs384);
            actionOutput18=actionOutput();

            state._fsp--;

            stream_actionOutput.add(actionOutput18.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:122:29: ( ',' actionOutput )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==79) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:122:30: ',' actionOutput
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:124:1: actionRepeat : 'repeat' expression -> expression ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:124:13: ( 'repeat' expression -> expression )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:124:15: 'repeat' expression
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:1: actionStatements : 'do' ( statement )* -> ( statement )* ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:17: ( 'do' ( statement )* -> ( statement )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:19: 'do' ( statement )*
            {
            string_literal23=(Token)match(input,81,FOLLOW_81_in_actionStatements416);  
            stream_81.add(string_literal23);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:24: ( statement )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==ID||LA7_0==92||LA7_0==95||LA7_0==103||LA7_0==105) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:24: statement
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
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:38: ( statement )*
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:128:1: actor : ( actorImport )* 'actor' id= ID ( '[' ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' actorDeclarations 'end' EOF -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS actorDeclarations ) ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:6: ( ( actorImport )* 'actor' id= ID ( '[' ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' actorDeclarations 'end' EOF -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS actorDeclarations ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:8: ( actorImport )* 'actor' id= ID ( '[' ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' actorDeclarations 'end' EOF
            {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:8: ( actorImport )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==93) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:8: actorImport
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

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:35: ( '[' ']' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==77) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:36: '[' ']'
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

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:50: ( actorParameters )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==ID) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:50: actorParameters
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

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:132:8: (inputs= actorPortDecls )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==ID) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:132:8: inputs= actorPortDecls
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

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:132:38: (outputs= actorPortDecls )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==ID) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:132:38: outputs= actorPortDecls
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

            pushFollow(FOLLOW_actorDeclarations_in_actor475);
            actorDeclarations34=actorDeclarations();

            state._fsp--;

            stream_actorDeclarations.add(actorDeclarations34.getTree());
            string_literal35=(Token)match(input,86,FOLLOW_86_in_actor477);  
            stream_86.add(string_literal35);

            EOF36=(Token)match(input,EOF,FOLLOW_EOF_in_actor479);  
            stream_EOF.add(EOF36);



            // AST REWRITE
            // elements: outputs, inputs, actorDeclarations, 82, actorParameters, id
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
            // 134:2: -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS actorDeclarations )
            {
                adaptor.addChild(root_0, stream_82.nextNode());
                adaptor.addChild(root_0, stream_id.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:135:2: ^( PARAMETERS ( actorParameters )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETERS, "PARAMETERS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:135:15: ( actorParameters )?
                if ( stream_actorParameters.hasNext() ) {
                    adaptor.addChild(root_1, stream_actorParameters.nextTree());

                }
                stream_actorParameters.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:2: ^( INPUTS ( $inputs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:11: ( $inputs)?
                if ( stream_inputs.hasNext() ) {
                    adaptor.addChild(root_1, stream_inputs.nextTree());

                }
                stream_inputs.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:137:2: ^( OUTPUTS ( $outputs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:137:12: ( $outputs)?
                if ( stream_outputs.hasNext() ) {
                    adaptor.addChild(root_1, stream_outputs.nextTree());

                }
                stream_outputs.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:138:2: ^( ACTOR_DECLS actorDeclarations )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:140:1: actorDeclaration : ( ID ( ( ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' ) | ACTION ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | priorityOrder -> priorityOrder | FUNCTION ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end' -> FUNCTION | PROCEDURE ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end' -> PROCEDURE );
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:143:17: ( ID ( ( ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' ) | ACTION ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | priorityOrder -> priorityOrder | FUNCTION ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end' -> FUNCTION | PROCEDURE ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end' -> PROCEDURE )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:3: ID ( ( ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' )
                    {
                    ID37=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration544);  
                    stream_ID.add(ID37);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:6: ( ( ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' )
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==76||LA26_0==87) ) {
                        alt26=1;
                    }
                    else if ( (LA26_0==ID||LA26_0==83) ) {
                        alt26=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 26, 0, input);

                        throw nvae;
                    }
                    switch (alt26) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:5: ( ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) )
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:5: ( ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) )
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:6: ( ( '.' tag+= ID )* ) ':' ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) )
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:6: ( ( '.' tag+= ID )* )
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:7: ( '.' tag+= ID )*
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:7: ( '.' tag+= ID )*
                            loop13:
                            do {
                                int alt13=2;
                                int LA13_0 = input.LA(1);

                                if ( (LA13_0==87) ) {
                                    alt13=1;
                                }


                                switch (alt13) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:8: '.' tag+= ID
                            	    {
                            	    char_literal38=(Token)match(input,87,FOLLOW_87_in_actorDeclaration555);  
                            	    stream_87.add(char_literal38);

                            	    tag=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration559);  
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

                            char_literal39=(Token)match(input,76,FOLLOW_76_in_actorDeclaration564);  
                            stream_76.add(char_literal39);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:7: ( ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) )
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
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:8: ACTION (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                                    {
                                    ACTION40=(Token)match(input,ACTION,FOLLOW_ACTION_in_actorDeclaration573);  
                                    stream_ACTION.add(ACTION40);

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:21: (inputs= actionInputs )?
                                    int alt14=2;
                                    int LA14_0 = input.LA(1);

                                    if ( (LA14_0==ID||LA14_0==77) ) {
                                        alt14=1;
                                    }
                                    switch (alt14) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:21: inputs= actionInputs
                                            {
                                            pushFollow(FOLLOW_actionInputs_in_actorDeclaration577);
                                            inputs=actionInputs();

                                            state._fsp--;

                                            stream_actionInputs.add(inputs.getTree());

                                            }
                                            break;

                                    }

                                    string_literal41=(Token)match(input,85,FOLLOW_85_in_actorDeclaration580);  
                                    stream_85.add(string_literal41);

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:49: (outputs= actionOutputs )?
                                    int alt15=2;
                                    int LA15_0 = input.LA(1);

                                    if ( (LA15_0==ID||LA15_0==77) ) {
                                        alt15=1;
                                    }
                                    switch (alt15) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:49: outputs= actionOutputs
                                            {
                                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration584);
                                            outputs=actionOutputs();

                                            state._fsp--;

                                            stream_actionOutputs.add(outputs.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:71: (guards= actionGuards )?
                                    int alt16=2;
                                    int LA16_0 = input.LA(1);

                                    if ( (LA16_0==75) ) {
                                        alt16=1;
                                    }
                                    switch (alt16) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:71: guards= actionGuards
                                            {
                                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration589);
                                            guards=actionGuards();

                                            state._fsp--;

                                            stream_actionGuards.add(guards.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:86: ( 'var' varDecls )?
                                    int alt17=2;
                                    int LA17_0 = input.LA(1);

                                    if ( (LA17_0==88) ) {
                                        alt17=1;
                                    }
                                    switch (alt17) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:87: 'var' varDecls
                                            {
                                            string_literal42=(Token)match(input,88,FOLLOW_88_in_actorDeclaration593);  
                                            stream_88.add(string_literal42);

                                            pushFollow(FOLLOW_varDecls_in_actorDeclaration595);
                                            varDecls43=varDecls();

                                            state._fsp--;

                                            stream_varDecls.add(varDecls43.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:104: ( actionStatements )?
                                    int alt18=2;
                                    int LA18_0 = input.LA(1);

                                    if ( (LA18_0==81) ) {
                                        alt18=1;
                                    }
                                    switch (alt18) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:104: actionStatements
                                            {
                                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration599);
                                            actionStatements44=actionStatements();

                                            state._fsp--;

                                            stream_actionStatements.add(actionStatements44.getTree());

                                            }
                                            break;

                                    }

                                    string_literal45=(Token)match(input,86,FOLLOW_86_in_actorDeclaration602);  
                                    stream_86.add(string_literal45);



                                    // AST REWRITE
                                    // elements: guards, outputs, ID, tag, varDecls, actionStatements, ACTION, inputs
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
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:12: ^( ACTION ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot(stream_ACTION.nextNode(), root_1);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:21: ^( TAG ID ( $tag)* )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TAG, "TAG"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:30: ( $tag)*
                                        while ( stream_tag.hasNext() ) {
                                            adaptor.addChild(root_2, stream_tag.nextNode());

                                        }
                                        stream_tag.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:37: ^( INPUTS ( $inputs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:46: ( $inputs)?
                                        if ( stream_inputs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_inputs.nextTree());

                                        }
                                        stream_inputs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:56: ^( OUTPUTS ( $outputs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:66: ( $outputs)?
                                        if ( stream_outputs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_outputs.nextTree());

                                        }
                                        stream_outputs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:77: ^( GUARDS ( $guards)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:86: ( $guards)?
                                        if ( stream_guards.hasNext() ) {
                                            adaptor.addChild(root_2, stream_guards.nextTree());

                                        }
                                        stream_guards.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:96: ^( VARS ( varDecls )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARS, "VARS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:103: ( varDecls )?
                                        if ( stream_varDecls.hasNext() ) {
                                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                                        }
                                        stream_varDecls.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:114: ^( STATEMENTS ( actionStatements )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:127: ( actionStatements )?
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
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:7: INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                                    {
                                    INITIALIZE46=(Token)match(input,INITIALIZE,FOLLOW_INITIALIZE_in_actorDeclaration673);  
                                    stream_INITIALIZE.add(INITIALIZE46);

                                    string_literal47=(Token)match(input,85,FOLLOW_85_in_actorDeclaration675);  
                                    stream_85.add(string_literal47);

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:24: ( actionOutputs )?
                                    int alt19=2;
                                    int LA19_0 = input.LA(1);

                                    if ( (LA19_0==ID||LA19_0==77) ) {
                                        alt19=1;
                                    }
                                    switch (alt19) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:24: actionOutputs
                                            {
                                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration677);
                                            actionOutputs48=actionOutputs();

                                            state._fsp--;

                                            stream_actionOutputs.add(actionOutputs48.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:39: ( actionGuards )?
                                    int alt20=2;
                                    int LA20_0 = input.LA(1);

                                    if ( (LA20_0==75) ) {
                                        alt20=1;
                                    }
                                    switch (alt20) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:39: actionGuards
                                            {
                                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration680);
                                            actionGuards49=actionGuards();

                                            state._fsp--;

                                            stream_actionGuards.add(actionGuards49.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:53: ( 'var' varDecls )?
                                    int alt21=2;
                                    int LA21_0 = input.LA(1);

                                    if ( (LA21_0==88) ) {
                                        alt21=1;
                                    }
                                    switch (alt21) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:54: 'var' varDecls
                                            {
                                            string_literal50=(Token)match(input,88,FOLLOW_88_in_actorDeclaration684);  
                                            stream_88.add(string_literal50);

                                            pushFollow(FOLLOW_varDecls_in_actorDeclaration686);
                                            varDecls51=varDecls();

                                            state._fsp--;

                                            stream_varDecls.add(varDecls51.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:71: ( actionStatements )?
                                    int alt22=2;
                                    int LA22_0 = input.LA(1);

                                    if ( (LA22_0==81) ) {
                                        alt22=1;
                                    }
                                    switch (alt22) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:71: actionStatements
                                            {
                                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration690);
                                            actionStatements52=actionStatements();

                                            state._fsp--;

                                            stream_actionStatements.add(actionStatements52.getTree());

                                            }
                                            break;

                                    }

                                    string_literal53=(Token)match(input,86,FOLLOW_86_in_actorDeclaration693);  
                                    stream_86.add(string_literal53);



                                    // AST REWRITE
                                    // elements: tag, actionStatements, varDecls, INITIALIZE, guards, outputs, ID
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
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:12: ^( INITIALIZE ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot(stream_INITIALIZE.nextNode(), root_1);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:25: ^( TAG ID ( $tag)* )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TAG, "TAG"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:34: ( $tag)*
                                        while ( stream_tag.hasNext() ) {
                                            adaptor.addChild(root_2, stream_tag.nextNode());

                                        }
                                        stream_tag.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        adaptor.addChild(root_1, (Object)adaptor.create(INPUTS, "INPUTS"));
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:48: ^( OUTPUTS ( $outputs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:58: ( $outputs)?
                                        if ( stream_outputs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_outputs.nextTree());

                                        }
                                        stream_outputs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:69: ^( GUARDS ( $guards)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:78: ( $guards)?
                                        if ( stream_guards.hasNext() ) {
                                            adaptor.addChild(root_2, stream_guards.nextTree());

                                        }
                                        stream_guards.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:88: ^( VARS ( varDecls )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARS, "VARS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:95: ( varDecls )?
                                        if ( stream_varDecls.hasNext() ) {
                                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                                        }
                                        stream_varDecls.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:106: ^( STATEMENTS ( actionStatements )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:119: ( actionStatements )?
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
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:160:5: ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';'
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:160:5: ( '(' attrs= typeAttrs ')' )?
                            int alt24=2;
                            int LA24_0 = input.LA(1);

                            if ( (LA24_0==83) ) {
                                alt24=1;
                            }
                            switch (alt24) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:160:6: '(' attrs= typeAttrs ')'
                                    {
                                    char_literal54=(Token)match(input,83,FOLLOW_83_in_actorDeclaration772);  
                                    stream_83.add(char_literal54);

                                    pushFollow(FOLLOW_typeAttrs_in_actorDeclaration776);
                                    attrs=typeAttrs();

                                    state._fsp--;

                                    stream_typeAttrs.add(attrs.getTree());
                                    char_literal55=(Token)match(input,84,FOLLOW_84_in_actorDeclaration778);  
                                    stream_84.add(char_literal55);


                                    }
                                    break;

                            }

                            varName=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration788);  
                            stream_ID.add(varName);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:5: ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) )
                            int alt25=3;
                            switch ( input.LA(1) ) {
                            case EQ:
                                {
                                alt25=1;
                                }
                                break;
                            case 89:
                                {
                                alt25=2;
                                }
                                break;
                            case 90:
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
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:8: '=' expression
                                    {
                                    char_literal56=(Token)match(input,EQ,FOLLOW_EQ_in_actorDeclaration797);  
                                    stream_EQ.add(char_literal56);

                                    pushFollow(FOLLOW_expression_in_actorDeclaration799);
                                    expression57=expression();

                                    state._fsp--;

                                    stream_expression.add(expression57.getTree());


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
                                    // 162:23: -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression )
                                    {
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:26: ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:38: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:48: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:61: ( $attrs)?
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
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:8: ':=' expression
                                    {
                                    string_literal58=(Token)match(input,89,FOLLOW_89_in_actorDeclaration835);  
                                    stream_89.add(string_literal58);

                                    pushFollow(FOLLOW_expression_in_actorDeclaration837);
                                    expression59=expression();

                                    state._fsp--;

                                    stream_expression.add(expression59.getTree());


                                    // AST REWRITE
                                    // elements: expression, varName, attrs, ID
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
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:27: ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:39: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:49: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:62: ( $attrs)?
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
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:8: 
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
                                    // 164:8: -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE )
                                    {
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:11: ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:23: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:33: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:46: ( $attrs)?
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

                            char_literal60=(Token)match(input,90,FOLLOW_90_in_actorDeclaration899);  
                            stream_90.add(char_literal60);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:3: ACTION ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                    {
                    ACTION61=(Token)match(input,ACTION,FOLLOW_ACTION_in_actorDeclaration909);  
                    stream_ACTION.add(ACTION61);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:10: ( actionInputs )?
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==ID||LA27_0==77) ) {
                        alt27=1;
                    }
                    switch (alt27) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:10: actionInputs
                            {
                            pushFollow(FOLLOW_actionInputs_in_actorDeclaration911);
                            actionInputs62=actionInputs();

                            state._fsp--;

                            stream_actionInputs.add(actionInputs62.getTree());

                            }
                            break;

                    }

                    string_literal63=(Token)match(input,85,FOLLOW_85_in_actorDeclaration914);  
                    stream_85.add(string_literal63);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:30: ( actionOutputs )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==ID||LA28_0==77) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:30: actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration916);
                            actionOutputs64=actionOutputs();

                            state._fsp--;

                            stream_actionOutputs.add(actionOutputs64.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:45: ( actionGuards )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==75) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:45: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration919);
                            actionGuards65=actionGuards();

                            state._fsp--;

                            stream_actionGuards.add(actionGuards65.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:59: ( 'var' varDecls )?
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==88) ) {
                        alt30=1;
                    }
                    switch (alt30) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:60: 'var' varDecls
                            {
                            string_literal66=(Token)match(input,88,FOLLOW_88_in_actorDeclaration923);  
                            stream_88.add(string_literal66);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration925);
                            varDecls67=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls67.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:77: ( actionStatements )?
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==81) ) {
                        alt31=1;
                    }
                    switch (alt31) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:77: actionStatements
                            {
                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration929);
                            actionStatements68=actionStatements();

                            state._fsp--;

                            stream_actionStatements.add(actionStatements68.getTree());

                            }
                            break;

                    }

                    string_literal69=(Token)match(input,86,FOLLOW_86_in_actorDeclaration932);  
                    stream_86.add(string_literal69);



                    // AST REWRITE
                    // elements: actionStatements, varDecls, inputs, ACTION, outputs, guards
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
                    // 169:3: -> ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:6: ^( ACTION TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_ACTION.nextNode(), root_1);

                        adaptor.addChild(root_1, (Object)adaptor.create(TAG, "TAG"));
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:19: ^( INPUTS ( $inputs)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:28: ( $inputs)?
                        if ( stream_inputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_inputs.nextTree());

                        }
                        stream_inputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:38: ^( OUTPUTS ( $outputs)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:48: ( $outputs)?
                        if ( stream_outputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_outputs.nextTree());

                        }
                        stream_outputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:59: ^( GUARDS ( $guards)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:68: ( $guards)?
                        if ( stream_guards.hasNext() ) {
                            adaptor.addChild(root_2, stream_guards.nextTree());

                        }
                        stream_guards.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:78: ^( VARS ( varDecls )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARS, "VARS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:85: ( varDecls )?
                        if ( stream_varDecls.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                        }
                        stream_varDecls.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:96: ^( STATEMENTS ( actionStatements )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:109: ( actionStatements )?
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:3: INITIALIZE '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                    {
                    INITIALIZE70=(Token)match(input,INITIALIZE,FOLLOW_INITIALIZE_in_actorDeclaration986);  
                    stream_INITIALIZE.add(INITIALIZE70);

                    string_literal71=(Token)match(input,85,FOLLOW_85_in_actorDeclaration988);  
                    stream_85.add(string_literal71);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:20: ( actionOutputs )?
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==ID||LA32_0==77) ) {
                        alt32=1;
                    }
                    switch (alt32) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:20: actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration990);
                            actionOutputs72=actionOutputs();

                            state._fsp--;

                            stream_actionOutputs.add(actionOutputs72.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:35: ( actionGuards )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==75) ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:35: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration993);
                            actionGuards73=actionGuards();

                            state._fsp--;

                            stream_actionGuards.add(actionGuards73.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:49: ( 'var' varDecls )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==88) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:50: 'var' varDecls
                            {
                            string_literal74=(Token)match(input,88,FOLLOW_88_in_actorDeclaration997);  
                            stream_88.add(string_literal74);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration999);
                            varDecls75=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls75.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:67: ( actionStatements )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==81) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:67: actionStatements
                            {
                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration1003);
                            actionStatements76=actionStatements();

                            state._fsp--;

                            stream_actionStatements.add(actionStatements76.getTree());

                            }
                            break;

                    }

                    string_literal77=(Token)match(input,86,FOLLOW_86_in_actorDeclaration1006);  
                    stream_86.add(string_literal77);



                    // AST REWRITE
                    // elements: guards, varDecls, INITIALIZE, outputs, actionStatements
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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:6: ^( INITIALIZE TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_INITIALIZE.nextNode(), root_1);

                        adaptor.addChild(root_1, (Object)adaptor.create(TAG, "TAG"));
                        adaptor.addChild(root_1, (Object)adaptor.create(INPUTS, "INPUTS"));
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:30: ^( OUTPUTS ( $outputs)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:40: ( $outputs)?
                        if ( stream_outputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_outputs.nextTree());

                        }
                        stream_outputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:51: ^( GUARDS ( $guards)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:60: ( $guards)?
                        if ( stream_guards.hasNext() ) {
                            adaptor.addChild(root_2, stream_guards.nextTree());

                        }
                        stream_guards.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:70: ^( VARS ( varDecls )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARS, "VARS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:77: ( varDecls )?
                        if ( stream_varDecls.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                        }
                        stream_varDecls.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:88: ^( STATEMENTS ( actionStatements )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:101: ( actionStatements )?
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:3: priorityOrder
                    {
                    pushFollow(FOLLOW_priorityOrder_in_actorDeclaration1053);
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:177:3: FUNCTION ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end'
                    {
                    FUNCTION79=(Token)match(input,FUNCTION,FOLLOW_FUNCTION_in_actorDeclaration1062);  
                    stream_FUNCTION.add(FUNCTION79);

                    ID80=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration1064);  
                    stream_ID.add(ID80);

                    char_literal81=(Token)match(input,83,FOLLOW_83_in_actorDeclaration1066);  
                    stream_83.add(char_literal81);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:177:19: ( varDeclNoExpr ( ',' varDeclNoExpr )* )?
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==ID) ) {
                        alt37=1;
                    }
                    switch (alt37) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:177:20: varDeclNoExpr ( ',' varDeclNoExpr )*
                            {
                            pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration1069);
                            varDeclNoExpr82=varDeclNoExpr();

                            state._fsp--;

                            stream_varDeclNoExpr.add(varDeclNoExpr82.getTree());
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:177:34: ( ',' varDeclNoExpr )*
                            loop36:
                            do {
                                int alt36=2;
                                int LA36_0 = input.LA(1);

                                if ( (LA36_0==79) ) {
                                    alt36=1;
                                }


                                switch (alt36) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:177:35: ',' varDeclNoExpr
                            	    {
                            	    char_literal83=(Token)match(input,79,FOLLOW_79_in_actorDeclaration1072);  
                            	    stream_79.add(char_literal83);

                            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration1074);
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

                    char_literal85=(Token)match(input,84,FOLLOW_84_in_actorDeclaration1080);  
                    stream_84.add(char_literal85);

                    string_literal86=(Token)match(input,91,FOLLOW_91_in_actorDeclaration1082);  
                    stream_91.add(string_literal86);

                    pushFollow(FOLLOW_typeDef_in_actorDeclaration1084);
                    typeDef87=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef87.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:5: ( 'var' varDecls )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==88) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:6: 'var' varDecls
                            {
                            string_literal88=(Token)match(input,88,FOLLOW_88_in_actorDeclaration1091);  
                            stream_88.add(string_literal88);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration1093);
                            varDecls89=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls89.getTree());

                            }
                            break;

                    }

                    char_literal90=(Token)match(input,76,FOLLOW_76_in_actorDeclaration1097);  
                    stream_76.add(char_literal90);

                    pushFollow(FOLLOW_expression_in_actorDeclaration1105);
                    expression91=expression();

                    state._fsp--;

                    stream_expression.add(expression91.getTree());
                    string_literal92=(Token)match(input,86,FOLLOW_86_in_actorDeclaration1111);  
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:3: PROCEDURE ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end'
                    {
                    PROCEDURE93=(Token)match(input,PROCEDURE,FOLLOW_PROCEDURE_in_actorDeclaration1121);  
                    stream_PROCEDURE.add(PROCEDURE93);

                    ID94=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration1123);  
                    stream_ID.add(ID94);

                    char_literal95=(Token)match(input,83,FOLLOW_83_in_actorDeclaration1125);  
                    stream_83.add(char_literal95);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:20: ( varDeclNoExpr ( ',' varDeclNoExpr )* )?
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( (LA40_0==ID) ) {
                        alt40=1;
                    }
                    switch (alt40) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:21: varDeclNoExpr ( ',' varDeclNoExpr )*
                            {
                            pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration1128);
                            varDeclNoExpr96=varDeclNoExpr();

                            state._fsp--;

                            stream_varDeclNoExpr.add(varDeclNoExpr96.getTree());
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:35: ( ',' varDeclNoExpr )*
                            loop39:
                            do {
                                int alt39=2;
                                int LA39_0 = input.LA(1);

                                if ( (LA39_0==79) ) {
                                    alt39=1;
                                }


                                switch (alt39) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:36: ',' varDeclNoExpr
                            	    {
                            	    char_literal97=(Token)match(input,79,FOLLOW_79_in_actorDeclaration1131);  
                            	    stream_79.add(char_literal97);

                            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration1133);
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

                    char_literal99=(Token)match(input,84,FOLLOW_84_in_actorDeclaration1139);  
                    stream_84.add(char_literal99);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:5: ( 'var' varDecls )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==88) ) {
                        alt41=1;
                    }
                    switch (alt41) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:6: 'var' varDecls
                            {
                            string_literal100=(Token)match(input,88,FOLLOW_88_in_actorDeclaration1146);  
                            stream_88.add(string_literal100);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration1148);
                            varDecls101=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls101.getTree());

                            }
                            break;

                    }

                    string_literal102=(Token)match(input,92,FOLLOW_92_in_actorDeclaration1156);  
                    stream_92.add(string_literal102);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:185:13: ( statement )*
                    loop42:
                    do {
                        int alt42=2;
                        int LA42_0 = input.LA(1);

                        if ( (LA42_0==ID||LA42_0==92||LA42_0==95||LA42_0==103||LA42_0==105) ) {
                            alt42=1;
                        }


                        switch (alt42) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:185:13: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_actorDeclaration1158);
                    	    statement103=statement();

                    	    state._fsp--;

                    	    stream_statement.add(statement103.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop42;
                        }
                    } while (true);

                    string_literal104=(Token)match(input,86,FOLLOW_86_in_actorDeclaration1161);  
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:1: actorDeclarations : ( actorDeclaration )* ( schedule ( actorDeclaration )* )? -> ( actorDeclaration )* ( schedule )? ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:18: ( ( actorDeclaration )* ( schedule ( actorDeclaration )* )? -> ( actorDeclaration )* ( schedule )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:20: ( actorDeclaration )* ( schedule ( actorDeclaration )* )?
            {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:20: ( actorDeclaration )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( ((LA44_0>=ID && LA44_0<=PROCEDURE)||LA44_0==PRIORITY) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:20: actorDeclaration
            	    {
            	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations1173);
            	    actorDeclaration105=actorDeclaration();

            	    state._fsp--;

            	    stream_actorDeclaration.add(actorDeclaration105.getTree());

            	    }
            	    break;

            	default :
            	    break loop44;
                }
            } while (true);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:38: ( schedule ( actorDeclaration )* )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==SCHEDULE) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:39: schedule ( actorDeclaration )*
                    {
                    pushFollow(FOLLOW_schedule_in_actorDeclarations1177);
                    schedule106=schedule();

                    state._fsp--;

                    stream_schedule.add(schedule106.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:48: ( actorDeclaration )*
                    loop45:
                    do {
                        int alt45=2;
                        int LA45_0 = input.LA(1);

                        if ( ((LA45_0>=ID && LA45_0<=PROCEDURE)||LA45_0==PRIORITY) ) {
                            alt45=1;
                        }


                        switch (alt45) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:48: actorDeclaration
                    	    {
                    	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations1179);
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
            // 188:68: -> ( actorDeclaration )* ( schedule )?
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:71: ( actorDeclaration )*
                while ( stream_actorDeclaration.hasNext() ) {
                    adaptor.addChild(root_0, stream_actorDeclaration.nextTree());

                }
                stream_actorDeclaration.reset();
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:89: ( schedule )?
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:190:1: actorImport : 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:193:12: ( 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:193:14: 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            {
            root_0 = (Object)adaptor.nil();

            string_literal108=(Token)match(input,93,FOLLOW_93_in_actorImport1202); 
            string_literal108_tree = (Object)adaptor.create(string_literal108);
            adaptor.addChild(root_0, string_literal108_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:194:3: ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==94) ) {
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:194:4: 'all' qualifiedIdent ';'
                    {
                    string_literal109=(Token)match(input,94,FOLLOW_94_in_actorImport1207); 
                    string_literal109_tree = (Object)adaptor.create(string_literal109);
                    adaptor.addChild(root_0, string_literal109_tree);

                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport1209);
                    qualifiedIdent110=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent110.getTree());
                    char_literal111=(Token)match(input,90,FOLLOW_90_in_actorImport1211); 
                    char_literal111_tree = (Object)adaptor.create(char_literal111);
                    adaptor.addChild(root_0, char_literal111_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:3: qualifiedIdent ';'
                    {
                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport1217);
                    qualifiedIdent112=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent112.getTree());
                    char_literal113=(Token)match(input,90,FOLLOW_90_in_actorImport1219); 
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:197:1: actorParameter : typeDef ID ( '=' expression )? -> ^( PARAMETER typeDef ID ( expression )? ) ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:199:15: ( typeDef ID ( '=' expression )? -> ^( PARAMETER typeDef ID ( expression )? ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:2: typeDef ID ( '=' expression )?
            {
            pushFollow(FOLLOW_typeDef_in_actorParameter1234);
            typeDef114=typeDef();

            state._fsp--;

            stream_typeDef.add(typeDef114.getTree());
            ID115=(Token)match(input,ID,FOLLOW_ID_in_actorParameter1236);  
            stream_ID.add(ID115);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:13: ( '=' expression )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==EQ) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:14: '=' expression
                    {
                    char_literal116=(Token)match(input,EQ,FOLLOW_EQ_in_actorParameter1239);  
                    stream_EQ.add(char_literal116);

                    pushFollow(FOLLOW_expression_in_actorParameter1241);
                    expression117=expression();

                    state._fsp--;

                    stream_expression.add(expression117.getTree());

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
            // 200:31: -> ^( PARAMETER typeDef ID ( expression )? )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:34: ^( PARAMETER typeDef ID ( expression )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETER, "PARAMETER"), root_1);

                adaptor.addChild(root_1, stream_typeDef.nextTree());
                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:57: ( expression )?
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:202:1: actorParameters : actorParameter ( ',' actorParameter )* -> ( actorParameter )+ ;
    public final RVCCalParser.actorParameters_return actorParameters() throws RecognitionException {
        RVCCalParser.actorParameters_return retval = new RVCCalParser.actorParameters_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal119=null;
        RVCCalParser.actorParameter_return actorParameter118 = null;

        RVCCalParser.actorParameter_return actorParameter120 = null;


        Object char_literal119_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleSubtreeStream stream_actorParameter=new RewriteRuleSubtreeStream(adaptor,"rule actorParameter");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:202:16: ( actorParameter ( ',' actorParameter )* -> ( actorParameter )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:202:18: actorParameter ( ',' actorParameter )*
            {
            pushFollow(FOLLOW_actorParameter_in_actorParameters1263);
            actorParameter118=actorParameter();

            state._fsp--;

            stream_actorParameter.add(actorParameter118.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:202:33: ( ',' actorParameter )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==79) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:202:34: ',' actorParameter
            	    {
            	    char_literal119=(Token)match(input,79,FOLLOW_79_in_actorParameters1266);  
            	    stream_79.add(char_literal119);

            	    pushFollow(FOLLOW_actorParameter_in_actorParameters1268);
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
            // 202:55: -> ( actorParameter )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:204:1: actorPortDecls : varDeclNoExpr ( ',' varDeclNoExpr )* -> ( varDeclNoExpr )+ ;
    public final RVCCalParser.actorPortDecls_return actorPortDecls() throws RecognitionException {
        RVCCalParser.actorPortDecls_return retval = new RVCCalParser.actorPortDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal122=null;
        RVCCalParser.varDeclNoExpr_return varDeclNoExpr121 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr123 = null;


        Object char_literal122_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleSubtreeStream stream_varDeclNoExpr=new RewriteRuleSubtreeStream(adaptor,"rule varDeclNoExpr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:207:15: ( varDeclNoExpr ( ',' varDeclNoExpr )* -> ( varDeclNoExpr )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:207:17: varDeclNoExpr ( ',' varDeclNoExpr )*
            {
            pushFollow(FOLLOW_varDeclNoExpr_in_actorPortDecls1287);
            varDeclNoExpr121=varDeclNoExpr();

            state._fsp--;

            stream_varDeclNoExpr.add(varDeclNoExpr121.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:207:31: ( ',' varDeclNoExpr )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==79) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:207:32: ',' varDeclNoExpr
            	    {
            	    char_literal122=(Token)match(input,79,FOLLOW_79_in_actorPortDecls1290);  
            	    stream_79.add(char_literal122);

            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorPortDecls1292);
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
            // 207:52: -> ( varDeclNoExpr )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:209:1: expression : un_expr ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr ) ;
    public final RVCCalParser.expression_return expression() throws RecognitionException {
        RVCCalParser.expression_return retval = new RVCCalParser.expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        RVCCalParser.un_expr_return un_expr124 = null;

        RVCCalParser.bop_return bop125 = null;

        RVCCalParser.un_expr_return un_expr126 = null;


        RewriteRuleSubtreeStream stream_bop=new RewriteRuleSubtreeStream(adaptor,"rule bop");
        RewriteRuleSubtreeStream stream_un_expr=new RewriteRuleSubtreeStream(adaptor,"rule un_expr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:214:11: ( un_expr ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:214:13: un_expr ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr )
            {
            pushFollow(FOLLOW_un_expr_in_expression1313);
            un_expr124=un_expr();

            state._fsp--;

            stream_un_expr.add(un_expr124.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:214:21: ( ( bop un_expr )+ -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) ) | -> un_expr )
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( ((LA52_0>=OR && LA52_0<=EXP)) ) {
                alt52=1;
            }
            else if ( ((LA52_0>=75 && LA52_0<=76)||(LA52_0>=78 && LA52_0<=79)||LA52_0==81||(LA52_0>=84 && LA52_0<=86)||LA52_0==88||LA52_0==90||LA52_0==92||(LA52_0>=96 && LA52_0<=97)||LA52_0==104) ) {
                alt52=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 52, 0, input);

                throw nvae;
            }
            switch (alt52) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:214:22: ( bop un_expr )+
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:214:22: ( bop un_expr )+
                    int cnt51=0;
                    loop51:
                    do {
                        int alt51=2;
                        int LA51_0 = input.LA(1);

                        if ( ((LA51_0>=OR && LA51_0<=EXP)) ) {
                            alt51=1;
                        }


                        switch (alt51) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:214:23: bop un_expr
                    	    {
                    	    pushFollow(FOLLOW_bop_in_expression1317);
                    	    bop125=bop();

                    	    state._fsp--;

                    	    stream_bop.add(bop125.getTree());
                    	    pushFollow(FOLLOW_un_expr_in_expression1319);
                    	    un_expr126=un_expr();

                    	    state._fsp--;

                    	    stream_un_expr.add(un_expr126.getTree());

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
                    // elements: bop, un_expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 214:37: -> ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:214:40: ^( EXPR_BINARY ^( EXPR ( un_expr )+ ) ^( OP ( bop )+ ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BINARY, "EXPR_BINARY"), root_1);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:214:54: ^( EXPR ( un_expr )+ )
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
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:214:71: ^( OP ( bop )+ )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:214:85: 
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
                    // 214:85: -> un_expr
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:216:1: bop : ( OR | AND | BITOR | BITAND | EQ | NE | LT | GT | LE | GE | SHIFT_LEFT | SHIFT_RIGHT | PLUS | MINUS | DIV | DIV_INT | MOD | TIMES | EXP );
    public final RVCCalParser.bop_return bop() throws RecognitionException {
        RVCCalParser.bop_return retval = new RVCCalParser.bop_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set127=null;

        Object set127_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:216:4: ( OR | AND | BITOR | BITAND | EQ | NE | LT | GT | LE | GE | SHIFT_LEFT | SHIFT_RIGHT | PLUS | MINUS | DIV | DIV_INT | MOD | TIMES | EXP )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:
            {
            root_0 = (Object)adaptor.nil();

            set127=(Token)input.LT(1);
            if ( (input.LA(1)>=OR && input.LA(1)<=EXP) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set127));
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:1: un_expr : ( postfix_expression -> postfix_expression | (op= MINUS | op= NOT | op= NUM_ELTS ) un_expr -> ^( EXPR_UNARY $op un_expr ) );
    public final RVCCalParser.un_expr_return un_expr() throws RecognitionException {
        RVCCalParser.un_expr_return retval = new RVCCalParser.un_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        RVCCalParser.postfix_expression_return postfix_expression128 = null;

        RVCCalParser.un_expr_return un_expr129 = null;


        Object op_tree=null;
        RewriteRuleTokenStream stream_NUM_ELTS=new RewriteRuleTokenStream(adaptor,"token NUM_ELTS");
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleTokenStream stream_MINUS=new RewriteRuleTokenStream(adaptor,"token MINUS");
        RewriteRuleSubtreeStream stream_postfix_expression=new RewriteRuleSubtreeStream(adaptor,"rule postfix_expression");
        RewriteRuleSubtreeStream stream_un_expr=new RewriteRuleSubtreeStream(adaptor,"rule un_expr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:8: ( postfix_expression -> postfix_expression | (op= MINUS | op= NOT | op= NUM_ELTS ) un_expr -> ^( EXPR_UNARY $op un_expr ) )
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==ID||(LA54_0>=FLOAT && LA54_0<=STRING)||LA54_0==77||LA54_0==83||LA54_0==95||(LA54_0>=98 && LA54_0<=99)) ) {
                alt54=1;
            }
            else if ( (LA54_0==MINUS||(LA54_0>=NOT && LA54_0<=NUM_ELTS)) ) {
                alt54=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 54, 0, input);

                throw nvae;
            }
            switch (alt54) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:10: postfix_expression
                    {
                    pushFollow(FOLLOW_postfix_expression_in_un_expr1435);
                    postfix_expression128=postfix_expression();

                    state._fsp--;

                    stream_postfix_expression.add(postfix_expression128.getTree());


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
                    // 227:29: -> postfix_expression
                    {
                        adaptor.addChild(root_0, stream_postfix_expression.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:4: (op= MINUS | op= NOT | op= NUM_ELTS ) un_expr
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:4: (op= MINUS | op= NOT | op= NUM_ELTS )
                    int alt53=3;
                    switch ( input.LA(1) ) {
                    case MINUS:
                        {
                        alt53=1;
                        }
                        break;
                    case NOT:
                        {
                        alt53=2;
                        }
                        break;
                    case NUM_ELTS:
                        {
                        alt53=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 53, 0, input);

                        throw nvae;
                    }

                    switch (alt53) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:5: op= MINUS
                            {
                            op=(Token)match(input,MINUS,FOLLOW_MINUS_in_un_expr1447);  
                            stream_MINUS.add(op);


                            }
                            break;
                        case 2 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:16: op= NOT
                            {
                            op=(Token)match(input,NOT,FOLLOW_NOT_in_un_expr1453);  
                            stream_NOT.add(op);


                            }
                            break;
                        case 3 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:25: op= NUM_ELTS
                            {
                            op=(Token)match(input,NUM_ELTS,FOLLOW_NUM_ELTS_in_un_expr1459);  
                            stream_NUM_ELTS.add(op);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_un_expr_in_un_expr1462);
                    un_expr129=un_expr();

                    state._fsp--;

                    stream_un_expr.add(un_expr129.getTree());


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
                    // 228:46: -> ^( EXPR_UNARY $op un_expr )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:49: ^( EXPR_UNARY $op un_expr )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:230:1: postfix_expression : ( '[' e= expressions ( ':' g= expressionGenerators )? ']' -> ^( EXPR_LIST $e ( $g)? ) | 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end' -> ^( EXPR_IF $e1 $e2 $e3) | constant -> constant | '(' expression ')' -> expression | var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) ) );
    public final RVCCalParser.postfix_expression_return postfix_expression() throws RecognitionException {
        RVCCalParser.postfix_expression_return retval = new RVCCalParser.postfix_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token var=null;
        Token char_literal130=null;
        Token char_literal131=null;
        Token char_literal132=null;
        Token string_literal133=null;
        Token string_literal134=null;
        Token string_literal135=null;
        Token string_literal136=null;
        Token char_literal138=null;
        Token char_literal140=null;
        Token char_literal141=null;
        Token char_literal143=null;
        Token char_literal144=null;
        Token char_literal146=null;
        RVCCalParser.expressions_return e = null;

        RVCCalParser.expressionGenerators_return g = null;

        RVCCalParser.expression_return e1 = null;

        RVCCalParser.expression_return e2 = null;

        RVCCalParser.expression_return e3 = null;

        RVCCalParser.constant_return constant137 = null;

        RVCCalParser.expression_return expression139 = null;

        RVCCalParser.expressions_return expressions142 = null;

        RVCCalParser.expressions_return expressions145 = null;


        Object var_tree=null;
        Object char_literal130_tree=null;
        Object char_literal131_tree=null;
        Object char_literal132_tree=null;
        Object string_literal133_tree=null;
        Object string_literal134_tree=null;
        Object string_literal135_tree=null;
        Object string_literal136_tree=null;
        Object char_literal138_tree=null;
        Object char_literal140_tree=null;
        Object char_literal141_tree=null;
        Object char_literal143_tree=null;
        Object char_literal144_tree=null;
        Object char_literal146_tree=null;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:230:19: ( '[' e= expressions ( ':' g= expressionGenerators )? ']' -> ^( EXPR_LIST $e ( $g)? ) | 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end' -> ^( EXPR_IF $e1 $e2 $e3) | constant -> constant | '(' expression ')' -> expression | var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) ) )
            int alt59=5;
            switch ( input.LA(1) ) {
            case 77:
                {
                alt59=1;
                }
                break;
            case 95:
                {
                alt59=2;
                }
                break;
            case FLOAT:
            case INTEGER:
            case STRING:
            case 98:
            case 99:
                {
                alt59=3;
                }
                break;
            case 83:
                {
                alt59=4;
                }
                break;
            case ID:
                {
                alt59=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }

            switch (alt59) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:3: '[' e= expressions ( ':' g= expressionGenerators )? ']'
                    {
                    char_literal130=(Token)match(input,77,FOLLOW_77_in_postfix_expression1482);  
                    stream_77.add(char_literal130);

                    pushFollow(FOLLOW_expressions_in_postfix_expression1486);
                    e=expressions();

                    state._fsp--;

                    stream_expressions.add(e.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:21: ( ':' g= expressionGenerators )?
                    int alt55=2;
                    int LA55_0 = input.LA(1);

                    if ( (LA55_0==76) ) {
                        alt55=1;
                    }
                    switch (alt55) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:22: ':' g= expressionGenerators
                            {
                            char_literal131=(Token)match(input,76,FOLLOW_76_in_postfix_expression1489);  
                            stream_76.add(char_literal131);

                            pushFollow(FOLLOW_expressionGenerators_in_postfix_expression1493);
                            g=expressionGenerators();

                            state._fsp--;

                            stream_expressionGenerators.add(g.getTree());

                            }
                            break;

                    }

                    char_literal132=(Token)match(input,78,FOLLOW_78_in_postfix_expression1497);  
                    stream_78.add(char_literal132);



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
                    // 231:55: -> ^( EXPR_LIST $e ( $g)? )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:58: ^( EXPR_LIST $e ( $g)? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_LIST, "EXPR_LIST"), root_1);

                        adaptor.addChild(root_1, stream_e.nextTree());
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:73: ( $g)?
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:3: 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end'
                    {
                    string_literal133=(Token)match(input,95,FOLLOW_95_in_postfix_expression1514);  
                    stream_95.add(string_literal133);

                    pushFollow(FOLLOW_expression_in_postfix_expression1518);
                    e1=expression();

                    state._fsp--;

                    stream_expression.add(e1.getTree());
                    string_literal134=(Token)match(input,96,FOLLOW_96_in_postfix_expression1520);  
                    stream_96.add(string_literal134);

                    pushFollow(FOLLOW_expression_in_postfix_expression1524);
                    e2=expression();

                    state._fsp--;

                    stream_expression.add(e2.getTree());
                    string_literal135=(Token)match(input,97,FOLLOW_97_in_postfix_expression1526);  
                    stream_97.add(string_literal135);

                    pushFollow(FOLLOW_expression_in_postfix_expression1530);
                    e3=expression();

                    state._fsp--;

                    stream_expression.add(e3.getTree());
                    string_literal136=(Token)match(input,86,FOLLOW_86_in_postfix_expression1532);  
                    stream_86.add(string_literal136);



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
                    // 232:70: -> ^( EXPR_IF $e1 $e2 $e3)
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:73: ^( EXPR_IF $e1 $e2 $e3)
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:3: constant
                    {
                    pushFollow(FOLLOW_constant_in_postfix_expression1551);
                    constant137=constant();

                    state._fsp--;

                    stream_constant.add(constant137.getTree());


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
                    // 233:12: -> constant
                    {
                        adaptor.addChild(root_0, stream_constant.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:234:3: '(' expression ')'
                    {
                    char_literal138=(Token)match(input,83,FOLLOW_83_in_postfix_expression1559);  
                    stream_83.add(char_literal138);

                    pushFollow(FOLLOW_expression_in_postfix_expression1561);
                    expression139=expression();

                    state._fsp--;

                    stream_expression.add(expression139.getTree());
                    char_literal140=(Token)match(input,84,FOLLOW_84_in_postfix_expression1563);  
                    stream_84.add(char_literal140);



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
                    // 234:22: -> expression
                    {
                        adaptor.addChild(root_0, stream_expression.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:3: var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) )
                    {
                    var=(Token)match(input,ID,FOLLOW_ID_in_postfix_expression1573);  
                    stream_ID.add(var);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:10: ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) )
                    int alt58=3;
                    switch ( input.LA(1) ) {
                    case 83:
                        {
                        alt58=1;
                        }
                        break;
                    case 77:
                        {
                        alt58=2;
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
                        alt58=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 58, 0, input);

                        throw nvae;
                    }

                    switch (alt58) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:5: '(' ( expressions )? ')'
                            {
                            char_literal141=(Token)match(input,83,FOLLOW_83_in_postfix_expression1581);  
                            stream_83.add(char_literal141);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:9: ( expressions )?
                            int alt56=2;
                            int LA56_0 = input.LA(1);

                            if ( (LA56_0==ID||LA56_0==MINUS||(LA56_0>=NOT && LA56_0<=STRING)||LA56_0==77||LA56_0==83||LA56_0==95||(LA56_0>=98 && LA56_0<=99)) ) {
                                alt56=1;
                            }
                            switch (alt56) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:9: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_postfix_expression1583);
                                    expressions142=expressions();

                                    state._fsp--;

                                    stream_expressions.add(expressions142.getTree());

                                    }
                                    break;

                            }

                            char_literal143=(Token)match(input,84,FOLLOW_84_in_postfix_expression1586);  
                            stream_84.add(char_literal143);



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
                            // 236:26: -> ^( EXPR_CALL $var ( expressions )? )
                            {
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:29: ^( EXPR_CALL $var ( expressions )? )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_CALL, "EXPR_CALL"), root_1);

                                adaptor.addChild(root_1, stream_var.nextNode());
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:46: ( expressions )?
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
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:6: ( '[' expressions ']' )+
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:6: ( '[' expressions ']' )+
                            int cnt57=0;
                            loop57:
                            do {
                                int alt57=2;
                                int LA57_0 = input.LA(1);

                                if ( (LA57_0==77) ) {
                                    alt57=1;
                                }


                                switch (alt57) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:7: '[' expressions ']'
                            	    {
                            	    char_literal144=(Token)match(input,77,FOLLOW_77_in_postfix_expression1606);  
                            	    stream_77.add(char_literal144);

                            	    pushFollow(FOLLOW_expressions_in_postfix_expression1608);
                            	    expressions145=expressions();

                            	    state._fsp--;

                            	    stream_expressions.add(expressions145.getTree());
                            	    char_literal146=(Token)match(input,78,FOLLOW_78_in_postfix_expression1610);  
                            	    stream_78.add(char_literal146);


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
                            // 237:29: -> ^( EXPR_IDX $var ( expressions )+ )
                            {
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:32: ^( EXPR_IDX $var ( expressions )+ )
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
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:5: 
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
                            // 238:5: -> ^( EXPR_VAR $var)
                            {
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:8: ^( EXPR_VAR $var)
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:1: constant : ( 'true' -> ^( EXPR_BOOL 'true' ) | 'false' -> ^( EXPR_BOOL 'false' ) | FLOAT -> ^( EXPR_FLOAT FLOAT ) | INTEGER -> ^( EXPR_INT INTEGER ) | STRING -> ^( EXPR_STRING STRING ) );
    public final RVCCalParser.constant_return constant() throws RecognitionException {
        RVCCalParser.constant_return retval = new RVCCalParser.constant_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal147=null;
        Token string_literal148=null;
        Token FLOAT149=null;
        Token INTEGER150=null;
        Token STRING151=null;

        Object string_literal147_tree=null;
        Object string_literal148_tree=null;
        Object FLOAT149_tree=null;
        Object INTEGER150_tree=null;
        Object STRING151_tree=null;
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_98=new RewriteRuleTokenStream(adaptor,"token 98");
        RewriteRuleTokenStream stream_FLOAT=new RewriteRuleTokenStream(adaptor,"token FLOAT");
        RewriteRuleTokenStream stream_99=new RewriteRuleTokenStream(adaptor,"token 99");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:9: ( 'true' -> ^( EXPR_BOOL 'true' ) | 'false' -> ^( EXPR_BOOL 'false' ) | FLOAT -> ^( EXPR_FLOAT FLOAT ) | INTEGER -> ^( EXPR_INT INTEGER ) | STRING -> ^( EXPR_STRING STRING ) )
            int alt60=5;
            switch ( input.LA(1) ) {
            case 98:
                {
                alt60=1;
                }
                break;
            case 99:
                {
                alt60=2;
                }
                break;
            case FLOAT:
                {
                alt60=3;
                }
                break;
            case INTEGER:
                {
                alt60=4;
                }
                break;
            case STRING:
                {
                alt60=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;
            }

            switch (alt60) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:241:3: 'true'
                    {
                    string_literal147=(Token)match(input,98,FOLLOW_98_in_constant1647);  
                    stream_98.add(string_literal147);



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
                    // 241:10: -> ^( EXPR_BOOL 'true' )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:241:13: ^( EXPR_BOOL 'true' )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:3: 'false'
                    {
                    string_literal148=(Token)match(input,99,FOLLOW_99_in_constant1659);  
                    stream_99.add(string_literal148);



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
                    // 242:11: -> ^( EXPR_BOOL 'false' )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:14: ^( EXPR_BOOL 'false' )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:243:3: FLOAT
                    {
                    FLOAT149=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_constant1671);  
                    stream_FLOAT.add(FLOAT149);



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
                    // 243:9: -> ^( EXPR_FLOAT FLOAT )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:243:12: ^( EXPR_FLOAT FLOAT )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:3: INTEGER
                    {
                    INTEGER150=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_constant1683);  
                    stream_INTEGER.add(INTEGER150);



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
                    // 244:11: -> ^( EXPR_INT INTEGER )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:14: ^( EXPR_INT INTEGER )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:3: STRING
                    {
                    STRING151=(Token)match(input,STRING,FOLLOW_STRING_in_constant1695);  
                    stream_STRING.add(STRING151);



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
                    // 245:10: -> ^( EXPR_STRING STRING )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:13: ^( EXPR_STRING STRING )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:247:1: expressionGenerator : 'for' typeDef ID 'in' expression ;
    public final RVCCalParser.expressionGenerator_return expressionGenerator() throws RecognitionException {
        RVCCalParser.expressionGenerator_return retval = new RVCCalParser.expressionGenerator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal152=null;
        Token ID154=null;
        Token string_literal155=null;
        RVCCalParser.typeDef_return typeDef153 = null;

        RVCCalParser.expression_return expression156 = null;


        Object string_literal152_tree=null;
        Object ID154_tree=null;
        Object string_literal155_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:247:20: ( 'for' typeDef ID 'in' expression )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:248:2: 'for' typeDef ID 'in' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal152=(Token)match(input,100,FOLLOW_100_in_expressionGenerator1711); 
            string_literal152_tree = (Object)adaptor.create(string_literal152);
            adaptor.addChild(root_0, string_literal152_tree);

            pushFollow(FOLLOW_typeDef_in_expressionGenerator1713);
            typeDef153=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef153.getTree());
            ID154=(Token)match(input,ID,FOLLOW_ID_in_expressionGenerator1715); 
            ID154_tree = (Object)adaptor.create(ID154);
            adaptor.addChild(root_0, ID154_tree);

            string_literal155=(Token)match(input,101,FOLLOW_101_in_expressionGenerator1717); 
            string_literal155_tree = (Object)adaptor.create(string_literal155);
            adaptor.addChild(root_0, string_literal155_tree);

            pushFollow(FOLLOW_expression_in_expressionGenerator1719);
            expression156=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression156.getTree());
             

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:1: expressionGenerators : expressionGenerator ( ',' expressionGenerator )* -> ( expressionGenerator )+ ;
    public final RVCCalParser.expressionGenerators_return expressionGenerators() throws RecognitionException {
        RVCCalParser.expressionGenerators_return retval = new RVCCalParser.expressionGenerators_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal158=null;
        RVCCalParser.expressionGenerator_return expressionGenerator157 = null;

        RVCCalParser.expressionGenerator_return expressionGenerator159 = null;


        Object char_literal158_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleSubtreeStream stream_expressionGenerator=new RewriteRuleSubtreeStream(adaptor,"rule expressionGenerator");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:21: ( expressionGenerator ( ',' expressionGenerator )* -> ( expressionGenerator )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:23: expressionGenerator ( ',' expressionGenerator )*
            {
            pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators1729);
            expressionGenerator157=expressionGenerator();

            state._fsp--;

            stream_expressionGenerator.add(expressionGenerator157.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:43: ( ',' expressionGenerator )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==79) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:44: ',' expressionGenerator
            	    {
            	    char_literal158=(Token)match(input,79,FOLLOW_79_in_expressionGenerators1732);  
            	    stream_79.add(char_literal158);

            	    pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators1734);
            	    expressionGenerator159=expressionGenerator();

            	    state._fsp--;

            	    stream_expressionGenerator.add(expressionGenerator159.getTree());

            	    }
            	    break;

            	default :
            	    break loop61;
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
            // 251:70: -> ( expressionGenerator )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:253:1: expressions : expression ( ',' expression )* -> ( expression )+ ;
    public final RVCCalParser.expressions_return expressions() throws RecognitionException {
        RVCCalParser.expressions_return retval = new RVCCalParser.expressions_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal161=null;
        RVCCalParser.expression_return expression160 = null;

        RVCCalParser.expression_return expression162 = null;


        Object char_literal161_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:253:12: ( expression ( ',' expression )* -> ( expression )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:253:14: expression ( ',' expression )*
            {
            pushFollow(FOLLOW_expression_in_expressions1748);
            expression160=expression();

            state._fsp--;

            stream_expression.add(expression160.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:253:25: ( ',' expression )*
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( (LA62_0==79) ) {
                    alt62=1;
                }


                switch (alt62) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:253:26: ',' expression
            	    {
            	    char_literal161=(Token)match(input,79,FOLLOW_79_in_expressions1751);  
            	    stream_79.add(char_literal161);

            	    pushFollow(FOLLOW_expression_in_expressions1753);
            	    expression162=expression();

            	    state._fsp--;

            	    stream_expression.add(expression162.getTree());

            	    }
            	    break;

            	default :
            	    break loop62;
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
            // 253:43: -> ( expression )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:255:1: idents : ID ( ',' ID )* ;
    public final RVCCalParser.idents_return idents() throws RecognitionException {
        RVCCalParser.idents_return retval = new RVCCalParser.idents_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID163=null;
        Token char_literal164=null;
        Token ID165=null;

        Object ID163_tree=null;
        Object char_literal164_tree=null;
        Object ID165_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:258:7: ( ID ( ',' ID )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:258:9: ID ( ',' ID )*
            {
            root_0 = (Object)adaptor.nil();

            ID163=(Token)match(input,ID,FOLLOW_ID_in_idents1772); 
            ID163_tree = (Object)adaptor.create(ID163);
            adaptor.addChild(root_0, ID163_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:258:12: ( ',' ID )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==79) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:258:13: ',' ID
            	    {
            	    char_literal164=(Token)match(input,79,FOLLOW_79_in_idents1775); 
            	    char_literal164_tree = (Object)adaptor.create(char_literal164);
            	    adaptor.addChild(root_0, char_literal164_tree);

            	    ID165=(Token)match(input,ID,FOLLOW_ID_in_idents1777); 
            	    ID165_tree = (Object)adaptor.create(ID165);
            	    adaptor.addChild(root_0, ID165_tree);


            	    }
            	    break;

            	default :
            	    break loop63;
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:260:1: priorityInequality : qualifiedIdent ( '>' qualifiedIdent )+ ';' -> ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ ) ;
    public final RVCCalParser.priorityInequality_return priorityInequality() throws RecognitionException {
        RVCCalParser.priorityInequality_return retval = new RVCCalParser.priorityInequality_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal167=null;
        Token char_literal169=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent166 = null;

        RVCCalParser.qualifiedIdent_return qualifiedIdent168 = null;


        Object char_literal167_tree=null;
        Object char_literal169_tree=null;
        RewriteRuleTokenStream stream_GT=new RewriteRuleTokenStream(adaptor,"token GT");
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleSubtreeStream stream_qualifiedIdent=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedIdent");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:263:19: ( qualifiedIdent ( '>' qualifiedIdent )+ ';' -> ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:263:21: qualifiedIdent ( '>' qualifiedIdent )+ ';'
            {
            pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality1793);
            qualifiedIdent166=qualifiedIdent();

            state._fsp--;

            stream_qualifiedIdent.add(qualifiedIdent166.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:263:36: ( '>' qualifiedIdent )+
            int cnt64=0;
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==GT) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:263:37: '>' qualifiedIdent
            	    {
            	    char_literal167=(Token)match(input,GT,FOLLOW_GT_in_priorityInequality1796);  
            	    stream_GT.add(char_literal167);

            	    pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality1798);
            	    qualifiedIdent168=qualifiedIdent();

            	    state._fsp--;

            	    stream_qualifiedIdent.add(qualifiedIdent168.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt64 >= 1 ) break loop64;
                        EarlyExitException eee =
                            new EarlyExitException(64, input);
                        throw eee;
                }
                cnt64++;
            } while (true);

            char_literal169=(Token)match(input,90,FOLLOW_90_in_priorityInequality1802);  
            stream_90.add(char_literal169);



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
            // 263:62: -> ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:263:65: ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:1: priorityOrder : PRIORITY ( priorityInequality )* 'end' -> ^( PRIORITY ( priorityInequality )* ) ;
    public final RVCCalParser.priorityOrder_return priorityOrder() throws RecognitionException {
        RVCCalParser.priorityOrder_return retval = new RVCCalParser.priorityOrder_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRIORITY170=null;
        Token string_literal172=null;
        RVCCalParser.priorityInequality_return priorityInequality171 = null;


        Object PRIORITY170_tree=null;
        Object string_literal172_tree=null;
        RewriteRuleTokenStream stream_PRIORITY=new RewriteRuleTokenStream(adaptor,"token PRIORITY");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleSubtreeStream stream_priorityInequality=new RewriteRuleSubtreeStream(adaptor,"rule priorityInequality");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:14: ( PRIORITY ( priorityInequality )* 'end' -> ^( PRIORITY ( priorityInequality )* ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:16: PRIORITY ( priorityInequality )* 'end'
            {
            PRIORITY170=(Token)match(input,PRIORITY,FOLLOW_PRIORITY_in_priorityOrder1821);  
            stream_PRIORITY.add(PRIORITY170);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:25: ( priorityInequality )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==ID) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:25: priorityInequality
            	    {
            	    pushFollow(FOLLOW_priorityInequality_in_priorityOrder1823);
            	    priorityInequality171=priorityInequality();

            	    state._fsp--;

            	    stream_priorityInequality.add(priorityInequality171.getTree());

            	    }
            	    break;

            	default :
            	    break loop65;
                }
            } while (true);

            string_literal172=(Token)match(input,86,FOLLOW_86_in_priorityOrder1826);  
            stream_86.add(string_literal172);



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
            // 265:51: -> ^( PRIORITY ( priorityInequality )* )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:54: ^( PRIORITY ( priorityInequality )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_PRIORITY.nextNode(), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:65: ( priorityInequality )*
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:267:1: qualifiedIdent : ID ( '.' ID )* -> ^( QID ( ID )+ ) ;
    public final RVCCalParser.qualifiedIdent_return qualifiedIdent() throws RecognitionException {
        RVCCalParser.qualifiedIdent_return retval = new RVCCalParser.qualifiedIdent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID173=null;
        Token char_literal174=null;
        Token ID175=null;

        Object ID173_tree=null;
        Object char_literal174_tree=null;
        Object ID175_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:270:15: ( ID ( '.' ID )* -> ^( QID ( ID )+ ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:270:17: ID ( '.' ID )*
            {
            ID173=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent1847);  
            stream_ID.add(ID173);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:270:20: ( '.' ID )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( (LA66_0==87) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:270:21: '.' ID
            	    {
            	    char_literal174=(Token)match(input,87,FOLLOW_87_in_qualifiedIdent1850);  
            	    stream_87.add(char_literal174);

            	    ID175=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent1852);  
            	    stream_ID.add(ID175);


            	    }
            	    break;

            	default :
            	    break loop66;
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
            // 270:30: -> ^( QID ( ID )+ )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:270:33: ^( QID ( ID )+ )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:272:1: schedule : SCHEDULE 'fsm' ID ':' ( stateTransition )* 'end' -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) ) ;
    public final RVCCalParser.schedule_return schedule() throws RecognitionException {
        RVCCalParser.schedule_return retval = new RVCCalParser.schedule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SCHEDULE176=null;
        Token string_literal177=null;
        Token ID178=null;
        Token char_literal179=null;
        Token string_literal181=null;
        RVCCalParser.stateTransition_return stateTransition180 = null;


        Object SCHEDULE176_tree=null;
        Object string_literal177_tree=null;
        Object ID178_tree=null;
        Object char_literal179_tree=null;
        Object string_literal181_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_SCHEDULE=new RewriteRuleTokenStream(adaptor,"token SCHEDULE");
        RewriteRuleTokenStream stream_102=new RewriteRuleTokenStream(adaptor,"token 102");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleSubtreeStream stream_stateTransition=new RewriteRuleSubtreeStream(adaptor,"rule stateTransition");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:275:9: ( SCHEDULE 'fsm' ID ':' ( stateTransition )* 'end' -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:3: SCHEDULE 'fsm' ID ':' ( stateTransition )* 'end'
            {
            SCHEDULE176=(Token)match(input,SCHEDULE,FOLLOW_SCHEDULE_in_schedule1877);  
            stream_SCHEDULE.add(SCHEDULE176);

            string_literal177=(Token)match(input,102,FOLLOW_102_in_schedule1879);  
            stream_102.add(string_literal177);

            ID178=(Token)match(input,ID,FOLLOW_ID_in_schedule1881);  
            stream_ID.add(ID178);

            char_literal179=(Token)match(input,76,FOLLOW_76_in_schedule1883);  
            stream_76.add(char_literal179);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:25: ( stateTransition )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==ID) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:25: stateTransition
            	    {
            	    pushFollow(FOLLOW_stateTransition_in_schedule1885);
            	    stateTransition180=stateTransition();

            	    state._fsp--;

            	    stream_stateTransition.add(stateTransition180.getTree());

            	    }
            	    break;

            	default :
            	    break loop67;
                }
            } while (true);

            string_literal181=(Token)match(input,86,FOLLOW_86_in_schedule1888);  
            stream_86.add(string_literal181);



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
            // 276:48: -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:51: ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_SCHEDULE.nextNode(), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:65: ^( TRANSITIONS ( stateTransition )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TRANSITIONS, "TRANSITIONS"), root_2);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:79: ( stateTransition )*
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:278:1: stateTransition : ID '(' qualifiedIdent ')' '-->' ID ';' -> ^( TRANSITION ID qualifiedIdent ID ) ;
    public final RVCCalParser.stateTransition_return stateTransition() throws RecognitionException {
        RVCCalParser.stateTransition_return retval = new RVCCalParser.stateTransition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID182=null;
        Token char_literal183=null;
        Token char_literal185=null;
        Token string_literal186=null;
        Token ID187=null;
        Token char_literal188=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent184 = null;


        Object ID182_tree=null;
        Object char_literal183_tree=null;
        Object char_literal185_tree=null;
        Object string_literal186_tree=null;
        Object ID187_tree=null;
        Object char_literal188_tree=null;
        RewriteRuleTokenStream stream_91=new RewriteRuleTokenStream(adaptor,"token 91");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_qualifiedIdent=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedIdent");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:278:16: ( ID '(' qualifiedIdent ')' '-->' ID ';' -> ^( TRANSITION ID qualifiedIdent ID ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:279:2: ID '(' qualifiedIdent ')' '-->' ID ';'
            {
            ID182=(Token)match(input,ID,FOLLOW_ID_in_stateTransition1911);  
            stream_ID.add(ID182);

            char_literal183=(Token)match(input,83,FOLLOW_83_in_stateTransition1913);  
            stream_83.add(char_literal183);

            pushFollow(FOLLOW_qualifiedIdent_in_stateTransition1915);
            qualifiedIdent184=qualifiedIdent();

            state._fsp--;

            stream_qualifiedIdent.add(qualifiedIdent184.getTree());
            char_literal185=(Token)match(input,84,FOLLOW_84_in_stateTransition1917);  
            stream_84.add(char_literal185);

            string_literal186=(Token)match(input,91,FOLLOW_91_in_stateTransition1919);  
            stream_91.add(string_literal186);

            ID187=(Token)match(input,ID,FOLLOW_ID_in_stateTransition1921);  
            stream_ID.add(ID187);

            char_literal188=(Token)match(input,90,FOLLOW_90_in_stateTransition1923);  
            stream_90.add(char_literal188);



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
            // 279:41: -> ^( TRANSITION ID qualifiedIdent ID )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:279:44: ^( TRANSITION ID qualifiedIdent ID )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:281:1: statement : ( 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end' | 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end' | 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end' | 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end' | ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' ) );
    public final RVCCalParser.statement_return statement() throws RecognitionException {
        RVCCalParser.statement_return retval = new RVCCalParser.statement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal189=null;
        Token string_literal190=null;
        Token string_literal192=null;
        Token string_literal194=null;
        Token string_literal195=null;
        Token string_literal197=null;
        Token string_literal199=null;
        Token string_literal201=null;
        Token string_literal203=null;
        Token string_literal205=null;
        Token string_literal206=null;
        Token string_literal208=null;
        Token string_literal210=null;
        Token string_literal212=null;
        Token string_literal213=null;
        Token string_literal215=null;
        Token string_literal217=null;
        Token string_literal219=null;
        Token ID220=null;
        Token char_literal221=null;
        Token char_literal223=null;
        Token string_literal224=null;
        Token char_literal226=null;
        Token char_literal227=null;
        Token char_literal229=null;
        Token char_literal230=null;
        RVCCalParser.varDecls_return varDecls191 = null;

        RVCCalParser.statement_return statement193 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr196 = null;

        RVCCalParser.expression_return expression198 = null;

        RVCCalParser.expression_return expression200 = null;

        RVCCalParser.varDecls_return varDecls202 = null;

        RVCCalParser.statement_return statement204 = null;

        RVCCalParser.expression_return expression207 = null;

        RVCCalParser.statement_return statement209 = null;

        RVCCalParser.statement_return statement211 = null;

        RVCCalParser.expression_return expression214 = null;

        RVCCalParser.varDecls_return varDecls216 = null;

        RVCCalParser.statement_return statement218 = null;

        RVCCalParser.expressions_return expressions222 = null;

        RVCCalParser.expression_return expression225 = null;

        RVCCalParser.expressions_return expressions228 = null;


        Object string_literal189_tree=null;
        Object string_literal190_tree=null;
        Object string_literal192_tree=null;
        Object string_literal194_tree=null;
        Object string_literal195_tree=null;
        Object string_literal197_tree=null;
        Object string_literal199_tree=null;
        Object string_literal201_tree=null;
        Object string_literal203_tree=null;
        Object string_literal205_tree=null;
        Object string_literal206_tree=null;
        Object string_literal208_tree=null;
        Object string_literal210_tree=null;
        Object string_literal212_tree=null;
        Object string_literal213_tree=null;
        Object string_literal215_tree=null;
        Object string_literal217_tree=null;
        Object string_literal219_tree=null;
        Object ID220_tree=null;
        Object char_literal221_tree=null;
        Object char_literal223_tree=null;
        Object string_literal224_tree=null;
        Object char_literal226_tree=null;
        Object char_literal227_tree=null;
        Object char_literal229_tree=null;
        Object char_literal230_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:284:10: ( 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end' | 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end' | 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end' | 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end' | ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' ) )
            int alt81=5;
            switch ( input.LA(1) ) {
            case 92:
                {
                alt81=1;
                }
                break;
            case 103:
                {
                alt81=2;
                }
                break;
            case 95:
                {
                alt81=3;
                }
                break;
            case 105:
                {
                alt81=4;
                }
                break;
            case ID:
                {
                alt81=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 81, 0, input);

                throw nvae;
            }

            switch (alt81) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:285:3: 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal189=(Token)match(input,92,FOLLOW_92_in_statement1949); 
                    string_literal189_tree = (Object)adaptor.create(string_literal189);
                    adaptor.addChild(root_0, string_literal189_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:285:11: ( 'var' varDecls 'do' )?
                    int alt68=2;
                    int LA68_0 = input.LA(1);

                    if ( (LA68_0==88) ) {
                        alt68=1;
                    }
                    switch (alt68) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:285:12: 'var' varDecls 'do'
                            {
                            string_literal190=(Token)match(input,88,FOLLOW_88_in_statement1952); 
                            string_literal190_tree = (Object)adaptor.create(string_literal190);
                            adaptor.addChild(root_0, string_literal190_tree);

                            pushFollow(FOLLOW_varDecls_in_statement1954);
                            varDecls191=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls191.getTree());
                            string_literal192=(Token)match(input,81,FOLLOW_81_in_statement1956); 
                            string_literal192_tree = (Object)adaptor.create(string_literal192);
                            adaptor.addChild(root_0, string_literal192_tree);


                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:285:34: ( statement )*
                    loop69:
                    do {
                        int alt69=2;
                        int LA69_0 = input.LA(1);

                        if ( (LA69_0==ID||LA69_0==92||LA69_0==95||LA69_0==103||LA69_0==105) ) {
                            alt69=1;
                        }


                        switch (alt69) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:285:34: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement1960);
                    	    statement193=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement193.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop69;
                        }
                    } while (true);

                    string_literal194=(Token)match(input,86,FOLLOW_86_in_statement1963); 
                    string_literal194_tree = (Object)adaptor.create(string_literal194);
                    adaptor.addChild(root_0, string_literal194_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:3: 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal195=(Token)match(input,103,FOLLOW_103_in_statement1969); 
                    string_literal195_tree = (Object)adaptor.create(string_literal195);
                    adaptor.addChild(root_0, string_literal195_tree);

                    pushFollow(FOLLOW_varDeclNoExpr_in_statement1971);
                    varDeclNoExpr196=varDeclNoExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, varDeclNoExpr196.getTree());
                    string_literal197=(Token)match(input,101,FOLLOW_101_in_statement1973); 
                    string_literal197_tree = (Object)adaptor.create(string_literal197);
                    adaptor.addChild(root_0, string_literal197_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:32: ( expression ( '..' expression )? )
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:33: expression ( '..' expression )?
                    {
                    pushFollow(FOLLOW_expression_in_statement1976);
                    expression198=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression198.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:44: ( '..' expression )?
                    int alt70=2;
                    int LA70_0 = input.LA(1);

                    if ( (LA70_0==104) ) {
                        alt70=1;
                    }
                    switch (alt70) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:45: '..' expression
                            {
                            string_literal199=(Token)match(input,104,FOLLOW_104_in_statement1979); 
                            string_literal199_tree = (Object)adaptor.create(string_literal199);
                            adaptor.addChild(root_0, string_literal199_tree);

                            pushFollow(FOLLOW_expression_in_statement1981);
                            expression200=expression();

                            state._fsp--;

                            adaptor.addChild(root_0, expression200.getTree());

                            }
                            break;

                    }


                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:64: ( 'var' varDecls )?
                    int alt71=2;
                    int LA71_0 = input.LA(1);

                    if ( (LA71_0==88) ) {
                        alt71=1;
                    }
                    switch (alt71) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:65: 'var' varDecls
                            {
                            string_literal201=(Token)match(input,88,FOLLOW_88_in_statement1987); 
                            string_literal201_tree = (Object)adaptor.create(string_literal201);
                            adaptor.addChild(root_0, string_literal201_tree);

                            pushFollow(FOLLOW_varDecls_in_statement1989);
                            varDecls202=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls202.getTree());

                            }
                            break;

                    }

                    string_literal203=(Token)match(input,81,FOLLOW_81_in_statement1993); 
                    string_literal203_tree = (Object)adaptor.create(string_literal203);
                    adaptor.addChild(root_0, string_literal203_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:87: ( statement )*
                    loop72:
                    do {
                        int alt72=2;
                        int LA72_0 = input.LA(1);

                        if ( (LA72_0==ID||LA72_0==92||LA72_0==95||LA72_0==103||LA72_0==105) ) {
                            alt72=1;
                        }


                        switch (alt72) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:87: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement1995);
                    	    statement204=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement204.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop72;
                        }
                    } while (true);

                    string_literal205=(Token)match(input,86,FOLLOW_86_in_statement1998); 
                    string_literal205_tree = (Object)adaptor.create(string_literal205);
                    adaptor.addChild(root_0, string_literal205_tree);

                     

                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:287:3: 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal206=(Token)match(input,95,FOLLOW_95_in_statement2004); 
                    string_literal206_tree = (Object)adaptor.create(string_literal206);
                    adaptor.addChild(root_0, string_literal206_tree);

                    pushFollow(FOLLOW_expression_in_statement2006);
                    expression207=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression207.getTree());
                    string_literal208=(Token)match(input,96,FOLLOW_96_in_statement2008); 
                    string_literal208_tree = (Object)adaptor.create(string_literal208);
                    adaptor.addChild(root_0, string_literal208_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:287:26: ( statement )*
                    loop73:
                    do {
                        int alt73=2;
                        int LA73_0 = input.LA(1);

                        if ( (LA73_0==ID||LA73_0==92||LA73_0==95||LA73_0==103||LA73_0==105) ) {
                            alt73=1;
                        }


                        switch (alt73) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:287:26: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement2010);
                    	    statement209=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement209.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop73;
                        }
                    } while (true);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:287:37: ( 'else' ( statement )* )?
                    int alt75=2;
                    int LA75_0 = input.LA(1);

                    if ( (LA75_0==97) ) {
                        alt75=1;
                    }
                    switch (alt75) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:287:38: 'else' ( statement )*
                            {
                            string_literal210=(Token)match(input,97,FOLLOW_97_in_statement2014); 
                            string_literal210_tree = (Object)adaptor.create(string_literal210);
                            adaptor.addChild(root_0, string_literal210_tree);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:287:45: ( statement )*
                            loop74:
                            do {
                                int alt74=2;
                                int LA74_0 = input.LA(1);

                                if ( (LA74_0==ID||LA74_0==92||LA74_0==95||LA74_0==103||LA74_0==105) ) {
                                    alt74=1;
                                }


                                switch (alt74) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:287:45: statement
                            	    {
                            	    pushFollow(FOLLOW_statement_in_statement2016);
                            	    statement211=statement();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, statement211.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop74;
                                }
                            } while (true);


                            }
                            break;

                    }

                    string_literal212=(Token)match(input,86,FOLLOW_86_in_statement2021); 
                    string_literal212_tree = (Object)adaptor.create(string_literal212);
                    adaptor.addChild(root_0, string_literal212_tree);

                      

                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:3: 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal213=(Token)match(input,105,FOLLOW_105_in_statement2027); 
                    string_literal213_tree = (Object)adaptor.create(string_literal213);
                    adaptor.addChild(root_0, string_literal213_tree);

                    pushFollow(FOLLOW_expression_in_statement2029);
                    expression214=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression214.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:22: ( 'var' varDecls )?
                    int alt76=2;
                    int LA76_0 = input.LA(1);

                    if ( (LA76_0==88) ) {
                        alt76=1;
                    }
                    switch (alt76) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:23: 'var' varDecls
                            {
                            string_literal215=(Token)match(input,88,FOLLOW_88_in_statement2032); 
                            string_literal215_tree = (Object)adaptor.create(string_literal215);
                            adaptor.addChild(root_0, string_literal215_tree);

                            pushFollow(FOLLOW_varDecls_in_statement2034);
                            varDecls216=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls216.getTree());

                            }
                            break;

                    }

                    string_literal217=(Token)match(input,81,FOLLOW_81_in_statement2038); 
                    string_literal217_tree = (Object)adaptor.create(string_literal217);
                    adaptor.addChild(root_0, string_literal217_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:45: ( statement )*
                    loop77:
                    do {
                        int alt77=2;
                        int LA77_0 = input.LA(1);

                        if ( (LA77_0==ID||LA77_0==92||LA77_0==95||LA77_0==103||LA77_0==105) ) {
                            alt77=1;
                        }


                        switch (alt77) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:45: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement2040);
                    	    statement218=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement218.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop77;
                        }
                    } while (true);

                    string_literal219=(Token)match(input,86,FOLLOW_86_in_statement2043); 
                    string_literal219_tree = (Object)adaptor.create(string_literal219);
                    adaptor.addChild(root_0, string_literal219_tree);

                      

                    }
                    break;
                case 5 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:290:3: ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' )
                    {
                    root_0 = (Object)adaptor.nil();

                    ID220=(Token)match(input,ID,FOLLOW_ID_in_statement2050); 
                    ID220_tree = (Object)adaptor.create(ID220);
                    adaptor.addChild(root_0, ID220_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:290:6: ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' )
                    int alt80=2;
                    int LA80_0 = input.LA(1);

                    if ( (LA80_0==77||LA80_0==89) ) {
                        alt80=1;
                    }
                    else if ( (LA80_0==83) ) {
                        alt80=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 80, 0, input);

                        throw nvae;
                    }
                    switch (alt80) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:291:5: ( ( '[' expressions ']' )? ':=' expression ';' )
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:291:5: ( ( '[' expressions ']' )? ':=' expression ';' )
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:291:6: ( '[' expressions ']' )? ':=' expression ';'
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:291:6: ( '[' expressions ']' )?
                            int alt78=2;
                            int LA78_0 = input.LA(1);

                            if ( (LA78_0==77) ) {
                                alt78=1;
                            }
                            switch (alt78) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:291:7: '[' expressions ']'
                                    {
                                    char_literal221=(Token)match(input,77,FOLLOW_77_in_statement2060); 
                                    char_literal221_tree = (Object)adaptor.create(char_literal221);
                                    adaptor.addChild(root_0, char_literal221_tree);

                                    pushFollow(FOLLOW_expressions_in_statement2062);
                                    expressions222=expressions();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expressions222.getTree());
                                    char_literal223=(Token)match(input,78,FOLLOW_78_in_statement2064); 
                                    char_literal223_tree = (Object)adaptor.create(char_literal223);
                                    adaptor.addChild(root_0, char_literal223_tree);


                                    }
                                    break;

                            }

                            string_literal224=(Token)match(input,89,FOLLOW_89_in_statement2068); 
                            string_literal224_tree = (Object)adaptor.create(string_literal224);
                            adaptor.addChild(root_0, string_literal224_tree);

                            pushFollow(FOLLOW_expression_in_statement2070);
                            expression225=expression();

                            state._fsp--;

                            adaptor.addChild(root_0, expression225.getTree());
                            char_literal226=(Token)match(input,90,FOLLOW_90_in_statement2072); 
                            char_literal226_tree = (Object)adaptor.create(char_literal226);
                            adaptor.addChild(root_0, char_literal226_tree);


                            }

                             

                            }
                            break;
                        case 2 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:292:6: '(' ( expressions )? ')' ';'
                            {
                            char_literal227=(Token)match(input,83,FOLLOW_83_in_statement2082); 
                            char_literal227_tree = (Object)adaptor.create(char_literal227);
                            adaptor.addChild(root_0, char_literal227_tree);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:292:10: ( expressions )?
                            int alt79=2;
                            int LA79_0 = input.LA(1);

                            if ( (LA79_0==ID||LA79_0==MINUS||(LA79_0>=NOT && LA79_0<=STRING)||LA79_0==77||LA79_0==83||LA79_0==95||(LA79_0>=98 && LA79_0<=99)) ) {
                                alt79=1;
                            }
                            switch (alt79) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:292:10: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_statement2084);
                                    expressions228=expressions();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expressions228.getTree());

                                    }
                                    break;

                            }

                            char_literal229=(Token)match(input,84,FOLLOW_84_in_statement2087); 
                            char_literal229_tree = (Object)adaptor.create(char_literal229);
                            adaptor.addChild(root_0, char_literal229_tree);

                            char_literal230=(Token)match(input,90,FOLLOW_90_in_statement2089); 
                            char_literal230_tree = (Object)adaptor.create(char_literal230);
                            adaptor.addChild(root_0, char_literal230_tree);

                             

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:294:1: typeAttr : ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) ) ;
    public final RVCCalParser.typeAttr_return typeAttr() throws RecognitionException {
        RVCCalParser.typeAttr_return retval = new RVCCalParser.typeAttr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID231=null;
        Token char_literal232=null;
        Token char_literal234=null;
        RVCCalParser.typeDef_return typeDef233 = null;

        RVCCalParser.expression_return expression235 = null;


        Object ID231_tree=null;
        Object char_literal232_tree=null;
        Object char_literal234_tree=null;
        RewriteRuleTokenStream stream_EQ=new RewriteRuleTokenStream(adaptor,"token EQ");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:301:9: ( ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:301:11: ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) )
            {
            ID231=(Token)match(input,ID,FOLLOW_ID_in_typeAttr2110);  
            stream_ID.add(ID231);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:301:14: ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) )
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==76) ) {
                alt82=1;
            }
            else if ( (LA82_0==EQ) ) {
                alt82=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 82, 0, input);

                throw nvae;
            }
            switch (alt82) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:301:15: ':' typeDef
                    {
                    char_literal232=(Token)match(input,76,FOLLOW_76_in_typeAttr2113);  
                    stream_76.add(char_literal232);

                    pushFollow(FOLLOW_typeDef_in_typeAttr2115);
                    typeDef233=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef233.getTree());


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
                    // 301:27: -> ^( TYPE ID typeDef )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:301:30: ^( TYPE ID typeDef )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:301:51: '=' expression
                    {
                    char_literal234=(Token)match(input,EQ,FOLLOW_EQ_in_typeAttr2129);  
                    stream_EQ.add(char_literal234);

                    pushFollow(FOLLOW_expression_in_typeAttr2131);
                    expression235=expression();

                    state._fsp--;

                    stream_expression.add(expression235.getTree());


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
                    // 301:66: -> ^( EXPR ID expression )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:301:69: ^( EXPR ID expression )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:303:1: typeAttrs : typeAttr ( ',' typeAttr )* -> ( typeAttr )+ ;
    public final RVCCalParser.typeAttrs_return typeAttrs() throws RecognitionException {
        RVCCalParser.typeAttrs_return retval = new RVCCalParser.typeAttrs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal237=null;
        RVCCalParser.typeAttr_return typeAttr236 = null;

        RVCCalParser.typeAttr_return typeAttr238 = null;


        Object char_literal237_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleSubtreeStream stream_typeAttr=new RewriteRuleSubtreeStream(adaptor,"rule typeAttr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:303:10: ( typeAttr ( ',' typeAttr )* -> ( typeAttr )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:303:12: typeAttr ( ',' typeAttr )*
            {
            pushFollow(FOLLOW_typeAttr_in_typeAttrs2150);
            typeAttr236=typeAttr();

            state._fsp--;

            stream_typeAttr.add(typeAttr236.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:303:21: ( ',' typeAttr )*
            loop83:
            do {
                int alt83=2;
                int LA83_0 = input.LA(1);

                if ( (LA83_0==79) ) {
                    alt83=1;
                }


                switch (alt83) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:303:22: ',' typeAttr
            	    {
            	    char_literal237=(Token)match(input,79,FOLLOW_79_in_typeAttrs2153);  
            	    stream_79.add(char_literal237);

            	    pushFollow(FOLLOW_typeAttr_in_typeAttrs2155);
            	    typeAttr238=typeAttr();

            	    state._fsp--;

            	    stream_typeAttr.add(typeAttr238.getTree());

            	    }
            	    break;

            	default :
            	    break loop83;
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
            // 303:37: -> ( typeAttr )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:306:1: typeDef : ID ( '(' attrs= typeAttrs ')' )? -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) ;
    public final RVCCalParser.typeDef_return typeDef() throws RecognitionException {
        RVCCalParser.typeDef_return retval = new RVCCalParser.typeDef_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID239=null;
        Token char_literal240=null;
        Token char_literal241=null;
        RVCCalParser.typeAttrs_return attrs = null;


        Object ID239_tree=null;
        Object char_literal240_tree=null;
        Object char_literal241_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_typeAttrs=new RewriteRuleSubtreeStream(adaptor,"rule typeAttrs");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:306:8: ( ID ( '(' attrs= typeAttrs ')' )? -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:306:10: ID ( '(' attrs= typeAttrs ')' )?
            {
            ID239=(Token)match(input,ID,FOLLOW_ID_in_typeDef2172);  
            stream_ID.add(ID239);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:306:13: ( '(' attrs= typeAttrs ')' )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==83) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:306:14: '(' attrs= typeAttrs ')'
                    {
                    char_literal240=(Token)match(input,83,FOLLOW_83_in_typeDef2175);  
                    stream_83.add(char_literal240);

                    pushFollow(FOLLOW_typeAttrs_in_typeDef2179);
                    attrs=typeAttrs();

                    state._fsp--;

                    stream_typeAttrs.add(attrs.getTree());
                    char_literal241=(Token)match(input,84,FOLLOW_84_in_typeDef2181);  
                    stream_84.add(char_literal241);


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
            // 306:40: -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:306:43: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:306:53: ^( TYPE_ATTRS ( $attrs)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_2);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:306:66: ( $attrs)?
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:308:1: varDecl : typeDef ID ( '=' expression | ':=' expression )? ;
    public final RVCCalParser.varDecl_return varDecl() throws RecognitionException {
        RVCCalParser.varDecl_return retval = new RVCCalParser.varDecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID243=null;
        Token char_literal244=null;
        Token string_literal246=null;
        RVCCalParser.typeDef_return typeDef242 = null;

        RVCCalParser.expression_return expression245 = null;

        RVCCalParser.expression_return expression247 = null;


        Object ID243_tree=null;
        Object char_literal244_tree=null;
        Object string_literal246_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:312:8: ( typeDef ID ( '=' expression | ':=' expression )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:312:10: typeDef ID ( '=' expression | ':=' expression )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_typeDef_in_varDecl2213);
            typeDef242=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef242.getTree());
            ID243=(Token)match(input,ID,FOLLOW_ID_in_varDecl2215); 
            ID243_tree = (Object)adaptor.create(ID243);
            adaptor.addChild(root_0, ID243_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:312:21: ( '=' expression | ':=' expression )?
            int alt85=3;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==EQ) ) {
                alt85=1;
            }
            else if ( (LA85_0==89) ) {
                alt85=2;
            }
            switch (alt85) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:312:22: '=' expression
                    {
                    char_literal244=(Token)match(input,EQ,FOLLOW_EQ_in_varDecl2218); 
                    char_literal244_tree = (Object)adaptor.create(char_literal244);
                    adaptor.addChild(root_0, char_literal244_tree);

                    pushFollow(FOLLOW_expression_in_varDecl2220);
                    expression245=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression245.getTree());

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:312:39: ':=' expression
                    {
                    string_literal246=(Token)match(input,89,FOLLOW_89_in_varDecl2224); 
                    string_literal246_tree = (Object)adaptor.create(string_literal246);
                    adaptor.addChild(root_0, string_literal246_tree);

                    pushFollow(FOLLOW_expression_in_varDecl2226);
                    expression247=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression247.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:314:1: varDeclNoExpr : typeDef ID -> ^( VAR typeDef ID ) ;
    public final RVCCalParser.varDeclNoExpr_return varDeclNoExpr() throws RecognitionException {
        RVCCalParser.varDeclNoExpr_return retval = new RVCCalParser.varDeclNoExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID249=null;
        RVCCalParser.typeDef_return typeDef248 = null;


        Object ID249_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:314:14: ( typeDef ID -> ^( VAR typeDef ID ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:314:16: typeDef ID
            {
            pushFollow(FOLLOW_typeDef_in_varDeclNoExpr2237);
            typeDef248=typeDef();

            state._fsp--;

            stream_typeDef.add(typeDef248.getTree());
            ID249=(Token)match(input,ID,FOLLOW_ID_in_varDeclNoExpr2239);  
            stream_ID.add(ID249);



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
            // 314:27: -> ^( VAR typeDef ID )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:314:30: ^( VAR typeDef ID )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:316:1: varDecls : varDecl ( ',' varDecl )* -> ( varDecl )+ ;
    public final RVCCalParser.varDecls_return varDecls() throws RecognitionException {
        RVCCalParser.varDecls_return retval = new RVCCalParser.varDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal251=null;
        RVCCalParser.varDecl_return varDecl250 = null;

        RVCCalParser.varDecl_return varDecl252 = null;


        Object char_literal251_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleSubtreeStream stream_varDecl=new RewriteRuleSubtreeStream(adaptor,"rule varDecl");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:316:9: ( varDecl ( ',' varDecl )* -> ( varDecl )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:316:11: varDecl ( ',' varDecl )*
            {
            pushFollow(FOLLOW_varDecl_in_varDecls2256);
            varDecl250=varDecl();

            state._fsp--;

            stream_varDecl.add(varDecl250.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:316:19: ( ',' varDecl )*
            loop86:
            do {
                int alt86=2;
                int LA86_0 = input.LA(1);

                if ( (LA86_0==79) ) {
                    alt86=1;
                }


                switch (alt86) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:316:20: ',' varDecl
            	    {
            	    char_literal251=(Token)match(input,79,FOLLOW_79_in_varDecls2259);  
            	    stream_79.add(char_literal251);

            	    pushFollow(FOLLOW_varDecl_in_varDecls2261);
            	    varDecl252=varDecl();

            	    state._fsp--;

            	    stream_varDecl.add(varDecl252.getTree());

            	    }
            	    break;

            	default :
            	    break loop86;
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
            // 316:34: -> ( varDecl )+
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
    public static final BitSet FOLLOW_86_in_actor477 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_actor479 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration544 = new BitSet(new long[]{0x0000002000000000L,0x0000000000881000L});
    public static final BitSet FOLLOW_87_in_actorDeclaration555 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration559 = new BitSet(new long[]{0x0000000000000000L,0x0000000000801000L});
    public static final BitSet FOLLOW_76_in_actorDeclaration564 = new BitSet(new long[]{0x000000C000000000L});
    public static final BitSet FOLLOW_ACTION_in_actorDeclaration573 = new BitSet(new long[]{0x0000002000000000L,0x0000000000202000L});
    public static final BitSet FOLLOW_actionInputs_in_actorDeclaration577 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actorDeclaration580 = new BitSet(new long[]{0x0000002000000000L,0x0000000001422800L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration584 = new BitSet(new long[]{0x0000000000000000L,0x0000000001420800L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration589 = new BitSet(new long[]{0x0000000000000000L,0x0000000001420000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration593 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration595 = new BitSet(new long[]{0x0000000000000000L,0x0000000000420000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration599 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration602 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INITIALIZE_in_actorDeclaration673 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actorDeclaration675 = new BitSet(new long[]{0x0000002000000000L,0x0000000001422800L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration677 = new BitSet(new long[]{0x0000000000000000L,0x0000000001420800L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration680 = new BitSet(new long[]{0x0000000000000000L,0x0000000001420000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration684 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration686 = new BitSet(new long[]{0x0000000000000000L,0x0000000000420000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration690 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration693 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_actorDeclaration772 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typeAttrs_in_actorDeclaration776 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_actorDeclaration778 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration788 = new BitSet(new long[]{0x0000400000000000L,0x0000000006000000L});
    public static final BitSet FOLLOW_EQ_in_actorDeclaration797 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration799 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_89_in_actorDeclaration835 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration837 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_actorDeclaration899 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ACTION_in_actorDeclaration909 = new BitSet(new long[]{0x0000002000000000L,0x0000000000202000L});
    public static final BitSet FOLLOW_actionInputs_in_actorDeclaration911 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actorDeclaration914 = new BitSet(new long[]{0x0000002000000000L,0x0000000001422800L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration916 = new BitSet(new long[]{0x0000000000000000L,0x0000000001420800L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration919 = new BitSet(new long[]{0x0000000000000000L,0x0000000001420000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration923 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration925 = new BitSet(new long[]{0x0000000000000000L,0x0000000000420000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration929 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INITIALIZE_in_actorDeclaration986 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_actorDeclaration988 = new BitSet(new long[]{0x0000002000000000L,0x0000000001422800L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration990 = new BitSet(new long[]{0x0000000000000000L,0x0000000001420800L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration993 = new BitSet(new long[]{0x0000000000000000L,0x0000000001420000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration997 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration999 = new BitSet(new long[]{0x0000000000000000L,0x0000000000420000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration1003 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration1006 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_priorityOrder_in_actorDeclaration1053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FUNCTION_in_actorDeclaration1062 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration1064 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_actorDeclaration1066 = new BitSet(new long[]{0x0000002000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration1069 = new BitSet(new long[]{0x0000000000000000L,0x0000000000108000L});
    public static final BitSet FOLLOW_79_in_actorDeclaration1072 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration1074 = new BitSet(new long[]{0x0000000000000000L,0x0000000000108000L});
    public static final BitSet FOLLOW_84_in_actorDeclaration1080 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_actorDeclaration1082 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typeDef_in_actorDeclaration1084 = new BitSet(new long[]{0x0000000000000000L,0x0000000001001000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration1091 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration1093 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_actorDeclaration1097 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration1105 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration1111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROCEDURE_in_actorDeclaration1121 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration1123 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_actorDeclaration1125 = new BitSet(new long[]{0x0000002000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration1128 = new BitSet(new long[]{0x0000000000000000L,0x0000000000108000L});
    public static final BitSet FOLLOW_79_in_actorDeclaration1131 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration1133 = new BitSet(new long[]{0x0000000000000000L,0x0000000000108000L});
    public static final BitSet FOLLOW_84_in_actorDeclaration1139 = new BitSet(new long[]{0x0000000000000000L,0x0000000011000000L});
    public static final BitSet FOLLOW_88_in_actorDeclaration1146 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration1148 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_actorDeclaration1156 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_statement_in_actorDeclaration1158 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_86_in_actorDeclaration1161 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations1173 = new BitSet(new long[]{0x000003E000000002L,0x000000000000000CL});
    public static final BitSet FOLLOW_schedule_in_actorDeclarations1177 = new BitSet(new long[]{0x000003E000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations1179 = new BitSet(new long[]{0x000003E000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_93_in_actorImport1202 = new BitSet(new long[]{0x0000002000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_actorImport1207 = new BitSet(new long[]{0x0000002000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport1209 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_actorImport1211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport1217 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_actorImport1219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_actorParameter1234 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_actorParameter1236 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_EQ_in_actorParameter1239 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_actorParameter1241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters1263 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_actorParameters1266 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters1268 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorPortDecls1287 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_actorPortDecls1290 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorPortDecls1292 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_un_expr_in_expression1313 = new BitSet(new long[]{0x1FFFFC0000000002L});
    public static final BitSet FOLLOW_bop_in_expression1317 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_un_expr_in_expression1319 = new BitSet(new long[]{0x1FFFFC0000000002L});
    public static final BitSet FOLLOW_set_in_bop0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfix_expression_in_un_expr1435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_un_expr1447 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_NOT_in_un_expr1453 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_NUM_ELTS_in_un_expr1459 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_un_expr_in_un_expr1462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_postfix_expression1482 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1486 = new BitSet(new long[]{0x0000000000000000L,0x0000000000005000L});
    public static final BitSet FOLLOW_76_in_postfix_expression1489 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_expressionGenerators_in_postfix_expression1493 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_postfix_expression1497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_95_in_postfix_expression1514 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1518 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_postfix_expression1520 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1524 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_97_in_postfix_expression1526 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1530 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_postfix_expression1532 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_postfix_expression1551 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_postfix_expression1559 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1561 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_postfix_expression1563 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_postfix_expression1573 = new BitSet(new long[]{0x0000000000000002L,0x0000000000082000L});
    public static final BitSet FOLLOW_83_in_postfix_expression1581 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80182003L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1583 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_postfix_expression1586 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_postfix_expression1606 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1608 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_postfix_expression1610 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_98_in_constant1647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_constant1659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_constant1671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_constant1683 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_constant1695 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_100_in_expressionGenerator1711 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typeDef_in_expressionGenerator1713 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_expressionGenerator1715 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_expressionGenerator1717 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_expressionGenerator1719 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators1729 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_expressionGenerators1732 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators1734 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_expression_in_expressions1748 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_expressions1751 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_expressions1753 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_idents1772 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_idents1775 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_idents1777 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality1793 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_GT_in_priorityInequality1796 = new BitSet(new long[]{0x0000002000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality1798 = new BitSet(new long[]{0x0002000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_priorityInequality1802 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRIORITY_in_priorityOrder1821 = new BitSet(new long[]{0x0000002000000000L,0x0000000040400000L});
    public static final BitSet FOLLOW_priorityInequality_in_priorityOrder1823 = new BitSet(new long[]{0x0000002000000000L,0x0000000040400000L});
    public static final BitSet FOLLOW_86_in_priorityOrder1826 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent1847 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_qualifiedIdent1850 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent1852 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_SCHEDULE_in_schedule1877 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_102_in_schedule1879 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_schedule1881 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_schedule1883 = new BitSet(new long[]{0x0000002000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_stateTransition_in_schedule1885 = new BitSet(new long[]{0x0000002000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_schedule1888 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stateTransition1911 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_stateTransition1913 = new BitSet(new long[]{0x0000002000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_stateTransition1915 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_stateTransition1917 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_stateTransition1919 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_stateTransition1921 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_stateTransition1923 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_statement1949 = new BitSet(new long[]{0x0000002000000000L,0x0000028091400000L});
    public static final BitSet FOLLOW_88_in_statement1952 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecls_in_statement1954 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_statement1956 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_statement_in_statement1960 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_86_in_statement1963 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_statement1969 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_statement1971 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_statement1973 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_statement1976 = new BitSet(new long[]{0x0000000000000000L,0x0000010001020000L});
    public static final BitSet FOLLOW_104_in_statement1979 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_statement1981 = new BitSet(new long[]{0x0000000000000000L,0x0000000001020000L});
    public static final BitSet FOLLOW_88_in_statement1987 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecls_in_statement1989 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_statement1993 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_statement_in_statement1995 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_86_in_statement1998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_95_in_statement2004 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_statement2006 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_statement2008 = new BitSet(new long[]{0x0000002000000000L,0x0000028290400000L});
    public static final BitSet FOLLOW_statement_in_statement2010 = new BitSet(new long[]{0x0000002000000000L,0x0000028290400000L});
    public static final BitSet FOLLOW_97_in_statement2014 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_statement_in_statement2016 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_86_in_statement2021 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_statement2027 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_statement2029 = new BitSet(new long[]{0x0000000000000000L,0x0000000001020000L});
    public static final BitSet FOLLOW_88_in_statement2032 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecls_in_statement2034 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_statement2038 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_statement_in_statement2040 = new BitSet(new long[]{0x0000002000000000L,0x0000028090400000L});
    public static final BitSet FOLLOW_86_in_statement2043 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_statement2050 = new BitSet(new long[]{0x0000000000000000L,0x0000000002082000L});
    public static final BitSet FOLLOW_77_in_statement2060 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expressions_in_statement2062 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_statement2064 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_statement2068 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_statement2070 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_statement2072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_statement2082 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80182003L});
    public static final BitSet FOLLOW_expressions_in_statement2084 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_statement2087 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_statement2089 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_typeAttr2110 = new BitSet(new long[]{0x0000400000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_typeAttr2113 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typeDef_in_typeAttr2115 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EQ_in_typeAttr2129 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_typeAttr2131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs2150 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_typeAttrs2153 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs2155 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_typeDef2172 = new BitSet(new long[]{0x0000000000000002L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_typeDef2175 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typeAttrs_in_typeDef2179 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_typeDef2181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_varDecl2213 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_varDecl2215 = new BitSet(new long[]{0x0000400000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_EQ_in_varDecl2218 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_varDecl2220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_varDecl2224 = new BitSet(new long[]{0xE080002000000000L,0x0000000C80082003L});
    public static final BitSet FOLLOW_expression_in_varDecl2226 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_varDeclNoExpr2237 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_ID_in_varDeclNoExpr2239 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varDecl_in_varDecls2256 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_varDecls2259 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_varDecl_in_varDecls2261 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});

}