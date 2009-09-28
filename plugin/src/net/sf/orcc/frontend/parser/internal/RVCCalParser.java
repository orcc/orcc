// $ANTLR 3.1.2 D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g 2009-09-25 16:43:49

package net.sf.orcc.frontend.parser.internal;

// @SuppressWarnings("unused")


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

@SuppressWarnings("unused")
public class RVCCalParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACTOR", "INPUTS", "OUTPUTS", "PORT", "EXPR", "ACTOR_DECLS", "FUNCTION", "PROCEDURE", "STATE_VAR", "PARAMETER", "PARAMETERS", "TYPE", "TYPE_ATTRS", "ASSIGNABLE", "NON_ASSIGNABLE", "ID", "INTEGER", "STRING", "FLOAT", "LINE_COMMENT", "MULTI_LINE_COMMENT", "WHITESPACE", "'all'", "'any'", "'at'", "'at*'", "'delay'", "'guard'", "':'", "'['", "']'", "','", "'repeat'", "'do'", "'actor'", "'('", "')'", "'==>'", "'end'", "'.'", "'action'", "'var'", "'initialize'", "'='", "':='", "';'", "'function'", "'-->'", "'procedure'", "'begin'", "'import'", "'multi'", "'or'", "'||'", "'and'", "'&&'", "'|'", "'&'", "'!='", "'<'", "'>'", "'<='", "'>='", "'<<'", "'>>'", "'+'", "'-'", "'div'", "'mod'", "'*'", "'/'", "'^'", "'not'", "'#'", "'if'", "'then'", "'else'", "'true'", "'false'", "'for'", "'in'", "'priority'", "'schedule'", "'fsm'", "'regexp'", "'choose'", "'foreach'", "'..'", "'while'", "'type'", "'size'"
    };
    public static final int FUNCTION=10;
    public static final int T__29=29;
    public static final int OUTPUTS=6;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int INPUTS=5;
    public static final int EOF=-1;
    public static final int TYPE=15;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int TYPE_ATTRS=16;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__90=90;
    public static final int PARAMETER=13;
    public static final int STATE_VAR=12;
    public static final int ASSIGNABLE=17;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int LINE_COMMENT=23;
    public static final int WHITESPACE=25;
    public static final int NON_ASSIGNABLE=18;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__70=70;
    public static final int ACTOR_DECLS=9;
    public static final int ACTOR=4;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int T__74=74;
    public static final int T__73=73;
    public static final int T__79=79;
    public static final int T__78=78;
    public static final int T__77=77;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int PARAMETERS=14;
    public static final int FLOAT=22;
    public static final int T__61=61;
    public static final int ID=19;
    public static final int T__60=60;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int EXPR=8;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__59=59;
    public static final int T__50=50;
    public static final int T__42=42;
    public static final int INTEGER=20;
    public static final int T__43=43;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int PORT=7;
    public static final int MULTI_LINE_COMMENT=24;
    public static final int PROCEDURE=11;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int STRING=21;

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


    public static class actionChannelSelector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionChannelSelector"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:78:1: actionChannelSelector : ( 'all' | 'any' | 'at' | 'at*' );
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:78:22: ( 'all' | 'any' | 'at' | 'at*' )
            int alt1=4;
            switch ( input.LA(1) ) {
            case 26:
                {
                alt1=1;
                }
                break;
            case 27:
                {
                alt1=2;
                }
                break;
            case 28:
                {
                alt1=3;
                }
                break;
            case 29:
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:79:3: 'all'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal1=(Token)match(input,26,FOLLOW_26_in_actionChannelSelector164); 
                    string_literal1_tree = (Object)adaptor.create(string_literal1);
                    adaptor.addChild(root_0, string_literal1_tree);

                     System.out.println("TODO: throw exception channel selectors"); 

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:80:3: 'any'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal2=(Token)match(input,27,FOLLOW_27_in_actionChannelSelector170); 
                    string_literal2_tree = (Object)adaptor.create(string_literal2);
                    adaptor.addChild(root_0, string_literal2_tree);

                     System.out.println("TODO: throw exception channel selectors"); 

                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:81:3: 'at'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal3=(Token)match(input,28,FOLLOW_28_in_actionChannelSelector176); 
                    string_literal3_tree = (Object)adaptor.create(string_literal3);
                    adaptor.addChild(root_0, string_literal3_tree);

                     System.out.println("TODO: throw exception channel selectors"); 

                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:82:3: 'at*'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal4=(Token)match(input,29,FOLLOW_29_in_actionChannelSelector182); 
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:84:1: actionDelay : 'delay' expression ;
    public final RVCCalParser.actionDelay_return actionDelay() throws RecognitionException {
        RVCCalParser.actionDelay_return retval = new RVCCalParser.actionDelay_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal5=null;
        RVCCalParser.expression_return expression6 = null;


        Object string_literal5_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:84:12: ( 'delay' expression )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:84:14: 'delay' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal5=(Token)match(input,30,FOLLOW_30_in_actionDelay191); 
            string_literal5_tree = (Object)adaptor.create(string_literal5);
            adaptor.addChild(root_0, string_literal5_tree);

            pushFollow(FOLLOW_expression_in_actionDelay193);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:87:1: actionGuards : 'guard' expressions ;
    public final RVCCalParser.actionGuards_return actionGuards() throws RecognitionException {
        RVCCalParser.actionGuards_return retval = new RVCCalParser.actionGuards_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal7=null;
        RVCCalParser.expressions_return expressions8 = null;


        Object string_literal7_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:87:13: ( 'guard' expressions )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:87:15: 'guard' expressions
            {
            root_0 = (Object)adaptor.nil();

            string_literal7=(Token)match(input,31,FOLLOW_31_in_actionGuards203); 
            string_literal7_tree = (Object)adaptor.create(string_literal7);
            adaptor.addChild(root_0, string_literal7_tree);

            pushFollow(FOLLOW_expressions_in_actionGuards205);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:89:1: actionInput : ( ID ':' )? '[' idents ']' ( actionRepeat )? ( actionChannelSelector )? ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:89:12: ( ( ID ':' )? '[' idents ']' ( actionRepeat )? ( actionChannelSelector )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:90:2: ( ID ':' )? '[' idents ']' ( actionRepeat )? ( actionChannelSelector )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:90:2: ( ID ':' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==ID) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:90:3: ID ':'
                    {
                    ID9=(Token)match(input,ID,FOLLOW_ID_in_actionInput216); 
                    ID9_tree = (Object)adaptor.create(ID9);
                    adaptor.addChild(root_0, ID9_tree);

                    char_literal10=(Token)match(input,32,FOLLOW_32_in_actionInput218); 
                    char_literal10_tree = (Object)adaptor.create(char_literal10);
                    adaptor.addChild(root_0, char_literal10_tree);


                    }
                    break;

            }

            char_literal11=(Token)match(input,33,FOLLOW_33_in_actionInput222); 
            char_literal11_tree = (Object)adaptor.create(char_literal11);
            adaptor.addChild(root_0, char_literal11_tree);

            pushFollow(FOLLOW_idents_in_actionInput224);
            idents12=idents();

            state._fsp--;

            adaptor.addChild(root_0, idents12.getTree());
            char_literal13=(Token)match(input,34,FOLLOW_34_in_actionInput226); 
            char_literal13_tree = (Object)adaptor.create(char_literal13);
            adaptor.addChild(root_0, char_literal13_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:90:27: ( actionRepeat )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==36) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:90:27: actionRepeat
                    {
                    pushFollow(FOLLOW_actionRepeat_in_actionInput228);
                    actionRepeat14=actionRepeat();

                    state._fsp--;

                    adaptor.addChild(root_0, actionRepeat14.getTree());

                    }
                    break;

            }

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:90:41: ( actionChannelSelector )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( ((LA4_0>=26 && LA4_0<=29)) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:90:41: actionChannelSelector
                    {
                    pushFollow(FOLLOW_actionChannelSelector_in_actionInput231);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:93:1: actionInputs : actionInput ( ',' actionInput )* ;
    public final RVCCalParser.actionInputs_return actionInputs() throws RecognitionException {
        RVCCalParser.actionInputs_return retval = new RVCCalParser.actionInputs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal17=null;
        RVCCalParser.actionInput_return actionInput16 = null;

        RVCCalParser.actionInput_return actionInput18 = null;


        Object char_literal17_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:93:13: ( actionInput ( ',' actionInput )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:93:15: actionInput ( ',' actionInput )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_actionInput_in_actionInputs242);
            actionInput16=actionInput();

            state._fsp--;

            adaptor.addChild(root_0, actionInput16.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:93:27: ( ',' actionInput )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==35) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:93:28: ',' actionInput
            	    {
            	    char_literal17=(Token)match(input,35,FOLLOW_35_in_actionInputs245); 
            	    char_literal17_tree = (Object)adaptor.create(char_literal17);
            	    adaptor.addChild(root_0, char_literal17_tree);

            	    pushFollow(FOLLOW_actionInput_in_actionInputs247);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:95:1: actionOutput : ( ID ':' )? '[' expressions ']' ( actionRepeat )? ( actionChannelSelector )? ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:95:13: ( ( ID ':' )? '[' expressions ']' ( actionRepeat )? ( actionChannelSelector )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:2: ( ID ':' )? '[' expressions ']' ( actionRepeat )? ( actionChannelSelector )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:2: ( ID ':' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==ID) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:3: ID ':'
                    {
                    ID19=(Token)match(input,ID,FOLLOW_ID_in_actionOutput260); 
                    ID19_tree = (Object)adaptor.create(ID19);
                    adaptor.addChild(root_0, ID19_tree);

                    char_literal20=(Token)match(input,32,FOLLOW_32_in_actionOutput262); 
                    char_literal20_tree = (Object)adaptor.create(char_literal20);
                    adaptor.addChild(root_0, char_literal20_tree);


                    }
                    break;

            }

            char_literal21=(Token)match(input,33,FOLLOW_33_in_actionOutput266); 
            char_literal21_tree = (Object)adaptor.create(char_literal21);
            adaptor.addChild(root_0, char_literal21_tree);

            pushFollow(FOLLOW_expressions_in_actionOutput268);
            expressions22=expressions();

            state._fsp--;

            adaptor.addChild(root_0, expressions22.getTree());
            char_literal23=(Token)match(input,34,FOLLOW_34_in_actionOutput270); 
            char_literal23_tree = (Object)adaptor.create(char_literal23);
            adaptor.addChild(root_0, char_literal23_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:32: ( actionRepeat )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==36) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:32: actionRepeat
                    {
                    pushFollow(FOLLOW_actionRepeat_in_actionOutput272);
                    actionRepeat24=actionRepeat();

                    state._fsp--;

                    adaptor.addChild(root_0, actionRepeat24.getTree());

                    }
                    break;

            }

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:46: ( actionChannelSelector )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0>=26 && LA8_0<=29)) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:46: actionChannelSelector
                    {
                    pushFollow(FOLLOW_actionChannelSelector_in_actionOutput275);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:99:1: actionOutputs : actionOutput ( ',' actionOutput )* ;
    public final RVCCalParser.actionOutputs_return actionOutputs() throws RecognitionException {
        RVCCalParser.actionOutputs_return retval = new RVCCalParser.actionOutputs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal27=null;
        RVCCalParser.actionOutput_return actionOutput26 = null;

        RVCCalParser.actionOutput_return actionOutput28 = null;


        Object char_literal27_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:99:14: ( actionOutput ( ',' actionOutput )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:99:16: actionOutput ( ',' actionOutput )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_actionOutput_in_actionOutputs286);
            actionOutput26=actionOutput();

            state._fsp--;

            adaptor.addChild(root_0, actionOutput26.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:99:29: ( ',' actionOutput )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==35) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:99:30: ',' actionOutput
            	    {
            	    char_literal27=(Token)match(input,35,FOLLOW_35_in_actionOutputs289); 
            	    char_literal27_tree = (Object)adaptor.create(char_literal27);
            	    adaptor.addChild(root_0, char_literal27_tree);

            	    pushFollow(FOLLOW_actionOutput_in_actionOutputs291);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:101:1: actionRepeat : 'repeat' expression ;
    public final RVCCalParser.actionRepeat_return actionRepeat() throws RecognitionException {
        RVCCalParser.actionRepeat_return retval = new RVCCalParser.actionRepeat_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal29=null;
        RVCCalParser.expression_return expression30 = null;


        Object string_literal29_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:101:13: ( 'repeat' expression )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:101:15: 'repeat' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal29=(Token)match(input,36,FOLLOW_36_in_actionRepeat302); 
            string_literal29_tree = (Object)adaptor.create(string_literal29);
            adaptor.addChild(root_0, string_literal29_tree);

            pushFollow(FOLLOW_expression_in_actionRepeat304);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:103:1: actionStatements : 'do' ( statement )* ;
    public final RVCCalParser.actionStatements_return actionStatements() throws RecognitionException {
        RVCCalParser.actionStatements_return retval = new RVCCalParser.actionStatements_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal31=null;
        RVCCalParser.statement_return statement32 = null;


        Object string_literal31_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:103:17: ( 'do' ( statement )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:103:19: 'do' ( statement )*
            {
            root_0 = (Object)adaptor.nil();

            string_literal31=(Token)match(input,37,FOLLOW_37_in_actionStatements313); 
            string_literal31_tree = (Object)adaptor.create(string_literal31);
            adaptor.addChild(root_0, string_literal31_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:103:24: ( statement )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==ID||LA10_0==53||LA10_0==78||LA10_0==83||(LA10_0>=89 && LA10_0<=90)||LA10_0==92) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:103:24: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_actionStatements315);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:105:1: actor : ( actorImport )* 'actor' id= ID ( '[' ( typePars )? ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' actorDeclarations 'end' EOF -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS actorDeclarations ) ;
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
        RewriteRuleTokenStream stream_42=new RewriteRuleTokenStream(adaptor,"token 42");
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_32=new RewriteRuleTokenStream(adaptor,"token 32");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleTokenStream stream_33=new RewriteRuleTokenStream(adaptor,"token 33");
        RewriteRuleTokenStream stream_34=new RewriteRuleTokenStream(adaptor,"token 34");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");
        RewriteRuleSubtreeStream stream_typePars=new RewriteRuleSubtreeStream(adaptor,"rule typePars");
        RewriteRuleSubtreeStream stream_actorImport=new RewriteRuleSubtreeStream(adaptor,"rule actorImport");
        RewriteRuleSubtreeStream stream_actorDeclarations=new RewriteRuleSubtreeStream(adaptor,"rule actorDeclarations");
        RewriteRuleSubtreeStream stream_actorPortDecls=new RewriteRuleSubtreeStream(adaptor,"rule actorPortDecls");
        RewriteRuleSubtreeStream stream_actorParameters=new RewriteRuleSubtreeStream(adaptor,"rule actorParameters");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:108:6: ( ( actorImport )* 'actor' id= ID ( '[' ( typePars )? ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' actorDeclarations 'end' EOF -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS actorDeclarations ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:108:8: ( actorImport )* 'actor' id= ID ( '[' ( typePars )? ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' actorDeclarations 'end' EOF
            {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:108:8: ( actorImport )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==54) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:108:8: actorImport
            	    {
            	    pushFollow(FOLLOW_actorImport_in_actor330);
            	    actorImport33=actorImport();

            	    state._fsp--;

            	    stream_actorImport.add(actorImport33.getTree());

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            string_literal34=(Token)match(input,38,FOLLOW_38_in_actor333);  
            stream_38.add(string_literal34);

            id=(Token)match(input,ID,FOLLOW_ID_in_actor337);  
            stream_ID.add(id);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:108:35: ( '[' ( typePars )? ']' )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==33) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:108:36: '[' ( typePars )? ']'
                    {
                    char_literal35=(Token)match(input,33,FOLLOW_33_in_actor340);  
                    stream_33.add(char_literal35);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:108:40: ( typePars )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==ID) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:108:40: typePars
                            {
                            pushFollow(FOLLOW_typePars_in_actor342);
                            typePars36=typePars();

                            state._fsp--;

                            stream_typePars.add(typePars36.getTree());

                            }
                            break;

                    }

                    char_literal37=(Token)match(input,34,FOLLOW_34_in_actor345);  
                    stream_34.add(char_literal37);


                    }
                    break;

            }

            char_literal38=(Token)match(input,39,FOLLOW_39_in_actor349);  
            stream_39.add(char_literal38);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:108:60: ( actorParameters )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==ID) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:108:60: actorParameters
                    {
                    pushFollow(FOLLOW_actorParameters_in_actor351);
                    actorParameters39=actorParameters();

                    state._fsp--;

                    stream_actorParameters.add(actorParameters39.getTree());

                    }
                    break;

            }

            char_literal40=(Token)match(input,40,FOLLOW_40_in_actor354);  
            stream_40.add(char_literal40);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:8: (inputs= actorPortDecls )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==ID||LA15_0==55) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:8: inputs= actorPortDecls
                    {
                    pushFollow(FOLLOW_actorPortDecls_in_actor359);
                    inputs=actorPortDecls();

                    state._fsp--;

                    stream_actorPortDecls.add(inputs.getTree());

                    }
                    break;

            }

            string_literal41=(Token)match(input,41,FOLLOW_41_in_actor362);  
            stream_41.add(string_literal41);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:38: (outputs= actorPortDecls )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==ID||LA16_0==55) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:38: outputs= actorPortDecls
                    {
                    pushFollow(FOLLOW_actorPortDecls_in_actor366);
                    outputs=actorPortDecls();

                    state._fsp--;

                    stream_actorPortDecls.add(outputs.getTree());

                    }
                    break;

            }

            char_literal42=(Token)match(input,32,FOLLOW_32_in_actor369);  
            stream_32.add(char_literal42);

            pushFollow(FOLLOW_actorDeclarations_in_actor372);
            actorDeclarations43=actorDeclarations();

            state._fsp--;

            stream_actorDeclarations.add(actorDeclarations43.getTree());
            string_literal44=(Token)match(input,42,FOLLOW_42_in_actor374);  
            stream_42.add(string_literal44);

            EOF45=(Token)match(input,EOF,FOLLOW_EOF_in_actor376);  
            stream_EOF.add(EOF45);



            // AST REWRITE
            // elements: actorDeclarations, inputs, 38, outputs, actorParameters, id
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
            // 111:2: -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ^( ACTOR_DECLS actorDeclarations )
            {
                adaptor.addChild(root_0, stream_38.nextNode());
                adaptor.addChild(root_0, stream_id.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:2: ^( PARAMETERS ( actorParameters )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETERS, "PARAMETERS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:15: ( actorParameters )?
                if ( stream_actorParameters.hasNext() ) {
                    adaptor.addChild(root_1, stream_actorParameters.nextTree());

                }
                stream_actorParameters.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:113:2: ^( INPUTS ( $inputs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:113:11: ( $inputs)?
                if ( stream_inputs.hasNext() ) {
                    adaptor.addChild(root_1, stream_inputs.nextTree());

                }
                stream_inputs.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:114:2: ^( OUTPUTS ( $outputs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:114:12: ( $outputs)?
                if ( stream_outputs.hasNext() ) {
                    adaptor.addChild(root_1, stream_outputs.nextTree());

                }
                stream_outputs.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:2: ^( ACTOR_DECLS actorDeclarations )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:117:1: actorDeclaration : ( ID ( ( ( '.' ID )* ':' ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' ) ) | ( '[' typePars ']' | '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' ) | 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | priorityOrder | 'function' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end' -> FUNCTION | 'procedure' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end' -> PROCEDURE );
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
        RewriteRuleTokenStream stream_49=new RewriteRuleTokenStream(adaptor,"token 49");
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_45=new RewriteRuleTokenStream(adaptor,"token 45");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_46=new RewriteRuleTokenStream(adaptor,"token 46");
        RewriteRuleTokenStream stream_35=new RewriteRuleTokenStream(adaptor,"token 35");
        RewriteRuleTokenStream stream_33=new RewriteRuleTokenStream(adaptor,"token 33");
        RewriteRuleTokenStream stream_34=new RewriteRuleTokenStream(adaptor,"token 34");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleTokenStream stream_43=new RewriteRuleTokenStream(adaptor,"token 43");
        RewriteRuleTokenStream stream_42=new RewriteRuleTokenStream(adaptor,"token 42");
        RewriteRuleTokenStream stream_32=new RewriteRuleTokenStream(adaptor,"token 32");
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_51=new RewriteRuleTokenStream(adaptor,"token 51");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_52=new RewriteRuleTokenStream(adaptor,"token 52");
        RewriteRuleTokenStream stream_53=new RewriteRuleTokenStream(adaptor,"token 53");
        RewriteRuleTokenStream stream_50=new RewriteRuleTokenStream(adaptor,"token 50");
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:120:17: ( ID ( ( ( '.' ID )* ':' ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' ) ) | ( '[' typePars ']' | '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' ) | 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | priorityOrder | 'function' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end' -> FUNCTION | 'procedure' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end' -> PROCEDURE )
            int alt51=6;
            switch ( input.LA(1) ) {
            case ID:
                {
                alt51=1;
                }
                break;
            case 44:
                {
                alt51=2;
                }
                break;
            case 46:
                {
                alt51=3;
                }
                break;
            case 85:
                {
                alt51=4;
                }
                break;
            case 50:
                {
                alt51=5;
                }
                break;
            case 52:
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:3: ID ( ( ( '.' ID )* ':' ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' ) ) | ( '[' typePars ']' | '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' )
                    {
                    ID46=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration441);  
                    stream_ID.add(ID46);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:6: ( ( ( '.' ID )* ':' ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' ) ) | ( '[' typePars ']' | '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';' )
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==32||LA32_0==43) ) {
                        alt32=1;
                    }
                    else if ( (LA32_0==ID||LA32_0==33||LA32_0==39) ) {
                        alt32=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 32, 0, input);

                        throw nvae;
                    }
                    switch (alt32) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:5: ( ( '.' ID )* ':' ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' ) )
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:5: ( ( '.' ID )* ':' ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' ) )
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:6: ( '.' ID )* ':' ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' )
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:6: ( '.' ID )*
                            loop17:
                            do {
                                int alt17=2;
                                int LA17_0 = input.LA(1);

                                if ( (LA17_0==43) ) {
                                    alt17=1;
                                }


                                switch (alt17) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:126:7: '.' ID
                            	    {
                            	    char_literal47=(Token)match(input,43,FOLLOW_43_in_actorDeclaration451);  
                            	    stream_43.add(char_literal47);

                            	    ID48=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration453);  
                            	    stream_ID.add(ID48);


                            	    }
                            	    break;

                            	default :
                            	    break loop17;
                                }
                            } while (true);

                            char_literal49=(Token)match(input,32,FOLLOW_32_in_actorDeclaration457);  
                            stream_32.add(char_literal49);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:127:7: ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' )
                            int alt29=2;
                            int LA29_0 = input.LA(1);

                            if ( (LA29_0==44) ) {
                                alt29=1;
                            }
                            else if ( (LA29_0==46) ) {
                                alt29=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 29, 0, input);

                                throw nvae;
                            }
                            switch (alt29) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:127:8: 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end'
                                    {
                                    string_literal50=(Token)match(input,44,FOLLOW_44_in_actorDeclaration466);  
                                    stream_44.add(string_literal50);

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:127:17: ( actionInputs )?
                                    int alt18=2;
                                    int LA18_0 = input.LA(1);

                                    if ( (LA18_0==ID||LA18_0==33) ) {
                                        alt18=1;
                                    }
                                    switch (alt18) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:127:17: actionInputs
                                            {
                                            pushFollow(FOLLOW_actionInputs_in_actorDeclaration468);
                                            actionInputs51=actionInputs();

                                            state._fsp--;

                                            stream_actionInputs.add(actionInputs51.getTree());

                                            }
                                            break;

                                    }

                                    string_literal52=(Token)match(input,41,FOLLOW_41_in_actorDeclaration471);  
                                    stream_41.add(string_literal52);

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:127:37: ( actionOutputs )?
                                    int alt19=2;
                                    int LA19_0 = input.LA(1);

                                    if ( (LA19_0==ID||LA19_0==33) ) {
                                        alt19=1;
                                    }
                                    switch (alt19) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:127:37: actionOutputs
                                            {
                                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration473);
                                            actionOutputs53=actionOutputs();

                                            state._fsp--;

                                            stream_actionOutputs.add(actionOutputs53.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:127:52: ( actionGuards )?
                                    int alt20=2;
                                    int LA20_0 = input.LA(1);

                                    if ( (LA20_0==31) ) {
                                        alt20=1;
                                    }
                                    switch (alt20) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:127:52: actionGuards
                                            {
                                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration476);
                                            actionGuards54=actionGuards();

                                            state._fsp--;

                                            stream_actionGuards.add(actionGuards54.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:127:66: ( actionDelay )?
                                    int alt21=2;
                                    int LA21_0 = input.LA(1);

                                    if ( (LA21_0==30) ) {
                                        alt21=1;
                                    }
                                    switch (alt21) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:127:66: actionDelay
                                            {
                                            pushFollow(FOLLOW_actionDelay_in_actorDeclaration479);
                                            actionDelay55=actionDelay();

                                            state._fsp--;

                                            stream_actionDelay.add(actionDelay55.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:127:79: ( 'var' varDecls )?
                                    int alt22=2;
                                    int LA22_0 = input.LA(1);

                                    if ( (LA22_0==45) ) {
                                        alt22=1;
                                    }
                                    switch (alt22) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:127:80: 'var' varDecls
                                            {
                                            string_literal56=(Token)match(input,45,FOLLOW_45_in_actorDeclaration483);  
                                            stream_45.add(string_literal56);

                                            pushFollow(FOLLOW_varDecls_in_actorDeclaration485);
                                            varDecls57=varDecls();

                                            state._fsp--;

                                            stream_varDecls.add(varDecls57.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:127:97: ( actionStatements )?
                                    int alt23=2;
                                    int LA23_0 = input.LA(1);

                                    if ( (LA23_0==37) ) {
                                        alt23=1;
                                    }
                                    switch (alt23) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:127:97: actionStatements
                                            {
                                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration489);
                                            actionStatements58=actionStatements();

                                            state._fsp--;

                                            stream_actionStatements.add(actionStatements58.getTree());

                                            }
                                            break;

                                    }

                                    string_literal59=(Token)match(input,42,FOLLOW_42_in_actorDeclaration492);  
                                    stream_42.add(string_literal59);

                                     

                                    }
                                    break;
                                case 2 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:7: 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end'
                                    {
                                    string_literal60=(Token)match(input,46,FOLLOW_46_in_actorDeclaration511);  
                                    stream_46.add(string_literal60);

                                    string_literal61=(Token)match(input,41,FOLLOW_41_in_actorDeclaration513);  
                                    stream_41.add(string_literal61);

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:26: ( actionOutputs )?
                                    int alt24=2;
                                    int LA24_0 = input.LA(1);

                                    if ( (LA24_0==ID||LA24_0==33) ) {
                                        alt24=1;
                                    }
                                    switch (alt24) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:26: actionOutputs
                                            {
                                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration515);
                                            actionOutputs62=actionOutputs();

                                            state._fsp--;

                                            stream_actionOutputs.add(actionOutputs62.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:41: ( actionGuards )?
                                    int alt25=2;
                                    int LA25_0 = input.LA(1);

                                    if ( (LA25_0==31) ) {
                                        alt25=1;
                                    }
                                    switch (alt25) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:41: actionGuards
                                            {
                                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration518);
                                            actionGuards63=actionGuards();

                                            state._fsp--;

                                            stream_actionGuards.add(actionGuards63.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:55: ( actionDelay )?
                                    int alt26=2;
                                    int LA26_0 = input.LA(1);

                                    if ( (LA26_0==30) ) {
                                        alt26=1;
                                    }
                                    switch (alt26) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:55: actionDelay
                                            {
                                            pushFollow(FOLLOW_actionDelay_in_actorDeclaration521);
                                            actionDelay64=actionDelay();

                                            state._fsp--;

                                            stream_actionDelay.add(actionDelay64.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:68: ( 'var' varDecls )?
                                    int alt27=2;
                                    int LA27_0 = input.LA(1);

                                    if ( (LA27_0==45) ) {
                                        alt27=1;
                                    }
                                    switch (alt27) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:69: 'var' varDecls
                                            {
                                            string_literal65=(Token)match(input,45,FOLLOW_45_in_actorDeclaration525);  
                                            stream_45.add(string_literal65);

                                            pushFollow(FOLLOW_varDecls_in_actorDeclaration527);
                                            varDecls66=varDecls();

                                            state._fsp--;

                                            stream_varDecls.add(varDecls66.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:86: ( actionStatements )?
                                    int alt28=2;
                                    int LA28_0 = input.LA(1);

                                    if ( (LA28_0==37) ) {
                                        alt28=1;
                                    }
                                    switch (alt28) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:86: actionStatements
                                            {
                                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration531);
                                            actionStatements67=actionStatements();

                                            state._fsp--;

                                            stream_actionStatements.add(actionStatements67.getTree());

                                            }
                                            break;

                                    }

                                    string_literal68=(Token)match(input,42,FOLLOW_42_in_actorDeclaration534);  
                                    stream_42.add(string_literal68);

                                     

                                    }
                                    break;

                            }


                            }


                            }
                            break;
                        case 2 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:5: ( '[' typePars ']' | '(' attrs= typeAttrs ')' )? varName= ID ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) ) ';'
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:5: ( '[' typePars ']' | '(' attrs= typeAttrs ')' )?
                            int alt30=3;
                            int LA30_0 = input.LA(1);

                            if ( (LA30_0==33) ) {
                                alt30=1;
                            }
                            else if ( (LA30_0==39) ) {
                                alt30=2;
                            }
                            switch (alt30) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:6: '[' typePars ']'
                                    {
                                    char_literal69=(Token)match(input,33,FOLLOW_33_in_actorDeclaration562);  
                                    stream_33.add(char_literal69);

                                    pushFollow(FOLLOW_typePars_in_actorDeclaration564);
                                    typePars70=typePars();

                                    state._fsp--;

                                    stream_typePars.add(typePars70.getTree());
                                    char_literal71=(Token)match(input,34,FOLLOW_34_in_actorDeclaration566);  
                                    stream_34.add(char_literal71);


                                    }
                                    break;
                                case 2 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:136:25: '(' attrs= typeAttrs ')'
                                    {
                                    char_literal72=(Token)match(input,39,FOLLOW_39_in_actorDeclaration570);  
                                    stream_39.add(char_literal72);

                                    pushFollow(FOLLOW_typeAttrs_in_actorDeclaration574);
                                    attrs=typeAttrs();

                                    state._fsp--;

                                    stream_typeAttrs.add(attrs.getTree());
                                    char_literal73=(Token)match(input,40,FOLLOW_40_in_actorDeclaration576);  
                                    stream_40.add(char_literal73);


                                    }
                                    break;

                            }

                            varName=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration586);  
                            stream_ID.add(varName);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:138:5: ( '=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression ) | ':=' expression -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression ) | -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE ) )
                            int alt31=3;
                            switch ( input.LA(1) ) {
                            case 47:
                                {
                                alt31=1;
                                }
                                break;
                            case 48:
                                {
                                alt31=2;
                                }
                                break;
                            case 49:
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
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:138:8: '=' expression
                                    {
                                    char_literal74=(Token)match(input,47,FOLLOW_47_in_actorDeclaration595);  
                                    stream_47.add(char_literal74);

                                    pushFollow(FOLLOW_expression_in_actorDeclaration597);
                                    expression75=expression();

                                    state._fsp--;

                                    stream_expression.add(expression75.getTree());


                                    // AST REWRITE
                                    // elements: varName, attrs, ID, expression
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
                                    // 138:23: -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression )
                                    {
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:138:26: ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName NON_ASSIGNABLE expression )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:138:38: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:138:48: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:138:61: ( $attrs)?
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
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:139:8: ':=' expression
                                    {
                                    string_literal76=(Token)match(input,48,FOLLOW_48_in_actorDeclaration633);  
                                    stream_48.add(string_literal76);

                                    pushFollow(FOLLOW_expression_in_actorDeclaration635);
                                    expression77=expression();

                                    state._fsp--;

                                    stream_expression.add(expression77.getTree());


                                    // AST REWRITE
                                    // elements: expression, attrs, ID, varName
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
                                    // 139:24: -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression )
                                    {
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:139:27: ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE expression )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:139:39: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:139:49: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:139:62: ( $attrs)?
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
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:140:8: 
                                    {

                                    // AST REWRITE
                                    // elements: varName, attrs, ID
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
                                    // 140:8: -> ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE )
                                    {
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:140:11: ^( STATE_VAR ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) $varName ASSIGNABLE )
                                        {
                                        Object root_1 = (Object)adaptor.nil();
                                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_VAR, "STATE_VAR"), root_1);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:140:23: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                                        {
                                        Object root_2 = (Object)adaptor.nil();
                                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_2);

                                        adaptor.addChild(root_2, stream_ID.nextNode());
                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:140:33: ^( TYPE_ATTRS ( $attrs)? )
                                        {
                                        Object root_3 = (Object)adaptor.nil();
                                        root_3 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_3);

                                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:140:46: ( $attrs)?
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

                            char_literal78=(Token)match(input,49,FOLLOW_49_in_actorDeclaration697);  
                            stream_49.add(char_literal78);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:3: 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal79=(Token)match(input,44,FOLLOW_44_in_actorDeclaration707); 
                    string_literal79_tree = (Object)adaptor.create(string_literal79);
                    adaptor.addChild(root_0, string_literal79_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:12: ( actionInputs )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==ID||LA33_0==33) ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:12: actionInputs
                            {
                            pushFollow(FOLLOW_actionInputs_in_actorDeclaration709);
                            actionInputs80=actionInputs();

                            state._fsp--;

                            adaptor.addChild(root_0, actionInputs80.getTree());

                            }
                            break;

                    }

                    string_literal81=(Token)match(input,41,FOLLOW_41_in_actorDeclaration712); 
                    string_literal81_tree = (Object)adaptor.create(string_literal81);
                    adaptor.addChild(root_0, string_literal81_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:32: ( actionOutputs )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==ID||LA34_0==33) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:32: actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration714);
                            actionOutputs82=actionOutputs();

                            state._fsp--;

                            adaptor.addChild(root_0, actionOutputs82.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:47: ( actionGuards )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==31) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:47: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration717);
                            actionGuards83=actionGuards();

                            state._fsp--;

                            adaptor.addChild(root_0, actionGuards83.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:61: ( actionDelay )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==30) ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:61: actionDelay
                            {
                            pushFollow(FOLLOW_actionDelay_in_actorDeclaration720);
                            actionDelay84=actionDelay();

                            state._fsp--;

                            adaptor.addChild(root_0, actionDelay84.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:74: ( 'var' varDecls )?
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==45) ) {
                        alt37=1;
                    }
                    switch (alt37) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:75: 'var' varDecls
                            {
                            string_literal85=(Token)match(input,45,FOLLOW_45_in_actorDeclaration724); 
                            string_literal85_tree = (Object)adaptor.create(string_literal85);
                            adaptor.addChild(root_0, string_literal85_tree);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration726);
                            varDecls86=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls86.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:92: ( actionStatements )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==37) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:92: actionStatements
                            {
                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration730);
                            actionStatements87=actionStatements();

                            state._fsp--;

                            adaptor.addChild(root_0, actionStatements87.getTree());

                            }
                            break;

                    }

                    string_literal88=(Token)match(input,42,FOLLOW_42_in_actorDeclaration733); 
                    string_literal88_tree = (Object)adaptor.create(string_literal88);
                    adaptor.addChild(root_0, string_literal88_tree);

                     

                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:3: 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal89=(Token)match(input,46,FOLLOW_46_in_actorDeclaration743); 
                    string_literal89_tree = (Object)adaptor.create(string_literal89);
                    adaptor.addChild(root_0, string_literal89_tree);

                    string_literal90=(Token)match(input,41,FOLLOW_41_in_actorDeclaration745); 
                    string_literal90_tree = (Object)adaptor.create(string_literal90);
                    adaptor.addChild(root_0, string_literal90_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:22: ( actionOutputs )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==ID||LA39_0==33) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:22: actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration747);
                            actionOutputs91=actionOutputs();

                            state._fsp--;

                            adaptor.addChild(root_0, actionOutputs91.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:37: ( actionGuards )?
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( (LA40_0==31) ) {
                        alt40=1;
                    }
                    switch (alt40) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:37: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration750);
                            actionGuards92=actionGuards();

                            state._fsp--;

                            adaptor.addChild(root_0, actionGuards92.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:51: ( actionDelay )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==30) ) {
                        alt41=1;
                    }
                    switch (alt41) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:51: actionDelay
                            {
                            pushFollow(FOLLOW_actionDelay_in_actorDeclaration753);
                            actionDelay93=actionDelay();

                            state._fsp--;

                            adaptor.addChild(root_0, actionDelay93.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:64: ( 'var' varDecls )?
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==45) ) {
                        alt42=1;
                    }
                    switch (alt42) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:65: 'var' varDecls
                            {
                            string_literal94=(Token)match(input,45,FOLLOW_45_in_actorDeclaration757); 
                            string_literal94_tree = (Object)adaptor.create(string_literal94);
                            adaptor.addChild(root_0, string_literal94_tree);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration759);
                            varDecls95=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls95.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:82: ( actionStatements )?
                    int alt43=2;
                    int LA43_0 = input.LA(1);

                    if ( (LA43_0==37) ) {
                        alt43=1;
                    }
                    switch (alt43) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:148:82: actionStatements
                            {
                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration763);
                            actionStatements96=actionStatements();

                            state._fsp--;

                            adaptor.addChild(root_0, actionStatements96.getTree());

                            }
                            break;

                    }

                    string_literal97=(Token)match(input,42,FOLLOW_42_in_actorDeclaration766); 
                    string_literal97_tree = (Object)adaptor.create(string_literal97);
                    adaptor.addChild(root_0, string_literal97_tree);

                     

                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:3: priorityOrder
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_priorityOrder_in_actorDeclaration775);
                    priorityOrder98=priorityOrder();

                    state._fsp--;

                    adaptor.addChild(root_0, priorityOrder98.getTree());
                     

                    }
                    break;
                case 5 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:3: 'function' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end'
                    {
                    string_literal99=(Token)match(input,50,FOLLOW_50_in_actorDeclaration782);  
                    stream_50.add(string_literal99);

                    ID100=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration784);  
                    stream_ID.add(ID100);

                    char_literal101=(Token)match(input,39,FOLLOW_39_in_actorDeclaration786);  
                    stream_39.add(char_literal101);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:21: ( varDeclNoExpr ( ',' varDeclNoExpr )* )?
                    int alt45=2;
                    int LA45_0 = input.LA(1);

                    if ( (LA45_0==ID) ) {
                        alt45=1;
                    }
                    switch (alt45) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:22: varDeclNoExpr ( ',' varDeclNoExpr )*
                            {
                            pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration789);
                            varDeclNoExpr102=varDeclNoExpr();

                            state._fsp--;

                            stream_varDeclNoExpr.add(varDeclNoExpr102.getTree());
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:36: ( ',' varDeclNoExpr )*
                            loop44:
                            do {
                                int alt44=2;
                                int LA44_0 = input.LA(1);

                                if ( (LA44_0==35) ) {
                                    alt44=1;
                                }


                                switch (alt44) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:153:37: ',' varDeclNoExpr
                            	    {
                            	    char_literal103=(Token)match(input,35,FOLLOW_35_in_actorDeclaration792);  
                            	    stream_35.add(char_literal103);

                            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration794);
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

                    char_literal105=(Token)match(input,40,FOLLOW_40_in_actorDeclaration800);  
                    stream_40.add(char_literal105);

                    string_literal106=(Token)match(input,51,FOLLOW_51_in_actorDeclaration802);  
                    stream_51.add(string_literal106);

                    pushFollow(FOLLOW_typeDef_in_actorDeclaration804);
                    typeDef107=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef107.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:5: ( 'var' varDecls )?
                    int alt46=2;
                    int LA46_0 = input.LA(1);

                    if ( (LA46_0==45) ) {
                        alt46=1;
                    }
                    switch (alt46) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:6: 'var' varDecls
                            {
                            string_literal108=(Token)match(input,45,FOLLOW_45_in_actorDeclaration811);  
                            stream_45.add(string_literal108);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration813);
                            varDecls109=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls109.getTree());

                            }
                            break;

                    }

                    char_literal110=(Token)match(input,32,FOLLOW_32_in_actorDeclaration817);  
                    stream_32.add(char_literal110);

                    pushFollow(FOLLOW_expression_in_actorDeclaration825);
                    expression111=expression();

                    state._fsp--;

                    stream_expression.add(expression111.getTree());
                    string_literal112=(Token)match(input,42,FOLLOW_42_in_actorDeclaration831);  
                    stream_42.add(string_literal112);



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
                    // 157:2: -> FUNCTION
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(FUNCTION, "FUNCTION"));

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 6 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:159:3: 'procedure' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end'
                    {
                    string_literal113=(Token)match(input,52,FOLLOW_52_in_actorDeclaration841);  
                    stream_52.add(string_literal113);

                    ID114=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration843);  
                    stream_ID.add(ID114);

                    char_literal115=(Token)match(input,39,FOLLOW_39_in_actorDeclaration845);  
                    stream_39.add(char_literal115);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:159:22: ( varDeclNoExpr ( ',' varDeclNoExpr )* )?
                    int alt48=2;
                    int LA48_0 = input.LA(1);

                    if ( (LA48_0==ID) ) {
                        alt48=1;
                    }
                    switch (alt48) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:159:23: varDeclNoExpr ( ',' varDeclNoExpr )*
                            {
                            pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration848);
                            varDeclNoExpr116=varDeclNoExpr();

                            state._fsp--;

                            stream_varDeclNoExpr.add(varDeclNoExpr116.getTree());
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:159:37: ( ',' varDeclNoExpr )*
                            loop47:
                            do {
                                int alt47=2;
                                int LA47_0 = input.LA(1);

                                if ( (LA47_0==35) ) {
                                    alt47=1;
                                }


                                switch (alt47) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:159:38: ',' varDeclNoExpr
                            	    {
                            	    char_literal117=(Token)match(input,35,FOLLOW_35_in_actorDeclaration851);  
                            	    stream_35.add(char_literal117);

                            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration853);
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

                    char_literal119=(Token)match(input,40,FOLLOW_40_in_actorDeclaration859);  
                    stream_40.add(char_literal119);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:160:5: ( 'var' varDecls )?
                    int alt49=2;
                    int LA49_0 = input.LA(1);

                    if ( (LA49_0==45) ) {
                        alt49=1;
                    }
                    switch (alt49) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:160:6: 'var' varDecls
                            {
                            string_literal120=(Token)match(input,45,FOLLOW_45_in_actorDeclaration866);  
                            stream_45.add(string_literal120);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration868);
                            varDecls121=varDecls();

                            state._fsp--;

                            stream_varDecls.add(varDecls121.getTree());

                            }
                            break;

                    }

                    string_literal122=(Token)match(input,53,FOLLOW_53_in_actorDeclaration876);  
                    stream_53.add(string_literal122);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:13: ( statement )*
                    loop50:
                    do {
                        int alt50=2;
                        int LA50_0 = input.LA(1);

                        if ( (LA50_0==ID||LA50_0==53||LA50_0==78||LA50_0==83||(LA50_0>=89 && LA50_0<=90)||LA50_0==92) ) {
                            alt50=1;
                        }


                        switch (alt50) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:13: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_actorDeclaration878);
                    	    statement123=statement();

                    	    state._fsp--;

                    	    stream_statement.add(statement123.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop50;
                        }
                    } while (true);

                    string_literal124=(Token)match(input,42,FOLLOW_42_in_actorDeclaration881);  
                    stream_42.add(string_literal124);



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
                    // 162:2: -> PROCEDURE
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:1: actorDeclarations : ( actorDeclaration )* ( schedule ( actorDeclaration )* )? -> ( actorDeclaration )* ( schedule )? ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:18: ( ( actorDeclaration )* ( schedule ( actorDeclaration )* )? -> ( actorDeclaration )* ( schedule )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:20: ( actorDeclaration )* ( schedule ( actorDeclaration )* )?
            {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:20: ( actorDeclaration )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==ID||LA52_0==44||LA52_0==46||LA52_0==50||LA52_0==52||LA52_0==85) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:20: actorDeclaration
            	    {
            	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations893);
            	    actorDeclaration125=actorDeclaration();

            	    state._fsp--;

            	    stream_actorDeclaration.add(actorDeclaration125.getTree());

            	    }
            	    break;

            	default :
            	    break loop52;
                }
            } while (true);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:38: ( schedule ( actorDeclaration )* )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==86) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:39: schedule ( actorDeclaration )*
                    {
                    pushFollow(FOLLOW_schedule_in_actorDeclarations897);
                    schedule126=schedule();

                    state._fsp--;

                    stream_schedule.add(schedule126.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:48: ( actorDeclaration )*
                    loop53:
                    do {
                        int alt53=2;
                        int LA53_0 = input.LA(1);

                        if ( (LA53_0==ID||LA53_0==44||LA53_0==46||LA53_0==50||LA53_0==52||LA53_0==85) ) {
                            alt53=1;
                        }


                        switch (alt53) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:48: actorDeclaration
                    	    {
                    	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations899);
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
            // 164:68: -> ( actorDeclaration )* ( schedule )?
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:71: ( actorDeclaration )*
                while ( stream_actorDeclaration.hasNext() ) {
                    adaptor.addChild(root_0, stream_actorDeclaration.nextTree());

                }
                stream_actorDeclaration.reset();
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:89: ( schedule )?
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:1: actorImport : 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:12: ( 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:169:14: 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            {
            root_0 = (Object)adaptor.nil();

            string_literal128=(Token)match(input,54,FOLLOW_54_in_actorImport922); 
            string_literal128_tree = (Object)adaptor.create(string_literal128);
            adaptor.addChild(root_0, string_literal128_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:3: ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==26) ) {
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:4: 'all' qualifiedIdent ';'
                    {
                    string_literal129=(Token)match(input,26,FOLLOW_26_in_actorImport927); 
                    string_literal129_tree = (Object)adaptor.create(string_literal129);
                    adaptor.addChild(root_0, string_literal129_tree);

                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport929);
                    qualifiedIdent130=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent130.getTree());
                    char_literal131=(Token)match(input,49,FOLLOW_49_in_actorImport931); 
                    char_literal131_tree = (Object)adaptor.create(char_literal131);
                    adaptor.addChild(root_0, char_literal131_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:171:3: qualifiedIdent ';'
                    {
                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport937);
                    qualifiedIdent132=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent132.getTree());
                    char_literal133=(Token)match(input,49,FOLLOW_49_in_actorImport939); 
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:173:1: actorParameter : typeDef ID ( '=' expression )? -> ^( PARAMETER typeDef ID ( expression )? ) ;
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
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:175:15: ( typeDef ID ( '=' expression )? -> ^( PARAMETER typeDef ID ( expression )? ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:2: typeDef ID ( '=' expression )?
            {
            pushFollow(FOLLOW_typeDef_in_actorParameter954);
            typeDef134=typeDef();

            state._fsp--;

            stream_typeDef.add(typeDef134.getTree());
            ID135=(Token)match(input,ID,FOLLOW_ID_in_actorParameter956);  
            stream_ID.add(ID135);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:13: ( '=' expression )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==47) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:14: '=' expression
                    {
                    char_literal136=(Token)match(input,47,FOLLOW_47_in_actorParameter959);  
                    stream_47.add(char_literal136);

                    pushFollow(FOLLOW_expression_in_actorParameter961);
                    expression137=expression();

                    state._fsp--;

                    stream_expression.add(expression137.getTree());

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
            // 176:31: -> ^( PARAMETER typeDef ID ( expression )? )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:34: ^( PARAMETER typeDef ID ( expression )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETER, "PARAMETER"), root_1);

                adaptor.addChild(root_1, stream_typeDef.nextTree());
                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:57: ( expression )?
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:1: actorParameters : actorParameter ( ',' actorParameter )* -> ( actorParameter )+ ;
    public final RVCCalParser.actorParameters_return actorParameters() throws RecognitionException {
        RVCCalParser.actorParameters_return retval = new RVCCalParser.actorParameters_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal139=null;
        RVCCalParser.actorParameter_return actorParameter138 = null;

        RVCCalParser.actorParameter_return actorParameter140 = null;


        Object char_literal139_tree=null;
        RewriteRuleTokenStream stream_35=new RewriteRuleTokenStream(adaptor,"token 35");
        RewriteRuleSubtreeStream stream_actorParameter=new RewriteRuleSubtreeStream(adaptor,"rule actorParameter");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:16: ( actorParameter ( ',' actorParameter )* -> ( actorParameter )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:18: actorParameter ( ',' actorParameter )*
            {
            pushFollow(FOLLOW_actorParameter_in_actorParameters983);
            actorParameter138=actorParameter();

            state._fsp--;

            stream_actorParameter.add(actorParameter138.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:33: ( ',' actorParameter )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==35) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:34: ',' actorParameter
            	    {
            	    char_literal139=(Token)match(input,35,FOLLOW_35_in_actorParameters986);  
            	    stream_35.add(char_literal139);

            	    pushFollow(FOLLOW_actorParameter_in_actorParameters988);
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
            // 178:55: -> ( actorParameter )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:180:1: actorPortDecl : ( 'multi' typeDef ID | typeDef ID -> ^( PORT typeDef ID ) );
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:183:14: ( 'multi' typeDef ID | typeDef ID -> ^( PORT typeDef ID ) )
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==55) ) {
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:2: 'multi' typeDef ID
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal141=(Token)match(input,55,FOLLOW_55_in_actorPortDecl1008); 
                    string_literal141_tree = (Object)adaptor.create(string_literal141);
                    adaptor.addChild(root_0, string_literal141_tree);

                    pushFollow(FOLLOW_typeDef_in_actorPortDecl1010);
                    typeDef142=typeDef();

                    state._fsp--;

                    adaptor.addChild(root_0, typeDef142.getTree());
                    ID143=(Token)match(input,ID,FOLLOW_ID_in_actorPortDecl1012); 
                    ID143_tree = (Object)adaptor.create(ID143);
                    adaptor.addChild(root_0, ID143_tree);

                     System.out.println("RVC-CAL does not permit the use of multi ports."); 

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:186:3: typeDef ID
                    {
                    pushFollow(FOLLOW_typeDef_in_actorPortDecl1019);
                    typeDef144=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef144.getTree());
                    ID145=(Token)match(input,ID,FOLLOW_ID_in_actorPortDecl1021);  
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
                    // 186:14: -> ^( PORT typeDef ID )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:186:17: ^( PORT typeDef ID )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:1: actorPortDecls : actorPortDecl ( ',' actorPortDecl )* -> ( actorPortDecl )+ ;
    public final RVCCalParser.actorPortDecls_return actorPortDecls() throws RecognitionException {
        RVCCalParser.actorPortDecls_return retval = new RVCCalParser.actorPortDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal147=null;
        RVCCalParser.actorPortDecl_return actorPortDecl146 = null;

        RVCCalParser.actorPortDecl_return actorPortDecl148 = null;


        Object char_literal147_tree=null;
        RewriteRuleTokenStream stream_35=new RewriteRuleTokenStream(adaptor,"token 35");
        RewriteRuleSubtreeStream stream_actorPortDecl=new RewriteRuleSubtreeStream(adaptor,"rule actorPortDecl");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:15: ( actorPortDecl ( ',' actorPortDecl )* -> ( actorPortDecl )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:17: actorPortDecl ( ',' actorPortDecl )*
            {
            pushFollow(FOLLOW_actorPortDecl_in_actorPortDecls1038);
            actorPortDecl146=actorPortDecl();

            state._fsp--;

            stream_actorPortDecl.add(actorPortDecl146.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:31: ( ',' actorPortDecl )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==35) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:188:32: ',' actorPortDecl
            	    {
            	    char_literal147=(Token)match(input,35,FOLLOW_35_in_actorPortDecls1041);  
            	    stream_35.add(char_literal147);

            	    pushFollow(FOLLOW_actorPortDecl_in_actorPortDecls1043);
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
            // 188:52: -> ( actorPortDecl )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:190:1: expression : and_expr ( ( 'or' | '||' ) and_expr )? ;
    public final RVCCalParser.expression_return expression() throws RecognitionException {
        RVCCalParser.expression_return retval = new RVCCalParser.expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set150=null;
        RVCCalParser.and_expr_return and_expr149 = null;

        RVCCalParser.and_expr_return and_expr151 = null;


        Object set150_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:193:11: ( and_expr ( ( 'or' | '||' ) and_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:193:13: and_expr ( ( 'or' | '||' ) and_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_and_expr_in_expression1062);
            and_expr149=and_expr();

            state._fsp--;

            adaptor.addChild(root_0, and_expr149.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:193:22: ( ( 'or' | '||' ) and_expr )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( ((LA60_0>=56 && LA60_0<=57)) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:193:23: ( 'or' | '||' ) and_expr
                    {
                    set150=(Token)input.LT(1);
                    if ( (input.LA(1)>=56 && input.LA(1)<=57) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set150));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_and_expr_in_expression1073);
                    and_expr151=and_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, and_expr151.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:1: and_expr : bitor_expr ( ( 'and' | '&&' ) bitor_expr )? ;
    public final RVCCalParser.and_expr_return and_expr() throws RecognitionException {
        RVCCalParser.and_expr_return retval = new RVCCalParser.and_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set153=null;
        RVCCalParser.bitor_expr_return bitor_expr152 = null;

        RVCCalParser.bitor_expr_return bitor_expr154 = null;


        Object set153_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:9: ( bitor_expr ( ( 'and' | '&&' ) bitor_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:11: bitor_expr ( ( 'and' | '&&' ) bitor_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_bitor_expr_in_and_expr1084);
            bitor_expr152=bitor_expr();

            state._fsp--;

            adaptor.addChild(root_0, bitor_expr152.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:22: ( ( 'and' | '&&' ) bitor_expr )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( ((LA61_0>=58 && LA61_0<=59)) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:23: ( 'and' | '&&' ) bitor_expr
                    {
                    set153=(Token)input.LT(1);
                    if ( (input.LA(1)>=58 && input.LA(1)<=59) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set153));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_bitor_expr_in_and_expr1095);
                    bitor_expr154=bitor_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, bitor_expr154.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:197:1: bitor_expr : bitand_expr ( '|' bitand_expr )? ;
    public final RVCCalParser.bitor_expr_return bitor_expr() throws RecognitionException {
        RVCCalParser.bitor_expr_return retval = new RVCCalParser.bitor_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal156=null;
        RVCCalParser.bitand_expr_return bitand_expr155 = null;

        RVCCalParser.bitand_expr_return bitand_expr157 = null;


        Object char_literal156_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:197:11: ( bitand_expr ( '|' bitand_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:197:13: bitand_expr ( '|' bitand_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_bitand_expr_in_bitor_expr1106);
            bitand_expr155=bitand_expr();

            state._fsp--;

            adaptor.addChild(root_0, bitand_expr155.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:197:25: ( '|' bitand_expr )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==60) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:197:26: '|' bitand_expr
                    {
                    char_literal156=(Token)match(input,60,FOLLOW_60_in_bitor_expr1109); 
                    char_literal156_tree = (Object)adaptor.create(char_literal156);
                    adaptor.addChild(root_0, char_literal156_tree);

                    pushFollow(FOLLOW_bitand_expr_in_bitor_expr1111);
                    bitand_expr157=bitand_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, bitand_expr157.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:199:1: bitand_expr : eq_expr ( '&' eq_expr )? ;
    public final RVCCalParser.bitand_expr_return bitand_expr() throws RecognitionException {
        RVCCalParser.bitand_expr_return retval = new RVCCalParser.bitand_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal159=null;
        RVCCalParser.eq_expr_return eq_expr158 = null;

        RVCCalParser.eq_expr_return eq_expr160 = null;


        Object char_literal159_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:199:12: ( eq_expr ( '&' eq_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:199:14: eq_expr ( '&' eq_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_eq_expr_in_bitand_expr1122);
            eq_expr158=eq_expr();

            state._fsp--;

            adaptor.addChild(root_0, eq_expr158.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:199:22: ( '&' eq_expr )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==61) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:199:23: '&' eq_expr
                    {
                    char_literal159=(Token)match(input,61,FOLLOW_61_in_bitand_expr1125); 
                    char_literal159_tree = (Object)adaptor.create(char_literal159);
                    adaptor.addChild(root_0, char_literal159_tree);

                    pushFollow(FOLLOW_eq_expr_in_bitand_expr1127);
                    eq_expr160=eq_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, eq_expr160.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:201:1: eq_expr : rel_expr ( ( '=' | '!=' ) rel_expr )? ;
    public final RVCCalParser.eq_expr_return eq_expr() throws RecognitionException {
        RVCCalParser.eq_expr_return retval = new RVCCalParser.eq_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set162=null;
        RVCCalParser.rel_expr_return rel_expr161 = null;

        RVCCalParser.rel_expr_return rel_expr163 = null;


        Object set162_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:201:8: ( rel_expr ( ( '=' | '!=' ) rel_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:201:10: rel_expr ( ( '=' | '!=' ) rel_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_rel_expr_in_eq_expr1138);
            rel_expr161=rel_expr();

            state._fsp--;

            adaptor.addChild(root_0, rel_expr161.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:201:19: ( ( '=' | '!=' ) rel_expr )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==47||LA64_0==62) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:201:20: ( '=' | '!=' ) rel_expr
                    {
                    set162=(Token)input.LT(1);
                    if ( input.LA(1)==47||input.LA(1)==62 ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set162));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_rel_expr_in_eq_expr1149);
                    rel_expr163=rel_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, rel_expr163.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:203:1: rel_expr : shift_expr ( ( '<' | '>' | '<=' | '>=' ) shift_expr )? ;
    public final RVCCalParser.rel_expr_return rel_expr() throws RecognitionException {
        RVCCalParser.rel_expr_return retval = new RVCCalParser.rel_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set165=null;
        RVCCalParser.shift_expr_return shift_expr164 = null;

        RVCCalParser.shift_expr_return shift_expr166 = null;


        Object set165_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:203:9: ( shift_expr ( ( '<' | '>' | '<=' | '>=' ) shift_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:203:11: shift_expr ( ( '<' | '>' | '<=' | '>=' ) shift_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_shift_expr_in_rel_expr1160);
            shift_expr164=shift_expr();

            state._fsp--;

            adaptor.addChild(root_0, shift_expr164.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:203:22: ( ( '<' | '>' | '<=' | '>=' ) shift_expr )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( ((LA65_0>=63 && LA65_0<=66)) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:203:23: ( '<' | '>' | '<=' | '>=' ) shift_expr
                    {
                    set165=(Token)input.LT(1);
                    if ( (input.LA(1)>=63 && input.LA(1)<=66) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set165));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_shift_expr_in_rel_expr1179);
                    shift_expr166=shift_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, shift_expr166.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:205:1: shift_expr : add_expr ( ( '<<' | '>>' ) add_expr )? ;
    public final RVCCalParser.shift_expr_return shift_expr() throws RecognitionException {
        RVCCalParser.shift_expr_return retval = new RVCCalParser.shift_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set168=null;
        RVCCalParser.add_expr_return add_expr167 = null;

        RVCCalParser.add_expr_return add_expr169 = null;


        Object set168_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:205:11: ( add_expr ( ( '<<' | '>>' ) add_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:205:13: add_expr ( ( '<<' | '>>' ) add_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_add_expr_in_shift_expr1190);
            add_expr167=add_expr();

            state._fsp--;

            adaptor.addChild(root_0, add_expr167.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:205:22: ( ( '<<' | '>>' ) add_expr )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( ((LA66_0>=67 && LA66_0<=68)) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:205:23: ( '<<' | '>>' ) add_expr
                    {
                    set168=(Token)input.LT(1);
                    if ( (input.LA(1)>=67 && input.LA(1)<=68) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set168));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_add_expr_in_shift_expr1201);
                    add_expr169=add_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, add_expr169.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:207:1: add_expr : mul_expr ( ( '+' | '-' ) mul_expr )? ;
    public final RVCCalParser.add_expr_return add_expr() throws RecognitionException {
        RVCCalParser.add_expr_return retval = new RVCCalParser.add_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set171=null;
        RVCCalParser.mul_expr_return mul_expr170 = null;

        RVCCalParser.mul_expr_return mul_expr172 = null;


        Object set171_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:207:9: ( mul_expr ( ( '+' | '-' ) mul_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:207:11: mul_expr ( ( '+' | '-' ) mul_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_mul_expr_in_add_expr1212);
            mul_expr170=mul_expr();

            state._fsp--;

            adaptor.addChild(root_0, mul_expr170.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:207:20: ( ( '+' | '-' ) mul_expr )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( ((LA67_0>=69 && LA67_0<=70)) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:207:21: ( '+' | '-' ) mul_expr
                    {
                    set171=(Token)input.LT(1);
                    if ( (input.LA(1)>=69 && input.LA(1)<=70) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set171));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_mul_expr_in_add_expr1223);
                    mul_expr172=mul_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, mul_expr172.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:209:1: mul_expr : exp_expr ( ( 'div' | 'mod' | '*' | '/' ) exp_expr )? ;
    public final RVCCalParser.mul_expr_return mul_expr() throws RecognitionException {
        RVCCalParser.mul_expr_return retval = new RVCCalParser.mul_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set174=null;
        RVCCalParser.exp_expr_return exp_expr173 = null;

        RVCCalParser.exp_expr_return exp_expr175 = null;


        Object set174_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:209:9: ( exp_expr ( ( 'div' | 'mod' | '*' | '/' ) exp_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:209:11: exp_expr ( ( 'div' | 'mod' | '*' | '/' ) exp_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_exp_expr_in_mul_expr1234);
            exp_expr173=exp_expr();

            state._fsp--;

            adaptor.addChild(root_0, exp_expr173.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:209:20: ( ( 'div' | 'mod' | '*' | '/' ) exp_expr )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( ((LA68_0>=71 && LA68_0<=74)) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:209:21: ( 'div' | 'mod' | '*' | '/' ) exp_expr
                    {
                    set174=(Token)input.LT(1);
                    if ( (input.LA(1)>=71 && input.LA(1)<=74) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set174));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_exp_expr_in_mul_expr1253);
                    exp_expr175=exp_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, exp_expr175.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:211:1: exp_expr : un_expr ( '^' un_expr )? ;
    public final RVCCalParser.exp_expr_return exp_expr() throws RecognitionException {
        RVCCalParser.exp_expr_return retval = new RVCCalParser.exp_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal177=null;
        RVCCalParser.un_expr_return un_expr176 = null;

        RVCCalParser.un_expr_return un_expr178 = null;


        Object char_literal177_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:211:9: ( un_expr ( '^' un_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:211:11: un_expr ( '^' un_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_un_expr_in_exp_expr1264);
            un_expr176=un_expr();

            state._fsp--;

            adaptor.addChild(root_0, un_expr176.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:211:19: ( '^' un_expr )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==75) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:211:20: '^' un_expr
                    {
                    char_literal177=(Token)match(input,75,FOLLOW_75_in_exp_expr1267); 
                    char_literal177_tree = (Object)adaptor.create(char_literal177);
                    adaptor.addChild(root_0, char_literal177_tree);

                    pushFollow(FOLLOW_un_expr_in_exp_expr1269);
                    un_expr178=un_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, un_expr178.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:213:1: un_expr : ( postfix_expression | '-' un_expr | 'not' un_expr | '#' un_expr );
    public final RVCCalParser.un_expr_return un_expr() throws RecognitionException {
        RVCCalParser.un_expr_return retval = new RVCCalParser.un_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal180=null;
        Token string_literal182=null;
        Token char_literal184=null;
        RVCCalParser.postfix_expression_return postfix_expression179 = null;

        RVCCalParser.un_expr_return un_expr181 = null;

        RVCCalParser.un_expr_return un_expr183 = null;

        RVCCalParser.un_expr_return un_expr185 = null;


        Object char_literal180_tree=null;
        Object string_literal182_tree=null;
        Object char_literal184_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:213:8: ( postfix_expression | '-' un_expr | 'not' un_expr | '#' un_expr )
            int alt70=4;
            switch ( input.LA(1) ) {
            case ID:
            case INTEGER:
            case STRING:
            case 33:
            case 39:
            case 78:
            case 81:
            case 82:
                {
                alt70=1;
                }
                break;
            case 70:
                {
                alt70=2;
                }
                break;
            case 76:
                {
                alt70=3;
                }
                break;
            case 77:
                {
                alt70=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }

            switch (alt70) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:213:10: postfix_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_postfix_expression_in_un_expr1280);
                    postfix_expression179=postfix_expression();

                    state._fsp--;

                    adaptor.addChild(root_0, postfix_expression179.getTree());

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:214:4: '-' un_expr
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal180=(Token)match(input,70,FOLLOW_70_in_un_expr1285); 
                    char_literal180_tree = (Object)adaptor.create(char_literal180);
                    adaptor.addChild(root_0, char_literal180_tree);

                    pushFollow(FOLLOW_un_expr_in_un_expr1287);
                    un_expr181=un_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, un_expr181.getTree());

                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:4: 'not' un_expr
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal182=(Token)match(input,76,FOLLOW_76_in_un_expr1292); 
                    string_literal182_tree = (Object)adaptor.create(string_literal182);
                    adaptor.addChild(root_0, string_literal182_tree);

                    pushFollow(FOLLOW_un_expr_in_un_expr1294);
                    un_expr183=un_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, un_expr183.getTree());

                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:216:4: '#' un_expr
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal184=(Token)match(input,77,FOLLOW_77_in_un_expr1299); 
                    char_literal184_tree = (Object)adaptor.create(char_literal184);
                    adaptor.addChild(root_0, char_literal184_tree);

                    pushFollow(FOLLOW_un_expr_in_un_expr1301);
                    un_expr185=un_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, un_expr185.getTree());
                     

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:218:1: postfix_expression : ( '[' expressions ( ':' expressionGenerators )? ']' | 'if' expression 'then' expression 'else' expression 'end' | constant | '(' expression ')' | ID ( '(' ( expressions )? ')' | ( '[' expressions ']' )+ )? );
    public final RVCCalParser.postfix_expression_return postfix_expression() throws RecognitionException {
        RVCCalParser.postfix_expression_return retval = new RVCCalParser.postfix_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal186=null;
        Token char_literal188=null;
        Token char_literal190=null;
        Token string_literal191=null;
        Token string_literal193=null;
        Token string_literal195=null;
        Token string_literal197=null;
        Token char_literal199=null;
        Token char_literal201=null;
        Token ID202=null;
        Token char_literal203=null;
        Token char_literal205=null;
        Token char_literal206=null;
        Token char_literal208=null;
        RVCCalParser.expressions_return expressions187 = null;

        RVCCalParser.expressionGenerators_return expressionGenerators189 = null;

        RVCCalParser.expression_return expression192 = null;

        RVCCalParser.expression_return expression194 = null;

        RVCCalParser.expression_return expression196 = null;

        RVCCalParser.constant_return constant198 = null;

        RVCCalParser.expression_return expression200 = null;

        RVCCalParser.expressions_return expressions204 = null;

        RVCCalParser.expressions_return expressions207 = null;


        Object char_literal186_tree=null;
        Object char_literal188_tree=null;
        Object char_literal190_tree=null;
        Object string_literal191_tree=null;
        Object string_literal193_tree=null;
        Object string_literal195_tree=null;
        Object string_literal197_tree=null;
        Object char_literal199_tree=null;
        Object char_literal201_tree=null;
        Object ID202_tree=null;
        Object char_literal203_tree=null;
        Object char_literal205_tree=null;
        Object char_literal206_tree=null;
        Object char_literal208_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:218:19: ( '[' expressions ( ':' expressionGenerators )? ']' | 'if' expression 'then' expression 'else' expression 'end' | constant | '(' expression ')' | ID ( '(' ( expressions )? ')' | ( '[' expressions ']' )+ )? )
            int alt75=5;
            switch ( input.LA(1) ) {
            case 33:
                {
                alt75=1;
                }
                break;
            case 78:
                {
                alt75=2;
                }
                break;
            case INTEGER:
            case STRING:
            case 81:
            case 82:
                {
                alt75=3;
                }
                break;
            case 39:
                {
                alt75=4;
                }
                break;
            case ID:
                {
                alt75=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 75, 0, input);

                throw nvae;
            }

            switch (alt75) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:3: '[' expressions ( ':' expressionGenerators )? ']'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal186=(Token)match(input,33,FOLLOW_33_in_postfix_expression1312); 
                    char_literal186_tree = (Object)adaptor.create(char_literal186);
                    adaptor.addChild(root_0, char_literal186_tree);

                    pushFollow(FOLLOW_expressions_in_postfix_expression1314);
                    expressions187=expressions();

                    state._fsp--;

                    adaptor.addChild(root_0, expressions187.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:19: ( ':' expressionGenerators )?
                    int alt71=2;
                    int LA71_0 = input.LA(1);

                    if ( (LA71_0==32) ) {
                        alt71=1;
                    }
                    switch (alt71) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:219:20: ':' expressionGenerators
                            {
                            char_literal188=(Token)match(input,32,FOLLOW_32_in_postfix_expression1317); 
                            char_literal188_tree = (Object)adaptor.create(char_literal188);
                            adaptor.addChild(root_0, char_literal188_tree);

                            pushFollow(FOLLOW_expressionGenerators_in_postfix_expression1319);
                            expressionGenerators189=expressionGenerators();

                            state._fsp--;

                            adaptor.addChild(root_0, expressionGenerators189.getTree());

                            }
                            break;

                    }

                    char_literal190=(Token)match(input,34,FOLLOW_34_in_postfix_expression1323); 
                    char_literal190_tree = (Object)adaptor.create(char_literal190);
                    adaptor.addChild(root_0, char_literal190_tree);


                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:220:3: 'if' expression 'then' expression 'else' expression 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal191=(Token)match(input,78,FOLLOW_78_in_postfix_expression1327); 
                    string_literal191_tree = (Object)adaptor.create(string_literal191);
                    adaptor.addChild(root_0, string_literal191_tree);

                    pushFollow(FOLLOW_expression_in_postfix_expression1329);
                    expression192=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression192.getTree());
                    string_literal193=(Token)match(input,79,FOLLOW_79_in_postfix_expression1331); 
                    string_literal193_tree = (Object)adaptor.create(string_literal193);
                    adaptor.addChild(root_0, string_literal193_tree);

                    pushFollow(FOLLOW_expression_in_postfix_expression1333);
                    expression194=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression194.getTree());
                    string_literal195=(Token)match(input,80,FOLLOW_80_in_postfix_expression1335); 
                    string_literal195_tree = (Object)adaptor.create(string_literal195);
                    adaptor.addChild(root_0, string_literal195_tree);

                    pushFollow(FOLLOW_expression_in_postfix_expression1337);
                    expression196=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression196.getTree());
                    string_literal197=(Token)match(input,42,FOLLOW_42_in_postfix_expression1339); 
                    string_literal197_tree = (Object)adaptor.create(string_literal197);
                    adaptor.addChild(root_0, string_literal197_tree);


                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:221:3: constant
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_constant_in_postfix_expression1343);
                    constant198=constant();

                    state._fsp--;

                    adaptor.addChild(root_0, constant198.getTree());

                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:222:3: '(' expression ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal199=(Token)match(input,39,FOLLOW_39_in_postfix_expression1347); 
                    char_literal199_tree = (Object)adaptor.create(char_literal199);
                    adaptor.addChild(root_0, char_literal199_tree);

                    pushFollow(FOLLOW_expression_in_postfix_expression1349);
                    expression200=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression200.getTree());
                    char_literal201=(Token)match(input,40,FOLLOW_40_in_postfix_expression1351); 
                    char_literal201_tree = (Object)adaptor.create(char_literal201);
                    adaptor.addChild(root_0, char_literal201_tree);


                    }
                    break;
                case 5 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:3: ID ( '(' ( expressions )? ')' | ( '[' expressions ']' )+ )?
                    {
                    root_0 = (Object)adaptor.nil();

                    ID202=(Token)match(input,ID,FOLLOW_ID_in_postfix_expression1355); 
                    ID202_tree = (Object)adaptor.create(ID202);
                    adaptor.addChild(root_0, ID202_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:223:6: ( '(' ( expressions )? ')' | ( '[' expressions ']' )+ )?
                    int alt74=3;
                    int LA74_0 = input.LA(1);

                    if ( (LA74_0==39) ) {
                        alt74=1;
                    }
                    else if ( (LA74_0==33) ) {
                        alt74=2;
                    }
                    switch (alt74) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:224:5: '(' ( expressions )? ')'
                            {
                            char_literal203=(Token)match(input,39,FOLLOW_39_in_postfix_expression1363); 
                            char_literal203_tree = (Object)adaptor.create(char_literal203);
                            adaptor.addChild(root_0, char_literal203_tree);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:224:9: ( expressions )?
                            int alt72=2;
                            int LA72_0 = input.LA(1);

                            if ( ((LA72_0>=ID && LA72_0<=STRING)||LA72_0==33||LA72_0==39||LA72_0==70||(LA72_0>=76 && LA72_0<=78)||(LA72_0>=81 && LA72_0<=82)) ) {
                                alt72=1;
                            }
                            switch (alt72) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:224:9: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_postfix_expression1365);
                                    expressions204=expressions();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expressions204.getTree());

                                    }
                                    break;

                            }

                            char_literal205=(Token)match(input,40,FOLLOW_40_in_postfix_expression1368); 
                            char_literal205_tree = (Object)adaptor.create(char_literal205);
                            adaptor.addChild(root_0, char_literal205_tree);


                            }
                            break;
                        case 2 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:6: ( '[' expressions ']' )+
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:6: ( '[' expressions ']' )+
                            int cnt73=0;
                            loop73:
                            do {
                                int alt73=2;
                                int LA73_0 = input.LA(1);

                                if ( (LA73_0==33) ) {
                                    alt73=1;
                                }


                                switch (alt73) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:225:7: '[' expressions ']'
                            	    {
                            	    char_literal206=(Token)match(input,33,FOLLOW_33_in_postfix_expression1376); 
                            	    char_literal206_tree = (Object)adaptor.create(char_literal206);
                            	    adaptor.addChild(root_0, char_literal206_tree);

                            	    pushFollow(FOLLOW_expressions_in_postfix_expression1378);
                            	    expressions207=expressions();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, expressions207.getTree());
                            	    char_literal208=(Token)match(input,34,FOLLOW_34_in_postfix_expression1380); 
                            	    char_literal208_tree = (Object)adaptor.create(char_literal208);
                            	    adaptor.addChild(root_0, char_literal208_tree);


                            	    }
                            	    break;

                            	default :
                            	    if ( cnt73 >= 1 ) break loop73;
                                        EarlyExitException eee =
                                            new EarlyExitException(73, input);
                                        throw eee;
                                }
                                cnt73++;
                            } while (true);


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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:1: constant : ( 'true' | 'false' | INTEGER | STRING );
    public final RVCCalParser.constant_return constant() throws RecognitionException {
        RVCCalParser.constant_return retval = new RVCCalParser.constant_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal209=null;
        Token string_literal210=null;
        Token INTEGER211=null;
        Token STRING212=null;

        Object string_literal209_tree=null;
        Object string_literal210_tree=null;
        Object INTEGER211_tree=null;
        Object STRING212_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:9: ( 'true' | 'false' | INTEGER | STRING )
            int alt76=4;
            switch ( input.LA(1) ) {
            case 81:
                {
                alt76=1;
                }
                break;
            case 82:
                {
                alt76=2;
                }
                break;
            case INTEGER:
                {
                alt76=3;
                }
                break;
            case STRING:
                {
                alt76=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 76, 0, input);

                throw nvae;
            }

            switch (alt76) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:228:3: 'true'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal209=(Token)match(input,81,FOLLOW_81_in_constant1394); 
                    string_literal209_tree = (Object)adaptor.create(string_literal209);
                    adaptor.addChild(root_0, string_literal209_tree);


                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:3: 'false'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal210=(Token)match(input,82,FOLLOW_82_in_constant1398); 
                    string_literal210_tree = (Object)adaptor.create(string_literal210);
                    adaptor.addChild(root_0, string_literal210_tree);


                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:230:3: INTEGER
                    {
                    root_0 = (Object)adaptor.nil();

                    INTEGER211=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_constant1402); 
                    INTEGER211_tree = (Object)adaptor.create(INTEGER211);
                    adaptor.addChild(root_0, INTEGER211_tree);


                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:231:3: STRING
                    {
                    root_0 = (Object)adaptor.nil();

                    STRING212=(Token)match(input,STRING,FOLLOW_STRING_in_constant1406); 
                    STRING212_tree = (Object)adaptor.create(STRING212);
                    adaptor.addChild(root_0, STRING212_tree);

                     

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:1: expressionGenerator : 'for' typeDef ID 'in' expression ;
    public final RVCCalParser.expressionGenerator_return expressionGenerator() throws RecognitionException {
        RVCCalParser.expressionGenerator_return retval = new RVCCalParser.expressionGenerator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal213=null;
        Token ID215=null;
        Token string_literal216=null;
        RVCCalParser.typeDef_return typeDef214 = null;

        RVCCalParser.expression_return expression217 = null;


        Object string_literal213_tree=null;
        Object ID215_tree=null;
        Object string_literal216_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:20: ( 'for' typeDef ID 'in' expression )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:234:2: 'for' typeDef ID 'in' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal213=(Token)match(input,83,FOLLOW_83_in_expressionGenerator1416); 
            string_literal213_tree = (Object)adaptor.create(string_literal213);
            adaptor.addChild(root_0, string_literal213_tree);

            pushFollow(FOLLOW_typeDef_in_expressionGenerator1418);
            typeDef214=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef214.getTree());
            ID215=(Token)match(input,ID,FOLLOW_ID_in_expressionGenerator1420); 
            ID215_tree = (Object)adaptor.create(ID215);
            adaptor.addChild(root_0, ID215_tree);

            string_literal216=(Token)match(input,84,FOLLOW_84_in_expressionGenerator1422); 
            string_literal216_tree = (Object)adaptor.create(string_literal216);
            adaptor.addChild(root_0, string_literal216_tree);

            pushFollow(FOLLOW_expression_in_expressionGenerator1424);
            expression217=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression217.getTree());
             

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:1: expressionGenerators : expressionGenerator ( ',' expressionGenerator )* ;
    public final RVCCalParser.expressionGenerators_return expressionGenerators() throws RecognitionException {
        RVCCalParser.expressionGenerators_return retval = new RVCCalParser.expressionGenerators_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal219=null;
        RVCCalParser.expressionGenerator_return expressionGenerator218 = null;

        RVCCalParser.expressionGenerator_return expressionGenerator220 = null;


        Object char_literal219_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:21: ( expressionGenerator ( ',' expressionGenerator )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:23: expressionGenerator ( ',' expressionGenerator )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators1434);
            expressionGenerator218=expressionGenerator();

            state._fsp--;

            adaptor.addChild(root_0, expressionGenerator218.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:43: ( ',' expressionGenerator )*
            loop77:
            do {
                int alt77=2;
                int LA77_0 = input.LA(1);

                if ( (LA77_0==35) ) {
                    alt77=1;
                }


                switch (alt77) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:44: ',' expressionGenerator
            	    {
            	    char_literal219=(Token)match(input,35,FOLLOW_35_in_expressionGenerators1437); 
            	    char_literal219_tree = (Object)adaptor.create(char_literal219);
            	    adaptor.addChild(root_0, char_literal219_tree);

            	    pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators1439);
            	    expressionGenerator220=expressionGenerator();

            	    state._fsp--;

            	    adaptor.addChild(root_0, expressionGenerator220.getTree());

            	    }
            	    break;

            	default :
            	    break loop77;
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
    // $ANTLR end "expressionGenerators"

    public static class expressions_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expressions"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:1: expressions : expression ( ',' expression )* ;
    public final RVCCalParser.expressions_return expressions() throws RecognitionException {
        RVCCalParser.expressions_return retval = new RVCCalParser.expressions_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal222=null;
        RVCCalParser.expression_return expression221 = null;

        RVCCalParser.expression_return expression223 = null;


        Object char_literal222_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:12: ( expression ( ',' expression )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:14: expression ( ',' expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_expressions1450);
            expression221=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression221.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:25: ( ',' expression )*
            loop78:
            do {
                int alt78=2;
                int LA78_0 = input.LA(1);

                if ( (LA78_0==35) ) {
                    alt78=1;
                }


                switch (alt78) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:239:26: ',' expression
            	    {
            	    char_literal222=(Token)match(input,35,FOLLOW_35_in_expressions1453); 
            	    char_literal222_tree = (Object)adaptor.create(char_literal222);
            	    adaptor.addChild(root_0, char_literal222_tree);

            	    pushFollow(FOLLOW_expression_in_expressions1455);
            	    expression223=expression();

            	    state._fsp--;

            	    adaptor.addChild(root_0, expression223.getTree());

            	    }
            	    break;

            	default :
            	    break loop78;
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
    // $ANTLR end "expressions"

    public static class idents_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "idents"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:241:1: idents : ID ( ',' ID )* ;
    public final RVCCalParser.idents_return idents() throws RecognitionException {
        RVCCalParser.idents_return retval = new RVCCalParser.idents_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID224=null;
        Token char_literal225=null;
        Token ID226=null;

        Object ID224_tree=null;
        Object char_literal225_tree=null;
        Object ID226_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:7: ( ID ( ',' ID )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:9: ID ( ',' ID )*
            {
            root_0 = (Object)adaptor.nil();

            ID224=(Token)match(input,ID,FOLLOW_ID_in_idents1471); 
            ID224_tree = (Object)adaptor.create(ID224);
            adaptor.addChild(root_0, ID224_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:12: ( ',' ID )*
            loop79:
            do {
                int alt79=2;
                int LA79_0 = input.LA(1);

                if ( (LA79_0==35) ) {
                    alt79=1;
                }


                switch (alt79) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:13: ',' ID
            	    {
            	    char_literal225=(Token)match(input,35,FOLLOW_35_in_idents1474); 
            	    char_literal225_tree = (Object)adaptor.create(char_literal225);
            	    adaptor.addChild(root_0, char_literal225_tree);

            	    ID226=(Token)match(input,ID,FOLLOW_ID_in_idents1476); 
            	    ID226_tree = (Object)adaptor.create(ID226);
            	    adaptor.addChild(root_0, ID226_tree);


            	    }
            	    break;

            	default :
            	    break loop79;
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:246:1: priorityInequality : qualifiedIdent ( '>' qualifiedIdent )+ ';' ;
    public final RVCCalParser.priorityInequality_return priorityInequality() throws RecognitionException {
        RVCCalParser.priorityInequality_return retval = new RVCCalParser.priorityInequality_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal228=null;
        Token char_literal230=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent227 = null;

        RVCCalParser.qualifiedIdent_return qualifiedIdent229 = null;


        Object char_literal228_tree=null;
        Object char_literal230_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:249:19: ( qualifiedIdent ( '>' qualifiedIdent )+ ';' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:249:21: qualifiedIdent ( '>' qualifiedIdent )+ ';'
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality1492);
            qualifiedIdent227=qualifiedIdent();

            state._fsp--;

            adaptor.addChild(root_0, qualifiedIdent227.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:249:36: ( '>' qualifiedIdent )+
            int cnt80=0;
            loop80:
            do {
                int alt80=2;
                int LA80_0 = input.LA(1);

                if ( (LA80_0==64) ) {
                    alt80=1;
                }


                switch (alt80) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:249:37: '>' qualifiedIdent
            	    {
            	    char_literal228=(Token)match(input,64,FOLLOW_64_in_priorityInequality1495); 
            	    char_literal228_tree = (Object)adaptor.create(char_literal228);
            	    adaptor.addChild(root_0, char_literal228_tree);

            	    pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality1497);
            	    qualifiedIdent229=qualifiedIdent();

            	    state._fsp--;

            	    adaptor.addChild(root_0, qualifiedIdent229.getTree());

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

            char_literal230=(Token)match(input,49,FOLLOW_49_in_priorityInequality1501); 
            char_literal230_tree = (Object)adaptor.create(char_literal230);
            adaptor.addChild(root_0, char_literal230_tree);

             

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:1: priorityOrder : 'priority' ( priorityInequality )* 'end' ;
    public final RVCCalParser.priorityOrder_return priorityOrder() throws RecognitionException {
        RVCCalParser.priorityOrder_return retval = new RVCCalParser.priorityOrder_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal231=null;
        Token string_literal233=null;
        RVCCalParser.priorityInequality_return priorityInequality232 = null;


        Object string_literal231_tree=null;
        Object string_literal233_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:14: ( 'priority' ( priorityInequality )* 'end' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:16: 'priority' ( priorityInequality )* 'end'
            {
            root_0 = (Object)adaptor.nil();

            string_literal231=(Token)match(input,85,FOLLOW_85_in_priorityOrder1511); 
            string_literal231_tree = (Object)adaptor.create(string_literal231);
            adaptor.addChild(root_0, string_literal231_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:27: ( priorityInequality )*
            loop81:
            do {
                int alt81=2;
                int LA81_0 = input.LA(1);

                if ( (LA81_0==ID) ) {
                    alt81=1;
                }


                switch (alt81) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:28: priorityInequality
            	    {
            	    pushFollow(FOLLOW_priorityInequality_in_priorityOrder1514);
            	    priorityInequality232=priorityInequality();

            	    state._fsp--;

            	    adaptor.addChild(root_0, priorityInequality232.getTree());

            	    }
            	    break;

            	default :
            	    break loop81;
                }
            } while (true);

            string_literal233=(Token)match(input,42,FOLLOW_42_in_priorityOrder1518); 
            string_literal233_tree = (Object)adaptor.create(string_literal233);
            adaptor.addChild(root_0, string_literal233_tree);

             

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:253:1: qualifiedIdent : ID ( '.' ID )* ;
    public final RVCCalParser.qualifiedIdent_return qualifiedIdent() throws RecognitionException {
        RVCCalParser.qualifiedIdent_return retval = new RVCCalParser.qualifiedIdent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID234=null;
        Token char_literal235=null;
        Token ID236=null;

        Object ID234_tree=null;
        Object char_literal235_tree=null;
        Object ID236_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:15: ( ID ( '.' ID )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:17: ID ( '.' ID )*
            {
            root_0 = (Object)adaptor.nil();

            ID234=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent1532); 
            ID234_tree = (Object)adaptor.create(ID234);
            adaptor.addChild(root_0, ID234_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:20: ( '.' ID )*
            loop82:
            do {
                int alt82=2;
                int LA82_0 = input.LA(1);

                if ( (LA82_0==43) ) {
                    alt82=1;
                }


                switch (alt82) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:256:21: '.' ID
            	    {
            	    char_literal235=(Token)match(input,43,FOLLOW_43_in_qualifiedIdent1535); 
            	    char_literal235_tree = (Object)adaptor.create(char_literal235);
            	    adaptor.addChild(root_0, char_literal235_tree);

            	    ID236=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent1537); 
            	    ID236_tree = (Object)adaptor.create(ID236);
            	    adaptor.addChild(root_0, ID236_tree);


            	    }
            	    break;

            	default :
            	    break loop82;
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:258:1: schedule : 'schedule' ( 'fsm' ID ':' ( stateTransition )* 'end' | 'regexp' ) ;
    public final RVCCalParser.schedule_return schedule() throws RecognitionException {
        RVCCalParser.schedule_return retval = new RVCCalParser.schedule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal237=null;
        Token string_literal238=null;
        Token ID239=null;
        Token char_literal240=null;
        Token string_literal242=null;
        Token string_literal243=null;
        RVCCalParser.stateTransition_return stateTransition241 = null;


        Object string_literal237_tree=null;
        Object string_literal238_tree=null;
        Object ID239_tree=null;
        Object char_literal240_tree=null;
        Object string_literal242_tree=null;
        Object string_literal243_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:261:9: ( 'schedule' ( 'fsm' ID ':' ( stateTransition )* 'end' | 'regexp' ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:262:3: 'schedule' ( 'fsm' ID ':' ( stateTransition )* 'end' | 'regexp' )
            {
            root_0 = (Object)adaptor.nil();

            string_literal237=(Token)match(input,86,FOLLOW_86_in_schedule1555); 
            string_literal237_tree = (Object)adaptor.create(string_literal237);
            adaptor.addChild(root_0, string_literal237_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:263:5: ( 'fsm' ID ':' ( stateTransition )* 'end' | 'regexp' )
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==87) ) {
                alt84=1;
            }
            else if ( (LA84_0==88) ) {
                alt84=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 84, 0, input);

                throw nvae;
            }
            switch (alt84) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:263:6: 'fsm' ID ':' ( stateTransition )* 'end'
                    {
                    string_literal238=(Token)match(input,87,FOLLOW_87_in_schedule1562); 
                    string_literal238_tree = (Object)adaptor.create(string_literal238);
                    adaptor.addChild(root_0, string_literal238_tree);

                    ID239=(Token)match(input,ID,FOLLOW_ID_in_schedule1564); 
                    ID239_tree = (Object)adaptor.create(ID239);
                    adaptor.addChild(root_0, ID239_tree);

                    char_literal240=(Token)match(input,32,FOLLOW_32_in_schedule1566); 
                    char_literal240_tree = (Object)adaptor.create(char_literal240);
                    adaptor.addChild(root_0, char_literal240_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:263:19: ( stateTransition )*
                    loop83:
                    do {
                        int alt83=2;
                        int LA83_0 = input.LA(1);

                        if ( (LA83_0==ID) ) {
                            alt83=1;
                        }


                        switch (alt83) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:263:19: stateTransition
                    	    {
                    	    pushFollow(FOLLOW_stateTransition_in_schedule1568);
                    	    stateTransition241=stateTransition();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, stateTransition241.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop83;
                        }
                    } while (true);

                    string_literal242=(Token)match(input,42,FOLLOW_42_in_schedule1571); 
                    string_literal242_tree = (Object)adaptor.create(string_literal242);
                    adaptor.addChild(root_0, string_literal242_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:264:7: 'regexp'
                    {
                    string_literal243=(Token)match(input,88,FOLLOW_88_in_schedule1581); 
                    string_literal243_tree = (Object)adaptor.create(string_literal243);
                    adaptor.addChild(root_0, string_literal243_tree);

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:1: stateTransition : ID '(' qualifiedIdent ')' '-->' ID ';' ;
    public final RVCCalParser.stateTransition_return stateTransition() throws RecognitionException {
        RVCCalParser.stateTransition_return retval = new RVCCalParser.stateTransition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID244=null;
        Token char_literal245=null;
        Token char_literal247=null;
        Token string_literal248=null;
        Token ID249=null;
        Token char_literal250=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent246 = null;


        Object ID244_tree=null;
        Object char_literal245_tree=null;
        Object char_literal247_tree=null;
        Object string_literal248_tree=null;
        Object ID249_tree=null;
        Object char_literal250_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:16: ( ID '(' qualifiedIdent ')' '-->' ID ';' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:267:2: ID '(' qualifiedIdent ')' '-->' ID ';'
            {
            root_0 = (Object)adaptor.nil();

            ID244=(Token)match(input,ID,FOLLOW_ID_in_stateTransition1593); 
            ID244_tree = (Object)adaptor.create(ID244);
            adaptor.addChild(root_0, ID244_tree);

            char_literal245=(Token)match(input,39,FOLLOW_39_in_stateTransition1595); 
            char_literal245_tree = (Object)adaptor.create(char_literal245);
            adaptor.addChild(root_0, char_literal245_tree);

            pushFollow(FOLLOW_qualifiedIdent_in_stateTransition1597);
            qualifiedIdent246=qualifiedIdent();

            state._fsp--;

            adaptor.addChild(root_0, qualifiedIdent246.getTree());
            char_literal247=(Token)match(input,40,FOLLOW_40_in_stateTransition1599); 
            char_literal247_tree = (Object)adaptor.create(char_literal247);
            adaptor.addChild(root_0, char_literal247_tree);

            string_literal248=(Token)match(input,51,FOLLOW_51_in_stateTransition1601); 
            string_literal248_tree = (Object)adaptor.create(string_literal248);
            adaptor.addChild(root_0, string_literal248_tree);

            ID249=(Token)match(input,ID,FOLLOW_ID_in_stateTransition1603); 
            ID249_tree = (Object)adaptor.create(ID249);
            adaptor.addChild(root_0, ID249_tree);

            char_literal250=(Token)match(input,49,FOLLOW_49_in_stateTransition1605); 
            char_literal250_tree = (Object)adaptor.create(char_literal250);
            adaptor.addChild(root_0, char_literal250_tree);

             

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:269:1: statement : ( 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end' | 'choose' | 'for' | 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end' | 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end' | 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end' | ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' ) );
    public final RVCCalParser.statement_return statement() throws RecognitionException {
        RVCCalParser.statement_return retval = new RVCCalParser.statement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal251=null;
        Token string_literal252=null;
        Token string_literal254=null;
        Token string_literal256=null;
        Token string_literal257=null;
        Token string_literal258=null;
        Token string_literal259=null;
        Token string_literal261=null;
        Token string_literal263=null;
        Token string_literal265=null;
        Token string_literal267=null;
        Token string_literal269=null;
        Token string_literal270=null;
        Token string_literal272=null;
        Token string_literal274=null;
        Token string_literal276=null;
        Token string_literal277=null;
        Token string_literal279=null;
        Token string_literal281=null;
        Token string_literal283=null;
        Token ID284=null;
        Token char_literal285=null;
        Token char_literal287=null;
        Token string_literal288=null;
        Token char_literal290=null;
        Token char_literal291=null;
        Token char_literal293=null;
        Token char_literal294=null;
        RVCCalParser.varDecls_return varDecls253 = null;

        RVCCalParser.statement_return statement255 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr260 = null;

        RVCCalParser.expression_return expression262 = null;

        RVCCalParser.expression_return expression264 = null;

        RVCCalParser.varDecls_return varDecls266 = null;

        RVCCalParser.statement_return statement268 = null;

        RVCCalParser.expression_return expression271 = null;

        RVCCalParser.statement_return statement273 = null;

        RVCCalParser.statement_return statement275 = null;

        RVCCalParser.expression_return expression278 = null;

        RVCCalParser.varDecls_return varDecls280 = null;

        RVCCalParser.statement_return statement282 = null;

        RVCCalParser.expressions_return expressions286 = null;

        RVCCalParser.expression_return expression289 = null;

        RVCCalParser.expressions_return expressions292 = null;


        Object string_literal251_tree=null;
        Object string_literal252_tree=null;
        Object string_literal254_tree=null;
        Object string_literal256_tree=null;
        Object string_literal257_tree=null;
        Object string_literal258_tree=null;
        Object string_literal259_tree=null;
        Object string_literal261_tree=null;
        Object string_literal263_tree=null;
        Object string_literal265_tree=null;
        Object string_literal267_tree=null;
        Object string_literal269_tree=null;
        Object string_literal270_tree=null;
        Object string_literal272_tree=null;
        Object string_literal274_tree=null;
        Object string_literal276_tree=null;
        Object string_literal277_tree=null;
        Object string_literal279_tree=null;
        Object string_literal281_tree=null;
        Object string_literal283_tree=null;
        Object ID284_tree=null;
        Object char_literal285_tree=null;
        Object char_literal287_tree=null;
        Object string_literal288_tree=null;
        Object char_literal290_tree=null;
        Object char_literal291_tree=null;
        Object char_literal293_tree=null;
        Object char_literal294_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:272:10: ( 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end' | 'choose' | 'for' | 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end' | 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end' | 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end' | ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' ) )
            int alt98=7;
            switch ( input.LA(1) ) {
            case 53:
                {
                alt98=1;
                }
                break;
            case 89:
                {
                alt98=2;
                }
                break;
            case 83:
                {
                alt98=3;
                }
                break;
            case 90:
                {
                alt98=4;
                }
                break;
            case 78:
                {
                alt98=5;
                }
                break;
            case 92:
                {
                alt98=6;
                }
                break;
            case ID:
                {
                alt98=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 98, 0, input);

                throw nvae;
            }

            switch (alt98) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:273:3: 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal251=(Token)match(input,53,FOLLOW_53_in_statement1621); 
                    string_literal251_tree = (Object)adaptor.create(string_literal251);
                    adaptor.addChild(root_0, string_literal251_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:273:11: ( 'var' varDecls 'do' )?
                    int alt85=2;
                    int LA85_0 = input.LA(1);

                    if ( (LA85_0==45) ) {
                        alt85=1;
                    }
                    switch (alt85) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:273:12: 'var' varDecls 'do'
                            {
                            string_literal252=(Token)match(input,45,FOLLOW_45_in_statement1624); 
                            string_literal252_tree = (Object)adaptor.create(string_literal252);
                            adaptor.addChild(root_0, string_literal252_tree);

                            pushFollow(FOLLOW_varDecls_in_statement1626);
                            varDecls253=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls253.getTree());
                            string_literal254=(Token)match(input,37,FOLLOW_37_in_statement1628); 
                            string_literal254_tree = (Object)adaptor.create(string_literal254);
                            adaptor.addChild(root_0, string_literal254_tree);


                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:273:34: ( statement )*
                    loop86:
                    do {
                        int alt86=2;
                        int LA86_0 = input.LA(1);

                        if ( (LA86_0==ID||LA86_0==53||LA86_0==78||LA86_0==83||(LA86_0>=89 && LA86_0<=90)||LA86_0==92) ) {
                            alt86=1;
                        }


                        switch (alt86) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:273:34: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement1632);
                    	    statement255=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement255.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop86;
                        }
                    } while (true);

                    string_literal256=(Token)match(input,42,FOLLOW_42_in_statement1635); 
                    string_literal256_tree = (Object)adaptor.create(string_literal256);
                    adaptor.addChild(root_0, string_literal256_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:274:3: 'choose'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal257=(Token)match(input,89,FOLLOW_89_in_statement1641); 
                    string_literal257_tree = (Object)adaptor.create(string_literal257);
                    adaptor.addChild(root_0, string_literal257_tree);

                     System.out.println("RVC-CAL does not support the \"choose\" statement."); 

                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:275:3: 'for'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal258=(Token)match(input,83,FOLLOW_83_in_statement1647); 
                    string_literal258_tree = (Object)adaptor.create(string_literal258);
                    adaptor.addChild(root_0, string_literal258_tree);

                     System.out.println("RVC-CAL does not support the \"for\" statement, please use \"foreach\" instead."); 

                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:3: 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal259=(Token)match(input,90,FOLLOW_90_in_statement1653); 
                    string_literal259_tree = (Object)adaptor.create(string_literal259);
                    adaptor.addChild(root_0, string_literal259_tree);

                    pushFollow(FOLLOW_varDeclNoExpr_in_statement1655);
                    varDeclNoExpr260=varDeclNoExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, varDeclNoExpr260.getTree());
                    string_literal261=(Token)match(input,84,FOLLOW_84_in_statement1657); 
                    string_literal261_tree = (Object)adaptor.create(string_literal261);
                    adaptor.addChild(root_0, string_literal261_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:32: ( expression ( '..' expression )? )
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:33: expression ( '..' expression )?
                    {
                    pushFollow(FOLLOW_expression_in_statement1660);
                    expression262=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression262.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:44: ( '..' expression )?
                    int alt87=2;
                    int LA87_0 = input.LA(1);

                    if ( (LA87_0==91) ) {
                        alt87=1;
                    }
                    switch (alt87) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:45: '..' expression
                            {
                            string_literal263=(Token)match(input,91,FOLLOW_91_in_statement1663); 
                            string_literal263_tree = (Object)adaptor.create(string_literal263);
                            adaptor.addChild(root_0, string_literal263_tree);

                            pushFollow(FOLLOW_expression_in_statement1665);
                            expression264=expression();

                            state._fsp--;

                            adaptor.addChild(root_0, expression264.getTree());

                            }
                            break;

                    }


                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:64: ( 'var' varDecls )?
                    int alt88=2;
                    int LA88_0 = input.LA(1);

                    if ( (LA88_0==45) ) {
                        alt88=1;
                    }
                    switch (alt88) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:65: 'var' varDecls
                            {
                            string_literal265=(Token)match(input,45,FOLLOW_45_in_statement1671); 
                            string_literal265_tree = (Object)adaptor.create(string_literal265);
                            adaptor.addChild(root_0, string_literal265_tree);

                            pushFollow(FOLLOW_varDecls_in_statement1673);
                            varDecls266=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls266.getTree());

                            }
                            break;

                    }

                    string_literal267=(Token)match(input,37,FOLLOW_37_in_statement1677); 
                    string_literal267_tree = (Object)adaptor.create(string_literal267);
                    adaptor.addChild(root_0, string_literal267_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:87: ( statement )*
                    loop89:
                    do {
                        int alt89=2;
                        int LA89_0 = input.LA(1);

                        if ( (LA89_0==ID||LA89_0==53||LA89_0==78||LA89_0==83||(LA89_0>=89 && LA89_0<=90)||LA89_0==92) ) {
                            alt89=1;
                        }


                        switch (alt89) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:276:87: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement1679);
                    	    statement268=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement268.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop89;
                        }
                    } while (true);

                    string_literal269=(Token)match(input,42,FOLLOW_42_in_statement1682); 
                    string_literal269_tree = (Object)adaptor.create(string_literal269);
                    adaptor.addChild(root_0, string_literal269_tree);

                     

                    }
                    break;
                case 5 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:3: 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal270=(Token)match(input,78,FOLLOW_78_in_statement1688); 
                    string_literal270_tree = (Object)adaptor.create(string_literal270);
                    adaptor.addChild(root_0, string_literal270_tree);

                    pushFollow(FOLLOW_expression_in_statement1690);
                    expression271=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression271.getTree());
                    string_literal272=(Token)match(input,79,FOLLOW_79_in_statement1692); 
                    string_literal272_tree = (Object)adaptor.create(string_literal272);
                    adaptor.addChild(root_0, string_literal272_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:26: ( statement )*
                    loop90:
                    do {
                        int alt90=2;
                        int LA90_0 = input.LA(1);

                        if ( (LA90_0==ID||LA90_0==53||LA90_0==78||LA90_0==83||(LA90_0>=89 && LA90_0<=90)||LA90_0==92) ) {
                            alt90=1;
                        }


                        switch (alt90) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:26: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement1694);
                    	    statement273=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement273.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop90;
                        }
                    } while (true);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:37: ( 'else' ( statement )* )?
                    int alt92=2;
                    int LA92_0 = input.LA(1);

                    if ( (LA92_0==80) ) {
                        alt92=1;
                    }
                    switch (alt92) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:38: 'else' ( statement )*
                            {
                            string_literal274=(Token)match(input,80,FOLLOW_80_in_statement1698); 
                            string_literal274_tree = (Object)adaptor.create(string_literal274);
                            adaptor.addChild(root_0, string_literal274_tree);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:45: ( statement )*
                            loop91:
                            do {
                                int alt91=2;
                                int LA91_0 = input.LA(1);

                                if ( (LA91_0==ID||LA91_0==53||LA91_0==78||LA91_0==83||(LA91_0>=89 && LA91_0<=90)||LA91_0==92) ) {
                                    alt91=1;
                                }


                                switch (alt91) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:45: statement
                            	    {
                            	    pushFollow(FOLLOW_statement_in_statement1700);
                            	    statement275=statement();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, statement275.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop91;
                                }
                            } while (true);


                            }
                            break;

                    }

                    string_literal276=(Token)match(input,42,FOLLOW_42_in_statement1705); 
                    string_literal276_tree = (Object)adaptor.create(string_literal276);
                    adaptor.addChild(root_0, string_literal276_tree);

                      

                    }
                    break;
                case 6 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:278:3: 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal277=(Token)match(input,92,FOLLOW_92_in_statement1711); 
                    string_literal277_tree = (Object)adaptor.create(string_literal277);
                    adaptor.addChild(root_0, string_literal277_tree);

                    pushFollow(FOLLOW_expression_in_statement1713);
                    expression278=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression278.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:278:22: ( 'var' varDecls )?
                    int alt93=2;
                    int LA93_0 = input.LA(1);

                    if ( (LA93_0==45) ) {
                        alt93=1;
                    }
                    switch (alt93) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:278:23: 'var' varDecls
                            {
                            string_literal279=(Token)match(input,45,FOLLOW_45_in_statement1716); 
                            string_literal279_tree = (Object)adaptor.create(string_literal279);
                            adaptor.addChild(root_0, string_literal279_tree);

                            pushFollow(FOLLOW_varDecls_in_statement1718);
                            varDecls280=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls280.getTree());

                            }
                            break;

                    }

                    string_literal281=(Token)match(input,37,FOLLOW_37_in_statement1722); 
                    string_literal281_tree = (Object)adaptor.create(string_literal281);
                    adaptor.addChild(root_0, string_literal281_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:278:45: ( statement )*
                    loop94:
                    do {
                        int alt94=2;
                        int LA94_0 = input.LA(1);

                        if ( (LA94_0==ID||LA94_0==53||LA94_0==78||LA94_0==83||(LA94_0>=89 && LA94_0<=90)||LA94_0==92) ) {
                            alt94=1;
                        }


                        switch (alt94) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:278:45: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement1724);
                    	    statement282=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement282.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop94;
                        }
                    } while (true);

                    string_literal283=(Token)match(input,42,FOLLOW_42_in_statement1727); 
                    string_literal283_tree = (Object)adaptor.create(string_literal283);
                    adaptor.addChild(root_0, string_literal283_tree);

                      

                    }
                    break;
                case 7 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:280:3: ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' )
                    {
                    root_0 = (Object)adaptor.nil();

                    ID284=(Token)match(input,ID,FOLLOW_ID_in_statement1734); 
                    ID284_tree = (Object)adaptor.create(ID284);
                    adaptor.addChild(root_0, ID284_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:280:6: ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' )
                    int alt97=2;
                    int LA97_0 = input.LA(1);

                    if ( (LA97_0==33||LA97_0==48) ) {
                        alt97=1;
                    }
                    else if ( (LA97_0==39) ) {
                        alt97=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 97, 0, input);

                        throw nvae;
                    }
                    switch (alt97) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:281:5: ( ( '[' expressions ']' )? ':=' expression ';' )
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:281:5: ( ( '[' expressions ']' )? ':=' expression ';' )
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:281:6: ( '[' expressions ']' )? ':=' expression ';'
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:281:6: ( '[' expressions ']' )?
                            int alt95=2;
                            int LA95_0 = input.LA(1);

                            if ( (LA95_0==33) ) {
                                alt95=1;
                            }
                            switch (alt95) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:281:7: '[' expressions ']'
                                    {
                                    char_literal285=(Token)match(input,33,FOLLOW_33_in_statement1744); 
                                    char_literal285_tree = (Object)adaptor.create(char_literal285);
                                    adaptor.addChild(root_0, char_literal285_tree);

                                    pushFollow(FOLLOW_expressions_in_statement1746);
                                    expressions286=expressions();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expressions286.getTree());
                                    char_literal287=(Token)match(input,34,FOLLOW_34_in_statement1748); 
                                    char_literal287_tree = (Object)adaptor.create(char_literal287);
                                    adaptor.addChild(root_0, char_literal287_tree);


                                    }
                                    break;

                            }

                            string_literal288=(Token)match(input,48,FOLLOW_48_in_statement1752); 
                            string_literal288_tree = (Object)adaptor.create(string_literal288);
                            adaptor.addChild(root_0, string_literal288_tree);

                            pushFollow(FOLLOW_expression_in_statement1754);
                            expression289=expression();

                            state._fsp--;

                            adaptor.addChild(root_0, expression289.getTree());
                            char_literal290=(Token)match(input,49,FOLLOW_49_in_statement1756); 
                            char_literal290_tree = (Object)adaptor.create(char_literal290);
                            adaptor.addChild(root_0, char_literal290_tree);


                            }

                             

                            }
                            break;
                        case 2 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:282:6: '(' ( expressions )? ')' ';'
                            {
                            char_literal291=(Token)match(input,39,FOLLOW_39_in_statement1766); 
                            char_literal291_tree = (Object)adaptor.create(char_literal291);
                            adaptor.addChild(root_0, char_literal291_tree);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:282:10: ( expressions )?
                            int alt96=2;
                            int LA96_0 = input.LA(1);

                            if ( ((LA96_0>=ID && LA96_0<=STRING)||LA96_0==33||LA96_0==39||LA96_0==70||(LA96_0>=76 && LA96_0<=78)||(LA96_0>=81 && LA96_0<=82)) ) {
                                alt96=1;
                            }
                            switch (alt96) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:282:10: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_statement1768);
                                    expressions292=expressions();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expressions292.getTree());

                                    }
                                    break;

                            }

                            char_literal293=(Token)match(input,40,FOLLOW_40_in_statement1771); 
                            char_literal293_tree = (Object)adaptor.create(char_literal293);
                            adaptor.addChild(root_0, char_literal293_tree);

                            char_literal294=(Token)match(input,49,FOLLOW_49_in_statement1773); 
                            char_literal294_tree = (Object)adaptor.create(char_literal294);
                            adaptor.addChild(root_0, char_literal294_tree);

                             

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:284:1: typeAttr : ( 'type' ':' typeDef -> ^( TYPE typeDef ) | 'size' '=' expression -> ^( EXPR expression ) );
    public final RVCCalParser.typeAttr_return typeAttr() throws RecognitionException {
        RVCCalParser.typeAttr_return retval = new RVCCalParser.typeAttr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal295=null;
        Token char_literal296=null;
        Token string_literal298=null;
        Token char_literal299=null;
        RVCCalParser.typeDef_return typeDef297 = null;

        RVCCalParser.expression_return expression300 = null;


        Object string_literal295_tree=null;
        Object char_literal296_tree=null;
        Object string_literal298_tree=null;
        Object char_literal299_tree=null;
        RewriteRuleTokenStream stream_94=new RewriteRuleTokenStream(adaptor,"token 94");
        RewriteRuleTokenStream stream_93=new RewriteRuleTokenStream(adaptor,"token 93");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_32=new RewriteRuleTokenStream(adaptor,"token 32");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:9: ( 'type' ':' typeDef -> ^( TYPE typeDef ) | 'size' '=' expression -> ^( EXPR expression ) )
            int alt99=2;
            int LA99_0 = input.LA(1);

            if ( (LA99_0==93) ) {
                alt99=1;
            }
            else if ( (LA99_0==94) ) {
                alt99=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 99, 0, input);

                throw nvae;
            }
            switch (alt99) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:11: 'type' ':' typeDef
                    {
                    string_literal295=(Token)match(input,93,FOLLOW_93_in_typeAttr1791);  
                    stream_93.add(string_literal295);

                    char_literal296=(Token)match(input,32,FOLLOW_32_in_typeAttr1793);  
                    stream_32.add(char_literal296);

                    pushFollow(FOLLOW_typeDef_in_typeAttr1795);
                    typeDef297=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef297.getTree());


                    // AST REWRITE
                    // elements: typeDef
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 288:30: -> ^( TYPE typeDef )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:288:33: ^( TYPE typeDef )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_1);

                        adaptor.addChild(root_1, stream_typeDef.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:289:3: 'size' '=' expression
                    {
                    string_literal298=(Token)match(input,94,FOLLOW_94_in_typeAttr1807);  
                    stream_94.add(string_literal298);

                    char_literal299=(Token)match(input,47,FOLLOW_47_in_typeAttr1809);  
                    stream_47.add(char_literal299);

                    pushFollow(FOLLOW_expression_in_typeAttr1811);
                    expression300=expression();

                    state._fsp--;

                    stream_expression.add(expression300.getTree());


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
                    // 289:25: -> ^( EXPR expression )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:289:28: ^( EXPR expression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXPR, "EXPR"), root_1);

                        adaptor.addChild(root_1, stream_expression.nextTree());

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
    // $ANTLR end "typeAttr"

    public static class typeAttrs_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typeAttrs"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:291:1: typeAttrs : typeAttr ( ',' typeAttr )* -> ( typeAttr )+ ;
    public final RVCCalParser.typeAttrs_return typeAttrs() throws RecognitionException {
        RVCCalParser.typeAttrs_return retval = new RVCCalParser.typeAttrs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal302=null;
        RVCCalParser.typeAttr_return typeAttr301 = null;

        RVCCalParser.typeAttr_return typeAttr303 = null;


        Object char_literal302_tree=null;
        RewriteRuleTokenStream stream_35=new RewriteRuleTokenStream(adaptor,"token 35");
        RewriteRuleSubtreeStream stream_typeAttr=new RewriteRuleSubtreeStream(adaptor,"rule typeAttr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:291:10: ( typeAttr ( ',' typeAttr )* -> ( typeAttr )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:291:12: typeAttr ( ',' typeAttr )*
            {
            pushFollow(FOLLOW_typeAttr_in_typeAttrs1827);
            typeAttr301=typeAttr();

            state._fsp--;

            stream_typeAttr.add(typeAttr301.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:291:21: ( ',' typeAttr )*
            loop100:
            do {
                int alt100=2;
                int LA100_0 = input.LA(1);

                if ( (LA100_0==35) ) {
                    alt100=1;
                }


                switch (alt100) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:291:22: ',' typeAttr
            	    {
            	    char_literal302=(Token)match(input,35,FOLLOW_35_in_typeAttrs1830);  
            	    stream_35.add(char_literal302);

            	    pushFollow(FOLLOW_typeAttr_in_typeAttrs1832);
            	    typeAttr303=typeAttr();

            	    state._fsp--;

            	    stream_typeAttr.add(typeAttr303.getTree());

            	    }
            	    break;

            	default :
            	    break loop100;
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
            // 291:37: -> ( typeAttr )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:294:1: typeDef : ID ( '[' typePars ']' | '(' attrs= typeAttrs ')' )? -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) ;
    public final RVCCalParser.typeDef_return typeDef() throws RecognitionException {
        RVCCalParser.typeDef_return retval = new RVCCalParser.typeDef_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID304=null;
        Token char_literal305=null;
        Token char_literal307=null;
        Token char_literal308=null;
        Token char_literal309=null;
        RVCCalParser.typeAttrs_return attrs = null;

        RVCCalParser.typePars_return typePars306 = null;


        Object ID304_tree=null;
        Object char_literal305_tree=null;
        Object char_literal307_tree=null;
        Object char_literal308_tree=null;
        Object char_literal309_tree=null;
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_33=new RewriteRuleTokenStream(adaptor,"token 33");
        RewriteRuleTokenStream stream_34=new RewriteRuleTokenStream(adaptor,"token 34");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleSubtreeStream stream_typePars=new RewriteRuleSubtreeStream(adaptor,"rule typePars");
        RewriteRuleSubtreeStream stream_typeAttrs=new RewriteRuleSubtreeStream(adaptor,"rule typeAttrs");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:294:8: ( ID ( '[' typePars ']' | '(' attrs= typeAttrs ')' )? -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:294:10: ID ( '[' typePars ']' | '(' attrs= typeAttrs ')' )?
            {
            ID304=(Token)match(input,ID,FOLLOW_ID_in_typeDef1849);  
            stream_ID.add(ID304);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:295:3: ( '[' typePars ']' | '(' attrs= typeAttrs ')' )?
            int alt101=3;
            int LA101_0 = input.LA(1);

            if ( (LA101_0==33) ) {
                alt101=1;
            }
            else if ( (LA101_0==39) ) {
                alt101=2;
            }
            switch (alt101) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:295:4: '[' typePars ']'
                    {
                    char_literal305=(Token)match(input,33,FOLLOW_33_in_typeDef1854);  
                    stream_33.add(char_literal305);

                    pushFollow(FOLLOW_typePars_in_typeDef1856);
                    typePars306=typePars();

                    state._fsp--;

                    stream_typePars.add(typePars306.getTree());
                    char_literal307=(Token)match(input,34,FOLLOW_34_in_typeDef1858);  
                    stream_34.add(char_literal307);


                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:296:5: '(' attrs= typeAttrs ')'
                    {
                    char_literal308=(Token)match(input,39,FOLLOW_39_in_typeDef1864);  
                    stream_39.add(char_literal308);

                    pushFollow(FOLLOW_typeAttrs_in_typeDef1868);
                    attrs=typeAttrs();

                    state._fsp--;

                    stream_typeAttrs.add(attrs.getTree());
                    char_literal309=(Token)match(input,40,FOLLOW_40_in_typeDef1870);  
                    stream_40.add(char_literal309);


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
            // 296:31: -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:296:34: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:296:44: ^( TYPE_ATTRS ( $attrs)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_2);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:296:57: ( $attrs)?
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:298:1: typePar : ID ( '<' typeDef )? ;
    public final RVCCalParser.typePar_return typePar() throws RecognitionException {
        RVCCalParser.typePar_return retval = new RVCCalParser.typePar_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID310=null;
        Token char_literal311=null;
        RVCCalParser.typeDef_return typeDef312 = null;


        Object ID310_tree=null;
        Object char_literal311_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:301:8: ( ID ( '<' typeDef )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:301:10: ID ( '<' typeDef )?
            {
            root_0 = (Object)adaptor.nil();

            ID310=(Token)match(input,ID,FOLLOW_ID_in_typePar1900); 
            ID310_tree = (Object)adaptor.create(ID310);
            adaptor.addChild(root_0, ID310_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:301:13: ( '<' typeDef )?
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( (LA102_0==63) ) {
                alt102=1;
            }
            switch (alt102) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:301:14: '<' typeDef
                    {
                    char_literal311=(Token)match(input,63,FOLLOW_63_in_typePar1903); 
                    char_literal311_tree = (Object)adaptor.create(char_literal311);
                    adaptor.addChild(root_0, char_literal311_tree);

                    pushFollow(FOLLOW_typeDef_in_typePar1905);
                    typeDef312=typeDef();

                    state._fsp--;

                    adaptor.addChild(root_0, typeDef312.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:303:1: typePars : typePar ( ',' typePar )* -> ( typePar )+ ;
    public final RVCCalParser.typePars_return typePars() throws RecognitionException {
        RVCCalParser.typePars_return retval = new RVCCalParser.typePars_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal314=null;
        RVCCalParser.typePar_return typePar313 = null;

        RVCCalParser.typePar_return typePar315 = null;


        Object char_literal314_tree=null;
        RewriteRuleTokenStream stream_35=new RewriteRuleTokenStream(adaptor,"token 35");
        RewriteRuleSubtreeStream stream_typePar=new RewriteRuleSubtreeStream(adaptor,"rule typePar");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:303:9: ( typePar ( ',' typePar )* -> ( typePar )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:303:11: typePar ( ',' typePar )*
            {
            pushFollow(FOLLOW_typePar_in_typePars1916);
            typePar313=typePar();

            state._fsp--;

            stream_typePar.add(typePar313.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:303:19: ( ',' typePar )*
            loop103:
            do {
                int alt103=2;
                int LA103_0 = input.LA(1);

                if ( (LA103_0==35) ) {
                    alt103=1;
                }


                switch (alt103) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:303:20: ',' typePar
            	    {
            	    char_literal314=(Token)match(input,35,FOLLOW_35_in_typePars1919);  
            	    stream_35.add(char_literal314);

            	    pushFollow(FOLLOW_typePar_in_typePars1921);
            	    typePar315=typePar();

            	    state._fsp--;

            	    stream_typePar.add(typePar315.getTree());

            	    }
            	    break;

            	default :
            	    break loop103;
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
            // 303:34: -> ( typePar )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:305:1: varDecl : typeDef ID ( '=' expression | ':=' expression )? ;
    public final RVCCalParser.varDecl_return varDecl() throws RecognitionException {
        RVCCalParser.varDecl_return retval = new RVCCalParser.varDecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID317=null;
        Token char_literal318=null;
        Token string_literal320=null;
        RVCCalParser.typeDef_return typeDef316 = null;

        RVCCalParser.expression_return expression319 = null;

        RVCCalParser.expression_return expression321 = null;


        Object ID317_tree=null;
        Object char_literal318_tree=null;
        Object string_literal320_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:8: ( typeDef ID ( '=' expression | ':=' expression )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:10: typeDef ID ( '=' expression | ':=' expression )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_typeDef_in_varDecl1942);
            typeDef316=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef316.getTree());
            ID317=(Token)match(input,ID,FOLLOW_ID_in_varDecl1944); 
            ID317_tree = (Object)adaptor.create(ID317);
            adaptor.addChild(root_0, ID317_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:21: ( '=' expression | ':=' expression )?
            int alt104=3;
            int LA104_0 = input.LA(1);

            if ( (LA104_0==47) ) {
                alt104=1;
            }
            else if ( (LA104_0==48) ) {
                alt104=2;
            }
            switch (alt104) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:22: '=' expression
                    {
                    char_literal318=(Token)match(input,47,FOLLOW_47_in_varDecl1947); 
                    char_literal318_tree = (Object)adaptor.create(char_literal318);
                    adaptor.addChild(root_0, char_literal318_tree);

                    pushFollow(FOLLOW_expression_in_varDecl1949);
                    expression319=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression319.getTree());

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:309:39: ':=' expression
                    {
                    string_literal320=(Token)match(input,48,FOLLOW_48_in_varDecl1953); 
                    string_literal320_tree = (Object)adaptor.create(string_literal320);
                    adaptor.addChild(root_0, string_literal320_tree);

                    pushFollow(FOLLOW_expression_in_varDecl1955);
                    expression321=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression321.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:311:1: varDeclNoExpr : typeDef ID ;
    public final RVCCalParser.varDeclNoExpr_return varDeclNoExpr() throws RecognitionException {
        RVCCalParser.varDeclNoExpr_return retval = new RVCCalParser.varDeclNoExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID323=null;
        RVCCalParser.typeDef_return typeDef322 = null;


        Object ID323_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:311:14: ( typeDef ID )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:311:16: typeDef ID
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_typeDef_in_varDeclNoExpr1966);
            typeDef322=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef322.getTree());
            ID323=(Token)match(input,ID,FOLLOW_ID_in_varDeclNoExpr1968); 
            ID323_tree = (Object)adaptor.create(ID323);
            adaptor.addChild(root_0, ID323_tree);

             

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:313:1: varDecls : varDecl ( ',' varDecl )* ;
    public final RVCCalParser.varDecls_return varDecls() throws RecognitionException {
        RVCCalParser.varDecls_return retval = new RVCCalParser.varDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal325=null;
        RVCCalParser.varDecl_return varDecl324 = null;

        RVCCalParser.varDecl_return varDecl326 = null;


        Object char_literal325_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:313:9: ( varDecl ( ',' varDecl )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:313:11: varDecl ( ',' varDecl )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_varDecl_in_varDecls1977);
            varDecl324=varDecl();

            state._fsp--;

            adaptor.addChild(root_0, varDecl324.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:313:19: ( ',' varDecl )*
            loop105:
            do {
                int alt105=2;
                int LA105_0 = input.LA(1);

                if ( (LA105_0==35) ) {
                    alt105=1;
                }


                switch (alt105) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:313:20: ',' varDecl
            	    {
            	    char_literal325=(Token)match(input,35,FOLLOW_35_in_varDecls1980); 
            	    char_literal325_tree = (Object)adaptor.create(char_literal325);
            	    adaptor.addChild(root_0, char_literal325_tree);

            	    pushFollow(FOLLOW_varDecl_in_varDecls1982);
            	    varDecl326=varDecl();

            	    state._fsp--;

            	    adaptor.addChild(root_0, varDecl326.getTree());

            	    }
            	    break;

            	default :
            	    break loop105;
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


 

    public static final BitSet FOLLOW_26_in_actionChannelSelector164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_actionChannelSelector170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_actionChannelSelector176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_actionChannelSelector182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_actionDelay191 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_actionDelay193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_actionGuards203 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expressions_in_actionGuards205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_actionInput216 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_actionInput218 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_actionInput222 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_idents_in_actionInput224 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_actionInput226 = new BitSet(new long[]{0x000000103C000002L});
    public static final BitSet FOLLOW_actionRepeat_in_actionInput228 = new BitSet(new long[]{0x000000003C000002L});
    public static final BitSet FOLLOW_actionChannelSelector_in_actionInput231 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionInput_in_actionInputs242 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_35_in_actionInputs245 = new BitSet(new long[]{0x0000000200080000L});
    public static final BitSet FOLLOW_actionInput_in_actionInputs247 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_ID_in_actionOutput260 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_actionOutput262 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_actionOutput266 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expressions_in_actionOutput268 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_actionOutput270 = new BitSet(new long[]{0x000000103C000002L});
    public static final BitSet FOLLOW_actionRepeat_in_actionOutput272 = new BitSet(new long[]{0x000000003C000002L});
    public static final BitSet FOLLOW_actionChannelSelector_in_actionOutput275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionOutput_in_actionOutputs286 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_35_in_actionOutputs289 = new BitSet(new long[]{0x0000000200080000L});
    public static final BitSet FOLLOW_actionOutput_in_actionOutputs291 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_36_in_actionRepeat302 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_actionRepeat304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_actionStatements313 = new BitSet(new long[]{0x0020000000080002L,0x0000000016084000L});
    public static final BitSet FOLLOW_statement_in_actionStatements315 = new BitSet(new long[]{0x0020000000080002L,0x0000000016084000L});
    public static final BitSet FOLLOW_actorImport_in_actor330 = new BitSet(new long[]{0x0040004000000000L});
    public static final BitSet FOLLOW_38_in_actor333 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_ID_in_actor337 = new BitSet(new long[]{0x0000008200000000L});
    public static final BitSet FOLLOW_33_in_actor340 = new BitSet(new long[]{0x0000000400080000L});
    public static final BitSet FOLLOW_typePars_in_actor342 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_actor345 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_actor349 = new BitSet(new long[]{0x0000010000080000L});
    public static final BitSet FOLLOW_actorParameters_in_actor351 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_actor354 = new BitSet(new long[]{0x0080020000080000L});
    public static final BitSet FOLLOW_actorPortDecls_in_actor359 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_actor362 = new BitSet(new long[]{0x0080000100080000L});
    public static final BitSet FOLLOW_actorPortDecls_in_actor366 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_actor369 = new BitSet(new long[]{0x0014540000080000L,0x0000000000600000L});
    public static final BitSet FOLLOW_actorDeclarations_in_actor372 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_actor374 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_actor376 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration441 = new BitSet(new long[]{0x0000088300080000L});
    public static final BitSet FOLLOW_43_in_actorDeclaration451 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration453 = new BitSet(new long[]{0x0000080100000000L});
    public static final BitSet FOLLOW_32_in_actorDeclaration457 = new BitSet(new long[]{0x0000500000000000L});
    public static final BitSet FOLLOW_44_in_actorDeclaration466 = new BitSet(new long[]{0x0000020200080000L});
    public static final BitSet FOLLOW_actionInputs_in_actorDeclaration468 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_actorDeclaration471 = new BitSet(new long[]{0x00002422C0080000L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration473 = new BitSet(new long[]{0x00002420C0000000L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration476 = new BitSet(new long[]{0x0000242040000000L});
    public static final BitSet FOLLOW_actionDelay_in_actorDeclaration479 = new BitSet(new long[]{0x0000242000000000L});
    public static final BitSet FOLLOW_45_in_actorDeclaration483 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration485 = new BitSet(new long[]{0x0000042000000000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration489 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_actorDeclaration492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_actorDeclaration511 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_actorDeclaration513 = new BitSet(new long[]{0x00002422C0080000L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration515 = new BitSet(new long[]{0x00002420C0000000L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration518 = new BitSet(new long[]{0x0000242040000000L});
    public static final BitSet FOLLOW_actionDelay_in_actorDeclaration521 = new BitSet(new long[]{0x0000242000000000L});
    public static final BitSet FOLLOW_45_in_actorDeclaration525 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration527 = new BitSet(new long[]{0x0000042000000000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration531 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_actorDeclaration534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_actorDeclaration562 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_typePars_in_actorDeclaration564 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_actorDeclaration566 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_39_in_actorDeclaration570 = new BitSet(new long[]{0x0000000000000000L,0x0000000060000000L});
    public static final BitSet FOLLOW_typeAttrs_in_actorDeclaration574 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_actorDeclaration576 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration586 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_47_in_actorDeclaration595 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration597 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_48_in_actorDeclaration633 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration635 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_actorDeclaration697 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_actorDeclaration707 = new BitSet(new long[]{0x0000020200080000L});
    public static final BitSet FOLLOW_actionInputs_in_actorDeclaration709 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_actorDeclaration712 = new BitSet(new long[]{0x00002422C0080000L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration714 = new BitSet(new long[]{0x00002420C0000000L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration717 = new BitSet(new long[]{0x0000242040000000L});
    public static final BitSet FOLLOW_actionDelay_in_actorDeclaration720 = new BitSet(new long[]{0x0000242000000000L});
    public static final BitSet FOLLOW_45_in_actorDeclaration724 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration726 = new BitSet(new long[]{0x0000042000000000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration730 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_actorDeclaration733 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_actorDeclaration743 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_actorDeclaration745 = new BitSet(new long[]{0x00002422C0080000L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration747 = new BitSet(new long[]{0x00002420C0000000L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration750 = new BitSet(new long[]{0x0000242040000000L});
    public static final BitSet FOLLOW_actionDelay_in_actorDeclaration753 = new BitSet(new long[]{0x0000242000000000L});
    public static final BitSet FOLLOW_45_in_actorDeclaration757 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration759 = new BitSet(new long[]{0x0000042000000000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration763 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_actorDeclaration766 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_priorityOrder_in_actorDeclaration775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_actorDeclaration782 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration784 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_actorDeclaration786 = new BitSet(new long[]{0x0000010000080000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration789 = new BitSet(new long[]{0x0000010800000000L});
    public static final BitSet FOLLOW_35_in_actorDeclaration792 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration794 = new BitSet(new long[]{0x0000010800000000L});
    public static final BitSet FOLLOW_40_in_actorDeclaration800 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_actorDeclaration802 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_typeDef_in_actorDeclaration804 = new BitSet(new long[]{0x0000200100000000L});
    public static final BitSet FOLLOW_45_in_actorDeclaration811 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration813 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_actorDeclaration817 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration825 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_actorDeclaration831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_actorDeclaration841 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration843 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_actorDeclaration845 = new BitSet(new long[]{0x0000010000080000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration848 = new BitSet(new long[]{0x0000010800000000L});
    public static final BitSet FOLLOW_35_in_actorDeclaration851 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration853 = new BitSet(new long[]{0x0000010800000000L});
    public static final BitSet FOLLOW_40_in_actorDeclaration859 = new BitSet(new long[]{0x0020200000000000L});
    public static final BitSet FOLLOW_45_in_actorDeclaration866 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration868 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_actorDeclaration876 = new BitSet(new long[]{0x0020040000080000L,0x0000000016084000L});
    public static final BitSet FOLLOW_statement_in_actorDeclaration878 = new BitSet(new long[]{0x0020040000080000L,0x0000000016084000L});
    public static final BitSet FOLLOW_42_in_actorDeclaration881 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations893 = new BitSet(new long[]{0x0014500000080002L,0x0000000000600000L});
    public static final BitSet FOLLOW_schedule_in_actorDeclarations897 = new BitSet(new long[]{0x0014500000080002L,0x0000000000200000L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations899 = new BitSet(new long[]{0x0014500000080002L,0x0000000000200000L});
    public static final BitSet FOLLOW_54_in_actorImport922 = new BitSet(new long[]{0x0000000004080000L});
    public static final BitSet FOLLOW_26_in_actorImport927 = new BitSet(new long[]{0x0000000004080000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport929 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_actorImport931 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport937 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_actorImport939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_actorParameter954 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_ID_in_actorParameter956 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_47_in_actorParameter959 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_actorParameter961 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters983 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_35_in_actorParameters986 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters988 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_55_in_actorPortDecl1008 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_typeDef_in_actorPortDecl1010 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_ID_in_actorPortDecl1012 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_actorPortDecl1019 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_ID_in_actorPortDecl1021 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorPortDecl_in_actorPortDecls1038 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_35_in_actorPortDecls1041 = new BitSet(new long[]{0x0080000000080000L});
    public static final BitSet FOLLOW_actorPortDecl_in_actorPortDecls1043 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_and_expr_in_expression1062 = new BitSet(new long[]{0x0300000000000002L});
    public static final BitSet FOLLOW_set_in_expression1065 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_and_expr_in_expression1073 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bitor_expr_in_and_expr1084 = new BitSet(new long[]{0x0C00000000000002L});
    public static final BitSet FOLLOW_set_in_and_expr1087 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_bitor_expr_in_and_expr1095 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bitand_expr_in_bitor_expr1106 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_60_in_bitor_expr1109 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_bitand_expr_in_bitor_expr1111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eq_expr_in_bitand_expr1122 = new BitSet(new long[]{0x2000000000000002L});
    public static final BitSet FOLLOW_61_in_bitand_expr1125 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_eq_expr_in_bitand_expr1127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rel_expr_in_eq_expr1138 = new BitSet(new long[]{0x4000800000000002L});
    public static final BitSet FOLLOW_set_in_eq_expr1141 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_rel_expr_in_eq_expr1149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_shift_expr_in_rel_expr1160 = new BitSet(new long[]{0x8000000000000002L,0x0000000000000007L});
    public static final BitSet FOLLOW_set_in_rel_expr1163 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_shift_expr_in_rel_expr1179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_add_expr_in_shift_expr1190 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000018L});
    public static final BitSet FOLLOW_set_in_shift_expr1193 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_add_expr_in_shift_expr1201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_mul_expr_in_add_expr1212 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000060L});
    public static final BitSet FOLLOW_set_in_add_expr1215 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_mul_expr_in_add_expr1223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exp_expr_in_mul_expr1234 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000780L});
    public static final BitSet FOLLOW_set_in_mul_expr1237 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_exp_expr_in_mul_expr1253 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_un_expr_in_exp_expr1264 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_exp_expr1267 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_un_expr_in_exp_expr1269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfix_expression_in_un_expr1280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_un_expr1285 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_un_expr_in_un_expr1287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_un_expr1292 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_un_expr_in_un_expr1294 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_un_expr1299 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_un_expr_in_un_expr1301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_postfix_expression1312 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1314 = new BitSet(new long[]{0x0000000500000000L});
    public static final BitSet FOLLOW_32_in_postfix_expression1317 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_expressionGenerators_in_postfix_expression1319 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_postfix_expression1323 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_postfix_expression1327 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1329 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_postfix_expression1331 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1333 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_postfix_expression1335 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1337 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_postfix_expression1339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_postfix_expression1343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_postfix_expression1347 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1349 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_postfix_expression1351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_postfix_expression1355 = new BitSet(new long[]{0x0000008200000002L});
    public static final BitSet FOLLOW_39_in_postfix_expression1363 = new BitSet(new long[]{0x0000018200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1365 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_postfix_expression1368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_postfix_expression1376 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1378 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_postfix_expression1380 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_81_in_constant1394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_constant1398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_constant1402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_constant1406 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_expressionGenerator1416 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_typeDef_in_expressionGenerator1418 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_ID_in_expressionGenerator1420 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_expressionGenerator1422 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_expressionGenerator1424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators1434 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_35_in_expressionGenerators1437 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators1439 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_expression_in_expressions1450 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_35_in_expressions1453 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_expressions1455 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_ID_in_idents1471 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_35_in_idents1474 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_ID_in_idents1476 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality1492 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_priorityInequality1495 = new BitSet(new long[]{0x0000000004080000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality1497 = new BitSet(new long[]{0x0002000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_49_in_priorityInequality1501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_85_in_priorityOrder1511 = new BitSet(new long[]{0x0000040004080000L});
    public static final BitSet FOLLOW_priorityInequality_in_priorityOrder1514 = new BitSet(new long[]{0x0000040004080000L});
    public static final BitSet FOLLOW_42_in_priorityOrder1518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent1532 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_43_in_qualifiedIdent1535 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent1537 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_86_in_schedule1555 = new BitSet(new long[]{0x0000000000000000L,0x0000000001800000L});
    public static final BitSet FOLLOW_87_in_schedule1562 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_ID_in_schedule1564 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_schedule1566 = new BitSet(new long[]{0x0000040000080000L});
    public static final BitSet FOLLOW_stateTransition_in_schedule1568 = new BitSet(new long[]{0x0000040000080000L});
    public static final BitSet FOLLOW_42_in_schedule1571 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_schedule1581 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stateTransition1593 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_stateTransition1595 = new BitSet(new long[]{0x0000000004080000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_stateTransition1597 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_stateTransition1599 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_stateTransition1601 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_ID_in_stateTransition1603 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_stateTransition1605 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_statement1621 = new BitSet(new long[]{0x0020240000080000L,0x0000000016084000L});
    public static final BitSet FOLLOW_45_in_statement1624 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_varDecls_in_statement1626 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_37_in_statement1628 = new BitSet(new long[]{0x0020040000080000L,0x0000000016084000L});
    public static final BitSet FOLLOW_statement_in_statement1632 = new BitSet(new long[]{0x0020040000080000L,0x0000000016084000L});
    public static final BitSet FOLLOW_42_in_statement1635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_statement1641 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_statement1647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_statement1653 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_statement1655 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_statement1657 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_statement1660 = new BitSet(new long[]{0x0000202000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_statement1663 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_statement1665 = new BitSet(new long[]{0x0000202000000000L});
    public static final BitSet FOLLOW_45_in_statement1671 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_varDecls_in_statement1673 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_37_in_statement1677 = new BitSet(new long[]{0x0020040000080000L,0x0000000016084000L});
    public static final BitSet FOLLOW_statement_in_statement1679 = new BitSet(new long[]{0x0020040000080000L,0x0000000016084000L});
    public static final BitSet FOLLOW_42_in_statement1682 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_statement1688 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_statement1690 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_statement1692 = new BitSet(new long[]{0x0020040000080000L,0x0000000016094000L});
    public static final BitSet FOLLOW_statement_in_statement1694 = new BitSet(new long[]{0x0020040000080000L,0x0000000016094000L});
    public static final BitSet FOLLOW_80_in_statement1698 = new BitSet(new long[]{0x0020040000080000L,0x0000000016084000L});
    public static final BitSet FOLLOW_statement_in_statement1700 = new BitSet(new long[]{0x0020040000080000L,0x0000000016084000L});
    public static final BitSet FOLLOW_42_in_statement1705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_statement1711 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_statement1713 = new BitSet(new long[]{0x0000202000000000L});
    public static final BitSet FOLLOW_45_in_statement1716 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_varDecls_in_statement1718 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_37_in_statement1722 = new BitSet(new long[]{0x0020040000080000L,0x0000000016084000L});
    public static final BitSet FOLLOW_statement_in_statement1724 = new BitSet(new long[]{0x0020040000080000L,0x0000000016084000L});
    public static final BitSet FOLLOW_42_in_statement1727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_statement1734 = new BitSet(new long[]{0x0001008200000000L});
    public static final BitSet FOLLOW_33_in_statement1744 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expressions_in_statement1746 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_statement1748 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_statement1752 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_statement1754 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_statement1756 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_statement1766 = new BitSet(new long[]{0x0000018200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expressions_in_statement1768 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_statement1771 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_statement1773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_typeAttr1791 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_typeAttr1793 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_typeDef_in_typeAttr1795 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_typeAttr1807 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_typeAttr1809 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_typeAttr1811 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs1827 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_35_in_typeAttrs1830 = new BitSet(new long[]{0x0000000000000000L,0x0000000060000000L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs1832 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_ID_in_typeDef1849 = new BitSet(new long[]{0x0000008200000002L});
    public static final BitSet FOLLOW_33_in_typeDef1854 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_typePars_in_typeDef1856 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_typeDef1858 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_typeDef1864 = new BitSet(new long[]{0x0000000000000000L,0x0000000060000000L});
    public static final BitSet FOLLOW_typeAttrs_in_typeDef1868 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_typeDef1870 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_typePar1900 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_63_in_typePar1903 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_typeDef_in_typePar1905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typePar_in_typePars1916 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_35_in_typePars1919 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_typePar_in_typePars1921 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_typeDef_in_varDecl1942 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_ID_in_varDecl1944 = new BitSet(new long[]{0x0001800000000002L});
    public static final BitSet FOLLOW_47_in_varDecl1947 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_varDecl1949 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_varDecl1953 = new BitSet(new long[]{0x0000008200380000L,0x0000000000067040L});
    public static final BitSet FOLLOW_expression_in_varDecl1955 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_varDeclNoExpr1966 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_ID_in_varDeclNoExpr1968 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varDecl_in_varDecls1977 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_35_in_varDecls1980 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_varDecl_in_varDecls1982 = new BitSet(new long[]{0x0000000800000002L});

}