// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g 2009-09-28 22:59:15

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

@SuppressWarnings("unused")
public class RVCCalParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACTOR", "INPUTS", "OUTPUTS", "PORT", "EXPR", "EXPR_OR", "EXPR_AND", "EXPR_BITOR", "EXPR_BITAND", "EXPR_EQ", "EXPR_REL", "EXPR_SHIFT", "EXPR_ADD", "EXPR_MUL", "EXPR_EXP", "EXPR_UN", "EXPR_LIST", "EXPR_IF", "EXPR_CALL", "EXPR_IDX", "EXPR_VAR", "EXPR_BOOL", "EXPR_FLOAT", "EXPR_INT", "EXPR_STRING", "ACTOR_DECLS", "FUNCTION", "PROCEDURE", "STATE_VAR", "PARAMETER", "PARAMETERS", "TYPE", "TYPE_ATTRS", "ASSIGNABLE", "NON_ASSIGNABLE", "ID", "FLOAT", "INTEGER", "STRING", "LETTER", "Exponent", "EscapeSequence", "OctalEscape", "LINE_COMMENT", "MULTI_LINE_COMMENT", "WHITESPACE", "'all'", "'any'", "'at'", "'at*'", "'delay'", "'guard'", "':'", "'['", "']'", "','", "'repeat'", "'do'", "'actor'", "'('", "')'", "'==>'", "'end'", "'.'", "'action'", "'var'", "'initialize'", "'='", "':='", "';'", "'function'", "'-->'", "'procedure'", "'begin'", "'import'", "'multi'", "'or'", "'||'", "'and'", "'&&'", "'|'", "'&'", "'!='", "'<'", "'>'", "'<='", "'>='", "'<<'", "'>>'", "'+'", "'-'", "'div'", "'mod'", "'*'", "'/'", "'^'", "'not'", "'#'", "'if'", "'then'", "'else'", "'true'", "'false'", "'for'", "'in'", "'priority'", "'schedule'", "'fsm'", "'regexp'", "'choose'", "'foreach'", "'..'", "'while'"
    };
    public static final int FUNCTION=30;
    public static final int EXPR_BOOL=25;
    public static final int OUTPUTS=6;
    public static final int EXPR_VAR=24;
    public static final int LETTER=43;
    public static final int EXPR_CALL=22;
    public static final int INPUTS=5;
    public static final int EOF=-1;
    public static final int TYPE=35;
    public static final int EXPR_BITOR=11;
    public static final int T__93=93;
    public static final int TYPE_ATTRS=36;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__90=90;
    public static final int PARAMETER=33;
    public static final int STATE_VAR=32;
    public static final int EXPR_UN=19;
    public static final int T__99=99;
    public static final int T__98=98;
    public static final int T__97=97;
    public static final int T__96=96;
    public static final int T__95=95;
    public static final int ASSIGNABLE=37;
    public static final int EXPR_EQ=13;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int LINE_COMMENT=47;
    public static final int WHITESPACE=49;
    public static final int NON_ASSIGNABLE=38;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int EXPR_IDX=23;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int EXPR_ADD=16;
    public static final int EXPR_OR=9;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__70=70;
    public static final int ACTOR_DECLS=29;
    public static final int ACTOR=4;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int T__74=74;
    public static final int EscapeSequence=45;
    public static final int T__73=73;
    public static final int EXPR_AND=10;
    public static final int T__79=79;
    public static final int T__78=78;
    public static final int T__77=77;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int EXPR_SHIFT=15;
    public static final int T__67=67;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int PARAMETERS=34;
    public static final int T__63=63;
    public static final int T__116=116;
    public static final int T__114=114;
    public static final int T__115=115;
    public static final int Exponent=44;
    public static final int FLOAT=40;
    public static final int EXPR_FLOAT=26;
    public static final int ID=39;
    public static final int T__61=61;
    public static final int EXPR_MUL=17;
    public static final int T__60=60;
    public static final int T__55=55;
    public static final int EXPR_LIST=20;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int EXPR=8;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int T__109=109;
    public static final int EXPR_STRING=28;
    public static final int T__59=59;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int T__105=105;
    public static final int T__106=106;
    public static final int T__111=111;
    public static final int T__110=110;
    public static final int T__113=113;
    public static final int T__112=112;
    public static final int EXPR_INT=27;
    public static final int T__50=50;
    public static final int INTEGER=41;
    public static final int EXPR_EXP=18;
    public static final int PORT=7;
    public static final int T__102=102;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int EXPR_IF=21;
    public static final int EXPR_REL=14;
    public static final int MULTI_LINE_COMMENT=48;
    public static final int PROCEDURE=31;
    public static final int EXPR_BITAND=12;
    public static final int OctalEscape=46;
    public static final int STRING=42;

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


    public static class actionChannelSelector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionChannelSelector"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:1: actionChannelSelector : ( 'all' | 'any' | 'at' | 'at*' );
    public final RVCCalParser.actionChannelSelector_return actionChannelSelector() throws RecognitionException {
        RVCCalParser.actionChannelSelector_return retval = new RVCCalParser.actionChannelSelector_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal1=null;
        Token string_literal2=null;
        Token string_literal3=null;
        Token string_literal4=null;

        Object string_literal1_tree=null;
        Object string_literal2_tree=null;
        Object string_literal3_tree=null;
        Object string_literal4_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:100:22: ( 'all' | 'any' | 'at' | 'at*' )
            int alt1=4;
            switch ( input.LA(1) ) {
            case 50:
                {
                alt1=1;
                }
                break;
            case 51:
                {
                alt1=2;
                }
                break;
            case 52:
                {
                alt1=3;
                }
                break;
            case 53:
                {
                alt1=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:101:3: 'all'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal1=(Token)match(input,50,FOLLOW_50_in_actionChannelSelector268); 
                    string_literal1_tree = (Object)adaptor.create(string_literal1);
                    adaptor.addChild(root_0, string_literal1_tree);

                     System.out.println("TODO: throw exception channel selectors"); 

                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:102:3: 'any'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal2=(Token)match(input,51,FOLLOW_51_in_actionChannelSelector274); 
                    string_literal2_tree = (Object)adaptor.create(string_literal2);
                    adaptor.addChild(root_0, string_literal2_tree);

                     System.out.println("TODO: throw exception channel selectors"); 

                    }
                    break;
                case 3 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:103:3: 'at'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal3=(Token)match(input,52,FOLLOW_52_in_actionChannelSelector280); 
                    string_literal3_tree = (Object)adaptor.create(string_literal3);
                    adaptor.addChild(root_0, string_literal3_tree);

                     System.out.println("TODO: throw exception channel selectors"); 

                    }
                    break;
                case 4 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:104:3: 'at*'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal4=(Token)match(input,53,FOLLOW_53_in_actionChannelSelector286); 
                    string_literal4_tree = (Object)adaptor.create(string_literal4);
                    adaptor.addChild(root_0, string_literal4_tree);

                     System.out.println("TODO: throw exception channel selectors"); 

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
    // $ANTLR end "actionChannelSelector"

    public static class actionDelay_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionDelay"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:106:1: actionDelay : 'delay' expression ;
    public final RVCCalParser.actionDelay_return actionDelay() throws RecognitionException {
        RVCCalParser.actionDelay_return retval = new RVCCalParser.actionDelay_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal5=null;
        RVCCalParser.expression_return expression6 = null;


        Object string_literal5_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:106:12: ( 'delay' expression )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:106:14: 'delay' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal5=(Token)match(input,54,FOLLOW_54_in_actionDelay295); 
            string_literal5_tree = (Object)adaptor.create(string_literal5);
            adaptor.addChild(root_0, string_literal5_tree);

            pushFollow(FOLLOW_expression_in_actionDelay297);
            expression6=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression6.getTree());
             System.out.println("TODO: throw exception no delay"); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "actionDelay"

    public static class actionGuards_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionGuards"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:1: actionGuards : 'guard' expressions ;
    public final RVCCalParser.actionGuards_return actionGuards() throws RecognitionException {
        RVCCalParser.actionGuards_return retval = new RVCCalParser.actionGuards_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal7=null;
        RVCCalParser.expressions_return expressions8 = null;


        Object string_literal7_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:13: ( 'guard' expressions )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:15: 'guard' expressions
            {
            root_0 = (Object)adaptor.nil();

            string_literal7=(Token)match(input,55,FOLLOW_55_in_actionGuards307); 
            string_literal7_tree = (Object)adaptor.create(string_literal7);
            adaptor.addChild(root_0, string_literal7_tree);

            pushFollow(FOLLOW_expressions_in_actionGuards309);
            expressions8=expressions();

            state._fsp--;

            adaptor.addChild(root_0, expressions8.getTree());
             

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:111:1: actionInput : ( ID ':' )? '[' idents ']' ( actionRepeat )? ( actionChannelSelector )? ;
    public final RVCCalParser.actionInput_return actionInput() throws RecognitionException {
        RVCCalParser.actionInput_return retval = new RVCCalParser.actionInput_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID9=null;
        Token char_literal10=null;
        Token char_literal11=null;
        Token char_literal13=null;
        RVCCalParser.idents_return idents12 = null;

        RVCCalParser.actionRepeat_return actionRepeat14 = null;

        RVCCalParser.actionChannelSelector_return actionChannelSelector15 = null;


        Object ID9_tree=null;
        Object char_literal10_tree=null;
        Object char_literal11_tree=null;
        Object char_literal13_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:111:12: ( ( ID ':' )? '[' idents ']' ( actionRepeat )? ( actionChannelSelector )? )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:2: ( ID ':' )? '[' idents ']' ( actionRepeat )? ( actionChannelSelector )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:2: ( ID ':' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==ID) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:3: ID ':'
                    {
                    ID9=(Token)match(input,ID,FOLLOW_ID_in_actionInput320); 
                    ID9_tree = (Object)adaptor.create(ID9);
                    adaptor.addChild(root_0, ID9_tree);

                    char_literal10=(Token)match(input,56,FOLLOW_56_in_actionInput322); 
                    char_literal10_tree = (Object)adaptor.create(char_literal10);
                    adaptor.addChild(root_0, char_literal10_tree);


                    }
                    break;

            }

            char_literal11=(Token)match(input,57,FOLLOW_57_in_actionInput326); 
            char_literal11_tree = (Object)adaptor.create(char_literal11);
            adaptor.addChild(root_0, char_literal11_tree);

            pushFollow(FOLLOW_idents_in_actionInput328);
            idents12=idents();

            state._fsp--;

            adaptor.addChild(root_0, idents12.getTree());
            char_literal13=(Token)match(input,58,FOLLOW_58_in_actionInput330); 
            char_literal13_tree = (Object)adaptor.create(char_literal13);
            adaptor.addChild(root_0, char_literal13_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:27: ( actionRepeat )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==60) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:27: actionRepeat
                    {
                    pushFollow(FOLLOW_actionRepeat_in_actionInput332);
                    actionRepeat14=actionRepeat();

                    state._fsp--;

                    adaptor.addChild(root_0, actionRepeat14.getTree());

                    }
                    break;

            }

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:41: ( actionChannelSelector )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( ((LA4_0>=50 && LA4_0<=53)) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:41: actionChannelSelector
                    {
                    pushFollow(FOLLOW_actionChannelSelector_in_actionInput335);
                    actionChannelSelector15=actionChannelSelector();

                    state._fsp--;

                    adaptor.addChild(root_0, actionChannelSelector15.getTree());

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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:1: actionInputs : actionInput ( ',' actionInput )* ;
    public final RVCCalParser.actionInputs_return actionInputs() throws RecognitionException {
        RVCCalParser.actionInputs_return retval = new RVCCalParser.actionInputs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal17=null;
        RVCCalParser.actionInput_return actionInput16 = null;

        RVCCalParser.actionInput_return actionInput18 = null;


        Object char_literal17_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:13: ( actionInput ( ',' actionInput )* )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:15: actionInput ( ',' actionInput )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_actionInput_in_actionInputs346);
            actionInput16=actionInput();

            state._fsp--;

            adaptor.addChild(root_0, actionInput16.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:27: ( ',' actionInput )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==59) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:28: ',' actionInput
            	    {
            	    char_literal17=(Token)match(input,59,FOLLOW_59_in_actionInputs349); 
            	    char_literal17_tree = (Object)adaptor.create(char_literal17);
            	    adaptor.addChild(root_0, char_literal17_tree);

            	    pushFollow(FOLLOW_actionInput_in_actionInputs351);
            	    actionInput18=actionInput();

            	    state._fsp--;

            	    adaptor.addChild(root_0, actionInput18.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
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
    // $ANTLR end "actionInputs"

    public static class actionOutput_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionOutput"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:1: actionOutput : ( ID ':' )? '[' expressions ']' ( actionRepeat )? ( actionChannelSelector )? ;
    public final RVCCalParser.actionOutput_return actionOutput() throws RecognitionException {
        RVCCalParser.actionOutput_return retval = new RVCCalParser.actionOutput_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID19=null;
        Token char_literal20=null;
        Token char_literal21=null;
        Token char_literal23=null;
        RVCCalParser.expressions_return expressions22 = null;

        RVCCalParser.actionRepeat_return actionRepeat24 = null;

        RVCCalParser.actionChannelSelector_return actionChannelSelector25 = null;


        Object ID19_tree=null;
        Object char_literal20_tree=null;
        Object char_literal21_tree=null;
        Object char_literal23_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:13: ( ( ID ':' )? '[' expressions ']' ( actionRepeat )? ( actionChannelSelector )? )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:118:2: ( ID ':' )? '[' expressions ']' ( actionRepeat )? ( actionChannelSelector )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:118:2: ( ID ':' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==ID) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:118:3: ID ':'
                    {
                    ID19=(Token)match(input,ID,FOLLOW_ID_in_actionOutput364); 
                    ID19_tree = (Object)adaptor.create(ID19);
                    adaptor.addChild(root_0, ID19_tree);

                    char_literal20=(Token)match(input,56,FOLLOW_56_in_actionOutput366); 
                    char_literal20_tree = (Object)adaptor.create(char_literal20);
                    adaptor.addChild(root_0, char_literal20_tree);


                    }
                    break;

            }

            char_literal21=(Token)match(input,57,FOLLOW_57_in_actionOutput370); 
            char_literal21_tree = (Object)adaptor.create(char_literal21);
            adaptor.addChild(root_0, char_literal21_tree);

            pushFollow(FOLLOW_expressions_in_actionOutput372);
            expressions22=expressions();

            state._fsp--;

            adaptor.addChild(root_0, expressions22.getTree());
            char_literal23=(Token)match(input,58,FOLLOW_58_in_actionOutput374); 
            char_literal23_tree = (Object)adaptor.create(char_literal23);
            adaptor.addChild(root_0, char_literal23_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:118:32: ( actionRepeat )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==60) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:118:32: actionRepeat
                    {
                    pushFollow(FOLLOW_actionRepeat_in_actionOutput376);
                    actionRepeat24=actionRepeat();

                    state._fsp--;

                    adaptor.addChild(root_0, actionRepeat24.getTree());

                    }
                    break;

            }

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:118:46: ( actionChannelSelector )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0>=50 && LA8_0<=53)) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:118:46: actionChannelSelector
                    {
                    pushFollow(FOLLOW_actionChannelSelector_in_actionOutput379);
                    actionChannelSelector25=actionChannelSelector();

                    state._fsp--;

                    adaptor.addChild(root_0, actionChannelSelector25.getTree());

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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:1: actionOutputs : actionOutput ( ',' actionOutput )* ;
    public final RVCCalParser.actionOutputs_return actionOutputs() throws RecognitionException {
        RVCCalParser.actionOutputs_return retval = new RVCCalParser.actionOutputs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal27=null;
        RVCCalParser.actionOutput_return actionOutput26 = null;

        RVCCalParser.actionOutput_return actionOutput28 = null;


        Object char_literal27_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:14: ( actionOutput ( ',' actionOutput )* )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:16: actionOutput ( ',' actionOutput )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_actionOutput_in_actionOutputs390);
            actionOutput26=actionOutput();

            state._fsp--;

            adaptor.addChild(root_0, actionOutput26.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:29: ( ',' actionOutput )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==59) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:121:30: ',' actionOutput
            	    {
            	    char_literal27=(Token)match(input,59,FOLLOW_59_in_actionOutputs393); 
            	    char_literal27_tree = (Object)adaptor.create(char_literal27);
            	    adaptor.addChild(root_0, char_literal27_tree);

            	    pushFollow(FOLLOW_actionOutput_in_actionOutputs395);
            	    actionOutput28=actionOutput();

            	    state._fsp--;

            	    adaptor.addChild(root_0, actionOutput28.getTree());

            	    }
            	    break;

            	default :
            	    break loop9;
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
    // $ANTLR end "actionOutputs"

    public static class actionRepeat_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionRepeat"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:123:1: actionRepeat : 'repeat' expression ;
    public final RVCCalParser.actionRepeat_return actionRepeat() throws RecognitionException {
        RVCCalParser.actionRepeat_return retval = new RVCCalParser.actionRepeat_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal29=null;
        RVCCalParser.expression_return expression30 = null;


        Object string_literal29_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:123:13: ( 'repeat' expression )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:123:15: 'repeat' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal29=(Token)match(input,60,FOLLOW_60_in_actionRepeat406); 
            string_literal29_tree = (Object)adaptor.create(string_literal29);
            adaptor.addChild(root_0, string_literal29_tree);

            pushFollow(FOLLOW_expression_in_actionRepeat408);
            expression30=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression30.getTree());
             

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:1: actionStatements : 'do' ( statement )* ;
    public final RVCCalParser.actionStatements_return actionStatements() throws RecognitionException {
        RVCCalParser.actionStatements_return retval = new RVCCalParser.actionStatements_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal31=null;
        RVCCalParser.statement_return statement32 = null;


        Object string_literal31_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:17: ( 'do' ( statement )* )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:19: 'do' ( statement )*
            {
            root_0 = (Object)adaptor.nil();

            string_literal31=(Token)match(input,61,FOLLOW_61_in_actionStatements417); 
            string_literal31_tree = (Object)adaptor.create(string_literal31);
            adaptor.addChild(root_0, string_literal31_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:24: ( statement )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==ID||LA10_0==77||LA10_0==102||LA10_0==107||(LA10_0>=113 && LA10_0<=114)||LA10_0==116) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:24: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_actionStatements419);
            	    statement32=statement();

            	    state._fsp--;

            	    adaptor.addChild(root_0, statement32.getTree());

            	    }
            	    break;

            	default :
            	    break loop10;
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
    // $ANTLR end "actionStatements"

    public static class actor_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actor"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:127:1: actor : ( actorImport )* 'actor' id= ID ( '[' ( typePars )? ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' actorDeclarations 'end' EOF -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS actorDeclarations ) ;
    public final RVCCalParser.actor_return actor() throws RecognitionException {
        RVCCalParser.actor_return retval = new RVCCalParser.actor_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token id=null;
        Token string_literal34=null;
        Token char_literal35=null;
        Token char_literal37=null;
        Token char_literal38=null;
        Token char_literal40=null;
        Token string_literal41=null;
        Token char_literal42=null;
        Token string_literal44=null;
        Token EOF45=null;
        RVCCalParser.actorPortDecls_return inputs = null;

        RVCCalParser.actorPortDecls_return outputs = null;

        RVCCalParser.actorImport_return actorImport33 = null;

        RVCCalParser.typePars_return typePars36 = null;

        RVCCalParser.actorParameters_return actorParameters39 = null;

        RVCCalParser.actorDeclarations_return actorDeclarations43 = null;


        Object id_tree=null;
        Object string_literal34_tree=null;
        Object char_literal35_tree=null;
        Object char_literal37_tree=null;
        Object char_literal38_tree=null;
        Object char_literal40_tree=null;
        Object string_literal41_tree=null;
        Object char_literal42_tree=null;
        Object string_literal44_tree=null;
        Object EOF45_tree=null;
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleTokenStream stream_62=new RewriteRuleTokenStream(adaptor,"token 62");
        RewriteRuleTokenStream stream_63=new RewriteRuleTokenStream(adaptor,"token 63");
        RewriteRuleSubtreeStream stream_typePars=new RewriteRuleSubtreeStream(adaptor,"rule typePars");
        RewriteRuleSubtreeStream stream_actorImport=new RewriteRuleSubtreeStream(adaptor,"rule actorImport");
        RewriteRuleSubtreeStream stream_actorDeclarations=new RewriteRuleSubtreeStream(adaptor,"rule actorDeclarations");
        RewriteRuleSubtreeStream stream_actorPortDecls=new RewriteRuleSubtreeStream(adaptor,"rule actorPortDecls");
        RewriteRuleSubtreeStream stream_actorParameters=new RewriteRuleSubtreeStream(adaptor,"rule actorParameters");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:6: ( ( actorImport )* 'actor' id= ID ( '[' ( typePars )? ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' actorDeclarations 'end' EOF -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS actorDeclarations ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:8: ( actorImport )* 'actor' id= ID ( '[' ( typePars )? ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' actorDeclarations 'end' EOF
            {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:8: ( actorImport )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==78) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:8: actorImport
            	    {
            	    pushFollow(FOLLOW_actorImport_in_actor434);
            	    actorImport33=actorImport();

            	    state._fsp--;

            	    stream_actorImport.add(actorImport33.getTree());

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            string_literal34=(Token)match(input,62,FOLLOW_62_in_actor437);  
            stream_62.add(string_literal34);

            id=(Token)match(input,ID,FOLLOW_ID_in_actor441);  
            stream_ID.add(id);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:35: ( '[' ( typePars )? ']' )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==57) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:36: '[' ( typePars )? ']'
                    {
                    char_literal35=(Token)match(input,57,FOLLOW_57_in_actor444);  
                    stream_57.add(char_literal35);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:40: ( typePars )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==ID) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:40: typePars
                            {
                            pushFollow(FOLLOW_typePars_in_actor446);
                            typePars36=typePars();

                            state._fsp--;

                            stream_typePars.add(typePars36.getTree());

                            }
                            break;

                    }

                    char_literal37=(Token)match(input,58,FOLLOW_58_in_actor449);  
                    stream_58.add(char_literal37);


                    }
                    break;

            }

            char_literal38=(Token)match(input,63,FOLLOW_63_in_actor453);  
            stream_63.add(char_literal38);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:60: ( actorParameters )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==ID) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:60: actorParameters
                    {
                    pushFollow(FOLLOW_actorParameters_in_actor455);
                    actorParameters39=actorParameters();

                    state._fsp--;

                    stream_actorParameters.add(actorParameters39.getTree());

                    }
                    break;

            }

            char_literal40=(Token)match(input,64,FOLLOW_64_in_actor458);  
            stream_64.add(char_literal40);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:8: (inputs= actorPortDecls )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==ID||LA15_0==79) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:8: inputs= actorPortDecls
                    {
                    pushFollow(FOLLOW_actorPortDecls_in_actor463);
                    inputs=actorPortDecls();

                    state._fsp--;

                    stream_actorPortDecls.add(inputs.getTree());

                    }
                    break;

            }

            string_literal41=(Token)match(input,65,FOLLOW_65_in_actor466);  
            stream_65.add(string_literal41);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:38: (outputs= actorPortDecls )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==ID||LA16_0==79) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:38: outputs= actorPortDecls
                    {
                    pushFollow(FOLLOW_actorPortDecls_in_actor470);
                    outputs=actorPortDecls();

                    state._fsp--;

                    stream_actorPortDecls.add(outputs.getTree());

                    }
                    break;

            }

            char_literal42=(Token)match(input,56,FOLLOW_56_in_actor473);  
            stream_56.add(char_literal42);

            pushFollow(FOLLOW_actorDeclarations_in_actor476);
            actorDeclarations43=actorDeclarations();

            state._fsp--;

            stream_actorDeclarations.add(actorDeclarations43.getTree());
            string_literal44=(Token)match(input,66,FOLLOW_66_in_actor478);  
            stream_66.add(string_literal44);

            EOF45=(Token)match(input,EOF,FOLLOW_EOF_in_actor480);  
            stream_EOF.add(EOF45);



            // AST REWRITE
            // elements: 62, inputs, actorParameters, id, actorDeclarations, outputs
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
                adaptor.addChild(root_0, stream_62.nextNode());
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:139:1: actorDeclaration : ( ID ( ( ( '.' ID )* ':' ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' ) ) | ( '[' typePars ']' | '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' ) | 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | priorityOrder | 'function' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end' -> FUNCTION | 'procedure' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end' -> PROCEDURE );
    public final RVCCalParser.actorDeclaration_return actorDeclaration() throws RecognitionException {
        RVCCalParser.actorDeclaration_return retval = new RVCCalParser.actorDeclaration_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token varName=null;
        Token ID46=null;
        Token char_literal47=null;
        Token ID48=null;
        Token char_literal49=null;
        Token string_literal50=null;
        Token string_literal52=null;
        Token string_literal56=null;
        Token string_literal59=null;
        Token string_literal60=null;
        Token string_literal61=null;
        Token string_literal65=null;
        Token string_literal68=null;
        Token char_literal69=null;
        Token char_literal71=null;
        Token char_literal72=null;
        Token char_literal73=null;
        Token char_literal74=null;
        Token string_literal76=null;
        Token char_literal78=null;
        Token string_literal79=null;
        Token string_literal81=null;
        Token string_literal85=null;
        Token string_literal88=null;
        Token string_literal89=null;
        Token string_literal90=null;
        Token string_literal94=null;
        Token string_literal97=null;
        Token string_literal99=null;
        Token ID100=null;
        Token char_literal101=null;
        Token char_literal103=null;
        Token char_literal105=null;
        Token string_literal106=null;
        Token string_literal108=null;
        Token char_literal110=null;
        Token string_literal112=null;
        Token string_literal113=null;
        Token ID114=null;
        Token char_literal115=null;
        Token char_literal117=null;
        Token char_literal119=null;
        Token string_literal120=null;
        Token string_literal122=null;
        Token string_literal124=null;
        RVCCalParser.typeAttrs_return attrs = null;

        RVCCalParser.actionInputs_return actionInputs51 = null;

        RVCCalParser.actionOutputs_return actionOutputs53 = null;

        RVCCalParser.actionGuards_return actionGuards54 = null;

        RVCCalParser.actionDelay_return actionDelay55 = null;

        RVCCalParser.varDecls_return varDecls57 = null;

        RVCCalParser.actionStatements_return actionStatements58 = null;

        RVCCalParser.actionOutputs_return actionOutputs62 = null;

        RVCCalParser.actionGuards_return actionGuards63 = null;

        RVCCalParser.actionDelay_return actionDelay64 = null;

        RVCCalParser.varDecls_return varDecls66 = null;

        RVCCalParser.actionStatements_return actionStatements67 = null;

        RVCCalParser.typePars_return typePars70 = null;

        RVCCalParser.expression_return expression75 = null;

        RVCCalParser.expression_return expression77 = null;

        RVCCalParser.actionInputs_return actionInputs80 = null;

        RVCCalParser.actionOutputs_return actionOutputs82 = null;

        RVCCalParser.actionGuards_return actionGuards83 = null;

        RVCCalParser.actionDelay_return actionDelay84 = null;

        RVCCalParser.varDecls_return varDecls86 = null;

        RVCCalParser.actionStatements_return actionStatements87 = null;

        RVCCalParser.actionOutputs_return actionOutputs91 = null;

        RVCCalParser.actionGuards_return actionGuards92 = null;

        RVCCalParser.actionDelay_return actionDelay93 = null;

        RVCCalParser.varDecls_return varDecls95 = null;

        RVCCalParser.actionStatements_return actionStatements96 = null;

        RVCCalParser.priorityOrder_return priorityOrder98 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr102 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr104 = null;

        RVCCalParser.typeDef_return typeDef107 = null;

        RVCCalParser.varDecls_return varDecls109 = null;

        RVCCalParser.expression_return expression111 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr116 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr118 = null;

        RVCCalParser.varDecls_return varDecls121 = null;

        RVCCalParser.statement_return statement123 = null;


        Object varName_tree=null;
        Object ID46_tree=null;
        Object char_literal47_tree=null;
        Object ID48_tree=null;
        Object char_literal49_tree=null;
        Object string_literal50_tree=null;
        Object string_literal52_tree=null;
        Object string_literal56_tree=null;
        Object string_literal59_tree=null;
        Object string_literal60_tree=null;
        Object string_literal61_tree=null;
        Object string_literal65_tree=null;
        Object string_literal68_tree=null;
        Object char_literal69_tree=null;
        Object char_literal71_tree=null;
        Object char_literal72_tree=null;
        Object char_literal73_tree=null;
        Object char_literal74_tree=null;
        Object string_literal76_tree=null;
        Object char_literal78_tree=null;
        Object string_literal79_tree=null;
        Object string_literal81_tree=null;
        Object string_literal85_tree=null;
        Object string_literal88_tree=null;
        Object string_literal89_tree=null;
        Object string_literal90_tree=null;
        Object string_literal94_tree=null;
        Object string_literal97_tree=null;
        Object string_literal99_tree=null;
        Object ID100_tree=null;
        Object char_literal101_tree=null;
        Object char_literal103_tree=null;
        Object char_literal105_tree=null;
        Object string_literal106_tree=null;
        Object string_literal108_tree=null;
        Object char_literal110_tree=null;
        Object string_literal112_tree=null;
        Object string_literal113_tree=null;
        Object ID114_tree=null;
        Object char_literal115_tree=null;
        Object char_literal117_tree=null;
        Object char_literal119_tree=null;
        Object string_literal120_tree=null;
        Object string_literal122_tree=null;
        Object string_literal124_tree=null;
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_69=new RewriteRuleTokenStream(adaptor,"token 69");
        RewriteRuleTokenStream stream_77=new RewriteRuleTokenStream(adaptor,"token 77");
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleTokenStream stream_68=new RewriteRuleTokenStream(adaptor,"token 68");
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_70=new RewriteRuleTokenStream(adaptor,"token 70");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");
        RewriteRuleTokenStream stream_72=new RewriteRuleTokenStream(adaptor,"token 72");
        RewriteRuleTokenStream stream_63=new RewriteRuleTokenStream(adaptor,"token 63");
        RewriteRuleTokenStream stream_73=new RewriteRuleTokenStream(adaptor,"token 73");
        RewriteRuleTokenStream stream_74=new RewriteRuleTokenStream(adaptor,"token 74");
        RewriteRuleTokenStream stream_75=new RewriteRuleTokenStream(adaptor,"token 75");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        RewriteRuleSubtreeStream stream_varDecls=new RewriteRuleSubtreeStream(adaptor,"rule varDecls");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_actionOutputs=new RewriteRuleSubtreeStream(adaptor,"rule actionOutputs");
        RewriteRuleSubtreeStream stream_typePars=new RewriteRuleSubtreeStream(adaptor,"rule typePars");
        RewriteRuleSubtreeStream stream_actionInputs=new RewriteRuleSubtreeStream(adaptor,"rule actionInputs");
        RewriteRuleSubtreeStream stream_actionGuards=new RewriteRuleSubtreeStream(adaptor,"rule actionGuards");
        RewriteRuleSubtreeStream stream_actionDelay=new RewriteRuleSubtreeStream(adaptor,"rule actionDelay");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        RewriteRuleSubtreeStream stream_actionStatements=new RewriteRuleSubtreeStream(adaptor,"rule actionStatements");
        RewriteRuleSubtreeStream stream_typeAttrs=new RewriteRuleSubtreeStream(adaptor,"rule typeAttrs");
        RewriteRuleSubtreeStream stream_varDeclNoExpr=new RewriteRuleSubtreeStream(adaptor,"rule varDeclNoExpr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:142:17: ( ID ( ( ( '.' ID )* ':' ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' ) ) | ( '[' typePars ']' | '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' ) | 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | priorityOrder | 'function' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end' -> FUNCTION | 'procedure' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end' -> PROCEDURE )
            int alt51=6;
            switch ( input.LA(1) ) {
            case ID:
                {
                alt51=1;
                }
                break;
            case 68:
                {
                alt51=2;
                }
                break;
            case 70:
                {
                alt51=3;
                }
                break;
            case 109:
                {
                alt51=4;
                }
                break;
            case 74:
                {
                alt51=5;
                }
                break;
            case 76:
                {
                alt51=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;
            }

            switch (alt51) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:147:3: ID ( ( ( '.' ID )* ':' ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' ) ) | ( '[' typePars ']' | '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' )
                    {
                    ID46=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration545);  
                    stream_ID.add(ID46);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:147:6: ( ( ( '.' ID )* ':' ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' ) ) | ( '[' typePars ']' | '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' )
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==56||LA32_0==67) ) {
                        alt32=1;
                    }
                    else if ( (LA32_0==ID||LA32_0==57||LA32_0==63) ) {
                        alt32=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 32, 0, input);

                        throw nvae;
                    }
                    switch (alt32) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:5: ( ( '.' ID )* ':' ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' ) )
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:5: ( ( '.' ID )* ':' ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' ) )
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:6: ( '.' ID )* ':' ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' )
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:6: ( '.' ID )*
                            loop17:
                            do {
                                int alt17=2;
                                int LA17_0 = input.LA(1);

                                if ( (LA17_0==67) ) {
                                    alt17=1;
                                }


                                switch (alt17) {
                            	case 1 :
                            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:7: '.' ID
                            	    {
                            	    char_literal47=(Token)match(input,67,FOLLOW_67_in_actorDeclaration555);  
                            	    stream_67.add(char_literal47);

                            	    ID48=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration557);  
                            	    stream_ID.add(ID48);


                            	    }
                            	    break;

                            	default :
                            	    break loop17;
                                }
                            } while (true);

                            char_literal49=(Token)match(input,56,FOLLOW_56_in_actorDeclaration561);  
                            stream_56.add(char_literal49);

                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:7: ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' )
                            int alt29=2;
                            int LA29_0 = input.LA(1);

                            if ( (LA29_0==68) ) {
                                alt29=1;
                            }
                            else if ( (LA29_0==70) ) {
                                alt29=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 29, 0, input);

                                throw nvae;
                            }
                            switch (alt29) {
                                case 1 :
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:8: 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end'
                                    {
                                    string_literal50=(Token)match(input,68,FOLLOW_68_in_actorDeclaration570);  
                                    stream_68.add(string_literal50);

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:17: ( actionInputs )?
                                    int alt18=2;
                                    int LA18_0 = input.LA(1);

                                    if ( (LA18_0==ID||LA18_0==57) ) {
                                        alt18=1;
                                    }
                                    switch (alt18) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:17: actionInputs
                                            {
                                            pushFollow(FOLLOW_actionInputs_in_actorDeclaration572);
                                            actionInputs51=actionInputs();

                                            state._fsp--;

                                            stream_actionInputs.add(actionInputs51.getTree());

                                            }
                                            break;

                                    }

                                    string_literal52=(Token)match(input,65,FOLLOW_65_in_actorDeclaration575);  
                                    stream_65.add(string_literal52);

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:37: ( actionOutputs )?
                                    int alt19=2;
                                    int LA19_0 = input.LA(1);

                                    if ( (LA19_0==ID||LA19_0==57) ) {
                                        alt19=1;
                                    }
                                    switch (alt19) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:37: actionOutputs
                                            {
                                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration577);
                                            actionOutputs53=actionOutputs();

                                            state._fsp--;

                                            stream_actionOutputs.add(actionOutputs53.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:52: ( actionGuards )?
                                    int alt20=2;
                                    int LA20_0 = input.LA(1);

                                    if ( (LA20_0==55) ) {
                                        alt20=1;
                                    }
                                    switch (alt20) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:52: actionGuards
                                            {
                                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration580);
                                            actionGuards54=actionGuards();

                                            state._fsp--;

                                            stream_actionGuards.add(actionGuards54.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:66: ( actionDelay )?
                                    int alt21=2;
                                    int LA21_0 = input.LA(1);

                                    if ( (LA21_0==54) ) {
                                        alt21=1;
                                    }
                                    switch (alt21) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:66: actionDelay
                                            {
                                            pushFollow(FOLLOW_actionDelay_in_actorDeclaration583);
                                            actionDelay55=actionDelay();

                                            state._fsp--;

                                            stream_actionDelay.add(actionDelay55.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:79: ( 'var' varDecls )?
                                    int alt22=2;
                                    int LA22_0 = input.LA(1);

                                    if ( (LA22_0==69) ) {
                                        alt22=1;
                                    }
                                    switch (alt22) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:80: 'var' varDecls
                                            {
                                            string_literal56=(Token)match(input,69,FOLLOW_69_in_actorDeclaration587);  
                                            stream_69.add(string_literal56);

                                            pushFollow(FOLLOW_varDecls_in_actorDeclaration589);
                                            varDecls57=varDecls();

                                            state._fsp--;

                                            stream_varDecls.add(varDecls57.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:97: ( actionStatements )?
                                    int alt23=2;
                                    int LA23_0 = input.LA(1);

                                    if ( (LA23_0==61) ) {
                                        alt23=1;
                                    }
                                    switch (alt23) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:97: actionStatements
                                            {
                                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration593);
                                            actionStatements58=actionStatements();

                                            state._fsp--;

                                            stream_actionStatements.add(actionStatements58.getTree());

                                            }
                                            break;

                                    }

                                    string_literal59=(Token)match(input,66,FOLLOW_66_in_actorDeclaration596);  
                                    stream_66.add(string_literal59);

                                     

                                    }
                                    break;
                                case 2 :
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:7: 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end'
                                    {
                                    string_literal60=(Token)match(input,70,FOLLOW_70_in_actorDeclaration615);  
                                    stream_70.add(string_literal60);

                                    string_literal61=(Token)match(input,65,FOLLOW_65_in_actorDeclaration617);  
                                    stream_65.add(string_literal61);

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:26: ( actionOutputs )?
                                    int alt24=2;
                                    int LA24_0 = input.LA(1);

                                    if ( (LA24_0==ID||LA24_0==57) ) {
                                        alt24=1;
                                    }
                                    switch (alt24) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:26: actionOutputs
                                            {
                                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration619);
                                            actionOutputs62=actionOutputs();

                                            state._fsp--;

                                            stream_actionOutputs.add(actionOutputs62.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:41: ( actionGuards )?
                                    int alt25=2;
                                    int LA25_0 = input.LA(1);

                                    if ( (LA25_0==55) ) {
                                        alt25=1;
                                    }
                                    switch (alt25) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:41: actionGuards
                                            {
                                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration622);
                                            actionGuards63=actionGuards();

                                            state._fsp--;

                                            stream_actionGuards.add(actionGuards63.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:55: ( actionDelay )?
                                    int alt26=2;
                                    int LA26_0 = input.LA(1);

                                    if ( (LA26_0==54) ) {
                                        alt26=1;
                                    }
                                    switch (alt26) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:55: actionDelay
                                            {
                                            pushFollow(FOLLOW_actionDelay_in_actorDeclaration625);
                                            actionDelay64=actionDelay();

                                            state._fsp--;

                                            stream_actionDelay.add(actionDelay64.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:68: ( 'var' varDecls )?
                                    int alt27=2;
                                    int LA27_0 = input.LA(1);

                                    if ( (LA27_0==69) ) {
                                        alt27=1;
                                    }
                                    switch (alt27) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:69: 'var' varDecls
                                            {
                                            string_literal65=(Token)match(input,69,FOLLOW_69_in_actorDeclaration629);  
                                            stream_69.add(string_literal65);

                                            pushFollow(FOLLOW_varDecls_in_actorDeclaration631);
                                            varDecls66=varDecls();

                                            state._fsp--;

                                            stream_varDecls.add(varDecls66.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:86: ( actionStatements )?
                                    int alt28=2;
                                    int LA28_0 = input.LA(1);

                                    if ( (LA28_0==61) ) {
                                        alt28=1;
                                    }
                                    switch (alt28) {
                                        case 1 :
                                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:152:86: actionStatements
                                            {
                                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration635);
                                            actionStatements67=actionStatements();

                                            state._fsp--;

                                            stream_actionStatements.add(actionStatements67.getTree());

                                            }
                                            break;

                                    }

                                    string_literal68=(Token)match(input,66,FOLLOW_66_in_actorDeclaration638);  
                                    stream_66.add(string_literal68);

                                     

                                    }
                                    break;

                            }


                            }


                            }
                            break;
                        case 2 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:158:5: ( '[' typePars ']' | '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';'
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:158:5: ( '[' typePars ']' | '(' attrs= typeAttrs ')' )?
                            int alt30=3;
                            int LA30_0 = input.LA(1);

                            if ( (LA30_0==57) ) {
                                alt30=1;
                            }
                            else if ( (LA30_0==63) ) {
                                alt30=2;
                            }
                            switch (alt30) {
                                case 1 :
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:158:6: '[' typePars ']'
                                    {
                                    char_literal69=(Token)match(input,57,FOLLOW_57_in_actorDeclaration666);  
                                    stream_57.add(char_literal69);

                                    pushFollow(FOLLOW_typePars_in_actorDeclaration668);
                                    typePars70=typePars();

                                    state._fsp--;

                                    stream_typePars.add(typePars70.getTree());
                                    char_literal71=(Token)match(input,58,FOLLOW_58_in_actorDeclaration670);  
                                    stream_58.add(char_literal71);


                                    }
                                    break;
                                case 2 :
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:158:25: '(' attrs= typeAttrs ')'
                                    {
                                    char_literal72=(Token)match(input,63,FOLLOW_63_in_actorDeclaration674);  
                                    stream_63.add(char_literal72);

                                    pushFollow(FOLLOW_typeAttrs_in_actorDeclaration678);
                                    attrs=typeAttrs();

                                    state._fsp--;

                                    stream_typeAttrs.add(attrs.getTree());
                                    char_literal73=(Token)match(input,64,FOLLOW_64_in_actorDeclaration680);  
                                    stream_64.add(char_literal73);


                                    }
                                    break;

                            }

                            varName=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration690);  
                            stream_ID.add(varName);

                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:160:5: ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) )
                            int alt31=3;
                            switch ( input.LA(1) ) {
                            case 71:
                                {
                                alt31=1;
                                }
                                break;
                            case 72:
                                {
                                alt31=2;
                                }
                                break;
                            case 73:
                                {
                                alt31=3;
                                }
                                break;
                            default:
                                NoViableAltException nvae =
                                    new NoViableAltException("", 31, 0, input);

                                throw nvae;
                            }

                            switch (alt31) {
                                case 1 :
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:160:8: '=' expression
                                    {
                                    char_literal74=(Token)match(input,71,FOLLOW_71_in_actorDeclaration699);  
                                    stream_71.add(char_literal74);

                                    pushFollow(FOLLOW_expression_in_actorDeclaration701);
                                    expression75=expression();

                                    state._fsp--;

                                    stream_expression.add(expression75.getTree());


                                    // AST REWRITE
                                    // elements: expression, varName, ID, attrs
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
                                    // 160:23: -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression )
                                    {
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:160:26: ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:160:38: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:160:48: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:160:61: ( $attrs)?
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
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:8: ':=' expression
                                    {
                                    string_literal76=(Token)match(input,72,FOLLOW_72_in_actorDeclaration737);  
                                    stream_72.add(string_literal76);

                                    pushFollow(FOLLOW_expression_in_actorDeclaration739);
                                    expression77=expression();

                                    state._fsp--;

                                    stream_expression.add(expression77.getTree());


                                    // AST REWRITE
                                    // elements: varName, ID, attrs, expression
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
                                    // 161:24: -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression )
                                    {
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:27: ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:39: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:49: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:62: ( $attrs)?
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
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:8: 
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
                                    // 162:8: -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE )
                                    {
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:11: ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:23: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:33: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:162:46: ( $attrs)?
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

                            char_literal78=(Token)match(input,73,FOLLOW_73_in_actorDeclaration801);  
                            stream_73.add(char_literal78);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:3: 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal79=(Token)match(input,68,FOLLOW_68_in_actorDeclaration811); 
                    string_literal79_tree = (Object)adaptor.create(string_literal79);
                    adaptor.addChild(root_0, string_literal79_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:12: ( actionInputs )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==ID||LA33_0==57) ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:12: actionInputs
                            {
                            pushFollow(FOLLOW_actionInputs_in_actorDeclaration813);
                            actionInputs80=actionInputs();

                            state._fsp--;

                            adaptor.addChild(root_0, actionInputs80.getTree());

                            }
                            break;

                    }

                    string_literal81=(Token)match(input,65,FOLLOW_65_in_actorDeclaration816); 
                    string_literal81_tree = (Object)adaptor.create(string_literal81);
                    adaptor.addChild(root_0, string_literal81_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:32: ( actionOutputs )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==ID||LA34_0==57) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:32: actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration818);
                            actionOutputs82=actionOutputs();

                            state._fsp--;

                            adaptor.addChild(root_0, actionOutputs82.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:47: ( actionGuards )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==55) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:47: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration821);
                            actionGuards83=actionGuards();

                            state._fsp--;

                            adaptor.addChild(root_0, actionGuards83.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:61: ( actionDelay )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==54) ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:61: actionDelay
                            {
                            pushFollow(FOLLOW_actionDelay_in_actorDeclaration824);
                            actionDelay84=actionDelay();

                            state._fsp--;

                            adaptor.addChild(root_0, actionDelay84.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:74: ( 'var' varDecls )?
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==69) ) {
                        alt37=1;
                    }
                    switch (alt37) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:75: 'var' varDecls
                            {
                            string_literal85=(Token)match(input,69,FOLLOW_69_in_actorDeclaration828); 
                            string_literal85_tree = (Object)adaptor.create(string_literal85);
                            adaptor.addChild(root_0, string_literal85_tree);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration830);
                            varDecls86=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls86.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:92: ( actionStatements )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==61) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:92: actionStatements
                            {
                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration834);
                            actionStatements87=actionStatements();

                            state._fsp--;

                            adaptor.addChild(root_0, actionStatements87.getTree());

                            }
                            break;

                    }

                    string_literal88=(Token)match(input,66,FOLLOW_66_in_actorDeclaration837); 
                    string_literal88_tree = (Object)adaptor.create(string_literal88);
                    adaptor.addChild(root_0, string_literal88_tree);

                     

                    }
                    break;
                case 3 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:3: 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal89=(Token)match(input,70,FOLLOW_70_in_actorDeclaration847); 
                    string_literal89_tree = (Object)adaptor.create(string_literal89);
                    adaptor.addChild(root_0, string_literal89_tree);

                    string_literal90=(Token)match(input,65,FOLLOW_65_in_actorDeclaration849); 
                    string_literal90_tree = (Object)adaptor.create(string_literal90);
                    adaptor.addChild(root_0, string_literal90_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:22: ( actionOutputs )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==ID||LA39_0==57) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:22: actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration851);
                            actionOutputs91=actionOutputs();

                            state._fsp--;

                            adaptor.addChild(root_0, actionOutputs91.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:37: ( actionGuards )?
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( (LA40_0==55) ) {
                        alt40=1;
                    }
                    switch (alt40) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:37: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration854);
                            actionGuards92=actionGuards();

                            state._fsp--;

                            adaptor.addChild(root_0, actionGuards92.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:51: ( actionDelay )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==54) ) {
                        alt41=1;
                    }
                    switch (alt41) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:51: actionDelay
                            {
                            pushFollow(FOLLOW_actionDelay_in_actorDeclaration857);
                            actionDelay93=actionDelay();

                            state._fsp--;

                            adaptor.addChild(root_0, actionDelay93.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:64: ( 'var' varDecls )?
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==69) ) {
                        alt42=1;
                    }
                    switch (alt42) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:65: 'var' varDecls
                            {
                            string_literal94=(Token)match(input,69,FOLLOW_69_in_actorDeclaration861); 
                            string_literal94_tree = (Object)adaptor.create(string_literal94);
                            adaptor.addChild(root_0, string_literal94_tree);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration863);
                            varDecls95=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls95.getTree());

                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:82: ( actionStatements )?
                    int alt43=2;
                    int LA43_0 = input.LA(1);

                    if ( (LA43_0==61) ) {
                        alt43=1;
                    }
                    switch (alt43) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:82: actionStatements
                            {
                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration867);
                            actionStatements96=actionStatements();

                            state._fsp--;

                            adaptor.addChild(root_0, actionStatements96.getTree());

                            }
                            break;

                    }

                    string_literal97=(Token)match(input,66,FOLLOW_66_in_actorDeclaration870); 
                    string_literal97_tree = (Object)adaptor.create(string_literal97);
                    adaptor.addChild(root_0, string_literal97_tree);

                     

                    }
                    break;
                case 4 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:3: priorityOrder
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_priorityOrder_in_actorDeclaration879);
                    priorityOrder98=priorityOrder();

                    state._fsp--;

                    adaptor.addChild(root_0, priorityOrder98.getTree());
                     

                    }
                    break;
                case 5 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:3: 'function' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end'
                    {
                    string_literal99=(Token)match(input,74,FOLLOW_74_in_actorDeclaration886);  
                    stream_74.add(string_literal99);

                    ID100=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration888);  
                    stream_ID.add(ID100);

                    char_literal101=(Token)match(input,63,FOLLOW_63_in_actorDeclaration890);  
                    stream_63.add(char_literal101);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:21: ( varDeclNoExpr ( ',' varDeclNoExpr )* )?
                    int alt45=2;
                    int LA45_0 = input.LA(1);

                    if ( (LA45_0==ID) ) {
                        alt45=1;
                    }
                    switch (alt45) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:22: varDeclNoExpr ( ',' varDeclNoExpr )*
                            {
                            pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration893);
                            varDeclNoExpr102=varDeclNoExpr();

                            state._fsp--;

                            stream_varDeclNoExpr.add(varDeclNoExpr102.getTree());
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:36: ( ',' varDeclNoExpr )*
                            loop44:
                            do {
                                int alt44=2;
                                int LA44_0 = input.LA(1);

                                if ( (LA44_0==59) ) {
                                    alt44=1;
                                }


                                switch (alt44) {
                            	case 1 :
                            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:37: ',' varDeclNoExpr
                            	    {
                            	    char_literal103=(Token)match(input,59,FOLLOW_59_in_actorDeclaration896);  
                            	    stream_59.add(char_literal103);

                            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration898);
                            	    varDeclNoExpr104=varDeclNoExpr();

                            	    state._fsp--;

                            	    stream_varDeclNoExpr.add(varDeclNoExpr104.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop44;
                                }
                            } while (true);


                            }
                            break;

                    }

                    char_literal105=(Token)match(input,64,FOLLOW_64_in_actorDeclaration904);  
                    stream_64.add(char_literal105);

                    string_literal106=(Token)match(input,75,FOLLOW_75_in_actorDeclaration906);  
                    stream_75.add(string_literal106);

                    pushFollow(FOLLOW_typeDef_in_actorDeclaration908);
                    typeDef107=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef107.getTree());
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:5: ( 'var' varDecls )?
                    int alt46=2;
                    int LA46_0 = input.LA(1);

                    if ( (LA46_0==69) ) {
                        alt46=1;
                    }
                    switch (alt46) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:6: 'var' varDecls
                            {
                            string_literal108=(Token)match(input,69,FOLLOW_69_in_actorDeclaration915);  
                            stream_69.add(string_literal108);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration917);
                            varDecls109=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls109.getTree());

                            }
                            break;

                    }

                    char_literal110=(Token)match(input,56,FOLLOW_56_in_actorDeclaration921);  
                    stream_56.add(char_literal110);

                    pushFollow(FOLLOW_expression_in_actorDeclaration929);
                    expression111=expression();

                    state._fsp--;

                    stream_expression.add(expression111.getTree());
                    string_literal112=(Token)match(input,66,FOLLOW_66_in_actorDeclaration935);  
                    stream_66.add(string_literal112);



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
                    // 179:2: -> FUNCTION
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(FUNCTION, "FUNCTION"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 6 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:3: 'procedure' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end'
                    {
                    string_literal113=(Token)match(input,76,FOLLOW_76_in_actorDeclaration945);  
                    stream_76.add(string_literal113);

                    ID114=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration947);  
                    stream_ID.add(ID114);

                    char_literal115=(Token)match(input,63,FOLLOW_63_in_actorDeclaration949);  
                    stream_63.add(char_literal115);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:22: ( varDeclNoExpr ( ',' varDeclNoExpr )* )?
                    int alt48=2;
                    int LA48_0 = input.LA(1);

                    if ( (LA48_0==ID) ) {
                        alt48=1;
                    }
                    switch (alt48) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:23: varDeclNoExpr ( ',' varDeclNoExpr )*
                            {
                            pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration952);
                            varDeclNoExpr116=varDeclNoExpr();

                            state._fsp--;

                            stream_varDeclNoExpr.add(varDeclNoExpr116.getTree());
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:37: ( ',' varDeclNoExpr )*
                            loop47:
                            do {
                                int alt47=2;
                                int LA47_0 = input.LA(1);

                                if ( (LA47_0==59) ) {
                                    alt47=1;
                                }


                                switch (alt47) {
                            	case 1 :
                            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:181:38: ',' varDeclNoExpr
                            	    {
                            	    char_literal117=(Token)match(input,59,FOLLOW_59_in_actorDeclaration955);  
                            	    stream_59.add(char_literal117);

                            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration957);
                            	    varDeclNoExpr118=varDeclNoExpr();

                            	    state._fsp--;

                            	    stream_varDeclNoExpr.add(varDeclNoExpr118.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop47;
                                }
                            } while (true);


                            }
                            break;

                    }

                    char_literal119=(Token)match(input,64,FOLLOW_64_in_actorDeclaration963);  
                    stream_64.add(char_literal119);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:182:5: ( 'var' varDecls )?
                    int alt49=2;
                    int LA49_0 = input.LA(1);

                    if ( (LA49_0==69) ) {
                        alt49=1;
                    }
                    switch (alt49) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:182:6: 'var' varDecls
                            {
                            string_literal120=(Token)match(input,69,FOLLOW_69_in_actorDeclaration970);  
                            stream_69.add(string_literal120);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration972);
                            varDecls121=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls121.getTree());

                            }
                            break;

                    }

                    string_literal122=(Token)match(input,77,FOLLOW_77_in_actorDeclaration980);  
                    stream_77.add(string_literal122);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:13: ( statement )*
                    loop50:
                    do {
                        int alt50=2;
                        int LA50_0 = input.LA(1);

                        if ( (LA50_0==ID||LA50_0==77||LA50_0==102||LA50_0==107||(LA50_0>=113 && LA50_0<=114)||LA50_0==116) ) {
                            alt50=1;
                        }


                        switch (alt50) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:13: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_actorDeclaration982);
                    	    statement123=statement();

                    	    state._fsp--;

                    	    stream_statement.add(statement123.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop50;
                        }
                    } while (true);

                    string_literal124=(Token)match(input,66,FOLLOW_66_in_actorDeclaration985);  
                    stream_66.add(string_literal124);



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
                    // 184:2: -> PROCEDURE
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:186:1: actorDeclarations : ( actorDeclaration )* ( schedule ( actorDeclaration )* )? -> ( actorDeclaration )* ( schedule )? ;
    public final RVCCalParser.actorDeclarations_return actorDeclarations() throws RecognitionException {
        RVCCalParser.actorDeclarations_return retval = new RVCCalParser.actorDeclarations_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        RVCCalParser.actorDeclaration_return actorDeclaration125 = null;

        RVCCalParser.schedule_return schedule126 = null;

        RVCCalParser.actorDeclaration_return actorDeclaration127 = null;


        RewriteRuleSubtreeStream stream_schedule=new RewriteRuleSubtreeStream(adaptor,"rule schedule");
        RewriteRuleSubtreeStream stream_actorDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule actorDeclaration");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:186:18: ( ( actorDeclaration )* ( schedule ( actorDeclaration )* )? -> ( actorDeclaration )* ( schedule )? )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:186:20: ( actorDeclaration )* ( schedule ( actorDeclaration )* )?
            {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:186:20: ( actorDeclaration )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==ID||LA52_0==68||LA52_0==70||LA52_0==74||LA52_0==76||LA52_0==109) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:186:20: actorDeclaration
            	    {
            	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations997);
            	    actorDeclaration125=actorDeclaration();

            	    state._fsp--;

            	    stream_actorDeclaration.add(actorDeclaration125.getTree());

            	    }
            	    break;

            	default :
            	    break loop52;
                }
            } while (true);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:186:38: ( schedule ( actorDeclaration )* )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==110) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:186:39: schedule ( actorDeclaration )*
                    {
                    pushFollow(FOLLOW_schedule_in_actorDeclarations1001);
                    schedule126=schedule();

                    state._fsp--;

                    stream_schedule.add(schedule126.getTree());
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:186:48: ( actorDeclaration )*
                    loop53:
                    do {
                        int alt53=2;
                        int LA53_0 = input.LA(1);

                        if ( (LA53_0==ID||LA53_0==68||LA53_0==70||LA53_0==74||LA53_0==76||LA53_0==109) ) {
                            alt53=1;
                        }


                        switch (alt53) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:186:48: actorDeclaration
                    	    {
                    	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations1003);
                    	    actorDeclaration127=actorDeclaration();

                    	    state._fsp--;

                    	    stream_actorDeclaration.add(actorDeclaration127.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop53;
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
            // 186:68: -> ( actorDeclaration )* ( schedule )?
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:186:71: ( actorDeclaration )*
                while ( stream_actorDeclaration.hasNext() ) {
                    adaptor.addChild(root_0, stream_actorDeclaration.nextTree());

                }
                stream_actorDeclaration.reset();
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:186:89: ( schedule )?
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:1: actorImport : 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) ;
    public final RVCCalParser.actorImport_return actorImport() throws RecognitionException {
        RVCCalParser.actorImport_return retval = new RVCCalParser.actorImport_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal128=null;
        Token string_literal129=null;
        Token char_literal131=null;
        Token char_literal133=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent130 = null;

        RVCCalParser.qualifiedIdent_return qualifiedIdent132 = null;


        Object string_literal128_tree=null;
        Object string_literal129_tree=null;
        Object char_literal131_tree=null;
        Object char_literal133_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:191:12: ( 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:191:14: 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            {
            root_0 = (Object)adaptor.nil();

            string_literal128=(Token)match(input,78,FOLLOW_78_in_actorImport1026); 
            string_literal128_tree = (Object)adaptor.create(string_literal128);
            adaptor.addChild(root_0, string_literal128_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:192:3: ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==50) ) {
                alt55=1;
            }
            else if ( (LA55_0==ID) ) {
                alt55=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;
            }
            switch (alt55) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:192:4: 'all' qualifiedIdent ';'
                    {
                    string_literal129=(Token)match(input,50,FOLLOW_50_in_actorImport1031); 
                    string_literal129_tree = (Object)adaptor.create(string_literal129);
                    adaptor.addChild(root_0, string_literal129_tree);

                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport1033);
                    qualifiedIdent130=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent130.getTree());
                    char_literal131=(Token)match(input,73,FOLLOW_73_in_actorImport1035); 
                    char_literal131_tree = (Object)adaptor.create(char_literal131);
                    adaptor.addChild(root_0, char_literal131_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:193:3: qualifiedIdent ';'
                    {
                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport1041);
                    qualifiedIdent132=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent132.getTree());
                    char_literal133=(Token)match(input,73,FOLLOW_73_in_actorImport1043); 
                    char_literal133_tree = (Object)adaptor.create(char_literal133);
                    adaptor.addChild(root_0, char_literal133_tree);

                     

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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:1: actorParameter : typeDef ID ( '=' expression )? -> ^( PARAMETER typeDef ID ( expression )? ) ;
    public final RVCCalParser.actorParameter_return actorParameter() throws RecognitionException {
        RVCCalParser.actorParameter_return retval = new RVCCalParser.actorParameter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID135=null;
        Token char_literal136=null;
        RVCCalParser.typeDef_return typeDef134 = null;

        RVCCalParser.expression_return expression137 = null;


        Object ID135_tree=null;
        Object char_literal136_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:197:15: ( typeDef ID ( '=' expression )? -> ^( PARAMETER typeDef ID ( expression )? ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:198:2: typeDef ID ( '=' expression )?
            {
            pushFollow(FOLLOW_typeDef_in_actorParameter1058);
            typeDef134=typeDef();

            state._fsp--;

            stream_typeDef.add(typeDef134.getTree());
            ID135=(Token)match(input,ID,FOLLOW_ID_in_actorParameter1060);  
            stream_ID.add(ID135);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:198:13: ( '=' expression )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==71) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:198:14: '=' expression
                    {
                    char_literal136=(Token)match(input,71,FOLLOW_71_in_actorParameter1063);  
                    stream_71.add(char_literal136);

                    pushFollow(FOLLOW_expression_in_actorParameter1065);
                    expression137=expression();

                    state._fsp--;

                    stream_expression.add(expression137.getTree());

                    }
                    break;

            }



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
            // 198:31: -> ^( PARAMETER typeDef ID ( expression )? )
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:198:34: ^( PARAMETER typeDef ID ( expression )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETER, "PARAMETER"), root_1);

                adaptor.addChild(root_1, stream_typeDef.nextTree());
                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:198:57: ( expression )?
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:1: actorParameters : actorParameter ( ',' actorParameter )* -> ( actorParameter )+ ;
    public final RVCCalParser.actorParameters_return actorParameters() throws RecognitionException {
        RVCCalParser.actorParameters_return retval = new RVCCalParser.actorParameters_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal139=null;
        RVCCalParser.actorParameter_return actorParameter138 = null;

        RVCCalParser.actorParameter_return actorParameter140 = null;


        Object char_literal139_tree=null;
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleSubtreeStream stream_actorParameter=new RewriteRuleSubtreeStream(adaptor,"rule actorParameter");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:16: ( actorParameter ( ',' actorParameter )* -> ( actorParameter )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:18: actorParameter ( ',' actorParameter )*
            {
            pushFollow(FOLLOW_actorParameter_in_actorParameters1087);
            actorParameter138=actorParameter();

            state._fsp--;

            stream_actorParameter.add(actorParameter138.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:33: ( ',' actorParameter )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==59) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:34: ',' actorParameter
            	    {
            	    char_literal139=(Token)match(input,59,FOLLOW_59_in_actorParameters1090);  
            	    stream_59.add(char_literal139);

            	    pushFollow(FOLLOW_actorParameter_in_actorParameters1092);
            	    actorParameter140=actorParameter();

            	    state._fsp--;

            	    stream_actorParameter.add(actorParameter140.getTree());

            	    }
            	    break;

            	default :
            	    break loop57;
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
            // 200:55: -> ( actorParameter )+
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

    public static class actorPortDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actorPortDecl"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:202:1: actorPortDecl : ( 'multi' typeDef ID | typeDef ID -> ^( PORT typeDef ID ) );
    public final RVCCalParser.actorPortDecl_return actorPortDecl() throws RecognitionException {
        RVCCalParser.actorPortDecl_return retval = new RVCCalParser.actorPortDecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal141=null;
        Token ID143=null;
        Token ID145=null;
        RVCCalParser.typeDef_return typeDef142 = null;

        RVCCalParser.typeDef_return typeDef144 = null;


        Object string_literal141_tree=null;
        Object ID143_tree=null;
        Object ID145_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:205:14: ( 'multi' typeDef ID | typeDef ID -> ^( PORT typeDef ID ) )
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==79) ) {
                alt58=1;
            }
            else if ( (LA58_0==ID) ) {
                alt58=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 58, 0, input);

                throw nvae;
            }
            switch (alt58) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:206:2: 'multi' typeDef ID
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal141=(Token)match(input,79,FOLLOW_79_in_actorPortDecl1112); 
                    string_literal141_tree = (Object)adaptor.create(string_literal141);
                    adaptor.addChild(root_0, string_literal141_tree);

                    pushFollow(FOLLOW_typeDef_in_actorPortDecl1114);
                    typeDef142=typeDef();

                    state._fsp--;

                    adaptor.addChild(root_0, typeDef142.getTree());
                    ID143=(Token)match(input,ID,FOLLOW_ID_in_actorPortDecl1116); 
                    ID143_tree = (Object)adaptor.create(ID143);
                    adaptor.addChild(root_0, ID143_tree);

                     System.out.println("RVC-CAL does not permit the use of multi ports."); 

                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:208:3: typeDef ID
                    {
                    pushFollow(FOLLOW_typeDef_in_actorPortDecl1123);
                    typeDef144=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef144.getTree());
                    ID145=(Token)match(input,ID,FOLLOW_ID_in_actorPortDecl1125);  
                    stream_ID.add(ID145);



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
                    // 208:14: -> ^( PORT typeDef ID )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:208:17: ^( PORT typeDef ID )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PORT, "PORT"), root_1);

                        adaptor.addChild(root_1, stream_typeDef.nextTree());
                        adaptor.addChild(root_1, stream_ID.nextNode());

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
    // $ANTLR end "actorPortDecl"

    public static class actorPortDecls_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actorPortDecls"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:210:1: actorPortDecls : actorPortDecl ( ',' actorPortDecl )* -> ( actorPortDecl )+ ;
    public final RVCCalParser.actorPortDecls_return actorPortDecls() throws RecognitionException {
        RVCCalParser.actorPortDecls_return retval = new RVCCalParser.actorPortDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal147=null;
        RVCCalParser.actorPortDecl_return actorPortDecl146 = null;

        RVCCalParser.actorPortDecl_return actorPortDecl148 = null;


        Object char_literal147_tree=null;
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleSubtreeStream stream_actorPortDecl=new RewriteRuleSubtreeStream(adaptor,"rule actorPortDecl");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:210:15: ( actorPortDecl ( ',' actorPortDecl )* -> ( actorPortDecl )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:210:17: actorPortDecl ( ',' actorPortDecl )*
            {
            pushFollow(FOLLOW_actorPortDecl_in_actorPortDecls1142);
            actorPortDecl146=actorPortDecl();

            state._fsp--;

            stream_actorPortDecl.add(actorPortDecl146.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:210:31: ( ',' actorPortDecl )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==59) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:210:32: ',' actorPortDecl
            	    {
            	    char_literal147=(Token)match(input,59,FOLLOW_59_in_actorPortDecls1145);  
            	    stream_59.add(char_literal147);

            	    pushFollow(FOLLOW_actorPortDecl_in_actorPortDecls1147);
            	    actorPortDecl148=actorPortDecl();

            	    state._fsp--;

            	    stream_actorPortDecl.add(actorPortDecl148.getTree());

            	    }
            	    break;

            	default :
            	    break loop59;
                }
            } while (true);



            // AST REWRITE
            // elements: actorPortDecl
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 210:52: -> ( actorPortDecl )+
            {
                if ( !(stream_actorPortDecl.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_actorPortDecl.hasNext() ) {
                    adaptor.addChild(root_0, stream_actorPortDecl.nextTree());

                }
                stream_actorPortDecl.reset();

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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:212:1: expression : e1= and_expr ( ( ( 'or' | '||' ) e2= and_expr )+ -> ^( EXPR_OR $e1 ( $e2)+ ) | -> $e1) ;
    public final RVCCalParser.expression_return expression() throws RecognitionException {
        RVCCalParser.expression_return retval = new RVCCalParser.expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal149=null;
        Token string_literal150=null;
        RVCCalParser.and_expr_return e1 = null;

        RVCCalParser.and_expr_return e2 = null;


        Object string_literal149_tree=null;
        Object string_literal150_tree=null;
        RewriteRuleTokenStream stream_80=new RewriteRuleTokenStream(adaptor,"token 80");
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleSubtreeStream stream_and_expr=new RewriteRuleSubtreeStream(adaptor,"rule and_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:11: (e1= and_expr ( ( ( 'or' | '||' ) e2= and_expr )+ -> ^( EXPR_OR $e1 ( $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:13: e1= and_expr ( ( ( 'or' | '||' ) e2= and_expr )+ -> ^( EXPR_OR $e1 ( $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_and_expr_in_expression1172);
            e1=and_expr();

            state._fsp--;

            stream_and_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:25: ( ( ( 'or' | '||' ) e2= and_expr )+ -> ^( EXPR_OR $e1 ( $e2)+ ) | -> $e1)
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( ((LA62_0>=80 && LA62_0<=81)) ) {
                alt62=1;
            }
            else if ( ((LA62_0>=50 && LA62_0<=56)||(LA62_0>=58 && LA62_0<=59)||LA62_0==61||(LA62_0>=64 && LA62_0<=66)||LA62_0==69||LA62_0==73||LA62_0==77||(LA62_0>=103 && LA62_0<=104)||LA62_0==115) ) {
                alt62=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                throw nvae;
            }
            switch (alt62) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:26: ( ( 'or' | '||' ) e2= and_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:26: ( ( 'or' | '||' ) e2= and_expr )+
                    int cnt61=0;
                    loop61:
                    do {
                        int alt61=2;
                        int LA61_0 = input.LA(1);

                        if ( ((LA61_0>=80 && LA61_0<=81)) ) {
                            alt61=1;
                        }


                        switch (alt61) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:27: ( 'or' | '||' ) e2= and_expr
                    	    {
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:27: ( 'or' | '||' )
                    	    int alt60=2;
                    	    int LA60_0 = input.LA(1);

                    	    if ( (LA60_0==80) ) {
                    	        alt60=1;
                    	    }
                    	    else if ( (LA60_0==81) ) {
                    	        alt60=2;
                    	    }
                    	    else {
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 60, 0, input);

                    	        throw nvae;
                    	    }
                    	    switch (alt60) {
                    	        case 1 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:28: 'or'
                    	            {
                    	            string_literal149=(Token)match(input,80,FOLLOW_80_in_expression1177);  
                    	            stream_80.add(string_literal149);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:35: '||'
                    	            {
                    	            string_literal150=(Token)match(input,81,FOLLOW_81_in_expression1181);  
                    	            stream_81.add(string_literal150);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_and_expr_in_expression1186);
                    	    e2=and_expr();

                    	    state._fsp--;

                    	    stream_and_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt61 >= 1 ) break loop61;
                                EarlyExitException eee =
                                    new EarlyExitException(61, input);
                                throw eee;
                        }
                        cnt61++;
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
                    // 219:55: -> ^( EXPR_OR $e1 ( $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:58: ^( EXPR_OR $e1 ( $e2)+ )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:80: 
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
                    // 219:80: -> $e1
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:1: and_expr : e1= bitor_expr ( ( ( 'and' | '&&' ) e2= bitor_expr )+ -> ^( EXPR_AND $e1 ( $e2)+ ) | -> $e1) ;
    public final RVCCalParser.and_expr_return and_expr() throws RecognitionException {
        RVCCalParser.and_expr_return retval = new RVCCalParser.and_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal151=null;
        Token string_literal152=null;
        RVCCalParser.bitor_expr_return e1 = null;

        RVCCalParser.bitor_expr_return e2 = null;


        Object string_literal151_tree=null;
        Object string_literal152_tree=null;
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleSubtreeStream stream_bitor_expr=new RewriteRuleSubtreeStream(adaptor,"rule bitor_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:9: (e1= bitor_expr ( ( ( 'and' | '&&' ) e2= bitor_expr )+ -> ^( EXPR_AND $e1 ( $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:11: e1= bitor_expr ( ( ( 'and' | '&&' ) e2= bitor_expr )+ -> ^( EXPR_AND $e1 ( $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_bitor_expr_in_and_expr1219);
            e1=bitor_expr();

            state._fsp--;

            stream_bitor_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:25: ( ( ( 'and' | '&&' ) e2= bitor_expr )+ -> ^( EXPR_AND $e1 ( $e2)+ ) | -> $e1)
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( ((LA65_0>=82 && LA65_0<=83)) ) {
                alt65=1;
            }
            else if ( ((LA65_0>=50 && LA65_0<=56)||(LA65_0>=58 && LA65_0<=59)||LA65_0==61||(LA65_0>=64 && LA65_0<=66)||LA65_0==69||LA65_0==73||LA65_0==77||(LA65_0>=80 && LA65_0<=81)||(LA65_0>=103 && LA65_0<=104)||LA65_0==115) ) {
                alt65=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 65, 0, input);

                throw nvae;
            }
            switch (alt65) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:26: ( ( 'and' | '&&' ) e2= bitor_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:26: ( ( 'and' | '&&' ) e2= bitor_expr )+
                    int cnt64=0;
                    loop64:
                    do {
                        int alt64=2;
                        int LA64_0 = input.LA(1);

                        if ( ((LA64_0>=82 && LA64_0<=83)) ) {
                            alt64=1;
                        }


                        switch (alt64) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:27: ( 'and' | '&&' ) e2= bitor_expr
                    	    {
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:27: ( 'and' | '&&' )
                    	    int alt63=2;
                    	    int LA63_0 = input.LA(1);

                    	    if ( (LA63_0==82) ) {
                    	        alt63=1;
                    	    }
                    	    else if ( (LA63_0==83) ) {
                    	        alt63=2;
                    	    }
                    	    else {
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 63, 0, input);

                    	        throw nvae;
                    	    }
                    	    switch (alt63) {
                    	        case 1 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:28: 'and'
                    	            {
                    	            string_literal151=(Token)match(input,82,FOLLOW_82_in_and_expr1224);  
                    	            stream_82.add(string_literal151);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:36: '&&'
                    	            {
                    	            string_literal152=(Token)match(input,83,FOLLOW_83_in_and_expr1228);  
                    	            stream_83.add(string_literal152);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_bitor_expr_in_and_expr1233);
                    	    e2=bitor_expr();

                    	    state._fsp--;

                    	    stream_bitor_expr.add(e2.getTree());

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
                    // 221:58: -> ^( EXPR_AND $e1 ( $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:61: ^( EXPR_AND $e1 ( $e2)+ )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:84: 
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
                    // 221:84: -> $e1
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:1: bitor_expr : e1= bitand_expr ( ( '|' e2= bitand_expr )+ -> ^( EXPR_BITOR $e1 ( $e2)+ ) | -> $e1) ;
    public final RVCCalParser.bitor_expr_return bitor_expr() throws RecognitionException {
        RVCCalParser.bitor_expr_return retval = new RVCCalParser.bitor_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal153=null;
        RVCCalParser.bitand_expr_return e1 = null;

        RVCCalParser.bitand_expr_return e2 = null;


        Object char_literal153_tree=null;
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleSubtreeStream stream_bitand_expr=new RewriteRuleSubtreeStream(adaptor,"rule bitand_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:11: (e1= bitand_expr ( ( '|' e2= bitand_expr )+ -> ^( EXPR_BITOR $e1 ( $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:13: e1= bitand_expr ( ( '|' e2= bitand_expr )+ -> ^( EXPR_BITOR $e1 ( $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_bitand_expr_in_bitor_expr1266);
            e1=bitand_expr();

            state._fsp--;

            stream_bitand_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:28: ( ( '|' e2= bitand_expr )+ -> ^( EXPR_BITOR $e1 ( $e2)+ ) | -> $e1)
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==84) ) {
                alt67=1;
            }
            else if ( ((LA67_0>=50 && LA67_0<=56)||(LA67_0>=58 && LA67_0<=59)||LA67_0==61||(LA67_0>=64 && LA67_0<=66)||LA67_0==69||LA67_0==73||LA67_0==77||(LA67_0>=80 && LA67_0<=83)||(LA67_0>=103 && LA67_0<=104)||LA67_0==115) ) {
                alt67=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;
            }
            switch (alt67) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:29: ( '|' e2= bitand_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:29: ( '|' e2= bitand_expr )+
                    int cnt66=0;
                    loop66:
                    do {
                        int alt66=2;
                        int LA66_0 = input.LA(1);

                        if ( (LA66_0==84) ) {
                            alt66=1;
                        }


                        switch (alt66) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:30: '|' e2= bitand_expr
                    	    {
                    	    char_literal153=(Token)match(input,84,FOLLOW_84_in_bitor_expr1270);  
                    	    stream_84.add(char_literal153);

                    	    pushFollow(FOLLOW_bitand_expr_in_bitor_expr1274);
                    	    e2=bitand_expr();

                    	    state._fsp--;

                    	    stream_bitand_expr.add(e2.getTree());

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
                    // 223:51: -> ^( EXPR_BITOR $e1 ( $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:54: ^( EXPR_BITOR $e1 ( $e2)+ )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:79: 
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
                    // 223:79: -> $e1
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:1: bitand_expr : e1= eq_expr ( ( '&' e2= eq_expr )+ -> ^( EXPR_BITAND $e1 ( $e2)+ ) | -> $e1) ;
    public final RVCCalParser.bitand_expr_return bitand_expr() throws RecognitionException {
        RVCCalParser.bitand_expr_return retval = new RVCCalParser.bitand_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal154=null;
        RVCCalParser.eq_expr_return e1 = null;

        RVCCalParser.eq_expr_return e2 = null;


        Object char_literal154_tree=null;
        RewriteRuleTokenStream stream_85=new RewriteRuleTokenStream(adaptor,"token 85");
        RewriteRuleSubtreeStream stream_eq_expr=new RewriteRuleSubtreeStream(adaptor,"rule eq_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:12: (e1= eq_expr ( ( '&' e2= eq_expr )+ -> ^( EXPR_BITAND $e1 ( $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:14: e1= eq_expr ( ( '&' e2= eq_expr )+ -> ^( EXPR_BITAND $e1 ( $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_eq_expr_in_bitand_expr1307);
            e1=eq_expr();

            state._fsp--;

            stream_eq_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:25: ( ( '&' e2= eq_expr )+ -> ^( EXPR_BITAND $e1 ( $e2)+ ) | -> $e1)
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==85) ) {
                alt69=1;
            }
            else if ( ((LA69_0>=50 && LA69_0<=56)||(LA69_0>=58 && LA69_0<=59)||LA69_0==61||(LA69_0>=64 && LA69_0<=66)||LA69_0==69||LA69_0==73||LA69_0==77||(LA69_0>=80 && LA69_0<=84)||(LA69_0>=103 && LA69_0<=104)||LA69_0==115) ) {
                alt69=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 69, 0, input);

                throw nvae;
            }
            switch (alt69) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:26: ( '&' e2= eq_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:26: ( '&' e2= eq_expr )+
                    int cnt68=0;
                    loop68:
                    do {
                        int alt68=2;
                        int LA68_0 = input.LA(1);

                        if ( (LA68_0==85) ) {
                            alt68=1;
                        }


                        switch (alt68) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:27: '&' e2= eq_expr
                    	    {
                    	    char_literal154=(Token)match(input,85,FOLLOW_85_in_bitand_expr1311);  
                    	    stream_85.add(char_literal154);

                    	    pushFollow(FOLLOW_eq_expr_in_bitand_expr1315);
                    	    e2=eq_expr();

                    	    state._fsp--;

                    	    stream_eq_expr.add(e2.getTree());

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
                    // 225:44: -> ^( EXPR_BITAND $e1 ( $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:47: ^( EXPR_BITAND $e1 ( $e2)+ )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:73: 
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
                    // 225:73: -> $e1
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:1: eq_expr : e1= rel_expr ( ( (op= '=' | op= '!=' ) e2= rel_expr )+ -> ^( EXPR_EQ $e1 ( $op $e2)+ ) | -> $e1) ;
    public final RVCCalParser.eq_expr_return eq_expr() throws RecognitionException {
        RVCCalParser.eq_expr_return retval = new RVCCalParser.eq_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        RVCCalParser.rel_expr_return e1 = null;

        RVCCalParser.rel_expr_return e2 = null;


        Object op_tree=null;
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleSubtreeStream stream_rel_expr=new RewriteRuleSubtreeStream(adaptor,"rule rel_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:8: (e1= rel_expr ( ( (op= '=' | op= '!=' ) e2= rel_expr )+ -> ^( EXPR_EQ $e1 ( $op $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:10: e1= rel_expr ( ( (op= '=' | op= '!=' ) e2= rel_expr )+ -> ^( EXPR_EQ $e1 ( $op $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_rel_expr_in_eq_expr1348);
            e1=rel_expr();

            state._fsp--;

            stream_rel_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:22: ( ( (op= '=' | op= '!=' ) e2= rel_expr )+ -> ^( EXPR_EQ $e1 ( $op $e2)+ ) | -> $e1)
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==71||LA72_0==86) ) {
                alt72=1;
            }
            else if ( ((LA72_0>=50 && LA72_0<=56)||(LA72_0>=58 && LA72_0<=59)||LA72_0==61||(LA72_0>=64 && LA72_0<=66)||LA72_0==69||LA72_0==73||LA72_0==77||(LA72_0>=80 && LA72_0<=85)||(LA72_0>=103 && LA72_0<=104)||LA72_0==115) ) {
                alt72=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 72, 0, input);

                throw nvae;
            }
            switch (alt72) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:23: ( (op= '=' | op= '!=' ) e2= rel_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:23: ( (op= '=' | op= '!=' ) e2= rel_expr )+
                    int cnt71=0;
                    loop71:
                    do {
                        int alt71=2;
                        int LA71_0 = input.LA(1);

                        if ( (LA71_0==71||LA71_0==86) ) {
                            alt71=1;
                        }


                        switch (alt71) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:24: (op= '=' | op= '!=' ) e2= rel_expr
                    	    {
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:24: (op= '=' | op= '!=' )
                    	    int alt70=2;
                    	    int LA70_0 = input.LA(1);

                    	    if ( (LA70_0==71) ) {
                    	        alt70=1;
                    	    }
                    	    else if ( (LA70_0==86) ) {
                    	        alt70=2;
                    	    }
                    	    else {
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 70, 0, input);

                    	        throw nvae;
                    	    }
                    	    switch (alt70) {
                    	        case 1 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:25: op= '='
                    	            {
                    	            op=(Token)match(input,71,FOLLOW_71_in_eq_expr1355);  
                    	            stream_71.add(op);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:34: op= '!='
                    	            {
                    	            op=(Token)match(input,86,FOLLOW_86_in_eq_expr1361);  
                    	            stream_86.add(op);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_rel_expr_in_eq_expr1366);
                    	    e2=rel_expr();

                    	    state._fsp--;

                    	    stream_rel_expr.add(e2.getTree());

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
                    // 227:57: -> ^( EXPR_EQ $e1 ( $op $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:60: ^( EXPR_EQ $e1 ( $op $e2)+ )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:88: 
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
                    // 227:88: -> $e1
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:1: rel_expr : e1= shift_expr ( ( (op= '<' | op= '>' | op= '<=' | op= '>=' ) e2= shift_expr )+ -> ^( EXPR_REL $e1 ( $op $e2)+ ) | -> $e1) ;
    public final RVCCalParser.rel_expr_return rel_expr() throws RecognitionException {
        RVCCalParser.rel_expr_return retval = new RVCCalParser.rel_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        RVCCalParser.shift_expr_return e1 = null;

        RVCCalParser.shift_expr_return e2 = null;


        Object op_tree=null;
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleTokenStream stream_87=new RewriteRuleTokenStream(adaptor,"token 87");
        RewriteRuleTokenStream stream_88=new RewriteRuleTokenStream(adaptor,"token 88");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleSubtreeStream stream_shift_expr=new RewriteRuleSubtreeStream(adaptor,"rule shift_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:9: (e1= shift_expr ( ( (op= '<' | op= '>' | op= '<=' | op= '>=' ) e2= shift_expr )+ -> ^( EXPR_REL $e1 ( $op $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:11: e1= shift_expr ( ( (op= '<' | op= '>' | op= '<=' | op= '>=' ) e2= shift_expr )+ -> ^( EXPR_REL $e1 ( $op $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_shift_expr_in_rel_expr1404);
            e1=shift_expr();

            state._fsp--;

            stream_shift_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:25: ( ( (op= '<' | op= '>' | op= '<=' | op= '>=' ) e2= shift_expr )+ -> ^( EXPR_REL $e1 ( $op $e2)+ ) | -> $e1)
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( ((LA75_0>=87 && LA75_0<=90)) ) {
                alt75=1;
            }
            else if ( ((LA75_0>=50 && LA75_0<=56)||(LA75_0>=58 && LA75_0<=59)||LA75_0==61||(LA75_0>=64 && LA75_0<=66)||LA75_0==69||LA75_0==71||LA75_0==73||LA75_0==77||(LA75_0>=80 && LA75_0<=86)||(LA75_0>=103 && LA75_0<=104)||LA75_0==115) ) {
                alt75=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 75, 0, input);

                throw nvae;
            }
            switch (alt75) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:26: ( (op= '<' | op= '>' | op= '<=' | op= '>=' ) e2= shift_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:26: ( (op= '<' | op= '>' | op= '<=' | op= '>=' ) e2= shift_expr )+
                    int cnt74=0;
                    loop74:
                    do {
                        int alt74=2;
                        int LA74_0 = input.LA(1);

                        if ( ((LA74_0>=87 && LA74_0<=90)) ) {
                            alt74=1;
                        }


                        switch (alt74) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:27: (op= '<' | op= '>' | op= '<=' | op= '>=' ) e2= shift_expr
                    	    {
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:27: (op= '<' | op= '>' | op= '<=' | op= '>=' )
                    	    int alt73=4;
                    	    switch ( input.LA(1) ) {
                    	    case 87:
                    	        {
                    	        alt73=1;
                    	        }
                    	        break;
                    	    case 88:
                    	        {
                    	        alt73=2;
                    	        }
                    	        break;
                    	    case 89:
                    	        {
                    	        alt73=3;
                    	        }
                    	        break;
                    	    case 90:
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
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:28: op= '<'
                    	            {
                    	            op=(Token)match(input,87,FOLLOW_87_in_rel_expr1411);  
                    	            stream_87.add(op);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:37: op= '>'
                    	            {
                    	            op=(Token)match(input,88,FOLLOW_88_in_rel_expr1417);  
                    	            stream_88.add(op);


                    	            }
                    	            break;
                    	        case 3 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:46: op= '<='
                    	            {
                    	            op=(Token)match(input,89,FOLLOW_89_in_rel_expr1423);  
                    	            stream_89.add(op);


                    	            }
                    	            break;
                    	        case 4 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:56: op= '>='
                    	            {
                    	            op=(Token)match(input,90,FOLLOW_90_in_rel_expr1429);  
                    	            stream_90.add(op);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_shift_expr_in_rel_expr1434);
                    	    e2=shift_expr();

                    	    state._fsp--;

                    	    stream_shift_expr.add(e2.getTree());

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
                    // 229:81: -> ^( EXPR_REL $e1 ( $op $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:84: ^( EXPR_REL $e1 ( $op $e2)+ )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:113: 
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
                    // 229:113: -> $e1
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:1: shift_expr : e1= add_expr ( ( (op= '<<' | op= '>>' ) e2= add_expr )+ -> ^( EXPR_SHIFT $e1 ( $op $e2)+ ) | -> $e1) ;
    public final RVCCalParser.shift_expr_return shift_expr() throws RecognitionException {
        RVCCalParser.shift_expr_return retval = new RVCCalParser.shift_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        RVCCalParser.add_expr_return e1 = null;

        RVCCalParser.add_expr_return e2 = null;


        Object op_tree=null;
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleTokenStream stream_91=new RewriteRuleTokenStream(adaptor,"token 91");
        RewriteRuleSubtreeStream stream_add_expr=new RewriteRuleSubtreeStream(adaptor,"rule add_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:11: (e1= add_expr ( ( (op= '<<' | op= '>>' ) e2= add_expr )+ -> ^( EXPR_SHIFT $e1 ( $op $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:13: e1= add_expr ( ( (op= '<<' | op= '>>' ) e2= add_expr )+ -> ^( EXPR_SHIFT $e1 ( $op $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_add_expr_in_shift_expr1471);
            e1=add_expr();

            state._fsp--;

            stream_add_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:25: ( ( (op= '<<' | op= '>>' ) e2= add_expr )+ -> ^( EXPR_SHIFT $e1 ( $op $e2)+ ) | -> $e1)
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( ((LA78_0>=91 && LA78_0<=92)) ) {
                alt78=1;
            }
            else if ( ((LA78_0>=50 && LA78_0<=56)||(LA78_0>=58 && LA78_0<=59)||LA78_0==61||(LA78_0>=64 && LA78_0<=66)||LA78_0==69||LA78_0==71||LA78_0==73||LA78_0==77||(LA78_0>=80 && LA78_0<=90)||(LA78_0>=103 && LA78_0<=104)||LA78_0==115) ) {
                alt78=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;
            }
            switch (alt78) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:26: ( (op= '<<' | op= '>>' ) e2= add_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:26: ( (op= '<<' | op= '>>' ) e2= add_expr )+
                    int cnt77=0;
                    loop77:
                    do {
                        int alt77=2;
                        int LA77_0 = input.LA(1);

                        if ( ((LA77_0>=91 && LA77_0<=92)) ) {
                            alt77=1;
                        }


                        switch (alt77) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:27: (op= '<<' | op= '>>' ) e2= add_expr
                    	    {
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:27: (op= '<<' | op= '>>' )
                    	    int alt76=2;
                    	    int LA76_0 = input.LA(1);

                    	    if ( (LA76_0==91) ) {
                    	        alt76=1;
                    	    }
                    	    else if ( (LA76_0==92) ) {
                    	        alt76=2;
                    	    }
                    	    else {
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 76, 0, input);

                    	        throw nvae;
                    	    }
                    	    switch (alt76) {
                    	        case 1 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:28: op= '<<'
                    	            {
                    	            op=(Token)match(input,91,FOLLOW_91_in_shift_expr1478);  
                    	            stream_91.add(op);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:38: op= '>>'
                    	            {
                    	            op=(Token)match(input,92,FOLLOW_92_in_shift_expr1484);  
                    	            stream_92.add(op);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_add_expr_in_shift_expr1489);
                    	    e2=add_expr();

                    	    state._fsp--;

                    	    stream_add_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt77 >= 1 ) break loop77;
                                EarlyExitException eee =
                                    new EarlyExitException(77, input);
                                throw eee;
                        }
                        cnt77++;
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
                    // 231:61: -> ^( EXPR_SHIFT $e1 ( $op $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:64: ^( EXPR_SHIFT $e1 ( $op $e2)+ )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:95: 
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
                    // 231:95: -> $e1
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:1: add_expr : e1= mul_expr ( ( (op= '+' | op= '-' ) e2= mul_expr )+ -> ^( EXPR_ADD $e1 ( $op $e2)+ ) | -> $e1) ;
    public final RVCCalParser.add_expr_return add_expr() throws RecognitionException {
        RVCCalParser.add_expr_return retval = new RVCCalParser.add_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        RVCCalParser.mul_expr_return e1 = null;

        RVCCalParser.mul_expr_return e2 = null;


        Object op_tree=null;
        RewriteRuleTokenStream stream_94=new RewriteRuleTokenStream(adaptor,"token 94");
        RewriteRuleTokenStream stream_93=new RewriteRuleTokenStream(adaptor,"token 93");
        RewriteRuleSubtreeStream stream_mul_expr=new RewriteRuleSubtreeStream(adaptor,"rule mul_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:9: (e1= mul_expr ( ( (op= '+' | op= '-' ) e2= mul_expr )+ -> ^( EXPR_ADD $e1 ( $op $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:11: e1= mul_expr ( ( (op= '+' | op= '-' ) e2= mul_expr )+ -> ^( EXPR_ADD $e1 ( $op $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_mul_expr_in_add_expr1527);
            e1=mul_expr();

            state._fsp--;

            stream_mul_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:23: ( ( (op= '+' | op= '-' ) e2= mul_expr )+ -> ^( EXPR_ADD $e1 ( $op $e2)+ ) | -> $e1)
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( ((LA81_0>=93 && LA81_0<=94)) ) {
                alt81=1;
            }
            else if ( ((LA81_0>=50 && LA81_0<=56)||(LA81_0>=58 && LA81_0<=59)||LA81_0==61||(LA81_0>=64 && LA81_0<=66)||LA81_0==69||LA81_0==71||LA81_0==73||LA81_0==77||(LA81_0>=80 && LA81_0<=92)||(LA81_0>=103 && LA81_0<=104)||LA81_0==115) ) {
                alt81=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 81, 0, input);

                throw nvae;
            }
            switch (alt81) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:24: ( (op= '+' | op= '-' ) e2= mul_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:24: ( (op= '+' | op= '-' ) e2= mul_expr )+
                    int cnt80=0;
                    loop80:
                    do {
                        int alt80=2;
                        int LA80_0 = input.LA(1);

                        if ( ((LA80_0>=93 && LA80_0<=94)) ) {
                            alt80=1;
                        }


                        switch (alt80) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:25: (op= '+' | op= '-' ) e2= mul_expr
                    	    {
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:25: (op= '+' | op= '-' )
                    	    int alt79=2;
                    	    int LA79_0 = input.LA(1);

                    	    if ( (LA79_0==93) ) {
                    	        alt79=1;
                    	    }
                    	    else if ( (LA79_0==94) ) {
                    	        alt79=2;
                    	    }
                    	    else {
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 79, 0, input);

                    	        throw nvae;
                    	    }
                    	    switch (alt79) {
                    	        case 1 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:26: op= '+'
                    	            {
                    	            op=(Token)match(input,93,FOLLOW_93_in_add_expr1534);  
                    	            stream_93.add(op);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:35: op= '-'
                    	            {
                    	            op=(Token)match(input,94,FOLLOW_94_in_add_expr1540);  
                    	            stream_94.add(op);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_mul_expr_in_add_expr1545);
                    	    e2=mul_expr();

                    	    state._fsp--;

                    	    stream_mul_expr.add(e2.getTree());

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
                    // 233:57: -> ^( EXPR_ADD $e1 ( $op $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:60: ^( EXPR_ADD $e1 ( $op $e2)+ )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:89: 
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
                    // 233:89: -> $e1
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:1: mul_expr : e1= exp_expr ( ( (op= 'div' | op= 'mod' | op= '*' | op= '/' ) e2= exp_expr )+ -> ^( EXPR_MUL $e1 ( $op $e2)+ ) | -> $e1) ;
    public final RVCCalParser.mul_expr_return mul_expr() throws RecognitionException {
        RVCCalParser.mul_expr_return retval = new RVCCalParser.mul_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;
        RVCCalParser.exp_expr_return e1 = null;

        RVCCalParser.exp_expr_return e2 = null;


        Object op_tree=null;
        RewriteRuleTokenStream stream_98=new RewriteRuleTokenStream(adaptor,"token 98");
        RewriteRuleTokenStream stream_97=new RewriteRuleTokenStream(adaptor,"token 97");
        RewriteRuleTokenStream stream_96=new RewriteRuleTokenStream(adaptor,"token 96");
        RewriteRuleTokenStream stream_95=new RewriteRuleTokenStream(adaptor,"token 95");
        RewriteRuleSubtreeStream stream_exp_expr=new RewriteRuleSubtreeStream(adaptor,"rule exp_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:9: (e1= exp_expr ( ( (op= 'div' | op= 'mod' | op= '*' | op= '/' ) e2= exp_expr )+ -> ^( EXPR_MUL $e1 ( $op $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:11: e1= exp_expr ( ( (op= 'div' | op= 'mod' | op= '*' | op= '/' ) e2= exp_expr )+ -> ^( EXPR_MUL $e1 ( $op $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_exp_expr_in_mul_expr1583);
            e1=exp_expr();

            state._fsp--;

            stream_exp_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:23: ( ( (op= 'div' | op= 'mod' | op= '*' | op= '/' ) e2= exp_expr )+ -> ^( EXPR_MUL $e1 ( $op $e2)+ ) | -> $e1)
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( ((LA84_0>=95 && LA84_0<=98)) ) {
                alt84=1;
            }
            else if ( ((LA84_0>=50 && LA84_0<=56)||(LA84_0>=58 && LA84_0<=59)||LA84_0==61||(LA84_0>=64 && LA84_0<=66)||LA84_0==69||LA84_0==71||LA84_0==73||LA84_0==77||(LA84_0>=80 && LA84_0<=94)||(LA84_0>=103 && LA84_0<=104)||LA84_0==115) ) {
                alt84=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 84, 0, input);

                throw nvae;
            }
            switch (alt84) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:24: ( (op= 'div' | op= 'mod' | op= '*' | op= '/' ) e2= exp_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:24: ( (op= 'div' | op= 'mod' | op= '*' | op= '/' ) e2= exp_expr )+
                    int cnt83=0;
                    loop83:
                    do {
                        int alt83=2;
                        int LA83_0 = input.LA(1);

                        if ( ((LA83_0>=95 && LA83_0<=98)) ) {
                            alt83=1;
                        }


                        switch (alt83) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:25: (op= 'div' | op= 'mod' | op= '*' | op= '/' ) e2= exp_expr
                    	    {
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:25: (op= 'div' | op= 'mod' | op= '*' | op= '/' )
                    	    int alt82=4;
                    	    switch ( input.LA(1) ) {
                    	    case 95:
                    	        {
                    	        alt82=1;
                    	        }
                    	        break;
                    	    case 96:
                    	        {
                    	        alt82=2;
                    	        }
                    	        break;
                    	    case 97:
                    	        {
                    	        alt82=3;
                    	        }
                    	        break;
                    	    case 98:
                    	        {
                    	        alt82=4;
                    	        }
                    	        break;
                    	    default:
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 82, 0, input);

                    	        throw nvae;
                    	    }

                    	    switch (alt82) {
                    	        case 1 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:26: op= 'div'
                    	            {
                    	            op=(Token)match(input,95,FOLLOW_95_in_mul_expr1590);  
                    	            stream_95.add(op);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:37: op= 'mod'
                    	            {
                    	            op=(Token)match(input,96,FOLLOW_96_in_mul_expr1596);  
                    	            stream_96.add(op);


                    	            }
                    	            break;
                    	        case 3 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:48: op= '*'
                    	            {
                    	            op=(Token)match(input,97,FOLLOW_97_in_mul_expr1602);  
                    	            stream_97.add(op);


                    	            }
                    	            break;
                    	        case 4 :
                    	            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:57: op= '/'
                    	            {
                    	            op=(Token)match(input,98,FOLLOW_98_in_mul_expr1608);  
                    	            stream_98.add(op);


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_exp_expr_in_mul_expr1613);
                    	    e2=exp_expr();

                    	    state._fsp--;

                    	    stream_exp_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt83 >= 1 ) break loop83;
                                EarlyExitException eee =
                                    new EarlyExitException(83, input);
                                throw eee;
                        }
                        cnt83++;
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
                    // 235:79: -> ^( EXPR_MUL $e1 ( $op $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:82: ^( EXPR_MUL $e1 ( $op $e2)+ )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:111: 
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
                    // 235:111: -> $e1
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:1: exp_expr : e1= un_expr ( ( '^' e2= un_expr )+ -> ^( EXPR_EXP $e1 ( $e2)+ ) | -> $e1) ;
    public final RVCCalParser.exp_expr_return exp_expr() throws RecognitionException {
        RVCCalParser.exp_expr_return retval = new RVCCalParser.exp_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal155=null;
        RVCCalParser.un_expr_return e1 = null;

        RVCCalParser.un_expr_return e2 = null;


        Object char_literal155_tree=null;
        RewriteRuleTokenStream stream_99=new RewriteRuleTokenStream(adaptor,"token 99");
        RewriteRuleSubtreeStream stream_un_expr=new RewriteRuleSubtreeStream(adaptor,"rule un_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:9: (e1= un_expr ( ( '^' e2= un_expr )+ -> ^( EXPR_EXP $e1 ( $e2)+ ) | -> $e1) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:11: e1= un_expr ( ( '^' e2= un_expr )+ -> ^( EXPR_EXP $e1 ( $e2)+ ) | -> $e1)
            {
            pushFollow(FOLLOW_un_expr_in_exp_expr1651);
            e1=un_expr();

            state._fsp--;

            stream_un_expr.add(e1.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:22: ( ( '^' e2= un_expr )+ -> ^( EXPR_EXP $e1 ( $e2)+ ) | -> $e1)
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==99) ) {
                alt86=1;
            }
            else if ( ((LA86_0>=50 && LA86_0<=56)||(LA86_0>=58 && LA86_0<=59)||LA86_0==61||(LA86_0>=64 && LA86_0<=66)||LA86_0==69||LA86_0==71||LA86_0==73||LA86_0==77||(LA86_0>=80 && LA86_0<=98)||(LA86_0>=103 && LA86_0<=104)||LA86_0==115) ) {
                alt86=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 86, 0, input);

                throw nvae;
            }
            switch (alt86) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:23: ( '^' e2= un_expr )+
                    {
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:23: ( '^' e2= un_expr )+
                    int cnt85=0;
                    loop85:
                    do {
                        int alt85=2;
                        int LA85_0 = input.LA(1);

                        if ( (LA85_0==99) ) {
                            alt85=1;
                        }


                        switch (alt85) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:24: '^' e2= un_expr
                    	    {
                    	    char_literal155=(Token)match(input,99,FOLLOW_99_in_exp_expr1655);  
                    	    stream_99.add(char_literal155);

                    	    pushFollow(FOLLOW_un_expr_in_exp_expr1659);
                    	    e2=un_expr();

                    	    state._fsp--;

                    	    stream_un_expr.add(e2.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt85 >= 1 ) break loop85;
                                EarlyExitException eee =
                                    new EarlyExitException(85, input);
                                throw eee;
                        }
                        cnt85++;
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
                    // 237:41: -> ^( EXPR_EXP $e1 ( $e2)+ )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:44: ^( EXPR_EXP $e1 ( $e2)+ )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:67: 
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
                    // 237:67: -> $e1
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:1: un_expr : ( postfix_expression -> postfix_expression | '-' un_expr -> ^( EXPR_UN '-' un_expr ) | 'not' un_expr -> ^( EXPR_UN 'not' un_expr ) | '#' un_expr -> ^( EXPR_UN '#' un_expr ) );
    public final RVCCalParser.un_expr_return un_expr() throws RecognitionException {
        RVCCalParser.un_expr_return retval = new RVCCalParser.un_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal157=null;
        Token string_literal159=null;
        Token char_literal161=null;
        RVCCalParser.postfix_expression_return postfix_expression156 = null;

        RVCCalParser.un_expr_return un_expr158 = null;

        RVCCalParser.un_expr_return un_expr160 = null;

        RVCCalParser.un_expr_return un_expr162 = null;


        Object char_literal157_tree=null;
        Object string_literal159_tree=null;
        Object char_literal161_tree=null;
        RewriteRuleTokenStream stream_94=new RewriteRuleTokenStream(adaptor,"token 94");
        RewriteRuleTokenStream stream_101=new RewriteRuleTokenStream(adaptor,"token 101");
        RewriteRuleTokenStream stream_100=new RewriteRuleTokenStream(adaptor,"token 100");
        RewriteRuleSubtreeStream stream_postfix_expression=new RewriteRuleSubtreeStream(adaptor,"rule postfix_expression");
        RewriteRuleSubtreeStream stream_un_expr=new RewriteRuleSubtreeStream(adaptor,"rule un_expr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:8: ( postfix_expression -> postfix_expression | '-' un_expr -> ^( EXPR_UN '-' un_expr ) | 'not' un_expr -> ^( EXPR_UN 'not' un_expr ) | '#' un_expr -> ^( EXPR_UN '#' un_expr ) )
            int alt87=4;
            switch ( input.LA(1) ) {
            case ID:
            case FLOAT:
            case INTEGER:
            case STRING:
            case 57:
            case 63:
            case 102:
            case 105:
            case 106:
                {
                alt87=1;
                }
                break;
            case 94:
                {
                alt87=2;
                }
                break;
            case 100:
                {
                alt87=3;
                }
                break;
            case 101:
                {
                alt87=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 87, 0, input);

                throw nvae;
            }

            switch (alt87) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:10: postfix_expression
                    {
                    pushFollow(FOLLOW_postfix_expression_in_un_expr1690);
                    postfix_expression156=postfix_expression();

                    state._fsp--;

                    stream_postfix_expression.add(postfix_expression156.getTree());


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
                    // 239:29: -> postfix_expression
                    {
                        adaptor.addChild(root_0, stream_postfix_expression.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:4: '-' un_expr
                    {
                    char_literal157=(Token)match(input,94,FOLLOW_94_in_un_expr1699);  
                    stream_94.add(char_literal157);

                    pushFollow(FOLLOW_un_expr_in_un_expr1701);
                    un_expr158=un_expr();

                    state._fsp--;

                    stream_un_expr.add(un_expr158.getTree());


                    // AST REWRITE
                    // elements: 94, un_expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 240:16: -> ^( EXPR_UN '-' un_expr )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:19: ^( EXPR_UN '-' un_expr )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_UN, "EXPR_UN"), root_1);

                        adaptor.addChild(root_1, stream_94.nextNode());
                        adaptor.addChild(root_1, stream_un_expr.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:241:4: 'not' un_expr
                    {
                    string_literal159=(Token)match(input,100,FOLLOW_100_in_un_expr1716);  
                    stream_100.add(string_literal159);

                    pushFollow(FOLLOW_un_expr_in_un_expr1718);
                    un_expr160=un_expr();

                    state._fsp--;

                    stream_un_expr.add(un_expr160.getTree());


                    // AST REWRITE
                    // elements: un_expr, 100
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 241:18: -> ^( EXPR_UN 'not' un_expr )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:241:21: ^( EXPR_UN 'not' un_expr )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_UN, "EXPR_UN"), root_1);

                        adaptor.addChild(root_1, stream_100.nextNode());
                        adaptor.addChild(root_1, stream_un_expr.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:4: '#' un_expr
                    {
                    char_literal161=(Token)match(input,101,FOLLOW_101_in_un_expr1733);  
                    stream_101.add(char_literal161);

                    pushFollow(FOLLOW_un_expr_in_un_expr1735);
                    un_expr162=un_expr();

                    state._fsp--;

                    stream_un_expr.add(un_expr162.getTree());


                    // AST REWRITE
                    // elements: un_expr, 101
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 242:17: -> ^( EXPR_UN '#' un_expr )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:242:20: ^( EXPR_UN '#' un_expr )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_UN, "EXPR_UN"), root_1);

                        adaptor.addChild(root_1, stream_101.nextNode());
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:1: postfix_expression : ( '[' e= expressions ( ':' g= expressionGenerators )? ']' -> ^( EXPR_LIST $e ( $g)? ) | 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end' -> ^( EXPR_IF $e1 $e2 $e3) | constant -> constant | '(' expression ')' -> expression | var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) ) );
    public final RVCCalParser.postfix_expression_return postfix_expression() throws RecognitionException {
        RVCCalParser.postfix_expression_return retval = new RVCCalParser.postfix_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token var=null;
        Token char_literal163=null;
        Token char_literal164=null;
        Token char_literal165=null;
        Token string_literal166=null;
        Token string_literal167=null;
        Token string_literal168=null;
        Token string_literal169=null;
        Token char_literal171=null;
        Token char_literal173=null;
        Token char_literal174=null;
        Token char_literal176=null;
        Token char_literal177=null;
        Token char_literal179=null;
        RVCCalParser.expressions_return e = null;

        RVCCalParser.expressionGenerators_return g = null;

        RVCCalParser.expression_return e1 = null;

        RVCCalParser.expression_return e2 = null;

        RVCCalParser.expression_return e3 = null;

        RVCCalParser.constant_return constant170 = null;

        RVCCalParser.expression_return expression172 = null;

        RVCCalParser.expressions_return expressions175 = null;

        RVCCalParser.expressions_return expressions178 = null;


        Object var_tree=null;
        Object char_literal163_tree=null;
        Object char_literal164_tree=null;
        Object char_literal165_tree=null;
        Object string_literal166_tree=null;
        Object string_literal167_tree=null;
        Object string_literal168_tree=null;
        Object string_literal169_tree=null;
        Object char_literal171_tree=null;
        Object char_literal173_tree=null;
        Object char_literal174_tree=null;
        Object char_literal176_tree=null;
        Object char_literal177_tree=null;
        Object char_literal179_tree=null;
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleTokenStream stream_104=new RewriteRuleTokenStream(adaptor,"token 104");
        RewriteRuleTokenStream stream_63=new RewriteRuleTokenStream(adaptor,"token 63");
        RewriteRuleTokenStream stream_103=new RewriteRuleTokenStream(adaptor,"token 103");
        RewriteRuleTokenStream stream_102=new RewriteRuleTokenStream(adaptor,"token 102");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_constant=new RewriteRuleSubtreeStream(adaptor,"rule constant");
        RewriteRuleSubtreeStream stream_expressionGenerators=new RewriteRuleSubtreeStream(adaptor,"rule expressionGenerators");
        RewriteRuleSubtreeStream stream_expressions=new RewriteRuleSubtreeStream(adaptor,"rule expressions");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:19: ( '[' e= expressions ( ':' g= expressionGenerators )? ']' -> ^( EXPR_LIST $e ( $g)? ) | 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end' -> ^( EXPR_IF $e1 $e2 $e3) | constant -> constant | '(' expression ')' -> expression | var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) ) )
            int alt92=5;
            switch ( input.LA(1) ) {
            case 57:
                {
                alt92=1;
                }
                break;
            case 102:
                {
                alt92=2;
                }
                break;
            case FLOAT:
            case INTEGER:
            case STRING:
            case 105:
            case 106:
                {
                alt92=3;
                }
                break;
            case 63:
                {
                alt92=4;
                }
                break;
            case ID:
                {
                alt92=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 92, 0, input);

                throw nvae;
            }

            switch (alt92) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:3: '[' e= expressions ( ':' g= expressionGenerators )? ']'
                    {
                    char_literal163=(Token)match(input,57,FOLLOW_57_in_postfix_expression1755);  
                    stream_57.add(char_literal163);

                    pushFollow(FOLLOW_expressions_in_postfix_expression1759);
                    e=expressions();

                    state._fsp--;

                    stream_expressions.add(e.getTree());
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:21: ( ':' g= expressionGenerators )?
                    int alt88=2;
                    int LA88_0 = input.LA(1);

                    if ( (LA88_0==56) ) {
                        alt88=1;
                    }
                    switch (alt88) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:22: ':' g= expressionGenerators
                            {
                            char_literal164=(Token)match(input,56,FOLLOW_56_in_postfix_expression1762);  
                            stream_56.add(char_literal164);

                            pushFollow(FOLLOW_expressionGenerators_in_postfix_expression1766);
                            g=expressionGenerators();

                            state._fsp--;

                            stream_expressionGenerators.add(g.getTree());

                            }
                            break;

                    }

                    char_literal165=(Token)match(input,58,FOLLOW_58_in_postfix_expression1770);  
                    stream_58.add(char_literal165);



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
                    // 245:55: -> ^( EXPR_LIST $e ( $g)? )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:58: ^( EXPR_LIST $e ( $g)? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_LIST, "EXPR_LIST"), root_1);

                        adaptor.addChild(root_1, stream_e.nextTree());
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:73: ( $g)?
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:246:3: 'if' e1= expression 'then' e2= expression 'else' e3= expression 'end'
                    {
                    string_literal166=(Token)match(input,102,FOLLOW_102_in_postfix_expression1787);  
                    stream_102.add(string_literal166);

                    pushFollow(FOLLOW_expression_in_postfix_expression1791);
                    e1=expression();

                    state._fsp--;

                    stream_expression.add(e1.getTree());
                    string_literal167=(Token)match(input,103,FOLLOW_103_in_postfix_expression1793);  
                    stream_103.add(string_literal167);

                    pushFollow(FOLLOW_expression_in_postfix_expression1797);
                    e2=expression();

                    state._fsp--;

                    stream_expression.add(e2.getTree());
                    string_literal168=(Token)match(input,104,FOLLOW_104_in_postfix_expression1799);  
                    stream_104.add(string_literal168);

                    pushFollow(FOLLOW_expression_in_postfix_expression1803);
                    e3=expression();

                    state._fsp--;

                    stream_expression.add(e3.getTree());
                    string_literal169=(Token)match(input,66,FOLLOW_66_in_postfix_expression1805);  
                    stream_66.add(string_literal169);



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
                    // 246:70: -> ^( EXPR_IF $e1 $e2 $e3)
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:246:73: ^( EXPR_IF $e1 $e2 $e3)
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:247:3: constant
                    {
                    pushFollow(FOLLOW_constant_in_postfix_expression1824);
                    constant170=constant();

                    state._fsp--;

                    stream_constant.add(constant170.getTree());


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
                    // 247:12: -> constant
                    {
                        adaptor.addChild(root_0, stream_constant.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:248:3: '(' expression ')'
                    {
                    char_literal171=(Token)match(input,63,FOLLOW_63_in_postfix_expression1832);  
                    stream_63.add(char_literal171);

                    pushFollow(FOLLOW_expression_in_postfix_expression1834);
                    expression172=expression();

                    state._fsp--;

                    stream_expression.add(expression172.getTree());
                    char_literal173=(Token)match(input,64,FOLLOW_64_in_postfix_expression1836);  
                    stream_64.add(char_literal173);



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
                    // 248:22: -> expression
                    {
                        adaptor.addChild(root_0, stream_expression.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:249:3: var= ID ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) )
                    {
                    var=(Token)match(input,ID,FOLLOW_ID_in_postfix_expression1846);  
                    stream_ID.add(var);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:249:10: ( '(' ( expressions )? ')' -> ^( EXPR_CALL $var ( expressions )? ) | ( '[' expressions ']' )+ -> ^( EXPR_IDX $var ( expressions )+ ) | -> ^( EXPR_VAR $var) )
                    int alt91=3;
                    switch ( input.LA(1) ) {
                    case 63:
                        {
                        alt91=1;
                        }
                        break;
                    case 57:
                        {
                        alt91=2;
                        }
                        break;
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 58:
                    case 59:
                    case 61:
                    case 64:
                    case 65:
                    case 66:
                    case 69:
                    case 71:
                    case 73:
                    case 77:
                    case 80:
                    case 81:
                    case 82:
                    case 83:
                    case 84:
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
                    case 103:
                    case 104:
                    case 115:
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
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:250:5: '(' ( expressions )? ')'
                            {
                            char_literal174=(Token)match(input,63,FOLLOW_63_in_postfix_expression1854);  
                            stream_63.add(char_literal174);

                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:250:9: ( expressions )?
                            int alt89=2;
                            int LA89_0 = input.LA(1);

                            if ( ((LA89_0>=ID && LA89_0<=STRING)||LA89_0==57||LA89_0==63||LA89_0==94||(LA89_0>=100 && LA89_0<=102)||(LA89_0>=105 && LA89_0<=106)) ) {
                                alt89=1;
                            }
                            switch (alt89) {
                                case 1 :
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:250:9: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_postfix_expression1856);
                                    expressions175=expressions();

                                    state._fsp--;

                                    stream_expressions.add(expressions175.getTree());

                                    }
                                    break;

                            }

                            char_literal176=(Token)match(input,64,FOLLOW_64_in_postfix_expression1859);  
                            stream_64.add(char_literal176);



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
                            // 250:26: -> ^( EXPR_CALL $var ( expressions )? )
                            {
                                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:250:29: ^( EXPR_CALL $var ( expressions )? )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_CALL, "EXPR_CALL"), root_1);

                                adaptor.addChild(root_1, stream_var.nextNode());
                                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:250:46: ( expressions )?
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
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:6: ( '[' expressions ']' )+
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:6: ( '[' expressions ']' )+
                            int cnt90=0;
                            loop90:
                            do {
                                int alt90=2;
                                int LA90_0 = input.LA(1);

                                if ( (LA90_0==57) ) {
                                    alt90=1;
                                }


                                switch (alt90) {
                            	case 1 :
                            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:7: '[' expressions ']'
                            	    {
                            	    char_literal177=(Token)match(input,57,FOLLOW_57_in_postfix_expression1879);  
                            	    stream_57.add(char_literal177);

                            	    pushFollow(FOLLOW_expressions_in_postfix_expression1881);
                            	    expressions178=expressions();

                            	    state._fsp--;

                            	    stream_expressions.add(expressions178.getTree());
                            	    char_literal179=(Token)match(input,58,FOLLOW_58_in_postfix_expression1883);  
                            	    stream_58.add(char_literal179);


                            	    }
                            	    break;

                            	default :
                            	    if ( cnt90 >= 1 ) break loop90;
                                        EarlyExitException eee =
                                            new EarlyExitException(90, input);
                                        throw eee;
                                }
                                cnt90++;
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
                            // 251:29: -> ^( EXPR_IDX $var ( expressions )+ )
                            {
                                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:32: ^( EXPR_IDX $var ( expressions )+ )
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
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:252:5: 
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
                            // 252:5: -> ^( EXPR_VAR $var)
                            {
                                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:252:8: ^( EXPR_VAR $var)
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:1: constant : ( 'true' -> ^( EXPR_BOOL 'true' ) | 'false' -> ^( EXPR_BOOL 'false' ) | FLOAT -> ^( EXPR_FLOAT FLOAT ) | INTEGER -> ^( EXPR_INT INTEGER ) | STRING -> ^( EXPR_STRING STRING ) );
    public final RVCCalParser.constant_return constant() throws RecognitionException {
        RVCCalParser.constant_return retval = new RVCCalParser.constant_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal180=null;
        Token string_literal181=null;
        Token FLOAT182=null;
        Token INTEGER183=null;
        Token STRING184=null;

        Object string_literal180_tree=null;
        Object string_literal181_tree=null;
        Object FLOAT182_tree=null;
        Object INTEGER183_tree=null;
        Object STRING184_tree=null;
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_FLOAT=new RewriteRuleTokenStream(adaptor,"token FLOAT");
        RewriteRuleTokenStream stream_106=new RewriteRuleTokenStream(adaptor,"token 106");
        RewriteRuleTokenStream stream_105=new RewriteRuleTokenStream(adaptor,"token 105");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:254:9: ( 'true' -> ^( EXPR_BOOL 'true' ) | 'false' -> ^( EXPR_BOOL 'false' ) | FLOAT -> ^( EXPR_FLOAT FLOAT ) | INTEGER -> ^( EXPR_INT INTEGER ) | STRING -> ^( EXPR_STRING STRING ) )
            int alt93=5;
            switch ( input.LA(1) ) {
            case 105:
                {
                alt93=1;
                }
                break;
            case 106:
                {
                alt93=2;
                }
                break;
            case FLOAT:
                {
                alt93=3;
                }
                break;
            case INTEGER:
                {
                alt93=4;
                }
                break;
            case STRING:
                {
                alt93=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 93, 0, input);

                throw nvae;
            }

            switch (alt93) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:255:3: 'true'
                    {
                    string_literal180=(Token)match(input,105,FOLLOW_105_in_constant1920);  
                    stream_105.add(string_literal180);



                    // AST REWRITE
                    // elements: 105
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 255:10: -> ^( EXPR_BOOL 'true' )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:255:13: ^( EXPR_BOOL 'true' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BOOL, "EXPR_BOOL"), root_1);

                        adaptor.addChild(root_1, stream_105.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:3: 'false'
                    {
                    string_literal181=(Token)match(input,106,FOLLOW_106_in_constant1932);  
                    stream_106.add(string_literal181);



                    // AST REWRITE
                    // elements: 106
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 256:11: -> ^( EXPR_BOOL 'false' )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:14: ^( EXPR_BOOL 'false' )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR_BOOL, "EXPR_BOOL"), root_1);

                        adaptor.addChild(root_1, stream_106.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:257:3: FLOAT
                    {
                    FLOAT182=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_constant1944);  
                    stream_FLOAT.add(FLOAT182);



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
                    // 257:9: -> ^( EXPR_FLOAT FLOAT )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:257:12: ^( EXPR_FLOAT FLOAT )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:258:3: INTEGER
                    {
                    INTEGER183=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_constant1956);  
                    stream_INTEGER.add(INTEGER183);



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
                    // 258:11: -> ^( EXPR_INT INTEGER )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:258:14: ^( EXPR_INT INTEGER )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:3: STRING
                    {
                    STRING184=(Token)match(input,STRING,FOLLOW_STRING_in_constant1968);  
                    stream_STRING.add(STRING184);



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
                    // 259:10: -> ^( EXPR_STRING STRING )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:13: ^( EXPR_STRING STRING )
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:261:1: expressionGenerator : 'for' typeDef ID 'in' expression ;
    public final RVCCalParser.expressionGenerator_return expressionGenerator() throws RecognitionException {
        RVCCalParser.expressionGenerator_return retval = new RVCCalParser.expressionGenerator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal185=null;
        Token ID187=null;
        Token string_literal188=null;
        RVCCalParser.typeDef_return typeDef186 = null;

        RVCCalParser.expression_return expression189 = null;


        Object string_literal185_tree=null;
        Object ID187_tree=null;
        Object string_literal188_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:261:20: ( 'for' typeDef ID 'in' expression )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:262:2: 'for' typeDef ID 'in' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal185=(Token)match(input,107,FOLLOW_107_in_expressionGenerator1984); 
            string_literal185_tree = (Object)adaptor.create(string_literal185);
            adaptor.addChild(root_0, string_literal185_tree);

            pushFollow(FOLLOW_typeDef_in_expressionGenerator1986);
            typeDef186=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef186.getTree());
            ID187=(Token)match(input,ID,FOLLOW_ID_in_expressionGenerator1988); 
            ID187_tree = (Object)adaptor.create(ID187);
            adaptor.addChild(root_0, ID187_tree);

            string_literal188=(Token)match(input,108,FOLLOW_108_in_expressionGenerator1990); 
            string_literal188_tree = (Object)adaptor.create(string_literal188);
            adaptor.addChild(root_0, string_literal188_tree);

            pushFollow(FOLLOW_expression_in_expressionGenerator1992);
            expression189=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression189.getTree());
             

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:1: expressionGenerators : expressionGenerator ( ',' expressionGenerator )* -> ( expressionGenerator )+ ;
    public final RVCCalParser.expressionGenerators_return expressionGenerators() throws RecognitionException {
        RVCCalParser.expressionGenerators_return retval = new RVCCalParser.expressionGenerators_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal191=null;
        RVCCalParser.expressionGenerator_return expressionGenerator190 = null;

        RVCCalParser.expressionGenerator_return expressionGenerator192 = null;


        Object char_literal191_tree=null;
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleSubtreeStream stream_expressionGenerator=new RewriteRuleSubtreeStream(adaptor,"rule expressionGenerator");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:21: ( expressionGenerator ( ',' expressionGenerator )* -> ( expressionGenerator )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:23: expressionGenerator ( ',' expressionGenerator )*
            {
            pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators2002);
            expressionGenerator190=expressionGenerator();

            state._fsp--;

            stream_expressionGenerator.add(expressionGenerator190.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:43: ( ',' expressionGenerator )*
            loop94:
            do {
                int alt94=2;
                int LA94_0 = input.LA(1);

                if ( (LA94_0==59) ) {
                    alt94=1;
                }


                switch (alt94) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:44: ',' expressionGenerator
            	    {
            	    char_literal191=(Token)match(input,59,FOLLOW_59_in_expressionGenerators2005);  
            	    stream_59.add(char_literal191);

            	    pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators2007);
            	    expressionGenerator192=expressionGenerator();

            	    state._fsp--;

            	    stream_expressionGenerator.add(expressionGenerator192.getTree());

            	    }
            	    break;

            	default :
            	    break loop94;
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
            // 265:70: -> ( expressionGenerator )+
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:267:1: expressions : expression ( ',' expression )* -> ( expression )+ ;
    public final RVCCalParser.expressions_return expressions() throws RecognitionException {
        RVCCalParser.expressions_return retval = new RVCCalParser.expressions_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal194=null;
        RVCCalParser.expression_return expression193 = null;

        RVCCalParser.expression_return expression195 = null;


        Object char_literal194_tree=null;
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:267:12: ( expression ( ',' expression )* -> ( expression )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:267:14: expression ( ',' expression )*
            {
            pushFollow(FOLLOW_expression_in_expressions2021);
            expression193=expression();

            state._fsp--;

            stream_expression.add(expression193.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:267:25: ( ',' expression )*
            loop95:
            do {
                int alt95=2;
                int LA95_0 = input.LA(1);

                if ( (LA95_0==59) ) {
                    alt95=1;
                }


                switch (alt95) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:267:26: ',' expression
            	    {
            	    char_literal194=(Token)match(input,59,FOLLOW_59_in_expressions2024);  
            	    stream_59.add(char_literal194);

            	    pushFollow(FOLLOW_expression_in_expressions2026);
            	    expression195=expression();

            	    state._fsp--;

            	    stream_expression.add(expression195.getTree());

            	    }
            	    break;

            	default :
            	    break loop95;
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
            // 267:43: -> ( expression )+
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:269:1: idents : ID ( ',' ID )* ;
    public final RVCCalParser.idents_return idents() throws RecognitionException {
        RVCCalParser.idents_return retval = new RVCCalParser.idents_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID196=null;
        Token char_literal197=null;
        Token ID198=null;

        Object ID196_tree=null;
        Object char_literal197_tree=null;
        Object ID198_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:272:7: ( ID ( ',' ID )* )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:272:9: ID ( ',' ID )*
            {
            root_0 = (Object)adaptor.nil();

            ID196=(Token)match(input,ID,FOLLOW_ID_in_idents2045); 
            ID196_tree = (Object)adaptor.create(ID196);
            adaptor.addChild(root_0, ID196_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:272:12: ( ',' ID )*
            loop96:
            do {
                int alt96=2;
                int LA96_0 = input.LA(1);

                if ( (LA96_0==59) ) {
                    alt96=1;
                }


                switch (alt96) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:272:13: ',' ID
            	    {
            	    char_literal197=(Token)match(input,59,FOLLOW_59_in_idents2048); 
            	    char_literal197_tree = (Object)adaptor.create(char_literal197);
            	    adaptor.addChild(root_0, char_literal197_tree);

            	    ID198=(Token)match(input,ID,FOLLOW_ID_in_idents2050); 
            	    ID198_tree = (Object)adaptor.create(ID198);
            	    adaptor.addChild(root_0, ID198_tree);


            	    }
            	    break;

            	default :
            	    break loop96;
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:274:1: priorityInequality : qualifiedIdent ( '>' qualifiedIdent )+ ';' ;
    public final RVCCalParser.priorityInequality_return priorityInequality() throws RecognitionException {
        RVCCalParser.priorityInequality_return retval = new RVCCalParser.priorityInequality_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal200=null;
        Token char_literal202=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent199 = null;

        RVCCalParser.qualifiedIdent_return qualifiedIdent201 = null;


        Object char_literal200_tree=null;
        Object char_literal202_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:19: ( qualifiedIdent ( '>' qualifiedIdent )+ ';' )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:21: qualifiedIdent ( '>' qualifiedIdent )+ ';'
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality2066);
            qualifiedIdent199=qualifiedIdent();

            state._fsp--;

            adaptor.addChild(root_0, qualifiedIdent199.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:36: ( '>' qualifiedIdent )+
            int cnt97=0;
            loop97:
            do {
                int alt97=2;
                int LA97_0 = input.LA(1);

                if ( (LA97_0==88) ) {
                    alt97=1;
                }


                switch (alt97) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:37: '>' qualifiedIdent
            	    {
            	    char_literal200=(Token)match(input,88,FOLLOW_88_in_priorityInequality2069); 
            	    char_literal200_tree = (Object)adaptor.create(char_literal200);
            	    adaptor.addChild(root_0, char_literal200_tree);

            	    pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality2071);
            	    qualifiedIdent201=qualifiedIdent();

            	    state._fsp--;

            	    adaptor.addChild(root_0, qualifiedIdent201.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt97 >= 1 ) break loop97;
                        EarlyExitException eee =
                            new EarlyExitException(97, input);
                        throw eee;
                }
                cnt97++;
            } while (true);

            char_literal202=(Token)match(input,73,FOLLOW_73_in_priorityInequality2075); 
            char_literal202_tree = (Object)adaptor.create(char_literal202);
            adaptor.addChild(root_0, char_literal202_tree);

             

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:279:1: priorityOrder : 'priority' ( priorityInequality )* 'end' ;
    public final RVCCalParser.priorityOrder_return priorityOrder() throws RecognitionException {
        RVCCalParser.priorityOrder_return retval = new RVCCalParser.priorityOrder_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal203=null;
        Token string_literal205=null;
        RVCCalParser.priorityInequality_return priorityInequality204 = null;


        Object string_literal203_tree=null;
        Object string_literal205_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:279:14: ( 'priority' ( priorityInequality )* 'end' )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:279:16: 'priority' ( priorityInequality )* 'end'
            {
            root_0 = (Object)adaptor.nil();

            string_literal203=(Token)match(input,109,FOLLOW_109_in_priorityOrder2085); 
            string_literal203_tree = (Object)adaptor.create(string_literal203);
            adaptor.addChild(root_0, string_literal203_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:279:27: ( priorityInequality )*
            loop98:
            do {
                int alt98=2;
                int LA98_0 = input.LA(1);

                if ( (LA98_0==ID) ) {
                    alt98=1;
                }


                switch (alt98) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:279:28: priorityInequality
            	    {
            	    pushFollow(FOLLOW_priorityInequality_in_priorityOrder2088);
            	    priorityInequality204=priorityInequality();

            	    state._fsp--;

            	    adaptor.addChild(root_0, priorityInequality204.getTree());

            	    }
            	    break;

            	default :
            	    break loop98;
                }
            } while (true);

            string_literal205=(Token)match(input,66,FOLLOW_66_in_priorityOrder2092); 
            string_literal205_tree = (Object)adaptor.create(string_literal205);
            adaptor.addChild(root_0, string_literal205_tree);

             

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:281:1: qualifiedIdent : ID ( '.' ID )* ;
    public final RVCCalParser.qualifiedIdent_return qualifiedIdent() throws RecognitionException {
        RVCCalParser.qualifiedIdent_return retval = new RVCCalParser.qualifiedIdent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID206=null;
        Token char_literal207=null;
        Token ID208=null;

        Object ID206_tree=null;
        Object char_literal207_tree=null;
        Object ID208_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:284:15: ( ID ( '.' ID )* )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:284:17: ID ( '.' ID )*
            {
            root_0 = (Object)adaptor.nil();

            ID206=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent2106); 
            ID206_tree = (Object)adaptor.create(ID206);
            adaptor.addChild(root_0, ID206_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:284:20: ( '.' ID )*
            loop99:
            do {
                int alt99=2;
                int LA99_0 = input.LA(1);

                if ( (LA99_0==67) ) {
                    alt99=1;
                }


                switch (alt99) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:284:21: '.' ID
            	    {
            	    char_literal207=(Token)match(input,67,FOLLOW_67_in_qualifiedIdent2109); 
            	    char_literal207_tree = (Object)adaptor.create(char_literal207);
            	    adaptor.addChild(root_0, char_literal207_tree);

            	    ID208=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent2111); 
            	    ID208_tree = (Object)adaptor.create(ID208);
            	    adaptor.addChild(root_0, ID208_tree);


            	    }
            	    break;

            	default :
            	    break loop99;
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
    // $ANTLR end "qualifiedIdent"

    public static class schedule_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "schedule"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:286:1: schedule : 'schedule' ( 'fsm' ID ':' ( stateTransition )* 'end' | 'regexp' ) ;
    public final RVCCalParser.schedule_return schedule() throws RecognitionException {
        RVCCalParser.schedule_return retval = new RVCCalParser.schedule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal209=null;
        Token string_literal210=null;
        Token ID211=null;
        Token char_literal212=null;
        Token string_literal214=null;
        Token string_literal215=null;
        RVCCalParser.stateTransition_return stateTransition213 = null;


        Object string_literal209_tree=null;
        Object string_literal210_tree=null;
        Object ID211_tree=null;
        Object char_literal212_tree=null;
        Object string_literal214_tree=null;
        Object string_literal215_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:289:9: ( 'schedule' ( 'fsm' ID ':' ( stateTransition )* 'end' | 'regexp' ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:290:3: 'schedule' ( 'fsm' ID ':' ( stateTransition )* 'end' | 'regexp' )
            {
            root_0 = (Object)adaptor.nil();

            string_literal209=(Token)match(input,110,FOLLOW_110_in_schedule2129); 
            string_literal209_tree = (Object)adaptor.create(string_literal209);
            adaptor.addChild(root_0, string_literal209_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:291:5: ( 'fsm' ID ':' ( stateTransition )* 'end' | 'regexp' )
            int alt101=2;
            int LA101_0 = input.LA(1);

            if ( (LA101_0==111) ) {
                alt101=1;
            }
            else if ( (LA101_0==112) ) {
                alt101=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 101, 0, input);

                throw nvae;
            }
            switch (alt101) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:291:6: 'fsm' ID ':' ( stateTransition )* 'end'
                    {
                    string_literal210=(Token)match(input,111,FOLLOW_111_in_schedule2136); 
                    string_literal210_tree = (Object)adaptor.create(string_literal210);
                    adaptor.addChild(root_0, string_literal210_tree);

                    ID211=(Token)match(input,ID,FOLLOW_ID_in_schedule2138); 
                    ID211_tree = (Object)adaptor.create(ID211);
                    adaptor.addChild(root_0, ID211_tree);

                    char_literal212=(Token)match(input,56,FOLLOW_56_in_schedule2140); 
                    char_literal212_tree = (Object)adaptor.create(char_literal212);
                    adaptor.addChild(root_0, char_literal212_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:291:19: ( stateTransition )*
                    loop100:
                    do {
                        int alt100=2;
                        int LA100_0 = input.LA(1);

                        if ( (LA100_0==ID) ) {
                            alt100=1;
                        }


                        switch (alt100) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:291:19: stateTransition
                    	    {
                    	    pushFollow(FOLLOW_stateTransition_in_schedule2142);
                    	    stateTransition213=stateTransition();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, stateTransition213.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop100;
                        }
                    } while (true);

                    string_literal214=(Token)match(input,66,FOLLOW_66_in_schedule2145); 
                    string_literal214_tree = (Object)adaptor.create(string_literal214);
                    adaptor.addChild(root_0, string_literal214_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:292:7: 'regexp'
                    {
                    string_literal215=(Token)match(input,112,FOLLOW_112_in_schedule2155); 
                    string_literal215_tree = (Object)adaptor.create(string_literal215);
                    adaptor.addChild(root_0, string_literal215_tree);

                     System.out.println("RVC-CAL does not support \"regexp\" schedules."); 

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
    // $ANTLR end "schedule"

    public static class stateTransition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stateTransition"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:294:1: stateTransition : ID '(' qualifiedIdent ')' '-->' ID ';' ;
    public final RVCCalParser.stateTransition_return stateTransition() throws RecognitionException {
        RVCCalParser.stateTransition_return retval = new RVCCalParser.stateTransition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID216=null;
        Token char_literal217=null;
        Token char_literal219=null;
        Token string_literal220=null;
        Token ID221=null;
        Token char_literal222=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent218 = null;


        Object ID216_tree=null;
        Object char_literal217_tree=null;
        Object char_literal219_tree=null;
        Object string_literal220_tree=null;
        Object ID221_tree=null;
        Object char_literal222_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:294:16: ( ID '(' qualifiedIdent ')' '-->' ID ';' )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:295:2: ID '(' qualifiedIdent ')' '-->' ID ';'
            {
            root_0 = (Object)adaptor.nil();

            ID216=(Token)match(input,ID,FOLLOW_ID_in_stateTransition2167); 
            ID216_tree = (Object)adaptor.create(ID216);
            adaptor.addChild(root_0, ID216_tree);

            char_literal217=(Token)match(input,63,FOLLOW_63_in_stateTransition2169); 
            char_literal217_tree = (Object)adaptor.create(char_literal217);
            adaptor.addChild(root_0, char_literal217_tree);

            pushFollow(FOLLOW_qualifiedIdent_in_stateTransition2171);
            qualifiedIdent218=qualifiedIdent();

            state._fsp--;

            adaptor.addChild(root_0, qualifiedIdent218.getTree());
            char_literal219=(Token)match(input,64,FOLLOW_64_in_stateTransition2173); 
            char_literal219_tree = (Object)adaptor.create(char_literal219);
            adaptor.addChild(root_0, char_literal219_tree);

            string_literal220=(Token)match(input,75,FOLLOW_75_in_stateTransition2175); 
            string_literal220_tree = (Object)adaptor.create(string_literal220);
            adaptor.addChild(root_0, string_literal220_tree);

            ID221=(Token)match(input,ID,FOLLOW_ID_in_stateTransition2177); 
            ID221_tree = (Object)adaptor.create(ID221);
            adaptor.addChild(root_0, ID221_tree);

            char_literal222=(Token)match(input,73,FOLLOW_73_in_stateTransition2179); 
            char_literal222_tree = (Object)adaptor.create(char_literal222);
            adaptor.addChild(root_0, char_literal222_tree);

             

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:297:1: statement : ( 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end' | 'choose' | 'for' | 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end' | 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end' | 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end' | ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' ) );
    public final RVCCalParser.statement_return statement() throws RecognitionException {
        RVCCalParser.statement_return retval = new RVCCalParser.statement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal223=null;
        Token string_literal224=null;
        Token string_literal226=null;
        Token string_literal228=null;
        Token string_literal229=null;
        Token string_literal230=null;
        Token string_literal231=null;
        Token string_literal233=null;
        Token string_literal235=null;
        Token string_literal237=null;
        Token string_literal239=null;
        Token string_literal241=null;
        Token string_literal242=null;
        Token string_literal244=null;
        Token string_literal246=null;
        Token string_literal248=null;
        Token string_literal249=null;
        Token string_literal251=null;
        Token string_literal253=null;
        Token string_literal255=null;
        Token ID256=null;
        Token char_literal257=null;
        Token char_literal259=null;
        Token string_literal260=null;
        Token char_literal262=null;
        Token char_literal263=null;
        Token char_literal265=null;
        Token char_literal266=null;
        RVCCalParser.varDecls_return varDecls225 = null;

        RVCCalParser.statement_return statement227 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr232 = null;

        RVCCalParser.expression_return expression234 = null;

        RVCCalParser.expression_return expression236 = null;

        RVCCalParser.varDecls_return varDecls238 = null;

        RVCCalParser.statement_return statement240 = null;

        RVCCalParser.expression_return expression243 = null;

        RVCCalParser.statement_return statement245 = null;

        RVCCalParser.statement_return statement247 = null;

        RVCCalParser.expression_return expression250 = null;

        RVCCalParser.varDecls_return varDecls252 = null;

        RVCCalParser.statement_return statement254 = null;

        RVCCalParser.expressions_return expressions258 = null;

        RVCCalParser.expression_return expression261 = null;

        RVCCalParser.expressions_return expressions264 = null;


        Object string_literal223_tree=null;
        Object string_literal224_tree=null;
        Object string_literal226_tree=null;
        Object string_literal228_tree=null;
        Object string_literal229_tree=null;
        Object string_literal230_tree=null;
        Object string_literal231_tree=null;
        Object string_literal233_tree=null;
        Object string_literal235_tree=null;
        Object string_literal237_tree=null;
        Object string_literal239_tree=null;
        Object string_literal241_tree=null;
        Object string_literal242_tree=null;
        Object string_literal244_tree=null;
        Object string_literal246_tree=null;
        Object string_literal248_tree=null;
        Object string_literal249_tree=null;
        Object string_literal251_tree=null;
        Object string_literal253_tree=null;
        Object string_literal255_tree=null;
        Object ID256_tree=null;
        Object char_literal257_tree=null;
        Object char_literal259_tree=null;
        Object string_literal260_tree=null;
        Object char_literal262_tree=null;
        Object char_literal263_tree=null;
        Object char_literal265_tree=null;
        Object char_literal266_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:300:10: ( 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end' | 'choose' | 'for' | 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end' | 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end' | 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end' | ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' ) )
            int alt115=7;
            switch ( input.LA(1) ) {
            case 77:
                {
                alt115=1;
                }
                break;
            case 113:
                {
                alt115=2;
                }
                break;
            case 107:
                {
                alt115=3;
                }
                break;
            case 114:
                {
                alt115=4;
                }
                break;
            case 102:
                {
                alt115=5;
                }
                break;
            case 116:
                {
                alt115=6;
                }
                break;
            case ID:
                {
                alt115=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 115, 0, input);

                throw nvae;
            }

            switch (alt115) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:301:3: 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal223=(Token)match(input,77,FOLLOW_77_in_statement2195); 
                    string_literal223_tree = (Object)adaptor.create(string_literal223);
                    adaptor.addChild(root_0, string_literal223_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:301:11: ( 'var' varDecls 'do' )?
                    int alt102=2;
                    int LA102_0 = input.LA(1);

                    if ( (LA102_0==69) ) {
                        alt102=1;
                    }
                    switch (alt102) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:301:12: 'var' varDecls 'do'
                            {
                            string_literal224=(Token)match(input,69,FOLLOW_69_in_statement2198); 
                            string_literal224_tree = (Object)adaptor.create(string_literal224);
                            adaptor.addChild(root_0, string_literal224_tree);

                            pushFollow(FOLLOW_varDecls_in_statement2200);
                            varDecls225=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls225.getTree());
                            string_literal226=(Token)match(input,61,FOLLOW_61_in_statement2202); 
                            string_literal226_tree = (Object)adaptor.create(string_literal226);
                            adaptor.addChild(root_0, string_literal226_tree);


                            }
                            break;

                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:301:34: ( statement )*
                    loop103:
                    do {
                        int alt103=2;
                        int LA103_0 = input.LA(1);

                        if ( (LA103_0==ID||LA103_0==77||LA103_0==102||LA103_0==107||(LA103_0>=113 && LA103_0<=114)||LA103_0==116) ) {
                            alt103=1;
                        }


                        switch (alt103) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:301:34: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement2206);
                    	    statement227=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement227.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop103;
                        }
                    } while (true);

                    string_literal228=(Token)match(input,66,FOLLOW_66_in_statement2209); 
                    string_literal228_tree = (Object)adaptor.create(string_literal228);
                    adaptor.addChild(root_0, string_literal228_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:302:3: 'choose'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal229=(Token)match(input,113,FOLLOW_113_in_statement2215); 
                    string_literal229_tree = (Object)adaptor.create(string_literal229);
                    adaptor.addChild(root_0, string_literal229_tree);

                     System.out.println("RVC-CAL does not support the \"choose\" statement."); 

                    }
                    break;
                case 3 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:303:3: 'for'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal230=(Token)match(input,107,FOLLOW_107_in_statement2221); 
                    string_literal230_tree = (Object)adaptor.create(string_literal230);
                    adaptor.addChild(root_0, string_literal230_tree);

                     System.out.println("RVC-CAL does not support the \"for\" statement, please use \"foreach\" instead."); 

                    }
                    break;
                case 4 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:304:3: 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal231=(Token)match(input,114,FOLLOW_114_in_statement2227); 
                    string_literal231_tree = (Object)adaptor.create(string_literal231);
                    adaptor.addChild(root_0, string_literal231_tree);

                    pushFollow(FOLLOW_varDeclNoExpr_in_statement2229);
                    varDeclNoExpr232=varDeclNoExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, varDeclNoExpr232.getTree());
                    string_literal233=(Token)match(input,108,FOLLOW_108_in_statement2231); 
                    string_literal233_tree = (Object)adaptor.create(string_literal233);
                    adaptor.addChild(root_0, string_literal233_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:304:32: ( expression ( '..' expression )? )
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:304:33: expression ( '..' expression )?
                    {
                    pushFollow(FOLLOW_expression_in_statement2234);
                    expression234=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression234.getTree());
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:304:44: ( '..' expression )?
                    int alt104=2;
                    int LA104_0 = input.LA(1);

                    if ( (LA104_0==115) ) {
                        alt104=1;
                    }
                    switch (alt104) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:304:45: '..' expression
                            {
                            string_literal235=(Token)match(input,115,FOLLOW_115_in_statement2237); 
                            string_literal235_tree = (Object)adaptor.create(string_literal235);
                            adaptor.addChild(root_0, string_literal235_tree);

                            pushFollow(FOLLOW_expression_in_statement2239);
                            expression236=expression();

                            state._fsp--;

                            adaptor.addChild(root_0, expression236.getTree());

                            }
                            break;

                    }


                    }

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:304:64: ( 'var' varDecls )?
                    int alt105=2;
                    int LA105_0 = input.LA(1);

                    if ( (LA105_0==69) ) {
                        alt105=1;
                    }
                    switch (alt105) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:304:65: 'var' varDecls
                            {
                            string_literal237=(Token)match(input,69,FOLLOW_69_in_statement2245); 
                            string_literal237_tree = (Object)adaptor.create(string_literal237);
                            adaptor.addChild(root_0, string_literal237_tree);

                            pushFollow(FOLLOW_varDecls_in_statement2247);
                            varDecls238=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls238.getTree());

                            }
                            break;

                    }

                    string_literal239=(Token)match(input,61,FOLLOW_61_in_statement2251); 
                    string_literal239_tree = (Object)adaptor.create(string_literal239);
                    adaptor.addChild(root_0, string_literal239_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:304:87: ( statement )*
                    loop106:
                    do {
                        int alt106=2;
                        int LA106_0 = input.LA(1);

                        if ( (LA106_0==ID||LA106_0==77||LA106_0==102||LA106_0==107||(LA106_0>=113 && LA106_0<=114)||LA106_0==116) ) {
                            alt106=1;
                        }


                        switch (alt106) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:304:87: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement2253);
                    	    statement240=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement240.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop106;
                        }
                    } while (true);

                    string_literal241=(Token)match(input,66,FOLLOW_66_in_statement2256); 
                    string_literal241_tree = (Object)adaptor.create(string_literal241);
                    adaptor.addChild(root_0, string_literal241_tree);

                     

                    }
                    break;
                case 5 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:305:3: 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal242=(Token)match(input,102,FOLLOW_102_in_statement2262); 
                    string_literal242_tree = (Object)adaptor.create(string_literal242);
                    adaptor.addChild(root_0, string_literal242_tree);

                    pushFollow(FOLLOW_expression_in_statement2264);
                    expression243=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression243.getTree());
                    string_literal244=(Token)match(input,103,FOLLOW_103_in_statement2266); 
                    string_literal244_tree = (Object)adaptor.create(string_literal244);
                    adaptor.addChild(root_0, string_literal244_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:305:26: ( statement )*
                    loop107:
                    do {
                        int alt107=2;
                        int LA107_0 = input.LA(1);

                        if ( (LA107_0==ID||LA107_0==77||LA107_0==102||LA107_0==107||(LA107_0>=113 && LA107_0<=114)||LA107_0==116) ) {
                            alt107=1;
                        }


                        switch (alt107) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:305:26: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement2268);
                    	    statement245=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement245.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop107;
                        }
                    } while (true);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:305:37: ( 'else' ( statement )* )?
                    int alt109=2;
                    int LA109_0 = input.LA(1);

                    if ( (LA109_0==104) ) {
                        alt109=1;
                    }
                    switch (alt109) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:305:38: 'else' ( statement )*
                            {
                            string_literal246=(Token)match(input,104,FOLLOW_104_in_statement2272); 
                            string_literal246_tree = (Object)adaptor.create(string_literal246);
                            adaptor.addChild(root_0, string_literal246_tree);

                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:305:45: ( statement )*
                            loop108:
                            do {
                                int alt108=2;
                                int LA108_0 = input.LA(1);

                                if ( (LA108_0==ID||LA108_0==77||LA108_0==102||LA108_0==107||(LA108_0>=113 && LA108_0<=114)||LA108_0==116) ) {
                                    alt108=1;
                                }


                                switch (alt108) {
                            	case 1 :
                            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:305:45: statement
                            	    {
                            	    pushFollow(FOLLOW_statement_in_statement2274);
                            	    statement247=statement();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, statement247.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop108;
                                }
                            } while (true);


                            }
                            break;

                    }

                    string_literal248=(Token)match(input,66,FOLLOW_66_in_statement2279); 
                    string_literal248_tree = (Object)adaptor.create(string_literal248);
                    adaptor.addChild(root_0, string_literal248_tree);

                      

                    }
                    break;
                case 6 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:306:3: 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal249=(Token)match(input,116,FOLLOW_116_in_statement2285); 
                    string_literal249_tree = (Object)adaptor.create(string_literal249);
                    adaptor.addChild(root_0, string_literal249_tree);

                    pushFollow(FOLLOW_expression_in_statement2287);
                    expression250=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression250.getTree());
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:306:22: ( 'var' varDecls )?
                    int alt110=2;
                    int LA110_0 = input.LA(1);

                    if ( (LA110_0==69) ) {
                        alt110=1;
                    }
                    switch (alt110) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:306:23: 'var' varDecls
                            {
                            string_literal251=(Token)match(input,69,FOLLOW_69_in_statement2290); 
                            string_literal251_tree = (Object)adaptor.create(string_literal251);
                            adaptor.addChild(root_0, string_literal251_tree);

                            pushFollow(FOLLOW_varDecls_in_statement2292);
                            varDecls252=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls252.getTree());

                            }
                            break;

                    }

                    string_literal253=(Token)match(input,61,FOLLOW_61_in_statement2296); 
                    string_literal253_tree = (Object)adaptor.create(string_literal253);
                    adaptor.addChild(root_0, string_literal253_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:306:45: ( statement )*
                    loop111:
                    do {
                        int alt111=2;
                        int LA111_0 = input.LA(1);

                        if ( (LA111_0==ID||LA111_0==77||LA111_0==102||LA111_0==107||(LA111_0>=113 && LA111_0<=114)||LA111_0==116) ) {
                            alt111=1;
                        }


                        switch (alt111) {
                    	case 1 :
                    	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:306:45: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement2298);
                    	    statement254=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement254.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop111;
                        }
                    } while (true);

                    string_literal255=(Token)match(input,66,FOLLOW_66_in_statement2301); 
                    string_literal255_tree = (Object)adaptor.create(string_literal255);
                    adaptor.addChild(root_0, string_literal255_tree);

                      

                    }
                    break;
                case 7 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:308:3: ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' )
                    {
                    root_0 = (Object)adaptor.nil();

                    ID256=(Token)match(input,ID,FOLLOW_ID_in_statement2308); 
                    ID256_tree = (Object)adaptor.create(ID256);
                    adaptor.addChild(root_0, ID256_tree);

                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:308:6: ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' )
                    int alt114=2;
                    int LA114_0 = input.LA(1);

                    if ( (LA114_0==57||LA114_0==72) ) {
                        alt114=1;
                    }
                    else if ( (LA114_0==63) ) {
                        alt114=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 114, 0, input);

                        throw nvae;
                    }
                    switch (alt114) {
                        case 1 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:5: ( ( '[' expressions ']' )? ':=' expression ';' )
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:5: ( ( '[' expressions ']' )? ':=' expression ';' )
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:6: ( '[' expressions ']' )? ':=' expression ';'
                            {
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:6: ( '[' expressions ']' )?
                            int alt112=2;
                            int LA112_0 = input.LA(1);

                            if ( (LA112_0==57) ) {
                                alt112=1;
                            }
                            switch (alt112) {
                                case 1 :
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:7: '[' expressions ']'
                                    {
                                    char_literal257=(Token)match(input,57,FOLLOW_57_in_statement2318); 
                                    char_literal257_tree = (Object)adaptor.create(char_literal257);
                                    adaptor.addChild(root_0, char_literal257_tree);

                                    pushFollow(FOLLOW_expressions_in_statement2320);
                                    expressions258=expressions();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expressions258.getTree());
                                    char_literal259=(Token)match(input,58,FOLLOW_58_in_statement2322); 
                                    char_literal259_tree = (Object)adaptor.create(char_literal259);
                                    adaptor.addChild(root_0, char_literal259_tree);


                                    }
                                    break;

                            }

                            string_literal260=(Token)match(input,72,FOLLOW_72_in_statement2326); 
                            string_literal260_tree = (Object)adaptor.create(string_literal260);
                            adaptor.addChild(root_0, string_literal260_tree);

                            pushFollow(FOLLOW_expression_in_statement2328);
                            expression261=expression();

                            state._fsp--;

                            adaptor.addChild(root_0, expression261.getTree());
                            char_literal262=(Token)match(input,73,FOLLOW_73_in_statement2330); 
                            char_literal262_tree = (Object)adaptor.create(char_literal262);
                            adaptor.addChild(root_0, char_literal262_tree);


                            }

                             

                            }
                            break;
                        case 2 :
                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:310:6: '(' ( expressions )? ')' ';'
                            {
                            char_literal263=(Token)match(input,63,FOLLOW_63_in_statement2340); 
                            char_literal263_tree = (Object)adaptor.create(char_literal263);
                            adaptor.addChild(root_0, char_literal263_tree);

                            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:310:10: ( expressions )?
                            int alt113=2;
                            int LA113_0 = input.LA(1);

                            if ( ((LA113_0>=ID && LA113_0<=STRING)||LA113_0==57||LA113_0==63||LA113_0==94||(LA113_0>=100 && LA113_0<=102)||(LA113_0>=105 && LA113_0<=106)) ) {
                                alt113=1;
                            }
                            switch (alt113) {
                                case 1 :
                                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:310:10: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_statement2342);
                                    expressions264=expressions();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expressions264.getTree());

                                    }
                                    break;

                            }

                            char_literal265=(Token)match(input,64,FOLLOW_64_in_statement2345); 
                            char_literal265_tree = (Object)adaptor.create(char_literal265);
                            adaptor.addChild(root_0, char_literal265_tree);

                            char_literal266=(Token)match(input,73,FOLLOW_73_in_statement2347); 
                            char_literal266_tree = (Object)adaptor.create(char_literal266);
                            adaptor.addChild(root_0, char_literal266_tree);

                             

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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:312:1: typeAttr : ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) ) ;
    public final RVCCalParser.typeAttr_return typeAttr() throws RecognitionException {
        RVCCalParser.typeAttr_return retval = new RVCCalParser.typeAttr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID267=null;
        Token char_literal268=null;
        Token char_literal270=null;
        RVCCalParser.typeDef_return typeDef269 = null;

        RVCCalParser.expression_return expression271 = null;


        Object ID267_tree=null;
        Object char_literal268_tree=null;
        Object char_literal270_tree=null;
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_71=new RewriteRuleTokenStream(adaptor,"token 71");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:319:9: ( ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:319:11: ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) )
            {
            ID267=(Token)match(input,ID,FOLLOW_ID_in_typeAttr2368);  
            stream_ID.add(ID267);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:319:14: ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) )
            int alt116=2;
            int LA116_0 = input.LA(1);

            if ( (LA116_0==56) ) {
                alt116=1;
            }
            else if ( (LA116_0==71) ) {
                alt116=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 116, 0, input);

                throw nvae;
            }
            switch (alt116) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:319:15: ':' typeDef
                    {
                    char_literal268=(Token)match(input,56,FOLLOW_56_in_typeAttr2371);  
                    stream_56.add(char_literal268);

                    pushFollow(FOLLOW_typeDef_in_typeAttr2373);
                    typeDef269=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef269.getTree());


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
                    // 319:27: -> ^( TYPE ID typeDef )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:319:30: ^( TYPE ID typeDef )
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
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:319:51: '=' expression
                    {
                    char_literal270=(Token)match(input,71,FOLLOW_71_in_typeAttr2387);  
                    stream_71.add(char_literal270);

                    pushFollow(FOLLOW_expression_in_typeAttr2389);
                    expression271=expression();

                    state._fsp--;

                    stream_expression.add(expression271.getTree());


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
                    // 319:66: -> ^( EXPR ID expression )
                    {
                        // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:319:69: ^( EXPR ID expression )
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:321:1: typeAttrs : typeAttr ( ',' typeAttr )* -> ( typeAttr )+ ;
    public final RVCCalParser.typeAttrs_return typeAttrs() throws RecognitionException {
        RVCCalParser.typeAttrs_return retval = new RVCCalParser.typeAttrs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal273=null;
        RVCCalParser.typeAttr_return typeAttr272 = null;

        RVCCalParser.typeAttr_return typeAttr274 = null;


        Object char_literal273_tree=null;
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleSubtreeStream stream_typeAttr=new RewriteRuleSubtreeStream(adaptor,"rule typeAttr");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:321:10: ( typeAttr ( ',' typeAttr )* -> ( typeAttr )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:321:12: typeAttr ( ',' typeAttr )*
            {
            pushFollow(FOLLOW_typeAttr_in_typeAttrs2408);
            typeAttr272=typeAttr();

            state._fsp--;

            stream_typeAttr.add(typeAttr272.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:321:21: ( ',' typeAttr )*
            loop117:
            do {
                int alt117=2;
                int LA117_0 = input.LA(1);

                if ( (LA117_0==59) ) {
                    alt117=1;
                }


                switch (alt117) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:321:22: ',' typeAttr
            	    {
            	    char_literal273=(Token)match(input,59,FOLLOW_59_in_typeAttrs2411);  
            	    stream_59.add(char_literal273);

            	    pushFollow(FOLLOW_typeAttr_in_typeAttrs2413);
            	    typeAttr274=typeAttr();

            	    state._fsp--;

            	    stream_typeAttr.add(typeAttr274.getTree());

            	    }
            	    break;

            	default :
            	    break loop117;
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
            // 321:37: -> ( typeAttr )+
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:324:1: typeDef : ID ( '[' typePars ']' | '(' attrs= typeAttrs ')' )? -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) ;
    public final RVCCalParser.typeDef_return typeDef() throws RecognitionException {
        RVCCalParser.typeDef_return retval = new RVCCalParser.typeDef_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID275=null;
        Token char_literal276=null;
        Token char_literal278=null;
        Token char_literal279=null;
        Token char_literal280=null;
        RVCCalParser.typeAttrs_return attrs = null;

        RVCCalParser.typePars_return typePars277 = null;


        Object ID275_tree=null;
        Object char_literal276_tree=null;
        Object char_literal278_tree=null;
        Object char_literal279_tree=null;
        Object char_literal280_tree=null;
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_63=new RewriteRuleTokenStream(adaptor,"token 63");
        RewriteRuleSubtreeStream stream_typePars=new RewriteRuleSubtreeStream(adaptor,"rule typePars");
        RewriteRuleSubtreeStream stream_typeAttrs=new RewriteRuleSubtreeStream(adaptor,"rule typeAttrs");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:324:8: ( ID ( '[' typePars ']' | '(' attrs= typeAttrs ')' )? -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:324:10: ID ( '[' typePars ']' | '(' attrs= typeAttrs ')' )?
            {
            ID275=(Token)match(input,ID,FOLLOW_ID_in_typeDef2430);  
            stream_ID.add(ID275);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:325:3: ( '[' typePars ']' | '(' attrs= typeAttrs ')' )?
            int alt118=3;
            int LA118_0 = input.LA(1);

            if ( (LA118_0==57) ) {
                alt118=1;
            }
            else if ( (LA118_0==63) ) {
                alt118=2;
            }
            switch (alt118) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:325:4: '[' typePars ']'
                    {
                    char_literal276=(Token)match(input,57,FOLLOW_57_in_typeDef2435);  
                    stream_57.add(char_literal276);

                    pushFollow(FOLLOW_typePars_in_typeDef2437);
                    typePars277=typePars();

                    state._fsp--;

                    stream_typePars.add(typePars277.getTree());
                    char_literal278=(Token)match(input,58,FOLLOW_58_in_typeDef2439);  
                    stream_58.add(char_literal278);


                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:326:5: '(' attrs= typeAttrs ')'
                    {
                    char_literal279=(Token)match(input,63,FOLLOW_63_in_typeDef2445);  
                    stream_63.add(char_literal279);

                    pushFollow(FOLLOW_typeAttrs_in_typeDef2449);
                    attrs=typeAttrs();

                    state._fsp--;

                    stream_typeAttrs.add(attrs.getTree());
                    char_literal280=(Token)match(input,64,FOLLOW_64_in_typeDef2451);  
                    stream_64.add(char_literal280);


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
            // 326:31: -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
            {
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:326:34: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:326:44: ^( TYPE_ATTRS ( $attrs)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_2);

                // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:326:57: ( $attrs)?
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

    public static class typePar_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typePar"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:328:1: typePar : ID ( '<' typeDef )? ;
    public final RVCCalParser.typePar_return typePar() throws RecognitionException {
        RVCCalParser.typePar_return retval = new RVCCalParser.typePar_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID281=null;
        Token char_literal282=null;
        RVCCalParser.typeDef_return typeDef283 = null;


        Object ID281_tree=null;
        Object char_literal282_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:331:8: ( ID ( '<' typeDef )? )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:331:10: ID ( '<' typeDef )?
            {
            root_0 = (Object)adaptor.nil();

            ID281=(Token)match(input,ID,FOLLOW_ID_in_typePar2481); 
            ID281_tree = (Object)adaptor.create(ID281);
            adaptor.addChild(root_0, ID281_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:331:13: ( '<' typeDef )?
            int alt119=2;
            int LA119_0 = input.LA(1);

            if ( (LA119_0==87) ) {
                alt119=1;
            }
            switch (alt119) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:331:14: '<' typeDef
                    {
                    char_literal282=(Token)match(input,87,FOLLOW_87_in_typePar2484); 
                    char_literal282_tree = (Object)adaptor.create(char_literal282);
                    adaptor.addChild(root_0, char_literal282_tree);

                    pushFollow(FOLLOW_typeDef_in_typePar2486);
                    typeDef283=typeDef();

                    state._fsp--;

                    adaptor.addChild(root_0, typeDef283.getTree());

                    }
                    break;

            }

             System.out.println("RVC-CAL does not support type parameters."); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "typePar"

    public static class typePars_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typePars"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:333:1: typePars : typePar ( ',' typePar )* -> ( typePar )+ ;
    public final RVCCalParser.typePars_return typePars() throws RecognitionException {
        RVCCalParser.typePars_return retval = new RVCCalParser.typePars_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal285=null;
        RVCCalParser.typePar_return typePar284 = null;

        RVCCalParser.typePar_return typePar286 = null;


        Object char_literal285_tree=null;
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleSubtreeStream stream_typePar=new RewriteRuleSubtreeStream(adaptor,"rule typePar");
        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:333:9: ( typePar ( ',' typePar )* -> ( typePar )+ )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:333:11: typePar ( ',' typePar )*
            {
            pushFollow(FOLLOW_typePar_in_typePars2497);
            typePar284=typePar();

            state._fsp--;

            stream_typePar.add(typePar284.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:333:19: ( ',' typePar )*
            loop120:
            do {
                int alt120=2;
                int LA120_0 = input.LA(1);

                if ( (LA120_0==59) ) {
                    alt120=1;
                }


                switch (alt120) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:333:20: ',' typePar
            	    {
            	    char_literal285=(Token)match(input,59,FOLLOW_59_in_typePars2500);  
            	    stream_59.add(char_literal285);

            	    pushFollow(FOLLOW_typePar_in_typePars2502);
            	    typePar286=typePar();

            	    state._fsp--;

            	    stream_typePar.add(typePar286.getTree());

            	    }
            	    break;

            	default :
            	    break loop120;
                }
            } while (true);



            // AST REWRITE
            // elements: typePar
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 333:34: -> ( typePar )+
            {
                if ( !(stream_typePar.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_typePar.hasNext() ) {
                    adaptor.addChild(root_0, stream_typePar.nextTree());

                }
                stream_typePar.reset();

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
    // $ANTLR end "typePars"

    public static class varDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "varDecl"
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:335:1: varDecl : typeDef ID ( '=' expression | ':=' expression )? ;
    public final RVCCalParser.varDecl_return varDecl() throws RecognitionException {
        RVCCalParser.varDecl_return retval = new RVCCalParser.varDecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID288=null;
        Token char_literal289=null;
        Token string_literal291=null;
        RVCCalParser.typeDef_return typeDef287 = null;

        RVCCalParser.expression_return expression290 = null;

        RVCCalParser.expression_return expression292 = null;


        Object ID288_tree=null;
        Object char_literal289_tree=null;
        Object string_literal291_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:339:8: ( typeDef ID ( '=' expression | ':=' expression )? )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:339:10: typeDef ID ( '=' expression | ':=' expression )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_typeDef_in_varDecl2523);
            typeDef287=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef287.getTree());
            ID288=(Token)match(input,ID,FOLLOW_ID_in_varDecl2525); 
            ID288_tree = (Object)adaptor.create(ID288);
            adaptor.addChild(root_0, ID288_tree);

            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:339:21: ( '=' expression | ':=' expression )?
            int alt121=3;
            int LA121_0 = input.LA(1);

            if ( (LA121_0==71) ) {
                alt121=1;
            }
            else if ( (LA121_0==72) ) {
                alt121=2;
            }
            switch (alt121) {
                case 1 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:339:22: '=' expression
                    {
                    char_literal289=(Token)match(input,71,FOLLOW_71_in_varDecl2528); 
                    char_literal289_tree = (Object)adaptor.create(char_literal289);
                    adaptor.addChild(root_0, char_literal289_tree);

                    pushFollow(FOLLOW_expression_in_varDecl2530);
                    expression290=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression290.getTree());

                    }
                    break;
                case 2 :
                    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:339:39: ':=' expression
                    {
                    string_literal291=(Token)match(input,72,FOLLOW_72_in_varDecl2534); 
                    string_literal291_tree = (Object)adaptor.create(string_literal291);
                    adaptor.addChild(root_0, string_literal291_tree);

                    pushFollow(FOLLOW_expression_in_varDecl2536);
                    expression292=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression292.getTree());

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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:341:1: varDeclNoExpr : typeDef ID ;
    public final RVCCalParser.varDeclNoExpr_return varDeclNoExpr() throws RecognitionException {
        RVCCalParser.varDeclNoExpr_return retval = new RVCCalParser.varDeclNoExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID294=null;
        RVCCalParser.typeDef_return typeDef293 = null;


        Object ID294_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:341:14: ( typeDef ID )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:341:16: typeDef ID
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_typeDef_in_varDeclNoExpr2547);
            typeDef293=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef293.getTree());
            ID294=(Token)match(input,ID,FOLLOW_ID_in_varDeclNoExpr2549); 
            ID294_tree = (Object)adaptor.create(ID294);
            adaptor.addChild(root_0, ID294_tree);

             

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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
    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:343:1: varDecls : varDecl ( ',' varDecl )* ;
    public final RVCCalParser.varDecls_return varDecls() throws RecognitionException {
        RVCCalParser.varDecls_return retval = new RVCCalParser.varDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal296=null;
        RVCCalParser.varDecl_return varDecl295 = null;

        RVCCalParser.varDecl_return varDecl297 = null;


        Object char_literal296_tree=null;

        try {
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:343:9: ( varDecl ( ',' varDecl )* )
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:343:11: varDecl ( ',' varDecl )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_varDecl_in_varDecls2558);
            varDecl295=varDecl();

            state._fsp--;

            adaptor.addChild(root_0, varDecl295.getTree());
            // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:343:19: ( ',' varDecl )*
            loop122:
            do {
                int alt122=2;
                int LA122_0 = input.LA(1);

                if ( (LA122_0==59) ) {
                    alt122=1;
                }


                switch (alt122) {
            	case 1 :
            	    // D:\\Prog\\repositories\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:343:20: ',' varDecl
            	    {
            	    char_literal296=(Token)match(input,59,FOLLOW_59_in_varDecls2561); 
            	    char_literal296_tree = (Object)adaptor.create(char_literal296);
            	    adaptor.addChild(root_0, char_literal296_tree);

            	    pushFollow(FOLLOW_varDecl_in_varDecls2563);
            	    varDecl297=varDecl();

            	    state._fsp--;

            	    adaptor.addChild(root_0, varDecl297.getTree());

            	    }
            	    break;

            	default :
            	    break loop122;
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
    // $ANTLR end "varDecls"

    // Delegated rules


 

    public static final BitSet FOLLOW_50_in_actionChannelSelector268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_51_in_actionChannelSelector274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_actionChannelSelector280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_actionChannelSelector286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_actionDelay295 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_actionDelay297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_actionGuards307 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expressions_in_actionGuards309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_actionInput320 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_actionInput322 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_actionInput326 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_idents_in_actionInput328 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_actionInput330 = new BitSet(new long[]{0x103C000000000002L});
    public static final BitSet FOLLOW_actionRepeat_in_actionInput332 = new BitSet(new long[]{0x003C000000000002L});
    public static final BitSet FOLLOW_actionChannelSelector_in_actionInput335 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionInput_in_actionInputs346 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_59_in_actionInputs349 = new BitSet(new long[]{0x0200008000000000L});
    public static final BitSet FOLLOW_actionInput_in_actionInputs351 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_ID_in_actionOutput364 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_actionOutput366 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_actionOutput370 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expressions_in_actionOutput372 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_actionOutput374 = new BitSet(new long[]{0x103C000000000002L});
    public static final BitSet FOLLOW_actionRepeat_in_actionOutput376 = new BitSet(new long[]{0x003C000000000002L});
    public static final BitSet FOLLOW_actionChannelSelector_in_actionOutput379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionOutput_in_actionOutputs390 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_59_in_actionOutputs393 = new BitSet(new long[]{0x0200008000000000L});
    public static final BitSet FOLLOW_actionOutput_in_actionOutputs395 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_60_in_actionRepeat406 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_actionRepeat408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_actionStatements417 = new BitSet(new long[]{0x0000008000000002L,0x0016084000002000L});
    public static final BitSet FOLLOW_statement_in_actionStatements419 = new BitSet(new long[]{0x0000008000000002L,0x0016084000002000L});
    public static final BitSet FOLLOW_actorImport_in_actor434 = new BitSet(new long[]{0x4000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_62_in_actor437 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_ID_in_actor441 = new BitSet(new long[]{0x8200000000000000L});
    public static final BitSet FOLLOW_57_in_actor444 = new BitSet(new long[]{0x0400008000000000L});
    public static final BitSet FOLLOW_typePars_in_actor446 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_actor449 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_actor453 = new BitSet(new long[]{0x0000008000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_actorParameters_in_actor455 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_actor458 = new BitSet(new long[]{0x0000008000000000L,0x0000000000008002L});
    public static final BitSet FOLLOW_actorPortDecls_in_actor463 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_actor466 = new BitSet(new long[]{0x0100008000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_actorPortDecls_in_actor470 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_actor473 = new BitSet(new long[]{0x0000008000000000L,0x0000600000001454L});
    public static final BitSet FOLLOW_actorDeclarations_in_actor476 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_actor478 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_actor480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration545 = new BitSet(new long[]{0x8300008000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_actorDeclaration555 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration557 = new BitSet(new long[]{0x0100000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_56_in_actorDeclaration561 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000050L});
    public static final BitSet FOLLOW_68_in_actorDeclaration570 = new BitSet(new long[]{0x0200008000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_actionInputs_in_actorDeclaration572 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_actorDeclaration575 = new BitSet(new long[]{0x22C0008000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration577 = new BitSet(new long[]{0x20C0000000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration580 = new BitSet(new long[]{0x2040000000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_actionDelay_in_actorDeclaration583 = new BitSet(new long[]{0x2000000000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_69_in_actorDeclaration587 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration589 = new BitSet(new long[]{0x2000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration593 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_actorDeclaration596 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_actorDeclaration615 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_actorDeclaration617 = new BitSet(new long[]{0x22C0008000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration619 = new BitSet(new long[]{0x20C0000000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration622 = new BitSet(new long[]{0x2040000000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_actionDelay_in_actorDeclaration625 = new BitSet(new long[]{0x2000000000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_69_in_actorDeclaration629 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration631 = new BitSet(new long[]{0x2000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration635 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_actorDeclaration638 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_actorDeclaration666 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_typePars_in_actorDeclaration668 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_actorDeclaration670 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_63_in_actorDeclaration674 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_typeAttrs_in_actorDeclaration678 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_actorDeclaration680 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration690 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000380L});
    public static final BitSet FOLLOW_71_in_actorDeclaration699 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration701 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_72_in_actorDeclaration737 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration739 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_actorDeclaration801 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_actorDeclaration811 = new BitSet(new long[]{0x0200008000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_actionInputs_in_actorDeclaration813 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_actorDeclaration816 = new BitSet(new long[]{0x22C0008000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration818 = new BitSet(new long[]{0x20C0000000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration821 = new BitSet(new long[]{0x2040000000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_actionDelay_in_actorDeclaration824 = new BitSet(new long[]{0x2000000000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_69_in_actorDeclaration828 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration830 = new BitSet(new long[]{0x2000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration834 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_actorDeclaration837 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_actorDeclaration847 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_actorDeclaration849 = new BitSet(new long[]{0x22C0008000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration851 = new BitSet(new long[]{0x20C0000000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration854 = new BitSet(new long[]{0x2040000000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_actionDelay_in_actorDeclaration857 = new BitSet(new long[]{0x2000000000000000L,0x0000000000000024L});
    public static final BitSet FOLLOW_69_in_actorDeclaration861 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration863 = new BitSet(new long[]{0x2000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration867 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_actorDeclaration870 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_priorityOrder_in_actorDeclaration879 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_actorDeclaration886 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration888 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_actorDeclaration890 = new BitSet(new long[]{0x0000008000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration893 = new BitSet(new long[]{0x0800000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_59_in_actorDeclaration896 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration898 = new BitSet(new long[]{0x0800000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_actorDeclaration904 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_actorDeclaration906 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_typeDef_in_actorDeclaration908 = new BitSet(new long[]{0x0100000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_actorDeclaration915 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration917 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_actorDeclaration921 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration929 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_actorDeclaration935 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_actorDeclaration945 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration947 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_actorDeclaration949 = new BitSet(new long[]{0x0000008000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration952 = new BitSet(new long[]{0x0800000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_59_in_actorDeclaration955 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration957 = new BitSet(new long[]{0x0800000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_actorDeclaration963 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002020L});
    public static final BitSet FOLLOW_69_in_actorDeclaration970 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration972 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_actorDeclaration980 = new BitSet(new long[]{0x0000008000000000L,0x0016084000002004L});
    public static final BitSet FOLLOW_statement_in_actorDeclaration982 = new BitSet(new long[]{0x0000008000000000L,0x0016084000002004L});
    public static final BitSet FOLLOW_66_in_actorDeclaration985 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations997 = new BitSet(new long[]{0x0000008000000002L,0x0000600000001450L});
    public static final BitSet FOLLOW_schedule_in_actorDeclarations1001 = new BitSet(new long[]{0x0000008000000002L,0x0000200000001450L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations1003 = new BitSet(new long[]{0x0000008000000002L,0x0000200000001450L});
    public static final BitSet FOLLOW_78_in_actorImport1026 = new BitSet(new long[]{0x0004008000000000L});
    public static final BitSet FOLLOW_50_in_actorImport1031 = new BitSet(new long[]{0x0004008000000000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport1033 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_actorImport1035 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport1041 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_actorImport1043 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_actorParameter1058 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_ID_in_actorParameter1060 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_actorParameter1063 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_actorParameter1065 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters1087 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_59_in_actorParameters1090 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters1092 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_79_in_actorPortDecl1112 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_typeDef_in_actorPortDecl1114 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_ID_in_actorPortDecl1116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_actorPortDecl1123 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_ID_in_actorPortDecl1125 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorPortDecl_in_actorPortDecls1142 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_59_in_actorPortDecls1145 = new BitSet(new long[]{0x0000008000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_actorPortDecl_in_actorPortDecls1147 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_and_expr_in_expression1172 = new BitSet(new long[]{0x0000000000000002L,0x0000000000030000L});
    public static final BitSet FOLLOW_80_in_expression1177 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_81_in_expression1181 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_and_expr_in_expression1186 = new BitSet(new long[]{0x0000000000000002L,0x0000000000030000L});
    public static final BitSet FOLLOW_bitor_expr_in_and_expr1219 = new BitSet(new long[]{0x0000000000000002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_82_in_and_expr1224 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_83_in_and_expr1228 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_bitor_expr_in_and_expr1233 = new BitSet(new long[]{0x0000000000000002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_bitand_expr_in_bitor_expr1266 = new BitSet(new long[]{0x0000000000000002L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_bitor_expr1270 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_bitand_expr_in_bitor_expr1274 = new BitSet(new long[]{0x0000000000000002L,0x0000000000100000L});
    public static final BitSet FOLLOW_eq_expr_in_bitand_expr1307 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_bitand_expr1311 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_eq_expr_in_bitand_expr1315 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_rel_expr_in_eq_expr1348 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400080L});
    public static final BitSet FOLLOW_71_in_eq_expr1355 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_86_in_eq_expr1361 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_rel_expr_in_eq_expr1366 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400080L});
    public static final BitSet FOLLOW_shift_expr_in_rel_expr1404 = new BitSet(new long[]{0x0000000000000002L,0x0000000007800000L});
    public static final BitSet FOLLOW_87_in_rel_expr1411 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_88_in_rel_expr1417 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_89_in_rel_expr1423 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_90_in_rel_expr1429 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_shift_expr_in_rel_expr1434 = new BitSet(new long[]{0x0000000000000002L,0x0000000007800000L});
    public static final BitSet FOLLOW_add_expr_in_shift_expr1471 = new BitSet(new long[]{0x0000000000000002L,0x0000000018000000L});
    public static final BitSet FOLLOW_91_in_shift_expr1478 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_92_in_shift_expr1484 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_add_expr_in_shift_expr1489 = new BitSet(new long[]{0x0000000000000002L,0x0000000018000000L});
    public static final BitSet FOLLOW_mul_expr_in_add_expr1527 = new BitSet(new long[]{0x0000000000000002L,0x0000000060000000L});
    public static final BitSet FOLLOW_93_in_add_expr1534 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_94_in_add_expr1540 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_mul_expr_in_add_expr1545 = new BitSet(new long[]{0x0000000000000002L,0x0000000060000000L});
    public static final BitSet FOLLOW_exp_expr_in_mul_expr1583 = new BitSet(new long[]{0x0000000000000002L,0x0000000780000000L});
    public static final BitSet FOLLOW_95_in_mul_expr1590 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_96_in_mul_expr1596 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_97_in_mul_expr1602 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_98_in_mul_expr1608 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_exp_expr_in_mul_expr1613 = new BitSet(new long[]{0x0000000000000002L,0x0000000780000000L});
    public static final BitSet FOLLOW_un_expr_in_exp_expr1651 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L});
    public static final BitSet FOLLOW_99_in_exp_expr1655 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_un_expr_in_exp_expr1659 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L});
    public static final BitSet FOLLOW_postfix_expression_in_un_expr1690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_un_expr1699 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_un_expr_in_un_expr1701 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_100_in_un_expr1716 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_un_expr_in_un_expr1718 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_un_expr1733 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_un_expr_in_un_expr1735 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_postfix_expression1755 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1759 = new BitSet(new long[]{0x0500000000000000L});
    public static final BitSet FOLLOW_56_in_postfix_expression1762 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_expressionGenerators_in_postfix_expression1766 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_postfix_expression1770 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_postfix_expression1787 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1791 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_103_in_postfix_expression1793 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1797 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_104_in_postfix_expression1799 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1803 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_postfix_expression1805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_postfix_expression1824 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_postfix_expression1832 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1834 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_postfix_expression1836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_postfix_expression1846 = new BitSet(new long[]{0x8200000000000002L});
    public static final BitSet FOLLOW_63_in_postfix_expression1854 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000001L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1856 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_postfix_expression1859 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_postfix_expression1879 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1881 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_postfix_expression1883 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_105_in_constant1920 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_106_in_constant1932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_constant1944 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_constant1956 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_constant1968 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_107_in_expressionGenerator1984 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_typeDef_in_expressionGenerator1986 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_ID_in_expressionGenerator1988 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_108_in_expressionGenerator1990 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_expressionGenerator1992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators2002 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_59_in_expressionGenerators2005 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators2007 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_expression_in_expressions2021 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_59_in_expressions2024 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_expressions2026 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_ID_in_idents2045 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_59_in_idents2048 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_ID_in_idents2050 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality2066 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_88_in_priorityInequality2069 = new BitSet(new long[]{0x0004008000000000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality2071 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000200L});
    public static final BitSet FOLLOW_73_in_priorityInequality2075 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_109_in_priorityOrder2085 = new BitSet(new long[]{0x0004008000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_priorityInequality_in_priorityOrder2088 = new BitSet(new long[]{0x0004008000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_priorityOrder2092 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent2106 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_qualifiedIdent2109 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent2111 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000008L});
    public static final BitSet FOLLOW_110_in_schedule2129 = new BitSet(new long[]{0x0000000000000000L,0x0001800000000000L});
    public static final BitSet FOLLOW_111_in_schedule2136 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_ID_in_schedule2138 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_schedule2140 = new BitSet(new long[]{0x0000008000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_stateTransition_in_schedule2142 = new BitSet(new long[]{0x0000008000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_schedule2145 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_112_in_schedule2155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stateTransition2167 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_stateTransition2169 = new BitSet(new long[]{0x0004008000000000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_stateTransition2171 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_stateTransition2173 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_stateTransition2175 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_ID_in_stateTransition2177 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_stateTransition2179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_statement2195 = new BitSet(new long[]{0x0000008000000000L,0x0016084000002024L});
    public static final BitSet FOLLOW_69_in_statement2198 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_varDecls_in_statement2200 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_statement2202 = new BitSet(new long[]{0x0000008000000000L,0x0016084000002004L});
    public static final BitSet FOLLOW_statement_in_statement2206 = new BitSet(new long[]{0x0000008000000000L,0x0016084000002004L});
    public static final BitSet FOLLOW_66_in_statement2209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_113_in_statement2215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_107_in_statement2221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_statement2227 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_statement2229 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_108_in_statement2231 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_statement2234 = new BitSet(new long[]{0x2000000000000000L,0x0008000000000020L});
    public static final BitSet FOLLOW_115_in_statement2237 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_statement2239 = new BitSet(new long[]{0x2000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_statement2245 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_varDecls_in_statement2247 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_statement2251 = new BitSet(new long[]{0x0000008000000000L,0x0016084000002004L});
    public static final BitSet FOLLOW_statement_in_statement2253 = new BitSet(new long[]{0x0000008000000000L,0x0016084000002004L});
    public static final BitSet FOLLOW_66_in_statement2256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_statement2262 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_statement2264 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_103_in_statement2266 = new BitSet(new long[]{0x0000008000000000L,0x0016094000002004L});
    public static final BitSet FOLLOW_statement_in_statement2268 = new BitSet(new long[]{0x0000008000000000L,0x0016094000002004L});
    public static final BitSet FOLLOW_104_in_statement2272 = new BitSet(new long[]{0x0000008000000000L,0x0016084000002004L});
    public static final BitSet FOLLOW_statement_in_statement2274 = new BitSet(new long[]{0x0000008000000000L,0x0016084000002004L});
    public static final BitSet FOLLOW_66_in_statement2279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_116_in_statement2285 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_statement2287 = new BitSet(new long[]{0x2000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_statement2290 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_varDecls_in_statement2292 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_statement2296 = new BitSet(new long[]{0x0000008000000000L,0x0016084000002004L});
    public static final BitSet FOLLOW_statement_in_statement2298 = new BitSet(new long[]{0x0000008000000000L,0x0016084000002004L});
    public static final BitSet FOLLOW_66_in_statement2301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_statement2308 = new BitSet(new long[]{0x8200000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_57_in_statement2318 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expressions_in_statement2320 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_statement2322 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_statement2326 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_statement2328 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_statement2330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_statement2340 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000001L});
    public static final BitSet FOLLOW_expressions_in_statement2342 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_statement2345 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_statement2347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_typeAttr2368 = new BitSet(new long[]{0x0100000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_56_in_typeAttr2371 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_typeDef_in_typeAttr2373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_typeAttr2387 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_typeAttr2389 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs2408 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_59_in_typeAttrs2411 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs2413 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_ID_in_typeDef2430 = new BitSet(new long[]{0x8200000000000002L});
    public static final BitSet FOLLOW_57_in_typeDef2435 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_typePars_in_typeDef2437 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_typeDef2439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_typeDef2445 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_typeAttrs_in_typeDef2449 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_typeDef2451 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_typePar2481 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_typePar2484 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_typeDef_in_typePar2486 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typePar_in_typePars2497 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_59_in_typePars2500 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_typePar_in_typePars2502 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_typeDef_in_varDecl2523 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_ID_in_varDecl2525 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000180L});
    public static final BitSet FOLLOW_71_in_varDecl2528 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_varDecl2530 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_varDecl2534 = new BitSet(new long[]{0x8200078000000000L,0x0000067040000000L});
    public static final BitSet FOLLOW_expression_in_varDecl2536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_varDeclNoExpr2547 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_ID_in_varDeclNoExpr2549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varDecl_in_varDecls2558 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_59_in_varDecls2561 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_varDecl_in_varDecls2563 = new BitSet(new long[]{0x0800000000000002L});

}