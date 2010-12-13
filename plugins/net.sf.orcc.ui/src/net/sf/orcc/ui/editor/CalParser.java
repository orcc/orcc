// $ANTLR 3.3 Nov 30, 2010 12:45:30 D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g 2010-12-13 15:58:04

package net.sf.orcc.ui.editor;


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
public class CalParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "Attribute", "Connector", "EntityDecl", "EntityExpr", "EntityPar", "Network", "StructureStmt", "VarDecl", "Actor", "Dot", "Empty", "Name", "Inputs", "Outputs", "PortDecl", "QualifiedId", "Parameter", "Type", "TypeAttr", "ExprAttr", "TypePar", "BinOp", "Boolean", "Expression", "Integer", "List", "Minus", "Not", "Real", "String", "UnOp", "Var", "NETWORK", "QID", "LBRACKET", "RBRACKET", "LPAREN", "RPAREN", "COLON", "END", "DOUBLE_EQUAL_ARROW", "VAR", "MUTABLE", "EQ", "COLON_EQUAL", "SEMICOLON", "ENTITIES", "COMMA", "STRUCTURE", "DOUBLE_DASH_ARROW", "DOT", "LBRACE", "RBRACE", "ACTOR", "IMPORT", "QID_WILDCARD", "MULTI", "LT", "MINUS", "NOT", "PLUS", "TIMES", "DIV", "XOR", "FLOAT", "INTEGER", "STRING", "TRUE", "FALSE", "ALL", "ID", "LINE_COMMENT", "MULTI_LINE_COMMENT", "WHITESPACE", "GE", "GT", "LE", "NE", "ARROW", "DOUBLE_DOT", "AND", "OR", "SHARP"
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
    public static final int ACTOR=57;
    public static final int IMPORT=58;
    public static final int QID_WILDCARD=59;
    public static final int MULTI=60;
    public static final int LT=61;
    public static final int MINUS=62;
    public static final int NOT=63;
    public static final int PLUS=64;
    public static final int TIMES=65;
    public static final int DIV=66;
    public static final int XOR=67;
    public static final int FLOAT=68;
    public static final int INTEGER=69;
    public static final int STRING=70;
    public static final int TRUE=71;
    public static final int FALSE=72;
    public static final int ALL=73;
    public static final int ID=74;
    public static final int LINE_COMMENT=75;
    public static final int MULTI_LINE_COMMENT=76;
    public static final int WHITESPACE=77;
    public static final int GE=78;
    public static final int GT=79;
    public static final int LE=80;
    public static final int NE=81;
    public static final int ARROW=82;
    public static final int DOUBLE_DOT=83;
    public static final int AND=84;
    public static final int OR=85;
    public static final int SHARP=86;

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
    public String getGrammarFileName() { return "D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g"; }


    public static class network_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "network"
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:90:1: network : NETWORK QID ( LBRACKET ( typePars )? RBRACKET )? LPAREN ( parameters )? RPAREN portSignature COLON ( oneImport )* ( varDeclSection )? ( entitySection )? ( structureSection )? END EOF -> ^( Network QID ( parameters )? portSignature ( varDeclSection )? ( entitySection )? ( structureSection )? ) ;
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:90:8: ( NETWORK QID ( LBRACKET ( typePars )? RBRACKET )? LPAREN ( parameters )? RPAREN portSignature COLON ( oneImport )* ( varDeclSection )? ( entitySection )? ( structureSection )? END EOF -> ^( Network QID ( parameters )? portSignature ( varDeclSection )? ( entitySection )? ( structureSection )? ) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:90:10: NETWORK QID ( LBRACKET ( typePars )? RBRACKET )? LPAREN ( parameters )? RPAREN portSignature COLON ( oneImport )* ( varDeclSection )? ( entitySection )? ( structureSection )? END EOF
            {
            NETWORK1=(Token)match(input,NETWORK,FOLLOW_NETWORK_in_network236);  
            stream_NETWORK.add(NETWORK1);

            QID2=(Token)match(input,QID,FOLLOW_QID_in_network238);  
            stream_QID.add(QID2);

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:90:22: ( LBRACKET ( typePars )? RBRACKET )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==LBRACKET) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:90:23: LBRACKET ( typePars )? RBRACKET
                    {
                    LBRACKET3=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_network241);  
                    stream_LBRACKET.add(LBRACKET3);

                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:90:32: ( typePars )?
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==QID) ) {
                        alt1=1;
                    }
                    switch (alt1) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:90:32: typePars
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

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:91:10: ( parameters )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==QID) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:91:10: parameters
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

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:93:3: ( oneImport )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==IMPORT) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:93:3: oneImport
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

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:93:14: ( varDeclSection )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==VAR) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:93:14: varDeclSection
                    {
                    pushFollow(FOLLOW_varDeclSection_in_network270);
                    varDeclSection12=varDeclSection();

                    state._fsp--;

                    stream_varDeclSection.add(varDeclSection12.getTree());

                    }
                    break;

            }

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:94:3: ( entitySection )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==ENTITIES) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:94:3: entitySection
                    {
                    pushFollow(FOLLOW_entitySection_in_network275);
                    entitySection13=entitySection();

                    state._fsp--;

                    stream_entitySection.add(entitySection13.getTree());

                    }
                    break;

            }

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:94:18: ( structureSection )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==STRUCTURE) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:94:18: structureSection
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
            // elements: varDeclSection, portSignature, entitySection, structureSection, QID, parameters
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
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:96:5: ^( Network QID ( parameters )? portSignature ( varDeclSection )? ( entitySection )? ( structureSection )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Network, "Network"), root_1);

                adaptor.addChild(root_1, stream_QID.nextNode());
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:96:19: ( parameters )?
                if ( stream_parameters.hasNext() ) {
                    adaptor.addChild(root_1, stream_parameters.nextTree());

                }
                stream_parameters.reset();
                adaptor.addChild(root_1, stream_portSignature.nextTree());
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:97:7: ( varDeclSection )?
                if ( stream_varDeclSection.hasNext() ) {
                    adaptor.addChild(root_1, stream_varDeclSection.nextTree());

                }
                stream_varDeclSection.reset();
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:97:23: ( entitySection )?
                if ( stream_entitySection.hasNext() ) {
                    adaptor.addChild(root_1, stream_entitySection.nextTree());

                }
                stream_entitySection.reset();
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:97:38: ( structureSection )?
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:99:1: portSignature : inputPorts DOUBLE_EQUAL_ARROW outputPorts -> inputPorts outputPorts ;
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:99:14: ( inputPorts DOUBLE_EQUAL_ARROW outputPorts -> inputPorts outputPorts )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:99:16: inputPorts DOUBLE_EQUAL_ARROW outputPorts
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:101:1: inputPorts : ( portDecls -> ^( Inputs portDecls ) | -> ^( Inputs Empty ) );
    public final CalParser.inputPorts_return inputPorts() throws RecognitionException {
        CalParser.inputPorts_return retval = new CalParser.inputPorts_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        CalParser.portDecls_return portDecls20 = null;


        RewriteRuleSubtreeStream stream_portDecls=new RewriteRuleSubtreeStream(adaptor,"rule portDecls");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:101:11: ( portDecls -> ^( Inputs portDecls ) | -> ^( Inputs Empty ) )
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:101:13: portDecls
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
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:101:26: ^( Inputs portDecls )
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:101:48: 
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
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:101:51: ^( Inputs Empty )
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:103:1: outputPorts : ( portDecls -> ^( Outputs portDecls ) | -> ^( Outputs Empty ) );
    public final CalParser.outputPorts_return outputPorts() throws RecognitionException {
        CalParser.outputPorts_return retval = new CalParser.outputPorts_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        CalParser.portDecls_return portDecls21 = null;


        RewriteRuleSubtreeStream stream_portDecls=new RewriteRuleSubtreeStream(adaptor,"rule portDecls");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:103:12: ( portDecls -> ^( Outputs portDecls ) | -> ^( Outputs Empty ) )
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:103:14: portDecls
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
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:103:27: ^( Outputs portDecls )
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:103:50: 
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
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:103:53: ^( Outputs Empty )
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:106:1: varDeclSection : VAR ( varDecl )+ -> ( varDecl )+ ;
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:106:15: ( VAR ( varDecl )+ -> ( varDecl )+ )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:106:17: VAR ( varDecl )+
            {
            VAR22=(Token)match(input,VAR,FOLLOW_VAR_in_varDeclSection392);  
            stream_VAR.add(VAR22);

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:106:21: ( varDecl )+
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
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:106:21: varDecl
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:108:1: varDecl : ( MUTABLE )? typeAndId ( ( EQ | COLON_EQUAL ) expression -> ^( VarDecl typeAndId ^( Expression expression ) ) | -> ^( VarDecl typeAndId ) ) SEMICOLON ;
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:108:8: ( ( MUTABLE )? typeAndId ( ( EQ | COLON_EQUAL ) expression -> ^( VarDecl typeAndId ^( Expression expression ) ) | -> ^( VarDecl typeAndId ) ) SEMICOLON )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:108:10: ( MUTABLE )? typeAndId ( ( EQ | COLON_EQUAL ) expression -> ^( VarDecl typeAndId ^( Expression expression ) ) | -> ^( VarDecl typeAndId ) ) SEMICOLON
            {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:108:10: ( MUTABLE )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==MUTABLE) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:108:10: MUTABLE
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:109:3: ( ( EQ | COLON_EQUAL ) expression -> ^( VarDecl typeAndId ^( Expression expression ) ) | -> ^( VarDecl typeAndId ) )
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:109:4: ( EQ | COLON_EQUAL ) expression
                    {
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:109:4: ( EQ | COLON_EQUAL )
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
                            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:109:5: EQ
                            {
                            EQ26=(Token)match(input,EQ,FOLLOW_EQ_in_varDecl416);  
                            stream_EQ.add(EQ26);


                            }
                            break;
                        case 2 :
                            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:109:10: COLON_EQUAL
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
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:109:37: ^( VarDecl typeAndId ^( Expression expression ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(VarDecl, "VarDecl"), root_1);

                        adaptor.addChild(root_1, stream_typeAndId.nextTree());
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:109:57: ^( Expression expression )
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:110:5: 
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
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:110:8: ^( VarDecl typeAndId )
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:113:1: entitySection : ENTITIES ( entityDecl )+ -> ( entityDecl )+ ;
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:113:14: ( ENTITIES ( entityDecl )+ -> ( entityDecl )+ )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:113:16: ENTITIES ( entityDecl )+
            {
            ENTITIES30=(Token)match(input,ENTITIES,FOLLOW_ENTITIES_in_entitySection460);  
            stream_ENTITIES.add(ENTITIES30);

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:113:25: ( entityDecl )+
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
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:113:25: entityDecl
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:115:1: entityDecl : QID EQ entityExpr SEMICOLON -> ^( EntityDecl ^( Var QID ) entityExpr ) ;
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:115:11: ( QID EQ entityExpr SEMICOLON -> ^( EntityDecl ^( Var QID ) entityExpr ) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:115:13: QID EQ entityExpr SEMICOLON
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
            // elements: entityExpr, QID
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
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:115:44: ^( EntityDecl ^( Var QID ) entityExpr )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EntityDecl, "EntityDecl"), root_1);

                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:115:57: ^( Var QID )
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:117:1: entityExpr : QID LPAREN ( entityPars )? RPAREN -> ^( EntityExpr ^( Var QID ) ( entityPars )? ) ;
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:117:11: ( QID LPAREN ( entityPars )? RPAREN -> ^( EntityExpr ^( Var QID ) ( entityPars )? ) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:117:13: QID LPAREN ( entityPars )? RPAREN
            {
            QID36=(Token)match(input,QID,FOLLOW_QID_in_entityExpr502);  
            stream_QID.add(QID36);

            LPAREN37=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_entityExpr504);  
            stream_LPAREN.add(LPAREN37);

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:117:24: ( entityPars )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==QID) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:117:24: entityPars
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
            // elements: QID, entityPars
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
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:117:46: ^( EntityExpr ^( Var QID ) ( entityPars )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EntityExpr, "EntityExpr"), root_1);

                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:117:59: ^( Var QID )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_2);

                adaptor.addChild(root_2, stream_QID.nextNode());

                adaptor.addChild(root_1, root_2);
                }
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:117:70: ( entityPars )?
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:119:1: entityPars : entityPar ( COMMA entityPar )* -> ( entityPar )+ ;
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:119:11: ( entityPar ( COMMA entityPar )* -> ( entityPar )+ )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:119:13: entityPar ( COMMA entityPar )*
            {
            pushFollow(FOLLOW_entityPar_in_entityPars531);
            entityPar40=entityPar();

            state._fsp--;

            stream_entityPar.add(entityPar40.getTree());
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:119:23: ( COMMA entityPar )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==COMMA) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:119:24: COMMA entityPar
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:121:1: entityPar : QID EQ expression -> ^( EntityPar ^( Var QID ) ^( Expression expression ) ) ;
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:121:10: ( QID EQ expression -> ^( EntityPar ^( Var QID ) ^( Expression expression ) ) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:121:12: QID EQ expression
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
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:121:33: ^( EntityPar ^( Var QID ) ^( Expression expression ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EntityPar, "EntityPar"), root_1);

                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:121:45: ^( Var QID )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_2);

                adaptor.addChild(root_2, stream_QID.nextNode());

                adaptor.addChild(root_1, root_2);
                }
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:121:56: ^( Expression expression )
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:124:1: structureSection : STRUCTURE ( structureStmt )+ -> ( structureStmt )+ ;
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:124:17: ( STRUCTURE ( structureStmt )+ -> ( structureStmt )+ )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:124:19: STRUCTURE ( structureStmt )+
            {
            STRUCTURE46=(Token)match(input,STRUCTURE,FOLLOW_STRUCTURE_in_structureSection580);  
            stream_STRUCTURE.add(STRUCTURE46);

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:124:29: ( structureStmt )+
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
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:124:29: structureStmt
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:126:1: structureStmt : c1= connector DOUBLE_DASH_ARROW c2= connector (at= attributeSection )? SEMICOLON -> ^( StructureStmt $c1 $c2 ( $at)? ) ;
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:126:14: (c1= connector DOUBLE_DASH_ARROW c2= connector (at= attributeSection )? SEMICOLON -> ^( StructureStmt $c1 $c2 ( $at)? ) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:126:16: c1= connector DOUBLE_DASH_ARROW c2= connector (at= attributeSection )? SEMICOLON
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:126:62: (at= attributeSection )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==LBRACE) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:126:62: at= attributeSection
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
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:127:3: ^( StructureStmt $c1 $c2 ( $at)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(StructureStmt, "StructureStmt"), root_1);

                adaptor.addChild(root_1, stream_c1.nextTree());
                adaptor.addChild(root_1, stream_c2.nextTree());
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:127:27: ( $at)?
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:129:1: connector : v1= QID ( DOT v2= QID -> ^( Connector ^( Var $v1) ^( Var $v2) ) | -> ^( Connector ^( Var $v1) ) ) ;
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:129:10: (v1= QID ( DOT v2= QID -> ^( Connector ^( Var $v1) ^( Var $v2) ) | -> ^( Connector ^( Var $v1) ) ) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:129:12: v1= QID ( DOT v2= QID -> ^( Connector ^( Var $v1) ^( Var $v2) ) | -> ^( Connector ^( Var $v1) ) )
            {
            v1=(Token)match(input,QID,FOLLOW_QID_in_connector638);  
            stream_QID.add(v1);

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:129:19: ( DOT v2= QID -> ^( Connector ^( Var $v1) ^( Var $v2) ) | -> ^( Connector ^( Var $v1) ) )
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:130:3: DOT v2= QID
                    {
                    DOT50=(Token)match(input,DOT,FOLLOW_DOT_in_connector644);  
                    stream_DOT.add(DOT50);

                    v2=(Token)match(input,QID,FOLLOW_QID_in_connector648);  
                    stream_QID.add(v2);



                    // AST REWRITE
                    // elements: v2, v1
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
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:130:17: ^( Connector ^( Var $v1) ^( Var $v2) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Connector, "Connector"), root_1);

                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:130:29: ^( Var $v1)
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_2);

                        adaptor.addChild(root_2, stream_v1.nextNode());

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:130:40: ^( Var $v2)
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:131:5: 
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
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:131:8: ^( Connector ^( Var $v1) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Connector, "Connector"), root_1);

                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:131:20: ^( Var $v1)
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:133:1: attributeSection : LBRACE ( attributeDecl )* RBRACE -> ( attributeDecl )* ;
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:133:17: ( LBRACE ( attributeDecl )* RBRACE -> ( attributeDecl )* )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:133:19: LBRACE ( attributeDecl )* RBRACE
            {
            LBRACE51=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_attributeSection693);  
            stream_LBRACE.add(LBRACE51);

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:133:26: ( attributeDecl )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==QID) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:133:26: attributeDecl
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
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:133:51: ( attributeDecl )*
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:135:1: attributeDecl : id= QID ( EQ expression SEMICOLON -> ^( Attribute ^( Var $id) ^( Expression expression ) ) | COLON type SEMICOLON -> ^( Attribute ^( Var $id) ^( Type type ) ) ) ;
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:135:14: (id= QID ( EQ expression SEMICOLON -> ^( Attribute ^( Var $id) ^( Expression expression ) ) | COLON type SEMICOLON -> ^( Attribute ^( Var $id) ^( Type type ) ) ) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:135:16: id= QID ( EQ expression SEMICOLON -> ^( Attribute ^( Var $id) ^( Expression expression ) ) | COLON type SEMICOLON -> ^( Attribute ^( Var $id) ^( Type type ) ) )
            {
            id=(Token)match(input,QID,FOLLOW_QID_in_attributeDecl712);  
            stream_QID.add(id);

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:135:23: ( EQ expression SEMICOLON -> ^( Attribute ^( Var $id) ^( Expression expression ) ) | COLON type SEMICOLON -> ^( Attribute ^( Var $id) ^( Type type ) ) )
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:135:24: EQ expression SEMICOLON
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
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:135:51: ^( Attribute ^( Var $id) ^( Expression expression ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Attribute, "Attribute"), root_1);

                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:135:63: ^( Var $id)
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_2);

                        adaptor.addChild(root_2, stream_id.nextNode());

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:135:74: ^( Expression expression )
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:136:3: COLON type SEMICOLON
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
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:136:27: ^( Attribute ^( Var $id) ^( Type type ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Attribute, "Attribute"), root_1);

                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:136:39: ^( Var $id)
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_2);

                        adaptor.addChild(root_2, stream_id.nextNode());

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:136:50: ^( Type type )
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:141:1: actor : ( oneImport )* ACTOR QID LPAREN ( parameters )? RPAREN portSignature COLON ( . )* EOF -> ^( Actor ^( Name QID ) ( parameters )? portSignature ) ;
    public final CalParser.actor_return actor() throws RecognitionException {
        CalParser.actor_return retval = new CalParser.actor_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ACTOR61=null;
        Token QID62=null;
        Token LPAREN63=null;
        Token RPAREN65=null;
        Token COLON67=null;
        Token wildcard68=null;
        Token EOF69=null;
        CalParser.oneImport_return oneImport60 = null;

        CalParser.parameters_return parameters64 = null;

        CalParser.portSignature_return portSignature66 = null;


        Object ACTOR61_tree=null;
        Object QID62_tree=null;
        Object LPAREN63_tree=null;
        Object RPAREN65_tree=null;
        Object COLON67_tree=null;
        Object wildcard68_tree=null;
        Object EOF69_tree=null;
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleTokenStream stream_ACTOR=new RewriteRuleTokenStream(adaptor,"token ACTOR");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleTokenStream stream_QID=new RewriteRuleTokenStream(adaptor,"token QID");
        RewriteRuleSubtreeStream stream_portSignature=new RewriteRuleSubtreeStream(adaptor,"rule portSignature");
        RewriteRuleSubtreeStream stream_parameters=new RewriteRuleSubtreeStream(adaptor,"rule parameters");
        RewriteRuleSubtreeStream stream_oneImport=new RewriteRuleSubtreeStream(adaptor,"rule oneImport");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:141:6: ( ( oneImport )* ACTOR QID LPAREN ( parameters )? RPAREN portSignature COLON ( . )* EOF -> ^( Actor ^( Name QID ) ( parameters )? portSignature ) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:141:8: ( oneImport )* ACTOR QID LPAREN ( parameters )? RPAREN portSignature COLON ( . )* EOF
            {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:141:8: ( oneImport )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==IMPORT) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:141:8: oneImport
            	    {
            	    pushFollow(FOLLOW_oneImport_in_actor777);
            	    oneImport60=oneImport();

            	    state._fsp--;

            	    stream_oneImport.add(oneImport60.getTree());

            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);

            ACTOR61=(Token)match(input,ACTOR,FOLLOW_ACTOR_in_actor780);  
            stream_ACTOR.add(ACTOR61);

            QID62=(Token)match(input,QID,FOLLOW_QID_in_actor782);  
            stream_QID.add(QID62);

            LPAREN63=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_actor786);  
            stream_LPAREN.add(LPAREN63);

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:142:10: ( parameters )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==QID) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:142:10: parameters
                    {
                    pushFollow(FOLLOW_parameters_in_actor788);
                    parameters64=parameters();

                    state._fsp--;

                    stream_parameters.add(parameters64.getTree());

                    }
                    break;

            }

            RPAREN65=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_actor791);  
            stream_RPAREN.add(RPAREN65);

            pushFollow(FOLLOW_portSignature_in_actor795);
            portSignature66=portSignature();

            state._fsp--;

            stream_portSignature.add(portSignature66.getTree());
            COLON67=(Token)match(input,COLON,FOLLOW_COLON_in_actor797);  
            stream_COLON.add(COLON67);

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:143:23: ( . )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( ((LA24_0>=Attribute && LA24_0<=SHARP)) ) {
                    alt24=1;
                }
                else if ( (LA24_0==EOF) ) {
                    alt24=2;
                }


                switch (alt24) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:143:23: .
            	    {
            	    wildcard68=(Token)input.LT(1);
            	    matchAny(input); 
            	    wildcard68_tree = (Object)adaptor.create(wildcard68);
            	    adaptor.addChild(root_0, wildcard68_tree);


            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);

            EOF69=(Token)match(input,EOF,FOLLOW_EOF_in_actor802);  
            stream_EOF.add(EOF69);



            // AST REWRITE
            // elements: QID, portSignature, parameters
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
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:144:5: ^( Actor ^( Name QID ) ( parameters )? portSignature )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Actor, "Actor"), root_1);

                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:144:13: ^( Name QID )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Name, "Name"), root_2);

                adaptor.addChild(root_2, stream_QID.nextNode());

                adaptor.addChild(root_1, root_2);
                }
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:144:25: ( parameters )?
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

    public static class oneImport_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "oneImport"
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:149:1: oneImport : IMPORT QID_WILDCARD SEMICOLON ;
    public final CalParser.oneImport_return oneImport() throws RecognitionException {
        CalParser.oneImport_return retval = new CalParser.oneImport_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IMPORT70=null;
        Token QID_WILDCARD71=null;
        Token SEMICOLON72=null;

        Object IMPORT70_tree=null;
        Object QID_WILDCARD71_tree=null;
        Object SEMICOLON72_tree=null;

        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:149:10: ( IMPORT QID_WILDCARD SEMICOLON )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:149:12: IMPORT QID_WILDCARD SEMICOLON
            {
            root_0 = (Object)adaptor.nil();

            IMPORT70=(Token)match(input,IMPORT,FOLLOW_IMPORT_in_oneImport833); 
            IMPORT70_tree = (Object)adaptor.create(IMPORT70);
            adaptor.addChild(root_0, IMPORT70_tree);

            QID_WILDCARD71=(Token)match(input,QID_WILDCARD,FOLLOW_QID_WILDCARD_in_oneImport835); 
            QID_WILDCARD71_tree = (Object)adaptor.create(QID_WILDCARD71);
            adaptor.addChild(root_0, QID_WILDCARD71_tree);

            SEMICOLON72=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_oneImport837); 
            SEMICOLON72_tree = (Object)adaptor.create(SEMICOLON72);
            adaptor.addChild(root_0, SEMICOLON72_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:154:1: parameter : typeAndId ( EQ expression -> ^( Parameter typeAndId ^( Expression expression ) ) | -> ^( Parameter typeAndId ) ) ;
    public final CalParser.parameter_return parameter() throws RecognitionException {
        CalParser.parameter_return retval = new CalParser.parameter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EQ74=null;
        CalParser.typeAndId_return typeAndId73 = null;

        CalParser.expression_return expression75 = null;


        Object EQ74_tree=null;
        RewriteRuleTokenStream stream_EQ=new RewriteRuleTokenStream(adaptor,"token EQ");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeAndId=new RewriteRuleSubtreeStream(adaptor,"rule typeAndId");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:154:10: ( typeAndId ( EQ expression -> ^( Parameter typeAndId ^( Expression expression ) ) | -> ^( Parameter typeAndId ) ) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:154:12: typeAndId ( EQ expression -> ^( Parameter typeAndId ^( Expression expression ) ) | -> ^( Parameter typeAndId ) )
            {
            pushFollow(FOLLOW_typeAndId_in_parameter847);
            typeAndId73=typeAndId();

            state._fsp--;

            stream_typeAndId.add(typeAndId73.getTree());
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:155:3: ( EQ expression -> ^( Parameter typeAndId ^( Expression expression ) ) | -> ^( Parameter typeAndId ) )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==EQ) ) {
                alt25=1;
            }
            else if ( (LA25_0==RPAREN||LA25_0==COMMA) ) {
                alt25=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:155:4: EQ expression
                    {
                    EQ74=(Token)match(input,EQ,FOLLOW_EQ_in_parameter852);  
                    stream_EQ.add(EQ74);

                    pushFollow(FOLLOW_expression_in_parameter854);
                    expression75=expression();

                    state._fsp--;

                    stream_expression.add(expression75.getTree());


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
                    // 155:18: -> ^( Parameter typeAndId ^( Expression expression ) )
                    {
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:155:21: ^( Parameter typeAndId ^( Expression expression ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Parameter, "Parameter"), root_1);

                        adaptor.addChild(root_1, stream_typeAndId.nextTree());
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:155:43: ^( Expression expression )
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:156:5: 
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
                    // 156:5: -> ^( Parameter typeAndId )
                    {
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:156:8: ^( Parameter typeAndId )
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:158:1: parameters : parameter ( COMMA parameter )* -> ( parameter )+ ;
    public final CalParser.parameters_return parameters() throws RecognitionException {
        CalParser.parameters_return retval = new CalParser.parameters_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA77=null;
        CalParser.parameter_return parameter76 = null;

        CalParser.parameter_return parameter78 = null;


        Object COMMA77_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleSubtreeStream stream_parameter=new RewriteRuleSubtreeStream(adaptor,"rule parameter");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:158:11: ( parameter ( COMMA parameter )* -> ( parameter )+ )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:158:13: parameter ( COMMA parameter )*
            {
            pushFollow(FOLLOW_parameter_in_parameters888);
            parameter76=parameter();

            state._fsp--;

            stream_parameter.add(parameter76.getTree());
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:158:23: ( COMMA parameter )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==COMMA) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:158:24: COMMA parameter
            	    {
            	    COMMA77=(Token)match(input,COMMA,FOLLOW_COMMA_in_parameters891);  
            	    stream_COMMA.add(COMMA77);

            	    pushFollow(FOLLOW_parameter_in_parameters893);
            	    parameter78=parameter();

            	    state._fsp--;

            	    stream_parameter.add(parameter78.getTree());

            	    }
            	    break;

            	default :
            	    break loop26;
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
            // 158:42: -> ( parameter )+
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:163:1: portDecl : ( MULTI )? typeAndId -> ^( PortDecl typeAndId ) ;
    public final CalParser.portDecl_return portDecl() throws RecognitionException {
        CalParser.portDecl_return retval = new CalParser.portDecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MULTI79=null;
        CalParser.typeAndId_return typeAndId80 = null;


        Object MULTI79_tree=null;
        RewriteRuleTokenStream stream_MULTI=new RewriteRuleTokenStream(adaptor,"token MULTI");
        RewriteRuleSubtreeStream stream_typeAndId=new RewriteRuleSubtreeStream(adaptor,"rule typeAndId");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:163:9: ( ( MULTI )? typeAndId -> ^( PortDecl typeAndId ) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:163:11: ( MULTI )? typeAndId
            {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:163:11: ( MULTI )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==MULTI) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:163:11: MULTI
                    {
                    MULTI79=(Token)match(input,MULTI,FOLLOW_MULTI_in_portDecl910);  
                    stream_MULTI.add(MULTI79);


                    }
                    break;

            }

            pushFollow(FOLLOW_typeAndId_in_portDecl913);
            typeAndId80=typeAndId();

            state._fsp--;

            stream_typeAndId.add(typeAndId80.getTree());


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
            // 163:28: -> ^( PortDecl typeAndId )
            {
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:163:31: ^( PortDecl typeAndId )
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:165:1: portDecls : portDecl ( COMMA portDecl )* -> ( portDecl )+ ;
    public final CalParser.portDecls_return portDecls() throws RecognitionException {
        CalParser.portDecls_return retval = new CalParser.portDecls_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA82=null;
        CalParser.portDecl_return portDecl81 = null;

        CalParser.portDecl_return portDecl83 = null;


        Object COMMA82_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleSubtreeStream stream_portDecl=new RewriteRuleSubtreeStream(adaptor,"rule portDecl");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:165:10: ( portDecl ( COMMA portDecl )* -> ( portDecl )+ )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:165:12: portDecl ( COMMA portDecl )*
            {
            pushFollow(FOLLOW_portDecl_in_portDecls928);
            portDecl81=portDecl();

            state._fsp--;

            stream_portDecl.add(portDecl81.getTree());
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:165:21: ( COMMA portDecl )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==COMMA) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:165:22: COMMA portDecl
            	    {
            	    COMMA82=(Token)match(input,COMMA,FOLLOW_COMMA_in_portDecls931);  
            	    stream_COMMA.add(COMMA82);

            	    pushFollow(FOLLOW_portDecl_in_portDecls933);
            	    portDecl83=portDecl();

            	    state._fsp--;

            	    stream_portDecl.add(portDecl83.getTree());

            	    }
            	    break;

            	default :
            	    break loop28;
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
            // 165:39: -> ( portDecl )+
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:170:1: mainParameter : typeAndId EOF -> ^( Parameter typeAndId ) ;
    public final CalParser.mainParameter_return mainParameter() throws RecognitionException {
        CalParser.mainParameter_return retval = new CalParser.mainParameter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EOF85=null;
        CalParser.typeAndId_return typeAndId84 = null;


        Object EOF85_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_typeAndId=new RewriteRuleSubtreeStream(adaptor,"rule typeAndId");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:170:14: ( typeAndId EOF -> ^( Parameter typeAndId ) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:170:16: typeAndId EOF
            {
            pushFollow(FOLLOW_typeAndId_in_mainParameter951);
            typeAndId84=typeAndId();

            state._fsp--;

            stream_typeAndId.add(typeAndId84.getTree());
            EOF85=(Token)match(input,EOF,FOLLOW_EOF_in_mainParameter953);  
            stream_EOF.add(EOF85);



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
            // 170:30: -> ^( Parameter typeAndId )
            {
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:170:33: ^( Parameter typeAndId )
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:172:1: typeAndId : typeName= QID ( ( typeRest )? varName= QID -> ^( Type ^( Var $typeName) ( typeRest )? ) ^( Var $varName) | -> ^( Var $typeName) ) ;
    public final CalParser.typeAndId_return typeAndId() throws RecognitionException {
        CalParser.typeAndId_return retval = new CalParser.typeAndId_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token typeName=null;
        Token varName=null;
        CalParser.typeRest_return typeRest86 = null;


        Object typeName_tree=null;
        Object varName_tree=null;
        RewriteRuleTokenStream stream_QID=new RewriteRuleTokenStream(adaptor,"token QID");
        RewriteRuleSubtreeStream stream_typeRest=new RewriteRuleSubtreeStream(adaptor,"rule typeRest");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:172:10: (typeName= QID ( ( typeRest )? varName= QID -> ^( Type ^( Var $typeName) ( typeRest )? ) ^( Var $varName) | -> ^( Var $typeName) ) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:172:12: typeName= QID ( ( typeRest )? varName= QID -> ^( Type ^( Var $typeName) ( typeRest )? ) ^( Var $varName) | -> ^( Var $typeName) )
            {
            typeName=(Token)match(input,QID,FOLLOW_QID_in_typeAndId970);  
            stream_QID.add(typeName);

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:173:3: ( ( typeRest )? varName= QID -> ^( Type ^( Var $typeName) ( typeRest )? ) ^( Var $varName) | -> ^( Var $typeName) )
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( ((LA30_0>=QID && LA30_0<=LBRACKET)||LA30_0==LPAREN) ) {
                alt30=1;
            }
            else if ( (LA30_0==EOF||(LA30_0>=RPAREN && LA30_0<=COLON)||LA30_0==DOUBLE_EQUAL_ARROW||(LA30_0>=EQ && LA30_0<=SEMICOLON)||LA30_0==COMMA) ) {
                alt30=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }
            switch (alt30) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:173:4: ( typeRest )? varName= QID
                    {
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:173:4: ( typeRest )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==LBRACKET||LA29_0==LPAREN) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:173:4: typeRest
                            {
                            pushFollow(FOLLOW_typeRest_in_typeAndId975);
                            typeRest86=typeRest();

                            state._fsp--;

                            stream_typeRest.add(typeRest86.getTree());

                            }
                            break;

                    }

                    varName=(Token)match(input,QID,FOLLOW_QID_in_typeAndId980);  
                    stream_QID.add(varName);



                    // AST REWRITE
                    // elements: typeName, typeRest, varName
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
                    // 173:26: -> ^( Type ^( Var $typeName) ( typeRest )? ) ^( Var $varName)
                    {
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:173:29: ^( Type ^( Var $typeName) ( typeRest )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Type, "Type"), root_1);

                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:173:36: ^( Var $typeName)
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_2);

                        adaptor.addChild(root_2, stream_typeName.nextNode());

                        adaptor.addChild(root_1, root_2);
                        }
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:173:53: ( typeRest )?
                        if ( stream_typeRest.hasNext() ) {
                            adaptor.addChild(root_1, stream_typeRest.nextTree());

                        }
                        stream_typeRest.reset();

                        adaptor.addChild(root_0, root_1);
                        }
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:173:64: ^( Var $varName)
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:174:5: 
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
                    // 174:5: -> ^( Var $typeName)
                    {
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:174:8: ^( Var $typeName)
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:176:1: type : QID ( typeRest )? -> ^( Type ^( Var QID ) ( typeRest )? ) ;
    public final CalParser.type_return type() throws RecognitionException {
        CalParser.type_return retval = new CalParser.type_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token QID87=null;
        CalParser.typeRest_return typeRest88 = null;


        Object QID87_tree=null;
        RewriteRuleTokenStream stream_QID=new RewriteRuleTokenStream(adaptor,"token QID");
        RewriteRuleSubtreeStream stream_typeRest=new RewriteRuleSubtreeStream(adaptor,"rule typeRest");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:176:5: ( QID ( typeRest )? -> ^( Type ^( Var QID ) ( typeRest )? ) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:176:7: QID ( typeRest )?
            {
            QID87=(Token)match(input,QID,FOLLOW_QID_in_type1024);  
            stream_QID.add(QID87);

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:176:11: ( typeRest )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==LBRACKET||LA31_0==LPAREN) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:176:11: typeRest
                    {
                    pushFollow(FOLLOW_typeRest_in_type1026);
                    typeRest88=typeRest();

                    state._fsp--;

                    stream_typeRest.add(typeRest88.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: QID, typeRest
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 176:21: -> ^( Type ^( Var QID ) ( typeRest )? )
            {
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:176:24: ^( Type ^( Var QID ) ( typeRest )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(Type, "Type"), root_1);

                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:176:31: ^( Var QID )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(Var, "Var"), root_2);

                adaptor.addChild(root_2, stream_QID.nextNode());

                adaptor.addChild(root_1, root_2);
                }
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:176:42: ( typeRest )?
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:178:1: typeRest : ( LBRACKET ( typePars )? RBRACKET -> ( typePars )? | LPAREN ( typeAttrs )? RPAREN -> ( typeAttrs )? );
    public final CalParser.typeRest_return typeRest() throws RecognitionException {
        CalParser.typeRest_return retval = new CalParser.typeRest_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token LBRACKET89=null;
        Token RBRACKET91=null;
        Token LPAREN92=null;
        Token RPAREN94=null;
        CalParser.typePars_return typePars90 = null;

        CalParser.typeAttrs_return typeAttrs93 = null;


        Object LBRACKET89_tree=null;
        Object RBRACKET91_tree=null;
        Object LPAREN92_tree=null;
        Object RPAREN94_tree=null;
        RewriteRuleTokenStream stream_LBRACKET=new RewriteRuleTokenStream(adaptor,"token LBRACKET");
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_RBRACKET=new RewriteRuleTokenStream(adaptor,"token RBRACKET");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_typePars=new RewriteRuleSubtreeStream(adaptor,"rule typePars");
        RewriteRuleSubtreeStream stream_typeAttrs=new RewriteRuleSubtreeStream(adaptor,"rule typeAttrs");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:178:9: ( LBRACKET ( typePars )? RBRACKET -> ( typePars )? | LPAREN ( typeAttrs )? RPAREN -> ( typeAttrs )? )
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==LBRACKET) ) {
                alt34=1;
            }
            else if ( (LA34_0==LPAREN) ) {
                alt34=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 34, 0, input);

                throw nvae;
            }
            switch (alt34) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:178:11: LBRACKET ( typePars )? RBRACKET
                    {
                    LBRACKET89=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_typeRest1049);  
                    stream_LBRACKET.add(LBRACKET89);

                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:178:20: ( typePars )?
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==QID) ) {
                        alt32=1;
                    }
                    switch (alt32) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:178:20: typePars
                            {
                            pushFollow(FOLLOW_typePars_in_typeRest1051);
                            typePars90=typePars();

                            state._fsp--;

                            stream_typePars.add(typePars90.getTree());

                            }
                            break;

                    }

                    RBRACKET91=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_typeRest1054);  
                    stream_RBRACKET.add(RBRACKET91);



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
                    // 178:39: -> ( typePars )?
                    {
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:178:42: ( typePars )?
                        if ( stream_typePars.hasNext() ) {
                            adaptor.addChild(root_0, stream_typePars.nextTree());

                        }
                        stream_typePars.reset();

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:179:5: LPAREN ( typeAttrs )? RPAREN
                    {
                    LPAREN92=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_typeRest1065);  
                    stream_LPAREN.add(LPAREN92);

                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:179:12: ( typeAttrs )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==QID) ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:179:12: typeAttrs
                            {
                            pushFollow(FOLLOW_typeAttrs_in_typeRest1067);
                            typeAttrs93=typeAttrs();

                            state._fsp--;

                            stream_typeAttrs.add(typeAttrs93.getTree());

                            }
                            break;

                    }

                    RPAREN94=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_typeRest1070);  
                    stream_RPAREN.add(RPAREN94);



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
                    // 179:30: -> ( typeAttrs )?
                    {
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:179:33: ( typeAttrs )?
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:181:1: typeAttrs : typeAttr ( COMMA typeAttr )* -> ( typeAttr )+ ;
    public final CalParser.typeAttrs_return typeAttrs() throws RecognitionException {
        CalParser.typeAttrs_return retval = new CalParser.typeAttrs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA96=null;
        CalParser.typeAttr_return typeAttr95 = null;

        CalParser.typeAttr_return typeAttr97 = null;


        Object COMMA96_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleSubtreeStream stream_typeAttr=new RewriteRuleSubtreeStream(adaptor,"rule typeAttr");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:181:10: ( typeAttr ( COMMA typeAttr )* -> ( typeAttr )+ )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:181:12: typeAttr ( COMMA typeAttr )*
            {
            pushFollow(FOLLOW_typeAttr_in_typeAttrs1082);
            typeAttr95=typeAttr();

            state._fsp--;

            stream_typeAttr.add(typeAttr95.getTree());
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:181:21: ( COMMA typeAttr )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==COMMA) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:181:22: COMMA typeAttr
            	    {
            	    COMMA96=(Token)match(input,COMMA,FOLLOW_COMMA_in_typeAttrs1085);  
            	    stream_COMMA.add(COMMA96);

            	    pushFollow(FOLLOW_typeAttr_in_typeAttrs1087);
            	    typeAttr97=typeAttr();

            	    state._fsp--;

            	    stream_typeAttr.add(typeAttr97.getTree());

            	    }
            	    break;

            	default :
            	    break loop35;
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
            // 181:39: -> ( typeAttr )+
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:183:1: typeAttr : QID typeAttrRest -> typeAttrRest ;
    public final CalParser.typeAttr_return typeAttr() throws RecognitionException {
        CalParser.typeAttr_return retval = new CalParser.typeAttr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token QID98=null;
        CalParser.typeAttrRest_return typeAttrRest99 = null;


        Object QID98_tree=null;
        RewriteRuleTokenStream stream_QID=new RewriteRuleTokenStream(adaptor,"token QID");
        RewriteRuleSubtreeStream stream_typeAttrRest=new RewriteRuleSubtreeStream(adaptor,"rule typeAttrRest");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:183:9: ( QID typeAttrRest -> typeAttrRest )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:183:11: QID typeAttrRest
            {
            QID98=(Token)match(input,QID,FOLLOW_QID_in_typeAttr1101);  
            stream_QID.add(QID98);

            pushFollow(FOLLOW_typeAttrRest_in_typeAttr1103);
            typeAttrRest99=typeAttrRest();

            state._fsp--;

            stream_typeAttrRest.add(typeAttrRest99.getTree());


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
            // 183:28: -> typeAttrRest
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:185:1: typeAttrRest : ( COLON type -> ^( TypeAttr type ) | EQ expression -> ^( ExprAttr ^( Expression expression ) ) );
    public final CalParser.typeAttrRest_return typeAttrRest() throws RecognitionException {
        CalParser.typeAttrRest_return retval = new CalParser.typeAttrRest_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COLON100=null;
        Token EQ102=null;
        CalParser.type_return type101 = null;

        CalParser.expression_return expression103 = null;


        Object COLON100_tree=null;
        Object EQ102_tree=null;
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleTokenStream stream_EQ=new RewriteRuleTokenStream(adaptor,"token EQ");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:185:13: ( COLON type -> ^( TypeAttr type ) | EQ expression -> ^( ExprAttr ^( Expression expression ) ) )
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==COLON) ) {
                alt36=1;
            }
            else if ( (LA36_0==EQ) ) {
                alt36=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }
            switch (alt36) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:185:15: COLON type
                    {
                    COLON100=(Token)match(input,COLON,FOLLOW_COLON_in_typeAttrRest1114);  
                    stream_COLON.add(COLON100);

                    pushFollow(FOLLOW_type_in_typeAttrRest1116);
                    type101=type();

                    state._fsp--;

                    stream_type.add(type101.getTree());


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
                    // 185:26: -> ^( TypeAttr type )
                    {
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:185:29: ^( TypeAttr type )
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:186:3: EQ expression
                    {
                    EQ102=(Token)match(input,EQ,FOLLOW_EQ_in_typeAttrRest1128);  
                    stream_EQ.add(EQ102);

                    pushFollow(FOLLOW_expression_in_typeAttrRest1130);
                    expression103=expression();

                    state._fsp--;

                    stream_expression.add(expression103.getTree());


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
                    // 186:17: -> ^( ExprAttr ^( Expression expression ) )
                    {
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:186:20: ^( ExprAttr ^( Expression expression ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ExprAttr, "ExprAttr"), root_1);

                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:186:31: ^( Expression expression )
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:188:1: typePars : typePar ( COMMA typePar )* -> ( typePar )+ ;
    public final CalParser.typePars_return typePars() throws RecognitionException {
        CalParser.typePars_return retval = new CalParser.typePars_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA105=null;
        CalParser.typePar_return typePar104 = null;

        CalParser.typePar_return typePar106 = null;


        Object COMMA105_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleSubtreeStream stream_typePar=new RewriteRuleSubtreeStream(adaptor,"rule typePar");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:188:9: ( typePar ( COMMA typePar )* -> ( typePar )+ )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:188:11: typePar ( COMMA typePar )*
            {
            pushFollow(FOLLOW_typePar_in_typePars1149);
            typePar104=typePar();

            state._fsp--;

            stream_typePar.add(typePar104.getTree());
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:188:19: ( COMMA typePar )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==COMMA) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:188:20: COMMA typePar
            	    {
            	    COMMA105=(Token)match(input,COMMA,FOLLOW_COMMA_in_typePars1152);  
            	    stream_COMMA.add(COMMA105);

            	    pushFollow(FOLLOW_typePar_in_typePars1154);
            	    typePar106=typePar();

            	    state._fsp--;

            	    stream_typePar.add(typePar106.getTree());

            	    }
            	    break;

            	default :
            	    break loop37;
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
            // 188:36: -> ( typePar )+
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:190:1: typePar : QID ( LT type )? -> ^( TypePar QID ( type )? ) ;
    public final CalParser.typePar_return typePar() throws RecognitionException {
        CalParser.typePar_return retval = new CalParser.typePar_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token QID107=null;
        Token LT108=null;
        CalParser.type_return type109 = null;


        Object QID107_tree=null;
        Object LT108_tree=null;
        RewriteRuleTokenStream stream_LT=new RewriteRuleTokenStream(adaptor,"token LT");
        RewriteRuleTokenStream stream_QID=new RewriteRuleTokenStream(adaptor,"token QID");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:190:8: ( QID ( LT type )? -> ^( TypePar QID ( type )? ) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:190:10: QID ( LT type )?
            {
            QID107=(Token)match(input,QID,FOLLOW_QID_in_typePar1168);  
            stream_QID.add(QID107);

            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:190:14: ( LT type )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==LT) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:190:15: LT type
                    {
                    LT108=(Token)match(input,LT,FOLLOW_LT_in_typePar1171);  
                    stream_LT.add(LT108);

                    pushFollow(FOLLOW_type_in_typePar1173);
                    type109=type();

                    state._fsp--;

                    stream_type.add(type109.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: type, QID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 190:25: -> ^( TypePar QID ( type )? )
            {
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:190:28: ^( TypePar QID ( type )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TypePar, "TypePar"), root_1);

                adaptor.addChild(root_1, stream_QID.nextNode());
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:190:42: ( type )?
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:195:1: mainExpression : expression EOF -> ^( Expression expression ) ;
    public final CalParser.mainExpression_return mainExpression() throws RecognitionException {
        CalParser.mainExpression_return retval = new CalParser.mainExpression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EOF111=null;
        CalParser.expression_return expression110 = null;


        Object EOF111_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:195:15: ( expression EOF -> ^( Expression expression ) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:195:17: expression EOF
            {
            pushFollow(FOLLOW_expression_in_mainExpression1196);
            expression110=expression();

            state._fsp--;

            stream_expression.add(expression110.getTree());
            EOF111=(Token)match(input,EOF,FOLLOW_EOF_in_mainExpression1198);  
            stream_EOF.add(EOF111);



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
            // 195:32: -> ^( Expression expression )
            {
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:195:35: ^( Expression expression )
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:197:1: expression : factor ( binop factor )* ;
    public final CalParser.expression_return expression() throws RecognitionException {
        CalParser.expression_return retval = new CalParser.expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        CalParser.factor_return factor112 = null;

        CalParser.binop_return binop113 = null;

        CalParser.factor_return factor114 = null;



        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:197:11: ( factor ( binop factor )* )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:197:13: factor ( binop factor )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_factor_in_expression1213);
            factor112=factor();

            state._fsp--;

            adaptor.addChild(root_0, factor112.getTree());
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:197:20: ( binop factor )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==MINUS||(LA39_0>=PLUS && LA39_0<=XOR)) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:197:21: binop factor
            	    {
            	    pushFollow(FOLLOW_binop_in_expression1216);
            	    binop113=binop();

            	    state._fsp--;

            	    adaptor.addChild(root_0, binop113.getTree());
            	    pushFollow(FOLLOW_factor_in_expression1218);
            	    factor114=factor();

            	    state._fsp--;

            	    adaptor.addChild(root_0, factor114.getTree());

            	    }
            	    break;

            	default :
            	    break loop39;
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:199:1: unop : (op= MINUS | op= NOT ) -> ^( UnOp $op) ;
    public final CalParser.unop_return unop() throws RecognitionException {
        CalParser.unop_return retval = new CalParser.unop_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token op=null;

        Object op_tree=null;
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleTokenStream stream_MINUS=new RewriteRuleTokenStream(adaptor,"token MINUS");

        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:199:5: ( (op= MINUS | op= NOT ) -> ^( UnOp $op) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:199:7: (op= MINUS | op= NOT )
            {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:199:7: (op= MINUS | op= NOT )
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==MINUS) ) {
                alt40=1;
            }
            else if ( (LA40_0==NOT) ) {
                alt40=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;
            }
            switch (alt40) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:199:8: op= MINUS
                    {
                    op=(Token)match(input,MINUS,FOLLOW_MINUS_in_unop1230);  
                    stream_MINUS.add(op);


                    }
                    break;
                case 2 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:199:19: op= NOT
                    {
                    op=(Token)match(input,NOT,FOLLOW_NOT_in_unop1236);  
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
            // 199:27: -> ^( UnOp $op)
            {
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:199:30: ^( UnOp $op)
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:201:1: binop : (op= PLUS | op= MINUS | op= TIMES | op= DIV | op= XOR ) -> ^( BinOp $op) ;
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:201:6: ( (op= PLUS | op= MINUS | op= TIMES | op= DIV | op= XOR ) -> ^( BinOp $op) )
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:201:8: (op= PLUS | op= MINUS | op= TIMES | op= DIV | op= XOR )
            {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:201:8: (op= PLUS | op= MINUS | op= TIMES | op= DIV | op= XOR )
            int alt41=5;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt41=1;
                }
                break;
            case MINUS:
                {
                alt41=2;
                }
                break;
            case TIMES:
                {
                alt41=3;
                }
                break;
            case DIV:
                {
                alt41=4;
                }
                break;
            case XOR:
                {
                alt41=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 41, 0, input);

                throw nvae;
            }

            switch (alt41) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:201:9: op= PLUS
                    {
                    op=(Token)match(input,PLUS,FOLLOW_PLUS_in_binop1256);  
                    stream_PLUS.add(op);


                    }
                    break;
                case 2 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:201:19: op= MINUS
                    {
                    op=(Token)match(input,MINUS,FOLLOW_MINUS_in_binop1262);  
                    stream_MINUS.add(op);


                    }
                    break;
                case 3 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:201:30: op= TIMES
                    {
                    op=(Token)match(input,TIMES,FOLLOW_TIMES_in_binop1268);  
                    stream_TIMES.add(op);


                    }
                    break;
                case 4 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:201:41: op= DIV
                    {
                    op=(Token)match(input,DIV,FOLLOW_DIV_in_binop1274);  
                    stream_DIV.add(op);


                    }
                    break;
                case 5 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:201:50: op= XOR
                    {
                    op=(Token)match(input,XOR,FOLLOW_XOR_in_binop1280);  
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
            // 201:58: -> ^( BinOp $op)
            {
                // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:201:61: ^( BinOp $op)
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:203:1: factor : ( term | unop term -> ^( Expression unop term ) );
    public final CalParser.factor_return factor() throws RecognitionException {
        CalParser.factor_return retval = new CalParser.factor_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        CalParser.term_return term115 = null;

        CalParser.unop_return unop116 = null;

        CalParser.term_return term117 = null;


        RewriteRuleSubtreeStream stream_unop=new RewriteRuleSubtreeStream(adaptor,"rule unop");
        RewriteRuleSubtreeStream stream_term=new RewriteRuleSubtreeStream(adaptor,"rule term");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:203:7: ( term | unop term -> ^( Expression unop term ) )
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( ((LA42_0>=QID && LA42_0<=LBRACKET)||LA42_0==LPAREN||(LA42_0>=FLOAT && LA42_0<=FALSE)) ) {
                alt42=1;
            }
            else if ( ((LA42_0>=MINUS && LA42_0<=NOT)) ) {
                alt42=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 42, 0, input);

                throw nvae;
            }
            switch (alt42) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:203:9: term
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_term_in_factor1297);
                    term115=term();

                    state._fsp--;

                    adaptor.addChild(root_0, term115.getTree());

                    }
                    break;
                case 2 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:204:3: unop term
                    {
                    pushFollow(FOLLOW_unop_in_factor1301);
                    unop116=unop();

                    state._fsp--;

                    stream_unop.add(unop116.getTree());
                    pushFollow(FOLLOW_term_in_factor1303);
                    term117=term();

                    state._fsp--;

                    stream_term.add(term117.getTree());


                    // AST REWRITE
                    // elements: term, unop
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 204:13: -> ^( Expression unop term )
                    {
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:204:16: ^( Expression unop term )
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:206:1: term : ( atom | LPAREN expression RPAREN -> ^( Expression expression ) );
    public final CalParser.term_return term() throws RecognitionException {
        CalParser.term_return retval = new CalParser.term_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token LPAREN119=null;
        Token RPAREN121=null;
        CalParser.atom_return atom118 = null;

        CalParser.expression_return expression120 = null;


        Object LPAREN119_tree=null;
        Object RPAREN121_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:206:5: ( atom | LPAREN expression RPAREN -> ^( Expression expression ) )
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( ((LA43_0>=QID && LA43_0<=LBRACKET)||(LA43_0>=FLOAT && LA43_0<=FALSE)) ) {
                alt43=1;
            }
            else if ( (LA43_0==LPAREN) ) {
                alt43=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 43, 0, input);

                throw nvae;
            }
            switch (alt43) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:206:7: atom
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_atom_in_term1320);
                    atom118=atom();

                    state._fsp--;

                    adaptor.addChild(root_0, atom118.getTree());

                    }
                    break;
                case 2 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:207:5: LPAREN expression RPAREN
                    {
                    LPAREN119=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_term1326);  
                    stream_LPAREN.add(LPAREN119);

                    pushFollow(FOLLOW_expression_in_term1328);
                    expression120=expression();

                    state._fsp--;

                    stream_expression.add(expression120.getTree());
                    RPAREN121=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_term1330);  
                    stream_RPAREN.add(RPAREN121);



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
                    // 207:30: -> ^( Expression expression )
                    {
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:207:33: ^( Expression expression )
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
    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:209:1: atom : ( QID -> ^( Var QID ) | FLOAT -> ^( Real FLOAT ) | INTEGER -> ^( Integer INTEGER ) | STRING -> ^( String STRING ) | TRUE -> ^( Boolean TRUE ) | FALSE -> ^( Boolean FALSE ) | LBRACKET ( expression ( COMMA expression )* )? RBRACKET -> ^( List ( expression )* ) );
    public final CalParser.atom_return atom() throws RecognitionException {
        CalParser.atom_return retval = new CalParser.atom_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token QID122=null;
        Token FLOAT123=null;
        Token INTEGER124=null;
        Token STRING125=null;
        Token TRUE126=null;
        Token FALSE127=null;
        Token LBRACKET128=null;
        Token COMMA130=null;
        Token RBRACKET132=null;
        CalParser.expression_return expression129 = null;

        CalParser.expression_return expression131 = null;


        Object QID122_tree=null;
        Object FLOAT123_tree=null;
        Object INTEGER124_tree=null;
        Object STRING125_tree=null;
        Object TRUE126_tree=null;
        Object FALSE127_tree=null;
        Object LBRACKET128_tree=null;
        Object COMMA130_tree=null;
        Object RBRACKET132_tree=null;
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
            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:209:5: ( QID -> ^( Var QID ) | FLOAT -> ^( Real FLOAT ) | INTEGER -> ^( Integer INTEGER ) | STRING -> ^( String STRING ) | TRUE -> ^( Boolean TRUE ) | FALSE -> ^( Boolean FALSE ) | LBRACKET ( expression ( COMMA expression )* )? RBRACKET -> ^( List ( expression )* ) )
            int alt46=7;
            switch ( input.LA(1) ) {
            case QID:
                {
                alt46=1;
                }
                break;
            case FLOAT:
                {
                alt46=2;
                }
                break;
            case INTEGER:
                {
                alt46=3;
                }
                break;
            case STRING:
                {
                alt46=4;
                }
                break;
            case TRUE:
                {
                alt46=5;
                }
                break;
            case FALSE:
                {
                alt46=6;
                }
                break;
            case LBRACKET:
                {
                alt46=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 46, 0, input);

                throw nvae;
            }

            switch (alt46) {
                case 1 :
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:209:7: QID
                    {
                    QID122=(Token)match(input,QID,FOLLOW_QID_in_atom1345);  
                    stream_QID.add(QID122);



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
                    // 209:11: -> ^( Var QID )
                    {
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:209:14: ^( Var QID )
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:210:3: FLOAT
                    {
                    FLOAT123=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_atom1357);  
                    stream_FLOAT.add(FLOAT123);



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
                    // 210:9: -> ^( Real FLOAT )
                    {
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:210:12: ^( Real FLOAT )
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:211:3: INTEGER
                    {
                    INTEGER124=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_atom1369);  
                    stream_INTEGER.add(INTEGER124);



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
                    // 211:11: -> ^( Integer INTEGER )
                    {
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:211:14: ^( Integer INTEGER )
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:212:3: STRING
                    {
                    STRING125=(Token)match(input,STRING,FOLLOW_STRING_in_atom1381);  
                    stream_STRING.add(STRING125);



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
                    // 212:10: -> ^( String STRING )
                    {
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:212:13: ^( String STRING )
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:213:3: TRUE
                    {
                    TRUE126=(Token)match(input,TRUE,FOLLOW_TRUE_in_atom1393);  
                    stream_TRUE.add(TRUE126);



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
                    // 213:8: -> ^( Boolean TRUE )
                    {
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:213:11: ^( Boolean TRUE )
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:214:3: FALSE
                    {
                    FALSE127=(Token)match(input,FALSE,FOLLOW_FALSE_in_atom1405);  
                    stream_FALSE.add(FALSE127);



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
                    // 214:9: -> ^( Boolean FALSE )
                    {
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:214:12: ^( Boolean FALSE )
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
                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:215:3: LBRACKET ( expression ( COMMA expression )* )? RBRACKET
                    {
                    LBRACKET128=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_atom1417);  
                    stream_LBRACKET.add(LBRACKET128);

                    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:215:12: ( expression ( COMMA expression )* )?
                    int alt45=2;
                    int LA45_0 = input.LA(1);

                    if ( ((LA45_0>=QID && LA45_0<=LBRACKET)||LA45_0==LPAREN||(LA45_0>=MINUS && LA45_0<=NOT)||(LA45_0>=FLOAT && LA45_0<=FALSE)) ) {
                        alt45=1;
                    }
                    switch (alt45) {
                        case 1 :
                            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:215:13: expression ( COMMA expression )*
                            {
                            pushFollow(FOLLOW_expression_in_atom1420);
                            expression129=expression();

                            state._fsp--;

                            stream_expression.add(expression129.getTree());
                            // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:215:24: ( COMMA expression )*
                            loop44:
                            do {
                                int alt44=2;
                                int LA44_0 = input.LA(1);

                                if ( (LA44_0==COMMA) ) {
                                    alt44=1;
                                }


                                switch (alt44) {
                            	case 1 :
                            	    // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:215:25: COMMA expression
                            	    {
                            	    COMMA130=(Token)match(input,COMMA,FOLLOW_COMMA_in_atom1423);  
                            	    stream_COMMA.add(COMMA130);

                            	    pushFollow(FOLLOW_expression_in_atom1425);
                            	    expression131=expression();

                            	    state._fsp--;

                            	    stream_expression.add(expression131.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop44;
                                }
                            } while (true);


                            }
                            break;

                    }

                    RBRACKET132=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_atom1431);  
                    stream_RBRACKET.add(RBRACKET132);



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
                    // 215:55: -> ^( List ( expression )* )
                    {
                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:215:58: ^( List ( expression )* )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(List, "List"), root_1);

                        // D:\\orcc\\trunk\\plugins\\net.sf.orcc.ui\\src\\net\\sf\\orcc\\ui\\editor\\Cal.g:215:65: ( expression )*
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
    public static final BitSet FOLLOW_RPAREN_in_network257 = new BitSet(new long[]{0x1000102000000000L});
    public static final BitSet FOLLOW_portSignature_in_network261 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_COLON_in_network263 = new BitSet(new long[]{0x0414280000000000L});
    public static final BitSet FOLLOW_oneImport_in_network267 = new BitSet(new long[]{0x0414280000000000L});
    public static final BitSet FOLLOW_varDeclSection_in_network270 = new BitSet(new long[]{0x0014080000000000L});
    public static final BitSet FOLLOW_entitySection_in_network275 = new BitSet(new long[]{0x0010080000000000L});
    public static final BitSet FOLLOW_structureSection_in_network278 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_END_in_network283 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_network285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inputPorts_in_portSignature324 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_DOUBLE_EQUAL_ARROW_in_portSignature326 = new BitSet(new long[]{0x1000002000000000L});
    public static final BitSet FOLLOW_outputPorts_in_portSignature328 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_portDecls_in_inputPorts341 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_portDecls_in_outputPorts366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_varDeclSection392 = new BitSet(new long[]{0x0000402000000000L});
    public static final BitSet FOLLOW_varDecl_in_varDeclSection394 = new BitSet(new long[]{0x0000402000000002L});
    public static final BitSet FOLLOW_MUTABLE_in_varDecl407 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typeAndId_in_varDecl410 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_EQ_in_varDecl416 = new BitSet(new long[]{0xC000016000000000L,0x00000000000001F0L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_varDecl420 = new BitSet(new long[]{0xC000016000000000L,0x00000000000001F0L});
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
    public static final BitSet FOLLOW_EQ_in_entityPar552 = new BitSet(new long[]{0xC000016000000000L,0x00000000000001F0L});
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
    public static final BitSet FOLLOW_EQ_in_attributeDecl715 = new BitSet(new long[]{0xC000016000000000L,0x00000000000001F0L});
    public static final BitSet FOLLOW_expression_in_attributeDecl717 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_attributeDecl719 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_attributeDecl742 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_type_in_attributeDecl744 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_attributeDecl746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oneImport_in_actor777 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_ACTOR_in_actor780 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_QID_in_actor782 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_LPAREN_in_actor786 = new BitSet(new long[]{0x0000022000000000L});
    public static final BitSet FOLLOW_parameters_in_actor788 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_RPAREN_in_actor791 = new BitSet(new long[]{0x1000102000000000L});
    public static final BitSet FOLLOW_portSignature_in_actor795 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_COLON_in_actor797 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x00000000007FFFFFL});
    public static final BitSet FOLLOW_EOF_in_actor802 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IMPORT_in_oneImport833 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_QID_WILDCARD_in_oneImport835 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_oneImport837 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAndId_in_parameter847 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_EQ_in_parameter852 = new BitSet(new long[]{0xC000016000000000L,0x00000000000001F0L});
    public static final BitSet FOLLOW_expression_in_parameter854 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parameter_in_parameters888 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_COMMA_in_parameters891 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_parameter_in_parameters893 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_MULTI_in_portDecl910 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typeAndId_in_portDecl913 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_portDecl_in_portDecls928 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_COMMA_in_portDecls931 = new BitSet(new long[]{0x1000002000000000L});
    public static final BitSet FOLLOW_portDecl_in_portDecls933 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_typeAndId_in_mainParameter951 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_mainParameter953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QID_in_typeAndId970 = new BitSet(new long[]{0x0000016000000002L});
    public static final BitSet FOLLOW_typeRest_in_typeAndId975 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_QID_in_typeAndId980 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QID_in_type1024 = new BitSet(new long[]{0x0000014000000002L});
    public static final BitSet FOLLOW_typeRest_in_type1026 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACKET_in_typeRest1049 = new BitSet(new long[]{0x000000A000000000L});
    public static final BitSet FOLLOW_typePars_in_typeRest1051 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_RBRACKET_in_typeRest1054 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_typeRest1065 = new BitSet(new long[]{0x0000022000000000L});
    public static final BitSet FOLLOW_typeAttrs_in_typeRest1067 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_RPAREN_in_typeRest1070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs1082 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_COMMA_in_typeAttrs1085 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typeAttr_in_typeAttrs1087 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_QID_in_typeAttr1101 = new BitSet(new long[]{0x0000840000000000L});
    public static final BitSet FOLLOW_typeAttrRest_in_typeAttr1103 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_typeAttrRest1114 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_type_in_typeAttrRest1116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EQ_in_typeAttrRest1128 = new BitSet(new long[]{0xC000016000000000L,0x00000000000001F0L});
    public static final BitSet FOLLOW_expression_in_typeAttrRest1130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typePar_in_typePars1149 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_COMMA_in_typePars1152 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_typePar_in_typePars1154 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_QID_in_typePar1168 = new BitSet(new long[]{0x2000000000000002L});
    public static final BitSet FOLLOW_LT_in_typePar1171 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_type_in_typePar1173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_mainExpression1196 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_mainExpression1198 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_factor_in_expression1213 = new BitSet(new long[]{0x4000000000000002L,0x000000000000000FL});
    public static final BitSet FOLLOW_binop_in_expression1216 = new BitSet(new long[]{0xC000016000000000L,0x00000000000001F0L});
    public static final BitSet FOLLOW_factor_in_expression1218 = new BitSet(new long[]{0x4000000000000002L,0x000000000000000FL});
    public static final BitSet FOLLOW_MINUS_in_unop1230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_unop1236 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_in_binop1256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_binop1262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TIMES_in_binop1268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DIV_in_binop1274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_XOR_in_binop1280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_factor1297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unop_in_factor1301 = new BitSet(new long[]{0x0000016000000000L,0x00000000000001F0L});
    public static final BitSet FOLLOW_term_in_factor1303 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atom_in_term1320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_term1326 = new BitSet(new long[]{0xC000016000000000L,0x00000000000001F0L});
    public static final BitSet FOLLOW_expression_in_term1328 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_RPAREN_in_term1330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QID_in_atom1345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_atom1357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_atom1369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_atom1381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_atom1393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_atom1405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACKET_in_atom1417 = new BitSet(new long[]{0xC00001E000000000L,0x00000000000001F0L});
    public static final BitSet FOLLOW_expression_in_atom1420 = new BitSet(new long[]{0x0008008000000000L});
    public static final BitSet FOLLOW_COMMA_in_atom1423 = new BitSet(new long[]{0xC000016000000000L,0x00000000000001F0L});
    public static final BitSet FOLLOW_expression_in_atom1425 = new BitSet(new long[]{0x0008008000000000L});
    public static final BitSet FOLLOW_RBRACKET_in_atom1431 = new BitSet(new long[]{0x0000000000000002L});

}