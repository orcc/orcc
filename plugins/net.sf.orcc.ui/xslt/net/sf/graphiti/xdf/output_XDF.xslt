<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:grammar="java:net.sf.graphiti.io.GrammarTransformer"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">

    <xsl:import href="exprToXml.xslt"/>
    <xsl:import href="typeToXml.xslt"/>

    <xsl:output indent="yes" method="xml"/>

    <xsl:template match="text()"/>

    <!-- writes the layout in a file that has the same name as the target document,
        except with .layout extension. -->
    <xsl:param name="path"/>
    <xsl:variable name="file" select="replace($path, '(.+)[.].+', '$1.layout')"/>
    
    <xsl:variable name="grammarId" select="'net.sf.graphiti.grammar.cal'"/>

    <!-- Top-level: graph -> XDF -->
    <xsl:template match="graph">

        <!-- layout information -->
        <xsl:result-document href="file:///{$file}" method="xml" indent="yes">
            <xsl:element name="layout">
                <xsl:element name="vertices">
                    <xsl:for-each select="vertices/vertex">
                        <xsl:element name="vertex">
                            <xsl:attribute name="id"
                                select="parameters/parameter[@name = 'id']/@value"/>
                            <xsl:attribute name="x" select="@x"/>
                            <xsl:attribute name="y" select="@y"/>
                        </xsl:element>
                    </xsl:for-each>
                </xsl:element>
            </xsl:element>
        </xsl:result-document>

        <!-- graph -->
        <xsl:element name="XDF">
            <xsl:attribute name="name" select="parameters/parameter[@name = 'id']/@value"/>
			<xsl:comment> ************************************** </xsl:comment>
			<xsl:comment> Input ports of the Graph               </xsl:comment>
			<xsl:comment> ************************************** </xsl:comment>
            <xsl:apply-templates select="vertices/vertex[@type = 'Input port']"/>
			<xsl:comment> ************************************** </xsl:comment>
			<xsl:comment> Output ports of the Graph              </xsl:comment>
			<xsl:comment> ************************************** </xsl:comment>
            <xsl:apply-templates select="vertices/vertex[@type = 'Output port']"/>
			<xsl:comment> ************************************** </xsl:comment>
			<xsl:comment> Variables and Parameters of the Graph  </xsl:comment>
			<xsl:comment> ************************************** </xsl:comment>
            <xsl:apply-templates select="parameters/parameter[@name != 'id']"/>
			<xsl:comment> ************************************** </xsl:comment>
			<xsl:comment> Instances of the Graph                 </xsl:comment>
			<xsl:comment> ************************************** </xsl:comment>
            <xsl:apply-templates select="vertices/vertex[@type = 'Instance']"/>
			<xsl:comment> ************************************** </xsl:comment>
			<xsl:comment> Connections of the Graph               </xsl:comment>
			<xsl:comment> ************************************** </xsl:comment>
            <xsl:apply-templates select="edges/edge"/>
        </xsl:element>
    </xsl:template>

    <!-- This templates takes a parameter "str", parses it as a variable declaration,
    optionally prefixed with a type. It returns a "name" attribute as well as a Type
    child if the declaration contains a type. -->
    <xsl:template name="typeAndId">
        <xsl:param name="str"/>
        <xsl:variable name="gt" select="grammar:new($grammarId, 'mainParameter')"/>
        <xsl:variable name="tree" select="grammar:parseString($gt, $str)"/>
        <xsl:attribute name="name" select="$tree/Var/text()"/>
        <xsl:if test="$tree/Type">
            <xsl:apply-templates select="$tree/Type"/>
        </xsl:if>
    </xsl:template>

    <!-- Parameter declarations -->
    <xsl:template match="parameter[@name = 'network parameter']/element">
        <xsl:element name="Decl">
            <xsl:attribute name="kind">Param</xsl:attribute>
            <xsl:call-template name="typeAndId">
                <xsl:with-param name="str" select="@value"/>
            </xsl:call-template>
        </xsl:element>
    </xsl:template>

    <!-- Variable declarations -->
    <xsl:template match="parameter[@name = 'network variable declaration']/entry">
        <xsl:element name="Decl">
            <xsl:attribute name="kind">Variable</xsl:attribute>
            <xsl:call-template name="typeAndId">
                <xsl:with-param name="str" select="@key"/>
            </xsl:call-template>

            <xsl:variable name="gt" select="grammar:new($grammarId, 'mainExpression')"/>
            <xsl:variable name="tree" select="grammar:parseString($gt, @value)"/>
            <xsl:apply-templates select="$tree"/>
        </xsl:element>
    </xsl:template>

    <!-- Input/output ports -->
    <xsl:template match="vertex[@type='Input port']">
        <xsl:element name="Port">
            <xsl:attribute name="kind">Input</xsl:attribute>
            <xsl:variable name="id" select="parameters/parameter[@name = 'id']/@value"/>
            <xsl:variable name="type" select="parameters/parameter[@name = 'port type']/@value"/>
            <xsl:call-template name="typeAndId">
                <xsl:with-param name="str" select="concat($type, ' ', $id)"/>
            </xsl:call-template>
        </xsl:element>
    </xsl:template>

    <xsl:template match="vertex[@type='Output port']">
        <xsl:element name="Port">
            <xsl:attribute name="kind">Output</xsl:attribute>
            <xsl:variable name="id" select="parameters/parameter[@name = 'id']/@value"/>
            <xsl:variable name="type" select="parameters/parameter[@name = 'port type']/@value"/>
            <xsl:call-template name="typeAndId">
                <xsl:with-param name="str" select="concat($type, ' ', $id)"/>
            </xsl:call-template>
        </xsl:element>
    </xsl:template>

    <!-- Instances -->
    <xsl:template match="vertex[@type='Instance']">
        <xsl:element name="Instance">
            <xsl:attribute name="id" select="parameters/parameter[@name = 'id']/@value"/>
			<xsl:comment> ************************* </xsl:comment>
			<xsl:comment> FU/Network refinement     </xsl:comment>
			<xsl:comment> ************************* </xsl:comment>
            <xsl:element name="Class">
                <xsl:attribute name="name"
                    select="parameters/parameter[@name = 'refinement']/@value"/>
            </xsl:element>
			<xsl:comment> ************************* </xsl:comment>
			<xsl:comment> FU/Network Parameter      </xsl:comment>
			<xsl:comment> ************************* </xsl:comment>
            <xsl:apply-templates select="parameters/parameter[@name = 'instance parameter']"/>
            <xsl:variable name="value" select="parameters/parameter[@name = 'part name']/@value"/>
            <xsl:if test="$value != ''">
                <xsl:element name="Attribute">
                    <xsl:attribute name="kind" select="'Value'"/>
                    <xsl:attribute name="name" select="'partName'"/>
                    <xsl:variable name="gt" select="grammar:new($grammarId, 'mainExpression')"/>
                    <xsl:variable name="tree" select="grammar:parseString($gt, $value)"/>
                    <xsl:apply-templates select="$tree"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="parameters/parameter[@name = 'skip']/@value = 'true'">
                <xsl:element name="Attribute">
                    <xsl:attribute name="kind" select="'Flag'"/>
                    <xsl:attribute name="name" select="'skip'"/>
                </xsl:element>
            </xsl:if>
        </xsl:element>
    </xsl:template>

    <!-- Parameter instantiations -->
    <xsl:template match="parameter[@name = 'instance parameter']/entry">
        <xsl:element name="Parameter">
            <xsl:attribute name="name" select="@key"/>
            <xsl:variable name="gt" select="grammar:new($grammarId, 'mainExpression')"/>
            <xsl:variable name="tree" select="grammar:parseString($gt, @value)"/>
            <xsl:apply-templates select="$tree"/>
        </xsl:element>
    </xsl:template>

    <!-- Connections -->
    <xsl:template match="edge">
        <xsl:element name="Connection">
            <xsl:choose>
                <xsl:when test="parameters/parameter[@name = 'source port']/@value != ''">
                    <xsl:attribute name="src" select="@source"/>
                    <xsl:attribute name="src-port"
                        select="parameters/parameter[@name = 'source port']/@value"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:attribute name="src"/>
                    <xsl:attribute name="src-port" select="@source"/>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:choose>
                <xsl:when test="parameters/parameter[@name = 'target port']/@value != ''">
                    <xsl:attribute name="dst" select="@target"/>
                    <xsl:attribute name="dst-port"
                        select="parameters/parameter[@name = 'target port']/@value"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:attribute name="dst"/>
                    <xsl:attribute name="dst-port" select="@target"/>
                </xsl:otherwise>
            </xsl:choose>

            <xsl:variable name="value" select="parameters/parameter[@name = 'buffer size']/@value"/>
            <xsl:if test="$value != ''">
                <xsl:element name="Attribute">
                    <xsl:attribute name="kind" select="'Value'"/>
                    <xsl:attribute name="name" select="'bufferSize'"/>
                    <xsl:variable name="gt" select="grammar:new($grammarId, 'mainExpression')"/>
                    <xsl:variable name="tree" select="grammar:parseString($gt, $value)"/>
                    <xsl:apply-templates select="$tree"/>
                </xsl:element>
            </xsl:if>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
