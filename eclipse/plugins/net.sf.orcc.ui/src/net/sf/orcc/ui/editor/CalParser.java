// $ANTLR 3.3 Nov 30, 2010 12:45:30 D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g 2011-02-10 09:56:25

package net.sf.orcc.ui.editor;


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
public class CalParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "Attribute", "Connector", "EntityDecl", "EntityExpr", "EntityPar", "Network", "StructureStmt", "VarDecl", "Actor", "Dot", "Empty", "Name", "Inputs", "Outputs", "PortDecl", "QualifiedId", "Parameter", "Type", "TypeAttr", "ExprAttr", "TypePar", "BinOp", "Boolean", "Expression", "Integer", "List", "Minus", "Not", "Real", "String", "UnOp", "Var", "NETWORK", "QID", "LBRACKET", "RBRACKET", "LPAREN", "RPAREN", "COLON", "END", "DOUBLE_EQUAL_ARROW", "VAR", "MUTABLE", "EQ", "COLON_EQUAL", "SEMICOLON", "ENTITIES", "COMMA", "STRUCTURE", "DOUBLE_DASH_ARROW", "DOT", "LBRACE", "RBRACE", "NATIVE", "ACTOR", "PACKAGE", "IMPORT", "QID_WILDCARD", "MULTI", "LT", "MINUS", "NOT", "PLUS", "TIMES", "DIV", "XOR", "FLOAT", "INTEGER", "STRING", "TRUE", "FALSE", "ALL", "ID", "LINE_COMMENT", "MULTI_LINE_COMMENT", "WHITESPACE", "GE", "GT", "LE", "NE", "ARROW", "DOUBLE_DOT", "AND", "OR", "SHARP"
    };
    public static final int EOF=-1;
    public static final int Attribute=4;
    public static final int Connector=5;
    public static final int EntityDecl=6;
    public static final int EntityExpr=7;
    public static final int EntityPar=8;
    public static final int Network=9;
    public static final int StructureStmt=10;
    public static final int VarDecl=11;
    public static final int Actor=12;
    public static final int Dot=13;
    public static final int Empty=14;
    public static final int Name=15;
    public static final int Inputs=16;
    public static final int Outputs=17;
    public static final int PortDecl=18;
    public static final int QualifiedId=19;
    public static final int Parameter=20;
    public static final int Type=21;
    public static final int TypeAttr=22;
    public static final int ExprAttr=23;
    public static final int TypePar=24;
    public static final int BinOp=25;
    public static final int Boolean=26;
    public static final int Expression=27;
    public static final int Integer=28;
    public static final int List=29;
    public static final int Minus=30;
    public static final int Not=31;
    public static final int Real=32;
    public static final int String=33;
    public static final int UnOp=34;
    public static final int Var=35;
    public static final int NETWORK=36;
    public static final int QID=37;
    public static final int LBRACKET=38;
    public static final int RBRACKET=39;
    public static final int LPAREN=40;
    public static final int RPAREN=41;
    public static final int COLON=42;
    public static final int END=43;
    public static final int DOUBLE_EQUAL_ARROW=44;
    public static final int VAR=45;
    public static final int MUTABLE=46;
    public static final int EQ=47;
    public static final int COLON_EQUAL=48;
    public static final int SEMICOLON=49;
    public static final int ENTITIES=50;
    public static final int COMMA=51;
    public static final int STRUCTURE=52;
    public static final int DOUBLE_DASH_ARROW=53;
    public static final int DOT=54;
    public static final int LBRACE=55;
    public static final int RBRACE=56;
    public static final int NATIVE=57;
    public static final int ACTOR=58;
    public static final int PACKAGE=59;
    public static final int IMPORT=60;
    public static final int QID_WILDCARD=61;
    public static final int MULTI=62;
    public static final int LT=63;
    public static final int MINUS=64;
    public static final int NOT=65;
    public static final int PLUS=66;
    public static final int TIMES=67;
    public static final int DIV=68;
    public static final int XOR=69;
    public static final int FLOAT=70;
    public static final int INTEGER=71;
    public static final int STRING=72;
    public static final int TRUE=73;
    public static final int FALSE=74;
    public static final int ALL=75;
    public static final int ID=76;
    public static final int LINE_COMMENT=77;
    public static final int MULTI_LINE_COMMENT=78;
    public static final int WHITESPACE=79;
    public static final int GE=80;
    public static final int GT=81;
    public static final int LE=82;
    public static final int NE=83;
    public static final int ARROW=84;
    public static final int DOUBLE_DOT=85;
    public static final int AND=86;
    public static final int OR=87;
    public static final int SHARP=88;

    // delegates
    // delegators


        public CalParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public CalParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return CalParser.tokenNames; }
    public String getGrammarFileName() { return "D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g"; }


    public static class network_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "network"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:90:1: network : NETWORK QID ( LBRACKET ( typePars )? RBRACKET )? LPAREN ( parameters )? RPAREN portSignature COLON ( oneImport )* ( varDeclSection )? ( entitySection )? ( structureSection )? END EOF -> ^( Network QID ( parameters )? portSignature ( varDeclSection )? ( entitySection )? ( structureSection )? ) ;
    public final CalParser.network_return network() throws RecognitionException {
        CalParser.network_return retval = new CalParser.network_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NETWORK1=null;
        Token QID2=null;
        Token LBRACKET3=null;
        Token RBRACKET5=null;
        Token LPAREN6=null;
        Token RPAREN8=null;
        Token COLON10=null;
        Token END15=null;
        Token EOF16=null;
        CalParser.typePars_return typePars4 = null;

        CalParser.parameters_return parameters7 = null;

        CalParser.portSignature_return portSignature9 = null;

        CalParser.oneImport_return oneImport11 = null;

        CalParser.varDeclSection_return varDeclSection12 = null;

        CalParser.entitySection_return entitySection13 = null;

        CalParser.structureSection_return structureSection14 = null;


        Object NETWORK1_tree=null;
        Object QID2_tree=null;
        Object LBRACKET3_tree=null;
        Object RBRACKET5_tree=null;
        Object LPAREN6_tree=null;
        Object RPAREN8_tree=null;
        Object COLON10_tree=null;
        Object END15_tree=null;
        Object EOF16_tree=null;
        RewriteRuleTokenStream stream_LBRACKET=new RewriteRuleTokenStream(adaptor,"token LBRACKET");
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_NETWORK=new RewriteRuleTokenStream(adaptor,"token NETWORK");
        RewriteRuleTokenStream stream_RBRACKET=new RewriteRuleTokenStream(adaptor,"token RBRACKET");
        RewriteRuleTokenStream stream_END=new RewriteRuleTokenStream(adaptor,"token END");
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleTokenStream stream_QID=new RewriteRuleTokenStream(adaptor,"token QID");
        RewriteRuleSubtreeStream stream_typePars=new RewriteRuleSubtreeStream(adaptor,"rule typePars");
        RewriteRuleSubtreeStream stream_varDeclSection=new RewriteRuleSubtreeStream(adaptor,"rule varDeclSection");
        RewriteRuleSubtreeStream stream_structureSection=new RewriteRuleSubtreeStream(adaptor,"rule structureSection");
        RewriteRuleSubtreeStream stream_entitySection=new RewriteRuleSubtreeStream(adaptor,"rule entitySection");
        RewriteRuleSubtreeStream stream_portSignature=new RewriteRuleSubtreeStream(adaptor,"rule portSignature");
        RewriteRuleSubtreeStream stream_parameters=new RewriteRuleSubtreeStream(adaptor,"rule parameters");
        RewriteRuleSubtreeStream stream_oneImport=new RewriteRuleSubtreeStream(adaptor,"rule oneImport");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:90:8: ( NETWORK QID ( LBRACKET ( typePars )? RBRACKET )? LPAREN ( parameters )? RPAREN portSignature COLON ( oneImport )* ( varDeclSection )? ( entitySection )? ( structureSection )? END EOF -> ^( Network QID ( parameters )? portSignature ( varDeclSection )? ( entitySection )? ( structureSection )? ) )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:90:10: NETWORK QID ( LBRACKET ( typePars )? RBRACKET )? LPAREN ( parameters )? RPAREN portSignature COLON ( oneImport )* ( varDeclSection )? ( entitySection )? ( structureSection )? END EOF
            {
            NETWORK1=(Token)match(input,NETWORK,FOLLOW_NETWORK_in_network236);  
            stream_NETWORK.add(NETWORK1);

            QID2=(Token)match(input,QID,FOLLOW_QID_in_network238);  
            stream_QID.add(QID2);

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:90:22: ( LBRACKET ( typePars )? RBRACKET )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==LBRACKET) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:90:23: LBRACKET ( typePars )? RBRACKET
                    {
                    LBRACKET3=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_network241);  
                    stream_LBRACKET.add(LBRACKET3);

                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:90:32: ( typePars )?
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==QID) ) {
                        alt1=1;
                    }
                    switch (alt1) {
                        case 1 :
                            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:90:32: typePars
                            {
                            pushFollow(FOLLOW_typePars_in_network243);
                            typePars4=typePars();

                            state._fsp--;

                            stream_typePars.add(typePars4.getTree());

                            }
                            break;

                    }

                    RBRACKET5=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_network246);  
                    stream_RBRACKET.add(RBRACKET5);


                    }
                    break;

            }

            LPAREN6=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_network252);  
            stream_LPAREN.add(LPAREN6);

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:91:10: ( parameters )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==QID) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:91:10: parameters
                    {
                    pushFollow(FOLLOW_parameters_in_network254);
                    parameters7=parameters();

                    state._fsp--;

                    stream_parameters.add(parameters7.getTree());

                    }
                    break;

            }

            RPAREN8=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_network257);  
            stream_RPAREN.add(RPAREN8);

            pushFollow(FOLLOW_portSignature_in_network261);
            portSignature9=portSignature();

            state._fsp--;

            stream_portSignature.add(portSignature9.getTree());
            COLON10=(Token)match(input,COLON,FOLLOW_COLON_in_network263);  
            stream_COLON.add(COLON10);

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:93:3: ( oneImport )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==IMPORT) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:93:3: oneImport
            	    {
            	    pushFollow(FOLLOW_oneImport_in_network267);
            	    oneImport11=oneImport();

            	    state._fsp--;

            	    stream_oneImport.add(oneImport11.getTree());

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:93:14: ( varDeclSection )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==VAR) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:93:14: varDeclSection
                    {
                    pushFollow(FOLLOW_varDeclSection_in_network270);
                    varDeclSection12=varDeclSection();

                    state._fsp--;

                    stream_varDeclSection.add(varDeclSection12.getTree());

                    }
                    break;

            }

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:94:3: ( entitySection )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==ENTITIES) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:94:3: entitySection
                    {
                    pushFollow(FOLLOW_entitySection_in_network275);
                    entitySection13=entitySection();

                    state._fsp--;

                    stream_entitySection.add(entitySection13.getTree());

                    }
                    break;

            }

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:94:18: ( structureSection )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==STRUCTURE) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:94:18: structureSection
                    {
                    pushFollow(FOLLOW_structureSection_in_network278);
                    structureSection14=structureSection();

                    state._fsp--;

                    stream_structureSection.add(structureSection14.getTree());

                    }
                    break;

            }

            END15=(Token)match(input,END,FOLLOW_END_in_network283);  
            stream_END.add(END15);

            EOF16=(Token)match(input,EOF,FOLLOW_EOF_in_network285);  
            stream_EOF.add(EOF16);



            // AST REWRITE
            // elements: entitySection, structureSection, portSignature, QID, parameters, varDeclSection
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 95:11: -> ^( Network QID ( parameters )? portSignature ( varDeclSection )? ( entitySection )? ( structureSection )? )
            {
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:96:5: ^( Network QID ( parameters )? portSignature ( varDeclSection )? ( entitySection )? ( structureSection )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Network, "Network"), root_1);

                adaptor.addChild(root_1, stream_QID.nextNode());
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:96:19: ( parameters )?
                if ( stream_parameters.hasNext() ) {
                    adaptor.addChild(root_1, stream_parameters.nextTree());

                }
                stream_parameters.reset();
                adaptor.addChild(root_1, stream_portSignature.nextTree());
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:97:7: ( varDeclSection )?
                if ( stream_varDeclSection.hasNext() ) {
                    adaptor.addChild(root_1, stream_varDeclSection.nextTree());

                }
                stream_varDeclSection.reset();
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:97:23: ( entitySection )?
                if ( stream_entitySection.hasNext() ) {
                    adaptor.addChild(root_1, stream_entitySection.nextTree());

                }
                stream_entitySection.reset();
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:97:38: ( structureSection )?
                if ( stream_structureSection.hasNext() ) {
                    adaptor.addChild(root_1, stream_structureSection.nextTree());

                }
                stream_structureSection.reset();

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
    // $ANTLR end "network"

    public static class portSignature_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "portSignature"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:99:1: portSignature : inputPorts DOUBLE_EQUAL_ARROW outputPorts -> inputPorts outputPorts ;
    public final CalParser.portSignature_return portSignature() throws RecognitionException {
        CalParser.portSignature_return retval = new CalParser.portSignature_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DOUBLE_EQUAL_ARROW18=null;
        CalParser.inputPorts_return inputPorts17 = null;

        CalParser.outputPorts_return outputPorts19 = null;


        Object DOUBLE_EQUAL_ARROW18_tree=null;
        RewriteRuleTokenStream stream_DOUBLE_EQUAL_ARROW=new RewriteRuleTokenStream(adaptor,"token DOUBLE_EQUAL_ARROW");
        RewriteRuleSubtreeStream stream_inputPorts=new RewriteRuleSubtreeStream(adaptor,"rule inputPorts");
        RewriteRuleSubtreeStream stream_outputPorts=new RewriteRuleSubtreeStream(adaptor,"rule outputPorts");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:99:14: ( inputPorts DOUBLE_EQUAL_ARROW outputPorts -> inputPorts outputPorts )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:99:16: inputPorts DOUBLE_EQUAL_ARROW outputPorts
            {
            pushFollow(FOLLOW_inputPorts_in_portSignature324);
            inputPorts17=inputPorts();

            state._fsp--;

            stream_inputPorts.add(inputPorts17.getTree());
            DOUBLE_EQUAL_ARROW18=(Token)match(input,DOUBLE_EQUAL_ARROW,FOLLOW_DOUBLE_EQUAL_ARROW_in_portSignature326);  
            stream_DOUBLE_EQUAL_ARROW.add(DOUBLE_EQUAL_ARROW18);

            pushFollow(FOLLOW_outputPorts_in_portSignature328);
            outputPorts19=outputPorts();

            state._fsp--;

            stream_outputPorts.add(outputPorts19.getTree());


            // AST REWRITE
            // elements: outputPorts, inputPorts
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 99:58: -> inputPorts outputPorts
            {
                adaptor.addChild(root_0, stream_inputPorts.nextTree());
                adaptor.addChild(root_0, stream_outputPorts.nextTree());

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
    // $ANTLR end "portSignature"

    public static class inputPorts_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "inputPorts"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:101:1: inputPorts : ( portDecls -> ^( Inputs portDecls ) | -> ^( Inputs Empty ) );
    public final CalParser.inputPorts_return inputPorts() throws RecognitionException {
        CalParser.inputPorts_return retval = new CalParser.inputPorts_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        CalParser.portDecls_return portDecls20 = null;


        RewriteRuleSubtreeStream stream_portDecls=new RewriteRuleSubtreeStream(adaptor,"rule portDecls");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:101:11: ( portDecls -> ^( Inputs portDecls ) | -> ^( Inputs Empty ) )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==QID||LA8_0==MULTI) ) {
                alt8=1;
            }
            else if ( (LA8_0==DOUBLE_EQUAL_ARROW) ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:101:13: portDecls
                    {
                    pushFollow(FOLLOW_portDecls_in_inputPorts341);
                    portDecls20=portDecls();

                    state._fsp--;

                    stream_portDecls.add(portDecls20.getTree());


                    // AST REWRITE
                    // elements: portDecls
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 101:23: -> ^( Inputs portDecls )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:101:26: ^( Inputs portDecls )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Inputs, "Inputs"), root_1);

                        adaptor.addChild(root_1, stream_portDecls.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:101:48: 
                    {

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
                    // 101:48: -> ^( Inputs Empty )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:101:51: ^( Inputs Empty )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Inputs, "Inputs"), root_1);

                        adaptor.addChild(root_1, (Object)adaptor.create(Empty, "Empty"));

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
    // $ANTLR end "inputPorts"

    public static class outputPorts_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "outputPorts"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:103:1: outputPorts : ( portDecls -> ^( Outputs portDecls ) | -> ^( Outputs Empty ) );
    public final CalParser.outputPorts_return outputPorts() throws RecognitionException {
        CalParser.outputPorts_return retval = new CalParser.outputPorts_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        CalParser.portDecls_return portDecls21 = null;


        RewriteRuleSubtreeStream stream_portDecls=new RewriteRuleSubtreeStream(adaptor,"rule portDecls");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:103:12: ( portDecls -> ^( Outputs portDecls ) | -> ^( Outputs Empty ) )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==QID||LA9_0==MULTI) ) {
                alt9=1;
            }
            else if ( (LA9_0==COLON) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:103:14: portDecls
                    {
                    pushFollow(FOLLOW_portDecls_in_outputPorts366);
                    portDecls21=portDecls();

                    state._fsp--;

                    stream_portDecls.add(portDecls21.getTree());


                    // AST REWRITE
                    // elements: portDecls
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 103:24: -> ^( Outputs portDecls )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:103:27: ^( Outputs portDecls )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Outputs, "Outputs"), root_1);

                        adaptor.addChild(root_1, stream_portDecls.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:103:50: 
                    {

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
                    // 103:50: -> ^( Outputs Empty )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:103:53: ^( Outputs Empty )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Outputs, "Outputs"), root_1);

                        adaptor.addChild(root_1, (Object)adaptor.create(Empty, "Empty"));

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
    // $ANTLR end "outputPorts"

    public static class varDeclSection_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "varDeclSection"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:106:1: varDeclSection : VAR ( varDecl )+ -> ( varDecl )+ ;
    public final CalParser.varDeclSection_return varDeclSection() throws RecognitionException {
        CalParser.varDeclSection_return retval = new CalParser.varDeclSection_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token VAR22=null;
        CalParser.varDecl_return varDecl23 = null;


        Object VAR22_tree=null;
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleSubtreeStream stream_varDecl=new RewriteRuleSubtreeStream(adaptor,"rule varDecl");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:106:15: ( VAR ( varDecl )+ -> ( varDecl )+ )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:106:17: VAR ( varDecl )+
            {
            VAR22=(Token)match(input,VAR,FOLLOW_VAR_in_varDeclSection392);  
            stream_VAR.add(VAR22);

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:106:21: ( varDecl )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==QID||LA10_0==MUTABLE) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:106:21: varDecl
            	    {
            	    pushFollow(FOLLOW_varDecl_in_varDeclSection394);
            	    varDecl23=varDecl();

            	    state._fsp--;

            	    stream_varDecl.add(varDecl23.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
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
            // 106:30: -> ( varDecl )+
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
    // $ANTLR end "varDeclSection"

    public static class varDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "varDecl"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:108:1: varDecl : ( MUTABLE )? typeAndId ( ( EQ | COLON_EQUAL ) expression -> ^( VarDecl typeAndId ^( Expression expression ) ) | -> ^( VarDecl typeAndId ) ) SEMICOLON ;
    public final CalParser.varDecl_return varDecl() throws RecognitionException {
        CalParser.varDecl_return retval = new CalParser.varDecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MUTABLE24=null;
        Token EQ26=null;
        Token COLON_EQUAL27=null;
        Token SEMICOLON29=null;
        CalParser.typeAndId_return typeAndId25 = null;

        CalParser.expression_return expression28 = null;


        Object MUTABLE24_tree=null;
        Object EQ26_tree=null;
        Object COLON_EQUAL27_tree=null;
        Object SEMICOLON29_tree=null;
        RewriteRuleTokenStream stream_COLON_EQUAL=new RewriteRuleTokenStream(adaptor,"token COLON_EQUAL");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_EQ=new RewriteRuleTokenStream(adaptor,"token EQ");
        RewriteRuleTokenStream stream_MUTABLE=new RewriteRuleTokenStream(adaptor,"token MUTABLE");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeAndId=new RewriteRuleSubtreeStream(adaptor,"rule typeAndId");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:108:8: ( ( MUTABLE )? typeAndId ( ( EQ | COLON_EQUAL ) expression -> ^( VarDecl typeAndId ^( Expression expression ) ) | -> ^( VarDecl typeAndId ) ) SEMICOLON )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:108:10: ( MUTABLE )? typeAndId ( ( EQ | COLON_EQUAL ) expression -> ^( VarDecl typeAndId ^( Expression expression ) ) | -> ^( VarDecl typeAndId ) ) SEMICOLON
            {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:108:10: ( MUTABLE )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==MUTABLE) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:108:10: MUTABLE
                    {
                    MUTABLE24=(Token)match(input,MUTABLE,FOLLOW_MUTABLE_in_varDecl407);  
                    stream_MUTABLE.add(MUTABLE24);


                    }
                    break;

            }

            pushFollow(FOLLOW_typeAndId_in_varDecl410);
            typeAndId25=typeAndId();

            state._fsp--;

            stream_typeAndId.add(typeAndId25.getTree());
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:109:3: ( ( EQ | COLON_EQUAL ) expression -> ^( VarDecl typeAndId ^( Expression expression ) ) | -> ^( VarDecl typeAndId ) )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0>=EQ && LA13_0<=COLON_EQUAL)) ) {
                alt13=1;
            }
            else if ( (LA13_0==SEMICOLON) ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:109:4: ( EQ | COLON_EQUAL ) expression
                    {
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:109:4: ( EQ | COLON_EQUAL )
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==EQ) ) {
                        alt12=1;
                    }
                    else if ( (LA12_0==COLON_EQUAL) ) {
                        alt12=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 12, 0, input);

                        throw nvae;
                    }
                    switch (alt12) {
                        case 1 :
                            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:109:5: EQ
                            {
                            EQ26=(Token)match(input,EQ,FOLLOW_EQ_in_varDecl416);  
                            stream_EQ.add(EQ26);


                            }
                            break;
                        case 2 :
                            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:109:10: COLON_EQUAL
                            {
                            COLON_EQUAL27=(Token)match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_varDecl420);  
                            stream_COLON_EQUAL.add(COLON_EQUAL27);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_expression_in_varDecl423);
                    expression28=expression();

                    state._fsp--;

                    stream_expression.add(expression28.getTree());


                    // AST REWRITE
                    // elements: typeAndId, expression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 109:34: -> ^( VarDecl typeAndId ^( Expression expression ) )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:109:37: ^( VarDecl typeAndId ^( Expression expression ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(VarDecl, "VarDecl"), root_1);

                        adaptor.addChild(root_1, stream_typeAndId.nextTree());
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:109:57: ^( Expression expression )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Expression, "Expression"), root_2);

                        adaptor.addChild(root_2, stream_expression.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:110:5: 
                    {

                    // AST REWRITE
                    // elements: typeAndId
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 110:5: -> ^( VarDecl typeAndId )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:110:8: ^( VarDecl typeAndId )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(VarDecl, "VarDecl"), root_1);

                        adaptor.addChild(root_1, stream_typeAndId.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }

            SEMICOLON29=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_varDecl452);  
            stream_SEMICOLON.add(SEMICOLON29);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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

    public static class entitySection_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "entitySection"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:113:1: entitySection : ENTITIES ( entityDecl )+ -> ( entityDecl )+ ;
    public final CalParser.entitySection_return entitySection() throws RecognitionException {
        CalParser.entitySection_return retval = new CalParser.entitySection_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ENTITIES30=null;
        CalParser.entityDecl_return entityDecl31 = null;


        Object ENTITIES30_tree=null;
        RewriteRuleTokenStream stream_ENTITIES=new RewriteRuleTokenStream(adaptor,"token ENTITIES");
        RewriteRuleSubtreeStream stream_entityDecl=new RewriteRuleSubtreeStream(adaptor,"rule entityDecl");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:113:14: ( ENTITIES ( entityDecl )+ -> ( entityDecl )+ )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:113:16: ENTITIES ( entityDecl )+
            {
            ENTITIES30=(Token)match(input,ENTITIES,FOLLOW_ENTITIES_in_entitySection460);  
            stream_ENTITIES.add(ENTITIES30);

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:113:25: ( entityDecl )+
            int cnt14=0;
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==QID) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:113:25: entityDecl
            	    {
            	    pushFollow(FOLLOW_entityDecl_in_entitySection462);
            	    entityDecl31=entityDecl();

            	    state._fsp--;

            	    stream_entityDecl.add(entityDecl31.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt14 >= 1 ) break loop14;
                        EarlyExitException eee =
                            new EarlyExitException(14, input);
                        throw eee;
                }
                cnt14++;
            } while (true);



            // AST REWRITE
            // elements: entityDecl
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 113:37: -> ( entityDecl )+
            {
                if ( !(stream_entityDecl.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_entityDecl.hasNext() ) {
                    adaptor.addChild(root_0, stream_entityDecl.nextTree());

                }
                stream_entityDecl.reset();

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
    // $ANTLR end "entitySection"

    public static class entityDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "entityDecl"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:115:1: entityDecl : QID EQ entityExpr SEMICOLON -> ^( EntityDecl ^( Var QID ) entityExpr ) ;
    public final CalParser.entityDecl_return entityDecl() throws RecognitionException {
        CalParser.entityDecl_return retval = new CalParser.entityDecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token QID32=null;
        Token EQ33=null;
        Token SEMICOLON35=null;
        CalParser.entityExpr_return entityExpr34 = null;


        Object QID32_tree=null;
        Object EQ33_tree=null;
        Object SEMICOLON35_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_EQ=new RewriteRuleTokenStream(adaptor,"token EQ");
        RewriteRuleTokenStream stream_QID=new RewriteRuleTokenStream(adaptor,"token QID");
        RewriteRuleSubtreeStream stream_entityExpr=new RewriteRuleSubtreeStream(adaptor,"rule entityExpr");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:115:11: ( QID EQ entityExpr SEMICOLON -> ^( EntityDecl ^( Var QID ) entityExpr ) )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:115:13: QID EQ entityExpr SEMICOLON
            {
            QID32=(Token)match(input,QID,FOLLOW_QID_in_entityDecl475);  
            stream_QID.add(QID32);

            EQ33=(Token)match(input,EQ,FOLLOW_EQ_in_entityDecl477);  
            stream_EQ.add(EQ33);

            pushFollow(FOLLOW_entityExpr_in_entityDecl479);
            entityExpr34=entityExpr();

            state._fsp--;

            stream_entityExpr.add(entityExpr34.getTree());
            SEMICOLON35=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_entityDecl481);  
            stream_SEMICOLON.add(SEMICOLON35);



            // AST REWRITE
            // elements: QID, entityExpr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 115:41: -> ^( EntityDecl ^( Var QID ) entityExpr )
            {
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:115:44: ^( EntityDecl ^( Var QID ) entityExpr )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EntityDecl, "EntityDecl"), root_1);

                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:115:57: ^( Var QID )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_2);

                adaptor.addChild(root_2, stream_QID.nextNode());

                adaptor.addChild(root_1, root_2);
                }
                adaptor.addChild(root_1, stream_entityExpr.nextTree());

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
    // $ANTLR end "entityDecl"

    public static class entityExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "entityExpr"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:117:1: entityExpr : QID LPAREN ( entityPars )? RPAREN -> ^( EntityExpr ^( Var QID ) ( entityPars )? ) ;
    public final CalParser.entityExpr_return entityExpr() throws RecognitionException {
        CalParser.entityExpr_return retval = new CalParser.entityExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token QID36=null;
        Token LPAREN37=null;
        Token RPAREN39=null;
        CalParser.entityPars_return entityPars38 = null;


        Object QID36_tree=null;
        Object LPAREN37_tree=null;
        Object RPAREN39_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleTokenStream stream_QID=new RewriteRuleTokenStream(adaptor,"token QID");
        RewriteRuleSubtreeStream stream_entityPars=new RewriteRuleSubtreeStream(adaptor,"rule entityPars");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:117:11: ( QID LPAREN ( entityPars )? RPAREN -> ^( EntityExpr ^( Var QID ) ( entityPars )? ) )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:117:13: QID LPAREN ( entityPars )? RPAREN
            {
            QID36=(Token)match(input,QID,FOLLOW_QID_in_entityExpr502);  
            stream_QID.add(QID36);

            LPAREN37=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_entityExpr504);  
            stream_LPAREN.add(LPAREN37);

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:117:24: ( entityPars )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==QID) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:117:24: entityPars
                    {
                    pushFollow(FOLLOW_entityPars_in_entityExpr506);
                    entityPars38=entityPars();

                    state._fsp--;

                    stream_entityPars.add(entityPars38.getTree());

                    }
                    break;

            }

            RPAREN39=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_entityExpr509);  
            stream_RPAREN.add(RPAREN39);



            // AST REWRITE
            // elements: entityPars, QID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 117:43: -> ^( EntityExpr ^( Var QID ) ( entityPars )? )
            {
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:117:46: ^( EntityExpr ^( Var QID ) ( entityPars )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EntityExpr, "EntityExpr"), root_1);

                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:117:59: ^( Var QID )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_2);

                adaptor.addChild(root_2, stream_QID.nextNode());

                adaptor.addChild(root_1, root_2);
                }
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:117:70: ( entityPars )?
                if ( stream_entityPars.hasNext() ) {
                    adaptor.addChild(root_1, stream_entityPars.nextTree());

                }
                stream_entityPars.reset();

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
    // $ANTLR end "entityExpr"

    public static class entityPars_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "entityPars"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:119:1: entityPars : entityPar ( COMMA entityPar )* -> ( entityPar )+ ;
    public final CalParser.entityPars_return entityPars() throws RecognitionException {
        CalParser.entityPars_return retval = new CalParser.entityPars_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA41=null;
        CalParser.entityPar_return entityPar40 = null;

        CalParser.entityPar_return entityPar42 = null;


        Object COMMA41_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleSubtreeStream stream_entityPar=new RewriteRuleSubtreeStream(adaptor,"rule entityPar");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:119:11: ( entityPar ( COMMA entityPar )* -> ( entityPar )+ )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:119:13: entityPar ( COMMA entityPar )*
            {
            pushFollow(FOLLOW_entityPar_in_entityPars531);
            entityPar40=entityPar();

            state._fsp--;

            stream_entityPar.add(entityPar40.getTree());
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:119:23: ( COMMA entityPar )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==COMMA) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:119:24: COMMA entityPar
            	    {
            	    COMMA41=(Token)match(input,COMMA,FOLLOW_COMMA_in_entityPars534);  
            	    stream_COMMA.add(COMMA41);

            	    pushFollow(FOLLOW_entityPar_in_entityPars536);
            	    entityPar42=entityPar();

            	    state._fsp--;

            	    stream_entityPar.add(entityPar42.getTree());

            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);



            // AST REWRITE
            // elements: entityPar
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 119:42: -> ( entityPar )+
            {
                if ( !(stream_entityPar.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_entityPar.hasNext() ) {
                    adaptor.addChild(root_0, stream_entityPar.nextTree());

                }
                stream_entityPar.reset();

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
    // $ANTLR end "entityPars"

    public static class entityPar_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "entityPar"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:121:1: entityPar : QID EQ expression -> ^( EntityPar ^( Var QID ) ^( Expression expression ) ) ;
    public final CalParser.entityPar_return entityPar() throws RecognitionException {
        CalParser.entityPar_return retval = new CalParser.entityPar_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token QID43=null;
        Token EQ44=null;
        CalParser.expression_return expression45 = null;


        Object QID43_tree=null;
        Object EQ44_tree=null;
        RewriteRuleTokenStream stream_EQ=new RewriteRuleTokenStream(adaptor,"token EQ");
        RewriteRuleTokenStream stream_QID=new RewriteRuleTokenStream(adaptor,"token QID");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:121:10: ( QID EQ expression -> ^( EntityPar ^( Var QID ) ^( Expression expression ) ) )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:121:12: QID EQ expression
            {
            QID43=(Token)match(input,QID,FOLLOW_QID_in_entityPar550);  
            stream_QID.add(QID43);

            EQ44=(Token)match(input,EQ,FOLLOW_EQ_in_entityPar552);  
            stream_EQ.add(EQ44);

            pushFollow(FOLLOW_expression_in_entityPar554);
            expression45=expression();

            state._fsp--;

            stream_expression.add(expression45.getTree());


            // AST REWRITE
            // elements: expression, QID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 121:30: -> ^( EntityPar ^( Var QID ) ^( Expression expression ) )
            {
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:121:33: ^( EntityPar ^( Var QID ) ^( Expression expression ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EntityPar, "EntityPar"), root_1);

                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:121:45: ^( Var QID )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_2);

                adaptor.addChild(root_2, stream_QID.nextNode());

                adaptor.addChild(root_1, root_2);
                }
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:121:56: ^( Expression expression )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Expression, "Expression"), root_2);

                adaptor.addChild(root_2, stream_expression.nextTree());

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
    // $ANTLR end "entityPar"

    public static class structureSection_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "structureSection"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:124:1: structureSection : STRUCTURE ( structureStmt )+ -> ( structureStmt )+ ;
    public final CalParser.structureSection_return structureSection() throws RecognitionException {
        CalParser.structureSection_return retval = new CalParser.structureSection_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token STRUCTURE46=null;
        CalParser.structureStmt_return structureStmt47 = null;


        Object STRUCTURE46_tree=null;
        RewriteRuleTokenStream stream_STRUCTURE=new RewriteRuleTokenStream(adaptor,"token STRUCTURE");
        RewriteRuleSubtreeStream stream_structureStmt=new RewriteRuleSubtreeStream(adaptor,"rule structureStmt");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:124:17: ( STRUCTURE ( structureStmt )+ -> ( structureStmt )+ )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:124:19: STRUCTURE ( structureStmt )+
            {
            STRUCTURE46=(Token)match(input,STRUCTURE,FOLLOW_STRUCTURE_in_structureSection580);  
            stream_STRUCTURE.add(STRUCTURE46);

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:124:29: ( structureStmt )+
            int cnt17=0;
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==QID) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:124:29: structureStmt
            	    {
            	    pushFollow(FOLLOW_structureStmt_in_structureSection582);
            	    structureStmt47=structureStmt();

            	    state._fsp--;

            	    stream_structureStmt.add(structureStmt47.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt17 >= 1 ) break loop17;
                        EarlyExitException eee =
                            new EarlyExitException(17, input);
                        throw eee;
                }
                cnt17++;
            } while (true);



            // AST REWRITE
            // elements: structureStmt
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 124:44: -> ( structureStmt )+
            {
                if ( !(stream_structureStmt.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_structureStmt.hasNext() ) {
                    adaptor.addChild(root_0, stream_structureStmt.nextTree());

                }
                stream_structureStmt.reset();

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
    // $ANTLR end "structureSection"

    public static class structureStmt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "structureStmt"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:126:1: structureStmt : c1= connector DOUBLE_DASH_ARROW c2= connector (at= attributeSection )? SEMICOLON -> ^( StructureStmt $c1 $c2 ( $at)? ) ;
    public final CalParser.structureStmt_return structureStmt() throws RecognitionException {
        CalParser.structureStmt_return retval = new CalParser.structureStmt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DOUBLE_DASH_ARROW48=null;
        Token SEMICOLON49=null;
        CalParser.connector_return c1 = null;

        CalParser.connector_return c2 = null;

        CalParser.attributeSection_return at = null;


        Object DOUBLE_DASH_ARROW48_tree=null;
        Object SEMICOLON49_tree=null;
        RewriteRuleTokenStream stream_DOUBLE_DASH_ARROW=new RewriteRuleTokenStream(adaptor,"token DOUBLE_DASH_ARROW");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleSubtreeStream stream_attributeSection=new RewriteRuleSubtreeStream(adaptor,"rule attributeSection");
        RewriteRuleSubtreeStream stream_connector=new RewriteRuleSubtreeStream(adaptor,"rule connector");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:126:14: (c1= connector DOUBLE_DASH_ARROW c2= connector (at= attributeSection )? SEMICOLON -> ^( StructureStmt $c1 $c2 ( $at)? ) )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:126:16: c1= connector DOUBLE_DASH_ARROW c2= connector (at= attributeSection )? SEMICOLON
            {
            pushFollow(FOLLOW_connector_in_structureStmt597);
            c1=connector();

            state._fsp--;

            stream_connector.add(c1.getTree());
            DOUBLE_DASH_ARROW48=(Token)match(input,DOUBLE_DASH_ARROW,FOLLOW_DOUBLE_DASH_ARROW_in_structureStmt599);  
            stream_DOUBLE_DASH_ARROW.add(DOUBLE_DASH_ARROW48);

            pushFollow(FOLLOW_connector_in_structureStmt603);
            c2=connector();

            state._fsp--;

            stream_connector.add(c2.getTree());
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:126:62: (at= attributeSection )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==LBRACE) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:126:62: at= attributeSection
                    {
                    pushFollow(FOLLOW_attributeSection_in_structureStmt607);
                    at=attributeSection();

                    state._fsp--;

                    stream_attributeSection.add(at.getTree());

                    }
                    break;

            }

            SEMICOLON49=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_structureStmt610);  
            stream_SEMICOLON.add(SEMICOLON49);



            // AST REWRITE
            // elements: at, c2, c1
            // token labels: 
            // rule labels: retval, c1, c2, at
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_c1=new RewriteRuleSubtreeStream(adaptor,"rule c1",c1!=null?c1.tree:null);
            RewriteRuleSubtreeStream stream_c2=new RewriteRuleSubtreeStream(adaptor,"rule c2",c2!=null?c2.tree:null);
            RewriteRuleSubtreeStream stream_at=new RewriteRuleSubtreeStream(adaptor,"rule at",at!=null?at.tree:null);

            root_0 = (Object)adaptor.nil();
            // 126:91: -> ^( StructureStmt $c1 $c2 ( $at)? )
            {
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:127:3: ^( StructureStmt $c1 $c2 ( $at)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(StructureStmt, "StructureStmt"), root_1);

                adaptor.addChild(root_1, stream_c1.nextTree());
                adaptor.addChild(root_1, stream_c2.nextTree());
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:127:27: ( $at)?
                if ( stream_at.hasNext() ) {
                    adaptor.addChild(root_1, stream_at.nextTree());

                }
                stream_at.reset();

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
    // $ANTLR end "structureStmt"

    public static class connector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "connector"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:129:1: connector : v1= QID ( DOT v2= QID -> ^( Connector ^( Var $v1) ^( Var $v2) ) | -> ^( Connector ^( Var $v1) ) ) ;
    public final CalParser.connector_return connector() throws RecognitionException {
        CalParser.connector_return retval = new CalParser.connector_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token v1=null;
        Token v2=null;
        Token DOT50=null;

        Object v1_tree=null;
        Object v2_tree=null;
        Object DOT50_tree=null;
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleTokenStream stream_QID=new RewriteRuleTokenStream(adaptor,"token QID");

        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:129:10: (v1= QID ( DOT v2= QID -> ^( Connector ^( Var $v1) ^( Var $v2) ) | -> ^( Connector ^( Var $v1) ) ) )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:129:12: v1= QID ( DOT v2= QID -> ^( Connector ^( Var $v1) ^( Var $v2) ) | -> ^( Connector ^( Var $v1) ) )
            {
            v1=(Token)match(input,QID,FOLLOW_QID_in_connector638);  
            stream_QID.add(v1);

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:129:19: ( DOT v2= QID -> ^( Connector ^( Var $v1) ^( Var $v2) ) | -> ^( Connector ^( Var $v1) ) )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==DOT) ) {
                alt19=1;
            }
            else if ( (LA19_0==SEMICOLON||LA19_0==DOUBLE_DASH_ARROW||LA19_0==LBRACE) ) {
                alt19=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:130:3: DOT v2= QID
                    {
                    DOT50=(Token)match(input,DOT,FOLLOW_DOT_in_connector644);  
                    stream_DOT.add(DOT50);

                    v2=(Token)match(input,QID,FOLLOW_QID_in_connector648);  
                    stream_QID.add(v2);



                    // AST REWRITE
                    // elements: v1, v2
                    // token labels: v1, v2
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_v1=new RewriteRuleTokenStream(adaptor,"token v1",v1);
                    RewriteRuleTokenStream stream_v2=new RewriteRuleTokenStream(adaptor,"token v2",v2);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 130:14: -> ^( Connector ^( Var $v1) ^( Var $v2) )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:130:17: ^( Connector ^( Var $v1) ^( Var $v2) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Connector, "Connector"), root_1);

                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:130:29: ^( Var $v1)
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_2);

                        adaptor.addChild(root_2, stream_v1.nextNode());

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:130:40: ^( Var $v2)
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_2);

                        adaptor.addChild(root_2, stream_v2.nextNode());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:131:5: 
                    {

                    // AST REWRITE
                    // elements: v1
                    // token labels: v1
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_v1=new RewriteRuleTokenStream(adaptor,"token v1",v1);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 131:5: -> ^( Connector ^( Var $v1) )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:131:8: ^( Connector ^( Var $v1) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Connector, "Connector"), root_1);

                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:131:20: ^( Var $v1)
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_2);

                        adaptor.addChild(root_2, stream_v1.nextNode());

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

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "connector"

    public static class attributeSection_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "attributeSection"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:133:1: attributeSection : LBRACE ( attributeDecl )* RBRACE -> ( attributeDecl )* ;
    public final CalParser.attributeSection_return attributeSection() throws RecognitionException {
        CalParser.attributeSection_return retval = new CalParser.attributeSection_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token LBRACE51=null;
        Token RBRACE53=null;
        CalParser.attributeDecl_return attributeDecl52 = null;


        Object LBRACE51_tree=null;
        Object RBRACE53_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_attributeDecl=new RewriteRuleSubtreeStream(adaptor,"rule attributeDecl");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:133:17: ( LBRACE ( attributeDecl )* RBRACE -> ( attributeDecl )* )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:133:19: LBRACE ( attributeDecl )* RBRACE
            {
            LBRACE51=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_attributeSection693);  
            stream_LBRACE.add(LBRACE51);

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:133:26: ( attributeDecl )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==QID) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:133:26: attributeDecl
            	    {
            	    pushFollow(FOLLOW_attributeDecl_in_attributeSection695);
            	    attributeDecl52=attributeDecl();

            	    state._fsp--;

            	    stream_attributeDecl.add(attributeDecl52.getTree());

            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

            RBRACE53=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_attributeSection698);  
            stream_RBRACE.add(RBRACE53);



            // AST REWRITE
            // elements: attributeDecl
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 133:48: -> ( attributeDecl )*
            {
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:133:51: ( attributeDecl )*
                while ( stream_attributeDecl.hasNext() ) {
                    adaptor.addChild(root_0, stream_attributeDecl.nextTree());

                }
                stream_attributeDecl.reset();

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
    // $ANTLR end "attributeSection"

    public static class attributeDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "attributeDecl"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:135:1: attributeDecl : id= QID ( EQ expression SEMICOLON -> ^( Attribute ^( Var $id) ^( Expression expression ) ) | COLON type SEMICOLON -> ^( Attribute ^( Var $id) ^( Type type ) ) ) ;
    public final CalParser.attributeDecl_return attributeDecl() throws RecognitionException {
        CalParser.attributeDecl_return retval = new CalParser.attributeDecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token id=null;
        Token EQ54=null;
        Token SEMICOLON56=null;
        Token COLON57=null;
        Token SEMICOLON59=null;
        CalParser.expression_return expression55 = null;

        CalParser.type_return type58 = null;


        Object id_tree=null;
        Object EQ54_tree=null;
        Object SEMICOLON56_tree=null;
        Object COLON57_tree=null;
        Object SEMICOLON59_tree=null;
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_EQ=new RewriteRuleTokenStream(adaptor,"token EQ");
        RewriteRuleTokenStream stream_QID=new RewriteRuleTokenStream(adaptor,"token QID");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:135:14: (id= QID ( EQ expression SEMICOLON -> ^( Attribute ^( Var $id) ^( Expression expression ) ) | COLON type SEMICOLON -> ^( Attribute ^( Var $id) ^( Type type ) ) ) )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:135:16: id= QID ( EQ expression SEMICOLON -> ^( Attribute ^( Var $id) ^( Expression expression ) ) | COLON type SEMICOLON -> ^( Attribute ^( Var $id) ^( Type type ) ) )
            {
            id=(Token)match(input,QID,FOLLOW_QID_in_attributeDecl712);  
            stream_QID.add(id);

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:135:23: ( EQ expression SEMICOLON -> ^( Attribute ^( Var $id) ^( Expression expression ) ) | COLON type SEMICOLON -> ^( Attribute ^( Var $id) ^( Type type ) ) )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==EQ) ) {
                alt21=1;
            }
            else if ( (LA21_0==COLON) ) {
                alt21=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:135:24: EQ expression SEMICOLON
                    {
                    EQ54=(Token)match(input,EQ,FOLLOW_EQ_in_attributeDecl715);  
                    stream_EQ.add(EQ54);

                    pushFollow(FOLLOW_expression_in_attributeDecl717);
                    expression55=expression();

                    state._fsp--;

                    stream_expression.add(expression55.getTree());
                    SEMICOLON56=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_attributeDecl719);  
                    stream_SEMICOLON.add(SEMICOLON56);



                    // AST REWRITE
                    // elements: expression, id
                    // token labels: id
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_id=new RewriteRuleTokenStream(adaptor,"token id",id);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 135:48: -> ^( Attribute ^( Var $id) ^( Expression expression ) )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:135:51: ^( Attribute ^( Var $id) ^( Expression expression ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Attribute, "Attribute"), root_1);

                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:135:63: ^( Var $id)
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_2);

                        adaptor.addChild(root_2, stream_id.nextNode());

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:135:74: ^( Expression expression )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Expression, "Expression"), root_2);

                        adaptor.addChild(root_2, stream_expression.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:136:3: COLON type SEMICOLON
                    {
                    COLON57=(Token)match(input,COLON,FOLLOW_COLON_in_attributeDecl742);  
                    stream_COLON.add(COLON57);

                    pushFollow(FOLLOW_type_in_attributeDecl744);
                    type58=type();

                    state._fsp--;

                    stream_type.add(type58.getTree());
                    SEMICOLON59=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_attributeDecl746);  
                    stream_SEMICOLON.add(SEMICOLON59);



                    // AST REWRITE
                    // elements: type, id
                    // token labels: id
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_id=new RewriteRuleTokenStream(adaptor,"token id",id);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 136:24: -> ^( Attribute ^( Var $id) ^( Type type ) )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:136:27: ^( Attribute ^( Var $id) ^( Type type ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Attribute, "Attribute"), root_1);

                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:136:39: ^( Var $id)
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_2);

                        adaptor.addChild(root_2, stream_id.nextNode());

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:136:50: ^( Type type )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Type, "Type"), root_2);

                        adaptor.addChild(root_2, stream_type.nextTree());

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

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "attributeDecl"

    public static class actor_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actor"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:141:1: actor : ( packageRule )? ( oneImport )* ( NATIVE )? ACTOR QID LPAREN ( parameters )? RPAREN portSignature COLON ( . )* EOF -> ^( Actor ^( Name QID ) ( parameters )? portSignature ) ;
    public final CalParser.actor_return actor() throws RecognitionException {
        CalParser.actor_return retval = new CalParser.actor_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NATIVE62=null;
        Token ACTOR63=null;
        Token QID64=null;
        Token LPAREN65=null;
        Token RPAREN67=null;
        Token COLON69=null;
        Token wildcard70=null;
        Token EOF71=null;
        CalParser.packageRule_return packageRule60 = null;

        CalParser.oneImport_return oneImport61 = null;

        CalParser.parameters_return parameters66 = null;

        CalParser.portSignature_return portSignature68 = null;


        Object NATIVE62_tree=null;
        Object ACTOR63_tree=null;
        Object QID64_tree=null;
        Object LPAREN65_tree=null;
        Object RPAREN67_tree=null;
        Object COLON69_tree=null;
        Object wildcard70_tree=null;
        Object EOF71_tree=null;
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleTokenStream stream_NATIVE=new RewriteRuleTokenStream(adaptor,"token NATIVE");
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleTokenStream stream_ACTOR=new RewriteRuleTokenStream(adaptor,"token ACTOR");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleTokenStream stream_QID=new RewriteRuleTokenStream(adaptor,"token QID");
        RewriteRuleSubtreeStream stream_portSignature=new RewriteRuleSubtreeStream(adaptor,"rule portSignature");
        RewriteRuleSubtreeStream stream_parameters=new RewriteRuleSubtreeStream(adaptor,"rule parameters");
        RewriteRuleSubtreeStream stream_packageRule=new RewriteRuleSubtreeStream(adaptor,"rule packageRule");
        RewriteRuleSubtreeStream stream_oneImport=new RewriteRuleSubtreeStream(adaptor,"rule oneImport");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:141:6: ( ( packageRule )? ( oneImport )* ( NATIVE )? ACTOR QID LPAREN ( parameters )? RPAREN portSignature COLON ( . )* EOF -> ^( Actor ^( Name QID ) ( parameters )? portSignature ) )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:141:8: ( packageRule )? ( oneImport )* ( NATIVE )? ACTOR QID LPAREN ( parameters )? RPAREN portSignature COLON ( . )* EOF
            {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:141:8: ( packageRule )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==PACKAGE) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:141:8: packageRule
                    {
                    pushFollow(FOLLOW_packageRule_in_actor777);
                    packageRule60=packageRule();

                    state._fsp--;

                    stream_packageRule.add(packageRule60.getTree());

                    }
                    break;

            }

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:141:21: ( oneImport )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==IMPORT) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:141:21: oneImport
            	    {
            	    pushFollow(FOLLOW_oneImport_in_actor780);
            	    oneImport61=oneImport();

            	    state._fsp--;

            	    stream_oneImport.add(oneImport61.getTree());

            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:141:32: ( NATIVE )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==NATIVE) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:141:32: NATIVE
                    {
                    NATIVE62=(Token)match(input,NATIVE,FOLLOW_NATIVE_in_actor783);  
                    stream_NATIVE.add(NATIVE62);


                    }
                    break;

            }

            ACTOR63=(Token)match(input,ACTOR,FOLLOW_ACTOR_in_actor786);  
            stream_ACTOR.add(ACTOR63);

            QID64=(Token)match(input,QID,FOLLOW_QID_in_actor788);  
            stream_QID.add(QID64);

            LPAREN65=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_actor792);  
            stream_LPAREN.add(LPAREN65);

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:142:10: ( parameters )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==QID) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:142:10: parameters
                    {
                    pushFollow(FOLLOW_parameters_in_actor794);
                    parameters66=parameters();

                    state._fsp--;

                    stream_parameters.add(parameters66.getTree());

                    }
                    break;

            }

            RPAREN67=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_actor797);  
            stream_RPAREN.add(RPAREN67);

            pushFollow(FOLLOW_portSignature_in_actor801);
            portSignature68=portSignature();

            state._fsp--;

            stream_portSignature.add(portSignature68.getTree());
            COLON69=(Token)match(input,COLON,FOLLOW_COLON_in_actor803);  
            stream_COLON.add(COLON69);

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:143:23: ( . )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( ((LA26_0>=Attribute && LA26_0<=SHARP)) ) {
                    alt26=1;
                }
                else if ( (LA26_0==EOF) ) {
                    alt26=2;
                }


                switch (alt26) {
            	case 1 :
            	    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:143:23: .
            	    {
            	    wildcard70=(Token)input.LT(1);
            	    matchAny(input); 
            	    wildcard70_tree = (Object)adaptor.create(wildcard70);
            	    adaptor.addChild(root_0, wildcard70_tree);


            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);

            EOF71=(Token)match(input,EOF,FOLLOW_EOF_in_actor808);  
            stream_EOF.add(EOF71);



            // AST REWRITE
            // elements: parameters, portSignature, QID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 143:30: -> ^( Actor ^( Name QID ) ( parameters )? portSignature )
            {
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:144:5: ^( Actor ^( Name QID ) ( parameters )? portSignature )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Actor, "Actor"), root_1);

                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:144:13: ^( Name QID )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Name, "Name"), root_2);

                adaptor.addChild(root_2, stream_QID.nextNode());

                adaptor.addChild(root_1, root_2);
                }
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:144:25: ( parameters )?
                if ( stream_parameters.hasNext() ) {
                    adaptor.addChild(root_1, stream_parameters.nextTree());

                }
                stream_parameters.reset();
                adaptor.addChild(root_1, stream_portSignature.nextTree());

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

    public static class packageRule_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "packageRule"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:146:1: packageRule : PACKAGE QID SEMICOLON ;
    public final CalParser.packageRule_return packageRule() throws RecognitionException {
        CalParser.packageRule_return retval = new CalParser.packageRule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PACKAGE72=null;
        Token QID73=null;
        Token SEMICOLON74=null;

        Object PACKAGE72_tree=null;
        Object QID73_tree=null;
        Object SEMICOLON74_tree=null;

        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:146:13: ( PACKAGE QID SEMICOLON )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:146:15: PACKAGE QID SEMICOLON
            {
            root_0 = (Object)adaptor.nil();

            PACKAGE72=(Token)match(input,PACKAGE,FOLLOW_PACKAGE_in_packageRule837); 
            PACKAGE72_tree = (Object)adaptor.create(PACKAGE72);
            adaptor.addChild(root_0, PACKAGE72_tree);

            QID73=(Token)match(input,QID,FOLLOW_QID_in_packageRule839); 
            QID73_tree = (Object)adaptor.create(QID73);
            adaptor.addChild(root_0, QID73_tree);

            SEMICOLON74=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_packageRule841); 
            SEMICOLON74_tree = (Object)adaptor.create(SEMICOLON74);
            adaptor.addChild(root_0, SEMICOLON74_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "packageRule"

    public static class oneImport_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "oneImport"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:151:1: oneImport : IMPORT ( QID | QID_WILDCARD ) SEMICOLON ;
    public final CalParser.oneImport_return oneImport() throws RecognitionException {
        CalParser.oneImport_return retval = new CalParser.oneImport_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IMPORT75=null;
        Token set76=null;
        Token SEMICOLON77=null;

        Object IMPORT75_tree=null;
        Object set76_tree=null;
        Object SEMICOLON77_tree=null;

        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:151:10: ( IMPORT ( QID | QID_WILDCARD ) SEMICOLON )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:151:12: IMPORT ( QID | QID_WILDCARD ) SEMICOLON
            {
            root_0 = (Object)adaptor.nil();

            IMPORT75=(Token)match(input,IMPORT,FOLLOW_IMPORT_in_oneImport851); 
            IMPORT75_tree = (Object)adaptor.create(IMPORT75);
            adaptor.addChild(root_0, IMPORT75_tree);

            set76=(Token)input.LT(1);
            if ( input.LA(1)==QID||input.LA(1)==QID_WILDCARD ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set76));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            SEMICOLON77=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_oneImport861); 
            SEMICOLON77_tree = (Object)adaptor.create(SEMICOLON77);
            adaptor.addChild(root_0, SEMICOLON77_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "oneImport"

    public static class parameter_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "parameter"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:156:1: parameter : typeAndId ( EQ expression -> ^( Parameter typeAndId ^( Expression expression ) ) | -> ^( Parameter typeAndId ) ) ;
    public final CalParser.parameter_return parameter() throws RecognitionException {
        CalParser.parameter_return retval = new CalParser.parameter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EQ79=null;
        CalParser.typeAndId_return typeAndId78 = null;

        CalParser.expression_return expression80 = null;


        Object EQ79_tree=null;
        RewriteRuleTokenStream stream_EQ=new RewriteRuleTokenStream(adaptor,"token EQ");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeAndId=new RewriteRuleSubtreeStream(adaptor,"rule typeAndId");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:156:10: ( typeAndId ( EQ expression -> ^( Parameter typeAndId ^( Expression expression ) ) | -> ^( Parameter typeAndId ) ) )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:156:12: typeAndId ( EQ expression -> ^( Parameter typeAndId ^( Expression expression ) ) | -> ^( Parameter typeAndId ) )
            {
            pushFollow(FOLLOW_typeAndId_in_parameter871);
            typeAndId78=typeAndId();

            state._fsp--;

            stream_typeAndId.add(typeAndId78.getTree());
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:157:3: ( EQ expression -> ^( Parameter typeAndId ^( Expression expression ) ) | -> ^( Parameter typeAndId ) )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==EQ) ) {
                alt27=1;
            }
            else if ( (LA27_0==RPAREN||LA27_0==COMMA) ) {
                alt27=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:157:4: EQ expression
                    {
                    EQ79=(Token)match(input,EQ,FOLLOW_EQ_in_parameter876);  
                    stream_EQ.add(EQ79);

                    pushFollow(FOLLOW_expression_in_parameter878);
                    expression80=expression();

                    state._fsp--;

                    stream_expression.add(expression80.getTree());


                    // AST REWRITE
                    // elements: expression, typeAndId
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 157:18: -> ^( Parameter typeAndId ^( Expression expression ) )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:157:21: ^( Parameter typeAndId ^( Expression expression ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Parameter, "Parameter"), root_1);

                        adaptor.addChild(root_1, stream_typeAndId.nextTree());
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:157:43: ^( Expression expression )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Expression, "Expression"), root_2);

                        adaptor.addChild(root_2, stream_expression.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:158:5: 
                    {

                    // AST REWRITE
                    // elements: typeAndId
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 158:5: -> ^( Parameter typeAndId )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:158:8: ^( Parameter typeAndId )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Parameter, "Parameter"), root_1);

                        adaptor.addChild(root_1, stream_typeAndId.nextTree());

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
    // $ANTLR end "parameter"

    public static class parameters_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "parameters"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:160:1: parameters : parameter ( COMMA parameter )* -> ( parameter )+ ;
    public final CalParser.parameters_return parameters() throws RecognitionException {
        CalParser.parameters_return retval = new CalParser.parameters_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA82=null;
        CalParser.parameter_return parameter81 = null;

        CalParser.parameter_return parameter83 = null;


        Object COMMA82_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleSubtreeStream stream_parameter=new RewriteRuleSubtreeStream(adaptor,"rule parameter");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:160:11: ( parameter ( COMMA parameter )* -> ( parameter )+ )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:160:13: parameter ( COMMA parameter )*
            {
            pushFollow(FOLLOW_parameter_in_parameters912);
            parameter81=parameter();

            state._fsp--;

            stream_parameter.add(parameter81.getTree());
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:160:23: ( COMMA parameter )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==COMMA) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:160:24: COMMA parameter
            	    {
            	    COMMA82=(Token)match(input,COMMA,FOLLOW_COMMA_in_parameters915);  
            	    stream_COMMA.add(COMMA82);

            	    pushFollow(FOLLOW_parameter_in_parameters917);
            	    parameter83=parameter();

            	    state._fsp--;

            	    stream_parameter.add(parameter83.getTree());

            	    }
            	    break;

            	default :
            	    break loop28;
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
            // 160:42: -> ( parameter )+
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

    public static class portDecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "portDecl"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:165:1: portDecl : ( MULTI )? typeAndId -> ^( PortDecl typeAndId ) ;
    public final CalParser.portDecl_return portDecl() throws RecognitionException {
        CalParser.portDecl_return retval = new CalParser.portDecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MULTI84=null;
        CalParser.typeAndId_return typeAndId85 = null;


        Object MULTI84_tree=null;
        RewriteRuleTokenStream stream_MULTI=new RewriteRuleTokenStream(adaptor,"token MULTI");
        RewriteRuleSubtreeStream stream_typeAndId=new RewriteRuleSubtreeStream(adaptor,"rule typeAndId");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:165:9: ( ( MULTI )? typeAndId -> ^( PortDecl typeAndId ) )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:165:11: ( MULTI )? typeAndId
            {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:165:11: ( MULTI )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==MULTI) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:165:11: MULTI
                    {
                    MULTI84=(Token)match(input,MULTI,FOLLOW_MULTI_in_portDecl934);  
                    stream_MULTI.add(MULTI84);


                    }
                    break;

            }

            pushFollow(FOLLOW_typeAndId_in_portDecl937);
            typeAndId85=typeAndId();

            state._fsp--;

            stream_typeAndId.add(typeAndId85.getTree());


            // AST REWRITE
            // elements: typeAndId
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 165:28: -> ^( PortDecl typeAndId )
            {
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:165:31: ^( PortDecl typeAndId )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PortDecl, "PortDecl"), root_1);

                adaptor.addChild(root_1, stream_typeAndId.nextTree());

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
    // $ANTLR end "portDecl"

    public static class portDecls_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "portDecls"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:167:1: portDecls : portDecl ( COMMA portDecl )* -> ( portDecl )+ ;
    public final CalParser.portDecls_return portDecls() throws RecognitionException {
        CalParser.portDecls_return retval = new CalParser.portDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA87=null;
        CalParser.portDecl_return portDecl86 = null;

        CalParser.portDecl_return portDecl88 = null;


        Object COMMA87_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleSubtreeStream stream_portDecl=new RewriteRuleSubtreeStream(adaptor,"rule portDecl");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:167:10: ( portDecl ( COMMA portDecl )* -> ( portDecl )+ )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:167:12: portDecl ( COMMA portDecl )*
            {
            pushFollow(FOLLOW_portDecl_in_portDecls952);
            portDecl86=portDecl();

            state._fsp--;

            stream_portDecl.add(portDecl86.getTree());
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:167:21: ( COMMA portDecl )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==COMMA) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:167:22: COMMA portDecl
            	    {
            	    COMMA87=(Token)match(input,COMMA,FOLLOW_COMMA_in_portDecls955);  
            	    stream_COMMA.add(COMMA87);

            	    pushFollow(FOLLOW_portDecl_in_portDecls957);
            	    portDecl88=portDecl();

            	    state._fsp--;

            	    stream_portDecl.add(portDecl88.getTree());

            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);



            // AST REWRITE
            // elements: portDecl
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 167:39: -> ( portDecl )+
            {
                if ( !(stream_portDecl.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_portDecl.hasNext() ) {
                    adaptor.addChild(root_0, stream_portDecl.nextTree());

                }
                stream_portDecl.reset();

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
    // $ANTLR end "portDecls"

    public static class mainParameter_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "mainParameter"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:172:1: mainParameter : typeAndId EOF -> ^( Parameter typeAndId ) ;
    public final CalParser.mainParameter_return mainParameter() throws RecognitionException {
        CalParser.mainParameter_return retval = new CalParser.mainParameter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EOF90=null;
        CalParser.typeAndId_return typeAndId89 = null;


        Object EOF90_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_typeAndId=new RewriteRuleSubtreeStream(adaptor,"rule typeAndId");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:172:14: ( typeAndId EOF -> ^( Parameter typeAndId ) )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:172:16: typeAndId EOF
            {
            pushFollow(FOLLOW_typeAndId_in_mainParameter975);
            typeAndId89=typeAndId();

            state._fsp--;

            stream_typeAndId.add(typeAndId89.getTree());
            EOF90=(Token)match(input,EOF,FOLLOW_EOF_in_mainParameter977);  
            stream_EOF.add(EOF90);



            // AST REWRITE
            // elements: typeAndId
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 172:30: -> ^( Parameter typeAndId )
            {
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:172:33: ^( Parameter typeAndId )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Parameter, "Parameter"), root_1);

                adaptor.addChild(root_1, stream_typeAndId.nextTree());

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
    // $ANTLR end "mainParameter"

    public static class typeAndId_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typeAndId"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:174:1: typeAndId : typeName= QID ( ( typeRest )? varName= QID -> ^( Type ^( Var $typeName) ( typeRest )? ) ^( Var $varName) | -> ^( Var $typeName) ) ;
    public final CalParser.typeAndId_return typeAndId() throws RecognitionException {
        CalParser.typeAndId_return retval = new CalParser.typeAndId_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token typeName=null;
        Token varName=null;
        CalParser.typeRest_return typeRest91 = null;


        Object typeName_tree=null;
        Object varName_tree=null;
        RewriteRuleTokenStream stream_QID=new RewriteRuleTokenStream(adaptor,"token QID");
        RewriteRuleSubtreeStream stream_typeRest=new RewriteRuleSubtreeStream(adaptor,"rule typeRest");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:174:10: (typeName= QID ( ( typeRest )? varName= QID -> ^( Type ^( Var $typeName) ( typeRest )? ) ^( Var $varName) | -> ^( Var $typeName) ) )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:174:12: typeName= QID ( ( typeRest )? varName= QID -> ^( Type ^( Var $typeName) ( typeRest )? ) ^( Var $varName) | -> ^( Var $typeName) )
            {
            typeName=(Token)match(input,QID,FOLLOW_QID_in_typeAndId994);  
            stream_QID.add(typeName);

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:175:3: ( ( typeRest )? varName= QID -> ^( Type ^( Var $typeName) ( typeRest )? ) ^( Var $varName) | -> ^( Var $typeName) )
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( ((LA32_0>=QID && LA32_0<=LBRACKET)||LA32_0==LPAREN) ) {
                alt32=1;
            }
            else if ( (LA32_0==EOF||(LA32_0>=RPAREN && LA32_0<=COLON)||LA32_0==DOUBLE_EQUAL_ARROW||(LA32_0>=EQ && LA32_0<=SEMICOLON)||LA32_0==COMMA) ) {
                alt32=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;
            }
            switch (alt32) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:175:4: ( typeRest )? varName= QID
                    {
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:175:4: ( typeRest )?
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==LBRACKET||LA31_0==LPAREN) ) {
                        alt31=1;
                    }
                    switch (alt31) {
                        case 1 :
                            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:175:4: typeRest
                            {
                            pushFollow(FOLLOW_typeRest_in_typeAndId999);
                            typeRest91=typeRest();

                            state._fsp--;

                            stream_typeRest.add(typeRest91.getTree());

                            }
                            break;

                    }

                    varName=(Token)match(input,QID,FOLLOW_QID_in_typeAndId1004);  
                    stream_QID.add(varName);



                    // AST REWRITE
                    // elements: typeName, varName, typeRest
                    // token labels: typeName, varName
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_typeName=new RewriteRuleTokenStream(adaptor,"token typeName",typeName);
                    RewriteRuleTokenStream stream_varName=new RewriteRuleTokenStream(adaptor,"token varName",varName);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 175:26: -> ^( Type ^( Var $typeName) ( typeRest )? ) ^( Var $varName)
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:175:29: ^( Type ^( Var $typeName) ( typeRest )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Type, "Type"), root_1);

                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:175:36: ^( Var $typeName)
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_2);

                        adaptor.addChild(root_2, stream_typeName.nextNode());

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:175:53: ( typeRest )?
                        if ( stream_typeRest.hasNext() ) {
                            adaptor.addChild(root_1, stream_typeRest.nextTree());

                        }
                        stream_typeRest.reset();

                        adaptor.addChild(root_0, root_1);
                        }
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:175:64: ^( Var $varName)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_1);

                        adaptor.addChild(root_1, stream_varName.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:176:5: 
                    {

                    // AST REWRITE
                    // elements: typeName
                    // token labels: typeName
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_typeName=new RewriteRuleTokenStream(adaptor,"token typeName",typeName);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 176:5: -> ^( Var $typeName)
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:176:8: ^( Var $typeName)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_1);

                        adaptor.addChild(root_1, stream_typeName.nextNode());

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
    // $ANTLR end "typeAndId"

    public static class type_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "type"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:178:1: type : QID ( typeRest )? -> ^( Type ^( Var QID ) ( typeRest )? ) ;
    public final CalParser.type_return type() throws RecognitionException {
        CalParser.type_return retval = new CalParser.type_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token QID92=null;
        CalParser.typeRest_return typeRest93 = null;


        Object QID92_tree=null;
        RewriteRuleTokenStream stream_QID=new RewriteRuleTokenStream(adaptor,"token QID");
        RewriteRuleSubtreeStream stream_typeRest=new RewriteRuleSubtreeStream(adaptor,"rule typeRest");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:178:5: ( QID ( typeRest )? -> ^( Type ^( Var QID ) ( typeRest )? ) )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:178:7: QID ( typeRest )?
            {
            QID92=(Token)match(input,QID,FOLLOW_QID_in_type1048);  
            stream_QID.add(QID92);

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:178:11: ( typeRest )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==LBRACKET||LA33_0==LPAREN) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:178:11: typeRest
                    {
                    pushFollow(FOLLOW_typeRest_in_type1050);
                    typeRest93=typeRest();

                    state._fsp--;

                    stream_typeRest.add(typeRest93.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: typeRest, QID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 178:21: -> ^( Type ^( Var QID ) ( typeRest )? )
            {
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:178:24: ^( Type ^( Var QID ) ( typeRest )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Type, "Type"), root_1);

                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:178:31: ^( Var QID )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_2);

                adaptor.addChild(root_2, stream_QID.nextNode());

                adaptor.addChild(root_1, root_2);
                }
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:178:42: ( typeRest )?
                if ( stream_typeRest.hasNext() ) {
                    adaptor.addChild(root_1, stream_typeRest.nextTree());

                }
                stream_typeRest.reset();

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
    // $ANTLR end "type"

    public static class typeRest_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typeRest"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:180:1: typeRest : ( LBRACKET ( typePars )? RBRACKET -> ( typePars )? | LPAREN ( typeAttrs )? RPAREN -> ( typeAttrs )? );
    public final CalParser.typeRest_return typeRest() throws RecognitionException {
        CalParser.typeRest_return retval = new CalParser.typeRest_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token LBRACKET94=null;
        Token RBRACKET96=null;
        Token LPAREN97=null;
        Token RPAREN99=null;
        CalParser.typePars_return typePars95 = null;

        CalParser.typeAttrs_return typeAttrs98 = null;


        Object LBRACKET94_tree=null;
        Object RBRACKET96_tree=null;
        Object LPAREN97_tree=null;
        Object RPAREN99_tree=null;
        RewriteRuleTokenStream stream_LBRACKET=new RewriteRuleTokenStream(adaptor,"token LBRACKET");
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_RBRACKET=new RewriteRuleTokenStream(adaptor,"token RBRACKET");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_typePars=new RewriteRuleSubtreeStream(adaptor,"rule typePars");
        RewriteRuleSubtreeStream stream_typeAttrs=new RewriteRuleSubtreeStream(adaptor,"rule typeAttrs");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:180:9: ( LBRACKET ( typePars )? RBRACKET -> ( typePars )? | LPAREN ( typeAttrs )? RPAREN -> ( typeAttrs )? )
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==LBRACKET) ) {
                alt36=1;
            }
            else if ( (LA36_0==LPAREN) ) {
                alt36=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }
            switch (alt36) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:180:11: LBRACKET ( typePars )? RBRACKET
                    {
                    LBRACKET94=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_typeRest1073);  
                    stream_LBRACKET.add(LBRACKET94);

                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:180:20: ( typePars )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==QID) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:180:20: typePars
                            {
                            pushFollow(FOLLOW_typePars_in_typeRest1075);
                            typePars95=typePars();

                            state._fsp--;

                            stream_typePars.add(typePars95.getTree());

                            }
                            break;

                    }

                    RBRACKET96=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_typeRest1078);  
                    stream_RBRACKET.add(RBRACKET96);



                    // AST REWRITE
                    // elements: typePars
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 180:39: -> ( typePars )?
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:180:42: ( typePars )?
                        if ( stream_typePars.hasNext() ) {
                            adaptor.addChild(root_0, stream_typePars.nextTree());

                        }
                        stream_typePars.reset();

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:181:5: LPAREN ( typeAttrs )? RPAREN
                    {
                    LPAREN97=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_typeRest1089);  
                    stream_LPAREN.add(LPAREN97);

                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:181:12: ( typeAttrs )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==QID) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:181:12: typeAttrs
                            {
                            pushFollow(FOLLOW_typeAttrs_in_typeRest1091);
                            typeAttrs98=typeAttrs();

                            state._fsp--;

                            stream_typeAttrs.add(typeAttrs98.getTree());

                            }
                            break;

                    }

                    RPAREN99=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_typeRest1094);  
                    stream_RPAREN.add(RPAREN99);



                    // AST REWRITE
                    // elements: typeAttrs
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 181:30: -> ( typeAttrs )?
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:181:33: ( typeAttrs )?
                        if ( stream_typeAttrs.hasNext() ) {
                            adaptor.addChild(root_0, stream_typeAttrs.nextTree());

                        }
                        stream_typeAttrs.reset();

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
    // $ANTLR end "typeRest"

    public static class typeAttrs_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typeAttrs"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:183:1: typeAttrs : typeAttr ( COMMA typeAttr )* -> ( typeAttr )+ ;
    public final CalParser.typeAttrs_return typeAttrs() throws RecognitionException {
        CalParser.typeAttrs_return retval = new CalParser.typeAttrs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA101=null;
        CalParser.typeAttr_return typeAttr100 = null;

        CalParser.typeAttr_return typeAttr102 = null;


        Object COMMA101_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleSubtreeStream stream_typeAttr=new RewriteRuleSubtreeStream(adaptor,"rule typeAttr");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:183:10: ( typeAttr ( COMMA typeAttr )* -> ( typeAttr )+ )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:183:12: typeAttr ( COMMA typeAttr )*
            {
            pushFollow(FOLLOW_typeAttr_in_typeAttrs1106);
            typeAttr100=typeAttr();

            state._fsp--;

            stream_typeAttr.add(typeAttr100.getTree());
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:183:21: ( COMMA typeAttr )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==COMMA) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:183:22: COMMA typeAttr
            	    {
            	    COMMA101=(Token)match(input,COMMA,FOLLOW_COMMA_in_typeAttrs1109);  
            	    stream_COMMA.add(COMMA101);

            	    pushFollow(FOLLOW_typeAttr_in_typeAttrs1111);
            	    typeAttr102=typeAttr();

            	    state._fsp--;

            	    stream_typeAttr.add(typeAttr102.getTree());

            	    }
            	    break;

            	default :
            	    break loop37;
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
            // 183:39: -> ( typeAttr )+
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

    public static class typeAttr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typeAttr"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:185:1: typeAttr : QID typeAttrRest -> typeAttrRest ;
    public final CalParser.typeAttr_return typeAttr() throws RecognitionException {
        CalParser.typeAttr_return retval = new CalParser.typeAttr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token QID103=null;
        CalParser.typeAttrRest_return typeAttrRest104 = null;


        Object QID103_tree=null;
        RewriteRuleTokenStream stream_QID=new RewriteRuleTokenStream(adaptor,"token QID");
        RewriteRuleSubtreeStream stream_typeAttrRest=new RewriteRuleSubtreeStream(adaptor,"rule typeAttrRest");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:185:9: ( QID typeAttrRest -> typeAttrRest )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:185:11: QID typeAttrRest
            {
            QID103=(Token)match(input,QID,FOLLOW_QID_in_typeAttr1125);  
            stream_QID.add(QID103);

            pushFollow(FOLLOW_typeAttrRest_in_typeAttr1127);
            typeAttrRest104=typeAttrRest();

            state._fsp--;

            stream_typeAttrRest.add(typeAttrRest104.getTree());


            // AST REWRITE
            // elements: typeAttrRest
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 185:28: -> typeAttrRest
            {
                adaptor.addChild(root_0, stream_typeAttrRest.nextTree());

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
    // $ANTLR end "typeAttr"

    public static class typeAttrRest_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typeAttrRest"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:187:1: typeAttrRest : ( COLON type -> ^( TypeAttr type ) | EQ expression -> ^( ExprAttr ^( Expression expression ) ) );
    public final CalParser.typeAttrRest_return typeAttrRest() throws RecognitionException {
        CalParser.typeAttrRest_return retval = new CalParser.typeAttrRest_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COLON105=null;
        Token EQ107=null;
        CalParser.type_return type106 = null;

        CalParser.expression_return expression108 = null;


        Object COLON105_tree=null;
        Object EQ107_tree=null;
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleTokenStream stream_EQ=new RewriteRuleTokenStream(adaptor,"token EQ");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:187:13: ( COLON type -> ^( TypeAttr type ) | EQ expression -> ^( ExprAttr ^( Expression expression ) ) )
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==COLON) ) {
                alt38=1;
            }
            else if ( (LA38_0==EQ) ) {
                alt38=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 38, 0, input);

                throw nvae;
            }
            switch (alt38) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:187:15: COLON type
                    {
                    COLON105=(Token)match(input,COLON,FOLLOW_COLON_in_typeAttrRest1138);  
                    stream_COLON.add(COLON105);

                    pushFollow(FOLLOW_type_in_typeAttrRest1140);
                    type106=type();

                    state._fsp--;

                    stream_type.add(type106.getTree());


                    // AST REWRITE
                    // elements: type
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 187:26: -> ^( TypeAttr type )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:187:29: ^( TypeAttr type )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TypeAttr, "TypeAttr"), root_1);

                        adaptor.addChild(root_1, stream_type.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:188:3: EQ expression
                    {
                    EQ107=(Token)match(input,EQ,FOLLOW_EQ_in_typeAttrRest1152);  
                    stream_EQ.add(EQ107);

                    pushFollow(FOLLOW_expression_in_typeAttrRest1154);
                    expression108=expression();

                    state._fsp--;

                    stream_expression.add(expression108.getTree());


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
                    // 188:17: -> ^( ExprAttr ^( Expression expression ) )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:188:20: ^( ExprAttr ^( Expression expression ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ExprAttr, "ExprAttr"), root_1);

                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:188:31: ^( Expression expression )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Expression, "Expression"), root_2);

                        adaptor.addChild(root_2, stream_expression.nextTree());

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
    // $ANTLR end "typeAttrRest"

    public static class typePars_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typePars"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:190:1: typePars : typePar ( COMMA typePar )* -> ( typePar )+ ;
    public final CalParser.typePars_return typePars() throws RecognitionException {
        CalParser.typePars_return retval = new CalParser.typePars_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA110=null;
        CalParser.typePar_return typePar109 = null;

        CalParser.typePar_return typePar111 = null;


        Object COMMA110_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleSubtreeStream stream_typePar=new RewriteRuleSubtreeStream(adaptor,"rule typePar");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:190:9: ( typePar ( COMMA typePar )* -> ( typePar )+ )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:190:11: typePar ( COMMA typePar )*
            {
            pushFollow(FOLLOW_typePar_in_typePars1173);
            typePar109=typePar();

            state._fsp--;

            stream_typePar.add(typePar109.getTree());
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:190:19: ( COMMA typePar )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==COMMA) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:190:20: COMMA typePar
            	    {
            	    COMMA110=(Token)match(input,COMMA,FOLLOW_COMMA_in_typePars1176);  
            	    stream_COMMA.add(COMMA110);

            	    pushFollow(FOLLOW_typePar_in_typePars1178);
            	    typePar111=typePar();

            	    state._fsp--;

            	    stream_typePar.add(typePar111.getTree());

            	    }
            	    break;

            	default :
            	    break loop39;
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
            // 190:36: -> ( typePar )+
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

    public static class typePar_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typePar"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:192:1: typePar : QID ( LT type )? -> ^( TypePar QID ( type )? ) ;
    public final CalParser.typePar_return typePar() throws RecognitionException {
        CalParser.typePar_return retval = new CalParser.typePar_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token QID112=null;
        Token LT113=null;
        CalParser.type_return type114 = null;


        Object QID112_tree=null;
        Object LT113_tree=null;
        RewriteRuleTokenStream stream_LT=new RewriteRuleTokenStream(adaptor,"token LT");
        RewriteRuleTokenStream stream_QID=new RewriteRuleTokenStream(adaptor,"token QID");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:192:8: ( QID ( LT type )? -> ^( TypePar QID ( type )? ) )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:192:10: QID ( LT type )?
            {
            QID112=(Token)match(input,QID,FOLLOW_QID_in_typePar1192);  
            stream_QID.add(QID112);

            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:192:14: ( LT type )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==LT) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:192:15: LT type
                    {
                    LT113=(Token)match(input,LT,FOLLOW_LT_in_typePar1195);  
                    stream_LT.add(LT113);

                    pushFollow(FOLLOW_type_in_typePar1197);
                    type114=type();

                    state._fsp--;

                    stream_type.add(type114.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: QID, type
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 192:25: -> ^( TypePar QID ( type )? )
            {
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:192:28: ^( TypePar QID ( type )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TypePar, "TypePar"), root_1);

                adaptor.addChild(root_1, stream_QID.nextNode());
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:192:42: ( type )?
                if ( stream_type.hasNext() ) {
                    adaptor.addChild(root_1, stream_type.nextTree());

                }
                stream_type.reset();

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
    // $ANTLR end "typePar"

    public static class mainExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "mainExpression"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:197:1: mainExpression : expression EOF -> ^( Expression expression ) ;
    public final CalParser.mainExpression_return mainExpression() throws RecognitionException {
        CalParser.mainExpression_return retval = new CalParser.mainExpression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EOF116=null;
        CalParser.expression_return expression115 = null;


        Object EOF116_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:197:15: ( expression EOF -> ^( Expression expression ) )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:197:17: expression EOF
            {
            pushFollow(FOLLOW_expression_in_mainExpression1220);
            expression115=expression();

            state._fsp--;

            stream_expression.add(expression115.getTree());
            EOF116=(Token)match(input,EOF,FOLLOW_EOF_in_mainExpression1222);  
            stream_EOF.add(EOF116);



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
            // 197:32: -> ^( Expression expression )
            {
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:197:35: ^( Expression expression )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Expression, "Expression"), root_1);

                adaptor.addChild(root_1, stream_expression.nextTree());

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
    // $ANTLR end "mainExpression"

    public static class expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:199:1: expression : factor ( binop factor )* ;
    public final CalParser.expression_return expression() throws RecognitionException {
        CalParser.expression_return retval = new CalParser.expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        CalParser.factor_return factor117 = null;

        CalParser.binop_return binop118 = null;

        CalParser.factor_return factor119 = null;



        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:199:11: ( factor ( binop factor )* )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:199:13: factor ( binop factor )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_factor_in_expression1237);
            factor117=factor();

            state._fsp--;

            adaptor.addChild(root_0, factor117.getTree());
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:199:20: ( binop factor )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==MINUS||(LA41_0>=PLUS && LA41_0<=XOR)) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:199:21: binop factor
            	    {
            	    pushFollow(FOLLOW_binop_in_expression1240);
            	    binop118=binop();

            	    state._fsp--;

            	    adaptor.addChild(root_0, binop118.getTree());
            	    pushFollow(FOLLOW_factor_in_expression1242);
            	    factor119=factor();

            	    state._fsp--;

            	    adaptor.addChild(root_0, factor119.getTree());

            	    }
            	    break;

            	default :
            	    break loop41;
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
    // $ANTLR end "expression"

    public static class unop_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unop"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:201:1: unop : (op= MINUS | op= NOT ) -> ^( UnOp $op) ;
    public final CalParser.unop_return unop() throws RecognitionException {
        CalParser.unop_return retval = new CalParser.unop_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;

        Object op_tree=null;
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleTokenStream stream_MINUS=new RewriteRuleTokenStream(adaptor,"token MINUS");

        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:201:5: ( (op= MINUS | op= NOT ) -> ^( UnOp $op) )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:201:7: (op= MINUS | op= NOT )
            {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:201:7: (op= MINUS | op= NOT )
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==MINUS) ) {
                alt42=1;
            }
            else if ( (LA42_0==NOT) ) {
                alt42=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 42, 0, input);

                throw nvae;
            }
            switch (alt42) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:201:8: op= MINUS
                    {
                    op=(Token)match(input,MINUS,FOLLOW_MINUS_in_unop1254);  
                    stream_MINUS.add(op);


                    }
                    break;
                case 2 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:201:19: op= NOT
                    {
                    op=(Token)match(input,NOT,FOLLOW_NOT_in_unop1260);  
                    stream_NOT.add(op);


                    }
                    break;

            }



            // AST REWRITE
            // elements: op
            // token labels: op
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_op=new RewriteRuleTokenStream(adaptor,"token op",op);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 201:27: -> ^( UnOp $op)
            {
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:201:30: ^( UnOp $op)
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(UnOp, "UnOp"), root_1);

                adaptor.addChild(root_1, stream_op.nextNode());

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
    // $ANTLR end "unop"

    public static class binop_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "binop"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:203:1: binop : (op= PLUS | op= MINUS | op= TIMES | op= DIV | op= XOR ) -> ^( BinOp $op) ;
    public final CalParser.binop_return binop() throws RecognitionException {
        CalParser.binop_return retval = new CalParser.binop_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;

        Object op_tree=null;
        RewriteRuleTokenStream stream_PLUS=new RewriteRuleTokenStream(adaptor,"token PLUS");
        RewriteRuleTokenStream stream_XOR=new RewriteRuleTokenStream(adaptor,"token XOR");
        RewriteRuleTokenStream stream_MINUS=new RewriteRuleTokenStream(adaptor,"token MINUS");
        RewriteRuleTokenStream stream_DIV=new RewriteRuleTokenStream(adaptor,"token DIV");
        RewriteRuleTokenStream stream_TIMES=new RewriteRuleTokenStream(adaptor,"token TIMES");

        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:203:6: ( (op= PLUS | op= MINUS | op= TIMES | op= DIV | op= XOR ) -> ^( BinOp $op) )
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:203:8: (op= PLUS | op= MINUS | op= TIMES | op= DIV | op= XOR )
            {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:203:8: (op= PLUS | op= MINUS | op= TIMES | op= DIV | op= XOR )
            int alt43=5;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt43=1;
                }
                break;
            case MINUS:
                {
                alt43=2;
                }
                break;
            case TIMES:
                {
                alt43=3;
                }
                break;
            case DIV:
                {
                alt43=4;
                }
                break;
            case XOR:
                {
                alt43=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 43, 0, input);

                throw nvae;
            }

            switch (alt43) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:203:9: op= PLUS
                    {
                    op=(Token)match(input,PLUS,FOLLOW_PLUS_in_binop1280);  
                    stream_PLUS.add(op);


                    }
                    break;
                case 2 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:203:19: op= MINUS
                    {
                    op=(Token)match(input,MINUS,FOLLOW_MINUS_in_binop1286);  
                    stream_MINUS.add(op);


                    }
                    break;
                case 3 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:203:30: op= TIMES
                    {
                    op=(Token)match(input,TIMES,FOLLOW_TIMES_in_binop1292);  
                    stream_TIMES.add(op);


                    }
                    break;
                case 4 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:203:41: op= DIV
                    {
                    op=(Token)match(input,DIV,FOLLOW_DIV_in_binop1298);  
                    stream_DIV.add(op);


                    }
                    break;
                case 5 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:203:50: op= XOR
                    {
                    op=(Token)match(input,XOR,FOLLOW_XOR_in_binop1304);  
                    stream_XOR.add(op);


                    }
                    break;

            }



            // AST REWRITE
            // elements: op
            // token labels: op
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_op=new RewriteRuleTokenStream(adaptor,"token op",op);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 203:58: -> ^( BinOp $op)
            {
                // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:203:61: ^( BinOp $op)
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(BinOp, "BinOp"), root_1);

                adaptor.addChild(root_1, stream_op.nextNode());

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
    // $ANTLR end "binop"

    public static class factor_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "factor"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:205:1: factor : ( term | unop term -> ^( Expression unop term ) );
    public final CalParser.factor_return factor() throws RecognitionException {
        CalParser.factor_return retval = new CalParser.factor_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        CalParser.term_return term120 = null;

        CalParser.unop_return unop121 = null;

        CalParser.term_return term122 = null;


        RewriteRuleSubtreeStream stream_unop=new RewriteRuleSubtreeStream(adaptor,"rule unop");
        RewriteRuleSubtreeStream stream_term=new RewriteRuleSubtreeStream(adaptor,"rule term");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:205:7: ( term | unop term -> ^( Expression unop term ) )
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( ((LA44_0>=QID && LA44_0<=LBRACKET)||LA44_0==LPAREN||(LA44_0>=FLOAT && LA44_0<=FALSE)) ) {
                alt44=1;
            }
            else if ( ((LA44_0>=MINUS && LA44_0<=NOT)) ) {
                alt44=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 44, 0, input);

                throw nvae;
            }
            switch (alt44) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:205:9: term
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_term_in_factor1321);
                    term120=term();

                    state._fsp--;

                    adaptor.addChild(root_0, term120.getTree());

                    }
                    break;
                case 2 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:206:3: unop term
                    {
                    pushFollow(FOLLOW_unop_in_factor1325);
                    unop121=unop();

                    state._fsp--;

                    stream_unop.add(unop121.getTree());
                    pushFollow(FOLLOW_term_in_factor1327);
                    term122=term();

                    state._fsp--;

                    stream_term.add(term122.getTree());


                    // AST REWRITE
                    // elements: unop, term
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 206:13: -> ^( Expression unop term )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:206:16: ^( Expression unop term )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Expression, "Expression"), root_1);

                        adaptor.addChild(root_1, stream_unop.nextTree());
                        adaptor.addChild(root_1, stream_term.nextTree());

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
    // $ANTLR end "factor"

    public static class term_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "term"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:208:1: term : ( atom | LPAREN expression RPAREN -> ^( Expression expression ) );
    public final CalParser.term_return term() throws RecognitionException {
        CalParser.term_return retval = new CalParser.term_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token LPAREN124=null;
        Token RPAREN126=null;
        CalParser.atom_return atom123 = null;

        CalParser.expression_return expression125 = null;


        Object LPAREN124_tree=null;
        Object RPAREN126_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:208:5: ( atom | LPAREN expression RPAREN -> ^( Expression expression ) )
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( ((LA45_0>=QID && LA45_0<=LBRACKET)||(LA45_0>=FLOAT && LA45_0<=FALSE)) ) {
                alt45=1;
            }
            else if ( (LA45_0==LPAREN) ) {
                alt45=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;
            }
            switch (alt45) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:208:7: atom
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_atom_in_term1344);
                    atom123=atom();

                    state._fsp--;

                    adaptor.addChild(root_0, atom123.getTree());

                    }
                    break;
                case 2 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:209:5: LPAREN expression RPAREN
                    {
                    LPAREN124=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_term1350);  
                    stream_LPAREN.add(LPAREN124);

                    pushFollow(FOLLOW_expression_in_term1352);
                    expression125=expression();

                    state._fsp--;

                    stream_expression.add(expression125.getTree());
                    RPAREN126=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_term1354);  
                    stream_RPAREN.add(RPAREN126);



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
                    // 209:30: -> ^( Expression expression )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:209:33: ^( Expression expression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Expression, "Expression"), root_1);

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
    // $ANTLR end "term"

    public static class atom_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "atom"
    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:211:1: atom : ( QID -> ^( Var QID ) | FLOAT -> ^( Real FLOAT ) | INTEGER -> ^( Integer INTEGER ) | STRING -> ^( String STRING ) | TRUE -> ^( Boolean TRUE ) | FALSE -> ^( Boolean FALSE ) | LBRACKET ( expression ( COMMA expression )* )? RBRACKET -> ^( List ( expression )* ) );
    public final CalParser.atom_return atom() throws RecognitionException {
        CalParser.atom_return retval = new CalParser.atom_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token QID127=null;
        Token FLOAT128=null;
        Token INTEGER129=null;
        Token STRING130=null;
        Token TRUE131=null;
        Token FALSE132=null;
        Token LBRACKET133=null;
        Token COMMA135=null;
        Token RBRACKET137=null;
        CalParser.expression_return expression134 = null;

        CalParser.expression_return expression136 = null;


        Object QID127_tree=null;
        Object FLOAT128_tree=null;
        Object INTEGER129_tree=null;
        Object STRING130_tree=null;
        Object TRUE131_tree=null;
        Object FALSE132_tree=null;
        Object LBRACKET133_tree=null;
        Object COMMA135_tree=null;
        Object RBRACKET137_tree=null;
        RewriteRuleTokenStream stream_LBRACKET=new RewriteRuleTokenStream(adaptor,"token LBRACKET");
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_FLOAT=new RewriteRuleTokenStream(adaptor,"token FLOAT");
        RewriteRuleTokenStream stream_RBRACKET=new RewriteRuleTokenStream(adaptor,"token RBRACKET");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_FALSE=new RewriteRuleTokenStream(adaptor,"token FALSE");
        RewriteRuleTokenStream stream_TRUE=new RewriteRuleTokenStream(adaptor,"token TRUE");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleTokenStream stream_QID=new RewriteRuleTokenStream(adaptor,"token QID");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:211:5: ( QID -> ^( Var QID ) | FLOAT -> ^( Real FLOAT ) | INTEGER -> ^( Integer INTEGER ) | STRING -> ^( String STRING ) | TRUE -> ^( Boolean TRUE ) | FALSE -> ^( Boolean FALSE ) | LBRACKET ( expression ( COMMA expression )* )? RBRACKET -> ^( List ( expression )* ) )
            int alt48=7;
            switch ( input.LA(1) ) {
            case QID:
                {
                alt48=1;
                }
                break;
            case FLOAT:
                {
                alt48=2;
                }
                break;
            case INTEGER:
                {
                alt48=3;
                }
                break;
            case STRING:
                {
                alt48=4;
                }
                break;
            case TRUE:
                {
                alt48=5;
                }
                break;
            case FALSE:
                {
                alt48=6;
                }
                break;
            case LBRACKET:
                {
                alt48=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 48, 0, input);

                throw nvae;
            }

            switch (alt48) {
                case 1 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:211:7: QID
                    {
                    QID127=(Token)match(input,QID,FOLLOW_QID_in_atom1369);  
                    stream_QID.add(QID127);



                    // AST REWRITE
                    // elements: QID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 211:11: -> ^( Var QID )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:211:14: ^( Var QID )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_1);

                        adaptor.addChild(root_1, stream_QID.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:212:3: FLOAT
                    {
                    FLOAT128=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_atom1381);  
                    stream_FLOAT.add(FLOAT128);



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
                    // 212:9: -> ^( Real FLOAT )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:212:12: ^( Real FLOAT )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Real, "Real"), root_1);

                        adaptor.addChild(root_1, stream_FLOAT.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:213:3: INTEGER
                    {
                    INTEGER129=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_atom1393);  
                    stream_INTEGER.add(INTEGER129);



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
                    // 213:11: -> ^( Integer INTEGER )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:213:14: ^( Integer INTEGER )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Integer, "Integer"), root_1);

                        adaptor.addChild(root_1, stream_INTEGER.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:214:3: STRING
                    {
                    STRING130=(Token)match(input,STRING,FOLLOW_STRING_in_atom1405);  
                    stream_STRING.add(STRING130);



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
                    // 214:10: -> ^( String STRING )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:214:13: ^( String STRING )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(String, "String"), root_1);

                        adaptor.addChild(root_1, stream_STRING.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:215:3: TRUE
                    {
                    TRUE131=(Token)match(input,TRUE,FOLLOW_TRUE_in_atom1417);  
                    stream_TRUE.add(TRUE131);



                    // AST REWRITE
                    // elements: TRUE
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 215:8: -> ^( Boolean TRUE )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:215:11: ^( Boolean TRUE )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Boolean, "Boolean"), root_1);

                        adaptor.addChild(root_1, stream_TRUE.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 6 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:216:3: FALSE
                    {
                    FALSE132=(Token)match(input,FALSE,FOLLOW_FALSE_in_atom1429);  
                    stream_FALSE.add(FALSE132);



                    // AST REWRITE
                    // elements: FALSE
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 216:9: -> ^( Boolean FALSE )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:216:12: ^( Boolean FALSE )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Boolean, "Boolean"), root_1);

                        adaptor.addChild(root_1, stream_FALSE.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 7 :
                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:217:3: LBRACKET ( expression ( COMMA expression )* )? RBRACKET
                    {
                    LBRACKET133=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_atom1441);  
                    stream_LBRACKET.add(LBRACKET133);

                    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:217:12: ( expression ( COMMA expression )* )?
                    int alt47=2;
                    int LA47_0 = input.LA(1);

                    if ( ((LA47_0>=QID && LA47_0<=LBRACKET)||LA47_0==LPAREN||(LA47_0>=MINUS && LA47_0<=NOT)||(LA47_0>=FLOAT && LA47_0<=FALSE)) ) {
                        alt47=1;
                    }
                    switch (alt47) {
                        case 1 :
                            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:217:13: expression ( COMMA expression )*
                            {
                            pushFollow(FOLLOW_expression_in_atom1444);
                            expression134=expression();

                            state._fsp--;

                            stream_expression.add(expression134.getTree());
                            // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:217:24: ( COMMA expression )*
                            loop46:
                            do {
                                int alt46=2;
                                int LA46_0 = input.LA(1);

                                if ( (LA46_0==COMMA) ) {
                                    alt46=1;
                                }


                                switch (alt46) {
                            	case 1 :
                            	    // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:217:25: COMMA expression
                            	    {
                            	    COMMA135=(Token)match(input,COMMA,FOLLOW_COMMA_in_atom1447);  
                            	    stream_COMMA.add(COMMA135);

                            	    pushFollow(FOLLOW_expression_in_atom1449);
                            	    expression136=expression();

                            	    state._fsp--;

                            	    stream_expression.add(expression136.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop46;
                                }
                            } while (true);


                            }
                            break;

                    }

                    RBRACKET137=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_atom1455);  
                    stream_RBRACKET.add(RBRACKET137);



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
                    // 217:55: -> ^( List ( expression )* )
                    {
                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:217:58: ^( List ( expression )* )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(List, "List"), root_1);

                        // D:\\Work\\orcc\\trunk\\eclipse\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:217:65: ( expression )*
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

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "atom"

    // Delegated rules


 

    public static final BitSet FOLLOW_NETWORK_in_network236 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_QID_in_network238 = new BitSet(new long[]{0x0000014000000000L});
    public static final BitSet FOLLOW_LBRACKET_in_network241 = new BitSet(new long[]{0x000000A000000000L});
    public static final BitSet FOLLOW_typePars_in_network243 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_RBRACKET_in_network246 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_LPAREN_in_network252 = new BitSet(new long[]{0x0000022000000000L});
    public static final BitSet FOLLOW_parameters_in_network254 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_RPAREN_in_network257 = new BitSet(new long[]{0x4000102000000000L});
    public static final BitSet FOLLOW_portSignature_in_network261 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_COLON_in_network263 = new BitSet(new long[]{0x1014280000000000L});
    public static final BitSet FOLLOW_oneImport_in_network267 = new BitSet(new long[]{0x1014280000000000L});
    public static final BitSet FOLLOW_varDeclSection_in_network270 = new BitSet(new long[]{0x0014080000000000L});
    public static final BitSet FOLLOW_entitySection_in_network275 = new BitSet(new long[]{0x0010080000000000L});
    public static final BitSet FOLLOW_structureSection_in_network278 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_END_in_network283 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_network285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inputPorts_in_portSignature324 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_DOUBLE_EQUAL_ARROW_in_portSignature326 = new BitSet(new long[]{0x4000002000000000L});
    public static final BitSet FOLLOW_outputPorts_in_portSignature328 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_portDecls_in_inputPorts341 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_portDecls_in_outputPorts366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_varDeclSection392 = new BitSet(new long[]{0x0000402000000000L});
    public static final BitSet FOLLOW_varDecl_in_varDeclSection394 = new BitSet(new long[]{0x0000402000000002L});
    public static final BitSet FOLLOW_MUTABLE_in_varDecl407 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typeAndId_in_varDecl410 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_EQ_in_varDecl416 = new BitSet(new long[]{0x0000016000000000L,0x00000000000007C3L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_varDecl420 = new BitSet(new long[]{0x0000016000000000L,0x00000000000007C3L});
    public static final BitSet FOLLOW_expression_in_varDecl423 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_varDecl452 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ENTITIES_in_entitySection460 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_entityDecl_in_entitySection462 = new BitSet(new long[]{0x0000002000000002L});
    public static final BitSet FOLLOW_QID_in_entityDecl475 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_EQ_in_entityDecl477 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_entityExpr_in_entityDecl479 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_entityDecl481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QID_in_entityExpr502 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_LPAREN_in_entityExpr504 = new BitSet(new long[]{0x0000022000000000L});
    public static final BitSet FOLLOW_entityPars_in_entityExpr506 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_RPAREN_in_entityExpr509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_entityPar_in_entityPars531 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_COMMA_in_entityPars534 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_entityPar_in_entityPars536 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_QID_in_entityPar550 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_EQ_in_entityPar552 = new BitSet(new long[]{0x0000016000000000L,0x00000000000007C3L});
    public static final BitSet FOLLOW_expression_in_entityPar554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRUCTURE_in_structureSection580 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_structureStmt_in_structureSection582 = new BitSet(new long[]{0x0000002000000002L});
    public static final BitSet FOLLOW_connector_in_structureStmt597 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_DOUBLE_DASH_ARROW_in_structureStmt599 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_connector_in_structureStmt603 = new BitSet(new long[]{0x0082000000000000L});
    public static final BitSet FOLLOW_attributeSection_in_structureStmt607 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_structureStmt610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QID_in_connector638 = new BitSet(new long[]{0x0040000000000002L});
    public static final BitSet FOLLOW_DOT_in_connector644 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_QID_in_connector648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_attributeSection693 = new BitSet(new long[]{0x0100002000000000L});
    public static final BitSet FOLLOW_attributeDecl_in_attributeSection695 = new BitSet(new long[]{0x0100002000000000L});
    public static final BitSet FOLLOW_RBRACE_in_attributeSection698 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QID_in_attributeDecl712 = new BitSet(new long[]{0x0000840000000000L});
    public static final BitSet FOLLOW_EQ_in_attributeDecl715 = new BitSet(new long[]{0x0000016000000000L,0x00000000000007C3L});
    public static final BitSet FOLLOW_expression_in_attributeDecl717 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_attributeDecl719 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_attributeDecl742 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_type_in_attributeDecl744 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_attributeDecl746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_packageRule_in_actor777 = new BitSet(new long[]{0x1600000000000000L});
    public static final BitSet FOLLOW_oneImport_in_actor780 = new BitSet(new long[]{0x1600000000000000L});
    public static final BitSet FOLLOW_NATIVE_in_actor783 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_ACTOR_in_actor786 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_QID_in_actor788 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_LPAREN_in_actor792 = new BitSet(new long[]{0x0000022000000000L});
    public static final BitSet FOLLOW_parameters_in_actor794 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_RPAREN_in_actor797 = new BitSet(new long[]{0x4000102000000000L});
    public static final BitSet FOLLOW_portSignature_in_actor801 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_COLON_in_actor803 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000001FFFFFFL});
    public static final BitSet FOLLOW_EOF_in_actor808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PACKAGE_in_packageRule837 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_QID_in_packageRule839 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_packageRule841 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IMPORT_in_oneImport851 = new BitSet(new long[]{0x2000002000000000L});
    public static final BitSet FOLLOW_set_in_oneImport853 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_oneImport861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAndId_in_parameter871 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_EQ_in_parameter876 = new BitSet(new long[]{0x0000016000000000L,0x00000000000007C3L});
    public static final BitSet FOLLOW_expression_in_parameter878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parameter_in_parameters912 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_COMMA_in_parameters915 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_parameter_in_parameters917 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_MULTI_in_portDecl934 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typeAndId_in_portDecl937 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_portDecl_in_portDecls952 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_COMMA_in_portDecls955 = new BitSet(new long[]{0x4000002000000000L});
    public static final BitSet FOLLOW_portDecl_in_portDecls957 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_typeAndId_in_mainParameter975 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_mainParameter977 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QID_in_typeAndId994 = new BitSet(new long[]{0x0000016000000002L});
    public static final BitSet FOLLOW_typeRest_in_typeAndId999 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_QID_in_typeAndId1004 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QID_in_type1048 = new BitSet(new long[]{0x0000014000000002L});
    public static final BitSet FOLLOW_typeRest_in_type1050 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACKET_in_typeRest1073 = new BitSet(new long[]{0x000000A000000000L});
    public static final BitSet FOLLOW_typePars_in_typeRest1075 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_RBRACKET_in_typeRest1078 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_typeRest1089 = new BitSet(new long[]{0x0000022000000000L});
    public static final BitSet FOLLOW_typeAttrs_in_typeRest1091 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_RPAREN_in_typeRest1094 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs1106 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_COMMA_in_typeAttrs1109 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs1111 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_QID_in_typeAttr1125 = new BitSet(new long[]{0x0000840000000000L});
    public static final BitSet FOLLOW_typeAttrRest_in_typeAttr1127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_typeAttrRest1138 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_type_in_typeAttrRest1140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EQ_in_typeAttrRest1152 = new BitSet(new long[]{0x0000016000000000L,0x00000000000007C3L});
    public static final BitSet FOLLOW_expression_in_typeAttrRest1154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typePar_in_typePars1173 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_COMMA_in_typePars1176 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typePar_in_typePars1178 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_QID_in_typePar1192 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_LT_in_typePar1195 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_type_in_typePar1197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_mainExpression1220 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_mainExpression1222 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_factor_in_expression1237 = new BitSet(new long[]{0x0000000000000002L,0x000000000000003DL});
    public static final BitSet FOLLOW_binop_in_expression1240 = new BitSet(new long[]{0x0000016000000000L,0x00000000000007C3L});
    public static final BitSet FOLLOW_factor_in_expression1242 = new BitSet(new long[]{0x0000000000000002L,0x000000000000003DL});
    public static final BitSet FOLLOW_MINUS_in_unop1254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_unop1260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_in_binop1280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_binop1286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TIMES_in_binop1292 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DIV_in_binop1298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_XOR_in_binop1304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_factor1321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unop_in_factor1325 = new BitSet(new long[]{0x0000016000000000L,0x00000000000007C0L});
    public static final BitSet FOLLOW_term_in_factor1327 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atom_in_term1344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_term1350 = new BitSet(new long[]{0x0000016000000000L,0x00000000000007C3L});
    public static final BitSet FOLLOW_expression_in_term1352 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_RPAREN_in_term1354 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QID_in_atom1369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_atom1381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_atom1393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_atom1405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_atom1417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_atom1429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACKET_in_atom1441 = new BitSet(new long[]{0x000001E000000000L,0x00000000000007C3L});
    public static final BitSet FOLLOW_expression_in_atom1444 = new BitSet(new long[]{0x0008008000000000L});
    public static final BitSet FOLLOW_COMMA_in_atom1447 = new BitSet(new long[]{0x0000016000000000L,0x00000000000007C3L});
    public static final BitSet FOLLOW_expression_in_atom1449 = new BitSet(new long[]{0x0008008000000000L});
    public static final BitSet FOLLOW_RBRACKET_in_atom1455 = new BitSet(new long[]{0x0000000000000002L});

}