// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g 2009-10-09 18:24:37

package net.sf.orcc.frontend.parser.internal;

// @SuppressWarnings("unused")


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

public class RVCCalParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACTOR", "INPUTS", "OUTPUTS", "PARAMETER", "PARAMETERS", "ACTOR_DECLS", "FUNCTION", "PROCEDURE", "STATE_VAR", "SCHEDULE", "TRANSITION", "TRANSITIONS", "INEQUALITY", "PRIORITY", "GUARDS", "TAG", "STATEMENTS", "VARS", "EXPR", "EXPR_OR", "EXPR_AND", "EXPR_BITOR", "EXPR_BITAND", "EXPR_EQ", "EXPR_REL", "EXPR_SHIFT", "EXPR_ADD", "EXPR_MUL", "EXPR_EXP", "EXPR_UN", "EXPR_LIST", "EXPR_IF", "EXPR_CALL", "EXPR_IDX", "EXPR_VAR", "EXPR_BOOL", "EXPR_FLOAT", "EXPR_INT", "EXPR_STRING", "VAR", "TYPE", "TYPE_ATTRS", "ASSIGNABLE", "NON_ASSIGNABLE", "QID", "ID", "FLOAT", "INTEGER", "STRING", "LETTER", "Exponent", "EscapeSequence", "OctalEscape", "LINE_COMMENT", "MULTI_LINE_COMMENT", "WHITESPACE", "'guard'", "':'", "'['", "']'", "','", "'repeat'", "'do'", "'actor'", "'('", "')'", "'==>'", "'end'", "'.'", "'action'", "'var'", "'initialize'", "'='", "':='", "';'", "'function'", "'-->'", "'procedure'", "'begin'", "'import'", "'all'", "'or'", "'||'", "'and'", "'&&'", "'|'", "'&'", "'!='", "'<'", "'>'", "'<='", "'>='", "'<<'", "'>>'", "'+'", "'-'", "'div'", "'mod'", "'*'", "'/'", "'^'", "'not'", "'#'", "'if'", "'then'", "'else'", "'true'", "'false'", "'for'", "'in'", "'priority'", "'schedule'", "'fsm'", "'foreach'", "'..'", "'while'"
    };
    public static final int FUNCTION=10;
    public static final int EXPR_BOOL=39;
    public static final int EXPR_VAR=38;
    public static final int OUTPUTS=6;
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
    public static final int WHITESPACE=59;
    public static final int NON_ASSIGNABLE=47;
    public static final int INEQUALITY=16;
    public static final int T__85=85;
    public static final int EXPR_IDX=37;
    public static final int T__84=84;
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
    public static final int MULTI_LINE_COMMENT=58;
    public static final int PROCEDURE=11;
    public static final int QID=48;
    public static final int TAG=19;
    public static final int EXPR_BITAND=26;
    public static final int OctalEscape=56;
    public static final int STRING=52;

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:122:1: actionGuards : 'guard' expressions -> expressions ;
    public final RVCCalParser.actionGuards_return actionGuards() throws RecognitionException {
        RVCCalParser.actionGuards_return retval = new RVCCalParser.actionGuards_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal1=null;
        RVCCalParser.expressions_return expressions2 = null;


        Object string_literal1_tree=null;
        RewriteRuleTokenStream stream_60=new RewriteRuleTokenStream(adaptor,"token 60");
        RewriteRuleSubtreeStream stream_expressions=new RewriteRuleSubtreeStream(adaptor,"rule expressions");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:122:13: ( 'guard' expressions -> expressions )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:122:15: 'guard' expressions
            {
            string_literal1=(Token)match(input,60,FOLLOW_60_in_actionGuards362);  
            stream_60.add(string_literal1);

            pushFollow(FOLLOW_expressions_in_actionGuards364);
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
            // 122:35: -> expressions
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:124:1: actionInput : ( ID ':' )? '[' idents ']' ( actionRepeat )? ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:124:12: ( ( ID ':' )? '[' idents ']' ( actionRepeat )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:2: ( ID ':' )? '[' idents ']' ( actionRepeat )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:2: ( ID ':' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==ID) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:3: ID ':'
                    {
                    ID3=(Token)match(input,ID,FOLLOW_ID_in_actionInput377); 
                    ID3_tree = (Object)adaptor.create(ID3);
                    adaptor.addChild(root_0, ID3_tree);

                    char_literal4=(Token)match(input,61,FOLLOW_61_in_actionInput379); 
                    char_literal4_tree = (Object)adaptor.create(char_literal4);
                    adaptor.addChild(root_0, char_literal4_tree);


                    }
                    break;

            }

            char_literal5=(Token)match(input,62,FOLLOW_62_in_actionInput383); 
            char_literal5_tree = (Object)adaptor.create(char_literal5);
            adaptor.addChild(root_0, char_literal5_tree);

            pushFollow(FOLLOW_idents_in_actionInput385);
            idents6=idents();

            state._fsp--;

            adaptor.addChild(root_0, idents6.getTree());
            char_literal7=(Token)match(input,63,FOLLOW_63_in_actionInput387); 
            char_literal7_tree = (Object)adaptor.create(char_literal7);
            adaptor.addChild(root_0, char_literal7_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:27: ( actionRepeat )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==65) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:27: actionRepeat
                    {
                    pushFollow(FOLLOW_actionRepeat_in_actionInput389);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:128:1: actionInputs : actionInput ( ',' actionInput )* -> ( actionInput )+ ;
    public final RVCCalParser.actionInputs_return actionInputs() throws RecognitionException {
        RVCCalParser.actionInputs_return retval = new RVCCalParser.actionInputs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal10=null;
        RVCCalParser.actionInput_return actionInput9 = null;

        RVCCalParser.actionInput_return actionInput11 = null;


        Object char_literal10_tree=null;
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleSubtreeStream stream_actionInput=new RewriteRuleSubtreeStream(adaptor,"rule actionInput");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:128:13: ( actionInput ( ',' actionInput )* -> ( actionInput )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:128:15: actionInput ( ',' actionInput )*
            {
            pushFollow(FOLLOW_actionInput_in_actionInputs400);
            actionInput9=actionInput();

            state._fsp--;

            stream_actionInput.add(actionInput9.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:128:27: ( ',' actionInput )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==64) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:128:28: ',' actionInput
            	    {
            	    char_literal10=(Token)match(input,64,FOLLOW_64_in_actionInputs403);  
            	    stream_64.add(char_literal10);

            	    pushFollow(FOLLOW_actionInput_in_actionInputs405);
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
            // 128:46: -> ( actionInput )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:1: actionOutput : ( ID ':' )? '[' expressions ']' ( actionRepeat )? ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:13: ( ( ID ':' )? '[' expressions ']' ( actionRepeat )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:2: ( ID ':' )? '[' expressions ']' ( actionRepeat )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:2: ( ID ':' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==ID) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:3: ID ':'
                    {
                    ID12=(Token)match(input,ID,FOLLOW_ID_in_actionOutput421); 
                    ID12_tree = (Object)adaptor.create(ID12);
                    adaptor.addChild(root_0, ID12_tree);

                    char_literal13=(Token)match(input,61,FOLLOW_61_in_actionOutput423); 
                    char_literal13_tree = (Object)adaptor.create(char_literal13);
                    adaptor.addChild(root_0, char_literal13_tree);


                    }
                    break;

            }

            char_literal14=(Token)match(input,62,FOLLOW_62_in_actionOutput427); 
            char_literal14_tree = (Object)adaptor.create(char_literal14);
            adaptor.addChild(root_0, char_literal14_tree);

            pushFollow(FOLLOW_expressions_in_actionOutput429);
            expressions15=expressions();

            state._fsp--;

            adaptor.addChild(root_0, expressions15.getTree());
            char_literal16=(Token)match(input,63,FOLLOW_63_in_actionOutput431); 
            char_literal16_tree = (Object)adaptor.create(char_literal16);
            adaptor.addChild(root_0, char_literal16_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:32: ( actionRepeat )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==65) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:32: actionRepeat
                    {
                    pushFollow(FOLLOW_actionRepeat_in_actionOutput433);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:1: actionOutputs : actionOutput ( ',' actionOutput )* -> ( actionOutput )+ ;
    public final RVCCalParser.actionOutputs_return actionOutputs() throws RecognitionException {
        RVCCalParser.actionOutputs_return retval = new RVCCalParser.actionOutputs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal19=null;
        RVCCalParser.actionOutput_return actionOutput18 = null;

        RVCCalParser.actionOutput_return actionOutput20 = null;


        Object char_literal19_tree=null;
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleSubtreeStream stream_actionOutput=new RewriteRuleSubtreeStream(adaptor,"rule actionOutput");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:14: ( actionOutput ( ',' actionOutput )* -> ( actionOutput )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:16: actionOutput ( ',' actionOutput )*
            {
            pushFollow(FOLLOW_actionOutput_in_actionOutputs444);
            actionOutput18=actionOutput();

            state._fsp--;

            stream_actionOutput.add(actionOutput18.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:29: ( ',' actionOutput )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==64) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:134:30: ',' actionOutput
            	    {
            	    char_literal19=(Token)match(input,64,FOLLOW_64_in_actionOutputs447);  
            	    stream_64.add(char_literal19);

            	    pushFollow(FOLLOW_actionOutput_in_actionOutputs449);
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
            // 134:49: -> ( actionOutput )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:1: actionRepeat : 'repeat' expression -> expression ;
    public final RVCCalParser.actionRepeat_return actionRepeat() throws RecognitionException {
        RVCCalParser.actionRepeat_return retval = new RVCCalParser.actionRepeat_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal21=null;
        RVCCalParser.expression_return expression22 = null;


        Object string_literal21_tree=null;
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:13: ( 'repeat' expression -> expression )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:15: 'repeat' expression
            {
            string_literal21=(Token)match(input,65,FOLLOW_65_in_actionRepeat463);  
            stream_65.add(string_literal21);

            pushFollow(FOLLOW_expression_in_actionRepeat465);
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
            // 136:35: -> expression
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:138:1: actionStatements : 'do' ( statement )* -> ( statement )* ;
    public final RVCCalParser.actionStatements_return actionStatements() throws RecognitionException {
        RVCCalParser.actionStatements_return retval = new RVCCalParser.actionStatements_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal23=null;
        RVCCalParser.statement_return statement24 = null;


        Object string_literal23_tree=null;
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:138:17: ( 'do' ( statement )* -> ( statement )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:138:19: 'do' ( statement )*
            {
            string_literal23=(Token)match(input,66,FOLLOW_66_in_actionStatements476);  
            stream_66.add(string_literal23);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:138:24: ( statement )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==ID||LA7_0==82||LA7_0==107||LA7_0==117||LA7_0==119) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:138:24: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_actionStatements478);
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
            // 138:35: -> ( statement )*
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:138:38: ( statement )*
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:140:1: actor : ( actorImport )* 'actor' id= ID ( '[' ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' actorDeclarations 'end' EOF -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS actorDeclarations ) ;
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
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_69=new RewriteRuleTokenStream(adaptor,"token 69");
        RewriteRuleTokenStream stream_68=new RewriteRuleTokenStream(adaptor,"token 68");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleTokenStream stream_70=new RewriteRuleTokenStream(adaptor,"token 70");
        RewriteRuleTokenStream stream_62=new RewriteRuleTokenStream(adaptor,"token 62");
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");
        RewriteRuleTokenStream stream_63=new RewriteRuleTokenStream(adaptor,"token 63");
        RewriteRuleTokenStream stream_61=new RewriteRuleTokenStream(adaptor,"token 61");
        RewriteRuleSubtreeStream stream_actorImport=new RewriteRuleSubtreeStream(adaptor,"rule actorImport");
        RewriteRuleSubtreeStream stream_actorDeclarations=new RewriteRuleSubtreeStream(adaptor,"rule actorDeclarations");
        RewriteRuleSubtreeStream stream_actorPortDecls=new RewriteRuleSubtreeStream(adaptor,"rule actorPortDecls");
        RewriteRuleSubtreeStream stream_actorParameters=new RewriteRuleSubtreeStream(adaptor,"rule actorParameters");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:143:6: ( ( actorImport )* 'actor' id= ID ( '[' ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' actorDeclarations 'end' EOF -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS actorDeclarations ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:143:8: ( actorImport )* 'actor' id= ID ( '[' ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' actorDeclarations 'end' EOF
            {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:143:8: ( actorImport )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==83) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:143:8: actorImport
            	    {
            	    pushFollow(FOLLOW_actorImport_in_actor496);
            	    actorImport25=actorImport();

            	    state._fsp--;

            	    stream_actorImport.add(actorImport25.getTree());

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            string_literal26=(Token)match(input,67,FOLLOW_67_in_actor499);  
            stream_67.add(string_literal26);

            id=(Token)match(input,ID,FOLLOW_ID_in_actor503);  
            stream_ID.add(id);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:143:35: ( '[' ']' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==62) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:143:36: '[' ']'
                    {
                    char_literal27=(Token)match(input,62,FOLLOW_62_in_actor506);  
                    stream_62.add(char_literal27);

                    char_literal28=(Token)match(input,63,FOLLOW_63_in_actor508);  
                    stream_63.add(char_literal28);


                    }
                    break;

            }

            char_literal29=(Token)match(input,68,FOLLOW_68_in_actor512);  
            stream_68.add(char_literal29);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:143:50: ( actorParameters )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==ID) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:143:50: actorParameters
                    {
                    pushFollow(FOLLOW_actorParameters_in_actor514);
                    actorParameters30=actorParameters();

                    state._fsp--;

                    stream_actorParameters.add(actorParameters30.getTree());

                    }
                    break;

            }

            char_literal31=(Token)match(input,69,FOLLOW_69_in_actor517);  
            stream_69.add(char_literal31);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:8: (inputs= actorPortDecls )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==ID) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:8: inputs= actorPortDecls
                    {
                    pushFollow(FOLLOW_actorPortDecls_in_actor522);
                    inputs=actorPortDecls();

                    state._fsp--;

                    stream_actorPortDecls.add(inputs.getTree());

                    }
                    break;

            }

            string_literal32=(Token)match(input,70,FOLLOW_70_in_actor525);  
            stream_70.add(string_literal32);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:38: (outputs= actorPortDecls )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==ID) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:38: outputs= actorPortDecls
                    {
                    pushFollow(FOLLOW_actorPortDecls_in_actor529);
                    outputs=actorPortDecls();

                    state._fsp--;

                    stream_actorPortDecls.add(outputs.getTree());

                    }
                    break;

            }

            char_literal33=(Token)match(input,61,FOLLOW_61_in_actor532);  
            stream_61.add(char_literal33);

            pushFollow(FOLLOW_actorDeclarations_in_actor535);
            actorDeclarations34=actorDeclarations();

            state._fsp--;

            stream_actorDeclarations.add(actorDeclarations34.getTree());
            string_literal35=(Token)match(input,71,FOLLOW_71_in_actor537);  
            stream_71.add(string_literal35);

            EOF36=(Token)match(input,EOF,FOLLOW_EOF_in_actor539);  
            stream_EOF.add(EOF36);



            // AST REWRITE
            // elements: actorDeclarations, inputs, actorParameters, 67, outputs, id
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
            // 146:2: -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS actorDeclarations )
            {
                adaptor.addChild(root_0, stream_67.nextNode());
                adaptor.addChild(root_0, stream_id.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:147:2: ^( PARAMETERS ( actorParameters )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETERS, "PARAMETERS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:147:15: ( actorParameters )?
                if ( stream_actorParameters.hasNext() ) {
                    adaptor.addChild(root_1, stream_actorParameters.nextTree());

                }
                stream_actorParameters.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:2: ^( INPUTS ( $inputs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:11: ( $inputs)?
                if ( stream_inputs.hasNext() ) {
                    adaptor.addChild(root_1, stream_inputs.nextTree());

                }
                stream_inputs.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:2: ^( OUTPUTS ( $outputs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:12: ( $outputs)?
                if ( stream_outputs.hasNext() ) {
                    adaptor.addChild(root_1, stream_outputs.nextTree());

                }
                stream_outputs.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:150:2: ^( ACTOR_DECLS actorDeclarations )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:1: actorDeclaration : ( ID ( ( ( ( '.' tag= ID )* -> ( $tag)* ) ':' ( 'action' (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'action' ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'initialize' ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' ) | 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'action' TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'initialize' TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | priorityOrder -> priorityOrder | 'function' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end' -> FUNCTION | 'procedure' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end' -> PROCEDURE );
    public final RVCCalParser.actorDeclaration_return actorDeclaration() throws RecognitionException {
        RVCCalParser.actorDeclaration_return retval = new RVCCalParser.actorDeclaration_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token tag=null;
        Token varName=null;
        Token ID37=null;
        Token char_literal38=null;
        Token char_literal39=null;
        Token string_literal40=null;
        Token string_literal41=null;
        Token string_literal42=null;
        Token string_literal45=null;
        Token string_literal46=null;
        Token string_literal47=null;
        Token string_literal50=null;
        Token string_literal53=null;
        Token char_literal54=null;
        Token char_literal55=null;
        Token char_literal56=null;
        Token string_literal58=null;
        Token char_literal60=null;
        Token string_literal61=null;
        Token string_literal63=null;
        Token string_literal66=null;
        Token string_literal69=null;
        Token string_literal70=null;
        Token string_literal71=null;
        Token string_literal74=null;
        Token string_literal77=null;
        Token string_literal79=null;
        Token ID80=null;
        Token char_literal81=null;
        Token char_literal83=null;
        Token char_literal85=null;
        Token string_literal86=null;
        Token string_literal88=null;
        Token char_literal90=null;
        Token string_literal92=null;
        Token string_literal93=null;
        Token ID94=null;
        Token char_literal95=null;
        Token char_literal97=null;
        Token char_literal99=null;
        Token string_literal100=null;
        Token string_literal102=null;
        Token string_literal104=null;
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


        Object tag_tree=null;
        Object varName_tree=null;
        Object ID37_tree=null;
        Object char_literal38_tree=null;
        Object char_literal39_tree=null;
        Object string_literal40_tree=null;
        Object string_literal41_tree=null;
        Object string_literal42_tree=null;
        Object string_literal45_tree=null;
        Object string_literal46_tree=null;
        Object string_literal47_tree=null;
        Object string_literal50_tree=null;
        Object string_literal53_tree=null;
        Object char_literal54_tree=null;
        Object char_literal55_tree=null;
        Object char_literal56_tree=null;
        Object string_literal58_tree=null;
        Object char_literal60_tree=null;
        Object string_literal61_tree=null;
        Object string_literal63_tree=null;
        Object string_literal66_tree=null;
        Object string_literal69_tree=null;
        Object string_literal70_tree=null;
        Object string_literal71_tree=null;
        Object string_literal74_tree=null;
        Object string_literal77_tree=null;
        Object string_literal79_tree=null;
        Object ID80_tree=null;
        Object char_literal81_tree=null;
        Object char_literal83_tree=null;
        Object char_literal85_tree=null;
        Object string_literal86_tree=null;
        Object string_literal88_tree=null;
        Object char_literal90_tree=null;
        Object string_literal92_tree=null;
        Object string_literal93_tree=null;
        Object ID94_tree=null;
        Object char_literal95_tree=null;
        Object char_literal97_tree=null;
        Object char_literal99_tree=null;
        Object string_literal100_tree=null;
        Object string_literal102_tree=null;
        Object string_literal104_tree=null;
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_69=new RewriteRuleTokenStream(adaptor,"token 69");
        RewriteRuleTokenStream stream_77=new RewriteRuleTokenStream(adaptor,"token 77");
        RewriteRuleTokenStream stream_68=new RewriteRuleTokenStream(adaptor,"token 68");
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_70=new RewriteRuleTokenStream(adaptor,"token 70");
        RewriteRuleTokenStream stream_80=new RewriteRuleTokenStream(adaptor,"token 80");
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleTokenStream stream_72=new RewriteRuleTokenStream(adaptor,"token 72");
        RewriteRuleTokenStream stream_73=new RewriteRuleTokenStream(adaptor,"token 73");
        RewriteRuleTokenStream stream_74=new RewriteRuleTokenStream(adaptor,"token 74");
        RewriteRuleTokenStream stream_61=new RewriteRuleTokenStream(adaptor,"token 61");
        RewriteRuleTokenStream stream_75=new RewriteRuleTokenStream(adaptor,"token 75");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:155:17: ( ID ( ( ( ( '.' tag= ID )* -> ( $tag)* ) ':' ( 'action' (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'action' ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'initialize' ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' ) | 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'action' TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'initialize' TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | priorityOrder -> priorityOrder | 'function' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end' -> FUNCTION | 'procedure' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end' -> PROCEDURE )
            int alt43=6;
            switch ( input.LA(1) ) {
            case ID:
                {
                alt43=1;
                }
                break;
            case 73:
                {
                alt43=2;
                }
                break;
            case 75:
                {
                alt43=3;
                }
                break;
            case 114:
                {
                alt43=4;
                }
                break;
            case 79:
                {
                alt43=5;
                }
                break;
            case 81:
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:160:3: ID ( ( ( ( '.' tag= ID )* -> ( $tag)* ) ':' ( 'action' (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'action' ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'initialize' ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' )
                    {
                    ID37=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration604);  
                    stream_ID.add(ID37);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:160:6: ( ( ( ( '.' tag= ID )* -> ( $tag)* ) ':' ( 'action' (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'action' ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'initialize' ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) ) | ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' )
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==61||LA26_0==72) ) {
                        alt26=1;
                    }
                    else if ( (LA26_0==ID||LA26_0==68) ) {
                        alt26=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 26, 0, input);

                        throw nvae;
                    }
                    switch (alt26) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:5: ( ( ( '.' tag= ID )* -> ( $tag)* ) ':' ( 'action' (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'action' ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'initialize' ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) )
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:5: ( ( ( '.' tag= ID )* -> ( $tag)* ) ':' ( 'action' (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'action' ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'initialize' ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) ) )
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:6: ( ( '.' tag= ID )* -> ( $tag)* ) ':' ( 'action' (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'action' ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'initialize' ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) )
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:6: ( ( '.' tag= ID )* -> ( $tag)* )
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:7: ( '.' tag= ID )*
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:7: ( '.' tag= ID )*
                            loop13:
                            do {
                                int alt13=2;
                                int LA13_0 = input.LA(1);

                                if ( (LA13_0==72) ) {
                                    alt13=1;
                                }


                                switch (alt13) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:8: '.' tag= ID
                            	    {
                            	    char_literal38=(Token)match(input,72,FOLLOW_72_in_actorDeclaration615);  
                            	    stream_72.add(char_literal38);

                            	    tag=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration619);  
                            	    stream_ID.add(tag);


                            	    }
                            	    break;

                            	default :
                            	    break loop13;
                                }
                            } while (true);



                            // AST REWRITE
                            // elements: tag
                            // token labels: tag
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleTokenStream stream_tag=new RewriteRuleTokenStream(adaptor,"token tag",tag);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 161:21: -> ( $tag)*
                            {
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:24: ( $tag)*
                                while ( stream_tag.hasNext() ) {
                                    adaptor.addChild(root_0, stream_tag.nextNode());

                                }
                                stream_tag.reset();

                            }

                            retval.tree = root_0;
                            }

                            char_literal39=(Token)match(input,61,FOLLOW_61_in_actorDeclaration630);  
                            stream_61.add(char_literal39);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:7: ( 'action' (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'action' ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end' -> ^( 'initialize' ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) ) )
                            int alt23=2;
                            int LA23_0 = input.LA(1);

                            if ( (LA23_0==73) ) {
                                alt23=1;
                            }
                            else if ( (LA23_0==75) ) {
                                alt23=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 23, 0, input);

                                throw nvae;
                            }
                            switch (alt23) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:8: 'action' (inputs= actionInputs )? '==>' (outputs= actionOutputs )? (guards= actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                                    {
                                    string_literal40=(Token)match(input,73,FOLLOW_73_in_actorDeclaration639);  
                                    stream_73.add(string_literal40);

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:23: (inputs= actionInputs )?
                                    int alt14=2;
                                    int LA14_0 = input.LA(1);

                                    if ( (LA14_0==ID||LA14_0==62) ) {
                                        alt14=1;
                                    }
                                    switch (alt14) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:23: inputs= actionInputs
                                            {
                                            pushFollow(FOLLOW_actionInputs_in_actorDeclaration643);
                                            inputs=actionInputs();

                                            state._fsp--;

                                            stream_actionInputs.add(inputs.getTree());

                                            }
                                            break;

                                    }

                                    string_literal41=(Token)match(input,70,FOLLOW_70_in_actorDeclaration646);  
                                    stream_70.add(string_literal41);

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:51: (outputs= actionOutputs )?
                                    int alt15=2;
                                    int LA15_0 = input.LA(1);

                                    if ( (LA15_0==ID||LA15_0==62) ) {
                                        alt15=1;
                                    }
                                    switch (alt15) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:51: outputs= actionOutputs
                                            {
                                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration650);
                                            outputs=actionOutputs();

                                            state._fsp--;

                                            stream_actionOutputs.add(outputs.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:73: (guards= actionGuards )?
                                    int alt16=2;
                                    int LA16_0 = input.LA(1);

                                    if ( (LA16_0==60) ) {
                                        alt16=1;
                                    }
                                    switch (alt16) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:73: guards= actionGuards
                                            {
                                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration655);
                                            guards=actionGuards();

                                            state._fsp--;

                                            stream_actionGuards.add(guards.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:88: ( 'var' varDecls )?
                                    int alt17=2;
                                    int LA17_0 = input.LA(1);

                                    if ( (LA17_0==74) ) {
                                        alt17=1;
                                    }
                                    switch (alt17) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:89: 'var' varDecls
                                            {
                                            string_literal42=(Token)match(input,74,FOLLOW_74_in_actorDeclaration659);  
                                            stream_74.add(string_literal42);

                                            pushFollow(FOLLOW_varDecls_in_actorDeclaration661);
                                            varDecls43=varDecls();

                                            state._fsp--;

                                            stream_varDecls.add(varDecls43.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:106: ( actionStatements )?
                                    int alt18=2;
                                    int LA18_0 = input.LA(1);

                                    if ( (LA18_0==66) ) {
                                        alt18=1;
                                    }
                                    switch (alt18) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:106: actionStatements
                                            {
                                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration665);
                                            actionStatements44=actionStatements();

                                            state._fsp--;

                                            stream_actionStatements.add(actionStatements44.getTree());

                                            }
                                            break;

                                    }

                                    string_literal45=(Token)match(input,71,FOLLOW_71_in_actorDeclaration668);  
                                    stream_71.add(string_literal45);



                                    // AST REWRITE
                                    // elements: 73, varDecls, inputs, tag, guards, actionStatements, outputs, ID
                                    // token labels: tag
                                    // rule labels: retval, guards, inputs, outputs
                                    // token list labels: 
                                    // rule list labels: 
                                    // wildcard labels: 
                                    retval.tree = root_0;
                                    RewriteRuleTokenStream stream_tag=new RewriteRuleTokenStream(adaptor,"token tag",tag);
                                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                                    RewriteRuleSubtreeStream stream_guards=new RewriteRuleSubtreeStream(adaptor,"rule guards",guards!=null?guards.tree:null);
                                    RewriteRuleSubtreeStream stream_inputs=new RewriteRuleSubtreeStream(adaptor,"rule inputs",inputs!=null?inputs.tree:null);
                                    RewriteRuleSubtreeStream stream_outputs=new RewriteRuleSubtreeStream(adaptor,"rule outputs",outputs!=null?outputs.tree:null);

                                    root_0 = (Object)adaptor.nil();
                                    // 163:9: -> ^( 'action' ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                    {
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:12: ^( 'action' ^( TAG ID ( $tag)* ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot(stream_73.nextNode(), root_1);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:23: ^( TAG ID ( $tag)* )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TAG, "TAG"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:32: ( $tag)*
                                        while ( stream_tag.hasNext() ) {
                                            adaptor.addChild(root_2, stream_tag.nextNode());

                                        }
                                        stream_tag.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:39: ^( INPUTS ( $inputs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:48: ( $inputs)?
                                        if ( stream_inputs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_inputs.nextTree());

                                        }
                                        stream_inputs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:58: ^( OUTPUTS ( $outputs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:68: ( $outputs)?
                                        if ( stream_outputs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_outputs.nextTree());

                                        }
                                        stream_outputs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:79: ^( GUARDS ( $guards)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:88: ( $guards)?
                                        if ( stream_guards.hasNext() ) {
                                            adaptor.addChild(root_2, stream_guards.nextTree());

                                        }
                                        stream_guards.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:98: ^( VARS ( varDecls )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARS, "VARS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:105: ( varDecls )?
                                        if ( stream_varDecls.hasNext() ) {
                                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                                        }
                                        stream_varDecls.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:116: ^( STATEMENTS ( actionStatements )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:163:129: ( actionStatements )?
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
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:165:7: 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                                    {
                                    string_literal46=(Token)match(input,75,FOLLOW_75_in_actorDeclaration739);  
                                    stream_75.add(string_literal46);

                                    string_literal47=(Token)match(input,70,FOLLOW_70_in_actorDeclaration741);  
                                    stream_70.add(string_literal47);

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:165:26: ( actionOutputs )?
                                    int alt19=2;
                                    int LA19_0 = input.LA(1);

                                    if ( (LA19_0==ID||LA19_0==62) ) {
                                        alt19=1;
                                    }
                                    switch (alt19) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:165:26: actionOutputs
                                            {
                                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration743);
                                            actionOutputs48=actionOutputs();

                                            state._fsp--;

                                            stream_actionOutputs.add(actionOutputs48.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:165:41: ( actionGuards )?
                                    int alt20=2;
                                    int LA20_0 = input.LA(1);

                                    if ( (LA20_0==60) ) {
                                        alt20=1;
                                    }
                                    switch (alt20) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:165:41: actionGuards
                                            {
                                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration746);
                                            actionGuards49=actionGuards();

                                            state._fsp--;

                                            stream_actionGuards.add(actionGuards49.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:165:55: ( 'var' varDecls )?
                                    int alt21=2;
                                    int LA21_0 = input.LA(1);

                                    if ( (LA21_0==74) ) {
                                        alt21=1;
                                    }
                                    switch (alt21) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:165:56: 'var' varDecls
                                            {
                                            string_literal50=(Token)match(input,74,FOLLOW_74_in_actorDeclaration750);  
                                            stream_74.add(string_literal50);

                                            pushFollow(FOLLOW_varDecls_in_actorDeclaration752);
                                            varDecls51=varDecls();

                                            state._fsp--;

                                            stream_varDecls.add(varDecls51.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:165:73: ( actionStatements )?
                                    int alt22=2;
                                    int LA22_0 = input.LA(1);

                                    if ( (LA22_0==66) ) {
                                        alt22=1;
                                    }
                                    switch (alt22) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:165:73: actionStatements
                                            {
                                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration756);
                                            actionStatements52=actionStatements();

                                            state._fsp--;

                                            stream_actionStatements.add(actionStatements52.getTree());

                                            }
                                            break;

                                    }

                                    string_literal53=(Token)match(input,71,FOLLOW_71_in_actorDeclaration759);  
                                    stream_71.add(string_literal53);



                                    // AST REWRITE
                                    // elements: 75, tag, outputs, ID, actionStatements, varDecls, guards
                                    // token labels: tag
                                    // rule labels: retval, guards, outputs
                                    // token list labels: 
                                    // rule list labels: 
                                    // wildcard labels: 
                                    retval.tree = root_0;
                                    RewriteRuleTokenStream stream_tag=new RewriteRuleTokenStream(adaptor,"token tag",tag);
                                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                                    RewriteRuleSubtreeStream stream_guards=new RewriteRuleSubtreeStream(adaptor,"rule guards",guards!=null?guards.tree:null);
                                    RewriteRuleSubtreeStream stream_outputs=new RewriteRuleSubtreeStream(adaptor,"rule outputs",outputs!=null?outputs.tree:null);

                                    root_0 = (Object)adaptor.nil();
                                    // 166:9: -> ^( 'initialize' ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                    {
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:12: ^( 'initialize' ^( TAG ID ( $tag)* ) INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot(stream_75.nextNode(), root_1);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:27: ^( TAG ID ( $tag)* )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TAG, "TAG"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:36: ( $tag)*
                                        while ( stream_tag.hasNext() ) {
                                            adaptor.addChild(root_2, stream_tag.nextNode());

                                        }
                                        stream_tag.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        adaptor.addChild(root_1, (Object)adaptor.create(INPUTS, "INPUTS"));
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:50: ^( OUTPUTS ( $outputs)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:60: ( $outputs)?
                                        if ( stream_outputs.hasNext() ) {
                                            adaptor.addChild(root_2, stream_outputs.nextTree());

                                        }
                                        stream_outputs.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:71: ^( GUARDS ( $guards)? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:80: ( $guards)?
                                        if ( stream_guards.hasNext() ) {
                                            adaptor.addChild(root_2, stream_guards.nextTree());

                                        }
                                        stream_guards.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:90: ^( VARS ( varDecls )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARS, "VARS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:97: ( varDecls )?
                                        if ( stream_varDecls.hasNext() ) {
                                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                                        }
                                        stream_varDecls.reset();

                                        adaptor.addChild(root_1, root_2);
                                        }
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:108: ^( STATEMENTS ( actionStatements )? )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:121: ( actionStatements )?
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
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:5: ( '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';'
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:5: ( '(' attrs= typeAttrs ')' )?
                            int alt24=2;
                            int LA24_0 = input.LA(1);

                            if ( (LA24_0==68) ) {
                                alt24=1;
                            }
                            switch (alt24) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:6: '(' attrs= typeAttrs ')'
                                    {
                                    char_literal54=(Token)match(input,68,FOLLOW_68_in_actorDeclaration838);  
                                    stream_68.add(char_literal54);

                                    pushFollow(FOLLOW_typeAttrs_in_actorDeclaration842);
                                    attrs=typeAttrs();

                                    state._fsp--;

                                    stream_typeAttrs.add(attrs.getTree());
                                    char_literal55=(Token)match(input,69,FOLLOW_69_in_actorDeclaration844);  
                                    stream_69.add(char_literal55);


                                    }
                                    break;

                            }

                            varName=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration854);  
                            stream_ID.add(varName);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:174:5: ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) )
                            int alt25=3;
                            switch ( input.LA(1) ) {
                            case 76:
                                {
                                alt25=1;
                                }
                                break;
                            case 77:
                                {
                                alt25=2;
                                }
                                break;
                            case 78:
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
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:174:8: '=' expression
                                    {
                                    char_literal56=(Token)match(input,76,FOLLOW_76_in_actorDeclaration863);  
                                    stream_76.add(char_literal56);

                                    pushFollow(FOLLOW_expression_in_actorDeclaration865);
                                    expression57=expression();

                                    state._fsp--;

                                    stream_expression.add(expression57.getTree());


                                    // AST REWRITE
                                    // elements: expression, attrs, varName, ID
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
                                    // 174:23: -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression )
                                    {
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:174:26: ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:174:38: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:174:48: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:174:61: ( $attrs)?
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
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:8: ':=' expression
                                    {
                                    string_literal58=(Token)match(input,77,FOLLOW_77_in_actorDeclaration901);  
                                    stream_77.add(string_literal58);

                                    pushFollow(FOLLOW_expression_in_actorDeclaration903);
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
                                    // 175:24: -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression )
                                    {
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:27: ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:39: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:49: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:62: ( $attrs)?
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
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:8: 
                                    {

                                    // AST REWRITE
                                    // elements: ID, attrs, varName
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
                                    // 176:8: -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE )
                                    {
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:11: ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:23: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:33: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:46: ( $attrs)?
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

                            char_literal60=(Token)match(input,78,FOLLOW_78_in_actorDeclaration965);  
                            stream_78.add(char_literal60);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:180:3: 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                    {
                    string_literal61=(Token)match(input,73,FOLLOW_73_in_actorDeclaration975);  
                    stream_73.add(string_literal61);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:180:12: ( actionInputs )?
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==ID||LA27_0==62) ) {
                        alt27=1;
                    }
                    switch (alt27) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:180:12: actionInputs
                            {
                            pushFollow(FOLLOW_actionInputs_in_actorDeclaration977);
                            actionInputs62=actionInputs();

                            state._fsp--;

                            stream_actionInputs.add(actionInputs62.getTree());

                            }
                            break;

                    }

                    string_literal63=(Token)match(input,70,FOLLOW_70_in_actorDeclaration980);  
                    stream_70.add(string_literal63);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:180:32: ( actionOutputs )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==ID||LA28_0==62) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:180:32: actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration982);
                            actionOutputs64=actionOutputs();

                            state._fsp--;

                            stream_actionOutputs.add(actionOutputs64.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:180:47: ( actionGuards )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==60) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:180:47: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration985);
                            actionGuards65=actionGuards();

                            state._fsp--;

                            stream_actionGuards.add(actionGuards65.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:180:61: ( 'var' varDecls )?
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==74) ) {
                        alt30=1;
                    }
                    switch (alt30) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:180:62: 'var' varDecls
                            {
                            string_literal66=(Token)match(input,74,FOLLOW_74_in_actorDeclaration989);  
                            stream_74.add(string_literal66);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration991);
                            varDecls67=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls67.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:180:79: ( actionStatements )?
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==66) ) {
                        alt31=1;
                    }
                    switch (alt31) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:180:79: actionStatements
                            {
                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration995);
                            actionStatements68=actionStatements();

                            state._fsp--;

                            stream_actionStatements.add(actionStatements68.getTree());

                            }
                            break;

                    }

                    string_literal69=(Token)match(input,71,FOLLOW_71_in_actorDeclaration998);  
                    stream_71.add(string_literal69);



                    // AST REWRITE
                    // elements: actionStatements, outputs, varDecls, inputs, 73, guards
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
                    // 181:3: -> ^( 'action' TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:6: ^( 'action' TAG ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_73.nextNode(), root_1);

                        adaptor.addChild(root_1, (Object)adaptor.create(TAG, "TAG"));
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:21: ^( INPUTS ( $inputs)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:30: ( $inputs)?
                        if ( stream_inputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_inputs.nextTree());

                        }
                        stream_inputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:40: ^( OUTPUTS ( $outputs)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:50: ( $outputs)?
                        if ( stream_outputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_outputs.nextTree());

                        }
                        stream_outputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:61: ^( GUARDS ( $guards)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:70: ( $guards)?
                        if ( stream_guards.hasNext() ) {
                            adaptor.addChild(root_2, stream_guards.nextTree());

                        }
                        stream_guards.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:80: ^( VARS ( varDecls )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARS, "VARS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:87: ( varDecls )?
                        if ( stream_varDecls.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                        }
                        stream_varDecls.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:98: ^( STATEMENTS ( actionStatements )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:111: ( actionStatements )?
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:3: 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( 'var' varDecls )? ( actionStatements )? 'end'
                    {
                    string_literal70=(Token)match(input,75,FOLLOW_75_in_actorDeclaration1052);  
                    stream_75.add(string_literal70);

                    string_literal71=(Token)match(input,70,FOLLOW_70_in_actorDeclaration1054);  
                    stream_70.add(string_literal71);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:22: ( actionOutputs )?
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==ID||LA32_0==62) ) {
                        alt32=1;
                    }
                    switch (alt32) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:22: actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration1056);
                            actionOutputs72=actionOutputs();

                            state._fsp--;

                            stream_actionOutputs.add(actionOutputs72.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:37: ( actionGuards )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==60) ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:37: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration1059);
                            actionGuards73=actionGuards();

                            state._fsp--;

                            stream_actionGuards.add(actionGuards73.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:51: ( 'var' varDecls )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==74) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:52: 'var' varDecls
                            {
                            string_literal74=(Token)match(input,74,FOLLOW_74_in_actorDeclaration1063);  
                            stream_74.add(string_literal74);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration1065);
                            varDecls75=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls75.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:69: ( actionStatements )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==66) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:69: actionStatements
                            {
                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration1069);
                            actionStatements76=actionStatements();

                            state._fsp--;

                            stream_actionStatements.add(actionStatements76.getTree());

                            }
                            break;

                    }

                    string_literal77=(Token)match(input,71,FOLLOW_71_in_actorDeclaration1072);  
                    stream_71.add(string_literal77);



                    // AST REWRITE
                    // elements: varDecls, guards, outputs, 75, actionStatements
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
                    // 185:3: -> ^( 'initialize' TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:185:6: ^( 'initialize' TAG INPUTS ^( OUTPUTS ( $outputs)? ) ^( GUARDS ( $guards)? ) ^( VARS ( varDecls )? ) ^( STATEMENTS ( actionStatements )? ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_75.nextNode(), root_1);

                        adaptor.addChild(root_1, (Object)adaptor.create(TAG, "TAG"));
                        adaptor.addChild(root_1, (Object)adaptor.create(INPUTS, "INPUTS"));
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:185:32: ^( OUTPUTS ( $outputs)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:185:42: ( $outputs)?
                        if ( stream_outputs.hasNext() ) {
                            adaptor.addChild(root_2, stream_outputs.nextTree());

                        }
                        stream_outputs.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:185:53: ^( GUARDS ( $guards)? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(GUARDS, "GUARDS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:185:62: ( $guards)?
                        if ( stream_guards.hasNext() ) {
                            adaptor.addChild(root_2, stream_guards.nextTree());

                        }
                        stream_guards.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:185:72: ^( VARS ( varDecls )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARS, "VARS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:185:79: ( varDecls )?
                        if ( stream_varDecls.hasNext() ) {
                            adaptor.addChild(root_2, stream_varDecls.nextTree());

                        }
                        stream_varDecls.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:185:90: ^( STATEMENTS ( actionStatements )? )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:185:103: ( actionStatements )?
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:187:3: priorityOrder
                    {
                    pushFollow(FOLLOW_priorityOrder_in_actorDeclaration1119);
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
                    // 187:17: -> priorityOrder
                    {
                        adaptor.addChild(root_0, stream_priorityOrder.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:3: 'function' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end'
                    {
                    string_literal79=(Token)match(input,79,FOLLOW_79_in_actorDeclaration1128);  
                    stream_79.add(string_literal79);

                    ID80=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration1130);  
                    stream_ID.add(ID80);

                    char_literal81=(Token)match(input,68,FOLLOW_68_in_actorDeclaration1132);  
                    stream_68.add(char_literal81);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:21: ( varDeclNoExpr ( ',' varDeclNoExpr )* )?
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==ID) ) {
                        alt37=1;
                    }
                    switch (alt37) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:22: varDeclNoExpr ( ',' varDeclNoExpr )*
                            {
                            pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration1135);
                            varDeclNoExpr82=varDeclNoExpr();

                            state._fsp--;

                            stream_varDeclNoExpr.add(varDeclNoExpr82.getTree());
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:36: ( ',' varDeclNoExpr )*
                            loop36:
                            do {
                                int alt36=2;
                                int LA36_0 = input.LA(1);

                                if ( (LA36_0==64) ) {
                                    alt36=1;
                                }


                                switch (alt36) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:37: ',' varDeclNoExpr
                            	    {
                            	    char_literal83=(Token)match(input,64,FOLLOW_64_in_actorDeclaration1138);  
                            	    stream_64.add(char_literal83);

                            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration1140);
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

                    char_literal85=(Token)match(input,69,FOLLOW_69_in_actorDeclaration1146);  
                    stream_69.add(char_literal85);

                    string_literal86=(Token)match(input,80,FOLLOW_80_in_actorDeclaration1148);  
                    stream_80.add(string_literal86);

                    pushFollow(FOLLOW_typeDef_in_actorDeclaration1150);
                    typeDef87=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef87.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:190:5: ( 'var' varDecls )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==74) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:190:6: 'var' varDecls
                            {
                            string_literal88=(Token)match(input,74,FOLLOW_74_in_actorDeclaration1157);  
                            stream_74.add(string_literal88);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration1159);
                            varDecls89=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls89.getTree());

                            }
                            break;

                    }

                    char_literal90=(Token)match(input,61,FOLLOW_61_in_actorDeclaration1163);  
                    stream_61.add(char_literal90);

                    pushFollow(FOLLOW_expression_in_actorDeclaration1171);
                    expression91=expression();

                    state._fsp--;

                    stream_expression.add(expression91.getTree());
                    string_literal92=(Token)match(input,71,FOLLOW_71_in_actorDeclaration1177);  
                    stream_71.add(string_literal92);



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
                    // 193:2: -> FUNCTION
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(FUNCTION, "FUNCTION"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 6 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:3: 'procedure' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end'
                    {
                    string_literal93=(Token)match(input,81,FOLLOW_81_in_actorDeclaration1187);  
                    stream_81.add(string_literal93);

                    ID94=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration1189);  
                    stream_ID.add(ID94);

                    char_literal95=(Token)match(input,68,FOLLOW_68_in_actorDeclaration1191);  
                    stream_68.add(char_literal95);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:22: ( varDeclNoExpr ( ',' varDeclNoExpr )* )?
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( (LA40_0==ID) ) {
                        alt40=1;
                    }
                    switch (alt40) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:23: varDeclNoExpr ( ',' varDeclNoExpr )*
                            {
                            pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration1194);
                            varDeclNoExpr96=varDeclNoExpr();

                            state._fsp--;

                            stream_varDeclNoExpr.add(varDeclNoExpr96.getTree());
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:37: ( ',' varDeclNoExpr )*
                            loop39:
                            do {
                                int alt39=2;
                                int LA39_0 = input.LA(1);

                                if ( (LA39_0==64) ) {
                                    alt39=1;
                                }


                                switch (alt39) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:38: ',' varDeclNoExpr
                            	    {
                            	    char_literal97=(Token)match(input,64,FOLLOW_64_in_actorDeclaration1197);  
                            	    stream_64.add(char_literal97);

                            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration1199);
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

                    char_literal99=(Token)match(input,69,FOLLOW_69_in_actorDeclaration1205);  
                    stream_69.add(char_literal99);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:196:5: ( 'var' varDecls )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==74) ) {
                        alt41=1;
                    }
                    switch (alt41) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:196:6: 'var' varDecls
                            {
                            string_literal100=(Token)match(input,74,FOLLOW_74_in_actorDeclaration1212);  
                            stream_74.add(string_literal100);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration1214);
                            varDecls101=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls101.getTree());

                            }
                            break;

                    }

                    string_literal102=(Token)match(input,82,FOLLOW_82_in_actorDeclaration1222);  
                    stream_82.add(string_literal102);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:197:13: ( statement )*
                    loop42:
                    do {
                        int alt42=2;
                        int LA42_0 = input.LA(1);

                        if ( (LA42_0==ID||LA42_0==82||LA42_0==107||LA42_0==117||LA42_0==119) ) {
                            alt42=1;
                        }


                        switch (alt42) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:197:13: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_actorDeclaration1224);
                    	    statement103=statement();

                    	    state._fsp--;

                    	    stream_statement.add(statement103.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop42;
                        }
                    } while (true);

                    string_literal104=(Token)match(input,71,FOLLOW_71_in_actorDeclaration1227);  
                    stream_71.add(string_literal104);



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
                    // 198:2: -> PROCEDURE
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(PROCEDURE, "PROCEDURE"));

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:1: actorDeclarations : ( actorDeclaration )* ( schedule ( actorDeclaration )* )? -> ( actorDeclaration )* ( schedule )? ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:18: ( ( actorDeclaration )* ( schedule ( actorDeclaration )* )? -> ( actorDeclaration )* ( schedule )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:20: ( actorDeclaration )* ( schedule ( actorDeclaration )* )?
            {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:20: ( actorDeclaration )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==ID||LA44_0==73||LA44_0==75||LA44_0==79||LA44_0==81||LA44_0==114) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:20: actorDeclaration
            	    {
            	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations1239);
            	    actorDeclaration105=actorDeclaration();

            	    state._fsp--;

            	    stream_actorDeclaration.add(actorDeclaration105.getTree());

            	    }
            	    break;

            	default :
            	    break loop44;
                }
            } while (true);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:38: ( schedule ( actorDeclaration )* )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==115) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:39: schedule ( actorDeclaration )*
                    {
                    pushFollow(FOLLOW_schedule_in_actorDeclarations1243);
                    schedule106=schedule();

                    state._fsp--;

                    stream_schedule.add(schedule106.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:48: ( actorDeclaration )*
                    loop45:
                    do {
                        int alt45=2;
                        int LA45_0 = input.LA(1);

                        if ( (LA45_0==ID||LA45_0==73||LA45_0==75||LA45_0==79||LA45_0==81||LA45_0==114) ) {
                            alt45=1;
                        }


                        switch (alt45) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:48: actorDeclaration
                    	    {
                    	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations1245);
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
            // 200:68: -> ( actorDeclaration )* ( schedule )?
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:71: ( actorDeclaration )*
                while ( stream_actorDeclaration.hasNext() ) {
                    adaptor.addChild(root_0, stream_actorDeclaration.nextTree());

                }
                stream_actorDeclaration.reset();
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:89: ( schedule )?
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:202:1: actorImport : 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:205:12: ( 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:205:14: 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            {
            root_0 = (Object)adaptor.nil();

            string_literal108=(Token)match(input,83,FOLLOW_83_in_actorImport1268); 
            string_literal108_tree = (Object)adaptor.create(string_literal108);
            adaptor.addChild(root_0, string_literal108_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:206:3: ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==84) ) {
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:206:4: 'all' qualifiedIdent ';'
                    {
                    string_literal109=(Token)match(input,84,FOLLOW_84_in_actorImport1273); 
                    string_literal109_tree = (Object)adaptor.create(string_literal109);
                    adaptor.addChild(root_0, string_literal109_tree);

                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport1275);
                    qualifiedIdent110=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent110.getTree());
                    char_literal111=(Token)match(input,78,FOLLOW_78_in_actorImport1277); 
                    char_literal111_tree = (Object)adaptor.create(char_literal111);
                    adaptor.addChild(root_0, char_literal111_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:207:3: qualifiedIdent ';'
                    {
                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport1283);
                    qualifiedIdent112=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent112.getTree());
                    char_literal113=(Token)match(input,78,FOLLOW_78_in_actorImport1285); 
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:209:1: actorParameter : typeDef ID ( '=' expression )? -> ^( PARAMETER typeDef ID ( expression )? ) ;
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
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:211:15: ( typeDef ID ( '=' expression )? -> ^( PARAMETER typeDef ID ( expression )? ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:212:2: typeDef ID ( '=' expression )?
            {
            pushFollow(FOLLOW_typeDef_in_actorParameter1300);
            typeDef114=typeDef();

            state._fsp--;

            stream_typeDef.add(typeDef114.getTree());
            ID115=(Token)match(input,ID,FOLLOW_ID_in_actorParameter1302);  
            stream_ID.add(ID115);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:212:13: ( '=' expression )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==76) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:212:14: '=' expression
                    {
                    char_literal116=(Token)match(input,76,FOLLOW_76_in_actorParameter1305);  
                    stream_76.add(char_literal116);

                    pushFollow(FOLLOW_expression_in_actorParameter1307);
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
            // 212:31: -> ^( PARAMETER typeDef ID ( expression )? )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:212:34: ^( PARAMETER typeDef ID ( expression )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETER, "PARAMETER"), root_1);

                adaptor.addChild(root_1, stream_typeDef.nextTree());
                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:212:57: ( expression )?
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:214:1: actorParameters : actorParameter ( ',' actorParameter )* -> ( actorParameter )+ ;
    public final RVCCalParser.actorParameters_return actorParameters() throws RecognitionException {
        RVCCalParser.actorParameters_return retval = new RVCCalParser.actorParameters_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal119=null;
        RVCCalParser.actorParameter_return actorParameter118 = null;

        RVCCalParser.actorParameter_return actorParameter120 = null;


        Object char_literal119_tree=null;
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleSubtreeStream stream_actorParameter=new RewriteRuleSubtreeStream(adaptor,"rule actorParameter");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:214:16: ( actorParameter ( ',' actorParameter )* -> ( actorParameter )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:214:18: actorParameter ( ',' actorParameter )*
            {
            pushFollow(FOLLOW_actorParameter_in_actorParameters1329);
            actorParameter118=actorParameter();

            state._fsp--;

            stream_actorParameter.add(actorParameter118.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:214:33: ( ',' actorParameter )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==64) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:214:34: ',' actorParameter
            	    {
            	    char_literal119=(Token)match(input,64,FOLLOW_64_in_actorParameters1332);  
            	    stream_64.add(char_literal119);

            	    pushFollow(FOLLOW_actorParameter_in_actorParameters1334);
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
            // 214:55: -> ( actorParameter )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:216:1: actorPortDecls : varDeclNoExpr ( ',' varDeclNoExpr )* -> ( varDeclNoExpr )+ ;
    public final RVCCalParser.actorPortDecls_return actorPortDecls() throws RecognitionException {
        RVCCalParser.actorPortDecls_return retval = new RVCCalParser.actorPortDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal122=null;
        RVCCalParser.varDeclNoExpr_return varDeclNoExpr121 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr123 = null;


        Object char_literal122_tree=null;
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleSubtreeStream stream_varDeclNoExpr=new RewriteRuleSubtreeStream(adaptor,"rule varDeclNoExpr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:15: ( varDeclNoExpr ( ',' varDeclNoExpr )* -> ( varDeclNoExpr )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:17: varDeclNoExpr ( ',' varDeclNoExpr )*
            {
            pushFollow(FOLLOW_varDeclNoExpr_in_actorPortDecls1353);
            varDeclNoExpr121=varDeclNoExpr();

            state._fsp--;

            stream_varDeclNoExpr.add(varDeclNoExpr121.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:31: ( ',' varDeclNoExpr )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==64) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:32: ',' varDeclNoExpr
            	    {
            	    char_literal122=(Token)match(input,64,FOLLOW_64_in_actorPortDecls1356);  
            	    stream_64.add(char_literal122);

            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorPortDecls1358);
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
            // 219:52: -> ( varDeclNoExpr )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:1: expression : e1= and_expr ( ( ( 'or' | '||' ) e2= and_expr )+ -> ^( EXPR_OR $e1 ( $e2)+ ) | -> $e1) ;
    public final RVCCalParser.expression_return expression() throws RecognitionException {
        RVCCalParser.expression_return retval = new RVCCalParser.expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal124=null;
        Token string_literal125=null;
        RVCCalParser.and_expr_return e1 = null;

        RVCCalParser.and_expr_return e2 = null;


        Object string_literal124_tree=null;
        Object string_literal125_tree=null;
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_85=new RewriteRuleTokenStream(adaptor,"token 85");
        RewriteRuleSubtreeStream stream_and_expr=new RewriteRuleSubtreeStream(adaptor,"rule and_expr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:11: (e1= and_expr ( ( ( 'or' | '||' ) e2= and_expr )+ -> ^( EXPR_OR $e1 ( $e2)+ ) | -> $e1) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:13: e1= and_expr ( ( ( 'or' | '||' ) e2= and_expr )+ -> ^( EXPR_OR $e1 ( $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_and_expr_in_expression1383);
            e1=and_expr();

            state._fsp--;

            stream_and_expr.add(e1.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:25: ( ( ( 'or' | '||' ) e2= and_expr )+ -> ^( EXPR_OR $e1 ( $e2)+ ) | -> $e1)
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( ((LA53_0>=85 && LA53_0<=86)) ) {
                alt53=1;
            }
            else if ( ((LA53_0>=60 && LA53_0<=61)||(LA53_0>=63 && LA53_0<=64)||LA53_0==66||(LA53_0>=69 && LA53_0<=71)||LA53_0==74||LA53_0==78||LA53_0==82||(LA53_0>=108 && LA53_0<=109)||LA53_0==118) ) {
                alt53=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 53, 0, input);

                throw nvae;
            }
            switch (alt53) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:26: ( ( 'or' | '||' ) e2= and_expr )+
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:26: ( ( 'or' | '||' ) e2= and_expr )+
                    int cnt52=0;
                    loop52:
                    do {
                        int alt52=2;
                        int LA52_0 = input.LA(1);

                        if ( ((LA52_0>=85 && LA52_0<=86)) ) {
                            alt52=1;
                        }


                        switch (alt52) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:27: ( 'or' | '||' ) e2= and_expr
                    	    {
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:27: ( 'or' | '||' )
                    	    int alt51=2;
                    	    int LA51_0 = input.LA(1);

                    	    if ( (LA51_0==85) ) {
                    	        alt51=1;
                    	    }
                    	    else if ( (LA51_0==86) ) {
                    	        alt51=2;
                    	    }
                    	    else {
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 51, 0, input);

                    	        throw nvae;
                    	    }
                    	    switch (alt51) {
                    	        case 1 :
                    	            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:28: 'or'
                    	            {
                    	            string_literal124=(Token)match(input,85,FOLLOW_85_in_expression1388);  
                    	            stream_85.add(string_literal124);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:35: '||'
                    	            {
                    	            string_literal125=(Token)match(input,86,FOLLOW_86_in_expression1392);  
                    	            stream_86.add(string_literal125);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_and_expr_in_expression1397);
                    	    e2=and_expr();

                    	    state._fsp--;

                    	    stream_and_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt52 >= 1 ) break loop52;
                                EarlyExitException eee =
                                    new EarlyExitException(52, input);
                                throw eee;
                        }
                        cnt52++;
                    } while (true);



                    // AST REWRITE
                    // elements: e2, e1
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
                    // 228:55: -> ^( EXPR_OR $e1 ( $e2)+ )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:58: ^( EXPR_OR $e1 ( $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_OR, "EXPR_OR"), root_1);

                        adaptor.addChild(root_1, stream_e1.nextTree());
                        if ( !(stream_e2.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_e2.hasNext() ) {
                            adaptor.addChild(root_1, stream_e2.nextTree());

                        }
                        stream_e2.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:80: 
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
                    // 228:80: -> $e1
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:230:1: and_expr : e1= bitor_expr ( ( ( 'and' | '&&' ) e2= bitor_expr )+ -> ^( EXPR_AND $e1 ( $e2)+ ) | -> $e1) ;
    public final RVCCalParser.and_expr_return and_expr() throws RecognitionException {
        RVCCalParser.and_expr_return retval = new RVCCalParser.and_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal126=null;
        Token string_literal127=null;
        RVCCalParser.bitor_expr_return e1 = null;

        RVCCalParser.bitor_expr_return e2 = null;


        Object string_literal126_tree=null;
        Object string_literal127_tree=null;
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleTokenStream stream_88=new RewriteRuleTokenStream(adaptor,"token 88");
        RewriteRuleSubtreeStream stream_bitor_expr=new RewriteRuleSubtreeStream(adaptor,"rule bitor_expr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:230:9: (e1= bitor_expr ( ( ( 'and' | '&&' ) e2= bitor_expr )+ -> ^( EXPR_AND $e1 ( $e2)+ ) | -> $e1) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:230:11: e1= bitor_expr ( ( ( 'and' | '&&' ) e2= bitor_expr )+ -> ^( EXPR_AND $e1 ( $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_bitor_expr_in_and_expr1430);
            e1=bitor_expr();

            state._fsp--;

            stream_bitor_expr.add(e1.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:230:25: ( ( ( 'and' | '&&' ) e2= bitor_expr )+ -> ^( EXPR_AND $e1 ( $e2)+ ) | -> $e1)
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( ((LA56_0>=87 && LA56_0<=88)) ) {
                alt56=1;
            }
            else if ( ((LA56_0>=60 && LA56_0<=61)||(LA56_0>=63 && LA56_0<=64)||LA56_0==66||(LA56_0>=69 && LA56_0<=71)||LA56_0==74||LA56_0==78||LA56_0==82||(LA56_0>=85 && LA56_0<=86)||(LA56_0>=108 && LA56_0<=109)||LA56_0==118) ) {
                alt56=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 56, 0, input);

                throw nvae;
            }
            switch (alt56) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:230:26: ( ( 'and' | '&&' ) e2= bitor_expr )+
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:230:26: ( ( 'and' | '&&' ) e2= bitor_expr )+
                    int cnt55=0;
                    loop55:
                    do {
                        int alt55=2;
                        int LA55_0 = input.LA(1);

                        if ( ((LA55_0>=87 && LA55_0<=88)) ) {
                            alt55=1;
                        }


                        switch (alt55) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:230:27: ( 'and' | '&&' ) e2= bitor_expr
                    	    {
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:230:27: ( 'and' | '&&' )
                    	    int alt54=2;
                    	    int LA54_0 = input.LA(1);

                    	    if ( (LA54_0==87) ) {
                    	        alt54=1;
                    	    }
                    	    else if ( (LA54_0==88) ) {
                    	        alt54=2;
                    	    }
                    	    else {
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 54, 0, input);

                    	        throw nvae;
                    	    }
                    	    switch (alt54) {
                    	        case 1 :
                    	            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:230:28: 'and'
                    	            {
                    	            string_literal126=(Token)match(input,87,FOLLOW_87_in_and_expr1435);  
                    	            stream_87.add(string_literal126);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:230:36: '&&'
                    	            {
                    	            string_literal127=(Token)match(input,88,FOLLOW_88_in_and_expr1439);  
                    	            stream_88.add(string_literal127);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_bitor_expr_in_and_expr1444);
                    	    e2=bitor_expr();

                    	    state._fsp--;

                    	    stream_bitor_expr.add(e2.getTree());

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
                    // elements: e2, e1
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
                    // 230:58: -> ^( EXPR_AND $e1 ( $e2)+ )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:230:61: ^( EXPR_AND $e1 ( $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_AND, "EXPR_AND"), root_1);

                        adaptor.addChild(root_1, stream_e1.nextTree());
                        if ( !(stream_e2.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_e2.hasNext() ) {
                            adaptor.addChild(root_1, stream_e2.nextTree());

                        }
                        stream_e2.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:230:84: 
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
                    // 230:84: -> $e1
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:1: bitor_expr : e1= bitand_expr ( ( '|' e2= bitand_expr )+ -> ^( EXPR_BITOR $e1 ( $e2)+ ) | -> $e1) ;
    public final RVCCalParser.bitor_expr_return bitor_expr() throws RecognitionException {
        RVCCalParser.bitor_expr_return retval = new RVCCalParser.bitor_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal128=null;
        RVCCalParser.bitand_expr_return e1 = null;

        RVCCalParser.bitand_expr_return e2 = null;


        Object char_literal128_tree=null;
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleSubtreeStream stream_bitand_expr=new RewriteRuleSubtreeStream(adaptor,"rule bitand_expr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:11: (e1= bitand_expr ( ( '|' e2= bitand_expr )+ -> ^( EXPR_BITOR $e1 ( $e2)+ ) | -> $e1) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:13: e1= bitand_expr ( ( '|' e2= bitand_expr )+ -> ^( EXPR_BITOR $e1 ( $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_bitand_expr_in_bitor_expr1477);
            e1=bitand_expr();

            state._fsp--;

            stream_bitand_expr.add(e1.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:28: ( ( '|' e2= bitand_expr )+ -> ^( EXPR_BITOR $e1 ( $e2)+ ) | -> $e1)
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==89) ) {
                alt58=1;
            }
            else if ( ((LA58_0>=60 && LA58_0<=61)||(LA58_0>=63 && LA58_0<=64)||LA58_0==66||(LA58_0>=69 && LA58_0<=71)||LA58_0==74||LA58_0==78||LA58_0==82||(LA58_0>=85 && LA58_0<=88)||(LA58_0>=108 && LA58_0<=109)||LA58_0==118) ) {
                alt58=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 58, 0, input);

                throw nvae;
            }
            switch (alt58) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:29: ( '|' e2= bitand_expr )+
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:29: ( '|' e2= bitand_expr )+
                    int cnt57=0;
                    loop57:
                    do {
                        int alt57=2;
                        int LA57_0 = input.LA(1);

                        if ( (LA57_0==89) ) {
                            alt57=1;
                        }


                        switch (alt57) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:30: '|' e2= bitand_expr
                    	    {
                    	    char_literal128=(Token)match(input,89,FOLLOW_89_in_bitor_expr1481);  
                    	    stream_89.add(char_literal128);

                    	    pushFollow(FOLLOW_bitand_expr_in_bitor_expr1485);
                    	    e2=bitand_expr();

                    	    state._fsp--;

                    	    stream_bitand_expr.add(e2.getTree());

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
                    // elements: e2, e1
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
                    // 232:51: -> ^( EXPR_BITOR $e1 ( $e2)+ )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:54: ^( EXPR_BITOR $e1 ( $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BITOR, "EXPR_BITOR"), root_1);

                        adaptor.addChild(root_1, stream_e1.nextTree());
                        if ( !(stream_e2.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_e2.hasNext() ) {
                            adaptor.addChild(root_1, stream_e2.nextTree());

                        }
                        stream_e2.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:79: 
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
                    // 232:79: -> $e1
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:234:1: bitand_expr : e1= eq_expr ( ( '&' e2= eq_expr )+ -> ^( EXPR_BITAND $e1 ( $e2)+ ) | -> $e1) ;
    public final RVCCalParser.bitand_expr_return bitand_expr() throws RecognitionException {
        RVCCalParser.bitand_expr_return retval = new RVCCalParser.bitand_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal129=null;
        RVCCalParser.eq_expr_return e1 = null;

        RVCCalParser.eq_expr_return e2 = null;


        Object char_literal129_tree=null;
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleSubtreeStream stream_eq_expr=new RewriteRuleSubtreeStream(adaptor,"rule eq_expr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:234:12: (e1= eq_expr ( ( '&' e2= eq_expr )+ -> ^( EXPR_BITAND $e1 ( $e2)+ ) | -> $e1) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:234:14: e1= eq_expr ( ( '&' e2= eq_expr )+ -> ^( EXPR_BITAND $e1 ( $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_eq_expr_in_bitand_expr1518);
            e1=eq_expr();

            state._fsp--;

            stream_eq_expr.add(e1.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:234:25: ( ( '&' e2= eq_expr )+ -> ^( EXPR_BITAND $e1 ( $e2)+ ) | -> $e1)
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==90) ) {
                alt60=1;
            }
            else if ( ((LA60_0>=60 && LA60_0<=61)||(LA60_0>=63 && LA60_0<=64)||LA60_0==66||(LA60_0>=69 && LA60_0<=71)||LA60_0==74||LA60_0==78||LA60_0==82||(LA60_0>=85 && LA60_0<=89)||(LA60_0>=108 && LA60_0<=109)||LA60_0==118) ) {
                alt60=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;
            }
            switch (alt60) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:234:26: ( '&' e2= eq_expr )+
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:234:26: ( '&' e2= eq_expr )+
                    int cnt59=0;
                    loop59:
                    do {
                        int alt59=2;
                        int LA59_0 = input.LA(1);

                        if ( (LA59_0==90) ) {
                            alt59=1;
                        }


                        switch (alt59) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:234:27: '&' e2= eq_expr
                    	    {
                    	    char_literal129=(Token)match(input,90,FOLLOW_90_in_bitand_expr1522);  
                    	    stream_90.add(char_literal129);

                    	    pushFollow(FOLLOW_eq_expr_in_bitand_expr1526);
                    	    e2=eq_expr();

                    	    state._fsp--;

                    	    stream_eq_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt59 >= 1 ) break loop59;
                                EarlyExitException eee =
                                    new EarlyExitException(59, input);
                                throw eee;
                        }
                        cnt59++;
                    } while (true);



                    // AST REWRITE
                    // elements: e2, e1
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
                    // 234:44: -> ^( EXPR_BITAND $e1 ( $e2)+ )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:234:47: ^( EXPR_BITAND $e1 ( $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BITAND, "EXPR_BITAND"), root_1);

                        adaptor.addChild(root_1, stream_e1.nextTree());
                        if ( !(stream_e2.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_e2.hasNext() ) {
                            adaptor.addChild(root_1, stream_e2.nextTree());

                        }
                        stream_e2.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:234:73: 
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
                    // 234:73: -> $e1
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:1: eq_expr : e1= rel_expr ( ( (op= '=' | op= '!=' ) e2= rel_expr )+ -> ^( EXPR_EQ $e1 ( $op $e2)+ ) | -> $e1) ;
    public final RVCCalParser.eq_expr_return eq_expr() throws RecognitionException {
        RVCCalParser.eq_expr_return retval = new RVCCalParser.eq_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        RVCCalParser.rel_expr_return e1 = null;

        RVCCalParser.rel_expr_return e2 = null;


        Object op_tree=null;
        RewriteRuleTokenStream stream_91=new RewriteRuleTokenStream(adaptor,"token 91");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleSubtreeStream stream_rel_expr=new RewriteRuleSubtreeStream(adaptor,"rule rel_expr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:8: (e1= rel_expr ( ( (op= '=' | op= '!=' ) e2= rel_expr )+ -> ^( EXPR_EQ $e1 ( $op $e2)+ ) | -> $e1) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:10: e1= rel_expr ( ( (op= '=' | op= '!=' ) e2= rel_expr )+ -> ^( EXPR_EQ $e1 ( $op $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_rel_expr_in_eq_expr1559);
            e1=rel_expr();

            state._fsp--;

            stream_rel_expr.add(e1.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:22: ( ( (op= '=' | op= '!=' ) e2= rel_expr )+ -> ^( EXPR_EQ $e1 ( $op $e2)+ ) | -> $e1)
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==76||LA63_0==91) ) {
                alt63=1;
            }
            else if ( ((LA63_0>=60 && LA63_0<=61)||(LA63_0>=63 && LA63_0<=64)||LA63_0==66||(LA63_0>=69 && LA63_0<=71)||LA63_0==74||LA63_0==78||LA63_0==82||(LA63_0>=85 && LA63_0<=90)||(LA63_0>=108 && LA63_0<=109)||LA63_0==118) ) {
                alt63=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }
            switch (alt63) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:23: ( (op= '=' | op= '!=' ) e2= rel_expr )+
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:23: ( (op= '=' | op= '!=' ) e2= rel_expr )+
                    int cnt62=0;
                    loop62:
                    do {
                        int alt62=2;
                        int LA62_0 = input.LA(1);

                        if ( (LA62_0==76||LA62_0==91) ) {
                            alt62=1;
                        }


                        switch (alt62) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:24: (op= '=' | op= '!=' ) e2= rel_expr
                    	    {
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:24: (op= '=' | op= '!=' )
                    	    int alt61=2;
                    	    int LA61_0 = input.LA(1);

                    	    if ( (LA61_0==76) ) {
                    	        alt61=1;
                    	    }
                    	    else if ( (LA61_0==91) ) {
                    	        alt61=2;
                    	    }
                    	    else {
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 61, 0, input);

                    	        throw nvae;
                    	    }
                    	    switch (alt61) {
                    	        case 1 :
                    	            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:25: op= '='
                    	            {
                    	            op=(Token)match(input,76,FOLLOW_76_in_eq_expr1566);  
                    	            stream_76.add(op);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:34: op= '!='
                    	            {
                    	            op=(Token)match(input,91,FOLLOW_91_in_eq_expr1572);  
                    	            stream_91.add(op);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_rel_expr_in_eq_expr1577);
                    	    e2=rel_expr();

                    	    state._fsp--;

                    	    stream_rel_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt62 >= 1 ) break loop62;
                                EarlyExitException eee =
                                    new EarlyExitException(62, input);
                                throw eee;
                        }
                        cnt62++;
                    } while (true);



                    // AST REWRITE
                    // elements: e2, e1, op
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
                    // 236:57: -> ^( EXPR_EQ $e1 ( $op $e2)+ )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:60: ^( EXPR_EQ $e1 ( $op $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_EQ, "EXPR_EQ"), root_1);

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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:236:88: 
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
                    // 236:88: -> $e1
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:1: rel_expr : e1= shift_expr ( ( (op= '<' | op= '>' | op= '<=' | op= '>=' ) e2= shift_expr )+ -> ^( EXPR_REL $e1 ( $op $e2)+ ) | -> $e1) ;
    public final RVCCalParser.rel_expr_return rel_expr() throws RecognitionException {
        RVCCalParser.rel_expr_return retval = new RVCCalParser.rel_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        RVCCalParser.shift_expr_return e1 = null;

        RVCCalParser.shift_expr_return e2 = null;


        Object op_tree=null;
        RewriteRuleTokenStream stream_95=new RewriteRuleTokenStream(adaptor,"token 95");
        RewriteRuleTokenStream stream_94=new RewriteRuleTokenStream(adaptor,"token 94");
        RewriteRuleTokenStream stream_93=new RewriteRuleTokenStream(adaptor,"token 93");
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleSubtreeStream stream_shift_expr=new RewriteRuleSubtreeStream(adaptor,"rule shift_expr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:9: (e1= shift_expr ( ( (op= '<' | op= '>' | op= '<=' | op= '>=' ) e2= shift_expr )+ -> ^( EXPR_REL $e1 ( $op $e2)+ ) | -> $e1) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:11: e1= shift_expr ( ( (op= '<' | op= '>' | op= '<=' | op= '>=' ) e2= shift_expr )+ -> ^( EXPR_REL $e1 ( $op $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_shift_expr_in_rel_expr1615);
            e1=shift_expr();

            state._fsp--;

            stream_shift_expr.add(e1.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:25: ( ( (op= '<' | op= '>' | op= '<=' | op= '>=' ) e2= shift_expr )+ -> ^( EXPR_REL $e1 ( $op $e2)+ ) | -> $e1)
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( ((LA66_0>=92 && LA66_0<=95)) ) {
                alt66=1;
            }
            else if ( ((LA66_0>=60 && LA66_0<=61)||(LA66_0>=63 && LA66_0<=64)||LA66_0==66||(LA66_0>=69 && LA66_0<=71)||LA66_0==74||LA66_0==76||LA66_0==78||LA66_0==82||(LA66_0>=85 && LA66_0<=91)||(LA66_0>=108 && LA66_0<=109)||LA66_0==118) ) {
                alt66=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 66, 0, input);

                throw nvae;
            }
            switch (alt66) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:26: ( (op= '<' | op= '>' | op= '<=' | op= '>=' ) e2= shift_expr )+
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:26: ( (op= '<' | op= '>' | op= '<=' | op= '>=' ) e2= shift_expr )+
                    int cnt65=0;
                    loop65:
                    do {
                        int alt65=2;
                        int LA65_0 = input.LA(1);

                        if ( ((LA65_0>=92 && LA65_0<=95)) ) {
                            alt65=1;
                        }


                        switch (alt65) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:27: (op= '<' | op= '>' | op= '<=' | op= '>=' ) e2= shift_expr
                    	    {
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:27: (op= '<' | op= '>' | op= '<=' | op= '>=' )
                    	    int alt64=4;
                    	    switch ( input.LA(1) ) {
                    	    case 92:
                    	        {
                    	        alt64=1;
                    	        }
                    	        break;
                    	    case 93:
                    	        {
                    	        alt64=2;
                    	        }
                    	        break;
                    	    case 94:
                    	        {
                    	        alt64=3;
                    	        }
                    	        break;
                    	    case 95:
                    	        {
                    	        alt64=4;
                    	        }
                    	        break;
                    	    default:
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 64, 0, input);

                    	        throw nvae;
                    	    }

                    	    switch (alt64) {
                    	        case 1 :
                    	            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:28: op= '<'
                    	            {
                    	            op=(Token)match(input,92,FOLLOW_92_in_rel_expr1622);  
                    	            stream_92.add(op);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:37: op= '>'
                    	            {
                    	            op=(Token)match(input,93,FOLLOW_93_in_rel_expr1628);  
                    	            stream_93.add(op);


                    	            }
                    	            break;
                    	        case 3 :
                    	            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:46: op= '<='
                    	            {
                    	            op=(Token)match(input,94,FOLLOW_94_in_rel_expr1634);  
                    	            stream_94.add(op);


                    	            }
                    	            break;
                    	        case 4 :
                    	            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:56: op= '>='
                    	            {
                    	            op=(Token)match(input,95,FOLLOW_95_in_rel_expr1640);  
                    	            stream_95.add(op);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_shift_expr_in_rel_expr1645);
                    	    e2=shift_expr();

                    	    state._fsp--;

                    	    stream_shift_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt65 >= 1 ) break loop65;
                                EarlyExitException eee =
                                    new EarlyExitException(65, input);
                                throw eee;
                        }
                        cnt65++;
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
                    // 238:81: -> ^( EXPR_REL $e1 ( $op $e2)+ )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:84: ^( EXPR_REL $e1 ( $op $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_REL, "EXPR_REL"), root_1);

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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:113: 
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
                    // 238:113: -> $e1
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:1: shift_expr : e1= add_expr ( ( (op= '<<' | op= '>>' ) e2= add_expr )+ -> ^( EXPR_SHIFT $e1 ( $op $e2)+ ) | -> $e1) ;
    public final RVCCalParser.shift_expr_return shift_expr() throws RecognitionException {
        RVCCalParser.shift_expr_return retval = new RVCCalParser.shift_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        RVCCalParser.add_expr_return e1 = null;

        RVCCalParser.add_expr_return e2 = null;


        Object op_tree=null;
        RewriteRuleTokenStream stream_97=new RewriteRuleTokenStream(adaptor,"token 97");
        RewriteRuleTokenStream stream_96=new RewriteRuleTokenStream(adaptor,"token 96");
        RewriteRuleSubtreeStream stream_add_expr=new RewriteRuleSubtreeStream(adaptor,"rule add_expr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:11: (e1= add_expr ( ( (op= '<<' | op= '>>' ) e2= add_expr )+ -> ^( EXPR_SHIFT $e1 ( $op $e2)+ ) | -> $e1) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:13: e1= add_expr ( ( (op= '<<' | op= '>>' ) e2= add_expr )+ -> ^( EXPR_SHIFT $e1 ( $op $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_add_expr_in_shift_expr1682);
            e1=add_expr();

            state._fsp--;

            stream_add_expr.add(e1.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:25: ( ( (op= '<<' | op= '>>' ) e2= add_expr )+ -> ^( EXPR_SHIFT $e1 ( $op $e2)+ ) | -> $e1)
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( ((LA69_0>=96 && LA69_0<=97)) ) {
                alt69=1;
            }
            else if ( ((LA69_0>=60 && LA69_0<=61)||(LA69_0>=63 && LA69_0<=64)||LA69_0==66||(LA69_0>=69 && LA69_0<=71)||LA69_0==74||LA69_0==76||LA69_0==78||LA69_0==82||(LA69_0>=85 && LA69_0<=95)||(LA69_0>=108 && LA69_0<=109)||LA69_0==118) ) {
                alt69=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 69, 0, input);

                throw nvae;
            }
            switch (alt69) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:26: ( (op= '<<' | op= '>>' ) e2= add_expr )+
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:26: ( (op= '<<' | op= '>>' ) e2= add_expr )+
                    int cnt68=0;
                    loop68:
                    do {
                        int alt68=2;
                        int LA68_0 = input.LA(1);

                        if ( ((LA68_0>=96 && LA68_0<=97)) ) {
                            alt68=1;
                        }


                        switch (alt68) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:27: (op= '<<' | op= '>>' ) e2= add_expr
                    	    {
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:27: (op= '<<' | op= '>>' )
                    	    int alt67=2;
                    	    int LA67_0 = input.LA(1);

                    	    if ( (LA67_0==96) ) {
                    	        alt67=1;
                    	    }
                    	    else if ( (LA67_0==97) ) {
                    	        alt67=2;
                    	    }
                    	    else {
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 67, 0, input);

                    	        throw nvae;
                    	    }
                    	    switch (alt67) {
                    	        case 1 :
                    	            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:28: op= '<<'
                    	            {
                    	            op=(Token)match(input,96,FOLLOW_96_in_shift_expr1689);  
                    	            stream_96.add(op);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:38: op= '>>'
                    	            {
                    	            op=(Token)match(input,97,FOLLOW_97_in_shift_expr1695);  
                    	            stream_97.add(op);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_add_expr_in_shift_expr1700);
                    	    e2=add_expr();

                    	    state._fsp--;

                    	    stream_add_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt68 >= 1 ) break loop68;
                                EarlyExitException eee =
                                    new EarlyExitException(68, input);
                                throw eee;
                        }
                        cnt68++;
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
                    // 240:61: -> ^( EXPR_SHIFT $e1 ( $op $e2)+ )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:64: ^( EXPR_SHIFT $e1 ( $op $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_SHIFT, "EXPR_SHIFT"), root_1);

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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:95: 
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
                    // 240:95: -> $e1
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:1: add_expr : e1= mul_expr ( ( (op= '+' | op= '-' ) e2= mul_expr )+ -> ^( EXPR_ADD $e1 ( $op $e2)+ ) | -> $e1) ;
    public final RVCCalParser.add_expr_return add_expr() throws RecognitionException {
        RVCCalParser.add_expr_return retval = new RVCCalParser.add_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        RVCCalParser.mul_expr_return e1 = null;

        RVCCalParser.mul_expr_return e2 = null;


        Object op_tree=null;
        RewriteRuleTokenStream stream_98=new RewriteRuleTokenStream(adaptor,"token 98");
        RewriteRuleTokenStream stream_99=new RewriteRuleTokenStream(adaptor,"token 99");
        RewriteRuleSubtreeStream stream_mul_expr=new RewriteRuleSubtreeStream(adaptor,"rule mul_expr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:9: (e1= mul_expr ( ( (op= '+' | op= '-' ) e2= mul_expr )+ -> ^( EXPR_ADD $e1 ( $op $e2)+ ) | -> $e1) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:11: e1= mul_expr ( ( (op= '+' | op= '-' ) e2= mul_expr )+ -> ^( EXPR_ADD $e1 ( $op $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_mul_expr_in_add_expr1738);
            e1=mul_expr();

            state._fsp--;

            stream_mul_expr.add(e1.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:23: ( ( (op= '+' | op= '-' ) e2= mul_expr )+ -> ^( EXPR_ADD $e1 ( $op $e2)+ ) | -> $e1)
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( ((LA72_0>=98 && LA72_0<=99)) ) {
                alt72=1;
            }
            else if ( ((LA72_0>=60 && LA72_0<=61)||(LA72_0>=63 && LA72_0<=64)||LA72_0==66||(LA72_0>=69 && LA72_0<=71)||LA72_0==74||LA72_0==76||LA72_0==78||LA72_0==82||(LA72_0>=85 && LA72_0<=97)||(LA72_0>=108 && LA72_0<=109)||LA72_0==118) ) {
                alt72=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 72, 0, input);

                throw nvae;
            }
            switch (alt72) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:24: ( (op= '+' | op= '-' ) e2= mul_expr )+
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:24: ( (op= '+' | op= '-' ) e2= mul_expr )+
                    int cnt71=0;
                    loop71:
                    do {
                        int alt71=2;
                        int LA71_0 = input.LA(1);

                        if ( ((LA71_0>=98 && LA71_0<=99)) ) {
                            alt71=1;
                        }


                        switch (alt71) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:25: (op= '+' | op= '-' ) e2= mul_expr
                    	    {
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:25: (op= '+' | op= '-' )
                    	    int alt70=2;
                    	    int LA70_0 = input.LA(1);

                    	    if ( (LA70_0==98) ) {
                    	        alt70=1;
                    	    }
                    	    else if ( (LA70_0==99) ) {
                    	        alt70=2;
                    	    }
                    	    else {
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 70, 0, input);

                    	        throw nvae;
                    	    }
                    	    switch (alt70) {
                    	        case 1 :
                    	            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:26: op= '+'
                    	            {
                    	            op=(Token)match(input,98,FOLLOW_98_in_add_expr1745);  
                    	            stream_98.add(op);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:35: op= '-'
                    	            {
                    	            op=(Token)match(input,99,FOLLOW_99_in_add_expr1751);  
                    	            stream_99.add(op);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_mul_expr_in_add_expr1756);
                    	    e2=mul_expr();

                    	    state._fsp--;

                    	    stream_mul_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt71 >= 1 ) break loop71;
                                EarlyExitException eee =
                                    new EarlyExitException(71, input);
                                throw eee;
                        }
                        cnt71++;
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
                    // 242:57: -> ^( EXPR_ADD $e1 ( $op $e2)+ )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:60: ^( EXPR_ADD $e1 ( $op $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_ADD, "EXPR_ADD"), root_1);

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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:89: 
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
                    // 242:89: -> $e1
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:1: mul_expr : e1= exp_expr ( ( (op= 'div' | op= 'mod' | op= '*' | op= '/' ) e2= exp_expr )+ -> ^( EXPR_MUL $e1 ( $op $e2)+ ) | -> $e1) ;
    public final RVCCalParser.mul_expr_return mul_expr() throws RecognitionException {
        RVCCalParser.mul_expr_return retval = new RVCCalParser.mul_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        RVCCalParser.exp_expr_return e1 = null;

        RVCCalParser.exp_expr_return e2 = null;


        Object op_tree=null;
        RewriteRuleTokenStream stream_103=new RewriteRuleTokenStream(adaptor,"token 103");
        RewriteRuleTokenStream stream_102=new RewriteRuleTokenStream(adaptor,"token 102");
        RewriteRuleTokenStream stream_101=new RewriteRuleTokenStream(adaptor,"token 101");
        RewriteRuleTokenStream stream_100=new RewriteRuleTokenStream(adaptor,"token 100");
        RewriteRuleSubtreeStream stream_exp_expr=new RewriteRuleSubtreeStream(adaptor,"rule exp_expr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:9: (e1= exp_expr ( ( (op= 'div' | op= 'mod' | op= '*' | op= '/' ) e2= exp_expr )+ -> ^( EXPR_MUL $e1 ( $op $e2)+ ) | -> $e1) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:11: e1= exp_expr ( ( (op= 'div' | op= 'mod' | op= '*' | op= '/' ) e2= exp_expr )+ -> ^( EXPR_MUL $e1 ( $op $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_exp_expr_in_mul_expr1794);
            e1=exp_expr();

            state._fsp--;

            stream_exp_expr.add(e1.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:23: ( ( (op= 'div' | op= 'mod' | op= '*' | op= '/' ) e2= exp_expr )+ -> ^( EXPR_MUL $e1 ( $op $e2)+ ) | -> $e1)
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( ((LA75_0>=100 && LA75_0<=103)) ) {
                alt75=1;
            }
            else if ( ((LA75_0>=60 && LA75_0<=61)||(LA75_0>=63 && LA75_0<=64)||LA75_0==66||(LA75_0>=69 && LA75_0<=71)||LA75_0==74||LA75_0==76||LA75_0==78||LA75_0==82||(LA75_0>=85 && LA75_0<=99)||(LA75_0>=108 && LA75_0<=109)||LA75_0==118) ) {
                alt75=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 75, 0, input);

                throw nvae;
            }
            switch (alt75) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:24: ( (op= 'div' | op= 'mod' | op= '*' | op= '/' ) e2= exp_expr )+
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:24: ( (op= 'div' | op= 'mod' | op= '*' | op= '/' ) e2= exp_expr )+
                    int cnt74=0;
                    loop74:
                    do {
                        int alt74=2;
                        int LA74_0 = input.LA(1);

                        if ( ((LA74_0>=100 && LA74_0<=103)) ) {
                            alt74=1;
                        }


                        switch (alt74) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:25: (op= 'div' | op= 'mod' | op= '*' | op= '/' ) e2= exp_expr
                    	    {
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:25: (op= 'div' | op= 'mod' | op= '*' | op= '/' )
                    	    int alt73=4;
                    	    switch ( input.LA(1) ) {
                    	    case 100:
                    	        {
                    	        alt73=1;
                    	        }
                    	        break;
                    	    case 101:
                    	        {
                    	        alt73=2;
                    	        }
                    	        break;
                    	    case 102:
                    	        {
                    	        alt73=3;
                    	        }
                    	        break;
                    	    case 103:
                    	        {
                    	        alt73=4;
                    	        }
                    	        break;
                    	    default:
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 73, 0, input);

                    	        throw nvae;
                    	    }

                    	    switch (alt73) {
                    	        case 1 :
                    	            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:26: op= 'div'
                    	            {
                    	            op=(Token)match(input,100,FOLLOW_100_in_mul_expr1801);  
                    	            stream_100.add(op);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:37: op= 'mod'
                    	            {
                    	            op=(Token)match(input,101,FOLLOW_101_in_mul_expr1807);  
                    	            stream_101.add(op);


                    	            }
                    	            break;
                    	        case 3 :
                    	            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:48: op= '*'
                    	            {
                    	            op=(Token)match(input,102,FOLLOW_102_in_mul_expr1813);  
                    	            stream_102.add(op);


                    	            }
                    	            break;
                    	        case 4 :
                    	            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:57: op= '/'
                    	            {
                    	            op=(Token)match(input,103,FOLLOW_103_in_mul_expr1819);  
                    	            stream_103.add(op);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_exp_expr_in_mul_expr1824);
                    	    e2=exp_expr();

                    	    state._fsp--;

                    	    stream_exp_expr.add(e2.getTree());

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
                    // 244:79: -> ^( EXPR_MUL $e1 ( $op $e2)+ )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:82: ^( EXPR_MUL $e1 ( $op $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_MUL, "EXPR_MUL"), root_1);

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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:111: 
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
                    // 244:111: -> $e1
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:246:1: exp_expr : e1= un_expr ( ( '^' e2= un_expr )+ -> ^( EXPR_EXP $e1 ( $e2)+ ) | -> $e1) ;
    public final RVCCalParser.exp_expr_return exp_expr() throws RecognitionException {
        RVCCalParser.exp_expr_return retval = new RVCCalParser.exp_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal130=null;
        RVCCalParser.un_expr_return e1 = null;

        RVCCalParser.un_expr_return e2 = null;


        Object char_literal130_tree=null;
        RewriteRuleTokenStream stream_104=new RewriteRuleTokenStream(adaptor,"token 104");
        RewriteRuleSubtreeStream stream_un_expr=new RewriteRuleSubtreeStream(adaptor,"rule un_expr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:246:9: (e1= un_expr ( ( '^' e2= un_expr )+ -> ^( EXPR_EXP $e1 ( $e2)+ ) | -> $e1) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:246:11: e1= un_expr ( ( '^' e2= un_expr )+ -> ^( EXPR_EXP $e1 ( $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_un_expr_in_exp_expr1862);
            e1=un_expr();

            state._fsp--;

            stream_un_expr.add(e1.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:246:22: ( ( '^' e2= un_expr )+ -> ^( EXPR_EXP $e1 ( $e2)+ ) | -> $e1)
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==104) ) {
                alt77=1;
            }
            else if ( ((LA77_0>=60 && LA77_0<=61)||(LA77_0>=63 && LA77_0<=64)||LA77_0==66||(LA77_0>=69 && LA77_0<=71)||LA77_0==74||LA77_0==76||LA77_0==78||LA77_0==82||(LA77_0>=85 && LA77_0<=103)||(LA77_0>=108 && LA77_0<=109)||LA77_0==118) ) {
                alt77=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 77, 0, input);

                throw nvae;
            }
            switch (alt77) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:246:23: ( '^' e2= un_expr )+
                    {
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:246:23: ( '^' e2= un_expr )+
                    int cnt76=0;
                    loop76:
                    do {
                        int alt76=2;
                        int LA76_0 = input.LA(1);

                        if ( (LA76_0==104) ) {
                            alt76=1;
                        }


                        switch (alt76) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:246:24: '^' e2= un_expr
                    	    {
                    	    char_literal130=(Token)match(input,104,FOLLOW_104_in_exp_expr1866);  
                    	    stream_104.add(char_literal130);

                    	    pushFollow(FOLLOW_un_expr_in_exp_expr1870);
                    	    e2=un_expr();

                    	    state._fsp--;

                    	    stream_un_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt76 >= 1 ) break loop76;
                                EarlyExitException eee =
                                    new EarlyExitException(76, input);
                                throw eee;
                        }
                        cnt76++;
                    } while (true);



                    // AST REWRITE
                    // elements: e1, e2
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
                    // 246:41: -> ^( EXPR_EXP $e1 ( $e2)+ )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:246:44: ^( EXPR_EXP $e1 ( $e2)+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_EXP, "EXPR_EXP"), root_1);

                        adaptor.addChild(root_1, stream_e1.nextTree());
                        if ( !(stream_e2.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_e2.hasNext() ) {
                            adaptor.addChild(root_1, stream_e2.nextTree());

                        }
                        stream_e2.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:246:67: 
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
                    // 246:67: -> $e1
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:248:1: un_expr : ( postfix_expression -> postfix_expression | '-' un_expr -> ^( EXPR_UN '-' un_expr ) | 'not' un_expr -> ^( EXPR_UN 'not' un_expr ) | '#' un_expr -> ^( EXPR_UN '#' un_expr ) );
    public final RVCCalParser.un_expr_return un_expr() throws RecognitionException {
        RVCCalParser.un_expr_return retval = new RVCCalParser.un_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal132=null;
        Token string_literal134=null;
        Token char_literal136=null;
        RVCCalParser.postfix_expression_return postfix_expression131 = null;

        RVCCalParser.un_expr_return un_expr133 = null;

        RVCCalParser.un_expr_return un_expr135 = null;

        RVCCalParser.un_expr_return un_expr137 = null;


        Object char_literal132_tree=null;
        Object string_literal134_tree=null;
        Object char_literal136_tree=null;
        RewriteRuleTokenStream stream_106=new RewriteRuleTokenStream(adaptor,"token 106");
        RewriteRuleTokenStream stream_105=new RewriteRuleTokenStream(adaptor,"token 105");
        RewriteRuleTokenStream stream_99=new RewriteRuleTokenStream(adaptor,"token 99");
        RewriteRuleSubtreeStream stream_postfix_expression=new RewriteRuleSubtreeStream(adaptor,"rule postfix_expression");
        RewriteRuleSubtreeStream stream_un_expr=new RewriteRuleSubtreeStream(adaptor,"rule un_expr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:248:8: ( postfix_expression -> postfix_expression | '-' un_expr -> ^( EXPR_UN '-' un_expr ) | 'not' un_expr -> ^( EXPR_UN 'not' un_expr ) | '#' un_expr -> ^( EXPR_UN '#' un_expr ) )
            int alt78=4;
            switch ( input.LA(1) ) {
            case ID:
            case FLOAT:
            case INTEGER:
            case STRING:
            case 62:
            case 68:
            case 107:
            case 110:
            case 111:
                {
                alt78=1;
                }
                break;
            case 99:
                {
                alt78=2;
                }
                break;
            case 105:
                {
                alt78=3;
                }
                break;
            case 106:
                {
                alt78=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;
            }

            switch (alt78) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:248:10: postfix_expression
                    {
                    pushFollow(FOLLOW_postfix_expression_in_un_expr1901);
                    postfix_expression131=postfix_expression();

                    state._fsp--;

                    stream_postfix_expression.add(postfix_expression131.getTree());


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
                    // 248:29: -> postfix_expression
                    {
                        adaptor.addChild(root_0, stream_postfix_expression.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:249:4: '-' un_expr
                    {
                    char_literal132=(Token)match(input,99,FOLLOW_99_in_un_expr1910);  
                    stream_99.add(char_literal132);

                    pushFollow(FOLLOW_un_expr_in_un_expr1912);
                    un_expr133=un_expr();

                    state._fsp--;

                    stream_un_expr.add(un_expr133.getTree());


                    // AST REWRITE
                    // elements: 99, un_expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 249:16: -> ^( EXPR_UN '-' un_expr )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:249:19: ^( EXPR_UN '-' un_expr )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_UN, "EXPR_UN"), root_1);

                        adaptor.addChild(root_1, stream_99.nextNode());
                        adaptor.addChild(root_1, stream_un_expr.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:250:4: 'not' un_expr
                    {
                    string_literal134=(Token)match(input,105,FOLLOW_105_in_un_expr1927);  
                    stream_105.add(string_literal134);

                    pushFollow(FOLLOW_un_expr_in_un_expr1929);
                    un_expr135=un_expr();

                    state._fsp--;

                    stream_un_expr.add(un_expr135.getTree());


                    // AST REWRITE
                    // elements: un_expr, 105
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 250:18: -> ^( EXPR_UN 'not' un_expr )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:250:21: ^( EXPR_UN 'not' un_expr )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_UN, "EXPR_UN"), root_1);

                        adaptor.addChild(root_1, stream_105.nextNode());
                        adaptor.addChild(root_1, stream_un_expr.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:4: '#' un_expr
                    {
                    char_literal136=(Token)match(input,106,FOLLOW_106_in_un_expr1944);  
                    stream_106.add(char_literal136);

                    pushFollow(FOLLOW_un_expr_in_un_expr1946);
                    un_expr137=un_expr();

                    state._fsp--;

                    stream_un_expr.add(un_expr137.getTree());


                    // AST REWRITE
                    // elements: un_expr, 106
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 251:17: -> ^( EXPR_UN '#' un_expr )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:20: ^( EXPR_UN '#' un_expr )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_UN, "EXPR_UN"), root_1);

                        adaptor.addChild(root_1, stream_106.nextNode());
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:253:1: postfix_expression : ( '[' e= expressions ( ':' g= expressionGenerators )? ']' -> ^( EXPR_LIST $e ( $g)? ) | 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end' -> ^( EXPR_IF $e1 $e2 $e3) | constant -> constant | '(' expression ')' -> expression | var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) ) );
    public final RVCCalParser.postfix_expression_return postfix_expression() throws RecognitionException {
        RVCCalParser.postfix_expression_return retval = new RVCCalParser.postfix_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token var=null;
        Token char_literal138=null;
        Token char_literal139=null;
        Token char_literal140=null;
        Token string_literal141=null;
        Token string_literal142=null;
        Token string_literal143=null;
        Token string_literal144=null;
        Token char_literal146=null;
        Token char_literal148=null;
        Token char_literal149=null;
        Token char_literal151=null;
        Token char_literal152=null;
        Token char_literal154=null;
        RVCCalParser.expressions_return e = null;

        RVCCalParser.expressionGenerators_return g = null;

        RVCCalParser.expression_return e1 = null;

        RVCCalParser.expression_return e2 = null;

        RVCCalParser.expression_return e3 = null;

        RVCCalParser.constant_return constant145 = null;

        RVCCalParser.expression_return expression147 = null;

        RVCCalParser.expressions_return expressions150 = null;

        RVCCalParser.expressions_return expressions153 = null;


        Object var_tree=null;
        Object char_literal138_tree=null;
        Object char_literal139_tree=null;
        Object char_literal140_tree=null;
        Object string_literal141_tree=null;
        Object string_literal142_tree=null;
        Object string_literal143_tree=null;
        Object string_literal144_tree=null;
        Object char_literal146_tree=null;
        Object char_literal148_tree=null;
        Object char_literal149_tree=null;
        Object char_literal151_tree=null;
        Object char_literal152_tree=null;
        Object char_literal154_tree=null;
        RewriteRuleTokenStream stream_69=new RewriteRuleTokenStream(adaptor,"token 69");
        RewriteRuleTokenStream stream_68=new RewriteRuleTokenStream(adaptor,"token 68");
        RewriteRuleTokenStream stream_109=new RewriteRuleTokenStream(adaptor,"token 109");
        RewriteRuleTokenStream stream_108=new RewriteRuleTokenStream(adaptor,"token 108");
        RewriteRuleTokenStream stream_107=new RewriteRuleTokenStream(adaptor,"token 107");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");
        RewriteRuleTokenStream stream_62=new RewriteRuleTokenStream(adaptor,"token 62");
        RewriteRuleTokenStream stream_63=new RewriteRuleTokenStream(adaptor,"token 63");
        RewriteRuleTokenStream stream_61=new RewriteRuleTokenStream(adaptor,"token 61");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_constant=new RewriteRuleSubtreeStream(adaptor,"rule constant");
        RewriteRuleSubtreeStream stream_expressionGenerators=new RewriteRuleSubtreeStream(adaptor,"rule expressionGenerators");
        RewriteRuleSubtreeStream stream_expressions=new RewriteRuleSubtreeStream(adaptor,"rule expressions");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:253:19: ( '[' e= expressions ( ':' g= expressionGenerators )? ']' -> ^( EXPR_LIST $e ( $g)? ) | 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end' -> ^( EXPR_IF $e1 $e2 $e3) | constant -> constant | '(' expression ')' -> expression | var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) ) )
            int alt83=5;
            switch ( input.LA(1) ) {
            case 62:
                {
                alt83=1;
                }
                break;
            case 107:
                {
                alt83=2;
                }
                break;
            case FLOAT:
            case INTEGER:
            case STRING:
            case 110:
            case 111:
                {
                alt83=3;
                }
                break;
            case 68:
                {
                alt83=4;
                }
                break;
            case ID:
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:3: '[' e= expressions ( ':' g= expressionGenerators )? ']'
                    {
                    char_literal138=(Token)match(input,62,FOLLOW_62_in_postfix_expression1966);  
                    stream_62.add(char_literal138);

                    pushFollow(FOLLOW_expressions_in_postfix_expression1970);
                    e=expressions();

                    state._fsp--;

                    stream_expressions.add(e.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:21: ( ':' g= expressionGenerators )?
                    int alt79=2;
                    int LA79_0 = input.LA(1);

                    if ( (LA79_0==61) ) {
                        alt79=1;
                    }
                    switch (alt79) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:22: ':' g= expressionGenerators
                            {
                            char_literal139=(Token)match(input,61,FOLLOW_61_in_postfix_expression1973);  
                            stream_61.add(char_literal139);

                            pushFollow(FOLLOW_expressionGenerators_in_postfix_expression1977);
                            g=expressionGenerators();

                            state._fsp--;

                            stream_expressionGenerators.add(g.getTree());

                            }
                            break;

                    }

                    char_literal140=(Token)match(input,63,FOLLOW_63_in_postfix_expression1981);  
                    stream_63.add(char_literal140);



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
                    // 254:55: -> ^( EXPR_LIST $e ( $g)? )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:58: ^( EXPR_LIST $e ( $g)? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_LIST, "EXPR_LIST"), root_1);

                        adaptor.addChild(root_1, stream_e.nextTree());
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:73: ( $g)?
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:255:3: 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end'
                    {
                    string_literal141=(Token)match(input,107,FOLLOW_107_in_postfix_expression1998);  
                    stream_107.add(string_literal141);

                    pushFollow(FOLLOW_expression_in_postfix_expression2002);
                    e1=expression();

                    state._fsp--;

                    stream_expression.add(e1.getTree());
                    string_literal142=(Token)match(input,108,FOLLOW_108_in_postfix_expression2004);  
                    stream_108.add(string_literal142);

                    pushFollow(FOLLOW_expression_in_postfix_expression2008);
                    e2=expression();

                    state._fsp--;

                    stream_expression.add(e2.getTree());
                    string_literal143=(Token)match(input,109,FOLLOW_109_in_postfix_expression2010);  
                    stream_109.add(string_literal143);

                    pushFollow(FOLLOW_expression_in_postfix_expression2014);
                    e3=expression();

                    state._fsp--;

                    stream_expression.add(e3.getTree());
                    string_literal144=(Token)match(input,71,FOLLOW_71_in_postfix_expression2016);  
                    stream_71.add(string_literal144);



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
                    // 255:70: -> ^( EXPR_IF $e1 $e2 $e3)
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:255:73: ^( EXPR_IF $e1 $e2 $e3)
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:3: constant
                    {
                    pushFollow(FOLLOW_constant_in_postfix_expression2035);
                    constant145=constant();

                    state._fsp--;

                    stream_constant.add(constant145.getTree());


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
                    // 256:12: -> constant
                    {
                        adaptor.addChild(root_0, stream_constant.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:257:3: '(' expression ')'
                    {
                    char_literal146=(Token)match(input,68,FOLLOW_68_in_postfix_expression2043);  
                    stream_68.add(char_literal146);

                    pushFollow(FOLLOW_expression_in_postfix_expression2045);
                    expression147=expression();

                    state._fsp--;

                    stream_expression.add(expression147.getTree());
                    char_literal148=(Token)match(input,69,FOLLOW_69_in_postfix_expression2047);  
                    stream_69.add(char_literal148);



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
                    // 257:22: -> expression
                    {
                        adaptor.addChild(root_0, stream_expression.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:258:3: var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) )
                    {
                    var=(Token)match(input,ID,FOLLOW_ID_in_postfix_expression2057);  
                    stream_ID.add(var);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:258:10: ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) )
                    int alt82=3;
                    switch ( input.LA(1) ) {
                    case 68:
                        {
                        alt82=1;
                        }
                        break;
                    case 62:
                        {
                        alt82=2;
                        }
                        break;
                    case 60:
                    case 61:
                    case 63:
                    case 64:
                    case 66:
                    case 69:
                    case 70:
                    case 71:
                    case 74:
                    case 76:
                    case 78:
                    case 82:
                    case 85:
                    case 86:
                    case 87:
                    case 88:
                    case 89:
                    case 90:
                    case 91:
                    case 92:
                    case 93:
                    case 94:
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
                    case 108:
                    case 109:
                    case 118:
                        {
                        alt82=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 82, 0, input);

                        throw nvae;
                    }

                    switch (alt82) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:5: '(' ( expressions )? ')'
                            {
                            char_literal149=(Token)match(input,68,FOLLOW_68_in_postfix_expression2065);  
                            stream_68.add(char_literal149);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:9: ( expressions )?
                            int alt80=2;
                            int LA80_0 = input.LA(1);

                            if ( ((LA80_0>=ID && LA80_0<=STRING)||LA80_0==62||LA80_0==68||LA80_0==99||(LA80_0>=105 && LA80_0<=107)||(LA80_0>=110 && LA80_0<=111)) ) {
                                alt80=1;
                            }
                            switch (alt80) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:9: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_postfix_expression2067);
                                    expressions150=expressions();

                                    state._fsp--;

                                    stream_expressions.add(expressions150.getTree());

                                    }
                                    break;

                            }

                            char_literal151=(Token)match(input,69,FOLLOW_69_in_postfix_expression2070);  
                            stream_69.add(char_literal151);



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
                            // 259:26: -> ^( EXPR_CALL $var ( expressions )? )
                            {
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:29: ^( EXPR_CALL $var ( expressions )? )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_CALL, "EXPR_CALL"), root_1);

                                adaptor.addChild(root_1, stream_var.nextNode());
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:46: ( expressions )?
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
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:260:6: ( '[' expressions ']' )+
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:260:6: ( '[' expressions ']' )+
                            int cnt81=0;
                            loop81:
                            do {
                                int alt81=2;
                                int LA81_0 = input.LA(1);

                                if ( (LA81_0==62) ) {
                                    alt81=1;
                                }


                                switch (alt81) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:260:7: '[' expressions ']'
                            	    {
                            	    char_literal152=(Token)match(input,62,FOLLOW_62_in_postfix_expression2090);  
                            	    stream_62.add(char_literal152);

                            	    pushFollow(FOLLOW_expressions_in_postfix_expression2092);
                            	    expressions153=expressions();

                            	    state._fsp--;

                            	    stream_expressions.add(expressions153.getTree());
                            	    char_literal154=(Token)match(input,63,FOLLOW_63_in_postfix_expression2094);  
                            	    stream_63.add(char_literal154);


                            	    }
                            	    break;

                            	default :
                            	    if ( cnt81 >= 1 ) break loop81;
                                        EarlyExitException eee =
                                            new EarlyExitException(81, input);
                                        throw eee;
                                }
                                cnt81++;
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
                            // 260:29: -> ^( EXPR_IDX $var ( expressions )+ )
                            {
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:260:32: ^( EXPR_IDX $var ( expressions )+ )
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
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:261:5: 
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
                            // 261:5: -> ^( EXPR_VAR $var)
                            {
                                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:261:8: ^( EXPR_VAR $var)
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:263:1: constant : ( 'true' -> ^( EXPR_BOOL 'true' ) | 'false' -> ^( EXPR_BOOL 'false' ) | FLOAT -> ^( EXPR_FLOAT FLOAT ) | INTEGER -> ^( EXPR_INT INTEGER ) | STRING -> ^( EXPR_STRING STRING ) );
    public final RVCCalParser.constant_return constant() throws RecognitionException {
        RVCCalParser.constant_return retval = new RVCCalParser.constant_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal155=null;
        Token string_literal156=null;
        Token FLOAT157=null;
        Token INTEGER158=null;
        Token STRING159=null;

        Object string_literal155_tree=null;
        Object string_literal156_tree=null;
        Object FLOAT157_tree=null;
        Object INTEGER158_tree=null;
        Object STRING159_tree=null;
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_FLOAT=new RewriteRuleTokenStream(adaptor,"token FLOAT");
        RewriteRuleTokenStream stream_110=new RewriteRuleTokenStream(adaptor,"token 110");
        RewriteRuleTokenStream stream_111=new RewriteRuleTokenStream(adaptor,"token 111");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:263:9: ( 'true' -> ^( EXPR_BOOL 'true' ) | 'false' -> ^( EXPR_BOOL 'false' ) | FLOAT -> ^( EXPR_FLOAT FLOAT ) | INTEGER -> ^( EXPR_INT INTEGER ) | STRING -> ^( EXPR_STRING STRING ) )
            int alt84=5;
            switch ( input.LA(1) ) {
            case 110:
                {
                alt84=1;
                }
                break;
            case 111:
                {
                alt84=2;
                }
                break;
            case FLOAT:
                {
                alt84=3;
                }
                break;
            case INTEGER:
                {
                alt84=4;
                }
                break;
            case STRING:
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:264:3: 'true'
                    {
                    string_literal155=(Token)match(input,110,FOLLOW_110_in_constant2131);  
                    stream_110.add(string_literal155);



                    // AST REWRITE
                    // elements: 110
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 264:10: -> ^( EXPR_BOOL 'true' )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:264:13: ^( EXPR_BOOL 'true' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BOOL, "EXPR_BOOL"), root_1);

                        adaptor.addChild(root_1, stream_110.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:3: 'false'
                    {
                    string_literal156=(Token)match(input,111,FOLLOW_111_in_constant2143);  
                    stream_111.add(string_literal156);



                    // AST REWRITE
                    // elements: 111
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 265:11: -> ^( EXPR_BOOL 'false' )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:14: ^( EXPR_BOOL 'false' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BOOL, "EXPR_BOOL"), root_1);

                        adaptor.addChild(root_1, stream_111.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:3: FLOAT
                    {
                    FLOAT157=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_constant2155);  
                    stream_FLOAT.add(FLOAT157);



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
                    // 266:9: -> ^( EXPR_FLOAT FLOAT )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:12: ^( EXPR_FLOAT FLOAT )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:267:3: INTEGER
                    {
                    INTEGER158=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_constant2167);  
                    stream_INTEGER.add(INTEGER158);



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
                    // 267:11: -> ^( EXPR_INT INTEGER )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:267:14: ^( EXPR_INT INTEGER )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:268:3: STRING
                    {
                    STRING159=(Token)match(input,STRING,FOLLOW_STRING_in_constant2179);  
                    stream_STRING.add(STRING159);



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
                    // 268:10: -> ^( EXPR_STRING STRING )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:268:13: ^( EXPR_STRING STRING )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:270:1: expressionGenerator : 'for' typeDef ID 'in' expression ;
    public final RVCCalParser.expressionGenerator_return expressionGenerator() throws RecognitionException {
        RVCCalParser.expressionGenerator_return retval = new RVCCalParser.expressionGenerator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal160=null;
        Token ID162=null;
        Token string_literal163=null;
        RVCCalParser.typeDef_return typeDef161 = null;

        RVCCalParser.expression_return expression164 = null;


        Object string_literal160_tree=null;
        Object ID162_tree=null;
        Object string_literal163_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:270:20: ( 'for' typeDef ID 'in' expression )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:271:2: 'for' typeDef ID 'in' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal160=(Token)match(input,112,FOLLOW_112_in_expressionGenerator2195); 
            string_literal160_tree = (Object)adaptor.create(string_literal160);
            adaptor.addChild(root_0, string_literal160_tree);

            pushFollow(FOLLOW_typeDef_in_expressionGenerator2197);
            typeDef161=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef161.getTree());
            ID162=(Token)match(input,ID,FOLLOW_ID_in_expressionGenerator2199); 
            ID162_tree = (Object)adaptor.create(ID162);
            adaptor.addChild(root_0, ID162_tree);

            string_literal163=(Token)match(input,113,FOLLOW_113_in_expressionGenerator2201); 
            string_literal163_tree = (Object)adaptor.create(string_literal163);
            adaptor.addChild(root_0, string_literal163_tree);

            pushFollow(FOLLOW_expression_in_expressionGenerator2203);
            expression164=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression164.getTree());
             

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:274:1: expressionGenerators : expressionGenerator ( ',' expressionGenerator )* -> ( expressionGenerator )+ ;
    public final RVCCalParser.expressionGenerators_return expressionGenerators() throws RecognitionException {
        RVCCalParser.expressionGenerators_return retval = new RVCCalParser.expressionGenerators_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal166=null;
        RVCCalParser.expressionGenerator_return expressionGenerator165 = null;

        RVCCalParser.expressionGenerator_return expressionGenerator167 = null;


        Object char_literal166_tree=null;
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleSubtreeStream stream_expressionGenerator=new RewriteRuleSubtreeStream(adaptor,"rule expressionGenerator");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:274:21: ( expressionGenerator ( ',' expressionGenerator )* -> ( expressionGenerator )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:274:23: expressionGenerator ( ',' expressionGenerator )*
            {
            pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators2213);
            expressionGenerator165=expressionGenerator();

            state._fsp--;

            stream_expressionGenerator.add(expressionGenerator165.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:274:43: ( ',' expressionGenerator )*
            loop85:
            do {
                int alt85=2;
                int LA85_0 = input.LA(1);

                if ( (LA85_0==64) ) {
                    alt85=1;
                }


                switch (alt85) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:274:44: ',' expressionGenerator
            	    {
            	    char_literal166=(Token)match(input,64,FOLLOW_64_in_expressionGenerators2216);  
            	    stream_64.add(char_literal166);

            	    pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators2218);
            	    expressionGenerator167=expressionGenerator();

            	    state._fsp--;

            	    stream_expressionGenerator.add(expressionGenerator167.getTree());

            	    }
            	    break;

            	default :
            	    break loop85;
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
            // 274:70: -> ( expressionGenerator )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:1: expressions : expression ( ',' expression )* -> ( expression )+ ;
    public final RVCCalParser.expressions_return expressions() throws RecognitionException {
        RVCCalParser.expressions_return retval = new RVCCalParser.expressions_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal169=null;
        RVCCalParser.expression_return expression168 = null;

        RVCCalParser.expression_return expression170 = null;


        Object char_literal169_tree=null;
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:12: ( expression ( ',' expression )* -> ( expression )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:14: expression ( ',' expression )*
            {
            pushFollow(FOLLOW_expression_in_expressions2232);
            expression168=expression();

            state._fsp--;

            stream_expression.add(expression168.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:25: ( ',' expression )*
            loop86:
            do {
                int alt86=2;
                int LA86_0 = input.LA(1);

                if ( (LA86_0==64) ) {
                    alt86=1;
                }


                switch (alt86) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:26: ',' expression
            	    {
            	    char_literal169=(Token)match(input,64,FOLLOW_64_in_expressions2235);  
            	    stream_64.add(char_literal169);

            	    pushFollow(FOLLOW_expression_in_expressions2237);
            	    expression170=expression();

            	    state._fsp--;

            	    stream_expression.add(expression170.getTree());

            	    }
            	    break;

            	default :
            	    break loop86;
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
            // 276:43: -> ( expression )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:278:1: idents : ID ( ',' ID )* ;
    public final RVCCalParser.idents_return idents() throws RecognitionException {
        RVCCalParser.idents_return retval = new RVCCalParser.idents_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID171=null;
        Token char_literal172=null;
        Token ID173=null;

        Object ID171_tree=null;
        Object char_literal172_tree=null;
        Object ID173_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:281:7: ( ID ( ',' ID )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:281:9: ID ( ',' ID )*
            {
            root_0 = (Object)adaptor.nil();

            ID171=(Token)match(input,ID,FOLLOW_ID_in_idents2256); 
            ID171_tree = (Object)adaptor.create(ID171);
            adaptor.addChild(root_0, ID171_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:281:12: ( ',' ID )*
            loop87:
            do {
                int alt87=2;
                int LA87_0 = input.LA(1);

                if ( (LA87_0==64) ) {
                    alt87=1;
                }


                switch (alt87) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:281:13: ',' ID
            	    {
            	    char_literal172=(Token)match(input,64,FOLLOW_64_in_idents2259); 
            	    char_literal172_tree = (Object)adaptor.create(char_literal172);
            	    adaptor.addChild(root_0, char_literal172_tree);

            	    ID173=(Token)match(input,ID,FOLLOW_ID_in_idents2261); 
            	    ID173_tree = (Object)adaptor.create(ID173);
            	    adaptor.addChild(root_0, ID173_tree);


            	    }
            	    break;

            	default :
            	    break loop87;
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:283:1: priorityInequality : qualifiedIdent ( '>' qualifiedIdent )+ ';' -> ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ ) ;
    public final RVCCalParser.priorityInequality_return priorityInequality() throws RecognitionException {
        RVCCalParser.priorityInequality_return retval = new RVCCalParser.priorityInequality_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal175=null;
        Token char_literal177=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent174 = null;

        RVCCalParser.qualifiedIdent_return qualifiedIdent176 = null;


        Object char_literal175_tree=null;
        Object char_literal177_tree=null;
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_93=new RewriteRuleTokenStream(adaptor,"token 93");
        RewriteRuleSubtreeStream stream_qualifiedIdent=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedIdent");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:19: ( qualifiedIdent ( '>' qualifiedIdent )+ ';' -> ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:21: qualifiedIdent ( '>' qualifiedIdent )+ ';'
            {
            pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality2277);
            qualifiedIdent174=qualifiedIdent();

            state._fsp--;

            stream_qualifiedIdent.add(qualifiedIdent174.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:36: ( '>' qualifiedIdent )+
            int cnt88=0;
            loop88:
            do {
                int alt88=2;
                int LA88_0 = input.LA(1);

                if ( (LA88_0==93) ) {
                    alt88=1;
                }


                switch (alt88) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:37: '>' qualifiedIdent
            	    {
            	    char_literal175=(Token)match(input,93,FOLLOW_93_in_priorityInequality2280);  
            	    stream_93.add(char_literal175);

            	    pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality2282);
            	    qualifiedIdent176=qualifiedIdent();

            	    state._fsp--;

            	    stream_qualifiedIdent.add(qualifiedIdent176.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt88 >= 1 ) break loop88;
                        EarlyExitException eee =
                            new EarlyExitException(88, input);
                        throw eee;
                }
                cnt88++;
            } while (true);

            char_literal177=(Token)match(input,78,FOLLOW_78_in_priorityInequality2286);  
            stream_78.add(char_literal177);



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
            // 286:62: -> ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:65: ^( INEQUALITY qualifiedIdent ( qualifiedIdent )+ )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:1: priorityOrder : 'priority' ( priorityInequality )* 'end' -> ^( PRIORITY ( priorityInequality )* ) ;
    public final RVCCalParser.priorityOrder_return priorityOrder() throws RecognitionException {
        RVCCalParser.priorityOrder_return retval = new RVCCalParser.priorityOrder_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal178=null;
        Token string_literal180=null;
        RVCCalParser.priorityInequality_return priorityInequality179 = null;


        Object string_literal178_tree=null;
        Object string_literal180_tree=null;
        RewriteRuleTokenStream stream_114=new RewriteRuleTokenStream(adaptor,"token 114");
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");
        RewriteRuleSubtreeStream stream_priorityInequality=new RewriteRuleSubtreeStream(adaptor,"rule priorityInequality");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:14: ( 'priority' ( priorityInequality )* 'end' -> ^( PRIORITY ( priorityInequality )* ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:16: 'priority' ( priorityInequality )* 'end'
            {
            string_literal178=(Token)match(input,114,FOLLOW_114_in_priorityOrder2305);  
            stream_114.add(string_literal178);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:27: ( priorityInequality )*
            loop89:
            do {
                int alt89=2;
                int LA89_0 = input.LA(1);

                if ( (LA89_0==ID) ) {
                    alt89=1;
                }


                switch (alt89) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:27: priorityInequality
            	    {
            	    pushFollow(FOLLOW_priorityInequality_in_priorityOrder2307);
            	    priorityInequality179=priorityInequality();

            	    state._fsp--;

            	    stream_priorityInequality.add(priorityInequality179.getTree());

            	    }
            	    break;

            	default :
            	    break loop89;
                }
            } while (true);

            string_literal180=(Token)match(input,71,FOLLOW_71_in_priorityOrder2310);  
            stream_71.add(string_literal180);



            // AST REWRITE
            // elements: priorityInequality
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 288:53: -> ^( PRIORITY ( priorityInequality )* )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:56: ^( PRIORITY ( priorityInequality )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PRIORITY, "PRIORITY"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:67: ( priorityInequality )*
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:290:1: qualifiedIdent : ID ( '.' ID )* -> ^( QID ( ID )+ ) ;
    public final RVCCalParser.qualifiedIdent_return qualifiedIdent() throws RecognitionException {
        RVCCalParser.qualifiedIdent_return retval = new RVCCalParser.qualifiedIdent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID181=null;
        Token char_literal182=null;
        Token ID183=null;

        Object ID181_tree=null;
        Object char_literal182_tree=null;
        Object ID183_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_72=new RewriteRuleTokenStream(adaptor,"token 72");

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:293:15: ( ID ( '.' ID )* -> ^( QID ( ID )+ ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:293:17: ID ( '.' ID )*
            {
            ID181=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent2331);  
            stream_ID.add(ID181);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:293:20: ( '.' ID )*
            loop90:
            do {
                int alt90=2;
                int LA90_0 = input.LA(1);

                if ( (LA90_0==72) ) {
                    alt90=1;
                }


                switch (alt90) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:293:21: '.' ID
            	    {
            	    char_literal182=(Token)match(input,72,FOLLOW_72_in_qualifiedIdent2334);  
            	    stream_72.add(char_literal182);

            	    ID183=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent2336);  
            	    stream_ID.add(ID183);


            	    }
            	    break;

            	default :
            	    break loop90;
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
            // 293:30: -> ^( QID ( ID )+ )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:293:33: ^( QID ( ID )+ )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:295:1: schedule : 'schedule' 'fsm' ID ':' ( stateTransition )* 'end' -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) ) ;
    public final RVCCalParser.schedule_return schedule() throws RecognitionException {
        RVCCalParser.schedule_return retval = new RVCCalParser.schedule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal184=null;
        Token string_literal185=null;
        Token ID186=null;
        Token char_literal187=null;
        Token string_literal189=null;
        RVCCalParser.stateTransition_return stateTransition188 = null;


        Object string_literal184_tree=null;
        Object string_literal185_tree=null;
        Object ID186_tree=null;
        Object char_literal187_tree=null;
        Object string_literal189_tree=null;
        RewriteRuleTokenStream stream_116=new RewriteRuleTokenStream(adaptor,"token 116");
        RewriteRuleTokenStream stream_115=new RewriteRuleTokenStream(adaptor,"token 115");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");
        RewriteRuleTokenStream stream_61=new RewriteRuleTokenStream(adaptor,"token 61");
        RewriteRuleSubtreeStream stream_stateTransition=new RewriteRuleSubtreeStream(adaptor,"rule stateTransition");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:298:9: ( 'schedule' 'fsm' ID ':' ( stateTransition )* 'end' -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:299:3: 'schedule' 'fsm' ID ':' ( stateTransition )* 'end'
            {
            string_literal184=(Token)match(input,115,FOLLOW_115_in_schedule2361);  
            stream_115.add(string_literal184);

            string_literal185=(Token)match(input,116,FOLLOW_116_in_schedule2363);  
            stream_116.add(string_literal185);

            ID186=(Token)match(input,ID,FOLLOW_ID_in_schedule2365);  
            stream_ID.add(ID186);

            char_literal187=(Token)match(input,61,FOLLOW_61_in_schedule2367);  
            stream_61.add(char_literal187);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:299:27: ( stateTransition )*
            loop91:
            do {
                int alt91=2;
                int LA91_0 = input.LA(1);

                if ( (LA91_0==ID) ) {
                    alt91=1;
                }


                switch (alt91) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:299:27: stateTransition
            	    {
            	    pushFollow(FOLLOW_stateTransition_in_schedule2369);
            	    stateTransition188=stateTransition();

            	    state._fsp--;

            	    stream_stateTransition.add(stateTransition188.getTree());

            	    }
            	    break;

            	default :
            	    break loop91;
                }
            } while (true);

            string_literal189=(Token)match(input,71,FOLLOW_71_in_schedule2372);  
            stream_71.add(string_literal189);



            // AST REWRITE
            // elements: stateTransition, ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 299:50: -> ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:299:53: ^( SCHEDULE ID ^( TRANSITIONS ( stateTransition )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SCHEDULE, "SCHEDULE"), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:299:67: ^( TRANSITIONS ( stateTransition )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TRANSITIONS, "TRANSITIONS"), root_2);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:299:81: ( stateTransition )*
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:301:1: stateTransition : ID '(' qualifiedIdent ')' '-->' ID ';' -> ^( TRANSITION ID qualifiedIdent ID ) ;
    public final RVCCalParser.stateTransition_return stateTransition() throws RecognitionException {
        RVCCalParser.stateTransition_return retval = new RVCCalParser.stateTransition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID190=null;
        Token char_literal191=null;
        Token char_literal193=null;
        Token string_literal194=null;
        Token ID195=null;
        Token char_literal196=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent192 = null;


        Object ID190_tree=null;
        Object char_literal191_tree=null;
        Object char_literal193_tree=null;
        Object string_literal194_tree=null;
        Object ID195_tree=null;
        Object char_literal196_tree=null;
        RewriteRuleTokenStream stream_69=new RewriteRuleTokenStream(adaptor,"token 69");
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_68=new RewriteRuleTokenStream(adaptor,"token 68");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_80=new RewriteRuleTokenStream(adaptor,"token 80");
        RewriteRuleSubtreeStream stream_qualifiedIdent=new RewriteRuleSubtreeStream(adaptor,"rule qualifiedIdent");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:301:16: ( ID '(' qualifiedIdent ')' '-->' ID ';' -> ^( TRANSITION ID qualifiedIdent ID ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:302:2: ID '(' qualifiedIdent ')' '-->' ID ';'
            {
            ID190=(Token)match(input,ID,FOLLOW_ID_in_stateTransition2395);  
            stream_ID.add(ID190);

            char_literal191=(Token)match(input,68,FOLLOW_68_in_stateTransition2397);  
            stream_68.add(char_literal191);

            pushFollow(FOLLOW_qualifiedIdent_in_stateTransition2399);
            qualifiedIdent192=qualifiedIdent();

            state._fsp--;

            stream_qualifiedIdent.add(qualifiedIdent192.getTree());
            char_literal193=(Token)match(input,69,FOLLOW_69_in_stateTransition2401);  
            stream_69.add(char_literal193);

            string_literal194=(Token)match(input,80,FOLLOW_80_in_stateTransition2403);  
            stream_80.add(string_literal194);

            ID195=(Token)match(input,ID,FOLLOW_ID_in_stateTransition2405);  
            stream_ID.add(ID195);

            char_literal196=(Token)match(input,78,FOLLOW_78_in_stateTransition2407);  
            stream_78.add(char_literal196);



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
            // 302:41: -> ^( TRANSITION ID qualifiedIdent ID )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:302:44: ^( TRANSITION ID qualifiedIdent ID )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:304:1: statement : ( 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end' | 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end' | 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end' | 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end' | ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' ) );
    public final RVCCalParser.statement_return statement() throws RecognitionException {
        RVCCalParser.statement_return retval = new RVCCalParser.statement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal197=null;
        Token string_literal198=null;
        Token string_literal200=null;
        Token string_literal202=null;
        Token string_literal203=null;
        Token string_literal205=null;
        Token string_literal207=null;
        Token string_literal209=null;
        Token string_literal211=null;
        Token string_literal213=null;
        Token string_literal214=null;
        Token string_literal216=null;
        Token string_literal218=null;
        Token string_literal220=null;
        Token string_literal221=null;
        Token string_literal223=null;
        Token string_literal225=null;
        Token string_literal227=null;
        Token ID228=null;
        Token char_literal229=null;
        Token char_literal231=null;
        Token string_literal232=null;
        Token char_literal234=null;
        Token char_literal235=null;
        Token char_literal237=null;
        Token char_literal238=null;
        RVCCalParser.varDecls_return varDecls199 = null;

        RVCCalParser.statement_return statement201 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr204 = null;

        RVCCalParser.expression_return expression206 = null;

        RVCCalParser.expression_return expression208 = null;

        RVCCalParser.varDecls_return varDecls210 = null;

        RVCCalParser.statement_return statement212 = null;

        RVCCalParser.expression_return expression215 = null;

        RVCCalParser.statement_return statement217 = null;

        RVCCalParser.statement_return statement219 = null;

        RVCCalParser.expression_return expression222 = null;

        RVCCalParser.varDecls_return varDecls224 = null;

        RVCCalParser.statement_return statement226 = null;

        RVCCalParser.expressions_return expressions230 = null;

        RVCCalParser.expression_return expression233 = null;

        RVCCalParser.expressions_return expressions236 = null;


        Object string_literal197_tree=null;
        Object string_literal198_tree=null;
        Object string_literal200_tree=null;
        Object string_literal202_tree=null;
        Object string_literal203_tree=null;
        Object string_literal205_tree=null;
        Object string_literal207_tree=null;
        Object string_literal209_tree=null;
        Object string_literal211_tree=null;
        Object string_literal213_tree=null;
        Object string_literal214_tree=null;
        Object string_literal216_tree=null;
        Object string_literal218_tree=null;
        Object string_literal220_tree=null;
        Object string_literal221_tree=null;
        Object string_literal223_tree=null;
        Object string_literal225_tree=null;
        Object string_literal227_tree=null;
        Object ID228_tree=null;
        Object char_literal229_tree=null;
        Object char_literal231_tree=null;
        Object string_literal232_tree=null;
        Object char_literal234_tree=null;
        Object char_literal235_tree=null;
        Object char_literal237_tree=null;
        Object char_literal238_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:307:10: ( 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end' | 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end' | 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end' | 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end' | ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' ) )
            int alt105=5;
            switch ( input.LA(1) ) {
            case 82:
                {
                alt105=1;
                }
                break;
            case 117:
                {
                alt105=2;
                }
                break;
            case 107:
                {
                alt105=3;
                }
                break;
            case 119:
                {
                alt105=4;
                }
                break;
            case ID:
                {
                alt105=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 105, 0, input);

                throw nvae;
            }

            switch (alt105) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:308:3: 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal197=(Token)match(input,82,FOLLOW_82_in_statement2433); 
                    string_literal197_tree = (Object)adaptor.create(string_literal197);
                    adaptor.addChild(root_0, string_literal197_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:308:11: ( 'var' varDecls 'do' )?
                    int alt92=2;
                    int LA92_0 = input.LA(1);

                    if ( (LA92_0==74) ) {
                        alt92=1;
                    }
                    switch (alt92) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:308:12: 'var' varDecls 'do'
                            {
                            string_literal198=(Token)match(input,74,FOLLOW_74_in_statement2436); 
                            string_literal198_tree = (Object)adaptor.create(string_literal198);
                            adaptor.addChild(root_0, string_literal198_tree);

                            pushFollow(FOLLOW_varDecls_in_statement2438);
                            varDecls199=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls199.getTree());
                            string_literal200=(Token)match(input,66,FOLLOW_66_in_statement2440); 
                            string_literal200_tree = (Object)adaptor.create(string_literal200);
                            adaptor.addChild(root_0, string_literal200_tree);


                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:308:34: ( statement )*
                    loop93:
                    do {
                        int alt93=2;
                        int LA93_0 = input.LA(1);

                        if ( (LA93_0==ID||LA93_0==82||LA93_0==107||LA93_0==117||LA93_0==119) ) {
                            alt93=1;
                        }


                        switch (alt93) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:308:34: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement2444);
                    	    statement201=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement201.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop93;
                        }
                    } while (true);

                    string_literal202=(Token)match(input,71,FOLLOW_71_in_statement2447); 
                    string_literal202_tree = (Object)adaptor.create(string_literal202);
                    adaptor.addChild(root_0, string_literal202_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:3: 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal203=(Token)match(input,117,FOLLOW_117_in_statement2453); 
                    string_literal203_tree = (Object)adaptor.create(string_literal203);
                    adaptor.addChild(root_0, string_literal203_tree);

                    pushFollow(FOLLOW_varDeclNoExpr_in_statement2455);
                    varDeclNoExpr204=varDeclNoExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, varDeclNoExpr204.getTree());
                    string_literal205=(Token)match(input,113,FOLLOW_113_in_statement2457); 
                    string_literal205_tree = (Object)adaptor.create(string_literal205);
                    adaptor.addChild(root_0, string_literal205_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:32: ( expression ( '..' expression )? )
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:33: expression ( '..' expression )?
                    {
                    pushFollow(FOLLOW_expression_in_statement2460);
                    expression206=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression206.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:44: ( '..' expression )?
                    int alt94=2;
                    int LA94_0 = input.LA(1);

                    if ( (LA94_0==118) ) {
                        alt94=1;
                    }
                    switch (alt94) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:45: '..' expression
                            {
                            string_literal207=(Token)match(input,118,FOLLOW_118_in_statement2463); 
                            string_literal207_tree = (Object)adaptor.create(string_literal207);
                            adaptor.addChild(root_0, string_literal207_tree);

                            pushFollow(FOLLOW_expression_in_statement2465);
                            expression208=expression();

                            state._fsp--;

                            adaptor.addChild(root_0, expression208.getTree());

                            }
                            break;

                    }


                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:64: ( 'var' varDecls )?
                    int alt95=2;
                    int LA95_0 = input.LA(1);

                    if ( (LA95_0==74) ) {
                        alt95=1;
                    }
                    switch (alt95) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:65: 'var' varDecls
                            {
                            string_literal209=(Token)match(input,74,FOLLOW_74_in_statement2471); 
                            string_literal209_tree = (Object)adaptor.create(string_literal209);
                            adaptor.addChild(root_0, string_literal209_tree);

                            pushFollow(FOLLOW_varDecls_in_statement2473);
                            varDecls210=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls210.getTree());

                            }
                            break;

                    }

                    string_literal211=(Token)match(input,66,FOLLOW_66_in_statement2477); 
                    string_literal211_tree = (Object)adaptor.create(string_literal211);
                    adaptor.addChild(root_0, string_literal211_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:87: ( statement )*
                    loop96:
                    do {
                        int alt96=2;
                        int LA96_0 = input.LA(1);

                        if ( (LA96_0==ID||LA96_0==82||LA96_0==107||LA96_0==117||LA96_0==119) ) {
                            alt96=1;
                        }


                        switch (alt96) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:87: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement2479);
                    	    statement212=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement212.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop96;
                        }
                    } while (true);

                    string_literal213=(Token)match(input,71,FOLLOW_71_in_statement2482); 
                    string_literal213_tree = (Object)adaptor.create(string_literal213);
                    adaptor.addChild(root_0, string_literal213_tree);

                     

                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:310:3: 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal214=(Token)match(input,107,FOLLOW_107_in_statement2488); 
                    string_literal214_tree = (Object)adaptor.create(string_literal214);
                    adaptor.addChild(root_0, string_literal214_tree);

                    pushFollow(FOLLOW_expression_in_statement2490);
                    expression215=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression215.getTree());
                    string_literal216=(Token)match(input,108,FOLLOW_108_in_statement2492); 
                    string_literal216_tree = (Object)adaptor.create(string_literal216);
                    adaptor.addChild(root_0, string_literal216_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:310:26: ( statement )*
                    loop97:
                    do {
                        int alt97=2;
                        int LA97_0 = input.LA(1);

                        if ( (LA97_0==ID||LA97_0==82||LA97_0==107||LA97_0==117||LA97_0==119) ) {
                            alt97=1;
                        }


                        switch (alt97) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:310:26: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement2494);
                    	    statement217=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement217.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop97;
                        }
                    } while (true);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:310:37: ( 'else' ( statement )* )?
                    int alt99=2;
                    int LA99_0 = input.LA(1);

                    if ( (LA99_0==109) ) {
                        alt99=1;
                    }
                    switch (alt99) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:310:38: 'else' ( statement )*
                            {
                            string_literal218=(Token)match(input,109,FOLLOW_109_in_statement2498); 
                            string_literal218_tree = (Object)adaptor.create(string_literal218);
                            adaptor.addChild(root_0, string_literal218_tree);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:310:45: ( statement )*
                            loop98:
                            do {
                                int alt98=2;
                                int LA98_0 = input.LA(1);

                                if ( (LA98_0==ID||LA98_0==82||LA98_0==107||LA98_0==117||LA98_0==119) ) {
                                    alt98=1;
                                }


                                switch (alt98) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:310:45: statement
                            	    {
                            	    pushFollow(FOLLOW_statement_in_statement2500);
                            	    statement219=statement();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, statement219.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop98;
                                }
                            } while (true);


                            }
                            break;

                    }

                    string_literal220=(Token)match(input,71,FOLLOW_71_in_statement2505); 
                    string_literal220_tree = (Object)adaptor.create(string_literal220);
                    adaptor.addChild(root_0, string_literal220_tree);

                      

                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:311:3: 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal221=(Token)match(input,119,FOLLOW_119_in_statement2511); 
                    string_literal221_tree = (Object)adaptor.create(string_literal221);
                    adaptor.addChild(root_0, string_literal221_tree);

                    pushFollow(FOLLOW_expression_in_statement2513);
                    expression222=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression222.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:311:22: ( 'var' varDecls )?
                    int alt100=2;
                    int LA100_0 = input.LA(1);

                    if ( (LA100_0==74) ) {
                        alt100=1;
                    }
                    switch (alt100) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:311:23: 'var' varDecls
                            {
                            string_literal223=(Token)match(input,74,FOLLOW_74_in_statement2516); 
                            string_literal223_tree = (Object)adaptor.create(string_literal223);
                            adaptor.addChild(root_0, string_literal223_tree);

                            pushFollow(FOLLOW_varDecls_in_statement2518);
                            varDecls224=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls224.getTree());

                            }
                            break;

                    }

                    string_literal225=(Token)match(input,66,FOLLOW_66_in_statement2522); 
                    string_literal225_tree = (Object)adaptor.create(string_literal225);
                    adaptor.addChild(root_0, string_literal225_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:311:45: ( statement )*
                    loop101:
                    do {
                        int alt101=2;
                        int LA101_0 = input.LA(1);

                        if ( (LA101_0==ID||LA101_0==82||LA101_0==107||LA101_0==117||LA101_0==119) ) {
                            alt101=1;
                        }


                        switch (alt101) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:311:45: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement2524);
                    	    statement226=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement226.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop101;
                        }
                    } while (true);

                    string_literal227=(Token)match(input,71,FOLLOW_71_in_statement2527); 
                    string_literal227_tree = (Object)adaptor.create(string_literal227);
                    adaptor.addChild(root_0, string_literal227_tree);

                      

                    }
                    break;
                case 5 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:313:3: ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' )
                    {
                    root_0 = (Object)adaptor.nil();

                    ID228=(Token)match(input,ID,FOLLOW_ID_in_statement2534); 
                    ID228_tree = (Object)adaptor.create(ID228);
                    adaptor.addChild(root_0, ID228_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:313:6: ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' )
                    int alt104=2;
                    int LA104_0 = input.LA(1);

                    if ( (LA104_0==62||LA104_0==77) ) {
                        alt104=1;
                    }
                    else if ( (LA104_0==68) ) {
                        alt104=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 104, 0, input);

                        throw nvae;
                    }
                    switch (alt104) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:314:5: ( ( '[' expressions ']' )? ':=' expression ';' )
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:314:5: ( ( '[' expressions ']' )? ':=' expression ';' )
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:314:6: ( '[' expressions ']' )? ':=' expression ';'
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:314:6: ( '[' expressions ']' )?
                            int alt102=2;
                            int LA102_0 = input.LA(1);

                            if ( (LA102_0==62) ) {
                                alt102=1;
                            }
                            switch (alt102) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:314:7: '[' expressions ']'
                                    {
                                    char_literal229=(Token)match(input,62,FOLLOW_62_in_statement2544); 
                                    char_literal229_tree = (Object)adaptor.create(char_literal229);
                                    adaptor.addChild(root_0, char_literal229_tree);

                                    pushFollow(FOLLOW_expressions_in_statement2546);
                                    expressions230=expressions();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expressions230.getTree());
                                    char_literal231=(Token)match(input,63,FOLLOW_63_in_statement2548); 
                                    char_literal231_tree = (Object)adaptor.create(char_literal231);
                                    adaptor.addChild(root_0, char_literal231_tree);


                                    }
                                    break;

                            }

                            string_literal232=(Token)match(input,77,FOLLOW_77_in_statement2552); 
                            string_literal232_tree = (Object)adaptor.create(string_literal232);
                            adaptor.addChild(root_0, string_literal232_tree);

                            pushFollow(FOLLOW_expression_in_statement2554);
                            expression233=expression();

                            state._fsp--;

                            adaptor.addChild(root_0, expression233.getTree());
                            char_literal234=(Token)match(input,78,FOLLOW_78_in_statement2556); 
                            char_literal234_tree = (Object)adaptor.create(char_literal234);
                            adaptor.addChild(root_0, char_literal234_tree);


                            }

                             

                            }
                            break;
                        case 2 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:315:6: '(' ( expressions )? ')' ';'
                            {
                            char_literal235=(Token)match(input,68,FOLLOW_68_in_statement2566); 
                            char_literal235_tree = (Object)adaptor.create(char_literal235);
                            adaptor.addChild(root_0, char_literal235_tree);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:315:10: ( expressions )?
                            int alt103=2;
                            int LA103_0 = input.LA(1);

                            if ( ((LA103_0>=ID && LA103_0<=STRING)||LA103_0==62||LA103_0==68||LA103_0==99||(LA103_0>=105 && LA103_0<=107)||(LA103_0>=110 && LA103_0<=111)) ) {
                                alt103=1;
                            }
                            switch (alt103) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:315:10: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_statement2568);
                                    expressions236=expressions();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expressions236.getTree());

                                    }
                                    break;

                            }

                            char_literal237=(Token)match(input,69,FOLLOW_69_in_statement2571); 
                            char_literal237_tree = (Object)adaptor.create(char_literal237);
                            adaptor.addChild(root_0, char_literal237_tree);

                            char_literal238=(Token)match(input,78,FOLLOW_78_in_statement2573); 
                            char_literal238_tree = (Object)adaptor.create(char_literal238);
                            adaptor.addChild(root_0, char_literal238_tree);

                             

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:317:1: typeAttr : ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) ) ;
    public final RVCCalParser.typeAttr_return typeAttr() throws RecognitionException {
        RVCCalParser.typeAttr_return retval = new RVCCalParser.typeAttr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID239=null;
        Token char_literal240=null;
        Token char_literal242=null;
        RVCCalParser.typeDef_return typeDef241 = null;

        RVCCalParser.expression_return expression243 = null;


        Object ID239_tree=null;
        Object char_literal240_tree=null;
        Object char_literal242_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_61=new RewriteRuleTokenStream(adaptor,"token 61");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:324:9: ( ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:324:11: ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) )
            {
            ID239=(Token)match(input,ID,FOLLOW_ID_in_typeAttr2594);  
            stream_ID.add(ID239);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:324:14: ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) )
            int alt106=2;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==61) ) {
                alt106=1;
            }
            else if ( (LA106_0==76) ) {
                alt106=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 106, 0, input);

                throw nvae;
            }
            switch (alt106) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:324:15: ':' typeDef
                    {
                    char_literal240=(Token)match(input,61,FOLLOW_61_in_typeAttr2597);  
                    stream_61.add(char_literal240);

                    pushFollow(FOLLOW_typeDef_in_typeAttr2599);
                    typeDef241=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef241.getTree());


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
                    // 324:27: -> ^( TYPE ID typeDef )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:324:30: ^( TYPE ID typeDef )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:324:51: '=' expression
                    {
                    char_literal242=(Token)match(input,76,FOLLOW_76_in_typeAttr2613);  
                    stream_76.add(char_literal242);

                    pushFollow(FOLLOW_expression_in_typeAttr2615);
                    expression243=expression();

                    state._fsp--;

                    stream_expression.add(expression243.getTree());


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
                    // 324:66: -> ^( EXPR ID expression )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:324:69: ^( EXPR ID expression )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:326:1: typeAttrs : typeAttr ( ',' typeAttr )* -> ( typeAttr )+ ;
    public final RVCCalParser.typeAttrs_return typeAttrs() throws RecognitionException {
        RVCCalParser.typeAttrs_return retval = new RVCCalParser.typeAttrs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal245=null;
        RVCCalParser.typeAttr_return typeAttr244 = null;

        RVCCalParser.typeAttr_return typeAttr246 = null;


        Object char_literal245_tree=null;
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleSubtreeStream stream_typeAttr=new RewriteRuleSubtreeStream(adaptor,"rule typeAttr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:326:10: ( typeAttr ( ',' typeAttr )* -> ( typeAttr )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:326:12: typeAttr ( ',' typeAttr )*
            {
            pushFollow(FOLLOW_typeAttr_in_typeAttrs2634);
            typeAttr244=typeAttr();

            state._fsp--;

            stream_typeAttr.add(typeAttr244.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:326:21: ( ',' typeAttr )*
            loop107:
            do {
                int alt107=2;
                int LA107_0 = input.LA(1);

                if ( (LA107_0==64) ) {
                    alt107=1;
                }


                switch (alt107) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:326:22: ',' typeAttr
            	    {
            	    char_literal245=(Token)match(input,64,FOLLOW_64_in_typeAttrs2637);  
            	    stream_64.add(char_literal245);

            	    pushFollow(FOLLOW_typeAttr_in_typeAttrs2639);
            	    typeAttr246=typeAttr();

            	    state._fsp--;

            	    stream_typeAttr.add(typeAttr246.getTree());

            	    }
            	    break;

            	default :
            	    break loop107;
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
            // 326:37: -> ( typeAttr )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:329:1: typeDef : ID ( '(' attrs= typeAttrs ')' )? -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) ;
    public final RVCCalParser.typeDef_return typeDef() throws RecognitionException {
        RVCCalParser.typeDef_return retval = new RVCCalParser.typeDef_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID247=null;
        Token char_literal248=null;
        Token char_literal249=null;
        RVCCalParser.typeAttrs_return attrs = null;


        Object ID247_tree=null;
        Object char_literal248_tree=null;
        Object char_literal249_tree=null;
        RewriteRuleTokenStream stream_69=new RewriteRuleTokenStream(adaptor,"token 69");
        RewriteRuleTokenStream stream_68=new RewriteRuleTokenStream(adaptor,"token 68");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_typeAttrs=new RewriteRuleSubtreeStream(adaptor,"rule typeAttrs");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:329:8: ( ID ( '(' attrs= typeAttrs ')' )? -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:329:10: ID ( '(' attrs= typeAttrs ')' )?
            {
            ID247=(Token)match(input,ID,FOLLOW_ID_in_typeDef2656);  
            stream_ID.add(ID247);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:329:13: ( '(' attrs= typeAttrs ')' )?
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( (LA108_0==68) ) {
                alt108=1;
            }
            switch (alt108) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:329:14: '(' attrs= typeAttrs ')'
                    {
                    char_literal248=(Token)match(input,68,FOLLOW_68_in_typeDef2659);  
                    stream_68.add(char_literal248);

                    pushFollow(FOLLOW_typeAttrs_in_typeDef2663);
                    attrs=typeAttrs();

                    state._fsp--;

                    stream_typeAttrs.add(attrs.getTree());
                    char_literal249=(Token)match(input,69,FOLLOW_69_in_typeDef2665);  
                    stream_69.add(char_literal249);


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
            // 329:40: -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:329:43: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:329:53: ^( TYPE_ATTRS ( $attrs)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_2);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:329:66: ( $attrs)?
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:331:1: varDecl : typeDef ID ( '=' expression | ':=' expression )? ;
    public final RVCCalParser.varDecl_return varDecl() throws RecognitionException {
        RVCCalParser.varDecl_return retval = new RVCCalParser.varDecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID251=null;
        Token char_literal252=null;
        Token string_literal254=null;
        RVCCalParser.typeDef_return typeDef250 = null;

        RVCCalParser.expression_return expression253 = null;

        RVCCalParser.expression_return expression255 = null;


        Object ID251_tree=null;
        Object char_literal252_tree=null;
        Object string_literal254_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:335:8: ( typeDef ID ( '=' expression | ':=' expression )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:335:10: typeDef ID ( '=' expression | ':=' expression )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_typeDef_in_varDecl2697);
            typeDef250=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef250.getTree());
            ID251=(Token)match(input,ID,FOLLOW_ID_in_varDecl2699); 
            ID251_tree = (Object)adaptor.create(ID251);
            adaptor.addChild(root_0, ID251_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:335:21: ( '=' expression | ':=' expression )?
            int alt109=3;
            int LA109_0 = input.LA(1);

            if ( (LA109_0==76) ) {
                alt109=1;
            }
            else if ( (LA109_0==77) ) {
                alt109=2;
            }
            switch (alt109) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:335:22: '=' expression
                    {
                    char_literal252=(Token)match(input,76,FOLLOW_76_in_varDecl2702); 
                    char_literal252_tree = (Object)adaptor.create(char_literal252);
                    adaptor.addChild(root_0, char_literal252_tree);

                    pushFollow(FOLLOW_expression_in_varDecl2704);
                    expression253=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression253.getTree());

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:335:39: ':=' expression
                    {
                    string_literal254=(Token)match(input,77,FOLLOW_77_in_varDecl2708); 
                    string_literal254_tree = (Object)adaptor.create(string_literal254);
                    adaptor.addChild(root_0, string_literal254_tree);

                    pushFollow(FOLLOW_expression_in_varDecl2710);
                    expression255=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression255.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:337:1: varDeclNoExpr : typeDef ID -> ^( VAR typeDef ID ) ;
    public final RVCCalParser.varDeclNoExpr_return varDeclNoExpr() throws RecognitionException {
        RVCCalParser.varDeclNoExpr_return retval = new RVCCalParser.varDeclNoExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID257=null;
        RVCCalParser.typeDef_return typeDef256 = null;


        Object ID257_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:337:14: ( typeDef ID -> ^( VAR typeDef ID ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:337:16: typeDef ID
            {
            pushFollow(FOLLOW_typeDef_in_varDeclNoExpr2721);
            typeDef256=typeDef();

            state._fsp--;

            stream_typeDef.add(typeDef256.getTree());
            ID257=(Token)match(input,ID,FOLLOW_ID_in_varDeclNoExpr2723);  
            stream_ID.add(ID257);



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
            // 337:27: -> ^( VAR typeDef ID )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:337:30: ^( VAR typeDef ID )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:339:1: varDecls : varDecl ( ',' varDecl )* -> ( varDecl )+ ;
    public final RVCCalParser.varDecls_return varDecls() throws RecognitionException {
        RVCCalParser.varDecls_return retval = new RVCCalParser.varDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal259=null;
        RVCCalParser.varDecl_return varDecl258 = null;

        RVCCalParser.varDecl_return varDecl260 = null;


        Object char_literal259_tree=null;
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleSubtreeStream stream_varDecl=new RewriteRuleSubtreeStream(adaptor,"rule varDecl");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:339:9: ( varDecl ( ',' varDecl )* -> ( varDecl )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:339:11: varDecl ( ',' varDecl )*
            {
            pushFollow(FOLLOW_varDecl_in_varDecls2740);
            varDecl258=varDecl();

            state._fsp--;

            stream_varDecl.add(varDecl258.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:339:19: ( ',' varDecl )*
            loop110:
            do {
                int alt110=2;
                int LA110_0 = input.LA(1);

                if ( (LA110_0==64) ) {
                    alt110=1;
                }


                switch (alt110) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:339:20: ',' varDecl
            	    {
            	    char_literal259=(Token)match(input,64,FOLLOW_64_in_varDecls2743);  
            	    stream_64.add(char_literal259);

            	    pushFollow(FOLLOW_varDecl_in_varDecls2745);
            	    varDecl260=varDecl();

            	    state._fsp--;

            	    stream_varDecl.add(varDecl260.getTree());

            	    }
            	    break;

            	default :
            	    break loop110;
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
            // 339:34: -> ( varDecl )+
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


 

    public static final BitSet FOLLOW_60_in_actionGuards362 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expressions_in_actionGuards364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_actionInput377 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_actionInput379 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62_in_actionInput383 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_idents_in_actionInput385 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_actionInput387 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_actionRepeat_in_actionInput389 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionInput_in_actionInputs400 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_actionInputs403 = new BitSet(new long[]{0x4002000000000000L});
    public static final BitSet FOLLOW_actionInput_in_actionInputs405 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_ID_in_actionOutput421 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_actionOutput423 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62_in_actionOutput427 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expressions_in_actionOutput429 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_actionOutput431 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_actionRepeat_in_actionOutput433 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionOutput_in_actionOutputs444 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_actionOutputs447 = new BitSet(new long[]{0x4002000000000000L});
    public static final BitSet FOLLOW_actionOutput_in_actionOutputs449 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_65_in_actionRepeat463 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_actionRepeat465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_actionStatements476 = new BitSet(new long[]{0x0002000000000002L,0x00A0080000040000L});
    public static final BitSet FOLLOW_statement_in_actionStatements478 = new BitSet(new long[]{0x0002000000000002L,0x00A0080000040000L});
    public static final BitSet FOLLOW_actorImport_in_actor496 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080008L});
    public static final BitSet FOLLOW_67_in_actor499 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_ID_in_actor503 = new BitSet(new long[]{0x4000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_62_in_actor506 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_actor508 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_actor512 = new BitSet(new long[]{0x0002000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_actorParameters_in_actor514 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_actor517 = new BitSet(new long[]{0x0002000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_actorPortDecls_in_actor522 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_actor525 = new BitSet(new long[]{0x2002000000000000L});
    public static final BitSet FOLLOW_actorPortDecls_in_actor529 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_actor532 = new BitSet(new long[]{0x0002000000000000L,0x000C000000028A80L});
    public static final BitSet FOLLOW_actorDeclarations_in_actor535 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_actor537 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_actor539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration604 = new BitSet(new long[]{0x2002000000000000L,0x0000000000000110L});
    public static final BitSet FOLLOW_72_in_actorDeclaration615 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration619 = new BitSet(new long[]{0x2000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_61_in_actorDeclaration630 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000A00L});
    public static final BitSet FOLLOW_73_in_actorDeclaration639 = new BitSet(new long[]{0x4002000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_actionInputs_in_actorDeclaration643 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_actorDeclaration646 = new BitSet(new long[]{0x5002000000000000L,0x0000000000000484L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration650 = new BitSet(new long[]{0x1000000000000000L,0x0000000000000484L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration655 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000484L});
    public static final BitSet FOLLOW_74_in_actorDeclaration659 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration661 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000084L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration665 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_actorDeclaration668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_actorDeclaration739 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_actorDeclaration741 = new BitSet(new long[]{0x5002000000000000L,0x0000000000000484L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration743 = new BitSet(new long[]{0x1000000000000000L,0x0000000000000484L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration746 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000484L});
    public static final BitSet FOLLOW_74_in_actorDeclaration750 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration752 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000084L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration756 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_actorDeclaration759 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_actorDeclaration838 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_typeAttrs_in_actorDeclaration842 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_actorDeclaration844 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration854 = new BitSet(new long[]{0x0000000000000000L,0x0000000000007000L});
    public static final BitSet FOLLOW_76_in_actorDeclaration863 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration865 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_77_in_actorDeclaration901 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration903 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_actorDeclaration965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_actorDeclaration975 = new BitSet(new long[]{0x4002000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_actionInputs_in_actorDeclaration977 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_actorDeclaration980 = new BitSet(new long[]{0x5002000000000000L,0x0000000000000484L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration982 = new BitSet(new long[]{0x1000000000000000L,0x0000000000000484L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration985 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000484L});
    public static final BitSet FOLLOW_74_in_actorDeclaration989 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration991 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000084L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration995 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_actorDeclaration998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_actorDeclaration1052 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_actorDeclaration1054 = new BitSet(new long[]{0x5002000000000000L,0x0000000000000484L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration1056 = new BitSet(new long[]{0x1000000000000000L,0x0000000000000484L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration1059 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000484L});
    public static final BitSet FOLLOW_74_in_actorDeclaration1063 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration1065 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000084L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration1069 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_actorDeclaration1072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_priorityOrder_in_actorDeclaration1119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_actorDeclaration1128 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration1130 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_actorDeclaration1132 = new BitSet(new long[]{0x0002000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration1135 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000021L});
    public static final BitSet FOLLOW_64_in_actorDeclaration1138 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration1140 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000021L});
    public static final BitSet FOLLOW_69_in_actorDeclaration1146 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_actorDeclaration1148 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_typeDef_in_actorDeclaration1150 = new BitSet(new long[]{0x2000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_actorDeclaration1157 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration1159 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_actorDeclaration1163 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration1171 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_actorDeclaration1177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_actorDeclaration1187 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration1189 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_actorDeclaration1191 = new BitSet(new long[]{0x0002000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration1194 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000021L});
    public static final BitSet FOLLOW_64_in_actorDeclaration1197 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration1199 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000021L});
    public static final BitSet FOLLOW_69_in_actorDeclaration1205 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040400L});
    public static final BitSet FOLLOW_74_in_actorDeclaration1212 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration1214 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_actorDeclaration1222 = new BitSet(new long[]{0x0002000000000000L,0x00A0080000040080L});
    public static final BitSet FOLLOW_statement_in_actorDeclaration1224 = new BitSet(new long[]{0x0002000000000000L,0x00A0080000040080L});
    public static final BitSet FOLLOW_71_in_actorDeclaration1227 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations1239 = new BitSet(new long[]{0x0002000000000002L,0x000C000000028A00L});
    public static final BitSet FOLLOW_schedule_in_actorDeclarations1243 = new BitSet(new long[]{0x0002000000000002L,0x0004000000028A00L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations1245 = new BitSet(new long[]{0x0002000000000002L,0x0004000000028A00L});
    public static final BitSet FOLLOW_83_in_actorImport1268 = new BitSet(new long[]{0x0002000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_actorImport1273 = new BitSet(new long[]{0x0002000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport1275 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_actorImport1277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport1283 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_actorImport1285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_actorParameter1300 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_ID_in_actorParameter1302 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_actorParameter1305 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_actorParameter1307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters1329 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_actorParameters1332 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters1334 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorPortDecls1353 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_actorPortDecls1356 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorPortDecls1358 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_and_expr_in_expression1383 = new BitSet(new long[]{0x0000000000000002L,0x0000000000600000L});
    public static final BitSet FOLLOW_85_in_expression1388 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_86_in_expression1392 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_and_expr_in_expression1397 = new BitSet(new long[]{0x0000000000000002L,0x0000000000600000L});
    public static final BitSet FOLLOW_bitor_expr_in_and_expr1430 = new BitSet(new long[]{0x0000000000000002L,0x0000000001800000L});
    public static final BitSet FOLLOW_87_in_and_expr1435 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_88_in_and_expr1439 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_bitor_expr_in_and_expr1444 = new BitSet(new long[]{0x0000000000000002L,0x0000000001800000L});
    public static final BitSet FOLLOW_bitand_expr_in_bitor_expr1477 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_bitor_expr1481 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_bitand_expr_in_bitor_expr1485 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_eq_expr_in_bitand_expr1518 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_bitand_expr1522 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_eq_expr_in_bitand_expr1526 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_rel_expr_in_eq_expr1559 = new BitSet(new long[]{0x0000000000000002L,0x0000000008001000L});
    public static final BitSet FOLLOW_76_in_eq_expr1566 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_91_in_eq_expr1572 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_rel_expr_in_eq_expr1577 = new BitSet(new long[]{0x0000000000000002L,0x0000000008001000L});
    public static final BitSet FOLLOW_shift_expr_in_rel_expr1615 = new BitSet(new long[]{0x0000000000000002L,0x00000000F0000000L});
    public static final BitSet FOLLOW_92_in_rel_expr1622 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_93_in_rel_expr1628 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_94_in_rel_expr1634 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_95_in_rel_expr1640 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_shift_expr_in_rel_expr1645 = new BitSet(new long[]{0x0000000000000002L,0x00000000F0000000L});
    public static final BitSet FOLLOW_add_expr_in_shift_expr1682 = new BitSet(new long[]{0x0000000000000002L,0x0000000300000000L});
    public static final BitSet FOLLOW_96_in_shift_expr1689 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_97_in_shift_expr1695 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_add_expr_in_shift_expr1700 = new BitSet(new long[]{0x0000000000000002L,0x0000000300000000L});
    public static final BitSet FOLLOW_mul_expr_in_add_expr1738 = new BitSet(new long[]{0x0000000000000002L,0x0000000C00000000L});
    public static final BitSet FOLLOW_98_in_add_expr1745 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_99_in_add_expr1751 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_mul_expr_in_add_expr1756 = new BitSet(new long[]{0x0000000000000002L,0x0000000C00000000L});
    public static final BitSet FOLLOW_exp_expr_in_mul_expr1794 = new BitSet(new long[]{0x0000000000000002L,0x000000F000000000L});
    public static final BitSet FOLLOW_100_in_mul_expr1801 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_101_in_mul_expr1807 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_102_in_mul_expr1813 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_103_in_mul_expr1819 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_exp_expr_in_mul_expr1824 = new BitSet(new long[]{0x0000000000000002L,0x000000F000000000L});
    public static final BitSet FOLLOW_un_expr_in_exp_expr1862 = new BitSet(new long[]{0x0000000000000002L,0x0000010000000000L});
    public static final BitSet FOLLOW_104_in_exp_expr1866 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_un_expr_in_exp_expr1870 = new BitSet(new long[]{0x0000000000000002L,0x0000010000000000L});
    public static final BitSet FOLLOW_postfix_expression_in_un_expr1901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_un_expr1910 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_un_expr_in_un_expr1912 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_un_expr1927 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_un_expr_in_un_expr1929 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_106_in_un_expr1944 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_un_expr_in_un_expr1946 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_postfix_expression1966 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1970 = new BitSet(new long[]{0xA000000000000000L});
    public static final BitSet FOLLOW_61_in_postfix_expression1973 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_expressionGenerators_in_postfix_expression1977 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_postfix_expression1981 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_107_in_postfix_expression1998 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_postfix_expression2002 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_108_in_postfix_expression2004 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_postfix_expression2008 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_109_in_postfix_expression2010 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_postfix_expression2014 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_postfix_expression2016 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_postfix_expression2035 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_postfix_expression2043 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_postfix_expression2045 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_postfix_expression2047 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_postfix_expression2057 = new BitSet(new long[]{0x4000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_postfix_expression2065 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000030L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression2067 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_postfix_expression2070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_postfix_expression2090 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression2092 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_postfix_expression2094 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_110_in_constant2131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_111_in_constant2143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_constant2155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_constant2167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_constant2179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_112_in_expressionGenerator2195 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_typeDef_in_expressionGenerator2197 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_ID_in_expressionGenerator2199 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_113_in_expressionGenerator2201 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_expressionGenerator2203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators2213 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_expressionGenerators2216 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators2218 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_expression_in_expressions2232 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_expressions2235 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_expressions2237 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_ID_in_idents2256 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_idents2259 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_ID_in_idents2261 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality2277 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_93_in_priorityInequality2280 = new BitSet(new long[]{0x0002000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality2282 = new BitSet(new long[]{0x0000000000000000L,0x0000000020004000L});
    public static final BitSet FOLLOW_78_in_priorityInequality2286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_priorityOrder2305 = new BitSet(new long[]{0x0002000000000000L,0x0000000000100080L});
    public static final BitSet FOLLOW_priorityInequality_in_priorityOrder2307 = new BitSet(new long[]{0x0002000000000000L,0x0000000000100080L});
    public static final BitSet FOLLOW_71_in_priorityOrder2310 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent2331 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_qualifiedIdent2334 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent2336 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000100L});
    public static final BitSet FOLLOW_115_in_schedule2361 = new BitSet(new long[]{0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_116_in_schedule2363 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_ID_in_schedule2365 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_schedule2367 = new BitSet(new long[]{0x0002000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_stateTransition_in_schedule2369 = new BitSet(new long[]{0x0002000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_schedule2372 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stateTransition2395 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_stateTransition2397 = new BitSet(new long[]{0x0002000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_stateTransition2399 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_stateTransition2401 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_stateTransition2403 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_ID_in_stateTransition2405 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_stateTransition2407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_statement2433 = new BitSet(new long[]{0x0002000000000000L,0x00A0080000040480L});
    public static final BitSet FOLLOW_74_in_statement2436 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_varDecls_in_statement2438 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_statement2440 = new BitSet(new long[]{0x0002000000000000L,0x00A0080000040080L});
    public static final BitSet FOLLOW_statement_in_statement2444 = new BitSet(new long[]{0x0002000000000000L,0x00A0080000040080L});
    public static final BitSet FOLLOW_71_in_statement2447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_117_in_statement2453 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_statement2455 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_113_in_statement2457 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_statement2460 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000404L});
    public static final BitSet FOLLOW_118_in_statement2463 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_statement2465 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000404L});
    public static final BitSet FOLLOW_74_in_statement2471 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_varDecls_in_statement2473 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_statement2477 = new BitSet(new long[]{0x0002000000000000L,0x00A0080000040080L});
    public static final BitSet FOLLOW_statement_in_statement2479 = new BitSet(new long[]{0x0002000000000000L,0x00A0080000040080L});
    public static final BitSet FOLLOW_71_in_statement2482 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_107_in_statement2488 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_statement2490 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_108_in_statement2492 = new BitSet(new long[]{0x0002000000000000L,0x00A0280000040080L});
    public static final BitSet FOLLOW_statement_in_statement2494 = new BitSet(new long[]{0x0002000000000000L,0x00A0280000040080L});
    public static final BitSet FOLLOW_109_in_statement2498 = new BitSet(new long[]{0x0002000000000000L,0x00A0080000040080L});
    public static final BitSet FOLLOW_statement_in_statement2500 = new BitSet(new long[]{0x0002000000000000L,0x00A0080000040080L});
    public static final BitSet FOLLOW_71_in_statement2505 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_119_in_statement2511 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_statement2513 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000404L});
    public static final BitSet FOLLOW_74_in_statement2516 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_varDecls_in_statement2518 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_statement2522 = new BitSet(new long[]{0x0002000000000000L,0x00A0080000040080L});
    public static final BitSet FOLLOW_statement_in_statement2524 = new BitSet(new long[]{0x0002000000000000L,0x00A0080000040080L});
    public static final BitSet FOLLOW_71_in_statement2527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_statement2534 = new BitSet(new long[]{0x4000000000000000L,0x0000000000002010L});
    public static final BitSet FOLLOW_62_in_statement2544 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expressions_in_statement2546 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_statement2548 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_statement2552 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_statement2554 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_statement2556 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_statement2566 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000030L});
    public static final BitSet FOLLOW_expressions_in_statement2568 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_statement2571 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_statement2573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_typeAttr2594 = new BitSet(new long[]{0x2000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_61_in_typeAttr2597 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_typeDef_in_typeAttr2599 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_typeAttr2613 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_typeAttr2615 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs2634 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_typeAttrs2637 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs2639 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_ID_in_typeDef2656 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_typeDef2659 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_typeAttrs_in_typeDef2663 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_typeDef2665 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_varDecl2697 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_ID_in_varDecl2699 = new BitSet(new long[]{0x0000000000000002L,0x0000000000003000L});
    public static final BitSet FOLLOW_76_in_varDecl2702 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_varDecl2704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_varDecl2708 = new BitSet(new long[]{0x401E000000000000L,0x0000CE0800000010L});
    public static final BitSet FOLLOW_expression_in_varDecl2710 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_varDeclNoExpr2721 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_ID_in_varDeclNoExpr2723 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varDecl_in_varDecls2740 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_varDecls2743 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_varDecl_in_varDecls2745 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});

}