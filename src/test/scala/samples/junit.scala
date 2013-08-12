package samples

import org.junit._
import Assert._
import org.scalatest.FunSpec
import scala.collection.mutable.Stack
import stokedover9k.proc_sched.des.Simulation
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/*
@Test
class AppTest {

    @Test
    def testOK() = assertTrue(true)

//    @Test
//    def testKO() = assertTrue(false)

}
*/

class SimulationSpecSuite extends FunSpec {
  
  describe("A DES Simulation") {
    
    it("should be constucted with time 0") {
      val sim = new Simulation
      assert(sim.currentTime === 1)
    }
    
  }
}


