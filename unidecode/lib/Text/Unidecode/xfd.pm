# Time-stamp: "2016-08-06 22:03:39 MDT"
$Text::Unidecode::Char[0xfd] = [
"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
'[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]',
"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
'[?]', '[?]', "", "", "", "", "", "", "", "", "", "", "", "", "", "",
"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
"", "", "", "", "", "", "", "", '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]',

#======================================================================
# FFD0 to FFEF = "Not A Character..."
#FDD0
'[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]',
#FDE0
'[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]', '[?]',
#======================================================================


#======================================================================
#perl -CO -e 'for( 0xFDF0 .. 0xFDFD) { printf "\"\",	# %04X p%cq\n", $_, $_; }'

"{Salla}",	# FDF0 pﷰq
	#  FDF0	ARABIC LIGATURE SALLA USED AS KORANIC STOP SIGN ISOLATED FORM
"{Qala}",	# FDF1 pﷱq
	#  FDF1	ARABIC LIGATURE QALA USED AS KORANIC STOP SIGN ISOLATED FORM
"Allah",	# FDF2 pﷲq
	#  FDF2	ARABIC LIGATURE ALLAH ISOLATED FORM
"Akbar",	# FDF3 pﷳq
	#  FDF3	ARABIC LIGATURE AKBAR ISOLATED FORM
"Mohammed",	# FDF4 pﷴq
	#  FDF4	ARABIC LIGATURE MOHAMMAD ISOLATED FORM
"SL`M",	# FDF5 pﷵq
	#  FDF5	ARABIC LIGATURE SALAM ISOLATED FORM
"Rasul",	# FDF6 pﷶq
	#  FDF6	ARABIC LIGATURE RASOUL ISOLATED FORM
"{Alayhi}",	# FDF7 pﷷq
	#  FDF7	ARABIC LIGATURE ALAYHE ISOLATED FORM
"{WaSallam}",	# FDF8 pﷸq
	#  FDF8	ARABIC LIGATURE WASALLAM ISOLATED FORM
"{Salla}",	# FDF9 pﷹq
	#  FDF9	ARABIC LIGATURE SALLA ISOLATED FORM
"{Salla Llahu Alayhi WaSallam}",	# FDFA pﷺq
	#  FDFA	ARABIC LIGATURE SALLALLAHOU ALAYHE WASALLAM

"{Jalla Jalalahu}",	# FDFB pﷻq
	#  FDFB	ARABIC LIGATURE JALLAJALALOUHOU



"Rial ",	# FDFC p﷼q
	#  FDFC	RIAL SIGN

"{Bismillah Ar-Rahman Ar-Rahimi}",	# FDFD p﷽q
	#  FDFD	ARABIC LIGATURE BISMILLAH AR-RAHMAN AR-RAHEEM


# And unassigned:
"[?]", #FDFE
"[?]", #FDFF

];
1;
