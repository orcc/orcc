<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">

    <xsl:import href="exprToString.xslt"/>

    <xsl:output indent="yes" method="xml"/>

    <xsl:template match="text()"/>

    <!-- Top-level: Actor -> graph -->
    <xsl:template match="Actor">
        <graph type="CAL actor">
            <parameters>
                <parameter name="id" value="{@name}"/>

                <parameter name="actor parameter">
                    <xsl:apply-templates select="Decl[@kind = 'Parameter']"/>
                </parameter>

                <parameter name="actor variable declaration">
                    <xsl:apply-templates select="Decl[@kind = 'Variable']"/>
                </parameter>
            </parameters>

            <vertices>
                <xsl:apply-templates select="Port"/>
            </vertices>

            <edges/>
        </graph>
    </xsl:template>

    <!-- Parameter declarations -->
    <xsl:template match="Decl[@kind = 'Parameter']">
        <element value="{@name}"/>
    </xsl:template>

    <!-- Variable declarations -->
    <xsl:template match="Decl[@kind = 'Variable']">
        <entry key="{@name}">
            <xsl:attribute name="value">
                <xsl:apply-templates select="Expr"/>
            </xsl:attribute>
        </entry>
    </xsl:template>

    <!-- Input/output ports -->
    <xsl:template match="Port">
        <vertex type="{concat(@kind, ' port')}">
            <parameters>
                <parameter name="id" value="{@name}"/>
            </parameters>
        </vertex>
    </xsl:template>

</xsl:stylesheet>
