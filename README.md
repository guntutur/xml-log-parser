# Oracle Communications Order and Service Management Simple Log Parser

This simple jar lib act as a parser to find an occurrences of presumably ncx-id milestone sent by external party

takes 3 args :
- path to dir yg isinya file untuk di cari, eg : /etc/
- string pattern biasanya seh ncx-id, eg : 3-123123123
- kind of search, anatara MS (log_ms) atau JSM (log_jms), udh tau sendirilah ya maksudnya

to execute java lib is as simple as

`java -jar logparser-1.0-SNAPSHOT.jar` 

and then follow the on screen instruction
the execution after effect is a generation of file with name `xml_pattern_<pattern>.txt`
file will be generated regardless the pattern is found or not
