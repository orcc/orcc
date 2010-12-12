<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">

    <xsl:import href="exprToXml.xslt"/>
    <xsl:import href="typeToXml.xslt"/>

    <xsl:output indent="yes" method="xml"/>

    <xsl:template match="text()"/>

    <!-- XDF -->
    <xsl:template match="Network">
        <XDF>
            <xsl:apply-templates select="QualifiedId"/>
            <xsl:apply-templates select="Parameter"/>
            <xsl:apply-templates select="Inputs">
                <xsl:with-param name="kind">Input</xsl:with-param>
            </xsl:apply-templates>
            <xsl:apply-templates select="Outputs">
                <xsl:with-param name="kind">Output</xsl:with-param>
            </xsl:apply-templates>

            <xsl:apply-templates select="VarDecl"/>
            <xsl:apply-templates select="EntityDecl"/>
            <xsl:apply-templates select="StructureStmt"/>
        </XDF>
    </xsl:template>

    <!-- QID -->
    <xsl:template match="QualifiedId">
        <xsl:attribute name="name" select="string-join((Var | Dot)/text(), '')"/>
    </xsl:template>

    <!-- Parameter -->
    <xsl:template match="Parameter">
        <Decl kind="Param" name="{Var/text()}">
            <xsl:apply-templates select="Type"/>
        </Decl>
    </xsl:template>

    <!-- Variable -->
    <xsl:template match="VarDecl">
        <Decl kind="Variable" name="{Var/text()}">
            <xsl:apply-templates select="Type"/>
            <xsl:apply-templates select="Expression"/>
        </Decl>
    </xsl:template>

    <!-- Port -->
    <xsl:template match="PortDecl">
        <xsl:param name="kind"/>
        <Port kind="{$kind}" name="{Var/text()}">
            <xsl:apply-templates select="Type"/>
        </Port>
    </xsl:template>

    <!-- EntityDecl -->
    <xsl:template match="EntityDecl">
        <Instance id="{Var/text()}">
            <xsl:apply-templates select="EntityExpr"/>
        </Instance>
    </xsl:template>

    <!-- EntityExpr -->
    <xsl:template match="EntityExpr">
        <Class name="{Var/text()}"/>
        <xsl:apply-templates select="EntityPar"/>
    </xsl:template>

    <!-- EntityPar -->
    <xsl:template match="EntityPar">
        <Parameter name="{Var/text()}">
            <xsl:apply-templates select="Expression"/>
        </Parameter>
    </xsl:template>

    <!-- StructureStmt -->
    <xsl:template match="StructureStmt">
        <Connection kind="Connection">
            <xsl:apply-templates select="Connector[1]">
                <xsl:with-param name="name" select="'src'"/>
            </xsl:apply-templates>
            <xsl:apply-templates select="Connector[2]">
                <xsl:with-param name="name" select="'dst'"/>
            </xsl:apply-templates>
            <xsl:apply-templates select="Attribute"/>
        </Connection>
    </xsl:template>

    <!-- Connector -->
    <xsl:template match="Connector">
        <xsl:param name="name"/>
        <xsl:choose>
            <xsl:when test="count(Var) = 1">
                <xsl:attribute name="{$name}" select="Var[1]/text()"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:attribute name="{$name}" select="Var[1]/text()"/>
                <xsl:attribute name="{concat($name, '-port')}" select="Var[2]/text()"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- Attribute -->
    <xsl:template match="Attribute">
        <Attribute name="{Var/text()}">
            <xsl:choose>
                <xsl:when test="Expression">
                    <xsl:attribute name="kind" select="'Value'"/>
                    <xsl:apply-templates select="Expression"/>
                </xsl:when>
                <xsl:when test="Type">
                    <xsl:attribute name="kind" select="'Type'"/>
                    <xsl:apply-templates select="Type"/>
                </xsl:when>
            </xsl:choose>
        </Attribute>
    </xsl:template>

</xsl:stylesheet>
