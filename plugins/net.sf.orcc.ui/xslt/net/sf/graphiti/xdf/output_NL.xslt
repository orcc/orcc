<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:grammar="java:net.sf.graphiti.io.GrammarTransformer"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">

    <xsl:import href="exprToString.xslt"/>

    <xsl:output indent="yes" method="text"/>

    <xsl:template match="text()"/>

    <xsl:param name="path"/>

    <!-- Top-level: graph -> XNL -->
    <xsl:template match="Network">
        <xsl:element name="text">
            <xsl:text>network </xsl:text>
            <xsl:value-of select="QID/@name"/>

            <xsl:call-template name="parameters"/>
            <xsl:call-template name="ports"/>
            <xsl:call-template name="variables"/>
            <xsl:call-template name="entities"/>
            <xsl:call-template name="structure"/>
            <xsl:text>
end
</xsl:text>
        </xsl:element>
    </xsl:template>

    <!-- variables -->
    <xsl:template name="variables">
		<xsl:for-each select="Decl[@kind = 'Variable']">
		<xsl:if test="position() = 1">
        <xsl:text>var</xsl:text>
            <xsl:text>
    </xsl:text>
		</xsl:if>
        <xsl:value-of select="@name"/>
            <xsl:text> = </xsl:text>
            <xsl:apply-templates select="Expr"/>
                <xsl:text>;</xsl:text>
            <xsl:text>
    </xsl:text>
        </xsl:for-each> 
        <xsl:text>
</xsl:text>
    </xsl:template>

    <!-- parameters -->
    <xsl:template name="parameters">
        <xsl:text> (</xsl:text>
        <xsl:variable name="parameters" select="for $a in Decl[@kind = 'Parameter'] return $a/@name"/>
        <xsl:value-of select="string-join($parameters, ', ')"/>
        <xsl:text>)
</xsl:text>
    </xsl:template>

    <!-- ports -->
    <xsl:template name="ports">
        <xsl:variable name="inputs" select="for $a in Port[@kind = 'Input'] return $a/@name"/>
        <xsl:variable name="outputs" select="for $a in Port[@kind = 'Output'] return $a/@name"/>

        <xsl:value-of select="string-join($inputs, ', ')"/>
        <xsl:text> ==&gt; </xsl:text>
        <xsl:value-of select="string-join($outputs, ', ')"/>
        <xsl:text> :

</xsl:text>
    </xsl:template>

    <!-- entities -->
    <xsl:template name="entities">
        <xsl:if test="EntityDecl">
        <xsl:text>entities

</xsl:text>
        </xsl:if>
        <xsl:apply-templates select="EntityDecl"/>
    </xsl:template>

    <!-- EntityDecl -->
    <xsl:template match="EntityDecl">
        <xsl:text>    </xsl:text>
        <xsl:value-of select="@name"/>
        <xsl:text> = </xsl:text>
        <xsl:value-of select="EntityExpr/@name"/>
        <xsl:text>(</xsl:text>
        <xsl:for-each select="EntityExpr/Arg">
            <xsl:text>
        </xsl:text>
            <xsl:value-of select="@name"/>
            <xsl:text> = </xsl:text>
            <xsl:apply-templates select="Expr"/>
            <xsl:if test="position() != last()">
                <xsl:text>,</xsl:text>
            </xsl:if>
        </xsl:for-each>
        <xsl:text>
    );

</xsl:text>
    </xsl:template>

    <!-- structure -->
    <xsl:template name="structure">
        <xsl:if test="StructureStmt">
        <xsl:text>structure

</xsl:text>
        </xsl:if>
        <xsl:apply-templates select="StructureStmt"/>
    </xsl:template>
    
    <!-- StructureStmt -->
    <xsl:template match="StructureStmt">
        <xsl:text>    </xsl:text>
        <xsl:apply-templates select="PortSpec[1]"/>
        <xsl:text> --&gt; </xsl:text>
        <xsl:apply-templates select="PortSpec[2]"/>
        <xsl:text>;
</xsl:text>
    </xsl:template>
    
    <!-- PortSpec -->
    <xsl:template match="PortSpec[@kind = 'Entity']">
        <xsl:value-of select="concat(EntityRef/@name, '.', PortRef/@name)"/>
    </xsl:template>
    
    <!-- PortSpec -->
    <xsl:template match="PortSpec[@kind = 'Local']">
        <xsl:value-of select="PortRef/@name"/>
    </xsl:template>

</xsl:stylesheet>
