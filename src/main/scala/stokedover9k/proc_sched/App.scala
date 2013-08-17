package stokedover9k.proc_sched

import des._
import scala.io.Source
import stokedover9k.proc_sched.util.RandomGenStreams
import java.io.File

/**
 * @author ${user.name}
 */
object App {

  var procCount = 0

  var sched: Scheduler = null
  var des: Simulation = new Simulation
  var busy: Boolean = false;

  val rands = RandomGenStreams.fromFile("data/rfile")

  def PRINT(eventType: String)(p: Process) = println(des.currentTime + "\t [" + eventType + "]\t " + p)

  def genBurst(burst: Int, limit: Int = Int.MaxValue): Int = {
    Math.min(1 + rands.next(burst), limit)
  }

  def arrive(at: Int, totalTime: Int, cpuBurst: Int, ioBurst: Int) = {
    val p = Process(id = procCount, totalTime, burst = 0, cpuBurst, ioBurst)
    ready(p)
    procCount += 1
  }

  def ready(proc: Process) = {
    if (!busy) {
      busy = true
      des.afterDelayDo(0, schedule)
    }
    sched.insert(proc)

    PRINT("READY")(proc)
  }

  def schedule(): Unit = {
    val (proc, quota) = sched.next

    val proc1 = proc match {
      case Process(id, time, 0, cpu, io) => Process(id, time, genBurst(cpu, Math.min(time, quota)), cpu, io)
      case p                             => p
    }

    val runTime = Math.min(quota, proc1.burst)
    des.afterDelayDo(runTime, stop(proc1.run(runTime)))

    busy = true
    PRINT("RUNNING")(proc)
  }

  def stop(proc: Process) = {
    proc match {
      case Process(_, 0, 0, _, _)    => des.afterDelayDo(0, finish(proc))
      case Process(_, time, 0, _, _) => des.afterDelayDo(0, block(proc))
      case p                         => des.afterDelayDo(0, preempt(proc))
    }
    busy = false
    if (sched.hasNext)
      des.afterDelayDo(0, schedule())
  }

  def preempt(proc: Process) = {
    sched.insert(proc)
    PRINT("PREEMPT")(proc)
  }

  def finish(proc: Process) = PRINT("FINISH")(proc)

  def block(proc: Process) = {
    val io = genBurst(proc.io)
    des.afterDelayDo(io, ready(proc))
    PRINT("BLOCK")(proc)
  }

  def main(args: Array[String]) {
    println("Hello from ProcSched")

    for (line <- Source.fromFile(args(0)).getLines() if line.length > 0) {
      val proc = line.split("\\s+").map(x => Integer.parseInt(x.trim()))
      des.afterDelayDo(proc(0), arrive(proc(0), proc(1), proc(2), proc(3)))
    }

    sched = new SchedFifo

    println("[simulation] begin")
    des.run
    println("[simulation] complete")
  }
}
