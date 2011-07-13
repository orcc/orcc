<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">

    <xsl:import href="exprToString.xslt"/>
    <xsl:import href="typeToString.xslt"/>

    <xsl:output indent="yes" method="xml"/>

    <xsl:template match="text()"/>

    <!-- reads the layout in a file that has the same name as the source document,
        except with .layout extension. -->
    <xsl:param name="path"/>
    <xsl:variable name="file" select="replace($path, '(.+)[.].+', '$1.layout')"/>
    <xsl:variable name="layout" select="document($file)"/>

    <!-- returns two attributes x and y that contains the position of the vertex,
        if specified in $layout -->
    <xsl:template name="getVertexLayoutAttributes">
        <xsl:param name="vertexId"/>
        <xsl:if test="not(empty($layout))">
            <xsl:variable name="vertex" select="$layout/layout/vertices/vertex[@id = $vertexId]"/>
            <xsl:if test="not(empty($vertex))">
                <xsl:attribute name="x" select="$vertex/@x"/>
                <xsl:attribute name="y" select="$vertex/@y"/>
            </xsl:if>
        </xsl:if>
    </xsl:template>

    <!-- Top-level: XDF -> graph -->
    <xsl:template match="XDF">
        <graph type="XML Dataflow Network">
            <parameters>
                <parameter name="id" value="{@name}"/>
                <parameter name="network parameter">
                    <xsl:apply-templates select="Decl[@kind = 'Param']"/>
                </parameter>
                <parameter name="network variable declaration">
                    <xsl:apply-templates select="Decl[@kind = 'Variable']"/>
                </parameter>
            </parameters>

            <vertices>
                <xsl:apply-templates select="Port"/>
                <xsl:apply-templates select="Instance"/>
            </vertices>

            <edges>
                <xsl:apply-templates select="Connection"/>
            </edges>
        </graph>
    </xsl:template>

    <!-- Parameter declarations -->
    <xsl:template match="Decl[@kind = 'Param']">
        <xsl:choose>
            <xsl:when test="Type">
                <xsl:variable name="typeText">
                    <xsl:apply-templates select="Type"/>
                </xsl:variable>
                <element value="{concat($typeText cast as xs:string, ' ', @name)}"/>
            </xsl:when>
            <xsl:otherwise>
                <element value="{@name}"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- Variable declarations -->
    <xsl:template match="Decl[@kind = 'Variable']">
        <entry>
            <xsl:choose>
                <xsl:when test="Type">
                    <xsl:variable name="typeText">
                        <xsl:apply-templates select="Type"/>
                    </xsl:variable>
                    <xsl:attribute name="key" select="concat($typeText cast as xs:string, ' ', @name)"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:attribute name="key" select="@name"/>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:attribute name="value">
                <xsl:apply-templates select="Expr"/>
            </xsl:attribute>
        </entry>
    </xsl:template>

    <!-- Input/output ports -->
    <xsl:template match="Port">
        <vertex type="{concat(@kind, ' port')}">
            <xsl:call-template name="getVertexLayoutAttributes">
                <xsl:with-param name="vertexId" select="@name"/>
            </xsl:call-template>

            <parameters>
                <parameter name="id" value="{@name}"/>
                <parameter name="port type">
                    <xsl:attribute name="value">
                        <xsl:apply-templates select="Type"/>
                    </xsl:attribute>
                </parameter>
                <xsl:if test="Note[@kind = 'native']">
                    <parameter name="native" value="true"/>
                </xsl:if>
            </parameters>
        </vertex>
    </xsl:template>

    <!-- Instances -->
    <xsl:template match="Instance">
        <vertex type="Instance">
            <xsl:call-template name="getVertexLayoutAttributes">
                <xsl:with-param name="vertexId" select="@id"/>
            </xsl:call-template>

            <parameters>
                <parameter name="id" value="{@id}"/>
                <parameter name="refinement" value="{Class/@name}"/>
                <parameter name="instance parameter">
                    <xsl:apply-templates select="Parameter"/>
                </parameter>
                <xsl:if test="Attribute[@kind = 'Value' and @name = 'partName']">
                    <parameter name="part name">
                        <xsl:attribute name="value">
                            <xsl:apply-templates
                                select="Attribute[@kind = 'Value' and @name = 'partName']/Expr"/>
                        </xsl:attribute>
                    </parameter>
                </xsl:if>
                <xsl:if test="Attribute[@kind = 'Value' and @name = 'clockDomain']">
                    <parameter name="clock domain">
                        <xsl:attribute name="value">
                            <xsl:apply-templates
                                select="Attribute[@kind = 'Value' and @name = 'clockDomain']/Expr"/>
                        </xsl:attribute>
                    </parameter>
                </xsl:if>
                <xsl:if test="Attribute[@kind = 'Flag' and @name = 'skip']">
                    <parameter name="skip" value="true"/>
                </xsl:if>
            </parameters>
        </vertex>
    </xsl:template>

    <!-- Parameter instantiations -->
    <xsl:template match="Parameter">
        <entry key="{@name}">
            <xsl:attribute name="value">
                <xsl:apply-templates select="Expr"/>
            </xsl:attribute>
        </entry>
    </xsl:template>

    <!-- Connections -->
    <xsl:template match="Connection">
        <edge type="Connection">
            <xsl:choose>
                <xsl:when test="@src = ''">
                    <xsl:attribute name="source" select="@src-port"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:attribute name="source" select="@src"/>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:choose>
                <xsl:when test="@dst = ''">
                    <xsl:attribute name="target" select="@dst-port"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:attribute name="target" select="@dst"/>
                </xsl:otherwise>
            </xsl:choose>
            <parameters>
                <xsl:if test="@src != ''">
                    <parameter name="source port" value="{@src-port}"/>
                </xsl:if>
                <xsl:if test="@dst != ''">
                    <parameter name="target port" value="{@dst-port}"/>
                </xsl:if>
                <xsl:if test="Attribute[@kind = 'Value' and @name = 'bufferSize']">
                    <parameter name="buffer size">
                        <xsl:attribute name="value">
                            <xsl:apply-templates
                                select="Attribute[@kind = 'Value' and @name = 'bufferSize']/Expr"/>
                        </xsl:attribute>
                    </parameter>
                </xsl:if>
            </parameters>
        </edge>
    </xsl:template>

</xsl:stylesheet>
