package stokedover9k.proc_sched

abstract class Scheduler {
	def insert(p: Process)
	def hasNext : Boolean
	def next : (Process, Int)  // returns process to schedule, and maximum allowed time
}

class SchedFifo extends Scheduler {
  import scala.collection.mutable.Queue
  val q : scala.collection.mutable.Queue[Process] = Queue()
  
  def insert(p: Process) = q += p
  def hasNext = !q.isEmpty
  def next = (q.dequeue, Int.MaxValue)
}