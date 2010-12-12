<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
    xmlns:g="http://net.sf.graphiti"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">

    <xsl:output indent="yes" method="xml"/>

    <xsl:template match="text()"/>
    
    <xsl:function name="g:parseEntry" as="xs:string">
        <xsl:param name="entry"/>
        <xsl:variable name="entryText">
            <xsl:apply-templates select="$entry"/>
        </xsl:variable>
        <xsl:value-of select="$entryText cast as xs:string"/>
    </xsl:function>

    <!-- Types -->
    <xsl:template match="Type">
        <xsl:value-of select="@name"/>
        <xsl:if test="Entry">
            <xsl:variable name="entries" select="for $e in Entry return g:parseEntry($e)"/>
            <xsl:text>(</xsl:text>
            <xsl:value-of select="string-join($entries, ', ')"/>
            <xsl:text>)</xsl:text>
        </xsl:if>
    </xsl:template>

    <xsl:template match="Entry[@name = 'type' and @kind='Type']">
        <xsl:text>type:</xsl:text>
        <xsl:apply-templates select="Type"/>
    </xsl:template>
    
    <xsl:template match="Entry[@name = 'size' and @kind = 'Expr']">
        <xsl:text>size=</xsl:text>
        <xsl:apply-templates select="Expr"/>
    </xsl:template>

</xsl:stylesheet>
