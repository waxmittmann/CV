package cvgen.renderers

object OdtFormat {
  sealed trait FontSize { val textStyle: String }
  case object Small extends FontSize { val textStyle = "T3"}
  case object Normal extends FontSize { val textStyle = "L1" }

  /**
    The main document elements that wrap the content

    $header
    $style
    $contentHeader
    ${content here...}
    $contentFooter
    $footer
   **/
  val header =
    """<?xml version="1.0" encoding="UTF-8"?><office:document-content xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0" xmlns:style="urn:oasis:names:tc:opendocument:xmlns:style:1.0" xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0" xmlns:table="urn:oasis:names:tc:opendocument:xmlns:table:1.0" xmlns:draw="urn:oasis:names:tc:opendocument:xmlns:drawing:1.0" xmlns:fo="urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:meta="urn:oasis:names:tc:opendocument:xmlns:meta:1.0" xmlns:number="urn:oasis:names:tc:opendocument:xmlns:datastyle:1.0" xmlns:svg="urn:oasis:names:tc:opendocument:xmlns:svg-compatible:1.0" xmlns:chart="urn:oasis:names:tc:opendocument:xmlns:chart:1.0" xmlns:dr3d="urn:oasis:names:tc:opendocument:xmlns:dr3d:1.0" xmlns:math="http://www.w3.org/1998/Math/MathML" xmlns:form="urn:oasis:names:tc:opendocument:xmlns:form:1.0" xmlns:script="urn:oasis:names:tc:opendocument:xmlns:script:1.0" xmlns:ooo="http://openoffice.org/2004/office" xmlns:ooow="http://openoffice.org/2004/writer" xmlns:oooc="http://openoffice.org/2004/calc" xmlns:dom="http://www.w3.org/2001/xml-events" xmlns:xforms="http://www.w3.org/2002/xforms" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:rpt="http://openoffice.org/2005/report" xmlns:of="urn:oasis:names:tc:opendocument:xmlns:of:1.2" xmlns:xhtml="http://www.w3.org/1999/xhtml" xmlns:grddl="http://www.w3.org/2003/g/data-view#" xmlns:officeooo="http://openoffice.org/2009/office" xmlns:tableooo="http://openoffice.org/2009/table" xmlns:drawooo="http://openoffice.org/2010/draw" xmlns:calcext="urn:org:documentfoundation:names:experimental:calc:xmlns:calcext:1.0" xmlns:loext="urn:org:documentfoundation:names:experimental:office:xmlns:loext:1.0" xmlns:field="urn:openoffice:names:experimental:ooo-ms-interop:xmlns:field:1.0" xmlns:formx="urn:openoffice:names:experimental:ooxml-odf-interop:xmlns:form:1.0" xmlns:css3t="http://www.w3.org/TR/css3-text/" office:version="1.2"><office:scripts/>"""

  val footer =
    """</office:document-content>"""

