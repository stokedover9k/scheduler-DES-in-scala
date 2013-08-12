package stokedover9k.proc_sched

import des._

/**
 * @author ${user.name}
 */
object App {

  def foo(x: Array[String]) = x.foldLeft("")((a, b) => a + b)

  def main(args: Array[String]) {
    println("Hello from ProcSched")

    val des = new Simulation
    des.afterDelayDo(1, println("1: hello"))
    des.afterDelayDo(3, println("3: hello"))
    des.afterDelayDo(2, println("2: hello"))

    println("[simulation] begin")
    des.run
    println("[simulation] complete")
  }
}
