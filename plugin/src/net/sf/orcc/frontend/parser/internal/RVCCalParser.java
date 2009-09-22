// $ANTLR 3.1.2 D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g 2009-09-22 16:09:50

package net.sf.orcc.frontend.parser.internal;

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACTOR", "EXPR", "INPUTS", "OUTPUTS", "PARAMETERS", "PORT", "TYPE", "TYPE_ATTRS", "ID", "INTEGER", "STRING", "FLOAT", "LINE_COMMENT", "MULTI_LINE_COMMENT", "WHITESPACE", "'all'", "'any'", "'at'", "'at*'", "'delay'", "'guard'", "':'", "'['", "']'", "','", "'repeat'", "'do'", "'actor'", "'('", "')'", "'==>'", "'end'", "'.'", "'action'", "'var'", "'initialize'", "'='", "':='", "';'", "'function'", "'-->'", "'procedure'", "'begin'", "'import'", "'multi'", "'or'", "'||'", "'and'", "'&&'", "'|'", "'&'", "'!='", "'<'", "'>'", "'<='", "'>='", "'<<'", "'>>'", "'+'", "'-'", "'div'", "'mod'", "'*'", "'/'", "'^'", "'not'", "'#'", "'if'", "'then'", "'else'", "'true'", "'false'", "'for'", "'in'", "'priority'", "'schedule'", "'fsm'", "'regexp'", "'choose'", "'foreach'", "'..'", "'while'"
    };
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int OUTPUTS=7;
    public static final int T__64=64;
    public static final int T__29=29;
    public static final int T__65=65;
    public static final int T__28=28;
    public static final int T__62=62;
    public static final int T__27=27;
    public static final int PARAMETERS=8;
    public static final int T__63=63;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int FLOAT=15;
    public static final int INPUTS=6;
    public static final int ID=12;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int EOF=-1;
    public static final int TYPE=10;
    public static final int T__55=55;
    public static final int TYPE_ATTRS=11;
    public static final int T__19=19;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int EXPR=5;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__59=59;
    public static final int T__50=50;
    public static final int INTEGER=13;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__80=80;
    public static final int T__46=46;
    public static final int T__81=81;
    public static final int T__47=47;
    public static final int T__82=82;
    public static final int T__44=44;
    public static final int T__83=83;
    public static final int T__45=45;
    public static final int LINE_COMMENT=16;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int WHITESPACE=18;
    public static final int PORT=9;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int MULTI_LINE_COMMENT=17;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__71=71;
    public static final int T__33=33;
    public static final int T__72=72;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__70=70;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int ACTOR=4;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int T__74=74;
    public static final int T__73=73;
    public static final int T__79=79;
    public static final int STRING=14;
    public static final int T__78=78;
    public static final int T__77=77;

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:61:1: actionChannelSelector : ( 'all' | 'any' | 'at' | 'at*' );
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:61:22: ( 'all' | 'any' | 'at' | 'at*' )
            int alt1=4;
            switch ( input.LA(1) ) {
            case 19:
                {
                alt1=1;
                }
                break;
            case 20:
                {
                alt1=2;
                }
                break;
            case 21:
                {
                alt1=3;
                }
                break;
            case 22:
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:62:2: 'all'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal1=(Token)match(input,19,FOLLOW_19_in_actionChannelSelector104); 
                    string_literal1_tree = (Object)adaptor.create(string_literal1);
                    adaptor.addChild(root_0, string_literal1_tree);

                     System.out.println("TODO: throw exception channel selectors"); 

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:63:3: 'any'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal2=(Token)match(input,20,FOLLOW_20_in_actionChannelSelector110); 
                    string_literal2_tree = (Object)adaptor.create(string_literal2);
                    adaptor.addChild(root_0, string_literal2_tree);

                     System.out.println("TODO: throw exception channel selectors"); 

                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:64:3: 'at'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal3=(Token)match(input,21,FOLLOW_21_in_actionChannelSelector116); 
                    string_literal3_tree = (Object)adaptor.create(string_literal3);
                    adaptor.addChild(root_0, string_literal3_tree);

                     System.out.println("TODO: throw exception channel selectors"); 

                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:65:3: 'at*'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal4=(Token)match(input,22,FOLLOW_22_in_actionChannelSelector122); 
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:67:1: actionDelay : 'delay' expression ;
    public final RVCCalParser.actionDelay_return actionDelay() throws RecognitionException {
        RVCCalParser.actionDelay_return retval = new RVCCalParser.actionDelay_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal5=null;
        RVCCalParser.expression_return expression6 = null;


        Object string_literal5_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:67:12: ( 'delay' expression )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:67:14: 'delay' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal5=(Token)match(input,23,FOLLOW_23_in_actionDelay131); 
            string_literal5_tree = (Object)adaptor.create(string_literal5);
            adaptor.addChild(root_0, string_literal5_tree);

            pushFollow(FOLLOW_expression_in_actionDelay133);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:70:1: actionGuards : 'guard' expressions ;
    public final RVCCalParser.actionGuards_return actionGuards() throws RecognitionException {
        RVCCalParser.actionGuards_return retval = new RVCCalParser.actionGuards_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal7=null;
        RVCCalParser.expressions_return expressions8 = null;


        Object string_literal7_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:70:13: ( 'guard' expressions )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:70:15: 'guard' expressions
            {
            root_0 = (Object)adaptor.nil();

            string_literal7=(Token)match(input,24,FOLLOW_24_in_actionGuards143); 
            string_literal7_tree = (Object)adaptor.create(string_literal7);
            adaptor.addChild(root_0, string_literal7_tree);

            pushFollow(FOLLOW_expressions_in_actionGuards145);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:72:1: actionInput : ( ID ':' )? '[' idents ']' ( actionRepeat )? ( actionChannelSelector )? ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:72:12: ( ( ID ':' )? '[' idents ']' ( actionRepeat )? ( actionChannelSelector )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:73:2: ( ID ':' )? '[' idents ']' ( actionRepeat )? ( actionChannelSelector )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:73:2: ( ID ':' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==ID) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:73:3: ID ':'
                    {
                    ID9=(Token)match(input,ID,FOLLOW_ID_in_actionInput156); 
                    ID9_tree = (Object)adaptor.create(ID9);
                    adaptor.addChild(root_0, ID9_tree);

                    char_literal10=(Token)match(input,25,FOLLOW_25_in_actionInput158); 
                    char_literal10_tree = (Object)adaptor.create(char_literal10);
                    adaptor.addChild(root_0, char_literal10_tree);


                    }
                    break;

            }

            char_literal11=(Token)match(input,26,FOLLOW_26_in_actionInput162); 
            char_literal11_tree = (Object)adaptor.create(char_literal11);
            adaptor.addChild(root_0, char_literal11_tree);

            pushFollow(FOLLOW_idents_in_actionInput164);
            idents12=idents();

            state._fsp--;

            adaptor.addChild(root_0, idents12.getTree());
            char_literal13=(Token)match(input,27,FOLLOW_27_in_actionInput166); 
            char_literal13_tree = (Object)adaptor.create(char_literal13);
            adaptor.addChild(root_0, char_literal13_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:73:27: ( actionRepeat )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==29) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:73:27: actionRepeat
                    {
                    pushFollow(FOLLOW_actionRepeat_in_actionInput168);
                    actionRepeat14=actionRepeat();

                    state._fsp--;

                    adaptor.addChild(root_0, actionRepeat14.getTree());

                    }
                    break;

            }

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:73:41: ( actionChannelSelector )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( ((LA4_0>=19 && LA4_0<=22)) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:73:41: actionChannelSelector
                    {
                    pushFollow(FOLLOW_actionChannelSelector_in_actionInput171);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:76:1: actionInputs : actionInput ( ',' actionInput )* ;
    public final RVCCalParser.actionInputs_return actionInputs() throws RecognitionException {
        RVCCalParser.actionInputs_return retval = new RVCCalParser.actionInputs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal17=null;
        RVCCalParser.actionInput_return actionInput16 = null;

        RVCCalParser.actionInput_return actionInput18 = null;


        Object char_literal17_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:76:13: ( actionInput ( ',' actionInput )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:76:15: actionInput ( ',' actionInput )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_actionInput_in_actionInputs182);
            actionInput16=actionInput();

            state._fsp--;

            adaptor.addChild(root_0, actionInput16.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:76:27: ( ',' actionInput )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==28) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:76:28: ',' actionInput
            	    {
            	    char_literal17=(Token)match(input,28,FOLLOW_28_in_actionInputs185); 
            	    char_literal17_tree = (Object)adaptor.create(char_literal17);
            	    adaptor.addChild(root_0, char_literal17_tree);

            	    pushFollow(FOLLOW_actionInput_in_actionInputs187);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:78:1: actionOutput : ( ID ':' )? '[' expressions ']' ( actionRepeat )? ( actionChannelSelector )? ;
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
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:78:13: ( ( ID ':' )? '[' expressions ']' ( actionRepeat )? ( actionChannelSelector )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:79:2: ( ID ':' )? '[' expressions ']' ( actionRepeat )? ( actionChannelSelector )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:79:2: ( ID ':' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==ID) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:79:3: ID ':'
                    {
                    ID19=(Token)match(input,ID,FOLLOW_ID_in_actionOutput200); 
                    ID19_tree = (Object)adaptor.create(ID19);
                    adaptor.addChild(root_0, ID19_tree);

                    char_literal20=(Token)match(input,25,FOLLOW_25_in_actionOutput202); 
                    char_literal20_tree = (Object)adaptor.create(char_literal20);
                    adaptor.addChild(root_0, char_literal20_tree);


                    }
                    break;

            }

            char_literal21=(Token)match(input,26,FOLLOW_26_in_actionOutput206); 
            char_literal21_tree = (Object)adaptor.create(char_literal21);
            adaptor.addChild(root_0, char_literal21_tree);

            pushFollow(FOLLOW_expressions_in_actionOutput208);
            expressions22=expressions();

            state._fsp--;

            adaptor.addChild(root_0, expressions22.getTree());
            char_literal23=(Token)match(input,27,FOLLOW_27_in_actionOutput210); 
            char_literal23_tree = (Object)adaptor.create(char_literal23);
            adaptor.addChild(root_0, char_literal23_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:79:32: ( actionRepeat )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==29) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:79:32: actionRepeat
                    {
                    pushFollow(FOLLOW_actionRepeat_in_actionOutput212);
                    actionRepeat24=actionRepeat();

                    state._fsp--;

                    adaptor.addChild(root_0, actionRepeat24.getTree());

                    }
                    break;

            }

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:79:46: ( actionChannelSelector )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0>=19 && LA8_0<=22)) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:79:46: actionChannelSelector
                    {
                    pushFollow(FOLLOW_actionChannelSelector_in_actionOutput215);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:82:1: actionOutputs : actionOutput ( ',' actionOutput )* ;
    public final RVCCalParser.actionOutputs_return actionOutputs() throws RecognitionException {
        RVCCalParser.actionOutputs_return retval = new RVCCalParser.actionOutputs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal27=null;
        RVCCalParser.actionOutput_return actionOutput26 = null;

        RVCCalParser.actionOutput_return actionOutput28 = null;


        Object char_literal27_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:82:14: ( actionOutput ( ',' actionOutput )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:82:16: actionOutput ( ',' actionOutput )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_actionOutput_in_actionOutputs226);
            actionOutput26=actionOutput();

            state._fsp--;

            adaptor.addChild(root_0, actionOutput26.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:82:29: ( ',' actionOutput )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==28) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:82:30: ',' actionOutput
            	    {
            	    char_literal27=(Token)match(input,28,FOLLOW_28_in_actionOutputs229); 
            	    char_literal27_tree = (Object)adaptor.create(char_literal27);
            	    adaptor.addChild(root_0, char_literal27_tree);

            	    pushFollow(FOLLOW_actionOutput_in_actionOutputs231);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:84:1: actionRepeat : 'repeat' expression ;
    public final RVCCalParser.actionRepeat_return actionRepeat() throws RecognitionException {
        RVCCalParser.actionRepeat_return retval = new RVCCalParser.actionRepeat_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal29=null;
        RVCCalParser.expression_return expression30 = null;


        Object string_literal29_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:84:13: ( 'repeat' expression )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:84:15: 'repeat' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal29=(Token)match(input,29,FOLLOW_29_in_actionRepeat242); 
            string_literal29_tree = (Object)adaptor.create(string_literal29);
            adaptor.addChild(root_0, string_literal29_tree);

            pushFollow(FOLLOW_expression_in_actionRepeat244);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:86:1: actionStatements : 'do' ( statement )* ;
    public final RVCCalParser.actionStatements_return actionStatements() throws RecognitionException {
        RVCCalParser.actionStatements_return retval = new RVCCalParser.actionStatements_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal31=null;
        RVCCalParser.statement_return statement32 = null;


        Object string_literal31_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:86:17: ( 'do' ( statement )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:86:19: 'do' ( statement )*
            {
            root_0 = (Object)adaptor.nil();

            string_literal31=(Token)match(input,30,FOLLOW_30_in_actionStatements253); 
            string_literal31_tree = (Object)adaptor.create(string_literal31);
            adaptor.addChild(root_0, string_literal31_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:86:24: ( statement )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==ID||LA10_0==46||LA10_0==71||LA10_0==76||(LA10_0>=82 && LA10_0<=83)||LA10_0==85) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:86:24: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_actionStatements255);
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:88:1: actor : ( actorImport )* 'actor' id= ID ( '[' ( typePar )+ ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' actorDeclarations 'end' EOF -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) ;
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

        RVCCalParser.typePar_return typePar36 = null;

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
        RewriteRuleTokenStream stream_32=new RewriteRuleTokenStream(adaptor,"token 32");
        RewriteRuleTokenStream stream_31=new RewriteRuleTokenStream(adaptor,"token 31");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_35=new RewriteRuleTokenStream(adaptor,"token 35");
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleTokenStream stream_33=new RewriteRuleTokenStream(adaptor,"token 33");
        RewriteRuleTokenStream stream_34=new RewriteRuleTokenStream(adaptor,"token 34");
        RewriteRuleTokenStream stream_25=new RewriteRuleTokenStream(adaptor,"token 25");
        RewriteRuleTokenStream stream_26=new RewriteRuleTokenStream(adaptor,"token 26");
        RewriteRuleTokenStream stream_27=new RewriteRuleTokenStream(adaptor,"token 27");
        RewriteRuleSubtreeStream stream_actorImport=new RewriteRuleSubtreeStream(adaptor,"rule actorImport");
        RewriteRuleSubtreeStream stream_typePar=new RewriteRuleSubtreeStream(adaptor,"rule typePar");
        RewriteRuleSubtreeStream stream_actorDeclarations=new RewriteRuleSubtreeStream(adaptor,"rule actorDeclarations");
        RewriteRuleSubtreeStream stream_actorPortDecls=new RewriteRuleSubtreeStream(adaptor,"rule actorPortDecls");
        RewriteRuleSubtreeStream stream_actorParameters=new RewriteRuleSubtreeStream(adaptor,"rule actorParameters");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:91:6: ( ( actorImport )* 'actor' id= ID ( '[' ( typePar )+ ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' actorDeclarations 'end' EOF -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:91:8: ( actorImport )* 'actor' id= ID ( '[' ( typePar )+ ']' )? '(' ( actorParameters )? ')' (inputs= actorPortDecls )? '==>' (outputs= actorPortDecls )? ':' actorDeclarations 'end' EOF
            {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:91:8: ( actorImport )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==47) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:91:8: actorImport
            	    {
            	    pushFollow(FOLLOW_actorImport_in_actor270);
            	    actorImport33=actorImport();

            	    state._fsp--;

            	    stream_actorImport.add(actorImport33.getTree());

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            string_literal34=(Token)match(input,31,FOLLOW_31_in_actor273);  
            stream_31.add(string_literal34);

            id=(Token)match(input,ID,FOLLOW_ID_in_actor277);  
            stream_ID.add(id);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:91:35: ( '[' ( typePar )+ ']' )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==26) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:91:36: '[' ( typePar )+ ']'
                    {
                    char_literal35=(Token)match(input,26,FOLLOW_26_in_actor280);  
                    stream_26.add(char_literal35);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:91:40: ( typePar )+
                    int cnt12=0;
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( (LA12_0==ID) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:91:40: typePar
                    	    {
                    	    pushFollow(FOLLOW_typePar_in_actor282);
                    	    typePar36=typePar();

                    	    state._fsp--;

                    	    stream_typePar.add(typePar36.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt12 >= 1 ) break loop12;
                                EarlyExitException eee =
                                    new EarlyExitException(12, input);
                                throw eee;
                        }
                        cnt12++;
                    } while (true);

                    char_literal37=(Token)match(input,27,FOLLOW_27_in_actor285);  
                    stream_27.add(char_literal37);


                    }
                    break;

            }

            char_literal38=(Token)match(input,32,FOLLOW_32_in_actor289);  
            stream_32.add(char_literal38);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:91:59: ( actorParameters )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==ID) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:91:59: actorParameters
                    {
                    pushFollow(FOLLOW_actorParameters_in_actor291);
                    actorParameters39=actorParameters();

                    state._fsp--;

                    stream_actorParameters.add(actorParameters39.getTree());

                    }
                    break;

            }

            char_literal40=(Token)match(input,33,FOLLOW_33_in_actor294);  
            stream_33.add(char_literal40);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:92:8: (inputs= actorPortDecls )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==ID||LA15_0==48) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:92:8: inputs= actorPortDecls
                    {
                    pushFollow(FOLLOW_actorPortDecls_in_actor299);
                    inputs=actorPortDecls();

                    state._fsp--;

                    stream_actorPortDecls.add(inputs.getTree());

                    }
                    break;

            }

            string_literal41=(Token)match(input,34,FOLLOW_34_in_actor302);  
            stream_34.add(string_literal41);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:92:38: (outputs= actorPortDecls )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==ID||LA16_0==48) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:92:38: outputs= actorPortDecls
                    {
                    pushFollow(FOLLOW_actorPortDecls_in_actor306);
                    outputs=actorPortDecls();

                    state._fsp--;

                    stream_actorPortDecls.add(outputs.getTree());

                    }
                    break;

            }

            char_literal42=(Token)match(input,25,FOLLOW_25_in_actor309);  
            stream_25.add(char_literal42);

            pushFollow(FOLLOW_actorDeclarations_in_actor312);
            actorDeclarations43=actorDeclarations();

            state._fsp--;

            stream_actorDeclarations.add(actorDeclarations43.getTree());
            string_literal44=(Token)match(input,35,FOLLOW_35_in_actor314);  
            stream_35.add(string_literal44);

            EOF45=(Token)match(input,EOF,FOLLOW_EOF_in_actor316);  
            stream_EOF.add(EOF45);



            // AST REWRITE
            // elements: outputs, 31, inputs, actorParameters, id
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
            // 94:2: -> 'actor' $id ^( PARAMETERS ( actorParameters )? ) ^( INPUTS ( $inputs)? ) ^( OUTPUTS ( $outputs)? )
            {
                adaptor.addChild(root_0, stream_31.nextNode());
                adaptor.addChild(root_0, stream_id.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:94:17: ^( PARAMETERS ( actorParameters )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARAMETERS, "PARAMETERS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:94:30: ( actorParameters )?
                if ( stream_actorParameters.hasNext() ) {
                    adaptor.addChild(root_1, stream_actorParameters.nextTree());

                }
                stream_actorParameters.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:94:48: ^( INPUTS ( $inputs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(INPUTS, "INPUTS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:94:57: ( $inputs)?
                if ( stream_inputs.hasNext() ) {
                    adaptor.addChild(root_1, stream_inputs.nextTree());

                }
                stream_inputs.reset();

                adaptor.addChild(root_0, root_1);
                }
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:94:67: ^( OUTPUTS ( $outputs)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(OUTPUTS, "OUTPUTS"), root_1);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:94:77: ( $outputs)?
                if ( stream_outputs.hasNext() ) {
                    adaptor.addChild(root_1, stream_outputs.nextTree());

                }
                stream_outputs.reset();

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:96:1: actorDeclaration : ( ID ( ( ( '.' ID )? ':' )? ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' ) | ( '[' ( typePar )+ ']' | '(' typeAttrs ')' )? ID ( '=' expression | ':=' expression )? ';' ) | 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | priorityOrder | 'function' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end' | 'procedure' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end' );
    public final RVCCalParser.actorDeclaration_return actorDeclaration() throws RecognitionException {
        RVCCalParser.actorDeclaration_return retval = new RVCCalParser.actorDeclaration_return();
        retval.start = input.LT(1);

        Object root_0 = null;

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
        Token char_literal74=null;
        Token ID75=null;
        Token char_literal76=null;
        Token string_literal78=null;
        Token char_literal80=null;
        Token string_literal81=null;
        Token string_literal83=null;
        Token string_literal87=null;
        Token string_literal90=null;
        Token string_literal91=null;
        Token string_literal92=null;
        Token string_literal96=null;
        Token string_literal99=null;
        Token string_literal101=null;
        Token ID102=null;
        Token char_literal103=null;
        Token char_literal105=null;
        Token char_literal107=null;
        Token string_literal108=null;
        Token string_literal110=null;
        Token char_literal112=null;
        Token string_literal114=null;
        Token string_literal115=null;
        Token ID116=null;
        Token char_literal117=null;
        Token char_literal119=null;
        Token char_literal121=null;
        Token string_literal122=null;
        Token string_literal124=null;
        Token string_literal126=null;
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

        RVCCalParser.typePar_return typePar70 = null;

        RVCCalParser.typeAttrs_return typeAttrs73 = null;

        RVCCalParser.expression_return expression77 = null;

        RVCCalParser.expression_return expression79 = null;

        RVCCalParser.actionInputs_return actionInputs82 = null;

        RVCCalParser.actionOutputs_return actionOutputs84 = null;

        RVCCalParser.actionGuards_return actionGuards85 = null;

        RVCCalParser.actionDelay_return actionDelay86 = null;

        RVCCalParser.varDecls_return varDecls88 = null;

        RVCCalParser.actionStatements_return actionStatements89 = null;

        RVCCalParser.actionOutputs_return actionOutputs93 = null;

        RVCCalParser.actionGuards_return actionGuards94 = null;

        RVCCalParser.actionDelay_return actionDelay95 = null;

        RVCCalParser.varDecls_return varDecls97 = null;

        RVCCalParser.actionStatements_return actionStatements98 = null;

        RVCCalParser.priorityOrder_return priorityOrder100 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr104 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr106 = null;

        RVCCalParser.typeDef_return typeDef109 = null;

        RVCCalParser.varDecls_return varDecls111 = null;

        RVCCalParser.expression_return expression113 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr118 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr120 = null;

        RVCCalParser.varDecls_return varDecls123 = null;

        RVCCalParser.statement_return statement125 = null;


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
        Object char_literal74_tree=null;
        Object ID75_tree=null;
        Object char_literal76_tree=null;
        Object string_literal78_tree=null;
        Object char_literal80_tree=null;
        Object string_literal81_tree=null;
        Object string_literal83_tree=null;
        Object string_literal87_tree=null;
        Object string_literal90_tree=null;
        Object string_literal91_tree=null;
        Object string_literal92_tree=null;
        Object string_literal96_tree=null;
        Object string_literal99_tree=null;
        Object string_literal101_tree=null;
        Object ID102_tree=null;
        Object char_literal103_tree=null;
        Object char_literal105_tree=null;
        Object char_literal107_tree=null;
        Object string_literal108_tree=null;
        Object string_literal110_tree=null;
        Object char_literal112_tree=null;
        Object string_literal114_tree=null;
        Object string_literal115_tree=null;
        Object ID116_tree=null;
        Object char_literal117_tree=null;
        Object char_literal119_tree=null;
        Object char_literal121_tree=null;
        Object string_literal122_tree=null;
        Object string_literal124_tree=null;
        Object string_literal126_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:99:17: ( ID ( ( ( '.' ID )? ':' )? ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' ) | ( '[' ( typePar )+ ']' | '(' typeAttrs ')' )? ID ( '=' expression | ':=' expression )? ';' ) | 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | priorityOrder | 'function' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end' | 'procedure' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end' )
            int alt53=6;
            switch ( input.LA(1) ) {
            case ID:
                {
                alt53=1;
                }
                break;
            case 37:
                {
                alt53=2;
                }
                break;
            case 39:
                {
                alt53=3;
                }
                break;
            case 78:
                {
                alt53=4;
                }
                break;
            case 43:
                {
                alt53=5;
                }
                break;
            case 45:
                {
                alt53=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 53, 0, input);

                throw nvae;
            }

            switch (alt53) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:104:3: ID ( ( ( '.' ID )? ':' )? ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' ) | ( '[' ( typePar )+ ']' | '(' typeAttrs ')' )? ID ( '=' expression | ':=' expression )? ';' )
                    {
                    root_0 = (Object)adaptor.nil();

                    ID46=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration371); 
                    ID46_tree = (Object)adaptor.create(ID46);
                    adaptor.addChild(root_0, ID46_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:105:5: ( ( ( '.' ID )? ':' )? ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' ) | ( '[' ( typePar )+ ']' | '(' typeAttrs ')' )? ID ( '=' expression | ':=' expression )? ';' )
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==25||(LA34_0>=36 && LA34_0<=37)||LA34_0==39) ) {
                        alt34=1;
                    }
                    else if ( (LA34_0==ID||LA34_0==26||LA34_0==32) ) {
                        alt34=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 34, 0, input);

                        throw nvae;
                    }
                    switch (alt34) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:105:6: ( ( '.' ID )? ':' )? ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' )
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:105:6: ( ( '.' ID )? ':' )?
                            int alt18=2;
                            int LA18_0 = input.LA(1);

                            if ( (LA18_0==25||LA18_0==36) ) {
                                alt18=1;
                            }
                            switch (alt18) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:105:7: ( '.' ID )? ':'
                                    {
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:105:7: ( '.' ID )?
                                    int alt17=2;
                                    int LA17_0 = input.LA(1);

                                    if ( (LA17_0==36) ) {
                                        alt17=1;
                                    }
                                    switch (alt17) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:105:8: '.' ID
                                            {
                                            char_literal47=(Token)match(input,36,FOLLOW_36_in_actorDeclaration380); 
                                            char_literal47_tree = (Object)adaptor.create(char_literal47);
                                            adaptor.addChild(root_0, char_literal47_tree);

                                            ID48=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration382); 
                                            ID48_tree = (Object)adaptor.create(ID48);
                                            adaptor.addChild(root_0, ID48_tree);


                                            }
                                            break;

                                    }

                                    char_literal49=(Token)match(input,25,FOLLOW_25_in_actorDeclaration386); 
                                    char_literal49_tree = (Object)adaptor.create(char_literal49);
                                    adaptor.addChild(root_0, char_literal49_tree);


                                    }
                                    break;

                            }

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:106:7: ( 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' | 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end' )
                            int alt30=2;
                            int LA30_0 = input.LA(1);

                            if ( (LA30_0==37) ) {
                                alt30=1;
                            }
                            else if ( (LA30_0==39) ) {
                                alt30=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 30, 0, input);

                                throw nvae;
                            }
                            switch (alt30) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:106:8: 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end'
                                    {
                                    string_literal50=(Token)match(input,37,FOLLOW_37_in_actorDeclaration397); 
                                    string_literal50_tree = (Object)adaptor.create(string_literal50);
                                    adaptor.addChild(root_0, string_literal50_tree);

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:106:17: ( actionInputs )?
                                    int alt19=2;
                                    int LA19_0 = input.LA(1);

                                    if ( (LA19_0==ID||LA19_0==26) ) {
                                        alt19=1;
                                    }
                                    switch (alt19) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:106:17: actionInputs
                                            {
                                            pushFollow(FOLLOW_actionInputs_in_actorDeclaration399);
                                            actionInputs51=actionInputs();

                                            state._fsp--;

                                            adaptor.addChild(root_0, actionInputs51.getTree());

                                            }
                                            break;

                                    }

                                    string_literal52=(Token)match(input,34,FOLLOW_34_in_actorDeclaration402); 
                                    string_literal52_tree = (Object)adaptor.create(string_literal52);
                                    adaptor.addChild(root_0, string_literal52_tree);

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:106:37: ( actionOutputs )?
                                    int alt20=2;
                                    int LA20_0 = input.LA(1);

                                    if ( (LA20_0==ID||LA20_0==26) ) {
                                        alt20=1;
                                    }
                                    switch (alt20) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:106:37: actionOutputs
                                            {
                                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration404);
                                            actionOutputs53=actionOutputs();

                                            state._fsp--;

                                            adaptor.addChild(root_0, actionOutputs53.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:106:52: ( actionGuards )?
                                    int alt21=2;
                                    int LA21_0 = input.LA(1);

                                    if ( (LA21_0==24) ) {
                                        alt21=1;
                                    }
                                    switch (alt21) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:106:52: actionGuards
                                            {
                                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration407);
                                            actionGuards54=actionGuards();

                                            state._fsp--;

                                            adaptor.addChild(root_0, actionGuards54.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:106:66: ( actionDelay )?
                                    int alt22=2;
                                    int LA22_0 = input.LA(1);

                                    if ( (LA22_0==23) ) {
                                        alt22=1;
                                    }
                                    switch (alt22) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:106:66: actionDelay
                                            {
                                            pushFollow(FOLLOW_actionDelay_in_actorDeclaration410);
                                            actionDelay55=actionDelay();

                                            state._fsp--;

                                            adaptor.addChild(root_0, actionDelay55.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:106:79: ( 'var' varDecls )?
                                    int alt23=2;
                                    int LA23_0 = input.LA(1);

                                    if ( (LA23_0==38) ) {
                                        alt23=1;
                                    }
                                    switch (alt23) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:106:80: 'var' varDecls
                                            {
                                            string_literal56=(Token)match(input,38,FOLLOW_38_in_actorDeclaration414); 
                                            string_literal56_tree = (Object)adaptor.create(string_literal56);
                                            adaptor.addChild(root_0, string_literal56_tree);

                                            pushFollow(FOLLOW_varDecls_in_actorDeclaration416);
                                            varDecls57=varDecls();

                                            state._fsp--;

                                            adaptor.addChild(root_0, varDecls57.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:106:97: ( actionStatements )?
                                    int alt24=2;
                                    int LA24_0 = input.LA(1);

                                    if ( (LA24_0==30) ) {
                                        alt24=1;
                                    }
                                    switch (alt24) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:106:97: actionStatements
                                            {
                                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration420);
                                            actionStatements58=actionStatements();

                                            state._fsp--;

                                            adaptor.addChild(root_0, actionStatements58.getTree());

                                            }
                                            break;

                                    }

                                    string_literal59=(Token)match(input,35,FOLLOW_35_in_actorDeclaration423); 
                                    string_literal59_tree = (Object)adaptor.create(string_literal59);
                                    adaptor.addChild(root_0, string_literal59_tree);

                                     

                                    }
                                    break;
                                case 2 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:7: 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end'
                                    {
                                    string_literal60=(Token)match(input,39,FOLLOW_39_in_actorDeclaration440); 
                                    string_literal60_tree = (Object)adaptor.create(string_literal60);
                                    adaptor.addChild(root_0, string_literal60_tree);

                                    string_literal61=(Token)match(input,34,FOLLOW_34_in_actorDeclaration442); 
                                    string_literal61_tree = (Object)adaptor.create(string_literal61);
                                    adaptor.addChild(root_0, string_literal61_tree);

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:26: ( actionOutputs )?
                                    int alt25=2;
                                    int LA25_0 = input.LA(1);

                                    if ( (LA25_0==ID||LA25_0==26) ) {
                                        alt25=1;
                                    }
                                    switch (alt25) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:26: actionOutputs
                                            {
                                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration444);
                                            actionOutputs62=actionOutputs();

                                            state._fsp--;

                                            adaptor.addChild(root_0, actionOutputs62.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:41: ( actionGuards )?
                                    int alt26=2;
                                    int LA26_0 = input.LA(1);

                                    if ( (LA26_0==24) ) {
                                        alt26=1;
                                    }
                                    switch (alt26) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:41: actionGuards
                                            {
                                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration447);
                                            actionGuards63=actionGuards();

                                            state._fsp--;

                                            adaptor.addChild(root_0, actionGuards63.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:55: ( actionDelay )?
                                    int alt27=2;
                                    int LA27_0 = input.LA(1);

                                    if ( (LA27_0==23) ) {
                                        alt27=1;
                                    }
                                    switch (alt27) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:55: actionDelay
                                            {
                                            pushFollow(FOLLOW_actionDelay_in_actorDeclaration450);
                                            actionDelay64=actionDelay();

                                            state._fsp--;

                                            adaptor.addChild(root_0, actionDelay64.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:68: ( 'var' varDecls )?
                                    int alt28=2;
                                    int LA28_0 = input.LA(1);

                                    if ( (LA28_0==38) ) {
                                        alt28=1;
                                    }
                                    switch (alt28) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:69: 'var' varDecls
                                            {
                                            string_literal65=(Token)match(input,38,FOLLOW_38_in_actorDeclaration454); 
                                            string_literal65_tree = (Object)adaptor.create(string_literal65);
                                            adaptor.addChild(root_0, string_literal65_tree);

                                            pushFollow(FOLLOW_varDecls_in_actorDeclaration456);
                                            varDecls66=varDecls();

                                            state._fsp--;

                                            adaptor.addChild(root_0, varDecls66.getTree());

                                            }
                                            break;

                                    }

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:86: ( actionStatements )?
                                    int alt29=2;
                                    int LA29_0 = input.LA(1);

                                    if ( (LA29_0==30) ) {
                                        alt29=1;
                                    }
                                    switch (alt29) {
                                        case 1 :
                                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:109:86: actionStatements
                                            {
                                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration460);
                                            actionStatements67=actionStatements();

                                            state._fsp--;

                                            adaptor.addChild(root_0, actionStatements67.getTree());

                                            }
                                            break;

                                    }

                                    string_literal68=(Token)match(input,35,FOLLOW_35_in_actorDeclaration463); 
                                    string_literal68_tree = (Object)adaptor.create(string_literal68);
                                    adaptor.addChild(root_0, string_literal68_tree);

                                     

                                    }
                                    break;

                            }


                            }
                            break;
                        case 2 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:5: ( '[' ( typePar )+ ']' | '(' typeAttrs ')' )? ID ( '=' expression | ':=' expression )? ';'
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:5: ( '[' ( typePar )+ ']' | '(' typeAttrs ')' )?
                            int alt32=3;
                            int LA32_0 = input.LA(1);

                            if ( (LA32_0==26) ) {
                                alt32=1;
                            }
                            else if ( (LA32_0==32) ) {
                                alt32=2;
                            }
                            switch (alt32) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:6: '[' ( typePar )+ ']'
                                    {
                                    char_literal69=(Token)match(input,26,FOLLOW_26_in_actorDeclaration480); 
                                    char_literal69_tree = (Object)adaptor.create(char_literal69);
                                    adaptor.addChild(root_0, char_literal69_tree);

                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:10: ( typePar )+
                                    int cnt31=0;
                                    loop31:
                                    do {
                                        int alt31=2;
                                        int LA31_0 = input.LA(1);

                                        if ( (LA31_0==ID) ) {
                                            alt31=1;
                                        }


                                        switch (alt31) {
                                    	case 1 :
                                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:10: typePar
                                    	    {
                                    	    pushFollow(FOLLOW_typePar_in_actorDeclaration482);
                                    	    typePar70=typePar();

                                    	    state._fsp--;

                                    	    adaptor.addChild(root_0, typePar70.getTree());

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

                                    char_literal71=(Token)match(input,27,FOLLOW_27_in_actorDeclaration485); 
                                    char_literal71_tree = (Object)adaptor.create(char_literal71);
                                    adaptor.addChild(root_0, char_literal71_tree);


                                    }
                                    break;
                                case 2 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:25: '(' typeAttrs ')'
                                    {
                                    char_literal72=(Token)match(input,32,FOLLOW_32_in_actorDeclaration489); 
                                    char_literal72_tree = (Object)adaptor.create(char_literal72);
                                    adaptor.addChild(root_0, char_literal72_tree);

                                    pushFollow(FOLLOW_typeAttrs_in_actorDeclaration491);
                                    typeAttrs73=typeAttrs();

                                    state._fsp--;

                                    adaptor.addChild(root_0, typeAttrs73.getTree());
                                    char_literal74=(Token)match(input,33,FOLLOW_33_in_actorDeclaration493); 
                                    char_literal74_tree = (Object)adaptor.create(char_literal74);
                                    adaptor.addChild(root_0, char_literal74_tree);


                                    }
                                    break;

                            }

                            ID75=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration497); 
                            ID75_tree = (Object)adaptor.create(ID75);
                            adaptor.addChild(root_0, ID75_tree);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:48: ( '=' expression | ':=' expression )?
                            int alt33=3;
                            int LA33_0 = input.LA(1);

                            if ( (LA33_0==40) ) {
                                alt33=1;
                            }
                            else if ( (LA33_0==41) ) {
                                alt33=2;
                            }
                            switch (alt33) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:49: '=' expression
                                    {
                                    char_literal76=(Token)match(input,40,FOLLOW_40_in_actorDeclaration500); 
                                    char_literal76_tree = (Object)adaptor.create(char_literal76);
                                    adaptor.addChild(root_0, char_literal76_tree);

                                    pushFollow(FOLLOW_expression_in_actorDeclaration502);
                                    expression77=expression();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expression77.getTree());

                                    }
                                    break;
                                case 2 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:112:66: ':=' expression
                                    {
                                    string_literal78=(Token)match(input,41,FOLLOW_41_in_actorDeclaration506); 
                                    string_literal78_tree = (Object)adaptor.create(string_literal78);
                                    adaptor.addChild(root_0, string_literal78_tree);

                                    pushFollow(FOLLOW_expression_in_actorDeclaration508);
                                    expression79=expression();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expression79.getTree());

                                    }
                                    break;

                            }

                            char_literal80=(Token)match(input,42,FOLLOW_42_in_actorDeclaration512); 
                            char_literal80_tree = (Object)adaptor.create(char_literal80);
                            adaptor.addChild(root_0, char_literal80_tree);


                            }
                            break;

                    }

                     

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:3: 'action' ( actionInputs )? '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal81=(Token)match(input,37,FOLLOW_37_in_actorDeclaration521); 
                    string_literal81_tree = (Object)adaptor.create(string_literal81);
                    adaptor.addChild(root_0, string_literal81_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:12: ( actionInputs )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==ID||LA35_0==26) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:12: actionInputs
                            {
                            pushFollow(FOLLOW_actionInputs_in_actorDeclaration523);
                            actionInputs82=actionInputs();

                            state._fsp--;

                            adaptor.addChild(root_0, actionInputs82.getTree());

                            }
                            break;

                    }

                    string_literal83=(Token)match(input,34,FOLLOW_34_in_actorDeclaration526); 
                    string_literal83_tree = (Object)adaptor.create(string_literal83);
                    adaptor.addChild(root_0, string_literal83_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:32: ( actionOutputs )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==ID||LA36_0==26) ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:32: actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration528);
                            actionOutputs84=actionOutputs();

                            state._fsp--;

                            adaptor.addChild(root_0, actionOutputs84.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:47: ( actionGuards )?
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==24) ) {
                        alt37=1;
                    }
                    switch (alt37) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:47: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration531);
                            actionGuards85=actionGuards();

                            state._fsp--;

                            adaptor.addChild(root_0, actionGuards85.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:61: ( actionDelay )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==23) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:61: actionDelay
                            {
                            pushFollow(FOLLOW_actionDelay_in_actorDeclaration534);
                            actionDelay86=actionDelay();

                            state._fsp--;

                            adaptor.addChild(root_0, actionDelay86.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:74: ( 'var' varDecls )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==38) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:75: 'var' varDecls
                            {
                            string_literal87=(Token)match(input,38,FOLLOW_38_in_actorDeclaration538); 
                            string_literal87_tree = (Object)adaptor.create(string_literal87);
                            adaptor.addChild(root_0, string_literal87_tree);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration540);
                            varDecls88=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls88.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:92: ( actionStatements )?
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( (LA40_0==30) ) {
                        alt40=1;
                    }
                    switch (alt40) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:115:92: actionStatements
                            {
                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration544);
                            actionStatements89=actionStatements();

                            state._fsp--;

                            adaptor.addChild(root_0, actionStatements89.getTree());

                            }
                            break;

                    }

                    string_literal90=(Token)match(input,35,FOLLOW_35_in_actorDeclaration547); 
                    string_literal90_tree = (Object)adaptor.create(string_literal90);
                    adaptor.addChild(root_0, string_literal90_tree);

                     

                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:3: 'initialize' '==>' ( actionOutputs )? ( actionGuards )? ( actionDelay )? ( 'var' varDecls )? ( actionStatements )? 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal91=(Token)match(input,39,FOLLOW_39_in_actorDeclaration557); 
                    string_literal91_tree = (Object)adaptor.create(string_literal91);
                    adaptor.addChild(root_0, string_literal91_tree);

                    string_literal92=(Token)match(input,34,FOLLOW_34_in_actorDeclaration559); 
                    string_literal92_tree = (Object)adaptor.create(string_literal92);
                    adaptor.addChild(root_0, string_literal92_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:22: ( actionOutputs )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==ID||LA41_0==26) ) {
                        alt41=1;
                    }
                    switch (alt41) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:22: actionOutputs
                            {
                            pushFollow(FOLLOW_actionOutputs_in_actorDeclaration561);
                            actionOutputs93=actionOutputs();

                            state._fsp--;

                            adaptor.addChild(root_0, actionOutputs93.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:37: ( actionGuards )?
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==24) ) {
                        alt42=1;
                    }
                    switch (alt42) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:37: actionGuards
                            {
                            pushFollow(FOLLOW_actionGuards_in_actorDeclaration564);
                            actionGuards94=actionGuards();

                            state._fsp--;

                            adaptor.addChild(root_0, actionGuards94.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:51: ( actionDelay )?
                    int alt43=2;
                    int LA43_0 = input.LA(1);

                    if ( (LA43_0==23) ) {
                        alt43=1;
                    }
                    switch (alt43) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:51: actionDelay
                            {
                            pushFollow(FOLLOW_actionDelay_in_actorDeclaration567);
                            actionDelay95=actionDelay();

                            state._fsp--;

                            adaptor.addChild(root_0, actionDelay95.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:64: ( 'var' varDecls )?
                    int alt44=2;
                    int LA44_0 = input.LA(1);

                    if ( (LA44_0==38) ) {
                        alt44=1;
                    }
                    switch (alt44) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:65: 'var' varDecls
                            {
                            string_literal96=(Token)match(input,38,FOLLOW_38_in_actorDeclaration571); 
                            string_literal96_tree = (Object)adaptor.create(string_literal96);
                            adaptor.addChild(root_0, string_literal96_tree);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration573);
                            varDecls97=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls97.getTree());

                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:82: ( actionStatements )?
                    int alt45=2;
                    int LA45_0 = input.LA(1);

                    if ( (LA45_0==30) ) {
                        alt45=1;
                    }
                    switch (alt45) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:119:82: actionStatements
                            {
                            pushFollow(FOLLOW_actionStatements_in_actorDeclaration577);
                            actionStatements98=actionStatements();

                            state._fsp--;

                            adaptor.addChild(root_0, actionStatements98.getTree());

                            }
                            break;

                    }

                    string_literal99=(Token)match(input,35,FOLLOW_35_in_actorDeclaration580); 
                    string_literal99_tree = (Object)adaptor.create(string_literal99);
                    adaptor.addChild(root_0, string_literal99_tree);

                     

                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:122:3: priorityOrder
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_priorityOrder_in_actorDeclaration589);
                    priorityOrder100=priorityOrder();

                    state._fsp--;

                    adaptor.addChild(root_0, priorityOrder100.getTree());
                     

                    }
                    break;
                case 5 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:124:3: 'function' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' '-->' typeDef ( 'var' varDecls )? ':' expression 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal101=(Token)match(input,43,FOLLOW_43_in_actorDeclaration596); 
                    string_literal101_tree = (Object)adaptor.create(string_literal101);
                    adaptor.addChild(root_0, string_literal101_tree);

                    ID102=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration598); 
                    ID102_tree = (Object)adaptor.create(ID102);
                    adaptor.addChild(root_0, ID102_tree);

                    char_literal103=(Token)match(input,32,FOLLOW_32_in_actorDeclaration600); 
                    char_literal103_tree = (Object)adaptor.create(char_literal103);
                    adaptor.addChild(root_0, char_literal103_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:124:21: ( varDeclNoExpr ( ',' varDeclNoExpr )* )?
                    int alt47=2;
                    int LA47_0 = input.LA(1);

                    if ( (LA47_0==ID) ) {
                        alt47=1;
                    }
                    switch (alt47) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:124:22: varDeclNoExpr ( ',' varDeclNoExpr )*
                            {
                            pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration603);
                            varDeclNoExpr104=varDeclNoExpr();

                            state._fsp--;

                            adaptor.addChild(root_0, varDeclNoExpr104.getTree());
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:124:36: ( ',' varDeclNoExpr )*
                            loop46:
                            do {
                                int alt46=2;
                                int LA46_0 = input.LA(1);

                                if ( (LA46_0==28) ) {
                                    alt46=1;
                                }


                                switch (alt46) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:124:37: ',' varDeclNoExpr
                            	    {
                            	    char_literal105=(Token)match(input,28,FOLLOW_28_in_actorDeclaration606); 
                            	    char_literal105_tree = (Object)adaptor.create(char_literal105);
                            	    adaptor.addChild(root_0, char_literal105_tree);

                            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration608);
                            	    varDeclNoExpr106=varDeclNoExpr();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, varDeclNoExpr106.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop46;
                                }
                            } while (true);


                            }
                            break;

                    }

                    char_literal107=(Token)match(input,33,FOLLOW_33_in_actorDeclaration614); 
                    char_literal107_tree = (Object)adaptor.create(char_literal107);
                    adaptor.addChild(root_0, char_literal107_tree);

                    string_literal108=(Token)match(input,44,FOLLOW_44_in_actorDeclaration616); 
                    string_literal108_tree = (Object)adaptor.create(string_literal108);
                    adaptor.addChild(root_0, string_literal108_tree);

                    pushFollow(FOLLOW_typeDef_in_actorDeclaration618);
                    typeDef109=typeDef();

                    state._fsp--;

                    adaptor.addChild(root_0, typeDef109.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:5: ( 'var' varDecls )?
                    int alt48=2;
                    int LA48_0 = input.LA(1);

                    if ( (LA48_0==38) ) {
                        alt48=1;
                    }
                    switch (alt48) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:125:6: 'var' varDecls
                            {
                            string_literal110=(Token)match(input,38,FOLLOW_38_in_actorDeclaration625); 
                            string_literal110_tree = (Object)adaptor.create(string_literal110);
                            adaptor.addChild(root_0, string_literal110_tree);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration627);
                            varDecls111=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls111.getTree());

                            }
                            break;

                    }

                    char_literal112=(Token)match(input,25,FOLLOW_25_in_actorDeclaration631); 
                    char_literal112_tree = (Object)adaptor.create(char_literal112);
                    adaptor.addChild(root_0, char_literal112_tree);

                    pushFollow(FOLLOW_expression_in_actorDeclaration639);
                    expression113=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression113.getTree());
                    string_literal114=(Token)match(input,35,FOLLOW_35_in_actorDeclaration645); 
                    string_literal114_tree = (Object)adaptor.create(string_literal114);
                    adaptor.addChild(root_0, string_literal114_tree);

                     

                    }
                    break;
                case 6 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:3: 'procedure' ID '(' ( varDeclNoExpr ( ',' varDeclNoExpr )* )? ')' ( 'var' varDecls )? 'begin' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal115=(Token)match(input,45,FOLLOW_45_in_actorDeclaration653); 
                    string_literal115_tree = (Object)adaptor.create(string_literal115);
                    adaptor.addChild(root_0, string_literal115_tree);

                    ID116=(Token)match(input,ID,FOLLOW_ID_in_actorDeclaration655); 
                    ID116_tree = (Object)adaptor.create(ID116);
                    adaptor.addChild(root_0, ID116_tree);

                    char_literal117=(Token)match(input,32,FOLLOW_32_in_actorDeclaration657); 
                    char_literal117_tree = (Object)adaptor.create(char_literal117);
                    adaptor.addChild(root_0, char_literal117_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:22: ( varDeclNoExpr ( ',' varDeclNoExpr )* )?
                    int alt50=2;
                    int LA50_0 = input.LA(1);

                    if ( (LA50_0==ID) ) {
                        alt50=1;
                    }
                    switch (alt50) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:23: varDeclNoExpr ( ',' varDeclNoExpr )*
                            {
                            pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration660);
                            varDeclNoExpr118=varDeclNoExpr();

                            state._fsp--;

                            adaptor.addChild(root_0, varDeclNoExpr118.getTree());
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:37: ( ',' varDeclNoExpr )*
                            loop49:
                            do {
                                int alt49=2;
                                int LA49_0 = input.LA(1);

                                if ( (LA49_0==28) ) {
                                    alt49=1;
                                }


                                switch (alt49) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:130:38: ',' varDeclNoExpr
                            	    {
                            	    char_literal119=(Token)match(input,28,FOLLOW_28_in_actorDeclaration663); 
                            	    char_literal119_tree = (Object)adaptor.create(char_literal119);
                            	    adaptor.addChild(root_0, char_literal119_tree);

                            	    pushFollow(FOLLOW_varDeclNoExpr_in_actorDeclaration665);
                            	    varDeclNoExpr120=varDeclNoExpr();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, varDeclNoExpr120.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop49;
                                }
                            } while (true);


                            }
                            break;

                    }

                    char_literal121=(Token)match(input,33,FOLLOW_33_in_actorDeclaration671); 
                    char_literal121_tree = (Object)adaptor.create(char_literal121);
                    adaptor.addChild(root_0, char_literal121_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:5: ( 'var' varDecls )?
                    int alt51=2;
                    int LA51_0 = input.LA(1);

                    if ( (LA51_0==38) ) {
                        alt51=1;
                    }
                    switch (alt51) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:131:6: 'var' varDecls
                            {
                            string_literal122=(Token)match(input,38,FOLLOW_38_in_actorDeclaration678); 
                            string_literal122_tree = (Object)adaptor.create(string_literal122);
                            adaptor.addChild(root_0, string_literal122_tree);

                            pushFollow(FOLLOW_varDecls_in_actorDeclaration680);
                            varDecls123=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls123.getTree());

                            }
                            break;

                    }

                    string_literal124=(Token)match(input,46,FOLLOW_46_in_actorDeclaration688); 
                    string_literal124_tree = (Object)adaptor.create(string_literal124);
                    adaptor.addChild(root_0, string_literal124_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:132:13: ( statement )*
                    loop52:
                    do {
                        int alt52=2;
                        int LA52_0 = input.LA(1);

                        if ( (LA52_0==ID||LA52_0==46||LA52_0==71||LA52_0==76||(LA52_0>=82 && LA52_0<=83)||LA52_0==85) ) {
                            alt52=1;
                        }


                        switch (alt52) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:132:13: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_actorDeclaration690);
                    	    statement125=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement125.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop52;
                        }
                    } while (true);

                    string_literal126=(Token)match(input,35,FOLLOW_35_in_actorDeclaration693); 
                    string_literal126_tree = (Object)adaptor.create(string_literal126);
                    adaptor.addChild(root_0, string_literal126_tree);

                     

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:135:1: actorDeclarations : ( actorDeclaration )* ( schedule ( actorDeclaration )* )? ;
    public final RVCCalParser.actorDeclarations_return actorDeclarations() throws RecognitionException {
        RVCCalParser.actorDeclarations_return retval = new RVCCalParser.actorDeclarations_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        RVCCalParser.actorDeclaration_return actorDeclaration127 = null;

        RVCCalParser.schedule_return schedule128 = null;

        RVCCalParser.actorDeclaration_return actorDeclaration129 = null;



        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:135:18: ( ( actorDeclaration )* ( schedule ( actorDeclaration )* )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:135:20: ( actorDeclaration )* ( schedule ( actorDeclaration )* )?
            {
            root_0 = (Object)adaptor.nil();

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:135:20: ( actorDeclaration )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==ID||LA54_0==37||LA54_0==39||LA54_0==43||LA54_0==45||LA54_0==78) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:135:20: actorDeclaration
            	    {
            	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations703);
            	    actorDeclaration127=actorDeclaration();

            	    state._fsp--;

            	    adaptor.addChild(root_0, actorDeclaration127.getTree());

            	    }
            	    break;

            	default :
            	    break loop54;
                }
            } while (true);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:135:38: ( schedule ( actorDeclaration )* )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==79) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:135:39: schedule ( actorDeclaration )*
                    {
                    pushFollow(FOLLOW_schedule_in_actorDeclarations707);
                    schedule128=schedule();

                    state._fsp--;

                    adaptor.addChild(root_0, schedule128.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:135:48: ( actorDeclaration )*
                    loop55:
                    do {
                        int alt55=2;
                        int LA55_0 = input.LA(1);

                        if ( (LA55_0==ID||LA55_0==37||LA55_0==39||LA55_0==43||LA55_0==45||LA55_0==78) ) {
                            alt55=1;
                        }


                        switch (alt55) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:135:48: actorDeclaration
                    	    {
                    	    pushFollow(FOLLOW_actorDeclaration_in_actorDeclarations709);
                    	    actorDeclaration129=actorDeclaration();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, actorDeclaration129.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop55;
                        }
                    } while (true);


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
    // $ANTLR end "actorDeclarations"

    public static class actorImport_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actorImport"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:137:1: actorImport : 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) ;
    public final RVCCalParser.actorImport_return actorImport() throws RecognitionException {
        RVCCalParser.actorImport_return retval = new RVCCalParser.actorImport_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal130=null;
        Token string_literal131=null;
        Token char_literal133=null;
        Token char_literal135=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent132 = null;

        RVCCalParser.qualifiedIdent_return qualifiedIdent134 = null;


        Object string_literal130_tree=null;
        Object string_literal131_tree=null;
        Object char_literal133_tree=null;
        Object char_literal135_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:140:12: ( 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:140:14: 'import' ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            {
            root_0 = (Object)adaptor.nil();

            string_literal130=(Token)match(input,47,FOLLOW_47_in_actorImport726); 
            string_literal130_tree = (Object)adaptor.create(string_literal130);
            adaptor.addChild(root_0, string_literal130_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:141:3: ( 'all' qualifiedIdent ';' | qualifiedIdent ';' )
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==19) ) {
                alt57=1;
            }
            else if ( (LA57_0==ID) ) {
                alt57=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;
            }
            switch (alt57) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:141:4: 'all' qualifiedIdent ';'
                    {
                    string_literal131=(Token)match(input,19,FOLLOW_19_in_actorImport731); 
                    string_literal131_tree = (Object)adaptor.create(string_literal131);
                    adaptor.addChild(root_0, string_literal131_tree);

                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport733);
                    qualifiedIdent132=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent132.getTree());
                    char_literal133=(Token)match(input,42,FOLLOW_42_in_actorImport735); 
                    char_literal133_tree = (Object)adaptor.create(char_literal133);
                    adaptor.addChild(root_0, char_literal133_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:142:3: qualifiedIdent ';'
                    {
                    pushFollow(FOLLOW_qualifiedIdent_in_actorImport741);
                    qualifiedIdent134=qualifiedIdent();

                    state._fsp--;

                    adaptor.addChild(root_0, qualifiedIdent134.getTree());
                    char_literal135=(Token)match(input,42,FOLLOW_42_in_actorImport743); 
                    char_literal135_tree = (Object)adaptor.create(char_literal135);
                    adaptor.addChild(root_0, char_literal135_tree);

                     

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:144:1: actorParameter : typeDef ID ( '=' expression )? ;
    public final RVCCalParser.actorParameter_return actorParameter() throws RecognitionException {
        RVCCalParser.actorParameter_return retval = new RVCCalParser.actorParameter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID137=null;
        Token char_literal138=null;
        RVCCalParser.typeDef_return typeDef136 = null;

        RVCCalParser.expression_return expression139 = null;


        Object ID137_tree=null;
        Object char_literal138_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:146:15: ( typeDef ID ( '=' expression )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:147:2: typeDef ID ( '=' expression )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_typeDef_in_actorParameter758);
            typeDef136=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef136.getTree());
            ID137=(Token)match(input,ID,FOLLOW_ID_in_actorParameter760); 
            ID137_tree = (Object)adaptor.create(ID137);
            adaptor.addChild(root_0, ID137_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:147:13: ( '=' expression )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==40) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:147:14: '=' expression
                    {
                    char_literal138=(Token)match(input,40,FOLLOW_40_in_actorParameter763); 
                    char_literal138_tree = (Object)adaptor.create(char_literal138);
                    adaptor.addChild(root_0, char_literal138_tree);

                    pushFollow(FOLLOW_expression_in_actorParameter765);
                    expression139=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression139.getTree());

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
    // $ANTLR end "actorParameter"

    public static class actorParameters_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actorParameters"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:1: actorParameters : actorParameter ( ',' actorParameter )* ;
    public final RVCCalParser.actorParameters_return actorParameters() throws RecognitionException {
        RVCCalParser.actorParameters_return retval = new RVCCalParser.actorParameters_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal141=null;
        RVCCalParser.actorParameter_return actorParameter140 = null;

        RVCCalParser.actorParameter_return actorParameter142 = null;


        Object char_literal141_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:16: ( actorParameter ( ',' actorParameter )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:18: actorParameter ( ',' actorParameter )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_actorParameter_in_actorParameters776);
            actorParameter140=actorParameter();

            state._fsp--;

            adaptor.addChild(root_0, actorParameter140.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:33: ( ',' actorParameter )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==28) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:149:34: ',' actorParameter
            	    {
            	    char_literal141=(Token)match(input,28,FOLLOW_28_in_actorParameters779); 
            	    char_literal141_tree = (Object)adaptor.create(char_literal141);
            	    adaptor.addChild(root_0, char_literal141_tree);

            	    pushFollow(FOLLOW_actorParameter_in_actorParameters781);
            	    actorParameter142=actorParameter();

            	    state._fsp--;

            	    adaptor.addChild(root_0, actorParameter142.getTree());

            	    }
            	    break;

            	default :
            	    break loop59;
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
    // $ANTLR end "actorParameters"

    public static class actorPortDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actorPortDecl"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:151:1: actorPortDecl : ( 'multi' typeDef ID | typeDef ID -> ^( PORT typeDef ID ) );
    public final RVCCalParser.actorPortDecl_return actorPortDecl() throws RecognitionException {
        RVCCalParser.actorPortDecl_return retval = new RVCCalParser.actorPortDecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal143=null;
        Token ID145=null;
        Token ID147=null;
        RVCCalParser.typeDef_return typeDef144 = null;

        RVCCalParser.typeDef_return typeDef146 = null;


        Object string_literal143_tree=null;
        Object ID145_tree=null;
        Object ID147_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:154:14: ( 'multi' typeDef ID | typeDef ID -> ^( PORT typeDef ID ) )
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==48) ) {
                alt60=1;
            }
            else if ( (LA60_0==ID) ) {
                alt60=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;
            }
            switch (alt60) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:155:2: 'multi' typeDef ID
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal143=(Token)match(input,48,FOLLOW_48_in_actorPortDecl798); 
                    string_literal143_tree = (Object)adaptor.create(string_literal143);
                    adaptor.addChild(root_0, string_literal143_tree);

                    pushFollow(FOLLOW_typeDef_in_actorPortDecl800);
                    typeDef144=typeDef();

                    state._fsp--;

                    adaptor.addChild(root_0, typeDef144.getTree());
                    ID145=(Token)match(input,ID,FOLLOW_ID_in_actorPortDecl802); 
                    ID145_tree = (Object)adaptor.create(ID145);
                    adaptor.addChild(root_0, ID145_tree);

                     System.out.println("RVC-CAL does not permit the use of multi ports."); 

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:157:3: typeDef ID
                    {
                    pushFollow(FOLLOW_typeDef_in_actorPortDecl809);
                    typeDef146=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef146.getTree());
                    ID147=(Token)match(input,ID,FOLLOW_ID_in_actorPortDecl811);  
                    stream_ID.add(ID147);



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
                    // 157:14: -> ^( PORT typeDef ID )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:157:17: ^( PORT typeDef ID )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:159:1: actorPortDecls : actorPortDecl ( ',' actorPortDecl )* -> ( actorPortDecl )+ ;
    public final RVCCalParser.actorPortDecls_return actorPortDecls() throws RecognitionException {
        RVCCalParser.actorPortDecls_return retval = new RVCCalParser.actorPortDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal149=null;
        RVCCalParser.actorPortDecl_return actorPortDecl148 = null;

        RVCCalParser.actorPortDecl_return actorPortDecl150 = null;


        Object char_literal149_tree=null;
        RewriteRuleTokenStream stream_28=new RewriteRuleTokenStream(adaptor,"token 28");
        RewriteRuleSubtreeStream stream_actorPortDecl=new RewriteRuleSubtreeStream(adaptor,"rule actorPortDecl");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:159:15: ( actorPortDecl ( ',' actorPortDecl )* -> ( actorPortDecl )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:159:17: actorPortDecl ( ',' actorPortDecl )*
            {
            pushFollow(FOLLOW_actorPortDecl_in_actorPortDecls828);
            actorPortDecl148=actorPortDecl();

            state._fsp--;

            stream_actorPortDecl.add(actorPortDecl148.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:159:31: ( ',' actorPortDecl )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==28) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:159:32: ',' actorPortDecl
            	    {
            	    char_literal149=(Token)match(input,28,FOLLOW_28_in_actorPortDecls831);  
            	    stream_28.add(char_literal149);

            	    pushFollow(FOLLOW_actorPortDecl_in_actorPortDecls833);
            	    actorPortDecl150=actorPortDecl();

            	    state._fsp--;

            	    stream_actorPortDecl.add(actorPortDecl150.getTree());

            	    }
            	    break;

            	default :
            	    break loop61;
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
            // 159:52: -> ( actorPortDecl )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:161:1: expression : and_expr ( ( 'or' | '||' ) and_expr )? ;
    public final RVCCalParser.expression_return expression() throws RecognitionException {
        RVCCalParser.expression_return retval = new RVCCalParser.expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set152=null;
        RVCCalParser.and_expr_return and_expr151 = null;

        RVCCalParser.and_expr_return and_expr153 = null;


        Object set152_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:11: ( and_expr ( ( 'or' | '||' ) and_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:13: and_expr ( ( 'or' | '||' ) and_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_and_expr_in_expression852);
            and_expr151=and_expr();

            state._fsp--;

            adaptor.addChild(root_0, and_expr151.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:22: ( ( 'or' | '||' ) and_expr )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( ((LA62_0>=49 && LA62_0<=50)) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:164:23: ( 'or' | '||' ) and_expr
                    {
                    set152=(Token)input.LT(1);
                    if ( (input.LA(1)>=49 && input.LA(1)<=50) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set152));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_and_expr_in_expression863);
                    and_expr153=and_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, and_expr153.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:1: and_expr : bitor_expr ( ( 'and' | '&&' ) bitor_expr )? ;
    public final RVCCalParser.and_expr_return and_expr() throws RecognitionException {
        RVCCalParser.and_expr_return retval = new RVCCalParser.and_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set155=null;
        RVCCalParser.bitor_expr_return bitor_expr154 = null;

        RVCCalParser.bitor_expr_return bitor_expr156 = null;


        Object set155_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:9: ( bitor_expr ( ( 'and' | '&&' ) bitor_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:11: bitor_expr ( ( 'and' | '&&' ) bitor_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_bitor_expr_in_and_expr874);
            bitor_expr154=bitor_expr();

            state._fsp--;

            adaptor.addChild(root_0, bitor_expr154.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:22: ( ( 'and' | '&&' ) bitor_expr )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( ((LA63_0>=51 && LA63_0<=52)) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:166:23: ( 'and' | '&&' ) bitor_expr
                    {
                    set155=(Token)input.LT(1);
                    if ( (input.LA(1)>=51 && input.LA(1)<=52) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set155));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_bitor_expr_in_and_expr885);
                    bitor_expr156=bitor_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, bitor_expr156.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:1: bitor_expr : bitand_expr ( '|' bitand_expr )? ;
    public final RVCCalParser.bitor_expr_return bitor_expr() throws RecognitionException {
        RVCCalParser.bitor_expr_return retval = new RVCCalParser.bitor_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal158=null;
        RVCCalParser.bitand_expr_return bitand_expr157 = null;

        RVCCalParser.bitand_expr_return bitand_expr159 = null;


        Object char_literal158_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:11: ( bitand_expr ( '|' bitand_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:13: bitand_expr ( '|' bitand_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_bitand_expr_in_bitor_expr896);
            bitand_expr157=bitand_expr();

            state._fsp--;

            adaptor.addChild(root_0, bitand_expr157.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:25: ( '|' bitand_expr )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==53) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:168:26: '|' bitand_expr
                    {
                    char_literal158=(Token)match(input,53,FOLLOW_53_in_bitor_expr899); 
                    char_literal158_tree = (Object)adaptor.create(char_literal158);
                    adaptor.addChild(root_0, char_literal158_tree);

                    pushFollow(FOLLOW_bitand_expr_in_bitor_expr901);
                    bitand_expr159=bitand_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, bitand_expr159.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:1: bitand_expr : eq_expr ( '&' eq_expr )? ;
    public final RVCCalParser.bitand_expr_return bitand_expr() throws RecognitionException {
        RVCCalParser.bitand_expr_return retval = new RVCCalParser.bitand_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal161=null;
        RVCCalParser.eq_expr_return eq_expr160 = null;

        RVCCalParser.eq_expr_return eq_expr162 = null;


        Object char_literal161_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:12: ( eq_expr ( '&' eq_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:14: eq_expr ( '&' eq_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_eq_expr_in_bitand_expr912);
            eq_expr160=eq_expr();

            state._fsp--;

            adaptor.addChild(root_0, eq_expr160.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:22: ( '&' eq_expr )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==54) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:170:23: '&' eq_expr
                    {
                    char_literal161=(Token)match(input,54,FOLLOW_54_in_bitand_expr915); 
                    char_literal161_tree = (Object)adaptor.create(char_literal161);
                    adaptor.addChild(root_0, char_literal161_tree);

                    pushFollow(FOLLOW_eq_expr_in_bitand_expr917);
                    eq_expr162=eq_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, eq_expr162.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:1: eq_expr : rel_expr ( ( '=' | '!=' ) rel_expr )? ;
    public final RVCCalParser.eq_expr_return eq_expr() throws RecognitionException {
        RVCCalParser.eq_expr_return retval = new RVCCalParser.eq_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set164=null;
        RVCCalParser.rel_expr_return rel_expr163 = null;

        RVCCalParser.rel_expr_return rel_expr165 = null;


        Object set164_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:8: ( rel_expr ( ( '=' | '!=' ) rel_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:10: rel_expr ( ( '=' | '!=' ) rel_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_rel_expr_in_eq_expr928);
            rel_expr163=rel_expr();

            state._fsp--;

            adaptor.addChild(root_0, rel_expr163.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:19: ( ( '=' | '!=' ) rel_expr )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==40||LA66_0==55) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:172:20: ( '=' | '!=' ) rel_expr
                    {
                    set164=(Token)input.LT(1);
                    if ( input.LA(1)==40||input.LA(1)==55 ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set164));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_rel_expr_in_eq_expr939);
                    rel_expr165=rel_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, rel_expr165.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:174:1: rel_expr : shift_expr ( ( '<' | '>' | '<=' | '>=' ) shift_expr )? ;
    public final RVCCalParser.rel_expr_return rel_expr() throws RecognitionException {
        RVCCalParser.rel_expr_return retval = new RVCCalParser.rel_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set167=null;
        RVCCalParser.shift_expr_return shift_expr166 = null;

        RVCCalParser.shift_expr_return shift_expr168 = null;


        Object set167_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:174:9: ( shift_expr ( ( '<' | '>' | '<=' | '>=' ) shift_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:174:11: shift_expr ( ( '<' | '>' | '<=' | '>=' ) shift_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_shift_expr_in_rel_expr950);
            shift_expr166=shift_expr();

            state._fsp--;

            adaptor.addChild(root_0, shift_expr166.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:174:22: ( ( '<' | '>' | '<=' | '>=' ) shift_expr )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( ((LA67_0>=56 && LA67_0<=59)) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:174:23: ( '<' | '>' | '<=' | '>=' ) shift_expr
                    {
                    set167=(Token)input.LT(1);
                    if ( (input.LA(1)>=56 && input.LA(1)<=59) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set167));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_shift_expr_in_rel_expr969);
                    shift_expr168=shift_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, shift_expr168.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:1: shift_expr : add_expr ( ( '<<' | '>>' ) add_expr )? ;
    public final RVCCalParser.shift_expr_return shift_expr() throws RecognitionException {
        RVCCalParser.shift_expr_return retval = new RVCCalParser.shift_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set170=null;
        RVCCalParser.add_expr_return add_expr169 = null;

        RVCCalParser.add_expr_return add_expr171 = null;


        Object set170_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:11: ( add_expr ( ( '<<' | '>>' ) add_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:13: add_expr ( ( '<<' | '>>' ) add_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_add_expr_in_shift_expr980);
            add_expr169=add_expr();

            state._fsp--;

            adaptor.addChild(root_0, add_expr169.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:22: ( ( '<<' | '>>' ) add_expr )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( ((LA68_0>=60 && LA68_0<=61)) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:176:23: ( '<<' | '>>' ) add_expr
                    {
                    set170=(Token)input.LT(1);
                    if ( (input.LA(1)>=60 && input.LA(1)<=61) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set170));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_add_expr_in_shift_expr991);
                    add_expr171=add_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, add_expr171.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:1: add_expr : mul_expr ( ( '+' | '-' ) mul_expr )? ;
    public final RVCCalParser.add_expr_return add_expr() throws RecognitionException {
        RVCCalParser.add_expr_return retval = new RVCCalParser.add_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set173=null;
        RVCCalParser.mul_expr_return mul_expr172 = null;

        RVCCalParser.mul_expr_return mul_expr174 = null;


        Object set173_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:9: ( mul_expr ( ( '+' | '-' ) mul_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:11: mul_expr ( ( '+' | '-' ) mul_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_mul_expr_in_add_expr1002);
            mul_expr172=mul_expr();

            state._fsp--;

            adaptor.addChild(root_0, mul_expr172.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:20: ( ( '+' | '-' ) mul_expr )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( ((LA69_0>=62 && LA69_0<=63)) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:178:21: ( '+' | '-' ) mul_expr
                    {
                    set173=(Token)input.LT(1);
                    if ( (input.LA(1)>=62 && input.LA(1)<=63) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set173));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_mul_expr_in_add_expr1013);
                    mul_expr174=mul_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, mul_expr174.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:180:1: mul_expr : exp_expr ( ( 'div' | 'mod' | '*' | '/' ) exp_expr )? ;
    public final RVCCalParser.mul_expr_return mul_expr() throws RecognitionException {
        RVCCalParser.mul_expr_return retval = new RVCCalParser.mul_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set176=null;
        RVCCalParser.exp_expr_return exp_expr175 = null;

        RVCCalParser.exp_expr_return exp_expr177 = null;


        Object set176_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:180:9: ( exp_expr ( ( 'div' | 'mod' | '*' | '/' ) exp_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:180:11: exp_expr ( ( 'div' | 'mod' | '*' | '/' ) exp_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_exp_expr_in_mul_expr1024);
            exp_expr175=exp_expr();

            state._fsp--;

            adaptor.addChild(root_0, exp_expr175.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:180:20: ( ( 'div' | 'mod' | '*' | '/' ) exp_expr )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( ((LA70_0>=64 && LA70_0<=67)) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:180:21: ( 'div' | 'mod' | '*' | '/' ) exp_expr
                    {
                    set176=(Token)input.LT(1);
                    if ( (input.LA(1)>=64 && input.LA(1)<=67) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set176));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_exp_expr_in_mul_expr1043);
                    exp_expr177=exp_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, exp_expr177.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:182:1: exp_expr : un_expr ( '^' un_expr )? ;
    public final RVCCalParser.exp_expr_return exp_expr() throws RecognitionException {
        RVCCalParser.exp_expr_return retval = new RVCCalParser.exp_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal179=null;
        RVCCalParser.un_expr_return un_expr178 = null;

        RVCCalParser.un_expr_return un_expr180 = null;


        Object char_literal179_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:182:9: ( un_expr ( '^' un_expr )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:182:11: un_expr ( '^' un_expr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_un_expr_in_exp_expr1054);
            un_expr178=un_expr();

            state._fsp--;

            adaptor.addChild(root_0, un_expr178.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:182:19: ( '^' un_expr )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==68) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:182:20: '^' un_expr
                    {
                    char_literal179=(Token)match(input,68,FOLLOW_68_in_exp_expr1057); 
                    char_literal179_tree = (Object)adaptor.create(char_literal179);
                    adaptor.addChild(root_0, char_literal179_tree);

                    pushFollow(FOLLOW_un_expr_in_exp_expr1059);
                    un_expr180=un_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, un_expr180.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:1: un_expr : ( postfix_expression | '-' un_expr | 'not' un_expr | '#' un_expr );
    public final RVCCalParser.un_expr_return un_expr() throws RecognitionException {
        RVCCalParser.un_expr_return retval = new RVCCalParser.un_expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal182=null;
        Token string_literal184=null;
        Token char_literal186=null;
        RVCCalParser.postfix_expression_return postfix_expression181 = null;

        RVCCalParser.un_expr_return un_expr183 = null;

        RVCCalParser.un_expr_return un_expr185 = null;

        RVCCalParser.un_expr_return un_expr187 = null;


        Object char_literal182_tree=null;
        Object string_literal184_tree=null;
        Object char_literal186_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:8: ( postfix_expression | '-' un_expr | 'not' un_expr | '#' un_expr )
            int alt72=4;
            switch ( input.LA(1) ) {
            case ID:
            case INTEGER:
            case STRING:
            case 26:
            case 32:
            case 71:
            case 74:
            case 75:
                {
                alt72=1;
                }
                break;
            case 63:
                {
                alt72=2;
                }
                break;
            case 69:
                {
                alt72=3;
                }
                break;
            case 70:
                {
                alt72=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 72, 0, input);

                throw nvae;
            }

            switch (alt72) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:184:10: postfix_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_postfix_expression_in_un_expr1070);
                    postfix_expression181=postfix_expression();

                    state._fsp--;

                    adaptor.addChild(root_0, postfix_expression181.getTree());

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:185:4: '-' un_expr
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal182=(Token)match(input,63,FOLLOW_63_in_un_expr1075); 
                    char_literal182_tree = (Object)adaptor.create(char_literal182);
                    adaptor.addChild(root_0, char_literal182_tree);

                    pushFollow(FOLLOW_un_expr_in_un_expr1077);
                    un_expr183=un_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, un_expr183.getTree());

                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:186:4: 'not' un_expr
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal184=(Token)match(input,69,FOLLOW_69_in_un_expr1082); 
                    string_literal184_tree = (Object)adaptor.create(string_literal184);
                    adaptor.addChild(root_0, string_literal184_tree);

                    pushFollow(FOLLOW_un_expr_in_un_expr1084);
                    un_expr185=un_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, un_expr185.getTree());

                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:187:4: '#' un_expr
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal186=(Token)match(input,70,FOLLOW_70_in_un_expr1089); 
                    char_literal186_tree = (Object)adaptor.create(char_literal186);
                    adaptor.addChild(root_0, char_literal186_tree);

                    pushFollow(FOLLOW_un_expr_in_un_expr1091);
                    un_expr187=un_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, un_expr187.getTree());
                     

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:1: postfix_expression : ( '[' expressions ( ':' expressionGenerators )? ']' | 'if' expression 'then' expression 'else' expression 'end' | constant | '(' expression ')' | ID ( '(' ( expressions )? ')' | ( '[' expressions ']' )+ )? );
    public final RVCCalParser.postfix_expression_return postfix_expression() throws RecognitionException {
        RVCCalParser.postfix_expression_return retval = new RVCCalParser.postfix_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal188=null;
        Token char_literal190=null;
        Token char_literal192=null;
        Token string_literal193=null;
        Token string_literal195=null;
        Token string_literal197=null;
        Token string_literal199=null;
        Token char_literal201=null;
        Token char_literal203=null;
        Token ID204=null;
        Token char_literal205=null;
        Token char_literal207=null;
        Token char_literal208=null;
        Token char_literal210=null;
        RVCCalParser.expressions_return expressions189 = null;

        RVCCalParser.expressionGenerators_return expressionGenerators191 = null;

        RVCCalParser.expression_return expression194 = null;

        RVCCalParser.expression_return expression196 = null;

        RVCCalParser.expression_return expression198 = null;

        RVCCalParser.constant_return constant200 = null;

        RVCCalParser.expression_return expression202 = null;

        RVCCalParser.expressions_return expressions206 = null;

        RVCCalParser.expressions_return expressions209 = null;


        Object char_literal188_tree=null;
        Object char_literal190_tree=null;
        Object char_literal192_tree=null;
        Object string_literal193_tree=null;
        Object string_literal195_tree=null;
        Object string_literal197_tree=null;
        Object string_literal199_tree=null;
        Object char_literal201_tree=null;
        Object char_literal203_tree=null;
        Object ID204_tree=null;
        Object char_literal205_tree=null;
        Object char_literal207_tree=null;
        Object char_literal208_tree=null;
        Object char_literal210_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:189:19: ( '[' expressions ( ':' expressionGenerators )? ']' | 'if' expression 'then' expression 'else' expression 'end' | constant | '(' expression ')' | ID ( '(' ( expressions )? ')' | ( '[' expressions ']' )+ )? )
            int alt77=5;
            switch ( input.LA(1) ) {
            case 26:
                {
                alt77=1;
                }
                break;
            case 71:
                {
                alt77=2;
                }
                break;
            case INTEGER:
            case STRING:
            case 74:
            case 75:
                {
                alt77=3;
                }
                break;
            case 32:
                {
                alt77=4;
                }
                break;
            case ID:
                {
                alt77=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 77, 0, input);

                throw nvae;
            }

            switch (alt77) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:190:3: '[' expressions ( ':' expressionGenerators )? ']'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal188=(Token)match(input,26,FOLLOW_26_in_postfix_expression1102); 
                    char_literal188_tree = (Object)adaptor.create(char_literal188);
                    adaptor.addChild(root_0, char_literal188_tree);

                    pushFollow(FOLLOW_expressions_in_postfix_expression1104);
                    expressions189=expressions();

                    state._fsp--;

                    adaptor.addChild(root_0, expressions189.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:190:19: ( ':' expressionGenerators )?
                    int alt73=2;
                    int LA73_0 = input.LA(1);

                    if ( (LA73_0==25) ) {
                        alt73=1;
                    }
                    switch (alt73) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:190:20: ':' expressionGenerators
                            {
                            char_literal190=(Token)match(input,25,FOLLOW_25_in_postfix_expression1107); 
                            char_literal190_tree = (Object)adaptor.create(char_literal190);
                            adaptor.addChild(root_0, char_literal190_tree);

                            pushFollow(FOLLOW_expressionGenerators_in_postfix_expression1109);
                            expressionGenerators191=expressionGenerators();

                            state._fsp--;

                            adaptor.addChild(root_0, expressionGenerators191.getTree());

                            }
                            break;

                    }

                    char_literal192=(Token)match(input,27,FOLLOW_27_in_postfix_expression1113); 
                    char_literal192_tree = (Object)adaptor.create(char_literal192);
                    adaptor.addChild(root_0, char_literal192_tree);


                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:191:3: 'if' expression 'then' expression 'else' expression 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal193=(Token)match(input,71,FOLLOW_71_in_postfix_expression1117); 
                    string_literal193_tree = (Object)adaptor.create(string_literal193);
                    adaptor.addChild(root_0, string_literal193_tree);

                    pushFollow(FOLLOW_expression_in_postfix_expression1119);
                    expression194=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression194.getTree());
                    string_literal195=(Token)match(input,72,FOLLOW_72_in_postfix_expression1121); 
                    string_literal195_tree = (Object)adaptor.create(string_literal195);
                    adaptor.addChild(root_0, string_literal195_tree);

                    pushFollow(FOLLOW_expression_in_postfix_expression1123);
                    expression196=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression196.getTree());
                    string_literal197=(Token)match(input,73,FOLLOW_73_in_postfix_expression1125); 
                    string_literal197_tree = (Object)adaptor.create(string_literal197);
                    adaptor.addChild(root_0, string_literal197_tree);

                    pushFollow(FOLLOW_expression_in_postfix_expression1127);
                    expression198=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression198.getTree());
                    string_literal199=(Token)match(input,35,FOLLOW_35_in_postfix_expression1129); 
                    string_literal199_tree = (Object)adaptor.create(string_literal199);
                    adaptor.addChild(root_0, string_literal199_tree);


                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:192:3: constant
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_constant_in_postfix_expression1133);
                    constant200=constant();

                    state._fsp--;

                    adaptor.addChild(root_0, constant200.getTree());

                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:193:3: '(' expression ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal201=(Token)match(input,32,FOLLOW_32_in_postfix_expression1137); 
                    char_literal201_tree = (Object)adaptor.create(char_literal201);
                    adaptor.addChild(root_0, char_literal201_tree);

                    pushFollow(FOLLOW_expression_in_postfix_expression1139);
                    expression202=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression202.getTree());
                    char_literal203=(Token)match(input,33,FOLLOW_33_in_postfix_expression1141); 
                    char_literal203_tree = (Object)adaptor.create(char_literal203);
                    adaptor.addChild(root_0, char_literal203_tree);


                    }
                    break;
                case 5 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:194:3: ID ( '(' ( expressions )? ')' | ( '[' expressions ']' )+ )?
                    {
                    root_0 = (Object)adaptor.nil();

                    ID204=(Token)match(input,ID,FOLLOW_ID_in_postfix_expression1145); 
                    ID204_tree = (Object)adaptor.create(ID204);
                    adaptor.addChild(root_0, ID204_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:194:6: ( '(' ( expressions )? ')' | ( '[' expressions ']' )+ )?
                    int alt76=3;
                    int LA76_0 = input.LA(1);

                    if ( (LA76_0==32) ) {
                        alt76=1;
                    }
                    else if ( (LA76_0==26) ) {
                        alt76=2;
                    }
                    switch (alt76) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:5: '(' ( expressions )? ')'
                            {
                            char_literal205=(Token)match(input,32,FOLLOW_32_in_postfix_expression1153); 
                            char_literal205_tree = (Object)adaptor.create(char_literal205);
                            adaptor.addChild(root_0, char_literal205_tree);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:9: ( expressions )?
                            int alt74=2;
                            int LA74_0 = input.LA(1);

                            if ( ((LA74_0>=ID && LA74_0<=STRING)||LA74_0==26||LA74_0==32||LA74_0==63||(LA74_0>=69 && LA74_0<=71)||(LA74_0>=74 && LA74_0<=75)) ) {
                                alt74=1;
                            }
                            switch (alt74) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:195:9: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_postfix_expression1155);
                                    expressions206=expressions();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expressions206.getTree());

                                    }
                                    break;

                            }

                            char_literal207=(Token)match(input,33,FOLLOW_33_in_postfix_expression1158); 
                            char_literal207_tree = (Object)adaptor.create(char_literal207);
                            adaptor.addChild(root_0, char_literal207_tree);


                            }
                            break;
                        case 2 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:196:6: ( '[' expressions ']' )+
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:196:6: ( '[' expressions ']' )+
                            int cnt75=0;
                            loop75:
                            do {
                                int alt75=2;
                                int LA75_0 = input.LA(1);

                                if ( (LA75_0==26) ) {
                                    alt75=1;
                                }


                                switch (alt75) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:196:7: '[' expressions ']'
                            	    {
                            	    char_literal208=(Token)match(input,26,FOLLOW_26_in_postfix_expression1166); 
                            	    char_literal208_tree = (Object)adaptor.create(char_literal208);
                            	    adaptor.addChild(root_0, char_literal208_tree);

                            	    pushFollow(FOLLOW_expressions_in_postfix_expression1168);
                            	    expressions209=expressions();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, expressions209.getTree());
                            	    char_literal210=(Token)match(input,27,FOLLOW_27_in_postfix_expression1170); 
                            	    char_literal210_tree = (Object)adaptor.create(char_literal210);
                            	    adaptor.addChild(root_0, char_literal210_tree);


                            	    }
                            	    break;

                            	default :
                            	    if ( cnt75 >= 1 ) break loop75;
                                        EarlyExitException eee =
                                            new EarlyExitException(75, input);
                                        throw eee;
                                }
                                cnt75++;
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:198:1: constant : ( 'true' | 'false' | INTEGER | STRING );
    public final RVCCalParser.constant_return constant() throws RecognitionException {
        RVCCalParser.constant_return retval = new RVCCalParser.constant_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal211=null;
        Token string_literal212=null;
        Token INTEGER213=null;
        Token STRING214=null;

        Object string_literal211_tree=null;
        Object string_literal212_tree=null;
        Object INTEGER213_tree=null;
        Object STRING214_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:198:9: ( 'true' | 'false' | INTEGER | STRING )
            int alt78=4;
            switch ( input.LA(1) ) {
            case 74:
                {
                alt78=1;
                }
                break;
            case 75:
                {
                alt78=2;
                }
                break;
            case INTEGER:
                {
                alt78=3;
                }
                break;
            case STRING:
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:199:3: 'true'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal211=(Token)match(input,74,FOLLOW_74_in_constant1184); 
                    string_literal211_tree = (Object)adaptor.create(string_literal211);
                    adaptor.addChild(root_0, string_literal211_tree);


                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:200:3: 'false'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal212=(Token)match(input,75,FOLLOW_75_in_constant1188); 
                    string_literal212_tree = (Object)adaptor.create(string_literal212);
                    adaptor.addChild(root_0, string_literal212_tree);


                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:201:3: INTEGER
                    {
                    root_0 = (Object)adaptor.nil();

                    INTEGER213=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_constant1192); 
                    INTEGER213_tree = (Object)adaptor.create(INTEGER213);
                    adaptor.addChild(root_0, INTEGER213_tree);


                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:202:3: STRING
                    {
                    root_0 = (Object)adaptor.nil();

                    STRING214=(Token)match(input,STRING,FOLLOW_STRING_in_constant1196); 
                    STRING214_tree = (Object)adaptor.create(STRING214);
                    adaptor.addChild(root_0, STRING214_tree);

                     

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:204:1: expressionGenerator : 'for' typeDef ID 'in' expression ;
    public final RVCCalParser.expressionGenerator_return expressionGenerator() throws RecognitionException {
        RVCCalParser.expressionGenerator_return retval = new RVCCalParser.expressionGenerator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal215=null;
        Token ID217=null;
        Token string_literal218=null;
        RVCCalParser.typeDef_return typeDef216 = null;

        RVCCalParser.expression_return expression219 = null;


        Object string_literal215_tree=null;
        Object ID217_tree=null;
        Object string_literal218_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:204:20: ( 'for' typeDef ID 'in' expression )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:205:2: 'for' typeDef ID 'in' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal215=(Token)match(input,76,FOLLOW_76_in_expressionGenerator1206); 
            string_literal215_tree = (Object)adaptor.create(string_literal215);
            adaptor.addChild(root_0, string_literal215_tree);

            pushFollow(FOLLOW_typeDef_in_expressionGenerator1208);
            typeDef216=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef216.getTree());
            ID217=(Token)match(input,ID,FOLLOW_ID_in_expressionGenerator1210); 
            ID217_tree = (Object)adaptor.create(ID217);
            adaptor.addChild(root_0, ID217_tree);

            string_literal218=(Token)match(input,77,FOLLOW_77_in_expressionGenerator1212); 
            string_literal218_tree = (Object)adaptor.create(string_literal218);
            adaptor.addChild(root_0, string_literal218_tree);

            pushFollow(FOLLOW_expression_in_expressionGenerator1214);
            expression219=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression219.getTree());
             

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:208:1: expressionGenerators : expressionGenerator ( ',' expressionGenerator )* ;
    public final RVCCalParser.expressionGenerators_return expressionGenerators() throws RecognitionException {
        RVCCalParser.expressionGenerators_return retval = new RVCCalParser.expressionGenerators_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal221=null;
        RVCCalParser.expressionGenerator_return expressionGenerator220 = null;

        RVCCalParser.expressionGenerator_return expressionGenerator222 = null;


        Object char_literal221_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:208:21: ( expressionGenerator ( ',' expressionGenerator )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:208:23: expressionGenerator ( ',' expressionGenerator )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators1224);
            expressionGenerator220=expressionGenerator();

            state._fsp--;

            adaptor.addChild(root_0, expressionGenerator220.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:208:43: ( ',' expressionGenerator )*
            loop79:
            do {
                int alt79=2;
                int LA79_0 = input.LA(1);

                if ( (LA79_0==28) ) {
                    alt79=1;
                }


                switch (alt79) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:208:44: ',' expressionGenerator
            	    {
            	    char_literal221=(Token)match(input,28,FOLLOW_28_in_expressionGenerators1227); 
            	    char_literal221_tree = (Object)adaptor.create(char_literal221);
            	    adaptor.addChild(root_0, char_literal221_tree);

            	    pushFollow(FOLLOW_expressionGenerator_in_expressionGenerators1229);
            	    expressionGenerator222=expressionGenerator();

            	    state._fsp--;

            	    adaptor.addChild(root_0, expressionGenerator222.getTree());

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
    // $ANTLR end "expressionGenerators"

    public static class expressions_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expressions"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:210:1: expressions : expression ( ',' expression )* ;
    public final RVCCalParser.expressions_return expressions() throws RecognitionException {
        RVCCalParser.expressions_return retval = new RVCCalParser.expressions_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal224=null;
        RVCCalParser.expression_return expression223 = null;

        RVCCalParser.expression_return expression225 = null;


        Object char_literal224_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:210:12: ( expression ( ',' expression )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:210:14: expression ( ',' expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_expressions1240);
            expression223=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression223.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:210:25: ( ',' expression )*
            loop80:
            do {
                int alt80=2;
                int LA80_0 = input.LA(1);

                if ( (LA80_0==28) ) {
                    alt80=1;
                }


                switch (alt80) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:210:26: ',' expression
            	    {
            	    char_literal224=(Token)match(input,28,FOLLOW_28_in_expressions1243); 
            	    char_literal224_tree = (Object)adaptor.create(char_literal224);
            	    adaptor.addChild(root_0, char_literal224_tree);

            	    pushFollow(FOLLOW_expression_in_expressions1245);
            	    expression225=expression();

            	    state._fsp--;

            	    adaptor.addChild(root_0, expression225.getTree());

            	    }
            	    break;

            	default :
            	    break loop80;
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:212:1: idents : ID ( ',' ID )* ;
    public final RVCCalParser.idents_return idents() throws RecognitionException {
        RVCCalParser.idents_return retval = new RVCCalParser.idents_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID226=null;
        Token char_literal227=null;
        Token ID228=null;

        Object ID226_tree=null;
        Object char_literal227_tree=null;
        Object ID228_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:7: ( ID ( ',' ID )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:9: ID ( ',' ID )*
            {
            root_0 = (Object)adaptor.nil();

            ID226=(Token)match(input,ID,FOLLOW_ID_in_idents1261); 
            ID226_tree = (Object)adaptor.create(ID226);
            adaptor.addChild(root_0, ID226_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:12: ( ',' ID )*
            loop81:
            do {
                int alt81=2;
                int LA81_0 = input.LA(1);

                if ( (LA81_0==28) ) {
                    alt81=1;
                }


                switch (alt81) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:215:13: ',' ID
            	    {
            	    char_literal227=(Token)match(input,28,FOLLOW_28_in_idents1264); 
            	    char_literal227_tree = (Object)adaptor.create(char_literal227);
            	    adaptor.addChild(root_0, char_literal227_tree);

            	    ID228=(Token)match(input,ID,FOLLOW_ID_in_idents1266); 
            	    ID228_tree = (Object)adaptor.create(ID228);
            	    adaptor.addChild(root_0, ID228_tree);


            	    }
            	    break;

            	default :
            	    break loop81;
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:217:1: priorityInequality : qualifiedIdent ( '>' qualifiedIdent )+ ';' ;
    public final RVCCalParser.priorityInequality_return priorityInequality() throws RecognitionException {
        RVCCalParser.priorityInequality_return retval = new RVCCalParser.priorityInequality_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal230=null;
        Token char_literal232=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent229 = null;

        RVCCalParser.qualifiedIdent_return qualifiedIdent231 = null;


        Object char_literal230_tree=null;
        Object char_literal232_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:220:19: ( qualifiedIdent ( '>' qualifiedIdent )+ ';' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:220:21: qualifiedIdent ( '>' qualifiedIdent )+ ';'
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality1282);
            qualifiedIdent229=qualifiedIdent();

            state._fsp--;

            adaptor.addChild(root_0, qualifiedIdent229.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:220:36: ( '>' qualifiedIdent )+
            int cnt82=0;
            loop82:
            do {
                int alt82=2;
                int LA82_0 = input.LA(1);

                if ( (LA82_0==57) ) {
                    alt82=1;
                }


                switch (alt82) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:220:37: '>' qualifiedIdent
            	    {
            	    char_literal230=(Token)match(input,57,FOLLOW_57_in_priorityInequality1285); 
            	    char_literal230_tree = (Object)adaptor.create(char_literal230);
            	    adaptor.addChild(root_0, char_literal230_tree);

            	    pushFollow(FOLLOW_qualifiedIdent_in_priorityInequality1287);
            	    qualifiedIdent231=qualifiedIdent();

            	    state._fsp--;

            	    adaptor.addChild(root_0, qualifiedIdent231.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt82 >= 1 ) break loop82;
                        EarlyExitException eee =
                            new EarlyExitException(82, input);
                        throw eee;
                }
                cnt82++;
            } while (true);

            char_literal232=(Token)match(input,42,FOLLOW_42_in_priorityInequality1291); 
            char_literal232_tree = (Object)adaptor.create(char_literal232);
            adaptor.addChild(root_0, char_literal232_tree);

             

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:222:1: priorityOrder : 'priority' ( priorityInequality )* 'end' ;
    public final RVCCalParser.priorityOrder_return priorityOrder() throws RecognitionException {
        RVCCalParser.priorityOrder_return retval = new RVCCalParser.priorityOrder_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal233=null;
        Token string_literal235=null;
        RVCCalParser.priorityInequality_return priorityInequality234 = null;


        Object string_literal233_tree=null;
        Object string_literal235_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:222:14: ( 'priority' ( priorityInequality )* 'end' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:222:16: 'priority' ( priorityInequality )* 'end'
            {
            root_0 = (Object)adaptor.nil();

            string_literal233=(Token)match(input,78,FOLLOW_78_in_priorityOrder1301); 
            string_literal233_tree = (Object)adaptor.create(string_literal233);
            adaptor.addChild(root_0, string_literal233_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:222:27: ( priorityInequality )*
            loop83:
            do {
                int alt83=2;
                int LA83_0 = input.LA(1);

                if ( (LA83_0==ID) ) {
                    alt83=1;
                }


                switch (alt83) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:222:28: priorityInequality
            	    {
            	    pushFollow(FOLLOW_priorityInequality_in_priorityOrder1304);
            	    priorityInequality234=priorityInequality();

            	    state._fsp--;

            	    adaptor.addChild(root_0, priorityInequality234.getTree());

            	    }
            	    break;

            	default :
            	    break loop83;
                }
            } while (true);

            string_literal235=(Token)match(input,35,FOLLOW_35_in_priorityOrder1308); 
            string_literal235_tree = (Object)adaptor.create(string_literal235);
            adaptor.addChild(root_0, string_literal235_tree);

             

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:224:1: qualifiedIdent : ID ( '.' ID )* ;
    public final RVCCalParser.qualifiedIdent_return qualifiedIdent() throws RecognitionException {
        RVCCalParser.qualifiedIdent_return retval = new RVCCalParser.qualifiedIdent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID236=null;
        Token char_literal237=null;
        Token ID238=null;

        Object ID236_tree=null;
        Object char_literal237_tree=null;
        Object ID238_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:15: ( ID ( '.' ID )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:17: ID ( '.' ID )*
            {
            root_0 = (Object)adaptor.nil();

            ID236=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent1322); 
            ID236_tree = (Object)adaptor.create(ID236);
            adaptor.addChild(root_0, ID236_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:20: ( '.' ID )*
            loop84:
            do {
                int alt84=2;
                int LA84_0 = input.LA(1);

                if ( (LA84_0==36) ) {
                    alt84=1;
                }


                switch (alt84) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:227:21: '.' ID
            	    {
            	    char_literal237=(Token)match(input,36,FOLLOW_36_in_qualifiedIdent1325); 
            	    char_literal237_tree = (Object)adaptor.create(char_literal237);
            	    adaptor.addChild(root_0, char_literal237_tree);

            	    ID238=(Token)match(input,ID,FOLLOW_ID_in_qualifiedIdent1327); 
            	    ID238_tree = (Object)adaptor.create(ID238);
            	    adaptor.addChild(root_0, ID238_tree);


            	    }
            	    break;

            	default :
            	    break loop84;
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:229:1: schedule : 'schedule' ( 'fsm' ID ':' ( stateTransition )* 'end' | 'regexp' ) ;
    public final RVCCalParser.schedule_return schedule() throws RecognitionException {
        RVCCalParser.schedule_return retval = new RVCCalParser.schedule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal239=null;
        Token string_literal240=null;
        Token ID241=null;
        Token char_literal242=null;
        Token string_literal244=null;
        Token string_literal245=null;
        RVCCalParser.stateTransition_return stateTransition243 = null;


        Object string_literal239_tree=null;
        Object string_literal240_tree=null;
        Object ID241_tree=null;
        Object char_literal242_tree=null;
        Object string_literal244_tree=null;
        Object string_literal245_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:232:9: ( 'schedule' ( 'fsm' ID ':' ( stateTransition )* 'end' | 'regexp' ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:233:3: 'schedule' ( 'fsm' ID ':' ( stateTransition )* 'end' | 'regexp' )
            {
            root_0 = (Object)adaptor.nil();

            string_literal239=(Token)match(input,79,FOLLOW_79_in_schedule1345); 
            string_literal239_tree = (Object)adaptor.create(string_literal239);
            adaptor.addChild(root_0, string_literal239_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:234:5: ( 'fsm' ID ':' ( stateTransition )* 'end' | 'regexp' )
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==80) ) {
                alt86=1;
            }
            else if ( (LA86_0==81) ) {
                alt86=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 86, 0, input);

                throw nvae;
            }
            switch (alt86) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:234:6: 'fsm' ID ':' ( stateTransition )* 'end'
                    {
                    string_literal240=(Token)match(input,80,FOLLOW_80_in_schedule1352); 
                    string_literal240_tree = (Object)adaptor.create(string_literal240);
                    adaptor.addChild(root_0, string_literal240_tree);

                    ID241=(Token)match(input,ID,FOLLOW_ID_in_schedule1354); 
                    ID241_tree = (Object)adaptor.create(ID241);
                    adaptor.addChild(root_0, ID241_tree);

                    char_literal242=(Token)match(input,25,FOLLOW_25_in_schedule1356); 
                    char_literal242_tree = (Object)adaptor.create(char_literal242);
                    adaptor.addChild(root_0, char_literal242_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:234:19: ( stateTransition )*
                    loop85:
                    do {
                        int alt85=2;
                        int LA85_0 = input.LA(1);

                        if ( (LA85_0==ID) ) {
                            alt85=1;
                        }


                        switch (alt85) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:234:19: stateTransition
                    	    {
                    	    pushFollow(FOLLOW_stateTransition_in_schedule1358);
                    	    stateTransition243=stateTransition();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, stateTransition243.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop85;
                        }
                    } while (true);

                    string_literal244=(Token)match(input,35,FOLLOW_35_in_schedule1361); 
                    string_literal244_tree = (Object)adaptor.create(string_literal244);
                    adaptor.addChild(root_0, string_literal244_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:235:7: 'regexp'
                    {
                    string_literal245=(Token)match(input,81,FOLLOW_81_in_schedule1371); 
                    string_literal245_tree = (Object)adaptor.create(string_literal245);
                    adaptor.addChild(root_0, string_literal245_tree);

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:1: stateTransition : ID '(' qualifiedIdent ')' '-->' ID ';' ;
    public final RVCCalParser.stateTransition_return stateTransition() throws RecognitionException {
        RVCCalParser.stateTransition_return retval = new RVCCalParser.stateTransition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID246=null;
        Token char_literal247=null;
        Token char_literal249=null;
        Token string_literal250=null;
        Token ID251=null;
        Token char_literal252=null;
        RVCCalParser.qualifiedIdent_return qualifiedIdent248 = null;


        Object ID246_tree=null;
        Object char_literal247_tree=null;
        Object char_literal249_tree=null;
        Object string_literal250_tree=null;
        Object ID251_tree=null;
        Object char_literal252_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:237:16: ( ID '(' qualifiedIdent ')' '-->' ID ';' )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:238:2: ID '(' qualifiedIdent ')' '-->' ID ';'
            {
            root_0 = (Object)adaptor.nil();

            ID246=(Token)match(input,ID,FOLLOW_ID_in_stateTransition1383); 
            ID246_tree = (Object)adaptor.create(ID246);
            adaptor.addChild(root_0, ID246_tree);

            char_literal247=(Token)match(input,32,FOLLOW_32_in_stateTransition1385); 
            char_literal247_tree = (Object)adaptor.create(char_literal247);
            adaptor.addChild(root_0, char_literal247_tree);

            pushFollow(FOLLOW_qualifiedIdent_in_stateTransition1387);
            qualifiedIdent248=qualifiedIdent();

            state._fsp--;

            adaptor.addChild(root_0, qualifiedIdent248.getTree());
            char_literal249=(Token)match(input,33,FOLLOW_33_in_stateTransition1389); 
            char_literal249_tree = (Object)adaptor.create(char_literal249);
            adaptor.addChild(root_0, char_literal249_tree);

            string_literal250=(Token)match(input,44,FOLLOW_44_in_stateTransition1391); 
            string_literal250_tree = (Object)adaptor.create(string_literal250);
            adaptor.addChild(root_0, string_literal250_tree);

            ID251=(Token)match(input,ID,FOLLOW_ID_in_stateTransition1393); 
            ID251_tree = (Object)adaptor.create(ID251);
            adaptor.addChild(root_0, ID251_tree);

            char_literal252=(Token)match(input,42,FOLLOW_42_in_stateTransition1395); 
            char_literal252_tree = (Object)adaptor.create(char_literal252);
            adaptor.addChild(root_0, char_literal252_tree);

             

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:240:1: statement : ( 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end' | 'choose' | 'for' | 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end' | 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end' | 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end' | ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' ) );
    public final RVCCalParser.statement_return statement() throws RecognitionException {
        RVCCalParser.statement_return retval = new RVCCalParser.statement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal253=null;
        Token string_literal254=null;
        Token string_literal256=null;
        Token string_literal258=null;
        Token string_literal259=null;
        Token string_literal260=null;
        Token string_literal261=null;
        Token string_literal263=null;
        Token string_literal265=null;
        Token string_literal267=null;
        Token string_literal269=null;
        Token string_literal271=null;
        Token string_literal272=null;
        Token string_literal274=null;
        Token string_literal276=null;
        Token string_literal278=null;
        Token string_literal279=null;
        Token string_literal281=null;
        Token string_literal283=null;
        Token string_literal285=null;
        Token ID286=null;
        Token char_literal287=null;
        Token char_literal289=null;
        Token string_literal290=null;
        Token char_literal292=null;
        Token char_literal293=null;
        Token char_literal295=null;
        Token char_literal296=null;
        RVCCalParser.varDecls_return varDecls255 = null;

        RVCCalParser.statement_return statement257 = null;

        RVCCalParser.varDeclNoExpr_return varDeclNoExpr262 = null;

        RVCCalParser.expression_return expression264 = null;

        RVCCalParser.expression_return expression266 = null;

        RVCCalParser.varDecls_return varDecls268 = null;

        RVCCalParser.statement_return statement270 = null;

        RVCCalParser.expression_return expression273 = null;

        RVCCalParser.statement_return statement275 = null;

        RVCCalParser.statement_return statement277 = null;

        RVCCalParser.expression_return expression280 = null;

        RVCCalParser.varDecls_return varDecls282 = null;

        RVCCalParser.statement_return statement284 = null;

        RVCCalParser.expressions_return expressions288 = null;

        RVCCalParser.expression_return expression291 = null;

        RVCCalParser.expressions_return expressions294 = null;


        Object string_literal253_tree=null;
        Object string_literal254_tree=null;
        Object string_literal256_tree=null;
        Object string_literal258_tree=null;
        Object string_literal259_tree=null;
        Object string_literal260_tree=null;
        Object string_literal261_tree=null;
        Object string_literal263_tree=null;
        Object string_literal265_tree=null;
        Object string_literal267_tree=null;
        Object string_literal269_tree=null;
        Object string_literal271_tree=null;
        Object string_literal272_tree=null;
        Object string_literal274_tree=null;
        Object string_literal276_tree=null;
        Object string_literal278_tree=null;
        Object string_literal279_tree=null;
        Object string_literal281_tree=null;
        Object string_literal283_tree=null;
        Object string_literal285_tree=null;
        Object ID286_tree=null;
        Object char_literal287_tree=null;
        Object char_literal289_tree=null;
        Object string_literal290_tree=null;
        Object char_literal292_tree=null;
        Object char_literal293_tree=null;
        Object char_literal295_tree=null;
        Object char_literal296_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:243:10: ( 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end' | 'choose' | 'for' | 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end' | 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end' | 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end' | ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' ) )
            int alt100=7;
            switch ( input.LA(1) ) {
            case 46:
                {
                alt100=1;
                }
                break;
            case 82:
                {
                alt100=2;
                }
                break;
            case 76:
                {
                alt100=3;
                }
                break;
            case 83:
                {
                alt100=4;
                }
                break;
            case 71:
                {
                alt100=5;
                }
                break;
            case 85:
                {
                alt100=6;
                }
                break;
            case ID:
                {
                alt100=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 100, 0, input);

                throw nvae;
            }

            switch (alt100) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:3: 'begin' ( 'var' varDecls 'do' )? ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal253=(Token)match(input,46,FOLLOW_46_in_statement1411); 
                    string_literal253_tree = (Object)adaptor.create(string_literal253);
                    adaptor.addChild(root_0, string_literal253_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:11: ( 'var' varDecls 'do' )?
                    int alt87=2;
                    int LA87_0 = input.LA(1);

                    if ( (LA87_0==38) ) {
                        alt87=1;
                    }
                    switch (alt87) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:12: 'var' varDecls 'do'
                            {
                            string_literal254=(Token)match(input,38,FOLLOW_38_in_statement1414); 
                            string_literal254_tree = (Object)adaptor.create(string_literal254);
                            adaptor.addChild(root_0, string_literal254_tree);

                            pushFollow(FOLLOW_varDecls_in_statement1416);
                            varDecls255=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls255.getTree());
                            string_literal256=(Token)match(input,30,FOLLOW_30_in_statement1418); 
                            string_literal256_tree = (Object)adaptor.create(string_literal256);
                            adaptor.addChild(root_0, string_literal256_tree);


                            }
                            break;

                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:34: ( statement )*
                    loop88:
                    do {
                        int alt88=2;
                        int LA88_0 = input.LA(1);

                        if ( (LA88_0==ID||LA88_0==46||LA88_0==71||LA88_0==76||(LA88_0>=82 && LA88_0<=83)||LA88_0==85) ) {
                            alt88=1;
                        }


                        switch (alt88) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:244:34: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement1422);
                    	    statement257=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement257.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop88;
                        }
                    } while (true);

                    string_literal258=(Token)match(input,35,FOLLOW_35_in_statement1425); 
                    string_literal258_tree = (Object)adaptor.create(string_literal258);
                    adaptor.addChild(root_0, string_literal258_tree);

                     

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:245:3: 'choose'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal259=(Token)match(input,82,FOLLOW_82_in_statement1431); 
                    string_literal259_tree = (Object)adaptor.create(string_literal259);
                    adaptor.addChild(root_0, string_literal259_tree);

                     System.out.println("RVC-CAL does not support the \"choose\" statement."); 

                    }
                    break;
                case 3 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:246:3: 'for'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal260=(Token)match(input,76,FOLLOW_76_in_statement1437); 
                    string_literal260_tree = (Object)adaptor.create(string_literal260);
                    adaptor.addChild(root_0, string_literal260_tree);

                     System.out.println("RVC-CAL does not support the \"for\" statement, please use \"foreach\" instead."); 

                    }
                    break;
                case 4 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:247:3: 'foreach' varDeclNoExpr 'in' ( expression ( '..' expression )? ) ( 'var' varDecls )? 'do' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal261=(Token)match(input,83,FOLLOW_83_in_statement1443); 
                    string_literal261_tree = (Object)adaptor.create(string_literal261);
                    adaptor.addChild(root_0, string_literal261_tree);

                    pushFollow(FOLLOW_varDeclNoExpr_in_statement1445);
                    varDeclNoExpr262=varDeclNoExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, varDeclNoExpr262.getTree());
                    string_literal263=(Token)match(input,77,FOLLOW_77_in_statement1447); 
                    string_literal263_tree = (Object)adaptor.create(string_literal263);
                    adaptor.addChild(root_0, string_literal263_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:247:32: ( expression ( '..' expression )? )
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:247:33: expression ( '..' expression )?
                    {
                    pushFollow(FOLLOW_expression_in_statement1450);
                    expression264=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression264.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:247:44: ( '..' expression )?
                    int alt89=2;
                    int LA89_0 = input.LA(1);

                    if ( (LA89_0==84) ) {
                        alt89=1;
                    }
                    switch (alt89) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:247:45: '..' expression
                            {
                            string_literal265=(Token)match(input,84,FOLLOW_84_in_statement1453); 
                            string_literal265_tree = (Object)adaptor.create(string_literal265);
                            adaptor.addChild(root_0, string_literal265_tree);

                            pushFollow(FOLLOW_expression_in_statement1455);
                            expression266=expression();

                            state._fsp--;

                            adaptor.addChild(root_0, expression266.getTree());

                            }
                            break;

                    }


                    }

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:247:64: ( 'var' varDecls )?
                    int alt90=2;
                    int LA90_0 = input.LA(1);

                    if ( (LA90_0==38) ) {
                        alt90=1;
                    }
                    switch (alt90) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:247:65: 'var' varDecls
                            {
                            string_literal267=(Token)match(input,38,FOLLOW_38_in_statement1461); 
                            string_literal267_tree = (Object)adaptor.create(string_literal267);
                            adaptor.addChild(root_0, string_literal267_tree);

                            pushFollow(FOLLOW_varDecls_in_statement1463);
                            varDecls268=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls268.getTree());

                            }
                            break;

                    }

                    string_literal269=(Token)match(input,30,FOLLOW_30_in_statement1467); 
                    string_literal269_tree = (Object)adaptor.create(string_literal269);
                    adaptor.addChild(root_0, string_literal269_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:247:87: ( statement )*
                    loop91:
                    do {
                        int alt91=2;
                        int LA91_0 = input.LA(1);

                        if ( (LA91_0==ID||LA91_0==46||LA91_0==71||LA91_0==76||(LA91_0>=82 && LA91_0<=83)||LA91_0==85) ) {
                            alt91=1;
                        }


                        switch (alt91) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:247:87: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement1469);
                    	    statement270=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement270.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop91;
                        }
                    } while (true);

                    string_literal271=(Token)match(input,35,FOLLOW_35_in_statement1472); 
                    string_literal271_tree = (Object)adaptor.create(string_literal271);
                    adaptor.addChild(root_0, string_literal271_tree);

                     

                    }
                    break;
                case 5 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:248:3: 'if' expression 'then' ( statement )* ( 'else' ( statement )* )? 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal272=(Token)match(input,71,FOLLOW_71_in_statement1478); 
                    string_literal272_tree = (Object)adaptor.create(string_literal272);
                    adaptor.addChild(root_0, string_literal272_tree);

                    pushFollow(FOLLOW_expression_in_statement1480);
                    expression273=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression273.getTree());
                    string_literal274=(Token)match(input,72,FOLLOW_72_in_statement1482); 
                    string_literal274_tree = (Object)adaptor.create(string_literal274);
                    adaptor.addChild(root_0, string_literal274_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:248:26: ( statement )*
                    loop92:
                    do {
                        int alt92=2;
                        int LA92_0 = input.LA(1);

                        if ( (LA92_0==ID||LA92_0==46||LA92_0==71||LA92_0==76||(LA92_0>=82 && LA92_0<=83)||LA92_0==85) ) {
                            alt92=1;
                        }


                        switch (alt92) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:248:26: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement1484);
                    	    statement275=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement275.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop92;
                        }
                    } while (true);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:248:37: ( 'else' ( statement )* )?
                    int alt94=2;
                    int LA94_0 = input.LA(1);

                    if ( (LA94_0==73) ) {
                        alt94=1;
                    }
                    switch (alt94) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:248:38: 'else' ( statement )*
                            {
                            string_literal276=(Token)match(input,73,FOLLOW_73_in_statement1488); 
                            string_literal276_tree = (Object)adaptor.create(string_literal276);
                            adaptor.addChild(root_0, string_literal276_tree);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:248:45: ( statement )*
                            loop93:
                            do {
                                int alt93=2;
                                int LA93_0 = input.LA(1);

                                if ( (LA93_0==ID||LA93_0==46||LA93_0==71||LA93_0==76||(LA93_0>=82 && LA93_0<=83)||LA93_0==85) ) {
                                    alt93=1;
                                }


                                switch (alt93) {
                            	case 1 :
                            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:248:45: statement
                            	    {
                            	    pushFollow(FOLLOW_statement_in_statement1490);
                            	    statement277=statement();

                            	    state._fsp--;

                            	    adaptor.addChild(root_0, statement277.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop93;
                                }
                            } while (true);


                            }
                            break;

                    }

                    string_literal278=(Token)match(input,35,FOLLOW_35_in_statement1495); 
                    string_literal278_tree = (Object)adaptor.create(string_literal278);
                    adaptor.addChild(root_0, string_literal278_tree);

                      

                    }
                    break;
                case 6 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:249:3: 'while' expression ( 'var' varDecls )? 'do' ( statement )* 'end'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal279=(Token)match(input,85,FOLLOW_85_in_statement1501); 
                    string_literal279_tree = (Object)adaptor.create(string_literal279);
                    adaptor.addChild(root_0, string_literal279_tree);

                    pushFollow(FOLLOW_expression_in_statement1503);
                    expression280=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression280.getTree());
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:249:22: ( 'var' varDecls )?
                    int alt95=2;
                    int LA95_0 = input.LA(1);

                    if ( (LA95_0==38) ) {
                        alt95=1;
                    }
                    switch (alt95) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:249:23: 'var' varDecls
                            {
                            string_literal281=(Token)match(input,38,FOLLOW_38_in_statement1506); 
                            string_literal281_tree = (Object)adaptor.create(string_literal281);
                            adaptor.addChild(root_0, string_literal281_tree);

                            pushFollow(FOLLOW_varDecls_in_statement1508);
                            varDecls282=varDecls();

                            state._fsp--;

                            adaptor.addChild(root_0, varDecls282.getTree());

                            }
                            break;

                    }

                    string_literal283=(Token)match(input,30,FOLLOW_30_in_statement1512); 
                    string_literal283_tree = (Object)adaptor.create(string_literal283);
                    adaptor.addChild(root_0, string_literal283_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:249:45: ( statement )*
                    loop96:
                    do {
                        int alt96=2;
                        int LA96_0 = input.LA(1);

                        if ( (LA96_0==ID||LA96_0==46||LA96_0==71||LA96_0==76||(LA96_0>=82 && LA96_0<=83)||LA96_0==85) ) {
                            alt96=1;
                        }


                        switch (alt96) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:249:45: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_statement1514);
                    	    statement284=statement();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, statement284.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop96;
                        }
                    } while (true);

                    string_literal285=(Token)match(input,35,FOLLOW_35_in_statement1517); 
                    string_literal285_tree = (Object)adaptor.create(string_literal285);
                    adaptor.addChild(root_0, string_literal285_tree);

                      

                    }
                    break;
                case 7 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:3: ID ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' )
                    {
                    root_0 = (Object)adaptor.nil();

                    ID286=(Token)match(input,ID,FOLLOW_ID_in_statement1524); 
                    ID286_tree = (Object)adaptor.create(ID286);
                    adaptor.addChild(root_0, ID286_tree);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:251:6: ( ( ( '[' expressions ']' )? ':=' expression ';' ) | '(' ( expressions )? ')' ';' )
                    int alt99=2;
                    int LA99_0 = input.LA(1);

                    if ( (LA99_0==26||LA99_0==41) ) {
                        alt99=1;
                    }
                    else if ( (LA99_0==32) ) {
                        alt99=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 99, 0, input);

                        throw nvae;
                    }
                    switch (alt99) {
                        case 1 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:252:5: ( ( '[' expressions ']' )? ':=' expression ';' )
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:252:5: ( ( '[' expressions ']' )? ':=' expression ';' )
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:252:6: ( '[' expressions ']' )? ':=' expression ';'
                            {
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:252:6: ( '[' expressions ']' )?
                            int alt97=2;
                            int LA97_0 = input.LA(1);

                            if ( (LA97_0==26) ) {
                                alt97=1;
                            }
                            switch (alt97) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:252:7: '[' expressions ']'
                                    {
                                    char_literal287=(Token)match(input,26,FOLLOW_26_in_statement1534); 
                                    char_literal287_tree = (Object)adaptor.create(char_literal287);
                                    adaptor.addChild(root_0, char_literal287_tree);

                                    pushFollow(FOLLOW_expressions_in_statement1536);
                                    expressions288=expressions();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expressions288.getTree());
                                    char_literal289=(Token)match(input,27,FOLLOW_27_in_statement1538); 
                                    char_literal289_tree = (Object)adaptor.create(char_literal289);
                                    adaptor.addChild(root_0, char_literal289_tree);


                                    }
                                    break;

                            }

                            string_literal290=(Token)match(input,41,FOLLOW_41_in_statement1542); 
                            string_literal290_tree = (Object)adaptor.create(string_literal290);
                            adaptor.addChild(root_0, string_literal290_tree);

                            pushFollow(FOLLOW_expression_in_statement1544);
                            expression291=expression();

                            state._fsp--;

                            adaptor.addChild(root_0, expression291.getTree());
                            char_literal292=(Token)match(input,42,FOLLOW_42_in_statement1546); 
                            char_literal292_tree = (Object)adaptor.create(char_literal292);
                            adaptor.addChild(root_0, char_literal292_tree);


                            }

                             

                            }
                            break;
                        case 2 :
                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:253:6: '(' ( expressions )? ')' ';'
                            {
                            char_literal293=(Token)match(input,32,FOLLOW_32_in_statement1556); 
                            char_literal293_tree = (Object)adaptor.create(char_literal293);
                            adaptor.addChild(root_0, char_literal293_tree);

                            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:253:10: ( expressions )?
                            int alt98=2;
                            int LA98_0 = input.LA(1);

                            if ( ((LA98_0>=ID && LA98_0<=STRING)||LA98_0==26||LA98_0==32||LA98_0==63||(LA98_0>=69 && LA98_0<=71)||(LA98_0>=74 && LA98_0<=75)) ) {
                                alt98=1;
                            }
                            switch (alt98) {
                                case 1 :
                                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:253:10: expressions
                                    {
                                    pushFollow(FOLLOW_expressions_in_statement1558);
                                    expressions294=expressions();

                                    state._fsp--;

                                    adaptor.addChild(root_0, expressions294.getTree());

                                    }
                                    break;

                            }

                            char_literal295=(Token)match(input,33,FOLLOW_33_in_statement1561); 
                            char_literal295_tree = (Object)adaptor.create(char_literal295);
                            adaptor.addChild(root_0, char_literal295_tree);

                            char_literal296=(Token)match(input,42,FOLLOW_42_in_statement1563); 
                            char_literal296_tree = (Object)adaptor.create(char_literal296);
                            adaptor.addChild(root_0, char_literal296_tree);

                             

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:255:1: typeAttr : ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) ) ;
    public final RVCCalParser.typeAttr_return typeAttr() throws RecognitionException {
        RVCCalParser.typeAttr_return retval = new RVCCalParser.typeAttr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID297=null;
        Token char_literal298=null;
        Token char_literal300=null;
        RVCCalParser.typeDef_return typeDef299 = null;

        RVCCalParser.expression_return expression301 = null;


        Object ID297_tree=null;
        Object char_literal298_tree=null;
        Object char_literal300_tree=null;
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_25=new RewriteRuleTokenStream(adaptor,"token 25");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeDef=new RewriteRuleSubtreeStream(adaptor,"rule typeDef");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:9: ( ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:11: ID ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) )
            {
            ID297=(Token)match(input,ID,FOLLOW_ID_in_typeAttr1581);  
            stream_ID.add(ID297);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:14: ( ':' typeDef -> ^( TYPE ID typeDef ) | '=' expression -> ^( EXPR ID expression ) )
            int alt101=2;
            int LA101_0 = input.LA(1);

            if ( (LA101_0==25) ) {
                alt101=1;
            }
            else if ( (LA101_0==40) ) {
                alt101=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 101, 0, input);

                throw nvae;
            }
            switch (alt101) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:15: ':' typeDef
                    {
                    char_literal298=(Token)match(input,25,FOLLOW_25_in_typeAttr1584);  
                    stream_25.add(char_literal298);

                    pushFollow(FOLLOW_typeDef_in_typeAttr1586);
                    typeDef299=typeDef();

                    state._fsp--;

                    stream_typeDef.add(typeDef299.getTree());


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
                    // 259:27: -> ^( TYPE ID typeDef )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:30: ^( TYPE ID typeDef )
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
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:51: '=' expression
                    {
                    char_literal300=(Token)match(input,40,FOLLOW_40_in_typeAttr1600);  
                    stream_40.add(char_literal300);

                    pushFollow(FOLLOW_expression_in_typeAttr1602);
                    expression301=expression();

                    state._fsp--;

                    stream_expression.add(expression301.getTree());


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
                    // 259:66: -> ^( EXPR ID expression )
                    {
                        // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:259:69: ^( EXPR ID expression )
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:261:1: typeAttrs : typeAttr ( ',' typeAttr )* -> ( typeAttr )+ ;
    public final RVCCalParser.typeAttrs_return typeAttrs() throws RecognitionException {
        RVCCalParser.typeAttrs_return retval = new RVCCalParser.typeAttrs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal303=null;
        RVCCalParser.typeAttr_return typeAttr302 = null;

        RVCCalParser.typeAttr_return typeAttr304 = null;


        Object char_literal303_tree=null;
        RewriteRuleTokenStream stream_28=new RewriteRuleTokenStream(adaptor,"token 28");
        RewriteRuleSubtreeStream stream_typeAttr=new RewriteRuleSubtreeStream(adaptor,"rule typeAttr");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:261:10: ( typeAttr ( ',' typeAttr )* -> ( typeAttr )+ )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:261:12: typeAttr ( ',' typeAttr )*
            {
            pushFollow(FOLLOW_typeAttr_in_typeAttrs1620);
            typeAttr302=typeAttr();

            state._fsp--;

            stream_typeAttr.add(typeAttr302.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:261:21: ( ',' typeAttr )*
            loop102:
            do {
                int alt102=2;
                int LA102_0 = input.LA(1);

                if ( (LA102_0==28) ) {
                    alt102=1;
                }


                switch (alt102) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:261:22: ',' typeAttr
            	    {
            	    char_literal303=(Token)match(input,28,FOLLOW_28_in_typeAttrs1623);  
            	    stream_28.add(char_literal303);

            	    pushFollow(FOLLOW_typeAttr_in_typeAttrs1625);
            	    typeAttr304=typeAttr();

            	    state._fsp--;

            	    stream_typeAttr.add(typeAttr304.getTree());

            	    }
            	    break;

            	default :
            	    break loop102;
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
            // 261:37: -> ( typeAttr )+
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:264:1: typeDef : ID ( '[' ( typePar )+ ']' | '(' attrs= typeAttrs ')' )? -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) ;
    public final RVCCalParser.typeDef_return typeDef() throws RecognitionException {
        RVCCalParser.typeDef_return retval = new RVCCalParser.typeDef_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID305=null;
        Token char_literal306=null;
        Token char_literal308=null;
        Token char_literal309=null;
        Token char_literal310=null;
        RVCCalParser.typeAttrs_return attrs = null;

        RVCCalParser.typePar_return typePar307 = null;


        Object ID305_tree=null;
        Object char_literal306_tree=null;
        Object char_literal308_tree=null;
        Object char_literal309_tree=null;
        Object char_literal310_tree=null;
        RewriteRuleTokenStream stream_32=new RewriteRuleTokenStream(adaptor,"token 32");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_33=new RewriteRuleTokenStream(adaptor,"token 33");
        RewriteRuleTokenStream stream_26=new RewriteRuleTokenStream(adaptor,"token 26");
        RewriteRuleTokenStream stream_27=new RewriteRuleTokenStream(adaptor,"token 27");
        RewriteRuleSubtreeStream stream_typePar=new RewriteRuleSubtreeStream(adaptor,"rule typePar");
        RewriteRuleSubtreeStream stream_typeAttrs=new RewriteRuleSubtreeStream(adaptor,"rule typeAttrs");
        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:264:8: ( ID ( '[' ( typePar )+ ']' | '(' attrs= typeAttrs ')' )? -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) ) )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:264:10: ID ( '[' ( typePar )+ ']' | '(' attrs= typeAttrs ')' )?
            {
            ID305=(Token)match(input,ID,FOLLOW_ID_in_typeDef1642);  
            stream_ID.add(ID305);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:3: ( '[' ( typePar )+ ']' | '(' attrs= typeAttrs ')' )?
            int alt104=3;
            int LA104_0 = input.LA(1);

            if ( (LA104_0==26) ) {
                alt104=1;
            }
            else if ( (LA104_0==32) ) {
                alt104=2;
            }
            switch (alt104) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:4: '[' ( typePar )+ ']'
                    {
                    char_literal306=(Token)match(input,26,FOLLOW_26_in_typeDef1647);  
                    stream_26.add(char_literal306);

                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:8: ( typePar )+
                    int cnt103=0;
                    loop103:
                    do {
                        int alt103=2;
                        int LA103_0 = input.LA(1);

                        if ( (LA103_0==ID) ) {
                            alt103=1;
                        }


                        switch (alt103) {
                    	case 1 :
                    	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:265:8: typePar
                    	    {
                    	    pushFollow(FOLLOW_typePar_in_typeDef1649);
                    	    typePar307=typePar();

                    	    state._fsp--;

                    	    stream_typePar.add(typePar307.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt103 >= 1 ) break loop103;
                                EarlyExitException eee =
                                    new EarlyExitException(103, input);
                                throw eee;
                        }
                        cnt103++;
                    } while (true);

                    char_literal308=(Token)match(input,27,FOLLOW_27_in_typeDef1652);  
                    stream_27.add(char_literal308);


                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:5: '(' attrs= typeAttrs ')'
                    {
                    char_literal309=(Token)match(input,32,FOLLOW_32_in_typeDef1658);  
                    stream_32.add(char_literal309);

                    pushFollow(FOLLOW_typeAttrs_in_typeDef1662);
                    attrs=typeAttrs();

                    state._fsp--;

                    stream_typeAttrs.add(attrs.getTree());
                    char_literal310=(Token)match(input,33,FOLLOW_33_in_typeDef1664);  
                    stream_33.add(char_literal310);


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
            // 266:31: -> ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
            {
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:34: ^( TYPE ID ^( TYPE_ATTRS ( $attrs)? ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "TYPE"), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:44: ^( TYPE_ATTRS ( $attrs)? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_ATTRS, "TYPE_ATTRS"), root_2);

                // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:266:57: ( $attrs)?
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:268:1: typePar : ID ( '<' typeDef )? ;
    public final RVCCalParser.typePar_return typePar() throws RecognitionException {
        RVCCalParser.typePar_return retval = new RVCCalParser.typePar_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID311=null;
        Token char_literal312=null;
        RVCCalParser.typeDef_return typeDef313 = null;


        Object ID311_tree=null;
        Object char_literal312_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:271:8: ( ID ( '<' typeDef )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:271:10: ID ( '<' typeDef )?
            {
            root_0 = (Object)adaptor.nil();

            ID311=(Token)match(input,ID,FOLLOW_ID_in_typePar1694); 
            ID311_tree = (Object)adaptor.create(ID311);
            adaptor.addChild(root_0, ID311_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:271:13: ( '<' typeDef )?
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==56) ) {
                alt105=1;
            }
            switch (alt105) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:271:14: '<' typeDef
                    {
                    char_literal312=(Token)match(input,56,FOLLOW_56_in_typePar1697); 
                    char_literal312_tree = (Object)adaptor.create(char_literal312);
                    adaptor.addChild(root_0, char_literal312_tree);

                    pushFollow(FOLLOW_typeDef_in_typePar1699);
                    typeDef313=typeDef();

                    state._fsp--;

                    adaptor.addChild(root_0, typeDef313.getTree());

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

    public static class varDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "varDecl"
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:273:1: varDecl : typeDef ID ( '=' expression | ':=' expression )? ;
    public final RVCCalParser.varDecl_return varDecl() throws RecognitionException {
        RVCCalParser.varDecl_return retval = new RVCCalParser.varDecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID315=null;
        Token char_literal316=null;
        Token string_literal318=null;
        RVCCalParser.typeDef_return typeDef314 = null;

        RVCCalParser.expression_return expression317 = null;

        RVCCalParser.expression_return expression319 = null;


        Object ID315_tree=null;
        Object char_literal316_tree=null;
        Object string_literal318_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:8: ( typeDef ID ( '=' expression | ':=' expression )? )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:10: typeDef ID ( '=' expression | ':=' expression )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_typeDef_in_varDecl1717);
            typeDef314=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef314.getTree());
            ID315=(Token)match(input,ID,FOLLOW_ID_in_varDecl1719); 
            ID315_tree = (Object)adaptor.create(ID315);
            adaptor.addChild(root_0, ID315_tree);

            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:21: ( '=' expression | ':=' expression )?
            int alt106=3;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==40) ) {
                alt106=1;
            }
            else if ( (LA106_0==41) ) {
                alt106=2;
            }
            switch (alt106) {
                case 1 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:22: '=' expression
                    {
                    char_literal316=(Token)match(input,40,FOLLOW_40_in_varDecl1722); 
                    char_literal316_tree = (Object)adaptor.create(char_literal316);
                    adaptor.addChild(root_0, char_literal316_tree);

                    pushFollow(FOLLOW_expression_in_varDecl1724);
                    expression317=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression317.getTree());

                    }
                    break;
                case 2 :
                    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:277:39: ':=' expression
                    {
                    string_literal318=(Token)match(input,41,FOLLOW_41_in_varDecl1728); 
                    string_literal318_tree = (Object)adaptor.create(string_literal318);
                    adaptor.addChild(root_0, string_literal318_tree);

                    pushFollow(FOLLOW_expression_in_varDecl1730);
                    expression319=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression319.getTree());

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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:279:1: varDeclNoExpr : typeDef ID ;
    public final RVCCalParser.varDeclNoExpr_return varDeclNoExpr() throws RecognitionException {
        RVCCalParser.varDeclNoExpr_return retval = new RVCCalParser.varDeclNoExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID321=null;
        RVCCalParser.typeDef_return typeDef320 = null;


        Object ID321_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:279:14: ( typeDef ID )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:279:16: typeDef ID
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_typeDef_in_varDeclNoExpr1741);
            typeDef320=typeDef();

            state._fsp--;

            adaptor.addChild(root_0, typeDef320.getTree());
            ID321=(Token)match(input,ID,FOLLOW_ID_in_varDeclNoExpr1743); 
            ID321_tree = (Object)adaptor.create(ID321);
            adaptor.addChild(root_0, ID321_tree);

             

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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
    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:281:1: varDecls : varDecl ( ',' varDecl )* ;
    public final RVCCalParser.varDecls_return varDecls() throws RecognitionException {
        RVCCalParser.varDecls_return retval = new RVCCalParser.varDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal323=null;
        RVCCalParser.varDecl_return varDecl322 = null;

        RVCCalParser.varDecl_return varDecl324 = null;


        Object char_literal323_tree=null;

        try {
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:281:9: ( varDecl ( ',' varDecl )* )
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:281:11: varDecl ( ',' varDecl )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_varDecl_in_varDecls1752);
            varDecl322=varDecl();

            state._fsp--;

            adaptor.addChild(root_0, varDecl322.getTree());
            // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:281:19: ( ',' varDecl )*
            loop107:
            do {
                int alt107=2;
                int LA107_0 = input.LA(1);

                if ( (LA107_0==28) ) {
                    alt107=1;
                }


                switch (alt107) {
            	case 1 :
            	    // D:\\repositories\\mwipliez\\orcc\\trunk\\plugin\\src\\net\\sf\\orcc\\frontend\\parser\\internal\\RVCCal.g:281:20: ',' varDecl
            	    {
            	    char_literal323=(Token)match(input,28,FOLLOW_28_in_varDecls1755); 
            	    char_literal323_tree = (Object)adaptor.create(char_literal323);
            	    adaptor.addChild(root_0, char_literal323_tree);

            	    pushFollow(FOLLOW_varDecl_in_varDecls1757);
            	    varDecl324=varDecl();

            	    state._fsp--;

            	    adaptor.addChild(root_0, varDecl324.getTree());

            	    }
            	    break;

            	default :
            	    break loop107;
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


 

    public static final BitSet FOLLOW_19_in_actionChannelSelector104 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_actionChannelSelector110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_actionChannelSelector116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_actionChannelSelector122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_actionDelay131 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_actionDelay133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_actionGuards143 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expressions_in_actionGuards145 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_actionInput156 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_actionInput158 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_actionInput162 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_idents_in_actionInput164 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_actionInput166 = new BitSet(new long[]{0x0000000020780002L});
    public static final BitSet FOLLOW_actionRepeat_in_actionInput168 = new BitSet(new long[]{0x0000000000780002L});
    public static final BitSet FOLLOW_actionChannelSelector_in_actionInput171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionInput_in_actionInputs182 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_actionInputs185 = new BitSet(new long[]{0x0000000004001000L});
    public static final BitSet FOLLOW_actionInput_in_actionInputs187 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_ID_in_actionOutput200 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_actionOutput202 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_actionOutput206 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expressions_in_actionOutput208 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_actionOutput210 = new BitSet(new long[]{0x0000000020780002L});
    public static final BitSet FOLLOW_actionRepeat_in_actionOutput212 = new BitSet(new long[]{0x0000000000780002L});
    public static final BitSet FOLLOW_actionChannelSelector_in_actionOutput215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionOutput_in_actionOutputs226 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_actionOutputs229 = new BitSet(new long[]{0x0000000004001000L});
    public static final BitSet FOLLOW_actionOutput_in_actionOutputs231 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_29_in_actionRepeat242 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_actionRepeat244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_actionStatements253 = new BitSet(new long[]{0x0000400000001002L,0x00000000002C1080L});
    public static final BitSet FOLLOW_statement_in_actionStatements255 = new BitSet(new long[]{0x0000400000001002L,0x00000000002C1080L});
    public static final BitSet FOLLOW_actorImport_in_actor270 = new BitSet(new long[]{0x0000800080000000L});
    public static final BitSet FOLLOW_31_in_actor273 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_actor277 = new BitSet(new long[]{0x0000000104000000L});
    public static final BitSet FOLLOW_26_in_actor280 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_typePar_in_actor282 = new BitSet(new long[]{0x0000000008001000L});
    public static final BitSet FOLLOW_27_in_actor285 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_actor289 = new BitSet(new long[]{0x0000000200001000L});
    public static final BitSet FOLLOW_actorParameters_in_actor291 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_actor294 = new BitSet(new long[]{0x0001000400001000L});
    public static final BitSet FOLLOW_actorPortDecls_in_actor299 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_actor302 = new BitSet(new long[]{0x0001000002001000L});
    public static final BitSet FOLLOW_actorPortDecls_in_actor306 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_actor309 = new BitSet(new long[]{0x000028A800001000L,0x000000000000C000L});
    public static final BitSet FOLLOW_actorDeclarations_in_actor312 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_actor314 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_actor316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration371 = new BitSet(new long[]{0x000000B106001000L});
    public static final BitSet FOLLOW_36_in_actorDeclaration380 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration382 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_actorDeclaration386 = new BitSet(new long[]{0x000000A000000000L});
    public static final BitSet FOLLOW_37_in_actorDeclaration397 = new BitSet(new long[]{0x0000000404001000L});
    public static final BitSet FOLLOW_actionInputs_in_actorDeclaration399 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_actorDeclaration402 = new BitSet(new long[]{0x0000004845801000L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration404 = new BitSet(new long[]{0x0000004841800000L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration407 = new BitSet(new long[]{0x0000004840800000L});
    public static final BitSet FOLLOW_actionDelay_in_actorDeclaration410 = new BitSet(new long[]{0x0000004840000000L});
    public static final BitSet FOLLOW_38_in_actorDeclaration414 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration416 = new BitSet(new long[]{0x0000000840000000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration420 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_actorDeclaration423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_actorDeclaration440 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_actorDeclaration442 = new BitSet(new long[]{0x0000004845801000L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration444 = new BitSet(new long[]{0x0000004841800000L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration447 = new BitSet(new long[]{0x0000004840800000L});
    public static final BitSet FOLLOW_actionDelay_in_actorDeclaration450 = new BitSet(new long[]{0x0000004840000000L});
    public static final BitSet FOLLOW_38_in_actorDeclaration454 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration456 = new BitSet(new long[]{0x0000000840000000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration460 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_actorDeclaration463 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_actorDeclaration480 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_typePar_in_actorDeclaration482 = new BitSet(new long[]{0x0000000008001000L});
    public static final BitSet FOLLOW_27_in_actorDeclaration485 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_32_in_actorDeclaration489 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_typeAttrs_in_actorDeclaration491 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_actorDeclaration493 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration497 = new BitSet(new long[]{0x0000070000000000L});
    public static final BitSet FOLLOW_40_in_actorDeclaration500 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration502 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_41_in_actorDeclaration506 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration508 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_actorDeclaration512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_actorDeclaration521 = new BitSet(new long[]{0x0000000404001000L});
    public static final BitSet FOLLOW_actionInputs_in_actorDeclaration523 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_actorDeclaration526 = new BitSet(new long[]{0x0000004845801000L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration528 = new BitSet(new long[]{0x0000004841800000L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration531 = new BitSet(new long[]{0x0000004840800000L});
    public static final BitSet FOLLOW_actionDelay_in_actorDeclaration534 = new BitSet(new long[]{0x0000004840000000L});
    public static final BitSet FOLLOW_38_in_actorDeclaration538 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration540 = new BitSet(new long[]{0x0000000840000000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration544 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_actorDeclaration547 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_actorDeclaration557 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_actorDeclaration559 = new BitSet(new long[]{0x0000004845801000L});
    public static final BitSet FOLLOW_actionOutputs_in_actorDeclaration561 = new BitSet(new long[]{0x0000004841800000L});
    public static final BitSet FOLLOW_actionGuards_in_actorDeclaration564 = new BitSet(new long[]{0x0000004840800000L});
    public static final BitSet FOLLOW_actionDelay_in_actorDeclaration567 = new BitSet(new long[]{0x0000004840000000L});
    public static final BitSet FOLLOW_38_in_actorDeclaration571 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration573 = new BitSet(new long[]{0x0000000840000000L});
    public static final BitSet FOLLOW_actionStatements_in_actorDeclaration577 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_actorDeclaration580 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_priorityOrder_in_actorDeclaration589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_actorDeclaration596 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration598 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_actorDeclaration600 = new BitSet(new long[]{0x0000000200001000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration603 = new BitSet(new long[]{0x0000000210000000L});
    public static final BitSet FOLLOW_28_in_actorDeclaration606 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration608 = new BitSet(new long[]{0x0000000210000000L});
    public static final BitSet FOLLOW_33_in_actorDeclaration614 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_actorDeclaration616 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_typeDef_in_actorDeclaration618 = new BitSet(new long[]{0x0000004002000000L});
    public static final BitSet FOLLOW_38_in_actorDeclaration625 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration627 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_actorDeclaration631 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_actorDeclaration639 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_actorDeclaration645 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_actorDeclaration653 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_actorDeclaration655 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_actorDeclaration657 = new BitSet(new long[]{0x0000000200001000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration660 = new BitSet(new long[]{0x0000000210000000L});
    public static final BitSet FOLLOW_28_in_actorDeclaration663 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_actorDeclaration665 = new BitSet(new long[]{0x0000000210000000L});
    public static final BitSet FOLLOW_33_in_actorDeclaration671 = new BitSet(new long[]{0x0000404000000000L});
    public static final BitSet FOLLOW_38_in_actorDeclaration678 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_varDecls_in_actorDeclaration680 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_actorDeclaration688 = new BitSet(new long[]{0x0000400800001000L,0x00000000002C1080L});
    public static final BitSet FOLLOW_statement_in_actorDeclaration690 = new BitSet(new long[]{0x0000400800001000L,0x00000000002C1080L});
    public static final BitSet FOLLOW_35_in_actorDeclaration693 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations703 = new BitSet(new long[]{0x000028A000001002L,0x000000000000C000L});
    public static final BitSet FOLLOW_schedule_in_actorDeclarations707 = new BitSet(new long[]{0x000028A000001002L,0x0000000000004000L});
    public static final BitSet FOLLOW_actorDeclaration_in_actorDeclarations709 = new BitSet(new long[]{0x000028A000001002L,0x0000000000004000L});
    public static final BitSet FOLLOW_47_in_actorImport726 = new BitSet(new long[]{0x0000000000081000L});
    public static final BitSet FOLLOW_19_in_actorImport731 = new BitSet(new long[]{0x0000000000081000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport733 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_actorImport735 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedIdent_in_actorImport741 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_actorImport743 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_actorParameter758 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_actorParameter760 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_40_in_actorParameter763 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_actorParameter765 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters776 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_actorParameters779 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_actorParameter_in_actorParameters781 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_48_in_actorPortDecl798 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_typeDef_in_actorPortDecl800 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_actorPortDecl802 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_actorPortDecl809 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_actorPortDecl811 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actorPortDecl_in_actorPortDecls828 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_actorPortDecls831 = new BitSet(new long[]{0x0001000000001000L});
    public static final BitSet FOLLOW_actorPortDecl_in_actorPortDecls833 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_and_expr_in_expression852 = new BitSet(new long[]{0x0006000000000002L});
    public static final BitSet FOLLOW_set_in_expression855 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_and_expr_in_expression863 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bitor_expr_in_and_expr874 = new BitSet(new long[]{0x0018000000000002L});
    public static final BitSet FOLLOW_set_in_and_expr877 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_bitor_expr_in_and_expr885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bitand_expr_in_bitor_expr896 = new BitSet(new long[]{0x0020000000000002L});
    public static final BitSet FOLLOW_53_in_bitor_expr899 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_bitand_expr_in_bitor_expr901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eq_expr_in_bitand_expr912 = new BitSet(new long[]{0x0040000000000002L});
    public static final BitSet FOLLOW_54_in_bitand_expr915 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_eq_expr_in_bitand_expr917 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rel_expr_in_eq_expr928 = new BitSet(new long[]{0x0080010000000002L});
    public static final BitSet FOLLOW_set_in_eq_expr931 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_rel_expr_in_eq_expr939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_shift_expr_in_rel_expr950 = new BitSet(new long[]{0x0F00000000000002L});
    public static final BitSet FOLLOW_set_in_rel_expr953 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_shift_expr_in_rel_expr969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_add_expr_in_shift_expr980 = new BitSet(new long[]{0x3000000000000002L});
    public static final BitSet FOLLOW_set_in_shift_expr983 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_add_expr_in_shift_expr991 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_mul_expr_in_add_expr1002 = new BitSet(new long[]{0xC000000000000002L});
    public static final BitSet FOLLOW_set_in_add_expr1005 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_mul_expr_in_add_expr1013 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exp_expr_in_mul_expr1024 = new BitSet(new long[]{0x0000000000000002L,0x000000000000000FL});
    public static final BitSet FOLLOW_set_in_mul_expr1027 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_exp_expr_in_mul_expr1043 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_un_expr_in_exp_expr1054 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_exp_expr1057 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_un_expr_in_exp_expr1059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfix_expression_in_un_expr1070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_un_expr1075 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_un_expr_in_un_expr1077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_un_expr1082 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_un_expr_in_un_expr1084 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_un_expr1089 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_un_expr_in_un_expr1091 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_postfix_expression1102 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1104 = new BitSet(new long[]{0x000000000A000000L});
    public static final BitSet FOLLOW_25_in_postfix_expression1107 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_expressionGenerators_in_postfix_expression1109 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_postfix_expression1113 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_postfix_expression1117 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1119 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_postfix_expression1121 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1123 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_postfix_expression1125 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1127 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_postfix_expression1129 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_postfix_expression1133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_postfix_expression1137 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_postfix_expression1139 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_postfix_expression1141 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_postfix_expression1145 = new BitSet(new long[]{0x0000000104000002L});
    public static final BitSet FOLLOW_32_in_postfix_expression1153 = new BitSet(new long[]{0x8000000304007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1155 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_postfix_expression1158 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_postfix_expression1166 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expressions_in_postfix_expression1168 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_postfix_expression1170 = new BitSet(new long[]{0x0000000004000002L});
    public static final BitSet FOLLOW_74_in_constant1184 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_constant1188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_constant1192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_constant1196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_expressionGenerator1206 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_typeDef_in_expressionGenerator1208 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_expressionGenerator1210 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_expressionGenerator1212 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_expressionGenerator1214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators1224 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_expressionGenerators1227 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_expressionGenerator_in_expressionGenerators1229 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_expression_in_expressions1240 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_expressions1243 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_expressions1245 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_ID_in_idents1261 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_idents1264 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_idents1266 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality1282 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_priorityInequality1285 = new BitSet(new long[]{0x0000000000081000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_priorityInequality1287 = new BitSet(new long[]{0x0200040000000000L});
    public static final BitSet FOLLOW_42_in_priorityInequality1291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_priorityOrder1301 = new BitSet(new long[]{0x0000000800081000L});
    public static final BitSet FOLLOW_priorityInequality_in_priorityOrder1304 = new BitSet(new long[]{0x0000000800081000L});
    public static final BitSet FOLLOW_35_in_priorityOrder1308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent1322 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_36_in_qualifiedIdent1325 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_qualifiedIdent1327 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_79_in_schedule1345 = new BitSet(new long[]{0x0000000000000000L,0x0000000000030000L});
    public static final BitSet FOLLOW_80_in_schedule1352 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_schedule1354 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_schedule1356 = new BitSet(new long[]{0x0000000800001000L});
    public static final BitSet FOLLOW_stateTransition_in_schedule1358 = new BitSet(new long[]{0x0000000800001000L});
    public static final BitSet FOLLOW_35_in_schedule1361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_schedule1371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stateTransition1383 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_stateTransition1385 = new BitSet(new long[]{0x0000000000081000L});
    public static final BitSet FOLLOW_qualifiedIdent_in_stateTransition1387 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_stateTransition1389 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_stateTransition1391 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_stateTransition1393 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_stateTransition1395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_statement1411 = new BitSet(new long[]{0x0000404800001000L,0x00000000002C1080L});
    public static final BitSet FOLLOW_38_in_statement1414 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_varDecls_in_statement1416 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_statement1418 = new BitSet(new long[]{0x0000400800001000L,0x00000000002C1080L});
    public static final BitSet FOLLOW_statement_in_statement1422 = new BitSet(new long[]{0x0000400800001000L,0x00000000002C1080L});
    public static final BitSet FOLLOW_35_in_statement1425 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_statement1431 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_statement1437 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_statement1443 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_varDeclNoExpr_in_statement1445 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_statement1447 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_statement1450 = new BitSet(new long[]{0x0000004040000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_statement1453 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_statement1455 = new BitSet(new long[]{0x0000004040000000L});
    public static final BitSet FOLLOW_38_in_statement1461 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_varDecls_in_statement1463 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_statement1467 = new BitSet(new long[]{0x0000400800001000L,0x00000000002C1080L});
    public static final BitSet FOLLOW_statement_in_statement1469 = new BitSet(new long[]{0x0000400800001000L,0x00000000002C1080L});
    public static final BitSet FOLLOW_35_in_statement1472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_statement1478 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_statement1480 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_statement1482 = new BitSet(new long[]{0x0000400800001000L,0x00000000002C1280L});
    public static final BitSet FOLLOW_statement_in_statement1484 = new BitSet(new long[]{0x0000400800001000L,0x00000000002C1280L});
    public static final BitSet FOLLOW_73_in_statement1488 = new BitSet(new long[]{0x0000400800001000L,0x00000000002C1080L});
    public static final BitSet FOLLOW_statement_in_statement1490 = new BitSet(new long[]{0x0000400800001000L,0x00000000002C1080L});
    public static final BitSet FOLLOW_35_in_statement1495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_85_in_statement1501 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_statement1503 = new BitSet(new long[]{0x0000004040000000L});
    public static final BitSet FOLLOW_38_in_statement1506 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_varDecls_in_statement1508 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_statement1512 = new BitSet(new long[]{0x0000400800001000L,0x00000000002C1080L});
    public static final BitSet FOLLOW_statement_in_statement1514 = new BitSet(new long[]{0x0000400800001000L,0x00000000002C1080L});
    public static final BitSet FOLLOW_35_in_statement1517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_statement1524 = new BitSet(new long[]{0x0000020104000000L});
    public static final BitSet FOLLOW_26_in_statement1534 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expressions_in_statement1536 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_statement1538 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_statement1542 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_statement1544 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_statement1546 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_statement1556 = new BitSet(new long[]{0x8000000304007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expressions_in_statement1558 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_statement1561 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_statement1563 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_typeAttr1581 = new BitSet(new long[]{0x0000010002000000L});
    public static final BitSet FOLLOW_25_in_typeAttr1584 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_typeDef_in_typeAttr1586 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_typeAttr1600 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_typeAttr1602 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs1620 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_typeAttrs1623 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs1625 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_ID_in_typeDef1642 = new BitSet(new long[]{0x0000000104000002L});
    public static final BitSet FOLLOW_26_in_typeDef1647 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_typePar_in_typeDef1649 = new BitSet(new long[]{0x0000000008001000L});
    public static final BitSet FOLLOW_27_in_typeDef1652 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_typeDef1658 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_typeAttrs_in_typeDef1662 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_typeDef1664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_typePar1694 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_56_in_typePar1697 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_typeDef_in_typePar1699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_varDecl1717 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_varDecl1719 = new BitSet(new long[]{0x0000030000000002L});
    public static final BitSet FOLLOW_40_in_varDecl1722 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_varDecl1724 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_varDecl1728 = new BitSet(new long[]{0x8000000104007000L,0x0000000000000CE0L});
    public static final BitSet FOLLOW_expression_in_varDecl1730 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeDef_in_varDeclNoExpr1741 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ID_in_varDeclNoExpr1743 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varDecl_in_varDecls1752 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_varDecls1755 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_varDecl_in_varDecls1757 = new BitSet(new long[]{0x0000000010000002L});

}