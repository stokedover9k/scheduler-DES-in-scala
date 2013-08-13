package stokedover9k.proc_sched.util

import scala.io.Source

class RandomGenStream(stream: Iterator[Int]) extends Iterator[Int] {

  var nextCap : Int = 1
  
  def next : Int = stream.next % nextCap
  def next(cap: Int): Int = { nextCap = cap; next }

  def hasNext: Boolean = stream.hasNext
}

object RandomGenStreams {

  def fromFile(filename: String) = {
    val file = new java.io.File(filename)
    if( !file.canRead() ) error("cannot open file for RandomGenStream")
    def intItr = Source.fromFile(file).getLines().map(x => Integer.parseInt(x, 10))
    new RandomGenStream(new LoopingIterator(intItr))
  }
}
