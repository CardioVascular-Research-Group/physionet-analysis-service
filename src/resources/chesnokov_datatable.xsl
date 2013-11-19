<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
    	<html>
	        File Analyzed,Length,http://purl.bioontology.org/ontology/ECGT/ECGTermsv1:ECG_000000150|Lead,http://purl.bioontology.org/ontology/ECGT/ECGTermsv1:ECG_000001083|Total Beat Count,http://purl.bioontology.org/ontology/ECGT/ECGTermsv1:ECG_000001084|Ectopic Beat Count,http://purl.bioontology.org/ontology/ECGT/ECGTermsv1:ECG_000000701|QT Corrected Bazett,http://purl.bioontology.org/ontology/ECGT/ECGTermsv1:ECG_000001070|QTVI log,http://purl.bioontology.org/ontology/ECGT/ECGTermsv1:ECG_000001078|QT Dispersion,http://purl.bioontology.org/ontology/ECGT/ECGTermsv1:ECG_000001086|RR Interval Count,http://purl.bioontology.org/ontology/ECGT/ECGTermsv1:ECG_0000001088|RR Mean,http://purl.bioontology.org/ontology/ECGT/ECGTermsv1:ECG_000001091|RR Minimum,http://purl.bioontology.org/ontology/ECGT/ECGTermsv1:ECG_000001090|RR Maximum,http://purl.bioontology.org/ontology/ECGT/ECGTermsv1:ECG_000001094|RR Variance,http://purl.bioontology.org/ontology/ECGT/ECGTermsv1:ECG_000001096|RR Standard Deviation,http://purl.bioontology.org/ontology/ECGT/ECGTermsv1:ECG_000001085|QT Interval Count,http://purl.bioontology.org/ontology/ECGT/ECGTermsv1:ECG_000001089|QT Mean,http://purl.bioontology.org/ontology/ECGT/ECGTermsv1:ECG_000001093|QT Minimum,http://purl.bioontology.org/ontology/ECGT/ECGTermsv1:ECG_000001092|QT Maximum,http://purl.bioontology.org/ontology/ECGT/ECGTermsv1:ECG_000001095|QT Variance,http://purl.bioontology.org/ontology/ECGT/ECGTermsv1:ECG_000001097|QT Standard Deviation
	        <xsl:for-each select="/autoQRSResults/LeadResults/Lead">
	        	<xsl:text>&#10;</xsl:text> <!--  New Line -->
				<xsl:value-of select="/autoQRSResults/FileAnalyzed"/>
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="/autoQRSResults/Length"/>
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="../Lead"/>
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="../TotalBeatCount"/>
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="../EctopicBeatCount"/>
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="../QTCorrected_Bazett"/>
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="../QTVI_log"/>				
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="../QT_Dispersion"/>				
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="../RRIntervalResults/RRIntervalCount"/>
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="../RRIntervalResults/RRMean"/>
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="../RRIntervalResults/RRMin"/>
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="../RRIntervalResults/RRMax"/>
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="../RRIntervalResults/RRVariance"/>
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="../RRIntervalResults/RRStandardDeviation"/>
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="../QTIntervalResults/QTIntervalCount"/>
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="../QTIntervalResults/QTMean"/>
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="../QTIntervalResults/QTMin"/>
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="../QTIntervalResults/QTMax"/>
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="../QTIntervalResults/QTVariance"/>
				<xsl:value-of select="','"/>
			 	<xsl:value-of select="../QTIntervalResults/QTStandardDeviation"/>
	         </xsl:for-each>
        	 <xsl:text>&#10;</xsl:text> <!--  New Line -->
    	</html>
	</xsl:template>

</xsl:stylesheet>