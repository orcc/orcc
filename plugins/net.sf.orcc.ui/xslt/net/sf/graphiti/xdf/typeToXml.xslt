<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">

    <xsl:import href="exprToXml.xslt"/>

    <xsl:output indent="yes" method="xml"/>

    <xsl:template match="text()"/>

    <!-- Type -->
    <xsl:template match="Type">
        <Type name="{Var/text()}">
            <xsl:apply-templates select="child::node()[name() != 'Var']"/>
        </Type>
    </xsl:template>

    <xsl:template match="TypeAttr">
        <Entry name="type" kind="Type">
            <xsl:apply-templates/>
        </Entry>
    </xsl:template>

    <xsl:template match="ExprAttr">
        <Entry name="size" kind="Expr">
            <xsl:apply-templates/>
        </Entry>
    </xsl:template>

</xsl:stylesheet>