  val styles =
    """<office:font-face-decls><style:font-face style:name="OpenSymbol" svg:font-family="OpenSymbol" style:font-charset="x-symbol"/><style:font-face style:name="Lohit Devanagari" svg:font-family="&apos;Lohit Devanagari&apos;"/><style:font-face style:name="Calibri" svg:font-family="Calibri" style:font-family-generic="roman" style:font-pitch="variable"/><style:font-face style:name="Cambria" svg:font-family="Cambria" style:font-family-generic="roman" style:font-pitch="variable"/><style:font-face style:name="Liberation Sans" svg:font-family="&apos;Liberation Sans&apos;" style:font-family-generic="roman" style:font-pitch="variable"/><style:font-face style:name="Arial" svg:font-family="Arial" style:font-family-generic="system" style:font-pitch="variable"/><style:font-face style:name="Calibri1" svg:font-family="Calibri" style:font-family-generic="system" style:font-pitch="variable"/><style:font-face style:name="Cambria1" svg:font-family="Cambria" style:font-family-generic="system" style:font-pitch="variable"/><style:font-face style:name="Courier New" svg:font-family="&apos;Courier New&apos;" style:font-family-generic="system" style:font-pitch="variable"/><style:font-face style:name="Lucida Sans" svg:font-family="&apos;Lucida Sans&apos;" style:font-family-generic="system" style:font-pitch="variable"/><style:font-face style:name="Microsoft YaHei" svg:font-family="&apos;Microsoft YaHei&apos;" style:font-family-generic="system" style:font-pitch="variable"/></office:font-face-decls><office:automatic-styles><style:style style:name="P1" style:family="paragraph" style:parent-style-name="Standard"><style:paragraph-properties fo:orphans="0" fo:widows="0"/></style:style><style:style style:name="P2" style:family="paragraph" style:parent-style-name="Standard"><style:text-properties officeooo:rsid="00063377" officeooo:paragraph-rsid="00063377"/></style:style><style:style style:name="P3" style:family="paragraph" style:parent-style-name="Standard"><style:paragraph-properties fo:margin-left="0cm" fo:margin-right="-0.39cm" fo:margin-top="0cm" fo:margin-bottom="0.212cm" loext:contextual-spacing="true" fo:line-height="110%" fo:text-align="justify" style:justify-single-word="false" fo:orphans="0" fo:widows="0" fo:text-indent="0cm" style:auto-text-indent="false"/></style:style><style:style style:name="P4" style:family="paragraph" style:parent-style-name="Standard" style:list-style-name="WWNum1"><style:paragraph-properties fo:margin-left="1.27cm" fo:margin-right="0cm" fo:margin-top="0cm" fo:margin-bottom="0.212cm" loext:contextual-spacing="true" fo:orphans="0" fo:widows="0" fo:text-indent="-0.635cm" style:auto-text-indent="false"/></style:style><style:style style:name="P5" style:family="paragraph" style:parent-style-name="Standard" style:list-style-name="WWNum1"><style:paragraph-properties fo:margin-left="1.27cm" fo:margin-right="0cm" fo:margin-top="0cm" fo:margin-bottom="0cm" loext:contextual-spacing="true" fo:orphans="0" fo:widows="0" fo:text-indent="-0.635cm" style:auto-text-indent="false"/></style:style><style:style style:name="P6" style:family="paragraph" style:parent-style-name="Standard"><style:paragraph-properties fo:margin-top="0cm" fo:margin-bottom="0.212cm" loext:contextual-spacing="false" fo:orphans="0" fo:widows="0"/></style:style><style:style style:name="P7" style:family="paragraph" style:parent-style-name="Standard"><style:paragraph-properties fo:margin-left="1.27cm" fo:margin-right="0cm" fo:margin-top="0cm" fo:margin-bottom="0.212cm" loext:contextual-spacing="false" fo:orphans="0" fo:widows="0" fo:text-indent="0cm" style:auto-text-indent="false"/></style:style><style:style style:name="P8" style:family="paragraph" style:parent-style-name="Standard"><style:paragraph-properties fo:margin-left="0cm" fo:margin-right="-1.199cm" fo:line-height="105%" fo:orphans="0" fo:widows="0" fo:text-indent="0cm" style:auto-text-indent="false"/></style:style><style:style style:name="P9" style:family="paragraph" style:parent-style-name="Standard"><style:paragraph-properties fo:margin-left="0cm" fo:margin-right="-0.917cm" fo:line-height="108%" fo:text-align="justify" style:justify-single-word="false" fo:orphans="0" fo:widows="0" fo:text-indent="0cm" style:auto-text-indent="false"/></style:style><style:style style:name="P10" style:family="paragraph" style:parent-style-name="Text_20_body"><style:paragraph-properties fo:margin-top="0cm" fo:margin-bottom="0cm" loext:contextual-spacing="false"/></style:style><style:style style:name="P11" style:family="paragraph" style:parent-style-name="Text_20_body" style:list-style-name="L1"><style:paragraph-properties fo:margin-top="0cm" fo:margin-bottom="0cm" loext:contextual-spacing="false"/></style:style><style:style style:name="P12" style:family="paragraph" style:parent-style-name="Heading_20_1"><style:text-properties officeooo:rsid="00063377" officeooo:paragraph-rsid="00063377"/></style:style><style:style style:name="P13" style:family="paragraph" style:parent-style-name="Title" style:master-page-name="Standard"><style:paragraph-properties fo:margin-top="0cm" fo:margin-bottom="0.212cm" loext:contextual-spacing="false" style:page-number="1"/><style:text-properties officeooo:rsid="00063377" officeooo:paragraph-rsid="00063377"/></style:style><style:style style:name="P14" style:family="paragraph" style:parent-style-name="Subtitle"><style:paragraph-properties fo:margin-top="0cm" fo:margin-bottom="0.212cm" loext:contextual-spacing="false"/></style:style><style:style style:name="T1" style:family="text"><style:text-properties style:font-name="Cambria" style:font-name-asian="Cambria1" style:font-name-complex="Cambria1"/></style:style><style:style style:name="T2" style:family="text"><style:text-properties style:font-name="Cambria" officeooo:rsid="00063377" style:font-name-asian="Cambria1" style:font-name-complex="Cambria1"/></style:style><style:style style:name="T3" style:family="text"><style:text-properties fo:font-size="10pt" style:font-size-asian="10pt" style:font-size-complex="10pt"/></style:style><style:style style:name="T4" style:family="text"><style:text-properties fo:color="#0000ff" fo:font-size="10pt" style:font-size-asian="10pt" style:font-size-complex="10pt"/></style:style><style:style style:name="T5" style:family="text"><style:text-properties fo:color="#4f81bd" style:font-name="Calibri" fo:font-size="13pt" fo:font-weight="bold" style:font-name-asian="Calibri1" style:font-size-asian="13pt" style:font-weight-asian="bold" style:font-name-complex="Calibri1" style:font-size-complex="13pt"/></style:style><style:style style:name="T6" style:family="text"><style:text-properties fo:color="#4f81bd" fo:font-size="13pt" fo:font-weight="bold" style:font-size-asian="13pt" style:font-weight-asian="bold" style:font-size-complex="13pt"/></style:style><style:style style:name="T7" style:family="text"><style:text-properties officeooo:rsid="00063377"/></style:style><text:list-style style:name="L1"><text:list-level-style-bullet text:level="1" text:style-name="Bullet_20_Symbols" text:bullet-char="•"><style:list-level-properties text:list-level-position-and-space-mode="label-alignment"><style:list-level-label-alignment text:label-followed-by="listtab" text:list-tab-stop-position="1.27cm" fo:text-indent="-0.635cm" fo:margin-left="1.27cm"/></style:list-level-properties></text:list-level-style-bullet><text:list-level-style-bullet text:level="2" text:style-name="Bullet_20_Symbols" text:bullet-char="◦"><style:list-level-properties text:list-level-position-and-space-mode="label-alignment"><style:list-level-label-alignment text:label-followed-by="listtab" text:list-tab-stop-position="1.905cm" fo:text-indent="-0.635cm" fo:margin-left="1.905cm"/></style:list-level-properties></text:list-level-style-bullet><text:list-level-style-bullet text:level="3" text:style-name="Bullet_20_Symbols" text:bullet-char="▪"><style:list-level-properties text:list-level-position-and-space-mode="label-alignment"><style:list-level-label-alignment text:label-followed-by="listtab" text:list-tab-stop-position="2.54cm" fo:text-indent="-0.635cm" fo:margin-left="2.54cm"/></style:list-level-properties></text:list-level-style-bullet><text:list-level-style-bullet text:level="4" text:style-name="Bullet_20_Symbols" text:bullet-char="•"><style:list-level-properties text:list-level-position-and-space-mode="label-alignment"><style:list-level-label-alignment text:label-followed-by="listtab" text:list-tab-stop-position="3.175cm" fo:text-indent="-0.635cm" fo:margin-left="3.175cm"/></style:list-level-properties></text:list-level-style-bullet><text:list-level-style-bullet text:level="5" text:style-name="Bullet_20_Symbols" text:bullet-char="◦"><style:list-level-properties text:list-level-position-and-space-mode="label-alignment"><style:list-level-label-alignment text:label-followed-by="listtab" text:list-tab-stop-position="3.81cm" fo:text-indent="-0.635cm" fo:margin-left="3.81cm"/></style:list-level-properties></text:list-level-style-bullet><text:list-level-style-bullet text:level="6" text:style-name="Bullet_20_Symbols" text:bullet-char="▪"><style:list-level-properties text:list-level-position-and-space-mode="label-alignment"><style:list-level-label-alignment text:label-followed-by="listtab" text:list-tab-stop-position="4.445cm" fo:text-indent="-0.635cm" fo:margin-left="4.445cm"/></style:list-level-properties></text:list-level-style-bullet><text:list-level-style-bullet text:level="7" text:style-name="Bullet_20_Symbols" text:bullet-char="•"><style:list-level-properties text:list-level-position-and-space-mode="label-alignment"><style:list-level-label-alignment text:label-followed-by="listtab" text:list-tab-stop-position="5.08cm" fo:text-indent="-0.635cm" fo:margin-left="5.08cm"/></style:list-level-properties></text:list-level-style-bullet><text:list-level-style-bullet text:level="8" text:style-name="Bullet_20_Symbols" text:bullet-char="◦"><style:list-level-properties text:list-level-position-and-space-mode="label-alignment"><style:list-level-label-alignment text:label-followed-by="listtab" text:list-tab-stop-position="5.715cm" fo:text-indent="-0.635cm" fo:margin-left="5.715cm"/></style:list-level-properties></text:list-level-style-bullet><text:list-level-style-bullet text:level="9" text:style-name="Bullet_20_Symbols" text:bullet-char="▪"><style:list-level-properties text:list-level-position-and-space-mode="label-alignment"><style:list-level-label-alignment text:label-followed-by="listtab" text:list-tab-stop-position="6.35cm" fo:text-indent="-0.635cm" fo:margin-left="6.35cm"/></style:list-level-properties></text:list-level-style-bullet><text:list-level-style-bullet text:level="10" text:style-name="Bullet_20_Symbols" text:bullet-char="•"><style:list-level-properties text:list-level-position-and-space-mode="label-alignment"><style:list-level-label-alignment text:label-followed-by="listtab" text:list-tab-stop-position="6.985cm" fo:text-indent="-0.635cm" fo:margin-left="6.985cm"/></style:list-level-properties></text:list-level-style-bullet></text:list-style></office:automatic-styles>"""

