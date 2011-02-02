<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">

    <xsl:import href="exprToXml.xslt"/>
    <xsl:import href="typeToXml.xslt"/>

    <xsl:output indent="yes" method="xml"/>

    <xsl:template match="text()"/>

    <!-- XDF -->
    <xsl:template match="Actor">
        <Actor name="{Name/text()}">
            <xsl:apply-templates select="Parameter"/>
            <xsl:apply-templates select="Inputs">
                <xsl:with-param name="kind">Input</xsl:with-param>
            </xsl:apply-templates>
            <xsl:apply-templates select="Outputs">
                <xsl:with-param name="kind">Output</xsl:with-param>
            </xsl:apply-templates>
        </Actor>
    </xsl:template>

    <!-- Parameter -->
    <xsl:template match="Parameter">
        <Decl kind="Parameter" name="{Var/text()}">
            <xsl:apply-templates select="Type"/>
        </Decl>
    </xsl:template>

    <!-- Port -->
    <xsl:template match="PortDecl">
        <xsl:param name="kind"/>
        <Port kind="{$kind}" name="{Var/text()}">
            <xsl:apply-templates select="Type"/>
        </Port>
    </xsl:template>

</xsl:stylesheet>
