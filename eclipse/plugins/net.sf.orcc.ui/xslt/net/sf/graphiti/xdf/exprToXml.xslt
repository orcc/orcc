<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">

    <xsl:output indent="yes" method="xml"/>

    <xsl:template match="text()"/>

    <xsl:template match="Boolean">
        <Expr kind="Literal" literal-kind="Boolean" value="{text()}"/>
    </xsl:template>
    
    <xsl:template match="Integer">
        <Expr kind="Literal" literal-kind="Integer" value="{text()}"/>
    </xsl:template>
    
    <xsl:template match="List">
        <Expr kind="List">
            <xsl:apply-templates/>
        </Expr>
    </xsl:template>
    
    <xsl:template match="Real">
        <Expr kind="Literal" literal-kind="Real" value="{text()}"/>
    </xsl:template>
    
    <xsl:template match="String">
        <xsl:variable name="textValue" select="text()"/>
        <xsl:variable name="string" select="substring($textValue, 2, string-length($textValue) - 2)"/>
        <Expr kind="Literal" literal-kind="String" value="{$string}"/>
    </xsl:template>
    
    <xsl:template match="Var">
        <Expr kind="Var" name="{text()}"/>
    </xsl:template>

    <xsl:template match="BinOp">
        <Op name="{text()}"/>
    </xsl:template>
    
    <xsl:template match="UnOp">
        <Op name="{text()}"/>
    </xsl:template>

    <xsl:template match="Expression">
        <xsl:choose>
            <xsl:when test="count(BinOp) = 0 and count(UnOp) = 0">
                <!-- this expression is a single factor -->
                <xsl:apply-templates/>
            </xsl:when>
            <xsl:when test="count(BinOp) = 0 and count(UnOp) != 0">
                <!-- this expression is a unary operation -->
                <Expr kind="UnaryOp">
                    <xsl:apply-templates/>
                </Expr>
            </xsl:when>
            <xsl:otherwise>
                <!-- this expression is a sequence of binary operations -->
                <Expr kind="BinOpSeq">
                    <xsl:apply-templates/>
                </Expr>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>
