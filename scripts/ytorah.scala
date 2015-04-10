import java.io._
import scala.util.matching.Regex

def prepTextBasic(filePath:String) : String = {
	scala.io.Source.fromFile(filePath).mkString.split( """-----------------""")(0)
}

def prepTextWithIdSplits(filePath:String): List[String] = {
 scala.io.Source.fromFile(filePath).mkString.split( """-----------------""")(0).split("""/\d{1,}:\d{1,}""").tail.toList
}

def createRankMap(directory:String): scala.collection.immutable.Map[String,Int] = {
	val files = new java.io.File(directory).listFiles.map(x=>x.toString).toList.filter(x=>x.contains(".txt"))
	val text = files.map{ x=> prepTextWithIdSplits(x).mkString("")}.mkString("")
	//need to remove punctuation and extra whitespace from below!
	val countList = text.split(" ").groupBy(identity).mapValues(_.size).toSeq.sortBy(_._2).reverse.map(x=>x._1).toList
	val rankingList = countList.map(x=>countList.indexOf(x))
	countList.zip(rankingList).toMap
}

def generateResults(directory:String):List[List[String]] ={
	val Ids = """/\d{1,}:\d{1,}""".r
	val files = new java.io.File(directory).listFiles.map(x=>x.toString).toList.filter(x=>x.contains(".txt"))
	val rankMap = createRankMap(directory)
	val Ids = """/\d{1,}:\d{1,}""".r
	val text = files.map{ x=> prepTextBasic(x)}.mkString("")
	val verses = files.map{ x=> prepTextWithIdSplits(x)}.flatten
	val counts = verses.map(x=>x.split(" ").map(x=>rankMap(x)).reduceLeft[Int](_+_)/x.split(" ").length)
	val ids = files.map{ x=> (Ids findAllIn (prepTextBasic(x))).map(y=> x.replaceAll(".txt","").replaceAll(directory + "/","") + y)}.flatten
	val idVerseDict = ids.zip(verses).toMap
	//with lowest scoring verses first
	ids.zip(counts).toSeq.sortBy(_._2).toList.map(x=>List(x._1, idVerseDict(x._1), x._2.toString))
}

def printHtml(directory:String) ={
	val wrt = new PrintWriter(new File("tanakhResults.html"))
	val freqList = generateResults(directory)
	val header = """<meta charset="utf-8" /> 
			<!DOCTYPE html lang="yid">
			<html>
			<head>
			<title>""" + directory + "reader" + """</title>
			</head><body>""" 
	val body = "<table>" + freqList.map(x=>"<tr><td class='verse-num'>" + x(0) + "</td><td dir='rtl' class='verse'>" + x(1)+ "</td><td class='freq-rank'>" + x(2) + "</td></tr>").mkString("") + "</table>"
	val footer = "</body></html>"
	wrt.print(header + body + footer)
	wrt.flush	
	wrt.close
}

// e.g. :

printHtml("torah")

//filter page with javascript in different ways (by book, etc, change formatting)



