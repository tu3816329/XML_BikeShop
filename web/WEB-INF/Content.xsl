<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : header.xsl
    Created on : October 26, 2017, 2:33 PM
    Author     : tudt
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:x="http://www.thientu.com/news" 
                version="1.0">
    <xsl:output method="text"/>
    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html>
            <body>
                <xsl:apply-templates/>
            </body>
        </html>
        
    </xsl:template> 
    <xsl:template match="/x:ListNews">
        <html>        
            <body>
                <table border="0">
                    <tbody>
                        <xsl:for-each select="/x:News">
                            <tr style="padding: 5px">
                                <td style="border-bottom: 1px solid #888;">
                                    <xsl:element name="img">
                                        <xsl:attribute name="width">200px</xsl:attribute>
                                        <xsl:attribute name="height">100px</xsl:attribute>
                                        <!--<img width="200px" height="100px">-->
                                        <xsl:attribute name="src">
                                            NewsImage/<xsl:value-of select="x:PreviewImage"/>
                                        </xsl:attribute>
                                        <!--</img>--> 
                                    </xsl:element>
                                </td>
                                <td style="border-bottom: 1px solid #888;">
                                    <xsl:element name="a">
                                        <!--<a style="text-decoration: none;color: black">-->
                                        <xsl:attribute name="href">
                                            ProcessServlet?btAction=NewsDetail&amp;&amp;id=<xsl:value-of select="x:Id"/>
                                        </xsl:attribute>
                                        <h6>
                                            <xsl:value-of select="x:Title"/>
                                        </h6>
                                        <p>
                                            <xsl:value-of select="x:Description"/>
                                        </p>
                                        <!--</a>-->
                                    </xsl:element>
                                </td>
                            </tr>                        
                        </xsl:for-each>
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
