package des

import org.junit.runner.RunWith
import org.scalatest.FunSpec
import stokedover9k.proc_sched.des.Simulation
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SimulationSpecSuite extends FunSpec {

  describe("A DES Simulation") {

    it("should be constucted with time 0") {
      val sim = new Simulation
      assert(sim.currentTime === 0)
    }

    it("should dispense events in time-ordered fashion") {
      val des = new Simulation
      var result = ""
      des.afterDelayDo(1, result += 1)
      des.afterDelayDo(3, result += 3)
      des.afterDelayDo(2, result += 2)
      
      des.run
      assert(result == "123")
    }
    
    it("should dispense events scheduled at the same time in FIFO fashion") {
      val des = new Simulation
      var result = ""
      des.afterDelayDo(1, result += "[11]")
      des.afterDelayDo(1, result += "[12]")
      des.afterDelayDo(1, result += "[13]")
      
      des.run
      assert(result == "[11][11][13]")
    }

  }
}
