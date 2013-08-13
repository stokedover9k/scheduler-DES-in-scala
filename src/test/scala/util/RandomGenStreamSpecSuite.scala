package util

import org.junit.runner.RunWith
import org.scalatest.FunSpec
import org.scalatest.GivenWhenThen
import org.scalatest.junit.JUnitRunner
import java.io.FileWriter
import scala.io.Source
import scala.compat.Platform
import stokedover9k.proc_sched.util.RandomGenStream
import stokedover9k.proc_sched.util.LoopingIterator
import stokedover9k.proc_sched.util.RandomGenStreams

@RunWith(classOf[JUnitRunner])
class RandomGenStreamSpecSuite extends FunSpec with GivenWhenThen {

  val file = java.io.File.createTempFile("_tmp_rand_nums_" + Platform.currentTime, ".txt")
  
  val writer = new FileWriter(file)
  writer.write("11\n12\n13\n")
  writer.close()

  describe("A RandomGenStream") {
    
    it("test was able to write temp file") {
      assert(file != null)
    }

    it("stream created from file") {
      val rands = RandomGenStreams.fromFile(file.getAbsolutePath())
      
      when("generator is created")
      then("has next")
      assert( rands.hasNext )
      
      when("stream querried")
      then("returns capped values")
      assert( rands.next(8) == 3 )
      assert( rands.next(8) == 4 )
      assert( rands.next(8) == 5 )
      
      when("stream reaches end of file")
      then("loops around and continues to return capped values")
      assert( rands.next(10) == 1 )
      assert( rands.next(10) == 2 )
      assert( rands.next(10) == 3 )
    }
  }
}