  val contentHeader =
    """<office:body><office:text>"""

  val contentFooter =
    """</office:text></office:body>"""

  /**
    Elements for creating the document body
   **/
  def listHeader(id: Long) =
    s"""<text:list xml:id="list$id" text:style-name="L1">"""

  val listFooter = "</text:list>"

  def listItem(content: String) = s"""<text:list-item><text:p text:style-name="P11">${escapeXml(content)}</text:p></text:list-item>"""

  val spacingParagraph = """<text:p text:style-name="P10"/>"""

  def hyperlink(text: String, url: String, fontSize: FontSize = Normal) =
    s"""<text:a xlink:type="simple" xlink:href="$url" text:style-name="Internet_20_link" text:visited-style-name="Visited_20_Internet_20_Link"><text:span text:style-name="Internet_20_link"><text:span text:style-name="${fontSize.textStyle}">${escapeXml(text)}</text:span></text:span></text:a>"""

  def textParagraph(text: String, fontSize: FontSize = Normal) =
    s"""${startParagraph(fontSize)}${escapeXml(text)}$endParagraph"""

  def startParagraph(fontSize: FontSize) = s"""<text:p text:style-name="Standard"><text:span text:style-name="${fontSize.textStyle}">"""

  def endParagraph = """</text:span></text:p>"""

  def title(text: String) =
    s"""<text:p text:style-name="P13"><text:span text:style-name="T1">${escapeXml(text)}</text:span></text:p>"""

  def subtitle(text: String) =
    s"""<text:p text:style-name="P14"><text:span text:style-name="T1">&gt; </text:span><text:span text:style-name="T2">${escapeXml(text)}</text:span></text:p>"""

  def heading1(text: String) =
    s"""<text:h text:style-name="Heading_20_1" text:outline-level="1"><text:span text:style-name="T1">${escapeXml(text)}</text:span></text:h>"""

  def heading2(text: String) =
    s"""<text:h text:style-name="Heading_20_2" text:outline-level="2"><text:span text:style-name="T7">${escapeXml(text)}</text:span><text:span text:style-name="T1"></text:span></text:h>"""

  def sectionHeading(date: String, title: String) =
    s"""<text:p text:style-name="P1"><text:soft-page-break/><text:span text:style-name="T5">&gt; $date<text:line-break/></text:span><text:span text:style-name="T5">$title</text:span></text:p>"""

  def escapeXml(str: String): String = xml.Utility.escape(str)
}
