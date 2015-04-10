import java.io._
import scala.util.matching.Regex

def prepTextBasic(filePath:String) : String = {
	scala.io.Source.fromFile(filePath).mkString.split( """-----------------""")(0)
}

def prepTextWithIdSplits(filePath:String): List[String] = {
 scala.io.Source.fromFile(filePath).mkString.split( """-----------------""")(0).split("""/\d{1,}:\d{1,}""").tail.toList
}


//counting words across the corpus

def createCountMaps(filePath:String): scala.collection.immutable.Map[String,Int] ={
	val text = prepTextWithIdSplits(filePath).mkString("")
	//need to remove punctuation and extra whitespace from below!
	text.split(" ").groupBy(identity).mapValues(_.size).toMap
}

def createRankMap(directory:String): scala.collection.immutable.Map[String,Int] = {
	val files = new java.io.File(directory).listFiles.map(x=>x.toString).toList.filter(x=>x.contains(".txt"))
	val ms = files.map(x=>createCountMaps(x))
	val masterCountMap = mergeMap(ms)((v1, v2) => v1 + v2)
	val countList = masterCountMap.toSeq.sortBy(_._2).reverse.map(x=>x._1).toList
	val rankingList = countList.map(x=>countList.indexOf(x))
	countList.zip(rankingList).toMap
}

def ranksAndCounts(directory:String): scala.collection.immutable.Map[String,Int] = {
	val files = new java.io.File(directory).listFiles.map(x=>x.toString).toList.filter(x=>x.contains(".txt"))
	val ms = files.map(x=>createCountMaps(x))
	val masterCountMap = mergeMap(ms)((v1, v2) => v1 + v2)
	val countList = masterCountMap.toSeq.sortBy(_._2).reverse.toList
	val rankingList = countList.map(x=>countList.indexOf(x))
	countList.zip(rankingList).map(x=>x._2.toString + "\t" + x._1._1 + "\t" + x._1._2.toString)
}

def generateResults(filePathList:List[String], directory:String, rankMap:scala.collection.immutable.Map[String,Int]):List[List[String]] ={
	val Ids = """/\d{1,}:\d{1,}""".r
	val files = filePathList
	val text = files.map{ x=> prepTextBasic(x)}.mkString("")
	val verses = files.map{ x=> prepTextWithIdSplits(x)}.flatten
	val counts = verses.map(x=> if(x.split(" ").length > 0){adSum(x.split(" ").map(x=>rankMap(x)))/x.split(" ").length}
								else{9999999})
	val ids = files.map{ x=> (Ids findAllIn (prepTextBasic(x))).map(y=> x.replaceAll(".txt","").replaceAll(directory + "/","") + y)}.flatten
	val idVerseDict = ids.zip(verses).toMap
	//with lowest scoring verses first
	ids.zip(counts).toSeq.sortBy(_._2).toList.map(x=>List(x._1, idVerseDict(x._1), x._2.toString))
}

def printCsv(filePathList:List[String], directory:String, rankMap:scala.collection.immutable.Map[String,Int]) ={
	val wrt = new PrintWriter(new File("results.txt"))
	val freqList = generateResults(filePathList, directory, rankMap)
	val body = freqList.map(x=>x(0) + "\t" + x(1)+ "\t" + x(2) + "\n").mkString("")
	wrt.print(body)
	wrt.flush	
	wrt.close
}



def printHtml(freqList:List[List[String]]) ={
	val wrt = new PrintWriter(new File("results.html"))
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

//val masterRankMap = createRankMap("ytanakh")

//val files = new java.io.File("ytanakh").listFiles.map(x=>x.toString).toList.filter(x=>x.contains(".txt"))

//printCsv(files, "ytanakh",masterRankMap)

//below function from: http://stackoverflow.com/questions/1262741/scala-how-to-merge-a-collection-of-maps

def mergeMap[A, B](ms: List[Map[A, B]])(f: (B, B) => B): Map[A, B] =
  (Map[A, B]() /: (for (m <- ms; kv <- m) yield kv)) { (a, kv) =>
    a + (if (a.contains(kv._1)) kv._1 -> f(a(kv._1), kv._2) else kv)
  }

 def adSum(ad: Array[Int]) = {
  var sum = 0
  var i = 0
  while (i<ad.length) { sum += ad(i); i += 1 }
  sum
}



