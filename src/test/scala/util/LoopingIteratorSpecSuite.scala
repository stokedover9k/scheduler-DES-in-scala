package util

import org.junit.runner.RunWith
import org.scalatest.FunSpec
import stokedover9k.proc_sched.util.LoopingIterator
import org.scalatest.GivenWhenThen
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class LoopingIteratorSpecSuite extends FunSpec with GivenWhenThen {

  describe("A LoopingIterator") {

    it("should be empty when iterating an empty stream") {
      def startItr : Iterator[String] = List().iterator
      val itr = new LoopingIterator[String](startItr)
      assert( !itr.hasNext )
    }
    
    it("should have next if created with non-empty stream") {
      val itr = new LoopingIterator(List(1).iterator)
      assert( itr.hasNext )
    }

    it("should loop around when stream is exhausted") {
    	given("a non-empty stream")
    	var n = 0
      def startItr : Iterator[Int] = (1::2::List()).iterator
      val itr = new LoopingIterator[Int](startItr)

      assert( itr.next == 1 ) ;  assert( itr.next == 2 )
      
      when("stream is exhausted") ; then("should go back to the beginning")
      assert( itr.next == 1 ) ; assert( itr.next == 2 )
      
      when("stream is exhausted again") ; then("should go back to the beginning")
      assert( itr.next == 1 ) ; assert( itr.next == 2 )
    }
  }
}